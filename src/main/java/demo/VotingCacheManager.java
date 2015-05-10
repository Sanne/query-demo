package demo;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.jboss.logging.Logger;

import java.io.IOException;

public class VotingCacheManager {

	private Cache<String, VotingCard> votingCache;
	private Cache<String, Candidate> candidateCache;
	private static VotingCacheManager instance;
	private static final Logger logger = Logger
			.getLogger(VotingCacheManager.class);

	private VotingCacheManager() {
		EmbeddedCacheManager cacheManager = null;
		try {
			cacheManager = new DefaultCacheManager
               ("localonly-infinispan.xml");
		} catch (IOException e) {
			throw new InstantiationError("Error looking up configuration file " +
					"for Infinispan");
		}
		this.votingCache = cacheManager.getCache("Votes", true);
		this.candidateCache = cacheManager.getCache("Candidates", true);
	}

	public Cache<String, VotingCard> getVotingCache() {
		return this.votingCache;
	}

	public Cache<String, Candidate> getCandidateCache() {
		return candidateCache;
	}

	public static VotingCacheManager getInstance() {
		if (instance == null) {
			instance = new VotingCacheManager();
		}
		return instance;
	}
}
