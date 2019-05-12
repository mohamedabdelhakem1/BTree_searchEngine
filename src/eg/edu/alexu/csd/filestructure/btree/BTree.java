package eg.edu.alexu.csd.filestructure.btree;

public class BTree<K extends Comparable<K>, V> implements IBTree<K ,V>  {
	private IBTreeNode<K, V> root ;
	private int minDegree ;
	public BTree(int minDegree) {
		this.minDegree = minDegree;
		root = new BTreeNode<>();
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
		// TODO Auto-generated method stub
		
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
