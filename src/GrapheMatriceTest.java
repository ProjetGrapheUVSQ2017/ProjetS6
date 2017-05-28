import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;

public class GrapheMatriceTest extends TestCase{

	private Graphe graphe;
	
	@Before
	public void creationGrapheMatrice(){
		graphe = new GrapheMatrice();
	}
	
	@Test
	public void testCreation() {
		assertTrue("Création raté du graphe vide", graphe != null);
	}
	
	@Test
	public void testAjoutsommetNull(){
		graphe.addSommet((Sommet)null);
		assertTrue("Sommet null ajouté", graphe.getNbSommets() == 0);
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
		Sommet s1 = new Sommet(new Point(11, 12));
		graphe.addSommet(s1);
		assertTrue("Le sommet s n'existe pas", s != null);
		assertTrue("Le sommet s1 n'existe pas", s1 != null);
		graphe.addArc(s, s1);
		assertTrue("L'arc (s,s1) n'existe pas.", graphe.existArc(s, s1));
	}
	
	@Test
	public void testSuppressionArc(){
		graphe.addSommet(new Point(10, 11));
		graphe.addSommet(new Point(12, 13));
		
		Sommet d = graphe.getSommet(0);
		Sommet a = graphe.getSommet(1);
		
		
		assertNotNull("d est null", d);
		assertNotNull("a est null", a);
		
		assertFalse("Sommet d�part et sommet arriv�e sont les m�mes", d.equals(a));
		
		graphe.addArc(d, a);
		assertTrue("Arc non crée correctement", graphe.existArc(d, a));
		graphe.deleteArc(d, a);
		assertFalse("Arc non supprimé correctement", graphe.existArc(d, a));
	}
	
	@Test
	public void testDSATUR(){
		Sommet s1 = new Sommet(new Point(11, 12));
		Sommet s2 = new Sommet(new Point(12, 13));
		graphe.addSommet(s1);
		graphe.addSommet(s2);
		graphe.addArc(s1, s2);
		graphe.dsatur();
		assertNotNull("Mauvaise couleur", graphe.getSommet(1).getCouleur());
		assertFalse("Couleur non diff�rente", graphe.getSommet(0).getCouleur().equals(graphe.getSommet(1).getCouleur()));
	}
	
	@Test
	public void testDijkstra(){
		Sommet s1 = new Sommet(new Point(11, 12));
		Sommet s2 = new Sommet(new Point(12, 13));
		graphe.addSommet(s1);
		graphe.addSommet(s2);
		graphe.addArc(s1, s2);
		assertTrue("Plus court chemin non trouvé", graphe.dijkstra(s1, s2));
	}

	@Test
	public void testBellmanFord(){
		Sommet s1 = new Sommet(new Point(10, 10));
		Sommet s2 = new Sommet(new Point(12, 12));
		Sommet s3 = new Sommet(new Point(14, 14));
		
		graphe.addSommet(s1);
		graphe.addSommet(s2);
		graphe.addSommet(s3);
		
		assertTrue("s1 est dans le graphe", graphe.getSommet(0).equals(s1));
		assertTrue("s2 est dans le graphe", graphe.getSommet(1).equals(s2));
		assertTrue("s3 est dans le graphe", graphe.getSommet(2).equals(s3));
		
		graphe.addArc(s1, s2);
		graphe.addArc(s2, s3);
		graphe.addArc(s1, s3);
		
		assertTrue("Le plus court chemin est trouver", graphe.bellman_ford(s1, s2));
	}
	
	@Test
	public void testFordFulkerson(){
		Sommet s1 = new Sommet(new Point(10, 10));
		Sommet s2 = new Sommet(new Point(12, 12));
		Sommet s3 = new Sommet(new Point(14, 14));
		
		graphe.addSommet(s1);
		graphe.addSommet(s2);
		graphe.addSommet(s3);
		
		assertTrue("s1 est dans le graphe", graphe.getSommet(0).equals(s1));
		assertTrue("s2 est dans le graphe", graphe.getSommet(1).equals(s2));
		assertTrue("s3 est dans le graphe", graphe.getSommet(2).equals(s3));
		
		graphe.addArc(graphe.getSommet(0), graphe.getSommet(1));
		assertTrue("création arc raté", graphe.existArc(graphe.getSommet(0), graphe.getSommet(1)));
		graphe.addArc(graphe.getSommet(1), graphe.getSommet(2));
		assertTrue("création arc raté", graphe.existArc(graphe.getSommet(1), graphe.getSommet(2)));
		graphe.addArc(graphe.getSommet(0), graphe.getSommet(2));
		assertTrue("création arc raté", graphe.existArc(graphe.getSommet(0), graphe.getSommet(2)));
		
		assertTrue("Ford-Fulkerson réussi", graphe.ford_fulkerson(graphe.getSommet(0), graphe.getSommet(2)));
		assertTrue("Le poids maximal est faux", graphe.getSommet(2).getVar(0).getFloat() == 2.0);
	}
}
