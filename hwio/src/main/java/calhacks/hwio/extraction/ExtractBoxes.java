package calhacks.hwio.extraction;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.DataBufferByte;
import java.awt.image.Kernel;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class ExtractBoxes {
	private static int[] convolveSize = {5, 5};
	private static final float GRADIENT = (float) 1.0;
	
	public static BufferedImage extract(BufferedImage image) throws IOException, InterruptedException{
		int convTotLength = convolveSize[0] * convolveSize[1];
		float[] convVals = {1f, 4f, 7f, 4f, 1f, 4f, 16f, 26f, 16f, 4f, 7f, 26f, 41f, 26f, 7f, 4f, 16f, 26f, 16f, 4f, 1f, 4f, 7f, 4f, 1f};
		for (int i = 0; i < convVals.length; i++ ){
			convVals[i] = convVals[i]/ (2 * 273);
		}
		ConvolveOp conv = new ConvolveOp(new Kernel(convolveSize[0], convolveSize[1], convVals));
		BufferedImage convImage = conv.createCompatibleDestImage(image, null);
		convImage = conv.filter(image, convImage);
		
		RescaleOp rescale = new RescaleOp(1.0f, -100f, null);
		convImage = rescale.filter(convImage, null);
		RescaleOp rescale2 = new RescaleOp(5.0f, 0f, null);
		convImage = rescale2.filter(convImage, null);
				
		System.out.println("Blur convolution done");		
		return ExtractBoxes.isolateText(convImage, image);
	}
	
	public static float distanceBetween(int[] src, int[] tar) {
		float[] a2 = new float[3];
		for (int i = 0; i < 3; i++){a2[i] = (src[i] - tar[i]) * (src[i] - tar[i]);}
		return (float) Math.sqrt(a2[0] + a2[1] + a2[2]);
	}
	public static void changePixel(boolean[][] edges, int xInd, int yInd, int maxXInd, int maxYInd){
		if (xInd < 0 || xInd > maxXInd || yInd < 0 || yInd > maxYInd){
			return;
		}
		/*
		if (edges[xInd][yInd] == false){
			return;
		}else{
			edges[xInd][yInd] = false;
			changePixel(edges, xInd - 1, yInd, maxXInd, maxYInd);
			changePixel(edges, xInd + 1, yInd, maxXInd, maxYInd);
			changePixel(edges, xInd, yInd - 1, maxXInd, maxYInd);
			changePixel(edges, xInd, yInd + 1, maxXInd, maxYInd);
		}
		*/
		edges[xInd][yInd] = false;
		if (xInd - 1 > 0 && edges[xInd - 1][yInd]) {
			changePixel(edges, xInd - 1, yInd, maxXInd, maxYInd);
		}
		if (xInd + 1 < maxXInd && edges[xInd + 1][yInd]) {
			changePixel(edges, xInd + 1, yInd, maxXInd, maxYInd);
		}
		if (yInd - 1 > 0 && edges[xInd][yInd - 1]) {
			changePixel(edges, xInd, yInd - 1, maxXInd, maxYInd);
		}
		if (yInd + 1 < maxYInd && edges[xInd][yInd + 1]) {
			changePixel(edges, xInd, yInd + 1, maxXInd, maxYInd);
		}

		
	}
	
	public static BufferedImage isolateText(BufferedImage image, BufferedImage origImage) {
		//int[] conSize = {3, 3};
		//float[] convVals = {0f, -0.5f, 0f, -0.5f, 0, 0.5f, 0, 0.5f, 0};
		//ConvolveOp conv = new ConvolveOp(new Kernel(conSize[0], conSize[1], convVals));
		//BufferedImage convImage = conv.createCompatibleDestImage(image, null);
		
		int[][][] imgColors = new int[3][image.getWidth()][image.getHeight()];
//		boolean[][] edge = new boolean[convImage.getWidth()][convImage.getHeight()];
		
		System.out.println("Gradient convolution");
		
		for (int i = 0; i < image.getWidth(); i++) {
			for (int j = 0; j < image.getHeight(); j++) {
				Color color = new Color(image.getRGB(i, j));
				imgColors[0][i][j] = color.getRed();
				imgColors[1][i][j] = color.getBlue();
				imgColors[2][i][j] = color.getGreen();
				
//				byte[] origin = {0, 0, 0};
//				
//				edge[i][j] = distanceBetween(origin, rgb) < GRADIENT;
			}
		}
		float[][] gradients = CannyEdgeDetection.getGradients(imgColors, image.getWidth(), image.getHeight());
		boolean[][] edge = CannyEdgeDetection.getInverseGradientMap(gradients, 3.0f);
				
		changePixel(edge, 20, 20, image.getWidth() - 1, image.getHeight() - 1);
		
		for (int i = 0; i < image.getWidth(); i++){
			for (int j = 0; j < image.getHeight(); j++){
				if (!edge[i][j]){
					origImage.setRGB(i, j, Color.WHITE.getRGB());
				}
			}
		}
		
		return origImage;
	}
	
	
}
