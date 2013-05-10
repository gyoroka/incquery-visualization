package hu.bme.mit.emf.incquery.visualization.contentgraph;

import hu.bme.mit.emf.incquery.visualization.model.CustomNode;

import java.util.List;

import org.eclipse.incquery.patternlanguage.emf.eMFPatternLanguage.ClassType;
import org.eclipse.incquery.patternlanguage.emf.eMFPatternLanguage.EClassifierConstraint;
import org.eclipse.incquery.patternlanguage.emf.eMFPatternLanguage.ReferenceType;
import org.eclipse.incquery.patternlanguage.emf.types.IEMFTypeProvider;
import org.eclipse.incquery.patternlanguage.patternLanguage.CheckConstraint;
import org.eclipse.incquery.patternlanguage.patternLanguage.CompareConstraint;
import org.eclipse.incquery.patternlanguage.patternLanguage.Constraint;
import org.eclipse.incquery.patternlanguage.patternLanguage.EntityType;
import org.eclipse.incquery.patternlanguage.patternLanguage.PathExpressionConstraint;
import org.eclipse.incquery.patternlanguage.patternLanguage.PathExpressionHead;
import org.eclipse.incquery.patternlanguage.patternLanguage.PathExpressionTail;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;
import org.eclipse.incquery.patternlanguage.patternLanguage.PatternBody;
import org.eclipse.incquery.patternlanguage.patternLanguage.PatternCall;
import org.eclipse.incquery.patternlanguage.patternLanguage.PatternCompositionConstraint;
import org.eclipse.incquery.patternlanguage.patternLanguage.RelationType;
import org.eclipse.incquery.patternlanguage.patternLanguage.Type;
import org.eclipse.incquery.patternlanguage.patternLanguage.Variable;

public class ContentGraphModelContentProvider {
	private ContentGraphModel cgm;
	public List<CustomNode> getNodes()
	{
		return cgm.getNodes();
	}
	public ContentGraphModelContentProvider(Pattern pattern, IEMFTypeProvider iEMFTypeProvider,int index)
	{
		cgm= new ContentGraphModel(iEMFTypeProvider,pattern);
		for (Variable p:pattern.getParameters())
		{
			cgm.addParameter(p);
		}
		add(pattern.getBodies().get(index));
//		for (PatternBody pb: pattern.getBodies())
//		{
//			add(pb);
//		}
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
		cgm.addPathExpression(pec,tail);
	}
	public void add(EClassifierConstraint ecc) 
	{
//		ClassType ct=(ClassType)ecc.getType();
		cgm.addClassifier(ecc,ecc.getVar().getVariable().getName());
	}
	public void add(CompareConstraint cc)
	{
		cgm.addCompare(cc);
	}
	public void add(PatternCompositionConstraint pcc)
	{
		PatternCall pc=pcc.getCall();
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
