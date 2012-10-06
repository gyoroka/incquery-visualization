package hu.bme.mit.emf.incquery.visualization.model;

public class VariableElement extends MyNode {
	private boolean isParameter;
	private boolean isTemporary;
	private String classifierName;
	public VariableElement(String name) {
		super(name);
		if (name.startsWith("_")) isTemporary=true;
		else isTemporary=false;
		isParameter=false;
	}
	public VariableElement(String name,boolean isPara) {
		super(name);
		if (name.startsWith("_")) isTemporary=true;
		else isTemporary=false;
		isParameter=isPara;
	}
	public boolean isParameter()
	{
		return isParameter;
	}
	public boolean isTemporary()
	{
		return isTemporary;
	}
	public void setClassifierName(String s)
	{
		classifierName=s;
	}
	public String getClassifierName()
	{
		return classifierName;
	}

}
