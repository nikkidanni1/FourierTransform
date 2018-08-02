package fouriertransform;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

public class FourierEditor extends JFrame{
    private TransformImg transformimg, addupTrImg, convolTrImg;
    private ImageIcon imgReverse = null, imgIm;
    private TextField infoField;
    private JFileChooser fileChooser = new JFileChooser();
    private File addupF, convolF;
    
    public FourierEditor(String string, TransformImg transformimg) {
        super(string);
        this.transformimg = transformimg;
    }

    public void create() {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setMinimumSize(new Dimension(transformimg.getXMax()*2+200, transformimg.getYMax()*2+100));
        
        JPanel panel = new JPanel();
        BufferedImage img = null;
        
        this.add(panel);
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        
        try {
            img =  ImageIO.read(transformimg.getImgRe());
        } catch (IOException ex) {
            Logger.getLogger(FourierEditor.class.getName()).log(Level.SEVERE, "can't read ImgRe", ex);
        }
        
        JLabel labRE = new JLabel(new ImageIcon(img));
        
        labRE.setText("Re");
        labRE.setVerticalTextPosition(SwingConstants.BOTTOM);
        labRE.setHorizontalTextPosition(SwingConstants.CENTER);
        
        c.weightx = 1;
        
        panel.add(labRE, c);
        
        try {
            img =  ImageIO.read(transformimg.getImgIm());
        } catch (IOException ex) {
            Logger.getLogger(FourierEditor.class.getName()).log(Level.SEVERE, "can't read ImgIm", ex);
        }
        
        JLabel labIM = new JLabel(new ImageIcon(img));
        
        labIM.setText("Im");
        labIM.setVerticalTextPosition(SwingConstants.BOTTOM);
        labIM.setHorizontalTextPosition(SwingConstants.CENTER);
        
        c.gridx = 1;
        c.weightx = 1;
        
        panel.add(labIM, c);
        
        try {
            img =  ImageIO.read(transformimg.getImgFobr());
        } catch (IOException ex) {
            Logger.getLogger(FourierEditor.class.getName()).log(Level.SEVERE, "can't read ImgIm", ex);
        }
        
        imgReverse = new ImageIcon(img);
        JLabel labFobr = new JLabel(imgReverse);
        
        labFobr.setVerticalTextPosition(SwingConstants.BOTTOM);
        labFobr.setHorizontalTextPosition(SwingConstants.CENTER);
        labFobr.setText("reverse");
        
        c.gridx = 2;
        
        panel.add(labFobr, c);
        
        try {
            img =  ImageIO.read(transformimg.getImgFA());
         } catch (IOException ex) {
            Logger.getLogger(FourierEditor.class.getName()).log(Level.SEVERE, "can't read ImgFA", ex);
         }
        
        JLabel labSA = new JLabel(new ImageIcon(img));
        
        labSA.setVerticalTextPosition(SwingConstants.BOTTOM);
        labSA.setHorizontalTextPosition(SwingConstants.CENTER);
        labSA.setText("amplitude spectrum");
        
        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 1;
        
        panel.add(labSA, c);
        
        try {
            img =  ImageIO.read(transformimg.getImgFF());
         } catch (IOException ex) {
            Logger.getLogger(FourierEditor.class.getName()).log(Level.SEVERE, "can't read ImgFF", ex);
         }
        
        JLabel labSF = new JLabel(new ImageIcon(img));
        
        labSF.setVerticalTextPosition(SwingConstants.BOTTOM);
        labSF.setHorizontalTextPosition(SwingConstants.CENTER);
        labSF.setText("phase spectrum");
        
        c.gridx = 1;
        
        panel.add(labSF, c);
        
        JPanel panelBs = new JPanel();
        JButton addupB = new JButton("add up image...");
        JButton convolB = new JButton("convolution");
        
        addupB.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                int ret = fileChooser.showDialog( null, "Open a File" );
                if ( ret == JFileChooser.APPROVE_OPTION ) {
                    addupF = fileChooser.getSelectedFile();
                    String filename = addupF.getName();
                    if (filename.endsWith(".png") || filename.endsWith(".jpg")) {
                        addupTrImg = new TransformImg(addupF);
                        addupTrImg.transform();
                        //TransformImg sum = DFTTransformer.addup(transformimg, addupTrImg, 450);
                        DFTTransformer.deshif(transformimg, addupTrImg);
                    }
                }
            }  
        });
        convolB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                int ret = fileChooser.showDialog( null, "Open a File" );
                if ( ret == JFileChooser.APPROVE_OPTION ) {
                    convolF = fileChooser.getSelectedFile();
                    String filename = convolF.getName();
                    if (filename.endsWith(".png") || filename.endsWith(".jpg")) {
                        convolTrImg = new TransformImg(convolF);
                        convolTrImg.transform();
                        DFTTransformer.convolution(transformimg, convolTrImg, 450);
                    }
                }
            }  
        });
        panelBs.setLayout(new BoxLayout(panelBs, BoxLayout.Y_AXIS));
        panelBs.add(addupB);
        panelBs.add(convolB);
        
        c.gridx = 2;
        c.gridy = 1;
        panel.add(panelBs, c);
        
        this.setVisible(true);
    }
}
