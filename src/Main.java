import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Arrays;

public class Main
{
    public static void main(String[] args)
    {
        String srcFolder = "/users/Valet/Desktop/src";
        String dstFolder = "/users/Valet/Desktop/dst";
        int cores = Runtime.getRuntime().availableProcessors();
        File srcDir = new File(srcFolder);
        long start = System.currentTimeMillis();
        File[] files = srcDir.listFiles();

        System.out.println("Cores: " + cores);
        System.out.println("Files count: " + files.length);

        int begin;
        int end = 0;
        for (int i = 0; i < cores; i++)
        {
            begin = end;
            end = (int)Math.ceil((double)files.length/cores) + begin;

            if (files.length < end){
                while (files.length != end){
                    end -= 1;
                }
            }

            File[] files1 = Arrays.copyOfRange(files, begin, end);
            ImageResizer resizer = new ImageResizer(files1, dstFolder, start);
            resizer.start();
        }

    }
}
