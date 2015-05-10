package demo.workers;

import demo.VotingCacheManager;
import demo.VotingCard;
import demo.service.VotingCacheDao;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.TermFilter;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.search.query.facet.Facet;
import org.infinispan.Cache;
import org.jboss.logging.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RegionResultWorker extends AbstractResultWorker {

   private VotingCacheDao votingCache;
   private ObjectMapper mapper;
   private static final Logger logger = Logger
         .getLogger(RegionResultWorker.class);

   public RegionResultWorker() {
      // Instantiate fields.
      Cache<String, VotingCard> c = VotingCacheManager
            .getInstance().getVotingCache();
      this.votingCache = new VotingCacheDao(c);
      this.mapper = new ObjectMapper();
   }

   public String results(String message) throws IOException,
         IllegalArgumentException {
      // We have to parse the message to find out the type of election that
      // we need to obtain the results from as well as the Filters that we
      // wish to create based off of the region (or polling station).
      logger.info("Received message: " + message);
      Map<String, Object> parsed = mapper.readValue(message, Map.class);

      String electionType = (String) parsed.get("electionType");
      // A bit of work to be done for the station.
      Object stationObj = parsed.get("pollingStation");
      String station = null;
      if (stationObj instanceof Integer) {
         Integer stationInt = (Integer) stationObj;
         station = Integer.toString(stationInt);
      }

      // Should be either "senateVote" or "governorVote". VALIDATE!!
      if (!electionType.equals("senateVote") && !electionType.equals
            ("governorVote")) {
         throw new IllegalArgumentException("You have passed an illegal " +
               "argument of the type of election you wish to see the results " +
               "for.");
      }
      logger.info("Obtaining result for election type: " + electionType);


      // Now create the filter from the station that we have.
      Term stationTerm = new Term("votingStation", station);
      TermFilter stationFilter = new TermFilter(stationTerm);

      List<Facet> facets = votingCache.countVotes(electionType, stationFilter);
      return stringResultsFromFacets(facets, electionType);
   }

}
