package com.scaler.fileio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadNumbersFile implements ReadFile{
    public ArrayList<Integer> getNumbersInFile (String fileName) {
        ArrayList<Integer> numbers = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNext()) {
                numbers.add(scanner.nextInt());
            }
        } catch (IOException exception) {
            System.err.print("Exception in reading input file: " + fileName);
        }
        return numbers;
    }
}
