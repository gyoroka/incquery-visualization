package hu.bme.mit.emf.incquery.visualization.model;

import java.util.ArrayList;

import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;

public class AggregatedElement extends PatternElement {

	public AggregatedElement(String name) {
		super(name);
	}
	public AggregatedElement(String name,Pattern p) {
		super(name,p);
	}
	public AggregatedElement(String name,boolean b)
	{
		super(name,b);
	}

}
