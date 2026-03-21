//  WahBuddy Extended
//  Copyright (C) 2026-present Ayus Chatterjee
//
//  This program is free software: you can redistribute it and/or modify
//  it under the terms of the GNU General Public License as published by
//  the Free Software Foundation, either version 3 of the License, or
//  (at your option) any later version.

//  This program is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//  GNU General Public License for more details.

//  You should have received a copy of the GNU General Public License
//  along with this program.  If not, see <https://www.gnu.org/licenses/>.

package com.wahbuddyext

import android.app.Application
import android.os.Handler
import android.os.Looper
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge.log
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.*
import java.security.cert.X509Certificate
import java.util.Calendar
import java.util.TimeZone
import java.util.concurrent.Executors

class XposedInit : IXposedHookLoadPackage {

    private var isLoopActive = false
    private val executor = Executors.newSingleThreadExecutor()

    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
        if (lpparam.packageName != "com.whatsapp") return

        // Hook the main process not subprocesses
        if (lpparam.processName != "com.whatsapp") return

        bypassSSLValidation()

        try {
            XposedHelpers.findAndHookMethod(
                "android.app.Application",
                lpparam.classLoader,
                "onCreate",
                object : XC_MethodHook() {
                    override fun afterHookedMethod(param: MethodHookParam) {
                        if (!isLoopActive) {
                            isLoopActive = true
                            val app = param.thisObject as Application
                            log("[WahBuddy] Hooked to WhatsApp Sucessfully !")
                            syncAboutLoop(app, lpparam.classLoader)
                        }
                    }
                }
            )
        } catch (t: Throwable) {
            log("[WahBuddy] Hook Error: ${t.message}")
        }
    }

    private fun syncAboutLoop(app: Application, classLoader: ClassLoader) {
        val handler = Handler(Looper.getMainLooper())
        
        val task = object : Runnable {
            override fun run() {
                executor.execute {
                    try {
                        val quote = fetchRandomQuote()
                        
                        val emoji = fetchRandomEmoji(quote)

                        log("[WahBuddy] About Edited: \"$quote\" $emoji")

                        val dispatcher = XposedHelpers.newInstance(classLoader.loadClass("X.7gI"))
                        val depO2 = XposedHelpers.newInstance(classLoader.loadClass("X.8LM"), app, 31)
                        val depO1 = XposedHelpers.newInstance(classLoader.loadClass("X.8LC"), app, app, app, 1)

                        XposedHelpers.callMethod(dispatcher, "A05", quote, emoji, depO1, depO2, 86400L, false)
                    } catch (e: Exception) {
                        log("[WahBuddy] Loop Error: ${e.message}")
                    }
                }

                val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"))
                val currentSeconds = calendar.get(Calendar.SECOND)
                val initialDelay = (60 - currentSeconds) * 1000L

                log("[WahBuddy] Scheduling Auto About update after ${initialDelay / 1000} seconds")
                
                handler.postDelayed(task, initialDelay)
            }
        }

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"))
        val currentSeconds = calendar.get(Calendar.SECOND)
        val initialDelay = (60 - currentSeconds) * 1000L
        
        handler.postDelayed(task, initialDelay)
    }

    private fun fetchRandomQuote(): String {
        val url = URL("https://api.quotable.io/random?maxLength=50")
        val conn = url.openConnection() as HttpsURLConnection
        conn.connectTimeout = 5000
        val response = conn.inputStream.bufferedReader().readText()
        return JSONObject(response).getString("content")
    }

    private fun fetchRandomEmoji(text: String): String {
        return try {
            val words = text.replace(Regex("[^a-zA-Z ]"), "").split(" ")
            val keyword = words.maxByOrNull { it.length } ?: "smile"

            val url = URL("https://emojihub.yurace.pro/api/random")
            val conn = url.openConnection() as HttpURLConnection
            conn.connectTimeout = 5000
            
            val response = conn.inputStream.bufferedReader().readText()
            val json = JSONObject(response)
            
            val htmlCode = json.getJSONArray("htmlCode").getString(0)
            val unicodeString = htmlCode.replace("&#", "").replace(";", "")
            String(Character.toChars(unicodeString.toInt()))

        } catch (e: Exception) {
            "💬" 
        }
    }

    private fun bypassSSLValidation() {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })
            val sc = SSLContext.getInstance("SSL")
            sc.init(null, trustAllCerts, java.security.SecureRandom())
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.socketFactory)
            HttpsURLConnection.setDefaultHostnameVerifier { _, _ -> true }
        } catch (e: Exception) {}
    }
}
