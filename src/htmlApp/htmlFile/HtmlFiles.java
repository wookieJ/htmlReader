package htmlApp.htmlFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class HtmlFiles
{
	private ArrayList<HtmlFile> links;
	private Integer linkNumber;
	
	public ArrayList<HtmlFile> getLinks()
	{
		return links;
	}
	
	public Integer getLinkNumber()
	{
		return linkNumber;
	}
	
	public void setLinkNumber(Integer linkNumber)
	{
		this.linkNumber = linkNumber;
	}
	
	public void addLink(HtmlFile name)
	{
		links.add(name);
	}
	
	public void increaseLinkNumber()
	{
		this.linkNumber++;
	}
	
	public void decreaseLinkNumber()
	{
		if(this.linkNumber > 0)
			this.linkNumber--;
	}
	
	public String getPath(int number)
	{
		String tmp = "/htmlApp/source/wiki/";
		tmp += links.get(number).getPath();
		tmp += ".html";
		return tmp;
	}
	
	public void getLinksFromFile(File file)
	{
		Scanner in;
		String tmpString;
		String[] boxString = new String[3];
		HtmlFile tmpHtmlFile;
		try
		{
			in = new Scanner(file);
			while(in.hasNextLine())
			{
				tmpString = in.nextLine();
				boxString = tmpString.split(";");
				tmpHtmlFile = new HtmlFile(boxString[0],boxString[1],boxString[2]);
				addLink(tmpHtmlFile);
			}
			in.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
	
	public HtmlFiles()
	{
		links = new ArrayList<>();
		linkNumber = 0;
	}

	public void clear()
	{
		links.clear();
		linkNumber = 0;
	}
}
