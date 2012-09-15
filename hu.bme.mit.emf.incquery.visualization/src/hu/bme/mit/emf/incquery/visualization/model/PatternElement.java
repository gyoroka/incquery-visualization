package hu.bme.mit.emf.incquery.visualization.model;

import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;

public class PatternElement extends MyNode {

	private Pattern pattern;
	
	public PatternElement(String name) {
		super(name);
	}
	public PatternElement(String name,Pattern p) {
		super(name);
		pattern=p;
	}
	
	public Pattern getPattern()
	{
		return pattern;
	}
}
