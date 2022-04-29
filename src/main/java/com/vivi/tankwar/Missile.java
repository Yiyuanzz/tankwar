package com.vivi.tankwar;

import java.awt.*;

public class Missile {
    private int x;
    private int y;
    private final boolean enemy;
    private final Direction direction;
    public static final int speed = 10;

    public Missile(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    Image getImage(){
        return direction.getImage("missile");
    }
    void move()
    {
        switch (direction) {
            case UP:
                y -= speed;
                break;
            case LEFT_UP:
                x -= speed;
                y -= speed;
                break;
            case RIGHT_UP:
                x += speed;
                y -= speed;
                break;
            case LEFT_DOWN:
                x -= speed;
                y += speed;
                break;
            case RIGHT_DOWN:
                x += speed;
                y += speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
        }
    }

    public void draw(Graphics g) {
        move();
        if(x < 0 || x > 800 || y < 0 || y > 600){
            return;
        }

        g.drawImage(getImage(), x, y, null);
    }
}
