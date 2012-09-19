package hu.bme.mit.emf.incquery.visualization.model;

import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.AggregatedValue;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.CheckConstraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.CompareConstraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.ComputationValue;
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
			add(p);
		}
	}
	
	private void add(Pattern p)
	{
		//String s;
		//s=p.getName();
		bodycount=0;
		//PatternElement node= new PatternElement(s,p);
		cgm.addPattern(p);
		
		//body
		for (PatternBody pb : p.getBodies()) {
			bodycount++;
			add(p,pb,bodycount);
		}
		
	}
	
	private void add(Pattern p,PatternBody pb, int index)
	{
//		String s=""+bodycount;
//		MyNode node = new MyNode("Body: "+s);
//		cgm.addBodie(node,src);	
		
		//constrains
		for (Constraint c : pb.getConstraints()) {
			add(p,c,index);
		}
	}
	
	private void add(Pattern p,PatternCompositionConstraint pcc,int index)
	{
		//PatternElement node = new PatternElement(pcc.getCall().getPatternRef().getName(),pcc.getCall().getPatternRef());
		cgm.addPatternCall(p,pcc.getCall(),pcc.isNegative(),index);
	}
	private void add(Pattern p,ValueReference vr,int index)
	{
		if (vr instanceof ComputationValue)
		{
			AggregatedValue ag=(AggregatedValue)vr;
			cgm.addPatternCall(p,ag.getCall(),false,index);
		}
	}
	private void add(Pattern p,CompareConstraint cc,int index)
	{
		add(p,cc.getLeftOperand(),index);
		add(p,cc.getRightOperand(),index);
	}
	
	private void add(Pattern p,Constraint c,int index)
	{	
		if (c instanceof PatternCompositionConstraint) 
		{
			add(p,(PatternCompositionConstraint)c,index);
		}
		if (c instanceof CompareConstraint)
		{
			add(p,(CompareConstraint)c,index);
		}
	}
	
	public List<PatternElement> getNodes()
	{
		return cgm.getNodes();
	}
}
