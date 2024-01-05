package org.example;

import java.util.*;

public class MiscUtils {

    public String generateBinary() {
        return this.generateBinary(16,2);
    }

    public String generateBinary(int bound) {
        return this.generateBinary(bound,2);
    }

    public String generateBinary(int bound, int interval) {
        StringBuilder binary = new StringBuilder();
        int n = new Random().nextInt(bound);
        binary.append("0".repeat(n));
        //it adds or removes in an interval of [-2,2]
        n += new Random().nextInt(interval*2)-interval;
        if(n < 0) n*=-1;
        binary.append("1".repeat(n));
        return binary.toString();
    }
}
