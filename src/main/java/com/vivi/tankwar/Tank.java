package com.vivi.tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {
    private int x;
    private int y;
    private boolean enemy;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    private Direction direction;

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Tank(int x, int y, Direction direction) {
        this(x, y, false, direction);
    }

    public Tank(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    void move(){
        if(this.stopped) return;
        switch (direction) {
            case UP:
                y-=5;
                break;
            case UPLEFT:
                x -= 5;
                y -= 5;
                break;
            case UPRIGHT:
                x += 5;
                y -= 5;
                break;
            case DOWNLEFT:
                x -= 5;
                y += 5;
                break;
            case DOWNRIGHT:
                x += 5;
                y += 5;
                break;
            case DOWN: y+=5;break;
            case LEFT: x-=5;break;
            case RIGHT: x+=5;break;
        }
    }

    Image getImage(){
        String prefix = enemy ? "e" : "";
        switch(direction){
            case UP: return new ImageIcon("images/"+ prefix + "tankU.gif").getImage();
            case DOWN: return new ImageIcon("images/"+ prefix + "tankD.gif").getImage();
            case LEFT: return new ImageIcon("images/"+ prefix + "tankL.gif").getImage();
            case RIGHT: return new ImageIcon("images/"+ prefix + "tankR.gif").getImage();
            case UPLEFT: return new ImageIcon("images/"+ prefix + "tankLU.gif").getImage();
            case UPRIGHT: return new ImageIcon("images/"+ prefix + "tankRU.gif").getImage();
            case DOWNLEFT: return new ImageIcon("images/"+ prefix + "tankLD.gif").getImage();
            case DOWNRIGHT: return new ImageIcon("images/"+ prefix + "tankRD.gif").getImage();
        }
        return null;
    }

    void draw(Graphics g){
        this.determineDirection();
        this.move();
        g.drawImage(this.getImage(),
                this.x, this.y, null);

    }
    private boolean up, down, left, right;

    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP: up = true; break;
            case KeyEvent.VK_DOWN: down = true; break;
            case KeyEvent.VK_LEFT: left = true; break;
            case KeyEvent.VK_RIGHT: right = true; break;
        }
    }

    private boolean stopped;

    private void determineDirection() {
        if (!up && !left && !down && !right) {
            this.stopped = true;
        } else {
            if (up && left && !down && !right) this.direction = Direction.UPLEFT;
            else if (up && right && !down && !left) this.direction = Direction.UPRIGHT;
            else if (down && left && !up && !right) this.direction = Direction.DOWNLEFT;
            else if (down && right && !up && !left) this.direction = Direction.DOWNRIGHT;
            else if (up && !right && !down && !left) this.direction = Direction.UP;
            else if (!up && !right && down && !left) this.direction = Direction.DOWN;
            else if (!up && right && !down && !left) this.direction = Direction.RIGHT;
            else if (!up && !right && !down && left) this.direction = Direction.LEFT;
            this.stopped = false;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }



}
