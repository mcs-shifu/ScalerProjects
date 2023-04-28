package com.scaler.merge;

import com.scaler.fileio.ReadNumbersFile;
import com.scaler.fileio.WriteNumbersFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MergeFilesWithSingleSort implements MergeFiles{
    private static final int MaxFileReadThreads = 10;

    public void mergeFiles (String outFile, ArrayList<String> inFiles) {
        int numFiles = inFiles.size();
        ArrayList<Integer> result = new ArrayList<>();
        ReadNumbersFile rnf = new ReadNumbersFile();

        ExecutorService executorService = Executors.newFixedThreadPool(Math.max(MaxFileReadThreads, numFiles));

        for (String infile : inFiles) {
            Runnable reader = new ProcessFile(rnf, infile, result);
            executorService.execute(reader);
        }
        executorService.shutdown();
        while (!executorService.isTerminated()) {
            // deliberate empty body here to wait for all files to be read
        }

        // Not bothering to do a merge sort.
        // Instead, simply reading each file in a new thread, collecting all the
        // numbers in an ArrayList and then sorting the final ArrayList before
        // writing it into the output file.
        // At some point, when the number of files and/or total volume
        // of numbers to be written is obscenely high, this will probably fail.
        //
        // But hey, this is java-basics/assignment-01
        // This will do, for now.

        Collections.sort(result);

        WriteNumbersFile wnf = new WriteNumbersFile();
        wnf.writeNumbersToFile(outFile, result);
    }
}