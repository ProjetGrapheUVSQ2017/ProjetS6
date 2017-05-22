
public class VarFloat extends Variable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8699786705793512893L;
	private float val;
	public VarFloat(float val){
		this.val=val;
	}
	@Override
	public float getFloat(){
		return this.val;
	}
	public void setFloat(float val){
		 this.val=val;
	}
	@Override
	public String getTypeVar() {
		return "Float";
	}

	@Override
	public String toString() {
		return Float.toString(val);
	}
	@Override
	public int getInt() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

}
