package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProgramPanel extends JPanel implements ActionListener {
    char []binaryString;
    String headState;

    //variables de dibujo

    static final int velocity = 3;
    static Font font = new Font("VCR OSD Mono", Font.BOLD,28); FontMetrics metrics;
    static final int WIDTH = 1400, HEIGHT = 500; Graphics2D canvas;
    static final Dimension PANEL_SIZE = new Dimension(WIDTH, HEIGHT);
    Timer timer;
    int superXmini_cubes = -34; int _TapeHeight = 400;

    //head parameters
    public int startPos, endPos, distanceMoved = 0;
    public static int headPos = 1, direction; int headXPos = 0;

    //boolean values
    public static boolean areWeMovingNow = true, isSetDirection = false;

    ProgramPanel() {

        this.setFocusable(true);
        this.setPreferredSize(PANEL_SIZE);

        timer = new Timer(10,this);
        timer.start();
        binaryString = TuringMachine.getBinaryString();
        headState = TuringMachine.getHeadState();
        setDirectionRight();
    }

    public void paint(Graphics g) {
        super.paint(g);
        canvas = (Graphics2D) g;
        canvas.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        drawBackground( );
        drawTapeElements();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.repaint();
    }

    void drawBackground( ){
        canvas.setColor(ProgramFrame.Color_base);
        canvas.fillRect(0,0,WIDTH, HEIGHT);
        drawElementsBackground();
    }

    int rectangleHeight = 100, miniRectWidth = rectangleHeight/9, miniRectHeight = 2*miniRectWidth/3,
            miniRectSpacing = 10,
            boxNumberWidth  = miniRectWidth*2 + 25,
            boxNumberHeight = rectangleHeight - (miniRectHeight + miniRectSpacing*2)*2;

    void drawElementsBackground(){
        canvas.setStroke(new BasicStroke(10.0f));


        //shadow foreground
        canvas.setColor(ProgramFrame.Color_surface0);
        canvas.drawLine(0,(_TapeHeight + rectangleHeight)/2 + 8,WIDTH,(_TapeHeight + rectangleHeight)/2 + 8);

        canvas.setStroke(new BasicStroke(5.0f));
        canvas.setColor(ProgramFrame.Color_overlay2);
        canvas.fillRect(0,(_TapeHeight - rectangleHeight)/2,WIDTH,rectangleHeight);
        canvas.setColor(ProgramFrame.Color_black);
        canvas.drawLine(0,(_TapeHeight - rectangleHeight)/2,WIDTH,(_TapeHeight - rectangleHeight)/2);
        canvas.drawLine(0,(_TapeHeight + rectangleHeight)/2,WIDTH,(_TapeHeight + rectangleHeight)/2);

        //drawing mini-cubes
        int mini_xSpacing = 20;
        for(int i = 0; i < 80; i++){
            canvas.fillRect(superXmini_cubes + (mini_xSpacing*i),(_TapeHeight - rectangleHeight)/2 + miniRectSpacing,miniRectWidth,miniRectHeight);
            canvas.fillRect(superXmini_cubes + (mini_xSpacing*i),(_TapeHeight + rectangleHeight)/2 - miniRectSpacing - miniRectHeight,miniRectWidth,miniRectHeight);
        }

    }
    void drawTapeElements(){
        //space for number
        int _xSpacing = 65;
        canvas.setFont(font);  metrics = canvas.getFontMetrics(font);
        for(int i = 0; i < 22; i++) {
            canvas.setColor(ProgramFrame.Color_base);
            canvas.fillRect(12 + (_xSpacing * i), (_TapeHeight - rectangleHeight) / 2 + miniRectSpacing * 2 + miniRectHeight, boxNumberWidth, boxNumberHeight);
        }

        for(int i = 0; i < binaryString.length; i++) {
            canvas.setColor(ProgramFrame.Color_black);
            drawString(12 + boxNumberWidth / 3 + (_xSpacing * (i)), (_TapeHeight - rectangleHeight) / 2 + miniRectSpacing * 2 + miniRectHeight * 2 + metrics.getHeight(), i);
        }
        for(int i = 0; i < 22; i++) {
            canvas.drawRect(12 + (_xSpacing*i),(_TapeHeight - rectangleHeight)/2 + miniRectSpacing*2 + miniRectHeight,boxNumberWidth,boxNumberHeight);
        }
        if(areWeMovingNow) {
            if (!isSetDirection) {
                distanceMoved = 0;
                headXPos = (_xSpacing * headPos) - boxNumberWidth / 2;
                startPos = headXPos;
                endPos = (_xSpacing * (headPos+direction)) - boxNumberWidth / 2;
                isSetDirection = true;
            }
            checkArrival();
        }
        drawHead(boxNumberWidth,headXPos + distanceMoved, (_TapeHeight + 3*rectangleHeight)/2 - 40);
    }

    void drawString(int x, int y, int pos){
        canvas.drawString(Character.toString(binaryString[pos]),x,y);
    }

    void drawHead(int size,int xPos, int yPos){
        size *= 5; size/= 2;
        int []xPoints = new int[]{xPos, xPos + size/2,xPos + size,xPos + size, xPos};
        int []yPoints = new int[]{yPos, (yPos - 2*size/3), yPos, yPos + size, yPos + size};
        Polygon head = new Polygon(xPoints,yPoints,5);

        //base
        canvas.setColor(ProgramFrame.Color_crust);
        canvas.fill(head);

        //String
        canvas.setColor(ProgramFrame.Color_black);
        canvas.drawString(headState,xPos + size/2 - metrics.stringWidth(headState)/2,yPos + size/2 + metrics.getHeight()/2);

        //outer line
        canvas.setStroke(new BasicStroke(4.0f));
        canvas.setColor(ProgramFrame.Color_black);
        canvas.draw(head);
        //arrow
        if(direction == -1){
            canvas.drawString("<-",xPos + size/2 - metrics.stringWidth("<-")/2,yPos + size + metrics.getHeight()*2);
        } else if( direction == 1){
            canvas.drawString("->",xPos + size/2 - metrics.stringWidth("->")/2,yPos + size + metrics.getHeight()*2);

        }
    }

        Boolean check;
    void checkArrival(){
        //nos estamos moviendo a la derecha
        if(direction>0) check = startPos + distanceMoved > endPos;
        else check = startPos + distanceMoved < endPos;
        //if(startPos + distanceMoved == endPos && startPos != 0){
        if(check && startPos != 0){
            headPos += direction;
            startPos = headXPos = endPos;
            distanceMoved = 0;
            checkMachineChanges();
            areWeMovingNow = true;
            isSetDirection = false;
        } else distanceMoved+=direction*velocity;
    }

    //debemos hacer que la maquina cambie paso por paso
    void checkMachineChanges() {
        TuringMachine.step();
        binaryString = TuringMachine.getBinaryString();
        headState = TuringMachine.getHeadState();
        if (TuringMachine.getMoviment() == 'L')
            setDirectionLeft();
        else setDirectionRight();
    }
    void setDirectionLeft(){ direction = -1; } void setDirectionRight(){ direction = 1; }
}
