package demo;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Indexed
public class Candidate {

	Candidate(String name, String party) {
		this.name = name;
		this.party = party;
	}

	@Field
	public final String name;

	@Field
	public final String party;

}
