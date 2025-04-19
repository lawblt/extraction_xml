package projet;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.zip.ZipOutputStream;

public class GUI extends JFrame{
	
	private String fold;
	private ArrayList<String>hist;
	
	public GUI() throws ParserConfigurationException {
		super( "JMenuBar sample" );
	    this.fold =System.getProperty("user.home")+"/folder_XML";
	    hist=new ArrayList<String>();
	    this.setSize(600,400);
	    this.setResizable(false);
	    this.setLocationRelativeTo( null );
	    this.setDefaultCloseOperation( DISPOSE_ON_CLOSE );
	    this.setJMenuBar(this.createMenuBar());
	    styleAcc();   
		}
	
	
	private JMenuBar createMenuBar() throws ParserConfigurationException {
	   
		JMenuBar menu = new JMenuBar();
	   	JMenu file = new JMenu("Fichier");
	    JMenuItem prop= new JMenuItem("Propriété");
	    JMenuItem mod= new JMenuItem("Modification Titre");
	    JMenuItem modS= new JMenuItem("Modification Sujet");
	    prop.addActionListener( e -> {
				try {
					String name= JOptionPane.showInputDialog( this, "Veuillez entrer le nom d'un fichier(.ODF)" );
				    File nom =new File(name);
					mnuNewListenerFilePr(e,name,nom);
					}catch (IOException | SAXException | ParserConfigurationException| TransformerException e1) {
						
					}catch(NullPointerException e2 ){
							System.out.println("");
						}
			    } );
		file.add(prop);
	    file.addSeparator();
	    mod.addActionListener( e -> {
				try {
					String name= JOptionPane.showInputDialog( this, "Veuillez entrer le nom d'un fichier(.ODT)\nà modifier" );
					String title= JOptionPane.showInputDialog( this, "Quelle est le nouveau titre " );
					mnuNewListenerFileMdT(e,title,name);
				}catch (IOException | SAXException | ParserConfigurationException| TransformerException e1) {
	
				}catch(NullPointerException e2 ){
						System.out.println("");
				}
		     } );
	    file.add(mod);
	    file.addSeparator();
		modS.addActionListener( e -> {
				try {
					String name= JOptionPane.showInputDialog( this, "Veuillez entrer le nom d'un fichier(.ODT)\nà modifier" );
					String sujet= JOptionPane.showInputDialog( this, "Quelle est le nom du nouveau sujet" );
					mnuNewListenerFileMdS(e,sujet,name);
				}catch (IOException | SAXException | ParserConfigurationException| TransformerException e1) {
						
				}catch(NullPointerException e2 ){
						System.out.println("");
				}
					
		     } );

	    file.add(modS);
	    menu.add(file);
	    JMenu dire = new JMenu("Repertoire");
	    JMenuItem det= new JMenuItem("Propriété");
	    JMenuItem modRT= new JMenuItem("Modifier Titre");
	    JMenuItem modRS= new JMenuItem("Modifier Sujet");
			     
	    det.addActionListener( e -> {
				try {
					String name= JOptionPane.showInputDialog( this, "entrez le nom d'un repertoire" );
					mnuNewListenerRepPr(e,name);
				}catch (IOException | SAXException | ParserConfigurationException| TransformerException e1) {
						
				}catch(NullPointerException e2 ){
						System.out.println("");
				}
					
	    	} );
	    dire.add(det);
	    dire.addSeparator();
	    modRT.addActionListener( e -> {
	    	try {
	    		String name= JOptionPane.showInputDialog( this, "entrez le nom d'un repertoire" );
	    		mnuNewRepMT(e,name);
	    	}catch(NullPointerException e2 ){
	    				System.out.println("");
			}
						
	    } );
	    
	    dire.add(modRT);
	    dire.addSeparator();
	    modRS.addActionListener( e -> {
	    	try {
	    		String name= JOptionPane.showInputDialog( this, "entrez le nom d'un repertoire" );

	    		mnuNewRepMS(e,name);
							
	    	}catch(NullPointerException e1 ){
	    		       System.out.println("");
			}
	    } );
	    dire.add(modRS);
	    menu.add(dire);
			    
	    JMenu histo = new JMenu("Historique");
	    JMenuItem historique= new JMenuItem("Historique");
	    
	    historique.addActionListener( e -> {
	    	try {
	    		mnuHistorique(e);
	    	} catch (NullPointerException e1) {
						e1.printStackTrace();
			}
	    } );
	    histo.add(historique);
	    menu.add(histo);
			     
	    return menu;
		 	
	}

	public void styleAcc() {
		 JTextArea aide= new JTextArea(100,100);
	        aide.setEditable(false);
	        aide.append("\n			BIENVENUE");								
	        aide.append("\n\n-> Pour afficher les métadonnées d'un fichier,avec son nom, rendez-vous dans le menu 'Fichier' puis\n'propriété'\n");
	        aide.append("\n-> Pour afficher les métadonnées d'un fichier,en parcourant un repertoire, rendez vous dans le menu\n'repertoire' puis 'propriété'\n");
	        aide.append("\n-> Pour modifier les métadonnées d'un fichier,avec son nom,rendez-vous dans le menu 'fichier' puis\n le menu 'modification'\n ");
	        aide.append("\n-> Pour modifier les métadonnées d'un fichier,en parcourant un repertoire,rendez vous dans le menu\n'repertoire' puis 'modification' \n");
	        aide.append("\n-> Pour consulter l'historique des différentes modification,rendez-vous dans le menu 'aide' puis\n'historique'\n");
	        aide.append("\n-> ATTENTION: la commande modifier, N'AJOUTE ni titre ni sujet");
	        this.add(aide);
	}
	public void mnuNewListenerFilePr( ActionEvent event,String name,File nom ) throws IOException, NullPointerException, SAXException, ParserConfigurationException, TransformerException {
	  try {
    	UnzipOdtFile.unzipfile(name,fold);             	 
	    if(new File(fold+"/mimeType").exists()&&TestTypeMime.TestMimeType(fold+"/mimeType")){
	    	if((TestTypeMime.TestOdf(fold+"/mimeType")).equalsIgnoreCase("ODT")) {
	    		JTextArea jt = new JTextArea();
	    		jt.setEditable(false);
	    		JFrame jFrame = new JFrame();
	  	        jFrame.setLayout(new FlowLayout());
	  	        jFrame.setSize(500, 350);
	  	        JLabel jLabel = new JLabel();
	  	        
	  	        ArrayList<String>b=meta.recupMeta(fold+"/meta.xml");
	  	        for(int i=0;i< b.size();i++) {
	    		 	jt.append(b.get(i)+"\n");
	    		 } 
	    		 
	    		 File file = new File( fold+"/Thumbnails/thumbnail.png" );
	    	     BufferedImage bufferedImage = ImageIO.read(file);
	    	     ImageIcon imageIcon = new ImageIcon(bufferedImage);
	    	     jLabel.setIcon(imageIcon);
	    	     jFrame.add(jt);
	    	     jFrame.add(jLabel);
	    	     jFrame.setResizable(false);
	    	    jFrame.setVisible(true);
	    	        
	    	  }else {
	    		  JTextArea jta=new JTextArea(5,5);
	    		  jta.setEditable(false);
	    		  JDialog d = new JDialog(this,"metadonnées");
	    		  jta.append("\nname:"+nom.getName()+"\n");
	    		  jta.append("type:"+TestTypeMime.TestOdf(System.getProperty("user.home")+"/folder_XML/mimetype")+"\n");
	    		  jta.append("date:"+(new Date (nom.lastModified())).toString()+"\n");
	    		  jta.append("length:"+Long.toString(nom.length()/1024)+"Ko");
	    		  d.add(jta);
	    		  d.setSize(300, 150);
	    		  d.setResizable(false);
	    		  d.setVisible(true);
	    		  
	    	  }
	      }else {
	    	  JDialog d = new JDialog(this,"metadonnées"); 
	    	  JLabel a =new JLabel("Le fichier n'a pas la bonne extension");
	    	  
	    	  d.add(a); 
	    	  d.setSize(250, 100); 
	    	  d.setResizable(false);
	    	  d.setVisible(true); 
	      }
	      CLI.SuprFolder(new File(fold));
	    }catch(FileNotFoundException e) {
	    	JDialog d = new JDialog(this,"metadonnées"); 
	    	  JLabel a =new JLabel("Le fichier est introuvable");
	    	  
	    	  d.add(a); 
	    	  d.setSize(200, 100); 
	    	  d.setResizable(false);
	    	  d.setVisible(true); 
	    	 
	    }
	    }
	
    public void mnuNewListenerFileMdT( ActionEvent event,String title,String name ) throws IOException, TransformerException, ParserConfigurationException, SAXException {
    	JDialog d = new JDialog(this,"metadonnées"); 
			d.setResizable(false);
			if(new File (name).exists()) {
    		
    			UnzipOdtFile.unzipfile(name,fold); 
  	  			JTextArea jt=new JTextArea();
  	  			jt.setEditable(false);
  	  			if(new File(fold+"/mimeType").exists()&&TestTypeMime.TestMimeType(fold+"/mimeType")){
  	  				if((TestTypeMime.TestOdf(fold+"/mimeType")).equalsIgnoreCase("ODT")) {
  	  					meta.ModifierTitle(fold+"/meta.xml", title);
  	  					ArrayList<String>b=meta.recupMeta(fold+"/meta.xml");
   		 
  	  					for(int i=0;i< b.size();i++) {
  	  						jt.append(b.get(i)+"\n");
  	  					} 
  	  				}else {
  	  	        	JTextArea a =new JTextArea("Le fichier n'est pas du bon format");
  	  	        	a.setEditable(false);
  	  	        	d.add(a);
  	  				}
  	  				d.add(jt);
  	  			}
  	  			ZipOutputStream zip =new ZipOutputStream(new FileOutputStream(new File(name)));
  	  			ZipFile.zip("",new File("C:\\Users\\lauri\\folder_XML") ,zip);
  	  			zip.close();
  	  			hist.add(name+"		titre\n");
    	}else {
        	JTextArea a =new JTextArea("Le fichier est inconnu");
        	a.setEditable(false);
        	d.add(a);
    	}
        d.setSize(300, 250); 
        d.setVisible(true); 
        
        CLI.SuprFolder(new File(fold));
    
    }
    public void mnuNewListenerFileMdS( ActionEvent event,String sujet,String name ) throws IOException, TransformerException, ParserConfigurationException, SAXException {
    	
    	JDialog d = new JDialog(this,"metadonnées"); 
  	  	d.setResizable(false);
  	  	if(new File(name).exists()) {
  	  		UnzipOdtFile.unzipfile(name,fold); 
  	  		JTextArea jt=new JTextArea();
  	  		jt.setEditable(false);
  	  		if(new File(fold+"/mimeType").exists()&&TestTypeMime.TestMimeType(fold+"/mimeType")){
  	  			if((TestTypeMime.TestOdf(fold+"/mimeType")).equalsIgnoreCase("ODT")) {
  	  				meta.ModifierSubject(fold+"/meta.xml", sujet);
  	  				ArrayList<String>b=meta.recupMeta(fold+"/meta.xml");
   		 
  	  				for(int i=0;i< b.size();i++) {
  	  					jt.append(b.get(i)+"\n");
  	  				} 
  	  			}else {	
  	  			JTextArea a =new JTextArea("Le fichier n'est pas du bon format");
  	        	d.add(a);
  	  			}
      	  d.add(jt);
        }
        hist.add(name+"		sujet\n");
        ZipOutputStream zip =new ZipOutputStream(new FileOutputStream(new File(name)));
		ZipFile.zip("",new File("C:\\Users\\lauri\\folder_XML") ,zip);
		zip.close();
        }else{
        	JTextArea a =new JTextArea("Le fichier est inconnu");
        	d.add(a);
       }
        d.setSize(300, 250); 
        d.setVisible(true);
        
        CLI.SuprFolder(new File(fold));
    
    }

	    
	public void mnuNewListenerRepPr( ActionEvent event,String name ) throws IOException, NullPointerException, SAXException, ParserConfigurationException, TransformerException {
	    	
			JFrame frame=new JFrame("repertoire "+name);
	    	File rep =new File(name);
	    	
	    	if(rep.isDirectory()) {
	    		try {
	        File[]liste= rep.listFiles();
	        JList<File> list =new JList<File>((liste));
	        JScrollPane sp = new JScrollPane(list);
	        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        list.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					File selectedFile = (File) list.getSelectedValue();
					try {
						if(!selectedFile.isDirectory()) {
							mnuNewListenerFilePr( event,selectedFile.getName(),selectedFile);
							}else {
								frame.dispose();
								mnuNewListenerRepPr( event,selectedFile.toString());
							}
					}catch (IOException | NullPointerException | SAXException | ParserConfigurationException | TransformerException e1) {
						e1.printStackTrace();
					}
				}
			});
	        frame.add(sp);
	        frame.setSize(400, 250);
	    	frame.setResizable(false);
	        frame.setVisible(true);
	        
	    }catch(IllegalArgumentException e){
	    	JDialog d = new JDialog(this,"metadonnées"); 
	    	JLabel a =new JLabel("Le fichier est inconnu");
	    	  
	    	  d.add(a); 
	    	  d.setSize(150, 100); 
	    	  d.setResizable(false);
	    	  d.setVisible(true);
	    	
	    	}
	    	  	
		 }
	 }
	public void mnuNewRepMT(ActionEvent event,String name) {
		
	    	File rep =new File(name);
	    	if(rep.isDirectory()) {
	    		File[]liste= rep.listFiles();
	    		JList<File> list =new JList<File>(liste);
	    		JScrollPane sp = new JScrollPane(list);
	    		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    		JFrame frame=new JFrame("repertoire "+name);
	    		list.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					File selectedFile = (File) list.getSelectedValue();
					String title= JOptionPane.showInputDialog( this, "Quelle est le nouveau titre" );
					try {
						if(!selectedFile.isDirectory()) {
							mnuNewListenerFileMdT( event,title,selectedFile.getName());
						}else {
							frame.dispose();
							mnuNewRepMT(event, selectedFile.toString());
						}
						} catch (IOException | NullPointerException | SAXException | ParserConfigurationException | TransformerException e1) {
						e1.printStackTrace();
					}
					
				}
			});
	     
	    	frame.add(sp);
	    	frame.setSize(400, 250);
	    	frame.setResizable(false);
	        frame.setVisible(true);
	    	}
	    }
	public void mnuNewRepMS(ActionEvent event,String name) {
	     	
	    	File rep =new File(name);
	    	if(rep.isDirectory()) {
	    		File[]liste= rep.listFiles();
	    		JList<File> list =new JList<File>(liste);
	    		JScrollPane sp = new JScrollPane(list);
	    		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    		JFrame frame=new JFrame("repertoire "+name);
	    		list.addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					File selectedFile = (File) list.getSelectedValue();
					String subject= JOptionPane.showInputDialog( this, "Quelle est le nouveau sujet" );
					try {
						if(!selectedFile.isDirectory()) {
							mnuNewListenerFileMdS( event,subject,selectedFile.getName());
						}else {
							frame.dispose();
							mnuNewRepMS(event, selectedFile.toString());
						}
						} catch (IOException | NullPointerException | SAXException | ParserConfigurationException | TransformerException e1) {
						e1.printStackTrace();
					}
					
				}
			});
	     
	    	frame.add(sp);
	    	frame.setSize(400, 250);
	    	frame.setResizable(false);
	        frame.setVisible(true);
	    	}
	    }
	public void mnuHistorique(ActionEvent e) {
		JDialog d=new JDialog(this,"historique");
		JTextArea histo= new JTextArea(100,100);
		histo.setEditable(false);
		if(hist.size()!=0) {
			for(int i=0;i<hist.size();i++) {
				histo.append(hist.get(i)+"\n");
			}
		}else {
			histo.append("aucune modification a été faites");
		
		}
		d.add(histo);
		d.setSize(400, 350); 
        d.setResizable(false);
        d.setVisible(true);
	}
	
	       
	    
	public static void main(String[] args) throws UnsupportedLookAndFeelException, ParserConfigurationException {
		    	 
		    	  new GUI().setVisible(true);
		    }

   }
