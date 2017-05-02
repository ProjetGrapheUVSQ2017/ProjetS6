import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

public abstract class Graphe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private int nbSommets;
	private int nbArcs;
	
	public int getNbSommets(){
		return nbSommets;
	}
	
	public int getNbArcs(){
		return nbArcs;
	}
	
	public void setNbSommets(int s){
		this.nbSommets = s;
	}
	
	public void setNbArcs(int a){
		this.nbArcs = a;
	}
	
	public abstract void addSommet(Sommet s);
	public abstract void addSommet(Point p);
	public abstract void addArc(Sommet d, Sommet a);
	public abstract void deleteArc(Sommet d, Sommet a);
	public abstract void deleteArc(int id);
	public abstract void deleteSommet(int id);
	public abstract boolean existArc(Sommet d, Sommet a);
	public abstract Sommet getSommet(int id);
	public abstract Arc getArc(Sommet d, Sommet a);
	public abstract Arc getArc(int id);
	public abstract Graphe changement_format();
	public abstract void creer_sous_graphe(ArrayList<Sommet> s);

	/** Algorithmes de problèmes de graphe **/
	public abstract boolean dijkstra(Sommet d, Sommet a);
	public abstract boolean bellman_ford(Sommet d, Sommet a);
	public abstract boolean ford_fulkerson(Sommet d, Sommet a);
	public abstract boolean kruskall();
	public abstract boolean welsh_powell();
	public abstract boolean dsatur();
	public abstract boolean kosaraju();
	public abstract boolean tarjan();
	
	/** Gestion des fichiers **/
	
	public void sauvegarder(String fichier){
		//TODO: Ecrire la sauvegarde
	}
	
	public static Graphe charger(String fichier){
		//TODO: Ecrire le chargement
		return null;
	}
}
