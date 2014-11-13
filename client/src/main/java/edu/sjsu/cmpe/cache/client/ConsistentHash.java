package edu.sjsu.cmpe.cache.client;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

public class ConsistentHash<T> {

  private final HashFunction hashFunction;
  private final int numberOfReplicas;
  private final SortedMap<Integer, T> circle =
    new TreeMap<Integer, T>();

  public ConsistentHash(HashFunction hashFunction, int numberOfReplicas, Collection<T> nodes) throws NoSuchAlgorithmException, IOException {

    this.hashFunction = hashFunction;
    this.numberOfReplicas = numberOfReplicas;

    for (T node : nodes) {
      add(node);
    }
  }

  public void add(T node) throws NoSuchAlgorithmException, IOException {
    for (int i = 0; i < numberOfReplicas; i++) {
      circle.put(hashFunction.hash(node.toString() + i),
        node);
      //System.out.println("added+ " + node.toString() + " at bucket " + hashFunction.hash(node.toString() + i));
    }
  }

  public void remove(T node) throws NoSuchAlgorithmException, IOException {
    for (int i = 0; i < numberOfReplicas; i++) {
      circle.remove(hashFunction.hash(node.toString() + i));
    }
  }

  public T get(Object key) throws NoSuchAlgorithmException, IOException {
    if (circle.isEmpty()) {
      return null;
    }
    int hash = hashFunction.hash(key);
   // System.out.println("For key " + key.toString() + ", hash = " + hash);
    if (!circle.containsKey(hash)) {
      SortedMap<Integer, T> tailMap =
        circle.tailMap(hash);
    //  System.out.println("Tailmap " + tailMap.size());
      for (Iterator iterator = tailMap.keySet().iterator(); iterator.hasNext();) {
    	  Object obj = iterator.next();
          //System.out.println(obj.toString());
      }
           hash = tailMap.isEmpty() ?
             circle.firstKey() : tailMap.firstKey();
    }
    return circle.get(hash);
  } 

}