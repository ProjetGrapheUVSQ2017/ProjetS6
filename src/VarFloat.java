/**
 * Variable implémentant un flottant
 * @author Aziz
 *
 */
public class VarFloat extends Variable {
	private static final long serialVersionUID = 8699786705793512893L;
	/**
	 * Valeur de la variable
	 */
	private float val;
	
	/**
	 * Constructeur de la variable
	 * @param val valeur d'origine de la variable
	 */
	public VarFloat(float val){
		this.val=val;
	}
	
	/**
	 * Getter de la variable
	 * @return float
	 */
	@Override
	public float getFloat(){
		return this.val;
	}
	
	/**
	 * Setter de la valeur flottante
	 * @param val
	 */
	public void setFloat(float val){
		 this.val=val;
	}
	
	/**
	 * Renvoie le type de la variable sous forme de string.
	 * Permet l'affichage du type dans l'interface
	 * @return String
	 */
	@Override
	public String getTypeVar() {
		return "Float";
	}

	/**
	 * Renvoie la valeur sous forme de String pour l'affichage
	 */
	@Override
	public String toString() {
		return Float.toString(val);
	}
	
	
	@Override
	public int getInt() {
		return 0;
	}
	
	@Override
	public String getString() {
		return null;
	}

}
