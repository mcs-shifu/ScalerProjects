package com.scaler;


import com.scaler.filterfiles.*;
import com.scaler.merge.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

// Assignment #1
// Session on Java Basics for Projects - 1
// conducted on 25 April 2023 by Arnav Gupta
//
// Problem Statement
// Inside the folder /files there are 2 files
//
// in1.txt
// in2.txt
// They both contain some numbers (separate numbers in each line).
//
// You have to read both files, merge all the numbers into one sorted array
// and then print that into a third file out.txt
//
// Bonus Levels
//      Level 1: Make the program work for any number of files.
//      Level 2: Make sure that the file read and write operations are on a separate thread
//      Level 3: Make sure that the read operations can be all done in parallel
//              (i.e. we are not waiting for file 1 to be read before we read file 2)
//
// DOUBT: KKK: Regarding Level 2: all the read operations can be done on separate threads.
// But the write CANNOT happen until all files have been read because we need to know
// all the numbers in order to sort them into their correct positions.
//
public class Main {
    public static void main(String[] args) {

        String currPath = System.getProperty("user.dir") + "/files/";
        if (args.length != 0) {
            currPath = args[0] + "/files/";
        }

        /*
         It would be nice to have a try-catch block here.
         All in good time. baby steps for a Java newbie :)
        */
        FilterFileByName fileNamesFiltered = new FilterFileByNameWithFileFilter();
        ArrayList<String> inFiles = fileNamesFiltered.getFileNamesInFolder(currPath, "in[0-9]+.txt");

        String outFile = (currPath + "out.txt");
        MergeFiles mf = new MergeFilesWithSingleSort();
        mf.mergeFiles(outFile, inFiles);
    }
}