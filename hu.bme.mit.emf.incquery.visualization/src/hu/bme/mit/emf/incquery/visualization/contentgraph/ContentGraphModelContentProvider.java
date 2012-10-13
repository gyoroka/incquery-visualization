package hu.bme.mit.emf.incquery.visualization.contentgraph;

import hu.bme.mit.emf.incquery.visualization.model.MyNode;

import java.util.List;

import org.eclipse.viatra2.patternlanguage.core.patternLanguage.CheckConstraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.CompareConstraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Constraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.EntityType;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PathExpressionConstraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PathExpressionHead;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PathExpressionTail;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternBody;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternCall;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternCompositionConstraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.RelationType;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Type;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.ValueReference;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Variable;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.VariableReference;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.VariableValue;
import org.eclipse.viatra2.patternlanguage.eMFPatternLanguage.ClassType;
import org.eclipse.viatra2.patternlanguage.eMFPatternLanguage.EClassifierConstraint;
import org.eclipse.viatra2.patternlanguage.eMFPatternLanguage.ReferenceType;
import org.eclipse.viatra2.patternlanguage.types.IEMFTypeProvider;

public class ContentGraphModelContentProvider {
	private ContentGraphModel cgm;
	public List<MyNode> getNodes()
	{
		return cgm.getNodes();
	}
	public ContentGraphModelContentProvider(Pattern pattern, IEMFTypeProvider iEMFTypeProvider)
	{
		cgm= new ContentGraphModel(iEMFTypeProvider,pattern);
		for (Variable p:pattern.getParameters())
		{
			cgm.addParameter(p);
		}
		for (PatternBody pb: pattern.getBodies())
		{
			add(pb);
		}
	}
	public void add(PatternBody pb) {
		for (Variable v:pb.getVariables())
		{
			cgm.addVariable(v);
		}
		for (Constraint c:pb.getConstraints())
		{
			add(c);
		}
	}
	public void add(Constraint c)
	{
		if (c instanceof PathExpressionConstraint) add((PathExpressionConstraint)c);
		if (c instanceof EClassifierConstraint) add((EClassifierConstraint)c);
		if (c instanceof CompareConstraint) add((CompareConstraint)c);
		if (c instanceof PatternCompositionConstraint) add((PatternCompositionConstraint)c);
		if (c instanceof CheckConstraint) add((CheckConstraint)c);
		
	}
	public void add(PathExpressionConstraint pec)
	{
		PathExpressionHead peh=pec.getHead();
		String tail="";
		if (peh.getTail()!=null)
		{
			tail+="."+getTail(peh.getTail());
		}
		if (tail.startsWith(".")) tail=tail.substring(1);
//		ValueReference vr=peh.getDst();
		cgm.addPathExpression(pec,tail);
	}
	public void add(EClassifierConstraint ecc) 
	{
		ClassType ct=(ClassType)ecc.getType();
		cgm.addClassifier(ecc,ecc.getVar().getVariable().getName());
	}
	public void add(CompareConstraint cc)
	{
//		String s=cc.getFeature().getLiteral();
//		cgm.addCompare(cc.getLeftOperand(), cc.getRightOperand(), s);
		cgm.addCompare(cc);
	}
	public void add(PatternCompositionConstraint pcc)
	{
		PatternCall pc=pcc.getCall();
		//Pattern p=pc.getPatternRef();
		cgm.addPatternComposition(pc,pcc.isNegative());
	}
	public void add(CheckConstraint cc)
	{
		cgm.addCheck(cc);
	}
	
	private String getTail(PathExpressionTail pet)
	{
		String s="";
		Type t=pet.getType();
		ClassType ct;
		if (t instanceof RelationType)
		{
			RelationType rt=(RelationType)t;
			ReferenceType rft=(ReferenceType)rt;
			s=rft.getRefname().getName();
		}
		if (t instanceof EntityType)
		{
			EntityType et=(EntityType)t;
			ct=(ClassType)et;
			s=ct.getClassname().getName();
		}
		if (pet.getTail()!=null)
		{
			s+="."+getTail(pet.getTail());
		}
		return s;
	}
}
