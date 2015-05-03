package demo.workers;

import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;

import java.util.Map;

/**
 * Class that gets called by the service endpoints when they receive messages.
 * This will then perform the work of creating beans and putting them into
 * the data grid.
 *
 * @author - @navssurtani
 */
public class BallotWorker {

   public void work(String data) throws Exception {

      // Parse the string (json) and build the beans from there.
      JsonParserFactory factory = JsonParserFactory.getInstance();
      JSONParser parser = factory.newJsonParser();

      Map jsonMap = parser.parseJson(data);

      // Put the data into the grid.

   }



}
