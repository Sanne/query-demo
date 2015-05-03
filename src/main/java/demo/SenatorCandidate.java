package demo;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Indexed
public class SenatorCandidate extends Candidate {

	public SenatorCandidate(String name, String party) {
		super(name, party);
	}

	@Field
	public final Office runningfor = Office.SENATOR;

}
