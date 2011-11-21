package Models;

public class LocationAddress
{
	public String city;
	public String state;
	public String country;
	
	public LocationAddress(String city, String state, String country)
	{
		this.city = city;
		this.state = state;
		this.country = country;
	}
	
	public String toString()
	{
		return city + ", " + state + ", " + country;
	}
}