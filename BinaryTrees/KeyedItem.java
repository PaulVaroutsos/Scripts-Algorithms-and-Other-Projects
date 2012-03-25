/**
 * This interface represents objects that have keys.  This allows
 * BinarySearchTree to operate by getting keys and comparing them.
 * This allows the items to be compared and/or sorted
 * 
 * Objects that implement the KeyedItem class must implement the
 * getKey() method that returns a comparable.
 * 
 * @author Paul Varoutsos
 * November 2008
 *
 */
public interface KeyedItem {
	
	public Comparable getKey();
}
