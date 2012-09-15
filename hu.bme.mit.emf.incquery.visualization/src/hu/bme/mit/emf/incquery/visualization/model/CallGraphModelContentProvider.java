package hu.bme.mit.emf.incquery.visualization.model;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.CheckConstraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.CompareConstraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Constraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PathExpressionConstraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternBody;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternCompositionConstraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternModel;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.ValueReference;
import org.eclipse.viatra2.patternlanguage.eMFPatternLanguage.EClassifierConstraint;

public class CallGraphModelContentProvider {
	private int bodycount;
	private CallGraphModel cgm;
	
	public CallGraphModelContentProvider(PatternModel model)
	{
		cgm=new CallGraphModel();
		for (Pattern p : model.getPatterns()) {
			add(p,null,false);
		}
	}
	
	private void add(Pattern p, MyNode src,boolean negative)
	{
		String s;
		s=p.getName();
		bodycount=0;
		PatternElement node= new PatternElement(s,p);
		cgm.addPattern(node, src,negative);
		
		//body
		for (PatternBody pb : p.getBodies()) {
			bodycount++;
			add(pb,node);
		}
		
	}
	
	private void add(PatternBody pb, MyNode src)
	{
		String s=""+bodycount;
		MyNode node = new MyNode("Body: "+s);
		cgm.addBodie(node,src);	
		
		//constrains
		for (Constraint c : pb.getConstraints()) {
			add(c,node);
		}
	}
	
	private void add(PatternCompositionConstraint pcc,MyNode src)
	{
		PatternElement node = new PatternElement(pcc.getCall().getPatternRef().getName(),pcc.getCall().getPatternRef());
		cgm.addPattern(node,src,pcc.isNegative());
	}
	
	private void add(Constraint ec,MyNode src)
	{	
		if (ec instanceof PatternCompositionConstraint) 
		{
			add((PatternCompositionConstraint)ec,src);
		}
	}
	
	public List<MyNode> getNodes()
	{
		return cgm.getNodes();
	}
}
