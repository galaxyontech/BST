package project6;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * An implementation of a binary search tree. The elements are ordered using their natural ordering.
 * This implementation provides guaranteed O(H) (H is the height of this tree which could be as low as logN for balanced trees, but could be as large as N for unbalanced trees) time cost for the basic operations (add, remove and contains)
 * This class implements many of the methods provided by the Java framework's TreeSet class. .
 * @author zeruiji
 *
 * @param <E>
 * 
 */

public class BST<E extends Comparable<E>> implements Iterable<E>, Cloneable {
	public static void main(String[] args) {
	}
	
	
	
	
/**
 * This is my own node class, it contains information of data, left and right
 * The class has two constructors and take different parameters  
 * @author zeruiji
 *
 * @param <E>
 */
	
	
	
	
	private static class Node<E>{
		E data;
		Node<E> left;
		Node<E> right;
		/**
		 * Store the information into the node data
		 * @param data
		 */
		Node(E data){
			this.data = data;
		}
		/**
		 * store the information left,right and data
		 * @param data
		 * @param L
		 * @param R
		 */
		@SuppressWarnings("unused")
		Node (E data, Node<E> L, Node<E> R){
			this.data = data;
			left = L;
			right = R;
		}
		
	}
	
	
	
	private Node<E> root;					// this is the root of the tree
	
	/**
	 * this is a getter method for private variable root 
	 * @return
	 */
	public Node<E> getRoot(){
		return this.root;
	}
	/**
	 * Constructor for creating the tree 
	 * the function will assign the root with null
	 */
	public BST() {
		root = null;
	}
	/**
	 * This is the constructor and accepts a collection as parameter and
	 * convert all elements into a tree
	 * @param collection
	 */
	public  BST(E[] collection) {
		for (E element : collection) {
			add(element);				// add them into the tree
		}
	}
	
	/**
	 * This is the wrapper class of the add method.
	 * It checks whether the element has been added base on the size of the tree
	 * @param e
	 * @return boolean
	 */
	public boolean add ( E e ) {
        int oldSize = size();			// store the old  size
        root  =  add ( e, root );		// call the recursive add function 
        if (oldSize == size())  		// check whether the size has been changed     	
            return false ;
        return true;
    }
	/**
	 * This is the actual recursion program to add the element, it will go down the tree in the order of BST 
	 * and find the proper position to add the element
	 * @param item
	 * @param root
	 * @return
	 */
	private Node<E> add ( E item, Node<E> root ) {
        if ( root == null ) {			// if the root is null, add this element as the root of the tree
            return new Node<E>(item);
        }
        if ( root.data == item ) {		// if the root.data is the item, then simply return the root
            return root;
        }
        else if ( item.compareTo(root.data) > 0 ) {		// go down the tree recursively to add the element
        	
            root.right =  add ( item, root.right );		// go to the right

        }
        else {
            root.left = add ( item, root.left);			// go to the left 
        }
        return root;
    }
	/**
	 * Adds the collection of elements into this tree
	 * The method iterates through the collection and add each element into the tree
	 * @param collection
	 * @return	boolean
	 */
	
	public boolean addAll(Collection<? extends E> collection) {
		if( collection.isEmpty())			// check if the collection is empty
			return false;					// return false if the collection is indeed empty
		int oldSize = size();				// store size
		for (E element: collection) {
			root = add(element,root);		// iterating and addition
		}
		if (oldSize == size()) {			// check if the size has been changed
			return false;
		}
		return true;
	}
	/**
	 * Returns the least element in this tree greater than or equal to the given element, 
	 * or null if there is no such element.
	 * This operation should be O(H)
	 * This is the wrapper class of ceiling
	 * @param e
	 * @return E
	 * @throws NullPointerException, NoSuchElementException
	 */
	public E ceiling (E e) {
		if (e == null) throw new NullPointerException("Error: not valid argument");		// check the exception for null argument 
		if (isEmpty()) throw new NoSuchElementException("Eroor: the set does not contain this element");		// check if the tree is empty 
		Node<E>  x = ceiling(root,e );		// call the recursive function 
		if (x == null) {			// return properly by the x 
			return null;
		}else {
			return x.data;
		}
	}
	/**
	 * The is the actual recursive program
	 * Recursively goes down the tree and find out the first element bigger than it 
	 * @param root
	 * @param e
	 * @return
	 */
	private Node<E> ceiling(Node<E> root, E e) {
		if (root == null ) return null;				
		int compare = root.data.compareTo(e);		// store the comparison result
		if (compare == 0) return root;				// equal
		if ( compare > 0) {							// goes down the tree from the right 
			Node<E> result = ceiling(root.left, e);	// as long as the result is bigger, immediately goes to the left
			if ( result != null) {
				return result;
			}else {
				return root;
			}
		}
		return ceiling(root.right,e);
	}
	/**
	 * Removes all of the elements from this set. 
	 * The set will be empty after this call returns.
	 * This operation should be O(1).
	 */
	
	public void clear() {
		root = null;
	}
	/**
	 * Returns a shallow copy of this tree instance (i.e., the elements themselves are not cloned but the nodes are).
	 * This operation should be O(N).
	 * @return BST<E>
	 */
	public BST<E> clone(){
		BST<E> clone = new BST<E>(); // create the new tree
		Iterator<E> pre = this.preorderIterator();		// creates the iterator
		while(pre.hasNext()) {
			clone.add(pre.next());			// iterates through the tree and add each element to the new tree 
		}
		return clone;
	}
	
	/**
	 * Returns true if this set contains the specified element. 
	 * More formally, returns true if and only if this set contains an element e such that Objects.equals(o, e).
	 * This operation should be O(H).
	 * @param e
	 * @return
	 */
	
	@SuppressWarnings("unchecked")
	public boolean contains(Object e) {
		if (root == null)			// if the root is null, output null
			return false;
		Node<E> cur = root;			// create the cur with the same reference with null
		do {
			E curVal = cur.data;	// compare the data with the object 
			if ( ((Comparable<E>) e).compareTo( curVal) == 0) return true;				// if true, return
			if (((E)e).compareTo( curVal) < 0) cur = cur.left;				// if not true, then goes the the tree recursively untill everynode has been visited 
			else cur = cur.right;
		}while ( cur != null);
		return false;
	}
	/**
	 * Returns true if this collection contains all of the elements in the specified collection. 
	 * This implementation iterates over the specified collection, checking each element returned by the iterator in turn to see if it's contained in this tree. 
	 * If all elements are so contained true is returned, otherwise false.
	 * @param c
	 * @return
	 */
	public boolean containsAll(Collection<?> c) {
		for(Object element: c) {    // iterates through the element 
			if (!contains(element)) {			// call the contain function 
				return false;
			}
		}
		return true; 
	}
	/**
	 * Compares the specified object with this tree for equality. Returns true if the given object is also a tree, the two trees have the same size,
	 *  and every member of the given tree is contained in this tree.
	 *  This operation should be O(N).
	 *  @return boolean
	 */
    public boolean equals(Object o){
		if (o == null) {				// check whether the object is null to avoid nullpointer exception
			return false;
		}
		if (!(o instanceof BST)) {		// whether o is a instance of bst
			return false;
		}
		@SuppressWarnings("unchecked")
		BST<E> B = (BST<E>) o;			// if it is, we can now cast it 
		for (E e : B) {
			if(!this.contains(e)) {		// check if each element is contained in the current tree
				return false;
			}
			
		}
		for (E e: this) {				// check if each element is contained in the object tree
			if (!B.contains(e)) return false;
		}
		return true;
	}
	/**
	 * Returns the greatest element in this set less than or equal to the given element, 
	 * or null if there is no such element.
	 * This is a wrapper function
	 * @param e
	 * @throws NullPointerException
	 * @return
	 */	
	public E floor(E e) {
		if (e == null) {
			throw new NullPointerException("Error: null argument");  //if the argument is null
		}
		if (e.compareTo(first()) < 0) { 		// if the element is strictly small than the smallest element in the tree
			return null;
		}
		if (e.compareTo(last()) >=0 ) {			// if the element is strictly larger than the largest element in the tree
			return last();
		}
		return floor(root, e);					// calls the actually recursive program 
	}
	/**
	 * Recursively goes down the tree and find out the first element smaller than, or the number itself
	 * @param root
	 * @param key
	 * @return
	 */
	private E floor(Node<E> root, E e ) {
		if (root.data == e) {		// the base case, if the number is found, simply return then number
			return e;
		}
		if (root.data.compareTo( e) > 0) {			// if the root is larger than the element, we go to the left subtree
			return floor(root.left,e);
		}
		E floor = floor(root.right,e);			// the first number to the right
		if (floor.compareTo(e) <= 0) {	// compares them the right to the number 
			return floor;
		}else {
			return root.data;
		}
		
	}
	/**
	 * Returns the first (lowest) element currently in this tree.
	 * This operation should be O(H).
	 * @return
	 * @throws NoSuchElementException
	 */
	public E first() {
		if ( root == null) {				// if the tree is empty
			throw new NoSuchElementException("Error: the tree is empty");
		}
		Node<E> current = root;				// keep calling the left substree and untill the reference is null
		while (current.left != null) {
			current = current.left;
		}
		return current.data;
	}
	/**
	 * Returns the last (highest) element currently in this tree.
	 * This operation should be O(H).
	 * @return
	 */
	public E last() {
		Node<E> cur = root;  		// go down the tree from the right
		while(cur.right != null) {	// recursively goes down the right subtree until the reference is null 
			cur = cur.right;
		}
		return cur.data;
	}
	/**
	 * Returns the greatest element in this set strictly less than the given element, 
	 * or null if there is no such element.
	 * This operation should be O(H).
	 * @param e
	 * @return
	 * @throws NullPointerException, NoSuchElementException
	 */

	public E lower(E e) {
		if ( e == null) throw new NullPointerException("Error: not valid argument"); // if the argument is empty 
		if (isEmpty())  throw new NoSuchElementException("Error: the set is empty"); // if the set is empty
		Node<E> x = lower(root,e);		// calls the recursive function 
		if (x == null) {				// if the result is null, make sure to return null, to avoid nullpointer exception
			return null;
		}else {
			return x.data;
		}
		
	}
	/**
	 * This is the actual recursive program, we compare the value with the root, base on the comparion
	 * Once the root is smaller than key, we go to the first element at right
	 * @param root
	 * @param e
	 * @return
	 */
	private Node<E> lower(Node<E> root, E e) {
		if (root == null) {					// if the root is null
			return null;
		}
		int compare = root.data.compareTo(e);
		if (compare < 0) {				// Once the root is smaller than the key, we want to hit the first number right 
			Node<E> result = lower(root.right, e);
			if (result !=null) {		// check the reference and return the corresponding value
				return result;
			}else {
				return root;
			}
					
		}
		return lower(root.left, e);
	}
	/**
	 * Returns the least element in this tree strictly greater than the given element,
	 *  or null if there is no such element.
	 * This operation should be O(H).
	 * @param e
	 * @return
	 * @throws NullPointerException, NoSuchElementException
	 */
	public E higher(E e) {
		// Check for exceptions
		if (e == null) throw new NullPointerException("Error: not valid argument");
		if (isEmpty()) throw new NoSuchElementException("Error: empty set");
		// call the recursive function and make the proper return 
		Node<E>  x = higher(root,e );    // calling
		if (x == null) {
			return null;
		}else {
			return x.data;
			}
		}
	/**
	 * this is the actual recursive function, the logic is similar to the lower.
	 * Except we go to the right first and as long as the element is bigger than the key
	 * we hit the first number smaller 
	 * @param root
	 * @param e
	 * @return
	 */
	public Node<E> higher(Node<E> root ,E e){
		if (root == null ) return null;			// checks for null 
		int compare = root.data.compareTo(e);	// checks for null
		if ( compare > 0) {			// once root is bigger than key
			Node<E> result = higher(root.left, e);			// call the first number at the left
			if ( result != null) {		// if the number is not null, return this number
				return result;
			}else {			// return the root 
				return root;
			}
		}
		return higher(root.right,e);
	}
	/**
	 * Returns the height of this tree. The height of a leaf is 1. 
	 * The height of the tree is the height of its root node.
	 * This is a wrapper class
	 * @return
	 */
	public int height() {
        return height(root);
    }
	/**
	 * This is actual recursive program that goes through each branch to check the maximum height
	 * @param x
	 * @return
	 */
	private int height(Node<E> x) {
		if (x == null) return 0;
		return 1 + Math.max(height(x.left) , height(x.right));
	}
	
    /**
     * Returns the number of elements in this tree.
     * This is the wrapper class for recursive function
     * @return
     */
	public int size ( ) {
        return size(root);
    }
	/**
	 * This is the actual recursion where it goes through the entire node add the size of all code 
	 * @param n
	 * @return
	 */
    private int size( Node<E> n ) {
        if (n == null ) return 0;			// if the tree is empty 
        return 1 + size(n.left) + size(n.right);				// goes through each brank
    }
    /**
     * Check if the tree is empty 
     * @return boolean
     */
    
    public boolean isEmpty() {
    	if (root == null) {			// if the root is null, return true, otherwise false
    		return true;
    	}
    	return false;
    	
    }
   
    
    
    /**
     * Returns an iterator over the elements in this tree in ascending order.
     * This operation should be O(N).
     * @return itr
     */

	@Override
	public Iterator<E> iterator() {
		Iterator<E> itr = new Itr(root);
		
		return itr;
	}
	/**
	 * The actual implementation of iterator class
	 * @author zeruiji
	 *
	 */
	
	private class Itr implements Iterator<E>{
		ArrayList<E> inOrder = new ArrayList<E>(); // creates the arraylist to store all the element 
		int index;	
		// keeps record of index 
		/**
		 * This is the constructor for iterator and set each data field to the default state
		 * @param node
		 */
		public Itr(Node<E> node) {			// constructor to set up the initial state
			this.index = -1;				
			this.inOrder(node);
		}
		/**
		 * This is the actual inorder traversal
		 * it adds each element to the Arraylist 
		 * @param root
		 */
		public void inOrder(Node<E> root) {		// this is the inorder traversal, it first goes to the left, process the code and goes to the right
			if ( root != null ) {
				inOrder(root.left);
				inOrder.add(root.data);			// adding to the arrayList
				inOrder(root.right);
			}
		}
		/**
		 * This is the implementation for hasNext(), it checks the index < size
		 * @return boolean
		 */
		@Override
		public boolean hasNext() {
			return (index+1) < inOrder.size();		// checks if the index is smaller the size of array
		}
		/**
		 * This method return the next element in the iteration and update the index as follows 
		 * @return E
		 * @throws NoSuchElementException
		 */
		@Override
		public E next() throws NoSuchElementException {
			// checks the exception 
			if (!hasNext()) throw new NoSuchElementException();			// gets the next element if it s there
			
			return inOrder.get(++index);			// return the element and update the index 
		}
		
	}
	/**
	 * Returns an iterator over the elements in this tree in order of the preorder traversal.
	 * This operation should be O(N).
	 * @return preItr
	 */
	public Iterator<E> preorderIterator(){
		Iterator<E> preItr = new preItr(root);			
		return preItr;
	}
	/**
	 * The is the actual implementation of preorder itertator 
	 * @author zeruiji
	 *
	 */
	private class preItr implements Iterator<E>{
		ArrayList<E> preOrder = new ArrayList<E>();
		int index;
		/**
		 * This is the constructor for iterator and set each data field to the default state
		 * @param node
		 */
		public preItr(Node<E> node) {
			this.index = -1;
			this.preOrder(node);
		}
		/**
		 * This is the actual preorder traversal
		 * it adds each element to the Arraylist 
		 * @param root
		 */
		public void preOrder(Node<E> root) {
			if ( root != null ) {
				preOrder.add(root.data); // process the code before traversal, add the element 
				preOrder(root.left);
				preOrder(root.right);
			}
		}
		/**
		 * This is the implementation for hasNext(), it checks the index < size
		 * @return boolean
		 */
		@Override
		public boolean hasNext() {
			return (index+1) < preOrder.size();
		}
		/**
		 * This method return the next element in the iteration and update the index as follows 
		 * @return E
		 * @throws NoSuchElementException
		 */
		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) throw new NoSuchElementException();   // checks the exception 
			
			return preOrder.get(++index);
		}
		
	}
	/**
	 * Returns an iterator over the elements in this tree in order of the postorder traversal.
	 * This operation should be O(N).
	 * @return
	 */
	public Iterator<E> postorderIterator(){
		Iterator<E> postItr = new postItr(root);
		return postItr;
	}
	/**
	 * This is the actual Post iteration. 
	 * It implement hasNext() and next() method and contains one constructor
	 * @author zeruiji
	 *
	 */
	private class postItr implements Iterator<E>{
		ArrayList<E> postOrder = new ArrayList<E>();	
		int index;
		/**
		 * This is the constructor for iterator and set each data field to the default state
		 * @param node
		 */
		public postItr(Node<E> node) {
			this.index = -1;
			this.postOrder(node);
		}
		/**
		 * This is the actual Postorder traversal
		 * it adds each element to the ArrayList 
		 * @param root
		 */
		public void postOrder(Node<E> root) {
			if ( root != null ) {
				postOrder(root.left);
				postOrder(root.right);
				postOrder.add(root.data);		// process the code before traversal, add the element 
			}
		}
		/**
		 * This is the implementation for hasNext(), it checks the index < size
		 * @return boolean
		 */
		@Override
		public boolean hasNext() {
			return (index+1) < postOrder.size();
		}
		/**
		 * This method return the next element in the iteration and update the index as follows 
		 * @return E
		 * @throws NoSuchElementException
		 */

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) throw new NoSuchElementException();
			
			return postOrder.get(++index);
		}
		
	}
	 private boolean found ;
	 	/**
	 	 * Removes the specified element from this tree if it is present. 
	 	 * More formally, removes an element e such that Objects.equals(o, e), if this tree contains such an element. 
	 	 * Returns true if this tree contained the element (or equivalently, if this tree changed as a result of the call). 
	 	 * (This tree will not contain the element once the call returns.)
	 	 * This operation should be O(H).
	 	 * @param o
	 	 * @return
	 	 */
	    public boolean remove(Object o) {
	    	// set the found as false 
	        found = false;
	        root = recRemove(o, root);   // calls the actual recursive step 
	        return found;   // return the update found 
	    }

	    @SuppressWarnings("unchecked")
		private Node<E> recRemove(Object target, Node<E>  node)
	    {
	        if (node == null)
	            found = false;				// check for the null element
	        // goes down the tree recursively 
	        else if (((E)target).compareTo( node.data) < 0)				// go to the left
	            node.left = recRemove(target, node.left);
	        else if (((E)target).compareTo( node.data) > 0)				// go to the right
	            node.right = recRemove(target, node.right );
	        else {
	            node = removeNode(node);	// calls the remove function 		
	            found = true;			// the node has been found 
	        }
	        return node;
	    }
	    /**
	     * Remove the Node base on the location of the node, one child, two children or leaf
	     * @param node
	     * @return
	     */
	    private Node<E> removeNode(Node<E> node)
	    {
	        E data;
	        
	        if (node.left == null)		// if the node has only one child
	            return node.right ;
	        else if (node.right  == null)		// finds out the side of the child
	            return node.left;

	        else {
	            data = getPredecessor(node.left);		// calls the predecessor function to get the previous largest node
	            node.data = data;			// store the data for the current node 
	            node.left = recRemove(data, node.left);			// detach the node 
	            return node;
	        }
	    }
	    /**
	     * This method will remove the largest node that is smaller than the current node. 
	     * @param subtree
	     * @return E
	     */
	    private E getPredecessor(Node<E> subtree)
	    {
	        Node<E> temp = subtree;   // makes a temporary reference
	        while (temp.right  != null)				// goes down the right subtree recursively 
	            temp = temp.right ;
	        return temp.data;
	    }
	/**\
	 * Returns the element at the specified position in this tree. T
	 * he order of the indexed elements is the same as provided by this tree's iterator. 
	 * The indexing is zero based (i.e., the smallest element in this tree is at index 0 and the largest one is at index size()-1).
	 * This operation should be O(H).
	 * @param index
	 * @return
	 * @throws IndexOutOfBoundsException
	 */
	public E get(int index) {
		// Checks for the exception 
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Error: the Index is either too big or too small");
		}
		// call the actual recursive method 
		E result = get(index, root);
		return result;
		
	}
	/**
	 * This method goes down the tree recursively, checks the size of the left subtree
	 * If the size of the left tree is bigger than the index, keep going down the left subtree until it is smaller 
	 * Once it is smaller, go to the right substree base on the remaining index 
	 * @param index
	 * @param root
	 * @return E 
	 */
	private E get(int index, Node<E> root) {
		if (root.left != null) {			// checks the left subtree 
			if (size(root.left) <= index) {		// checks for the size of the left subtree
				index -= size(root.left);		// decrease the index if the size of the left subtree is smaller 
			}else {
				return get( index, root.left);		// recursively go down the step 
			}
		}
			if (index == 0) {
				return root.data;			// basis step, if the index is 0, return the current element data 
			}else {
				index --;					// decrease the index by one 
				
			}
			return get(index,root.right);	// go to the right subtree 
			
		}
	
	private ArrayList<E> range = new ArrayList<E>();
	/**
	 * Returns a collection whose elements range from fromElement, inclusive, to toElement, inclusive. 
	 * The returned collection/list is backed by this tree, so changes in the returned list are reflected in this tree, and vice-versa (i.e., the two structures share elements. 
	 * The returned collection should be organized according to the natural ordering of the elements (i.e., it should be sorted).
	 * This operation should be O(M) where M is the number of elements in the returned list.
	 * @param fromElement
	 * @param toElement
	 * @return ArrayList<E> 
	 * @throws NullPointerException,IllegalArgumentException
	 */
	public ArrayList<E> getRange(E fromElement, E toElement){
		range.clear(); 				// safely clear the data field 
		if (fromElement == null|| toElement == null) {
			throw new NullPointerException("Error: do not accept null argument");	// checks the exceptions 
		}
		if (fromElement.compareTo(toElement) > 0) {
			throw new IllegalArgumentException("Error: Illegal Argument");
		}
		getRange(root, fromElement,toElement);		// calls the actual recursive step 
		
		return range;
		
	}
	/**
	 * This is the actual recursive step. The node will make comparison with the element from element and toElement.
	 * The default node is root. This will capture all elements in between 
	 * @param node
	 * @param fromElement
	 * @param toElement
	 */
	
	private void getRange(Node<E> node, E fromElement, E toElement){
		if (node == null) {    // if the root is null 
			return;
		}
		if (node.data.compareTo (fromElement) > 0) {   
			// check if the root is bigger than the from element, if it is go left until it is no longer bigger than the fromElement
			getRange(node.left, fromElement,toElement);
		}
		if (node.data.compareTo(fromElement) >= 0  && node.data.compareTo(toElement) <= 0) {
			// if the element is in the range, add them to the arrayList
			range.add(node.data);
		}
		if (toElement.compareTo(node.data) > 0) {
			// if the element is smaller than the toElement, keep going right until you meet the bound 
			getRange(node.right,fromElement,toElement);
		}
	}
	/**
	 * This function returns an array containing all the elements returned by this tree's iterator, 
	 * in the same order, stored in consecutive elements of the array, starting with index 0. 
	 * The length of the returned array is equal to the number of elements returned by the iterator.
	 * @return object[]
	 */
	public Object[] toArray() {
		Object[] toBeReturned= new Object[size()];   // create a new array of object 
		inOrder(root);								// In order traversal the entire element list and add element to arrayList
		toBeReturned = inOrder.toArray();			// convert the arrayList to array
		inOrder.clear();							// clear the arrayList
		
		return toBeReturned;
	}
	/**
	 * Returns an iterator over the elements in this tree in order of the preorder traversal.
	 * This operation should be O(N).
	 * @param tree
	 * @param level
	 * @param output
	 */
	private void preOrderPrint(Node<E> tree, int level, StringBuilder output) {
        if (tree != null) {
            String spaces = "\n";
            // properly to format the output to show the relationship between parent and children
            if (level > 0) {
                for (int i = 0; i < level - 1; i++)
                    spaces += "   ";
                spaces += "|--";
            }
            output.append(spaces);
            output.append(tree.data);
            // recursively call each branch to each element 
            preOrderPrint(tree.left, level + 1, output);
            preOrderPrint(tree.right, level + 1, output);
        }
        // uncomment the part below to show "null children" in the output
        else {
        	// again, properly format 
            String spaces = "\n";
            if (level > 0) {
                for (int i = 0; i < level - 1; i++)
                    spaces += "   ";
                spaces += "|--";
            }
            output.append(spaces);
            output.append("null");
        }
    }
	public void preOrderPrint1(){
		preOrderPrint1(root);
		
	}
	public void preOrderPrint1(Node<E> root ) {
		if (root == null) {
			return;
		}
		System.out.println(root.data);
		preOrderPrint1(root.left);
		preOrderPrint1(root.right);
	}
	@SuppressWarnings("unchecked")
	public int leafsum() {
		System.out.println(root.data);
		return leafSum((Node<Integer>) root);
	}
	public int leafSum(Node<Integer> n) {
		if (n == null) {
			return 0;
		}
		 if (n.left == null && n.right == null) {
			 return  n.data;
			}
		return leafSum(n.left) + leafSum(n.right);
		}
		//System.out.println(n.data);
		//return n.data + leafSum(n.right) +  leafSum(n.left);
	
	/**
	 * This is the wrapper class that contains the preorderprint
	 * @return String 
	 */
    public String toStringTreeFormat() {

        StringBuilder s = new StringBuilder();

        preOrderPrint(root, 0, s);
        return s.toString();
    }
	/**
	 * Returns a string representation of this tree. 
	 * The string representation consists of a list of the tree's elements in the order they are returned by its iterator (inorder traversal), enclosed in square brackets ("[]"). 
	 * Adjacent elements are separated by the characters ", " (comma and space). 
	 * Elements are converted to strings as by String.valueOf(Object).
	 * This operation should be O(N).
	 */
    ArrayList<E> inOrder = new ArrayList<E>();
    public String toString() {
    	// call the Inorder traversal to add each element into the ArrayList 
    	inOrder(root);   
    	StringBuilder s =  new StringBuilder();   // create the sting builder
    	s.append("[");			// properly format the string
    	for (E e : inOrder) {
    	// iterates through the arrayList, append each data into the arrayList
    		s.append(e);
    		s.append(", "); 	// format
    	}
 
    	String result = s.toString();			// convert into the string 
    	result = result.substring(0, s.length()-2);		// chop down the last two index
    	
    	inOrder.clear(); // clear the list
    	result = result + "]";		
    	return result;
    }
    /**
     * Inorder traverse the tree to add each element onto the list 
     * @param root
     */
    public void inOrder(Node<E> root) {
		if ( root != null ) {				// if root is not null, to avoid nullpointerexception
			inOrder(root.left);				// go to the left 
			inOrder.add(root.data);			// process 
			inOrder(root.right);			// go to the right 
		}
	}
	
    
	
	
}
