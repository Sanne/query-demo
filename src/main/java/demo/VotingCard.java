package demo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Indexed
public class VotingCard implements Serializable {

	public VotingCard(SenatorCandidate senateVote, GovernorCandidate governorVote, int voterAge, String voterName, Date votingTime, String votingStation) {
		this.senateVote = senateVote;
		this.governorVote = governorVote;
		this.voterAge = voterAge;
		this.voterName = voterName;
		this.votingTime = votingTime;
		this.votingStation = votingStation;
	}

	@IndexedEmbedded
	public final SenatorCandidate senateVote;

	@IndexedEmbedded
	public final GovernorCandidate governorVote;

	@Field
	public final int voterAge;

	@Field
	public final String voterName;

	@Field
	public final Date votingTime;

	@Field
	public final String votingStation;

}
