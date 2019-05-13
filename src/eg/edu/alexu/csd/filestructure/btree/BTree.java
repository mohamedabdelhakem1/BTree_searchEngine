package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

public class BTree<K extends Comparable<K>, V> implements IBTree<K ,V>  {
	private IBTreeNode<K, V> root ;
	private int minDegree;
	private int maxDegree;
	public BTree(int minDegree) {
		this.minDegree = minDegree;
		maxDegree = 2*minDegree - 1;
		root = null;
	}
	@Override
	public int getMinimumDegree() {
		return minDegree;
	}

	@Override
	public IBTreeNode<K, V> getRoot() {
		// TODO Auto-generated method stub
		return root;
	}

	@Override
	public void insert(K key, V value) {
		if(key == null) throw new RuntimeErrorException(new Error());
		if(root == null) {
			root = new BTreeNode<K, V>();
			List<K> keys = new ArrayList<K>();
			keys.add(key);
			List<V> values = new ArrayList<V>();
			values.add(value);
			root.setKeys(keys);
			root.setValues(values);
			return;
		} else {
			BTreeNode<K, V> z = (BTreeNode<K, V>) root;
			int index = 0;
			while(!z.isLeaf()) {
				List<K> keys = z.getKeys();
				int i = 0;
				K k = keys.get(i);
				while(k != null && k.compareTo(key) < 0) {
					i++;
					k = keys.get(i);
				}
				if(k != null && k.compareTo(key) == 0) {
					return;
				}
				z = (BTreeNode<K, V>) z.getChildren().get(i);
				index = i;
			}
			
			List<K> keys = z.getKeys();
			int pos = 0;
			while(pos < z.getNumOfKeys() && key.compareTo(keys.get(pos)) > 0) {
				pos++;
			}
			keys.add(pos, key);
			z.getValues().add(pos, value);
			insertFixup(z);
		}
		
	}

	private void insertFixup(BTreeNode<K, V> n) {
		if(n == null || n.getNumOfKeys() < maxDegree) {
			return;
		} else {
			int splitIndex = n.getNumOfKeys() / 2;
			List<K> leftKeys = new ArrayList<K>(n.getKeys().subList(0, splitIndex));
			List<K> rightKeys = new ArrayList<K>(n.getKeys().subList(splitIndex + 1, n.getNumOfKeys()));
			List<V> leftValues = new ArrayList<V>(n.getValues().subList(0, splitIndex));
			List<V> rightValues = new ArrayList<V>(n.getValues().subList(splitIndex + 1, n.getNumOfKeys()));
			K k = n.getKeys().get(splitIndex);
			V v = n.getValues().get(splitIndex);
			if(!n.isLeaf()) {
				List<IBTreeNode<K, V>> leftC = new ArrayList<IBTreeNode<K,V>>(n.getChildren().subList(0, splitIndex));
				List<IBTreeNode<K, V>> rightC = new ArrayList<IBTreeNode<K,V>>(n.getChildren().subList(splitIndex, n.getNumOfKeys()));
				if(n.getParent() == null) {
					root = new BTreeNode<K, V>();
					root.setKeys(new ArrayList<K>());
					root.getKeys().add(k);
					root.getValues().add(v);
					BTreeNode<K, V> left = new BTreeNode<K, V>();
					left.setKeys(leftKeys);
					left.setValues(leftValues);
					left.setLeaf(false);
					left.setNumOfKeys(leftKeys.size());
					left.setChildren(leftC);
					BTreeNode<K, V> right = new BTreeNode<K, V>();
					right.setKeys(rightKeys);
					right.setValues(rightValues);
					right.setLeaf(false);
					right.setNumOfKeys(rightKeys.size());
					right.setChildren(rightC);
					root.getChildren().add(0, left);
					root.getChildren().add(1, right);
					root.setLeaf(false);
				} else {
					int index = n.getIndex();
					BTreeNode<K, V> parent = n.getParent();
					parent.getKeys().add(index, k);
					parent.getValues().add(index, v);
					parent.getChildren().remove(index);
					BTreeNode<K, V> left = new BTreeNode<K, V>();
					left.setKeys(leftKeys);
					left.setValues(leftValues);
					left.setLeaf(false);
					left.setNumOfKeys(leftKeys.size());
					left.setChildren(leftC);
					BTreeNode<K, V> right = new BTreeNode<K, V>();
					right.setKeys(rightKeys);
					right.setValues(rightValues);
					right.setLeaf(false);
					right.setNumOfKeys(rightKeys.size());
					right.setChildren(rightC);
					parent.getChildren().add(index, left);
					parent.getChildren().add(index + 1, right);
					parent.setNumOfKeys(parent.getNumOfKeys() + 1);
				}
			} else {
				if(n.getParent() == null) {
					root = new BTreeNode<K, V>();
					root.setKeys(new ArrayList<K>());
					root.getKeys().add(k);
					root.getValues().add(v);
					BTreeNode<K, V> left = new BTreeNode<K, V>();
					left.setKeys(leftKeys);
					left.setValues(leftValues);
					left.setLeaf(true);
					left.setNumOfKeys(leftKeys.size());
					BTreeNode<K, V> right = new BTreeNode<K, V>();
					right.setKeys(rightKeys);
					right.setValues(rightValues);
					right.setLeaf(true);
					right.setNumOfKeys(rightKeys.size());
					root.getChildren().add(0, left);
					root.getChildren().add(1, right);
					root.setLeaf(false);
					root.setNumOfKeys(1);
				} else {
					int index = n.getIndex();
					BTreeNode<K, V> parent = n.getParent();
					parent.getKeys().add(index, k);
					parent.getValues().add(index, v);
					parent.getChildren().remove(index);
					BTreeNode<K, V> left = new BTreeNode<K, V>();
					left.setKeys(leftKeys);
					left.setValues(leftValues);
					left.setLeaf(true);
					left.setNumOfKeys(leftKeys.size());
					BTreeNode<K, V> right = new BTreeNode<K, V>();
					right.setKeys(rightKeys);
					right.setValues(rightValues);
					right.setLeaf(true);
					right.setNumOfKeys(rightKeys.size());
					parent.getChildren().add(index, left);
					parent.getChildren().add(index + 1, right);
					parent.setNumOfKeys(parent.getNumOfKeys() + 1);
				}
			}
			insertFixup(n.getParent());
		}
	}
	
	@Override
	public V search(K key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(K key) {
		// TODO Auto-generated method stub
		return false;
	}

}
