package BT5;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class LogWriterThread extends Thread {
    private final String logFile;
    private final String message;

    public LogWriterThread(String logFile, String message) {
        this.logFile = logFile;
        this.message = message;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            writer.write(timestamp + " - " + message);
            writer.newLine();
            System.out.println("Đã ghi log: " + timestamp + " - " + message);
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi log: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String logFile = "log.txt"; // Đường dẫn đến file log
        String message = "Đây là thông điệp log đầu tiên";

        LogWriterThread logThread = new LogWriterThread(logFile, message);
        logThread.start();
    }
}
