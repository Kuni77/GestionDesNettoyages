package test;

/*





*/
import java.awt.Color;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.Printable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

/*

 
 Classe qui permet de sauvegarder directement
 
 le contenu une JPanel en image. Il est possible
 
 de préciser la zone à sauvegarder, ainsi que la couleur
 
 qui sera utilisée pour le fond (donc, si le fond n'a pas
 
 été défini dans la fonction paint(Graphics) du JPanel - par
 
 appel à Graphics#fillRect(0,0,getWidht(), getHeight()) par exemple -,
 
 il se peut que la couleur de fond diffère.
 
 @author laurent bernabé

 *

 
*/

public class Telecharger implements ActionListener {
	
	/*

	 
 Dossier du dernier fichier validé. 
	 
*/

	
	public JPanel p1,p2;
	
	public Telecharger(JPanel j,Component p) throws IOException
	{
		//dossier=this.menuSauvegarder(j);
		p1=j;
		p2=(JPanel) p;
	}
	/*

	 
 Propose une fenêtre Commune de sauvegarde de fichier JPG.
	 
 @param parent - Component - Parent
	 
 @return true si la fenêtre a été validée ou le
	 
 fichier a été validé.
	 


	protected File menuSauvegarder(Component parent){
		JFileChooser fenetreMenu = 
			new JFileChooser();
		fenetreMenu.setCurrentDirectory(dossier);
		fenetreMenu.setFileFilter(new FileNameExtensionFilter("Image compressée",new String[]{"jpg"}));
		int resultat = fenetreMenu.showSaveDialog(parent);
		if (resultat == JFileChooser.APPROVE_OPTION){
			dossier = fenetreMenu.getCurrentDirectory();
			return fenetreMenu.getSelectedFile();
		}
		else return null;
	}
	*/
	
	
	protected File menuSauvegarder(Component parent){
		JFileChooser fenetreMenu = 
			new JFileChooser();
		//fenetreMenu.showOpenDialog(null);
		fenetreMenu.setFileFilter(new FileNameExtensionFilter("Image compressée",new String[]{"jpg"}));
		int resultat = fenetreMenu.showSaveDialog(parent);
		if (resultat == JFileChooser.APPROVE_OPTION){
			//dossier = fenetreMenu.getCurrentDirectory();
			return fenetreMenu.getSelectedFile();
		}
		else return null;
	}
	
	
/*

	 
 Sauvegarde directement le contenu d'un JPanel dans 
	 
 un fichier JPG. Se base sur la méthode paint(Graphics)
	 
 du JPanel pour dessiner le contenu de l'image.<br>
	 
 Le fond de l'image sera blanc. Toute la zone image du
	 
 panneau est dessinée.
	 
 @param panneauImage - JPanel - Le panneau source
	 
 @param fichier - File - Le descriptif du fichier dans 
	 
 lequel sauvegarder l'image .
	 
 @throws IOException si le fichier n'a pas pu être créé .
	 
*/

	public void sauverJPanelDansFile(JPanel panneauImage, File fichier)
	throws IOException{
		BufferedImage tamponSauvegarde = new BufferedImage(
				panneauImage.getPreferredSize().width,
				panneauImage.getPreferredSize().height,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = tamponSauvegarde.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, panneauImage.getPreferredSize().width,
				panneauImage.getPreferredSize().height);
		panneauImage.paint(g);
		ImageIO.write(tamponSauvegarde, "JPG", fichier);
	}
	
	/*

	 
 Sauvegarde directement le contenu d'un JPanel dans 
	 
 un fichier JPG, mais en ayant proposé un menu. Se base sur la méthode paint(Graphics)
	 
 du JPanel pour dessiner le contenu de l'image.<br>
	 
 Le fond de l'image sera blanc. Toute la zone image du
	 
 panneau est dessinée.<br>
	 
 Aucun fichier ne sera crée si la fenêtre a été fermée
	 
 ou si le bouton Annuler a été pressé .
	 
 @param panneauImage - JPanel - Le panneau source
	 
 @param parent - Component - Le composant parent du menu
	 
 sauvegarde.
	 
 @throws IOException si le fichier n'a pas pu être créé .
	 
*/

	public void sauverJPanelDansFichier(JPanel panneauImage,
			Component parent)
	throws IOException, DocumentException{
		File fichier = menuSauvegarder(parent);
		if (fichier != null) 
		{
			Document document = new Document(PageSize.A3, 20, 20, 20, 20);
			
			BufferedImage tamponSauvegarde = new BufferedImage(panneauImage
					.getPreferredSize().width, panneauImage.getPreferredSize().height,
					BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = tamponSauvegarde.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, panneauImage.getPreferredSize().width, panneauImage
					.getPreferredSize().height);
			panneauImage.paint(g);
			if(fichier.toString().endsWith("pdf"))
			{
				PdfWriter.getInstance(document, new FileOutputStream(fichier));
				document.open();
				document.add(Image.getInstance(tamponSauvegarde,null));
			}
			else 
			{
				PdfWriter.getInstance(document, new FileOutputStream(new File(fichier.toString()+".pdf")));
				document.open();
				document.add(Image.getInstance(tamponSauvegarde,null));
			}
					
			
			
			//Image image = Image.getInstance(getClass().getResource("/logo.png"));
			
			document.close();
			
			
		}
	}
	
	/*

	 
 Sauvegarde directement le contenu d'un JPanel dans 
	 
 un fichier JPG. La zone à sauvegarder est à préciser.
	 
 Se base sur la méthode paint(Graphics)
	 
 du JPanel pour dessiner le contenu de l'image.<br>
	 
 Le fond de l'image sera blanc. La taille de la zone à sauvegarder est à préciser.
	 
 (Cela reste par rapport à l'origine (0,0) du JPanel)
	 
 @param panneauImage - JPanel - Le panneau source
	 
 @param fichier - File - Le descriptif du fichier dans 
	 
 lequel sauvegarder l'image .
	 
 @param tailleEnX - int - la largeur  de la zone à sauver
	 
 @param tailleEnY - int - la longueur de la zone à sauver
	 
 @throws IOException si le fichier n'a pas pu être créé .
	 
*/

	public void sauverJPanelDansFileSelonZone(JPanel panneauImage, File fichier,
			int tailleEnX, int tailleEnY)
	throws IOException{
		BufferedImage tamponSauvegarde = new BufferedImage(
				tailleEnX,
				tailleEnY,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = tamponSauvegarde.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(
				0, 
				0, 
				tailleEnX,
				tailleEnY		
		);
		panneauImage.paint(g);
		ImageIO.write(tamponSauvegarde, "JPG", fichier);
	}
	
	/*

	 
 Sauvegarde directement le contenu d'un JPanel dans 
	 
 un fichier JPG, mais en ayant proposé un menu. 
	 
 La taille de la zone à sauvegarder est à préciser.
	 
 (Cela reste par rapport à l'origine (0,0) du JPanel)
	 
 Se base sur la méthode paint(Graphics)
	 
 du JPanel pour dessiner le contenu de l'image.<br>
	 
 Le fond de l'image sera blanc. Toute la zone image du
	 
 panneau est dessinée.<br>
	 
 Aucun fichier ne sera crée si la fenêtre a été fermée
	 
 ou si le bouton Annuler a été pressé .
	 
 @param panneauImage - JPanel - Le panneau source
	 
 @param parent - Component - Le composant parent du menu
	 
 sauvegarde.
	 
 @param tailleEnX - int - la largeur  de la zone à sauver
	 
 @param tailleEnY - int - la longueur de la zone à sauver
	 
 @throws IOException si le fichier n'a pas pu être créé .
	 
*/

	public void sauverJPanelDansFichierSelonZone(JPanel panneauImage,
			Component parent, int tailleEnX, int tailleEnY)
	throws IOException{
		File fichier = menuSauvegarder(parent);
		if (fichier != null) {
			BufferedImage tamponSauvegarde = new BufferedImage(
					tailleEnX, 
					tailleEnY,
					BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = tamponSauvegarde.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 
					tailleEnX, 
					tailleEnY);
			panneauImage.paint(g);
			if(fichier.toString().endsWith("jpg"))
				ImageIO.write(tamponSauvegarde, "JPG", fichier);
			else ImageIO.write(tamponSauvegarde, "JPG", 
					new File(fichier.toString()+".jpg"));
		}
	}
	
	/*

	 
 Sauvegarde directement le contenu d'un JPanel dans 
	 
 un fichier JPG. Se base sur la méthode paint(Graphics)
	 
 du JPanel pour dessiner le contenu de l'image.<br>
	 
 La couleur de fond de l'image sera à préciser. Toute la zone image du
	 
 panneau est dessinée.
	 
 @param panneauImage - JPanel - Le panneau source
	 
 @param fichier - File - Le descriptif du fichier dans 
	 
 lequel sauvegarder l'image .
	 
 @param couleurFond - Color - La couleur de fond qui sera utilisée dans
	 
 la sauvegarde de l'image .
	 
 @throws IOException si le fichier n'a pas pu être créé .
	 
*/

	public void sauverJPanelDansFileSelonCouleurFond(JPanel panneauImage, File fichier,
			Color couleurFond)
	throws IOException{
		BufferedImage tamponSauvegarde = new BufferedImage(
				panneauImage.getPreferredSize().width,
				panneauImage.getPreferredSize().height,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = tamponSauvegarde.getGraphics();
		g.setColor(couleurFond);
		g.fillRect(0, 0, panneauImage.getPreferredSize().width,
				panneauImage.getPreferredSize().height);
		panneauImage.paint(g);
		ImageIO.write(tamponSauvegarde, "JPG", fichier);
	}
	
	/*

	 
 Sauvegarde directement le contenu d'un JPanel dans 
	 
 un fichier JPG, mais en ayant proposé un menu. Se base sur la méthode paint(Graphics)
	 
 du JPanel pour dessiner le contenu de l'image.<br>
	 
 La couleur de fond de l'image sera à préciser. Toute la zone image du
	 
 panneau est dessinée.<br>
	 
 Aucun fichier ne sera crée si la fenêtre a été fermée
	 
 ou si le bouton Annuler a été pressé .
	 
 @param panneauImage - JPanel - Le panneau source
	 
 @param parent - Component - Le composant parent du menu
	 
 sauvegarde.
	 
 @param couleurFond - Color - La couleur de fond qui sera utilisée dans
	 
 la sauvegarde de l'image .
	 
 @throws IOException si le fichier n'a pas pu être créé .
	 
*/

	public void sauverJPanelDansFichierSelonCouleurFond(JPanel panneauImage,
			Component parent, Color couleurFond)
	throws IOException{
		File fichier = menuSauvegarder(parent);
		if (fichier != null) {
			BufferedImage tamponSauvegarde = new BufferedImage(panneauImage
					.getPreferredSize().width, panneauImage.getPreferredSize().height,
					BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = tamponSauvegarde.getGraphics();
			g.setColor(couleurFond);
			g.fillRect(0, 0, panneauImage.getPreferredSize().width, panneauImage
					.getPreferredSize().height);
			panneauImage.paint(g);
			if(fichier.toString().endsWith("jpg"))
				ImageIO.write(tamponSauvegarde, "JPG", fichier);
			else ImageIO.write(tamponSauvegarde, "JPG", 
					new File(fichier.toString()+".jpg"));
		}
	}
	
	/*

	 
 Sauvegarde directement le contenu d'un JPanel dans 
	 
 un fichier JPG. Se base sur la méthode paint(Graphics)
	 
 du JPanel pour dessiner le contenu de l'image.<br>
	 
 La couleur de fond ainsi que la taille de la zone à sauvegarder est à préciser.
	 
 (Cela reste par rapport à l'origine (0,0) du JPanel) .
	 
 @param panneauImage - JPanel - Le panneau source
	 
 @param fichier - File - Le descriptif du fichier dans 
	 
 lequel sauvegarder l'image .
	 
 @param
	 
 @param
	 
 @param couleurFond - Color - La couleur de fond qui sera utilisée dans
	 
 la sauvegarde de l'image .
	 
 @throws IOException si le fichier n'a pas pu être créé .
	 
*/

	public void sauverJPanelDansFileSelonZoneEtCouleurFond(
			JPanel panneauImage, File fichier,
			int tailleEnX, int tailleEnY,
			Color couleurFond)
	throws IOException{
		BufferedImage tamponSauvegarde = new BufferedImage(
				tailleEnX,
				tailleEnY,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics g = tamponSauvegarde.getGraphics();
		g.setColor(couleurFond);
		g.fillRect(0, 
					0, 
					tailleEnX,
					tailleEnY);
		panneauImage.paint(g);
		ImageIO.write(tamponSauvegarde, "JPG", fichier);
	}
	
	/*

	 
 Sauvegarde directement le contenu d'un JPanel dans 
	 
 un fichier JPG, mais en ayant proposé un menu. Se base sur la méthode paint(Graphics)
	 
 du JPanel pour dessiner le contenu de l'image.<br>
	 
 La couleur de fond ainsi que la taille de la zone à sauvegarder est à préciser.
	 
 (Cela reste par rapport à l'origine (0,0) du JPanel)<br>
	 
 Aucun fichier ne sera crée si la fenêtre a été fermée
	 
 ou si le bouton Annuler a été pressé .
	 
 @param panneauImage - JPanel - Le panneau source
	 
 @param parent - Component - Le composant parent du menu
	 
 sauvegarde.
	 
 @param
	 
 @param
	 
 @param couleurFond - Color - La couleur de fond qui sera utilisée dans
	 
 la sauvegarde de l'image .
	 
 @throws IOException si le fichier n'a pas pu être créé .
	 
*/

	public void sauverJPanelDansFichierSelonZoneEtCouleurFond(JPanel panneauImage,
			Component parent,
			int tailleEnX, int tailleEnY,
			Color couleurFond)
	throws IOException{
		File fichier = menuSauvegarder(parent);
		if (fichier != null) {
			BufferedImage tamponSauvegarde = new BufferedImage(tailleEnX, tailleEnY,
					BufferedImage.TYPE_3BYTE_BGR);
			Graphics g = tamponSauvegarde.getGraphics();
			g.setColor(couleurFond);
			g.fillRect(0, 0, tailleEnX, tailleEnY);
			panneauImage.paint(g);
			if(fichier.toString().endsWith("jpg"))
				ImageIO.write(tamponSauvegarde, "JPG", fichier);
			else ImageIO.write(tamponSauvegarde, "JPG", 
					new File(fichier.toString()+".jpg"));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		try {
			Telecharger.this.sauverJPanelDansFichier(Telecharger.this.p1,Telecharger.this.p2);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}