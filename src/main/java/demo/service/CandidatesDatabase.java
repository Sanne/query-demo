package demo.service;

import demo.GovernorCandidate;
import demo.SenatorCandidate;

public class CandidatesDatabase {

   public final static SenatorCandidate HomerSimpson = new SenatorCandidate("Homer Simpson", "The Simpsons");
   public final static SenatorCandidate PeterGriffin = new SenatorCandidate("Peter Griffin", "Family Guy");
   public final static SenatorCandidate EricCartman = new SenatorCandidate("Eric Cartman", "South Park");
   public final static SenatorCandidate StanSmith = new SenatorCandidate("Stan Smith", "American Dad");

   public final static GovernorCandidate MargeSimpson = new GovernorCandidate("Marge Simpson", "The Simpsons");
   public final static GovernorCandidate StewieGriffin = new GovernorCandidate("Stewie Griffin", "Family Guy");
   public final static GovernorCandidate KennyMcCormick = new GovernorCandidate("Kenny McCormick", "South Park");
   public final static GovernorCandidate HayleySmith = new GovernorCandidate("Hayley Smith ", "American Dad");

   private CandidatesDatabase() {
   }

}
