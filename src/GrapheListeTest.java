import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;


public class GrapheListeTest {

	private Graphe graphe;
	
	@Before
	public void init(){
		graphe = new GrapheListe();
	}
	
	@Test
	public void testCreation() {
		assertTrue("Création raté du graphe vide", graphe != null);
	}
	
	@Test
	public void testAjoutSommet(){
		graphe.addSommet(new Sommet(new Point(10, 11)));
		assertTrue("Sommet crée incorrectement x", graphe.getSommet(0).getPoint().x == 10);
		assertTrue("Sommet crée incorrectement y", graphe.getSommet(0).getPoint().y == 11);
	}

}
