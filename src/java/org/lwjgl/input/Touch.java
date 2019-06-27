package org.lwjgl.input;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.InputImplementation;

public class Touch {

    private static InputImplementation implementation;
    private static boolean		created;

    static InputImplementation getImplementation() {
        return implementation;
    }

    public static void create() throws LWJGLException {
        synchronized (OpenGLPackageAccess.global_lock) {
            if (!Display.isCreated()) throw new IllegalStateException("Display must be created.");
            create(OpenGLPackageAccess.createImplementation());
        }
    }

    private static void create(InputImplementation impl) throws LWJGLException {
        if (created)
            return;
        implementation = impl;
        implementation.createTouch();
        created = true;
    }

}
