package com.vivi.tankwar;




import sun.audio.AudioPlayer;
import sun.audio.AudioStream;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

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
            case LEFT_UP:
                x -= 5;
                y -= 5;
                break;
            case RIGHT_UP:
                x += 5;
                y -= 5;
                break;
            case LEFT_DOWN:
                x -= 5;
                y += 5;
                break;
            case RIGHT_DOWN:
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
        return direction.getImage(prefix + "tank");
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
            case KeyEvent.VK_A: superFire();break;
        }
    }

    private void fire() {
        Missile missile = new Missile(x + getImage().getWidth(null)/2 - 6,
                y + getImage().getHeight(null)/2 - 6, enemy, direction);
        GameClient.getInstance().getMissiles().add(missile);
        playAudio("audios/shoot.wav");

//        Media sound  = new Media(new File("audios/shoot.wav").toURI().toString());
//        MediaPlayer mediaPlayer = new MediaPlayer(sound);
//        mediaPlayer.play();
    }

    private void playAudio(String fileName) {
        try {
            FileInputStream fileau = new FileInputStream(fileName);
            AudioStream as = new AudioStream(fileau);
            AudioPlayer.player.start(as);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void superFire(){
        for(Direction direction : Direction.values()){
            Missile missile = new Missile(x + getImage().getWidth(null)/2 - 6,
                    y + getImage().getHeight(null)/2 - 6, enemy, direction);
            GameClient.getInstance().getMissiles().add(missile);
        }
        String audioFile = new Random().nextBoolean() ? "supershoot.aiff" : "supershoot.wav";
        playAudio("audios/" + audioFile);
    }

    private boolean stopped;

    private void determineDirection() {
        if (!up && !left && !down && !right) {
            this.stopped = true;
        } else {
            if (up && left && !down && !right) this.direction = Direction.LEFT_UP;
            else if (up && right && !down && !left) this.direction = Direction.RIGHT_UP;
            else if (down && left && !up && !right) this.direction = Direction.LEFT_DOWN;
            else if (down && right && !up && !left) this.direction = Direction.RIGHT_DOWN;
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
