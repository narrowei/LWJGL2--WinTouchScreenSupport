package org.lwjgl;

import org.lwjgl.input.TOUCHINPUT;
import org.lwjgl.opengl.Display;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

public class WindowsTouchImplementation {

    static {
        //System.loadLibrary("native");
    }

    public static void main(String[] args) {

        boolean res = nRegisterTouchWindow(getHwnd());

    }

    private static long getHwnd() {
        if (!Display.isCreated())
            return 0;
        /* Use reflection since we can't make Display.getImplementation
         * public
         */
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Long>() {
                public Long run() throws Exception {
                    Method getImplementation_method = Display.class.getDeclaredMethod("getImplementation");
                    getImplementation_method.setAccessible(true);
                    Object display_impl = getImplementation_method.invoke(null);
                    Class<?> WindowsDisplay_class = Class.forName("org.lwjgl.opengl.WindowsDisplay");
                    Method getHwnd_method = WindowsDisplay_class.getDeclaredMethod("getHwnd");
                    getHwnd_method.setAccessible(true);
                    return (Long)getHwnd_method.invoke(display_impl);
                }
            });
        } catch (PrivilegedActionException e) {
            throw new Error(e);
        }
    }

    public static boolean RegisterTouchWindow(){
        return nRegisterTouchWindow(getHwnd());
    }

    private static native boolean nRegisterTouchWindow(long parent_hwnd);

    private static native TOUCHINPUT[] nGetTouches(long wParam, long lParam, long parent_hwnd);

    public static TOUCHINPUT[] GetTouches(long wParam, long lParam){
        return nGetTouches(wParam, lParam, getHwnd());
    }

}
