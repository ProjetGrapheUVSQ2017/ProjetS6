
/**
 * Implémentation de la variable avec une valeur entière
 * @author Aziz
 *
 */
public class VarInt extends Variable{
	
	private static final long serialVersionUID = 5594894313407954692L;
	/**
	 * Valeur
	 */
	private int val;
	
	/**
	 * Constructeur de VarInt
	 * @param val int
	 */
	public  VarInt(int val){
		this.val=val;
	}
	
	/**
	 * Getter pour la valeur
	 * @return int
	 */
	@Override
	public int getInt(){
		return this.val;
	}
	
	/**
	 * Setter de la valeur
	 * @param val nouvelle valeur
	 */
	public void setInt(int val){
		this.val=val;
	}
	
	/**
	 * Renvoie le type de la variable sous forme de String pour l'affichage des variables
	 * @return String
	 */
	@Override
	public String getTypeVar() {
		return "Int";
	}

	
	@Override
	public String toString() {
		return Integer.toString(val);
	}
	
	
	@Override
	public String getString() {
		return null;
	}
	
	
	@Override
	public float getFloat() {
		return 0;
	}
}
