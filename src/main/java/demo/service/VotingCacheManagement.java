package demo.service;

import java.util.Date;
import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.facet.Facet;
import org.hibernate.search.query.facet.FacetSortOrder;
import org.hibernate.search.query.facet.FacetingRequest;
import org.infinispan.Cache;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.Search;
import org.infinispan.query.SearchManager;

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

	public void clear() {
		cache.clear();
	}

	public List<Facet> countVotesPerGovernor() {
		SearchManager searchManager = Search.getSearchManager( cache );
		QueryBuilder queryBuilder = searchManager.buildQueryBuilderForClass(VotingCard.class).get();

		//Two things are going to happen here:
		// - a full-text query on the data grid
		// - a faceting request to organize results in scored groups

		FacetingRequest facetingRequest = queryBuilder.facet()
			.name( "Candidate Names" )
			.onField( "governorVote.name" )
			.discrete()
			.orderedBy( FacetSortOrder.COUNT_DESC )
			.createFacetingRequest();

		Query luceneQuery = queryBuilder.all().createQuery();

		CacheQuery infinispanQuery = searchManager.getQuery( luceneQuery, VotingCard.class );
		infinispanQuery.getFacetManager().enableFaceting(facetingRequest);

		infinispanQuery.list(); // Perform the Query

		// Get the Faceted results as we asked for
		return infinispanQuery
				.getFacetManager()
				.getFacets( "Candidate Names" );
	}

}
