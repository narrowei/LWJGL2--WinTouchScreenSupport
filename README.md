# LWJGL2 - WinTouchScreenSupport

# introduction
This project gives a solution about how to enable windows TouchScreen on one of the popular JAVA game frameworks: *LWJGL2*. 

The core idea is using JNI (java native interface) to register Touch windows for the application and process *WM_TOUCH* message. You can find codes on the folder "src/native/..."

if you need more information about Windows Touch, you can click the following link: https://docs.microsoft.com/en-us/windows/desktop/wintouch/about-the-multi-touch-sdk

Also, I have implemented some useful function to handle the touch point data, like print data info, manage outdated point data. You can customize and create functions in the file "src\java\org\lwjgl\opengl\WindowsTouch.java"





