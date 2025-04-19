package projet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

/**
 * Methode qui permet de récupérer les metadonnées de fichier odt
 * @author lauri
 *
 */

public class FiltreRepertoire {
/**
 * filtre qui permet d'afficher les métadonnées des fichers odt présent dans un repertoire 
 * @see TestTypeMime#TestMimeType(mimetype)
 * @see meta#recupMeta(String)
 * @see UnzipOdtFile#unzipfile(ZipFilePath, DestDir)
 * @param repertoire dans lequel le filtre va s'excécuter
 * @throws IOException Si une erreur se déclare lors de l'entrée ou de la sortie
 * @throws SAXException Si il y un problème dans la lecture du fichier XML
 * @throws ParserConfigurationException indique une erreur de configuration
 * @throws TransformerException Si une erreur se déclare lors de la transformation
 */


	    public static void listeRepertoire(File repertoire) throws IOException, SAXException, ParserConfigurationException, TransformerException {
	    	String fields=System.getProperty("users.home")+"/folder_XML";
	    	try {
	        if (repertoire.isDirectory()) {
	            File[] list = repertoire.listFiles();
	            
	            
	            if (list != null) {
	                for (int i = 0; i <list.length; i++) {
	                	String path=list[i].getAbsolutePath();
	                	UnzipOdtFile.unzipfile( path,fields);             	 
	             
	                	 if(new File(fields+"/mimeType").exists()&&TestTypeMime.TestMimeType(fields+"/mimeType")) {
	                		 meta.recupMeta(fields+"/meta.xml");
	                		 System.out.println("\n");
	                		 File[] liste = (new File(fields )).listFiles();                		
	                		 for(int j=0;j<liste.length;j++) {		 
	                			 liste[j].delete();
	                		 }
	                	
	                	}                                	 
	                    listeRepertoire(list[i]);
	                }
	            }
	           CLI.SuprFolder(new File(fields));
	            
	        }
	        }catch(FileNotFoundException e){
	        	 System.err.println(repertoire + " : inconnu ou n'esxiste pas ");
	        	 
	        	
	        }
	        	
	        
	            
	    }
	 
	    	
	    }

