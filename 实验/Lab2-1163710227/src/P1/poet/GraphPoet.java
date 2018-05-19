/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>
 * GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph. Vertices in the graph are words. Words are defined as
 * non-empty case-insensitive strings of non-space non-newline characters. They
 * are delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     Hello, HELLO, hello, goodbye!
 * </pre>
 * <p>
 * the graph would contain two edges:
 * <ul>
 * <li>("hello,") -> ("hello,") with weight 2
 * <li>("hello,") -> ("goodbye!") with weight 1
 * </ul>
 * <p>
 * where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>
 * Given an input string, GraphPoet generates a poem by attempting to insert a
 * bridge word between every adjacent pair of words in the input. The bridge
 * word between input words "w1" and "w2" will be some "b" such that w1 -> b ->
 * w2 is a two-edge-long path with maximum-weight weight among all the
 * two-edge-long paths from w1 to w2 in the affinity graph. If there are no such
 * paths, no bridge word is inserted. In the output poem, input words retain
 * their original case, while bridge words are lower case. The whitespace
 * between every word in the poem is a single space.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     This is a test of the Mugar Omni Theater sound system.
 * </pre>
 * <p>
 * on this input:
 * 
 * <pre>
 *     Test the system.
 * </pre>
 * <p>
 * the output poem would be:
 * 
 * <pre>
 *     Test of the system.
 * </pre>
 * 
 * <p>
 * PS2 instructions: this is a required ADT class, and you MUST NOT weaken the
 * required specifications. However, you MAY strengthen the specifications and
 * you MAY add additional methods. You MUST use Graph in your rep, but otherwise
 * the implementation of this class is up to you.
 */
public class GraphPoet {

	private final Graph<String> graph = Graph.empty();

	// Abstraction function:
	// TODO
	// Representation invariant:
	// TODO
	// Safety from rep exposure:
	// TODO

	/**
	 * Create a new poet with the graph from corpus (as described above).
	 * 
	 * @param corpus
	 *            text file from which to derive the poet's affinity graph
	 * @throws IOException
	 *             if the corpus file cannot be found or read
	 */
	public GraphPoet(File corpus) throws IOException {
		BufferedReader corpusReader;

		String currentTextLine;
		corpusReader = new BufferedReader(new FileReader(corpus));
		String previousWord = "";

		Set<String> spaceSet = new HashSet<>(Arrays.asList(" "));
		Set<String> emptyStringSet = new HashSet<>(Arrays.asList(""));

		boolean atFirstLine = true;

		while ((currentTextLine = corpusReader.readLine()) != null) {

			List<String> listOfStringSegments = new ArrayList<>(
					Arrays.asList((currentTextLine.replaceAll("\\s+", " ")).split(" ")));

			listOfStringSegments.removeAll(spaceSet);
			listOfStringSegments.removeAll(emptyStringSet);

			for (String word : listOfStringSegments) {

				String wordLowercase = word.toLowerCase();

				boolean wordInAffinityGraph = this.graph.vertices().contains(wordLowercase);

				boolean previousEqualsCurrentWord = previousWord.equals(wordLowercase);

				boolean currentWordInPreviousWordTargets = this.graph.targets(previousWord).containsKey(wordLowercase);

				if (!wordInAffinityGraph) {

					this.graph.add(wordLowercase);

					if (!atFirstLine && listOfStringSegments.indexOf(wordLowercase) == 0) {
						this.graph.set(previousWord, wordLowercase, 1);
					} else if (listOfStringSegments.indexOf(wordLowercase) != 0) {
						this.graph.set(previousWord, wordLowercase, 1);
					}
				} else if (wordInAffinityGraph && previousEqualsCurrentWord && currentWordInPreviousWordTargets) {

					int currentEdgeWeight = this.graph.targets(previousWord).get(wordLowercase);
					this.graph.set(previousWord, wordLowercase, currentEdgeWeight + 1);
				} else {
					this.graph.set(previousWord, wordLowercase, 1);
				}
				previousWord = wordLowercase;
			}
			atFirstLine = false;
		}
		corpusReader.close();
		checkRep();
	}

	private void checkRep() {

		Set<String> setOfVertexKeys = new HashSet<>();

		for (String vertex : this.graph.vertices()) {
			for (int i = 0; i < vertex.length(); i++) {
				Character vertexChar = vertex.charAt(i);
				if (Character.isLetter(vertexChar)) {
					assert Character.isLowerCase(vertexChar);
				}
			}

			Map<String, Integer> sourceMap = this.graph.sources(vertex);

			for (String key : sourceMap.keySet()) {
				assert (sourceMap.get(key) >= 1);
				setOfVertexKeys.add(key);
			}

			Map<String, Integer> targetMap = this.graph.targets(vertex);

			for (String key : targetMap.keySet()) {
				assert (targetMap.get(key) >= 1);
				setOfVertexKeys.add(key);
			}
		}

		int numVertices = this.graph.vertices().size();
		int numVertexKeys = setOfVertexKeys.size();
		assert (numVertices == numVertexKeys);
	}

	/**
	 * Generate a poem.
	 * 
	 * @param input
	 *            string from which to create the poem
	 * @return poem (as described above)
	 */
	public String poem(String input) {
		String outputPoem = "";
		List<String> listOfStringSegments = new ArrayList<>(Arrays.asList((input.replaceAll("\\s+", " ")).split(" ")));

		if (input == "") {
			return outputPoem;
		}
		String previousWord = "";
		for (String currentWord : listOfStringSegments) {
			boolean addedBridgeWord = false;
			if (listOfStringSegments.indexOf(currentWord) == 0) {
				outputPoem += currentWord;
			}
			else {
				String previousWordLowercase = previousWord.toLowerCase();
				String currentWordLowercase = currentWord.toLowerCase();

				boolean previousWordInGraph = this.graph.vertices().contains(previousWordLowercase);
				boolean currentWordInGraph = this.graph.vertices().contains(currentWordLowercase);
				Set<String> targetsOfPreviousWord = graph.targets(previousWordLowercase).keySet();
				Set<String> sourcesOfCurrentWord = graph.sources(currentWordLowercase).keySet();

				for (String bridgeWord : targetsOfPreviousWord) {

					boolean foundBridgeWord = sourcesOfCurrentWord.contains(bridgeWord);
					boolean bridgeWordNotEmptyString = !(bridgeWord.equals(""));

					if (previousWordInGraph && currentWordInGraph && foundBridgeWord && bridgeWordNotEmptyString) {
						outputPoem += " ";
						outputPoem += bridgeWord;
						outputPoem += " ";
						outputPoem += currentWord;

						addedBridgeWord = true;
						break;
					}
				}
				if (!addedBridgeWord) {
					outputPoem += " ";
					outputPoem += currentWord;
				}
			}
			previousWord = currentWord;
		}
		return outputPoem;
	}

	public String toString() {
        return graph.toString();
    }

}
