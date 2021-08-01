import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Random;

public class Main 
{
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Interface fenetre=new Interface();
		fenetre.execute();
	/*	Random rand = new Random();
		int max=8,min=1,n;
		n=rand.nextInt(max-min+1);
		System.out.println(n);*/
		
		/*try
		{
			Class.forName("org.postgresql.Driver");
			System.out.println("Driver O.K.");
			String url = "jdbc:postgresql://localhost:5432/gestiondenettoyage";
			String user = "postgres";
			String passwd = "kuni";
			Connection conn = DriverManager.getConnection(url, user, passwd);
			System.out.println("Connexion effective !");
			//Création d'un objet Statement
			Statement state = conn.createStatement();
			//L'objet ResultSet contient le résultat de la requête SQL
			ResultSet result = state.executeQuery("SELECT * FROM classe");
			//On récupère les MetaData
			ResultSetMetaData resultMeta = result.getMetaData();
			System.out.println("\n**********************************");
			//On affiche le nom des colonnes
			for(int i = 1; i <= resultMeta.getColumnCount(); i++)
			System.out.print("\t" + resultMeta.getColumnName(i).toUpperCase() + "\t *");
			System.out.println("\n**********************************");
			while(result.next()){
			for(int i = 1; i <= resultMeta.getColumnCount(); i++)
			System.out.print("\t" + result.getObject(i).toString() + "\t |");
			System.out.println("\n---------------------------------");
			
			
			
			Statement state = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					//On crée la requête
					String query = "SELECT prof_nom, prof_prenom FROM professeur";
					//Premier trou pour le nom du professeur
					query += " WHERE prof_nom = ?";
					//Deuxième trou pour l'identifiant du professeur
					query += " OR prof_id = ?";
					//On crée l'objet avec la requête en paramètre
					PreparedStatement prepare = conn.prepareStatement(query);
					//On remplace le premier trou par le nom du professeur
					prepare.setString(1, "MAMOU");
					//On remplace le deuxième trou par l'identifiant du professeur
					prepare.setInt(2, 2);
					//On affiche la requête exécutée
					System.out.println(prepare.toString());
					//On modifie le premier trou
					prepare.setString(1, "TOTO");
					//On affiche à nouveau la requête exécutée
					System.out.println(prepare.toString());
					//On modifie le deuxième trou
					prepare.setInt(2, 159753);
					//On affiche une nouvelle fois la requête exécutée
					System.out.println(prepare.toString());
					
					prepare.setString(1, "Daniel");
					prepare.executeUpdate();
					res = state.executeQuery(query);
					res.first();
					prepare.close();
					state.executeUpdate("INSERT INTO professeur (prof_nom, prof_prenom) VALUES('SALMON', 'Dylan')");
					state.executeUpdate("DELETE FROM professeur WHERE prof_nom = 'MAMOU'");
			}
			result.close();
			state.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		 JFrame f = new JFrame("ma fenetre");
		    f.setSize(100,200);

		    JPanel pannel = new JPanel(); 
		    JLabel jLabel1 =new JLabel("Mon texte dans JLabel"); 
		    pannel.add(jLabel1);

		    ImageIcon icone = new ImageIcon("hokage.jpg");
		    JLabel jLabel2 =new JLabel(icone); 
		    pannel.add(jLabel2);

		    JLabel jLabel3 =new JLabel("Mon texte",icone,SwingConstants.LEFT); 
		    pannel.add(jLabel3);

		    f.getContentPane().add(pannel);
		    f.setVisible(true);
		 JFrame f = new JFrame("ma fenetre");
		    f.setSize(300,100);
		    JPanel pannel = new JPanel(); 

		    JButton bouton = new JButton("saisir");
		    pannel.add(bouton);

		    JTextField jEdit = new JTextField("votre nom");

		    JLabel jLabel1 =new JLabel("Nom : "); 
		    jLabel1.setBackground(Color.red);
		    jLabel1.setDisplayedMnemonic('n');
		    jLabel1.setLabelFor(jEdit);
		    pannel.add(jLabel1);
		    pannel.add(jEdit);

		    f.getContentPane().add(pannel);
		    f.setVisible(true);
		JFrame f = new JFrame("ma fenetre");
	    f.setSize(300,100);
	    JButton b =new JButton("Mon bouton"); 
	    f.getContentPane().add(b);

	    JMenuBar menuBar = new JMenuBar();
	    f.setJMenuBar(menuBar);

	    JMenu menu = new JMenu("Fichier");
	   // menu.add(menuItem);
	    menuBar.add(menu);

	    f.setVisible(true);*/
	}
}
