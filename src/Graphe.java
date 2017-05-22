import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public abstract class Graphe implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private int nbSommets=0;
	private int nbArcs=0;
	
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

	public void reset_couleur_graph() {
		for(Arc arc : this.get_liste_arc()){
			arc.setCouleur(Color.BLACK);
		}
		for(Sommet s : this.get_liste_de_sommet()){
			s.setCouleur(Color.BLACK);
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

	/** Algorithmes de probl�mes de graphe **/
	public abstract boolean dijkstra(Sommet d, Sommet a);
	public abstract boolean bellman_ford(Sommet d, Sommet a);
	public abstract boolean ford_fulkerson(Sommet d, Sommet a);
	public abstract boolean kruskall();
	public abstract boolean welsh_powell();
	public abstract boolean dsatur();
	public abstract boolean kosaraju();
	public abstract boolean tarjan();
	
	/** Gestion des fichiers **/
	
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
