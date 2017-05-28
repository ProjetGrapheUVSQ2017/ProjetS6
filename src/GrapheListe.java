import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;




/**
 * Classe stockant le graphe sous forme de liste de sommets et de liste d'arcs.
 * @author Damien
 * @see Graphe
 */
public class GrapheListe extends Graphe {

	/**
	 * Liste des sommets composant le graphe
	 */
	private ArrayList<Sommet> sommets;
	/**
	 * Liste des arcs composant le graphe
	 */
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
	
	/**
	 * Crée un graphe de type liste depuis n'importe quel autre type de graphe
	 * Attention, ce constructeur ne fait pas une copie mais récupère les mêmes arcs et sommets que l'ancien graphe (old)
	 * @param old Ancien graphe
	 */
	public GrapheListe(Graphe old){
		sommets = new ArrayList<Sommet>(old.get_liste_de_sommet());
		arcs = new ArrayList<Arc>(old.get_liste_arc());
		this.setNbSommets(old.getNbSommets());
		this.setNbArcs(old.getNbArcs());
	}

	/**
	 * Ajoute le sommet s au graphe
	 * @author Damien, Madeleine
	 * @param s : Sommet à ajouté
	 */
	@Override
	public void addSommet(Sommet s) {		
		if(s != null){
			sommets.add(s);
			this.setNbSommets(getNbSommets()+1);
			for(int i = 0; i <sommets.size(); i++){
				sommets.get(i).setID(i);
			}
		}
	}

	/**
	 * Ajoute un sommet se trouvant l'adresse p au graphe
	 * @param p : Point où sera ajouté le sommet
	 * @author Damien, Madeleine
	 */
	@Override
	public void addSommet(Point p) {		
		if(p != null){
			this.setNbSommets(getNbSommets()+1);
			sommets.add(new Sommet(p));
			for(int i = 0; i <sommets.size(); i++){
				sommets.get(i).setID(i);
			}
		}
	}

	/**
	 * Ajoute un arc entre le Sommet d et le Sommet a au graphe
	 * Vérifie si les sommets d et a font partie du graphe.
	 * @param d : Sommet de départ de l'arc
	 * @param a : Sommet d'arrivée de l'arc
	 */
	@Override
	public void addArc(Sommet d, Sommet a) {
		if(sommets.contains(d) && sommets.contains(a)){
			int id = 0;
			boolean change = true;
			while(change){
				change=false;
				for(Arc arc : arcs){
					if(id==arc.getId()) {
						id++;
						change=true;
					}
				}
			}
			arcs.add(new Arc(d, a));
			this.setNbArcs(getNbArcs()+1);
			arcs.get(getNbArcs()-1).setID(id);
		}

	}

	/**
	 * On cherche l'arc correspondant et on le supprime de la liste d'arcs, le GC s'occupe du reste.
	 * @param d : Sommet de départ de l'arc à supprimer
	 * @param a : Sommet d'arrivé de l'arc à supprimer
	 * @author Damien
	 */
	@Override
	public void deleteArc(Sommet d, Sommet a) {
		Arc aSupprimer = null;
		for(Arc act : arcs){
			if(act.getSommetDepart().equals(d) && act.getSommetArrivee().equals(a)){
				aSupprimer = act;
				break;
			}
		}
		
		if(aSupprimer != null){
			arcs.remove(aSupprimer);
			this.setNbArcs(this.getNbArcs()-1);
		}
		for(int i = 0; i <this.arcs.size(); i++){
			this.arcs.get(i).setID(i);
		}
	}

	/**
	 * Supprime l'arc id dans le graphe
	 * @param id : Identifiant de l'arc à supprimer
	 * @author Damien
	 */
	@Override
	public void deleteArc(int id) {
		for(Arc act : this.arcs){
			if(act.getId() == id){
				this.arcs.remove(act);
				this.setNbArcs(this.getNbArcs()-1);
				break;
			}
		}
		for(int i = 0; i <this.arcs.size(); i++){
			this.arcs.get(i).setID(i);
		}
	}

	/**
	 * Supprime le sommet id du graphe et les arcs sortant et entrant de lui.
	 * @param id : Identifiant du sommet
	 * @author Damien
	 */
	@Override
	public void deleteSommet(int id) {
		for(Sommet s : this.sommets){
			if(s.getId() == id){
				ListIterator<Arc> i = this.arcs.listIterator();
				while (i.hasNext()) {
					Arc a = i.next();
					if(a.getSommetDepart()==s || a.getSommetArrivee()==s){
						i.remove();
						this.setNbArcs(this.getNbArcs()-1);
					}
				}
				this.sommets.remove(s);
				this.setNbSommets(getNbSommets()-1);
				break;
			}
		}
		
		for(int i = 0; i <this.sommets.size(); i++){
			this.sommets.get(i).setID(i);
		}
		for(int i = 0; i <this.arcs.size(); i++){
			this.arcs.get(i).setID(i);
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
	 * Renvoie le sommet identifié par id.
	 * Renvoi null si le sommet n'existe pas dans le graphe.
	 * @param id : Identifiant du sommet recherch� dans la liste
	 * @return Sommet
	 * @author Damien
	 */
	@Override
	public Sommet getSommet(int id) {		
		return sommets.get(id);
	}



	/**
	 * Renvoi l'arc identifié par d et a.
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
	 * Renvoi l'arc identifié par id.
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
		return new GrapheMatrice(this);
	}

	/**
	 * Crée un sous-graphe composé des sommets donnés dans s et des arcs entre eux.
	 * @param s : Une ArrayList de sommet qui composeront le nouveau graphe
	 * @author Damien
	 * @see ArrayList
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
		for(Arc act : arcs){
			if(act.getVarPoids() < 0){
				act.setCouleur(Color.red);
				presenceArcNeg = true;
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
					for(Arc c : this.getSortants(enTraitement, this)){
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
	 * Fonction affichant le plus court chemin en le sommet d et le sommet a
	 * @param d : Sommet de départ pour le plus court chemin
	 * @param a : Sommet d'arrivée pour le plus court chemin
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
			for(Arc act : arcs){
				if(distance.get(act.getSommetArrivee().getId()) > (distance.get(act.getSommetDepart().getId())+act.getVarPoids())){
					distance.set(act.getSommetArrivee().getId(), (distance.get(act.getSommetDepart().getId())+act.getVarPoids()));
					pere.set(act.getSommetArrivee().getId(), act.getSommetDepart());
					aColorier.set(act.getSommetArrivee().getId(), act);
				}
			}
		}
		
		//Détection cycle de poids négatif, si il y a un cycle de poids négatif, la méthode renvoi false;
		for(Arc act : arcs){
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
	 * Cette méthode prend un sommet d et un sommet a.
	 * Le sommet d sera le sommet source et le sommet a le sommet puit.
	 * La méthode calculera le flot maximal sur le graphe et affichera les résultats directement dessus.
	 * Elle utilise la méthode BFS qui permet de savoir si il existe encore un chemin augmentant sur le graphe des capacité résiduel en utilisant un parcours en largeur.
	 * @author Damien
	 * @see BFS
	 */
	@Override
	public boolean ford_fulkerson(Sommet d, Sommet a) {
		Color affichageRes = Color.cyan; //Couleur d'affichage des résultats
		
		this.reset_couleur_graph();
		
		//Vérification de la présence de poids négatif sur le graphe
		boolean presenceArcNeg = false;
		for(Arc act : arcs){
			if(act.getVarPoids() < 0){
				act.setCouleur(Color.red);
				presenceArcNeg = true;
			}
		}
		if(presenceArcNeg) return false;
		
		float capacite[][] = new float[getNbSommets()][getNbSommets()];
		//Initialisation du tableau de capacité à 0 pour les arcs qui n'existe pas dans la matrice
		for(int i = 0; i<capacite.length; i++){
			for(int j = 0; j<capacite[i].length; j++){
				capacite[i][j] = 0;
			}
		}

		//On crée la matrice des capacités (c'est à dire ce que chaque arc peut recevoir comme flot)
		for(Arc act : arcs){
			capacite[act.getSommetDepart().getId()][act.getSommetArrivee().getId()] = act.getVarPoids();
		}

		//Initialisation de la matrice de capacité résiduel
		float capaciteResiduel[][] = new float[capacite.length][capacite[0].length];
		for (int i = 0; i < capacite.length; i++) {
			for (int j = 0; j < capacite[0].length; j++) {
				capaciteResiduel[i][j] = capacite[i][j];
			}
		}

		//Map utilisé pour lier l'ID d'un sommet à l'ID d'un de ses fils
		Map<Integer,Integer> parent = new HashMap<>();

		//Permet de stocker les arcs et sommet du chemin augmentant pour les afficher après la boucle principale
		List<List<Arc>> cheminsAugmentant = new ArrayList<>();
		List<Sommet> sommetsAugmentant = new ArrayList<>();
		float flotArc[] = new float[getNbArcs()];
		
		for(Arc act : arcs){
			flotArc[act.getId()] = 0;
		}

		//Flot maximum de d à a
		float flotMax = 0;

		//Tant qu'il existe un chemin augmentant
		while(BFS(capaciteResiduel, parent, d.getId(), a.getId())){
			List<Arc> cheminAugmentant = new ArrayList<>();
			float flot = Float.MAX_VALUE;
			//Trouve la capacité résiduel minimal dans le graphe de capacité résiduel
			//et ajoute le chemin augmentant à liste de chemins augmentant finaux
			int v = a.getId();
			while(v != d.getId()){
				int u = parent.get(v);
				Arc tmp = getArc(getSommet(u), getSommet(v));
				if (flot > capaciteResiduel[u][v]) {
					flot = capaciteResiduel[u][v];
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
			
			//add min capacity to max flow
			flotMax += flot;

			//Met à jour la capacité résiduel entre u et v
			//et l'augmente dans le sens inverse
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
		for(List<Arc> l : cheminsAugmentant){
			for(Arc act : l){
				act.setCouleur(affichageRes);
			}
		}
		
		for(Arc act : arcs){
			act.addVar(new VarFloat(flotArc[act.getId()])); //Ajout des variables sur les arcs
		}
		for(Sommet s : sommetsAugmentant){
			s.setCouleur(affichageRes); //Coloration des sommets appartenant à un chemin augmentant
		}
		
		a.addVar(new VarFloat(flotMax));//Affichage du résultat final sur le sommet d'arrivée
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
	private boolean BFS(float[][] capaciteResiduel, Map<Integer,Integer> parent, int source, int puit){
        Set<Integer> visiter = new HashSet<>();
        Queue<Integer> file = new LinkedList<>();
        file.add(source);
        visiter.add(source);
        boolean cheminAugmentantTrouver = false;
        //Recherche si l'on trouve un chemin augmentant
        while(!file.isEmpty()){
            int u = file.poll();
            for(int v = 0; v < capaciteResiduel.length; v++){
            	//Explore les sommets non visités et dont la capacité résiduel est plus grande que 0 et donc qu'on est une possibilité d'amélioration sur cet arc
                if(!visiter.contains(v) &&  capaciteResiduel[u][v] > 0){
                    parent.put(v, u);
                    //On met le sommet en tant que visité
                    visiter.add(v);
                    //et on ajoute v dans la file d'attente
                    file.add(v);
                    //Si l'on peut accéder au puit alors on a un chemin augmentant
                    if ( v == puit) {
                        cheminAugmentantTrouver = true;
                        break;
                    }
                }
            }
        }
        //Retourne vrai si on a trouver un chemin augmentant entre la source et le puit
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

		ArrayList<Arc> ArcsNonTries=new ArrayList<Arc>(get_liste_arc());
		ArrayList<Arc> ArcsTries=new ArrayList<Arc>();
		ArrayList<Sommet> SommetSelectionnes=new ArrayList<Sommet>(get_liste_de_sommet());
		boolean existeSommetIsole=false;
		this.reset_couleur_graph();
		//test si on a le cas où existe un sommet ou plusieurs qui ne sont attachés à aucun arc (sommet isolé)
		for(Sommet s : this.get_liste_de_sommet()){
			existeSommetIsole=true;
		for(Arc t : this.get_liste_arc()){
			if(t.getSommetArrivee().equals(s) || t.getSommetDepart().equals(s)){
				existeSommetIsole=false;
			}
		}
		//colorie en rouge les sommets isolés qui ne seront pas traitré
		if(existeSommetIsole && ArcsNonTries.size()>0) {
			s.setCouleur(Color.RED);
					}
		}
		//renvoit une erreur s'il n'existe aucun arc
		if(ArcsNonTries.isEmpty()){
			return false;
		}
		/*
		 * trier les poids des arcs par ordre croissant
		 * */
		int idArc=0;
		while (ArcsTries.size()!=this.getNbArcs()){
			Arc ArcMin= ArcsNonTries.get(0);
			idArc=0;
			for(int i=1;i<ArcsNonTries.size();i++){
				if(ArcsNonTries.get(i).getVarPoids()<ArcMin.getVarPoids() && !(ArcsTries.contains(ArcsNonTries.get(i))) ){
					ArcMin=ArcsNonTries.get(i);
					idArc=i;
				}
			}
			ArcsNonTries.remove(idArc);
			ArcsTries.add(ArcMin);

		}
		int i, n, num1, num2;
		n = SommetSelectionnes.size();
		ListIterator<Sommet> z = SommetSelectionnes.listIterator();
		while (z.hasNext()) {
			Sommet a = z.next();
			if (liste_voisins_pere_et_fils(a).isEmpty()) {
				z.remove();
			}
		}

		n = SommetSelectionnes.size();
		for (i = 0; i < n; i++){
			SommetSelectionnes.get(i).addVar(new VarInt(i));
		}
		
		
		i = 0;
		ArrayList<Arc> arcajoute=new ArrayList<Arc>();
		boolean change=true;
		while (change) {
			change=false;
			Arc a = ArcsTries.get(i);
			num1 = a.getSommetDepart().getVar(a.getSommetDepart().getList().size()-1).getInt();
			num2 = a.getSommetArrivee().getVar(a.getSommetDepart().getList().size()-1).getInt();
			if (num1 != num2) {
				ArcsTries.get(i).setCouleur(Color.BLUE);
				ArcsTries.get(i).getSommetArrivee().setCouleur(Color.BLUE);
				ArcsTries.get(i).getSommetDepart().setCouleur(Color.BLUE);
				arcajoute.add(a);
				for (Sommet s : SommetSelectionnes)
					if (s.getVar(s.getList().size()-1).getInt() == num2) 
						{
						s.setVar(s.getList().size()-1,new VarInt(num1));
						}
			}
			for (Sommet x : SommetSelectionnes){
				if (x.getCouleur()!=Color.BLUE){
					change=true;
				}
			}
			i++;
		}
		for (Sommet t : SommetSelectionnes){
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
	public boolean kosaraju() {
		this.reset_couleur_graph();
	        //pile d'arrivée des sommets
	        Deque<Sommet> stack = new ArrayDeque<>();
	        //sommets déjà visité
	        Set<Sommet> visited = new HashSet<>();

	        //visiter chaque sommets et regarder pour chaque fils
	        for (Sommet vertex : this.get_liste_de_sommet()) {
	            if (visited.contains(vertex)) {
	                continue;
	            }
	            DFS(vertex, visited, stack);
	        }


	        //on met le graphe à l'envers
	        	Graphe reverseGraph = new GrapheListe();
		        for (Sommet s : this.get_liste_de_sommet()){
		        	reverseGraph.addSommet(s);
		        }
		        for (Arc edge : this.get_liste_arc()) {
		            reverseGraph.addArc(edge.getSommetArrivee(), edge.getSommetDepart());
		        }

	       
	        //Faire recherche en profondeur sur le graphe renversé
	        visited.clear();
	        List<Set<Sommet>> result = new ArrayList<>();
	        
	        while (!stack.isEmpty()) {
	        	Sommet vertex = reverseGraph.getSommet(stack.poll().getId());
	            if(visited.contains(vertex)){
	                continue;
	            }
	            Set<Sommet> set = new HashSet<>();
	            DFSRenverse(vertex, visited, set, reverseGraph);
	            result.add(set);
	        }
	        
	        //mettre les couleurs les sommets contenu dans chaque liste
	        result.forEach(set -> {
	        	Random rand = new Random();
	        	float r = rand.nextFloat();
				float g = rand.nextFloat();
				float b = rand.nextFloat();
	            set.forEach(v -> v.setCouleur(new Color(r,g,b)));
	        });
	        
	        return true;
}
	/**
	 * Permet de faire un parcours en profondeur pour Kosaraju
	 * @author Madeleine
	 */
	    private void DFS(Sommet vertex,Set<Sommet> visited, Deque<Sommet> stack) {
	        visited.add(vertex);
	        for (Arc a : getSortants(vertex, this)) {
	        	Sommet v = a.getSommetArrivee();
	            if (visited.contains(v)) {
	                continue;
	            }
	            DFS(v, visited, stack);
	        }
	        stack.offerFirst(vertex);
	    }
		 //la différence entre les deux parcours en profondeur est que le 1er met dans la pile les sommets qu'on a fini de visité alors que l'autre non	 
		 /**
		 * Permet de faire un parcours en profondeur avec un graphe qui a été retourné pour Kosaraju
		 * @author Madeleine
		 */
	    private void DFSRenverse(Sommet vertex, Set<Sommet> visited, Set<Sommet> set, Graphe graph) {
	        visited.add(vertex);
	        set.add(vertex);
	        for (Arc a : getSortants(vertex, graph)) {
	        	Sommet v = a.getSommetArrivee();
	            if (visited.contains(v)) {
	                continue;
	            }
	            DFSRenverse(v, visited, set, graph);
	        }
	    }
	    /**
		 * Permet de faire un parcours en profondeur avec un graphe pour extraire les points d'articulation
		 * @author Aziz, Maxence
		 */
	private int attache(int num[],Graphe g,int x,ArrayList<Sommet> PointsArticulation,int j ){
		int min=num[x]= ++j;
		for(Sommet s:this.liste_voisins_pere_et_fils(this.get_liste_de_sommet().get(x))){
			int y=s.getId();// y stock le id du sommet adjacent du sommet courant
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
	 * Permet de trouver les points d'articulation et les colorer
	 * @return true : si l'algorithme trouve au moins un point d'articulation
	 * @return false : s'il n y a pas aucun point d'articulation
	 * @author Aziz, Maxence
	 */
	@Override
	public boolean tarjan() {
		this.reset_couleur_graph();
		int numOrdre=0,n;
		n=this.getNbSommets();
		//Tableau indexé par les identifiants des sommets
		int num[]=new int[n];
		ArrayList<Sommet> PointsArticulation = new ArrayList<Sommet>();
		//On initialise tous les sommets comme non visités
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
		//on colore les points d'articulation en rouge
		for (Sommet t : PointsArticulation){
			t.setCouleur(Color.red);
		}
		//on retourne false si aucun point d'articulation est trouvé
		if(PointsArticulation.isEmpty()) {
			return false;
		}
		return true;
	}
	 /**
	  * Permet de renvoyer la liste des sommets contenu dans le graphe
	  * @author Madeleine
	  */
	@Override
	public ArrayList<Sommet> get_liste_de_sommet() {
		return this.sommets;
	}
	 /**
	  * Permet de renvoyer la liste des arcs contenu dans le graphe
	  * @author Madeleine
	  */
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
				if(!res.contains(act.getSommetDepart())){
					res.add(act.getSommetDepart());
				}
			}
			else if(act.getSommetDepart().equals(s)){
				if(!res.contains(act.getSommetArrivee())){
					res.add(act.getSommetArrivee());
				}
			}
		}
		return res;
	}
}