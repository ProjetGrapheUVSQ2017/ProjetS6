import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

public class Arc implements Serializable {
	
	private int id;
	private Sommet depart;
	private Sommet arrivee;
	private Color couleur;
	private ArrayList<Variable> variables;
	
	
	public Arc(Sommet d, Sommet a){
		this.depart = d;
		this.arrivee = a;
		this.variables = new ArrayList<Variable>();
		this.couleur = Color.BLACK;
	}
	
	public Arc(Sommet d, Sommet a, ArrayList<Variable> var){
		this.depart = d;
		this.arrivee = a;
		this.variables = var;
		this.couleur = Color.BLACK;
	}

	public Sommet getSommetDepart() {
		return depart;
	}

	public void setSommetDepart(Sommet depart) {
		this.depart = depart;
	}

	public Color getCouleur() {
		return couleur;
	}

	public void setCouleur(Color col) {
		this.couleur = col;
	}

	public Sommet getSommetArrivee() {
		return arrivee;
	}

	public void setSommetArrivee(Sommet arrivee) {
		this.arrivee = arrivee;
	}

	public int getId() {
		return id;
	}
	
	public Variable getVar(int i){
		return this.variables.get(i);
	}
	
	public ArrayList<Variable> getList(){
		return this.variables;
	}
	
	public void addVar(Variable var){
		this.variables.add(var);
	}
	
	public void setVar(int i, Variable var){
		this.variables.set(i, var);
	}
	
	public void removeVar(int i){
		this.variables.remove(i);
	}
	
	public float getVarPoids(){
		//TODO: Renvoyer la premi�re variable poids
		return 0;
	}
	
	public void setID(int id)
	{ 
		this.id = id; 
		}
	
	
}
