package BT10;

import java.io.*;
import java.net.*;

class URLDownloadThread extends Thread {
    private final String urlString;
    private final String outputFilePath;

    public URLDownloadThread(String urlString, String outputFilePath) {
        this.urlString = urlString;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void run() {
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(urlString).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(outputFilePath)) {

            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("Tải nội dung từ URL hoàn tất: " + urlString);
        } catch (IOException e) {
            System.err.println("Lỗi khi tải nội dung từ URL: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String url = "https://example.com"; // Thay URL bằng liên kết bạn muốn tải
        String outputFile = "output.txt";   // Đường dẫn tệp đích để lưu nội dung

        URLDownloadThread downloadThread = new URLDownloadThread(url, outputFile);
        downloadThread.start();
    }
}

