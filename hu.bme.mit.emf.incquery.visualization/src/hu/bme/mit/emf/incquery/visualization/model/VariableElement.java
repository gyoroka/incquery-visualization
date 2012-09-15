package hu.bme.mit.emf.incquery.visualization.model;

public class VariableElement extends MyNode {
	private boolean isParameter;
	private String classifierName;
	public VariableElement(String name) {
		super(name);
		isParameter=false;
	}
	public VariableElement(String name,boolean isPara) {
		super(name);
		isParameter=isPara;
	}
	public boolean isParameter()
	{
		return isParameter;
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
