package demo.workers;

import demo.VotingCacheManager;
import demo.VotingCard;
import demo.service.VotingCacheDao;
import org.hibernate.search.query.facet.Facet;
import org.infinispan.Cache;
import org.jboss.logging.Logger;

import java.util.List;

public class ResultWorker {

   private static final Logger logger = Logger.getLogger(ResultWorker.class);
   private VotingCacheDao votingCache;

   public ResultWorker() {
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
      // We need to return a String of the form:

      StringBuilder builder = new StringBuilder();
      builder.append("{\"electionType\": \"");
      builder.append(electionType);
      builder.append("\", \"candidates\": [");

      for (Facet f : facets) {
         builder.append("{\"name\": \"");
         builder.append(f.getValue());
         builder.append("\", \"votes\": \"");
         builder.append(f.getCount());
         builder.append("\"}, ");
      }
      // Remove the trailing comma.
      builder.deleteCharAt(builder.length() - 2);
      builder.append("]}");

      return builder.toString();
   }

}
