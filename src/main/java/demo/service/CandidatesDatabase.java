package demo.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import demo.Candidate;
import demo.GovernorCandidate;
import demo.SenatorCandidate;

public class CandidatesDatabase {

	public final static SenatorCandidate HomerSimpson = new SenatorCandidate(
			"Homer Simpson", "The Simpsons");
	public final static SenatorCandidate PeterGriffin = new SenatorCandidate(
			"Peter Griffin", "Family Guy");
	public final static SenatorCandidate EricCartman = new SenatorCandidate(
			"Eric Cartman", "South Park");
	public final static SenatorCandidate StanSmith = new SenatorCandidate(
			"Stan Smith", "American Dad");

	public final static GovernorCandidate MargeSimpson = new GovernorCandidate(
			"Marge Simpson", "The Simpsons");
	public final static GovernorCandidate StewieGriffin = new GovernorCandidate(
			"Stewie Griffin", "Family Guy");
	public final static GovernorCandidate KennyMcCormick = new GovernorCandidate(
			"Kenny McCormick", "South Park");
	public final static GovernorCandidate HayleySmith = new GovernorCandidate(
			"Hayley Smith", "American Dad");

	public final static Map<String, Candidate> allcandidates = initializeAllCandidates();

	private CandidatesDatabase() {
	}

	private static Map<String, Candidate> initializeAllCandidates() {
		HashMap<String, Candidate> map = new HashMap<>();
		map.put(HomerSimpson.name, HomerSimpson);
		map.put(PeterGriffin.name, PeterGriffin);
		map.put(EricCartman.name, EricCartman);
		map.put(StanSmith.name, StanSmith);
		map.put(MargeSimpson.name, MargeSimpson);
		map.put(StewieGriffin.name, StewieGriffin);
		map.put(KennyMcCormick.name, KennyMcCormick);
		map.put(HayleySmith.name, HayleySmith);
		return Collections.unmodifiableMap(map);
	}

}
