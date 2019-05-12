package eg.edu.alexu.csd.filestructure.btree;

import java.util.List;

import javax.management.RuntimeErrorException;

public class BTree<K extends Comparable<K>, V> implements IBTree<K ,V>  {
	private IBTreeNode<K, V> root ;
	private int minDegree ;
	public BTree(int minDegree) {
		this.minDegree = minDegree;
		root = null ;
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
		
	}

	@Override
	public V search(K key) {
		return null;
	}

	@Override
	public boolean delete(K key) {
		if(key == null )throw new RuntimeErrorException(new Error());
		if(root == null) return false;
		deleteRecursively(root, key);
		return false;
	}
	public void deleteRecursively(IBTreeNode<K, V> node , K key) {
		List<K> keys = node.getKeys();
		int i = 0;
		while(i < keys.size() && key.compareTo(keys.get(i)) > 0) {
			i++;
		}
		if (key == keys.get(i)  ){
			if(node.isLeaf() &&  keys.size() >= minDegree) {
				keys.remove(i);
				node.getValues().remove(i);
				node.setNumOfKeys(keys.size());
			}else if(node.isLeaf() &&  keys.size() == minDegree-1) {
				
			}
		}else {
			List<IBTreeNode<K, V>> children = node.getChildren();
			deleteRecursively(children.get(i), key);
		}
	}
	
}
