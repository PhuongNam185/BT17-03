package BT2;

import java.io.*;

class FileWriterThread extends Thread {
    private final String filePath;
    private final String message;
    private final Object lock;

    public FileWriterThread(String filePath, String message, Object lock) {
        this.filePath = filePath;
        this.message = message;
        this.lock = lock;
    }

    @Override
    public void run() {
        synchronized (lock) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(message);
                writer.newLine();
                System.out.println(Thread.currentThread().getName() + " đã ghi: " + message);
            } catch (IOException e) {
                System.err.println("Lỗi ghi file: " + e.getMessage());
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String filePath = "output.txt"; // Đường dẫn đến tệp
        Object lock = new Object();

        FileWriterThread thread1 = new FileWriterThread(filePath, "Chuỗi thứ nhất từ Thread 1", lock);
        FileWriterThread thread2 = new FileWriterThread(filePath, "Chuỗi thứ hai từ Thread 2", lock);

        thread1.start();
        thread2.start();
    }
}
