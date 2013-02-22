package com.paradise.java7.learning.nio2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryTest {
    public static void main(String[] args) {
        //This is just check the direct C:\ path, not all the sub directories in it. 
        Path dir = Paths.get("C:");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, "*.properties")) {
            for (Path entry : stream) {
                System.out.println(entry.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
