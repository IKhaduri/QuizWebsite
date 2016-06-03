package sourcePackage;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializeMedium {
	/**
	 * This method uses the standard java serialization technique as taught on
	 * oracle.com and stackoverflow
	 * 
	 * @param obj- object that needs to be serialized
	 * @return string serialization of object passed
	 * 
	 */
	public static String toSerial(Object obj) {
		String res = "";
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream outputStream = new ObjectOutputStream(byteStream);
			outputStream.writeObject(obj);
			outputStream.flush();
			res = byteStream.toString();
		} catch (Exception ignore) {
			// this exception simply can't be solved as we don't
			// know how to
		}
		return res;
	}

	/**
	 * This method uses the standard java deserialization technique as taught on
	 * oracle.com and stackoverflow
	 * 
	 * @param serial - string that needs to be deserialized
	 * @return object deserialization of object passed
	 * 
	 */
	public static Object toObject(String serial) {
		Object obj = null;
		try {
			byte b[] = serial.getBytes();
			ByteArrayInputStream bi = new ByteArrayInputStream(b);
			ObjectInputStream si = new ObjectInputStream(bi);
			obj = si.readObject();
		} catch (Exception ignore) {
			// this exception simply can't be solved as we don't
			// know how to
		}

		return obj;
	}

}
