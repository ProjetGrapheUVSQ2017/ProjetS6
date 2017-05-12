import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
	
	public abstract void addSommet(Sommet s);
	public abstract ArrayList<Sommet> get_liste_de_sommet();
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
	
	public static Graphe charger(String fichier){
		Graphe newGraphe = null;
		try{
			FileInputStream fis = new FileInputStream(fichier);
			ObjectInputStream ois = new ObjectInputStream(fis);
			newGraphe = (Graphe) ois.readObject();
			ois.close();
			System.out.println("Graphe.load : " + newGraphe.toString());
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
