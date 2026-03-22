> [!NOTE]
> Whew, it was a hard time reverse engineering Whatsapp (all thanks to ProGuard) 
> <br>If you like it please give it a star 🌟
> <br><br>Only works on version 2.26.10.72 (Latest as of now)

# WahBuddy Extended
An Lsposed module to automatically update the user's About status in Whatsapp
<br>This is made as a extension for my whatsapp userbot since whatsapp have removed the ability to change the user's About in their web version (see [this](https://github.com/ayusc/WahBuddy/blob/main/modules/autobio.js)) and they also made some big changes to the About section.

This is made just for fun and pranking with my friends, although I might be stupid making something like this.

> [!IMPORTANT]
> You must use [Zygisk-Detach](https://github.com/j-hc/zygisk-detach) along with this lsposed module for best results
> <br>Don't forget to select "com.whatsapp" in the WebUI

## What it does?
• This lsposed module changes the users About (on Whatsapp's new About Screen) both locally and server-side.
<br>• It creates a random Quote (under 50 Chars) and assigns a random emoji and hits Whatsapp's internal helpers to update the new About every 60 secs.
<br>• It automatically aligns to exact times like 10:00, 10:01, 10:03 etc... and changes the About after every 60secs (first sync is done according to IST)

## Success Logs should look like this: 

```
[ 2026-03-22T20:26:51.671    10302: 18346: 18346 I/LSPosedFramework ] [com.wahbuddyext,XposedBridge] [WahBuddy] Hooked to WhatsApp Sucessfully !
[ 2026-03-22T20:26:51.672    10302: 18346: 18346 I/LSPosedFramework ] [com.wahbuddyext,XposedBridge] [WahBuddy] Starting auto update in 9 seconds...
[ 2026-03-22T20:27:04.323    10302: 18346: 18510 I/LSPosedFramework ] [com.wahbuddyext,XposedBridge] [WahBuddy] About Edited.
[ 2026-03-22T20:28:03.144    10302: 18346: 18510 I/LSPosedFramework ] [com.wahbuddyext,XposedBridge] [WahBuddy] About Edited.
[ 2026-03-22T20:29:03.990    10302: 18346: 18510 I/LSPosedFramework ] [com.wahbuddyext,XposedBridge] [WahBuddy] About Edited.
[ 2026-03-22T20:30:03.188    10302: 18346: 18510 I/LSPosedFramework ] [com.wahbuddyext,XposedBridge] [WahBuddy] About Edited.
[ 2026-03-22T20:31:03.171    10302: 18346: 18510 I/LSPosedFramework ] [com.wahbuddyext,XposedBridge] [WahBuddy] About Edited.
```
