
public class VarString extends Variable{

		private String val;
		public VarString(String val){
			this.val=val;
		}
		public String getVal(){
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

}
