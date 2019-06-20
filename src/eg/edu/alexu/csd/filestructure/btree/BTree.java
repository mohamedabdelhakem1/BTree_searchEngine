package eg.edu.alexu.csd.filestructure.btree;

import java.util.ArrayList;

import java.util.List;
import java.util.Stack;

import javax.management.RuntimeErrorException;

public class BTree<K extends Comparable<K>, V> implements IBTree<K, V> {
	private IBTreeNode<K, V> root;
	private int minDegree;
	private int maxDegree;

	public BTree(int minDegree) {
		if (minDegree < 2)
			throw new RuntimeErrorException(new Error());
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
		if (key == null || value == null)
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
				int pos = 0;
				while (pos < z.getNumOfKeys() && key.compareTo(z.getKeys().get(pos)) > 0) {
					pos++;
				}
				if (pos < z.getNumOfKeys() && z.getKeys().get(pos).compareTo(key) == 0) {
					return;
				}
				z = (BTreeNode<K, V>) z.getChildren().get(pos);
			}
			int pos = 0;
			while (pos < z.getNumOfKeys() && key.compareTo(z.getKeys().get(pos)) > 0) {
				pos++;
			}
			if (pos < z.getNumOfKeys() && z.getKeys().get(pos).compareTo(key) == 0) {
				return;
			}
			insertFixup(z, key, value);
		}

	}

	private void insertFixup(BTreeNode<K, V> n, K key, V value) {
		if (n == null || n.getNumOfKeys() < maxDegree) {
			int pos = 0;
			while (pos < n.getNumOfKeys() && key.compareTo(n.getKeys().get(pos)) > 0) {
				pos++;
			}
			if (pos < n.getNumOfKeys() && n.getKeys().get(pos).compareTo(key) == 0) {
				return;
			}
			n.getKeys().add(pos, key);
			n.setNumOfKeys(n.getNumOfKeys() + 1);
			n.getValues().add(pos, value);
			return;
		} else {
			int splitIndex = (n.getNumOfKeys() / 2);
			List<K> leftKeys = new ArrayList<K>(n.getKeys().subList(0, splitIndex));
			List<K> rightKeys = new ArrayList<K>(n.getKeys().subList(splitIndex + 1, n.getNumOfKeys()));
			List<V> leftValues = new ArrayList<V>(n.getValues().subList(0, splitIndex));
			List<V> rightValues = new ArrayList<V>(n.getValues().subList(splitIndex + 1, n.getNumOfKeys()));
			K k = n.getKeys().get(splitIndex);
			V v = n.getValues().get(splitIndex);
			if (!n.isLeaf()) {
				if (n.getParent() == null) {
					root = new BTreeNode<K, V>();
					root.setKeys(new ArrayList<K>());
					root.getKeys().add(k);
					root.getValues().add(v);
					BTreeNode<K, V> left = new BTreeNode<K, V>();
					left.setKeys(leftKeys);
					left.setValues(leftValues);
					left.setLeaf(false);
					left.setNumOfKeys(leftKeys.size());
					// left.setChildren(leftC);
					left.setParent(root);
					BTreeNode<K, V> right = new BTreeNode<K, V>();
					right.setKeys(rightKeys);
					right.setValues(rightValues);
					right.setLeaf(false);
					right.setNumOfKeys(rightKeys.size());
					// right.setChildren(rightC);
					right.setParent(root);
					root.getChildren().add(0, left);
					root.getChildren().add(1, right);
					root.setLeaf(false);
					root.setNumOfKeys(1);
					if (key.compareTo(n.getKeys().get(splitIndex)) < 0) {
						List<IBTreeNode<K, V>> leftC = new ArrayList<IBTreeNode<K, V>>(
								n.getChildren().subList(0, splitIndex + 2));
						List<IBTreeNode<K, V>> rightC = new ArrayList<IBTreeNode<K, V>>(
								n.getChildren().subList(splitIndex + 2, n.getChildren().size()));
						int pos = 0;
						while (pos < left.getNumOfKeys() && key.compareTo(left.getKeys().get(pos)) > 0) {
							pos++;
						}
						left.getKeys().add(pos, key);
						left.setNumOfKeys(left.getNumOfKeys() + 1);
						left.getValues().add(pos, value);
						left.setChildren(leftC);
						right.setChildren(rightC);
					} else {
						List<IBTreeNode<K, V>> leftC = new ArrayList<IBTreeNode<K, V>>(
								n.getChildren().subList(0, splitIndex + 1));
						List<IBTreeNode<K, V>> rightC = new ArrayList<IBTreeNode<K, V>>(
								n.getChildren().subList(splitIndex + 1, n.getChildren().size()));
						int pos = 0;
						while (pos < right.getNumOfKeys() && key.compareTo(right.getKeys().get(pos)) > 0) {
							pos++;
						}
						right.getKeys().add(pos, key);
						right.setNumOfKeys(right.getNumOfKeys() + 1);
						right.getValues().add(pos, value);
						left.setChildren(leftC);
						right.setChildren(rightC);
					}
				} else {
					int index = n.getIndex();
					BTreeNode<K, V> parent = (BTreeNode<K, V>) n.getParent();
					parent.getChildren().remove(index);
					BTreeNode<K, V> left = new BTreeNode<K, V>();
					left.setKeys(leftKeys);
					left.setValues(leftValues);
					left.setLeaf(false);
					left.setNumOfKeys(leftKeys.size());
					// left.setChildren(leftC);
					left.setParent(parent);
					BTreeNode<K, V> right = new BTreeNode<K, V>();
					right.setKeys(rightKeys);
					right.setValues(rightValues);
					right.setLeaf(false);
					right.setNumOfKeys(rightKeys.size());
					// right.setChildren(rightC);
					right.setParent(parent);
					parent.getChildren().add(index, left);
					parent.getChildren().add(index + 1, right);
					if (key.compareTo(n.getKeys().get(splitIndex)) < 0) {
						List<IBTreeNode<K, V>> leftC = new ArrayList<IBTreeNode<K, V>>(
								n.getChildren().subList(0, splitIndex + 2));
						List<IBTreeNode<K, V>> rightC = new ArrayList<IBTreeNode<K, V>>(
								n.getChildren().subList(splitIndex + 2, n.getChildren().size()));
						int pos = 0;
						while (pos < left.getNumOfKeys() && key.compareTo(left.getKeys().get(pos)) > 0) {
							pos++;
						}
						left.getKeys().add(pos, key);
						left.setNumOfKeys(left.getNumOfKeys() + 1);
						left.getValues().add(pos, value);
						left.setChildren(leftC);
						right.setChildren(rightC);
					} else {
						List<IBTreeNode<K, V>> leftC = new ArrayList<IBTreeNode<K, V>>(
								n.getChildren().subList(0, splitIndex + 1));
						List<IBTreeNode<K, V>> rightC = new ArrayList<IBTreeNode<K, V>>(
								n.getChildren().subList(splitIndex + 1, n.getChildren().size()));
						int pos = 0;
						while (pos < right.getNumOfKeys() && key.compareTo(right.getKeys().get(pos)) > 0) {
							pos++;
						}
						right.getKeys().add(pos, key);
						right.setNumOfKeys(right.getNumOfKeys() + 1);
						right.getValues().add(pos, value);
						left.setChildren(leftC);
						right.setChildren(rightC);
					}
					insertFixup(parent, k, v);
				}
			} else {
				if (n.getParent() == null) {
					root = new BTreeNode<K, V>();
					root.setKeys(new ArrayList<K>());
					root.getKeys().add(k);
					root.getValues().add(v);
					BTreeNode<K, V> left = new BTreeNode<K, V>();
					left.setKeys(leftKeys);
					left.setValues(leftValues);
					left.setLeaf(true);
					left.setNumOfKeys(leftKeys.size());
					left.setParent(root);
					BTreeNode<K, V> right = new BTreeNode<K, V>();
					right.setKeys(rightKeys);
					right.setValues(rightValues);
					right.setLeaf(true);
					right.setNumOfKeys(rightKeys.size());
					right.setParent(root);
					root.getChildren().add(0, left);
					root.getChildren().add(1, right);
					root.setLeaf(false);
					root.setNumOfKeys(1);
					if (key.compareTo(n.getKeys().get(splitIndex)) < 0) {
						int pos = 0;
						while (pos < left.getNumOfKeys() && key.compareTo(left.getKeys().get(pos)) > 0) {
							pos++;
						}
						if (pos < right.getNumOfKeys() && right.getKeys().get(pos).compareTo(key) == 0) {
							return;
						}
						left.getKeys().add(pos, key);
						left.setNumOfKeys(left.getNumOfKeys() + 1);
						left.getValues().add(pos, value);
					} else {
						int pos = 0;
						while (pos < right.getNumOfKeys() && key.compareTo(right.getKeys().get(pos)) > 0) {
							pos++;
						}
						if (pos < right.getNumOfKeys() && right.getKeys().get(pos).compareTo(key) == 0) {
							return;
						}
						right.getKeys().add(pos, key);
						right.setNumOfKeys(right.getNumOfKeys() + 1);
						right.getValues().add(pos, value);
					}
				} else {
					int index = n.getIndex();
					BTreeNode<K, V> parent = (BTreeNode<K, V>) n.getParent();

					parent.getChildren().remove(index);
					BTreeNode<K, V> left = new BTreeNode<K, V>();
					left.setKeys(leftKeys);
					left.setValues(leftValues);
					left.setLeaf(true);
					left.setNumOfKeys(leftKeys.size());
					left.setParent(parent);
					BTreeNode<K, V> right = new BTreeNode<K, V>();
					right.setKeys(rightKeys);
					right.setValues(rightValues);
					right.setLeaf(true);
					right.setNumOfKeys(rightKeys.size());
					right.setParent(parent);
					parent.getChildren().add(index, left);
					parent.getChildren().add(index + 1, right);
					if (key.compareTo(n.getKeys().get(splitIndex)) < 0) {
						int pos = 0;
						while (pos < left.getNumOfKeys() && key.compareTo(left.getKeys().get(pos)) > 0) {
							pos++;
						}
						if (pos < right.getNumOfKeys() && right.getKeys().get(pos).compareTo(key) == 0) {
							return;
						}
						left.getKeys().add(pos, key);
						left.setNumOfKeys(left.getNumOfKeys() + 1);
						left.getValues().add(pos, value);
					} else {
						int pos = 0;
						while (pos < right.getNumOfKeys() && key.compareTo(right.getKeys().get(pos)) > 0) {
							pos++;
						}
						if (pos < right.getNumOfKeys() && right.getKeys().get(pos).compareTo(key) == 0) {
							return;
						}
						right.getKeys().add(pos, key);
						right.setNumOfKeys(right.getNumOfKeys() + 1);
						right.getValues().add(pos, value);
					}
					// parent.setNumOfKeys(parent.getNumOfKeys() + 1);
					insertFixup(parent, k, v);
				}
			}
		}
	}

	@Override
	public V search(K key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());
		if (root == null)
			return null;
		BTreeNode<K, V> z = (BTreeNode<K, V>) root;
		while (!z.isLeaf()) {
			int pos = 0;
			while (pos < z.getNumOfKeys() && key.compareTo(z.getKeys().get(pos)) > 0) {
				pos++;
			}
			if (pos < z.getNumOfKeys() && z.getKeys().get(pos).compareTo(key) == 0) {
				return z.getValues().get(pos);
			}
			z = (BTreeNode<K, V>) z.getChildren().get(pos);
		}
		int pos = 0;
		while (pos < z.getNumOfKeys() && key.compareTo(z.getKeys().get(pos)) > 0) {
			pos++;
		}
		if (pos < z.getNumOfKeys() && z.getKeys().get(pos).compareTo(key) == 0) {
			return z.getValues().get(pos);
		}
		return null;
	}

	@Override
	public boolean delete(K key) {
		if (key == null)
			throw new RuntimeErrorException(new Error());
		if (root == null) {
			// System.out.println("null root");
			return false;
		}
		return deleteRecursively(root, key);

	}

	private boolean deleteRecursively(IBTreeNode<K, V> node, K key) {
		List<K> keys = node.getKeys();
		int i = 0;
		while (!node.isLeaf()) {
			// System.out.println(node.isLeaf());
			while (i < keys.size() && key.compareTo(keys.get(i)) > 0) {
				i++;
			}
			if (i < keys.size() && key.compareTo(keys.get(i)) == 0) {
				break;

			} else {

				node = node.getChildren().get(i);
				keys = node.getKeys();
				i = 0;
			}

		}
		if (node.isLeaf()) {
			// System.out.println(node.isLeaf());
			i = 0;
			while (i < keys.size() && key.compareTo(keys.get(i)) > 0) {
				i++;
			}

			if (i < keys.size() && key.compareTo(keys.get(i)) == 0) {

			} else {
				return false;

			}
		}
		if (node.isLeaf() && keys.size() >= minDegree) { // case 1
			keys.remove(i);
			node.getValues().remove(i);
			node.setNumOfKeys(keys.size());
			// System.out.println("case 1");
		} else if (node.isLeaf() && keys.size() <= minDegree - 1) { // case 2 a ,b
			BTreeNode<K, V> p = (BTreeNode<K, V>) ((BTreeNode<K, V>) node).getParent();

			if (p == null) {
				keys.remove(i);
				node.getValues().remove(i);
				node.setNumOfKeys(keys.size());
				if (keys.size() == 0) {
					root = null;
				}
				// System.out.println("case root");
			} else {
				List<IBTreeNode<K, V>> parentChildren = p.getChildren();
				int index = ((BTreeNode<K, V>) node).getIndex();
				if (index - 1 >= 0 && parentChildren.get(index - 1).getKeys().size() >= minDegree) { // left
																										// immediate
																										// sibling
					int size = parentChildren.get(index - 1).getKeys().size();

					// remove the greatest key of left immediate sibling
					K exKey = parentChildren.get(index - 1).getKeys().remove(size - 1);
					V exValue = parentChildren.get(index - 1).getValues().remove(size - 1);
					parentChildren.get(index - 1).setNumOfKeys(size - 1);

					// replace the predecessor key in the parent node with the greatest key of left
					// immediate sibling
					K exParentKey = p.getKeys().get(index - 1);
					V exParentValue = p.getValues().get(index - 1);

					p.getKeys().set(index - 1, exKey);
					p.getValues().set(index - 1, exValue);

					// remove the key to be deleted
					node.getKeys().remove(i);
					node.getValues().remove(i);

					// add the predecessor key in the parent node at the 0-index of the starting of
					// key node
					node.getKeys().add(0, exParentKey);
					node.getValues().add(0, exParentValue);
					// System.out.println("case 2b1");
				} else if (index + 1 < parentChildren.size()
						&& parentChildren.get(index + 1).getKeys().size() >= minDegree) { // right immediate
																							// sibling
					int size = parentChildren.get(index + 1).getKeys().size();

					// remove the least key of right immediate sibling
					K exKey = parentChildren.get(index + 1).getKeys().remove(0);
					V exValue = parentChildren.get(index + 1).getValues().remove(0);
					parentChildren.get(index + 1).setNumOfKeys(size - 1);

					// replace the successor key in the parent node with the least key of right
					// immediate sibling
					K exParentKey = p.getKeys().get(index);
					V exParentValue = p.getValues().get(index);

					p.getKeys().set(index, exKey);
					p.getValues().set(index, exValue);

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

					if (p.getKeys().size() >= minDegree || p.getParent() == null) {
						merge(p, index, false);
					} else {
						merge(p, index, true);
						fixInternalNode((BTreeNode<K, V>) p);

					}
					// System.out.println("case 2b");
				}
			}
		} else if (!node.isLeaf()) { // case 3 a,b,c

			IBTreeNode<K, V> predecessor = getPredessor((BTreeNode<K, V>) node, i);

			if (predecessor.getKeys().size() >= minDegree) {
				K predecessorKey = predecessor.getKeys().get(predecessor.getKeys().size() - 1);
				V predecessorValue = predecessor.getValues().get(predecessor.getValues().size() - 1);
				deleteRecursively(predecessor, predecessorKey);
				node.getKeys().set(i, predecessorKey);
				node.getValues().set(i, predecessorValue);

			} else {
				IBTreeNode<K, V> successor = null; 
				boolean successorFound = false;
				try {
					successor = getSuccessor((BTreeNode<K, V>) node, i + 1);
					successorFound = true;
				} catch (IndexOutOfBoundsException e) {
					successorFound = false;
				}

				if (successorFound && successor.getKeys().size() >= minDegree) {
					K successorKey = successor.getKeys().get(0);
					V successorValue = successor.getValues().get(0);
					deleteRecursively(successor, successorKey);
					node.getKeys().set(i, successorKey);
					node.getValues().set(i, successorValue);
				} else {
	
					K predecessorKey = predecessor.getKeys().get(predecessor.getKeys().size() - 1);
					V predecessorValue = predecessor.getValues().get(predecessor.getValues().size() - 1);
					node.getKeys().set(i, predecessorKey);
					node.getValues().set(i, predecessorValue);
					deleteRecursively(predecessor, predecessorKey);
				}

			}

		}
		return true;
	}

	private BTreeNode<K, V> getSuccessor(BTreeNode<K, V> x, int index) {
		BTreeNode<K, V> z = (BTreeNode<K, V>) x.getChildren().get(index);
		while (!z.isLeaf()) {
			z = (BTreeNode<K, V>) z.getChildren().get(0);
		}
		return z;
	}

	private BTreeNode<K, V> getPredessor(BTreeNode<K, V> x, int index) {
		BTreeNode<K, V> z = (BTreeNode<K, V>) x.getChildren().get(index);
		while (!z.isLeaf()) {
			z = (BTreeNode<K, V>) z.getChildren().get(z.getChildren().size() - 1);
		}
		return z;
	}

	private void fixInternalNode(BTreeNode<K, V> p) {
		Stack<BTreeNode<K, V>> stack = new Stack<>();
		stack.push(p);
		System.out.println(p.hashCode() + "fix first internal");
		BTreeNode<K, V> node;
		while (!stack.isEmpty()) {
			node = stack.peek();
			if (node.getKeys().size() <= minDegree - 1) {
				if (!rebalanceBySpareKeys(node.getParent(), node.getIndex())) {
					if (!merge(node.getParent(), node.getIndex(), false)) {
						if (((BTreeNode<K, V>) node.getParent()).getParent() == null) {
							break;
						} else {
							stack.push((BTreeNode<K, V>) ((BTreeNode<K, V>) node.getParent()));
						}
					} else {
						break;
					}
				} else {
					break;
				}
			} else {
				break;
			}
		}
		stack.pop();
		while (!stack.isEmpty()) {
			node = stack.pop();
			merge(node.getParent(), node.getIndex(), false);
			System.out.println(node.hashCode() + "while emptying stack");
		}
	}

	private boolean merge(IBTreeNode<K, V> parent, int childIndex, boolean neglectMinDegree) {
		if (parent.getKeys().size() >= minDegree || ((BTreeNode<K, V>) parent).getParent() == null
				|| neglectMinDegree) {
			List<IBTreeNode<K, V>> parentChildren = parent.getChildren();
			// merge children
			if (childIndex - 1 >= 0 && parentChildren.get(childIndex - 1).getKeys().size() <= minDegree - 1) { // merge
																												// with
																												// left
																												// immediate
																												// sibling
				int parentIndex = childIndex - 1;
				K parentKey = parent.getKeys().remove(parentIndex);
				V parentValue = parent.getValues().remove(parentIndex);
				parent.setNumOfKeys(parent.getKeys().size());

				List<K> keys = new ArrayList<>();
				List<V> values = new ArrayList<>();

				List<K> preKeys = parentChildren.get(childIndex - 1).getKeys();
				List<K> sucKeys = parentChildren.get(childIndex).getKeys();

				keys.addAll(preKeys);
				keys.add(parentKey);
				keys.addAll(sucKeys);

				List<V> preValues = parentChildren.get(childIndex - 1).getValues();
				List<V> sucValues = parentChildren.get(childIndex).getValues();

				values.addAll(preValues);
				values.add(parentValue);
				values.addAll(sucValues);

				parentChildren.get(childIndex).setKeys(keys);
				parentChildren.get(childIndex).setValues(values);
				parentChildren.get(childIndex).setNumOfKeys(keys.size());

				List<IBTreeNode<K, V>> children = new ArrayList<>();
				children.addAll(parentChildren.get(childIndex - 1).getChildren());
				children.addAll(parentChildren.get(childIndex).getChildren());
				parentChildren.get(childIndex).setChildren(children);

				parentChildren.remove(childIndex - 1);
				/// System.out.println(parent.getChildren().get(childIndex).getKeys().size());
				/// // sibling

				// parent.setChildren(parentChildren);
				if (parent == root) {
					if (root.getKeys().size() == 0) {
						root = parent.getChildren().get(0);
						((BTreeNode<K, V>) root).setParent(null);
						if (parent.getChildren().get(0).isLeaf()) {
							root.setLeaf(true);
						}

					}

				}
				return true;
			} else if (childIndex + 1 < parentChildren.size()
					&& parentChildren.get(childIndex + 1).getKeys().size() <= minDegree - 1) { // merge with right
																								// immediate
																								// sibling
				int parentIndex = childIndex;
				K parentKey = parent.getKeys().remove(parentIndex);
				V parentValue = parent.getValues().remove(parentIndex);
				parent.setNumOfKeys(parent.getKeys().size());

				List<K> keys = new ArrayList<>();
				List<V> values = new ArrayList<>();

				List<K> preKeys = parentChildren.get(childIndex).getKeys();
				List<K> sucKeys = parentChildren.get(childIndex + 1).getKeys();

				keys.addAll(preKeys);
				keys.add(parentKey);
				keys.addAll(sucKeys);

				List<V> preValues = parentChildren.get(childIndex).getValues();
				List<V> sucValues = parentChildren.get(childIndex + 1).getValues();

				values.addAll(preValues);
				values.add(parentValue);
				values.addAll(sucValues);

				parent.getChildren().get(childIndex).setKeys(keys);
				parent.getChildren().get(childIndex).setValues(values);
				parent.getChildren().get(childIndex).setNumOfKeys(keys.size());

				List<IBTreeNode<K, V>> children = new ArrayList<>();
				children.addAll(parentChildren.get(childIndex).getChildren());
				children.addAll(parentChildren.get(childIndex + 1).getChildren());
				parentChildren.get(childIndex).setChildren(children);

				parentChildren.remove(childIndex + 1);

				if (parent == root) {
					if (root.getKeys().size() == 0) {
						root = parent.getChildren().get(0);
						((BTreeNode<K, V>) root).setParent(null);
						if (parent.getChildren().get(0).isLeaf()) {
							root.setLeaf(true);
						}
					}

				}
				return true;
			}
		}
		return false;
	}

	private boolean rebalanceBySpareKeys(IBTreeNode<K, V> parent, int childIndex) {
		List<IBTreeNode<K, V>> parentChildren = parent.getChildren();
		if (childIndex - 1 >= 0 && parentChildren.get(childIndex - 1).getKeys().size() >= minDegree) {// left
			// immediate
			// sibling
			int size = parentChildren.get(childIndex - 1).getKeys().size();

			// remove the greatest key of left immediate sibling
			K exKey = parentChildren.get(childIndex - 1).getKeys().remove(size - 1);
			V exValue = parentChildren.get(childIndex - 1).getValues().remove(size - 1);
			parentChildren.get(childIndex - 1).setNumOfKeys(parentChildren.get(childIndex - 1).getKeys().size());

			IBTreeNode<K, V> borrowedChild = parentChildren.get(childIndex - 1).getChildren().remove(size);
			// replace the predecessor key in the parent node with the greatest key of left
			// immediate sibling
			K exParentKey = parent.getKeys().get(childIndex - 1);
			V exParentValue = parent.getValues().get(childIndex - 1);

			parent.getKeys().set(childIndex - 1, exKey);
			parent.getValues().set(childIndex - 1, exValue);

			// remove the key to be deleted

			// add the predecessor key in the parent node at the 0-index of the starting of
			// key node
			parentChildren.get(childIndex).getKeys().add(0, exParentKey);
			parentChildren.get(childIndex).getValues().add(0, exParentValue);
			parentChildren.get(childIndex).setNumOfKeys(parentChildren.get(childIndex).getKeys().size());

			List<IBTreeNode<K, V>> newChildren = parentChildren.get(childIndex).getChildren();
			newChildren.add(0, borrowedChild);
			parentChildren.get(childIndex).setChildren(newChildren);

			return true;
		} else if (childIndex + 1 < parentChildren.size()
				&& parentChildren.get(childIndex + 1).getKeys().size() >= minDegree) {
			// int size = parentChildren.get(childIndex + 1).getKeys().size();

			// remove the least key of right immediate sibling
			K exKey = parentChildren.get(childIndex + 1).getKeys().remove(0);
			V exValue = parentChildren.get(childIndex + 1).getValues().remove(0);
			parentChildren.get(childIndex + 1).setNumOfKeys(parentChildren.get(childIndex + 1).getKeys().size());

			IBTreeNode<K, V> borrowedChild = parentChildren.get(childIndex + 1).getChildren().remove(0);

			// replace the successor key in the parent node with the least key of right
			// immediate sibling
			K exParentKey = parent.getKeys().get(childIndex);
			V exParentValue = parent.getValues().get(childIndex);

			parent.getKeys().set(childIndex, exKey);
			parent.getValues().set(childIndex, exValue);

			// add the predecessor key in the parent node at the 0-index of the starting of
			// key node
			parentChildren.get(childIndex).getKeys().add(exParentKey);
			parentChildren.get(childIndex).getValues().add(exParentValue);
			parentChildren.get(childIndex).setNumOfKeys(parentChildren.get(childIndex).getKeys().size());

			List<IBTreeNode<K, V>> newChildren = parentChildren.get(childIndex).getChildren();
			newChildren.add(borrowedChild);
			parentChildren.get(childIndex).setChildren(newChildren);

			return true;
		}
		return false;
	}
}
