import java.io.*;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileStore;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MyFileWriter {
    public static void main(String[] args) throws IOException {


        // generateRegularFile();
        // generateHiddenFile();
        // String data = "Hello, World!";
        // String fileName1 = "example.txt";
        // String fileName2 = "example2.txt";
        // String fileName3 = "example3.txt";
        // String fileName4 = "example4.txt";

        // // 1. Using FileWriter
        // try (FileWriter writer = new FileWriter(fileName1)) {
        //     writer.write(data);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // // 2. Using BufferedWriter
        // try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName2))) {
        //     bufferedWriter.write(data);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // // 3. Using FileOutputStream
        // try (FileOutputStream outputStream = new FileOutputStream(fileName3)) {
        //     outputStream.write(data.getBytes());
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // // 4. Using BufferedOutputStream
        // try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(fileName4))) {
        //     bufferedOutputStream.write(data.getBytes());
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // printFileSize("example5.txt");
        // try {
        //     System.out.println(stringify("example5.txt"));
        // } catch (IOException e) {
        //     // TODO Auto-generated catch block
        //     e.printStackTrace();
        // }
       
        File file1 = new File("file1.txt");
        File file2 = new File("file2.txt");
        File file3 = new File("file3.txt");

        Files.writeString(file1.toPath(), "Hellur");
        Files.writeString(file2.toPath(), "My name is ellikakaka");
        Files.writeString(file3.toPath(), "bababooey went to topics class and cried");

        printFileSize("file1.txt", "file2.txt", "file3.txt", "smush.txt");
        
            String h1 = hashFile("file1.txt");
            String h2 = hashFile("file2.txt");
            System.out.println("Hash of file1.txt: " + h1);
            System.out.println("Hash of file2.txt: " + h2);
            testHashFileEmptyFiles();
            testHashFileLargeFiles();
            testHashFileSpecialChars();
            testHashFileNonExistent();
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
    private static void printFileSize(String... fileNames) {
    long totalSize = 0;
    for (String fileName : fileNames) {
        File file = new File(fileName);
        if (file.exists()) {
            totalSize += file.length();
        }
    }
    System.out.println("Total size of all files: " + totalSize + " bytes");
}




/**
* Reads a text file and returns its contents as a string.
* 
* @param filePath the path to the file
* @return the contents of the file as a string
* @throws IOException if an I/O error occurs
*/
public static String stringify(String filePath) throws IOException {
    Path filePathh = Paths.get(filePath);
    String content = Files.readString(filePathh);
    return content;
}



    
    public static String hashFile(String filePath) {
        try {
            // info on message digest: https://docs.oracle.com/javase/8/docs/api/java/security/MessageDigest.html
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");

            // Read file in chunks of 1024 bytes using fileinputstream: https://docs.oracle.com/javase/8/docs/api/java/io/FileInputStream.html and a buffer byte array: https://docs.oracle.com/javase/tutorial/essential/io/file.html

            try (InputStream fileInputS = new FileInputStream(filePath)) {
                byte[] temporary = new byte[1024];
                int numberBytes = fileInputS.read(temporary);
                while (numberBytes != -1) {
                    sha256.update(temporary, 0, numberBytes);
                    numberBytes = fileInputS.read(temporary);
                }

                // finalizing hash
                byte[] hashBytes = sha256.digest();

                // convert bytes to readable hex string: https://www.baeldung.com/sha-256-hashing-java

                StringBuilder str = new StringBuilder();
                for (byte b : hashBytes) {
                    String hex = String.format("%02x", b);
                    str.append(hex);
                }
                return str.toString();
            }

        } catch (FileNotFoundException e) {
            System.err.println("Error: File not found -> " + filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error: SHA-256 algorithm not available.");
        }
        return null;
    }


    // empty file test
    // expected hash: e3b0c442... (the known empty sha256)
    // got the same when I ran it---> pass

    public static void testHashFileEmptyFiles() throws IOException {
        // creates a 0-byte empty file
        Path emptyFile = Paths.get("emptyTest.txt");
        Files.write(emptyFile, new byte[0]);

        // hashes the empty file
        String hash = hashFile("emptyTest.txt");

        // known SHA-256 hash of an empty file (verified online)
        String expected = "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855";

        System.out.println("Empty file hash: " + hash);
        System.out.println("Matches expected? " + hash.equals(expected));
    }

    // large file test
    // made a file with 100k lines, hash ran fine, didn’t crash ; pass
public static void testHashFileLargeFiles() throws IOException {
    // making a LARGEE test file
    Path largeFile = Paths.get("largeTest.txt");
    StringBuilder stringBuild = new StringBuilder();

    for (int i = 0; i < 100000; i++) {
        stringBuild.append("bababoey.\n");
    }
    Files.writeString(largeFile, stringBuild.toString());

    // Hash the large file
    String hash = hashFile("largeTest.txt");

    System.out.println("Hash of large file: " + hash);
}


// special chars test
// file had chinese, arabic, hebrew, emoji ; hash came out length 64 → pass

public static void testHashFileSpecialChars() throws IOException {
    // making a file with with non-ASCII content 
    Path specialFile = Paths.get("specialTest.txt");
    Files.writeString(specialFile, "你好世界 🚀 مرحبا بالعالمשלום עולם");

    // hashing the file
    String hash = hashFile("specialTest.txt");

    System.out.println("Special chars file hash: " + hash);
    System.out.println("Pass? " + (hash != null && hash.length() == 64));

}


// non-existent file test
// tried to hash a file that doesn’t exist, got error + null ; pass

public static void testHashFileNonExistent() {
    String result = hashFile("no_such_file.txt");

    // should get FileNotFoundException, so should return 
    System.out.println("Non-existent file test: " + (result == null));
}





}
