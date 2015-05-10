import demo.workers.RegionResultWorker;
import org.junit.Test;

import java.io.IOException;

public class RegionResultWorkerTest {


   @Test
   public void testCorrect() {
      String message = "{\"electionType\":\"governorVote\", " +
            "\"pollingStation\":1}";
      RegionResultWorker worker = new RegionResultWorker();
      try {
         worker.results(message);
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}
