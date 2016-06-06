package sourcePackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;

/**
 * Usage sample serializing SomeClass instance
 */
public class Serialization {

	/**
	 * Read the object from Base64 string.
	 * as learned from stackoverflow and oracle
	 * @param serial - serialized object in string form
	 * @return object that was deserialized from string
	 * 
	 * 
	 */
	public static Object fromString(String serial) throws IOException, ClassNotFoundException {
		byte[] data = Base64.getDecoder().decode(serial);
		ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(data));
		Object obj = objectStream.readObject();
		objectStream.close();
		return obj;
	}

	/** Write the object to a Base64 string. 
	 * as learned from stackoverflow and oracle
	 * @param obj - object to be serialized
	 * @return serialized object in string form 
	 * 
	 * */
	public static String toString(Serializable obj) throws IOException {
		ByteArrayOutputStream byteOutpurStream = new ByteArrayOutputStream();
		ObjectOutputStream objOutputStream = new ObjectOutputStream(byteOutpurStream);
		objOutputStream.writeObject(obj);
		objOutputStream.close();
		return Base64.getEncoder().encodeToString(byteOutpurStream.toByteArray());
	}

}