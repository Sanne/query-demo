package demo.workers;

/**
 * Class that gets called by the service endpoints when they receive messages.
 * This will then perform the work of creating beans and putting them into
 * the data grid.
 *
 * @author - @navssurtani
 */
public class BallotWorker {

   /**
    * The data that comes from the WS endpoint.
    */
   private final String data;

   public BallotWorker(String data) {
      this.data = data;
   }

   public void work() throws Exception {

      // Parse the string and build the beans from there.

      // Put the data into the grid.

   }



}
