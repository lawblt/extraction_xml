package projet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * classe regroupant les differentes methodes liées aux métadonnées
 * @author lauri
 */


public class meta {
	/**
	 * Recupère et affiche les métadonnées d'un fichier odt
	 * @param meta Fichier meta.xml du fichier opendocumment que l'on veut extraire  
	 * @throws SAXException Si il y un problème dans la lecture du fichier XML
	 * @throws ParserConfigurationException qui indique une erreur de configuration
	 * @throws TransformerException Si une erreur se déclare lors de la transformation
	 * @throws IOException Si une erreur se déclare lors de l'entré ou la sortie du programme 
	 * @throws NullPointerException Si le node appelé n'existe pas
	 * @see UnzipOdtFile#unzipfile(String, String)
	 * @return a qui liste toutes les metadonnees
	 */

	public static ArrayList<String> recupMeta(String meta) throws SAXException, ParserConfigurationException, TransformerException, IOException,NullPointerException {
		 ArrayList<String> a=new ArrayList<String>();
		try {
			File xmlDoc=new File(meta);
			DocumentBuilderFactory fact=DocumentBuilderFactory.newInstance();
			DocumentBuilder build=fact.newDocumentBuilder();
			Document doc=build.parse(xmlDoc);
	
			NodeList list= doc.getElementsByTagName("office:meta");
			for(int i=0;i<list.getLength();i++) {
				Node node=list.item(i);
				if(node.getNodeType()==Node.ELEMENT_NODE){
				Element element=(Element) node;
				
				 Node node1 = element.getElementsByTagName("dc:creator").item(0);
				 
	             if(node1!=null) {
	            	 String creator = node1.getTextContent();
	            	 System.out.printf("creator: %s%n", creator); 
	            	 a.add("creator: "+creator);
	             }
	             Node node2 = element.getElementsByTagName("dc:date").item(0);
	             
	                if(node2!=null) {
	                String date = node2.getTextContent();
	                System.out.printf("Date of the creation: %s%n", date);
	                a.add("date of creation: "+date);
	                }
	                Node node3 = element.getElementsByTagName("meta:keyword").item(0);
	                if(node3!=null) {                
	                	String key = node3.getTextContent();
	                	 System.out.printf("keyword: %s%n", key);
	                	  a.add("keyword: "+key);	                }
	                
	                Node node4 = element.getElementsByTagName("dc:subject").item(0);
	                if(node4!=null) {	                
	                	String subject = node4.getTextContent();
	                	  System.out.printf("subject: %s%n", subject);
	                	  a.add("subject: "+subject);
	                }
	                
	                
	                Node node5 = element.getElementsByTagName("dc:title").item(0);
	                if(node5!=null) {
	                String title = node5.getTextContent();
	               System.out.printf("title: %s%n", title);
	                a.add("title: "+ title);
	                }
		
				}
				
		}
				 
					
	          
			NodeList list2= doc.getElementsByTagName("meta:document-statistic");
			for(int i=0;i<list2.getLength();i++) {
				Node node=list2.item(i);
				if(node.getNodeType()==Node.ELEMENT_NODE){
					Element element=(Element) node;
				
					Node node1= element.getAttributeNode("meta:page-count");
					if(node1!=null) {
						String nbCount =node1.getTextContent();
						System.out.printf("page count:%s%n",nbCount);
						a.add("page count: "+ nbCount);
					}
               
					Node node2= element.getAttributeNode("meta:image-count");
					if(node2!=null) {
						String nbim =node2.getTextContent();
						System.out.printf("image count:%s%n",nbim);
						a.add("image count: "+nbim);
					}
					Node node3= element.getAttributeNode("meta:character-count");
					if(node3!=null) {
						String nbchar =node3.getTextContent();
						System.out.printf("character count:%s%n",nbchar);
						a.add("character count: "+nbchar);
					}
					Node node4= element.getAttributeNode("meta:word-count");
					if(node4!=null) {
						String nbw =node4.getTextContent();
						System.out.printf("word count:%s%n",nbw);
						a.add("word count: "+nbw);
					}
				}
			}
			
		
		}catch(FileNotFoundException n) {
			System.out.println("impossible de récupérer les métadonnées");; 
		}
		return a;
	
		
	}
	/**
	 * Modification de la métadonnée titre d'un fichier odt
	 * @param meta Fichier meta.xml du fichier opendocumment que l'on veut modifier 
	 * @param read Nouveau titre à ajouter
	 * @throws SAXException Si il y un problème dans la lecture du fichier XML
	 * @throws ParserConfigurationException qui indique une erreur de cofiguration
	 * @throws TransformerException Si une erreur se déclare lors de la transformation
	 * @throws IOException Si une erreur se déclare lors de l'entré ou la sortie du programme 
	 */
	
	public static void ModifierTitle(String meta,String read) throws IOException, TransformerException, ParserConfigurationException, SAXException {
		try {
			
				String title= read;
		       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		       DocumentBuilder db = dbf.newDocumentBuilder();
		       Document doc =db.parse(meta);
		       // Récupérer l'élément office meta
		       Node docStat = doc.getElementsByTagName("office:meta").item(0);
		       // Récupérer la liste des nœuds fils de office meta
		       NodeList list = docStat.getChildNodes();
		       for (int i = 0; i < list.getLength(); i++) {
		           Node node = list.item(i);
		           // Récupérer l'élément titre et modifier la valeur
		           if (node.getNodeName().equals("dc:title")) {
		              node.setTextContent(title);
		           }
		       }
		       // écrire le contenu dans un fichier xml
		       TransformerFactory tf = TransformerFactory.newInstance();
		       Transformer transformer = tf.newTransformer();
		       DOMSource src = new DOMSource(doc);
		       StreamResult res = new StreamResult(new File(meta));
		       transformer.transform(src, res);
		       //ZipFile.Zip();
		     } catch (FileNotFoundException e) {
		      System.out.println("impossible de modifier le titre");
		     }
	}
	/**
	 * Modification de la métadonnée sujet d'un fichier odt 
	 * @param meta Fichier meta.xml du fichier opendocumment que l'on veut modifier 
	 * @param read Nouveau sujet à ajouter
	 * @throws SAXException Si il y un problème dans la lecture du fichier XML
	 * @throws ParserConfigurationException qui indique une erreur de configuration
	 * @throws TransformerException Si une erreur se déclare lors de la transformation
	 * @throws IOException Si une erreur se déclare lors de l'entré ou la sortie du programme 
	 */

		
		      
	public static void ModifierSubject(String meta,String read) throws IOException, TransformerException, ParserConfigurationException, SAXException {

		 try {
			
				String subject= read;
		      
		       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		       DocumentBuilder db = dbf.newDocumentBuilder();
		       Document doc = db.parse(meta);
		       // Récupérer l'élément office meta
		       Node docStat = doc.getElementsByTagName("office:meta").item(0);
		       // Récupérer la liste des nœuds fils de office meta
		       NodeList list = docStat.getChildNodes();
		       for (int i = 0; i < list.getLength(); i++) {
		           Node node = list.item(i);
		           // Récupérer l'élément titre et modifier la valeur
		           if (node.getNodeName().equals("dc:subject")) {
		              node.setTextContent(subject);
		              
		           }
		           
		       }
		       // écrire le contenu dans un fichier xml
		       TransformerFactory tf = TransformerFactory.newInstance();
		       Transformer transformer = tf.newTransformer();
		       DOMSource src = new DOMSource(doc);
		       StreamResult res = new StreamResult(new File(meta));
		       transformer.transform(src, res);
		      
		       
		 }catch (FileNotFoundException e) {
		      System.out.println("impossible de modifier le sujet");
		     }
		     }
	/**
	 * Ajouter la métadonnée sujet à un fichier odt
	 * @param meta Fichier meta.xml du fichier opendocumment que l'on veut modifier 
	 * @param read Nouveau titre  à ajouter
	 * @throws SAXException Si une erreur se déclare  dans la lecture du fichier XML
	 * @throws ParserConfigurationException si une erreur se déclare lors de la configuration
	 * @throws TransformerException Si une erreur se déclare lors de la transformation
	 * @throws IOException Si une erreur se déclare lors de l'entré ou la sortie du programme 
	 */
	public static void AjoutSujet(String meta,String read) throws TransformerException, SAXException, IOException, ParserConfigurationException {	
		//try {
				
				String Newsubject= read;
		      
		       DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		       DocumentBuilder db = dbf.newDocumentBuilder();
		       Document doc = db.parse(meta);
		       // Récupérer l'élément office meta
		       Node noeud = doc.getElementsByTagName("office:meta").item(0);
		      
		       if(doc.getElementsByTagName("dc:subject").item(0)!=null) {
		    	   System.out.println("la métadonnée titre existe déjà, pour modifier voir commande -h\n");
		       }
		       Element sujet =(Element) doc.createElement("dc:subject");
				sujet.appendChild(doc.createTextNode(Newsubject));
				noeud.appendChild(sujet);
				
			
		       // écrire le contenu dans un fichier xml
		       TransformerFactory tf = TransformerFactory.newInstance();
		       Transformer transformer = tf.newTransformer();
		       DOMSource src = new DOMSource(doc);
		       StreamResult res = new StreamResult(new File(meta));
		       transformer.transform(src, res);
		
	
		
	}
	/**
	 * Ajouter la métadonnée titre à un fichier odt
	 * @param meta Fichier meta.xml du fichier opendocumment que l'on veut modifier 
	 * @param read Nouveau titre à ajouter 
	 * @throws SAXException Si il y un problème dans la lecture du fichier XML
	 * @throws ParserConfigurationException si une erreur se déclare lors de la configuration
	 * @throws TransformerException Si une erreur se déclare lors de la transformation
	 * @throws IOException Si une erreur se déclare lors de l'entré ou la sortie du programme 
	 */
public static void AjouterTitle(String meta,String read) throws ParserConfigurationException, SAXException, IOException, TransformerException {
		
		String NewTitle= read;
		
		DocumentBuilderFactory fact=DocumentBuilderFactory.newInstance();
		DocumentBuilder build=fact.newDocumentBuilder();
		Document doc=build.parse(new File(meta));
		NodeList list= doc.getElementsByTagName("office:meta");
		if(doc.getElementsByTagName("dc:title").item(0)!=null) {
	    	   System.out.println("la métadonnée titre existe déjà, pour modifier voir commande -h\n");
	       }

		Element titre =doc.createElement("dc:title");
		titre.appendChild(doc.createTextNode(NewTitle));
		list.item(0).appendChild(titre);
		TransformerFactory factory= TransformerFactory.newInstance();
		//écrire le contenu dans un fichier xml
	
		Transformer transformer = factory.newTransformer();
		DOMSource src = new DOMSource(doc);
		StreamResult res = new StreamResult(new File(meta));
		transformer.transform(src, res);	
		
}
	
		}

