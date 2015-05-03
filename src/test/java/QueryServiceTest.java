import java.io.IOException;

import org.infinispan.manager.DefaultCacheManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import demo.VotingCard;
import demo.service.CandidatesDatabase;
import demo.service.VotingCacheManagement;

public class QueryServiceTest {

	private static DefaultCacheManager defaultCacheManager;
	private static VotingCacheManagement votingCache;

	@BeforeClass
	public static void startInfinispan() throws IOException {
		defaultCacheManager = new DefaultCacheManager( "localonly-infinispan.xml" );
		votingCache = new VotingCacheManagement(defaultCacheManager.getCache("Votes"), "Geecon One");
		votingCache.storeVote("007", 62, "James Bond", CandidatesDatabase.HomerSimpson, CandidatesDatabase.MargeSimpson);
	}

	@Test
	public void testSimpleGet() {
		VotingCard vote = votingCache.getVote("007");
		Assert.assertNotNull(vote);
		Assert.assertEquals("Marge Simpson", vote.voteForGovernor.name);
	}

}
