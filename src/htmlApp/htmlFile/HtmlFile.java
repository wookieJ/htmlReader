package htmlApp.htmlFile;

public class HtmlFile
{
	private String path;
	private String description;
	private String lesson;
	private String notes;
	
	public String getNotes()
	{
		return notes;
	}
	public void setNotes(String notes)
	{
		this.notes = notes;
	}
	public String getPath()
	{
		return path;
	}
	public void setPath(String path)
	{
		this.path = path;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public String getLesson()
	{
		return lesson;
	}
	public void setLesson(String lesson)
	{
		this.lesson = lesson;
	}
	
	public HtmlFile(String path, String description, String lesson)
	{
		this.path = path;
		this.description = description;
		this.lesson = lesson;
	}
	
	public HtmlFile()
	{
		
	}
}
