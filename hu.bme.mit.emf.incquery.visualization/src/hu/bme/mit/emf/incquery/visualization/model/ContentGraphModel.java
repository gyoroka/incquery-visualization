package hu.bme.mit.emf.incquery.visualization.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.viatra2.patternlanguage.core.patternLanguage.BoolValue;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.DoubleValue;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.IntValue;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.LiteralValueReference;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.StringValue;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.ValueReference;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Variable;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.VariableReference;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.VariableValue;

public class ContentGraphModel {
	//public List<MyNode> parameters;
	public List<VariableElement> variables;
	public List<MyNode> ints,strings,bools,doubles,patterns;
	public List<MyNode> getNodes() {
		List<MyNode> tmp=new ArrayList<MyNode>();
		//tmp.addAll(parameters);
		tmp.addAll(variables);
		tmp.addAll(ints);
		tmp.addAll(strings);
		tmp.addAll(bools);
		tmp.addAll(doubles);
		tmp.addAll(patterns);
		return tmp;
	}
	public ContentGraphModel()
	{
		variables=new ArrayList<VariableElement>();
		ints=new ArrayList<MyNode>();
		strings=new ArrayList<MyNode>();
		bools=new ArrayList<MyNode>();
		doubles=new ArrayList<MyNode>();
		patterns=new ArrayList<MyNode>();
	}
	public void addParameter(Variable v)
	{
		VariableElement ve= new VariableElement(v.getName(),true);
		variables.add(ve);
	}
	public void addVariable(Variable v) {
		VariableElement ve= new VariableElement(v.getName());
		variables.add(ve);		
	}
	public void addPathExpression(VariableReference varr, ValueReference valr,String head,String tail) 
	{
		//System.out.println(s);
		// TODO Auto-generated method stub
		Variable src=varr.getVariable();
		MyNode snode=findVariable(src);
		MyNode dnode=getValueNode(valr);
		VariableElement varnode=(VariableElement)snode;
		if (varnode.getClassifierName()==null) varnode.setClassifierName(head);
		MyConnection conn=new MyConnection(tail,snode,dnode);
		snode.getConnectedTo().add(conn);
	}
	public void addClassifier(String cln,String name)
	{
		findVariable(name).setClassifierName(cln);
	}
	public void addCompare(ValueReference vl,ValueReference vr, String s)
	{
		MyNode left=getValueNode(vl);
		MyNode right=getValueNode(vr);
		MyConnection conn=new MyConnection(s,left,right);
		left.getConnectedTo().add(conn);
	}
	public VariableElement findVariable(Variable v)
	{
		String s=v.getName();
		return findVariable(s);
	}
	public VariableElement findVariable(String s)
	{
		for (VariableElement item:variables)
		{
			if (item.getName().equals(s)) return item;
		}
		return null;
	}
	public MyNode findInt(int i)
	{
		String s=Integer.toString(i);
		return findInt(s);
	}
	public MyNode findInt(String s)
	{
		for (MyNode item:ints)
		{
			if (item.getName().equals(s)) return item;
		}
		return null;
	}
	public MyNode findString(String s)
	{
		for (MyNode item:strings)
		{
			if (item.getName().equals(s)) return item;
		}
		return null;
	}
	public MyNode findBool(boolean l)
	{
		String s=Boolean.toString(l);
		return findInt(s);
	}
	public MyNode findBool(String s)
	{
		for (MyNode item:bools)
		{
			if (item.getName().equals(s)) return item;
		}
		return null;
	}
	public MyNode findDouble(double d)
	{
		String s=Double.toString(d);
		return findInt(s);
	}
	public MyNode findDouble(String s)
	{
		for (MyNode item:doubles)
		{
			if (item.getName().equals(s)) return item;
		}
		return null;
	}
	private MyNode getValueNode(ValueReference vr)
	{
		MyNode node=null;
		LiteralValueReference lvr=null;
		if (vr instanceof VariableValue) node=getVariableValue(((VariableValue)vr));
		if (vr instanceof LiteralValueReference)
		{
			lvr=(LiteralValueReference)vr;
			if (lvr instanceof IntValue) node=getIntValue((IntValue)lvr);
			if (lvr instanceof StringValue) node=getStringValue((StringValue)lvr);
			if (lvr instanceof BoolValue) node=getBoolValue((BoolValue)lvr);
			if (lvr instanceof DoubleValue) node=getDoubleValue((DoubleValue)lvr);
		}
		return node;
	}
	private MyNode getVariableValue(VariableValue vv)
	{
		Variable v=vv.getValue().getVariable();
		MyNode node=findVariable(v);
		if (node!=null) return node;
		//TODO
		return null;
	}
	private MyNode getIntValue(IntValue iv) {
		int i=iv.getValue();
		MyNode node=findInt(i);
		if (node!=null) return node;
		node = new MyNode(Integer.toString(i));
		ints.add(node);
		return node;
	}
	private MyNode getStringValue(StringValue s) {
		MyNode node=findString(s.getValue());
		if (node!=null) return node;
		node = new MyNode(s.getValue());
		strings.add(node);
		return node;
	}
	private MyNode getBoolValue(BoolValue bv) {
		boolean b=bv.isValue();
		MyNode node=findBool(b);
		if (node!=null) return node;
		node = new MyNode(Boolean.toString(b));
		bools.add(node);
		return node;
	}
	private MyNode getDoubleValue(DoubleValue dv) {
		double d=dv.getValue();
		MyNode node=findDouble(d);
		if (node!=null) return node;
		node = new MyNode(Double.toString(d));
		doubles.add(node);
		return node;
	}
	
	

}
