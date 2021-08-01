import java.awt.Color;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dialog.ModalityType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.toedter.calendar.JCalendar;

import test.ComboNew;


public class Calendrier extends JPanel 
{
	private JTextField heure=new JTextField();
	private JCalendar cdate = new JCalendar();
	private ComboNew comboContrat=new ComboNew();
	private ComboNew comboService=new ComboNew();
	private ComboNew comboEmploye=new ComboNew();
	private ComboNew rempli;
	private GridLayout gl;
	private Color c;
	private Box boxRecherche;
	private JPanel conteneur1;
	private JScrollPane scroller;
	private Font f,f1,f2,f3;
	private Color couleurEcriture;
	private Dimension dim;
	private JPanel contratActif , contratRempli , employes,courbe;
	private JLabel nombreTache;
	private Box b7,boxPlanning;
	private JPanel [][] panelPlanning=new JPanel[10][8];
	private JLabel [][] labelPlanning=new JLabel[10][24];
	private Bouton boutonRecherche;
	private JLabel textRecherche;
	private JTextField recherche;
	private LocalDate j1,j2,j3,j4,j5,j6,j7;
	private int line=0;
	private String []comb=new String[100];
	private Hashtable<String, String[]> listeContrat;
	private JLabel labdat=new JLabel();
	
	
	public Calendrier()
	{
		super();
		listeContrat=new Hashtable<String, String[]>();
		
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
			String tabcli="create table IF NOT EXISTS AFFECTATION_TACHE("+
					   "NUMERO_CONTRAT       VARCHAR(30)          not null,"+
					   "MATRICULE_EMPLOYE    VARCHAR(30)          not null,"+
					   "CODE_SERVICE        VARCHAR(30)           not null,"+
					   "DATE  			  TEXT                null,"+
					   "DUREE    		  VARCHAR(10)               null,"+
					   "REMPLI              BOOLEAN               not null,"+
					   "constraint PK_AFFECTATION_TACHE primary key (NUMERO_CONTRAT, MATRICULE_EMPLOYE, CODE_SERVICE)"+
					")";





			state7.addBatch(tabcli);
			state7.executeBatch();
			state7.clearBatch();
			state7.close();
			
			
			
			
		/*	Class.forName("com.mysql.jdbc.JDBC");  
			System.out.println("Driver O.K.");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/gestionnettoyage","","");  
			System.out.println("Connexion effective !");*/
			
			
			//Création d'un objet Statement
			Statement state = conn.createStatement();
		//	Statement state1 = conn.createStatement();
			//Statement state2 = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery("SELECT * FROM contrat where rempli=false");
			ResultSet res,res1;
			//ResultSet result1 = state1.executeQuery("SELECT * FROM contrat where rempli=false");
			//ResultSet result2 = state2.executeQuery("SELECT * FROM service");
			//On récupère les MetaData
			ResultSetMetaData resultMeta = result.getMetaData();
			//ResultSetMetaData resultMeta1 = result1.getMetaData();
			//ResultSetMetaData resultMeta2 = result2.getMetaData();
			PreparedStatement prepare,prepare1;
			String str="SELECT code_client FROM devis where numero_devis=?";
			String str1="SELECT nom_client FROM client where code_client=?";
			//Date date =  cdate.getTi
			prepare = conn.prepareStatement(str);
			prepare1 = conn.prepareStatement(str1);
				
			while(result.next())
			{
				prepare.setString(1,result.getObject(2).toString());
				res=prepare.executeQuery();
				res.next();
				
				prepare1.setString(1,res.getObject(1).toString());
				res1=prepare1.executeQuery();
				res1.next();
				
				//comboContrat.addItem(result.getObject(1).toString());
				String []s=new String[resultMeta.getColumnCount()];
				int i;
				for( i = 2; i <= resultMeta.getColumnCount(); i++)
				{
					if(result.getString(i)!=null)
						s[i-2]=new String(result.getString(i));
				}
				s[i-2]=new String(res1.getObject(1).toString());
				listeContrat.put(result.getObject(1).toString(),s ) ;
				

			}
			
			
			//On affiche le nom des colonnes
			result.close();
			//res.close();
			//res1.close();
			//result1.close();
			prepare.close();
			prepare1.close();
			state.close();
			conn.close();
			
		//	state1.close();
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		
		
		couleurEcriture=new Color(242,153,186,255);
		c=new Color(161,8,73,255);
		dim=new Dimension(170,40);
		f=new Font("Arial", Font.BOLD, 30);
		f1=new Font("Arial", Font.BOLD, 20);
		f3=new Font("Arial", Font.BOLD, 15);
		f2=new Font("Arial", Font.BOLD, 25);
		gl=new GridLayout(10,6,5,5);
		//JLabel titre=new JLabel("Calendrier");
		conteneur1=new JPanel();
		conteneur1.setLayout(gl);
		scroller=new JScrollPane(conteneur1);
		scroller.setPreferredSize(new Dimension(600,500));
		
		boxPlanning=Box.createVerticalBox();
		JLabel titrePlanning=new JLabel("Plannification");
		titrePlanning.setFont(f);
		JPanel panPlanning=new JPanel();
		panPlanning.add(titrePlanning);
		boxPlanning.add(panPlanning);
		boxPlanning.add(new Separateur(600,20));
		
		recherche=new JTextField("votre text");
		recherche.setPreferredSize(new Dimension(200,25));
		textRecherche=new JLabel("recherche: ");
		textRecherche.setFont(f1);
		boutonRecherche=new Bouton("ok");
		boutonRecherche.setPreferredSize(new Dimension(60,25));
		//boutonRecherche.setBackground(Color.gray);
		JPanel panelRecherche=new JPanel();
		//panelRecherche.setBackground(c);
		panelRecherche.add(textRecherche);
		panelRecherche.add(recherche);
		panelRecherche.add(boutonRecherche);
		boxRecherche=Box.createHorizontalBox();
		boxRecherche.add(panelRecherche);
		boxPlanning.add(boxRecherche);
		boxPlanning.add(new Separateur(600,10));
		JPanel pandat=new JPanel();
		pandat.add(labdat);
		Box boxdat=Box.createHorizontalBox();
		boxdat.add(pandat);
		
		boxPlanning.add(boxdat);
		boxPlanning.add(new Separateur(600,10));
		boxPlanning.add(scroller);
		boxPlanning.add(new Separateur(600,10));
		Bouton bretour=new Bouton("retour");
		Bouton bsuiv=new Bouton("semaine suivante");
		bsuiv.addActionListener(new BoutonSuiv());
		bsuiv.setPreferredSize(dim);
		Bouton bprec=new Bouton("semaine precedente");
		bprec.addActionListener(new BoutonPrec());
		bprec.setPreferredSize(dim);
		bretour.addActionListener(new BoutonRetour());
		Box boxret=Box.createHorizontalBox();
		
		boxret.add(bprec);
		boxret.add(new Separateur(10,5));
		boxret.add(bretour);
		boxret.add(new Separateur(10,5));
		boxret.add(bsuiv);
		boxPlanning.add(boxret);
		for(int i=0;i<8;i++)
		{
			panelPlanning[0][i]=new JPanel();
			labelPlanning[0][i]=new JLabel("                    ");
			panelPlanning[0][i].setBackground(Color.pink);
			panelPlanning[0][i].add(labelPlanning[0][i]);
			conteneur1.add(panelPlanning[0][i]);
			
		}
		
		for(int i=1;i<10;i++)
		{
			int k=0;
			for(int j=0;j<24;j++)
			{
				labelPlanning[i][j]=new JLabel("                    ");
				if((j+1)%3==0)
				{
					panelPlanning[i][k]=new JPanel();
					Box cpl=Box.createVerticalBox();
					cpl.add(labelPlanning[i][j-2]);
					cpl.add(labelPlanning[i][j-1]);
					cpl.add(labelPlanning[i][j]);
					panelPlanning[i][k].setBackground(Color.pink);
					panelPlanning[i][k].add(cpl);
					conteneur1.add(panelPlanning[i][k]);
					k++;
				}
			}
		}
		
		JLabel textC=new JLabel("Controle ");
		JLabel textC1=new JLabel("De qualite");
		JLabel textT=new JLabel("Taches");
		JButton bPe=new JButton(" Planning employe");
		bPe.addActionListener(new PlanningEmploye());
		JButton bAt=new JButton("Affecter tache");
		bAt.addActionListener(new BoutonAffecter());
		Color cTache=new Color(54,117,94,255);
		Color cAf=new Color(107,194,162,255);
		Color cPe=new Color(162,206,175,255);
		Color cCe=new Color(186,221,207,255);
		Dimension dT=new Dimension(100,200);
		Dimension dC=new Dimension(200,160);
		Dimension dPe=new Dimension(420,60);
		Dimension dTe=new Dimension(150,20);
		JLabel titre=new JLabel("Kendif nettoyage et service");
		Font f4=new Font("Arial", Font.BOLD, 50);
		titre.setFont(f);
		titre.setForeground(new Color(0,0,0,175));
		JPanel pantitre=new JPanel();
		pantitre.add(titre);
		Box boxtitre=Box.createHorizontalBox();
		boxtitre.add(pantitre);
		nombreTache=new JLabel("150");
		nombreTache.setFont(f4);
		nombreTache.setForeground(Color.white);
		
		textT.setFont(f2);
		textT.setForeground(Color.white);
		
		textC.setFont(f);
		textC.setForeground(Color.white);
		textC.setBackground(cCe);
		textC1.setFont(f);
		textC1.setForeground(Color.white);
		textC1.setBackground(cCe);
		JPanel nC=new JPanel();
		nC.setPreferredSize(dC);
		nC.setBackground(cCe);
		JPanel nC1=new JPanel();
		nC1.setPreferredSize(new Dimension(100,80));
		nC1.setBackground(cCe);
		nC.add(nC1);
		nC.add(textC);
		nC.add(textC1);
		
		
		Box b1=Box.createHorizontalBox();
		b1.add(textT);
		Box b2=Box.createHorizontalBox();
		b2.add(nombreTache);
		Box bT=Box.createVerticalBox();
		bT.add(b1);
		bT.add(b2);
		JPanel pnt=new JPanel();
		pnt.setPreferredSize(dT);
		pnt.setBackground(cTache);
		JPanel pnt1=new JPanel();
		pnt1.setPreferredSize(new Dimension(120,20));
		pnt1.setBackground(cTache);
		pnt.add(pnt1);
		pnt.add(bT);
		
		bAt.setBackground(cAf);
		bAt.setFont(f2);
		JPanel pbat=new JPanel();
		pbat.setPreferredSize(dT);
		pbat.setBackground(cAf);
		JPanel pbat1=new JPanel();
		pbat1.setPreferredSize(new Dimension(100,60));
		pbat1.setBackground(cAf);
		pbat.add(pbat1);
		pbat.add(bAt);
		Box b3=Box.createHorizontalBox();
		b3.add(pnt);
		//b3.add(new Separateur(5,100));
		b3.add(pbat);
		
		bPe.setBackground(cPe);
		bPe.setFont(f2);
		JPanel nbPe=new JPanel();
		nbPe.setBackground(cPe);
		nbPe.setPreferredSize(dPe);
		nbPe.add(bPe);
		Box b4=Box.createHorizontalBox();
		b4.add(nbPe);
		
		Box b5=Box.createVerticalBox();
		b5.add(b3);
		//b5.add(new Separateur(20,20));
		b5.add(b4);
		
		b7=Box.createVerticalBox();
		b7.add(boxtitre);
		//Box b8=Box.createHorizontalBox();
		Box b6=Box.createHorizontalBox();
		b6.add(b5);
		b6.add(nC);
		b7.add(new Separateur(600,20));
		b7.add(b6);
		this.add(b7);
	}
	
	
	public void viderLabel()
	{
		for(int i=1;i<10;i++)
		{
		
			for(int j=0;j<24;j++)
			{
				labelPlanning[i][j].setText("                    ");
			}
		}
	}
	
	public void aJour()
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
			Statement state = conn.createStatement();
			Statement state1 = conn.createStatement();
			Statement state2 = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery("SELECT COUNT(*) FROM contrat where rempli=true");
			ResultSet result1 = state1.executeQuery("SELECT COUNT(*) FROM contrat where rempli=false");
			ResultSet result2 = state2.executeQuery("SELECT COUNT(*) FROM employe");
			//On récupère les MetaData
			/*ResultSetMetaData resultMeta = result.getMetaData();
			ResultSetMetaData resultMeta1 = result1.getMetaData();
			ResultSetMetaData resultMeta2 = result2.getMetaData();*/
			//On affiche le nom des colonnes
			String s,s1,s2;
			result.next();
			result1.next();
			result2.next();
			s=String.valueOf(result.getInt(1));
			s1=String.valueOf(result1.getInt(1));
			s2=String.valueOf(result2.getInt(1));
			this.nombreTache.setText(s1);
			result.close();
			result1.close();
			result2.close();
			state.close();
			state1.close();
			state2.close();
			conn.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	class PlanningEmploye implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Calendrier.this.removeAll();
			j1=LocalDate.now();
		
			j2=j1.plusDays(1);
			labelPlanning[0][1].setText(j1.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j3=j2.plusDays(1);
			labelPlanning[0][2].setText(j2.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j4=j3.plusDays(1);
			labelPlanning[0][3].setText(j3.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j5=j4.plusDays(1);
			labelPlanning[0][4].setText(j4.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j5=j4.plusDays(1);
			labelPlanning[0][4].setText(j4.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j6=j5.plusDays(1);
			labelPlanning[0][5].setText(j5.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j7=j6.plusDays(1);
			labelPlanning[0][6].setText(j6.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			
			labelPlanning[0][7].setText(j7.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			labdat.setText("Semaine du "+j1.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))+" au "+j7.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			
			viderLabel();
			
			Set keys = listeContrat.keySet();
			 
		    //obtenir un iterator des clés
		    Iterator itr = keys.iterator();
		 
		    String key="";
		   
			int n=1;
		    //affichage des pairs clé-valeur
		    while (itr.hasNext()) 
		    { 
		       // obtenir la clé
		       key = (String) itr.next();
		     //  labelPlanning[n][0].setText(listeContrat.get(key)[5]);
		      /* labelPlanning[1][3].setText("uzumaki 2h");
			    panelPlanning[1][3].setBackground(Color.green);
			    labelPlanning[2][5].setText("zoldyck 3h");
			    panelPlanning[2][5].setBackground(Color.red);*/
	
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
				//	Statement state1 = conn.createStatement();
					//Statement state2 = conn.createStatement();
					//L'objet ResultSet contient le résultat de la requête SQL
					//ResultSet result = state.executeQuery("SELECT * FROM contrat where rempli=false ");
					ResultSet res,res1,res2;
					//ResultSet result1 = state1.executeQuery("SELECT * FROM contrat where rempli=false");
					//ResultSet result2 = state2.executeQuery("SELECT * FROM service");
					//On récupère les MetaData
					/*ResultSetMetaData resultMeta = result.getMetaData();
					//ResultSetMetaData resultMeta1 = result1.getMetaData();
					ResultSetMetaData resultMeta2 = result2.getMetaData();*/
					PreparedStatement prepare,prepare1,prepare2;
					String str="SELECT * FROM affectation_tache where numero_contrat=?";
					String str2="SELECT nom_employe FROM employe where matricule_employe=?";
					String str1="SELECT libelle_service FROM service where code_service=?";
					//Date date =  cdate.getTi
					prepare = conn.prepareStatement(str);
					prepare1 = conn.prepareStatement(str1);
					prepare2 = conn.prepareStatement(str2);
						
						int pol=0,polav=-1;
						//comb[0]="";
						prepare.setString(1,key);
						res=prepare.executeQuery();
						while(res.next())
						{
							prepare1.setString(1,res.getObject(3).toString());
							res1=prepare1.executeQuery();
							pol=0;
							if(res1.next())
							{
								prepare2.setString(1,res.getObject(2).toString());
								res2=prepare2.executeQuery();
								if(res2.next())
								{
									LocalDate localDate = LocalDate.parse(res.getObject(4).toString());
									
									//System.out.println(((Date)res.getObject(4)).toLocalDate().toString() +" vs  "+j1.toString());
									if(localDate.equals(j1))
										pol=1;
									//convert String to LocalDate
									if(localDate.equals(j2))
										pol=2;
									if(localDate.equals(j3))
										pol=3;
									if(localDate.equals(j4))
										pol=4;
									if(localDate.equals(j5))
										pol=5;
									if(localDate.equals(j6))
										pol=6;
									if(localDate.equals(j7))
										pol=7;
									if(pol!=0)
									{
										if(pol==polav)
										{
											n++;
										}
										polav=pol;
										 labelPlanning[n][0].setText(listeContrat.get(key)[5]);
										labelPlanning[n][3*pol].setText(res2.getObject(1).toString());
										labelPlanning[n][3*pol+1].setText(res1.getObject(1).toString());
										labelPlanning[n][3*pol+2].setText(res.getObject(5).toString());
									}
								}
							}
							
						}
						//On affiche le nom des colonnes
						//result.close();
						//result1.close();
						prepare.close();
						prepare1.close();
						prepare2.close();
						conn.close();
					//	state.close();
						
					//	state1.close();
						
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
		
		       n++;
		    }
		    		
			Calendrier.this.add(boxPlanning);
			Calendrier.this.updateUI();
		}
	}
	
	
	class BoutonAffecter implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			
			
		//	JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
			//String s = jop.showInputDialog(null, "Donner le numero du contrat ","Affecter tache", JOptionPane.QUESTION_MESSAGE);
			 //create a JOptionPane
	       /* Object[] options = new Object[] {};
	        JOptionPane jop = new JOptionPane("Please Select",
	                                        JOptionPane.QUESTION_MESSAGE,
	                                        JOptionPane.DEFAULT_OPTION,
	                                        null,options, null);

	        //add combos to JOptionPane
	        jop.add(comboContrat);
	       // jop.add(jcm);
	        //jop.add(jcy);

	        //create a JDialog and add JOptionPane to it 
	        JDialog diag = new JDialog();
	        diag.getContentPane().add(jop);
	        diag.pack();
	        diag.setVisible(true);
	        System.out.println(comboContrat.getSelectedItem().toString());*/
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
				//Statement state2 = conn.createStatement();
				//L'objet ResultSet contient le résultat de la requête SQL
				ResultSet result = state.executeQuery("SELECT * FROM contrat where rempli=false");
				ResultSet res,res1;
				//ResultSet result1 = state1.executeQuery("SELECT * FROM contrat where rempli=false");
				//ResultSet result2 = state2.executeQuery("SELECT * FROM service");
				//On récupère les MetaData
				ResultSetMetaData resultMeta = result.getMetaData();
				//ResultSetMetaData resultMeta1 = result1.getMetaData();
				//ResultSetMetaData resultMeta2 = result2.getMetaData();
				PreparedStatement prepare,prepare1;
				String str="SELECT code_client FROM devis where numero_devis=?";
				String str1="SELECT nom_client FROM client where code_client=?";
				//Date date =  cdate.getTi
				prepare = conn.prepareStatement(str);
				prepare1 = conn.prepareStatement(str1);
					
					comboContrat.setPreferredSize(new Dimension(100,40));
					//comboContrat[i].addItemListener(new ItemState());
					//comboContrat.addItem("");
					comb[0]="";
				int o=1;
				while(result.next())
				{
					prepare.setString(1,result.getObject(2).toString());
					res=prepare.executeQuery();
					res.next();
					
					prepare1.setString(1,res.getObject(1).toString());
					res1=prepare1.executeQuery();
					res1.next();
					
					//comboContrat.addItem(result.getObject(1).toString());
					comboContrat.setMat(o-1,result.getObject(1).toString() );
					comboContrat.addItem(String.valueOf(o)+") contrat avec "+res1.getObject(1).toString());
					comb[o]=String.valueOf(o)+") contrat avec "+res1.getObject(1).toString();
					
					o++;
				}
				
				
				//On affiche le nom des colonnes
				result.close();
				//res.close();
				//res1.close();
				//result1.close();
				prepare.close();
				prepare1.close();
				state.close();
				conn.close();
				
			//	state1.close();
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			 JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
			    String nom = (String)jop.showInputDialog(null, 
			      "donner le contrat",
			      "Affectation de tache",
			      JOptionPane.QUESTION_MESSAGE,
			      null,
			      comb,
			      comb[0]);
			    System.out.println(nom+"  111111111");
			    if(nom==null || nom.length()==0)
			    {

					JOptionPane jop3 = new JOptionPane();
					jop3.showMessageDialog(null, "Vous Annuler l'affectation", "Erreur", JOptionPane.ERROR_MESSAGE);
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
						Statement state = conn.createStatement();
					//	Statement state1 = conn.createStatement();
						//Statement state2 = conn.createStatement();
						//L'objet ResultSet contient le résultat de la requête SQL
						ResultSet result = state.executeQuery("SELECT * FROM employe ");
						ResultSet res,res1,res2;
						//ResultSet result1 = state1.executeQuery("SELECT * FROM contrat where rempli=false");
						//ResultSet result2 = state2.executeQuery("SELECT * FROM service");
						//On récupère les MetaData
						/*ResultSetMetaData resultMeta = result.getMetaData();
						//ResultSetMetaData resultMeta1 = result1.getMetaData();
						ResultSetMetaData resultMeta2 = result2.getMetaData();*/
						PreparedStatement prepare,prepare1,prepare2;
						String str="SELECT numero_devis FROM contrat where numero_contrat=?";
						String str1="SELECT code_service FROM contenu_devis where numero_devis=? and code_service not in(select code_service from affectation_tache where numero_contrat=? )";
						String str2="SELECT libelle_service FROM service where code_service=?";
						//Date date =  cdate.getTi
						prepare = conn.prepareStatement(str);
						prepare1 = conn.prepareStatement(str1);
						prepare2 = conn.prepareStatement(str2);
							comboService.removeAllItems();;
							comboEmploye.removeAllItems();;
							comboService.setPreferredSize(new Dimension(100,40));
							//comboContrat[i].addItemListener(new ItemState());
							comboService.addItem("");
							comboEmploye.setPreferredSize(new Dimension(100,40));
							//comboContrat[i].addItemListener(new ItemState());
							comboEmploye.addItem("");
							//comb[0]="";
							prepare.setString(1,comboContrat.getMat(Character.getNumericValue(nom.charAt(0))-1));
							res=prepare.executeQuery();
							if(res.next())
							{
								prepare1.setString(1,res.getObject(1).toString());
								prepare1.setString(2,comboContrat.getMat(Character.getNumericValue(nom.charAt(0))-1));
								res1=prepare1.executeQuery();
								//res1.next();
								int m=1;
								while(res1.next())
								{
									prepare2.setString(1,res1.getObject(1).toString());
									res2=prepare2.executeQuery();
									
									comboService.setMat(m-1,res1.getObject(1).toString() );
									if(res2.next())
										comboService.addItem(String.valueOf(m)+") "+res2.getObject(1).toString());
									
									m++;
								}
							}
								
							
							
							
						int h=1;
						while(result.next())
						{
							
							
							//comboContrat.addItem(result.getObject(1).toString());
							comboEmploye.setMat(h-1,result.getObject(1).toString() );
							comboEmploye.addItem(String.valueOf(h)+") "+result.getObject(3).toString());
							//comb[o]=String.valueOf(o)+") contrat avec "+res1.getObject(1).toString();
							h++;
						}
						
						
							
								
						
						
						//On affiche le nom des colonnes
						result.close();
						//result1.close();
						prepare.close();
						prepare1.close();
						state.close();
						conn.close();
						//state.close();
						
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
			    	JLabel titre=new JLabel("Affectation Tache");
					JPanel pantitre=new JPanel();
					Box boxtitre=Box.createHorizontalBox();
					pantitre.add(titre);
					boxtitre.add(pantitre);
					JLabel numcon=new JLabel("Numero Contrat: ");
					numcon.setFont(f1);
					comboContrat.setSelectedItem(nom);
					comboContrat.setFont(f3);
					comboContrat.setEnabled(false);
					Box boxcon=Box.createHorizontalBox();
					boxcon.add(numcon);
					boxcon.add(new Separateur(20,5));
					boxcon.add(comboContrat);
					
					JLabel numser=new JLabel("Service: ");
					numser.setFont(f1);;
					Box boxser=Box.createHorizontalBox();
					boxser.add(numser);
					boxser.add(new Separateur(20,5));
					boxser.add(comboService);
					
					
					JLabel numemp=new JLabel("Employe : ");
					numemp.setFont(f1);
					Box boxemp=Box.createHorizontalBox();
					boxemp.add(numemp);
					boxemp.add(new Separateur(20,5));
					boxemp.add(comboEmploye);
					
					

					JLabel dateNaissance = new JLabel("Date : ");
					//dateNaissance.setForeground(couleurEcriture);
					dateNaissance.setFont(f1);
					Bouton cr=new Bouton("choisir date");
					cr.addActionListener(new ChoixDate());
					Box boxdte=Box.createHorizontalBox();
					boxdte.add(dateNaissance);
					//boxdte.add(dteIn);
					Separateur paneldater=new Separateur(100,40);
					paneldater.add(cr);
					boxdte.add(paneldater);
					
					
					JLabel duree=new JLabel("Duree : ");
					duree.setFont(f1);
					Box boxdur=Box.createHorizontalBox();
					boxdur.add(duree);
					boxdur.add(new Separateur(20,5));
					boxdur.add(heure);
					
					JLabel labremp=new JLabel("Rempli :");
					//labremp.setForeground(couleurEcriture);
					labremp.setFont(f1);
					rempli=new ComboNew();
					rempli.addItem("faux");
					rempli.setEnabled(false);
					rempli.setFont(f1);
					Box boxremp=Box.createHorizontalBox();
					boxremp.add(labremp);
					boxremp.add(rempli);
					
					
					Bouton boutonRetour=new Bouton("Annuler");
					JPanel panRetour=new JPanel();
					panRetour.add(boutonRetour);
					boutonRetour.addActionListener(new BoutonRetour());
					
					Bouton boutonValider=new Bouton("Valider");
					panRetour.add(boutonValider);
					boutonValider.addActionListener(new BoutonValiderAffectation());
					
					
					
					Box retourPayer=Box.createHorizontalBox();
					retourPayer.add(panRetour);
					
					Box boxprinc=Box.createVerticalBox();
					boxprinc.add(boxcon);
					boxprinc.add(new Separateur(600,20));
					boxprinc.add(boxser);
					boxprinc.add(new Separateur(600,20));
					boxprinc.add(boxemp);
					boxprinc.add(new Separateur(600,20));
					boxprinc.add(boxdte);
					boxprinc.add(new Separateur(600,20));
					boxprinc.add(boxdur);
					boxprinc.add(new Separateur(600,20));
					//boxprinc.add(boxremp);
					boxprinc.add(new Separateur(600,20));
					boxprinc.add(retourPayer);
					boxprinc.add(new Separateur(600,20));
					Calendrier.this.removeAll();
					Calendrier.this.add(boxprinc);
					Calendrier.this.updateUI();
					
			    }
			
			
		}
	}
	
	
	class BoutonValiderAffectation implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			if(comboService.getSelectedItem().toString().length()==0)
			{
				JOptionPane jop3 = new JOptionPane();
				jop3.showMessageDialog(null, "Aucun Service selectionner", "Erreur", JOptionPane.ERROR_MESSAGE);
				
			}
			else {
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
				
					String str="insert into AFFECTATION_TACHE (NUMERO_CONTRAT, MATRICULE_EMPLOYE,CODE_SERVICE,DATE,DUREE,REMPLI )  ";
					str+=" values (?,?,?,?,?,?)";
					
					//Date date =  cdate.getTi
					
					prepare = conn.prepareStatement(str);
					
					//On remplace le premier trou par le nom du professeur
					prepare.setString(1,comboContrat.getMat(Character.getNumericValue(comboContrat.getSelectedItem().toString().charAt(0))-1));
					prepare.setString(2,comboEmploye.getMat(Character.getNumericValue(comboEmploye.getSelectedItem().toString().charAt(0))-1));
					prepare.setString(3,comboService.getMat(Character.getNumericValue(comboService.getSelectedItem().toString().charAt(0))-1));
					prepare.setString(4, new java.sql.Date(cdate.getDate().getTime()).toString());
					prepare.setString(5,heure.getText());
					prepare.setBoolean(6,false);
					prepare.executeUpdate();
				
				prepare.close();
				conn.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			}
			Calendrier.this.removeAll();
			Calendrier.this.add(b7);
			Calendrier.this.updateUI();
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
			d.setLocationRelativeTo(Calendrier.this);
			d.setVisible(true);

			/*Date date = c.getCalendar().getTime(); // on récupère la date

			/* on affiche la date dans le label 
			Locale locale = Locale.getDefault();
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale);
			label.setText(dateFormat.format(date));*/

			
		}
		
	}
	
	class BoutonRetour implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Calendrier.this.removeAll();
			Calendrier.this.add(b7);
			Calendrier.this.updateUI();
		}
	}
	
	
	class BoutonSuiv implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			j1=j1.plusDays(7);
			j2=j1.plusDays(1);
			labelPlanning[0][1].setText(j1.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j3=j2.plusDays(1);
			labelPlanning[0][2].setText(j2.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j4=j3.plusDays(1);
			labelPlanning[0][3].setText(j3.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j5=j4.plusDays(1);
			labelPlanning[0][4].setText(j4.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j5=j4.plusDays(1);
			labelPlanning[0][4].setText(j4.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j6=j5.plusDays(1);
			labelPlanning[0][5].setText(j5.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j7=j6.plusDays(1);
			labelPlanning[0][6].setText(j6.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			
			labelPlanning[0][7].setText(j7.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			labdat.setText("Semaine du "+j1.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))+" au "+j7.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			
			viderLabel();
			
			Set keys = listeContrat.keySet();
			 
		    //obtenir un iterator des clés
		    Iterator itr = keys.iterator();
		 
		    String key="";
		   
			int n=1;
		    //affichage des pairs clé-valeur
		    while (itr.hasNext()) 
		    { 
		       // obtenir la clé
		       key = (String) itr.next();
		       
		      /* labelPlanning[1][3].setText("uzumaki 2h");
			    panelPlanning[1][3].setBackground(Color.green);
			    labelPlanning[2][5].setText("zoldyck 3h");
			    panelPlanning[2][5].setBackground(Color.red);*/
	
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
				//	Statement state1 = conn.createStatement();
					//Statement state2 = conn.createStatement();
					//L'objet ResultSet contient le résultat de la requête SQL
					//ResultSet result = state.executeQuery("SELECT * FROM contrat where rempli=false ");
					ResultSet res,res1,res2;
					//ResultSet result1 = state1.executeQuery("SELECT * FROM contrat where rempli=false");
					//ResultSet result2 = state2.executeQuery("SELECT * FROM service");
					//On récupère les MetaData
					/*ResultSetMetaData resultMeta = result.getMetaData();
					//ResultSetMetaData resultMeta1 = result1.getMetaData();
					ResultSetMetaData resultMeta2 = result2.getMetaData();*/
					PreparedStatement prepare,prepare1,prepare2;
					String str="SELECT * FROM affectation_tache where numero_contrat=?";
					String str2="SELECT nom_employe FROM employe where matricule_employe=?";
					String str1="SELECT libelle_service FROM service where code_service=?";
					//Date date =  cdate.getTi
					prepare = conn.prepareStatement(str);
					prepare1 = conn.prepareStatement(str1);
					prepare2 = conn.prepareStatement(str2);
						
						int pol=0,polav=-1;
						//comb[0]="";
						prepare.setString(1,key);
						res=prepare.executeQuery();
						while(res.next())
						{
							prepare1.setString(1,res.getObject(3).toString());
							res1=prepare1.executeQuery();
							if(res1.next())
							{
								pol=0;
								prepare2.setString(1,res.getObject(2).toString());
								res2=prepare2.executeQuery();
								if(res2.next())
								{
									LocalDate localDate = LocalDate.parse(res.getObject(4).toString());
									//System.out.println(((Date)res.getObject(4)).toLocalDate().toString() +" vs  "+j1.toString());
									if(localDate.equals(j1))
										pol=1;
									if(localDate.equals(j2))
										pol=2;
									if(localDate.equals(j3))
										pol=3;
									if(localDate.equals(j4))
										pol=4;
									if(localDate.equals(j5))
										pol=5;
									if(localDate.equals(j6))
										pol=6;
									if(localDate.equals(j7))
										pol=7;
									if(pol!=0)
									{
										if(pol==polav)
										{
											n++;
										}
										polav=pol;
										labelPlanning[n][0].setText(listeContrat.get(key)[5]);
										labelPlanning[n][3*pol].setText(res2.getObject(1).toString());
										labelPlanning[n][3*pol+1].setText(res1.getObject(1).toString());
										labelPlanning[n][3*pol+2].setText(res.getObject(5).toString());
									}
								}
							}
							
						}
						//On affiche le nom des colonnes
						//result.close();
						//result1.close();
						prepare.close();
						prepare1.close();
						prepare2.close();
					//	state.close();
						conn.close();
					//	state1.close();
						
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
		
		       n++;
		    }
		    Calendrier.this.updateUI();
		}
	}
	
	
	class BoutonPrec implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			j1=j1.plusDays(-7);
			j2=j1.plusDays(1);
			labelPlanning[0][1].setText(j1.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j3=j2.plusDays(1);
			labelPlanning[0][2].setText(j2.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j4=j3.plusDays(1);
			labelPlanning[0][3].setText(j3.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j5=j4.plusDays(1);
			labelPlanning[0][4].setText(j4.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j5=j4.plusDays(1);
			labelPlanning[0][4].setText(j4.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j6=j5.plusDays(1);
			labelPlanning[0][5].setText(j5.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			j7=j6.plusDays(1);
			labelPlanning[0][6].setText(j6.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			
			labelPlanning[0][7].setText(j7.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			labdat.setText("Semaine du "+j1.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL))+" au "+j7.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
			
			viderLabel();
			
			Set keys = listeContrat.keySet();
			 
		    //obtenir un iterator des clés
		    Iterator itr = keys.iterator();
		 
		    String key="";
		   
			int n=1;
		    //affichage des pairs clé-valeur
		    while (itr.hasNext()) 
		    { 
		       // obtenir la clé
		       key = (String) itr.next();
		      
		      /* labelPlanning[1][3].setText("uzumaki 2h");
			    panelPlanning[1][3].setBackground(Color.green);
			    labelPlanning[2][5].setText("zoldyck 3h");
			    panelPlanning[2][5].setBackground(Color.red);*/
	
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
				//	Statement state1 = conn.createStatement();
					//Statement state2 = conn.createStatement();
					//L'objet ResultSet contient le résultat de la requête SQL
					//ResultSet result = state.executeQuery("SELECT * FROM contrat where rempli=false ");
					ResultSet res,res1,res2;
					//ResultSet result1 = state1.executeQuery("SELECT * FROM contrat where rempli=false");
					//ResultSet result2 = state2.executeQuery("SELECT * FROM service");
					//On récupère les MetaData
					/*ResultSetMetaData resultMeta = result.getMetaData();
					//ResultSetMetaData resultMeta1 = result1.getMetaData();
					ResultSetMetaData resultMeta2 = result2.getMetaData();*/
					PreparedStatement prepare,prepare1,prepare2;
					String str="SELECT * FROM affectation_tache where numero_contrat=?";
					String str2="SELECT nom_employe FROM employe where matricule_employe=?";
					String str1="SELECT libelle_service FROM service where code_service=?";
					//Date date =  cdate.getTi
					prepare = conn.prepareStatement(str);
					prepare1 = conn.prepareStatement(str1);
					prepare2 = conn.prepareStatement(str2);
						
						int pol=0,polav=-1;
						//comb[0]="";
						prepare.setString(1,key);
						res=prepare.executeQuery();
						while(res.next())
						{
							prepare1.setString(1,res.getObject(3).toString());
							res1=prepare1.executeQuery();
							pol=0;
							if(res1.next())
							{
								prepare2.setString(1,res.getObject(2).toString());
								res2=prepare2.executeQuery();
								if(res2.next())
								{
									LocalDate localDate = LocalDate.parse(res.getObject(4).toString());
									//System.out.println(((Date)res.getObject(4)).toLocalDate().toString() +" vs  "+j1.toString());
									if(localDate.equals(j1))
										pol=1;
									if(localDate.equals(j2))
										pol=2;
									if(localDate.equals(j3))
										pol=3;
									if(localDate.equals(j4))
										pol=4;
									if(localDate.equals(j5))
										pol=5;
									if(localDate.equals(j6))
										pol=6;
									if(localDate.equals(j7))
										pol=7;
									if(pol!=0)
									{
										if(pol==polav)
										{
											n++;
										}
										polav=pol;
										 labelPlanning[n][0].setText(listeContrat.get(key)[5]);
										labelPlanning[n][3*pol].setText(res2.getObject(1).toString());
										labelPlanning[n][3*pol+1].setText(res1.getObject(1).toString());
										labelPlanning[n][3*pol+2].setText(res.getObject(5).toString());
									}
								}
							}
							
						}
						//On affiche le nom des colonnes
						//result.close();
						//result1.close();
						prepare.close();
						prepare1.close();
						prepare2.close();
						conn.close();
					//	state.close();
						
					//	state1.close();
						
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
		
		       n++;
		    }
		    Calendrier.this.updateUI();
		}
	}
	
	
	
}
