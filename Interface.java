import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Interface extends JFrame
{
	private JPanel panelMenu;
	private Facture facture;
	private Statistique statistiques;
	private Employe employes;
	private Bouton buttonMenu[]=new Bouton[7];
	private Client client;
	private Devis devis;
	private Service service;
	private Calendrier plannification;
	CardLayout cl = new CardLayout();
	JPanel content = new JPanel();
	//Liste des noms de nos conteneurs pour la pile de cartes
	String[] listContent = {"CARD_1", "CARD_2", "CARD_3", "CARD_4", "CARD_5","CARD_6","CARD_7"};
	static int indice = 0;
	
	public Interface()
	{
		
		this.setTitle("Gestion Nettoyage");
		this.setSize(800, 750);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		statistiques = new Statistique();
		statistiques.setBackground(Color.white);
		employes = new Employe();
		employes.setBackground(Color.white);
		plannification = new Calendrier();
		plannification.setBackground(Color.white);
		devis = new Devis();
		devis.setBackground(Color.white);
		facture=new Facture();
		facture.setBackground(Color.white);
		client=new Client();
		client.setBackground(Color.white);
		service=new Service();
		service.setBackground(Color.white);
		
	}
	
	public void execute()
	{
		this.menuGauche();
		
		//On définit le layout
		content.setLayout(cl);
		//On ajoute les cartes à la pile avec un nom pour les retrouver
		content.add(statistiques, listContent[0]);
		content.add(employes, listContent[1]);
		content.add(plannification, listContent[2]);
		content.add(devis, listContent[3]);
		content.add(facture, listContent[4]);
		content.add(client, listContent[5]);
		content.add(service, listContent[6]);
		/*Bouton po=new Bouton("click");
		po.addActionListener(new BoutonEcoute());
		statistiques.add(po);*/
		//ImageIcon image = new ImageIcon("hokage.jpg");
		//this.setIconImage(image.getImage());
		statistiques.aJour();
		employes.aJour();
		client.aJour();
		service.aJour();
		this.getContentPane().add(content, BorderLayout.CENTER);
		this.setVisible(true);
	}
	
	public void menuGauche()
	{
		panelMenu=new JPanel();
		JLabel titre=new JLabel("      Menu");
		titre.setBackground(Color.white);
		titre.setForeground(Color.white);
		titre.setPreferredSize(new Dimension(150,40));
		titre.setFont(new Font("Arial", Font.BOLD, 25));
		Color c=new Color(11,103,86,255);
		panelMenu.setPreferredSize(new Dimension(150,600));
		panelMenu.setBackground(c);
		buttonMenu[0]=new Bouton("STATISTIQUES");
		buttonMenu[1]=new Bouton("EMPLOYES");
		buttonMenu[2]=new Bouton("CALENDRIER");
		buttonMenu[3]=new Bouton("DEVIS");
		buttonMenu[4]=new Bouton("FACTURE");
		buttonMenu[5]=new Bouton("CLIENT");
		buttonMenu[6]=new Bouton("SERVICE");
		
		buttonMenu[0].addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent event)
			{
				cl.show(content, listContent[0]);
			}
		});
		buttonMenu[1].addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent event)
			{
				cl.show(content, listContent[1]);
			}
		});
		buttonMenu[2].addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent event)
			{
				cl.show(content, listContent[2]);
			}
		});
		buttonMenu[3].addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent event)
			{
				cl.show(content, listContent[3]);
			}
		});
		buttonMenu[4].addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent event)
			{
				cl.show(content, listContent[4]);
			}
		});
		buttonMenu[5].addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent event)
			{
				cl.show(content, listContent[5]);
			}
		});
		buttonMenu[6].addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent event)
			{
				cl.show(content, listContent[6]);
			}
		});
		panelMenu.add(titre);
		for(int i=0 ;i<7;i++)
			panelMenu.add(buttonMenu[i]);
		this.getContentPane().add(panelMenu, BorderLayout.WEST);
	}
	
	
	
	/*JPanel p=new JPanel();
	p.setBackground(Color.red);
	JButton liste=new JButton("liste des employes");
	liste.addActionListener(new ActionListener()
	{
		
		public void actionPerformed(ActionEvent event)
		{
			cl.show(content, listContent[5]);
		}
	});
	employes.add(liste);
	content.add(p, listContent[5]);
	liste de selection combobox
	combo.setPreferredSize(new Dimension(100, 20));
combo.addItem("Option 1");
combo.addItem("Option 2");
combo.addItem("Option 3");
combo.addItem("Option 4");
combo.addItemListener(new ItemState());
class ItemState implements ItemListener{
public void itemStateChanged(ItemEvent e) {
System.out.println("événement déclenché sur : " + e.getItem());
}
}

String[] tab = {"Option 1", "Option 2", "Option 3", "Option 4"};
combo = new JComboBox(tab);
//Ajout du listener
combo.addItemListener(new ItemState());
combo.setPreferredSize(new Dimension(100, 20));
combo.setForeground(Color.blue);
//La fin reste inchangée
}
//Classe interne implémentant l'interface ItemListener
class ItemState implements ItemListener{
public void itemStateChanged(ItemEvent e) {
System.out.println("événement déclenché sur : " + e.getItem());
}



check box
private JCheckBox check2 = new JCheckBox("Case 2");
check1.addActionListener(new StateListener());
check2.addActionListener(new StateListener());
top.add(check1);
top.add(check2);
class StateListener implements ActionListener{
public void actionPerformed(ActionEvent e) {

text en entree
private JTextField jtf = new JTextField("Valeur par défaut");
Font police = new Font("Arial", Font.BOLD, 14);
jtf.setFont(police);
jtf.setPreferredSize(new Dimension(150, 30));
jtf.setForeground(Color.BLUE);
top.add(label);
top.add(jtf);
jtf.getText()
private JFormattedTextField jtf =
new JFormattedTextField(NumberFormat.getIntegerInstance());
MaskFormatter tel = new MaskFormatter("## ## ## ## ##");
//Ou encore
MaskFormatter tel2 = new MaskFormatter("##-##-##-##-##");
//Vous pouvez ensuite le passer à votre zone de texte
JFormattedTextField jtf = new JFormattedTextField(tel2);
}catch(ParseException e){e.printStackTrace();}


//Boîte du message d'information
jop1 = new JOptionPane();
jop1.showMessageDialog(null, "Message informatif", "Information", JOptionPane.
,! INFORMATION_MESSAGE);
//Boîte du message préventif
jop2 = new JOptionPane();
jop2.showMessageDialog(null, "Message préventif", "Attention", JOptionPane.
,! WARNING_MESSAGE);
//Boîte du message d'erreur
 * jop3 = new JOptionPane();
jop3.showMessageDialog(null, "Message d'erreur", "Erreur", JOptionPane.ERROR_MESSAGE);

int option = jop.showConfirmDialog(null,
"Voulez-vous lancer l'animation ?",
"Lancement de l'animation",
JOptionPane.YES_NO_OPTION,
JOptionPane.QUESTION_MESSAGE);
if(option == JOptionPane.OK_OPTION){

JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
String nom = jop.showInputDialog(null, "Veuillez décliner votre, identité ","Gendarmerie nationale !", JOptionPane.QUESTION_MESSAGE);
jop2.showMessageDialog(null, "Votre nom est " + nom,
"Identité", JOptionPane.INFORMATION_MESSAGE);

dialogue personaliser page422

ButtonGroup bg = new ButtonGroup();
bg.add(tranche1);
bg.add(tranche2);
bg.add(tranche3);
bg.add(tranche4);
panAge.add(tranche1);
panAge.add(tranche2);
panAge.add(tranche3);
panAge.add(tranche4);


menuuuuu
private JMenuBar menuBar = new JMenuBar();
private JMenu test1 = new JMenu("Fichier");
private JMenu test1_2 = new JMenu("Sous ficher");
private JMenu test2 = new JMenu("Edition");
private JMenuItem item1 = new JMenuItem("Ouvrir");
private JMenuItem item2 = new JMenuItem("Fermer");
private JMenuItem item3 = new JMenuItem("Lancer");
private JMenuItem item4 = new JMenuItem("Arrêter");
private JCheckBoxMenuItem jcmi1 = new JCheckBoxMenuItem("Choix 1");
private JCheckBoxMenuItem jcmi2 = new JCheckBoxMenuItem("Choix 2");
private JRadioButtonMenuItem jrmi1 = new JRadioButtonMenuItem("Radio 1");
private JRadioButtonMenuItem jrmi2 = new JRadioButtonMenuItem("Radio 2");
//On initialise nos menus
this.test1.add(item1);
//On ajoute les éléments dans notre sous-menu
this.test1_2.add(jcmi1);
this.test1_2.add(jcmi2);
//Ajout d'un séparateur
this.test1_2.addSeparator();
jrmi1.setSelected(true);
item2.addActionListener(new ActionListener(){
public void actionPerformed(ActionEvent arg0) {
System.exit(0);
}
});
this.test1.add(item2);
this.test2.add(item3);
this.menuBar.add(test1);
this.menuBar.add(test2);
this.setJMenuBar(menuBar);
*/
	
}
