import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class MyFileWriter {
    public static void main(String[] args) {
        generateRegularFile();
        generateHiddenFile();
        String data = "Hello, World!";
        String fileName1 = "example.txt";
        String fileName2 = "example2.txt";
        String fileName3 = "example3.txt";
        String fileName4 = "example4.txt";

        // 1. Using FileWriter
        try (FileWriter writer = new FileWriter(fileName1)) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 2. Using BufferedWriter
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName2))) {
            bufferedWriter.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. Using FileOutputStream
        try (FileOutputStream outputStream = new FileOutputStream(fileName3)) {
            outputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 4. Using BufferedOutputStream
        try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName4))) {
            bufferedOutputStream.write(data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        printFileSize("example5.txt");

    }

    public static void generateHiddenFile() {
        // 5. Using Files (java.nio.file)
        try {
            String data = "pookie";
            Files.write(Paths.get(".secrtpswd.txt"), data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static void generateRegularFile() {
        // 5. Using Files (java.nio.file)
        try {
            String data = "confidential info";
            Files.write(Paths.get(".topsecret/confidentiallll.txt"), data.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Calculate and print the file size using the File class
    private static void printFileSize(String fileName) {
        File f = new File(fileName);
        System.out.println(f.getTotalSpace());
        }

}