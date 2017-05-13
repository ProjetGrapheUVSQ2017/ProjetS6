import java.awt.Point;
import java.util.ArrayList;

import java.awt.Color;
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
	 * Cr�e un Graphe sous forme de liste vide
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
		// Rep : Il copie l'ancienne graphe liste dans une nouvelle
	}

	/**
	 * Ajoute le sommet s au graphe
	 * @author Damien, Madeleine
	 */
	@Override
	public void addSommet(Sommet s) {
		if(s != null){
			sommets.add(s);
			this.setNbSommets(getNbSommets()+1);
			sommets.get(getNbSommets()-1).setID(getNbSommets());
		}
	}

	/**
	 * Ajoute un sommet se trouvant � l'adresse p au graphe
	 * @author Damien, Madeleine
	 */
	@Override
	public void addSommet(Point p) {
		sommets.add(new Sommet(p));
		this.setNbSommets(getNbSommets()+1);
		sommets.get(getNbSommets()-1).setID(getNbSommets());
	}

	/**
	 * Ajoute un arc entre le Sommet d et le Sommet a au graphe</br>
	 * Vérifie si les sommets d et a font partie du graphe.
	 * @param d : Sommet de d�part de l'arc
	 * @param a : Sommet d'arriv�e de l'arc
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
	 * @param d : Sommet de d�part de l'arc � supprimer
	 * @param a : Sommet d'arriv� de l'arc � supprimer
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
	 * @param id : Identifiant de l'arc � supprimer
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
	 * @param d : Sommet de d�part de l'arc
	 * @param a : Sommet d'arriv�e de l'arc
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
	 * Renvoie le sommet identifi� par id.</br>
	 * Renvoi null si le sommet n'existe pas dans le graphe.
	 * @param id : Identifiant du sommet recherch� dans la liste
	 * @return Sommet
	 * @author Damien
	 */
	@Override
	public Sommet getSommet(int id) {
		return this.sommets.get(id);
	}

	/**
	 * Renvoi l'arc identifi� par d et a.</br>
	 * Renvoi null si l'arc n'existe pas.
	 * @param d : Sommet de d�part de l'arc
	 * @param a : Sommet d'arriv�e de l'arc
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
	 * Renvoi l'arc identifi� par id.</br>
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
	 * Changement du format du graphe vers le format GrapheMatrice, repr�sentant le graphe sous forme de matrice d'adjacence.
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
	 * Cr�e un sous-graphe compos� des sommets donn�s dans s et des arcs entre eux.
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
		ArrayList<Sommet> acolo = new ArrayList<Sommet>(this.get_liste_de_sommet());
		Sommet max1 = null;
		Sommet max2 = null;
		int nbcolor=0;
		int nbcolormax=0;
		int nbarc=0;
		int nbarcmax=0;
		Sommet actu, max;
		int color=0;
		boolean change=true;
		ArrayList<Sommet> liste_voisins;
	

		
		//mettre la couleur � 0 pour tous les sommets (on stocke �a � la fin de la liste des variables)

		for (int h=0; h<this.get_liste_de_sommet().size();h++){
			this.getSommet(h).addVar(new VarInt(0));
		}
		
		while (!acolo.isEmpty()) {
			
			nbcolormax=0;
			nbarcmax=0;
			
			
			for (int i=0; i<acolo.size();i++) {
				System.out.println("dans le premier for");
				actu=acolo.get(i);
				nbarc=0;
				nbcolor=0;
				for (int j=0;j<(liste_voisins=liste_voisins_pere_et_fils(actu)).size();j++) {
					if ( liste_voisins.get(j).getVar(liste_voisins.get(j).getList().size()-1).getInt()!=0 ){
					nbcolor=nbcolor+1;
					}
				}
				
				nbarc=liste_voisins.size();
				if (nbcolor>=nbcolormax) {
					max1=actu;
					nbcolormax=nbcolor;
				}
				if (nbarc>=nbarcmax){
					nbarcmax=nbarc;
					max2=actu;
				}
			}
			System.out.println("nb color ?");
			System.out.println(nbcolormax);
			if (nbcolormax==0){
				max=max2;
				System.out.println("max=maxarc");
			}
			else {
				max=max1;
				System.out.println("max=maxcouleur");
			}
			
			System.out.println("le sommet choisi ici est :");
			System.out.println(max.getId());
			liste_voisins=liste_voisins_pere_et_fils(max);
			int compare;
			color=0;
			while (change) {
				change =false;
				for (int k=0; k<liste_voisins.size();k++){
					compare=liste_voisins.get(k).getVar(liste_voisins.get(k).getList().size()-1).getInt();
					System.out.println("voisin :");
					System.out.println(liste_voisins.get(k).getId());
					System.out.println(compare);
					if (compare==color){
						color=color+1;
						System.out.println("couleur chang�");
						change=true;
					}
				}
			}
			
			change=true;
			System.out.println("sommet num�ro trait�");
			System.out.println(max.getId());
			System.out.println(sommets.size());
			System.out.println(this.getSommet(max.getId()-1).getList().size());
			
			
			this.getSommet(max.getId()-1).setVar(this.getSommet(max.getId()-1).getList().size()-1, new VarInt(color));
			System.out.println(this.getSommet(max.getId()-1).getVar(this.getSommet(max.getId()-1).getList().size()-1).getInt());
			System.out.println("taille liste avant :");
			System.out.println(acolo.size());
			for (int z=0;z<acolo.size();z++) {
				if (max.equals(acolo.get(z))) {
					System.out.println("on remoove: ");
					System.out.println(acolo.get(z).getId());
					acolo.remove(z);
				}
			}
			System.out.println("taille liste apres :");
			System.out.println(acolo.size());
		}

			

		
		//met la couleur a jour pour chaque sommet et supprime tous les dernieres variables de chaque sommet (l� o� je stockais la couleur)
		for (int g=0;g<this.get_liste_de_sommet().size();g++) {
			this.get_liste_de_sommet().get(g).setCouleur(new Color(255% ((this.get_liste_de_sommet().get(g).getList().size() -1)+1), 255, 255));
			this.get_liste_de_sommet().get(g).removeVar(this.get_liste_de_sommet().get(g).getList().size() -1);
			
		}
		
		return true;
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


	@Override
	public ArrayList<Sommet> get_liste_de_sommet() {
		return this.sommets;
	
	}
	
	@Override
	public ArrayList<Arc> get_liste_arc() {
		return this.arcs;
}

	@Override
	public ArrayList<Arc> get_liste_arc() {
		return this.arcs;
	}

	/**
	 * Renvoie une liste de Sommet contenant les sommets reli�s par un arc � s
	 * La liste est vide si s n'a aucuns voisins.
	 * @author Damien
	 */
	@Override
	public ArrayList<Sommet> liste_voisins_pere_et_fils(Sommet s) {
		ArrayList<Sommet> res = new ArrayList<Sommet>();
		for(Arc act : arcs){
			if(act.getSommetArrivee().equals(s)){
				res.add(act.getSommetDepart());
			}
			else if(act.getSommetDepart().equals(s)){
				res.add(act.getSommetArrivee());
			}
		}
		return res;
	}

}
