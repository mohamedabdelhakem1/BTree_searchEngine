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
			while(!z.isLeaf()) {
				List<K> keys = z.getKeys();
				int i = 0;
				K k = keys.get(i);
				while(k != null && k.compareTo(key) < 0) {
					i++;
					k = keys.get(i);
				}
				z = (BTreeNode<K, V>) z.getChildren().get(i);
			}
			if(z.getKeys().size() == maxDegree) {
				
			} else if(z.getNumOfKeys() < maxDegree) {
				List<K> keys = z.getKeys();
				int pos = 0;
				while(pos < z.getNumOfKeys() && key.compareTo(keys.get(pos)) > 0) {
					pos++;
				}
				keys.add(pos, key);
				z.getValues().add(pos, value);
			}
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
