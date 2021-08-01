import javax.swing.JPanel;
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

public class Service extends JPanel 
{


	
		private Hashtable<String, String[]> listeService;
		private JTextField recherche,prenomIn,matriculeIn,emailIn,telIn,adresseIn;
		private JTextArea nomIn;
		private Box boxTitre,boxMatricule,boxNom,boxTelephone,boxBouton,conteneur,boxRecherche;
		private JLabel titre,textRecherche,matricule,nom,prenom,plus;
		private Bouton boutonRecherche;
		private Color c;
		private JPanel conteneur1;
		private JScrollPane scroller;
		private Font f,f1,f2,f3;
		private Color couleurEcriture;
		private Dimension dim,dim1,dim2,dim11,dim12,dim13;
		private int colin=1;
		private String mser;
		
		public Service()
		{
			super();
			
			couleurEcriture=new Color(150,141,141,255);
			c=new Color(228,213,213,255);
			dim=new Dimension(148,150);
			dim1=new Dimension(148,50);
			dim2=new Dimension(148,100);
			dim11=new Dimension(400,50);
			dim12=new Dimension(400,100);
			dim13=new Dimension(400,150);
			f=new Font("Arial", Font.BOLD, 30);
			f1=new Font("Arial", Font.BOLD, 20);
			f3=new Font("Arial", Font.BOLD, 15);
			f2=new Font("Arial", Font.BOLD, 25);
			titre=new JLabel("Nos des Services");
			listeService=new Hashtable<String, String[]>();
			recherche=new JTextField("code du service");
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
			
			matricule=new JLabel("CODEService");
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
			prenom=new JLabel("PRIX");
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
				String tabcli="create table IF NOT EXISTS SERVICE ("+
						   "CODE_SERVICE         VARCHAR(30)          not null,"+
						   "LIBELLE_SERVICE      TEXT                 null,"+
						   "CATEGORIE_SERVICE    TEXT                 null,"+
						   "PRIX_SERVICE         REAL                null,"+
						   "SALAIRE_SERVICE      REAL              null,"+
						   "constraint PK_SERVICE primary key (CODE_SERVICE)"+
						")";





				state7.addBatch(tabcli);
				state7.executeBatch();
				state7.clearBatch();
				state7.close();	
				
				
				
				
				//Création d'un objet Statement
				Statement state = conn.createStatement();
				//L'objet ResultSet contient le résultat de la requête SQL
				ResultSet result = state.executeQuery("SELECT * FROM Service");
				//On récupère les MetaData
				ResultSetMetaData resultMeta = result.getMetaData();
				//On affiche le nom des colonnes
				boxMatricule.add(new Separateur(80,5));
				boxNom.add(new Separateur(80,5));
				boxTelephone.add(new Separateur(80,5));
				boxBouton.add(new Separateur(80,5));
				//String []saut=new String[20];
				int cp=0;
				mser="001";
				while(result.next())
				{
					String []s=new String[resultMeta.getColumnCount()-1];
					for(int i = 2; i <= resultMeta.getColumnCount(); i++)
					{
						if(result.getString(i)!=null)
							s[i-2]=new String(result.getString(i));
					}
					listeService.put(result.getObject(1).toString(),s ) ;
					//saut[cp++]=new String(result.getObject(1).toString());
					JPanel n1=new JPanel();
					
					mser=result.getObject(1).toString();
					JLabel l1=new JLabel(result.getObject(1).toString());
					l1.setFont(f3);
					n1.add(l1);
					//n1.setBackground(Color.white);
					boxMatricule.add(n1);
					boxMatricule.add(new Separateur(80,5));
					JPanel n2=new JPanel();
					
					JTextArea l2=new JTextArea(result.getObject(2).toString());
					l2.setFont(f3);
					l2.setEditable(false);
					l2.setLineWrap(true);
					l2.setWrapStyleWord(true);
					
					JScrollPane scrollqual=new JScrollPane(l2);
					scrollqual.setPreferredSize(new Dimension(400,150));
					
					
					//JScrollPane js=new JScrollPane(l2);
					//l2.setPreferredSize(new Dimension(148,50));
					n2.add(scrollqual);
					//n2.setBackground(Color.white);
					boxNom.add(n2);
					boxNom.add(new Separateur(400,5));
					JPanel n3=new JPanel();
					
					JLabel l3=new JLabel(result.getObject(4).toString());
					l3.setFont(f3);
					n3.add(l3);
					//n3.setBackground(Color.white);
					boxTelephone.add(n3);
					boxTelephone.add(new Separateur(80,5));
					JPanel n4=new JPanel();
					
					//n4.setBackground(Color.white);
					Bouton b=new Bouton("voir plus",result.getObject(1).toString());
					b.addActionListener(new Boutontest());
					//b.setBackground(Color.white);
					
					b.setBorderPainted(false);
					b.setFont(f3);
					n4.add(b);
					
					boxBouton.add(n4);
					boxBouton.add(new Separateur(80,5));
					
					
					
					if(result.getObject(2).toString().length()>=1  &&  result.getObject(2).toString().length()<=50)
					{
						n1.setPreferredSize(dim1);
						n2.setPreferredSize(dim11);
						n3.setPreferredSize(dim1);
						n4.setPreferredSize(dim1);
						b.setPreferredSize(dim1);
					}
					if(result.getObject(2).toString().length()>50 && result.getObject(2).toString().length()<=100)
					{
						n1.setPreferredSize(dim2);
						n2.setPreferredSize(dim12);
						n3.setPreferredSize(dim2);
						n4.setPreferredSize(dim2);
						b.setPreferredSize(dim2);
					}
					if(result.getObject(2).toString().length()>100)
						
					{
						n1.setPreferredSize(dim);
						n2.setPreferredSize(dim13);
						n3.setPreferredSize(dim);
						n4.setPreferredSize(dim);
						b.setPreferredSize(dim);
					}
					
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
			Bouton boutonAjout=new Bouton("ajouter Service");
			boutonAjout.addActionListener(new BoutonAjouter());
			boutonAjout.setPreferredSize(new Dimension(200,40));
			//JPanel panAjout=new JPanel();
			Box boxAjout=Box.createHorizontalBox();
			//boxAjout.add(labelAjout);
			boxAjout.add(boutonAjout);
			boxAjout.add(new Separateur(20,20));
			//JLabel labelSup=new JLabel("Voulez vous supprimer un employe: ");
			Bouton boutonSup=new Bouton("supprimer Service");
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
				Service.this.removeAll();
				JLabel titre1=new JLabel("Details Service");
				titre1.setFont(f);
				JPanel panTitre=new JPanel();
				panTitre.add(titre1);
				Box boxTitre1=Box.createHorizontalBox();
				boxTitre1.add(panTitre);
				Box boxfirst=Box.createVerticalBox();
				JLabel photo,labelqual,matEmp,dateNaissance,adresseEmp,telEmp,poste,email,labelImage;
				JTextArea prenomEmp;
				prenomEmp=new JTextArea(listeService.get(s)[0]);
				prenomEmp.setFont(f1);
				prenomEmp.setForeground(couleurEcriture);
				prenomEmp.setEditable(false);
				prenomEmp.setLineWrap(true);
				prenomEmp.setWrapStyleWord(true);
				
				
				JScrollPane scrollqual=new JScrollPane(prenomEmp);
				scrollqual.setPreferredSize(new Dimension(600,100));
				
				JPanel panprnm=new JPanel();
				panprnm.setLayout(new BorderLayout());
				panprnm.setPreferredSize(new Dimension(600,100));
				panprnm.add(scrollqual,BorderLayout.WEST);
				panprnm.setBackground(Color.white);
				boxfirst.add(panprnm);
				boxfirst.add(new Separateur(600,10));
				matEmp=new JLabel("CodeService: "+s);
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
				
				/*JLabel detailContact=new JLabel("Detail contact");
				detailContact.setFont(f);
				detailContact.setForeground(couleurEcriture);
				JPanel panDetail=new JPanel();
				panDetail.add(detailContact);*/
				//Box boxDetail=Box.createHorizontalBox();
				//boxDetail.add(panDetail);
				Box total2h=Box.createVerticalBox();
				//total2.add(boxDetail);
				//Box total2=Box.createHorizontalBox();
				telEmp=new JLabel("Salaire: "+listeService.get(s)[3]);
				telEmp.setForeground(couleurEcriture);
				telEmp.setFont(f1);
				JPanel pantelemp=new JPanel();
				pantelemp.setLayout(new BorderLayout());
				pantelemp.setPreferredSize(new Dimension(500,25));
				pantelemp.add(telEmp,BorderLayout.WEST);
				pantelemp.setBackground(Color.white);
				total2h.add(pantelemp);
				total2h.add(new Separateur(600,10));
				email=new JLabel("Prix: "+listeService.get(s)[2]);
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
				adresseEmp=new JLabel("Categorie: "+listeService.get(s)[1]);
				adresseEmp.setForeground(couleurEcriture);
				adresseEmp.setFont(f1);
				total2h.add(adresseEmp);
				total2h.add(new Separateur(600,10));
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
				//boutonModifier.setPreferredSize(new Dimension(120,20));
				//boutonModifier.setFont(f3);
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
				contain.add(total2h);
				contain.add(new Separateur(600,10));
				contain.add(boxSwitch);
				contain.add(new Separateur(600,10));
				contain.add(retourPayer);
				Service.this.add(contain);
				Service.this.updateUI();
			}
			
		}
		
		
		class BoutonRetour implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Service.this.removeAll();
				Service.this.add(conteneur);
				Service.this.updateUI();
			}
		}
		
		
		class BoutonAjouter implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Service.this.removeAll();
				
				JLabel titre1=new JLabel("Ajouter Service");
				titre1.setFont(f);
				JPanel panTitre=new JPanel();
				panTitre.add(titre1);
				Box boxTitre1=Box.createHorizontalBox();
				boxTitre1.add(panTitre);
				Box boxPrincipal=Box.createVerticalBox();
				
				JLabel nomEmp = new JLabel("Nom: ");
				nomEmp.setFont(f1);
				nomEmp.setForeground(couleurEcriture);
				nomIn=new JTextArea("");
				
				JScrollPane scrollqual=new JScrollPane(nomIn);
				scrollqual.setPreferredSize(new Dimension(500,100));
				
				
				Box boxName=Box.createHorizontalBox();
				boxName.add(nomEmp);
				boxName.add(scrollqual);
				
				JLabel matEmp = new JLabel("CodeService: ");
				matEmp.setForeground(couleurEcriture);
				matEmp.setFont(f1);
				if(mser=="001")
				{
					matriculeIn=new JTextField(mser);
					mser="002";
				}
				else
				{
					int i=Integer.valueOf(mser);
					mser=String.valueOf(i+1);
					matriculeIn=new JTextField(mser);
				}
				matriculeIn.setEditable(false);
				Box boxMat1=Box.createHorizontalBox();
				boxMat1.add(matEmp);
				boxMat1.add(matriculeIn);
				
				
				
				
				
				
				JLabel erreur=new JLabel("le code service est obligatoire");
				erreur.setForeground(Color.red);
				erreur.setFont(new Font("Arial", Font.BOLD, 12));
				Box boxMat=Box.createVerticalBox();
				boxMat.add(boxMat1);
				boxMat.add(erreur);
				
				/*JLabel detailContact=new JLabel("Detail contact");
				detailContact.setFont(f2);
				detailContact.setForeground(couleurEcriture);
				JPanel panDetail=new JPanel();
				panDetail.add(detailContact);
				Box boxDetail=Box.createHorizontalBox();
				boxDetail.add(panDetail);
				*/
				JLabel telEmp = new JLabel("Salaire: ");
				telEmp.setForeground(couleurEcriture);
				telEmp.setFont(f1);
				telIn=new JTextField("100",20);
				Box boxtel=Box.createHorizontalBox();
				boxtel.add(telEmp);
				boxtel.add(telIn);
				
				JLabel email = new JLabel("Prix: ");
				email.setForeground(couleurEcriture);
				email.setFont(f1);
				emailIn=new JTextField("150");
				Box boxemail=Box.createHorizontalBox();
				boxemail.add(email);
				boxemail.add(emailIn);
			
				JLabel adresseEmp = new JLabel("Categorie: ");
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
				boxPrincipal.add(boxName);
				boxPrincipal.add(new Separateur(600,20));
				boxPrincipal.add(boxMat);
				boxPrincipal.add(new Separateur(600,20));
				boxPrincipal.add(boxtel);
				boxPrincipal.add(new Separateur(600,20));
				boxPrincipal.add(boxad);
				boxPrincipal.add(new Separateur(600,20));
				boxPrincipal.add(boxemail);
				boxPrincipal.add(new Separateur(600,20));
				boxPrincipal.add(retourPayer);
				Service.this.add(boxPrincipal);
				Service.this.updateUI();
			}
		}
		
		public  boolean isNumeric(String str) 
		{ 
			  try 
			  {  
			    Double.parseDouble(str);  
			    return true;
			  } catch(NumberFormatException e){  
			    return false;  
			  }  
		}
		
		
		
		class BoutonRecherche implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				if(listeService.get(recherche.getText())!=null)
				{
					Service.this.removeAll();
					JLabel tr=new JLabel("Service Recherche");
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
					
					JLabel matri = new JLabel("Code Service");
					matri.setFont(f1);
					JPanel panmatri=new JPanel();
					panmatri.setPreferredSize(new Dimension(148,35));
					panmatri.add(matri);
					panmatri.setBackground(c);
					//matricule.setPreferredSize(dim);
					bm.add(panmatri);
					JLabel nom1 = new JLabel("Prix");
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
							JTextArea l2=new JTextArea(listeService.get(recherche.getText())[2]);
							l2.setFont(f3);
							
							n2.add(l2);
							
							bn.add(n2);
							bn.add(new Separateur(400,5));
							JPanel n3=new JPanel();
							//n3.setPreferredSize(dim);
							JTextArea l3=new JTextArea(listeService.get(recherche.getText())[0]);
							l3.setEditable(false);
							l3.setLineWrap(true);
							l3.setWrapStyleWord(true);
							l3.setFont(f3);
							
							JScrollPane scrollqual=new JScrollPane(l3);
							scrollqual.setPreferredSize(new Dimension(400,150));
							
							
							n3.add(scrollqual);
							
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
							if(listeService.get(recherche.getText())[0].length()>=1  &&  listeService.get(recherche.getText())[0].length()<=50)
							{
								n1.setPreferredSize(dim1);
								n2.setPreferredSize(dim1);
								n3.setPreferredSize(dim11);
								n4.setPreferredSize(dim1);
								b.setPreferredSize(dim1);
							}
							if(listeService.get(recherche.getText())[0].length()>50 && listeService.get(recherche.getText())[0].length()<=100)
							{
								n1.setPreferredSize(dim2);
								n2.setPreferredSize(dim2);
								n3.setPreferredSize(dim12);
								n4.setPreferredSize(dim2);
								b.setPreferredSize(dim2);
							}
							if(listeService.get(recherche.getText())[0].length()>100)
								
							{
								n1.setPreferredSize(dim);
								n2.setPreferredSize(dim);
								n3.setPreferredSize(dim13);
								n4.setPreferredSize(dim);
								b.setPreferredSize(dim);
							}
							
					Service.this.add(bp);
					Service.this.updateUI();
					
					
				}
				else
				{
				    JOptionPane jop4 = new JOptionPane();
					jop4.showMessageDialog(null, "aucun service trouve", "Information", JOptionPane.INFORMATION_MESSAGE);
		
				}
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
				if(matriculeIn.getText().length()==0 || matriculeIn.getText().length()>9)
				{
					JOptionPane jop3 = new JOptionPane();
					jop3.showMessageDialog(null, "ERREUR SUR CODE Service", "Erreur", JOptionPane.ERROR_MESSAGE);
					
				}
				else
				{
					if(nomIn.getText().length()>1000)
					{
						JOptionPane jop3 = new JOptionPane();
						jop3.showMessageDialog(null, "NOM TROP LONG", "Erreur", JOptionPane.ERROR_MESSAGE);
						
					}
					else
					{
						if(!isNumeric(telIn.getText()) || !isNumeric(emailIn.getText()))
						{
							JOptionPane jop3 = new JOptionPane();
							jop3.showMessageDialog(null, "ERREUR SALAIRE ET PRIX SONT DES REELS", "Erreur", JOptionPane.ERROR_MESSAGE);
							
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
													String s="insert into Service (CODE_Service, Libelle_Service, categorie_Service, prix_Service, salaire_Service)";
															s+=" values (?,?,?,?,?)";
													//Date date =  cdate.getTi
													prepare = conn.prepareStatement(s);
													//On remplace le premier trou par le nom du professeur
													prepare.setString(1,matriculeIn.getText());
													prepare.setString(2,nomIn.getText());
													prepare.setString(3,adresseIn.getText());
													prepare.setDouble(5,Double.valueOf(telIn.getText()));
													prepare.setDouble(4,Double.valueOf(emailIn.getText()));
													
												}
												else
												{
													String s="update Service "
															+ "set CODE_Service=?,"
															+ " libelle_Service=?,"
															+ " categorie_Service=?,"
															+ " prix_Service=?,"
															+ " salaire_Service=?";
													s+=" where CODE_Service=?";
													//Date date =  cdate.getTi
													prepare = conn.prepareStatement(s);
													//On remplace le premier trou par le nom du professeur
													prepare.setString(1,matriculeIn.getText());
													prepare.setString(2,nomIn.getText());
													prepare.setString(3,adresseIn.getText());
													prepare.setDouble(5,Double.valueOf(telIn.getText()));
													prepare.setDouble(4,Double.valueOf(emailIn.getText()));
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
														if(listeService.containsKey(id))
														{
															listeService.remove(id);
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
															listeService.put(matriculeIn.getText(),str ) ;
															Set keys = listeService.keySet();
															 
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
																//JLabel l2=new JLabel(listeService.get(key)[0]);
																//l2.setFont(f3);
																
																JTextArea l2=new JTextArea(listeService.get(key)[0]);
																l2.setFont(f3);
																l2.setEditable(false);
																l2.setLineWrap(true);
																l2.setWrapStyleWord(true);
																
																JScrollPane scrollqual=new JScrollPane(l2);
																scrollqual.setPreferredSize(new Dimension(400,150));
																
																//JScrollPane js=new JScrollPane(l2);
																//js.setPreferredSize(dim);
																n2.add(scrollqual);
																
																//n2.add(l2);
																//n2.setBackground(Color.white);
																boxNom.add(n2);
																boxNom.add(new Separateur(400,5));
																JPanel n3=new JPanel();
																//n3.setPreferredSize(dim);
																JLabel l3=new JLabel(listeService.get(key)[2]);
																l3.setFont(f3);
																
																n3.add(l3);
																//n3.setBackground(Color.white);
																boxTelephone.add(n3);
																boxTelephone.add(new Separateur(80,5));
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
																	l2.setBackground(couleurEcriture);
																	n3.setBackground(couleurEcriture);
																	n4.setBackground(couleurEcriture);
																	b.setBackground(couleurEcriture);
																}
																else
																{
																	n1.setBackground(Color.white);
																	n2.setBackground(Color.white);
																	l2.setBackground(Color.white);
																	n3.setBackground(Color.white);
																	n4.setBackground(Color.white);
																	b.setBackground(Color.white);
																}
																colin++;
																b.setBorderPainted(false);
																b.setFont(f3);
																n4.add(b);
																//b.setPreferredSize(dim);
																boxBouton.add(n4);
																boxBouton.add(new Separateur(50,5));
																
																if(listeService.get(key)[0].length()>=1  &&  listeService.get(key)[0].length()<=50)
																{
																	n1.setPreferredSize(dim1);
																	n2.setPreferredSize(dim11);
																	n3.setPreferredSize(dim1);
																	n4.setPreferredSize(dim1);
																	b.setPreferredSize(dim1);
																}
																if(listeService.get(key)[0].length()>50 && listeService.get(key)[0].length()<=100)
																{
																	n1.setPreferredSize(dim2);
																	n2.setPreferredSize(dim12);
																	n3.setPreferredSize(dim2);
																	n4.setPreferredSize(dim2);
																	b.setPreferredSize(dim2);
																}
																if(listeService.get(key)[0].length()>100)
																	
																{
																	n1.setPreferredSize(dim);
																	n2.setPreferredSize(dim13);
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
														listeService.put(matriculeIn.getText(),str ) ;
														JPanel n1=new JPanel();
														//n1.setPreferredSize(dim);
														JLabel l1=new JLabel(matriculeIn.getText());
														System.out.println(matriculeIn.getText());
														l1.setFont(f3);
														n1.add(l1);
													//	n1.setBackground(Color.white);
														boxMatricule.add(n1);
														boxMatricule.add(new Separateur(50,5));
														JPanel n2=new JPanel();
														//n2.setPreferredSize(dim);
														//JLabel l2=new JLabel(nomIn.getText());
														//l2.setFont(f3);
														
														JTextArea l2=new JTextArea(nomIn.getText());
														l2.setFont(f3);
														l2.setEditable(false);
														l2.setLineWrap(true);
														l2.setWrapStyleWord(true);
														//JScrollPane js=new JScrollPane(l2);
														//js.setPreferredSize(dim);
														
														JScrollPane scrollqual=new JScrollPane(l2);
														scrollqual.setPreferredSize(new Dimension(400,150));
														
														
														n2.add(scrollqual);
														
														//n2.add(l2);
														//n2.setBackground(Color.white);
														boxNom.add(n2);
														boxNom.add(new Separateur(400,5));
														JPanel n3=new JPanel();
														//n3.setPreferredSize(dim);
														JLabel l3=new JLabel(telIn.getText());
														l3.setFont(f3);
														n3.add(l3);
													//	n3.setBackground(Color.white);
														boxTelephone.add(n3);
														boxTelephone.add(new Separateur(80,5));
														JPanel n4=new JPanel();
														//n4.setPreferredSize(dim);
													//	n4.setBackground(Color.white);
														Bouton b=new Bouton("voir plus",matriculeIn.getText());
														b.addActionListener(new Boutontest());
													//	b.setBackground(Color.white);
														if(colin%2==0)
														{
															n1.setBackground(couleurEcriture);
															n2.setBackground(couleurEcriture);
															l2.setBackground(couleurEcriture);
															n3.setBackground(couleurEcriture);
															n4.setBackground(couleurEcriture);
															b.setBackground(couleurEcriture);
														}
														else
														{
															n1.setBackground(Color.white);
															n2.setBackground(Color.white);
															l2.setBackground(Color.white);
															n3.setBackground(Color.white);
															n4.setBackground(Color.white);
															b.setBackground(Color.white);
														}
														colin++;
														b.setBorderPainted(false);
														b.setFont(f3);
														n4.add(b);
														//b.setPreferredSize(dim);
														boxBouton.add(n4);
														boxBouton.add(new Separateur(50,5));

														if(nomIn.getText().length()>=1  &&  nomIn.getText().length()<=50)
														{
															n1.setPreferredSize(dim1);
															n2.setPreferredSize(dim11);
															n3.setPreferredSize(dim1);
															n4.setPreferredSize(dim1);
															b.setPreferredSize(dim1);
														}
														if(nomIn.getText().length()>50 && nomIn.getText().length()<=100)
														{
															n1.setPreferredSize(dim2);
															n2.setPreferredSize(dim12);
															n3.setPreferredSize(dim2);
															n4.setPreferredSize(dim2);
															b.setPreferredSize(dim2);
														}
														if(nomIn.getText().length()>100)
															
														{
															n1.setPreferredSize(dim);
															n2.setPreferredSize(dim13);
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
				Service.this.removeAll();//    1/1/1   11/1/1   11/11/1 1/11/1
				Service.this.add(conteneur);
				Service.this.updateUI();
			}
		}

		

		class BoutonModifier implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				Object b=arg0.getSource();
				String s=new String();
				s=((Bouton)b).getMat();
				Service.this.removeAll();
				
				JLabel titre1=new JLabel("Modifier Service");
				titre1.setFont(f);
				JPanel panTitre=new JPanel();
				panTitre.add(titre1);
				Box boxTitre1=Box.createHorizontalBox();
				boxTitre1.add(panTitre);
				Box boxPrincipal=Box.createVerticalBox();
				
				JLabel nomEmp = new JLabel("Nom: ");
				nomEmp.setFont(f1);
				nomEmp.setForeground(couleurEcriture);
				nomIn=new JTextArea(listeService.get(s)[0]);
				Box boxName=Box.createHorizontalBox();
				boxName.add(nomEmp);
				boxName.add(nomIn);
				
				JLabel matEmp = new JLabel("CodeService: ");
				matEmp.setForeground(couleurEcriture);
				matEmp.setFont(f1);
				matriculeIn=new JTextField(s);
				matriculeIn.setEditable(false);
				Box boxMat1=Box.createHorizontalBox();
				boxMat1.add(matEmp);
				boxMat1.add(matriculeIn);
				JLabel erreur=new JLabel("le code Service est obligatoire");
				erreur.setForeground(Color.red);
				erreur.setFont(new Font("Arial", Font.BOLD, 12));
				Box boxMat=Box.createVerticalBox();
				boxMat.add(boxMat1);
				boxMat.add(erreur);
				/*JLabel detailContact=new JLabel("Detail contact");
				detailContact.setFont(f2);
				detailContact.setForeground(couleurEcriture);
				JPanel panDetail=new JPanel();
				panDetail.add(detailContact);
				Box boxDetail=Box.createHorizontalBox();
				boxDetail.add(panDetail);*/
				
				JLabel telEmp = new JLabel("Salaire: ");
				telEmp.setForeground(couleurEcriture);
				telEmp.setFont(f1);
				telIn=new JTextField(listeService.get(s)[3],20);
				Box boxtel=Box.createHorizontalBox();
				boxtel.add(telEmp);
				boxtel.add(telIn);
				
				JLabel email = new JLabel("Prix: ");
				email.setForeground(couleurEcriture);
				email.setFont(f1);
				emailIn=new JTextField(listeService.get(s)[2]);
				Box boxemail=Box.createHorizontalBox();
				boxemail.add(email);
				boxemail.add(emailIn);
			
				JLabel adresseEmp = new JLabel("Categorie: ");
				adresseEmp.setForeground(couleurEcriture);
				adresseEmp.setFont(f1);
				adresseIn=new JTextField(listeService.get(s)[1]);
				Box boxad=Box.createHorizontalBox();
				boxad.add(adresseEmp);
				boxad.add(adresseIn);
				
				Bouton boutonRetour=new Bouton("Annuler");
				JPanel panRetour=new JPanel();
				panRetour.add(boutonRetour);
				boutonRetour.addActionListener(new BoutonRetour());
				
				Bouton boutonValider=new Bouton("Valider",s);
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
				boxPrincipal.add(boxtel);
				boxPrincipal.add(new Separateur(600,20));
				boxPrincipal.add(boxad);
				boxPrincipal.add(new Separateur(600,20));
				boxPrincipal.add(boxemail);
				boxPrincipal.add(new Separateur(600,20));
				boxPrincipal.add(retourPayer);
				Service.this.add(boxPrincipal);
				Service.this.updateUI();
			}
		}
		
		class BoutonSupprimer implements ActionListener
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
				String s = jop.showInputDialog(null, "Donner le matricule de l'Servicer ","Supprimer un Service", JOptionPane.QUESTION_MESSAGE);
				if(s!=null)
				{
					int option = jop.showConfirmDialog(null,
						"Voulez-vous vraiment supprimer l'Service ?",
						"suppression",
						JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE);
				
						if(option == JOptionPane.OK_OPTION)
						{
							if(listeService.containsKey(s))
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
										String str="delete from Service where CODE_Service=?";
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
								listeService.remove(s);
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
								
								
								Set keys = listeService.keySet();
								 
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
									//n1.setPreferredSize(dim);
									JLabel l1=new JLabel(key);
									l1.setFont(f3);
									n1.add(l1);
								//	n1.setBackground(Color.white);
									boxMatricule.add(n1);
									boxMatricule.add(new Separateur(50,5));
									JPanel n2=new JPanel();
									//n2.setPreferredSize(dim);
									JTextArea l2=new JTextArea(listeService.get(key)[0]);
									l2.setFont(f3);
									l2.setEditable(false);
									l2.setLineWrap(true);
									l2.setWrapStyleWord(true);
									
									JScrollPane scrollqual=new JScrollPane(l2);
									scrollqual.setPreferredSize(new Dimension(400,150));
									
									
									//n2.add(l2);
									//JScrollPane js=new JScrollPane(l2);
									//js.setPreferredSize(dim);
									n2.add(scrollqual);
								//	n2.setBackground(Color.white);
									boxNom.add(n2);
									boxNom.add(new Separateur(400,5));
									JPanel n3=new JPanel();
									//n3.setPreferredSize(dim);
									JLabel l3=new JLabel(listeService.get(key)[2]);
									l3.setFont(f3);
									n3.add(l3);
								//	n3.setBackground(Color.white);
									boxTelephone.add(n3);
									boxTelephone.add(new Separateur(80,5));
									JPanel n4=new JPanel();
									//n4.setPreferredSize(dim);
								//	n4.setBackground(Color.white);
									Bouton b=new Bouton("voir plus",key);
									b.addActionListener(new Boutontest());
								//	b.setBackground(Color.white);
									if(colin%2==0)
									{
										n1.setBackground(couleurEcriture);
										n2.setBackground(couleurEcriture);
										l2.setBackground(couleurEcriture);
										n3.setBackground(couleurEcriture);
										n4.setBackground(couleurEcriture);
										b.setBackground(couleurEcriture);
									}
									else
									{
										n1.setBackground(Color.white);
										n2.setBackground(Color.white);
										l2.setBackground(Color.white);
										n3.setBackground(Color.white);
										n4.setBackground(Color.white);
										b.setBackground(Color.white);
									}
									colin++;
									b.setBorderPainted(false);
									b.setFont(f3);
									n4.add(b);
									///b.setPreferredSize(dim);
									boxBouton.add(n4);
									boxBouton.add(new Separateur(50,5));
									
									if(listeService.get(key)[0].length()>=1  &&  listeService.get(key)[0].length()<=50)
									{
										n1.setPreferredSize(dim1);
										n2.setPreferredSize(dim11);
										n3.setPreferredSize(dim1);
										n4.setPreferredSize(dim1);
										b.setPreferredSize(dim1);
									}
									if(listeService.get(key)[0].length()>50 && listeService.get(key)[0].length()<=100)
									{
										n1.setPreferredSize(dim2);
										n2.setPreferredSize(dim12);
										n3.setPreferredSize(dim2);
										n4.setPreferredSize(dim2);
										b.setPreferredSize(dim2);
									}
									if(listeService.get(key)[0].length()>100)
										
									{
										n1.setPreferredSize(dim);
										n2.setPreferredSize(dim13);
										n3.setPreferredSize(dim);
										n4.setPreferredSize(dim);
										b.setPreferredSize(dim);
									}
									
							    }
							    JOptionPane jop4 = new JOptionPane();
								jop4.showMessageDialog(null, "vous avez supprimer le Service", "Information", JOptionPane.INFORMATION_MESSAGE);
					
							}
							else
							{
								JOptionPane jop1 = new JOptionPane();
								jop1.showMessageDialog(null, " le Servicer n'existe pas", "Information", JOptionPane.INFORMATION_MESSAGE);
					
							}
						
						}
						else
						{
							JOptionPane jop1 = new JOptionPane();
							jop1.showMessageDialog(null, "vous avez annuler la suppression", "Information", JOptionPane.INFORMATION_MESSAGE);
				
						}
				}
						Service.this.add(conteneur);
						Service.this.updateUI();
				}
		}

		
		
	

}
