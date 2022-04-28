package com.vivi.tankwar;




import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Tank {
    private int x;
    private int y;
    private boolean enemy;


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
            case UP: return Tools.getImage(prefix + "tankU.gif" );
            case DOWN: return Tools.getImage(prefix + "tankD.gif");
            case LEFT: return Tools.getImage(prefix +  "tankL.gif");
            case RIGHT: return Tools.getImage(prefix +  "tankR.gif");
            case UPLEFT: return Tools.getImage(prefix + "tankLU.gif");
            case UPRIGHT: return Tools.getImage(prefix + "tankRU.gif");
            case DOWNLEFT: return Tools.getImage(prefix +  "tankLD.gif");
            case DOWNRIGHT: return Tools.getImage(prefix + "tankRD.gif");
        }
        return null;
    }

    void draw(Graphics g){
        int oldX = x, oldY = y;
        this.determineDirection();
        this.move();
        if(x < 0) x = 0;
        else if(x > 800 - getImage().getWidth(null)) x = 800 - getImage().getWidth(null);
        if(y < 0) y = 0;
        else if (y > 600 - getImage().getHeight(null)) y = 600 - getImage().getHeight(null);

        Rectangle rec = this.getRectangle();
        for (Wall wall : GameClient.getInstance().getWalls()){
            if(rec.intersects(wall.getRectangle())) {
                x = oldX;
                y = oldY;
                break;
            }
        }
        for(Tank tank : GameClient.getInstance().getEnemyTanks()){
            if(rec.intersects(tank.getRectangle())){
                x = oldX;
                y = oldY;
                break;
            }
        }

        g.drawImage(this.getImage(),
                this.x, this.y, null);

    }
    public Rectangle getRectangle(){
        return new Rectangle(x, y, getImage().getWidth(null), getImage().getHeight(null));
    }

    private boolean up, down, left, right;


    public void keyPressed(KeyEvent e){
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP: up = true; break;
            case KeyEvent.VK_DOWN: down = true; break;
            case KeyEvent.VK_LEFT: left = true; break;
            case KeyEvent.VK_RIGHT: right = true; break;
            case KeyEvent.VK_F: fire(); break;
        }
    }

    private void fire() {
        Missile missile = new Missile(x + getImage().getWidth(null)/2 - 6,
                y + getImage().getHeight(null)/2 - 6, enemy, direction);
        GameClient.getInstance().getMissiles().add(missile);
        try {
            FileInputStream fileau = new FileInputStream("audios/shoot.wav");
            AudioStream as = new AudioStream(fileau);
            AudioPlayer.player.start(as);
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Media sound  = new Media(new File("audios/shoot.wav").toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
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
