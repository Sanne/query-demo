package demo;

import java.io.Serializable;

import org.hibernate.search.annotations.Latitude;
import org.hibernate.search.annotations.Longitude;

public class PollingStation implements Serializable {

	String name;

	@Latitude
	long latitude;

	@Longitude
	long longitude;

}
