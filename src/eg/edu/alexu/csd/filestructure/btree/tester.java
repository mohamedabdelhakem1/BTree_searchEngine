package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.junit.Assert;

public class tester {

	public static void main(String[] args) {
		BTree<Integer, String> btree = new BTree<>(3);
		List<Integer> inp = Arrays
				.asList(new Integer[] { 1, 3, 7, 10, 11, 13, 14, 15, 18, 16, 19, 24, 25, 26, 21, 4, 5, 20, 22, 2, 17,
						12, 6, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50 });

		for (int i : inp)
			btree.insert(i, "Soso" + i);
		inp = Arrays.asList(new Integer[] {11,19,6,50,49,48,45,44,46,42,40,41,39,43,47,30});

		Queue<BTreeNode<Integer, String>> q = new LinkedList<>();	
		int iter = 0;
		int next = 1;

		q.add((BTreeNode<Integer, String>) btree.getRoot());
		while (!q.isEmpty()) {
			BTreeNode<Integer, String> node = q.poll();
			System.out.print(node.getKeys()); 
			System.out.print(node.isLeaf());
		//	System.out.print(" " + node.getNumOfKeys() + " " + node.getChildren().size() + " ");

			for (IBTreeNode<Integer, String> n : node.getChildren()) {
				q.add((BTreeNode<Integer, String>) n);
			}
			iter++;

			if (iter == next) {
				iter = 0;
				next = q.size();
				System.out.println();
			}

		}
		for (Integer i : inp) {

			btree.delete(i);
			Queue<BTreeNode<Integer, String>> qr = new LinkedList<>();
			qr.add((BTreeNode<Integer, String>) btree.getRoot());

			iter = 0;
			next = 1;

			System.out.println();
			System.out.println("tree delete" + i);
			List<Integer> l = new ArrayList<>();
			l.add(1);
			while (!qr.isEmpty()) {
				BTreeNode<Integer, String> node = qr.poll();
				System.out.print(node.getKeys());
				System.out.print(node.isLeaf());
				System.out.print(" " + node.getNumOfKeys() + " " + node.getChildren().size() + " ");

				for (IBTreeNode<Integer, String> n : node.getChildren()) {
					qr.add((BTreeNode<Integer, String>) n);
				}
				iter++;

				if (iter == next) {
					iter = 0;
					next = qr.size();
					System.out.println();
				}

			}

			System.out.println();
		}

	}

}
