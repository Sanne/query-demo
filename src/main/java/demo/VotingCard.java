package demo;

import java.util.Date;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Indexed
public class VotingCard {
	
	@IndexedEmbedded
	Candidate voteForSenate;

	@IndexedEmbedded
	Candidate voteForGovernor;

	@Field
	int voterAge;

	@Field
	String voterName;
	
	@Field
	Date votingTime;
	
	@Field
	String votingStation;
	

}
