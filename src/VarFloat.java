
public class VarFloat extends Variable {
	private float val;
	public VarFloat(float val){
		this.val=val;
	}
	public float getFloat(){
		return this.val;
	}
	public void setFloat(float val){
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
