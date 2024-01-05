package org.example;

public class TuringMachine {

    //HeadStates
    public static final String Q0 = "Q0",Q1 = "Q1", Q2 = "Q2", Q3 = "Q3", Q4 = "Q4";
    public static final int L = -1, R = 1;
    //Variables
    public static String headState = "Q0";
    public static char[] binaryString;
    public static int headPos = 0;
    public static ProgramFrame mainPanel;
    public static char moviment;

    public static void main(String[] args) {
        String response = JavaPane.askUser();
        System.out.println("Response :" + response);
        response = "BB" + response + "BBBB";
        binaryString = response.toCharArray();
        if(response.length() <= 20){
            mainPanel = new ProgramFrame();
            mainPanel.start();
        } else{
            while(true){
                step();
            }
        }
    }

    public static char[] getBinaryString(){
        return binaryString;
    }

    public static String getHeadState(){
        return headState;
    }

    public static char getMoviment(){
        return moviment;
    }

    public static void step(){
        char aux = getActualChar();
        switch(getHeadState()){
            case Q0 -> {
                switch(aux){
                    case '0':
                        headState = Q1; setActualChar('X');  moveHead(R);
                        break;
                    case 'Y':
                        headState = Q3; setActualChar('Y');  moveHead(R);
                        break;
                    case 'X', 'B', '1':
                    default : notDefinedMovement(); break;
                }
            }
            case Q1 -> {
                switch (aux){
                    case '0':
                        headState = Q1; setActualChar('0');  moveHead(R);
                        break;
                    case '1':
                        headState = Q2; setActualChar('Y');  moveHead(L);
                        break;
                    case 'Y':
                        headState = Q1; setActualChar('Y');  moveHead(R);
                        break;
                    case 'X', 'B':
                    default:
                        notDefinedMovement(); break;

                }
            }
            case Q2 -> {
                switch (aux){
                    case '0':
                        headState = Q2; setActualChar('0');  moveHead(L);
                        break;
                    case 'X':
                        headState = Q0; setActualChar('X');  moveHead(R);
                        break;
                    case 'Y':
                        headState = Q2; setActualChar('Y');  moveHead(L);
                        break;
                    case '1', 'B':
                    default:
                        notDefinedMovement(); break;
                }

            }
            case Q3 -> {
                switch (aux){
                    case 'Y':
                        headState = Q3; setActualChar('Y');  moveHead(R);
                        break;
                    case 'B':
                        headState = Q4; setActualChar('B');  moveHead(R);
                        break;
                    case '0', '1', 'X':
                    default:
                        notDefinedMovement(); break;
                }
            }
            case Q4 -> EndOfProgram();
        }
    }
    private static void moveHead(int movement){
        headPos+=movement;
        if(movement == L) moviment = 'L';
        else if(movement == R) moviment = 'R';
    }

    private static char getActualChar(){
        return binaryString[headPos+2];
    }
    private static void setActualChar(char symbol){
        binaryString[headPos+2] = symbol;
    }

    public static void EndOfProgram(){
        System.out.println("Success!");
        System.exit(1);
    }

    public static void notDefinedMovement(){
        System.out.println("Not defined!");
        System.exit(-1);
    }

}