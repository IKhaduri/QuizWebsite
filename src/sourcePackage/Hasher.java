package sourcePackage;

import java.security.*;

public class Hasher {
	private static final String ALGORITHM = "SHA";
	
	/*
	 * Creates and returns a MessageDigest object.
	 */
	private static MessageDigest getHasher(){
		try{
			MessageDigest msg = MessageDigest.getInstance(ALGORITHM);
			return msg;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/*
	 * Given a byte[] array, produces a hex String,
	 * such as "234a6f". with 2 chars for each byte in the array.
	 * (copy-pasted from assignment 4)
	 */
	private static String hexToString(byte[] bytes) {
		StringBuffer buff = new StringBuffer();
		for (int i=0; i<bytes.length; i++) {
			int val = bytes[i];
			val = val & 0xff;  // remove higher bits, sign
			if (val<16) buff.append('0'); // leading 0
			buff.append(Integer.toString(val, 16));
		}
		return buff.toString();
	}
	
	/**
	 * Returns the password hash for the given user
	 * @param name - user name
	 * @param password - user's password
	 * @return password hash for the user
	 */
	public static String hash(String name, String password){
		if(name == null || password == null) 
			return null;
		MessageDigest hasher = getHasher();
		if(hasher == null)
			return null;
		byte[] nameHash = hasher.digest(name.getBytes());
		byte[] hash = hasher.digest((hexToString(nameHash) + password).getBytes());
		return(hexToString(hash));
	}
}
