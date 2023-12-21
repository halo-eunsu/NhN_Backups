import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JavaFilesExtractor {
    public static void main(String[] args) {
        File currentDirectory = new File(".");
        File[] filesList = currentDirectory.listFiles();
        String selfFileName = "JavaFilesExtractor.java"; // 자신의 파일 이름 지정

        try (FileWriter writer = new FileWriter("output.txt")) {
            for (File file : filesList) {
                if (file.isFile() && file.getName().endsWith(".java") && !file.getName().equals(selfFileName)) {
                    // 파일 이름을 기록합니다.
                    writer.write(file.getName() + "\n\n");

                    // 파일의 소스코드를 읽고 기록합니다.
                    String content = new String(Files.readAllBytes(Paths.get(file.getPath())));
                    writer.write(content + "\n\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
