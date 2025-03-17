package BT4;

import java.io.*;
import java.util.concurrent.*;

class FileReaderTask implements Runnable {
    private final String filePath;
    private final BlockingQueue<String> queue;

    public FileReaderTask(String filePath, BlockingQueue<String> queue) {
        this.filePath = filePath;
        this.queue = queue;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                queue.put(line); // Đưa dữ liệu vào hàng đợi
            }
            queue.put("EOF"); // Đánh dấu kết thúc
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class WordCountTask implements Runnable {
    private final BlockingQueue<String> queue;

    public WordCountTask(BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int totalWordCount = 0;
        try {
            while (true) {
                String line = queue.take(); // Lấy dữ liệu từ hàng đợi
                if (line.equals("EOF")) break; // Kiểm tra kết thúc
                totalWordCount += line.split("\\s+").length; // Đếm số từ
            }
            System.out.println("Tổng số từ: " + totalWordCount);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String filePath = "input.txt"; // Đường dẫn tệp dữ liệu
        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        Thread readerThread = new Thread(new FileReaderTask(filePath, queue));
        Thread processorThread = new Thread(new WordCountTask(queue));

        readerThread.start();
        processorThread.start();
    }
}
