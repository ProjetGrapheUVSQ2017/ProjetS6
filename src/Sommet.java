import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Sommet {
	private Point coords;
	private int id;
	private Color couleur;
	private ArrayList<Variable> variables;
	
	public Sommet(Point coords, ArrayList<Variable> var){
		this.coords = coords;
		this.variables = var;
	}
	
	public Sommet(Point coords){
		this.coords = coords;
		this.variables = new ArrayList<Variable>();
		//TODO: Ajouter une prise en compte des ID, initialisation etc...
	}
	
	public int getId(){
		return this.id;
	}
	
	public Variable getVar(int i){
		return this.variables.get(i);
	}
	
	public Color getCouleur(){
		return this.couleur;
	}
	
	public Point getPoint(){
		return this.coords;
	}
	
	public void setCouleur(Color col){
		this.couleur = col;
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
	
	public void setPoint(Point p){
		this.coords = p;
	}
	
	public ArrayList<Variable> getList(){
		return this.variables;
	}
	
	public void setID(int id){
		this.id = id;
	}
	
}
