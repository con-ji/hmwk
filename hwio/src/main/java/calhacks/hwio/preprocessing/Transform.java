package calhacks.hwio.preprocessing;
import java.util.Arrays;
import static java.lang.Math.sqrt;
import static java.lang.Math.max;

import org.opencv.imgproc.Imgproc;
import org.opencv.core.*;

public class Transform {
	
	public static double[][] orderPoints(double[][] points)
	{
		double[][] rect = new double[][]{
			{0, 0},
			{0, 0},
			{0, 0},
			{0, 0}
		};
		
		//top left and bottom right
		double min = 99999, max = 0;
		for (int i = 0; i<points.length; i++){
			//System.out.println(points[i][0] + points[i][1]);
			double sum = points[i][0] + points[i][1];
			if (sum > max){
				max = sum;
				rect[2][0] = points[i][0];
				rect[2][1] = points[i][1];
			}
			if (sum < min){
				min = sum;
				rect[0][0] = points[i][0];
				rect[0][1] = points[i][1];
			}
		}
		//top right and bottom left
		double diffMin = 99999, diffMax = 0;
		for (int i = 0; i<points.length; i++){
			//System.out.println(points[i][0] + points[i][1]);
			double diff = points[i][0] - points[i][1];
			if (diff > diffMax){
				diffMax = diff;
				rect[1][0] = points[i][0];
				rect[1][1] = points[i][1];
			}
			if (diff < diffMin){
				diffMin = diff;
				rect[3][0] = points[i][0];
				rect[3][1] = points[i][1];
			}
		}
		System.out.println(Arrays.deepToString(rect));
		return rect;
	}
	
	
	public static void fourPointTransform(double[][] points)
	{
		double[][] rect = orderPoints(points);
		double[] topLeft = new double[2];
		double[] topRight = new double[2];
		double[] bottomRight = new double[2];
		double[] bottomLeft = new double[2];
		topLeft[0] = rect[0][0];
		topLeft[1] = rect[0][1];
		topRight[0] = rect[1][0];
		topRight[1] = rect[1][1];
		bottomRight[0] = rect[2][0];
		bottomRight[1] = rect[2][1];
		bottomLeft[0] = rect[3][0];
		bottomLeft[1] = rect[3][1];
		
		//max width calc
		double bx = bottomRight[0] - bottomLeft[0];
		double by = bottomRight[1] - bottomLeft[1];
		double tx = topRight[0] - topLeft[0];
		double ty = topRight[1] - topLeft[1];
		double widthA = sqrt((bx * bx) + (by * by));
		double widthB = sqrt((tx * tx) + (ty * ty));
		double maxWidth = max(widthA, widthB);
		System.out.println(widthA);
		System.out.println(widthB);
		
		
		//max height calc
		double lx = topLeft[0] - bottomLeft[0];
		double ly = topLeft[1] - bottomLeft[1];
		double rx = topRight[0] - bottomRight[0];
		double ry = topRight[1] - bottomRight[1];
		double heightA = sqrt((lx * lx) + (ly * ly));
		double heightB = sqrt((rx * rx) + (ry * ry));
		System.out.println(heightA);
		System.out.println(heightB);
		double maxHeight = max(heightA, heightB);

		int row = 0, col = 0;
		//Mat original = new Mat(4, 2, CvType.CV_64FC1);		
		//Mat transform = new Mat(4, 2, CvType.CV_32FC2);
	    Mat src_mat = new Mat();
		//Point p1 = new Point[510.0, 515.0]
				//, p2 = new Point[2626.0, 573.0], p3 = new Point[469.0, 3349.0], p4 = new Point[2683.0, 3326.0];
		//transform.put(row ,col, 0.0, 0.0, maxWidth, 0.0, maxWidth, maxHeight, 0.0, maxHeight);
		//Mat M = Imgproc.getPerspectiveTransform(original, transform);
	    //Imgproc.warpPerspective(original, transform, M, new Size(maxWidth,maxHeight));

	    //System.out.println(transform.dump());
	}
	
}

