package eg.edu.alexu.csd.filestructure.btree;

import java.util.List;

public class BTreeNode<K extends Comparable<K>,V> implements IBTreeNode<K, V> {

	@Override
	public int getNumOfKeys() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setNumOfKeys(int numOfKeys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLeaf() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setLeaf(boolean isLeaf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<K> getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKeys(List<K> keys) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<V> getValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValues(List<V> values) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<IBTreeNode<K, V>> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setChildren(List<IBTreeNode<K, V>> children) {
		// TODO Auto-generated method stub
		
	}

}
