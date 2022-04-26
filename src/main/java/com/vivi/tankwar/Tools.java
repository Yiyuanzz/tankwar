package com.vivi.tankwar;

import javax.swing.*;
import java.awt.*;

public class Tools {
    public static Image getImage(String imageName){
        return new ImageIcon("images/" + imageName).getImage();
    }
}
