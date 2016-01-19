/**
 * 
 */
package experimentalOCR;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * @author Azrael
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String fileName;
		ScreenPrint printer = new ScreenPrint();
		BufferedImage capture = printer.printRoutine();
		ImageConverter converter = new ImageConverter();
		
		ArrayList<BufferedImage> imageList = converter.segmentation(capture);
		IntStream.range(0, imageList.size()).forEach(
				
				nbr->printer.saveImage(Utils.generateFileUrl(), imageList.get(nbr))
				);
		
		
		
		
		
	}

}