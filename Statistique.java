import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Statistique extends JPanel
{
	private JPanel contratActif , contratRempli , employes,courbe;
	private JLabel nombreContratActif,nombreContratRempli,nombreEmploye;
	
	
	public Statistique()
	{
		super();
		JLabel text1=new JLabel("Contrats actifs");
		JLabel text2=new JLabel("Contrats remplis");
		JLabel text3=new JLabel(" Employes");
		JPanel entete=new JPanel();
		JPanel entete1=new JPanel();
		JPanel espace=new JPanel();
		JPanel espace1=new JPanel();
		Color c1=new Color(139,19,76,255);
		Color c2=new Color(184,30,103,255);
		Color c3=new Color(253,122,202,255);
		Dimension d=new Dimension(170,60);
		Dimension d1=new Dimension(150,40);
		Dimension d2=new Dimension(150,20);
		JLabel titre=new JLabel("Kendif nettoyage et service");
		Font f2=new Font("Arial", Font.BOLD, 20);
		Font f1=new Font("Arial", Font.BOLD, 50);
		Font f=new Font("Arial", Font.BOLD, 30);
		titre.setFont(f);
		titre.setForeground(new Color(0,0,0,175));
		nombreContratActif=new JLabel("  50");
		nombreContratRempli=new JLabel("  20");
		nombreEmploye=new JLabel("  150");
		nombreContratActif.setFont(f1);
		nombreContratRempli.setFont(f1);
		nombreEmploye.setFont(f1);
		text1.setFont(f2);
		text2.setFont(new Font("Arial", Font.BOLD, 18));
		text3.setFont(f2);
		nombreContratActif.setForeground(Color.white);
		nombreContratRempli.setForeground(Color.white);
		nombreEmploye.setForeground(Color.white);
		text1.setForeground(Color.white);
		text2.setForeground(Color.white);
		text3.setForeground(Color.white);
		nombreContratActif.setPreferredSize(d1);
		nombreContratRempli.setPreferredSize(d1);
		nombreEmploye.setPreferredSize(d1);
		text1.setPreferredSize(d2);
		text2.setPreferredSize(d2);
		text3.setPreferredSize(d2);
		contratActif=new JPanel();
		contratRempli=new JPanel();
		employes=new JPanel();
		courbe=new JPanel();
		JScrollPane scourbe=new JScrollPane(courbe);
		scourbe.setPreferredSize(new Dimension(600,400));
		contratActif.setBackground(c1);
		contratRempli.setBackground(c2);
		employes.setBackground(c3);
		JPanel e=new JPanel();
		e.setPreferredSize(new Dimension(170,20));
		e.setBackground(c1);
		JPanel e1=new JPanel();
		e1.setPreferredSize(new Dimension(170,20));
		e1.setBackground(c2);
		JPanel e2=new JPanel();
		e2.setPreferredSize(new Dimension(170,20));
		e2.setBackground(c3);
		entete.setPreferredSize(new Dimension(600,50));
		entete.add(titre);
		entete1.setPreferredSize(new Dimension(600,20));
		entete1.setBackground(Color.white);
		contratActif.setPreferredSize(d);
		contratRempli.setPreferredSize(d);
		employes.setPreferredSize(d);
		espace.setPreferredSize(new Dimension(20,150));
		espace1.setPreferredSize(new Dimension(20,150));
		espace.setBackground(Color.white);
		espace1.setBackground(Color.white);
		contratActif.add(nombreContratActif);
		contratActif.add(e);
		contratActif.add(text1);
		contratRempli.add(nombreContratRempli);
		contratRempli.add(e1);
		contratRempli.add(text2);
		employes.add(nombreEmploye);
		employes.add(e2);
		employes.add(text3);
		
		
		
		LocalDate j1 = LocalDate.now();
		LocalDate j2 = j1.plusDays(1);
		LocalDate j3 = j2.plusDays(1);
		LocalDate j4 = j3.plusDays(1);
		LocalDate j5 = j4.plusDays(1);
		LocalDate j6 = j5.plusDays(1);
		LocalDate j7 = j6.plusDays(1);
		
		JLabel labcon=new JLabel("Les contrats qui expirent cette semaine: ");
		labcon.setFont(f);
		Box boxcon=Box.createHorizontalBox();
		boxcon.add(labcon);
		Box boxvert=Box.createVerticalBox();
		boxvert.add(boxcon);
		boxvert.add(new Separateur(400,20));
		
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
			String tabcli="create table IF NOT EXISTS CONTENU_DEVIS ("+
					   "CODE_SERVICE         VARCHAR(30)          not null,"+
					   "NUMERO_DEVIS         VARCHAR(30)          not null,"+
					   "constraint PK_CONTENU_DEVIS primary key (CODE_SERVICE, NUMERO_DEVIS)"+
					")";

			state7.addBatch(tabcli);
			state7.executeBatch();
			state7.clearBatch();
			state7.close();
			
			
						
			
			//Création d'un objet Statement
			//Statement state = conn.createStatement();
		//	Statement state1 = conn.createStatement();
			//Statement state2 = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
		//	ResultSet result = state.executeQuery("SELECT * FROM contrat where rempli=false ");
			ResultSet res,res1,res2;
			//ResultSet result1 = state1.executeQuery("SELECT * FROM contrat where rempli=false");
			//ResultSet result2 = state2.executeQuery("SELECT * FROM service");
			//On récupère les MetaData
			/*ResultSetMetaData resultMeta = result.getMetaData();
			//ResultSetMetaData resultMeta1 = result1.getMetaData();
			ResultSetMetaData resultMeta2 = result2.getMetaData();*/
			PreparedStatement prepare,prepare1,prepare2;
			String str="SELECT * FROM contrat where rempli=false and date_fin=? or  date_fin=? or  date_fin=? or  date_fin=? or  date_fin=? or  date_fin=? or  date_fin=?";
			String str1="SELECT code_client FROM devis where numero_devis=?";
			String str2="SELECT nom_client FROM client where code_client=?";
			//Date date =  cdate.getTi
			prepare = conn.prepareStatement(str);
			prepare1 = conn.prepareStatement(str1);
			prepare2 = conn.prepareStatement(str2);
				
				
				//comb[0]="";
				prepare.setString(1,Date.valueOf(j1).toString());
				prepare.setString(2,Date.valueOf(j2).toString());
				prepare.setString(3,Date.valueOf(j3).toString());
				prepare.setString(4,Date.valueOf(j4).toString());
				prepare.setString(5,Date.valueOf(j5).toString());
				prepare.setString(6,Date.valueOf(j6).toString());
				prepare.setString(7,Date.valueOf(j7).toString());
				res=prepare.executeQuery();
				while(res.next())
				{
					prepare1.setString(1,res.getObject(2).toString());
					res1=prepare1.executeQuery();
					res1.next();
					
					prepare2.setString(1,res1.getObject(1).toString());
					res2=prepare2.executeQuery();
					res2.next();
					LocalDate localDate = LocalDate.parse(res.getObject(4).toString());
					JLabel labc=new JLabel("Contrat avec  "+res2.getObject(1).toString()+" expire le "+localDate.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)));
					labc.setFont(f2);
					labc.setForeground(Color.red);
					Box boxc=Box.createHorizontalBox();
					boxc.add(labc);
					boxvert.add(boxc);
					boxvert.add(new Separateur(400,5));
				}
				
				//On affiche le nom des colonnes
				
				//result1.close();
				prepare.close();
				prepare1.close();
				prepare2.close();
				conn.close();
				//state.close();
				
			//	state1.close();
				
			} 
			catch (Exception e11) 
			{
				e11.printStackTrace();
			}
		
		
		courbe.add(boxvert);
		courbe.setBackground(Color.white);
		
		Box b1=Box.createHorizontalBox();
		Box b2=Box.createHorizontalBox();
		Box b3=Box.createHorizontalBox();
		Box b4=Box.createHorizontalBox();
		Box b5=Box.createHorizontalBox();
		Box b7=Box.createHorizontalBox();
		Box b8=Box.createHorizontalBox();
		Box b6=Box.createVerticalBox();
		b1.add(entete);
		b2.add(entete1);
		b3.add(contratActif);
		b3.add(espace);
		b3.add(contratRempli);
		b3.add(espace1);
		b3.add(employes);
		JPanel enteteClone=new JPanel();
		enteteClone.setPreferredSize(new Dimension(600,20));
		enteteClone.setBackground(Color.white);
		b7.add(enteteClone);
		JPanel enteteClone1=new JPanel();
		enteteClone1.setPreferredSize(new Dimension(600,20));
		enteteClone1.setBackground(Color.white);
		b8.add(enteteClone1);
		b4.add(scourbe);
		Bouton b=new Bouton("actualiser");
		b.addActionListener(new BoutonActualiser());
		b.setAlignmentX(LEFT_ALIGNMENT);
		b5.add(b);
		b6.add(b1);
		b6.add(b2);
		b6.add(b3);
		b6.add(b7);
		b6.add(b4);
		b6.add(b8);
		b6.add(b5);
		this.add(b6);
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
			this.nombreContratActif.setText(s1);
			this.nombreContratRempli.setText(s);
			this.nombreEmploye.setText(s2);
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
	
	class BoutonActualiser implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0) 
		{
			Statistique.this.aJour();
		}
	}
	
}
