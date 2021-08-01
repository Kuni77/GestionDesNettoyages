import java.awt.BorderLayout;
import java.awt.Color;



import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
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
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import test.Telecharger;




public class Facture extends JPanel 
{
	private Hashtable<String, String[]> listeFacture;
	private Hashtable<String, String> listeCl;
	private Hashtable<String, String> listeDv;
	private JTextField recherche;
	private Box conteneur, boxTitre,conteneur2,boxRecherche ;
	private JLabel titre,textRecherche,ltva;
	private Color c;
	private JPanel conteneur1;
	private JScrollPane scroller;
	private Font f,f1,f2,f3;
	private Color couleurEcriture;
	private Dimension dim,dim1,dim2,dim3;
	private Bouton boutonRecherche;
	private double dtva;

	
	public Facture()
	{
		couleurEcriture=new Color(242,153,186,255);
		c=new Color(161,8,73,255);
		
		dim=new Dimension(148,80);
		dim1=new Dimension(148,35);
		//dim3=new Dimension(148,40);
		//dim2=new Dimension(148,65);
		f=new Font("Arial", Font.BOLD, 30);
		f1=new Font("Arial", Font.BOLD, 20);
		f3=new Font("Arial", Font.BOLD, 15);
		f2=new Font("Arial", Font.BOLD, 25);
		titre = new JLabel("Facture");
		listeFacture=new Hashtable<String, String[]>();
		listeCl=new Hashtable<String, String>();
		listeDv=new Hashtable<String, String>();
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
			
			
			
			Statement state8 = conn.createStatement();
			String tabcli1="create table IF NOT EXISTS TVA ("+
					   "tva      REAL          not null,"+
					   "constraint PK_FACTURE primary key (tva)"+
					")";




			state8.addBatch(tabcli1);
			state8.executeBatch();
			state8.clearBatch();
			state8.close();
			
			
			Statement state7 = conn.createStatement();
			String tabcli="create table IF NOT EXISTS FACTURE ("+
					   "NUMERO_FACTURE       VARCHAR(30)          not null,"+
					   "NUMERO_CONTRAT       VARCHAR(30)          null,"+
					   "MONTANT_RABAIS       REAL                null,"+
					   "TAUX_TVA             REAL               null,"+
					   "DATE_FACTURE         TEXT                 null,"+
					   "constraint PK_FACTURE primary key (NUMERO_FACTURE)"+
					")";




			state7.addBatch(tabcli);
			state7.executeBatch();
			state7.clearBatch();
			state7.close();
			
			//Création d'un objet Statement
			Statement state9 = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result9 = state9.executeQuery("SELECT * FROM TVA");
			if(!result9.next())
			{
				Statement state10 = conn.createStatement();
				//L'objet ResultSet contient le résultat de la requête SQL
				state10.executeQuery("insert into TVA (tva) values(20)");
				ltva = new JLabel("TVA : 20%");
				dtva=20;
				state10.close();
			}
			else
			{
				ltva = new JLabel("TVA : "+result9.getObject(1).toString()+"%");
				dtva=Double.valueOf(result9.getObject(1).toString());
			}
			result9.close();
			state9.close();
			
			Statement state = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery("SELECT * FROM contrat where rempli=true");
			ResultSet res,res1;
			//On récupère les MetaData
			ResultSetMetaData resultMeta = result.getMetaData();
			//On affiche le total des colonnes
			
			PreparedStatement prepare,prepare1;
			String str="SELECT nom_client FROM client where code_client=?";
			String str1="SELECT code_client FROM devis where numero_devis=?";
			
			//Date date =  cdate.getTi
			prepare = conn.prepareStatement(str);
			prepare1 = conn.prepareStatement(str1);
			//On remplace le premier trou par le total du professeur
			
			
			
			int cp=0;
			while(result.next())
			{
				String []s=new String[resultMeta.getColumnCount()+1];
				int i;
				for(i = 2; i <= resultMeta.getColumnCount(); i++)
				{
					if(result.getString(i)!=null)
						s[i-2]=new String(result.getString(i));
				}
				prepare1.setString(1,result.getObject(2).toString());
				res1=prepare1.executeQuery();
				res1.next();
				
				prepare.setString(1,res1.getObject(1).toString());
				res=prepare.executeQuery();
				res.next();
				
				s[i-2]=new String(res.getObject(1).toString());
				s[i-2+1]=new String(res1.getObject(1).toString());
				listeFacture.put(result.getObject(1).toString(),s ) ;
				listeCl.put(res1.getObject(1).toString(), result.getObject(1).toString());
				listeDv.put(result.getObject(2).toString(), result.getObject(1).toString());
				
				
				JPanel n4=new JPanel();
				//n4.setPreferredSize(dim);
				n4.setBackground(Color.white);
				Bouton b=new Bouton("Facture du contrat: "+" avec  "+res.getObject(1).toString()+"   du  "+result.getObject(3).toString(),result.getObject(1).toString());
				//listeFacture.put(s, res.getObject(1).toString());
				b.addActionListener(new BoutonVoirFacture());
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
			state.close();
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
	//	conteneur.add(boxtva);
		conteneur.add(new Separateur(600,10));
		conteneur.add(scroller);
		
		
		Bouton tva1=new Bouton("TVA");
		tva1.addActionListener(new BoutonTva());
		
		Bouton actu=new Bouton("actualiser");
		actu.addActionListener(new BoutonActualiser());
		Box actub=Box.createHorizontalBox();
		actub.add(tva1);
		//actub.add(new Separateur(10,10));
		ltva.setFont(f3);
		actub.add(ltva);
		actub.add(new Separateur(5,5));
		actub.add(actu);
		
		conteneur.add(actub);
		
		
		this.add(conteneur);
	
		
	}
	
	
	class BoutonTva implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			JOptionPane jop = new JOptionPane();
			String s = jop.showInputDialog(null, "Donner le tva ","Changer TVA", JOptionPane.QUESTION_MESSAGE);
			if(s!=null)
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
					
					String s1="update TVA "
							+ "set tva=?";
							
					//Date date =  cdate.getTi
					prepare = conn.prepareStatement(s1);
					//On remplace le premier trou par le nom du professeur
					prepare.setDouble(1,Double.valueOf(s));
					
					if( prepare.executeUpdate()==0 )
					{
						JOptionPane jop3 = new JOptionPane();
						jop3.showMessageDialog(null, "Erreur d'insertion", "Erreur", JOptionPane.ERROR_MESSAGE);
					}
					else
					{
						JOptionPane jop3 = new JOptionPane();
						jop3.showMessageDialog(null, "Tva change", "Okay", JOptionPane.INFORMATION_MESSAGE);
						ltva.setText("TVA : "+s+"%");
						dtva=Double.valueOf(s);
					}
					prepare.close();
					conn.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}

			}
			else
			{
				JOptionPane jop1 = new JOptionPane();
				jop1.showMessageDialog(null, "vous avez annuler la saisie", "Information", JOptionPane.INFORMATION_MESSAGE);
	
			}
		}
		
	}
	

	class BoutonRecherche implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			if(listeCl.get(recherche.getText())!=null)
			{
				Facture.this.removeAll();
				JLabel tr=new JLabel("Facture Recherche");
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
					{System.out.println(res.getObject(1).toString());
						if(listeDv.get(res.getObject(1).toString())!=null)
						{
							
							recherche.setText(listeDv.get(res.getObject(1).toString()));
							JPanel n4=new JPanel();
							//n4.setPreferredSize(dim);
							n4.setBackground(Color.white);
							Bouton b=new Bouton("Facture du contrat: "+recherche.getText()+"\n"+" avec  "+listeFacture.get(recherche.getText())[5]+"   du  "+listeFacture.get(recherche.getText())[1],recherche.getText());
							
							b.addActionListener(new BoutonVoirFacture());
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
				Facture.this.add(bp);
				Facture.this.updateUI();
	
				
			}
			else
			{
			    JOptionPane jop4 = new JOptionPane();
				jop4.showMessageDialog(null, "aucune facture trouve", "Information", JOptionPane.INFORMATION_MESSAGE);
	
			}
		}
	}

	
	
	
	class BoutonRetour implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Facture.this.removeAll();
			Facture.this.add(conteneur);
			Facture.this.updateUI();
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
				//L'objet ResultSet contient le résultat de la requête SQL
				ResultSet result = state.executeQuery("SELECT * FROM contrat where rempli=true");
				ResultSet res,res1;
				//On récupère les MetaData
				ResultSetMetaData resultMeta = result.getMetaData();
				//On affiche le total des colonnes
				
				PreparedStatement prepare,prepare1;
				String str="SELECT nom_client FROM client where code_client=?";
				String str1="SELECT code_client FROM devis where numero_devis=?";
				
				//Date date =  cdate.getTi
				prepare = conn.prepareStatement(str);
				prepare1 = conn.prepareStatement(str1);
				//On remplace le premier trou par le total du professeur
				
				
				
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
					prepare1.setString(1,result.getObject(2).toString());
					res1=prepare1.executeQuery();
					res1.next();
					
					prepare.setString(1,res1.getObject(1).toString());
					res=prepare.executeQuery();
					res.next();
					
					s[i-2]=new String(res.getObject(1).toString());
					listeFacture.put(result.getObject(1).toString(),s ) ;
					listeCl.put(res1.getObject(1).toString(), result.getObject(1).toString());
					listeDv.put(result.getObject(2).toString(), result.getObject(1).toString());
					
					JPanel n4=new JPanel();
					//n4.setPreferredSize(dim);
					n4.setBackground(Color.white);
					Bouton b=new Bouton("Facture du contrat: "+" avec  "+res.getObject(1).toString()+"   du  "+result.getObject(3).toString(),result.getObject(1).toString());
					//listeFacture.put(s, res.getObject(1).toString());
					b.addActionListener(new BoutonVoirFacture());
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
				state.close();
				conn.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			Facture.this.updateUI();
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
			
			
			Facture.this.removeAll();
			Facture.this.add(conteneur);
			Facture.this.updateUI();
		}
	}
	
	
	
	
	class BoutonVoirFacture implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Object b=arg0.getSource();
			String s=new String();
			s=((Bouton)b).getMat();
			Facture.this.removeAll();
			
			JLabel title=new JLabel("Details sur facture");
			title.setForeground(couleurEcriture);
			title.setFont(f);
			JPanel panelTitle=new JPanel();
			panelTitle.add(title);
			Box boxTitle=Box.createHorizontalBox();
			boxTitle.setBackground(Color.black);
			boxTitle.add(panelTitle);
			Box boxprin=Box.createVerticalBox();
			boxprin.add(boxTitle);
			
			boxprin.add(new Separateur(400,20));
			JLabel nfp=new JLabel("Client: ");
			nfp.setFont(f2);
			JPanel pnfp=new JPanel();
			pnfp.add(nfp);
			JLabel mfp=new JLabel("Contrat: "+s);
			mfp.setFont(f2);
			JPanel pmfp=new JPanel();
			pmfp.add(mfp);
			Box bhfp=Box.createHorizontalBox();
			bhfp.add(pnfp);
			Box bhfp1=Box.createHorizontalBox();
			bhfp1.add(pmfp);
			Box bvfp=Box.createVerticalBox();
			bvfp.add(bhfp);
			bvfp.add(bhfp1);
			boxprin.add(bvfp);
			boxprin.add(new Separateur(400,20));
			
			
			
			Box boxPuht=Box.createVerticalBox();
			Box boxTotal=Box.createVerticalBox();
			Box boxtva=Box.createVerticalBox();
			Box boxDesignation=Box.createVerticalBox();
			//conteneur=Box.createVerticalBox();
			//titre.setFont(f);
			
			JLabel designation=new JLabel("Designation");
			designation.setFont(f1);
			JPanel panmatri=new JPanel();
			panmatri.setPreferredSize(dim1);
			panmatri.add(designation);
			panmatri.setBackground(Color.white);
			//designation.setPreferredSize(dim);
			boxDesignation.add(panmatri);
			JLabel total=new JLabel("Total");
			total.setFont(f1);
			JPanel pantotal=new JPanel();
			pantotal.setPreferredSize(dim1);
			pantotal.setBackground(Color.white);
			//total.setPreferredSize(dim);
			pantotal.add(total);
			boxTotal.add(pantotal);
			JLabel puht=new JLabel("Puht");
			puht.setFont(f1);
			JPanel panpnm=new JPanel();
			panpnm.setPreferredSize(dim1);
			panpnm.add(puht);
			panpnm.setBackground(Color.white);
			boxPuht.add(panpnm);
			//puht.setPreferredSize(dim);
			JLabel tva=new JLabel("tva");
			tva.setFont(f1);
			JPanel pantva=new JPanel();
			pantva.setPreferredSize(dim1);
			pantva.add(tva);
			pantva.setBackground(Color.white);
			//tva.setPreferredSize(dim);
			boxtva.add(pantva);
			JPanel conteneur3=new JPanel();
			JScrollPane scroller1=new JScrollPane(conteneur3);
			scroller1.setPreferredSize(new Dimension(600,450));
			//JScrollPane jsp = new JScrollPane(pimage);
		     // JViewport jvp = new JViewport();
		      //jvp =scroller.getViewport();
		      //jvp.setViewPosition(new Point(30,200));
			conteneur3.setBackground(Color.white);
			
			
			Box header=Box.createHorizontalBox();
			Box hleft=Box.createVerticalBox();
			Box hrig=Box.createVerticalBox();
			JTextArea cl=new JTextArea("Client: ");
			JTextArea cl1=new JTextArea();
			LocalDate datenow=LocalDate.now();
			JLabel dt=new JLabel("Date: "+datenow.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
			cl.setFont(f3);
			cl1.setFont(f3);
			cl.setEditable(false);
			cl.setLineWrap(true);
			cl.setWrapStyleWord(true);
			cl1.setEditable(false);
			cl1.setLineWrap(true);
			cl1.setWrapStyleWord(true);
			
			dt.setFont(f3);
			
			JLabel cl2=new JLabel("Facture N° FR-"+s);
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
			hleft.add(new Separateur(250,2));
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
			JLabel cl3=new JLabel("66, chemin de la valbarelle ");
			cl3.setFont(f3);
			
			JPanel pi=new JPanel();
			pi.add(hrig);
			pi.setBackground(Color.white);
			
			hrig.add(labelImage);
			hrig.add(cl3);
			hrig.add(cl4);
			hrig.add(mail);
			hrig.add(rcs);
			
			
			header.add(hleft1);
			header.add(pi);
			
			
			

			//On récupère les MetaData
		//	ResultSetMetaData resultMeta = result.getMetaData();
			//On affiche le total des colonnes
			boxDesignation.add(new Separateur(140,5));
			boxTotal.add(new Separateur(140,5));
			boxPuht.add(new Separateur(140,5));
			boxtva.add(new Separateur(140,5));
		//	String []saut=new String[20];
			JPanel tt=new JPanel();
			
			DecimalFormat df=new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(1);
			df.setDecimalSeparatorAlwaysShown(true);
			
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
				
				//L'objet ResultSet contient le résultat de la requête SQL
				ResultSet res,res1,res2,res3,res4;
				
				
				
				PreparedStatement prepare,prepare1,prepare2,prepare3,prepare4;
				String str="SELECT numero_devis FROM contrat where numero_contrat=?";
				String str1="SELECT code_service FROM contenu_devis where numero_devis=?";
				String str2="SELECT * FROM service where code_service=?";
				String str3="SELECT * FROM client where code_client=?";
				String str4="SELECT code_client FROM devis where numero_devis=?";
				//Date date =  cdate.getTi
				prepare = conn.prepareStatement(str);
				prepare1 = conn.prepareStatement(str1);
				prepare2 = conn.prepareStatement(str2);
				prepare3=conn.prepareStatement(str3);
				prepare4=conn.prepareStatement(str4);
				
				
				prepare.setString(1,s);
				res=prepare.executeQuery();
				double sommeht=0;
				double sommett=0;
			
				if(res.next())
				{
					prepare4.setString(1,res.getObject(1).toString());
					res4=prepare4.executeQuery();
					prepare3.setString(1,res4.getObject(1).toString());
					res3=prepare3.executeQuery();
					if(res3.next())
					{
						cl1.setText("Adresse: "+res3.getObject(3).toString());
						nfp.setText("Client : "+res3.getObject(2).toString());
						cl.setText("Client : "+res3.getObject(2).toString());
					}
					
					prepare1.setString(1,res.getObject(1).toString());
					res1=prepare1.executeQuery();
					while(res1.next())
					{
						prepare2.setString(1,res1.getObject(1).toString());
						res2=prepare2.executeQuery();
						if(res2.next())
						{
							JPanel n1=new JPanel();
							//n1.setPreferredSize(dim);
							JTextArea l1=new JTextArea(res2.getObject(2).toString());
							l1.setFont(f3);
							l1.setLineWrap(true);
							l1.setEditable(false);
							l1.setWrapStyleWord(true);
							
							JScrollPane scrollqual=new JScrollPane(l1);
							scrollqual.setPreferredSize(new Dimension(400,250));
							scrollqual.setBorder(null);
							
							
							n1.add(scrollqual);
							
							boxDesignation.add(n1);
							boxDesignation.add(new Separateur(140,5));
							JPanel n2=new JPanel();
							//n2.setPreferredSize(dim);
							JLabel l2=new JLabel(res2.getObject(4).toString());
							sommeht+=Double.valueOf(res2.getObject(4).toString());
							sommett+=Double.valueOf(res2.getObject(4).toString())*dtva/100;
							double ss=Double.valueOf(res2.getObject(4).toString())*dtva/100+Double.valueOf(res2.getObject(4).toString());
						
							
							
							l2.setFont(f3);
							n2.add(l2);
							
							boxPuht.add(n2);
							boxPuht.add(new Separateur(140,5));
							JPanel n3=new JPanel();
							//n3.setPreferredSize(dim);
							JLabel l3=new JLabel(String.valueOf(dtva)+"%");
							l3.setFont(f3);
							n3.add(l3);
							
							boxtva.add(n3);
							boxtva.add(new Separateur(140,5));
							JPanel n4=new JPanel();
							//n4.setPreferredSize(dim);
							JLabel bt=new JLabel(String.valueOf(df.format(ss)));
								n1.setBackground(Color.white);
								n2.setBackground(Color.white);
								n3.setBackground(Color.white);
								n4.setBackground(Color.white);
								bt.setBackground(Color.white);
							//b.setBorderPainted(false);
							bt.setFont(f3);
							n4.add(bt);
							//bt.setPreferredSize(dim);
							boxTotal.add(n4);
							boxTotal.add(new Separateur(140,5));
						
							
							if(res2.getObject(2).toString().length()>=1 && res2.getObject(2).toString().length()<=50)
							{
								dim3=new Dimension(400,50);
								dim2=new Dimension(148,50);
								n1.setPreferredSize(dim3);
								n2.setPreferredSize(dim2);
								n3.setPreferredSize(dim2);
								n4.setPreferredSize(dim2);
							}
							if(res2.getObject(2).toString().length()>50 && res2.getObject(2).toString().length()<=100)
							{
								dim3=new Dimension(400,100);
								dim2=new Dimension(148,100);
								n1.setPreferredSize(dim3);
								n2.setPreferredSize(dim2);
								n3.setPreferredSize(dim2);
								n4.setPreferredSize(dim2);
							}
							if(res2.getObject(2).toString().length()>100)
							{
								dim3=new Dimension(400,250);
								dim2=new Dimension(148,250);
								n1.setPreferredSize(dim3);
								n2.setPreferredSize(dim2);
								n3.setPreferredSize(dim2);
								n4.setPreferredSize(dim2);
							}
							
						}
					}
				}
				
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
				
				prepare.close();
				prepare1.close();
				prepare2.close();
				conn.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			Box conteneur7=Box.createHorizontalBox();
			conteneur7.add(boxDesignation);
			conteneur7.add(boxPuht);
			conteneur7.add(boxtva);
			conteneur7.add(boxTotal);
			
			JLabel det2=new JLabel("Facture ");
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
			
			
			
			
			Box hbp=Box.createVerticalBox();
			hbp.add(boxdet2);
			hbp.add(new Separateur(10,20));
			hbp.add(header);
			hbp.add(new Separateur(10,20));
			hbp.add(boxdet);
			hbp.add(new Separateur(10,20));
			hbp.add(conteneur7);
			hbp.add(new Separateur(10,20));
			hbp.add(boxdet1);
			hbp.add(new Separateur(10,20));
			
			hbp.add(tt);
			conteneur3.add(hbp);
			
			Bouton jbi=new Bouton("Telecharger");
			try {
				jbi.addActionListener(new Telecharger(conteneur3,Facture.this));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Bouton jbr=new Bouton("Supprimer",s,1);
			jbr.addActionListener(new BoutonSupprimerContrat());
			
			Bouton jb=new Bouton("retour");
			jb.addActionListener(new BoutonRetour());
			Box bjb=Box.createHorizontalBox();
			bjb.add(jb);
			bjb.add(new Separateur(5,5));
			bjb.add(jbi);
			bjb.add(new Separateur(5,5));
			bjb.add(jbr);
			
			boxprin.add(scroller1);
			boxprin.add(new Separateur(400,20));
			//boxprin.add(tt);
			//boxprin.add(new Separateur(400,20));
			boxprin.add(bjb);
			
			//creerPdf(scroller1,listeFacture.get(s)[5].toString());
			
			Facture.this.add(boxprin);
			Facture.this.updateUI();
			
			
		}
	}
	
	

	
	
}
