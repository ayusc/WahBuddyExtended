> [!NOTE]
> Whew, it was a hard time reverse engineering Whatsapp (the ProGuard curse hit well) 
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
