import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
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
	public void testAjoutsommetNull(){
		graphe.addSommet((Sommet)null);
		assertTrue("Sommet null ajout�", graphe.getNbSommets() == 0);
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
		
		//TODO: Le Syst�me d'ID non fonctionnel sur les sommets fausse les r�sultats !
		
		assertNotNull("d est null", d);
		assertNotNull("a est null", a);
		
		assertFalse("Sommet d�part et sommet arriv�e sont les m�mes", d.equals(a));
		
		graphe.addArc(d, a);
		assertTrue("Arc non cr�e correctement", graphe.existArc(d, a));
		graphe.deleteArc(d, a);
		assertFalse("Arc non supprim� correctement", graphe.existArc(d, a));
	}
	
	@Test
	public void testDSATUR(){
		//TODO: Test de l'algorithme de DSatur
		assertTrue("Test non impl�ment�", false);
	}

}
