package com.scaler;

// Assignment 02
// On https://jsonplaceholder.typicode.com/photos there are 5000 photos.
// 50 each in 100 albums.
// The response contains the URL from where each photo can be downloaded.
// You have to download all the photos and save them in respective folders.
//
// We can just download 2 photos each from albums 1-10 for demo purpose
// After download, the folder structure should look like this
//        /albums
//        /1
//        9e59da.png
//        a4bc66.png
//
//        /2
//        9e59da.png
//        2cd88b.png
//
//        /3
//        9e59da.png
//        2cd88b.png
//
//        ...
//
//        /10
//        9e59da.png
//        2cd88b.png
// https://github.com/scaleracademy/Project-Module-Apr-2023/blob/main/java-basics/README.md

import com.scaler.fileio.WritePhotoToFile;
import com.scaler.fileio.WriteToFile;
import com.scaler.http.HttpPhotoDownloader;
import com.scaler.json.JSONPlaceHolderClient;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Create a new repository under your own account (not a fork of this repo)
//        Create a /albums folder where the output will be stored
//        Finish the assignment, and upload the final /albums folder
//        The repository should contain the final working code as well as the /albums folder
//        Submit your assignment on this form
//

// https://docs.google.com/forms/d/e/1FAIpQLSfCG3u656lsptYXsWfrgwl8mg6BcxmZIv_wXNV5fk46Nww7jQ/viewform

class DownloadAndWritePhoto implements Runnable {
    final private static HttpPhotoDownloader photoDownloader = new HttpPhotoDownloader();
    private String fileName;
    private String photoUrl;
    final private static WriteToFile writer = new WritePhotoToFile();
    public DownloadAndWritePhoto(String outFile, String url) {
        this.fileName = outFile;
        this.photoUrl = url;
        //writer.write(outFile, photoDownloader.getPhoto(url));
    }

    private void processPhoto (String fileName, String url) {
        writer.write(fileName, photoDownloader.getPhoto(url));
    }
    @Override
    public void run() {
        System.out.println("Processing file " + fileName + " in a new thread");
        processPhoto(fileName, photoUrl);
    }
}

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("usage: <url> <output-path>");
            System.out.println("\t<url>: URL of the website containing photos that need to be downloaded");
            System.out.println("\t<output-path>: path to local folder in which the albums are downloaded");
            System.out.println("\t\tNote that a folder called albums will be created in <output-path>");
            System.exit(-1);
        }
        final String url = args[0];
        final String albumPath = args[1] + "/albums";

        final Integer NumAlbumsToProcess = 10;
        final Integer NumPhotosPerAlbumToProcess = 2;
        final Integer TotalPhotosPerAlbum = 50;
        final Integer TotalAlbums = 100;


        try {
            var jsonClient = new JSONPlaceHolderClient(url);
            var response = jsonClient.getApi().getPhotos().execute();

            // Get the list of photos to be downloaded & the corresponding filenames into which they have to be written
            List<String> fileNames = new ArrayList<>();
            List<String> photoUrls = new ArrayList<>();
            response.body().forEach(photo -> {
                Integer currAlbumId = photo.getAlbumId();
                Integer currPhotoId = photo.getId() % TotalPhotosPerAlbum;
                if (currAlbumId <= NumAlbumsToProcess  &&  currPhotoId > 0  &&  currPhotoId <= NumPhotosPerAlbumToProcess) {
                    String[] tokens = photo.getUrl().split("/");

                    String filePath = albumPath + "/" + currAlbumId + "/" + tokens[tokens.length-1] + ".png";
                    fileNames.add(filePath);
                    photoUrls.add(photo.getUrl());
                }
            });
            assert (fileNames.size() != 0);
            assert (photoUrls.size() == fileNames.size());

            // download and write the photos in their respective files concurrently
            // Note: This whole code is convoluted and can probably be simplified
            // but this will do for now when I'm just dipping my toes into the pool of concurrency.
            ExecutorService pool = Executors.newFixedThreadPool(NumAlbumsToProcess * NumPhotosPerAlbumToProcess);
            for (int i = 0; i < fileNames.size(); i++) {
                pool.submit(new DownloadAndWritePhoto(fileNames.get(i), photoUrls.get(i)));
            }
            pool.shutdown();
            pool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            // Should probably be writing error messages to System.err not System.out
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
