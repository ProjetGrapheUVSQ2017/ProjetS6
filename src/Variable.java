import java.io.Serializable;

/**
 * Classe représentant les variables pouvant être associées à un arc ou un sommet
 * @author Aziz
 */
public abstract class Variable implements Serializable{


	private static final long serialVersionUID = 1L;
	/**
	 * ID identifiant la variable
	 */
	private int id;
	
	/**
	 * Booléen permettant de savoir si cette variable est le poids
	 */
	private boolean poids;

	/**
	 * Permet de récupérer l'id de la variable
	 * @return int
	 */
	public int getId(){
		return this.id;
	}
	/**
	 * Setter pour l'ID
	 * @param id : int
	 */
	public void setId(int id){
		this.id=id;
	}
	public abstract String getTypeVar();
	public abstract String toString();
	public abstract int getInt();
	public abstract String getString();
	public abstract float getFloat();
	public boolean isPoids(){
		if(this.poids)
			return true;
		else
			return false;
	}
	/**
	 * Permet de définir cette variable comme le poids de l'arc
	 * @param poids : boolean
	 */
	public void setPoids(boolean poids){
		this.poids=poids;
	}

}

