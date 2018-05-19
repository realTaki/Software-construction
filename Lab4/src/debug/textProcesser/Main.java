package debug.textProcesser;

import java.io.FileNotFoundException;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		SearchEngine google = new SearchEngine();
		google.processText("C:/Users/Spencer/Desktop/testText.txt");
		google.trie.display();
		
		System.out.println("\n");
		System.out.println(google.map.toString());
				
	}
}
