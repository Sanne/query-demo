package demo;

import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.jboss.logging.Logger;

import java.io.IOException;

public class VotingCacheManager {

	private Cache<String, VotingCard> cache;
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
		this.cache = cacheManager.getCache("Votes", true);
	}

	public Cache<String, VotingCard> getCache() {
		return this.cache;
	}

	public static VotingCacheManager getInstance() {
		if (instance == null) {
			instance = new VotingCacheManager();
		}
		return instance;
	}

}
