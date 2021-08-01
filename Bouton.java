import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class Bouton extends JButton implements MouseListener
{
	private String nomBouton;
	private Font f;
	private Color c,bc;
	private Dimension d;
	private String mat=null;
	private int ind;
	
	public Bouton()
	{
		super();
	}
	

	public Bouton(String n)
	{
		super(n);
		f=new Font("Arial", Font.BOLD, 16);
		c=new Color(11,103,86,255);
		d=new Dimension(150,40);
		bc=new Color(255,255,255,255);
		this.nomBouton=n;
		this.setBorderPainted(false);
		this.setBackground(c);
		this.setForeground(bc);
		this.setPreferredSize(d);
		this.setFont(f);
		this.addMouseListener(this);
			
	}
	
	public Bouton(String s,String s1)
	{
		super(s);
		mat=s1;
		nomBouton=s;
		
		
		//this.setPreferredSize(new Dimension(100,20));
	//	this.addMouseListener(this);
		
	}
	
	
	public Bouton(String s,String s1,int i)
	{
		super(s);
		mat=s1;
		nomBouton=s;
		ind=i;
		f=new Font("Arial", Font.BOLD, 16);
		c=new Color(11,103,86,255);
		d=new Dimension(150,40);
		bc=new Color(255,255,255,255);
		this.setBorderPainted(false);
		this.setBackground(c);
		this.setForeground(bc);
		this.setPreferredSize(d);
		this.setFont(f);
		this.addMouseListener(this);
	}
	
	public Bouton(String s,int i)
	{
		super(s);
		nomBouton=s;
		mat="suis";
		ind=i;
		f=new Font("Arial", Font.BOLD, 16);
		c=new Color(255,255,255,255);
		d=new Dimension(150,40);
		bc=new Color(161,8,73,255);
		this.setBorderPainted(false);
		this.setBackground(bc);
		this.setForeground(c);
		this.setPreferredSize(d);
		this.setFont(f);
		this.addMouseListener(this);
	}
	
	
	public String getMat()
	{
		return this.mat;
	}
	/*
	public void paintComponent(Graphics g)
	{
		System.out.println("sdfghjkj");
	}*/


	@Override
	public void mouseClicked(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		if(ind==2)
			this.setBackground(Color.pink);
		else
			this.setBackground(Color.gray);
		//this.setForeground(Color.black);
	}


	@Override
	public void mouseExited(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		if(mat==null)
		{
			this.setBackground(c);
			this.setForeground(Color.white);
		}
		else
		{
			if(ind==1)
			{
				this.setBackground(c);
				this.setForeground(Color.white);
			}
			else
			{
				if(ind==2)
				{
					this.setBackground(new Color(161,8,73,255));
				}
				else
				this.setBackground(Color.white);
			}
		}
			
	}
	
	
}
