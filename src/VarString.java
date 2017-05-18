
public class VarString extends Variable{

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
	public int getTypeVar() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
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
