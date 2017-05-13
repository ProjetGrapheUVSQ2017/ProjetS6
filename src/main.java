import java.awt.Point;
import java.util.ArrayList;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graphe graphe = new GrapheListe();
		Sommet s1 = new Sommet(new Point(11, 12));
		Sommet s2 = new Sommet(new Point(11, 12));
		Point p1 = new Point(1,2);
		graphe.addSommet(s1);
		graphe.addSommet(s2);
		graphe.addSommet(p1);
		graphe.addArc(s1, s2);
		System.out.println(graphe.getSommet(0).getId());
		System.out.println(graphe.getSommet(1).getId());
		System.out.println(graphe.getSommet(2).getId());
		
		ArrayList <Sommet> a= graphe.get_liste_de_sommet();
		
		for (int i=0;i<a.size();i++){
			System.out.println(a.get(i).getId());
		}
		graphe.dsatur();
		/*
		System.out.println(graphe.getSommet(1).getId());
		System.out.println(graphe.getNbSommets());
		graphe.dsatur();
		System.out.println("prout");
		graphe.setNbSommets(graphe.getNbSommets()+1);
		System.out.println(graphe.getNbSommets());
		//System.out.println(graphe.getSommet(0).getCouleur());
		 * */

		
	}

}
