import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class ImageReaderService {
    InputStream inputStream;

        ImageReaderService(String filename) throws FileNotFoundException {
            inputStream = new FileInputStream(Environment.getPathToPages() + filename);
        }


        public byte[] read() throws IOException {
            BufferedImage image;
            try {
                image = ImageIO.read(inputStream);
            } finally {
                inputStream.close();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
            return byteArrayOutputStream.toByteArray();
        }
}
