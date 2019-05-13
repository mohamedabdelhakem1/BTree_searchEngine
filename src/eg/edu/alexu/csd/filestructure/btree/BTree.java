package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;

import java.util.List;

import javax.management.RuntimeErrorException;

public class BTree<K extends Comparable<K>, V> implements IBTree<K, V> {
	private IBTreeNode<K, V> root;
	private int minDegree;
	private int maxDegree;

	public BTree(int minDegree) {
		this.minDegree = minDegree;
		root = null;
		maxDegree = 2 * minDegree - 1;

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

		if (key == null)
			throw new RuntimeErrorException(new Error());
		if (root == null) {
			root = new BTreeNode<K, V>();
			List<K> keys = new ArrayList<K>();
			keys.add(key);
			List<V> values = new ArrayList<V>();
			values.add(value);
			root.setKeys(keys);
			root.setValues(values);
			root.setNumOfKeys(keys.size());
			return;
		} else {
			BTreeNode<K, V> z = (BTreeNode<K, V>) root;
			while (!z.isLeaf()) {
				List<K> keys = z.getKeys();
				int i = 0;
				K k = keys.get(i);
				while (k != null && k.compareTo(key) < 0) {
					i++;
					k = keys.get(i);
				}
				if(k != null && k.compareTo(key) == 0) {
					return;
				}
				z = (BTreeNode<K, V>) z.getChildren().get(i);
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
		return null;
	}

	@Override
	public boolean delete(K key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());
		if (root == null)
			return false;
		return deleteRecursively(root, key);

	}

	private boolean deleteRecursively(IBTreeNode<K, V> node, K key) {
		List<K> keys = node.getKeys();
		int parentindex = 0;
		List<Integer> parentIndices = new ArrayList<>();
		int i = 0;
		while (true) {
			if (node == null) {
				return false;
			}
			while (i < keys.size() && key.compareTo(keys.get(i)) > 0) {
				i++;
			}
			if (key == keys.get(i)) {
				break;
			} else {
				parentindex = i; // the least greatest key than the key in the parent Node
				parentIndices.add(parentindex );
				node = node.getChildren().get(i);
				keys = node.getKeys();
				i = 0;
			}
		}

		if (node.isLeaf() && keys.size() >= minDegree) { // case 1
			keys.remove(i);
			node.getValues().remove(i);
			node.setNumOfKeys(keys.size());
		} else if (node.isLeaf() && keys.size() == minDegree - 1) { // case 2 a ,b
			BTreeNode<K, V> p = ((BTreeNode<K, V>) node).getParent();
			if (p == null) {
				keys.remove(i);
				node.getValues().remove(i);
				node.setNumOfKeys(keys.size());
			} else {
				List<IBTreeNode<K, V>> parentChildren = p.getChildren();
				if (parentindex - 1 >= 0 && parentChildren.get(parentindex - 1).getKeys().size() >= minDegree) { // left
																													// immediate
																													// sibling
					int size = parentChildren.get(parentindex - 1).getKeys().size();

					// remove the greatest key of left immediate sibling
					K exKey = parentChildren.get(parentindex - 1).getKeys().remove(size - 1);
					V exValue = parentChildren.get(parentindex - 1).getValues().remove(size - 1);
					parentChildren.get(parentindex - 1).setNumOfKeys(size - 1);

					// replace the predecessor key in the parent node with the greatest key of left
					// immediate sibling
					K exParentKey = p.getKeys().get(parentindex - 1);
					V exParentValue = p.getValues().get(parentindex - 1);

					p.getKeys().set(parentindex - 1, exKey);
					p.getValues().set(parentindex - 1, exValue);

					// remove the key to be deleted
					node.getKeys().remove(i);
					node.getValues().remove(i);

					// add the predecessor key in the parent node at the 0-index of the starting of
					// key node
					node.getKeys().add(0, exParentKey);
					node.getValues().add(0, exParentValue);

				} else if (parentindex + 1 < parentChildren.size()
						&& parentChildren.get(parentindex + 1).getKeys().size() >= minDegree) { // right immediate
																								// sibling
					int size = parentChildren.get(parentindex + 1).getKeys().size();

					// remove the least key of right immediate sibling
					K exKey = parentChildren.get(parentindex + 1).getKeys().remove(0);
					V exValue = parentChildren.get(parentindex + 1).getValues().remove(0);
					parentChildren.get(parentindex + 1).setNumOfKeys(size - 1);

					// replace the successor key in the parent node with the least key of right
					// immediate sibling
					K exParentKey = p.getKeys().get(parentindex);
					V exParentValue = p.getValues().get(parentindex);

					p.getKeys().set(parentindex, exKey);
					p.getValues().set(parentindex, exValue);

					// remove the key to be deleted
					node.getKeys().remove(i);
					node.getValues().remove(i);

					// add the predecessor key in the parent node at the 0-index of the starting of
					// key node
					node.getKeys().add(exParentKey);
					node.getValues().add(exParentValue);
				} else {
					node.getKeys().remove(i);
					node.getValues().remove(i);
					node.setNumOfKeys(node.getKeys().size());
					mergeUp(p ,parentindex , parentIndices);	
				}
			}
		}else if (! node.isLeaf()) { // case 3 a,b,c
			IBTreeNode<K, V> predecessor =  node.getChildren().get(i);
			
			if(predecessor.getKeys().size() >= minDegree) {
				K predecessorKey = predecessor.getKeys().get(predecessor.getKeys().size() - 1);
				V predecessorValue = predecessor.getValues().get(predecessor.getValues().size() - 1);
				node.getKeys().set(i, predecessorKey);
				node.getValues().set(i, predecessorValue);
				deleteRecursively(predecessor, predecessorKey);
			}else {
				IBTreeNode<K, V> successor =  node.getChildren().get(i+1);
				if(successor.getKeys().size() >= minDegree) {
					K successorKey = successor.getKeys().get(0);
					V successorValue = successor.getValues().get(0);
					node.getKeys().set(i, successorKey);
					node.getValues().set(i, successorValue);
					deleteRecursively(successor, successorKey);
				}else {
					// store value and key	// delete key and value
					K centerKey = node.getKeys().remove(i);
					V centerValue = node.getValues().remove(i);
					node.setNumOfKeys(node.getKeys().size());
					mergeDown(node , i,centerKey,centerValue);
					if(node.getKeys().size() < minDegree -1) {
						mergeUp(((BTreeNode<K, V>)node).getParent() ,parentIndices.get(parentIndices.size()-1),parentIndices );
					}
					deleteRecursively(node.getChildren().get(i), centerKey);
				}
			}
			
		}
		return true;
	}
	private void mergeDown(IBTreeNode<K, V> node , int index ,K centerKey ,V centerValue) {
		IBTreeNode<K, V> pre = node.getChildren().get(index);
		IBTreeNode<K, V> succ = node.getChildren().get(index + 1);
		// merge keys and values
		List<K> keys= new ArrayList<>();
		List<V> values= new ArrayList<>();
		List<IBTreeNode<K, V>> children= new ArrayList<>();
		keys.addAll( pre.getKeys());
		values.addAll(pre.getValues());
		keys.add(centerKey);
		values.add(centerValue);
		keys.addAll(succ.getKeys());
		values.addAll(succ.getValues());
		pre.getChildren().get(index).setKeys(keys);
		pre.getChildren().get(index).setValues(values);
		children.addAll(pre.getChildren());
		children.addAll(succ.getChildren());
		pre.setChildren(children);	
		node.getChildren().remove(index+1);		
	}
	private void mergeUp(IBTreeNode<K, V> parent ,  int childIndex, List<Integer> parentIndices) {
		List<IBTreeNode<K, V>> parentChildren = parent.getChildren();
		// merge children
		if (childIndex - 1 >= 0 && parentChildren.get(childIndex - 1).getKeys().size() == minDegree - 1 ) { // merge with left immediate sibling
			int parentIndex = childIndex - 1;
			K parentKey = parent.getKeys().remove(parentIndex); 
			V parentValue = parent.getValues().remove(parentIndex);
			List<K> keys= new ArrayList<>();
			List<V> values= new ArrayList<>();
			List<IBTreeNode<K, V>> children = new ArrayList<>();
			keys.addAll(parentChildren.get(childIndex - 1).getKeys());
			keys.add(parentKey);
			keys.addAll(parentChildren.get(childIndex).getKeys());
			values.addAll(parentChildren.get(childIndex - 1).getValues());
			values.add(parentValue);
			values.addAll(parentChildren.get(childIndex).getValues());
			parent.getChildren().get(childIndex - 1).setKeys(keys);
			parent.getChildren().get(childIndex - 1).setValues(values);
			children.addAll(parentChildren.get(childIndex -1).getChildren());
			children.addAll(parentChildren.get(childIndex ).getChildren());
			parent.getChildren().get(childIndex - 1).setChildren(children);
			parent.getChildren().remove(childIndex);
			
			
			parentIndices.remove(parentIndices.size()-1); // check this // la tmam mfhash 7aga kda 3zma awy
			if(parent.getKeys().size() < minDegree - 1) {
				mergeUp(((BTreeNode<K,V>)parent).getParent() , parentIndices.get(parentIndices.size() - 1) , parentIndices);
			}
		} else if (childIndex + 1 < parentChildren.size() && parentChildren.get(childIndex + 1).getKeys().size() == minDegree - 1) { // merge with right immediate sibling
			int parentIndex = childIndex ;
			K parentKey = parent.getKeys().remove(parentIndex); 
			V parentValue = parent.getValues().remove(parentIndex);
			List<K> keys= new ArrayList<>();
			List<V> values= new ArrayList<>();
			List<IBTreeNode<K, V>> children = new ArrayList<>();
			keys.addAll(parentChildren.get(childIndex).getKeys());
			keys.add(parentKey);
			keys.addAll(parentChildren.get(childIndex + 1).getKeys());
			values.addAll(parentChildren.get(childIndex).getValues());
			values.add(parentValue);
			values.addAll(parentChildren.get(childIndex + 1).getValues());
			parent.getChildren().get(childIndex+1).setKeys(keys);
			parent.getChildren().get(childIndex+1).setValues(values);
			children.addAll(parentChildren.get(childIndex ).getChildren());
			children.addAll(parentChildren.get(childIndex +1).getChildren());
			parent.getChildren().get(childIndex).setChildren(children);
			
			parent.getChildren().remove(childIndex + 1);
			parentIndices.remove(parentIndices.size()-1);
			if(parent.getKeys().size() < minDegree - 1) {
				mergeUp(((BTreeNode<K,V>)parent).getParent() , parentIndices.get(parentIndices.size() - 1) , parentIndices);
			}
		}
	}
}
