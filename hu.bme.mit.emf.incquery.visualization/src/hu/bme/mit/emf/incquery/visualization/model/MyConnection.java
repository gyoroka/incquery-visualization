package hu.bme.mit.emf.incquery.visualization.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;

public class MyConnection {
//	private String id; 
	private EObject origin;
	private Pattern pattern;
	private String label; 
	final MyNode source;
	final MyNode destination;
	private boolean negative;
	
	public MyConnection(String label, MyNode source, MyNode destination) {
//		this.id = id;
		this.label = label;
		this.source = source;
		this.destination = destination;
		negative=false;
	}
	public MyConnection(String label, MyNode source, MyNode destination,EObject o,Pattern p) {
//		this.id = id;
		this.label = label;
		this.source = source;
		this.destination = destination;
		origin=o;
		pattern=p;
		negative=false;
	}

	public String getLabel() {
		//System.out.println(label);
		return label;
	}
	
	public MyNode getSource() {
		return source;
	}
	public MyNode getDestination() {
		return destination;
	}
	
	public EObject getOrigin()
	{
		return origin;
	}
	
	public Pattern getPattern()
	{
		return pattern;
	}
	
	public void setNegative(boolean n)
	{
		negative=n;
	}
	public boolean isNegative()
	{
		return negative;
	}
	
}
