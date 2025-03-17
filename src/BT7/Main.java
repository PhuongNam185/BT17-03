package BT7;

import java.io.*;
import java.util.Scanner;

class KeyboardInputThread extends Thread {
    private final String filePath;

    public KeyboardInputThread(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in);
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            System.out.println("Nhập dữ liệu (gõ 'exit' để thoát):");

            while (true) {
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input)) {
                    System.out.println("Đã dừng ghi file.");
                    break;
                }
                writer.write(input);
                writer.newLine();
                System.out.println("Đã ghi: " + input);
            }
        } catch (IOException e) {
            System.err.println("Lỗi ghi file: " + e.getMessage());
        }
    }
}

public class Main {
    public static void main(String[] args) {
        String filePath = "output.txt"; // Thay thế bằng đường dẫn file bạn muốn ghi
        KeyboardInputThread inputThread = new KeyboardInputThread(filePath);
        inputThread.start();
    }
}
