package demo;

import org.hibernate.search.annotations.Latitude;
import org.hibernate.search.annotations.Longitude;

public class PollingStation {
	
	String name;
	
	@Latitude
	long latitude;
	
	@Longitude
	long longitude;

}
