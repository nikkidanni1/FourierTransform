package fouriertransform;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public final class TransformImg {
    
    private static BufferedImage img;
    private int XMax, YMax;
    private int original[][];
    private double[][] Im, Re, Fobr, FA, FF;
    private File txtRe, txtIm, txtFA, txtFF;
    private File imgRe, imgIm, imgFA, imgFF, imgFobr;
    private int c = 450;
    private File curDir = null;
    private File originalFile;

    TransformImg(File originalFile) {
        try {
            img = ImageIO.read(originalFile);
        } catch (IOException ex) {
            Logger.getLogger(TransformImg.class.getName()).log(Level.SEVERE, "can't read the original image", ex);
        }
        this.originalFile = originalFile;
        XMax = img.getWidth();
        YMax = img.getHeight();
                
        original = new int[YMax][XMax];
        
        for (int y = 0; y < YMax; y++) {
            for (int x = 0; x < XMax; x++) {
            	original[y][x]=img.getRGB(x, y);
            }
        }

    }
    
    public void transform() {
        DFTTransformer.DFTReIm(this);
	DFTTransformer.DFTFaFf(this);
        TxtFiles.writeToTxtFiles(this);
        DFTTransformer.reverseDFT(this);
        ImgFiles.writeToImgFiles(this, c);
    }/*
    public void originalForConvolution() {
        for (int i = 0; i < YMax; i++) {
            for (int  j = 0; j < XMax; j++) {
                original[i][j] = (int)(original[i][j] * Math.pow(-1, i + j));
            }
        }   
    }
    public void reverseForConvolution() {
        for (int i = 0; i < YMax; i++) {
            for (int  j = 0; j < XMax; j++) {
                Fobr[i][j] = (int)(Fobr[i][j] * Math.pow(-1, i + j)) * 2;
            }
        }   
    }*/
    public void originalForConvolution(double co) {
        for (int i = 0; i < YMax; i++) {
            for (int  j = 0; j < XMax; j++) {
                original[i][j] = (255 << 24 ) | ((int)((double)(original[i][j]&0x000000FF) * co) << 16) | ((int)((double)(original[i][j]&0x000000FF) * co) << 8) | ((int)((double)(original[i][j]&0x000000FF) * co));
            }
        }   
    }
        public int[][] getOriginal() {
		return original;
	}
        public double[][] getFA() {
		return FA;
	}
        public void setFA(double[][] FAValues) {
		FA = FAValues;
	}
        public double[][] getFF() {
		return FF;
	}
        public void setFobr(double[][] fobr) {
            Fobr = fobr;
        }
        public double[][] getFobr() {
            return Fobr;
        }
        public void setFF(double[][] FFValues) {
		FF = FFValues;
	}
        public double[][] getRe() {
		return Re;
	}
        public void setRe(double[][] ReValues) {
		Re = ReValues;
	}
        public double[][] getIm() {
		return Im;
	}
        public void setIm(double[][] ImValues) {
		Im = ImValues;
	}
        public void setImg(BufferedImage image) {
            img = image;
        }
        public BufferedImage getImg() {
            return img;
        }
        public void setXMax(int XValue) {
            XMax = XValue;
        }
        public int getXMax() {
            return XMax;
        }
        public void setYMax(int YValue) {
            YMax = YValue;
        }
        public int getYMax() {
            return YMax;
        }
        public File getImgRe() {
            return imgRe;
        }
        public void setImgRe(File f) {
            imgRe = f;
        }
        public File getImgIm() {
            return imgIm;
        }
        public void setImgIm(File f) {
            imgIm = f;
        }
        public File getImgFA() {
            return imgFA;
        }
        public void setImgFA(File f) {
            imgFA = f;
        }
        public File getImgFF() {
            return imgFF;
        }
        public void setImgFF(File f) {
            imgFF = f;
        }
        public File getImgFobr() {
            return imgFobr;
        }
        public void setImgFobr(File f) {
            imgFobr = f;
        }
        
        public File getTxtRe() {
            return txtRe;
        }
        public void setTxtRe(File f) {
            txtRe = f;
        }
        public File getTxtIm() {
            return txtIm;
        }
        public void setTxtIm(File f) {
            txtIm = f;
        }
        public File getTxtFA() {
            return txtFA;
        }
        public void setTxtFA(File f) {
            txtFA = f;
        }
        public File getTxtFF() {
            return txtFF;
        }
        public void setTxtFF(File f) {
            txtFF = f;
        }
        public void setCurDir(File cur) {
            curDir = cur;
        }
        public File getCurDir() {
            return curDir;
        }
}
