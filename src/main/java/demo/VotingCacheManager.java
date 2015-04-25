package demo;

import org.infinispan.Cache;

public class VotingCacheManager {
	
	Cache<VoterId,VotingCard> votes;

	Cache<String,Candidate> candidates;

}
