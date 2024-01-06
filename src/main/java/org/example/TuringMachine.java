package org.example;

public class TuringMachine {

    //HeadStates
    public static final String Q0 = "Q0",Q1 = "Q1", Q2 = "Q2", Q3 = "Q3", Q4 = "Q4";
    public static final int L = -1, R = 1;
    static final char appendSymbol = '├';
    //Variables
    public static String headState = "Q0", string;
    public static char[] binaryString;
    public static int headPos = 0;
    public static ProgramFrame mainPanel;
    public static char movement;
    public static SendToFileManager buffer;

    public static void main(String[] args) {
        string = JavaPane.askUser();
        String response = string;
        buffer = new SendToFileManager("output");
        buffer.appendTextToFile("Cadena: "+string);
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

    public static char getMovement(){
        return movement;
    }

    static char aux;
    public static void step(){
        aux = getActualChar();
        takeSnapshot(headState,headPos);

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
        if(movement == L) TuringMachine.movement = 'L';
        else if(movement == R) TuringMachine.movement = 'R';
    }

    private static char getActualChar(){
        return binaryString[headPos+2];
    }
    private static void setActualChar(char symbol){
        binaryString[headPos+2] = symbol;
    }


    static StringBuilder stringAux = new StringBuilder();

    public static void takeFinalSnapshot(String state, int pos){
        appendTextToString(state, pos);

        stringAux.append(".");
        buffer.appendTextToFile(stringAux.toString());
        buffer.closeBinaryFile();
    }

    private static void appendTextToString(String state, int pos) {
        stringAux.delete(0,stringAux.length());
        stringAux.append('\n');
        for(int i = 2; i < pos+2; i++)
            stringAux.append(binaryString[i]);
        stringAux.append('[').append(state).append(']');
        for(int i = pos+2; i < binaryString.length-4; i++)
            stringAux.append(binaryString[i]);
    }

    public static void takeSnapshot(String state,int pos){
        appendTextToString(state, pos);

        stringAux.append(" ").append(appendSymbol);
        buffer.appendTextToFile(stringAux.toString());
    }

    public static void EndOfProgram(){
        System.out.println("Success!");
        if(mainPanel != null)
            mainPanel.stop();
        JavaPane.showFinalDialog("La cadena\n" + string + "\npertenece al lenguaje.");
        takeFinalSnapshot(headState,headPos);
        System.exit(1);
    }

    public static void notDefinedMovement(){
        System.out.println("Not defined!");
        if(mainPanel != null)
            mainPanel.stop();
        takeFinalSnapshot(headState,headPos);
        JavaPane.showFinalDialog("La cadena\n" + string + "\nno pertenece al lenguaje.");
        System.exit(-1);
    }

}
