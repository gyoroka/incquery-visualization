package hu.bme.mit.emf.incquery.visualization.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;

public class MyNode {
	private String name;
	private EObject origin;
	private Pattern pattern;
	private List<MyConnection> connections;

	public MyNode(String name,EObject o,Pattern p) {
		this.name = name;
		origin=o;
		pattern=p;
		this.connections = new ArrayList<MyConnection>();
	}

	public void setName(String s) {
		name=s;
	}

	public String getName() {
		return name;
	}
	
	public void setOrigin(EObject o)
	{
		origin=o;
	}
	
	public EObject getOrigin()
	{
		return origin;
	}
	
	public Pattern getPattern()
	{
		return pattern;
	}

	public List<MyConnection> getConnectedTo() {
		return connections;
	}

}
