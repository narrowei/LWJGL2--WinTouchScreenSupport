
	private WindowsTouch touch;

	
	public void createTouch(){
		touch = new WindowsTouch(getHwnd());
	}

	

	private long doHandleMessage(long hwnd, int msg, long wParam, long lParam, long millis) {


		...

		switch (msg) {

		...		
			case WM_TOUCH:
				TOUCHINPUT[] touchinputs = WindowsTouchImplementation.GetTouches(wParam, lParam);
				touch.HandleTouch(touchinputs);
				break;
			
		}

		return defWindowProc(hwnd, msg, wParam, lParam);
	}

