package demo;

import org.infinispan.Cache;

public class VotingCacheManager {

	Cache<String,VotingCard> votes;

	Cache<String,Candidate> candidates;

}
