package demo;

import java.io.Serializable;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Indexed
public class GovernorCandidate extends Candidate implements Serializable {

	public GovernorCandidate(String name, String party) {
		super(name, party);
	}

	@Field
	public final Office runningfor = Office.GOVERNOR;

}
