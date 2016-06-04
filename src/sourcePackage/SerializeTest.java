package sourcePackage;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SerializeTest implements Serializable {
	// a simple useless nonsense class
	// to test serialization and
	// deserialization
	/*class TestingClass implements Serializable {
		
		private static final long serialVersionUID = 1L;
		private String name = null;
		public int num = 0;
		public ArrayList<String> list = null;

	
		public TestingClass() {
			list = new ArrayList<String>();
			num = 0;
			name = "Default";
		}

		public TestingClass(String a) {
			list = new ArrayList<String>();
			name = a;
			num = a.length();
		}

		public void addTo(String a) {
			list.add(a);
			addOne();
		}

		private void addOne() {
			num++;
		}

	}*/
	class SomeClass implements Serializable {

	    private final static long serialVersionUID = 1; // See Nick's comment below

	    int i    = Integer.MAX_VALUE;
	    String s = "ABCDEFGHIJKLMNOP";
	    Double d = new Double( -1.0 );
	    public String toString(){
	        return  "SomeClass instance says: " 
	              + "my data is i = " + i  
	              + ", s = " + s + ", d = " + d;
	    }
	}
	/*private TestingClass t1, t2;

	@Before
	public void setUp() throws Exception {
		t1 = new TestingClass();
		t2 = new TestingClass("other testing class");
	
	}

	@Test
	public void test() {
		String serialT1 = SerializeMedium.toSerial(t1);
		String serialT2 = SerializeMedium.toSerial(t2);
		TestingClass t3 = (TestingClass) SerializeMedium.toObject(serialT1);
		TestingClass t4 = (TestingClass) SerializeMedium.toObject(serialT2);
	
		assertEquals(t3.name,t1.name); 
		assertEquals(t4.name, t2.name);
		
	}
*/ @Test
	public void test1() throws Throwable {
		String string = Serialization.toString( new SomeClass() );
	    System.out.println(" Encoded serialized version " );
	    System.out.println( string );
	    SomeClass some = ( SomeClass ) Serialization.fromString( string );
	    System.out.println( "\n\nReconstituted object");
	    System.out.println( some );
	}
	
	
}
