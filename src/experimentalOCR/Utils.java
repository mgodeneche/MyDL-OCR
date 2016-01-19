package experimentalOCR;

public class Utils {

	private static String URL_LOCAL = "C:/test/";
	private static String EXTENSION = ".png";
	
	/**
	 * Génère un nom de fichier unique avec le timestamp comme nom
	 * @return
	 */
	public static String generateFileUrl(){
		Long timeNow = System.currentTimeMillis();
		StringBuilder builder = new StringBuilder();
		builder.append(URL_LOCAL);
		builder.append(timeNow.toString());
		builder.append(EXTENSION);
		return builder.toString();
		
	}
}