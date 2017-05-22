import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class GrapheMatriceTest {

	private Graphe graphe;
	
	@Before
	public void creationGrapheMatrice(){
		graphe = new GrapheMatrice();
	}
	
	@Test
	public void testCreationSommet() {
		Sommet s1 = new Sommet(new Point(10, 10));
		graphe.addSommet(s1);
		assertTrue("Sommet non crée correctement dans le graphe", graphe.getSommet(0) != null);
	}
	
	@Test
	public void testCreationArc(){
		Sommet s1 = new Sommet(new Point(10, 10));
		Sommet s2 = new Sommet(new Point(11, 12));
		
		graphe.addSommet(s1);
		graphe.addSommet(s2);
		assertTrue("Sommet s1 n'existe pas", graphe.getSommet(0) != null);
		assertTrue("Sommet s2 n'existe pas", graphe.getSommet(1) != null); //Vérification de l'ajout des sommets au graphe
		
		graphe.addArc(s1, s2);
		
		assertTrue("Arc non existant", graphe.existArc(s1, s2));
	}

}
