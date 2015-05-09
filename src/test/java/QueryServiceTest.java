import java.io.IOException;
import java.util.List;

import org.apache.lucene.index.Term;
import org.apache.lucene.queries.TermFilter;
import org.hibernate.search.query.facet.Facet;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import demo.GovernorCandidate;
import demo.VotingCard;
import demo.service.CandidatesDatabase;
import demo.service.VotingCacheManagement;

public class QueryServiceTest {

	private static DefaultCacheManager defaultCacheManager;
	private static VotingCacheManagement votingCache;

	@BeforeClass
	public static void startInfinispan() throws IOException {
		defaultCacheManager = new DefaultCacheManager( "localonly-infinispan.xml" );
		votingCache = new VotingCacheManagement(defaultCacheManager.getCache("Votes"));
	}

	@AfterClass
	public static void stopInfinispan() {
		if ( defaultCacheManager != null )
			defaultCacheManager.stop();
	}

	@Before
	public void clearData() {
		votingCache.clear();
	}

	@Test
	public void testSimpleGet() {
		{
			VotingCard vote = votingCache.getVote("007");
			Assert.assertNull(vote);
		}
		{
			votingCache.storeVote("007", 62, "James Bond", CandidatesDatabase
					.HomerSimpson, CandidatesDatabase.MargeSimpson, "1");
			VotingCard vote = votingCache.getVote("007");
			Assert.assertNotNull(vote);
			Assert.assertEquals("Marge Simpson", vote.governorVote.name);
		}
	}

	@Test
	public void testElectionWinnerGovernor() {
		//Let's store some votes for Marge and for Stewie
		int VOTES_MARGE = 17;
		int VOTES_STEWIE = 42;
		int VOTING_STATION_ID = 1;
		int voteId = 0;
		for (int i=0; i<VOTES_MARGE; i++) {
			voteForGovernor(CandidatesDatabase.MargeSimpson, voteId++, VOTING_STATION_ID);
		}
		for (int i=0; i<VOTES_STEWIE; i++) {
			voteForGovernor(CandidatesDatabase.StewieGriffin, voteId++, VOTING_STATION_ID);
		}
		List<Facet> facets = votingCache.countVotesForGovernor();

		// There are only votes for two candidates
		Assert.assertEquals(2, facets.size());

		// Top voted count is on top..
		Assert.assertEquals(VOTES_STEWIE, facets.get(0).getCount());
		// And the winner is...
		Assert.assertEquals(CandidatesDatabase.StewieGriffin.name, facets.get(0).getValue());

		// In second position we have:
		Assert.assertEquals(VOTES_MARGE, facets.get(1).getCount());
		Assert.assertEquals(CandidatesDatabase.MargeSimpson.name, facets.get(1).getValue());
	}

	@Test
	public void testVotesPerRegion() {
		//Let's store some votes for Marge and for Stewie
		int VOTES_MARGE_AT_PLACE_ONE = 17;
		int VOTES_STEWIE_AT_PLACE_ONE = 42;
		int voteId = 0;

		int VOTING_STATION_ID = 1;
		for (int i=0; i<VOTES_MARGE_AT_PLACE_ONE; i++) {
			voteForGovernor(CandidatesDatabase.MargeSimpson, voteId++, VOTING_STATION_ID);
		}
		for (int i=0; i<VOTES_STEWIE_AT_PLACE_ONE; i++) {
			voteForGovernor(CandidatesDatabase.StewieGriffin, voteId++, VOTING_STATION_ID);
		}

		VOTING_STATION_ID = 2;
		for (int i=0; i<10; i++) {
			voteForGovernor(CandidatesDatabase.MargeSimpson, voteId++, VOTING_STATION_ID);
		}
		for (int i=0; i<10; i++) {
			voteForGovernor(CandidatesDatabase.StewieGriffin, voteId++, VOTING_STATION_ID);
		}

		//Define a filter using Lucene's native API:
		Term votingStationOne = new Term("votingStation", "1");
		TermFilter filterOnStationOne = new TermFilter(votingStationOne);

		List<Facet> facets = votingCache.countVotesForGovernor(filterOnStationOne);

		// There are only votes for two candidates
		Assert.assertEquals(2, facets.size());

		// Top voted count is on top..
		Assert.assertEquals(VOTES_STEWIE_AT_PLACE_ONE, facets.get(0).getCount());
		// And the winner is...
		Assert.assertEquals(CandidatesDatabase.StewieGriffin.name, facets.get(0).getValue());

		// In second position we have:
		Assert.assertEquals(VOTES_MARGE_AT_PLACE_ONE, facets.get(1).getCount());
		Assert.assertEquals(CandidatesDatabase.MargeSimpson.name, facets.get(1).getValue());
	}

	private void voteForGovernor(GovernorCandidate governor, int i, int votingStationId) {
		votingCache.storeVote("00"+i, 20 + i, "User "+i, CandidatesDatabase
				.HomerSimpson, governor, String.valueOf(votingStationId) );
	}

}
