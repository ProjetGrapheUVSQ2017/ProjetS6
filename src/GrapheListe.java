import java.awt.Point;
import java.util.ArrayList;

/**
 * Classe stockant le graphe sous forme de liste de sommets et de liste d'arcs.
 * @author Damien
 * @see Graphe
 */
public class GrapheListe extends Graphe {

	
	private ArrayList<Sommet> sommets;
	private ArrayList<Arc> arcs;
	private static final long serialVersionUID = -1405896813221493308L;
	
	/**
	 * Crée un Graphe sous forme de liste vide
	 * @author Damien
	 */
	public GrapheListe(){
		sommets = new ArrayList<Sommet>();
		arcs = new ArrayList<Arc>();
	}
	
	public GrapheListe(GrapheListe old){
		sommets = new ArrayList<Sommet>();
		arcs = new ArrayList<Arc>();
		//TODO: Il fait quoi ce constructeur ?
	}

	/**
	 * Ajoute le sommet s au graphe
	 * @author Damien
	 */
	@Override
	public void addSommet(Sommet s) {
		sommets.add(s);

	}

	/**
	 * Ajoute un sommet se trouvant à l'adresse p au graphe
	 * @author Damien
	 */
	@Override
	public void addSommet(Point p) {
		sommets.add(new Sommet(p));
	}

	/**
	 * Ajoute un arc entre le Sommet d et le Sommet a au graphe
	 * @param d : Sommet de départ de l'arc
	 * @param a : Sommet d'arrivée de l'arc
	 * @author Damien
	 */
	@Override
	public void addArc(Sommet d, Sommet a) {
		arcs.add(new Arc(d, a));

	}

	/**
	 * On cherche l'arc correspondant et on le supprime de la liste d'arcs, le GC s'occupe du reste.
	 * @param d : Sommet de départ de l'arc à supprimer
	 * @param a : Sommet d'arrivé de l'arc à supprimer
	 * @author Damien
	 */
	@Override
	public void deleteArc(Sommet d, Sommet a) {
		for(Arc act : arcs){
			if(act.getSommetDepart().equals(d) && act.getSommetArrivee().equals(a)){
				arcs.remove(act);
			}
		}
	}

	/**
	 * Supprime l'arc id dans le graphe
	 * @param id : Identifiant de l'arc à supprimer
	 * @author Damien
	 */
	@Override
	public void deleteArc(int id) {
		for(Arc act : arcs){
			if(act.getId() == id){
				arcs.remove(act);
			}
		}
	}

	/**
	 * Supprime le sommet id du graphe et les arcs sortant et entrant de lui.
	 * @param id : Identifiant du sommet
	 * @author Damien
	 */
	@Override
	public void deleteSommet(int id) {
		
		//TODO: Il faut supprimer les arcs en relation du sommet
		for(Sommet s : sommets){
			if(s.getId() == id){
				sommets.remove(s);
			}
		}
	}

	/**
	 * Renvoie vrai si l'arc existe dans le graphe, faux sinon
	 * @param d : Sommet de départ de l'arc
	 * @param a : Sommet d'arrivée de l'arc
	 * @return boolean
	 * @author Damien
	 */
	@Override
	public boolean existArc(Sommet d, Sommet a) {
		for(Arc act : arcs){
			if(act.getSommetDepart().equals(d) && act.getSommetArrivee().equals(a)){
				return true;
			}
		}
		return false;
	}

	/**
	 * Renvoie le sommet identifié par id.</br>
	 * Renvoi null si le sommet n'existe pas dans le graphe.
	 * @param id : Identifiant du sommet recherché
	 * @return Sommet
	 * @author Damien
	 */
	@Override
	public Sommet getSommet(int id) {
		for(Sommet s : sommets){
			if(s.getId() == id){
				return s;
			}
		}
		return null;
	}

	/**
	 * Renvoi l'arc identifié par d et a.</br>
	 * Renvoi null si l'arc n'existe pas.
	 * @param d : Sommet de départ de l'arc
	 * @param a : Sommet d'arrivée de l'arc
	 * @return Arc
	 * @author Damien
	 */
	@Override
	public Arc getArc(Sommet d, Sommet a) {
		for(Arc act : arcs){
			if(act.getSommetDepart().equals(d) && act.getSommetArrivee().equals(a)){
				return act;
			}
		}
		return null;
	}

	/**
	 * Renvoi l'arc identifié par id.</br>
	 * Renvoi null si l'arc n'existe pas.
	 * @param id : Identifiant de l'arc
	 * @return Arc
	 * @author Damien
	 */
	@Override
	public Arc getArc(int id) {
		for(Arc act : arcs){
			if(act.getId() == id){
				return act;
			}
		}
		return null;
	}

	/**
	 * Changement du format du graphe vers le format GrapheMatrice, représentant le graphe sous forme de matrice d'adjacence.
	 * @return Graphe : Le nouveau graphe sous forme de GrapheMatrice
	 * @see GrapheMatrice
	 * @author Damien
	 */
	@Override
	public Graphe changement_format() {
		// TODO: Faut faire le changement ;)
		return null;
	}

	/**
	 * Crée un sous-graphe composé des sommets donnés dans s et des arcs entre eux.
	 * @param s : Une ArrayList de sommet qui composeront le nouveau graphe
	 * @author Damien
	 * @see ArrayList
	 */
	@Override
	public void creer_sous_graphe(ArrayList<Sommet> s) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean dijkstra(Sommet d, Sommet a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean bellman_ford(Sommet d, Sommet a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean ford_fulkerson(Sommet d, Sommet a) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean kruskall() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean welsh_powell() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean dsatur() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean kosaraju() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tarjan() {
		// TODO Auto-generated method stub
		return false;
	}

}
