package projet;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipOutputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;	
/**
 * classe principale
 * @author lauri
 *
 */

public class CLI{ 

	static String fold=System.getProperty("user.home")+"/folder_XML";
	/**
	 * methode main
	 * @param args contient les commandes rentrées sur la ligne de commande
	 * @throws IOException Si une erreur se déclare lors de l'entrée ou de la sortie
	 * @throws ParserConfigurationException si une erreur se déclare lors de la configuration
	 * @throws SAXException Si il y un problème dans la lecture du fichier XML
	 * @throws TransformerException  Si une erreur se déclare lors de la transformation
	 * @see UnzipOdtFile#unzipfile(ZipFilePath, destDir)
	 * @see meta#recupMeta(String)
	 * @see ZipFile Permet de zipper un fichier
	 * @see meta#ModifierSubject(String, String) Methode qui modifie le sujet d'un fichier
	 * @see meta#ModifierTitle(String, String) Methode qui modifie le titre d'un fichier
	 * @see meta#AjouterTitle(String, String) Methode qui permet d'ajouter un titre
	 * @see meta#AjoutSujet(String, String) Methode qui permet d'ajouter un sujet
	 * 
	 */

	public static void main( String[] args ) throws IOException, ParserConfigurationException, SAXException, TransformerException {  
		
		try{
			
		File folder_XML= new File(System.getProperty("user.home")+"/folder_XML");
		
		if(!folder_XML.exists()){
			folder_XML.mkdir();
		}
		if(args.length==0) {
			System.out.println("aucune commande rentrée, si besoin d'aide rentrer la commande -h exemple:java -jar cli.jar -h");
			
		}else if(args[0].equalsIgnoreCase("-f")) {
			if(args[1].length()!=0) {
				UnzipOdtFile.unzipfile(args[1],fold);             	 
	            if(new File(fold+"/mimeType").exists()&&TestTypeMime.TestMimeType(fold+"/mimeType")) {
	            	meta.recupMeta(fold +"/meta.xml");
	            	
	            }
	            SuprFolder(new File(fold));
			}
				
			
	}else if(args[0].equalsIgnoreCase("-d")) {

			FiltreRepertoire.listeRepertoire(new File(args[1]));
			
	}else if(args[0].equalsIgnoreCase("-h")) {
			
			
			System.out.println("\nafficher les metadonnées des dossiers opendocument d'un repertoire:\n-d Nom_Du_Repertoire\n");
			System.out.println("afficher les metadonnées d'un fichier:\n-f Nom_Du_Fichier.odt\n");
			System.out.println("modifier le sujet d'un fichier:\nNom_Du_Fichier.odt -NewSubject sujet\n");
			System.out.println("modifier le titre d'un fichier:\n Nom_Du_Fichier.odt  -NewTitle titre\n");
			System.out.println("ajouter un titre à un fichier:\nNom_Du_Fichier.odt -title\n");
			System.out.println("ajouter un sujet à un fichier:\nNom_Du_Fichier.odt -subject\n");
			System.out.println("exemple: java -jar cli.jar fichier.odt -title Titre");
	
	}else if(args[1].equalsIgnoreCase("-NewSubject")) {
		UnzipOdtFile.unzipfile( args[0],System.getProperty("user.home")+"/folder_XML");
		meta.ModifierSubject(System.getProperty("user.home")+"/folder_XML/meta.xml",args[2]);
		meta.recupMeta(System.getProperty("user.home")+"/folder_XML/meta.xml");
		new File (args[0]).delete();
		
		ZipOutputStream zip =new ZipOutputStream(new FileOutputStream(new File(args[0])));
		ZipFile.zip("",new File("C:\\Users\\lauri\\folder_XML") ,zip);
		zip.close();
		SuprFolder(new File(fold));
		
		
	}else if(args[1].equalsIgnoreCase("-NewTitle")) {
		UnzipOdtFile.unzipfile( args[0],System.getProperty("user.home")+"/folder_XML");
		meta.ModifierTitle(System.getProperty("user.home")+"/folder_XML/meta.xml",args[2]);
		meta.recupMeta(System.getProperty("user.home")+"/folder_XML/meta.xml");
		new File (args[0]).delete();
		ZipOutputStream zip =new ZipOutputStream(new FileOutputStream(new File(args[0])));
		ZipFile.zip("",new File("C:\\Users\\lauri\\folder_XML") ,zip);
		zip.close();
		SuprFolder(new File(fold));
		
	}else if(args[1].equalsIgnoreCase("-title")) {
		UnzipOdtFile.unzipfile( args[0],System.getProperty("user.home")+"/folder_XML");
		meta.AjouterTitle(System.getProperty("user.home")+"/folder_XML/meta.xml", args[2]);
		meta.recupMeta(System.getProperty("user.home")+"/folder_XML/meta.xml");
		new File (args[0]).delete();
		ZipOutputStream zip =new ZipOutputStream(new FileOutputStream(new File(args[0])));
		ZipFile.zip("",new File("C:\\Users\\lauri\\folder_XML") ,zip);
		zip.close();
		SuprFolder(new File(fold));
	}else if(args[1].equalsIgnoreCase("-subject")) {
		UnzipOdtFile.unzipfile( args[0],System.getProperty("user.home")+"/folder_XML");
		meta.AjoutSujet(System.getProperty("user.home")+"/folder_XML/meta.xml", args[2]);
		meta.recupMeta(System.getProperty("user.home")+"/folder_XML/meta.xml");
		new File (args[0]).delete();
		ZipOutputStream zip =new ZipOutputStream(new FileOutputStream(new File(args[0])));
		ZipFile.zip("",new File("C:\\Users\\lauri\\folder_XML") ,zip);
		zip.close();
		SuprFolder(new File(fold));
	}
		
	
	else {
		System.out.println("entrer une commande correct(utiliser la commande -h si besoin exemple: java -jar cli.jar -h)");
	}
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("entrer l'information manquante, utiliser la commande -h si besoin");
		}	
		}
	/**
	 * permet de supprimer les fichier d'un repertoire 
	 */
		public static void SuprFolder(File rep) {
			File[] liste = rep.listFiles(); 
   		 	 if(rep.isDirectory()) {
			for(int j=0;j<liste.length;j++) {		 
   			 liste[j].delete();
   			SuprFolder(liste[j]);
   			rep.delete();
   			 }
   		 		
   		 	}	 
   		 }
   		 

}

