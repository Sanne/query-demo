package demo.workers;

import demo.VotingCacheManager;
import demo.VotingCard;
import demo.service.VotingCacheDao;
import org.hibernate.search.query.facet.Facet;
import org.infinispan.Cache;
import org.jboss.logging.Logger;

import java.util.List;

public class ElectionResultWorker extends AbstractResultWorker {

   private static final Logger logger = Logger
         .getLogger(ElectionResultWorker.class);
   private VotingCacheDao votingCache;

   public ElectionResultWorker() {
      // Instantiate fields.
      Cache<String, VotingCard> c = VotingCacheManager
            .getInstance().getVotingCache();
      votingCache = new VotingCacheDao(c);

   }

   public String result(String electionType) throws IllegalArgumentException {
      // Should be either "senateVote" or "governorVote". VALIDATE!!
      if (!electionType.equals("senateVote") && !electionType.equals
            ("governorVote")) {
         throw new IllegalArgumentException("You have passed an illegal " +
               "argument of the type of election you wish to see the results " +
               "for.");
      }
      logger.info("Obtaining result for election type: " + electionType);
      List<Facet> facets = votingCache.countVotes(electionType);
      return stringResultsFromFacets(facets, electionType);
   }

}
