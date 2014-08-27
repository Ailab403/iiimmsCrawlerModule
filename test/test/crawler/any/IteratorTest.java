package test.crawler.any;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

public class IteratorTest {

	@Test
	public void testIterator() {
		Set<Integer> testSet = new HashSet<Integer>();

		for (int i = 0; i < 10; i++) {
			testSet.add(i);
			System.out.println(i);
		}

		Iterator<Integer> iterator = testSet.iterator();
		while (iterator.hasNext()) {
			if (iterator.hasNext()) {
				System.out.println(iterator.next());
			}
		}
	}
}
