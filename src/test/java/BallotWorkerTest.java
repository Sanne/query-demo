import demo.workers.BallotWorker;
import org.infinispan.Cache;
import org.infinispan.manager.DefaultCacheManager;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * @author - @navssurtani
 */
public class BallotWorkerTest {

   @BeforeClass
   public static void startInfinispan() throws IOException {
      DefaultCacheManager defaultCacheManager =
            new DefaultCacheManager("localonly-infinispan.xml");
      Cache testCache = defaultCacheManager.getCache("testCache", true);


   }

   @Test
   public void testCorrectJson(){
      String correct = "{\"name\":\"Another\",\"age\":\"75\"," +
            "\"governorVote\":\"Stewie Griffin\"," +
            "\"senatorVote\":\"Homer Simpson\"," +
            "\"regionSelection\":2}";
      BallotWorker ballotWorker = new BallotWorker();

      try {
         ballotWorker.work(correct);
      } catch (Exception e) {
         e.printStackTrace();
         Assert.fail("There shouldn't be an exception here. The worker should " +
               "run smoothly");
      }
   }


}
