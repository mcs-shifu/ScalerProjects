package com.scaler.fileio;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class WriteNumbersFile implements WriteFile{
    public void writeNumbersToFile (String outFileName, ArrayList<Integer> numbers) {
        try {
            FileWriter fw = new FileWriter(outFileName);

            for (int i = 0;  i < numbers.size();  ++i) {
                fw.write(numbers.get(i)+"\n"+"");
            }
            fw.close();
            System.out.println("\nWrote " + numbers.size() + " lines to " + outFileName);
        } catch (IOException e) {
            System.err.println("Exception in writing to output file: " + outFileName);
        }
    }
}
