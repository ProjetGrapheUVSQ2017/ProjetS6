import java.awt.Color;
import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Classe représentant un sommet du graphe
 * @author Damien
 *
 */
public class Sommet implements Serializable{
	/**
	 * Coordonnées du sommet dans l'espace d'affichage
	 */
	private Point coords;
	/**
	 * ID unique identifiant le sommet
	 */
	private int id;
	/**
	 * Couleur du sommet dans l'espace d'affichage.
	 */
	private Color couleur;
	/**
	 * Liste des variables rattaché au sommet.
	 */
	private ArrayList<Variable> variables;
	
	
	public Sommet(Point coords, ArrayList<Variable> var){
		this.coords = coords;
		this.variables = var;
		this.couleur = Color.BLACK;
	}
	
	/**
	 * Construit un nouveau sommet à partir d'un point
	 * @param coords Coordonnées du sommet sur l'interface
	 */
	public Sommet(Point coords){
		this.coords = coords;
		this.variables = new ArrayList<Variable>();
		this.couleur = Color.BLACK;
	}
	
	/**
	 * Getter de l'identifiant du sommet
	 * @return int
	 */
	public int getId(){
		return this.id;
	}
	
	/*
	 * Donne la variable à l'emplacement i dans la liste de variables
	 */
	public Variable getVar(int i){
		return this.variables.get(i);
	}
	
	/**
	 * Getter permettant d'obtenir la couleur du sommet
	 * @return Color
	 */
	public Color getCouleur(){
		return this.couleur;
	}
	
	/**
	 * Getter des coordonnées du sommet
	 * @return Point
	 */
	public Point getPoint(){
		return this.coords;
	}
	
	/**
	 * Setter de la couleur
	 * @param col nouvelle couleur du sommet
	 */
	public void setCouleur(Color col){
		this.couleur = col;
	}
	
	/**
	 * Ajoute une variable à la liste des variables du sommet
	 * @param var
	 */
	public void addVar(Variable var){
		this.variables.add(var);
	}
	
	/**
	 * Modifie la variable i du sommet
	 * @param i emplacement de la variable dans la liste des variables du sommet
	 * @param var
	 */
	public void setVar(int i, Variable var){
		this.variables.set(i, var);
	}
	
	/**
	 * Supprime la variable i du sommet
	 * @param i emplacement dans la liste des variables de la variable à supprimer;
	 */
	public void removeVar(int i){
		this.variables.remove(i);
	}

	/**
	 * Setter des coordonnées du sommet
	 * @param p nouvelle coordonnées du sommet sous forme de Point
	 */
	public void setPoint(Point p){
		this.coords = p;
	}
	
	/**
	 * Getter de la liste des variables du sommet
	 * @return ArrayList
	 */
	public ArrayList<Variable> getList(){
		return this.variables;
	}
	
	/**
	 * Setter de l'ID du sommet
	 * @param id nouvelle id pour le sommet
	 */
	public void setID(int id){
		this.id = id;
	}
}
