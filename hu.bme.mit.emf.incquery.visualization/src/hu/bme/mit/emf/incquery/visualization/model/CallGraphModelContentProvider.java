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
	private int i;
	private int bodycount;
	private CallGraphModel cgm;
	
	public CallGraphModelContentProvider(PatternModel model)
	{
		cgm=new CallGraphModel();
		i=1;
		EList<Pattern> patterns = model.getPatterns();
		Iterator<Pattern> patterniterator = patterns.iterator();
		while (patterniterator.hasNext())
		{
			add(patterniterator.next(),null,false);	
		}
	}
	
	private void add(Pattern p, MyNode src,boolean negative)
	{
		String s;
		s=p.getName();
		bodycount=0;
		PatternElement node= new PatternElement(""+i,s,p);
		cgm.addPattern(node, src,negative);
		
		//body
		Iterator<PatternBody> patternbodyiterator = p.getBodies().iterator();
		while (patternbodyiterator.hasNext())
		{	
			bodycount++;
			add(patternbodyiterator.next(),node);		
		}
		
	}
	
	private void add(PatternBody pb, MyNode src)
	{
		String s=""+bodycount;
		MyNode node = new MyNode(""+i,"Body: "+s);
		cgm.addBodie(node,src);	
		
		//constrains
		Iterator<Constraint> constraintiterator = pb.getConstraints().iterator();
		while (constraintiterator.hasNext())
		{
			add(constraintiterator.next(),node);
		}
	}
	
	private void add(PatternCompositionConstraint pcc,MyNode src)
	{
		PatternElement node = new PatternElement(""+i,pcc.getPatternRef().getName(),pcc.getPatternRef());
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
