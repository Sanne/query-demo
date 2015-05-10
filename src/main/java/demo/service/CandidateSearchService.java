package demo.service;

import javax.websocket.OnMessage;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/candidateSearch")
public class CandidateSearchService {

   @OnMessage
   public void onMessage(String message, Session)

}
