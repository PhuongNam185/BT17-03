package BT9;

import java.io.*;

class CharacterCountThread extends Thread {
    private final String inputFilePath;
    private final String outputFilePath;

    public CharacterCountThread(String inputFilePath, String outputFilePath) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            int characterCount = 0;
            String line;

            // Đếm số ký tự trong file
            while ((line = reader.readLine()) != null) {
                characterCount += line.length();
            }

            // Ghi kết quả vào file khác
            writer.write("Tổng số ký tự trong file: " + characterCount);
            System.out.println("Đã ghi kết quả: Tổng số ký tự là " + characterCount);

        } catch (IOException e) {
            System.err.println("Lỗi: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String inputFilePath = "input.txt"; // Thay đường dẫn tới file bạn cần xử lý
        String outputFilePath = "output.txt"; // Thay đường dẫn file để ghi kết quả

        CharacterCountThread thread = new CharacterCountThread(inputFilePath, outputFilePath);
        thread.start();
    }
}

