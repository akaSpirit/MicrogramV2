package dev.khansergei.microgramv2.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util {
   private static final String postsDir = "src/main/resources/static/";

    public static String createFile(MultipartFile multipartFile, int postID, String username) {
        byte[] data = getBytesFromMultipartFile(multipartFile);
        String name = String.format("posts_%s_%s.jpg", username, postID);
        File file = new File(postsDir + name);
        try(FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name;
    }

    public static void deleteFile(String path) {
        try {
            Files.delete(Path.of(postsDir + path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static byte[] getBytesFromMultipartFile(MultipartFile multipartFile) {
        byte[] data = new byte[0];
        try {
            data = multipartFile.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }
}