package demo;

import java.util.Date;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Indexed
public class VotingCard {

	public VotingCard(SenatorCandidate voteForSenate, GovernorCandidate voteForGovernor, int voterAge, String voterName, Date votingTime, String votingStation) {
		this.voteForSenate = voteForSenate;
		this.voteForGovernor = voteForGovernor;
		this.voterAge = voterAge;
		this.voterName = voterName;
		this.votingTime = votingTime;
		this.votingStation = votingStation;
	}

	@IndexedEmbedded
	public final SenatorCandidate voteForSenate;

	@IndexedEmbedded
	public final GovernorCandidate voteForGovernor;

	@Field
	public final int voterAge;

	@Field
	public final String voterName;

	@Field
	public final Date votingTime;

	@Field
	public final String votingStation;

}
