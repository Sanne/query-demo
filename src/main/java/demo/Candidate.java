package demo;

import java.io.Serializable;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Indexed;

@Indexed
public class Candidate implements Serializable {

	public Candidate(String name, String party) {
		this.name = name;
		this.party = party;
	}

	@Fields({
		@Field(name="name", analyze=Analyze.YES),
		@Field(name="keyword_name", analyze=Analyze.NO)
	})
	public final String name;

	@Fields({
		@Field(name="party", analyze=Analyze.YES),
		@Field(name="keyword_party", analyze=Analyze.NO)
	})
	public final String party;

}
