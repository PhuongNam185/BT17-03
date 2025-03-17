package BT8;


import java.io.*;

class FileReaderThread extends Thread {
    private final String sourceFilePath;
    private final SharedBuffer buffer;

    public FileReaderThread(String sourceFilePath, SharedBuffer buffer) {
        this.sourceFilePath = sourceFilePath;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.write(line); // Ghi dữ liệu vào buffer
            }
            buffer.setDone(true); // Thông báo việc đọc dữ liệu đã xong
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file nguồn: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class FileWriterThread extends Thread {
    private final String destinationFilePath;
    private final SharedBuffer buffer;

    public FileWriterThread(String destinationFilePath, SharedBuffer buffer) {
        this.destinationFilePath = destinationFilePath;
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destinationFilePath))) {
            String line;
            while ((line = buffer.read()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file đích: " + e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class SharedBuffer {
    private String buffer = null;
    private boolean done = false;

    public synchronized void write(String data) throws InterruptedException {
        while (buffer != null) {
            wait(); // Chờ buffer rỗng
        }
        buffer = data;
        notifyAll(); // Thông báo Thread khác
    }

    public synchronized String read() throws InterruptedException {
        while (buffer == null && !done) {
            wait(); // Chờ buffer có dữ liệu
        }
        String data = buffer;
        buffer = null;
        notifyAll(); // Thông báo Thread khác
        return data;
    }

    public synchronized void setDone(boolean done) {
        this.done = done;
        notifyAll(); // Đảm bảo tất cả Threads đang chờ được đánh thức
    }
}

public class Main {
    public static void main(String[] args) {
        String sourceFilePath = "source.txt"; // Đường dẫn file nguồn
        String destinationFilePath = "destination.txt"; // Đường dẫn file đích

        SharedBuffer buffer = new SharedBuffer();

        FileReaderThread readerThread = new FileReaderThread(sourceFilePath, buffer);
        FileWriterThread writerThread = new FileWriterThread(destinationFilePath, buffer);

        readerThread.start();
        writerThread.start();
    }
}
