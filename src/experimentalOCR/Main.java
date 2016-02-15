/**
 * 
 */
package experimentalOCR;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Azrael
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		while(true){
			Dispatcher();
		}
	}
	
	private static void Dispatcher(){
		List<Boolean> resultOcr = OCRRoutine();
		for (Boolean segmentContainDownload : resultOcr) {
			System.out.print("Segment "
					+ resultOcr.indexOf(segmentContainDownload) + " : "
					+ segmentContainDownload.toString() + "\r");
			if (segmentContainDownload) {
				System.out.println("Trouvé !");
				OCRActif(resultOcr.indexOf(segmentContainDownload));
			}
		}
	}
	
	
	private static List<Boolean> OCRRoutine(){
		String fileName;
		ScreenPrint printer = new ScreenPrint();
		BufferedImage capture = printer.printRoutine();
		ImageConverter converter = new ImageConverter();
		Map<BufferedImage, Double> mapImageWithPercent = new HashMap<BufferedImage, Double>();
		List<Boolean> resultRoutine = new ArrayList<Boolean>();
		ArrayList<BufferedImage> imageList = converter.segmentation(capture, -1);
		for (BufferedImage img : imageList){
			printer.saveImage(Utils.generateFileUrl(), img);
			resultRoutine.add(converter.processImagePassif(img));
		}	
		return resultRoutine;
	}
	
	private static void OCRActif(int segmentIndex){
		String fileName;
		ScreenPrint printer = new ScreenPrint();
		BufferedImage capture = printer.printRoutine();
		ImageConverter converter = new ImageConverter();
		Map<BufferedImage, Double> mapImageWithPercent = new HashMap<BufferedImage, Double>();
		ArrayList<BufferedImage> imageList = converter.segmentation(capture, segmentIndex);
		for (BufferedImage img : imageList){
			printer.saveImage(Utils.generateFileUrl(), img);
			converter.processImageActif(img);
		}	
	}

}