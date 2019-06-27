package org.lwjgl.opengl;

import javafx.scene.input.TouchPoint;
import org.lwjgl.WindowsTouchImplementation;
import org.lwjgl.input.TOUCHINPUT;

import java.util.ArrayList;
import java.util.Arrays;

public class WindowsTouch {

    private final int TOUCHEVENTF_MOVE      =      1;
    private final int TOUCHEVENTF_DOWN      =      2;
    private final int TOUCHEVENTF_UP        =      3;
    private final int MAIN_FLAG_UP          =      20;
    private final int MAIN_FLAG_DOWN        =      26;
    private final int MAIN_FLAG_MOVE        =      25;
    private final int TOUCH_FLAG_UP         =      4;
    private final int TOUCH_FLAG_DOWN       =      10;
    private final int TOUCH_FLAG_MOVE       =      9;
    private final int MAX_POINTS             =      10;

    private ArrayList<TOUCHINPUT> touchinputs;
    private int[] idLookup;
    private final long hwnd;
    private boolean isCreated = false;

    public WindowsTouch(long hwnd) {
        isCreated = RegisterTouchScreen();
        System.out.println(isCreated);
        this.hwnd = hwnd;
        this.touchinputs = new ArrayList<TOUCHINPUT>();
        idLookup = new int[MAX_POINTS];
    }


    public ArrayList<TOUCHINPUT> getTouchinputs() {
        return this.touchinputs;
    }

    public boolean RegisterTouchScreen() {
       return WindowsTouchImplementation.RegisterTouchWindow();
    }

    public void HandleTouch(TOUCHINPUT[] inputs){
        for (int i = 0; i < inputs.length; i++) {
            removeOutdatedPoints();
            int index = getIndex(inputs[i].DWID);
            if (index != -1) {
                this.touchinputs.set(index, inputs[i]);
            } else {
                this.touchinputs.add(inputs[i]);
            }
        }
        printTouchInfo();
    }


    private void removeOutdatedPoints() {
        for (int i = 0; i < this.touchinputs.size(); i++) {
            TOUCHINPUT ti = this.touchinputs.get(i);
            if (ti.DWFLAGS == MAIN_FLAG_UP || ti.DWFLAGS == TOUCH_FLAG_UP) {
                this.touchinputs.remove(i);
            }
        }
    }

    private int getIndex(int id) {
        for (int i = 0; i < this.touchinputs.size(); i++) {
            TOUCHINPUT ti = this.touchinputs.get(i);
            if (ti.DWID == id) {
                return i;
            }
        }
        return -1;
    }




    public void printTouchInfo() {
        System.out.println("--------------------------------------------------");
        for (TOUCHINPUT touchinput : this.touchinputs) {
            switch (touchinput.DWFLAGS) {
                case 20:
                    System.out.println("main touch up");
                    break;
                case 26:
                    System.out.println("main touch down");
                    break;
                case 25:
                    System.out.println("main touch moving");
                    break;
                case 4:
                    System.out.println("touch up");
                    break;
                case 10:
                    System.out.println("touch down");
                    break;
                case 9:
                    System.out.println("moving");
                    break;
            }
            System.out.println("id: " + touchinput.DWID + " x: " + touchinput.x + " y: " + touchinput.y + " flag: "+touchinput.DWFLAGS);

        }
        System.out.println("--------------------------------------------------");
    }



}
