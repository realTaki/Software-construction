package debug.webDownloader;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		WebDownloader downloader = null;

		int choice = 0;
		String fileURLSource = "https://people.ok.ubc.ca/rlawrenc/teaching/304/Notes/index.html";
		String fileURLRoot = "https://people.ok.ubc.ca/rlawrenc/teaching/304/Notes/";
		String destination;

		Scanner in = new Scanner(System.in);

		System.out.print("Enter your desired file destination: ");
		destination = in.nextLine();

		if (destination.length() != 0) {
			if (destination.charAt(destination.length() - 1) != '/') {
				destination = destination + "/";
			}
		}

		while (choice != 1 || choice != 2 || choice != 3) {

			System.out.println("Choose a file type to download: \n1).pdf\n2).mp3\n3).txt\nChoice: ");
			choice = in.nextInt();

			switch (choice) {

			case 1:
				downloader = new WebDownloader(fileURLSource, destination, fileURLRoot);
				downloader.init();
				System.out.println("test");
				break;

			case 2:
				downloader = new WebDownloader(fileURLSource, destination, fileURLRoot);
				downloader.init();
				break;

			case 3:
				downloader = new WebDownloader(fileURLSource, destination, fileURLRoot);
				downloader.init();
				break;

			default:
				System.out.println("Invalid input, try again...");
				break;
			}
		}

		choice = 0;

		while (choice != 1 || choice != 2) {
			System.out.println("Print files before downloading?\n1) Yes\n2) No");

			try {
				choice = in.nextInt();
			} catch (Exception e) {
				choice = 0;
			}

			switch (choice) {

			case 1:
				break;

			case 2:
				downloader.printFiles();
				break;

			default:
				System.out.println("Invalid input, try again...");
				break;
			}
		}

		choice = 0;

		while (choice != 1 || choice != 2) {
			System.out.println("Download files?\n1) Yes\n2) No");

			try {
				choice = in.nextInt();
			} catch (Exception e) {
				choice = 0;
			}

			switch (choice) {

			case 1:
				break;

			case 2:
				downloader.downloadFiles();
				break;

			default:
				System.out.println("Invalid input, try again...");
				break;
			}
		}

		if (choice == 1) {
			System.out.println(downloader.files.size() + " file(s) downloaded");
		} else {
			System.out.println("Download not executed.");
		}

		System.out.println("Program exiting, goodbye!");

		try {
			if (downloader != null)
				if (downloader.out != null)
					downloader.out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

}
