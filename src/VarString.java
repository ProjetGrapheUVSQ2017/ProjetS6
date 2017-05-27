/**
 * Implémentation de variable sous forme de String
 * @author Aziz
 *
 */
public class VarString extends Variable{

	private static final long serialVersionUID = -166926269467455150L;
	/**
	 * Valeur de la variable
	 */
		private String val;
		
		/**
		 * Constructeur de la variable avec la valeur initiale
		 * @param val
		 */
		public VarString(String val){
			this.val=val;
		}
		
		/**
		 * Getter de la valeur
		 */
		@Override
		public String getString(){
			return this.val;
		}
		
		/**
		 * Setter de la valeur
		 * @param val nouvelle valeur
		 */
		public void setVal(String val){
			this.val=val;
		}
		
	/**
	 * Renvoi le type de la valeur sous forme de chaine de caractère pour l'affichage sur l'interface
	 */
	@Override
	public String getTypeVar() {
		return "String";
	}

	
	@Override
	public String toString() {
		return val;
	}

	@Override
	public int getInt() {
		return 0;
	}

	@Override
	public float getFloat() {
		return 0;
	}

	

}
