package demo.service;

import java.util.Date;

import org.infinispan.Cache;

import demo.GovernorCandidate;
import demo.SenatorCandidate;
import demo.VotingCard;


public class VotingCacheManagement {

	private final Cache<String, VotingCard> cache;
	private final String votingStationName;

	public VotingCacheManagement(Cache<String, VotingCard> cache, String votingStationName) {
		this.cache = cache;
		this.votingStationName = votingStationName;
	}

	private void storeVote(String idVoter, VotingCard vote) {
		cache.put( idVoter, vote );
	}

	public void storeVote(String idCardNumber, int voterAge, String voterName, SenatorCandidate voteForSenate, GovernorCandidate voteForGovernor) {
		VotingCard card = new VotingCard(voteForSenate, voteForGovernor, voterAge, voterName, new Date(), votingStationName );
		storeVote( idCardNumber, card );
	}

	public VotingCard getVote(String voter) {
		return cache.get( voter );
	}

}
