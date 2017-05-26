import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.awt.Color;
import java.util.Iterator;
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
	
	public GrapheListe(Graphe old){
		sommets = new ArrayList<Sommet>(old.get_liste_de_sommet());
		arcs = new ArrayList<Arc>(old.get_liste_arc());
		this.setNbSommets(old.getNbSommets());
		this.setNbArcs(old.getNbArcs());
	}

	/**
	 * Ajoute le sommet s au graphe
	 * @author Damien, Madeleine
	 */
	@Override
	public void addSommet(Sommet s) {
		/*int id = 0;
		boolean change = true;
		if(s != null){
			while(change){
				change=false;
				for(Sommet se : sommets){
					if(id==se.getId()) {
						id++;
						change=true;
					}
				}
			}
			sommets.add(s);
			this.setNbSommets(getNbSommets()+1);
			sommets.get(getNbSommets()-1).setID(getNbSommets());
		}*/
		
		if(s != null){
			sommets.add(s);
			this.setNbSommets(getNbSommets()+1);
			for(int i = 0; i <sommets.size(); i++){
				sommets.get(i).setID(i);
			}
		}
	}

	/**
	 * Ajoute un sommet se trouvant � l'adresse p au graphe
	 * @author Damien, Madeleine
	 */
	@Override
	public void addSommet(Point p) {
//		int id = 0;
//		boolean change = true;
//		while(change){
//			change=false;
//			for(Sommet s : sommets){
//				if(id==s.getId()) {
//					id++;
//					change=true;
//				}
//			}
//		}
//
//
//		sommets.add(new Sommet(p));
//		this.setNbSommets(getNbSommets()+1);
//		sommets.get(getNbSommets()-1).setID(id);
		
		if(p != null){
			this.setNbSommets(getNbSommets()+1);
			sommets.add(new Sommet(p));
			for(int i = 0; i <sommets.size(); i++){
				sommets.get(i).setID(i);
			}
		}
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
	 * @param id : Identifiant de l'arc � supprimer
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
		System.out.println("--------------------*deleteArc(id)------------------\nNombre de sommet : "+this.getNbSommets());
		System.out.println("Nombre d'arcs : "+this.getNbArcs());
		if(this.getNbSommets()>0){
		System.out.println("dernière id de sommet : "+this.get_liste_de_sommet().get(this.getNbSommets()-1).getId());
		}
		if(this.getNbArcs()>0){
		System.out.println("dernière id d'arc : "+this.get_liste_arc().get(this.getNbArcs()-1).getId()+"\n");
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
		System.out.println("--------------------*deleteSommet(id)------------------\nNombre de sommet : "+this.getNbSommets());
		System.out.println("Nombre d'arcs : "+this.getNbArcs());
		if(this.getNbSommets()>0){
		System.out.println("dernière id de sommet : "+this.get_liste_de_sommet().get(this.getNbSommets()-1).getId());
		}
		if(this.getNbArcs()>0){
		System.out.println("dernière id d'arc : "+this.get_liste_arc().get(this.getNbArcs()-1).getId()+"\n");
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
//		for(Sommet s : sommets){
//			if(s.getId()==id){
//				return s;
//			}
//		}
//		return null;
		
		return sommets.get(id);
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

	@Override
	public boolean dijkstra(Sommet d, Sommet a) {
		//Reinitialise toute les couleurs des arcs et sommets en noir
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
//			System.err.println(act.toString()+ " id : "+ act.getId() + " pere : " + pere.get(act.getId())+" , " + aColorier.get(act.getId()));
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
	 * 
	 */
	@Override
	public boolean ford_fulkerson(Sommet d, Sommet a) {
		this.reset_couleur_graph();
		double capacite[][] = new double[getNbSommets()][getNbSommets()];
		
		//Liste pour tenir compte des flots totale pour chaque arc pour pouvoir ajouter les variables sur l'arc plus tard
		List<Float> flotArc = new ArrayList<Float>();
		for(Arc act : arcs){
			flotArc.add((float) 0);
		}

		
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
		double capaciteResiduel[][] = new double[capacite.length][capacite[0].length];
		for (int i = 0; i < capacite.length; i++) {
			for (int j = 0; j < capacite[0].length; j++) {
				capaciteResiduel[i][j] = capacite[i][j];
			}
		}

		//this is parent map for storing BFS parent
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
			//find minimum residual capacity in augmented path
			//also add vertices to augmented path list
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
				flotArc.set(tmp.getId(), flotArc.get(tmp.getId())+flot);
			}
			Collections.reverse(cheminAugmentant);
			cheminsAugmentant.add(cheminAugmentant);

			//add min capacity to max flow
			flotMax += flot;

			//decrease residual capacity by min capacity from u to v in augmented path
			// and increase residual capacity by min capacity from v to u
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
		for(Arc act : arcs){
			float flot = flotArc.get(act.getId());
			act.addVar(new VarFloat(flot));
		}
		System.out.println("Flot maximum sur le graphe : "+ flotMax);//Print d'affichage du flot maximal trouver

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
        Set<Integer> visited = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(source);
        visited.add(source);
        boolean foundAugmentedPath = false;
        //see if we can find augmented path from source to sink
        while(!queue.isEmpty()){
            int u = queue.poll();
            for(int v = 0; v < capaciteResiduel.length; v++){
                //explore the vertex only if it is not visited and its residual capacity is
                //greater than 0
                if(!visited.contains(v) &&  capaciteResiduel[u][v] > 0){
                    //add in parent map saying v got explored by u
                    parent.put(v, u);
                    //add v to visited
                    visited.add(v);
                    //add v to queue for BFS
                    queue.add(v);
                    //if sink is found then augmented path is found
                    if ( v == puit) {
                        foundAugmentedPath = true;
                        break;
                    }
                }
            }
        }
        //returns if augmented path is found from source to sink or not
        return foundAugmentedPath;
    }

	@Override
	public boolean kruskall() {

		ArrayList<Arc> ArcsNonTries=new ArrayList<Arc>(get_liste_arc());
		ArrayList<Arc> ArcsTries=new ArrayList<Arc>();
		ArrayList<Sommet> SommetSelectionnes=new ArrayList<Sommet>(get_liste_de_sommet());
		boolean existeSommetIsole=false;
		this.reset_couleur_graph();
		//tester si on a le cas où existe un sommet ou plusieurs qui ne sont attachés à aucun arc (sommet isolé)
		for(Sommet s : this.get_liste_de_sommet()){
			existeSommetIsole=true;
		for(Arc t : this.get_liste_arc()){
			if(t.getSommetArrivee().equals(s) || t.getSommetDepart().equals(s)){
				existeSommetIsole=false;
			}
		}
		if(existeSommetIsole) {
			s.setCouleur(Color.RED);
			System.out.println("le sommet num : "+s.getId()+" est isolé"); 
			//TODO afficher un message pour informer l'utilisateur qu'il faut relier tous les sommets pour 
			//appliquer l'algo de Kruskall, sinon il crée un nouveau sous graphe
					}
		}
		if(ArcsNonTries.isEmpty() || existeSommetIsole == true){
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
		int i, n, num1, num2,poids=0;
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
		ArrayList<Arc> arcajouté=new ArrayList<Arc>();
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
				poids+=ArcsTries.get(i).getVarPoids();
				arcajouté.add(a);
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

		for(Sommet s: sommets){
			s.addVar(new VarInt(-1));
		}
		
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

		//parcours en profondeur normal
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
	    //parcours en profondeur sur graphe renversé (on ajoute pas a la pile
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

	@Override
	public boolean tarjan() {
		// TODO Auto-generated method stub
		this.reset_couleur_graph();
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


