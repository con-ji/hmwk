package calhacks.hwio;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import calhacks.hwio.extraction.ExtractBoxes;
import calhacks.hwio.extraction.ExtractText;
import calhacks.hwio.watson.GetDocuments;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import net.sourceforge.tess4j.TesseractException;
/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
    
    
//    public void testImage() throws TesseractException, IOException, InterruptedException{
//    	BufferedImage img;
//    	String fileIndex = "src/main/java/calhacks/hwio/assets/" + 1 + ".png";
//        try {
//			img = ImageIO.read(new File(fileIndex));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			img = null;
//		}
//        BufferedImage bi = ExtractBoxes.extract(img);
//        
////        File f = new File("saved.jpg");
////        ImageIO.write(bi, "jpg", f);
//        
//        String imageText = ExtractText.extract(img);
//        //GetDocuments.queryGoogle(GetDocuments.getDocuments(imageText));
//        ArrayList<String> returns = GetDocuments.queryBing(GetDocuments.getDocuments(imageText));
//    }
    
//    public void testGetDocs(){
//    	GetDocuments.getDocuments(inputKey);
//    }
}
