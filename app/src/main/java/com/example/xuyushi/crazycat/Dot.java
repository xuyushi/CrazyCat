package com.example.xuyushi.crazycat;

/**
 * Created by xuyushi on 15/2/21.
 */
public class Dot {
    public int x,y;
    public int status;

    public static final int STATUS_ON = 0;
    public static final int STATUS_OFF = 1;
    public static final int STATUS_IN = 9;

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
        this.status = STATUS_OFF;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setXY(int x,int y) {
        this.x = x;
        this.y = y;
    }
}
