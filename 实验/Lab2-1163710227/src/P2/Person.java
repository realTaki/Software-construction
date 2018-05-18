package P2;

import java.util.HashMap;
import java.util.Map;

public class Person  {
	private int age;
	private String gender;
	private String Name;
	private Map<Person,Integer > friends = new HashMap<>();
	
	public Person(String name,String gender,int age) {
		this.age = age;
		this.gender = gender;
		this.Name =name;
	}


	public String getSex() {
		return this.gender;
	}

	public boolean addFriend(Person person,int weight) {
		boolean flag = false;
		if(!this.friends.containsKey(person)) {
			this.friends.put(person, weight);
			flag = true;
		}

		return flag;
	}

	public boolean removeFriend(Person person) {
		boolean flag = false;
		if(this.friends.containsKey(person)) {
			this.friends.remove(person);
			flag = true;
		}

		return flag;
	}
	public Map<Person, Integer> getFriends() {
		
		return this.friends;
	}
	
	public int getAge() {
		return this.age;
	}


	
	public String toString(){
		return Name;
	}
	
	public boolean equals(Object v){
		return this.Name.equals(v.toString());
	}
	
	public int hashCode(){
		return this.Name.hashCode();
	}

}
