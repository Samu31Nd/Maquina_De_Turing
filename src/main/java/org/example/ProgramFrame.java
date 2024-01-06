package org.example;

import javax.swing.*;
import java.awt.*;

public class ProgramFrame extends JFrame {
    ProgramPanel panel;
    Boolean darkTheme = false;

    public static Color Color_base,
        Color_overlay2,
        Color_overlay1,
        Color_crust,
        Color_black,
        Color_surface0;

    public ProgramFrame(){
        this.setVisible(false);
        this.setBackground(Color_base);

        if(darkTheme) setDarkTheme();
        else setLightTheme();
    }

    public void start(){
        panel = new ProgramPanel();
        this.add(panel);
        setBasicParameters();
    }

    public void stop(){
        panel.areWeMovingNow = false;
    }

    public void setDarkTheme(){
        Color_base = new Color(36, 39, 58);
        Color_overlay2 = new Color(147, 154, 183);
        Color_overlay1 = new Color(165, 173, 203);
        Color_crust = new Color(24, 25, 38);
        Color_black = new Color(202, 211, 245);
        Color_surface0 = new Color(54, 58, 79);
    }
    public void setLightTheme(){
        Color_base = new Color(239, 241, 245);
        Color_overlay2 = new Color(124, 127, 147);
        Color_overlay1 = new Color(140, 143, 161);
        Color_crust = new Color(220, 224, 232);
        Color_black = new Color(76, 79, 105);
        Color_surface0 = new Color(204, 208, 218);
    }

    private void setBasicParameters(){
        //this.setIconImage(new ImageIcon("src/main/resources/logo2.png").getImage());
        this.setTitle("Turing machine [0^n1^n]");
        this.setVisible(true);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
