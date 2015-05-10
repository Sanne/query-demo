package demo.service;

import demo.workers.CandidateSearchWorker;
import org.jboss.logging.Logger;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/candidateSearch")
public class CandidateSearchService {

   private static final Logger logger = Logger.getLogger
         (CandidateSearchService.class);

   @OnMessage
   public void onMessage(String message, Session session) {
      // The message here is a simple search string.
      CandidateSearchWorker searchWorker = null;
      try {
         searchWorker = new CandidateSearchWorker();

         // A String of the candidates to return in JSON format.
         String candidates = searchWorker.work(message);
         session.getAsyncRemote().sendText(candidates);
      } catch (InstantiationError e) {
         logger.error("Error instantiating CandidateCacheDao", e);
         session.getAsyncRemote().sendText("Error instantiating " +
               "CandidateCacheDao. Check server logs.");
      }
   }

}
