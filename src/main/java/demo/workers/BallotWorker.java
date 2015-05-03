package demo.workers;

import demo.Candidate;
import demo.GovernorCandidate;
import demo.SenatorCandidate;
import demo.service.CandidatesDatabase;
import demo.service.VotingCacheManagement;
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
 * @author - @navssurtani
 */
public class BallotWorker {

   private static final Logger logger = Logger.getLogger(BallotWorker.class);
   private ObjectMapper mapper;
   private VotingCacheManagement vcm;

   public BallotWorker() {
      // Instantiate fields.
      mapper = new ObjectMapper();
   }


   public void work(String data) throws Exception {
      // Parse the JSON String into a Map<String, Object>
      Map<String, Object> parsed = mapper.readValue(data, Map.class);
      logger.debug("Parsed String into Map using JSON parser");

      // Now we need to put the data into the grid.
      //TODO: Change region selection to "pollingStation"
      // Get the name of the voter.
      String voterName = (String) parsed.get("name");
      int voterAge = Integer.valueOf((String) parsed.get("age"));
      String governorVoteName = (String) parsed.get("governorVote");
      String senatorVoteName = (String) parsed.get("senatorVote");

      GovernorCandidate gov = (GovernorCandidate)
            getCandidateFromName(governorVoteName);
      SenatorCandidate sen = (SenatorCandidate)
            getCandidateFromName(senatorVoteName);

      String idCardNumber = generateCardNumber(voterName);
      // Last of all, put it through the vcm
      vcm.storeVote(idCardNumber, voterAge, voterName, sen, gov);

   }

   private String generateCardNumber(String voterName) {
      // Use the first two letters of the string (upper-case) and a randomly
      // generated number between 0-10k
      long random = Math.round(Math.random() * 10000);
      String stringRep = String.format("%05d", random);
      String prefix = voterName.substring(0, 2);
      return prefix.concat(stringRep);
   }

   private Candidate getCandidateFromName(String governorVoteName) {
      Candidate toReturn;

      switch (governorVoteName) {
         case "Marge Simpson":
            toReturn = CandidatesDatabase.MargeSimpson;
            break;
         case "Stewie Griffin":
            toReturn = CandidatesDatabase.StewieGriffin;
            break;
         case "Kenny McCormick":
            toReturn = CandidatesDatabase.KennyMcCormick;
            break;
         case "Hayley Smith":
            toReturn = CandidatesDatabase.HayleySmith;
            break;
         case "Homer Simpson":
            toReturn = CandidatesDatabase.HomerSimpson;
            break;
         case "Peter Griffin":
            toReturn = CandidatesDatabase.PeterGriffin;
            break;
         case "Eric Cartman":
            toReturn = CandidatesDatabase.EricCartman;
            break;
         case "Stan Smith":
            toReturn = CandidatesDatabase.StanSmith;
            break;

         default:
            throw new IllegalArgumentException("Wrong string parameter passed");
      }

      return toReturn;
   }




}
