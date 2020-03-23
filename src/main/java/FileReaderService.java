import java.io.*;

public class FileReaderService {
    FileReader fileReader;

    FileReaderService(String fullPath) throws FileNotFoundException {
        fileReader = new FileReader(fullPath);
    }

    public String getContentFile() throws IOException {
        String content = new String();
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null || line.trim().length() == 0) {
                    break;
                }
                content += line + '\n';
            }
        return content;
    }
}
