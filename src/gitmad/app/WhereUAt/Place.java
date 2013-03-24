package gitmad.app.WhereUAt;

import android.graphics.Bitmap;

public class Place 
{
	String name;
	double lat;
	double lon;
	int id;	
	
	public Place(int id, String name, double lat, double lon)  //, Bitmap image)
	{
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.id = id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public int getID()
	{
		return id;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}
	
	public void setlat(double lat)
	{
		this.lat = lat;
	}
	
	public double getLat()
	{
		return lat;
	}
	
	public void setLon(double lon)
	{
		this.lon = lon;
	}
	
	public double getLon()
	{
		return lon;
	}
	
	public String toString()
	{
		return "ID: " + this.id + " Name: " + this.name + " Lat: " + this.lat + " Lon: " + this.lon;
	}
	
}
