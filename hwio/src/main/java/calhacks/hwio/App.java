package calhacks.hwio;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import javax.imageio.*;

import com.google.gson.Gson;

import calhacks.hwio.extraction.ExtractBoxes;
import calhacks.hwio.extraction.ExtractText;
import calhacks.hwio.watson.GetDocuments;
import net.sourceforge.tess4j.TesseractException;

public class App 
{
    public static void main( String[] args )
    {
        String fileIndex1 = "/../assets/" + args[0] + ".JPG";
        String fileIndex2 = "/../assets/" + args[0] + ".jpg";
        String fileIndex3 = "/../assets/" + args[0] + ".png";
        String returnString = "";
        BufferedImage img = null;
        try {
			img = ImageIO.read(new File(fileIndex1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
        try {
        	img = ImageIO.read(new File(fileIndex2));
        } catch (IOException e) {
        	
        }
        try {
        	img = ImageIO.read(new File(fileIndex3));
        	BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
    		newImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
    		img = newImage;
        } catch (IOException e) {
        	
        }
        
        BufferedImage extractedText;
        
        try {
			extractedText = ExtractBoxes.extract(img);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        String imageText = "";
		try {
			imageText = ExtractText.extract(img);
		} catch (TesseractException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        ArrayList<String> returns = new ArrayList<String>();
		try {
			returns = GetDocuments.queryBing(GetDocuments.getDocuments(imageText));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        FileWriter file;
        try {
			file = new FileWriter("/../targetAssets/" + args[0] + ".json");
			Gson gson = new Gson();
			file.write(gson.toJson(returns));
			file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			file = null;
		}
    }
}
