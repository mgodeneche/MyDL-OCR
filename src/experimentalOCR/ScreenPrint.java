package experimentalOCR;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScreenPrint {
	
	private int resizeRatio;
	

	public ScreenPrint() {
		super();
		this.resizeRatio = 4;
	}
	
	
	/**
	 * Lance une capture d'écran
	 * @param outputURL
	 * @return 
	 */
	protected  BufferedImage newPrint(){
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage capture = null;
		try {
			capture = new Robot().createScreenCapture(screenRect);
		} catch (AWTException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return capture;
	}
	/**
	 * Sauvegarde l'image dans le disque
	 * @param outputURL
	 * @param capture
	 */
	protected  void saveImage(String outputURL, BufferedImage capture) {
		try {
			ImageIO.write(capture, "png", new File(outputURL));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * On considère que les barres ne sont pas sur les bords des écrans, on enlève 1/5eme de chaque côté
	 * @return
	 */
	protected  Rectangle generatePrintWorkspace(){
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int newWidth = (int)(dim.getWidth()-(dim.getWidth()/resizeRatio));
		int newHeight= (int)(dim.getHeight()-(dim.getHeight()/resizeRatio));
		dim.setSize(newWidth, newHeight);
		return new Rectangle(dim) ;
	}

	
	/**
	 * Lance la routine de capture d'écran et de resize
	 * @return
	 */
	protected  BufferedImage printRoutine(){
		BufferedImage capture = newPrint();
		ImageConverter converter = new ImageConverter();
		Rectangle rect = generatePrintWorkspace();
		capture = converter.cropImage(capture,rect);
		return capture;
	}
	
	
	
	
	
}