package edu.sjsu.cmpe.cache.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunction<T> {
		
	int hash(T key) throws IOException, NoSuchAlgorithmException {
		
		//convert input to byte array
		ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(key);
        byte[] data =  b.toByteArray();
        
        //hash key using MD5 algorithm
		//System.out.println("Start MD5 Digest");
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(data);//this updates the digest using the specified byte array
    	byte[] hash = md.digest();
    	ByteBuffer wrapped = ByteBuffer.wrap(hash); // big-endian by default
    	int hashNum = wrapped.getInt(); 
    	return hashNum;
    	
	}
	}
