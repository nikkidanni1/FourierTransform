package fouriertransform;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TxtFiles {
    static File curTime = null;
    private TxtFiles() {};
    public static void writeToTxtFiles(TransformImg trImg) {
        int XMax = trImg.getXMax();
        int YMax = trImg.getYMax();
        double[][] Re = trImg.getRe();
        double[][] Im = trImg.getIm();
        double[][] FA = trImg.getFA();
        double[][] FF = trImg.getFF();
        
        curTime = new File("../" + LocalDateTime.now().toString().replace(":", "_"));
        curTime.mkdirs();
        File txtRe = new File(curTime,"/Re.txt");
        File txtIm = new File(curTime, "/Im.txt");
        File txtFA = new File(curTime, "/FA.txt");
        File txtFF = new File(curTime, "/FF.txt");
        
    	try ( FileWriter filewriterIm = new FileWriter(txtIm) ) {
            try ( FileWriter filewriterRe = new FileWriter(txtRe) ) {
         	   try ( FileWriter filewriterFF = new FileWriter(txtFF) ) {
                    try ( FileWriter filewriterFA = new FileWriter(txtFA) ) {
                 	   	for (int i = 0; i < YMax; i++ ) {
                 	   		for (int j = 0; j < XMax; j++ ) {
                 	   			if ( j == 0 && i != 0 ) {
                 	   				filewriterIm.write("\n");
                 	   				filewriterRe.write("\n");
                 	   				
                 	   				filewriterFF.write("\n");
                 	   				filewriterFA.write("\n");
                 	   			}
                 	   			
                 	   			filewriterIm.write(Im[i][j] + " ");
                 	   			filewriterRe.write(Re[i][j] + " ");
             		   
                 	   			filewriterFF.write(FF[i][j] + " ");
                 	   			filewriterFA.write(FA[i][j] + " ");
                 	   		} 
                 	   	}
                    } catch (IOException exFA) {
                 	   Logger.getLogger(TxtFiles.class.getName()).log(Level.SEVERE, "can't write FA.txt", exFA);
                    }
         	   } catch (IOException exFF) {
         		   Logger.getLogger(TxtFiles.class.getName()).log(Level.SEVERE, "can't write FF.txt", exFF);
         	   }
            } catch (IOException exRe) {
                Logger.getLogger(TxtFiles.class.getName()).log(Level.SEVERE, "can't write Re.txt", exRe);
            }
         } catch (IOException ex) {
         	Logger.getLogger(TxtFiles.class.getName()).log(Level.SEVERE, "can't write Im", ex);
         }
        trImg.setTxtRe(txtRe);
        trImg.setTxtIm(txtIm);
        trImg.setTxtFA(txtFA);
        trImg.setTxtFF(txtFF);
        trImg.setCurDir(curTime);
    }
     public static File getCurDir() {
        return curTime;
    }
    public static File writeToTxtFile(double[][] arr, String name, int XMax, int YMax) {
        File txtFile = new File(curTime, name);
        try ( FileWriter filewriter = new FileWriter(txtFile) ) {
            for (int i = 0; i < YMax; i++ ) {
                for (int j = 0; j < XMax; j++ ) {
                    if ( j == 0 && i != 0 ) {
                 	filewriter.write("\n");
                    }
                    filewriter.write(arr[i][j] + " ");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(TxtFiles.class.getName()).log(Level.SEVERE, "can't write txtFile", ex);
        }
        return txtFile;
    }
}
