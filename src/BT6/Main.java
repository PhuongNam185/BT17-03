package BT6;

import java.io.RandomAccessFile;

class FileReaderThread extends Thread {
    private String filePath;
    private long start;
    private long end;

    public FileReaderThread(String filePath, long start, long end) {
        this.filePath = filePath;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            file.seek(start); // Đi tới vị trí bắt đầu
            long bytesRead = start;

            while (bytesRead < end) {
                String line = file.readLine();
                if (line == null) break; // Nếu đến cuối file, dừng lại
                System.out.println(Thread.currentThread().getName() + " đọc: " + line);
                bytesRead = file.getFilePointer(); // Cập nhật vị trí đọc
            }
        } catch (Exception e) {
            System.err.println(Thread.currentThread().getName() + " gặp lỗi: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String filePath = "largefile.txt"; // Đường dẫn đến file lớn
        long fileSize = 0;

        try (RandomAccessFile file = new RandomAccessFile(filePath, "r")) {
            fileSize = file.length(); // Kích thước file
        } catch (Exception e) {
            System.err.println("Không thể đọc file: " + e.getMessage());
            return;
        }

        int threadCount = 4; // Số lượng Thread
        long chunkSize = fileSize / threadCount;

        for (int i = 0; i < threadCount; i++) {
            long start = i * chunkSize;
            long end = (i == threadCount - 1) ? fileSize : (i + 1) * chunkSize;

            FileReaderThread thread = new FileReaderThread(filePath, start, end);
            thread.start();
        }
    }
}
