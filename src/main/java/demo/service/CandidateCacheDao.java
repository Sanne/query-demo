package demo.service;

import java.util.List;

import org.apache.lucene.search.Query;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.infinispan.Cache;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.Search;
import org.infinispan.query.SearchManager;

import demo.Candidate;

public class CandidateCacheDao {

	private final Cache<String, Candidate> cache;
	private final SearchManager searchManager;
	private QueryBuilder candidatesQueryBuilder;

	public CandidateCacheDao(Cache<String, Candidate> cache) {
		this.cache = cache;
		this.searchManager = Search.getSearchManager(cache);
		this.candidatesQueryBuilder = searchManager
				.buildQueryBuilderForClass(Candidate.class)
				.get();
	}

	public void prefill() {
		cache.clear();
		cache.putAll(CandidatesDatabase.allcandidates);
	}

	public boolean isEmpty() {
		return cache.isEmpty();
	}

	public List<?> findCandidatedByAny(String userinput) {
		Query luceneQuery = candidatesQueryBuilder
				.keyword()
				.onField("name")
				.andField("party")
				.matching(userinput)
					.createQuery();

		CacheQuery cacheQuery = searchManager.getQuery(luceneQuery, Candidate.class);
		return cacheQuery.list();
	}

	public List<?> findAllCandidates() {
		Query luceneQuery = candidatesQueryBuilder
			.all()
				.createQuery();

		CacheQuery cacheQuery = searchManager.getQuery(luceneQuery, Candidate.class);
		return cacheQuery.list();
	}

}
