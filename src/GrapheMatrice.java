import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
/**
 * Classe implémentant un graphe sous forme de matrice d'adjacence.
 * @see Graphe
 * @author damien
 */
public class GrapheMatrice extends Graphe {

	private static final long serialVersionUID = 7980039478265577744L;
	
	/**
	 * Matrice d'adjacence du graphe
	 */
	private Arc graphe[][];
	/**
	 * Liste des sommets dans le graphe
	 */
	private ArrayList<Sommet> sommets;
	
	/**
	 * Génére un graphe sous forme de matrice vide
	 * @author damien
	 */
	public GrapheMatrice(){
		this.graphe = new Arc[0][0];
		this.sommets = new ArrayList<Sommet>();
	}

	/**
	 * Constructeur à partir d'un graphe donnée.
	 * Attention, ne fais pas de copie mais peut travailler à partir de n'importe quel type de graphe
	 * @param graphe Ancien graphe
	 */
	public GrapheMatrice(Graphe graphe) {
		this.sommets = new ArrayList<Sommet>();
		this.graphe = new Arc[0][0];
		
		//On parcourt les sommets de l'ancien graphe et on les ajoute dans ce GrapheMatrice
		for(Sommet s : graphe.get_liste_de_sommet()){
			try{
				this.addSommet(s);
			}
			catch(NullPointerException e){
				System.err.println("Un des sommets est nul " + e.toString());
			}
		}
		
		//Puis on récupère les arcs et on copie leurs données 
		int newID = 0;
		for(Arc a : graphe.get_liste_arc()){
			this.addArc(a.getSommetDepart(),a.getSommetArrivee());
			Arc arcTemp = this.getArc(a.getSommetDepart(), a.getSommetArrivee());
			//On récupère le nouvel arc du graphe
			arcTemp.setVar(0, a.getVar(0));
			for(int i=1; i < a.getList().size();i++){
				arcTemp.addVar(a.getVar(i));
			}
			arcTemp.setID(newID); newID++;
			//et on recopie sa couleur
			arcTemp.setCouleur(a.getCouleur());
		}
		
	}

	/**
	 * Ajoute un sommet au graphe
	 * Agrandit le tableau en le copiant dans un tableau plus grand de 1.
	 * Et ajoute le sommet Ã  notre liste de sommets composant le graphe.
	 * @param s : Sommet ajouté au graphe
	 * @author damien
	 */
	@Override
	public void addSommet(Sommet s) {
		if(s != null){
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
	}

	/**
	 * Ajoute un sommet au graphe
	 * Appel addSommet(Sommet) qui agrandit le tableau en le copiant dans un tableau plus grand de 1.
	 * Et ajoute le sommet à notre liste de sommets composant le graphe.
	 * De plus, cette méthode crée le sommet avec l'adresse donné.
	 * @param p : Adresse du nouveau sommet
	 * @author damien
	 */
	@Override
	public void addSommet(Point p) {
		this.addSommet(new Sommet(p));
	}

	/**
	 * Crée un arc entre les sommets d et a.
	 * Ne fais rien si l'arc existe déjà.
	 * Ne fais rien si les sommets ne se trouvent pas dans le graphe.
	 * @param d : Sommet de départ de l'arc
	 * @param a : Sommet d'arrivée de l'arc
	 * @author damien
	 */
	@Override
	public void addArc(Sommet d, Sommet a){
		if(sommets.contains(d) && sommets.contains(a)){
			if(graphe[d.getId()][a.getId()] == null){
				graphe[d.getId()][a.getId()] = new Arc(d, a);
				this.setNbArcs(this.getNbArcs()+1);
				graphe[d.getId()][a.getId()].setID(getNbArcs()-1);
			}
		}
		
	}

	/**
	 * Supprime l'arc du tableau.
	 * Ne fais rien si les sommets d et a n'appartiennent pas au graphe.
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
		for(int n=0, i = 0; i < this.graphe.length && n!=this.getNbArcs(); i++){
			for(int j = 0; j<this.graphe[0].length; j++){
				if(this.graphe[i][j] != null ){
					this.graphe[i][j].setID(n);
					n++;
				}
			}
		}
	}

	/**
	 * Supprime l'arc par ID
	 * Recherche tous le graphe à la recherche de l'arc
	 * Demande plus de ressources que la suppression en donnant les sommets.
	 * @param id : Identifiant de l'arc à supprimer
	 * @author damien
	 */
	@Override
	public void deleteArc(int id) {
		for(int i = 0; i<this.graphe.length; i++){
			for(int j = 0; j<this.graphe.length; j++){
				if(this.graphe[i][j] != null && this.graphe[i][j].getId() == id){
					this.graphe[i][j] = null;
					this.setNbArcs(this.getNbArcs()-1);
				}
			}
		}
		for(int n=0, i = 0; i < this.graphe.length && n!=this.getNbArcs(); i++){
			for(int j = 0; j<this.graphe[0].length; j++){
				if(this.graphe[i][j] != null ){
					this.graphe[i][j].setID(n);
					n++;
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
		for(Sommet s : this.sommets){
			if(s.getId() == id){
				aSupprimer = s;
			}
		}
		
		//On supprime tous les arcs liés au sommet à supprimer
		for(int i = 0; i<this.graphe.length; i++){
			for(int j = 0; j<this.graphe.length; j++){
				if(this.graphe[i][j] != null){
					if(this.graphe[i][j].getSommetArrivee().equals(aSupprimer) || this.graphe[i][j].getSommetDepart().equals(aSupprimer)){ //Si l'arc est lié au sommet à supprimer
						this.graphe[i][j] = null;
						this.setNbArcs(getNbArcs()-1);
					}
				}
			}
		}
		
		//Et on supprime le sommet de la liste des sommets du graphe
		this.sommets.remove(aSupprimer);
		this.setNbSommets(this.getNbSommets()-1);
		//On redéfinit les IDs de tous les sommets de 0 à nombre de sommets-1
				for(int i = 0; i <this.sommets.size(); i++){
					this.sommets.get(i).setID(i);
				}
		//On redéfinit les IDs de tous les arcs de 0 à nombre de arcs-1
		for(int n=0, i = 0; i < this.graphe.length && n!=this.getNbArcs(); i++){
			for(int j = 0; j<this.graphe[0].length; j++){
				if(this.graphe[i][j] != null ){
					this.graphe[i][j].setID(n);
					n++;
				}
			}
		}
		
		//On stocke temporairement tous les arcs de la matrice
		ArrayList<Arc> arcTemp = new ArrayList<Arc>();
		for(int i = 0; i < graphe.length; i++){
			for(int j = 0; j < graphe.length; j++){
				if(graphe[i][j] != null){
					arcTemp.add(graphe[i][j]);
				}
			}
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
		
		graphe = tableauTemp;
				
	}
	
	/**
	 * Envoi true si l'arc existe dans le graphe, et false sinon
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
	 * Renvoie null si le sommet n'existe pas dans le graphe.
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
	 * Renvoie l'arc identifié par les sommets d et a.
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
	 * Renvoie l'arc identifié par l'identifiant.
	 * Renvoie null si l'arc n'existe pas.
	 * Parcours le graphe pour obtenir l'arc concerné, la recherche en donnant les sommets de départ et d'arrivée est moins couteuses.
	 * @param id : int identifiant l'arc recherché
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

	/**
	 * Fonction changeant le format à la liste en appelant le constructeur de GrapheListe avec ce graphe
	 * @return Graphe retourne le graphe en liste
	 */
	@Override
	public Graphe changement_format() {
		return new GrapheListe(this);
	}

	/**
	 * Supprime tous les sommets n'appartenant pas à s ainsi que les arcs liés
	 * @author Damien
	 */
	@Override
	public void creer_sous_graphe(ArrayList<Sommet> s) {
		ArrayList<Sommet> aSupprimer = new ArrayList<Sommet>();
		for(Sommet act : sommets){
			if(!s.contains(act)){
				aSupprimer.add(act);
			}
		}
		
		for(Sommet act : aSupprimer){
			this.deleteSommet(act.getId());
		}
	}

	/**
	 * Donne le plus court chemin en d et a s'il n'y a pas de poids négatif sur le graphe
	 * @return boolean true si le chemin a été trouver, false s'il n'y a pas de chemin ou si il y a un poids négatif sur le graphe
	 * @author Damien
	 */
	
	@Override
	public boolean dijkstra(Sommet d, Sommet a) {
		this.reset_couleur_graph();
		boolean presenceArcNeg = false;
		
		for(int i = 0; i<graphe.length; i++){
			for(int j = 0; j<graphe.length; j++){
				if(graphe[i][j] != null && graphe[i][j].getVarPoids() < 0){
					graphe[i][j].setCouleur(Color.RED);
					presenceArcNeg = true;
				}
			}
		}
		
		if(presenceArcNeg) return false;
		
		ArrayList<Sommet> aTraiter = new ArrayList<Sommet>();
		boolean continuer = true;
		
		ArrayList<Arc> aColorier = new ArrayList<Arc>(); //Liste d'arc étant utiliser dans le plus court chemin et qui doivent être coloré à la fin de dijkstra
		
		
		//Liste représentant les distances pour les sommets, les père et un booléen indiquant si un sommet à été traité
		ArrayList<Double> distance = new ArrayList<Double>();
		ArrayList<Sommet> pere = new ArrayList<Sommet>();
		ArrayList<Boolean> traiter = new ArrayList<Boolean>();

		


		
		for(Sommet s : sommets){
			distance.add(Double.MAX_VALUE);
			pere.add(null);
			traiter.add(false);
			aColorier.add(null);
		}
		
		if(sommets.contains(d) && sommets.contains(a)){
			
			if(d.equals(a)){ //Les sommets de départ et d'arrivée doivent être différents ou alors l'algo s'arrête en renvoyant un false
				return false;//Peut être changer si l'on souhaite le contraire
			}
			
			//Initialisation du départ et de aTraiter avec le sommet de départ
			distance.set(d.getId(), 0.0);
			aTraiter.add(d);
			pere.set(d.getId(), null);
			
			while(continuer){//Tant que tous les sommets n'ont pas été traité
				Sommet enTraitement = null;
				
				double min = Double.MAX_VALUE; Sommet mini = null;
				for(Sommet s : aTraiter){
					if(distance.get(s.getId()) < min){
						min = distance.get(s.getId());
						mini = s;
					}
				}
				enTraitement = mini; //On trouve le sommet de aTraiter ayant la distance la plus courte

				if(enTraitement != null){
					
					//Création de la liste d'arc sortants de traitement
					ArrayList<Arc> sortants = new ArrayList<Arc>();
					for(int i = 0; i<this.graphe.length; i++){
						if(graphe[enTraitement.getId()][i] != null){
							sortants.add(graphe[enTraitement.getId()][i]);
						}
					}
					
					for(Arc c : sortants){
						for(Sommet s : sommets){
							if(c.getSommetArrivee().equals(s) && traiter.get(s.getId()) == false){
								aTraiter.add(s);
							}
							
							if(c.getSommetArrivee().equals(s)){
								if(distance.get(s.getId()) > (distance.get(enTraitement.getId())+c.getVarPoids())){
									distance.set(s.getId(), distance.get(enTraitement.getId())+c.getVarPoids());
									pere.set(s.getId(), enTraitement);
									aColorier.set(s.getId(), c);
									
								}
							}
						}
					}
					traiter.set(enTraitement.getId(), true);
					aTraiter.remove(enTraitement);
				}
				
				continuer = false;
				for(Sommet s : aTraiter){//On regarder si tous les sommets ont été traités
					if(!traiter.get(s.getId())){
						continuer = true;
					}
				}
			}
			
			
			//Vérification des résultats
			boolean cheminExiste = false;
			Sommet act = a;
			while(act != null){
				if(act.equals(d)){
					cheminExiste = true;
				}
				act = pere.get(act.getId());
			}
			

			//Affichage des résultats
			if(cheminExiste){
				d.setCouleur(Color.red);
				a.setCouleur(Color.red);
				Sommet pereA = pere.get(a.getId()); 
				aColorier.get(a.getId()).setCouleur(Color.red);
				while(!pereA.equals(d)){ //On colore les sommets en remontant la chaine du plus court chemin depuis l'arrivée.
					pereA.setCouleur(Color.red);
					aColorier.get(pereA.getId()).setCouleur(Color.RED);
					pereA = pere.get(pereA.getId());
				}

				return true;
			}
		}
		return false;
	}
	
	/**
	 * Permet de trouver le plus court chemin entre un sommet d et un sommet a
	 * @param sommet d : sommet de départ
	 * @param sommet a : sommet d'arrivée
	 * @return false : si présence d'un cycle négatif
	 * @return true : si aucun problème
	 * @author Damien, Madeleine
	 */
	@Override
	public boolean bellman_ford(Sommet d, Sommet a) {
		this.reset_couleur_graph();
		
		ArrayList<Arc> aColorier = new ArrayList<Arc>();
		ArrayList<Double> distance = new ArrayList<Double>();
		ArrayList<Sommet> pere = new ArrayList<Sommet>();
		
		
		//Initialisation des distances et des pères
		for(int i = 0; i<sommets.size(); i++){
			distance.add(Double.MAX_VALUE);
			pere.add(null);
			aColorier.add(null);
		}
		
		//Initialisation pour le départ
		distance.set(d.getId(), 0.0);

		//Implémentation de Bellman-Ford, on teste le graphe le nombre de sommet-1 fois pour obtenir le résultat
		for(int i = 0; i<getNbSommets()-1; i++){
			for(Arc act : get_liste_arc()){
				if(distance.get(act.getSommetArrivee().getId()) > (distance.get(act.getSommetDepart().getId())+act.getVarPoids())){
					distance.set(act.getSommetArrivee().getId(), (distance.get(act.getSommetDepart().getId())+act.getVarPoids()));
					pere.set(act.getSommetArrivee().getId(), act.getSommetDepart());
					aColorier.set(act.getSommetArrivee().getId(), act);
				}
			}
		}
		
		//Détection cycle de poids négatif, si il y a un cycle de poids négatif, la méthode renvoi false;
		for(Arc act : get_liste_arc()){
			if(distance.get(act.getSommetArrivee().getId()) > (distance.get(act.getSommetDepart().getId()) + act.getVarPoids())){
				return false;
			}
		}
		
		boolean cheminExiste = false;
		Sommet act = a;
		while(act != null){
			if(act.equals(d)){
				cheminExiste = true;
			}
			act = pere.get(act.getId());
		}
		
		if(cheminExiste){
		act = a;
		while(act != null){
			act.setCouleur(Color.GREEN);
			Arc tmp = aColorier.get(act.getId());
			if(tmp != null){//L'arc a colorié est l'arc qui arrive à act or le sommet départ n'a pas forcément celui là
					tmp.setCouleur(Color.green);
			}
			act = pere.get(act.getId());
			
		}
		return true;
		}
		return false;
	}
	
	/**
	 * Méthode donnant le flot maximal entre le sommet source (d) et le sommet puit (a) et affiche le résultats sur le graphe
	 * @param d Sommet source du flot
	 * @param a Sommet puit du flot
	 * @return réussite de la méthode, renvoi false si il y a un poids négatif
	 */
	@Override
	public boolean ford_fulkerson(Sommet d, Sommet a) {
		this.reset_couleur_graph();
		
		//Vérification de la présence de poids négatifs
		boolean presenceArcNeg = false;
		for(int i = 0; i<graphe.length; i++){
			for(int j = 0; j<graphe.length; j++){
				if(graphe[i][j] != null && graphe[i][j].getVarPoids() < 0){
					graphe[i][j].setCouleur(Color.RED);
					presenceArcNeg = true;
				}
			}
		}
		if(presenceArcNeg) return false;
		
		double capacite[][] = new double[getNbSommets()][getNbSommets()];
		
		//Liste pour tenir compte des flots totale pour chaque arc pour pouvoir ajouter les variables sur l'arc plus tard
		float flotArc[] = new float[getNbArcs()];
		for(Arc act : get_liste_arc()){
			flotArc[act.getId()] = 0;
		}

		
		//Initialisation du tableau de capacité à 0 pour les arcs qui n'existe pas dans la matrice
		for(int i = 0; i<capacite.length; i++){
			for(int j = 0; j<capacite[i].length; j++){
				capacite[i][j] = 0;
			}
		}

		//On crée la matrice des capacités (c'est à dire ce que chaque arc peut recevoir comme flot)
		for(int i = 0; i<getNbSommets(); i++){
			for(int j = 0; j<getNbSommets(); j++){
				if(graphe[i][j] != null){
					capacite[i][j] = graphe[i][j].getVarPoids();
				}
			}
		}

		//Initialisation de la matrice de capacité résiduel
		double capaciteResiduel[][] = new double[capacite.length][capacite[0].length];
		for (int i = 0; i < capacite.length; i++) {
			for (int j = 0; j < capacite[0].length; j++) {
				capaciteResiduel[i][j] = capacite[i][j];
			}
		}

		Map<Integer,Integer> parent = new HashMap<>();

		//Permet de stocker les arcs et sommet du chemin augmentant pour les afficher après la boucle principale
		List<List<Arc>> cheminsAugmentant = new ArrayList<>();
		List<Sommet> sommetsAugmentant = new ArrayList<>();

		//Flot maximum de d à a
		double flotMax = 0;

		//Tant qu'il existe un chemin augmentant
		while(BFS(capaciteResiduel, parent, d.getId(), a.getId())){
			List<Arc> cheminAugmentant = new ArrayList<>();
			float flot = Float.MAX_VALUE;
			int v = a.getId();
			while(v != d.getId()){
				int u = parent.get(v);
				Arc tmp = getArc(getSommet(u), getSommet(v));
				if (flot > capaciteResiduel[u][v]) {
					flot = (float) capaciteResiduel[u][v];
				}
				v = u;
				
				cheminAugmentant.add(tmp);
				sommetsAugmentant.add(getSommet(u));
			}
			Collections.reverse(cheminAugmentant);
			cheminsAugmentant.add(cheminAugmentant);

			//On set le flot minimum des arcs comme étant celui que le chemin porte
			for(Arc act : cheminAugmentant){
				flotArc[act.getId()] += flot;
			}
			
			flotMax += flot;

			v = a.getId();
			while(v != d.getId()){
				int u = parent.get(v);
				capaciteResiduel[u][v] -= flot;
				capaciteResiduel[v][u] += flot;
				v = u;
			}
		}
		sommetsAugmentant.add(a);
		
		//Coloration des chemins augmentant et des sommets augmentant
		cheminsAugmentant.forEach(path -> {
            path.forEach(i -> i.setCouleur(Color.CYAN));
        });
		for(Sommet s : sommetsAugmentant){
			s.setCouleur(Color.CYAN);
		}
		//Ajout des variables sur les arcs
		for(Arc act : get_liste_arc()){
			float flot = flotArc[act.getId()];
			act.addVar(new VarFloat(flot));
		}
		a.addVar(new VarFloat((float)flotMax));

		return true;
	}
	
	/**
	 * Parcours le graphe de capacité résiduel en largeur pour trouver si une chaine améliorante existe entre la source et le puit
	 * @param capaciteResiduel : graphe sous forme de matrice d'adjacence de la capacité résiduel
	 * @param parent
	 * @param source : ID du sommet source (de départ) dans le graphe de capacité résiduel
	 * @param puit : ID du sommet puit (d'arrivée) dans le graphe de capacité résiduel
	 * @return true si il y a un chemin augmentant dans le graphe de capacité résiduel
	 */
	private boolean BFS(double[][] capaciteResiduel, Map<Integer,Integer> parent, int source, int puit){
        Set<Integer> visiter = new HashSet<>();
        Queue<Integer> file = new LinkedList<>();
        file.add(source);
        visiter.add(source);
        boolean cheminAugmentantTrouver = false;
        while(!file.isEmpty()){
            int u = file.poll();
            for(int v = 0; v < capaciteResiduel.length; v++){
                if(!visiter.contains(v) &&  capaciteResiduel[u][v] > 0){
                    parent.put(v, u);
                    visiter.add(v);
                    file.add(v);
                    if ( v == puit) {
                        cheminAugmentantTrouver = true;
                        break;
                    }
                }
            }
        }
        return cheminAugmentantTrouver;
    }

	/**
	 * Permet de trouver l'arbre couvrant de poids minimum dans un graphe
	 * @return true : si aucun problème
	 * @return false : s'il n'existe aucun arc dans le graphe
	 * @author Madeleine, Aziz
	 */
	@Override
	public boolean kruskall() {
		ArrayList<Arc> ArcsNonTries=get_liste_arc();
		ArrayList<Arc> ArcsTries=new ArrayList<Arc>();
		boolean existeSommetIsole=false;
		/*initialiser la couleur de tous les arcs et des sommets en noir
		 * */
		this.reset_couleur_graph();
		//tester si on a le cas où existe un sommet ou plusieurs qui ne sont attachés à aucun arc (sommets isolé)
		for(Sommet s : this.get_liste_de_sommet()){
			existeSommetIsole=true;
		for(Arc t : this.get_liste_arc()){
			if(t.getSommetArrivee().equals(s) || t.getSommetDepart().equals(s)){
				existeSommetIsole=false;
			}
		}
		
		//colorie les sommets isolés en rouge
		if(existeSommetIsole  && ArcsNonTries.size()>0) {
			s.setCouleur(Color.RED);
					}
		}
		//si le graphe est vide ou il n'existe aucun arc, on return false.
		if(ArcsNonTries.isEmpty()){
			return false;
		}

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
		int i, num1, num2;
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
	
	/**
	 * Permet de colorier le graphe avec le nombre de couleur différente minimum par rapport au nombre d'arcs par sommet.
	 * @return true : si aucun problème
	 * @author Madeleine
	 */
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
		
		//on ajoute une variable qui va nous permettre de stocker les couleurs de chaque sommet
		for(Sommet s: sommets){
			s.addVar(new VarInt(-1));
		}
		
		//Tant que la liste des sommets à colorier n'est pas vide
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
			//pour les sommets qui n'ont pas d'arcs
			if (nbarc==0){
				max=actu;
				}
			
			}
			//on a trouvé le sommet à colorier, maintenant on regarde de quelle couleur on peut le colorier
			liste_voisins=liste_voisins_pere_et_fils(max);
			int compare;
			color=0;
			while (change) {
				change =false;
				for (int k=0; k<liste_voisins.size();k++){
					//on regarde la couleur des voisins du sommet
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

		//met la couleur a jour pour chaque sommet et supprime tous les dernieres variables de chaque sommet (là où je stockais la couleur)
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
	
	/**
	 * Permet de colorier le graphe avec le nombre de couleur différente minimum par rapport au nombre de sommets qui sont coloriés autour d'un sommet
	 * @return true : si aucun problème
	 * @author Madeleine
	 */
	@Override
	public boolean dsatur() {
		ArrayList<Sommet> acolo = new ArrayList<Sommet>(this.get_liste_de_sommet());
		int nbcolor=0;
		int nbcolormax=0;
		int nbarc=0;
		int nbarcmax=0;
		Sommet actu, max = null;
		int color=0;
		boolean change=true;
		ArrayList<Sommet> liste_voisins;
	

		this.reset_couleur_graph();

		//on ajoute une variable qui va nous permettre de stocker les couleurs de chaque sommets
		for(Sommet s: sommets){
			s.addVar(new VarInt(-1));
		}
		
		//tant que les sommets ne sont pas tous colorés
		while (!acolo.isEmpty()) {
			
			nbcolormax=0;
			nbarcmax=0;
			max=null;
			
			for (int i=0; i<acolo.size();i++) {
				actu=acolo.get(i);
				nbarc=0;
				nbcolor=0;
				for (int j=0;j<(liste_voisins=liste_voisins_pere_et_fils(actu)).size();j++) {
					if ( liste_voisins.get(j).getVar(liste_voisins.get(j).getList().size()-1).getInt()!=-1 ){
					nbcolor=nbcolor+1;
					}
				}
				
				nbarc=liste_voisins.size();
				if (nbcolor>nbcolormax) {
					max=actu;
					nbcolormax=nbcolor;
				}
				if (nbarc>nbarcmax && nbcolor==nbcolormax){
					nbarcmax=nbarc;
					max=actu;
				}
				//pour les sommets qui n'ont pas d'arcs
				if (nbarc==0){
					max=actu;
				}
				
				
			}
			

			//on a trouvé le sommet à colorer, maintenant on regarde avec ses voisins quelle couleur peut-on lui donner
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

			

		
		//met la couleur a jour pour chaque sommet et supprime tous les dernieres variables de chaque sommet (là  où je stockais la couleur)
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

	 private ArrayList<Arc> getSortants(Sommet s, Graphe graph) {
		 
			//Fonction rajouté pour obtenir les arcs sortant d'un sommet
			ArrayList<Arc> arcSortant = new ArrayList<Arc>();
			
			for(Arc a : graph.get_liste_arc()){
				if(a.getSommetDepart().equals(s)){
					arcSortant.add(a);
				}
			}
			return arcSortant;
		}
	 
		
	 /**
	  * Permet de faire un parcours en profondeur pour Kosaraju
	  * @author Madeleine
	  */
	 private void DFS(Sommet s,ArrayList<Sommet> visited,Stack<Sommet> stack)  {
		 //fait un parcours en profondeur, si un sommet est dans "visited", on continue le parcours. Si on ne peut plus, c'est qu'on a fini de traité le sommet, et on l'ajoute dans la pile
		 visited.add(s);
		 for (Arc a : getSortants(s, this)) {
	        	Sommet v = a.getSommetArrivee();
	            if (visited.contains(v)) {
	                continue;
	            }
	            DFS(v, visited, stack);
	        }
		  stack.push(s);
	 }	
	 
	 //la différence entre les deux parcours en profondeur est que le 1er met dans la pile les sommets qu'on a fini de visité alors que l'autre non
	 
	 /**
		 * Permet de faire un parcours en profondeur avec un graphe qui a été retourné pour Kosaraju
		 * @author Madeleine
		 */
	 private void DFSRenverse(Sommet s,ArrayList<Sommet> visited,Stack<Sommet> stack,List<Sommet> res)  {
		 visited.add(s);
		  res.add(s);
		  for (Arc a : getSortants(s, this)) {
	        	Sommet v = a.getSommetArrivee();
	            if (visited.contains(v)) {
	                continue;
	            }
	            DFSRenverse(v, visited, stack, res);
	        }
	    }
		
	 
	 /**
		 * Permet de trouver les composantes fortement connexe dans un graphe. Les composantes fortement connexe seront colorés de la même couleur
		 * @author Madeleine
		 */
	@Override
	public boolean kosaraju() {
		this.reset_couleur_graph();
		Stack<Sommet> stack = new Stack<Sommet>();
        ArrayList<Sommet> visited = new ArrayList<Sommet>();

        //pour tous les sommets, on va faire un parcours en profondeur. Si un sommet est dans "visited", on continue si on peut le parcours en profondeur
       for (Sommet vertex : this.get_liste_de_sommet()) {
           if (visited.contains(vertex)) {
               continue;
           }
           DFS(vertex, visited, stack);
       }
     
           Graphe reverseGraph = new GrapheMatrice();
           //on créer un graphe qui est le même que notre graphe, mais ses arcs sont renversés.
           for (Sommet s : this.get_liste_de_sommet()){
        	   reverseGraph.addSommet(s);
           	}
           	for (Arc a : this.get_liste_arc()){
           		reverseGraph.addArc(a.getSommetArrivee(), a.getSommetDepart());
       		}
           	//on clear notre liste des sommets visited
            visited.clear();
            List<List<Sommet>> components = new ArrayList<>();
            //on fait un parcours en profondeur sur le graphe inversé, pour chaque sommet de la pile.
            while (!stack.isEmpty()) {
           				 List<Sommet> component = new ArrayList<>();
           				Sommet x = reverseGraph.getSommet(stack.pop().getId());
           				if(visited.contains(x)){
        	                continue;
        	            }
                	    
           				((GrapheMatrice) reverseGraph).DFSRenverse(x,visited,stack,component);
                	    components.add(component);
           }
            //pour chaque composantes fortement connexe (qui a été stocké dans une liste de liste), on lui attribut une couleur
           				components.forEach(component ->{
           					Random rand = new Random();
           		        	float r = rand.nextFloat();
           					float h = rand.nextFloat();
           					float b = rand.nextFloat();
           					component.forEach(p -> p.setCouleur(new Color(r,h,b)));
           				});
		return true;
	}

	/**
	 * Permet de faire un parcours en profondeur à partir d'un sommet pour extraire les points d'articulation
	 * @author Aziz
	 */
	
	private int attache(int num[],Graphe g,int x,ArrayList<Sommet> PointsArticulation,int j ){
		int min=num[x]= ++j;
		for(Sommet s:this.liste_voisins_pere_et_fils(this.get_liste_de_sommet().get(x))){
			int y=s.getId(); // y stock le id du sommet adjacent du sommet courant
			int m;
			//si le sommet n'est pas visité on fait l'appel récursif de parcours en profondeur sur ce sommet
			if(num[y]==-1){
				m=attache(num,g,y,PointsArticulation,j);
				if(m>=num[x]){
					PointsArticulation.add(this.get_liste_de_sommet().get(x));
				}
			}else
				m=num[y];
			min=Math.min(min,m);
		}
		return min;
	}

	/**
	 *  Permet de trouver les points d'articulation et les colorer
	 *  @return true : si l'algorithme trouve au moins un point d'articulation
	 *  @return false : s'il n y a pas aucun point d'articulation
	 *  @author Aziz
	 */
	
	@Override
	public boolean tarjan() {
		this.reset_couleur_graph();
		int numOrdre=0,n;
		n=this.getNbSommets();
		//Tableau indexé par les identifiants des sommets
		int num[]=new int[n];
		ArrayList<Sommet> PointsArticulation = new ArrayList<Sommet>();

		for(int x=0;x<n;++x)
			num[x]=-1;

		//faire un parcours en profondeur pour tous les sommets du graphe
		for(int x=0;x<n;++x)
		if(num[x]== -1){
			num[x]=++numOrdre;
			int nfils=0;
			for(Sommet s: this.liste_voisins_pere_et_fils(this.get_liste_de_sommet().get(x))){
				int y=s.getId();
				if(num[y]==-1){
					++nfils;// on incrémente le nombre de sommets adjacents du sommet courant
					
				int m=attache(num,this,y,PointsArticulation,numOrdre);
				}
				}
				//on teste le où un sommet est la racine de l'arbre de parcours en profondeur ayant plus qu'un sommet adjacent
			if(nfils>1) PointsArticulation.add(this.get_liste_de_sommet().get(x));
		}

		if(PointsArticulation.isEmpty()){
			return false;
		}
		//on colore les points d'articulation en rouge


		for (Sommet t : PointsArticulation){
        	t.setCouleur(Color.red);
		}


		return true;
	}

	/**
	 * Permet de renvoyer la liste de tous les voisins d'un sommet
	 * @param sommet : le sommet qu'on veut traiter
	 * @return ArrayList<Sommet> : la liste de sommets voisins
	 * @author Damien
	 */
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
	
	/**
	 * Permet de renvoyer la liste des sommets du graphe
	 * @return ArrayList<Sommet> : la liste des sommets du graphe
	 * @author Madeline
	 */
	@Override
	public ArrayList<Sommet> get_liste_de_sommet() {
		return sommets;
	}

	/**
	 * Fabrique et donne une liste des arcs appartenant au graphe
	 * @return ArrayList
	 */
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
}
