package demo.service;

import demo.GovernorCandidate;
import demo.SenatorCandidate;

public class CandidatesDatabase {

	public final static SenatorCandidate HomerSimpson = new SenatorCandidate("Homer Simpson", "No More work!", "North");
	public final static SenatorCandidate PeterGriffin = new SenatorCandidate("Peter Griffin", "No More work!", "North");
	public final static SenatorCandidate EricCartman = new SenatorCandidate("Eric Cartman", "No More work!", "North");
	public final static SenatorCandidate StanSmith = new SenatorCandidate("Stan Smith", "No More work!", "North");

	public final static GovernorCandidate MargeSimpson = new GovernorCandidate("Marge Simpson", "High Ceilings", "North");
	public final static GovernorCandidate StewieGriffin = new GovernorCandidate("Stewie Griffin", "Low Ceilings", "North");
	public final static GovernorCandidate KennyMcCormick = new GovernorCandidate("Kenny McCormick", "Low Ceilings", "North");
	public final static GovernorCandidate HayleySmith  = new GovernorCandidate("Hayley Smith ", "Low Ceilings", "North");

	private CandidatesDatabase() {
	}

}
