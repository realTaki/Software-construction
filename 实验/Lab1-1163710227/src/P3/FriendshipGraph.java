package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FriendshipGraph {

	Map<Person, ArrayList<Person>> SocialNetwork = new HashMap<Person, ArrayList<Person>>();

	public void addVertex(Person person) {
		for (Person key : this.SocialNetwork.keySet()) {
			if (key.name == person.name) {
				System.out.println("Each person has a unique name!");
				return;
			}
		}
		this.SocialNetwork.put(person, new ArrayList<Person>());
	}

	public static void main(String args[]) {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);

		System.out.println(graph.getDistance(rachel, ross) + ":1");
		// should print 1

		System.out.println(graph.getDistance(rachel, ben) + ":2");
		// should print 2

		System.out.println(graph.getDistance(rachel, rachel) + ":0");
		// should print 0

		System.out.println(graph.getDistance(rachel, kramer) + ":-1");
		// should print ©\1
	}

	public void addEdge(Person personA, Person personB) {

		if (this.SocialNetwork.containsKey(personA) && this.SocialNetwork.containsKey(personB)) {
			ArrayList<Person> temp;

			temp = this.SocialNetwork.get(personA);
			temp.add(personB);

		} else
			System.out.println("At least one person has not been add to the SocialNetwork!");

	}

	public int getDistance(Person personA, Person personB) {
		int distance = -1;
		if (this.SocialNetwork.containsKey(personA) && this.SocialNetwork.containsKey(personB)) {
			ArrayList<Person> relationships = getShortestRelationships(personA, personB);

			if (relationships != null)
				distance = relationships.size() - 1;
		}
		return distance;
	}

	public ArrayList<Person> getShortestRelationships(Person personA, Person personB) {
		// maintain a queue of relationships
		ArrayList<ArrayList<Person>> queue = new ArrayList<ArrayList<Person>>();
		ArrayList<Person> relationships = new ArrayList<Person>();
		Person aPerson;
		int mapSize = SocialNetwork.size();

		relationships.add(personA);
		queue.add(relationships);

		while (!queue.isEmpty()) {

			// get the first relationships from the queue
			relationships = queue.get(0);
			// distance will never beyond the map-size
			if (relationships.size() > mapSize)
				return null;
			queue.remove(0);

			// get the last person from the relationships
			aPerson = relationships.get(relationships.size() - 1);

			// relationships found
			if (aPerson == personB)
				return relationships;

			// enumerate all adjacent person, construct a new relationships and push it into
			// the queue
			for (Iterator<Person> iter = this.SocialNetwork.get(aPerson).iterator(); iter.hasNext();) {
				ArrayList<Person> Expand_relationships = new ArrayList<Person>(relationships);
				Person temp = iter.next();

				Expand_relationships.add(temp);
				queue.add(Expand_relationships);
			}
		}
		return null;

	}

}
