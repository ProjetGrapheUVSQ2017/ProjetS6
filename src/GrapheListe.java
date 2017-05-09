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
	 * Crï¿½e un Graphe sous forme de liste vide
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
		if(s != null){
			sommets.add(s);
			this.setNbSommets(getNbSommets()+1);
		}
	}

	/**
	 * Ajoute un sommet se trouvant ï¿½ l'adresse p au graphe
	 * @author Damien
	 */
	@Override
	public void addSommet(Point p) {
		sommets.add(new Sommet(p));
		this.setNbSommets(getNbSommets()+1);
	}

	/**
	 * Ajoute un arc entre le Sommet d et le Sommet a au graphe</br>
	 * VÃ©rifie si les sommets d et a font partie du graphe.
	 * @param d : Sommet de dï¿½part de l'arc
	 * @param a : Sommet d'arrivï¿½e de l'arc
	 * @author Damien
	 */
	@Override
	public void addArc(Sommet d, Sommet a) {
		if(sommets.contains(d) && sommets.contains(a)){
			arcs.add(new Arc(d, a));
		}

	}

	/**
	 * On cherche l'arc correspondant et on le supprime de la liste d'arcs, le GC s'occupe du reste.
	 * @param d : Sommet de dï¿½part de l'arc ï¿½ supprimer
	 * @param a : Sommet d'arrivï¿½ de l'arc ï¿½ supprimer
	 * @author Damien
	 */
	@Override
	public void deleteArc(Sommet d, Sommet a) {
		Arc aSupprimer = null;
		for(Arc act : arcs){
			if(act.getSommetDepart().equals(d) && act.getSommetArrivee().equals(a)){
				aSupprimer = act;
			}
		}
		
		if(aSupprimer != null){
			arcs.remove(aSupprimer);
		}
	}

	/**
	 * Supprime l'arc id dans le graphe
	 * @param id : Identifiant de l'arc ï¿½ supprimer
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
				this.setNbSommets(getNbSommets()-1);
			}
			
		}
	}

	/**
	 * Renvoie vrai si l'arc existe dans le graphe, faux sinon
	 * @param d : Sommet de dï¿½part de l'arc
	 * @param a : Sommet d'arrivï¿½e de l'arc
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
	 * Renvoie le sommet identifiï¿½ par id.</br>
	 * Renvoi null si le sommet n'existe pas dans le graphe.
	 * @param id : Identifiant du sommet recherché dans la liste
	 * @return Sommet
	 * @author Damien
	 */
	@Override
	public Sommet getSommet(int id) {
		return this.sommets.get(id);
	}

	/**
	 * Renvoi l'arc identifiï¿½ par d et a.</br>
	 * Renvoi null si l'arc n'existe pas.
	 * @param d : Sommet de dï¿½part de l'arc
	 * @param a : Sommet d'arrivï¿½e de l'arc
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
	 * Renvoi l'arc identifiï¿½ par id.</br>
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
	 * Changement du format du graphe vers le format GrapheMatrice, reprï¿½sentant le graphe sous forme de matrice d'adjacence.
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
	 * Crï¿½e un sous-graphe composï¿½ des sommets donnï¿½s dans s et des arcs entre eux.
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
