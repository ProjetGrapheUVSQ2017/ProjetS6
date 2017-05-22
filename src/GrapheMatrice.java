import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;


/**
 * Classe implémentant un graphe sous forme de matrice d'adjacence.
 * @see Graphe
 * @author damien
 */
public class GrapheMatrice extends Graphe {

	private static final long serialVersionUID = 7980039478265577744L;
	
	private Arc graphe[][];
	private ArrayList<Sommet> sommets;
	
	/**
	 * Génére un graphe sous forme de matrice vide
	 * @author damien
	 */
	public GrapheMatrice(){
		this.graphe = new Arc[0][0];
		this.sommets = new ArrayList<Sommet>();
	}

	public GrapheMatrice(Graphe graphe) {
		this.graphe = new Arc[graphe.getNbSommets()][graphe.getNbSommets()];
		this.sommets = new ArrayList<Sommet>(graphe.get_liste_de_sommet());
		for(Arc a : graphe.get_liste_arc()){
			this.addArc(a.getSommetDepart(),a.getSommetArrivee());
		}
	}

	/**
	 * Ajoute un sommet au graphe</br>
	 * Agrandit le tableau en le copiant dans un tableau plus grand de 1.</br>
	 * Et ajoute le sommet Ã  notre liste de sommets composant le graphe.
	 * @param s : Sommet ajouté au graphe
	 * @author damien
	 */
	@Override
	public void addSommet(Sommet s) {
		Arc temp[][] = new Arc[graphe.length+1][graphe.length+1];
		
		for(int i = 0; i<temp.length; i++){
			for(int j = 0; j<temp.length; j++){
				temp[i][j] = null;
			}
		}
		
		for(int i = 0; i<graphe.length; i++){
			System.arraycopy(graphe[i], 0, temp[i], 0, graphe[i].length);
		}
		
		graphe = temp;
		graphe[graphe.length-1][graphe.length-1] = null;
		s.setID(graphe.length-1);
		sommets.add(s);
		this.setNbSommets(this.getNbSommets()+1);
	}

	/**
	 * Ajoute un sommet au graphe</br>
	 * Appel addSommet(Sommet) qui agrandit le tableau en le copiant dans un tableau plus grand de 1.</br>
	 * Et ajoute le sommet à notre liste de sommets composant le graphe.</br>
	 * De plus, cette méthode crée le sommet avec l'adresse donné.
	 * @param p : Adresse du nouveau sommet
	 * @author damien
	 */
	@Override
	public void addSommet(Point p) {
		this.addSommet(new Sommet(p));
	}

	
	/**
	 * Crée un arc entre les sommets d et a.</br>
	 * Ne fais rien si l'arc existe déjà.</br>
	 * Ne fais rien si les sommets ne se trouvent pas dans le graphe.
	 * @param d : Sommet de départ de l'arc
	 * @param a : Sommet d'arrivée de l'arc
	 * @author damien
	 */
	@Override
	public void addArc(Sommet d, Sommet a) {
//		System.err.println("Ajout d'un arc " + d.toString() + " "+ a.toString());
//		System.err.println("d.getId() : "+ d.getId());
//		System.err.println("a.getId() : "+ a.getId());
//		System.err.println("Graphe de taille " +graphe.length);
//		System.err.println(graphe.toString());
		if(sommets.contains(d) && sommets.contains(a)){
			if(graphe[d.getId()][a.getId()] == null){
				graphe[d.getId()][a.getId()] = new Arc(d, a);
				this.setNbArcs(this.getNbArcs()+1);
				graphe[d.getId()][a.getId()].setID(getNbArcs());
			}
		}
		
	}

	/**
	 * Supprime l'arc du tableau.
	 * </br>Ne fais rien si les sommets d et a n'appartiennent pas au graphe.
	 * @param d : Sommet de départ de l'arc
	 * @param a : Sommet d'arrivée de l'arc
	 * @author damien
	 */
	@Override
	public void deleteArc(Sommet d, Sommet a) {
		if(sommets.contains(d) && sommets.contains(a)){
			graphe[d.getId()][a.getId()] = null;
			this.setNbArcs(this.getNbArcs()-1);
		}
	}

	/**
	 * Supprime l'arc par ID</br>
	 * Recherche tous le graphe à la recherche de l'arc</br>
	 * Demande plus de ressources que la suppression en donnant les sommets.
	 * @param id : Identifiant de l'arc à supprimer
	 * @author damien
	 */
	@Override
	public void deleteArc(int id) {
		for(int i = 0; i<graphe.length; i++){
			for(int j = 0; j<graphe.length; j++){
				if(graphe[i][j] != null && graphe[i][j].getId() == id){
					graphe[i][j] = null;
					this.setNbArcs(this.getNbArcs()-1);
				}
			}
		}
	}

	/**
	 * Supprime un sommet et refait les ID de tous les autres pour qu'ils corresondent.
	 * @param id : Identifiant du sommet
	 * @author damien
	 */
	@Override
	public void deleteSommet(int id){
		//On repère le sommet à supprimer
		Sommet aSupprimer = null;
		for(Sommet s : sommets){
			if(s.getId() == id){
				aSupprimer = s;
			}
		}
		
		//On supprime tous les arcs liés au sommet à supprimer
		for(int i = 0; i<graphe.length; i++){
			for(int j = 0; j<graphe.length; j++){
				if(graphe[i][j] != null){
					if(graphe[i][j].getSommetArrivee().equals(aSupprimer) || graphe[i][j].getSommetDepart().equals(aSupprimer)){ //Si l'arc est lié au sommet à supprimer
						graphe[i][j] = null;
						this.setNbArcs(getNbArcs()-1);
					}
				}
			}
		}
		
		//Et on supprime le sommet de la liste des sommets du graphe
		sommets.remove(aSupprimer);
		
		
		//On stocke temporairement tous les arcs de la matrice
		ArrayList<Arc> arcTemp = new ArrayList<Arc>();
		for(int i = 0; i < graphe.length; i++){
			for(int j = 0; j < graphe.length; j++){
				if(graphe[i][j] != null){
					arcTemp.add(graphe[i][j]);
				}
			}
		}
		
		//On redéfinit les IDs de tous les sommets de 0 à nombre de sommets-1
		for(int i = 0; i < sommets.size(); i++){
			sommets.get(i).setID(i);
		}
		
		//Initialisation de la nouvelle matrice d'adjacence
		Arc[][] tableauTemp = new Arc[sommets.size()][sommets.size()];		
		for(int i = 0; i<sommets.size(); i++){
			for(int j = 0; j<sommets.size(); j++){
				tableauTemp[i][j] = null; 
			}
		}
		
		//Et on remet les arcs à leur place dans la matrice d'adjacence
		for(Arc act : arcTemp){
			tableauTemp[act.getSommetDepart().getId()][act.getSommetArrivee().getId()] = act;
		}

		this.setNbSommets(sommets.size());
		System.out.println(this.getNbSommets());
	}
	
	/**
	 * Envoi true si l'arc existe dans le graphe, et false sinon</br>
	 * Renvoi false aussi si les sommets spécifiés ne font pas partie du graphe.
	 * @param d : Sommet de départ de l'arc
	 * @param a : Sommet d'arrivée de l'arc
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
	}

	/**
	 * Renvoie le sommet identifié.
	 * </br>Renvoie null si le sommet n'existe pas dans le graphe.
	 * @param id : Identifiant du sommet recherché
	 * @author damien
	 * @return Sommet : Sommet recherché identifié
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
	 * Renvoie l'arc identifié par les sommets d et a.</br>
	 * Renvoie null si l'arc n'existe pas ou si les sommets d ou a ne font pas partie du graphe
	 * @param d : Sommet de départ de l'arc recherché
	 * @param a : Sommet d'arrivée de l'arc recherché
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
	 * Renvoie l'arc identifié par l'identifiant.</br>
	 * Renvoie null si l'arc n'existe pas.
	 * </br>Parcours le graphe pour obtenir l'arc concerné, la recherche en donnant les sommets de départ et d'arrivée est moins couteuses.
	 * @param d : Sommet de départ de l'arc recherché
	 * @param a : Sommet d'arrivée de l'arc recherché
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
		this.reset_couleur_graph();
		return false;
	}

	@Override
	public boolean bellman_ford(Sommet d, Sommet a) {
		// TODO Auto-generated method stub
		this.reset_couleur_graph();
		return false;
	}

	@Override
	public boolean ford_fulkerson(Sommet d, Sommet a) {
		// TODO Auto-generated method stub
		this.reset_couleur_graph();
		return false;
	}

	@Override
	public boolean kruskall() {
		ArrayList<Arc> ArcsNonTries=get_liste_arc();
		ArrayList<Arc> ArcsTries=new ArrayList<Arc>();

		/*initialiser la couleur de tous les arcs et des sommets en noir
		 * */
		this.reset_couleur_graph();
		/*
		 * trier les poids des arcs par ordre croissant
		 * */
		while(ArcsTries.size()!=this.getNbArcs()){
			Arc ArcMin= ArcsNonTries.get(0);
		for(int i=1;i<ArcsNonTries.size();i++){
			if(ArcsNonTries.get(i).getVarPoids()<ArcMin.getVarPoids()&& !(ArcsTries.contains(ArcsNonTries.get(i)))){
				ArcMin=ArcsNonTries.get(i);
			}
		}
		ArcsTries.add(ArcMin);
		ArcsNonTries.remove(ArcMin);
		}
		/*colorer les arcs et les sommets qui consruisent l'arbre couvrant minimal
		 * 
		 * */
		int i, num1, num2,poids=0;
		for (i = 0; i < this.getNbSommets(); i++)
			sommets.get(i).addVar(new VarInt(i));
		i = 0;
		while (i<ArcsTries.size()) {
			Arc a = ArcsTries.get(i);
			num1 = a.getSommetDepart().getVar(a.getSommetDepart().getList().size()-1).getInt();
			num2 = a.getSommetArrivee().getVar(a.getSommetArrivee().getList().size()-1).getInt();
			if (num1 != num2) {
				ArcsTries.get(i).setCouleur(Color.BLUE);
				ArcsTries.get(i).getSommetArrivee().setCouleur(Color.BLUE);
				ArcsTries.get(i).getSommetDepart().setCouleur(Color.BLUE);
				poids+=ArcsTries.get(i).getVarPoids();
				for (Sommet s : sommets)
					if (s.getVar(s.getList().size()-1).getInt() == num2) 
						{
						s.setVar(s.getList().size()-1,new VarInt(num1));
						}
			}
			i++;
		}
		for (Sommet t : sommets){
			t.removeVar(t.getList().size()-1);
		}
			return true;
	}
	
	@Override
	public boolean welsh_powell() {
		ArrayList<Sommet> acolo = new ArrayList<Sommet>(this.get_liste_de_sommet());
		int nbarc=0;
		Sommet actu, max = null;
		int nbarcmax=0;
		ArrayList<Sommet> liste_voisins;
		int color=0;
		boolean change=true;

		this.reset_couleur_graph();
		
		
		for(Sommet s: sommets){
			s.addVar(new VarInt(-1));
		}
		
		while (!acolo.isEmpty()) {
			nbarcmax=0;
			max=null;
			
			for (int i=0; i<acolo.size();i++) {
			actu=acolo.get(i);
			nbarc=0;
			nbarc=liste_voisins_pere_et_fils(actu).size();
			
			if (nbarc>nbarcmax){
				nbarcmax=nbarc;
				max=actu;
				}
			
			if (nbarc==0){
				max=actu;
				}
			
			}
			liste_voisins=liste_voisins_pere_et_fils(max);
			int compare;
			color=0;
			while (change) {
				change =false;
				for (int k=0; k<liste_voisins.size();k++){
					compare=liste_voisins.get(k).getVar(liste_voisins.get(k).getList().size()-1).getInt();
					if (compare==color){
						color=color+1;
						change=true;
					}
				}
			}
			change=true;
			this.getSommet(max.getId()).setVar(this.getSommet(max.getId()).getList().size()-1, new VarInt(color));
			for (int z=0;z<acolo.size();z++) {
				if (max.equals(acolo.get(z))) {
					acolo.remove(z);
				}
			}
		}

		//met la couleur a jour pour chaque sommet et supprime tous les dernieres variables de chaque sommet (l� o� je stockais la couleur)
		ArrayList<Color> liste_id_color = new ArrayList<Color>();
		for (Sommet s: sommets) {
			Random rand = new Random();
			int id_color = s.getVar(s.getList().size()-1).getInt();
			while(id_color > liste_id_color.size()-1){

				float r = rand.nextFloat();
				float g = rand.nextFloat();
				float b = rand.nextFloat();
				liste_id_color.add(new Color(r,g,b));
			}
			s.setCouleur(liste_id_color.get(id_color));
			s.removeVar(s.getList().size() -1);
			
		}
	
		return true;
	}

	@Override
	public boolean dsatur() {
		// TODO Auto-generated method stub
		this.reset_couleur_graph();
		return false;
	}

	@Override
	public boolean kosaraju() {
		// TODO Auto-generated method stub
		this.reset_couleur_graph();
		return false;
	}

	@Override
	public boolean tarjan() {
		// TODO Auto-generated method stub
		this.reset_couleur_graph();
		return false;
	}

	@Override
	public ArrayList<Sommet> liste_voisins_pere_et_fils(Sommet s) {
		ArrayList<Sommet> res = new ArrayList<Sommet>();
		for(int i = 0; i<graphe.length; i++){
			for(int j = 0; j<graphe[0].length; j++){
				if(graphe[i][j]!=null){
				if(graphe[i][j].getSommetArrivee().equals(s)){
				if(!res.contains(graphe[i][j].getSommetDepart())){
					res.add(graphe[i][j].getSommetDepart());
				}
			}
			else if(graphe[i][j].getSommetDepart().equals(s)){
				if(!res.contains(graphe[i][j].getSommetArrivee())){
					res.add(graphe[i][j].getSommetArrivee());
					}
				}
			}
		}
	}
		return res;
	}
	
	@Override
	public ArrayList<Sommet> get_liste_de_sommet() {
		return sommets;
	}

	@Override
	public ArrayList<Arc> get_liste_arc() {
		ArrayList<Arc> arcs=new ArrayList<Arc>();
		for(int i = 0; i<graphe.length; i++){
			for(int j = 0; j<graphe[0].length; j++){
				if(graphe[i][j] != null){
					arcs.add(graphe[i][j]);
				}
			}
		}
		return arcs;
	}
	
	@Override
	public String toString(){
		String tmp = new String();
		
		for(int i = 0; i<graphe.length; i++){
			for(int j = 0; j<graphe.length; j++){
				tmp += "[" + graphe[i][j].toString() + "]";
			}
			tmp += "\n";
		}
		
		return tmp;
	}
}
