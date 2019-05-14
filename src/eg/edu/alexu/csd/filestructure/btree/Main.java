package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

public class Main {

	public static void main(String[] args) {
		IBTree<Integer, String> btree = (IBTree<Integer, String>) TestRunner.getImplementationInstanceForInterface(IBTree.class, new Object[]{3});

		try {
			//List<Integer> inp = Arrays.asList(new Integer[]{1, 3, 7, 10, 11, 13, 14, 15, 18, 16, 19, 24, 25, 26, 21, 4, 5, 20, 22, 2, 17, 12, 6});
			for (int i = 0; i < 24; i++)
				btree.insert(i, "Soso" + i);
			IBTreeNode<Integer, String> root = btree.getRoot();
			List<List<List<?>>> keys = new ArrayList<>();
			traverseBtreePreOrder(root, 0, keys);
//			List<List<List<?>>> ans = new ArrayList<>();
//			List<List<?>> lvl0 = new ArrayList<>();
//			lvl0.add(new ArrayList<>(Arrays.asList(new Integer[]{16})));
//			List<List<?>> lvl1 = new ArrayList<>();
//			lvl1.add(new ArrayList<>(Arrays.asList(new Integer[]{3, 7, 13})));
//			lvl1.add(new ArrayList<>(Arrays.asList(new Integer[]{20, 24})));
//			List<List<?>> lvl2 = new ArrayList<>();
//			lvl2.add(new ArrayList<>(Arrays.asList(new Integer[]{1, 2})));
//			lvl2.add(new ArrayList<>(Arrays.asList(new Integer[]{4, 5, 6})));
//			lvl2.add(new ArrayList<>(Arrays.asList(new Integer[]{10, 11, 12})));
//			lvl2.add(new ArrayList<>(Arrays.asList(new Integer[]{14, 15})));
//			lvl2.add(new ArrayList<>(Arrays.asList(new Integer[]{17, 18, 19})));
//			lvl2.add(new ArrayList<>(Arrays.asList(new Integer[]{21, 22})));
//			lvl2.add(new ArrayList<>(Arrays.asList(new Integer[]{25, 26})));
//			ans.add(lvl0);
//			ans.add(lvl1);
//			ans.add(lvl2);
			for (int i = 0; i < keys.size(); i++) {
				for (int j = 0; j < keys.get(i).size(); j++) {
					//Assert.assertEquals(ans.get(i).get(j), keys.get(i).get(j));
					System.out.print(keys.get(i).get(j));
				}
				System.out.println();
			}
//			if(!verifyBTree(root, 0, getHeight(root), 3, root))
//				Assert.fail();

//			System.out.println(root.getKeys());
//			System.out.println(root.getChildren().get(0).getKeys());
//			System.out.println(root.getChildren().get(1).getKeys());
////			System.out.println(root.getChildren().get(2).getKeys());
////			System.out.println(root.getChildren().get(3).getKeys());
////			System.out.println(root.getChildren().get(4).getKeys());
////			System.out.println(root.getChildren().get(5).getKeys());
//			System.out.println(root.getChildren().get(0).getChildren().get(0).getKeys());
//			System.out.println(root.getChildren().get(0).getChildren().get(1).getKeys());
//			System.out.println(root.getChildren().get(0).getChildren().get(2).getKeys());
//			System.out.println(root.getChildren().get(0).getChildren().get(3).getKeys());
//			System.out.println(root.getChildren().get(1).getChildren().get(0).getKeys());
//			System.out.println(root.getChildren().get(1).getChildren().get(1).getKeys());
//			System.out.println(root.getChildren().get(1).getChildren().get(2).getKeys());
		} catch (Throwable e) {
			TestRunner.fail("Fail to insert in tree", e);
		}
	}
	private static boolean verifyBTree (IBTreeNode<?, ?> node, int lvl, int height, int t, IBTreeNode<?, ?> root) {
		if (!node.equals(root)) 
			if (node.getNumOfKeys() < t - 1 || node.getNumOfKeys() > 2 * t - 1)
				return false;
		boolean ans = true;
		if (!node.isLeaf()) {
			for (int i = 0; i <= node.getNumOfKeys(); i++) {
				ans = ans && verifyBTree(node.getChildren().get(i), lvl + 1, height, t, root);
				if (!ans) break;
			}

		}else {
			ans = ans && (lvl == height);
		}
		return ans;
	} 
	private static int getHeight (IBTreeNode<?, ?> node) {
		if (node.isLeaf()) return 0;

		return node.getNumOfKeys() > 0 ? 1 + getHeight(node.getChildren().get(0)) : 0;
	}
	private static void traverseBtreePreOrder(IBTreeNode<?, ?> node, int level, List<List<List<?>>> keys) {
		if (level >= keys.size())
			keys.add(new ArrayList<>());
		keys.get(level).add(node.getKeys());
		if (!node.isLeaf())
			for (int j = 0; j <= node.getNumOfKeys(); j++)
				traverseBtreePreOrder(node.getChildren().get(j), level + 1, keys);
	}
}
