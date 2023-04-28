package com.scaler.merge;

import com.scaler.fileio.ReadNumbersFile;
import java.util.ArrayList;

// The interwebs kindly informs me that using Runnable is the most naïve way
// to implement threads. At this early stage of my Java learning, I'm happy to
// stick to something naïve as long as it works. Go bully someone else, internet :)
public class ProcessFile implements Runnable{
    private final ReadNumbersFile rnf;
    private final String fileName;
    private final ArrayList<Integer> result;
    ProcessFile (ReadNumbersFile rnf, String fileName, ArrayList<Integer> result) {         this.rnf = rnf;
        this.fileName = fileName;
        this.result = result;
    }

    @Override public void run() {
        ArrayList<Integer> numbers = rnf.getNumbersInFile(fileName);
        System.out.println("Read file " + fileName + " (" + numbers.size() + " lines) in a new thread");
        result.addAll(numbers);
    }
}
