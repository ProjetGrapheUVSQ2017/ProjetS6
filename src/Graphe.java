import java.awt.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Classe abstraite représentant un graphe
 * @author Damien
 *
 */
public abstract class Graphe implements Serializable {

	
	private static final long serialVersionUID = 1L;

	/**
	 * Nombre de sommets du graphe
	 */
	private int nbSommets=0;
	/**
	 * Nombre d'arcs du graphe
	 */
	private int nbArcs=0;
	
	/**
	 * Getter du nombre de sommets du graphe
	 * @return int Nombre de sommets actuel
	 */
	public int getNbSommets(){
		return nbSommets;
	}
	
	/**
	 * Getter du nombre d'arcs se trouvant dans le graphe
	 * @return int nombre d'arcs
	 */
	public int getNbArcs(){
		return nbArcs;
	}
	
	/**
	 * Setter du nombre de sommets du graphe
	 * @param s int
	 */
	public void setNbSommets(int s){
		this.nbSommets = s;
	}
	
	/**
	 * Setter du nombre d'arcs du graphe
	 * @param a int
	 */
	public void setNbArcs(int a){
		this.nbArcs = a;
	}

	/**
	 * Reset les couleurs des arcs et sommets en noir
	 */
	public void reset_couleur_graph() {
		Color resetCol = Color.black;
		for(Arc arc : this.get_liste_arc()){
			arc.setCouleur(resetCol);
		}
		for(Sommet s : this.get_liste_de_sommet()){
			s.setCouleur(resetCol);
		}
	}

	public abstract void addSommet(Sommet s);
	public abstract ArrayList<Sommet> get_liste_de_sommet();
	public abstract ArrayList<Arc> get_liste_arc();
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

	/* Algorithmes de problèmes de graphe */
	public abstract boolean dijkstra(Sommet d, Sommet a);
	public abstract boolean bellman_ford(Sommet d, Sommet a);
	public abstract boolean ford_fulkerson(Sommet d, Sommet a);
	public abstract boolean kruskall();
	public abstract boolean welsh_powell();
	public abstract boolean dsatur();
	public abstract boolean kosaraju();
	public abstract boolean tarjan();
	
	/* Gestion des fichiers */
	
	/**
	 * Méthode sauvegardant le graphe sous forme de fichier
	 * @param fichier File 
	 * @see File
	 * @see FileOutputStream
	 * @see ObjectOutputStream
	 */
	public void sauvegarder(File fichier){
		try{
			FileOutputStream fos = new FileOutputStream(fichier);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.close();
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Méthode statique permettant de charger un graphe depuis un fichier
	 * @param fichier File
	 * @see FileInputStream
	 * @see ObjectInputStream
	 * @see File
	 * @return Graphe Graphe chargé depuis la mémoire, il se trouve au même format qu'au moment de la sauvegarde
	 */
	public static Graphe charger(File fichier){
		Graphe newGraphe = null;
		try{
			FileInputStream fis = new FileInputStream(fichier);
			ObjectInputStream ois = new ObjectInputStream(fis);
			newGraphe = (Graphe) ois.readObject();
			ois.close();
			return newGraphe;
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return newGraphe;
	}

	public abstract ArrayList<Sommet> liste_voisins_pere_et_fils(Sommet s);
}
