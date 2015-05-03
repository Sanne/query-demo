package demo.workers;

import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.logging.Logger;

import java.util.Map;

/**
 * Class that gets called by the service endpoints when they receive messages.
 * This will then perform the work of creating beans and putting them into
 * the data grid.
 *
 * JSON is of the form:
 *
 * {
 *    "name":"voterName",
 *    "age":"voterAge",
 *    "governorVote":"governorVoteName",
 *    "senatorVote":"senatorVoteName",
 *    "regionSelection":1
 *    }
 *
 * @author - @navssurtani
 */
public class BallotWorker {

   private static final Logger logger = Logger.getLogger(BallotWorker.class);
   private ObjectMapper mapper;


   public BallotWorker() {
      // Instantiate fields.
      mapper = new ObjectMapper();
   }


   public void work(String data) throws Exception {
      // Parse the JSON String into a Map<String, Object>
      Map<String, Object> parsed = mapper.readValue(data, Map.class);
      logger.log(Logger.Level.DEBUG, "Parsed String into Map using JSON " +
            "parser");

      // Now we need to put the data into the grid.

   }



}
