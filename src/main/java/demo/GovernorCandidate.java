package demo;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Indexed
public class GovernorCandidate extends Candidate {

	public GovernorCandidate(String name, String party) {
		super(name, party);
	}

	@Field
	public final Office runningfor = Office.GOVERNOR;

}
