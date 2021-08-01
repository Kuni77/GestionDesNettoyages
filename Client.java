import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.validator.routines.EmailValidator;

import test.ComboNew;

public class Client extends JPanel 
{
	private Hashtable<String, String[]> listeClient;
	private JTextField recherche,prenomIn,nomIn,emailIn,telIn,adresseIn;
	private Box boxTitre,boxMatricule,boxNom,boxTelephone,boxBouton,conteneur,boxRecherche;
	private JLabel titre,textRecherche,matricule,nom,prenom,plus;
	private Bouton boutonRecherche;
	private Color c;
	private JPanel conteneur1;
	private JScrollPane scroller;
	private Font f,f1,f2,f3;
	private Color couleurEcriture;
	private Dimension dim,dim1,dim2;
	private int colin=1;
	private ComboNew civilite;
	
	
	public Client()
	{
		super();
		
		civilite=new ComboNew();
		civilite.addItem("M.");
		civilite.addItem("Mme.");
		civilite.addItem("Mlle.");
		civilite.addItem("ent.");
		couleurEcriture=new Color(150,141,141,255);
		c=new Color(228,213,213,255);
		dim=new Dimension(148,80);
		dim1=new Dimension(148,40);
		dim2=new Dimension(148,65);
		f=new Font("Arial", Font.BOLD, 30);
		f1=new Font("Arial", Font.BOLD, 20);
		f3=new Font("Arial", Font.BOLD, 15);
		f2=new Font("Arial", Font.BOLD, 25);
		titre=new JLabel("Liste des clients");
		listeClient=new Hashtable<String, String[]>();
		recherche=new JTextField("numero client");
		recherche.setPreferredSize(new Dimension(200,25));
		textRecherche=new JLabel("recherche: ");
		textRecherche.setFont(f1);
		boutonRecherche=new Bouton("ok");
		boutonRecherche.addActionListener(new BoutonRecherche());
		boutonRecherche.setPreferredSize(new Dimension(60,25));
		//boutonRecherche.setBackground(Color.gray);
		JPanel panelTitre=new JPanel();
		panelTitre.setBackground(c);
		panelTitre.add(titre);
		JPanel panelRecherche=new JPanel();
		panelRecherche.setBackground(c);
		panelRecherche.add(textRecherche);
		panelRecherche.add(recherche);
		panelRecherche.add(boutonRecherche);
		boxRecherche=Box.createHorizontalBox();
		boxRecherche.add(panelRecherche);
		
		boxTitre=Box.createHorizontalBox();
		boxTitre.setBackground(Color.black);
		boxTitre.add(panelTitre);
		boxNom=Box.createVerticalBox();
		boxTelephone=Box.createVerticalBox();
		
		boxBouton=Box.createVerticalBox();
		boxMatricule=Box.createVerticalBox();
		conteneur=Box.createVerticalBox();
		titre.setFont(f);
		
		matricule=new JLabel("CODECLIENT");
		matricule.setFont(f1);
		JPanel panmatri=new JPanel();
		panmatri.setPreferredSize(new Dimension(148,35));
		panmatri.add(matricule);
		panmatri.setBackground(c);
		//matricule.setPreferredSize(dim);
		boxMatricule.add(panmatri);
		nom=new JLabel("NOM");
		nom.setFont(f1);
		JPanel pannom=new JPanel();
		pannom.setPreferredSize(new Dimension(148,35));
		pannom.setBackground(c);
		//nom.setPreferredSize(dim);
		pannom.add(nom);
		boxNom.add(pannom);
		prenom=new JLabel("TEL");
		prenom.setFont(f1);
		JPanel panpnm=new JPanel();
		panpnm.setPreferredSize(new Dimension(148,35));
		panpnm.add(prenom);
		panpnm.setBackground(c);
		boxTelephone.add(panpnm);
		//prenom.setPreferredSize(dim);
		plus=new JLabel("PLUS");
		plus.setFont(f1);
		JPanel panplus=new JPanel();
		panplus.setPreferredSize(new Dimension(148,35));
		panplus.add(plus);
		panplus.setBackground(c);
		//plus.setPreferredSize(dim);
		boxBouton.add(panplus);
		conteneur1=new JPanel();
		scroller=new JScrollPane(conteneur1);
		scroller.setPreferredSize(new Dimension(100,500));
		//JScrollPane jsp = new JScrollPane(pimage);
	     // JViewport jvp = new JViewport();
	      //jvp =scroller.getViewport();
	      //jvp.setViewPosition(new Point(30,200));
		conteneur1.setBackground(c);
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
			String tabcli="create table IF NOT EXISTS CLIENT ("+
					   "CODE_CLIENT          VARCHAR(30)          not null,"+
					   "NOM_CLIENT           VARCHAR(50)          null,"+
					   "ADRESSE_CLIENT       text              null,"+
					   "EMAIL_CLIENT         VARCHAR(100)                 null,"+
					   "TELEPHONE_CLIENT     VARCHAR(30)                 null,"+
					   "constraint PK_CLIENT primary key (CODE_CLIENT)"+
					")";
			state7.addBatch(tabcli);
			state7.executeBatch();
			state7.clearBatch();
			state7.close();
			
			
			//Création d'un objet Statement
			Statement state = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery("SELECT * FROM client");
			//On récupère les MetaData
			ResultSetMetaData resultMeta = result.getMetaData();
			//On affiche le nom des colonnes
			boxMatricule.add(new Separateur(50,5));
			boxNom.add(new Separateur(80,5));
			boxTelephone.add(new Separateur(80,5));
			boxBouton.add(new Separateur(40,5));
			//String []saut=new String[20];
			int cp=0;
			while(result.next())
			{
				String []s=new String[resultMeta.getColumnCount()-1];
				for(int i = 2; i <= resultMeta.getColumnCount(); i++)
				{
					if(result.getString(i)!=null)
						s[i-2]=new String(result.getString(i));
				}
				listeClient.put(result.getObject(1).toString(),s ) ;
				//saut[cp++]=new String(result.getObject(1).toString());
				JPanel n1=new JPanel();
				//n1.setPreferredSize(dim);
				JLabel l1=new JLabel(result.getObject(1).toString());
				l1.setFont(f3);
				n1.add(l1);
				//n1.setBackground(Color.white);
				boxMatricule.add(n1);
				boxMatricule.add(new Separateur(50,5));
				JPanel n2=new JPanel();
				//n2.setPreferredSize(dim);
				JTextArea l2=new JTextArea(result.getObject(2).toString());
				l2.setFont(f3);
				l2.setEditable(false);
				l2.setLineWrap(true);
				l2.setWrapStyleWord(true);
				
				n2.add(l2);
			//	n2.setBackground(Color.white);
				boxNom.add(n2);
				boxNom.add(new Separateur(80,5));
				JPanel n3=new JPanel();
				//n3.setPreferredSize(dim);
				JLabel l3=new JLabel(result.getObject(5).toString());
				l3.setFont(f3);
				n3.add(l3);
				//n3.setBackground(Color.white);
				boxTelephone.add(n3);
				boxTelephone.add(new Separateur(80,5));
				JPanel n4=new JPanel();
				//n4.setPreferredSize(dim);
			//	n4.setBackground(Color.white);
				Bouton b=new Bouton("voir plus",result.getObject(1).toString());
				b.addActionListener(new Boutontest());
				//b.setBackground(Color.white);
				if(colin%2==0)
				{
					n1.setBackground(couleurEcriture);
					n2.setBackground(couleurEcriture);
					n3.setBackground(couleurEcriture);
					n4.setBackground(couleurEcriture);
					l2.setBackground(couleurEcriture);
					b.setBackground(couleurEcriture);
				}
				else
				{
					n1.setBackground(Color.white);
					n2.setBackground(Color.white);
					n3.setBackground(Color.white);
					n4.setBackground(Color.white);
					l2.setBackground(Color.white);
					b.setBackground(Color.white);
				}
				colin++;
				//
				b.setFont(f3);
				n4.add(b);
				//b.setPreferredSize(dim);
				boxBouton.add(n4);
				boxBouton.add(new Separateur(50,5));
				if(result.getObject(2).toString().length()>=1  &&  result.getObject(2).toString().length()<=15)
				{
					n1.setPreferredSize(dim1);
					n2.setPreferredSize(dim1);
					n3.setPreferredSize(dim1);
					n4.setPreferredSize(dim1);
					b.setPreferredSize(dim1);
				}
				if(result.getObject(2).toString().length()>15 && result.getObject(2).toString().length()<=30)
				{
					n1.setPreferredSize(dim2);
					n2.setPreferredSize(dim2);
					n3.setPreferredSize(dim2);
					n4.setPreferredSize(dim2);
					b.setPreferredSize(dim2);
				}
				if(result.getObject(2).toString().length()>30)
					
				{
					n1.setPreferredSize(dim);
					n2.setPreferredSize(dim);
					n3.setPreferredSize(dim);
					n4.setPreferredSize(dim);
					b.setPreferredSize(dim);
				}
			}
			//recherche=testAutocomplete.createAutoCompleteTextField(saut);
			result.close();
			state.close();
			conn.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void aJour()
	{
		conteneur.add(boxTitre);
		conteneur.add(new Separateur(600,20));
		conteneur.add(boxRecherche);
		conteneur1.add(boxMatricule);
		
		conteneur1.add(boxNom);
		//conteneur1.add(new Separateur(10,30));
		conteneur1.add(boxTelephone);
		//conteneur1.add(new Separateur(10,30));
		//conteneur1.add(boxNumero);
		//conteneur1.add(new Separateur(10,30));
		conteneur1.add(boxBouton);
		conteneur.add(scroller);
		Bouton boutonAjout=new Bouton("ajouter Client");
		boutonAjout.addActionListener(new BoutonAjouter());
		boutonAjout.setPreferredSize(new Dimension(200,40));
		//JPanel panAjout=new JPanel();
		Box boxAjout=Box.createHorizontalBox();
		//boxAjout.add(labelAjout);
		boxAjout.add(boutonAjout);
		boxAjout.add(new Separateur(20,20));
		//JLabel labelSup=new JLabel("Voulez vous supprimer un employe: ");
		Bouton boutonSup=new Bouton("supprimer Client");
		boutonSup.addActionListener(new BoutonSupprimer());
		boutonSup.setPreferredSize(new Dimension(200,40));
		boxAjout.add(boutonSup);
		//Box boxSup=Box.createHorizontalBox();
		//boxSup.add(labelSup);
		//boxSup.add(boutonSup);
		conteneur.add(new Separateur(600,20));
		conteneur.add(boxAjout);
		//conteneur.add(new Separateur(600,10));
		//conteneur.add(boxSup);
		//JPanel panSup=new JPanel();
		this.add(conteneur);
	}
	
	
	
	class Boutontest implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			
			Object b=arg0.getSource();
			String s=new String();
			s=((Bouton)b).getMat();
			Client.this.removeAll();
			JLabel titre1=new JLabel("Details Client");
			titre1.setFont(f);
			JPanel panTitre=new JPanel();
			panTitre.add(titre1);
			Box boxTitre1=Box.createHorizontalBox();
			boxTitre1.add(panTitre);
			Box boxfirst=Box.createVerticalBox();
			JLabel photo,prenomEmp,labelqual,matEmp,dateNaissance,adresseEmp,telEmp,poste,email,labelImage;
			
			prenomEmp=new JLabel(listeClient.get(s)[0]);
			prenomEmp.setFont(f1);
			prenomEmp.setForeground(couleurEcriture);
			JPanel panprnm=new JPanel();
			panprnm.setLayout(new BorderLayout());
			panprnm.setPreferredSize(new Dimension(400,20));
			panprnm.add(prenomEmp,BorderLayout.WEST);
			panprnm.setBackground(Color.white);
			boxfirst.add(panprnm);
			boxfirst.add(new Separateur(600,10));
			matEmp=new JLabel("Codeclient: "+s);
			matEmp.setForeground(couleurEcriture);
			matEmp.setFont(f1);
			JPanel panmatemp=new JPanel();
			panmatemp.setLayout(new BorderLayout());
			panmatemp.setPreferredSize(new Dimension(400,20));
			panmatemp.add(matEmp,BorderLayout.WEST);
			panmatemp.setBackground(Color.white);
			boxfirst.add(panmatemp);
			boxfirst.add(new Separateur(600,10));
			//boxfirst.add(matEmp);
			
			Box total1=Box.createHorizontalBox();
			total1.add(boxfirst);
			
			JLabel detailContact=new JLabel("Detail contact");
			detailContact.setFont(f);
			detailContact.setForeground(couleurEcriture);
			JPanel panDetail=new JPanel();
			panDetail.add(detailContact);
			Box boxDetail=Box.createHorizontalBox();
			boxDetail.add(panDetail);
			Box total2h=Box.createVerticalBox();
			//total2.add(boxDetail);
			//Box total2=Box.createHorizontalBox();
			telEmp=new JLabel("Tel: "+listeClient.get(s)[3]);
			telEmp.setForeground(couleurEcriture);
			telEmp.setFont(f1);
			JPanel pantelemp=new JPanel();
			pantelemp.setLayout(new BorderLayout());
			pantelemp.setPreferredSize(new Dimension(500,25));
			pantelemp.add(telEmp,BorderLayout.WEST);
			pantelemp.setBackground(Color.white);
			total2h.add(pantelemp);
			total2h.add(new Separateur(600,10));
			email=new JLabel("Email: "+listeClient.get(s)[2]);
			email.setForeground(couleurEcriture);
			email.setFont(f1);
			JPanel panemail=new JPanel();
			panemail.setLayout(new BorderLayout());
			panemail.setPreferredSize(new Dimension(500,25));
			panemail.add(email,BorderLayout.WEST);
			panemail.setBackground(Color.white);
			total2h.add(panemail);
			total2h.add(new Separateur(600,10));
			//total2h.add(email);
			adresseEmp=new JLabel("adresse: "+listeClient.get(s)[1]);
			adresseEmp.setForeground(couleurEcriture);
			adresseEmp.setFont(f1);
			total2h.add(adresseEmp);
			JPanel panad=new JPanel();
			panad.setLayout(new BorderLayout());
			panad.setPreferredSize(new Dimension(500,25));
			panad.add(adresseEmp,BorderLayout.WEST);
			panad.setBackground(Color.white);
			total2h.add(panad);
			//total2.add(total2h);
			//total2.add(new Separateur(500,10));
			
			Bouton boutonSuivant=new Bouton("Suivant>>");
			boutonSuivant.setPreferredSize(new Dimension(120,20));
			boutonSuivant.setFont(f3);
			Bouton boutonPrecedent=new Bouton("<<Precedent");
			boutonPrecedent.setPreferredSize(new Dimension(120,20));
			boutonPrecedent.setFont(f3);
			Bouton boutonModifier=new Bouton("Modifier",s,1);
			boutonModifier.setPreferredSize(new Dimension(120,20));
			boutonModifier.setFont(f3);
			//boutonModifier.setBackground();
			boutonModifier.addActionListener(new BoutonModifier());
			JPanel panSwitch=new JPanel();
			//panSwitch.add(boutonPrecedent);
			panSwitch.add(boutonModifier);
			//panSwitch.add(boutonSuivant);
			Box boxSwitch=Box.createHorizontalBox();
			boxSwitch.add(panSwitch);
			
			Bouton boutonRetour=new Bouton("Retour");
			boutonRetour.setPreferredSize(new Dimension(120,20));
			boutonRetour.setFont(f3);
			JPanel panRetour=new JPanel();
			panRetour.add(boutonRetour);
			boutonRetour.addActionListener(new BoutonRetour());
			
			Box retourPayer=Box.createHorizontalBox();
			retourPayer.add(boutonRetour);
			
			Box contain=Box.createVerticalBox();
			contain.add(boxTitre1);
			contain.add(new Separateur(600,10));
			contain.add(total1);
			contain.add(new Separateur(600,10));
			contain.add(boxDetail);
			contain.add(new Separateur(600,10));
			contain.add(total2h);
			contain.add(new Separateur(600,10));
			contain.add(boxSwitch);
			contain.add(new Separateur(600,10));
			contain.add(retourPayer);
			Client.this.add(contain);
			Client.this.updateUI();
		}
		
	}
	
	
	class BoutonRetour implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Client.this.removeAll();
			Client.this.add(conteneur);
			Client.this.updateUI();
		}
	}
	
	
	
	class BoutonRecherche implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			if(listeClient.get(recherche.getText())!=null)
			{
				Client.this.removeAll();
				JLabel tr=new JLabel("Client Recherche");
				tr.setFont(f);
				JPanel ptr=new JPanel();
				ptr.add(tr);
				Box btr=Box.createHorizontalBox();
				btr.add(ptr);
				
				Box bp=Box.createVerticalBox();
				bp.add(btr);
				bp.add(new Separateur(600,20));
				JPanel bp1=new JPanel();
				JScrollPane js11=new JScrollPane(bp1);
				js11.setPreferredSize(new Dimension(500,500));
				
				JLabel tr1=new JLabel("Resultats de la Recherche");
				tr1.setFont(f2);
				JPanel ptr1=new JPanel();
				ptr1.add(tr1);
				Box btr1=Box.createHorizontalBox();
				btr1.add(ptr1);
				bp.add(btr1);
				bp.add(new Separateur(600,20));
				
				Box bm=Box.createVerticalBox();
				Box bpr=Box.createVerticalBox();
				Box bn=Box.createVerticalBox();
				Box bpl=Box.createVerticalBox();
				bp1.add(bm);
				bp1.add(bpr);
				bp1.add(bn);
				bp1.add(bpl);
				bp.add(js11);
				bp.add(new Separateur(600,20));
				
				Bouton br=new Bouton("retour");
				br.addActionListener(new BoutonRetour());
				bp.add(br);
				
				JLabel matri = new JLabel("Code Client");
				matri.setFont(f1);
				JPanel panmatri=new JPanel();
				panmatri.setPreferredSize(new Dimension(148,35));
				panmatri.add(matri);
				panmatri.setBackground(c);
				//matricule.setPreferredSize(dim);
				bm.add(panmatri);
				JLabel nom1 = new JLabel("Tel");
				nom1.setFont(f1);
				JPanel pannom=new JPanel();
				pannom.setPreferredSize(new Dimension(148,35));
				pannom.setBackground(c);
				//nom.setPreferredSize(dim);
				pannom.add(nom1);
				bn.add(pannom);
				JLabel pre = new JLabel("NOM");
				pre.setFont(f1);
				JPanel panpnm=new JPanel();
				panpnm.setPreferredSize(new Dimension(148,35));
				panpnm.add(pre);
				panpnm.setBackground(c);
				bpr.add(panpnm);
				//prenom.setPreferredSize(dim);
				JLabel plus1 = new JLabel("PLUS");
				plus1.setFont(f1);
				JPanel panplus=new JPanel();
				panplus.setPreferredSize(new Dimension(148,35));
				panplus.add(plus1);
				panplus.setBackground(c);
				//plus.setPreferredSize(dim);
				bpl.add(panplus);
						JPanel n1=new JPanel();
						//n1.setPreferredSize(dim);
						JLabel l1=new JLabel(recherche.getText());
						l1.setFont(f3);
						n1.add(l1);
						
						bm.add(n1);
						bm.add(new Separateur(50,5));
						JPanel n2=new JPanel();
						//n2.setPreferredSize(dim);
						JLabel l2=new JLabel(listeClient.get(recherche.getText())[3]);
						l2.setFont(f3);
						
						n2.add(l2);
						
						bn.add(n2);
						bn.add(new Separateur(80,5));
						JPanel n3=new JPanel();
						//n3.setPreferredSize(dim);
						JTextArea l3=new JTextArea(listeClient.get(recherche.getText())[0]);
						l3.setFont(f3);
						l3.setEditable(false);
						l3.setLineWrap(true);
						l3.setWrapStyleWord(true);
						
						n3.add(l3);
						
						bpr.add(n3);
						bpr.add(new Separateur(80,5));
						JPanel n4=new JPanel();
						//n4.setPreferredSize(dim);
						
						Bouton b=new Bouton("voir plus",recherche.getText());
						b.addActionListener(new Boutontest());
							n1.setBackground(Color.white);
							n2.setBackground(Color.white);
							n3.setBackground(Color.white);
							n4.setBackground(Color.white);
							b.setBackground(Color.white);
						//b.setBorderPainted(false);
						b.setFont(f3);
						n4.add(b);
						//b.setPreferredSize(dim);
						bpl.add(n4);
						bpl.add(new Separateur(50,5));
						if(listeClient.get(recherche.getText())[0].length()>=1  &&  listeClient.get(recherche.getText())[0].length()<=15)
						{
							n1.setPreferredSize(dim1);
							n2.setPreferredSize(dim1);
							n3.setPreferredSize(dim1);
							n4.setPreferredSize(dim1);
							b.setPreferredSize(dim1);
						}
						if(listeClient.get(recherche.getText())[0].length()>15 && listeClient.get(recherche.getText())[0].length()<=30)
						{
							n1.setPreferredSize(dim2);
							n2.setPreferredSize(dim2);
							n3.setPreferredSize(dim2);
							n4.setPreferredSize(dim2);
							b.setPreferredSize(dim2);
						}
						if(listeClient.get(recherche.getText())[0].length()>30)
							
						{
							n1.setPreferredSize(dim);
							n2.setPreferredSize(dim);
							n3.setPreferredSize(dim);
							n4.setPreferredSize(dim);
							b.setPreferredSize(dim);
						}
						
						
				Client.this.add(bp);
				Client.this.updateUI();
				
				
			}
			else
			{
			    JOptionPane jop4 = new JOptionPane();
				jop4.showMessageDialog(null, "aucun client trouve", "Information", JOptionPane.INFORMATION_MESSAGE);
	
			}
		}
	}
	
	
	
	
	class BoutonAjouter implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Client.this.removeAll();
			
			JLabel titre1=new JLabel("Ajouter Client");
			titre1.setFont(f);
			JPanel panTitre=new JPanel();
			panTitre.add(titre1);
			Box boxTitre1=Box.createHorizontalBox();
			boxTitre1.add(panTitre);
			Box boxPrincipal=Box.createVerticalBox();
			
			

			JLabel labciv = new JLabel("Civilite: ");
			labciv.setFont(f1);
			labciv.setForeground(couleurEcriture);
			Box boxciv=Box.createHorizontalBox();
			boxciv.add(labciv);
			boxciv.add(civilite);
			
			JLabel nomEmp = new JLabel("Nom: ");
			nomEmp.setFont(f1);
			nomEmp.setForeground(couleurEcriture);
			nomIn=new JTextField("");
			Box boxName=Box.createHorizontalBox();
			boxName.add(nomEmp);
			
			boxName.add(nomIn);
			
			JLabel matEmp = new JLabel("Codeclient: ");
			matEmp.setForeground(couleurEcriture);
			matEmp.setFont(f1);
			telIn=new JTextField("",30);
			Box boxMat1=Box.createHorizontalBox();
			boxMat1.add(matEmp);
			boxMat1.add(telIn);
			JLabel erreur=new JLabel("le matricule est obligatoire");
			erreur.setForeground(Color.red);
			erreur.setFont(new Font("Arial", Font.BOLD, 12));
			Box boxMat=Box.createVerticalBox();
			boxMat.add(boxMat1);
			boxMat.add(erreur);
			
			JLabel detailContact=new JLabel("Detail contact");
			detailContact.setFont(f2);
			detailContact.setForeground(couleurEcriture);
			JPanel panDetail=new JPanel();
			panDetail.add(detailContact);
			Box boxDetail=Box.createHorizontalBox();
			boxDetail.add(panDetail);
			
			JLabel telEmp = new JLabel("Tel: ");
			telEmp.setForeground(couleurEcriture);
			telEmp.setFont(f1);
			telIn=new JTextField("",20);
			Box boxtel=Box.createHorizontalBox();
			boxtel.add(telEmp);
			boxtel.add(telIn);
			
			JLabel email = new JLabel("Email: ");
			email.setForeground(couleurEcriture);
			email.setFont(f1);
			emailIn=new JTextField("");
			Box boxemail=Box.createHorizontalBox();
			boxemail.add(email);
			boxemail.add(emailIn);
		
			JLabel adresseEmp = new JLabel("Adresse: ");
			adresseEmp.setForeground(couleurEcriture);
			adresseEmp.setFont(f1);
			adresseIn=new JTextField("");
			Box boxad=Box.createHorizontalBox();
			boxad.add(adresseEmp);
			boxad.add(adresseIn);
			
			Bouton boutonRetour=new Bouton("Annuler");
			JPanel panRetour=new JPanel();
			panRetour.add(boutonRetour);
			boutonRetour.addActionListener(new BoutonRetour());
			
			Bouton boutonValider=new Bouton("Valider");
			panRetour.add(boutonValider);
			boutonValider.addActionListener(new BoutonValider());
			
			
			
			Box retourPayer=Box.createHorizontalBox();
			retourPayer.add(panRetour);
			
			
			
			boxPrincipal.add(boxTitre1);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxciv);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxName);
			//boxPrincipal.add(new Separateur(600,20));
			//boxPrincipal.add(boxMat);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxDetail);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxtel);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxad);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxemail);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(retourPayer);
			Client.this.add(boxPrincipal);
			Client.this.updateUI();
		}
	}
	
	
	class BoutonValider implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			//System.out.println((int)(sp.getValue()));
			//Date date = new Date(1, 1, 1996) ;
			Object bout=arg0.getSource();
			String id=new String();
			id=((Bouton)bout).getMat();
			if(telIn.getText().length()==0 || telIn.getText().length()>29)
			{
				JOptionPane jop3 = new JOptionPane();
				jop3.showMessageDialog(null, "ERREUR SUR CODE CLIENT", "Erreur", JOptionPane.ERROR_MESSAGE);
				
			}
			else
			{
				if(nomIn.getText().length()>49)
				{
					JOptionPane jop3 = new JOptionPane();
					jop3.showMessageDialog(null, "NOM TROP LONG", "Erreur", JOptionPane.ERROR_MESSAGE);
					
				}
				else
				{
					if(telIn.getText().length()>29)
					{
						JOptionPane jop3 = new JOptionPane();
						jop3.showMessageDialog(null, "NUMERO TELEPHONE TROP LONG", "Erreur", JOptionPane.ERROR_MESSAGE);
						
					}
					else
					{
						EmailValidator emailValidator = EmailValidator.getInstance();
						if(!emailValidator.isValid(emailIn.getText()))
						{
							JOptionPane jop3 = new JOptionPane();
							jop3.showMessageDialog(null, "EMAIL NON VALIDE", "Erreur", JOptionPane.ERROR_MESSAGE);
							
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
											PreparedStatement prepare;
											if(id==null)
											{
												String s="insert into CLIENT (CODE_CLIENT, NOM_CLIENT, ADRESSE_CLIENT, EMAIL_CLIENT, TELEPHONE_CLIENT)";
														s+=" values (?,?,?,?,?)";
												//Date date =  cdate.getTi
												prepare = conn.prepareStatement(s);
												//On remplace le premier trou par le nom du professeur
												prepare.setString(1,telIn.getText());
												prepare.setString(2,civilite.getSelectedItem().toString()+" "+nomIn.getText());
												prepare.setString(3,adresseIn.getText());
												prepare.setString(5,telIn.getText());
												prepare.setString(4,emailIn.getText());
												
											}
											else
											{
												String s="update Client "
														+ "set CODE_Client=?,"
														+ " NOM_Client=?,"
														+ " ADRESSE_Client=?,"
														+ " EMAIL_Client=?,"
														+ " TELEPHONE_Client=?";
												s+=" where CODE_Client=?";
												//Date date =  cdate.getTi
												prepare = conn.prepareStatement(s);
												//On remplace le premier trou par le nom du professeur
												prepare.setString(1,telIn.getText());
												prepare.setString(2,civilite.getSelectedItem().toString()+" "+nomIn.getText());
												prepare.setString(3,adresseIn.getText());
												prepare.setString(5,telIn.getText());
												prepare.setString(4,emailIn.getText());
												prepare.setString(6,id);
												System.out.println(s);
											}
											if( prepare.executeUpdate()==0 )
											{
												JOptionPane jop3 = new JOptionPane();
												jop3.showMessageDialog(null, "Erreur d'insertion", "Erreur", JOptionPane.ERROR_MESSAGE);
											}
											else
											{
												if(id!=null)
												{
													if(listeClient.containsKey(id))
													{
														listeClient.remove(id);
														boxMatricule.removeAll();
														boxNom.removeAll();
														boxBouton.removeAll();
														boxTelephone.removeAll();
														colin=1;
														JPanel panmatri=new JPanel();
														panmatri.setPreferredSize(new Dimension(148,35));
														panmatri.add(matricule);
														panmatri.setBackground(c);
														//matricule.setPreferredSize(dim);
														boxMatricule.add(panmatri);
														
														JPanel pannom=new JPanel();
														pannom.setPreferredSize(new Dimension(148,35));
														pannom.setBackground(c);
														//nom.setPreferredSize(dim);
														pannom.add(nom);
														boxNom.add(pannom);
														
														JPanel panpnm=new JPanel();
														panpnm.setPreferredSize(new Dimension(148,35));
														panpnm.add(prenom);
														panpnm.setBackground(c);
														boxTelephone.add(panpnm);
														//prenom.setPreferredSize(dim);
													
														JPanel panplus=new JPanel();
														panplus.setPreferredSize(new Dimension(148,35));
														panplus.add(plus);
														panplus.setBackground(c);
														//plus.setPreferredSize(dim);
														boxBouton.add(panplus);
														
														String []str= {nomIn.getText(),adresseIn.getText(),emailIn.getText(),telIn.getText()};
														listeClient.put(telIn.getText(),str ) ;
														Set keys = listeClient.keySet();
														 
													    //obtenir un iterator des clés
													    Iterator itr = keys.iterator();
													 
													    String key="";
													    boxMatricule.add(new Separateur(50,5));
														boxNom.add(new Separateur(80,5));
														boxTelephone.add(new Separateur(80,5));
														boxBouton.add(new Separateur(40,5));
														
													    //affichage des pairs clé-valeur
													    while (itr.hasNext()) 
													    { 
													       // obtenir la clé
													       key = (String) itr.next();
	
															JPanel n1=new JPanel();
														//	n1.setPreferredSize(dim);
															JLabel l1=new JLabel(key);
															l1.setFont(f3);
															n1.add(l1);
															//n1.setBackground(Color.white);
															boxMatricule.add(n1);
															boxMatricule.add(new Separateur(50,5));
															JPanel n2=new JPanel();
															//n2.setPreferredSize(dim);
															JTextArea l2=new JTextArea(listeClient.get(key)[0]);
															l2.setFont(f3);
															l2.setEditable(false);
															l2.setLineWrap(true);
															l2.setWrapStyleWord(true);
															
															n2.add(l2);
															//n2.setBackground(Color.white);
															boxNom.add(n2);
															boxNom.add(new Separateur(80,5));
															JPanel n3=new JPanel();
															//n3.setPreferredSize(dim);
															JLabel l3=new JLabel(listeClient.get(key)[3]);
															l3.setFont(f3);
															n3.add(l3);
															//n3.setBackground(Color.white);
															boxTelephone.add(n3);
															boxTelephone.add(new Separateur(80,5));
															JPanel n4=new JPanel();
															//n4.setPreferredSize(dim);
															//n4.setBackground(Color.white);
															Bouton b=new Bouton("voir plus",key);
															b.addActionListener(new Boutontest());
															//b.setBackground(Color.white);
															if(colin%2==0)
															{
																n1.setBackground(couleurEcriture);
																n2.setBackground(couleurEcriture);
																n3.setBackground(couleurEcriture);
																n4.setBackground(couleurEcriture);
																l2.setBackground(couleurEcriture);
																b.setBackground(couleurEcriture);
															}
															else
															{
																n1.setBackground(Color.white);
																n2.setBackground(Color.white);
																n3.setBackground(Color.white);
																n4.setBackground(Color.white);
																l2.setBackground(Color.white);
																b.setBackground(Color.white);
															}
															colin++;
														//	
															b.setFont(f3);
															n4.add(b);
															//b.setPreferredSize(dim);
															boxBouton.add(n4);
															boxBouton.add(new Separateur(50,5));
															
															if(listeClient.get(key)[0].length()>=1  &&  listeClient.get(key)[0].length()<=15)
															{
																n1.setPreferredSize(dim1);
																n2.setPreferredSize(dim1);
																n3.setPreferredSize(dim1);
																n4.setPreferredSize(dim1);
																b.setPreferredSize(dim1);
															}
															if(listeClient.get(key)[0].length()>15 && listeClient.get(key)[0].length()<=30)
															{
																n1.setPreferredSize(dim2);
																n2.setPreferredSize(dim2);
																n3.setPreferredSize(dim2);
																n4.setPreferredSize(dim2);
																b.setPreferredSize(dim2);
															}
															if(listeClient.get(key)[0].length()>30)
																
															{
																n1.setPreferredSize(dim);
																n2.setPreferredSize(dim);
																n3.setPreferredSize(dim);
																n4.setPreferredSize(dim);
																b.setPreferredSize(dim);
															}
															
													    }
													}
													else {
														
													}
																							    
												}
												else
												{
													String []str= {nomIn.getText(),adresseIn.getText(),emailIn.getText(),telIn.getText()};
													listeClient.put(telIn.getText(),str ) ;
													JPanel n1=new JPanel();
												//	n1.setPreferredSize(dim);
													JLabel l1=new JLabel(telIn.getText());
													System.out.println(telIn.getText());
													l1.setFont(f3);
													n1.add(l1);
													//n1.setBackground(Color.white);
													boxMatricule.add(n1);
													boxMatricule.add(new Separateur(50,5));
													JPanel n2=new JPanel();
													//n2.setPreferredSize(dim);
													JTextArea l2=new JTextArea(nomIn.getText());
													l2.setFont(f3);
													l2.setEditable(false);
													l2.setLineWrap(true);
													l2.setWrapStyleWord(true);
													
													n2.add(l2);
													//n2.setBackground(Color.white);
													boxNom.add(n2);
													boxNom.add(new Separateur(80,5));
													JPanel n3=new JPanel();
													//n3.setPreferredSize(dim);
													JLabel l3=new JLabel(telIn.getText());
													l3.setFont(f3);
													n3.add(l3);
													//n3.setBackground(Color.white);
													boxTelephone.add(n3);
													boxTelephone.add(new Separateur(80,5));
													JPanel n4=new JPanel();
													//n4.setPreferredSize(dim);
													//n4.setBackground(Color.white);
													Bouton b=new Bouton("voir plus",telIn.getText());
													b.addActionListener(new Boutontest());
													//b.setBackground(Color.white);
													if(colin%2==0)
													{
														n1.setBackground(couleurEcriture);
														n2.setBackground(couleurEcriture);
														n3.setBackground(couleurEcriture);
														n4.setBackground(couleurEcriture);
														l2.setBackground(couleurEcriture);
														b.setBackground(couleurEcriture);
													}
													else
													{
														n1.setBackground(Color.white);
														n2.setBackground(Color.white);
														n3.setBackground(Color.white);
														n4.setBackground(Color.white);
														l2.setBackground(Color.white);
														b.setBackground(Color.white);
													}
													colin++;
												//	
													b.setFont(f3);
													n4.add(b);
												//	b.setPreferredSize(dim);
													boxBouton.add(n4);
													boxBouton.add(new Separateur(50,5));
													
													if(nomIn.getText().length()>=1  &&  nomIn.getText().length()<=15)
													{
														n1.setPreferredSize(dim1);
														n2.setPreferredSize(dim1);
														n3.setPreferredSize(dim1);
														n4.setPreferredSize(dim1);
														b.setPreferredSize(dim1);
													}
													if(nomIn.getText().length()>15 && nomIn.getText().length()<=30)
													{
														n1.setPreferredSize(dim2);
														n2.setPreferredSize(dim2);
														n3.setPreferredSize(dim2);
														n4.setPreferredSize(dim2);
														b.setPreferredSize(dim2);
													}
													if(nomIn.getText().length()>30)
														
													{
														n1.setPreferredSize(dim);
														n2.setPreferredSize(dim);
														n3.setPreferredSize(dim);
														n4.setPreferredSize(dim);
														b.setPreferredSize(dim);
													}
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
					
				}
				
			}
			Client.this.removeAll();//    1/1/1   11/1/1   11/11/1 1/11/1
			Client.this.add(conteneur);
			Client.this.updateUI();
		}
	}

	

	class BoutonModifier implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Object b=arg0.getSource();
			String s=new String();
			s=((Bouton)b).getMat();
			Client.this.removeAll();
			
			JLabel titre1=new JLabel("Modifier Client");
			titre1.setFont(f);
			JPanel panTitre=new JPanel();
			panTitre.add(titre1);
			Box boxTitre1=Box.createHorizontalBox();
			boxTitre1.add(panTitre);
			Box boxPrincipal=Box.createVerticalBox();
			
			JLabel labciv = new JLabel("Civilite: ");
			labciv.setFont(f1);
			labciv.setForeground(couleurEcriture);
			Box boxciv=Box.createHorizontalBox();
			boxciv.add(labciv);
			boxciv.add(civilite);
			
			JLabel nomEmp = new JLabel("Nom: ");
			nomEmp.setFont(f1);
			nomEmp.setForeground(couleurEcriture);
			nomIn=new JTextField(listeClient.get(s)[0]);
			Box boxName=Box.createHorizontalBox();
			boxName.add(nomEmp);
			boxName.add(nomIn);
			
			JLabel matEmp = new JLabel("Codeclient: ");
			matEmp.setForeground(couleurEcriture);
			matEmp.setFont(f1);
			telIn=new JTextField(s,9);
			Box boxMat1=Box.createHorizontalBox();
			boxMat1.add(matEmp);
			boxMat1.add(telIn);
			JLabel erreur=new JLabel("le code client est obligatoire");
			erreur.setForeground(Color.red);
			erreur.setFont(new Font("Arial", Font.BOLD, 12));
			Box boxMat=Box.createVerticalBox();
			boxMat.add(boxMat1);
			boxMat.add(erreur);
			JLabel detailContact=new JLabel("Detail contact");
			detailContact.setFont(f2);
			detailContact.setForeground(couleurEcriture);
			JPanel panDetail=new JPanel();
			panDetail.add(detailContact);
			Box boxDetail=Box.createHorizontalBox();
			boxDetail.add(panDetail);
			
			JLabel telEmp = new JLabel("Tel: ");
			telEmp.setForeground(couleurEcriture);
			telEmp.setFont(f1);
			telIn=new JTextField(listeClient.get(s)[3],20);
			Box boxtel=Box.createHorizontalBox();
			boxtel.add(telEmp);
			boxtel.add(telIn);
			
			JLabel email = new JLabel("Email: ");
			email.setForeground(couleurEcriture);
			email.setFont(f1);
			emailIn=new JTextField(listeClient.get(s)[2]);
			Box boxemail=Box.createHorizontalBox();
			boxemail.add(email);
			boxemail.add(emailIn);
		
			JLabel adresseEmp = new JLabel("Adresse: ");
			adresseEmp.setForeground(couleurEcriture);
			adresseEmp.setFont(f1);
			adresseIn=new JTextField(listeClient.get(s)[1]);
			Box boxad=Box.createHorizontalBox();
			boxad.add(adresseEmp);
			boxad.add(adresseIn);
			
			Bouton boutonRetour=new Bouton("Annuler");
			JPanel panRetour=new JPanel();
			panRetour.add(boutonRetour);
			boutonRetour.addActionListener(new BoutonRetour());
			
			Bouton boutonValider=new Bouton("Valider",s,1);
			panRetour.add(boutonValider);
			boutonValider.addActionListener(new BoutonValider());
			
			
			
			Box retourPayer=Box.createHorizontalBox();
			retourPayer.add(panRetour);
			
			
			
			boxPrincipal.add(boxTitre1);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxciv);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxName);
			//boxPrincipal.add(new Separateur(600,20));
			//boxPrincipal.add(boxMat);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxDetail);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxtel);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxad);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxemail);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(retourPayer);
			Client.this.add(boxPrincipal);
			Client.this.updateUI();
		}
	}
	
	class BoutonSupprimer implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
			String s = jop.showInputDialog(null, "Donner le matricule de l'Clientr ","Supprimer un Client", JOptionPane.QUESTION_MESSAGE);
			if(s!=null)
			{
				int option = jop.showConfirmDialog(null,
					"Voulez-vous vraiment supprimer l'Client ?",
					"suppression",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			
					if(option == JOptionPane.OK_OPTION)
					{
						if(listeClient.containsKey(s))
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
									String str="delete from Client where CODE_Client=?";
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
							listeClient.remove(s);
							boxMatricule.removeAll();
							boxNom.removeAll();
							boxBouton.removeAll();
							boxTelephone.removeAll();
							colin=1;
							JPanel panmatri=new JPanel();
							panmatri.setPreferredSize(new Dimension(148,35));
							panmatri.add(matricule);
							panmatri.setBackground(c);
							//matricule.setPreferredSize(dim);
							boxMatricule.add(panmatri);
							
							JPanel pannom=new JPanel();
							pannom.setPreferredSize(new Dimension(148,35));
							pannom.setBackground(c);
							//nom.setPreferredSize(dim);
							pannom.add(nom);
							boxNom.add(pannom);
							
							JPanel panpnm=new JPanel();
							panpnm.setPreferredSize(new Dimension(148,35));
							panpnm.add(prenom);
							panpnm.setBackground(c);
							boxTelephone.add(panpnm);
							//prenom.setPreferredSize(dim);
						
							JPanel panplus=new JPanel();
							panplus.setPreferredSize(new Dimension(148,35));
							panplus.add(plus);
							panplus.setBackground(c);
							//plus.setPreferredSize(dim);
							boxBouton.add(panplus);
							
							
							Set keys = listeClient.keySet();
							 
						    //obtenir un iterator des clés
						    Iterator itr = keys.iterator();
						 
						    String key="";
						    boxMatricule.add(new Separateur(50,5));
							boxNom.add(new Separateur(80,5));
							boxTelephone.add(new Separateur(80,5));
							boxBouton.add(new Separateur(40,5));
							
						    //affichage des pairs clé-valeur
						    while (itr.hasNext()) 
						    { 
						       // obtenir la clé
						       key = (String) itr.next();
						       
								JPanel n1=new JPanel();
							//	n1.setPreferredSize(dim);
								JLabel l1=new JLabel(key);
								l1.setFont(f3);
								n1.add(l1);
								//n1.setBackground(Color.white);
								boxMatricule.add(n1);
								boxMatricule.add(new Separateur(50,5));
								JPanel n2=new JPanel();
								//n2.setPreferredSize(dim);
								JTextArea l2=new JTextArea(listeClient.get(key)[0]);
								l2.setFont(f3);
								l2.setEditable(false);
								l2.setLineWrap(true);
								l2.setWrapStyleWord(true);
								
								n2.add(l2);
								//n2.setBackground(Color.white);
								boxNom.add(n2);
								boxNom.add(new Separateur(80,5));
								JPanel n3=new JPanel();
								//n3.setPreferredSize(dim);
								JLabel l3=new JLabel(listeClient.get(key)[3]);
								l3.setFont(f3);
								n3.add(l3);
								//n3.setBackground(Color.white);
								boxTelephone.add(n3);
								boxTelephone.add(new Separateur(80,5));
								JPanel n4=new JPanel();
								//n4.setPreferredSize(dim);
								//n4.setBackground(Color.white);
								Bouton b=new Bouton("voir plus",key);
								b.addActionListener(new Boutontest());
								//b.setBackground(Color.white);
								if(colin%2==0)
								{
									n1.setBackground(couleurEcriture);
									n2.setBackground(couleurEcriture);
									n3.setBackground(couleurEcriture);
									n4.setBackground(couleurEcriture);
									l2.setBackground(couleurEcriture);
									b.setBackground(couleurEcriture);
								}
								else
								{
									n1.setBackground(Color.white);
									n2.setBackground(Color.white);
									n3.setBackground(Color.white);
									n4.setBackground(Color.white);
									l2.setBackground(Color.white);
									b.setBackground(Color.white);
								}
								colin++;
							//	
								b.setFont(f3);
								n4.add(b);
								//b.setPreferredSize(dim);
								boxBouton.add(n4);
								boxBouton.add(new Separateur(50,5));
								
								if(listeClient.get(key)[0].length()>=1  &&  listeClient.get(key)[0].length()<=15)
								{
									n1.setPreferredSize(dim1);
									n2.setPreferredSize(dim1);
									n3.setPreferredSize(dim1);
									n4.setPreferredSize(dim1);
									b.setPreferredSize(dim1);
								}
								if(listeClient.get(key)[0].length()>15 && listeClient.get(key)[0].length()<=30)
								{
									n1.setPreferredSize(dim2);
									n2.setPreferredSize(dim2);
									n3.setPreferredSize(dim2);
									n4.setPreferredSize(dim2);
									b.setPreferredSize(dim2);
								}
								if(listeClient.get(key)[0].length()>30)
									
								{
									n1.setPreferredSize(dim);
									n2.setPreferredSize(dim);
									n3.setPreferredSize(dim);
									n4.setPreferredSize(dim);
									b.setPreferredSize(dim);
								}
							
						    }
						    JOptionPane jop4 = new JOptionPane();
							jop4.showMessageDialog(null, "vous avez supprimer le Client", "Information", JOptionPane.INFORMATION_MESSAGE);
				
						}
						else
						{
							JOptionPane jop1 = new JOptionPane();
							jop1.showMessageDialog(null, " le Clientr n'existe pas", "Information", JOptionPane.INFORMATION_MESSAGE);
				
						}
					
					}
					else
					{
						JOptionPane jop1 = new JOptionPane();
						jop1.showMessageDialog(null, "vous avez annuler la suppression", "Information", JOptionPane.INFORMATION_MESSAGE);
			
					}
			}
					Client.this.add(conteneur);
					Client.this.updateUI();
			}
	}

	
	
}
