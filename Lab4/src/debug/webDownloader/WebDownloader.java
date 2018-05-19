package debug.webDownloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;

public abstract class WebDownloader {

	// String containing website url
	String website;

	// File Extension
	String fileExtension;

	// Folder on Computer where files will be downloaded to
	String destination;

	// File path on the server for the location of the file you want to download
	String fileRoot;
	URL url;

	// List of files on the current URL of this object
	ArrayList<String> files;

	ReadableByteChannel out;

	// String to store the entire html code of the website currently being
	// scripted
	String html;

	public void init() {
		try {
			url = new URL(website);
			files = new ArrayList<>();
			readHtml();
			findFiles();

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// Setter Methods
	public void setFileDestination(String d) {
		destination = d;
	}

	public void setFileRoot(String r) {
		fileRoot = r;
	}

	// Method for displaying entire HTML code of webpage in the console
	public void printHtml() {
		System.out.println(html);
	}

	public void printFiles() {
		for (String f : files)
			System.out.println(f);

		System.out.println("total files found: " + (files.size() - 1));
	}

	// Populates the list of files by traversing the HTML and searching for
	// correct pattern according to the file extension
	public void findFiles() {
		int startIndex = 0;
		int endIndex = 0;

		for (int i = 0; i < html.length(); i++) {

			// get the required indices for the beggining and end of the
			// substring that
			// references the url of the file
			if (i + 4 < html.length()) {
				if (html.substring(i, i + 4).equals("href")) {
					startIndex = i + 6;
				} 
				
				//Pattern matching check for file extension
				else if (html.substring(i, i + fileExtension.length()).equals(fileExtension)) {
					endIndex = i + 3;
				}

				// when the location of the substring has been found begin file
				// transfer
				if (startIndex != 0 && endIndex != 0 && startIndex < endIndex) {

					// print the fileExtension/indices
					// System.out.println(html.substring(startIndex, endIndex));
					// System.out.println(startIndex + ", " + endIndex);

					// get the substring of the array corresponding to the
					// file's url
					files.add(html.substring(startIndex, endIndex));
					startIndex = 0;
					endIndex = 0;

				}
			}
		}
	}

	// Method to read all the html of the current URL to a string
	public void readHtml() {
		// Read all the text returned by the server
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(url.openStream()));

			html = "";
			while ((html.contains("</html>")) == false) {
				if (html.equals(null))
					continue;
				else
					// str is one line of text; readLine() strips the newline
					// character(s)
					html = html + in.readLine().toString() + "\n";
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Method to download all the files stored in the files list to destination
	public void downloadFiles() {
		//Time record to measure execution time
		long t1 = System.currentTimeMillis();
		
		//The channel read the bytes target file to be downloaded
		ReadableByteChannel target;
		
		//Output stream to write the file to be downloaded to destination 
		FileOutputStream loot;
		try {
			for (String f : files) {
				target = Channels.newChannel(new URL(fileRoot + f).openStream());
				loot = new FileOutputStream(new File(destination + f + ".pdf"));
				loot.getChannel().transferFrom(target, 0, Integer.MAX_VALUE);
				loot.close();
			}
			
			System.out.println("Execution time = "  + (System.currentTimeMillis() - t1)/100 + " seconds");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
