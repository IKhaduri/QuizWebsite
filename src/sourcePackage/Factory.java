package sourcePackage;

public class Factory {
	
	public static Database getDBObject() {
		return new Database();
	}
	
}
