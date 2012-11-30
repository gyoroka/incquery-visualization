package hu.bme.mit.emf.incquery.visualization.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;

public class VariableElement extends MyNode {
	private boolean isParameter;
	private boolean isTemporary;
	private String classifierName;
	public VariableElement(String name,EObject o,Pattern p) {
		super(name,o,p);
		if (name.startsWith("_")) isTemporary=true;
		else isTemporary=false;
		isParameter=false;
	}
	public VariableElement(String name,EObject o,Pattern p,boolean isPara) {
		super(name,o,p);
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
