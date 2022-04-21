package com.vivi.tankwar;

import javax.swing.*;
import java.awt.*;

public class GameClient extends JComponent {
    public GameClient(){
        this.setPreferredSize(new Dimension(800, 600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("images/tankD.gif").getImage(),
                400, 100, null);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("the best tank war!");
        frame.setIconImage(new ImageIcon("images/tree.png").getImage());
        frame.add(new GameClient());

        GameClient client = new GameClient();
        client.repaint();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}
