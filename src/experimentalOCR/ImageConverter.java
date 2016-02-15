package experimentalOCR;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class ImageConverter {

	
	private int segmentationNumber;
	private static Color colorDefaultDownloadBar;
	private static int DownloadBarLength;
	
	public ImageConverter() {
		super();
		
		this.segmentationNumber = 3;
		this.colorDefaultDownloadBar = new Color(255,136,0,1);
		this.DownloadBarLength = 1000;
	}
	/**
	 * Permet de couper l'image afin de réduire la source à analyser
	 * @param src
	 * @param rect
	 * @return
	 */
	public  BufferedImage cropImage(BufferedImage capture,Rectangle rect) {
	      BufferedImage dest = capture.getSubimage(0, 0, rect.width, rect.height);
	      return dest; 
	   }
	
	
	
	public ArrayList<BufferedImage> segmentation(BufferedImage capture,int segmentIndex){
		ArrayList<BufferedImage> output = new ArrayList<BufferedImage>();
		
		int rectHeight = (int)capture.getHeight()/segmentationNumber;
		Rectangle rect = new Rectangle((int)capture.getWidth(),rectHeight);
		IntStream.range(0, segmentationNumber).forEach(
				nbr-> output.add(capture.getSubimage(0,nbr*rectHeight, rect.width, rect.height))
				);
		if(segmentIndex>=0){
			ArrayList<BufferedImage> targetedOutput = new ArrayList<BufferedImage>();
			targetedOutput.add(output.get(segmentIndex));
			return targetedOutput;
		}
		return output;
		
	}
	
	public Boolean processImagePassif(BufferedImage image){
		return (countColorAppearances(image) > 0);
	}
	
	public void processImageActif(BufferedImage image) {
		int countColor = countColorAppearances(image);
		double percent = countColor / 10;
		System.out.print("pourcentage : "+percent+" % \r");
	}
	
	public static int countColorAppearances(BufferedImage image){
		int width = image.getWidth();
		int height = image.getHeight();
		int[][] pixels = new int[height][width];
		int compteur = 0;
		for (int row = 0; row < height; row++) {
			for (int col = 0; col < width; col++) {
				pixels[row][col] = image.getRGB(col, row);
				Color pixelColor = new Color(pixels[row][col]);
				if (isEqualToColor(pixelColor, colorDefaultDownloadBar)){
					compteur ++;
				}
			}
			if (compteur != 0){
				return compteur;
			}
		}
		return 0;
	}
	
	private static Boolean isEqualToColor(Color pixelColor, Color colorToSearch){
		int redValue = pixelColor.getRed();
		int greenValue = pixelColor.getGreen();
		int blueValue = pixelColor.getBlue();
		
		int redValueToSearch = colorToSearch.getRed();
		int greenValueToSearch = colorToSearch.getGreen();
		int blueValueToSearch = colorToSearch.getBlue();
		
		if (redValue == redValueToSearch && greenValue == greenValueToSearch && blueValue == blueValueToSearch){
			return true;
		}else{
			return false;
		}	
	}
}