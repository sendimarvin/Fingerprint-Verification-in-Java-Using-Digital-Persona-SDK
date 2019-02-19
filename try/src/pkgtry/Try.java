/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgtry;


import com.digitalpersona.uareu.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author link
 */

public class Try {
    private static ReaderCollection m_collection;
    private static Reader           m_reader;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Fmd[] m_fmds = new Fmd[2];
//        m_reader = Selection.Select(m_collection);
        //acquire available readers
        
//            m_collection.isEmpty();
        try {
            m_collection = UareUGlobal.GetReaderCollection();
            m_collection.GetReaders();
        } catch (UareUException e1) {
                // TODO Auto-generated catch block
    //            JOptionPane.showMessageDialog(null, "Error getting collection");
                return;
        }
        System.out.println("Available Readers: " + m_collection.size());

        m_reader = m_collection.get(0);

        System.out.println("Reader: " + m_reader.GetDescription().name.toString());
        
    
    //extract features
        Engine engine = UareUGlobal.GetEngine();
    
        try {
            //image 1
            BufferedImage img = ImageIO.read(new File("C:\\xampp\\htdocs\\karibu-pms\\FingerprintApplication\\kpms\\pic\\0","finger4.png"));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write( img, "png", baos );
            baos.flush();
            byte[] imageInByte = baos.toByteArray();
            baos.close();
            
            //image 1
            BufferedImage img2 = ImageIO.read(new File("C:\\xampp\\htdocs\\karibu-pms\\FingerprintApplication\\kpms\\pic\\0","finger3.png"));
            ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
            ImageIO.write( img2, "png", baos2 );
            baos2.flush();
            byte[] imageInByte2 = baos2.toByteArray();
            baos2.close();
            
            
            System.out.println("Byte Array1: " + imageInByte.length);
            System.out.println("Byte Array2: " + imageInByte2.length);
            
            
            Fmd fmd =UareUGlobal.GetEngine().CreateFmd(
                toBytes(img),
                img.getWidth(),
                img.getHeight(),
                500, 0, 3407615, Fmd.Format.ISO_19794_2_2005
            );
            
            
            Fmd fmd2=UareUGlobal.GetEngine().CreateFmd(
                toBytes(img2),
                img.getWidth(),
                img.getHeight(),
                500, 0, 3407615, Fmd.Format.ISO_19794_2_2005
            );
            
            m_fmds[0] = fmd;
            m_fmds[1] = fmd2;
            
        } catch (IOException ex) {
            Logger.getLogger(Try.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UareUException ex) {
            Logger.getLogger(Try.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            int falsematch_rate = engine.Compare(m_fmds[0], 0, m_fmds[1], 0);
            int target_falsematch_rate = Engine.PROBABILITY_ONE / 100000; //target rate is 0.00001
            if(falsematch_rate < target_falsematch_rate){
                    System.out.println("Fingerprints matched.\n");
                    String str = String.format("dissimilarity score: 0x%x.\n", falsematch_rate);
                    
                    str = String.format("false match rate: %e.\n\n\n", (double)(falsematch_rate / Engine.PROBABILITY_ONE));
            }
            else{
                    System.out.print("Fingerprints did not match.\n\n\n");
            }
            
        } catch (UareUException ex) {
//            Logger.getLogger(Try.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    public static byte[] toBytes(BufferedImage image) {
        WritableRaster raster = image.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();

        return (data.getData());
    }
    
}

