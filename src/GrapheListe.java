import java.awt.Point;
import java.util.ArrayList;

import java.awt.Color;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Random;

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
				this.setNbArcs(this.getNbArcs()-1);
				break;
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
		for(Sommet s : sommets){
			if(s.getId() == id){
				ListIterator<Arc> i = arcs.listIterator();
				while (i.hasNext()) {
					Arc a = i.next();
					if(a.getSommetDepart()==s || a.getSommetArrivee()==s){
						i.remove();
						this.setNbArcs(this.getNbArcs()-1);
					}
				}
				sommets.remove(s);
				this.setNbSommets(getNbSommets()-1);
				break;
			}
		}
		
		for(int i = 0; i <sommets.size(); i++){
			sommets.get(i).setID(i);
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
					for(Arc c : this.getSortants(enTraitement)){
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
				for(Sommet s : aTraiter){
					if(!traiter.get(s.getId())){
						continuer = true;
					}
				}
			}
			
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
		return false;
	}

	private ArrayList<Arc> getSortants(Sommet s) {
		//Fonction rajouté pour obtenir les arcs sortant d'un sommet
		ArrayList<Arc> arcSortant = new ArrayList<Arc>();
		
		for(Arc a : arcs){
			if(a.getSommetDepart().equals(s)){
				arcSortant.add(a);
			}
		}
		
		return arcSortant;
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
//		ArrayList<Arc> ListeSommets =new ArrayList<Arc>(get_liste_de_sommet());
		ArrayList<Arc> ArcsNonTries=new ArrayList<Arc>(get_liste_arc());
		ArrayList<Arc> ArcsTries=new ArrayList<Arc>();
		ArrayList<Sommet> SommetSelectionnes=new ArrayList<Sommet>(get_liste_de_sommet());
		/*
		 * trier les poids des arcs par ordre croissant
		 * */
		for(Arc a : this.get_liste_arc()){
			a.setCouleur(Color.BLACK);
		}
		for(Sommet s : this.get_liste_de_sommet()){
			s.setCouleur(Color.BLACK);
		}
		
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
		for (i = 0; i < n; i++)
			sommets.get(i).addVar(new VarInt(i));
		i = 0;
		ArrayList<Arc> arcajouté=new ArrayList<Arc>();
		while (arcajouté.size() < n - 1) {
			Arc a = ArcsTries.get(i);
			num1 = a.getSommetDepart().getVar(a.getSommetDepart().getList().size()-1).getInt();
			num2 = a.getSommetArrivee().getVar(a.getSommetDepart().getList().size()-1).getInt();
			if (num1 != num2) {
				ArcsTries.get(i).setCouleur(Color.BLUE);
				ArcsTries.get(i).getSommetArrivee().setCouleur(Color.BLUE);
				ArcsTries.get(i).getSommetDepart().setCouleur(Color.BLUE);
				poids+=ArcsTries.get(i).getVarPoids();
				arcajouté.add(a);
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
		
		for(Arc a : this.get_liste_arc()){
			a.setCouleur(Color.BLACK);
		}
		for(Sommet s : this.get_liste_de_sommet()){
			s.setCouleur(Color.BLACK);
		}
		
		
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
	

		for(Arc a : this.get_liste_arc()){
			a.setCouleur(Color.BLACK);
		}
		for(Sommet s : this.get_liste_de_sommet()){
			s.setCouleur(Color.BLACK);
		}

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


