import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class ImageResizer extends Thread
{
    private File[] files;
    private String dstFolder;
    private long start;

    public ImageResizer(File[] files, String dstFolder, long start) {
        this.files = files;
        this.dstFolder = dstFolder;
        this.start = start;
    }

    @Override
    public void run() {
        try
        {
            for(File file : files)
            {
                BufferedImage image = ImageIO.read(file);
                if(image == null) {
                    continue;
                }
                
                int newWidth = 300;
                int newHeight = (int) Math.round(
                        image.getHeight() / (image.getWidth() / (double) newWidth)
                );

                Image tmp = image.getScaledInstance(newWidth, newHeight , Image.SCALE_SMOOTH);
                BufferedImage resized = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
                Graphics2D g2d = resized.createGraphics();
                g2d.drawImage(tmp, 0, 0, null);
                g2d.dispose();

                /*BufferedImage newImage = new BufferedImage(
                        newWidth, newHeight, BufferedImage.TYPE_INT_RGB
                );
                int widthStep = image.getWidth() / newWidth;
                int heightStep = image.getHeight() / newHeight;

                for (int x = 0; x < newWidth; x++)
                {
                    for (int y = 0; y < newHeight; y++) {
                        int rgb = image.getRGB(x * widthStep, y * heightStep);
                        newImage.setRGB(x, y, rgb);
                    }
                }*/

                File newFile = new File(dstFolder + "/" + file.getName());
                ImageIO.write(resized, "jpg", newFile);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("Duration: " + (System.currentTimeMillis() - start) + " ms");
    }
}
