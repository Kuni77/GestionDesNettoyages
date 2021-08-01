import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class Separateur extends JPanel 
{
	private int x,y;
	
	public Separateur(int a,int b)
	{
		super();
		x=a;
		y=b;
		this.setPreferredSize(new Dimension(x,y));
		this.setBackground(Color.white);
	}
}
