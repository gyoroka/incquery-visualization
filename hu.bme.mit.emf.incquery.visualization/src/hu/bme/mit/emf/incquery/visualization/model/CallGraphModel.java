package hu.bme.mit.emf.incquery.visualization.model;

import java.util.ArrayList;
import java.util.List;

public class CallGraphModel {
	private List<MyNode> patterns;
	private List<MyNode> bodies;
	public CallGraphModel()
	{
		patterns=new ArrayList<MyNode>();
		bodies=new ArrayList<MyNode>();
	}
	
	public void addPattern(MyNode pattern,MyNode body, boolean negative)
	{
		boolean l=false;
		MyNode tmppattern=pattern;
		for (MyNode n:patterns)
		{
			if (n.getName().equals(pattern.getName())) 
			{
				l=true;
				tmppattern=n;
			}
		}
		if (!l) patterns.add(pattern);
		for (MyNode n:bodies)
		{
			if (n.equals(body)) 
			{
				MyConnection conn=new MyConnection("0",body.getName(),body,tmppattern);
				if (negative) conn.setNegative(true);
				body.getConnectedTo().add(conn);
			}
		}
	}
	public void addBodie(MyNode body,MyNode pattern)
	{
		bodies.add(body);
		for (MyNode n:patterns)
		{
			if (n.equals(pattern)) 
			{
				MyConnection conn=new MyConnection("0",body.getName(),pattern,body);
				pattern.getConnectedTo().add(conn);
			}
		}
	}
	
	public List<MyNode> getNodes()
	{
		List<MyNode> list=new ArrayList<MyNode>();
		list.addAll(patterns);
		list.addAll(bodies);
		return list;
	}

}
