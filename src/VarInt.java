
public class VarInt extends Variable{
	
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
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public float getFloat() {
		// TODO Auto-generated method stub
		return 0;
	}
}
