package P2;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import P1.graph.Graph;

public class Friendship implements Graph<Person> {
	private final Set<Person> vertices = new HashSet<>();

	
	@Override
	public int set(Person person1, Person person2, int weight) {
		person1.addFriend(person2, weight);
		return weight;

	}

	@Override
	public boolean add(Person person) {
		boolean flag = false;
		if(!this.vertices.contains(person)) {
			this.vertices.add(person);
		}

		return flag;
	}


	@Override
	public boolean remove(Person person) {
		boolean flag = false;
		if(this.vertices.contains(person)) {
			for(Person per:this.vertices) {
				per.removeFriend(person);//有这个人自动删除，没有不做改变
			}
			this.vertices.remove(person);
		}

		return flag;
	}

	@Override
	public Set<Person> vertices() {

		return this.vertices;
	}

	@Override
	public Map<Person, Integer> sources(Person target) {
		Map<Person, Integer> sources = new HashMap<>();
		for(Person per:this.vertices) {
			if(per.getFriends().containsKey(target)) {
				sources.put(per, per.getFriends().get(target));
			}
		}
		return sources;
	}

	@Override
	public Map<Person, Integer> targets(Person source) {
		
		return source.getFriends();
	}


}
