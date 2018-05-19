package P3;

import static org.junit.Assert.*;
import org.junit.Test;
import P3.FriendshipGraph;
import P3.Person;

public class FriendshipGraphTest {
	FriendshipGraph graph = new FriendshipGraph();
	@Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false;
    }
	
	@Test
	public void test() {
		
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		//Person ross = new Person("Rachel");
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

		assertEquals(1, graph.getDistance(rachel, ross));
		// should print 1

		assertEquals(2, graph.getDistance(rachel, ben));
		// should print 2

		assertEquals(0, graph.getDistance(rachel, rachel));
		// should print 0

		assertEquals(-1, graph.getDistance(rachel, kramer));
		// should print ©\1
	}

}