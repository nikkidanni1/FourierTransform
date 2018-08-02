package fouriertransform;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class ImgFiles {

    public static void writeToImgFiles(TransformImg trImg, int c) {
        File curDir = trImg.getCurDir();
        int XMax = trImg.getXMax();
        int YMax = trImg.getYMax();
        BufferedImage ReImg = createImg(XMax, YMax, trImg.getRe(), c);
        BufferedImage ImImg = createImg(XMax, YMax, trImg.getIm(), c);
        BufferedImage FAImg = createImg(XMax, YMax, trImg.getFA(), c);
        BufferedImage FFImg = createImg(XMax, YMax, trImg.getFF(), c);
        BufferedImage FobrImg = createImg(XMax, YMax, trImg.getFobr(), 1);
        
        try {
            ImageIO.write(ReImg, "png", new File(curDir, "/ReImg.png"));
        } catch (IOException ex) {
            Logger.getLogger(TransformImg.class.getName()).log(Level.SEVERE, "can't create ReImg.png", ex);
        }
        try {
            ImageIO.write(ImImg, "png", new File(curDir, "/ImImg.png"));
        } catch (IOException ex) {
            Logger.getLogger(TransformImg.class.getName()).log(Level.SEVERE, "can't create ImImg.png", ex);
        }
        try {
            ImageIO.write(FAImg, "png", new File(curDir, "/FAImg.png"));
        } catch (IOException ex) {
            Logger.getLogger(TransformImg.class.getName()).log(Level.SEVERE, "can't create FAImg.png", ex);
        }
        try {
            ImageIO.write(FFImg, "png", new File(curDir, "/FFImg.png"));
        } catch (IOException ex) {
            Logger.getLogger(TransformImg.class.getName()).log(Level.SEVERE, "can't create FFImg.png", ex);
        }
        try {
            ImageIO.write(FobrImg, "png", new File(curDir, "/FobrImg.png"));
        } catch (IOException ex) {
            Logger.getLogger(TransformImg.class.getName()).log(Level.SEVERE, "can't create FobrImg.png", ex);
        }
        trImg.setImgRe(new File(curDir, "/ReImg.png"));
        trImg.setImgIm(new File(curDir, "/ImImg.png"));
        trImg.setImgFA(new File(curDir, "/FAImg.png"));
        trImg.setImgFF(new File(curDir, "/FFImg.png"));
        trImg.setImgFobr(new File(curDir, "/FobrImg.png"));
    }
    private static BufferedImage createImg(int XMax, int YMax, double[][] rgb, int c) {
        BufferedImage bufferedImage = new BufferedImage(XMax, YMax, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < YMax; i++) {
            for(int j = 0; j < XMax; j++) {
                Color color = new Color(Math.abs((int)(rgb[i][j]*c))%256,Math.abs((int)(rgb[i][j]*c))%256,Math.abs((int)(rgb[i][j]*c))%256);
                bufferedImage.setRGB(j, i, color.getRGB());
            }
        }
        return bufferedImage;
    }
    public static File writeToImgFile(double[][] arr, File curDir, int XMax, int YMax, int c, String name) {
        BufferedImage img = createImg(XMax, YMax, arr, c);
        File file = new File(curDir, name);
        try {
            ImageIO.write(img, "png", new File(curDir, name));
        } catch (IOException ex) {
            Logger.getLogger(TransformImg.class.getName()).log(Level.SEVERE, "can't create " + name, ex);
        }
        return file;
    }
}
