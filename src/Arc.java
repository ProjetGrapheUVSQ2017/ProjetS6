import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe représentant un arc du graphe
 * @author Damien
 *
 */
public class Arc implements Serializable {
	
	/**
	 * Identifiant de l'arc
	 */
	private int id;
	/**
	 * Sommet de départ de l'arc
	 */
	private Sommet depart;
	/**
	 * Sommet d'arrivée de l'arc
	 */
	private Sommet arrivee;
	/**
	 * Couleur d'affichage de l'arc
	 */
	private Color couleur;
	/**
	 * Liste des variables se trouvant sur l'arc
	 */
	private ArrayList<Variable> variables;
	
	/**
	 * Constructeur principale d'un arc
	 * @param d Sommet de départ de l'arc
	 * @param a Sommet d'arrivée de l'arc
	 */
	public Arc(Sommet d, Sommet a){
		this.depart = d;
		this.arrivee = a;
		this.variables = new ArrayList<Variable>();
		this.couleur = Color.BLACK;
		this.addVar(new VarFloat(1.0f));
		this.getVar(0).setPoids(true);
	}
	
	/**
	 * Constructeur de l'arc mais donnant les Variables contenues dans var dans la liste des variables de l'arc
	 * @param d sommet de départ
	 * @param a sommet d'arrivée
	 * @param var liste de variables qui sera lié au graphe
	 */
	public Arc(Sommet d, Sommet a, ArrayList<Variable> var){
		this.depart = d;
		this.arrivee = a;
		this.variables = var;
		this.couleur = Color.BLACK;
		this.addVar(new VarFloat(1.0f));
		this.getVar(0).setPoids(true);
		for(Variable v : var){
			this.addVar(v);
		}
	}

	/**
	 * Getter permettant d'obtenir le sommet de départ du graphe
	 * @return Sommet
	 */
	public Sommet getSommetDepart() {
		return depart;
	}

	/**
	 * Setter du sommet de départ si l'arc doit changer de sommet de départ
	 * @param depart nouveau Sommet de départ
	 */
	public void setSommetDepart(Sommet depart) {
		this.depart = depart;
	}

	/**
	 * Getter permettant d'obtenir la couleur de l'arc
	 * @return Color
	 */
	public Color getCouleur() {
		return couleur;
	}

	/**
	 * Setter pour la couleur de l'arc
	 * @param col Nouvelle couleur pour l'arc
	 */
	public void setCouleur(Color col) {
		this.couleur = col;
	}

	/**
	 * Getter permettant d'obtenir le sommet d'arrivée
	 * @return Sommet
	 */
	public Sommet getSommetArrivee() {
		return arrivee;
	}

	/**
	 * Setter pour le sommet d'arrivée et permettant de changer le sommet d'arrivée
	 * @param arrivee Sommet
	 */
	public void setSommetArrivee(Sommet arrivee) {
		this.arrivee = arrivee;
	}

	/**
	 * Getter de l'identifiant de l'arc
	 * @return int Identifiant
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Getter pour les Variables de l'arc
	 * @param i Place dans la liste de la variables
	 * @return Variable
	 */
	public Variable getVar(int i){
		return this.variables.get(i);
	}
	
	/**
	 * Getter pour la liste de variables de l'arc
	 * @return ArrayList
	 */
	public ArrayList<Variable> getList(){
		return this.variables;
	}
	
	/**
	 * Permet d'ajouter une nouvelle variable à la liste des variables 
	 * @param var Variable a ajouté
	 */
	public void addVar(Variable var){
		this.variables.add(var);
	}
	
	/**
	 * Permet de modifier la variable de la liste se trouvant à la position i
	 * @param i : int
	 * @param var Variable
	 */
	public void setVar(int i, Variable var){
		this.variables.set(i, var);
	}
	
	/**
	 * Méthode permettant d'enlever la variable de liste
	 * @param i int
	 */
	public void removeVar(int i){
		this.variables.remove(i);
	}
	
	/**
	 * Méthode permettant d'obtenir le premier poids sur la liste de variables sous forme de flottant
	 * @return float
	 */
	public float getVarPoids(){
		for(Variable v : variables){
			if(v.isPoids()){
				return v.getFloat();
			}
		}
		return 0;
	}
	
	/**
	 * Permet de changer l'identifiant de l'arc
	 * @param id nouvel identifiant
	 */
	public void setID(int id)
	{ 
		this.id = id; 
		}
	
	
}
