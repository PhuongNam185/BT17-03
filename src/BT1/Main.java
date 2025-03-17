package BT1;//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.*;

class FileReaderThread extends Thread {
    private String filePath;

    public FileReaderThread(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String filePath = "your_file_path.txt"; // Đổi "your_file_path.txt" thành đường dẫn file của bạn
        FileReaderThread fileReaderThread = new FileReaderThread(filePath);
        fileReaderThread.start();
    }
}
