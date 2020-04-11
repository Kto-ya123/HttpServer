import java.io.*;

public class FileReaderService {
    FileReader fileReader;
    private static String path = "C:\\Users\\Artur\\eclipse-workspace\\HttpServer\\src\\pages/";

    FileReaderService(String fileName) throws FileNotFoundException {
        fileReader = new FileReader(path + fileName);
    }

    public byte [] getContentFile() throws IOException {
        String content = new String();
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null || line.trim().length() == 0) {
                    break;
                }
                content += line + '\n';
            }
        return content.getBytes();
    }
}
