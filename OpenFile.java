package fouriertransform;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class OpenFile extends JFrame    {
    private JPanel basePanel = new JPanel();
    private JFileChooser fileChooser = new JFileChooser();
    private JButton openFileButton = new JButton("Open a File...");
    private JButton fourierTransformButton = new JButton("Fourier Transform");
    private JLabel infoLabel = new JLabel("Choose .png or .jpg file...");
    private JProgressBar progressBar = new JProgressBar();
    private File file;

    OpenFile( String fourierTransform ) {
        super(fourierTransform);
    }
    
    public void create() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize( new Dimension(250, 100) );
        this.setResizable(false);
        
        progressBar.setIndeterminate(true);
        fourierTransformButton.setEnabled(false);
        
        basePanel.setLayout( new GridBagLayout() );
        this.add(basePanel);
        
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        basePanel.add( openFileButton, c );
        
        c.gridx = 1;
        c.gridy = 0;
        basePanel.add( fourierTransformButton, c );
        
        openFileButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e ) {
                int ret = fileChooser.showDialog( null, "Open a File" );
                if ( ret == JFileChooser.APPROVE_OPTION ) {
                    file = fileChooser.getSelectedFile();
                    String filename = file.getName();
                    infoLabel.setText(filename);
                    if (filename.endsWith(".png") || filename.endsWith(".jpg")) {
                        fourierTransformButton.setEnabled(true);
                    }
                }
            }  
        });
        fourierTransformButton.addActionListener( new ActionListener() {
            JFrame openFile;
            @Override
            public void actionPerformed(ActionEvent e) {
                setSize( new Dimension(250, 150) );
                c.gridx = 0;
                c.gridy = 2;
                
                basePanel.add( progressBar, c );
                
                Thread threadDFT = new Thread(new Runnable()    {
                	public void run()   {
                            fourierTransformButton.setEnabled(false);
                            openFileButton.setEnabled(false);
                            TransformImg transformimg = new TransformImg(file);
                            transformimg.transform();
                            
                            basePanel.remove(progressBar);
                            setSize( new Dimension(250, 100) );
                            fourierTransformButton.setEnabled(true);
                            openFileButton.setEnabled(true);
                            setVisible(false);
                            
                            FourierEditor fourEd = new FourierEditor("FourierEditor", transformimg);
                            fourEd.create();
                            
                            fourEd.addWindowListener(new java.awt.event.WindowAdapter() {
                                @Override
                                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                                    setVisible(true);
                                }
                            });
			}
		});
                threadDFT.setPriority(10);
		threadDFT.start();
            }  
        });
        
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = 1;
        c.ipady = 25;
        basePanel.add( infoLabel, c );
        
        this.setVisible(true);
    }
}