
public class VarString extends Variable{

		/**
	 * 
	 */
	private static final long serialVersionUID = -166926269467455150L;
		private String val;
		public VarString(String val){
			this.val=val;
		}
		
		@Override
		public String getString(){
			return this.val;
		}
		public void setVal(String val){
			this.val=val;
		}
		
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public float getFloat() {
		// TODO Auto-generated method stub
		return 0;
	}

	

}
