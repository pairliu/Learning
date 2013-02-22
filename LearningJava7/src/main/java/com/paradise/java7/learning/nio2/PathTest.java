package com.paradise.java7.learning.nio2;

import java.nio.file.Path;
import java.nio.file.Paths;

public class PathTest {
    public static void main(String[] args) {
        Path listing = Paths.get("/usr/bin/zip");
        System.out.println("File Name [" + listing.getFileName() + "]");
        System.out.println("Number of Name Elements in the Path [" + listing.getNameCount() + "]");
        System.out.println("Parent Path [" + listing.getParent() + "]");
        System.out.println("Root of Path [" + listing.getRoot() + "]");
        System.out.println("Subpath from Root, 2 elements deep [" + listing.subpath(0, 2) + "]");
    }

}
