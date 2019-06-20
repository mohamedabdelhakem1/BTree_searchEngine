package eg.edu.alexu.csd.filestructure.btree;

import java.util.HashSet;
import java.util.Random;
import java.util.TreeSet;

import org.junit.Assert;


public abstract class test {

	public static void main(String[] args) {
		BTree<Integer, String> btree = new BTree<>(3);
		try {
			Random r = new Random();
			TreeSet<Integer> set = new TreeSet<>();
			HashSet<Integer> deleteSet = new HashSet<>();
			for (int i = 0; i < 100000; i++) {
				int key = r.nextInt(100000);
				btree.insert(key, "Soso" + key);
				set.add(key);
				if (r.nextInt(5) % 4 == 0)
					deleteSet.add(key);
			}

			for (Integer i : deleteSet) {

				Assert.assertTrue(btree.delete(i));
				Assert.assertNull(btree.search(i));
			}

		} catch (Throwable e) {
			TestRunner.fail("Fail to search in tree", e);
		}

	}
}
