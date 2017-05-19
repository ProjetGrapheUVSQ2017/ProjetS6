

public abstract class Variable {
  
private int id;
private boolean poids;
  
public int getId(){
	return this.id;
}
public void setId(int id){
	this.id=id;
}
public abstract String getTypeVar();
public abstract String toString();
public abstract int getInt();
public abstract String getString();
public abstract float getFloat();
public boolean isPoids(){
	if(this.poids)
		return true;
	else
		return false;
}
public void setPoids(boolean poids){
	this.poids=poids;
}
  
}

