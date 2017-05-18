import java.awt.Point;
import java.util.ArrayList;


/**
<<<<<<< HEAD
 * Classe implémentant un graphe sous forme de matrice d'adjacence.
=======
 * Classe implÃ©mentant un graphe sous forme de matrice d'adjacence.
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
 * @see Graphe
 * @author damien
 */
public class GrapheMatrice extends Graphe {

	private static final long serialVersionUID = 7980039478265577744L;
	
	private Arc graphe[][];
	private ArrayList<Sommet> sommets;
	
	/**
<<<<<<< HEAD
	 * Génére un graphe sous forme de matrice vide
=======
	 * GÃ©nÃ©re un graphe sous forme de matrice vide
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	 * @author damien
	 */
	public GrapheMatrice(){
		this.graphe = new Arc[0][0];
		this.sommets = new ArrayList<Sommet>();
	}

	/**
	 * Ajoute un sommet au graphe</br>
	 * Agrandit le tableau en le copiant dans un tableau plus grand de 1.</br>
<<<<<<< HEAD
	 * Et ajoute le sommet à notre liste de sommets composant le graphe.
	 * @param s : Sommet ajouté au graphe
=======
	 * Et ajoute le sommet Ã  notre liste de sommets composant le graphe.
	 * @param s : Sommet ajoutÃ© au graphe
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	 * @author damien
	 */
	@Override
	public void addSommet(Sommet s) {
		Arc temp[][] = new Arc[graphe.length+1][graphe.length+1];
		System.arraycopy(graphe, 0, temp, 0, graphe.length);
		graphe = temp;
		graphe[graphe.length-1][graphe.length-1] = null;
		s.setID(graphe.length);
		sommets.add(s);
	}

	/**
	 * Ajoute un sommet au graphe</br>
	 * Agrandit le tableau en le copiant dans un tableau plus grand de 1.</br>
<<<<<<< HEAD
	 * Et ajoute le sommet à notre liste de sommets composant le graphe.</br>
	 * De plus, cette méthode crée le sommet avec l'adresse! donné.
=======
	 * Et ajoute le sommet Ã  notre liste de sommets composant le graphe.</br>
	 * De plus, cette mÃ©thode crÃ©e le sommet avec l'adresse donnÃ©.
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	 * @param p : Adresse du nouveau sommet
	 * @author damien
	 */
	@Override
	public void addSommet(Point p) {
		Arc temp[][] = new Arc[graphe.length+1][graphe.length+1];
		System.arraycopy(graphe, 0, temp, 0, graphe.length);
		graphe = temp;
		graphe[graphe.length-1][graphe.length-1] = null;
		Sommet s = new Sommet(p);
		s.setID(graphe.length);
		sommets.add(s);

	}

	
	/**
<<<<<<< HEAD
	 * Crée un arc entre les sommets d et a.</br>
	 * Ne fais rien si l'arc existe déjà.</br>
	 * Ne fais rien si les sommets ne se trouvent pas dans le graphe.
	 * @param d : Sommet de départ de l'arc
	 * @param a : Sommet d'arrivée de l'arc
=======
	 * CrÃ©e un arc entre les sommets d et a.</br>
	 * Ne fais rien si l'arc existe dÃ©jÃ .</br>
	 * Ne fais rien si les sommets ne se trouvent pas dans le graphe.
	 * @param d : Sommet de dÃ©part de l'arc
	 * @param a : Sommet d'arrivÃ©e de l'arc
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	 * @author damien
	 */
	@Override
	public void addArc(Sommet d, Sommet a) {
		if(sommets.contains(d) && sommets.contains(a)){
			if(graphe[d.getId()][a.getId()] != null){
				graphe[d.getId()][a.getId()] = new Arc(d, a);
<<<<<<< HEAD
				this.setNbSommets(this.getNbSommets()+1);
=======
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
			}
		}
		
	}

	/**
	 * Supprime l'arc du tableau.
	 * </br>Ne fais rien si les sommets d et a n'appartiennent pas au graphe.
<<<<<<< HEAD
	 * @param d : Sommet de départ de l'arc
	 * @param a : Sommet d'arrivée de l'arc
=======
	 * @param d : Sommet de dÃ©part de l'arc
	 * @param a : Sommet d'arrivÃ©e de l'arc
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	 * @author damien
	 */
	@Override
	public void deleteArc(Sommet d, Sommet a) {
		if(sommets.contains(d) && sommets.contains(a)){
			graphe[d.getId()][a.getId()] = null;
<<<<<<< HEAD
			this.setNbSommets(this.getNbSommets()-1);
=======
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
		}
	}

	/**
	 * Supprime l'arc par ID</br>
<<<<<<< HEAD
	 * Recherche tous le graphe à la recherche de l'arc</br>
	 * Demande plus de ressources que la suppression en donnant les sommets.
	 * @param id : Identifiant de l'arc à supprimer
=======
	 * Recherche tous le graphe Ã  la recherche de l'arc</br>
	 * Demande plus de ressources que la suppression en donnant les sommets.
	 * @param id : Identifiant de l'arc Ã  supprimer
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	 * @author damien
	 */
	@Override
	public void deleteArc(int id) {
		for(int i = 0; i<graphe.length; i++){
			for(int j = 0; j<graphe.length; j++){
				if(graphe[i][j] != null && graphe[i][j].getId() == id){
					graphe[i][j] = null;
<<<<<<< HEAD
					this.setNbSommets(this.getNbSommets()-1);
=======
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
				}
			}
		}
	}

<<<<<<< HEAD
	/**
	 * Supprime un sommet et refait les ID de tous les autres pour qu'ils corresondent.
	 * @param id : Identifiant du sommet
	 * @author damien
	 */
	@Override
	public void deleteSommet(int id){
		 /**
		  * Supprimer le sommet du tableau
		 */
=======

	/**
	 * Envoi true si l'arc existe dans le graphe, et false sinon</br>
	 * Renvoi false aussi si les sommets spÃ©cifiÃ©s ne font pas partie du graphe.
	 * @param id : Identifiant du sommet à supprimer
	 * @author Aziz
	 */
	@Override
	public void deleteSommet(int id){
		/* Supprimer le sommet du tableau */
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
		for(Sommet act : sommets){
			if(act.getId() == id){
				sommets.remove(act);
				this.setNbSommets(this.getNbSommets()-1);
			}
		}
<<<<<<< HEAD
		/**
		 * supprimer les arcs qui sont attach�s au sommet
		 */
		for(int i = id; i<graphe.length; i++){
			for(int  j = 0;j<graphe[0].length-1; j++){
				if(graphe[i][j] != null){
			this.deleteArc(graphe[i][j].getId());
			this.setNbArcs(this.getNbArcs()-1);
			}
				if(graphe[j][i] != null){
			this.deleteArc(graphe[j][i].getId());
			this.setNbArcs(this.getNbArcs()-1);
				}
		}
			}
		/**
		 *  refaire les id des arcs qui viennent apr�s le id du sommet supprim�
		*/
		for(int i = 0; i<graphe.length; i++){
			for(int  j = 0;j<graphe[0].length-1; j++){
				if(graphe[i][j] != null){
					graphe[i][j].setId(graphe[i][j].getId()-1);	
				}
			}
		}
		 /**
		  *   il faut aussi refaire les id des sommets qui viennent apr�s le id du sommet supprim�
		  */
=======

		/* supprimer les arcs qui sont attachés au sommet*/
		for(int i = id; i<graphe.length; i++){
			for(int  j = 0;j<graphe[0].length-1; j++){
				if(graphe[i][j] != null){
					this.deleteArc(graphe[i][j].getId());
					this.setNbArcs(this.getNbArcs()-1);
				}
				if(graphe[j][i] != null){
					this.deleteArc(graphe[j][i].getId());
					this.setNbArcs(this.getNbArcs()-1);
				}
			}
		}
		/*  refaire les id des arcs qui viennent après le id du sommet supprimé*/
		for(int i = 0; i<graphe.length; i++){
			for(int  j = 0;j<graphe[0].length-1; j++){
				if(graphe[i][j] != null){
					graphe[i][j].setID(graphe[i][j].getId()-1);
				}
			}
		}
		/*il faut aussi refaire les id des sommets qui viennent après le id du sommet supprimé*/
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
		for(int i = graphe[0].length; id<i; i--){
			sommets.get(i).setID(sommets.get(i).getId()-1);
		}
	}
<<<<<<< HEAD
	/**
	 * Envoi true si l'arc existe dans le graphe, et false sinon</br>
	 * Renvoi false aussi si les sommets spécifiés ne font pas partie du graphe.
	 * @param d : Sommet de départ de l'arc
	 * @param a : Sommet d'arrivée de l'arc
=======
	
	
	
	/**
	 * Envoi true si l'arc existe dans le graphe, et false sinon</br>
	 * Renvoi false aussi si les sommets spÃ©cifiÃ©s ne font pas partie du graphe.
	 * @param d : Sommet de dÃ©part de l'arc
	 * @param a : Sommet d'arrivÃ©e de l'arc
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	 * @author damien
	 */
	@Override
	public boolean existArc(Sommet d, Sommet a) {
		if(sommets.contains(d) && sommets.contains(a)){
			if(graphe[d.getId()][a.getId()] != null){
				return true;
			}
		}
		return false;
<<<<<<< HEAD
	}

	/**
	 * Renvoie le sommet identifié.
	 * </br>Renvoie null si le sommet n'existe pas dans le graphe.
	 * @param id : Identifiant du sommet recherché
	 * @author damien
	 * @return Sommet : Sommet recherché identifié
=======
}

	/**
	 * Renvoie le sommet identifiÃ©.
	 * </br>Renvoie null si le sommet n'existe pas dans le graphe.
	 * @param id : Identifiant du sommet recherchÃ©
	 * @author damien
	 * @return Sommet : Sommet recherchÃ© identifiÃ©
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	 */
	@Override
	public Sommet getSommet(int id) {
		for(Sommet act : sommets){
			if(act.getId() == id){
				return act;
			}
		}
		return null;
	}

	/**
<<<<<<< HEAD
	 * Renvoie l'arc identifié par les sommets d et a.</br>
	 * Renvoie null si l'arc n'existe pas ou si les sommets d ou a ne font pas partie du graphe
	 * @param d : Sommet de départ de l'arc recherché
	 * @param a : Sommet d'arrivée de l'arc recherché
=======
	 * Renvoie l'arc identifiÃ© par les sommets d et a.</br>
	 * Renvoie null si l'arc n'existe pas ou si les sommets d ou a ne font pas partie du graphe
	 * @param d : Sommet de dÃ©part de l'arc recherchÃ©
	 * @param a : Sommet d'arrivÃ©e de l'arc recherchÃ©
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	 * @return Arc
	 * @author damien
	 */
	@Override
	public Arc getArc(Sommet d, Sommet a) {
		if(sommets.contains(d) && sommets.contains(a)){
			if(graphe[d.getId()][a.getId()] != null){
				return graphe[d.getId()][a.getId()];
			}
		}
		return null;
	}

	/**
<<<<<<< HEAD
	 * Renvoie l'arc identifié par l'identifiant.</br>
	 * Renvoie null si l'arc n'existe pas.
	 * </br>Parcours le graphe pour obtenir l'arc concerné, la recherche en donnant les sommets de départ et d'arrivée est moins couteuses.
	 * @param d : Sommet de départ de l'arc recherché
	 * @param a : Sommet d'arrivée de l'arc recherché
=======
	 * Renvoie l'arc identifiÃ© par l'identifiant.</br>
	 * Renvoie null si l'arc n'existe pas.
	 * </br>Parcours le graphe pour obtenir l'arc concernÃ©, la recherche en donnant les sommets de dÃ©part et d'arrivÃ©e est moins couteuses.
	 * @param id : Identifiant de l'arc recherché
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	 * @return Arc
	 * @author damien
	 */
	@Override
	public Arc getArc(int id) {
		for(int i = 0; i<graphe.length; i++){
			for(int j = 0; j<graphe.length; j++){
				if(graphe[i][j] != null && graphe[i][j].getId() == id){
					return graphe[i][j];
				}
			}
		}
		return null;
	}

	@Override
	public Graphe changement_format() {
		// TODO Auto-generated method stub
		return null;
	}

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
<<<<<<< HEAD

=======
	
>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	@Override
	public ArrayList<Sommet> liste_voisins_pere_et_fils(Sommet s) {
		// TODO Auto-generated method stub
		return null;
	}
<<<<<<< HEAD
	
=======

>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
	@Override
	public ArrayList<Sommet> get_liste_de_sommet() {
		return sommets;
	}
<<<<<<< HEAD
	
	@Override
	public ArrayList<Arc> get_liste_arc() {
		return null;
	}
=======


	@Override
	public ArrayList<Arc> get_liste_arc() {
		// TODO Auto-generated method stub
		return null;
	}

>>>>>>> 8558d35c241c63273d73b3acdd9379cd54280117
}
