package com.scaler.fileio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WritePhotoToFile implements WriteToFile{
    public void write (String outFile, String photo) {
        try {
            File file = new File(outFile);
            file.getParentFile().mkdirs();
            file.createNewFile();

            FileWriter fw = new FileWriter(outFile);
            fw.write(photo);
            fw.close();
        } catch (IOException e) {
            // Should probably be writing error messages to System.err not System.out
            System.out.println("Exception: " + e.getMessage() + " while writing to file: " + outFile);
        }
    }

}
