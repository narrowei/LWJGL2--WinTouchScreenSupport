
	private static void initControls() {
		// Automatically create mouse, keyboard and controller
		if ( !getPrivilegedBoolean("org.lwjgl.opengl.Display.noinput") ) {
			if ( !Mouse.isCreated() && !getPrivilegedBoolean("org.lwjgl.opengl.Display.nomouse") ) {
				try {
					Mouse.create();
					if (LWJGLUtil.getPlatform() == LWJGLUtil.PLATFORM_WINDOWS) {
						Touch.create();
					}
				} catch (LWJGLException e) {
					if ( LWJGLUtil.DEBUG ) {
						e.printStackTrace(System.err);
					} else {
						LWJGLUtil.log("Failed to create Mouse: " + e);
					}
				}
			}
			if ( !Keyboard.isCreated() && !getPrivilegedBoolean("org.lwjgl.opengl.Display.nokeyboard") ) {
				try {
					Keyboard.create();
				} catch (LWJGLException e) {
					if ( LWJGLUtil.DEBUG ) {
						e.printStackTrace(System.err);
					} else {
						LWJGLUtil.log("Failed to create Keyboard: " + e);
					}
				}
			}
		}
	}
