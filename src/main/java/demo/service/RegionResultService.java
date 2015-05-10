package demo.service;

import demo.workers.RegionResultWorker;
import org.jboss.logging.Logger;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/regionResult")
public class RegionResultService {

   private static final Logger logger = Logger
         .getLogger(RegionResultService.class);

   @OnMessage
   public void onMessage(String message, Session session) {
      // The message that we have is in JSON format containing the election
      // type as well as the region that we wish to filter by.

      RegionResultWorker worker = new RegionResultWorker();
      String results = null;
      try {
         results = worker.results(message);
      } catch (IOException e) {
         logger.error("IOException while trying to parse JSON", e);
         session.getAsyncRemote().sendText("Error parsing JSON!");
      }
      session.getAsyncRemote().sendText(results);

   }

}
