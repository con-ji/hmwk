package calhacks.hwio.extraction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ExtractText {
	public static String extract(BufferedImage img) throws TesseractException{	
		ITesseract instance = new Tesseract();
		instance.setDatapath("/usr/local/share/");
		return instance.doOCR(img);
	}
}
