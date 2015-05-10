package demo.workers;

import demo.Candidate;
import demo.VotingCacheManager;
import demo.service.CandidateCacheDao;
import org.infinispan.Cache;
import org.jboss.logging.Logger;

import java.util.List;

public class CandidateSearchWorker {

   private CandidateCacheDao ccd;
   private static final Logger logger = Logger.getLogger
         (CandidateSearchWorker.class);

   public CandidateSearchWorker() throws InstantiationError {
      // Instantiate fields.
      Cache<String, Candidate> c = VotingCacheManager
            .getInstance().getCandidateCache();

      this.ccd = new CandidateCacheDao(c);
      ccd.prefill();
      if (ccd.isEmpty()) {
         throw new InstantiationError("Could not instantiate " +
               "CandidateCacheDao properly. The cache is still empty.");
      }
   }

   public String work(String message) {
      // Run the query on the ccd.
      List cResults = ccd.findCandidatedByAny(message);

      StringBuilder builder = new StringBuilder();
      builder.append("{\"candidateNames\": [");

      for (Object o : cResults) {
         // First cast it as a candidate.
         Candidate c = null;
         if (o instanceof Candidate) {
            c = (Candidate) o;
         } else {
            logger.error("Could not cast Object of type " + o.getClass() +
                  "to a Candidate.");
         }
         builder.append("\"");
         builder.append(c.name);
         builder.append("\", ");
      }
      builder.deleteCharAt(builder.length() - 2);
      builder.append("]}");

      return builder.toString();
   }
}



