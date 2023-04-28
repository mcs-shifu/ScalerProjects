package com.scaler.filterfiles;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class FilterFileByNameWithFileFilter implements FilterFileByName{
    public ArrayList<String> getFileNamesInFolder (String path, String filter) {
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (! pathname.getName().matches(filter)) {
                    System.out.println("Ignoring file: files/" + pathname.getName());
                }
                return pathname.getName().matches(filter);
            }
        };

        ArrayList<String> result = new ArrayList<>();

        File cwd = new File(path);
        File[] files = cwd.listFiles(fileFilter);

        if (files == null) {
            // KKK: need to change this eventually to an exception not a hard exit
            System.err.println("folder /files does not exist. Abandoning program");
            System.exit(1);
        }
        for (int i = 0;  i < files.length;  ++i) {
            result.add(i, path + files[i].getName());
        };
        return result;
    }
}
