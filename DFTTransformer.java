package fouriertransform;

import java.awt.image.BufferedImage;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class DFTTransformer {
    
    private DFTTransformer() {};
    
    public static void DFTReIm(TransformImg trImg) {
        int XMax = trImg.getXMax();
        int YMax = trImg.getYMax();
        /*
        double[][] Re = new double[YMax][XMax];
        double[][] Im = new double[YMax][XMax];
        
        double[][] Re11 = new double[YMax/2][XMax/2];
        double[][] Im11 = new double[YMax/2][XMax/2];
        
        double[][] Re12 = new double[YMax/2][XMax/2];
        double[][] Im12 = new double[YMax/2][XMax/2];
        
        double[][] Re21 = new double[YMax/2][XMax/2];
        double[][] Im21 = new double[YMax/2][XMax/2];
        
        double[][] Re22 = new double[YMax/2][XMax/2];
        double[][] Im22 = new double[YMax/2][XMax/2];
        
        int[][] original = trImg.getOriginal();
        
        for ( int k = 0; k < YMax/2; k++ ) {
            for ( int l = 0; l < XMax/2; l++ )	{
                Re11[k][l] = 0;     Im11[k][l] = 0;
                for ( int m = 0; m < YMax; m++ ) {
                    for ( int n = 0; n < XMax; n++ )	{
                        double arg = ( (double)(k * m) / (double)YMax + (double)(l * n) / (double)XMax ) * Math.PI;
                        Re11[k][l] += ( (double)(original[m][n]&0x000000FF) * cos(arg) );
                        Im11[k][l] += ( (double)(original[m][n]&0x000000FF) * sin(arg) * -1.0 );
                    }
                }
                Re11[k][l] /= (double)( (XMax/2) * (YMax/2) );
                Im11[k][l] /= (double)( (XMax/2) * (YMax/2) );
            }
        }
        for ( int m = 0; m < YMax/2; m++ ) {
            for ( int n = 0; n < XMax/2; n++ )	{
                Re[m][n] = Re11[m][n];
                Im[m][n] = Im11[m][n];
            }
        }
        
        for ( int m = 0; m < YMax/2; m++ ) {
            for ( int n = 0; n < XMax/2; n++ )	{
                Re12[m][n] = Re11[m][XMax/2 - 1 - n];
                Im12[m][n] = Im11[m][XMax/2 - 1 - n];
            }
        }
        for ( int m = 0; m < YMax/2; m++ ) {
            for ( int n = 0; n < XMax/2; n++ )	{
                Re21[m][n] = Re11[YMax/2 - 1 - m][n];
                Im21[m][n] = Im11[YMax/2 - 1 - m][n];
            }
            
        }
        for ( int m = 0; m < YMax/2; m++ ) {
            for ( int n = 0; n < XMax/2; n++ )	{
                Re22[m][n] = Re11[YMax/2 - 1 - m][XMax/2 - 1 - n];
                Im22[m][n] = Im11[YMax/2 - 1 - m][XMax/2 - 1 - n];
            }
        }
        
        for ( int m = 0; m < YMax/2; m++ ) {
            for ( int n = XMax/2; n < XMax; n++ ) {
                Re[m][n] = Re12[m][n - XMax/2];
                Im[m][n] = Im12[m][n - XMax/2];
            }
        }
        for ( int m = YMax/2; m < YMax; m++ ) {
            for ( int n = 0; n < XMax/2; n++ )	{
                Re[m][n] = Re21[m - YMax/2][n];
                Im[m][n] = Im21[m - YMax/2][n];
            }
        }
        for ( int m = YMax/2; m < YMax; m++ ) {
            for ( int n = XMax/2; n < XMax; n++ )	{
                Re[m][n] = Re22[m - YMax/2][n - XMax/2];
                Im[m][n] = Im22[m - YMax/2][n - XMax/2];
            }
        }
        
        trImg.setRe(Re);
        trImg.setIm(Im);*/
        // код исходный
        double[][] Re = new double[YMax][XMax];
        double[][] Im = new double[YMax][XMax];
        int[][] original = trImg.getOriginal();
        
        for ( int k = 0; k < YMax; k++ ) {
            for ( int l = 0; l < XMax; l++ )	{
                Re[k][l] = 0;     Im[k][l] = 0;
                for ( int m = 0; m < YMax; m++ ) {
                    for ( int n = 0; n < XMax; n++ )	{
                        double arg = ( (double)(k * m) / (double)YMax + (double)(l * n) / (double)XMax ) * 2 * Math.PI;
                        Re[k][l] += ( (double)(original[m][n]&0x000000FF) * cos(arg) );
                        Im[k][l] += ( (double)(original[m][n]&0x000000FF) * sin(arg) * -1.0 );
                    }
                }
                Re[k][l] /= (double)( XMax * YMax );
                Im[k][l] /= (double)( XMax * YMax );
            }
        }
        trImg.setRe(Re);
        trImg.setIm(Im);
    }
    public static void DFTFaFf(TransformImg trImg) {
        int XMax = trImg.getXMax();
        int YMax = trImg.getYMax();
        
        double[][] Re = trImg.getRe();
        double[][] Im = trImg.getIm();
        double[][] FA = new double[YMax][XMax];
        double[][] FF = new double[YMax][XMax];
        
    	for(int i = 0; i < YMax; i++) {
            for(int j = 0; j < XMax; j++) {
                FA[i][j] = Math.sqrt(Re[i][j] * Re[i][j] + Im[i][j] * Im[i][j])/2;
                FF[i][j] = Math.atan(Im[i][j] / Re[i][j]);  
            }
        }
        trImg.setFA(FA);
        trImg.setFF(FF);
    }
    public static void reverseDFT(TransformImg trImg) {
        int XMax = trImg.getXMax();
        int YMax = trImg.getYMax();
        double[][] Re = trImg.getRe();
        double[][] Im = trImg.getIm();
        
        double[][] Fobr = new double[YMax][XMax];
        for(int m = 0; m < YMax; m++)
            for(int n = 0; n < XMax; n++) {
                Fobr[m][n] = 0;
                for(int k = 0; k < YMax; k++) {
                    for(int l = 0; l < XMax; l++){
                        double arg = ((double)(k*m)/(double)YMax +(double)(l*n)/(double)XMax)*2*Math.PI;
                        Fobr[m][n] += (Re[k][l] * cos(arg) + Im[k][l] * sin(arg));
                    }
                }
                //Fobr[m][n] /= XMax * YMax;
            }
        double[][] FobrRez = new double[YMax][XMax];
        for (int i = 0; i < YMax; i++) {
            for (int j = 0; j < XMax; j++) {
                FobrRez[YMax-1-i][XMax-1-j] = Fobr[i][j];
            }
        }
        trImg.setFobr(FobrRez);
    }
    public static TransformImg addup(TransformImg img1, TransformImg img2, int c) {
        TransformImg resultImg = new TransformImg(img1.getImgFobr());
        int XMax1 = img1.getXMax(), YMax1 = img1.getYMax();
        int XMax2 = img2.getXMax(), YMax2 = img2.getYMax();
        int XMax = XMax1, YMax = YMax1;
        double[][] addupRe = new double[YMax][XMax];
        double[][] addupIm = new double[YMax][XMax];
        double[][] img1Re = img1.getRe();
        double[][] img1Im = img1.getIm();
        
        double[][] img2Re = img2.getRe();
        double[][] img2Im = img2.getIm();
        /*
        if ((XMax1 == XMax2) && (YMax1 == YMax2)) {
            for (int i = 0; i < YMax; i++) {
                for (int j = 0; j < XMax; j++) {
                    addupRe[i][j] = (img1Re[i][j] + img2Re[i][j]);
                    addupIm[i][j] = (img1Im[i][j] + img2Im[i][j]);
                }
            }
        } else {
            for (int i = 0; i < YMax; i++) {
                for (int j = 0; j < XMax; j++) {
                    addupRe[i][j] = img1Re[i][j];
                    addupIm[i][j] = img1Im[i][j];
            }
        }
            
            for (int i = 0; i < YMax2; i++) {
                for (int j = 0; j < XMax2; j++) {
                    addupRe[i][j] = (img1Re[i][j] + img2Re[i][j]);
                    addupIm[i][j] = (img1Im[i][j] + img2Im[i][j]);
                    
                    addupRe[i][XMax - 1 - j] = (img1Re[i][XMax - 1 - j] + img2Re[i][j]);
                    addupIm[i][XMax - 1 - j] = (img1Im[i][XMax - 1 - j] + img2Im[i][j]);
                    
                    addupRe[YMax - 1 - i][j] = (img1Re[YMax - 1 - i][j] + img2Re[i][j]);
                    addupIm[YMax - 1 - i][j] = (img1Im[YMax - 1 - i][j] + img2Im[i][j]);
                    
                    addupRe[YMax - 1 - i][XMax - 1 - j] = (img1Re[YMax - 1 - i][XMax - 1 - j] + img2Re[i][j]);
                    addupIm[YMax - 1 - i][XMax - 1 - j] = (img1Im[YMax - 1 - i][XMax - 1 - j] + img2Im[i][j]);
                }
            }
                }*/
        // исходный вариант
        for (int i = 0; i < YMax; i++) {
            for (int j = 0; j < XMax; j++) {
                addupRe[i][j] = (img1Re[i][j] + img2Re[i % YMax2][j % XMax2]);
                addupIm[i][j] = (img1Im[i][j] + img2Im[i  % YMax2][j  % XMax2]);
            }
        }
        resultImg.setRe(addupRe);
        resultImg.setIm(addupIm);
        DFTFaFf(resultImg);
        reverseDFT(resultImg);
        TxtFiles.writeToTxtFiles(resultImg);
        ImgFiles.writeToImgFiles(resultImg, c);
        
        return resultImg;
    }
    
    public static TransformImg deshif(TransformImg img1, TransformImg img2) {
        TransformImg resultImg = new TransformImg(img1.getImgFobr());
        int XMax = img1.getXMax(), YMax = img1.getYMax();

        double[][] addupRe = new double[YMax][XMax];
        double[][] addupIm = new double[YMax][XMax];
        double[][] img1Re = img1.getRe();
        double[][] img1Im = img1.getIm();
        
        double[][] img2Re = img2.getRe();
        double[][] img2Im = img2.getIm();
        
            for (int i = 0; i < YMax; i++) {
                for (int j = 0; j < XMax; j++) {
                    addupRe[i][j] = (img1Re[i][j] - img2Re[i][j]);
                    addupIm[i][j] = (img1Im[i][j] - img2Im[i][j]);
                }
            }
            
        resultImg.setRe(addupRe);
        resultImg.setIm(addupIm);
        DFTFaFf(resultImg);
        reverseDFT(resultImg);
        TxtFiles.writeToTxtFiles(resultImg);
        ImgFiles.writeToImgFiles(resultImg, 450);
        
        return resultImg;
    }
    
    public static TransformImg convolution(TransformImg img1, TransformImg img2, int c) {
        /*img1.originalForConvolution();
        img1.transform();*/
        img2.originalForConvolution(0.2);
        img2.transform();
        TransformImg resultImg = new TransformImg(img1.getImgFobr());
        
        int XMax1 = img1.getXMax(), YMax1 = img1.getYMax();
        int XMax2 = img2.getXMax(), YMax2 = img2.getYMax();
        int XMax = XMax1, YMax = YMax1;
        
        double[][] convolRe = new double[YMax][XMax];
        double[][] convolIm = new double[YMax][XMax];
        double[][] img1Re = img1.getRe();
        double[][] img1Im = img1.getIm();
        
        double[][] img2Re = img2.getRe();
        double[][] img2Im = img2.getIm();
        
        for (int i = 0; i < YMax; i++) {
            for (int j = 0; j < XMax; j++) {
                convolRe[i][j] = img1Re[i][j] * img2Re[i][j] - img1Im[i][j] * img2Im[i][j];
                convolIm[i][j] = img1Re[i][j] * img2Im[i][j] + img2Re[i][j] * img1Im[i][j];
                /*convolRe[i][j] = img1Re[i][j] * img2Re[i][j];
                convolIm[i][j] = img1Im[i][j] * img2Im[i][j];*/
            }
        }
        //img1.originalForConvolution();
        
        resultImg.setRe(convolRe);
        resultImg.setIm(convolIm);
        DFTFaFf(resultImg);
        reverseDFT(resultImg);
        //resultImg.reverseForConvolution();
        TxtFiles.writeToTxtFiles(resultImg);
        ImgFiles.writeToImgFiles(resultImg, c);
        
        return resultImg;
    }
}