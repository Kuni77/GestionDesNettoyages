import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.toedter.calendar.JCalendar;
import test.ComboNew;
import test.Telecharger;

public class Devis extends JPanel 
{
	private Hashtable<String, String[]> listeDevis;
	private Hashtable<String, String> listeClient;
	private Hashtable<String, String> listeCon;
	private Box boxTitre,conteneur2,boxBouton,conteneur,boxRecherche,conteneur3;
	private JLabel titre,textRecherche,matricule,nom,prenom,plus;
	private JTextField recherche,numCon,numDev,matriculeIn,numCl;
	private Color c;
	private JPanel conteneur1;
	private JScrollPane scroller;
	private Font f,f1,f2,f3;
	private Color couleurEcriture;
	private Dimension dim,dim1,dim2,dim3,dim11;
	private Bouton boutonRecherche;
	private JCalendar cdate = new JCalendar();
	private JCalendar debCon = new JCalendar();
	private JCalendar finCon = new JCalendar();
	private ComboNew []tabCombo=new ComboNew[100];
	private ComboNew rempli;
	private String mdev,mcon;
	
	public Devis()
	{
		super();
		
		couleurEcriture=new Color(242,153,186,255);
		c=new Color(161,8,73,255);
		dim1=new Dimension(148,25);
		dim11=new Dimension(300,25);
		
		//dim3=new Dimension(300,100);
		dim2=new Dimension(300,150);
		f=new Font("Arial", Font.BOLD, 30);
		f1=new Font("Arial", Font.BOLD, 20);
		f3=new Font("Arial", Font.BOLD, 15);
		f2=new Font("Arial", Font.BOLD, 25);
		titre = new JLabel("Gestion des devis");
		listeDevis=new Hashtable<String, String[]>();
		listeClient=new Hashtable<String, String>();
		listeCon=new Hashtable<String, String>();
		recherche=new JTextField("votre text");
		recherche.setPreferredSize(new Dimension(200,25));
		textRecherche=new JLabel("recherche: ");
		textRecherche.setForeground(couleurEcriture);
		textRecherche.setFont(f1);
		boutonRecherche=new Bouton("ok");
		boutonRecherche.addActionListener(new BoutonRecherche());
		boutonRecherche.setPreferredSize(new Dimension(60,25));
		//boutonRecherche.setBackground(Color.gray);
		JPanel panelTitre=new JPanel();
		titre.setForeground(couleurEcriture);
		titre.setFont(f);
		//panelTitre.setBackground(c);
		panelTitre.add(titre);
		JPanel panelRecherche=new JPanel();
		//panelRecherche.setBackground(c);
		panelRecherche.add(textRecherche);
		panelRecherche.add(recherche);
		panelRecherche.add(boutonRecherche);
		boxRecherche=Box.createHorizontalBox();
		boxRecherche.add(panelRecherche);
		
		boxTitre=Box.createHorizontalBox();
		boxTitre.setBackground(Color.black);
		boxTitre.add(panelTitre);
		
		Bouton ajoutDevis=new Bouton("Creer Devis",2);
		ajoutDevis.addActionListener(new BoutonCreerDevis());
		Bouton ajoutCon=new Bouton("Voir Contrat",2);
		ajoutCon.addActionListener(new BoutonVoirContrat());
		//ajoutDevis.addActionListener(new BoutonCreerDevis());
		Bouton supDevis=new Bouton("Enlever Devis",2);
		supDevis.addActionListener(new BoutonSupprimer());
		boxBouton=Box.createHorizontalBox();
		boxBouton.add(supDevis);
		boxBouton.add(new Separateur(50,2));
		boxBouton.add(ajoutDevis);
		boxBouton.add(new Separateur(50,2));
		boxBouton.add(ajoutCon);
		
		
		
		conteneur2=Box.createVerticalBox();
		
		//JScrollPane jsp = new JScrollPane(pimage);
	     // JViewport jvp = new JViewport();
	      //jvp =scroller.getViewport();
	      //jvp.setViewPosition(new Point(30,200));
		conteneur1=new JPanel();
		//conteneur1.setBackground(couleurEcriture);
		scroller=new JScrollPane(conteneur1);
		scroller.setPreferredSize(new Dimension(100,500));
		conteneur2.add(boxRecherche);
		try
		{
			Class.forName("org.sqlite.JDBC");
			System.out.println("Driver O.K.");
			String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
			String user = "aysha";
			String passwd = "kuni";
			Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
			System.out.println("Connexion effective !");
			
			
			Statement state7 = conn.createStatement();
			String tabcli="create table IF NOT EXISTS DEVIS ("+
					   "NUMERO_DEVIS         VARCHAR(30)          not null,"+
					  "CODE_CLIENT          VARCHAR(30)          not null,"+
					   "DATE_DEVIS           TEXT                 null,"+
					   "constraint PK_DEVIS primary key (NUMERO_DEVIS)"+
					")";


			state7.addBatch(tabcli);
			state7.executeBatch();
			state7.clearBatch();
			state7.close();
			
			
			Statement state8 = conn.createStatement();
			String tabcli1="create table IF NOT EXISTS CONTRAT ("+
					  " NUMERO_CONTRAT       VARCHAR(30)          not null,"+
					   "NUMERO_DEVIS         VARCHAR(30)          not null,"+
					   "DATE_DEBUT           TEXT                 null,"+
					   "DATE_FIN             TEXT                 null,"+
					   "RECURRENCE           TEXT                 null,"+
					   "REMPLI               BOOLEAN                 not null,"+
					   "constraint PK_CONTRAT primary key (NUMERO_CONTRAT)"+
					")";


			state8.addBatch(tabcli1);
			state8.executeBatch();
			state8.clearBatch();
			state8.close();

			
			
			//Création d'un objet Statement
			Statement state = conn.createStatement();
			Statement state1 = conn.createStatement();
			
			
			Statement state3 = conn.createStatement();
			ResultSet result3=state3.executeQuery("SELECT NUMERO_CONTRAT FROM CONTRAT");
			mcon="-1";
			while(result3.next())
			{
				mcon=result3.getObject(1).toString();
			}
			state3.close();
			result3.close();
			
			
			
			Statement state2 = conn.createStatement();
			ResultSet result2=state2.executeQuery("SELECT NUMERO_DEVIS FROM DEVIS");
			mdev="-1";
			while(result2.next())
			{
				mdev=result2.getObject(1).toString();
			}
			state2.close();
			result2.close();
			//L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery("SELECT * FROM devis where numero_devis not in(select numero_devis from contrat )");
			//ResultSet result1 = state1.executeQuery("SELECT * FROM contrat where rempli=false");
			
			ResultSet res;
			//On récupère les MetaData
			ResultSetMetaData resultMeta = result.getMetaData();
			//On affiche le nom des colonnes
			
			PreparedStatement prepare;
			String str="SELECT nom_client FROM client where code_client=?";
			//Date date =  cdate.getTi
			prepare = conn.prepareStatement(str);
			//On remplace le premier trou par le nom du professeur
			
			
			
			int cp=0;
			while(result.next())
			{
				String []s=new String[resultMeta.getColumnCount()];
				int i;
				for(i = 2; i <= resultMeta.getColumnCount(); i++)
				{
					if(result.getString(i)!=null)
						s[i-2]=new String(result.getString(i));
				}
				prepare.setString(1,result.getObject(2).toString());
				res=prepare.executeQuery();
				res.next();
				s[i-2]=new String(res.getObject(1).toString());
				listeDevis.put(result.getObject(1).toString(),s ) ;
				listeClient.put(result.getObject(2).toString(),result.getObject(1).toString() ) ;
			//	if(result1.next())
				//	listeCon.put(result1.getObject(2).toString(),s[i-2] );
				JPanel n4=new JPanel();
				//n4.setPreferredSize(dim);
				n4.setBackground(Color.white);
				Bouton b=new Bouton("Devis: "+result.getObject(1).toString()+"\n"+"  pour  "+res.getObject(1).toString()+"   du  "+result.getObject(3).toString(),result.getObject(1).toString());
				
				b.addActionListener(new Boutontest());
				b.setBackground(Color.white);
				//b.setBorderPainted(false);
				b.setPreferredSize(new Dimension(500,30));
				b.setFont(f3);
				n4.add(b);
				//Box hb=Box.createHorizontalBox();
				//hb.add(n4);
				//b.setPreferredSize(dim);
				conteneur2.add(n4);
				conteneur2.add(new Separateur(50,5));
				
			}
			//recherche=testAutocomplete.createAutoCompleteTextField(saut);
			
			prepare.close();
			result.close();
		//	result1.close();
			state.close();
			state1.close();
			conn.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		conteneur1.add(conteneur2);
		
		conteneur=Box.createVerticalBox();
		conteneur.add(boxTitre);
		conteneur.add(new Separateur(600,10));
		conteneur.add(boxBouton);
		conteneur.add(new Separateur(600,10));
		conteneur.add(scroller);
		conteneur.add(new Separateur(600,10));
		Bouton actu=new Bouton("actualiser");
		actu.addActionListener(new BoutonActualiser());
		Box actub=Box.createHorizontalBox();
		actub.add(actu);
		
		conteneur.add(actub);
		this.add(conteneur);
		
	}
	
	
	class BoutonRetour implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Devis.this.removeAll();
			Devis.this.add(conteneur);
			Devis.this.updateUI();
		}
	}
	
	
	
	class BoutonRecherche implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			if(listeClient.get(recherche.getText())!=null)
			{
				Devis.this.removeAll();
				JLabel tr=new JLabel("Devis Recherche");
				tr.setFont(f);
				JPanel ptr=new JPanel();
				ptr.add(tr);
				Box btr=Box.createHorizontalBox();
				btr.add(ptr);
				
				Box bp=Box.createVerticalBox();
				bp.add(btr);
				bp.add(new Separateur(600,20));
				
				JLabel tr1=new JLabel("Resultats de la Recherche");
				tr1.setFont(f2);
				JPanel ptr1=new JPanel();
				ptr1.add(tr1);
				Box btr1=Box.createHorizontalBox();
				btr1.add(ptr1);
				bp.add(btr1);
				bp.add(new Separateur(600,20));
				

				Box conteneur5=Box.createVerticalBox();
				JPanel conteneur6=new JPanel();
				conteneur6.add(conteneur5);
				JScrollPane js5=new JScrollPane(conteneur6);
				js5.setPreferredSize(new Dimension(500,500));
				bp.add(js5);
				
				
				try
				{
					Class.forName("org.sqlite.JDBC");
					System.out.println("Driver O.K.");
					String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
					String user = "aysha";
					String passwd = "kuni";
					Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
					System.out.println("Connexion effective !");
					//Création d'un objet Statement
					Statement state = conn.createStatement();
					//L'objet ResultSet contient le résultat de la requête SQL
				//	ResultSet result = state.executeQuery("SELECT * FROM devis where numero_devis not in(select numero_devis from contrat where rempli=true)");
					ResultSet res;
					//On récupère les MetaData
					//ResultSetMetaData resultMeta = result.getMetaData();
					//On affiche le nom des colonnes
					
					PreparedStatement prepare;
					String str="SELECT numero_devis FROM devis where code_client=?";
					//Date date =  cdate.getTi
					prepare = conn.prepareStatement(str);
					//On remplace le premier trou par le nom du professeur
					prepare.setString(1, recherche.getText());
					res=prepare.executeQuery();
					while(res.next())
					{
						recherche.setText(res.getObject(1).toString());
						JPanel n4=new JPanel();
						//n4.setPreferredSize(dim);
						n4.setBackground(Color.white);
						Bouton b=new Bouton("Devis: "+recherche.getText()+"\n"+"  pour  "+listeDevis.get(recherche.getText())[2]+"   du  "+listeDevis.get(recherche.getText())[1],recherche.getText());
						
						b.addActionListener(new Boutontest());
						b.setBackground(Color.white);
						//b.setBorderPainted(false);
						b.setPreferredSize(new Dimension(500,30));
						b.setFont(f3);
						n4.add(b);
						//Box hb=Box.createHorizontalBox();
						//hb.add(n4);
						//b.setPreferredSize(dim);
						conteneur5.add(n4);
						conteneur5.add(new Separateur(50,5));
					}
					prepare.close();
					//result.close();
					state.close();
					conn.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
				
				
				
				Bouton br=new Bouton("retour");
				br.addActionListener(new BoutonRetour());
				bp.add(br);
				Devis.this.add(bp);
				Devis.this.updateUI();
	
				
			}
			else
			{
			    JOptionPane jop4 = new JOptionPane();
				jop4.showMessageDialog(null, "aucun devis trouve", "Information", JOptionPane.INFORMATION_MESSAGE);
	
			}
		}
	}
	
	
	
	
	
	class BoutonSupprimer implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
			String s = jop.showInputDialog(null, "Donner le matricule du Devis ","Supprimer un Devis", JOptionPane.QUESTION_MESSAGE);
			if(s!=null)
			{
				int option = jop.showConfirmDialog(null,
					"Voulez-vous vraiment supprimer le Devis ?",
					"suppression",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			
					if(option == JOptionPane.OK_OPTION)
					{
						if(listeDevis.containsKey(s))
						{
							try
							{
								Class.forName("org.sqlite.JDBC");
								System.out.println("Driver O.K.");
								String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
								String user = "aysha";
								String passwd = "kuni";
								Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
								System.out.println("Connexion effective !");
								//Création d'un objet Statement
								//Statement state = conn.createStatement();
								//On crée la requête
								PreparedStatement prepare;
									String str="delete from Devis where NUMERO_Devis=?";
									//Date date =  cdate.getTi
									prepare = conn.prepareStatement(str);
									//On remplace le premier trou par le nom du professeur
									prepare.setString(1,s);
									prepare.executeUpdate();
									prepare.close();
									conn.close();
							} 
							catch (Exception e) 
							{
								e.printStackTrace();
							}
							listeDevis.remove(s);
							conteneur2.removeAll();	
							
							Set keys = listeDevis.keySet();
							 
						    //obtenir un iterator des clés
						    Iterator itr = keys.iterator();
						    conteneur2.add(boxRecherche);
						    String key="";
						   //affichage des pairs clé-valeur
						    while (itr.hasNext()) 
						    { 
						       // obtenir la clé
						       key = (String) itr.next();
						       JPanel n4=new JPanel();
								//n4.setPreferredSize(dim);
								n4.setBackground(Color.white);
								Bouton b=new Bouton("Devis: "+key+"\n"+"   pour  "+listeDevis.get(key)[2]+"\n"+"   de   "+listeDevis.get(key)[1],key);
								//b.addActionListener(new Boutontest());
								b.setBackground(Color.white);
								//b.setBorderPainted(false);
								b.setPreferredSize(new Dimension(500,30));
								b.setFont(f3);
								n4.add(b);
								//Box hb=Box.createHorizontalBox();
								//hb.add(n4);
								//b.setPreferredSize(dim);
								conteneur2.add(n4);
								conteneur2.add(new Separateur(50,5));
								
						       
						    }
						    JOptionPane jop4 = new JOptionPane();
							jop4.showMessageDialog(null, "vous avez supprimer le Devis", "Information", JOptionPane.INFORMATION_MESSAGE);
				
						}
						else
						{
							JOptionPane jop1 = new JOptionPane();
							jop1.showMessageDialog(null, " le Devis n'existe pas", "Information", JOptionPane.INFORMATION_MESSAGE);
				
						}
					
					}
					else
					{
						JOptionPane jop1 = new JOptionPane();
						jop1.showMessageDialog(null, "vous avez annuler la suppression", "Information", JOptionPane.INFORMATION_MESSAGE);
			
					}
			}
					Devis.this.add(conteneur);
					Devis.this.updateUI();
			}
	}
	
	
	
	
	class BoutonActualiser implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			conteneur2.removeAll();
			conteneur2.add(boxRecherche);
			try
			{
				Class.forName("org.sqlite.JDBC");
				System.out.println("Driver O.K.");
				String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
				String user = "aysha";
				String passwd = "kuni";
				Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
				System.out.println("Connexion effective !");
				
				//Création d'un objet Statement
				Statement state = conn.createStatement();
				Statement state1 = conn.createStatement();
				//L'objet ResultSet contient le résultat de la requête SQL
				ResultSet result = state.executeQuery("SELECT * FROM devis where numero_devis not in(select numero_devis from contrat )");
				//ResultSet result1 = state1.executeQuery("SELECT * FROM contrat where rempli=false");
				
				ResultSet res;
				//On récupère les MetaData
				ResultSetMetaData resultMeta = result.getMetaData();
				//On affiche le nom des colonnes
				
				PreparedStatement prepare;
				String str="SELECT nom_client FROM client where code_client=?";
				//Date date =  cdate.getTi
				prepare = conn.prepareStatement(str);
				//On remplace le premier trou par le nom du professeur
				
				
				
				int cp=0;
				while(result.next())
				{
					String []s=new String[resultMeta.getColumnCount()];
					int i;
					for(i = 2; i <= resultMeta.getColumnCount(); i++)
					{
						if(result.getString(i)!=null)
							s[i-2]=new String(result.getString(i));
					}
					prepare.setString(1,result.getObject(2).toString());
					res=prepare.executeQuery();
					res.next();
					s[i-2]=new String(res.getObject(1).toString());
					//	if(result1.next())
					//	listeCon.put(result1.getObject(2).toString(),s[i-2] );
					JPanel n4=new JPanel();
					//n4.setPreferredSize(dim);
					n4.setBackground(Color.white);
					Bouton b=new Bouton("Devis: "+result.getObject(1).toString()+"\n"+"  pour  "+res.getObject(1).toString()+"   du  "+result.getObject(3).toString(),result.getObject(1).toString());
					
					b.addActionListener(new Boutontest());
					b.setBackground(Color.white);
					//b.setBorderPainted(false);
					b.setPreferredSize(new Dimension(500,30));
					b.setFont(f3);
					n4.add(b);
					//Box hb=Box.createHorizontalBox();
					//hb.add(n4);
					//b.setPreferredSize(dim);
					conteneur2.add(n4);
					conteneur2.add(new Separateur(50,5));
					
				}
				//recherche=testAutocomplete.createAutoCompleteTextField(saut);
				
				prepare.close();
				result.close();
			//	result1.close();
				state.close();
				state1.close();
				conn.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			

			Devis.this.updateUI();
		}
	}
	
	
	
	
	
	class BoutonCreerDevis implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Devis.this.removeAll();
			try
			{
				Class.forName("org.sqlite.JDBC");
				System.out.println("Driver O.K.");
				String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
				String user = "aysha";
				String passwd = "kuni";
				Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
				System.out.println("Connexion effective !");
				//Création d'un objet Statement
				Statement state = conn.createStatement();
			//	Statement state1 = conn.createStatement();
				Statement state2 = conn.createStatement();
				//L'objet ResultSet contient le résultat de la requête SQL
				ResultSet result = state.executeQuery("SELECT * FROM client");
				//ResultSet result1 = state1.executeQuery("SELECT * FROM contrat where rempli=false");
				ResultSet result2 = state2.executeQuery("SELECT * FROM service");
				//On récupère les MetaData
				/*ResultSetMetaData resultMeta = result.getMetaData();
				//ResultSetMetaData resultMeta1 = result1.getMetaData();
				ResultSetMetaData resultMeta2 = result2.getMetaData();*/
				for(int i=1;i<100;i++)
				{
					tabCombo[i]=new ComboNew();
					tabCombo[i].setPreferredSize(new Dimension(100,40));
					tabCombo[i].addItemListener(new ItemState());
					tabCombo[i].addItem("");
				}
				
				while(result.next())
				{
					if(!listeClient.containsKey(result.getObject(1)))
					{
						listeClient.put(result.getObject(1).toString(), result.getObject(2).toString());
					}
				}
				int r=1;
				while(result2.next())
				{
					
					for(int i=1;i<100;i++)
					{
						tabCombo[i].setMat(r-1,result2.getObject(1).toString());
						tabCombo[i].addItem(String.valueOf(r)+") "+result2.getObject(2).toString());
					}
					r++;
				}
				
				//On affiche le nom des colonnes
				result.close();
				//result1.close();
				result2.close();
				state.close();
			//	state1.close();
				state2.close();
				conn.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			
			
			JLabel titre1=new JLabel("Creer Devis");
			titre1.setFont(f);
			titre1.setForeground(couleurEcriture);
			JPanel panTitre=new JPanel();
			panTitre.add(titre1);
			Box boxTitre1=Box.createHorizontalBox();
			boxTitre1.add(panTitre);
			Box boxPrincipal=Box.createVerticalBox();
			
		/*	JLabel prenomEmp = new JLabel(": ");
			prenomEmp.setFont(f1);
			prenomEmp.setForeground(couleurEcriture);
			prenomIn=new JTextField("prenom Devis");
			Box boxPrnom=Box.createHorizontalBox();
			boxPrnom.add(prenomEmp);
			boxPrnom.add(prenomIn);*/
			
			
			
			
			
			JLabel nomEmp = new JLabel("Numero client: ");
			nomEmp.setFont(f1);
			nomEmp.setForeground(couleurEcriture);
			numCl=new JTextField();
			Box boxName=Box.createHorizontalBox();
			boxName.add(nomEmp);
			boxName.add(numCl);
			
			JLabel matEmp = new JLabel("Code devis: ");
			matEmp.setForeground(couleurEcriture);
			matEmp.setFont(f1);
			if(mdev=="-1")
			{
				mdev="001";
				matriculeIn=new JTextField(mdev);
				
			}
			else
			{
				int i=Integer.valueOf(mdev);
				mdev=String.valueOf(i+1);
				matriculeIn=new JTextField(mdev);
			}
			matriculeIn.setEditable(false);
			Box boxMat1=Box.createHorizontalBox();
			boxMat1.add(matEmp);
			boxMat1.add(matriculeIn);
			JLabel erreur=new JLabel("le code devis est obligatoire");
			erreur.setForeground(Color.red);
			erreur.setFont(new Font("Arial", Font.BOLD, 12));
			Box boxMat=Box.createVerticalBox();
			boxMat.add(boxMat1);
			boxMat.add(erreur);
			
			JLabel dateNaissance = new JLabel("Date : ");
			dateNaissance.setForeground(couleurEcriture);
			dateNaissance.setFont(f1);
			Bouton cr=new Bouton("choisir date");
			cr.addActionListener(new ChoixDate());
			Box boxdte=Box.createHorizontalBox();
			boxdte.add(dateNaissance);
			//boxdte.add(dteIn);
			Separateur paneldater=new Separateur(100,40);
			paneldater.add(cr);
			boxdte.add(paneldater);
			
			
			JLabel detailContact=new JLabel("Ajouter Service");
			detailContact.setFont(f2);
			detailContact.setForeground(couleurEcriture);
			JPanel panDetail=new JPanel();
			panDetail.add(detailContact);
			Box boxDetail=Box.createHorizontalBox();
			boxDetail.add(panDetail);
			
			
			JLabel []serv=new JLabel[10];
			Box hbo=Box.createVerticalBox();
			for(int i=1;i<11;i++)
			{
				serv[i-1] = new JLabel("Service"+String.valueOf(i)+" : ");
				serv[i-1].setFont(f1);
				serv[i-1].setForeground(couleurEcriture);
				//nomIn=new JTextField("code client");
				Box bo=Box.createHorizontalBox();
				bo.add(serv[i-1]);
				bo.add(tabCombo[i]);
				tabCombo[i].setSelectedItem("");
				hbo.add(bo);
			}
			JPanel panbo=new JPanel();
			panbo.add(hbo);
			JScrollPane jsbo=new JScrollPane(panbo);
			jsbo.setPreferredSize(new Dimension(400,200));
			
			
			Bouton boutonRetour=new Bouton("Annuler");
			boutonRetour.addActionListener(new BoutonRetour());
			JPanel panRetour=new JPanel();
			panRetour.add(boutonRetour);
		//	boutonRetour.addActionListener(new BoutonRetour());
			
			Bouton boutonValider=new Bouton("Valider");
			panRetour.add(boutonValider);
			boutonValider.addActionListener(new BoutonValider());
			
			
			
			Box retourPayer=Box.createHorizontalBox();
			retourPayer.add(panRetour);
			
			
			
			boxPrincipal.add(boxTitre1);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxName);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxMat);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxdte);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxDetail);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(jsbo);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(retourPayer);
			Devis.this.add(boxPrincipal);
			Devis.this.updateUI();
		}
	}
	
	
	
/*
	class BoutonModifier implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Object b=arg0.getSource();
			String s=new String();
			s=((Bouton)b).getMat();
			
			Devis.this.removeAll();
			try
			{
				Class.forName("org.sqlite.JDBC");
				System.out.println("Driver O.K.");
				String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
				String user = "aysha";
				String passwd = "kuni";
				Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
				System.out.println("Connexion effective !");
				//Création d'un objet Statement
				Statement state = conn.createStatement();
			//	Statement state1 = conn.createStatement();
				Statement state2 = conn.createStatement();
				//L'objet ResultSet contient le résultat de la requête SQL
				ResultSet result = state.executeQuery("SELECT * FROM client");
				//ResultSet result1 = state1.executeQuery("SELECT * FROM contrat where rempli=false");
				ResultSet result2 = state2.executeQuery("SELECT * FROM service");
				//On récupère les MetaData
				/*ResultSetMetaData resultMeta = result.getMetaData();
				//ResultSetMetaData resultMeta1 = result1.getMetaData();
				ResultSetMetaData resultMeta2 = result2.getMetaData();*/
		/*		for(int i=1;i<100;i++)
				{
					tabCombo[i].removeAll();
					//tabCombo[i]=new ComboNew();
					//tabCombo[i].setPreferredSize(new Dimension(100,40));
					//tabCombo[i].addItemListener(new ItemState());
					tabCombo[i].addItem("");
				}
				
				while(result.next())
				{
					if(!listeClient.containsKey(result.getObject(1)))
					{
						listeClient.put(result.getObject(1).toString(), result.getObject(2).toString());
					}
				}
				
				int r=1;
				while(result2.next())
				{
					
					for(int i=1;i<100;i++)
					{
						tabCombo[i].setMat(r-1,result2.getObject(1).toString());
						tabCombo[i].addItem(String.valueOf(r)+") "+result2.getObject(2).toString());
					}
					r++;
				}
				
				//On affiche le nom des colonnes
				result.close();
				//result1.close();
				result2.close();
				state.close();
			//	state1.close();
				state2.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			
			
			JLabel titre1=new JLabel("Modifier Devis");
			titre1.setFont(f);
			titre1.setForeground(couleurEcriture);
			JPanel panTitre=new JPanel();
			panTitre.add(titre1);
			Box boxTitre1=Box.createHorizontalBox();
			boxTitre1.add(panTitre);
			Box boxPrincipal=Box.createVerticalBox();*/
			
		/*	JLabel prenomEmp = new JLabel(": ");
			prenomEmp.setFont(f1);
			prenomEmp.setForeground(couleurEcriture);
			prenomIn=new JTextField("prenom Devis");
			Box boxPrnom=Box.createHorizontalBox();
			boxPrnom.add(prenomEmp);
			boxPrnom.add(prenomIn);*/
		/*	
			
			
			
			
			JLabel nomEmp = new JLabel("Numero client: ");
			nomEmp.setFont(f1);
			nomEmp.setForeground(couleurEcriture);
			//numCl=new JTextField();
			numCl.setText(listeDevis.get(s)[0]);
			Box boxName=Box.createHorizontalBox();
			boxName.add(nomEmp);
			boxName.add(numCl);
			
			JLabel matEmp = new JLabel("Code devis: ");
			matEmp.setForeground(couleurEcriture);
			matEmp.setFont(f1);
			//matriculeIn=new JTextField("ex 123546",9);
			matriculeIn.setText(s);
			Box boxMat1=Box.createHorizontalBox();
			boxMat1.add(matEmp);
			boxMat1.add(matriculeIn);
			JLabel erreur=new JLabel("le code devis est obligatoire");
			erreur.setForeground(Color.red);
			erreur.setFont(new Font("Arial", Font.BOLD, 12));
			Box boxMat=Box.createVerticalBox();
			boxMat.add(boxMat1);
			boxMat.add(erreur);
			
			JLabel dateNaissance = new JLabel("Date : ");
			dateNaissance.setForeground(couleurEcriture);
			dateNaissance.setFont(f1);
			Bouton cr=new Bouton("choisir date");
			cr.addActionListener(new ChoixDate());
			Box boxdte=Box.createHorizontalBox();
			boxdte.add(dateNaissance);
			//boxdte.add(dteIn);
			Separateur paneldater=new Separateur(100,40);
			paneldater.add(cr);
			boxdte.add(paneldater);
			
			
			JLabel detailContact=new JLabel("Ajouter Service");
			detailContact.setFont(f2);
			detailContact.setForeground(couleurEcriture);
			JPanel panDetail=new JPanel();
			panDetail.add(detailContact);
			Box boxDetail=Box.createHorizontalBox();
			boxDetail.add(panDetail);
			
			
			JLabel []serv=new JLabel[10];
			Box hbo=Box.createVerticalBox();
			for(int i=1;i<11;i++)
			{
				serv[i-1] = new JLabel("Service"+String.valueOf(i)+" : ");
				serv[i-1].setFont(f1);
				serv[i-1].setForeground(couleurEcriture);
				//nomIn=new JTextField("code client");
				Box bo=Box.createHorizontalBox();
				bo.add(serv[i-1]);
				bo.add(tabCombo[i]);
				tabCombo[i].setSelectedItem("");
				hbo.add(bo);
			}
			JPanel panbo=new JPanel();
			panbo.add(hbo);
			JScrollPane jsbo=new JScrollPane(panbo);
			jsbo.setPreferredSize(new Dimension(400,200));
			
			
			Bouton boutonRetour=new Bouton("Annuler");
			boutonRetour.addActionListener(new BoutonRetour());
			JPanel panRetour=new JPanel();
			panRetour.add(boutonRetour);
		//	boutonRetour.addActionListener(new BoutonRetour());
			
			Bouton boutonValider=new Bouton("Valider",s,1);
			panRetour.add(boutonValider);
			boutonValider.addActionListener(new BoutonValider());
			
			
			
			Box retourPayer=Box.createHorizontalBox();
			retourPayer.add(panRetour);
			
			
			
			boxPrincipal.add(boxTitre1);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxName);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxMat);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxdte);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxDetail);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(jsbo);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(retourPayer);
			Devis.this.add(boxPrincipal);
			Devis.this.updateUI();
		}
	}
	*/
	
	
	
	class BoutonValider implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			
			Object b=arg0.getSource();
			String s=new String();
			s=((Bouton)b).getMat();
			
			if(matriculeIn.getText().length()==0 || matriculeIn.getText().length()>29)
			{
				JOptionPane jop3 = new JOptionPane();
				jop3.showMessageDialog(null, "ERREUR SUR Code devis", "Erreur", JOptionPane.ERROR_MESSAGE);
				
			}
			else
			{
				if(numCl.getText().length()==0 || numCl.getText().length()>29)
				{
					JOptionPane jop3 = new JOptionPane();
					jop3.showMessageDialog(null, "ERREUR SUR Code client", "Erreur", JOptionPane.ERROR_MESSAGE);
					
				}
				else
				{
					try
					{
						Class.forName("org.sqlite.JDBC");
						System.out.println("Driver O.K.");
						String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
						String user = "aysha";
						String passwd = "kuni";
						Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
						System.out.println("Connexion effective !");
						//Création d'un objet Statement
						//Statement state = conn.createStatement();
						//On crée la requête
						PreparedStatement prepare,prepare1;
						if(s==null || s=="suis")
						{
							String str="insert into DEVIS (NUMERO_DEVIS, CODE_CLIENT, DATE_DEVIS)";
							str+=" values (?,?,?)";
							String str1="insert into contenu_DEVIS (code_service,NUMERO_DEVIS)";
							str1+=" values (?,?)";
							//Date date =  cdate.getTi
							prepare1 = conn.prepareStatement(str1);
							int p=1;
							while(tabCombo[p].getSelectedItem().toString().length()!=0)
							{
								prepare1.setString(2,matriculeIn.getText());
								prepare1.setString(1,tabCombo[p].getMat(Character.getNumericValue(tabCombo[p].getSelectedItem().toString().charAt(0))-1));
								prepare1.executeUpdate();
								p++;
							}
							prepare = conn.prepareStatement(str);
							
							//On remplace le premier trou par le nom du professeur
							prepare.setString(1,matriculeIn.getText());
							prepare.setString(2,numCl.getText());
							prepare.setString(3, new java.sql.Date(cdate.getDate().getTime()).toString());
							
						}
						else
						{
							String str="update Devis "
									+ "set numero_devis=?,"
									+ " code_client=?,"
									+ " date_devis=?";
								str+=" where numero_Devis=?";
								
								String str1="update contenu_Devis "
										+ "set code_service=?,"
										+ " numero_devis=?";
									str1+=" where numero_Devis=?";
									prepare1 = conn.prepareStatement(str1);
									int p=1;
									while(tabCombo[p].getSelectedItem().toString().length()!=0)
									{
										prepare1.setString(1,matriculeIn.getText());
										prepare1.setString(2,tabCombo[p].getMat(Character.getNumericValue(tabCombo[p].getSelectedItem().toString().charAt(0))-1));
										prepare1.setString(3,s);
										prepare1.executeUpdate();
										p++;
									}
							//Date date =  cdate.getTi
							prepare = conn.prepareStatement(str);
							//On remplace le premier trou par le nom du professeur
							prepare.setString(1,matriculeIn.getText());
							prepare.setString(2,numCl.getText());
							prepare.setString(3, new java.sql.Date(cdate.getDate().getTime()).toString());
							prepare.setString(3,s);
							//System.out.println(s);
						}
						if( prepare.executeUpdate()==0 )
						{
							JOptionPane jop3 = new JOptionPane();
							jop3.showMessageDialog(null, "Erreur d'insertion", "Erreur", JOptionPane.ERROR_MESSAGE);
						}
						else
						{
							if(s!=null && s!="suis")
							{
								if(listeDevis.containsKey(s))
								{
									listeDevis.remove(s);
									conteneur2.removeAll();
										
									String []str= {numCl.getText(),cdate.toString()};
									listeDevis.put(matriculeIn.getText(),str ) ;
									Set keys = listeDevis.keySet();
									 
								    //obtenir un iterator des clés
								    Iterator itr = keys.iterator();
								 
								    String key="";
								    
								    //affichage des pairs clé-valeur
								    while (itr.hasNext()) 
								    { 
								       // obtenir la clé
								       key = (String) itr.next();
	
								       JPanel n4=new JPanel();
										//n4.setPreferredSize(dim);
										n4.setBackground(Color.white);
										Bouton br=new Bouton("Devis: "+matriculeIn.getText()+"\n"+"   pour  "+numCl.getText()+"\n"+"   du   "+cdate.toString());
										br.addActionListener(new Boutontest());
										br.setBackground(Color.white);
										//b.setBorderPainted(false);
										br.setPreferredSize(new Dimension(500,30));
										br.setFont(f3);
										n4.add(br);
										//Box hb=Box.createHorizontalBox();
										//hb.add(n4);
										//b.setPreferredSize(dim);
										conteneur2.add(n4);
										conteneur2.add(new Separateur(50,5));							    }
								}
								else {
									
								}
																		    
							}
							else
							{
								String []str= {numCl.getText(),cdate.toString()};
								listeDevis.put(matriculeIn.getText(),str ) ;
								JPanel n4=new JPanel();
								//n4.setPreferredSize(dim);
								n4.setBackground(Color.white);
								Bouton br=new Bouton("Devis: "+matriculeIn.getText()+"\n"+"   pour  "+numCl.getText()+"\n"+"   du   "+cdate.toString(),matriculeIn.getText());
								br.addActionListener(new Boutontest());
								br.setBackground(Color.white);
								//b.setBorderPainted(false);
								br.setPreferredSize(new Dimension(500,30));
								br.setFont(f3);
								n4.add(br);
								//Box hb=Box.createHorizontalBox();
								//hb.add(n4);
								//b.setPreferredSize(dim);
								conteneur2.add(n4);
								conteneur2.add(new Separateur(50,5));							    
								
							}
							
							
						}
						prepare.close();
						conn.close();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
			}

			}
			Devis.this.removeAll();
			Devis.this.add(conteneur);
			Devis.this.updateUI();
		}
	}
	
	
	class Boutontest implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			
			Object b=arg0.getSource();
			String s=new String();
			s=((Bouton)b).getMat();
			System.out.println(s);
			Devis.this.removeAll();
			JLabel titre1=new JLabel("Details Devis");
			titre1.setFont(f);
			JPanel panTitre=new JPanel();
			panTitre.add(titre1);
			Box boxTitre1=Box.createHorizontalBox();
			boxTitre1.add(panTitre);
			
			Box boxfirst=Box.createVerticalBox();
			
			
			Box boxPuht=Box.createVerticalBox();
			Box boxDesignation=Box.createVerticalBox();
			//conteneur=Box.createVerticalBox();
			//titre.setFont(f);
			
			JLabel designation=new JLabel("Designation");
			designation.setFont(f1);
			JPanel panmatri=new JPanel();
			
			panmatri.setPreferredSize(dim11);
			panmatri.add(designation);
			panmatri.setBackground(Color.white);
			//designation.setPreferredSize(dim);
			boxDesignation.add(panmatri);
			JLabel puht=new JLabel("Puht");
			puht.setFont(f1);
			JPanel panpnm=new JPanel();
			panpnm.setPreferredSize(dim1);
			panpnm.add(puht);
			panpnm.setBackground(Color.white);
			boxPuht.add(panpnm);
			
			
			DecimalFormat df=new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(1);
			df.setDecimalSeparatorAlwaysShown(true);
			double sommeht=0;
			double sommett=0;
			
			/*JLabel detailContact=new JLabel("Service du Devis");
			detailContact.setFont(f);
			detailContact.setForeground(couleurEcriture);
			JPanel panDetail=new JPanel();
			panDetail.add(detailContact);
			Box boxDetail=Box.createHorizontalBox();
			boxDetail.add(panDetail);*/
			//Box total2h=Box.createVerticalBox();
			//total2.add(boxDetail);
			//Box total2=Box.createHorizontalBox();
			Box hbp=Box.createVerticalBox();
			JTextArea cl=new JTextArea();
			
			JTextArea cl1=new JTextArea();
			
			
			Box header=Box.createHorizontalBox();
			Box hleft=Box.createVerticalBox();
			Box hrig=Box.createVerticalBox();
			
			LocalDate datenow=LocalDate.now();
			JLabel dt=new JLabel("Date: "+datenow.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
			cl.setFont(f3);
			cl1.setFont(f3);
			dt.setFont(f3);
			
			JLabel cl2=new JLabel("Devis N° FR-"+s);
			cl2.setFont(f3);
			datenow=datenow.plusDays(30);
			JLabel dt1=new JLabel("Echeance: "+datenow.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
			dt1.setFont(f3);
			
			JLabel rcs=new JLabel("RCS: 888209228");
			rcs.setFont(f3);
			JLabel mail=new JLabel("Mail: e.koukendif@free.fr");
			mail.setFont(f3);
			JLabel tele=new JLabel("Tel: 0952995781");
			tele.setFont(f3);
			JLabel port=new JLabel("Port: 0622978704");
			port.setFont(f3);
			JLabel site=new JLabel("Site: KENDIF.com");
			site.setFont(f3);
			
			
			JPanel hleft1=new JPanel();
			hleft.add(cl);
			//hleft.add(new Separateur(2,2));
			hleft.add(cl1);
			Box hleft11=Box.createVerticalBox();
			JPanel hleft112=new JPanel();
			hleft112.setLayout(new BorderLayout());
			hleft112.setBackground(Color.white);
			hleft.add(new Separateur(250,2));
			hleft11.add(cl2);
			hleft11.add(dt);
			hleft11.add(dt1);
			hleft11.add(site);
			hleft11.add(port);
			hleft11.add(tele);
			hleft112.add(hleft11,BorderLayout.WEST);
			hleft.add(hleft112);
			hleft1.setLayout(new BorderLayout());
			hleft1.add(hleft,BorderLayout.WEST);
			hleft1.setPreferredSize(new Dimension(300,50));
			hleft1.setBackground(Color.white);
			
			ImageIcon image;
			image = new ImageIcon("app/image/image2.png");
			JLabel labelImage=new JLabel();
			labelImage.setIcon(image);
			
			JLabel cl4=new JLabel("Batiment 2 entree 4 marseille 13010 ");
			cl4.setFont(f3);
			JLabel cl3=new JLabel("Adresse: 66, chemin de la valbarelle ");
			cl3.setFont(f3);
			
			JPanel pi=new JPanel();
			pi.add(hrig);
			pi.setBackground(Color.white);
			
			hrig.add(labelImage);
			hrig.add(cl3);
			hrig.add(cl4);
			hrig.add(mail);
			hrig.add(rcs);
			//hrig.add(tele);
			
			header.add(hleft1);
			header.add(pi);

			
			
			JLabel det2=new JLabel("Devis ");
			det2.setFont(f2);
			JPanel pandet2=new JPanel();
			pandet2.add(det2);
			Box boxdet2=Box.createHorizontalBox();
			boxdet2.add(pandet2);
			
			JLabel det1=new JLabel("Total ");
			det1.setFont(f2);
			JPanel pandet1=new JPanel();
			pandet1.add(det1);
			Box boxdet1=Box.createHorizontalBox();
			boxdet1.add(pandet1);
			
			JLabel det=new JLabel("Details ");
			det.setFont(f2);
			JPanel pandet=new JPanel();
			pandet.add(det);
			Box boxdet=Box.createHorizontalBox();
			boxdet.add(pandet);
			
			
			
			
			//Box bo=Box.createHorizontalBox();
			hbp.add(boxdet2);
			hbp.add(new Separateur(10,20));
			hbp.add(header);
			hbp.add(new Separateur(10,20));
			hbp.add(boxdet);
			double dtva=0;

			double somme=0;
			
			try
			{
				Class.forName("org.sqlite.JDBC");
				System.out.println("Driver O.K.");
				String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
				String user = "aysha";
				String passwd = "kuni";
				Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
				System.out.println("Connexion effective !");
				//Création d'un objet Statement
				
				Statement state = conn.createStatement();
				//On crée la requête
				ResultSet res,res1,res2,res4,res3,res5;
				PreparedStatement prepare,prepare1,prepare2,prepare3,prepare4,prepare5;
				
					String stri="SELECT * FROM contenu_devis where numero_devis=?";
					String stri1="SELECT * FROM service where code_service=?";
					String stri2="SELECT * FROM client where code_client=?";
					String stri3="SELECT matricule_employe FROM affectation_tache where code_service=? and numero_contrat=?";
					String stri4="SELECT nom_employe FROM employe where matricule_employe=?";
					String stri5="SELECT numero_contrat FROM contrat where numero_devis=?";
					//Date date =  cdate.getTi
					
					prepare2 = conn.prepareStatement(stri2);
					prepare3 = conn.prepareStatement(stri3);
					prepare4 = conn.prepareStatement(stri4);
					prepare5 = conn.prepareStatement(stri5);
					//On remplace le premier trou par le nom du professeur
					prepare2.setString(1,listeDevis.get(s)[0]);
					res2=prepare2.executeQuery();
					
					
					res2.next();
					
					JLabel prenomEmp,matEmp,dateNaissance;
					prenomEmp=new JLabel("Nom client   "+res2.getObject(2).toString());
					prenomEmp.setFont(f1);
					//prenomEmp.setForeground(couleurEcriture);
					JPanel panprnm=new JPanel();
					panprnm.setLayout(new BorderLayout());
					panprnm.setPreferredSize(new Dimension(400,20));
					panprnm.add(prenomEmp,BorderLayout.WEST);
					panprnm.setBackground(Color.white);
					boxfirst.add(panprnm);
					boxfirst.add(new Separateur(600,10));
					matEmp=new JLabel("Code devis: "+s);
					//matEmp.setForeground(couleurEcriture);
					matEmp.setFont(f1);
					JPanel panmatemp=new JPanel();
					panmatemp.setLayout(new BorderLayout());
					panmatemp.setPreferredSize(new Dimension(400,20));
					panmatemp.add(matEmp,BorderLayout.WEST);
					panmatemp.setBackground(Color.white);
					boxfirst.add(panmatemp);
					boxfirst.add(new Separateur(600,10));
					dateNaissance=new JLabel("Date : "+listeDevis.get(s)[1]);
					//dateNaissance.setForeground(couleurEcriture);
					dateNaissance.setFont(f1);
					JPanel pandte=new JPanel();
					pandte.setLayout(new BorderLayout());
					pandte.setPreferredSize(new Dimension(400,20));
					pandte.add(dateNaissance,BorderLayout.WEST);
					pandte.setBackground(Color.white);
					boxfirst.add(pandte);
					boxfirst.add(new Separateur(600,10));
					
					
					
					prepare = conn.prepareStatement(stri);
					//On remplace le premier trou par le nom du professeur
					prepare.setString(1,s);
					res=prepare.executeQuery();
					
					prepare1 = conn.prepareStatement(stri1);
					//On remplace le premier trou par le nom du professeur
					
					
					//state.executeQuery(stri);
					//JLabel []serv=new JLabel[100];
					
					
					Statement state9 = conn.createStatement();
					//L'objet ResultSet contient le résultat de la requête SQL
					ResultSet result9 = state9.executeQuery("SELECT * FROM TVA");
					if(result9.next())
					{
						dtva=Double.valueOf(result9.getObject(1).toString());
					}
					result9.close();
					state9.close();
					
					
					int i=1;
					while(res.next())
					{
						prepare1.setString(1,res.getObject(1).toString());
						res1=prepare1.executeQuery();
						if(res1.next())
						{
							somme+=Double.valueOf(res1.getObject(4).toString());
							//sommeht=Double.valueOf(res1.getObject(4).toString());
							JLabel l2=new JLabel(res1.getObject(4).toString());
							sommeht+=Double.valueOf(res1.getObject(4).toString());
							sommett+=Double.valueOf(res1.getObject(4).toString())*dtva/100;
							//double ss=Double.valueOf(res1.getObject(4).toString())*dtva/100+Double.valueOf(res2.getObject(4).toString());
						
							JPanel n2=new JPanel();
							//n2.setPreferredSize(dim);
							
							l2.setFont(f3);
							n2.add(l2);
							
							boxPuht.add(n2);
							boxPuht.add(new Separateur(180,5));
						
							
							
							/*serv[i-1] = new JLabel("Service"+i+" : ");
							serv[i-1].setFont(f1);
							serv[i-1].setForeground(couleurEcriture);*/
							JTextArea servi= new JTextArea(res1.getObject(2).toString());
							servi.setFont(f3);
							servi.setEditable(false);
							servi.setLineWrap(true);
							servi.setWrapStyleWord(true);
							
							
							JScrollPane scrollqual=new JScrollPane(servi);
							scrollqual.setPreferredSize(new Dimension(400,250));
							scrollqual.setBorder(null);
							
							
							JLabel empl=new JLabel();
							empl.setFont(f1);
							prepare5.setString(1,s);
							res5=prepare5.executeQuery();
							if(res5.next())
							{	
								prepare3.setString(1,res.getObject(1).toString());
								prepare3.setString(2,res5.getObject(1).toString());
								res3=prepare3.executeQuery();
								if(res3.next())
								{
									prepare4.setString(1,res3.getObject(1).toString());
									res4=prepare4.executeQuery();
									if(res4.next())
									{
										empl.setText("affecte a "+res4.getObject(1).toString());
									}
									
								}
								else
								{
									empl.setText("Non affecte");
								}
							}
							else
							{
								empl.setText("Non affecte");
							}
							empl.setForeground(Color.white);
							//nomIn=new JTextField("code client");
							JPanel pou=new JPanel();
							pou.setPreferredSize(new Dimension(5,5));
							
							
							
							
							cl.setText("Client: "+res2.getObject(2).toString());
							cl.setEditable(false);
							cl.setLineWrap(true);
							cl.setWrapStyleWord(true);
							cl1.setText("Adress: "+res2.getObject(3).toString());
							cl1.setEditable(false);
							cl1.setLineWrap(true);
							cl1.setWrapStyleWord(true);
							JPanel n1=new JPanel();
							//n1.setPreferredSize(dim);
							//JLabel l1=new JLabel(res2.getObject(2).toString());
							//l1.setFont(f3);
							//n1.setLayout(new BorderLayout());
							n1.add(scrollqual);
							
							boxDesignation.add(n1);
							boxDesignation.add(new Separateur(300,5));
							//hbo.add(bo);
							
							
							
							n1.setBackground(Color.white);
							n2.setBackground(Color.white);
							
							
							if(res1.getObject(2).toString().length()>=1 && res1.getObject(2).toString().length()<=50)
							{
								dim=new Dimension(400,50);
								dim3=new Dimension(148,50);
								n1.setPreferredSize(dim);
								n2.setPreferredSize(dim3);
							}
							if(res1.getObject(2).toString().length()>50 && res1.getObject(2).toString().length()<=100)
							{
								dim=new Dimension(400,100);
								dim3=new Dimension(148,100);
								n1.setPreferredSize(dim);
								n2.setPreferredSize(dim3);
							}
							if(res1.getObject(2).toString().length()>100)
							{
								dim=new Dimension(400,250);
								dim3=new Dimension(148,250);
								n1.setPreferredSize(dim);
								n2.setPreferredSize(dim3);
							}
							
							
							res1.close();
							i++;
						}
					}
					prepare1.close();
					prepare.close();
					res.close();
					prepare2.close();
					res2.close();
					conn.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			
			JPanel tt=new JPanel();
			tt.setLayout(new GridLayout(3,2));
			
			JPanel n1=new JPanel();
			n1.setPreferredSize(dim1);
			JLabel t1=new JLabel("Total ht: ");
			t1.setFont(f3);
			n1.add(t1);
			tt.add(n1);
			
			JPanel n2=new JPanel();
			n2.setPreferredSize(dim1);
			JLabel t2=new JLabel(String.valueOf(df.format(sommeht))+" euro");
			t2.setFont(f3);
			n2.add(t2);
			tt.add(n2);
			
			JPanel n3=new JPanel();
			n3.setPreferredSize(dim1);
			JLabel t3=new JLabel("Total tva: ");
			t3.setFont(f3);
			n3.add(t3);
			tt.add(n3);
			
			JPanel n4=new JPanel();
			n4.setPreferredSize(dim1);
			JLabel t4=new JLabel(String.valueOf(df.format(sommett))+" euro");
			t4.setFont(f3);
			n4.add(t4);
			tt.add(n4);
			
			JPanel n5=new JPanel();
			n5.setPreferredSize(dim1);
			JLabel t5=new JLabel("Total ttc: ");
			t5.setFont(f3);
			n5.add(t5);
			tt.add(n5);
			
			JPanel n6=new JPanel();
			n6.setPreferredSize(dim1);
			JLabel t6=new JLabel(String.valueOf(df.format(sommeht+sommett))+" euro");
			t6.setFont(f1);
			n6.add(t6);
			tt.add(n6);
			
			
			
			Box bo=Box.createHorizontalBox();
			bo.add(boxDesignation);
			bo.add(boxPuht);
			hbp.add(bo);
			hbp.add(new Separateur(10,20));
			hbp.add(boxdet1);
			hbp.add(new Separateur(10,20));
			hbp.add(tt);
			hbp.add(new Separateur(10,20));
			
			
			JPanel panbo=new JPanel();
			panbo.add(hbp);
			panbo.setBackground(Color.white);
			JScrollPane jsbo=new JScrollPane(panbo);
			jsbo.setPreferredSize(new Dimension(500,450));
			
			/*JLabel som=new JLabel("Prix du devis "+String.valueOf(somme)+" euro");
			som.setFont(f);
			som.setForeground(Color.green);
			Box boxsom=Box.createHorizontalBox();
			boxsom.add(som);*/
			//hbo.add(boxsom);
			
			Bouton jbi=new Bouton("Telecharger");
			try {
				jbi.addActionListener(new Telecharger(panbo,Devis.this));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Bouton boutonSuivant=new Bouton("transformer en contrat>>",s,1);
			boutonSuivant.addActionListener(new BoutonContrat());
			boutonSuivant.setPreferredSize(new Dimension(220,20));
			boutonSuivant.setFont(f3);
			Bouton boutonPrecedent=new Bouton("<<Precedent");
			boutonPrecedent.setPreferredSize(new Dimension(120,20));
			boutonPrecedent.setFont(f3);
			Bouton boutonModifier=new Bouton("Modifier",s,1);
			boutonModifier.setPreferredSize(new Dimension(120,20));
			//boutonModifier.setFont(f3);
			//boutonModifier.setBackground();
			//boutonModifier.addActionListener(new BoutonModifier());
			JPanel panSwitch=new JPanel();
			//panSwitch.add(boutonPrecedent);
		//	panSwitch.add(boutonModifier);
			panSwitch.add(boutonSuivant);
			//panSwitch.add(new Separateur(5,5));
			Box boxSwitch=Box.createHorizontalBox();
			boxSwitch.add(panSwitch);
			
			Bouton boutonRetour=new Bouton("Retour");
			
			panSwitch.add(boutonRetour);
			
			boutonRetour.setPreferredSize(new Dimension(120,20));
			boutonRetour.setFont(f3);
			JPanel panRetour=new JPanel();
			panRetour.add(jbi);
			boutonRetour.addActionListener(new BoutonRetour());
			
			
			Box retourPayer=Box.createHorizontalBox();
			retourPayer.add(panRetour);
			
			Box contain=Box.createVerticalBox();
			contain.add(boxTitre1);
			contain.add(new Separateur(600,10));
			contain.add(boxfirst);
			contain.add(new Separateur(600,10));
			//contain.add(boxDetail);
			//contain.add(new Separateur(600,10));
			contain.add(jsbo);
			//contain.add(new Separateur(600,10));
			//contain.add(boxsom);
			contain.add(new Separateur(600,10));
			contain.add(boxSwitch);
			contain.add(new Separateur(600,10));
			contain.add(retourPayer);
			/*try {
				jbi.addActionListener(new Telecharger(panbo,Devis.this));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			
			Devis.this.add(contain);
			Devis.this.updateUI();
			
		}
		

		

		
	}
	
	
	
	
	

	
	
	
	
	class BoutonContrat implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Object b=arg0.getSource();
			String s=new String();
			s=((Bouton)b).getMat();
			Devis.this.removeAll();
			JLabel titre1=new JLabel("Contrat");
			titre1.setFont(f);
			JPanel panTitre=new JPanel();
			panTitre.add(titre1);
			Box boxTitre1=Box.createHorizontalBox();
			boxTitre1.add(panTitre);
			
			JLabel labDev = new JLabel("Numero Devis: ");
			labDev.setFont(f1);
			labDev.setForeground(couleurEcriture);
			numDev=new JTextField(s);
			numDev.setEditable(false);
			Box boxDev=Box.createHorizontalBox();
			boxDev.add(labDev);
			boxDev.add(numDev);
			
			JLabel labCon = new JLabel("Numero Contrat: ");
			labCon.setFont(f1);
			labCon.setForeground(couleurEcriture);
			if(mcon=="-1")
			{
				mcon="001";
				numCon=new JTextField(mcon);
			}
			else
			{
				int i=Integer.valueOf(mcon);
				mcon=String.valueOf(i+1);
				numCon=new JTextField(mcon);
			}
			numCon.setEditable(false);
			Box boxCon=Box.createHorizontalBox();
			boxCon.add(labCon);
			boxCon.add(numCon);
			
			JLabel dateDeb = new JLabel("Date de debut: ");
			dateDeb.setForeground(couleurEcriture);
			dateDeb.setFont(f1);
			Bouton cr=new Bouton("choisir date de debut");
			cr.setPreferredSize(new Dimension(200,30));
			cr.addActionListener(new ChoixDate1());
			Box boxdte=Box.createHorizontalBox();
			boxdte.add(dateDeb);
			//boxdte.add(dteIn);
			Separateur paneldater=new Separateur(100,40);
			paneldater.add(cr);
			boxdte.add(paneldater);
			
			JLabel dateDeb1 = new JLabel("Date de fin: ");
			dateDeb1.setForeground(couleurEcriture);
			dateDeb1.setFont(f1);
			Bouton cr1=new Bouton("choisir date de fin");
			cr1.setPreferredSize(new Dimension(200,30));
			cr1.addActionListener(new ChoixDate2());
			Box boxdte1=Box.createHorizontalBox();
			boxdte1.add(dateDeb1);
			//boxdte.add(dteIn);
			Separateur paneldater1=new Separateur(100,40);
			paneldater1.add(cr1);
			boxdte1.add(paneldater1);
			
			JLabel labremp=new JLabel("Rempli :");
			labremp.setForeground(couleurEcriture);
			labremp.setFont(f1);
			rempli=new ComboNew();
			rempli.addItem("faux");
			rempli.setEnabled(false);
			rempli.setFont(f1);
			/*Box boxremp=Box.createHorizontalBox();
			boxremp.add(labremp);
			boxremp.add(rempli);*/
			Bouton boutonRetour=new Bouton("Annuler");
			JPanel panRetour=new JPanel();
			panRetour.add(boutonRetour);
			boutonRetour.addActionListener(new BoutonRetour());
			
			Bouton boutonValider=new Bouton("Valider");
			panRetour.add(boutonValider);
			boutonValider.addActionListener(new BoutonValiderContrat());
			
			
			
			Box retourPayer=Box.createHorizontalBox();
			retourPayer.add(panRetour);
			
			Box boxPrincipal=Box.createVerticalBox();
			
			boxPrincipal.add(boxTitre1);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxDev);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxCon);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxdte);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxdte1);
			boxPrincipal.add(new Separateur(600,20));
		//	boxPrincipal.add(boxremp);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(retourPayer);
			Devis.this.add(boxPrincipal);
			Devis.this.updateUI();
			
		}
	}
	
	
	class BoutonValiderContrat implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			if(numCon.getText().length()==0 || numCon.getText().length()>30)
			{
				JOptionPane jop3 = new JOptionPane();
				jop3.showMessageDialog(null, "ERREUR SUR numero contrat", "Erreur", JOptionPane.ERROR_MESSAGE);
				
			}
			else
			{
				try
				{
					Class.forName("org.sqlite.JDBC");
					System.out.println("Driver O.K.");
					String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
					String user = "aysha";
					String passwd = "kuni";
					Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
					System.out.println("Connexion effective 1111!");
					//Création d'un objet Statement
					//Statement state = conn.createStatement();
					//On crée la requête
					PreparedStatement prepare;
					String s="insert into CONTRAT (NUMERO_CONTRAT, NUMERO_DEVIS, DATE_DEBUT, DATE_FIN,RECURRENCE, REMPLI) ";
							s+=" values (?,?,?,?,1,false)";
					//Date date =  cdate.getTi
					prepare = conn.prepareStatement(s);
					//On remplace le premier trou par le nom du professeur
					prepare.setString(1,numCon.getText());
					prepare.setString(2,numDev.getText());
					prepare.setString(3,new java.sql.Date(debCon.getDate().getTime()).toString());
					prepare.setString(4, new java.sql.Date(finCon.getDate().getTime()).toString());
					prepare.executeUpdate();
					prepare.close();
					conn.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
					
				
			}
			Devis.this.removeAll();
			Devis.this.add(conteneur);
			Devis.this.updateUI();
		}
	}
	
	class BoutonVoirContrat implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Box conteneur3=Box.createVerticalBox();
			JTextField recherche1=new JTextField("votre text");
			recherche1.setPreferredSize(new Dimension(200,25));
			JLabel textRecherche1=new JLabel("recherche: ");
			textRecherche1.setForeground(couleurEcriture);
			textRecherche1.setFont(f1);
			Bouton boutonRecherche1=new Bouton("ok");
			boutonRecherche1.setPreferredSize(new Dimension(60,25));
			//boutonRecherche.setBackground(Color.gray);
			JPanel panelRecherche=new JPanel();
			//panelRecherche.setBackground(c);
			panelRecherche.add(textRecherche1);
			panelRecherche.add(recherche1);
			panelRecherche.add(boutonRecherche1);
			Box boxRecherche1=Box.createHorizontalBox();
			boxRecherche1.add(panelRecherche);
			
			
			JLabel titre1=new JLabel("Voir Contrat");
			titre1.setFont(f);
			JPanel panTitre=new JPanel();
			panTitre.add(titre1);
			Box boxTitre1=Box.createHorizontalBox();
			boxTitre1.add(panTitre);
			conteneur3.add(boxTitre1);
			conteneur3.add(boxRecherche1);
			Box conteneur4=Box.createVerticalBox();
			try
			{
				Class.forName("org.sqlite.JDBC");
				System.out.println("Driver O.K.");
				String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
				String user = "aysha";
				String passwd = "kuni";
				Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
				System.out.println("Connexion effective !");
				//Création d'un objet Statement
				Statement state = conn.createStatement();
				//L'objet ResultSet contient le résultat de la requête SQL
				ResultSet result = state.executeQuery("SELECT * FROM contrat where rempli=false");
				//On récupère les MetaData
				ResultSet res1,res;
				ResultSetMetaData resultMeta = result.getMetaData();
				//On affiche le nom des colonnes
				
				PreparedStatement prepare,prepare1;
				String str="SELECT code_client FROM devis where numero_devis=?";
				String str1="SELECT nom_client FROM client where code_client=?";
				//Date date =  cdate.getTi
				prepare = conn.prepareStatement(str);
				prepare1 = conn.prepareStatement(str1);
				
				
				int cp=0;
				while(result.next())
				{
					String []s=new String[resultMeta.getColumnCount()-1];
					for(int i = 2; i <= resultMeta.getColumnCount(); i++)
					{
						if(result.getString(i)!=null)
							s[i-2]=new String(result.getString(i));
					}
					
					prepare.setString(1,result.getString(2).toString() );
					res=prepare.executeQuery();
					res.next();
					prepare1.setString(1, res.getObject(1).toString());
					res1=prepare1.executeQuery();
					
					//listeDevis.put(result.getObject(1).toString(),s ) ;
					JPanel n4=new JPanel();
					//n4.setPreferredSize(new Dimension(600,40));
					//n4.setBackground(Color.gray);
					JLabel b=new JLabel();
					res1.next();
					b.setText("Contrat du  devis avec  "+res1.getObject(1).toString());
					//b.addActionListener(new Boutontest());
					b.setBackground(Color.white);
					//b.setBorderPainted(false);
					b.setPreferredSize(new Dimension(300,30));
					b.setFont(f3);
					n4.add(b);
					Bouton rep=new Bouton("rempli",result.getObject(1).toString(),1);
					rep.addActionListener(new BoutonRempli());
					rep.setPreferredSize(dim1);
					Bouton rep1=new Bouton("supprimer",result.getObject(1).toString(),1);
					rep1.addActionListener(new BoutonSupprimerContrat());
					rep1.setPreferredSize(dim1);
					n4.add(rep);
					n4.add(rep1);
					Box hb=Box.createHorizontalBox();
					hb.add(n4);
					//b.setPreferredSize(dim);
					conteneur4.add(hb);
					conteneur4.add(new Separateur(50,5));
					//recherche=testAutocomplete.createAutoCompleteTextField(saut);
					
				}
				result.close();
				state.close();
				conn.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			JPanel conteneur5=new JPanel();
			conteneur5.add(conteneur4);
			JScrollPane njs=new JScrollPane(conteneur5);
			njs.setPreferredSize(new Dimension(600,500));
			conteneur3.add(njs);
			Bouton boutonRetour=new Bouton("Retour");
			JPanel panRetour=new JPanel();
			panRetour.add(boutonRetour);
			boutonRetour.addActionListener(new BoutonRetour());
			conteneur3.add(panRetour);
			Devis.this.removeAll();
			Devis.this.add(conteneur3);
			Devis.this.updateUI();
			
		}
	}
	
	
	class BoutonSupprimerContrat implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Object b=arg0.getSource();
			String id=new String();
			id=((Bouton)b).getMat();
			try
			{
				Class.forName("org.sqlite.JDBC");
				System.out.println("Driver O.K.");
				String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
				String user = "aysha";
				String passwd = "kuni";
				Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
				System.out.println("Connexion effective !");
				//Création d'un objet Statement
				PreparedStatement prepare;
				String s = " delete  from  contrat  where numero_contrat=?";
				//Date date =  cdate.getTi
				prepare = conn.prepareStatement(s);
				//On remplace le premier trou par le nom du professeur
				prepare.setString(1,id);
				prepare.executeUpdate();
				prepare.close();
				conn.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			Devis.this.removeAll();
			Devis.this.add(conteneur);
			Devis.this.updateUI();
		}
	}
	
	
	class BoutonRempli implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Object bout=arg0.getSource();
			String id=new String();
			id=((Bouton)bout).getMat();
			try
			{
				Class.forName("org.sqlite.JDBC");
				System.out.println("Driver O.K.");
				String url = "jdbc:postgresql://localhost:5432/gestionnettoyage";
				String user = "aysha";
				String passwd = "kuni";
				Connection conn = DriverManager.getConnection("jdbc:sqlite:gestionnettoyage.db");
				System.out.println("Connexion effective !");
				//Création d'un objet Statement
				PreparedStatement prepare;
				String s = " update  contrat set rempli=true where numero_contrat=?";
				//Date date =  cdate.getTi
				prepare = conn.prepareStatement(s);
				//On remplace le premier trou par le nom du professeur
				prepare.setString(1,id);
				prepare.executeUpdate();
				prepare.close();
				conn.close();
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			/*Devis.this.removeAll();
			Devis.this.add(conteneur3);
			Devis.this.updateUI();*/
			Devis.this.removeAll();
			Devis.this.add(conteneur);
			Devis.this.updateUI();
			
			
		}
	}
	
	
	
	class ItemState implements ItemListener
	{
		public void itemStateChanged(ItemEvent e) 
		{
			System.out.println("événement déclenché sur : " + e.getItem());
		}
	}

	
	class ChoixDate implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			 // calendrier (JPanel)
			 
			JDialog d = new JDialog(); // fenêtre
			d.setTitle("Choisir une date");
			d.setModalityType(ModalityType.APPLICATION_MODAL);
			d.add(cdate);
			d.pack();
			d.setLocationRelativeTo(Devis.this);
			d.setVisible(true);

			/*Date date = c.getCalendar().getTime(); // on récupère la date

			/* on affiche la date dans le label 
			Locale locale = Locale.getDefault();
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
			label.setText(dateFormat.format(date));*/

			
		}
		
	}
	
	class ChoixDate1 implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			 // calendrier (JPanel)
			 
			JDialog d = new JDialog(); // fenêtre
			d.setTitle("Choisir une date");
			d.setModalityType(ModalityType.APPLICATION_MODAL);
			d.add(debCon);
			d.pack();
			d.setLocationRelativeTo(Devis.this);
			d.setVisible(true);

			/*Date date = c.getCalendar().getTime(); // on récupère la date

			/* on affiche la date dans le label 
			Locale locale = Locale.getDefault();
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
			label.setText(dateFormat.format(date));*/

			
		}
		
	}

	class ChoixDate2 implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			 // calendrier (JPanel)
			 
			JDialog d = new JDialog(); // fenêtre
			d.setTitle("Choisir une date");
			d.setModalityType(ModalityType.APPLICATION_MODAL);
			d.add(finCon);
			d.pack();
			d.setLocationRelativeTo(Devis.this);
			d.setVisible(true);

			/*Date date = c.getCalendar().getTime(); // on récupère la date

			/* on affiche la date dans le label 
			Locale locale = Locale.getDefault();
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
			label.setText(dateFormat.format(date));*/

			
		}
		
	}
	
	

}
