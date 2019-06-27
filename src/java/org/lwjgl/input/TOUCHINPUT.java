package org.lwjgl.input;

public class TOUCHINPUT {
    public int       x;
    public int       y;
    public int       DWID;
    public int       DWFLAGS;

    public TOUCHINPUT(int x, int y, int DWID, int DWFLAGS) {
        this.x = x;
        this.y = y;
        this.DWID = DWID;
        this.DWFLAGS = DWFLAGS;
    }
}
