package org.example;

public class Main {

    //Variables
    public static String string, headState = "Q0";
    public static int headPos = 0;
    public static ProgramFrame mainPanel;

    public static void main(String[] args) {
        string = JavaPane.askUser();
        string = "  " + string + "  ";
        System.out.println("Response :" + string);
        if(string.length() <= 20){
            mainPanel = new ProgramFrame();
            mainPanel.start();
        } else{
            System.out.println("String max");
        }
    }

    public static char[] getBinaryString(){
        return string.toCharArray();
    }

    public static String getHeadState(){
        return headState;
    }

    public static void step(){

    }

}