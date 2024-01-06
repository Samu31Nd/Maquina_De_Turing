package org.example;

import java.io.FileWriter;
import java.io.IOException;

public class SendToFileManager {
    public FileWriter streamFile;
    public SendToFileManager(String fileName){

        try { streamFile = new FileWriter(fileName.concat(".txt"),false);
        } catch (IOException e) { throw new RuntimeException(e); }
    }

    public void appendTextToFile(String text){
        try {
            streamFile.write(text);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void closeBinaryFile(){
        try{ streamFile.close(); }
        catch (IOException err){ System.out.println(err.getMessage());  }
    }
}
