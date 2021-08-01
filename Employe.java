import java.awt.BorderLayout;


import com.toedter.calendar.JCalendar;

import test.Telecharger;

import java.awt.Color;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import org.apache.commons.validator.routines.EmailValidator;

public class Employe extends JPanel 
{
	private Hashtable<String, String[]> listeEmploye;
	private JTextField recherche,prenomIn,nomIn,emailIn,telIn,adresseIn,posteIn;
	private Box boxTitre,boxMatricule,boxNom,boxPrenom,boxBouton,conteneur,boxRecherche;
	private JLabel titre,textRecherche,matricule,nom,prenom,plus;
	private Bouton boutonRecherche;
	private Color c;
	private JPanel conteneur1;
	private JScrollPane scroller;
	private Font f,f1,f2,f3;
	private Color couleurEcriture;
	private JTextArea qualIn;
	private Dimension dim,dim1,dim2,dim3;
	private JCalendar cdate = new JCalendar();
	private int colin=1;
	private String imageIn;
	
	public Employe()
	{
		super();
		couleurEcriture=new Color(150,141,141,255);
		c=new Color(228,213,213,255);
		dim=new Dimension(148,80);
		dim1=new Dimension(148,35);
		dim2=new Dimension(148,65);
		dim3=new Dimension(148,40);
		
		
		f=new Font("Arial", Font.BOLD, 30);
		f1=new Font("Arial", Font.BOLD, 20);
		f3=new Font("Arial", Font.BOLD, 15);
		f2=new Font("Arial", Font.BOLD, 25);
		titre=new JLabel("Liste des employes");
		listeEmploye=new Hashtable<String, String[]>();
		recherche=new JTextField("votre text");
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
		boxPrenom=Box.createVerticalBox();
		
		boxBouton=Box.createVerticalBox();
		boxMatricule=Box.createVerticalBox();
		conteneur=Box.createVerticalBox();
		titre.setFont(f);
		
		matricule=new JLabel("MATRICULE");
		matricule.setFont(f1);
		JPanel panmatri=new JPanel();
		panmatri.setPreferredSize(dim1);
		panmatri.add(matricule);
		panmatri.setBackground(c);
		//matricule.setPreferredSize(dim);
		boxMatricule.add(panmatri);
		nom=new JLabel("NOM");
		nom.setFont(f1);
		JPanel pannom=new JPanel();
		pannom.setPreferredSize(dim1);
		pannom.setBackground(c);
		//nom.setPreferredSize(dim);
		pannom.add(nom);
		boxNom.add(pannom);
		prenom=new JLabel("PRENOM");
		prenom.setFont(f1);
		JPanel panpnm=new JPanel();
		panpnm.setPreferredSize(dim1);
		panpnm.add(prenom);
		panpnm.setBackground(c);
		boxPrenom.add(panpnm);
		//prenom.setPreferredSize(dim);
		plus=new JLabel("PLUS");
		plus.setFont(f1);
		JPanel panplus=new JPanel();
		panplus.setPreferredSize(dim1);
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
			String tabcli="create table IF NOT EXISTS EMPLOYE ("+
					   "MATRICULE_EMPLOYE    VARCHAR(30)          not null,"+
					   "DATE_PAIEMENT           TEXT                 null,"+
					   "PRENOM_EMPLOYE       VARCHAR(50)                 null,"+
					  "NOM_EMPLOYE         VARCHAR(50)          null,"+
					   "DATE_NAISSANCE       TEXT                 null,"+
					   "ADRESSE_EMPLOYE      TEXT             null,"+
					   "QUALIFICATION        TEXT                 null,"+
					   "POSTE                VARCHAR(100)          null,"+
					   "EMAIL_EMPLOYE        VARCHAR(100)                null,"+
					   "IMAGE                VARCHAR(100)            null,"+
					   "TELEPHONE_EMPLOYE    VARCHAR(30)                 null,"+
					   "constraint PK_EMPLOYE primary key (MATRICULE_EMPLOYE)"+
					")";



			state7.addBatch(tabcli);
			state7.executeBatch();
			state7.clearBatch();
			state7.close();
			
			
			Statement state8 = conn.createStatement();
			String tabcli1="create table IF NOT EXISTS FICHE_DE_PAIEMENT ("+
					   "MATRICULE_EMPLOYE         VARCHAR(30)          not null,"+
					   "DATE_PAIEMENT        TEXT                 not null,"+
					   "PRIME                REAL                null,"+
					   "constraint PK_FICHE_DE_PAIEMENT primary key (MATRICULE_EMPLOYE,DATE_PAIEMENT)"+
					")";



			state8.addBatch(tabcli1);
			state8.executeBatch();
			state8.clearBatch();
			state8.close();

			
			
			
			//Création d'un objet Statement
			Statement state = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery("SELECT * FROM employe");
			//On récupère les MetaData
			ResultSetMetaData resultMeta = result.getMetaData();
			//On affiche le nom des colonnes
			boxMatricule.add(new Separateur(50,5));
			boxNom.add(new Separateur(80,5));
			boxPrenom.add(new Separateur(80,5));
			boxBouton.add(new Separateur(40,5));
		//	String []saut=new String[20];
			int cp=0;
			while(result.next())
			{
				String []s=new String[resultMeta.getColumnCount()-1];
				for(int i = 3; i <= resultMeta.getColumnCount(); i++)
				{
					if(result.getString(i)!=null)
						s[i-2]=new String(result.getString(i));
				}
				listeEmploye.put(result.getObject(1).toString(),s ) ;
				//saut[cp++]=new String(result.getObject(1).toString());
				JPanel n1=new JPanel();
				//n1.setPreferredSize(dim);
				JLabel l1=new JLabel(result.getObject(1).toString());
				l1.setFont(f3);
				n1.add(l1);
				
				boxMatricule.add(n1);
				boxMatricule.add(new Separateur(50,5));
				JPanel n2=new JPanel();
				//n2.setPreferredSize(dim);
				JTextArea l2=new JTextArea(result.getObject(4).toString());
				l2.setFont(f3);
				l2.setEditable(false);
				l2.setLineWrap(true);
				l2.setWrapStyleWord(true);
				
				n2.add(l2);
				
				boxNom.add(n2);
				boxNom.add(new Separateur(80,5));
				JPanel n3=new JPanel();
				//n3.setPreferredSize(dim);
				JTextArea l3=new JTextArea(result.getObject(3).toString());
				l3.setFont(f3);
				l3.setEditable(false);
				l3.setLineWrap(true);
				l3.setWrapStyleWord(true);
				
				n3.add(l3);
				
				boxPrenom.add(n3);
				boxPrenom.add(new Separateur(80,5));
				JPanel n4=new JPanel();
				//n4.setPreferredSize(dim);
				
				Bouton b=new Bouton("voir plus",result.getObject(1).toString());
				b.addActionListener(new Boutontest());
				if(colin%2==0)
				{
					n1.setBackground(couleurEcriture);
					n2.setBackground(couleurEcriture);
					n3.setBackground(couleurEcriture);
					n4.setBackground(couleurEcriture);
					l2.setBackground(couleurEcriture);
					l3.setBackground(couleurEcriture);
					b.setBackground(couleurEcriture);
				}
				else
				{
					n1.setBackground(Color.white);
					n2.setBackground(Color.white);
					n3.setBackground(Color.white);
					n4.setBackground(Color.white);
					l2.setBackground(Color.white);
					l3.setBackground(Color.white);
					b.setBackground(Color.white);
				}
				colin++;
			//	b.setBorderPainted(false);
				b.setFont(f3);
				n4.add(b);
				//b.setPreferredSize(dim);
				boxBouton.add(n4);
				boxBouton.add(new Separateur(50,5));
				
				if(result.getObject(3).toString().length()>=1  &&  result.getObject(3).toString().length()<=15)
				{
					n1.setPreferredSize(dim1);
					n2.setPreferredSize(dim1);
					n3.setPreferredSize(dim1);
					n4.setPreferredSize(dim1);
					b.setPreferredSize(dim1);
				}
				if(result.getObject(3).toString().length()>15 && result.getObject(3).toString().length()<=30)
				{
					n1.setPreferredSize(dim2);
					n2.setPreferredSize(dim2);
					n3.setPreferredSize(dim2);
					n4.setPreferredSize(dim2);
					b.setPreferredSize(dim2);
				}
				if(result.getObject(3).toString().length()>30)
					
				{
					n1.setPreferredSize(dim);
					n2.setPreferredSize(dim);
					n3.setPreferredSize(dim);
					n4.setPreferredSize(dim);
					b.setPreferredSize(dim);
				}
			}
		//	recherche=testAutocomplete.createAutoCompleteTextField(saut);
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
		conteneur1.add(boxPrenom);
		//conteneur1.add(new Separateur(10,30));
		//conteneur1.add(boxNumero);
		//conteneur1.add(new Separateur(10,30));
		conteneur1.add(boxBouton);
		conteneur.add(scroller);
		//JLabel labelAjout=new JLabel("Voulez vous ajouter un employe: ");
		Bouton boutonAjout=new Bouton("ajouter Employe");
		boutonAjout.addActionListener(new BoutonAjouter());
		boutonAjout.setPreferredSize(new Dimension(200,40));
		//JPanel panAjout=new JPanel();
		Box boxAjout=Box.createHorizontalBox();
		//boxAjout.add(labelAjout);
		boxAjout.add(boutonAjout);
		boxAjout.add(new Separateur(20,20));
		//JLabel labelSup=new JLabel("Voulez vous supprimer un employe: ");
		Bouton boutonSup=new Bouton("supprimer Employe");
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
			Employe.this.removeAll();
			JLabel titre1=new JLabel("Details employe");
			titre1.setFont(f);
			JPanel panTitre=new JPanel();
			panTitre.add(titre1);
			Box boxTitre1=Box.createHorizontalBox();
			boxTitre1.add(panTitre);
			Box boxfirst=Box.createVerticalBox();
			JLabel photo,prenomEmp,labelqual,matEmp,dateNaissance,adresseEmp,telEmp,poste,email,labelImage;
			
			JPanel panelImage=new JPanel();
			ImageIcon image;
			image = new ImageIcon(listeEmploye.get(s)[8]);
			labelImage=new JLabel();
			labelImage.setIcon(image);
			//labelImage.setPreferredSize(new Dimension(300,300));
			panelImage.add(labelImage);
			JScrollPane scrollImage=new JScrollPane(panelImage);
			scrollImage.setPreferredSize(new Dimension(150,200));
			
			prenomEmp=new JLabel(listeEmploye.get(s)[2]+"   "+listeEmploye.get(s)[1]);
			prenomEmp.setFont(f1);
			prenomEmp.setForeground(couleurEcriture);
			JPanel panprnm=new JPanel();
			panprnm.setLayout(new BorderLayout());
			panprnm.setPreferredSize(new Dimension(400,20));
			panprnm.add(prenomEmp,BorderLayout.WEST);
			panprnm.setBackground(Color.white);
			boxfirst.add(panprnm);
			matEmp=new JLabel("Matricule: "+s);
			matEmp.setForeground(couleurEcriture);
			matEmp.setFont(f1);
			JPanel panmatemp=new JPanel();
			panmatemp.setLayout(new BorderLayout());
			panmatemp.setPreferredSize(new Dimension(400,20));
			panmatemp.add(matEmp,BorderLayout.WEST);
			panmatemp.setBackground(Color.white);
			boxfirst.add(panmatemp);
			//boxfirst.add(matEmp);
			poste=new JLabel("Poste:  "+listeEmploye.get(s)[6]);
			poste.setForeground(couleurEcriture);
			poste.setFont(f1);
			JPanel panpos=new JPanel();
			panpos.setLayout(new BorderLayout());
			panpos.setPreferredSize(new Dimension(400,20));
			panpos.add(poste,BorderLayout.WEST);
			panpos.setBackground(Color.white);
			boxfirst.add(panpos);
			//boxfirst.add(poste);
			dateNaissance=new JLabel("Date de naissance: "+listeEmploye.get(s)[3]);
			dateNaissance.setForeground(couleurEcriture);
			dateNaissance.setFont(f1);
			JPanel pandte=new JPanel();
			pandte.setLayout(new BorderLayout());
			pandte.setPreferredSize(new Dimension(400,20));
			pandte.add(dateNaissance,BorderLayout.WEST);
			pandte.setBackground(Color.white);
			boxfirst.add(pandte);
		//	boxfirst.add(dateNaissance);
			
			Box boxsecond=Box.createVerticalBox();
			boxsecond.add(scrollImage);
			photo=new JLabel("photo de "+listeEmploye.get(s)[2]);
			photo.setForeground(couleurEcriture);
			photo.setFont(f1);
			JPanel panph=new JPanel();
			panph.setLayout(new BorderLayout());
			panph.setPreferredSize(new Dimension(100,20));
			panph.add(photo,BorderLayout.WEST);
			panph.setBackground(Color.white);
			boxsecond.add(panph);
			
			//boxsecond.add(photo);
			Box total1=Box.createHorizontalBox();
			total1.add(boxfirst);
			//total1.add(new Separateur(100,200));
			total1.add(boxsecond);
			
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
			telEmp=new JLabel("Tel: "+listeEmploye.get(s)[9]);
			telEmp.setForeground(couleurEcriture);
			telEmp.setFont(f1);
			JPanel pantelemp=new JPanel();
			pantelemp.setLayout(new BorderLayout());
			pantelemp.setPreferredSize(new Dimension(500,25));
			pantelemp.add(telEmp,BorderLayout.WEST);
			pantelemp.setBackground(Color.white);
			total2h.add(pantelemp);
			email=new JLabel("Email: "+listeEmploye.get(s)[7]);
			email.setForeground(couleurEcriture);
			email.setFont(f1);
			JPanel panemail=new JPanel();
			panemail.setLayout(new BorderLayout());
			panemail.setPreferredSize(new Dimension(500,25));
			panemail.add(email,BorderLayout.WEST);
			panemail.setBackground(Color.white);
			total2h.add(panemail);
			//total2h.add(email);
			adresseEmp=new JLabel("adresse: "+listeEmploye.get(s)[4]);
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
			
			Box boxqual=Box.createHorizontalBox();
			labelqual=new JLabel("Apptitudes");
			labelqual.setForeground(couleurEcriture);
			labelqual.setFont(f);
			JPanel panelqual=new JPanel();
			panelqual.add(labelqual);
			boxqual.add(panelqual);
			JTextArea qualification=new JTextArea("*"+listeEmploye.get(s)[5]);
			qualification.setEditable(false);
			qualification.setFont(f1);
			qualification.setForeground(couleurEcriture);
			JScrollPane scrollqual=new JScrollPane(qualification);
			scrollqual.setPreferredSize(new Dimension(100,50));
			Box total3=Box.createHorizontalBox();
			total3.add(scrollqual);
			
			Bouton boutonSuivant=new Bouton("Suivant>>");
			boutonSuivant.setPreferredSize(new Dimension(120,20));
			boutonSuivant.setFont(f3);
			Bouton boutonPrecedent=new Bouton("<<Precedent");
			boutonPrecedent.setPreferredSize(new Dimension(120,20));
			boutonPrecedent.setFont(f3);
			Bouton boutonModifier=new Bouton("Modifier",s,1);
			boutonModifier.setPreferredSize(new Dimension(120,20));
			//boutonModifier.setFont(f3);
			//boutonModifier.setBackground();
			boutonModifier.addActionListener(new BoutonModifier());
			JPanel panSwitch=new JPanel();
		//	panSwitch.add(boutonPrecedent);
			panSwitch.add(boutonModifier);
		//	panSwitch.add(boutonSuivant);
			Box boxSwitch=Box.createHorizontalBox();
			boxSwitch.add(panSwitch);
			
			Bouton boutonRetour=new Bouton("Retour");
			boutonRetour.setPreferredSize(new Dimension(120,20));
			boutonRetour.setFont(f3);
			JPanel panRetour=new JPanel();
			panRetour.add(boutonRetour);
			boutonRetour.addActionListener(new BoutonRetour());
			
			Bouton boutonPayer=new Bouton("payement",s,1);
			boutonPayer.addActionListener(new BoutonPayement());
			boutonPayer.setPreferredSize(new Dimension(120,20));
			boutonPayer.setFont(f3);
			JPanel panPayer=new JPanel();
			panPayer.add(boutonPayer);
			
			Box retourPayer=Box.createHorizontalBox();
			retourPayer.add(boutonRetour);
			retourPayer.add(new Separateur(300,10));
			retourPayer.add(boutonPayer);
			
			Box contain=Box.createVerticalBox();
			contain.add(boxTitre1);
			contain.add(new Separateur(600,10));
			contain.add(total1);
			contain.add(new Separateur(600,10));
			contain.add(boxDetail);
			contain.add(new Separateur(600,10));
			contain.add(total2h);
			contain.add(new Separateur(600,10));
			contain.add(boxqual);
			contain.add(new Separateur(600,10));
			contain.add(total3);
			contain.add(new Separateur(600,10));
			contain.add(boxSwitch);
			contain.add(new Separateur(600,10));
			contain.add(retourPayer);
			Employe.this.add(contain);
			Employe.this.updateUI();
		}
		
	}
	
	class BoutonRetour implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Employe.this.removeAll();
			Employe.this.add(conteneur);
			Employe.this.updateUI();
		}
	}
	
	
	class BoutonAjouter implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Employe.this.removeAll();
			
			JLabel titre1=new JLabel("Ajouter employe");
			titre1.setFont(f);
			JPanel panTitre=new JPanel();
			panTitre.add(titre1);
			Box boxTitre1=Box.createHorizontalBox();
			boxTitre1.add(panTitre);
			Box boxPrincipal=Box.createVerticalBox();
			
			JLabel prenomEmp = new JLabel("Prenom: ");
			prenomEmp.setFont(f1);
			prenomEmp.setForeground(couleurEcriture);
			prenomIn=new JTextField("");
			Box boxPrnom=Box.createHorizontalBox();
			boxPrnom.add(prenomEmp);
			boxPrnom.add(prenomIn);
			
			JLabel nomEmp = new JLabel("Nom: ");
			nomEmp.setFont(f1);
			nomEmp.setForeground(couleurEcriture);
			nomIn=new JTextField("");
			Box boxName=Box.createHorizontalBox();
			boxName.add(nomEmp);
			boxName.add(nomIn);
			
			JLabel matEmp = new JLabel("Matricule: ");
			matEmp.setForeground(couleurEcriture);
			matEmp.setFont(f1);
			telIn=new JTextField("");
			Box boxMat1=Box.createHorizontalBox();
			boxMat1.add(matEmp);
			boxMat1.add(telIn);
			JLabel erreur=new JLabel("le matricule est obligatoire");
			erreur.setForeground(Color.red);
			erreur.setFont(new Font("Arial", Font.BOLD, 12));
			Box boxMat=Box.createVerticalBox();
			boxMat.add(boxMat1);
			boxMat.add(erreur);
			
			JLabel poste = new JLabel("Poste:  ");
			poste.setForeground(couleurEcriture);
			poste.setFont(f1);
			posteIn=new JTextField("",29);
			Box boxposte=Box.createHorizontalBox();
			boxposte.add(poste);
			boxposte.add(posteIn);
			
			JLabel dateNaissance = new JLabel("Date de naissance: ");
			dateNaissance.setForeground(couleurEcriture);
			dateNaissance.setFont(f1);
			
			
			/* SpinnerModel modelJour = new SpinnerNumberModel(
		                7, //valeur initiale
		                1, //valeur minimum
		                31, //valeur maximum
		                1 //pas
		    ); 
		   sp = new JSpinner(model); 
		    sp.setBounds(100,100,45,30);  */
			
			//JCalendar cr = new JCalendar();
			Bouton cr=new Bouton("choisir date");
			cr.addActionListener(new ChoixDate());
			Box boxdte=Box.createHorizontalBox();
			boxdte.add(dateNaissance);
			//boxdte.add(dteIn);
			Separateur paneldater=new Separateur(100,40);
			paneldater.add(cr);
			boxdte.add(paneldater);
			
			JLabel photo = new JLabel("Photo ");
			photo.setForeground(couleurEcriture);
			photo.setFont(f1);
			imageIn="neant";
			Bouton imageInb=new Bouton("ajouter image");
			imageInb.addActionListener(new ChoixImage());
			Box boxim=Box.createHorizontalBox();
			boxim.add(photo);
			boxim.add(imageInb);
			
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
			
			JLabel labelqual = new JLabel("Apptitudes");
			labelqual.setForeground(couleurEcriture);
			labelqual.setFont(f);
			JPanel panelqual=new JPanel();
			panelqual.add(labelqual);
			Box boxqual=Box.createHorizontalBox();
			boxqual.add(panelqual);
			
			qualIn=new JTextArea("qualifications");
			qualIn.setFont(f1);
			qualIn.setForeground(couleurEcriture);
			JScrollPane scrollqual=new JScrollPane(qualIn);
			scrollqual.setPreferredSize(new Dimension(100,50));
			JLabel label=new JLabel("Qualifications: ");
			label.setFont(f1);
			label.setForeground(couleurEcriture);
			Box total3=Box.createHorizontalBox();
			total3.add(label);
			total3.add(scrollqual);
			
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
			boxPrincipal.add(boxPrnom);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxName);
			boxPrincipal.add(new Separateur(600,20));
		//	boxPrincipal.add(boxMat);
			//boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxim);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxdte);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxDetail);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxtel);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxad);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxemail);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxqual);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(total3);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(retourPayer);
			Employe.this.add(boxPrincipal);
			Employe.this.updateUI();
		}
	}
	
	
	
	class ChoixImage implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
	
		    JFileChooser chooser = new JFileChooser();
		    chooser.showOpenDialog(null);
		    File f = chooser.getSelectedFile();
		    if(f != null)
		    	imageIn= f.getAbsolutePath();
		    else
		    	imageIn="neant";
		    
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
			d.setLocationRelativeTo(Employe.this);
			d.setVisible(true);

			/*Date date = c.getCalendar().getTime(); // on récupère la date

			/* on affiche la date dans le label 
			Locale locale = Locale.getDefault();
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
			label.setText(dateFormat.format(date));*/

			
		}
		
	}
	
	
	class BoutonPayement implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Object b=arg0.getSource();
			String s=new String();
			s=((Bouton)b).getMat();
			Employe.this.removeAll();
			
			JLabel title=new JLabel("Fiche de Payement Par Semaine");
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
			JLabel nfp=new JLabel(listeEmploye.get(s)[2]+"  "+listeEmploye.get(s)[1]);
			nfp.setFont(f2);
			JPanel pnfp=new JPanel();
			pnfp.add(nfp);
			JLabel mfp=new JLabel("Matricule: "+s);
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
			
			
			
			Box hbp=Box.createVerticalBox();
			JTextArea cl=new JTextArea("Employe: "+listeEmploye.get(s)[2]);
			cl.setLineWrap(true);
			cl.setEditable(false);
			cl.setWrapStyleWord(true);
			
			JTextArea cl1=new JTextArea("Matricule: "+s);
			
			cl1.setLineWrap(true);
			cl1.setEditable(false);
			cl1.setWrapStyleWord(true);
			Box header=Box.createHorizontalBox();
			Box hleft=Box.createVerticalBox();
			Box hrig=Box.createVerticalBox();
			
			LocalDate datenow=LocalDate.now();
			
			JLabel dt1=new JLabel("Au: "+datenow.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
			dt1.setFont(f3);
			
			datenow=datenow.plusDays(-7);
			JLabel dt=new JLabel("Du: "+datenow.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)));
			cl.setFont(f3);
			cl1.setFont(f3);
			dt.setFont(f3);
			
			JLabel cl2=new JLabel("Facture N° FR-"+s);
			cl2.setFont(f3);
			
			
			
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
			
			header.add(hleft1);
			header.add(pi);
			
			
			
			JLabel designation=new JLabel("Designation");
			designation.setFont(f1);
			JPanel panmatri=new JPanel();
			panmatri.setLayout(new FlowLayout());
			panmatri.add(designation,FlowLayout.LEFT);
			panmatri.setPreferredSize(dim1);
			panmatri.setBackground(Color.white);
			//designation.setPreferredSize(dim);
			boxDesignation.add(panmatri);
			JLabel total=new JLabel("Total");
			total.setFont(f1);
			JPanel pantotal=new JPanel();
			pantotal.setLayout(new FlowLayout());
			pantotal.add(total,FlowLayout.LEFT);
			pantotal.setPreferredSize(dim1);
			pantotal.setBackground(Color.white);
			//total.setPreferredSize(dim);
			pantotal.add(total);
			boxTotal.add(pantotal);
			JLabel puht=new JLabel("Puht");
			puht.setFont(f1);
			JPanel panpnm=new JPanel();
			panpnm.setLayout(new FlowLayout());
			panpnm.add(puht,FlowLayout.LEFT);
			panpnm.setPreferredSize(dim1);
			panpnm.add(puht);
			panpnm.setBackground(Color.white);
			boxPuht.add(panpnm);
			//puht.setPreferredSize(dim);
			JLabel tva=new JLabel("reccurence");
			tva.setFont(f1);
			JPanel pantva=new JPanel();
			pantva.setLayout(new FlowLayout());
			pantva.add(tva,FlowLayout.LEFT);
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
				ResultSet res,res1;//,res2;
				
				LocalDate j1 = LocalDate.now();
				LocalDate j2 = j1.plusDays(-1);
				LocalDate j3 = j2.plusDays(-1);
				LocalDate j4 = j3.plusDays(-1);
				LocalDate j5 = j4.plusDays(-1);
				LocalDate j6 = j5.plusDays(-1);
				LocalDate j7 = j6.plusDays(-1);
				
				
				PreparedStatement prepare,prepare1,prepare2;
				String str="SELECT code_service,count(code_service) FROM affectation_tache where matricule_employe=? and (date=? or  date=? or  date=? or  date=? or  date=? or  date=? or  date=?) group by code_service";
				//String str2="SELECT code_service FROM contenu_devis where numero_devis=?";
				String str1="SELECT * FROM service where code_service=?";
				//Date date =  cdate.getTi
				prepare = conn.prepareStatement(str);
				prepare1 = conn.prepareStatement(str1);
				//prepare2 = conn.prepareStatement(str2);
			
				prepare.setString(1,s);
				prepare.setString(2,Date.valueOf(j1).toString());
				prepare.setString(3,Date.valueOf(j2).toString());
				prepare.setString(4,Date.valueOf(j3).toString());
				prepare.setString(5,Date.valueOf(j4).toString());
				prepare.setString(6,Date.valueOf(j5).toString());
				prepare.setString(7,Date.valueOf(j6).toString());
				prepare.setString(8,Date.valueOf(j7).toString());
				res=prepare.executeQuery();
				double sommeht=0;
				//double sommett=0;
			
				while(res.next())
				{
					prepare1.setString(1,res.getObject(1).toString());
					res1=prepare1.executeQuery();
					if(res1.next())
					{
						JPanel n1=new JPanel();
						//n1.setPreferredSize(dim1);
						JTextArea l1=new JTextArea(res1.getObject(2).toString());
						l1.setFont(f3);
						l1.setLineWrap(true);
						l1.setEditable(false);
						l1.setWrapStyleWord(true);
						
						n1.add(l1);
						
						boxDesignation.add(n1);
						boxDesignation.add(new Separateur(140,5));
						JPanel n2=new JPanel();
						//n2.setPreferredSize(dim1);
						JLabel l2=new JLabel(res1.getObject(5).toString());
						sommeht+=Double.valueOf(res.getObject(2).toString())*Double.valueOf(res1.getObject(5).toString());
						//sommett+=Integer.valueOf(res2.getObject(4).toString())*0.2;
						double ss=Double.valueOf(res.getObject(2).toString())*Double.valueOf(res1.getObject(5).toString());
					
						l2.setFont(f3);
						n2.add(l2);
						
						boxPuht.add(n2);
						boxPuht.add(new Separateur(140,5));
						JPanel n3=new JPanel();
						//n3.setPreferredSize(dim1);
						JLabel l3=new JLabel(res.getObject(2).toString());
						l3.setFont(f3);
						n3.add(l3);
						
						boxtva.add(n3);
						boxtva.add(new Separateur(140,5));
						JPanel n4=new JPanel();
						//n4.setPreferredSize(dim1);
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
						
						if(res1.getObject(2).toString().length()>=1 && res1.getObject(2).toString().length()<=15)
						{
							n1.setPreferredSize(dim3);
							n2.setPreferredSize(dim3);
							n3.setPreferredSize(dim3);
							n4.setPreferredSize(dim3);
						}
						if(res1.getObject(2).toString().length()>15 && res1.getObject(2).toString().length()<=30)
						{
							n1.setPreferredSize(dim2);
							n2.setPreferredSize(dim2);
							n3.setPreferredSize(dim2);
							n4.setPreferredSize(dim2);
						}
						if(res1.getObject(2).toString().length()>30)
						{
							n1.setPreferredSize(dim);
							n2.setPreferredSize(dim);
							n3.setPreferredSize(dim);
							n4.setPreferredSize(dim);
						}
					
					
					}
				}
				
				tt.setLayout(new GridLayout(3,2));
				
				JPanel n1=new JPanel();
				n1.setPreferredSize(dim1);
				JLabel t1=new JLabel("Total : ");
				t1.setFont(f3);
				n1.add(t1);
				tt.add(n1);
				
				JPanel n2=new JPanel();
				n2.setPreferredSize(dim1);
				JLabel t2=new JLabel(String.valueOf(df.format(sommeht))+" euro");
				t2.setFont(f3);
				n2.add(t2);
				tt.add(n2);
					prepare.close();
				prepare1.close();
				//prepare2.close();
				conn.close();
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			/*conteneur3.add(boxDesignation);
			conteneur3.add(boxPuht);
			conteneur3.add(boxtva);
			conteneur3.add(boxTotal);*/
			
			
			
			
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
			
			Box conteneur7=Box.createHorizontalBox();
			conteneur7.add(boxDesignation);
			conteneur7.add(boxPuht);
			conteneur7.add(boxtva);
			conteneur7.add(boxTotal);
			
			
		//	Box hbp=Box.createVerticalBox();
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
			///jbi.addActionListener(new Impression1(conteneur3));
			try {
				jbi.addActionListener(new Telecharger(conteneur3,Employe.this));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			Bouton jb=new Bouton("retour");
			jb.addActionListener(new BoutonRetour());
			Box bjb=Box.createHorizontalBox();
			bjb.add(jb);
			bjb.add(new Separateur(5,5));
			bjb.add(jbi);
			
			
			boxprin.add(scroller1);
			boxprin.add(new Separateur(400,20));
			//boxprin.add(tt);
			//boxprin.add(new Separateur(400,20));
			boxprin.add(bjb);
			
			//creerPdf(scroller1,listeFacture.get(s)[5].toString());
			
			
			
			Employe.this.add(boxprin);
			Employe.this.updateUI();
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
				if(imageIn.length()>100)
				{
					JOptionPane jop3 = new JOptionPane();
					jop3.showMessageDialog(null, "NOM IMAGE TROP LONG", "Erreur", JOptionPane.ERROR_MESSAGE);
					
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
							if(posteIn.getText().length()>99)
							{
								JOptionPane jop3 = new JOptionPane();
								jop3.showMessageDialog(null, "NOM IMAGE TROP LONG", "Erreur", JOptionPane.ERROR_MESSAGE);
								
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
												String s="insert into EMPLOYE (MATRICULE_EMPLOYE, PRENOM_EMPLOYE, NOM_EMPLOYE, DATE_NAISSANCE, ADRESSE_EMPLOYE, QUALIFICATION, POSTE, EMAIL_EMPLOYE, image, TELEPHONE_EMPLOYE)";
												s+=" values (?,?,?,?,?,?,?,?,?,?)";
												//Date date =  cdate.getTi
												prepare = conn.prepareStatement(s);
												//On remplace le premier trou par le nom du professeur
												prepare.setString(1,telIn.getText());
												prepare.setString(2,prenomIn.getText());
												prepare.setString(3,nomIn.getText());
												prepare.setString(4, new java.sql.Date(cdate.getDate().getTime()).toString());
												prepare.setString(5,adresseIn.getText());
												prepare.setString(6,qualIn.getText());
												prepare.setString(10,telIn.getText());
												prepare.setString(7,posteIn.getText());
												prepare.setString(8,emailIn.getText());
												prepare.setString(9,imageIn);
											}
											else
											{
												String s="update EMPLOYE "
														+ "set MATRICULE_EMPLOYE=?,"
														+ " PRENOM_EMPLOYE=?,"
														+ " NOM_EMPLOYE=?,"
														+ " DATE_NAISSANCE=?,"
														+ " ADRESSE_EMPLOYE=?,"
														+ " QUALIFICATION=?, "
														+ " POSTE=?, "
														+ " EMAIL_EMPLOYE=?,"
														+ " image=?,"
														+ " TELEPHONE_EMPLOYE=?";
												s+=" where MATRICULE_EMPLOYE=?";
												//Date date =  cdate.getTi
												prepare = conn.prepareStatement(s);
												//On remplace le premier trou par le nom du professeur
												prepare.setString(1,telIn.getText());
												prepare.setString(2,prenomIn.getText());
												prepare.setString(3,nomIn.getText());
												prepare.setString(4, new java.sql.Date(cdate.getDate().getTime()).toString());
												prepare.setString(5,adresseIn.getText());
												prepare.setString(6,qualIn.getText());
												prepare.setString(10,telIn.getText());
												prepare.setString(7,posteIn.getText());
												prepare.setString(8,emailIn.getText());
												prepare.setString(9,imageIn);
												prepare.setString(11,id);
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
													if(listeEmploye.containsKey(id))
													{
														listeEmploye.remove(id);
														boxMatricule.removeAll();
														boxNom.removeAll();
														boxBouton.removeAll();
														boxPrenom.removeAll();
														colin=1;
														JPanel panmatri=new JPanel();
														panmatri.setPreferredSize(dim1);
														panmatri.add(matricule);
														panmatri.setBackground(c);
														//matricule.setPreferredSize(dim);
														boxMatricule.add(panmatri);
														
														JPanel pannom=new JPanel();
														pannom.setPreferredSize(dim1);
														pannom.setBackground(c);
														//nom.setPreferredSize(dim);
														pannom.add(nom);
														boxNom.add(pannom);
														
														JPanel panpnm=new JPanel();
														panpnm.setPreferredSize(dim1);
														panpnm.add(prenom);
														panpnm.setBackground(c);
														boxPrenom.add(panpnm);
														//prenom.setPreferredSize(dim);
													
														JPanel panplus=new JPanel();
														panplus.setPreferredSize(dim1);
														panplus.add(plus);
														panplus.setBackground(c);
														//plus.setPreferredSize(dim);
														boxBouton.add(panplus);
														
														String []str= {telIn.getText(),prenomIn.getText(),nomIn.getText(),cdate.toString(),adresseIn.getText(),qualIn.getText(),posteIn.getText(),emailIn.getText(),imageIn,telIn.getText()};
														listeEmploye.put(telIn.getText(),str ) ;
														Set keys = listeEmploye.keySet();
														 
													    //obtenir un iterator des clés
													    Iterator itr = keys.iterator();
													 
													    String key="";
													    boxMatricule.add(new Separateur(50,5));
														boxNom.add(new Separateur(80,5));
														boxPrenom.add(new Separateur(80,5));
														boxBouton.add(new Separateur(40,5));
														
													    //affichage des pairs clé-valeur
													    while (itr.hasNext()) 
													    { 
													       // obtenir la clé
													       key = (String) itr.next();
	
															JPanel n1=new JPanel();
															//n1.setPreferredSize(dim);
															JLabel l1=new JLabel(key);
															l1.setFont(f3);
															n1.add(l1);
															//n1.setBackground(Color.white);
															boxMatricule.add(n1);
															boxMatricule.add(new Separateur(50,5));
															JPanel n2=new JPanel();
															//n2.setPreferredSize(dim);
															JTextArea l2=new JTextArea(listeEmploye.get(key)[2]);
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
															JTextArea l3=new JTextArea(listeEmploye.get(key)[1]);
															l3.setFont(f3);
															l3.setEditable(false);
															l3.setLineWrap(true);
															l3.setWrapStyleWord(true);
															
															n3.add(l3);
															//n3.setBackground(Color.white);
															boxPrenom.add(n3);
															boxPrenom.add(new Separateur(80,5));
															JPanel n4=new JPanel();
															//n4.setPreferredSize(dim);
														//	n4.setBackground(Color.white);
															Bouton b=new Bouton("voir plus",key);
															b.addActionListener(new Boutontest());
															if(colin%2==0)
															{
																n1.setBackground(couleurEcriture);
																n2.setBackground(couleurEcriture);
																n3.setBackground(couleurEcriture);
																n4.setBackground(couleurEcriture);
																l2.setBackground(couleurEcriture);
																l3.setBackground(couleurEcriture);
																b.setBackground(couleurEcriture);
															}
															else
															{
																n1.setBackground(Color.white);
																n2.setBackground(Color.white);
																n3.setBackground(Color.white);
																n4.setBackground(Color.white);
																l2.setBackground(Color.white);
																l3.setBackground(Color.white);
																b.setBackground(Color.white);
															}
															colin++;
															//b.setBackground(Color.white);
														//	b.setBorderPainted(false);
															b.setFont(f3);
															n4.add(b);
															//b.setPreferredSize(dim);
															boxBouton.add(n4);
															boxBouton.add(new Separateur(50,5));
															
															if(listeEmploye.get(key)[1].length()>=1  &&  listeEmploye.get(key)[1].length()<=15)
															{
																n1.setPreferredSize(dim1);
																n2.setPreferredSize(dim1);
																n3.setPreferredSize(dim1);
																n4.setPreferredSize(dim1);
																b.setPreferredSize(dim1);
															}
															if(listeEmploye.get(key)[1].length()>15 && listeEmploye.get(key)[1].length()<=30)
															{
																n1.setPreferredSize(dim2);
																n2.setPreferredSize(dim2);
																n3.setPreferredSize(dim2);
																n4.setPreferredSize(dim2);
																b.setPreferredSize(dim2);
															}
															if(listeEmploye.get(key)[1].length()>30)
																
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
													String []str= {telIn.getText(),prenomIn.getText(),nomIn.getText(),cdate.toString(),adresseIn.getText(),qualIn.getText(),posteIn.getText(),emailIn.getText(),imageIn,telIn.getText()};
													listeEmploye.put(telIn.getText(),str ) ;
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
													///n2.setPreferredSize(dim);
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
													JTextArea l3=new JTextArea(prenomIn.getText());
													l3.setFont(f3);
													l3.setEditable(false);
													l3.setLineWrap(true);
													l3.setWrapStyleWord(true);
													
													n3.add(l3);
													//n3.setBackground(Color.white);
													boxPrenom.add(n3);
													boxPrenom.add(new Separateur(80,5));
													JPanel n4=new JPanel();
													//n4.setPreferredSize(dim);
													
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
														l3.setBackground(couleurEcriture);
														b.setBackground(couleurEcriture);
													}
													else
													{
														n1.setBackground(Color.white);
														n2.setBackground(Color.white);
														n3.setBackground(Color.white);
														n4.setBackground(Color.white);
														l2.setBackground(Color.white);
														l3.setBackground(Color.white);
														b.setBackground(Color.white);
													}
													colin++;
												//	b.setBorderPainted(false);
													b.setFont(f3);
													n4.add(b);
													//b.setPreferredSize(dim);
													boxBouton.add(n4);
													boxBouton.add(new Separateur(50,5));
													
													
													if(prenomIn.getText().length()>=1  &&  prenomIn.getText().length()<=15)
													{
														n1.setPreferredSize(dim1);
														n2.setPreferredSize(dim1);
														n3.setPreferredSize(dim1);
														n4.setPreferredSize(dim1);
														b.setPreferredSize(dim1);
													}
													if(prenomIn.getText().length()>15 && prenomIn.getText().length()<=30)
													{
														n1.setPreferredSize(dim2);
														n2.setPreferredSize(dim2);
														n3.setPreferredSize(dim2);
														n4.setPreferredSize(dim2);
														b.setPreferredSize(dim2);
													}
													if(prenomIn.getText().length()>30)
														
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
				
			
			Employe.this.removeAll();//    1/1/1   11/1/1   11/11/1 1/11/1
			Employe.this.add(conteneur);
			Employe.this.updateUI();
		}
	}
	
	
	class BoutonModifier implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Object b=arg0.getSource();
			String s=new String();
			s=((Bouton)b).getMat();
			Employe.this.removeAll();
			
			JLabel titre1=new JLabel("Modifier employe");
			titre1.setFont(f);
			JPanel panTitre=new JPanel();
			panTitre.add(titre1);
			Box boxTitre1=Box.createHorizontalBox();
			boxTitre1.add(panTitre);
			Box boxPrincipal=Box.createVerticalBox();
			
			JLabel prenomEmp = new JLabel("Prenom: ");
			prenomEmp.setFont(f1);
			prenomEmp.setForeground(couleurEcriture);
			prenomIn=new JTextField(listeEmploye.get(s)[1]);
			Box boxPrnom=Box.createHorizontalBox();
			boxPrnom.add(prenomEmp);
			boxPrnom.add(prenomIn);
			
			JLabel nomEmp = new JLabel("Nom: ");
			nomEmp.setFont(f1);
			nomEmp.setForeground(couleurEcriture);
			nomIn=new JTextField(listeEmploye.get(s)[2]);
			Box boxName=Box.createHorizontalBox();
			boxName.add(nomEmp);
			boxName.add(nomIn);
			
			JLabel matEmp = new JLabel("Matricule: ");
			matEmp.setForeground(couleurEcriture);
			matEmp.setFont(f1);
			telIn=new JTextField(s,9);
			Box boxMat1=Box.createHorizontalBox();
			boxMat1.add(matEmp);
			boxMat1.add(telIn);
			JLabel erreur=new JLabel("le matricule est obligatoire");
			erreur.setForeground(Color.red);
			erreur.setFont(new Font("Arial", Font.BOLD, 12));
			Box boxMat=Box.createVerticalBox();
			boxMat.add(boxMat1);
			boxMat.add(erreur);
			
			JLabel poste = new JLabel("Poste:  ");
			poste.setForeground(couleurEcriture);
			poste.setFont(f1);
			posteIn=new JTextField(listeEmploye.get(s)[6],29);
			Box boxposte=Box.createHorizontalBox();
			boxposte.add(poste);
			boxposte.add(posteIn);
			
			JLabel dateNaissance = new JLabel("Date de naissance: ");
			dateNaissance.setForeground(couleurEcriture);
			dateNaissance.setFont(f1);
			
			
			/* SpinnerModel modelJour = new SpinnerNumberModel(
		                7, //valeur initiale
		                1, //valeur minimum
		                31, //valeur maximum
		                1 //pas
		    ); 
		   sp = new JSpinner(model); 
		    sp.setBounds(100,100,45,30);  */
			
			//JCalendar cr = new JCalendar();
			/*ZoneId defaultZoneId = ZoneId.systemDefault();
			LocalDate ld=LocalDate.parse(listeEmploye.get(s)[3]);
			 Date date = (Date) Date.from(ld.atStartOfDay(defaultZoneId).toInstant());
			cdate.setDate(date);*/
			Bouton cr=new Bouton("choisir date");
			cr.addActionListener(new ChoixDate());
			Box boxdte=Box.createHorizontalBox();
			boxdte.add(dateNaissance);
			//boxdte.add(dteIn);
			Separateur paneldater=new Separateur(100,40);
			paneldater.add(cr);
			boxdte.add(paneldater);
			
			JLabel photo = new JLabel("Photo ");
			photo.setForeground(couleurEcriture);
			photo.setFont(f1);
			imageIn=listeEmploye.get(s)[8];
			Bouton imageInb=new Bouton("modifier image");
			imageInb.addActionListener(new ChoixImage());
			Box boxim=Box.createHorizontalBox();
			boxim.add(photo);
			boxim.add(imageInb);
			
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
			telIn=new JTextField(listeEmploye.get(s)[9],20);
			Box boxtel=Box.createHorizontalBox();
			boxtel.add(telEmp);
			boxtel.add(telIn);
			
			JLabel email = new JLabel("Email: ");
			email.setForeground(couleurEcriture);
			email.setFont(f1);
			emailIn=new JTextField(listeEmploye.get(s)[7]);
			Box boxemail=Box.createHorizontalBox();
			boxemail.add(email);
			boxemail.add(emailIn);
		
			JLabel adresseEmp = new JLabel("Adresse: ");
			adresseEmp.setForeground(couleurEcriture);
			adresseEmp.setFont(f1);
			adresseIn=new JTextField(listeEmploye.get(s)[4]);
			Box boxad=Box.createHorizontalBox();
			boxad.add(adresseEmp);
			boxad.add(adresseIn);
			
			JLabel labelqual = new JLabel("Apptitudes");
			labelqual.setForeground(couleurEcriture);
			labelqual.setFont(f);
			JPanel panelqual=new JPanel();
			panelqual.add(labelqual);
			Box boxqual=Box.createHorizontalBox();
			boxqual.add(panelqual);
			
			qualIn=new JTextArea(listeEmploye.get(s)[5]);
			qualIn.setFont(f1);
			qualIn.setForeground(couleurEcriture);
			JScrollPane scrollqual=new JScrollPane(qualIn);
			scrollqual.setPreferredSize(new Dimension(100,50));
			JLabel label=new JLabel("Qualifications: ");
			label.setFont(f1);
			label.setForeground(couleurEcriture);
			Box total3=Box.createHorizontalBox();
			total3.add(label);
			total3.add(scrollqual);
			
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
			boxPrincipal.add(boxPrnom);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxName);
		//	boxPrincipal.add(new Separateur(600,20));
			//boxPrincipal.add(boxMat);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxim);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxdte);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxDetail);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxtel);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxad);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxemail);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(boxqual);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(total3);
			boxPrincipal.add(new Separateur(600,20));
			boxPrincipal.add(retourPayer);
			Employe.this.add(boxPrincipal);
			Employe.this.updateUI();
		}
	}
	
	
	class BoutonRecherche implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			if(listeEmploye.get(recherche.getText())!=null)
			{
				Employe.this.removeAll();
				JLabel tr=new JLabel("Employe Recherche");
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
				
				JLabel matri = new JLabel("MATRICULE");
				matri.setFont(f1);
				JPanel panmatri=new JPanel();
				panmatri.setPreferredSize(dim1);
				panmatri.add(matri);
				panmatri.setBackground(c);
				//matricule.setPreferredSize(dim);
				bm.add(panmatri);
				JLabel nom1 = new JLabel("NOM");
				nom1.setFont(f1);
				JPanel pannom=new JPanel();
				pannom.setPreferredSize(dim1);
				pannom.setBackground(c);
				//nom.setPreferredSize(dim);
				pannom.add(nom1);
				bn.add(pannom);
				JLabel pre = new JLabel("PRENOM");
				pre.setFont(f1);
				JPanel panpnm=new JPanel();
				panpnm.setPreferredSize(dim1);
				panpnm.add(pre);
				panpnm.setBackground(c);
				bpr.add(panpnm);
				//prenom.setPreferredSize(dim);
				JLabel plus1 = new JLabel("PLUS");
				plus1.setFont(f1);
				JPanel panplus=new JPanel();
				panplus.setPreferredSize(dim1);
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
						JTextArea l2=new JTextArea(listeEmploye.get(recherche.getText())[2]);
						l2.setFont(f3);
						l2.setEditable(false);
						l2.setLineWrap(true);
						l2.setWrapStyleWord(true);
						
						n2.add(l2);
						
						bn.add(n2);
						bn.add(new Separateur(80,5));
						JPanel n3=new JPanel();
						//n3.setPreferredSize(dim);
						JTextArea l3=new JTextArea(listeEmploye.get(recherche.getText())[1]);
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
						
						if(listeEmploye.get(recherche.getText())[1].length()>=1  &&  listeEmploye.get(recherche.getText())[1].length()<=15)
						{
							n1.setPreferredSize(dim1);
							n2.setPreferredSize(dim1);
							n3.setPreferredSize(dim1);
							n4.setPreferredSize(dim1);
							b.setPreferredSize(dim1);
						}
						if(listeEmploye.get(recherche.getText())[1].length()>15 && listeEmploye.get(recherche.getText())[1].length()<=30)
						{
							n1.setPreferredSize(dim2);
							n2.setPreferredSize(dim2);
							n3.setPreferredSize(dim2);
							n4.setPreferredSize(dim2);
							b.setPreferredSize(dim2);
						}
						if(listeEmploye.get(recherche.getText())[1].length()>30)
							
						{
							n1.setPreferredSize(dim);
							n2.setPreferredSize(dim);
							n3.setPreferredSize(dim);
							n4.setPreferredSize(dim);
							b.setPreferredSize(dim);
						}
					
						
				Employe.this.add(bp);
				Employe.this.updateUI();
				
				
			}
			else
			{
			    JOptionPane jop4 = new JOptionPane();
				jop4.showMessageDialog(null, "aucun employe trouve", "Information", JOptionPane.INFORMATION_MESSAGE);
	
			}
		}
	}
	
	class BoutonSupprimer implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
			String s = jop.showInputDialog(null, "Donner le matricule de l'employer ","Supprimer un employe", JOptionPane.QUESTION_MESSAGE);
			
			if(s!=null)
			{
			int option = jop.showConfirmDialog(null,
					"Voulez-vous vraiment supprimer l'employe ?",
					"suppression",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			
					if(option == JOptionPane.OK_OPTION)
					{
						if(listeEmploye.containsKey(s))
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
									String str="delete from employe where MATRICULE_EMPLOYE=?";
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
							listeEmploye.remove(s);
							boxMatricule.removeAll();
							boxNom.removeAll();
							boxBouton.removeAll();
							boxPrenom.removeAll();
							colin=1;
							JPanel panmatri=new JPanel();
							panmatri.setPreferredSize(dim1);
							panmatri.add(matricule);
							panmatri.setBackground(c);
							//matricule.setPreferredSize(dim);
							boxMatricule.add(panmatri);
							
							JPanel pannom=new JPanel();
							pannom.setPreferredSize(dim1);
							pannom.setBackground(c);
							//nom.setPreferredSize(dim);
							pannom.add(nom);
							boxNom.add(pannom);
							
							JPanel panpnm=new JPanel();
							panpnm.setPreferredSize(dim1);
							panpnm.add(prenom);
							panpnm.setBackground(c);
							boxPrenom.add(panpnm);
							//prenom.setPreferredSize(dim);
						
							JPanel panplus=new JPanel();
							panplus.setPreferredSize(dim1);
							panplus.add(plus);
							panplus.setBackground(c);
							//plus.setPreferredSize(dim);
							boxBouton.add(panplus);
							
							
							Set keys = listeEmploye.keySet();
							 
						    //obtenir un iterator des clés
						    Iterator itr = keys.iterator();
						 
						    String key="";
						    boxMatricule.add(new Separateur(50,5));
							boxNom.add(new Separateur(80,5));
							boxPrenom.add(new Separateur(80,5));
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
							//	n2.setPreferredSize(dim);
								JTextArea l2=new JTextArea(listeEmploye.get(key)[2]);
								l2.setFont(f3);
								l2.setEditable(false);
								l2.setLineWrap(true);
								l2.setWrapStyleWord(true);
								
								n2.add(l2);
								//n2.setBackground(Color.white);
								boxNom.add(n2);
								boxNom.add(new Separateur(80,5));
								JPanel n3=new JPanel();
							//	n3.setPreferredSize(dim);
								JTextArea l3=new JTextArea(listeEmploye.get(key)[1]);
								l3.setFont(f3);
								l3.setEditable(false);
								l3.setLineWrap(true);
								l3.setWrapStyleWord(true);
								
								n3.add(l3);
								//n3.setBackground(Color.white);
								boxPrenom.add(n3);
								boxPrenom.add(new Separateur(80,5));
								JPanel n4=new JPanel();
							//	n4.setPreferredSize(dim);
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
									l3.setBackground(couleurEcriture);
									b.setBackground(couleurEcriture);
								}
								else
								{
									n1.setBackground(Color.white);
									n2.setBackground(Color.white);
									n3.setBackground(Color.white);
									n4.setBackground(Color.white);
									l2.setBackground(Color.white);
									l3.setBackground(Color.white);
									b.setBackground(Color.white);
								}
								colin++;
								//b.setBorderPainted(false);
								b.setFont(f3);
								n4.add(b);
							//	b.setPreferredSize(dim);
								boxBouton.add(n4);
								boxBouton.add(new Separateur(50,5));
								
								if(listeEmploye.get(key)[1].length()>=1  &&  listeEmploye.get(key)[1].length()<=15)
								{
									n1.setPreferredSize(dim1);
									n2.setPreferredSize(dim1);
									n3.setPreferredSize(dim1);
									n4.setPreferredSize(dim1);
									b.setPreferredSize(dim1);
								}
								if(listeEmploye.get(key)[1].length()>15 && listeEmploye.get(key)[1].length()<=30)
								{
									n1.setPreferredSize(dim2);
									n2.setPreferredSize(dim2);
									n3.setPreferredSize(dim2);
									n4.setPreferredSize(dim2);
									b.setPreferredSize(dim2);
								}
								if(listeEmploye.get(key)[1].length()>30)
									
								{
									n1.setPreferredSize(dim);
									n2.setPreferredSize(dim);
									n3.setPreferredSize(dim);
									n4.setPreferredSize(dim);
									b.setPreferredSize(dim);
								}

						    }
						    JOptionPane jop4 = new JOptionPane();
							jop4.showMessageDialog(null, "vous avez supprimer l'employe", "Information", JOptionPane.INFORMATION_MESSAGE);
				
						}
						else
						{
							JOptionPane jop1 = new JOptionPane();
							jop1.showMessageDialog(null, " l'employer n'existe pas", "Information", JOptionPane.INFORMATION_MESSAGE);
				
						}
					
					}
					else
					{
						JOptionPane jop1 = new JOptionPane();
						jop1.showMessageDialog(null, "vous avez annuler la suppression", "Information", JOptionPane.INFORMATION_MESSAGE);
			
					}
			}
					Employe.this.add(conteneur);
					Employe.this.updateUI();
			}
	}

}
