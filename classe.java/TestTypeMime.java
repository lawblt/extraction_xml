
public class TestTypeMime{
	/**
	 *Test le mimetype d'un fichier odt
	 * @param mimeType Fichier mimetype que l'on récupère lors du dezipage du fichier odt
	 * @return true Si le fichier possède un mymetype opendocument sinon elle retounera false
	 * @throws IOException Si une erreur se déclare lors de l'entrée ou de la sortie
	 */
	
  public static boolean TestMimeType(String mimeType) throws IOException {
	File doc = new File(mimeType);

	  try (BufferedReader buffer = new BufferedReader(new FileReader(doc))) {
		String strng=buffer.readLine();
		if(strng.contains("opendocument")) {
			return true;
		}
		else{
			System.err.println("le document n'a pas le bon format");
			return false;
		}
		  			
	  }
  }	  
	  

  public static String TestOdf(String mimeType) throws IOException {
		File doc = new File(mimeType);

		  try (BufferedReader buffer = new BufferedReader(new FileReader(doc))) {
			String strng=buffer.readLine();
			if(strng.contains("opendocument.text")) {
				return "ODT";
			}else if(strng.contains("presentation")) {
				return "ODP";
			}else if(strng.contains("spreadsheets")) {
					return"ODS";
			} else if(strng.contains("graphic")) {
				return "ODG";
			}else {
				return("le format est inconnu");
				}
			}
		  }
		  

