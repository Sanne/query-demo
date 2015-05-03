package demo.service;

import demo.workers.BallotWorker;
import org.jboss.logging.Logger;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @author - @navssurtani
 */

@ServerEndpoint("/basicVote")
public class BasicVoteService {

   private static final Logger logger = Logger.
         getLogger(BasicVoteService.class);

   @OnOpen
   public void onOpen(Session session) throws IOException {
      session.getBasicRemote().sendText("Opened!");
   }

   @OnClose
   public void onClose(Session session) throws IOException {
      session.getBasicRemote().sendText("Closed!");
   }

   @OnMessage
   public void onMessage(String data, Session session) throws IOException {
      logger.log(Logger.Level.INFO, "Received String message: " + data);
      BallotWorker bw = new BallotWorker();
      try {
         bw.work(data);
      } catch (Exception e) {
      }

   }

}
