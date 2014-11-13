package edu.sjsu.cmpe.cache.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Client {

    public static void main(String[] args) throws Exception {
        System.out.println("-----------Starting Cache Client...---------------");
       /* CacheServiceInterface cache = new DistributedCacheService(
                "http://localhost:3000");

        cache.put(1, "foo");
        System.out.println("put(1 => foo)");

        String value = cache.get(1);
        System.out.println("get(1) => " + value);
*/              
        CacheServiceInterface cache0 = new DistributedCacheService(
                "http://localhost:3000");
        CacheServiceInterface cache1 = new DistributedCacheService(
                "http://localhost:3001");
        CacheServiceInterface cache2 = new DistributedCacheService(
                "http://localhost:3002");
        
        ArrayList<CacheServiceInterface> list = new ArrayList<CacheServiceInterface>();
        list.add(cache0);
        list.add(cache1);
        list.add(cache2);
        
        ConsistentHash ch = new ConsistentHash<CacheServiceInterface>(new HashFunction<String>(), 10,list ); 
        
        System.out.println("----------Start of inserting ten keys to cache...-------------");
        //put
        for(int i=1;i < 11; i++){
        	CacheServiceInterface c = (CacheServiceInterface) ch.get(i);
        	//System.out.println("Node returned " + c.toString());
        	String val = Character.toString((char)(64+i)); 
        	System.out.println("Adding value "+ val + " and key "+ i+" to " +c.toString());
        	c.put(i, val);
        }
        
        System.out.println("------------End of inserting ten keys to cache...----------------");
        
        //getAll
        System.out.println("----------------Retrieving ten keys...--------------");
        for(int i =0;i<list.size();i++)
        {
        	System.out.println("Server "+ list.get(i));
        	CacheServiceInterface cacheget = list.get(i);
        	System.out.println(cacheget.getAll());
        }
      
        System.out.println("------------------Exiting Cache Client...---------------");
        
    }

}
