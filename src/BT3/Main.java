package BT3;

import java.io.*;

class FileCopyThread extends Thread {
    private String sourceFile;
    private String destinationFile;

    public FileCopyThread(String sourceFile, String destinationFile) {
        this.sourceFile = sourceFile;
        this.destinationFile = destinationFile;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Sao chép file hoàn tất!");
        } catch (IOException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String sourceFile = "source.txt"; // Đường dẫn file nguồn
        String destinationFile = "destination.txt"; // Đường dẫn file đích

        FileCopyThread fileCopyThread = new FileCopyThread(sourceFile, destinationFile);
        fileCopyThread.start();
    }
}
