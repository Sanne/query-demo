package demo;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Indexed
public class Candidate {
	
	@Field
	String name;

	@Field
	String party;
	
	@Field
	String district;
	
	@Field
	Office runningfor;
}
