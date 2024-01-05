package org.example;

import javax.swing.*;

public class JavaPane {
    static String binaryNumber;
    static int option;
    static void ShowNotDefinedDialog(){
        JOptionPane.showMessageDialog(null,
                "This string is not defined by the language.");
    }

    static void showFinalDialog(String message){
        JOptionPane.showMessageDialog(null,
                message);
    }

    static String askUser() {

        option = JOptionPane.showOptionDialog(null,
                "Insert a mode",
                "PDA",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Random String", "Write a String"},
                null
        );

        switch (option) {
            case -1 -> System.exit(-1);
            case 0 -> binaryNumber = new MiscUtils().generateBinary(WriteBound());
            case 1 -> WriteNumber();
        }
        return binaryNumber;
    }

    static void WriteNumber(){
        binaryNumber = JOptionPane.showInputDialog(
                "Write the string 0n1n",
                "0011"
        );
        if(binaryNumber == null)
            System.exit(-1);
    }
    static int WriteBound(){
        int bound = Integer.parseInt(JOptionPane.showInputDialog(
                "Write bound",
                "16"
        ));
        if(bound == 0)
            System.exit(-1);
        return bound;
    }
}
