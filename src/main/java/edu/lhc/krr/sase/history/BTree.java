package edu.lhc.krr.sase.history;

import java.util.List;

/******************************************************************************
 *  Compilation:  javac BTree.java
 *  Execution:    java BTree
 *  Dependencies: StdOut.java
 *
 *  B-tree.
 *
 *  Limitations
 *  -----------
 *   -  Assumes M is even and M >= 4
 *   -  should b be an array of children or list (it would help with
 *      casting to make it a list)
 *
 ******************************************************************************/

/**
 * The {@code BTree} class represents an ordered symbol table of generic
 * key-value pairs. It supports the <em>put</em>, <em>get</em>,
 * <em>contains</em>, <em>size</em>, and <em>is-empty</em> methods. A symbol
 * table implements the <em>associative array</em> abstraction: when associating
 * a value with a key that is already in the symbol table, the convention is to
 * replace the old value with the new value. Unlike {@link java.util.Map}, this
 * class uses the convention that values cannot be {@code null}—setting the
 * value associated with a key to {@code null} is equivalent to deleting the key
 * from the symbol table.
 * <p>
 * This implementation uses a B-tree. It requires that the key type implements
 * the {@code Comparable} interface and calls the {@code compareTo()} and method
 * to compare two keys. It does not call either {@code equals()} or
 * {@code hashCode()}. The <em>get</em>, <em>put</em>, and <em>contains</em>
 * operations each make log<sub><em>m</em></sub>(<em>n</em>) probes in the worst
 * case, where <em>n</em> is the number of key-value pairs and <em>m</em> is the
 * branching factor. The <em>size</em>, and <em>is-empty</em> operations take
 * constant time. Construction takes constant time.
 * <p>
 * For additional documentation, see
 * <a href="http://algs4.cs.princeton.edu/62btree">Section 6.2</a> of
 * <i>Algorithms, 4th Edition</i> by Robert Sedgewick and Kevin Wayne.
 */
public class BTree<Key extends Comparable<Key>, Value> {
	// max children per B-tree node = M-1
	// (must be even and greater than 2)
	private static final int M = 4;

	private Node root; // root of the B-tree
	private int height; // height of the B-tree
	private int n; // number of key-value pairs in the B-tree

	// helper B-tree node data type
	private static final class Node {
		private int m; // number of children
		private Entry[] children = new Entry[M]; // the array of children

		// create a node with k children
		private Node(int k) {
			m = k;
		}
	}

	// internal nodes: only use key and next
	// external nodes: only use key and value
	private static class Entry {
		private Comparable key;
		private final Object val;
		private Node next; // helper field to iterate over array entries

		public Entry(Comparable key, Object val, Node next) {
			this.key = key;
			this.val = val;
			this.next = next;
		}
	}

	/**
	 * Initializes an empty B-tree.
	 */
	public BTree() {
		root = new Node(0);
	}

	/**
	 * Returns true if this symbol table is empty.
	 * 
	 * @return {@code true} if this symbol table is empty; {@code false}
	 *         otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 * 
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return n;
	}

	/**
	 * Returns the height of this B-tree (for debugging).
	 *
	 * @return the height of this B-tree
	 */
	public int height() {
		return height;
	}

	/**
	 * Returns the value associated with the given key.
	 *
	 * @param key
	 *            the key
	 * @return the value associated with the given key if the key is in the
	 *         symbol table and {@code null} if the key is not in the symbol
	 *         table
	 * @throws IllegalArgumentException
	 *             if {@code key} is {@code null}
	 */
	public Value get(Key key) {
		if (key == null)
			throw new IllegalArgumentException("argument to get() is null");
		return search(root, key, height);
	}

	private Value search(Node x, Key key, int ht) {
		Entry[] children = x.children;

		// external node
		if (ht == 0) {
			for (int j = 0; j < x.m; j++) {
				if (eq(key, children[j].key))
					return (Value) children[j].val;
			}
		}

		// internal node
		else {
			for (int j = 0; j < x.m; j++) {
				if (j + 1 == x.m || lt(key, children[j + 1].key))
					return search(children[j].next, key, ht - 1);
			}
		}
		return null;
	}
	
	/**
	 * Recursive Range search in a DFS fashion
	 * @param x  Node to search ( Node can be a leaf or a parent)
	 * @param beginKey begin key to search
	 * @param endKey end key to search
	 * @param ht height of the node to search
	 */
	
	private void rangeSearchDFSRecursive(Node x,Key beginKey,Key endKey , int ht){
		Entry[] children = x.children;
		// external node
				if (ht == 0) {
					for (int j = 0; j < x.m; j++) {
						if (lte(beginKey, children[j].key) && gte(endKey, children[j].key) )
							System.out.println( (Value) children[j].key);
					}
				}
		
				else {
					for (int j = 0; j < x.m; j++) {
						if ( j + 1 == x.m || lte(beginKey, children[j].key) ||  (gte(beginKey, children[j].key) && lte(beginKey, children[j+1].key)) )
							rangeSearchDFSRecursive(children[j].next,  beginKey, endKey , ht - 1);
					}
				}
	}
	
	private int rangeCountDFSRecursive(Node x,Key beginKey,Key endKey , int ht, int count){
	
		Entry[] children = x.children;
		// external node
				if (ht == 0) {
					for (int j = 0; j < x.m; j++) {
						if (lte(beginKey, children[j].key) && gte(endKey, children[j].key) )
							count++;
					}
				}
		
				else {
					for (int j = 0; j < x.m; j++) {
						if ( j + 1 == x.m || lte(beginKey, children[j].key) ||  (gte(beginKey, children[j].key) && lte(beginKey, children[j+1].key)) )
							count=rangeCountDFSRecursive(children[j].next,  beginKey, endKey , ht - 1,count);
					}
				}
				return count;
	}
	
	/**
	 * Recursive Range search in a DFS fashion and avoiding any use of recursion
	 * @param x  Node to search ( Node can be a leaf or a parent)
	 * @param beginKey begin key to search
	 * @param endKey end key to search
	 * @param ht height of the node to search
	 */
	
//	private void rangeSearchDFS(BTree tree, Node root,int ht){
//		 Node current, pre;
//         
//	        if (root == null)
//	            return;
//	          
//	        current = root;
//	        while (current != null) 
//	        {
//	        	if (ht==0){
//	        		for (int i=0; i< current.m;i++)
//	        		{
//	        			System.out.print(current.children[i] + " ");
//	        		}
//	        		
//	        	}
//	        	else
//	        		
//	            if (current.left == null) 
//	            {
//	                System.out.print(current.data + " ");
//	                current = current.right;
//	            }
//	            else
//	            {
//	                /* Find the inorder predecessor of current */
//	                pre = current.left;
//	                while (pre.right != null && pre.right != current) 
//	                    pre = pre.right;
//	                 
//	                /* Make current as right child of its inorder predecessor */
//	                if (pre.right == null) 
//	                {
//	                    pre.right = current;
//	                    current = current.left;
//	                } 
//	  
//	                 /* Revert the changes made in if part to restore the 
//	                    original tree i.e.,fix the right child of predecssor*/
//	                 else
//	                 {
//	                    pre.right = null;
//	                    System.out.print(current.data + " ");
//	                    current = current.right;
//	                }   /* End of if condition pre->right == NULL */
//	                  
//	            } /* End of if condition current->left == NULL*/
//	              
//	        } /* End of while */
//	}

	/**
	 * Inserts the key-value pair into the symbol table, overwriting the old
	 * value with the new value if the key is already in the symbol table. If
	 * the value is {@code null}, this effectively deletes the key from the
	 * symbol table.
	 *
	 * @param key
	 *            the key
	 * @param val
	 *            the value
	 * @throws IllegalArgumentException
	 *             if {@code key} is {@code null}
	 */
	public void put(Key key, Value val) {
		//System.out.println();
		if (key == null)
			throw new IllegalArgumentException("argument key to put() is null");
		Node u = insert(root, key, val, height);
		n++;
		if (u == null)
			return;

		// need to split root
		Node t = new Node(2);
		t.children[0] = new Entry(root.children[0].key, null, root);
		t.children[1] = new Entry(u.children[0].key, null, u);
		root = t;
		height++;
	}

	private Node insert(Node h, Key key, Value val, int ht) {
		int j;
		Entry t = new Entry(key, val, null);

		// external node
		if (ht == 0) {
			for (j = 0; j < h.m; j++) {
				if (lt(key, h.children[j].key))
					break;
			}
		}

		// internal node
		else {
			for (j = 0; j < h.m; j++) {
				if ((j + 1 == h.m) || lt(key, h.children[j + 1].key)) {
					Node u = insert(h.children[j++].next, key, val, ht - 1);
					if (u == null)
						return null;
					t.key = u.children[0].key;
					t.next = u;
					break;
				}
			}
		}

		for (int i = h.m; i > j; i--)
			h.children[i] = h.children[i - 1];
		h.children[j] = t;
		h.m++;
		if (h.m < M)
			return null;
		else
			return split(h);
	}

	// split node in half
	private Node split(Node h) {
		Node t = new Node(M / 2);
		h.m = M / 2;
		for (int j = 0; j < M / 2; j++)
			t.children[j] = h.children[M / 2 + j];
		return t;
	}

	/**
	 * Returns a string representation of this B-tree (for debugging).
	 *
	 * @return a string representation of this B-tree.
	 */
	public String toString() {
		return toString(root, height, "") + "\n";
	}

	private String toString(Node h, int ht, String indent) {
		StringBuilder s = new StringBuilder();
		Entry[] children = h.children;

		if (ht == 0) {
			for (int j = 0; j < h.m; j++) {
				s.append(indent + children[j].key + " " + children[j].val + "\n");
			}
		} else {
			for (int j = 0; j < h.m; j++) {
				if (j > 0)
					s.append(indent + "(" + children[j].key + ")\n");
				s.append(toString(children[j].next, ht - 1, indent + "     "));
			}
		}
		return s.toString();
	}

	// comparison functions - make Comparable instead of Key to avoid casts
	private boolean lt(Comparable k1, Comparable k2) {
		return k1.compareTo(k2) < 0;
	}

	private boolean eq(Comparable k1, Comparable k2) {
		return k1.compareTo(k2) == 0;
	}
	
	private boolean lte(Comparable k1, Comparable k2) {
		return k1.compareTo(k2) <= 0;
	}

	private boolean gte(Comparable k1, Comparable k2) {
		return k1.compareTo(k2) >= 0;
	}
	private boolean gt(Comparable k1, Comparable k2) {
		return k1.compareTo(k2) > 0;
	}

	// zcurve function

	/**
	 * Unit tests the {@code BTree} data type.
	 *
	 * @param args
	 *            the command-line arguments
	 */
	public static void main(String[] args) {

		
		int  n = (int)(Math.log(99999) / Math.log(2));
		
		// public ZValue(int dimensions, int depth, double[] min, double[] max)
		// {
		// https://github.com/ngageoint/hootenanny/blob/master/hoot-services/src/main/java/hoot/services/geo/zindex/ZValue.java
		ZValue zv = new ZValue(2, 8, new double[] { 0, 0 }, new double[] { 15, 15 });
		
		//Decomposes a bounding box into a range of zcurve values
		ZCurveRanger ranger = new ZCurveRanger(zv);
		
		
		LongBox in= new LongBox(new long[] { 2, 14 }, new long[] { 5, 15 });
		LongBox[] lb= ranger.breakBox(in);		
		List<Range> r = ranger.decomposeRange(in, 2);

		System.out.println(r.toString());

		BTree<Long, String> st = new BTree<Long, String>();
		

		
		
long ff = zv.calculate(new double[] { 5, 6 });
		st.put(ff, "www.cs.princeton.edu");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 6, 8 }), "www.cs.princeton.edu");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 7, 14 }), "www.princeton.edu");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 9, 10 }), "www.yale.edu");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 8, 1 }), "www.simpsons.com");
		System.out.println(st);
		
		System.out.println();
		System.out.println(zv.calculate(new double[] { 5, 0 }));
		System.out.println(zv.calculate(new double[] { 6, 14 }));
		System.out.println();System.out.println();
		System.out.println(st.rangeCountDFSRecursive(st.root,zv.calculate(new double[] {5, 0 }),zv.calculate(new double[] {  6, 14}),st.height,0 ));
		st.rangeSearchDFSRecursive(st.root,zv.calculate(new double[] { 5, 0 }),zv.calculate(new double[] {  6, 14 }),st.height);
		
		st.put(zv.calculate(new double[] { 10, 2 }), "www.apple.com");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 15, 11 }), "www.amazon.com");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 4, 12 }), "www.ebay.com");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 5, 5 }), "www.cnn.com");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 9, 8 }), "www.google.com");
		System.out.println(st);

		st.put(zv.calculate(new double[] { 10, 3 }), "www.apple1.com");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 15, 10 }), "www.amazon1.com");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 4, 4 }), "www.ebay1.com");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 5, 6 }), "www.cnn1.com");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 9, 10 }), "www.google1.com");
		System.out.println(st);
		st.put(zv.calculate(new double[] { 7, 6 }), "www.cs.princeton1.edu");

		
		
		
		
		
		
		// System.out.println("cs.princeton.edu: " + st.get(7596));
		// System.out.println("hardvardsucks.com: " + st.get(652));
		// System.out.println("simpsons.com: " + st.get(8523));
		// System.out.println("apple.com: " + st.get("www.apple.com"));
		// System.out.println("ebay.com: " + st.get("www.ebay.com"));
		// System.out.println("dell.com: " + st.get("www.dell.com"));
		System.out.println();

		System.out.println("size:    " + st.size());
		System.out.println("height:  " + st.height());
		System.out.println(st);
		System.out.println(zv.calculate(new double[] { 9, 1 }));
		System.out.println(zv.calculate(new double[] { 8, 9 }));
		System.out.println(st);
		System.out.println(st.rangeCountDFSRecursive(st.root,zv.calculate(new double[] { 9, 1 }),zv.calculate(new double[] { 10, 9 }),st.height,0 ));
		st.rangeSearchDFSRecursive(st.root,zv.calculate(new double[] { 9, 1 }),zv.calculate(new double[] { 10, 9 }),st.height);

		
		
	}

}

/******************************************************************************
 * Copyright 2002-2016, Robert Sedgewick and Kevin Wayne.
 *
 * This file is part of algs4.jar, which accompanies the textbook
 *
 * Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne, Addison-Wesley
 * Professional, 2011, ISBN 0-321-57351-X. http://algs4.cs.princeton.edu
 *
 *
 * algs4.jar is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * algs4.jar is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * algs4.jar. If not, see http://www.gnu.org/licenses.
 ******************************************************************************/
