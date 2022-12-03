package com.mygdx.game;

public class Nodes {
    private int x, y;

    public Nodes() {
        setX(0);
        setY(0);
    }

    public Nodes(int x, int y) {
        setX(x);
        setY(y);
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
}
