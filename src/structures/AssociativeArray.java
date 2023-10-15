package structures;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K and values of type V.
 * Associative Arrays store key/value pairs and permit you to look up values by key.
 *
 * @author Maria Rodriguez
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V> pairs[];

  K image;
  V text;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({"unchecked"})
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(), DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> clone = new AssociativeArray<>();

    while (clone.pairs.length < this.pairs.length) {
      clone.expand(); // expands the clone until it is as large as the original pairs[]
    }
    clone.size = this.size;

    for (int i = 0; i < this.pairs.length; i++) {
      if (this.pairs[i] != null) { 
        //if not empty, set the clone's KVArray equal to the same pair in the original pairs[]
        clone.pairs[i] = new KVPair<>(this.pairs[i].key, this.pairs[i].value);
      } else {
        //if original pairs is empty, set the clone to null
        clone.pairs[i] = null;
      }
    }

    
    return clone;
  } // clone()

  /**
   * Convert the array to a string.
   */
  public String toString() {
    String kvArray = "{ ";
    // this is the string we are building onto

    if (this.size == 0) { // if the array is empty in the first place:
      return "{}";
    } // if

    for (int i = 0; i < size; i++) { // make string[] then convert to one string
      if (pairs[i] != null) {
        kvArray = kvArray.concat(pairs[i].key.toString() + ": " + pairs[i].value.toString());
        // prints the key value pair if it exists (is not empty)

        if (i < (size - 1)) {
          kvArray = kvArray.concat(", ");
          // adds a comma at the end of every pair
        } else {
          kvArray = kvArray.concat(" }");
          // adds the closing } at the end of the last pair
        } // if
      } // if
    } // for

    return kvArray;
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to get(key) will return value.
   */
  public void set(K key, V value) {
    if (size == 0) {
      pairs[0] = new KVPair<>(key, value);
      this.size++;
      return;
    } else {
      for (int i = 0; i < pairs.length; i++) {
        if (i == (pairs.length - 1)) {
          this.expand();
        } // if

        // if it fines an empty space, assign it the KV pair
        if (pairs[i] == null) {
          pairs[i] = new KVPair<>(key, value);
          this.size++;
          return;
        }
        // this code is for re-setting values
        else if (pairs[i].key.equals(key)) {
          pairs[i].value = value;
          return;
        } // if

      } // for

    } // else if

  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @throws KeyNotFoundException when the key does not appear in the associative array.
   */

  public V get(K key) throws KeyNotFoundException {
    int index = find(key);
    return pairs[index].value;

  } // get(K)

  /**
   * Determine if key appears in the associative array.
   */

  public boolean hasKey(K key) {
    try {
      find(key);
      return true;

    } catch (KeyNotFoundException e) {
    }

    return false;
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls to get(key) will throw an
   * exception. If the key does not appear in the associative array, does nothing.
   */
  public void remove(K key) {
    try {
      int index = find(key); // finds the key if it exists
      pairs[index] = null; // removes the pair
      this.size--; // decreases the size since we removed an element
    } catch (KeyNotFoundException e) {
    }
  } // remove(K)

  /**
   * Determine how many values are in the associative array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  public void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key. If no such entry is found,
   * throws an exception.
   */
  public int find(K key) throws KeyNotFoundException {
    for (int i = 0; i < pairs.length; i++) {
      if (pairs[i] != null) { // makes sure to check only if it isn't empty
        if (pairs[i].key.equals(key)) {
          return i; // if the keys match, return the index
        } // if
      } // if
    } // for

    throw new KeyNotFoundException(); // STUB
  } // find(K)

  public String[] keys(){
    String[] keysArray = new String[size];
    for (int i = 0; i < pairs.length; i++) {
      if (pairs[i] != null) { // makes sure to check only if it isn't empty
        keysArray[i] = (String) pairs[i].key;
      } // if
    } // for

    return keysArray;
  }

} // class AssociativeArray
