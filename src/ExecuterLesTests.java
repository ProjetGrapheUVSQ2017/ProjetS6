import junit.framework.*;

public class ExecuterLesTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Tous les tests");
		suite.addTestSuite(GrapheMatriceTest.class);
		suite.addTestSuite(GrapheListeTest.class);

		return suite;
	}

	public static void main(String args[]) {
		junit.textui.TestRunner.run(suite());
	}
}