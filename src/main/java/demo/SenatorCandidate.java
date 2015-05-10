package demo;

import java.io.Serializable;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;

@Indexed
public class SenatorCandidate extends Candidate implements Serializable {

	public SenatorCandidate(String name, String party) {
		super(name, party);
	}

	@Field
	public final Office runningfor = Office.SENATOR;

}
