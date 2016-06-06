package sourcePackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SerializeTest implements Serializable {
	// a simple useless nonsense class
	// to test serialization and
	// deserialization
	class TestClass1 implements Serializable {

		private static final long serialVersionUID = 2L;
		private String name = null;
		public int num = 0;
		public ArrayList<String> list = null;

		public TestClass1() {
			list = new ArrayList<String>();
			num = 0;
			name = "Default";
		}

		public TestClass1(String a) {
			list = new ArrayList<String>();
			name = a;
		}

		public void addTo(String a) {
			list.add(a);
			addOne();
		}

		private void addOne() {
			num++;
		}

	}
	//another simple useless class
	//
	class TestClass2 implements Serializable {

		private final static long serialVersionUID = 1;

		private int i = Integer.MAX_VALUE;
		private String s = "QWERTYUIOPASDFGHJKLZXCVBNM";
		private Double d = new Double(-1.0);

		public int getI() {
			return i;
		}

		public String getS() {
			return s;
		}

		public double getD() {
			return d;
		}
	}

	private TestClass1 t1, t2;
	private String[] arr = { "asd", "loves", "hates", "sees", "knows", "looks for", "finds", "runs", "jumps", "talks",
			"sleeps", "man", "woman", "fish", "elephant", "unicorn", "Fred", "Jane", "Richard Nixon", "Miss America" };

	@Before
	public void setUp() throws Exception {
		t1 = new TestClass1();
		t2 = new TestClass1("other testing class");
		for (String s : arr){
			t1.addTo(s);
		}
	}
	//tests serialization for first class
	@Test
	public void test() throws Throwable {
		int X1 = t1.num;
		int X2 = t2.num;
		String serialT1 = Serialization.toString(t1);
		String serialT2 = Serialization.toString(t2);
		
		TestClass1 t3 = (TestClass1) Serialization.fromString(serialT1);
		TestClass1 t4 = (TestClass1) Serialization.fromString(serialT2);
		assertEquals(t3.name, t1.name);
		assertEquals(t4.name, t2.name);
		assertTrue(t3.list.equals(t1.list));
		assertTrue(t4.list.equals(t2.list));
		assertEquals(X1,t1.num);
		assertEquals(X1,t1.list.size());
		assertEquals(X1,t3.num);
		assertEquals(X1,t3.list.size());
		assertEquals(X2,t2.num);
		assertEquals(X2,t2.list.size());
		assertEquals(X2,t4.num);
		assertEquals(X2,t4.list.size());
		
	}
	//tests for second class
	@Test
	public void test1() {
		TestClass2 T = null;
		try {
			String string = Serialization.toString(new TestClass2());
			T = (TestClass2) Serialization.fromString(string);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals((double) -1.0, (double) T.getD(), 0.000000001);
		assertEquals("QWERTYUIOPASDFGHJKLZXCVBNM", T.getS());
		assertEquals(Integer.MAX_VALUE, T.getI());
	}

}
