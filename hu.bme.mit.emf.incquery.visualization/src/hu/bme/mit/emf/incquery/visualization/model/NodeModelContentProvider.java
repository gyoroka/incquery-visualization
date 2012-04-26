package hu.bme.mit.emf.incquery.visualization.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.*;
import org.eclipse.viatra2.patternlanguage.eMFPatternLanguage.ClassType;
import org.eclipse.viatra2.patternlanguage.eMFPatternLanguage.EClassifierConstraint;
import org.eclipse.viatra2.patternlanguage.eMFPatternLanguage.ReferenceType;

import hu.bme.mit.emf.incquery.visualization.model.MyConnection;
import hu.bme.mit.emf.incquery.visualization.model.MyNode;


public class NodeModelContentProvider {
	private List<MyConnection> connections;
	private List<MyNode> nodes;
	private int i;
	private int bodycount=0;

	public NodeModelContentProvider(PatternModel model) {
		
		

		//EMFModelLoad loader = new EMFModelLoad();
		//Resource resource= loader.Load(input);
		//EObject resource=model;
		System.out.println(model.toString());
		
		EList<Pattern> patterns = model.getPatterns();
		String s;
		i=1;
		Iterator<Pattern> patterniterator = patterns.iterator();
		nodes = new ArrayList<MyNode>();
		connections = new ArrayList<MyConnection>();
		MyNode node=new MyNode(""+0,"Patterns");
		nodes.add(node);
		while (patterniterator.hasNext())
		{

			add(patterniterator.next(),node);
			//s=newnode.eGet(newnode.eClass().getEStructuralFeature("name")).toString();	
			
			
		}
		

		for (MyConnection connection : connections) {
			connection.getSource().getConnectedTo()
					.add(connection.getDestination());
		}
	}
	
	private void addNode(MyNode akt, MyNode src)
	{
		
		System.out.println(akt.getId()+". "+akt.getName());
		nodes.add(akt);
		MyConnection connect = new MyConnection(""+i, ""+i,src,akt);
		connections.add(connect);
		i++;
	}
	
	private void add(EObject eo,MyNode src)
	{
		if (eo instanceof VariableReference)
		{
			add((VariableReference)eo,src);
		}
		if (eo instanceof ClassType)
		{
			add((ClassType)eo,src);
		}
		if (eo instanceof PathExpressionHead)
		{
			add((PathExpressionHead)eo,src);
		}
	}
	private void add(IntValue iv, MyNode src)
	{
		MyNode node = new MyNode(""+i,"IntValue: "+iv.getValue());
		addNode(node,src);
	}
	private void add(StringValue sv, MyNode src)
	{
		MyNode node = new MyNode(""+i,"StringValue: "+sv.getValue());
		addNode(node,src);
	}
	private void add(BoolValue bv, MyNode src)
	{
		MyNode node = new MyNode(""+i,"BoolValue: "+bv.isValue());
		addNode(node,src);
	}
	private void add(DoubleValue dv, MyNode src)
	{
		MyNode node = new MyNode(""+i,"DoubleValue: "+dv.getValue());
		addNode(node,src);
	}
	private void add(VariableValue vv, MyNode src)
	{
		MyNode node = new MyNode(""+i,"VariableValue: "+vv.getValue().getVar());
		addNode(node,src);
	}
	private void add(ListValue lv, MyNode src)
	{
		Iterator<ValueReference> vri =lv.getValues().iterator();
		while (vri.hasNext())
		{
			add(vri.next(),src);
		}
	}
	private void add(ValueReference vr, MyNode src)
	{
		if (vr instanceof IntValue)
		{
			add((IntValue)vr,src);
		}
		if (vr instanceof StringValue)
		{
			add((StringValue)vr,src);
		}
		if (vr instanceof BoolValue)
		{
			add((BoolValue)vr,src);
		}
		if (vr instanceof DoubleValue)
		{
			add((DoubleValue)vr,src);
		}
		if (vr instanceof VariableValue)
		{
			add((VariableValue)vr,src);
		}
		if (vr instanceof ListValue)
		{
			add((ListValue)vr,src);
		}
	}
	private void add(VariableReference varref,MyNode src)
	{
		MyNode node=new MyNode(""+i,"VariableReference: "+varref.getVar());
		addNode(node,src);
	}
	private void add(ClassType ctype,MyNode src)
	{
		MyNode node=new MyNode(""+i,"ClassType: "+ctype.getTypename());
		addNode(node,src);
	}
	/*
	private void add(String s,PathExpressionTail pet,MyNode src)
	{
		//System.out.println(pet.getType().eContainingFeature().toString());
		String s1="";
		Type t=pet.getType();
		ClassType ct;
		if (t instanceof RelationType)
		{
			RelationType rt=(RelationType)t;
			ReferenceType rft=(ReferenceType)rt;
			s1=rft.getRefname().getName();
		}
		if (t instanceof EntityType)
		{
			EntityType et=(EntityType)t;
			ct=(ClassType)et;
			s1=ct.getClassname().getName();
		}
		s1+=s+"."+s1;
		if (pet.getTail()!=null)
		{
			add(s1,pet.getTail(),src);
		}
		else
		{
			MyNode node=new MyNode(""+i,"PathExpression: "+s1);
			addNode(node,src);
		}
	}
	*/
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
	private void add(PathExpressionHead peh,MyNode src)
	{
		String s="";
		Type t=peh.getType();
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
		if (peh.getTail()!=null)
		{
			s+="."+getTail(peh.getTail());
		}
		s+="("+peh.getSrc().getVar()+","+getString(peh.getDst())+")";
		MyNode node=new MyNode(""+i,"PathExpression: "+s);
		addNode(node,src);
	}
	private void add(PatternCompositionConstraint pcc,MyNode src)
	{
		Iterator<ValueReference> pi=pcc.getParameters().iterator();
		while (pi.hasNext())
		{
			add(pi.next(),src);
		}
		MyNode node = new MyNode(""+i,pcc.getPatternRef().getName());
		addNode(node,src);
	}
	private void add(CompareConstraint cc,MyNode src)
	{
		MyNode node = new MyNode(""+i,"CompareConstraint: "+cc.getFeature().getLiteral());
		addNode(node,src);
		add(cc.getLeftOperand(),node);
		add(cc.getRightOperand(),node);
	}
	private void add(CheckConstraint cc,MyNode src)
	{
		System.out.print("CheckConstraint");
	}
	private void add(PathExpressionConstraint pec,MyNode src)
	{
		add(pec.getHead(),src);
	}
	private void add(EClassifierConstraint ecc, MyNode src)
	{
		ClassType ct=(ClassType)ecc.getType();
		MyNode node = new MyNode(""+i,"EClassifierConstraint: "+ct.getClassname().getName()+"("+ecc.getVar().getVar()+")");
		addNode(node,src);
	}
	private void add(Constraint ec,MyNode src)
	{	
		if (ec instanceof PatternCompositionConstraint) 
		{
			add((PatternCompositionConstraint)ec,src);
		}
		if (ec instanceof CompareConstraint) 
		{
			add((CompareConstraint)ec,src);
		}
		if (ec instanceof CheckConstraint) 
		{
			add((CheckConstraint)ec,src);
		}
		if (ec instanceof PathExpressionConstraint) 
		{
			add((PathExpressionConstraint)ec,src);
		}
		if (ec instanceof EClassifierConstraint)
		{
			add((EClassifierConstraint)ec,src);
		}
		/*
		Iterator<EObject> contiterator = ec.eContents().iterator();
		while (contiterator.hasNext())
		{
			add(contiterator.next(),src);
		}	
		*/	
	}
	
	private void add(PatternBody pb, MyNode src)
	{
		String s=""+bodycount;
		MyNode node = new MyNode(""+i,"Body: "+s);
		addNode(node,src);		
		//parameters
		Iterator<Variable> parametersiterator = pb.getVariables().iterator();
		while (parametersiterator.hasNext())
		{
			add(parametersiterator.next(),node);
			

		}		
		//constrains
		Iterator<Constraint> constraintiterator = pb.getConstraints().iterator();
		while (constraintiterator.hasNext())
		{
			add(constraintiterator.next(),node);
		}
	}
	
	private void add(Variable v, MyNode src)
	{
		String s=v.getName();
		MyNode aktnode= new MyNode(""+i,"Variable:"+s);
		addNode(aktnode,src);
	}
	private void add(Pattern p, MyNode src)
	{
		String s;
		s=p.getName();
		bodycount=0;
		MyNode node= new MyNode(""+i,"Pattern:"+s);
		addNode(node,src);
		
		
		/*
		//annotation
		Iterator<Annotation> annotationiterator = p.getAnnotations().iterator();
		while (annotationiterator.hasNext())
		{
			i++;
			Annotation aktannotation = annotationiterator.next();
			//s=newnode.eGet(newnode.eClass().getEStructuralFeature("name")).toString();	
			s=aktannotation.getName();
			aktnode= new MyNode(""+i,"Annotation:"+s);
			System.out.println(i+". "+s);
			nodes.add(aktnode);	
			connect = new MyConnection(""+i, ""+i,node,aktnode);
			connections.add(connect);
		}
		*/
		
		//body
		Iterator<PatternBody> patternbodyiterator = p.getBodies().iterator();
		while (patternbodyiterator.hasNext())
		{	
			bodycount++;
			add(patternbodyiterator.next(),node);		
		}
		
		//parameters
		Iterator<Variable> parametersiterator = p.getParameters().iterator();
		while (parametersiterator.hasNext())
		{
			add(parametersiterator.next(),node);
		}
		/*
		//modifiers
		Iterator<Modifiers> modifiersiterator = p.getModifiers().iterator();
		while (modifiersiterator.hasNext())
		{
			i++;
			Modifiers aktmodifier = modifiersiterator.next();
			//s=newnode.eGet(newnode.eClass().getEStructuralFeature("name")).toString();	
			s=aktmodifier.toString();
			aktnode= new MyNode(""+i,"Modifiers:"+s);
			System.out.println(i+". "+s);
			nodes.add(aktnode);
			connect = new MyConnection(""+i, ""+i,node,aktnode);
			connections.add(connect);
			

		}
		*/
	}
	
	public String getString(IntValue iv)
	{
		return ""+iv.getValue();
	}
	public String getString(StringValue sv)
	{
		return sv.getValue();
	}
	public String getString(BoolValue bv)
	{
		return ""+bv.isValue();
	}
	public String getString(DoubleValue dv)
	{
		return ""+dv.getValue();
	}
	public String getString(VariableValue vv)
	{
		return vv.getValue().getVar();
	}
	public String getString(ListValue lv)
	{
		String s=null;
		Iterator<ValueReference> vri =lv.getValues().iterator();
		while (vri.hasNext())
		{
			s+=getString(vri.next())+";" ;
		}
		if (s!=null) s=s.substring(0, s.lastIndexOf(";"));
		return s;
	}
	public String getString(ValueReference vr)
	{
		if (vr instanceof IntValue)
		{
			return getString((IntValue)vr);
		}
		if (vr instanceof StringValue)
		{
			return getString((StringValue)vr);
		}
		if (vr instanceof BoolValue)
		{
			return getString((BoolValue)vr);
		}
		if (vr instanceof DoubleValue)
		{
			return getString((DoubleValue)vr);
		}
		if (vr instanceof VariableValue)
		{
			return getString((VariableValue)vr);
		}
		if (vr instanceof ListValue)
		{
			return getString((ListValue)vr);
		}
		return null;
	}
	
	public List<MyNode> getNodes() {
		
		return nodes;
	}
}
