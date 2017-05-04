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
	
	@Test
	public void testAjoutArcNull(){
		graphe.addArc(null, null);
		assertFalse("L'arc (null, null) existe", graphe.existArc(null, null));
	}
	
	@Test
	public void testAjoutArc(){
		graphe.addSommet(new Point(10, 11));
		Sommet s = graphe.getSommet(0);
		assertTrue("Le sommet s n'existe pas", s != null);
		graphe.addArc(s, s);
		assertTrue("L'arc (s,s) n'existe pas.", graphe.existArc(s, s));
	}

}
