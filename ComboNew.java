package test;

import javax.swing.JComboBox;

public class ComboNew extends JComboBox 
{
	private String []id=new String[100];
	private int i=0;
	
	public ComboNew(String s)
	{
		super();
		id[i++]=s;
	}
	
	public ComboNew()
	{
		super();
	}
	
	
	public void setMat(int i,String s)
	{
		id[i]=s;
	}
	
	public String getMat(int i)
	{
		return id[i];
	}
}
