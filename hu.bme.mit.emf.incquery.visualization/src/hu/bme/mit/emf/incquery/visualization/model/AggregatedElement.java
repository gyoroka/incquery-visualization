package hu.bme.mit.emf.incquery.visualization.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;

public class AggregatedElement extends PatternElement {

	public AggregatedElement(String name,EObject o,Pattern p) {
		super(name,o,p);
	}
	public AggregatedElement(String name,EObject o,Pattern p,boolean b)
	{
		super(name,o,p,b);
	}

}
