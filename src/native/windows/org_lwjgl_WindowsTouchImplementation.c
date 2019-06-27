#include "Window.h"
#include <jni.h>
#include "org_lwjgl_WindowsTouchImplementation.h"

/*
 * Conversion of touch input coordinates to pixels
 */
#define TOUCH_COORD_TO_PIXEL(l)         ((l) / 100)

DECLARE_HANDLE(HTOUCHINPUT);



typedef struct tagTOUCHINPUT {
    LONG x;
    LONG y;
    HANDLE hSource;
    DWORD dwID;
    DWORD dwFlags;
    DWORD dwMask;
    DWORD dwTime;
    ULONG_PTR dwExtraInfo;
    DWORD cxContact;
    DWORD cyContact;
} TOUCHINPUT, *PTOUCHINPUT;
typedef TOUCHINPUT const * PCTOUCHINPUT;

JNIEXPORT jboolean JNICALL Java_org_lwjgl_WindowsTouchImplementation_nRegisterTouchWindow
  (JNIEnv * env, jclass unused, jlong hwnd_ptr){
        HWND hwnd = (HWND)(INT_PTR)hwnd_ptr;
        return RegisterTouchWindow(hwnd, 0);
  }

JNIEXPORT jobjectArray JNICALL Java_org_lwjgl_WindowsTouchImplementation_nGetTouches
   (JNIEnv * env, jclass unused, jlong wParam, jlong lParam , jlong hwnd_ptr){
        UINT cInputs = LOWORD(wParam);
        LPMSG pStruct = (LPMSG)lParam;
        HWND hwnd = (HWND)(INT_PTR)hwnd_ptr;
        PTOUCHINPUT pInputs = malloc(sizeof(TOUCHINPUT) * cInputs);
        jclass touchClass = (*env)->FindClass(env, "org/lwjgl/input/TOUCHINPUT");
        jmethodID touchInputConstructor = (*env)->GetMethodID(env, touchClass, "<init>", "(IIII)V");
        jobjectArray ret = (*env)->NewObjectArray(env, cInputs, touchClass, NULL);
        if (NULL == touchInputConstructor) {
            printfDebugJava(env, "Could not get TOUCHINPUT init");
            return NULL;
        }
        // You will use this array to track touch points

        if (pInputs) {
        	if (GetTouchInputInfo((HTOUCHINPUT)lParam, cInputs, pInputs, sizeof(TOUCHINPUT))) {
        	    for (UINT i = 0; i < cInputs; i++) {
                   				TOUCHINPUT ti = pInputs[i];
                   				POINT ptInput = {.x = -1, .y = -1};
                   				ptInput.x = TOUCH_COORD_TO_PIXEL(ti.x);
                                ptInput.y = TOUCH_COORD_TO_PIXEL(ti.y);
                                ScreenToClient(hwnd, &ptInput);
                   				long x = ptInput.x;
                   				long y = ptInput.y;
                   				DWORD id = ti.dwID;
                   				DWORD flag = ti.dwFlags;
                   				jobject touch = (*env)->NewObject(env, touchClass, touchInputConstructor, x, y, id, flag);
                                (*env)->SetObjectArrayElement(env, ret, i, touch);
                   			}
        		return ret;
        	}else{
        	    return NULL;
        	}
        }
        else {
        	return NULL;
        }
   }
