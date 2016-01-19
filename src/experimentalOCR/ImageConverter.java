package experimentalOCR;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.IntStream;

public class ImageConverter {

	
	private int segmentationNumber;
	
	public ImageConverter() {
		super();
		
		this.segmentationNumber = 3;
		// TODO Auto-generated constructor stub
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
	
	
	
	public ArrayList<BufferedImage> segmentation(BufferedImage capture){
		ArrayList<BufferedImage> output = new ArrayList<BufferedImage>();
		
		int rectHeight = (int)capture.getHeight()/segmentationNumber;
		Rectangle rect = new Rectangle((int)capture.getWidth(),rectHeight);
		IntStream.range(0, segmentationNumber).forEach(
				nbr-> output.add(capture.getSubimage(0,nbr*rectHeight, rect.width, rect.height))
				);
		return output;
		
	}
}