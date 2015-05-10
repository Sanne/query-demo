package demo.service;

import demo.workers.ResultWorker;
import org.jboss.logging.Logger;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/electionResult")
public class ElectionResultService {

   private static Logger logger = Logger
         .getLogger(ElectionResultService.class);

   @OnMessage
   public void onMessage(String message, Session session) {
      // The message here is just the name of the election type.
      // The choices should be either 'governor' or 'senate'
      ResultWorker worker = new ResultWorker();
      String result;
      try {
         result = worker.result(message);
         // Send that result back to the front-end.
         session.getAsyncRemote().sendText(result);
      } catch (IllegalArgumentException e) {
         // Send an error message back to the front-end.
         session.getAsyncRemote().sendText("Incorrect message format!");
         logger.error("Illegal argument from front-end JS.", e);
      }
   }

}
