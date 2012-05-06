package hu.bme.mit.emf.incquery.visualization.model;

import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;

public class PatternElement extends MyNode {

	private Pattern pattern;
	
	public PatternElement(String id, String name) {
		super(id, name);
	}
	public PatternElement(String id, String name,Pattern p) {
		super(id, name);
		pattern=p;
	}
	
	public Pattern getPattern()
	{
		return pattern;
	}
}
