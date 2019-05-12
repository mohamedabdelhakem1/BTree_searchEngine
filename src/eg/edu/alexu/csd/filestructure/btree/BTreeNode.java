package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;
import java.util.List;

public class BTreeNode<K extends Comparable<K>,V> implements IBTreeNode<K, V> {
	private boolean leaf ;
	private int numOfKeys ;
	private List<K> keys ;
	private List<V>  values ;
	private List<IBTreeNode<K, V>> children;
	public BTreeNode() {
		leaf = false;
		keys = new ArrayList<>();
		values = new ArrayList<>();
		children = new ArrayList<>();
		numOfKeys = 0;
	}
	@Override
	public int getNumOfKeys() {
		return numOfKeys;
	}

	@Override
	public void setNumOfKeys(int numOfKeys) {
		this.numOfKeys = numOfKeys;
	}

	@Override
	public boolean isLeaf() {
		return leaf;
	}

	@Override
	public void setLeaf(boolean isLeaf) {
		leaf = isLeaf;
		
	}

	@Override
	public List<K> getKeys() {
		return keys;
	}

	@Override
	public void setKeys(List<K> keys) {
		this.keys  = keys;	
	}

	@Override
	public List<V> getValues() {
		return values;
	}

	@Override
	public void setValues(List<V> values) {
		this.values = values;
	}

	@Override
	public List<IBTreeNode<K, V>> getChildren() {
		
		return children;
	}

	@Override
	public void setChildren(List<IBTreeNode<K, V>> children) {
		this.children  = children;
		
	}

}
