package items;

public class Movie extends Item{
	private String director;
	private Double duration;
	
	public Movie() {}
	public Movie(int ID, String director, Double duration)
	{
		super(ID);
		setDirector(director);
		setDuration(duration);
	}
	public Movie(int ID, String director, Double duration, Boolean available)
	{
		super(ID, available);
		setDirector(director);
		setDuration(duration);
	}
	public Movie(int ID, String director, Double duration, Boolean available, String date)
	{
		super(ID, available, date);
		setDirector(director);
		setDuration(duration);
	}


	public String getDirector() {
		return director;
	}
	private void setDirector(String director) {
		this.director = director;
	}
	public Double getDuration() {
		return duration;
	}
	public String getDurationOutput()
	{
		int hours = duration.intValue();
		int minutes = (int) ((duration - hours) * 100);
		String durationOutput = hours + "H and " + minutes + "M";
		return durationOutput;
	}
	private void setDuration(Double duration) {
		this.duration = duration;
	}

	@Override
	public String displayExtra()
	{
		String output = "Director: " + director + 
							"\nDuration: " + getDurationOutput();
		return output;
	}
}
