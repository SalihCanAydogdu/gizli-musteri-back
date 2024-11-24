package com.example.gizli_musteri_back.services;

import java.io.*;
import java.nio.file.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static void zipDirectory(Path sourceDirPath, OutputStream os) throws IOException {
        try (ZipOutputStream zs = new ZipOutputStream(os)) {
            Files.walk(sourceDirPath)
                .filter(path -> !Files.isDirectory(path))
                .forEach(path -> {
                    ZipEntry zipEntry = new ZipEntry(sourceDirPath.relativize(path).toString());
                    try {
                        zs.putNextEntry(zipEntry);
                        Files.copy(path, zs);
                        zs.closeEntry();
                    } catch (IOException e) {
                        System.err.println("Error while zipping file: " + path);
                    }
                });
        }
    }
}
