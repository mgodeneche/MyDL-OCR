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
 * @author Azrael && Wolran
 *
 */

public class Main {

	private static Boolean firstLaunch = true;
	private static Boolean downloadsAreFinished = true;
	private static List<Boolean> resultOcr;
	
	public static void main(String[] args) throws InterruptedException {
		resultOcr = OCRRoutine();
		while (true){
			Dispatcher();
		}
	}
	
	private static void Dispatcher() throws InterruptedException{

		List<Integer> segmentsContainingDownloads = analyzeResultOcr(resultOcr);
		if(!downloadsAreFinished){
			OCRActif(segmentsContainingDownloads);
			Thread.sleep(1000);
		}else{
			System.out.print("No downloads detected \r");
			Thread.sleep(5000);
		}
	}
	
	private static List<Integer> analyzeResultOcr(List<Boolean> resultOcr){
		int compteur = 0;
		List<Integer> segmentsContainingDownloads = new ArrayList<Integer>();
		for (Boolean containsDownload : resultOcr){
			if (containsDownload){
				segmentsContainingDownloads.add(compteur);
			}
			compteur++;
		}
		if (!segmentsContainingDownloads.isEmpty()){
			downloadsAreFinished = false;
		}else{
			downloadsAreFinished = true;
		}
		return segmentsContainingDownloads;
	}
	
	
	private static List<Boolean> OCRRoutine(){
		ScreenPrint printer = new ScreenPrint();
		BufferedImage capture = printer.printRoutine();
		ImageConverter converter = new ImageConverter();
		List<Boolean> resultRoutine = new ArrayList<Boolean>();
		ArrayList<BufferedImage> imageList = converter.segmentation(capture, null);
		for (BufferedImage img : imageList){
			printer.saveImage(Utils.generateFileUrl(), img);
			resultRoutine.add(converter.processImagePassif(img));
		}	
		return resultRoutine;
	}
	
	private static void OCRActif(List<Integer> listSegment){
		ScreenPrint printer = new ScreenPrint();
		BufferedImage capture = printer.printRoutine();
		ImageConverter converter = new ImageConverter();
		ArrayList<BufferedImage> imageList = converter.segmentation(capture, listSegment);
		for (BufferedImage img : imageList){
			printer.saveImage(Utils.generateFileUrl(), img);
			Double percent = converter.processImageActif(img);
			if (percent.equals(Integer.valueOf(100)) || percent.equals(Integer.valueOf(0))){
				downloadsAreFinished = true;
			}
		}	
	}

}