package hu.bme.mit.emf.incquery.visualization.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;

public class PatternElement extends MyNode {

	private Pattern pattern;
	private List<String> parameters;
	private boolean count=false;
	
	public PatternElement(String name) {
		super(name);
		parameters=new ArrayList<String>();
	}
	public PatternElement(String name,Pattern p) {
		super(name);
		pattern=p;
		parameters=new ArrayList<String>();
	}
	public List<String> getParameters()
	{
		return parameters;
	}
	
	public Pattern getPattern()
	{
		return pattern;
	}
	public void setCount(boolean b)
	{
		count=b;
	}
	public boolean getCount()
	{
		return count;
	}
}
