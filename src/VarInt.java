
public class VarInt extends Variable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5594894313407954692L;
	private int val;
	public  VarInt(int val){
		this.val=val;
	}
	@Override
	public int getInt(){
		return this.val;
	}
	public void setInt(int val){
		this.val=val;
	}
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
		// TODO Auto-generated method stub
		return 0;
	}
}
