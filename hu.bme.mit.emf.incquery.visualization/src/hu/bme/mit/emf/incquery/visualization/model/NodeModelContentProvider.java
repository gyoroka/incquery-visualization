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
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Annotation;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Constraint;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Modifiers;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternBody;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternModel;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Variable;

import hu.bme.mit.emf.incquery.visualization.model.MyConnection;
import hu.bme.mit.emf.incquery.visualization.model.MyNode;


public class NodeModelContentProvider {
	private List<MyConnection> connections;
	private List<MyNode> nodes;

	public NodeModelContentProvider(PatternModel model) {
		
		
		
		int i=0;
		int bodycount=0;
		//EMFModelLoad loader = new EMFModelLoad();
		//Resource resource= loader.Load(input);
		EObject resource=model;
		System.out.println(model.toString());
		
		EList<Pattern> patterns = model.getPatterns();
		i=0;
		String s;
		Iterator<Pattern> patterniterator = patterns.iterator();
		nodes = new ArrayList<MyNode>();
		connections = new ArrayList<MyConnection>();
		MyNode node,aktnode,bodynode;
		MyConnection connect =null;
		while (patterniterator.hasNext())
		{
			i++;
			Pattern aktpattern = patterniterator.next();
			//s=newnode.eGet(newnode.eClass().getEStructuralFeature("name")).toString();	
			s=aktpattern.getName();
			node= new MyNode(""+i,"Pattern:"+s);
			System.out.println(i+". "+s);
			nodes.add(node);
			
			//annotation
			Iterator<Annotation> annotationiterator = aktpattern.getAnnotations().iterator();
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
			
			//body
			Iterator<PatternBody> patternbodyiterator = aktpattern.getBodies().iterator();
			while (patternbodyiterator.hasNext())
			{
				i++;
				bodycount++;
				PatternBody aktpatternbody = patternbodyiterator.next();
				//s=newnode.eGet(newnode.eClass().getEStructuralFeature("name")).toString();	
				s=""+bodycount;
				bodynode= new MyNode(""+i,"Body:"+s);
				System.out.println(i+". "+s);
				nodes.add(bodynode);	
				connect = new MyConnection(""+i, ""+i,node,bodynode);
				connections.add(connect);
				
				//parameters
				Iterator<Variable> parametersiterator = aktpatternbody.getVariables().iterator();
				while (parametersiterator.hasNext())
				{
					i++;
					Variable aktvariable = parametersiterator.next();
					//s=newnode.eGet(newnode.eClass().getEStructuralFeature("name")).toString();	
					s=aktvariable.getName()+":"+aktvariable.getType();
					aktnode= new MyNode(""+i,"Variable:"+s);
					System.out.println(i+". "+s);
					nodes.add(aktnode);
					connect = new MyConnection(""+i, ""+i,bodynode,aktnode);
					connections.add(connect);
					

				}
				
				
				//constrains
				Iterator<Constraint> constraintiterator = aktpatternbody.getConstraints().iterator();
				while (constraintiterator.hasNext())
				{
					i++;
					Constraint aktconstraint = constraintiterator.next();
					//s=newnode.eGet(newnode.eClass().getEStructuralFeature("name")).toString();	
					Iterator<EObject> contiterator = aktconstraint.eContents().iterator();
					while (contiterator.hasNext())
					{
						EObject aktcont = contiterator.next();
						s=aktcont.eClass().getName();
						aktnode= new MyNode(""+i,"Constrains:"+s);
						System.out.println(i+". "+s);
						nodes.add(aktnode);
						connect = new MyConnection(""+i, ""+i,bodynode,aktnode);
						connections.add(connect);
					}
					

				}
				
				
			}
			
			//parameters
			Iterator<Variable> parametersiterator = aktpattern.getParameters().iterator();
			while (parametersiterator.hasNext())
			{
				i++;
				Variable aktparameter = parametersiterator.next();
				//s=newnode.eGet(newnode.eClass().getEStructuralFeature("name")).toString();	
				s=aktparameter.getName()+":"+aktparameter.getType();
				aktnode= new MyNode(""+i,"Parameters:"+s);
				System.out.println(i+". "+s);
				nodes.add(aktnode);
				connect = new MyConnection(""+i, ""+i,node,aktnode);
				connections.add(connect);
				

			}
			
			//modifiers
			Iterator<Modifiers> modifiersiterator = aktpattern.getModifiers().iterator();
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
			
			
		}
		
		
		//nodes.add(new MyNode(""+i,""+model.getPackageName()));
		/*
		//reference
		String contentname,sourcename,destinationname,tmpname,newnodename;
		MyNode source=null,destination,tmp;
		EObject content;
		connections = new ArrayList<MyConnection>();
		MyConnection connect =null;
		resiterator= resource.eAllContents();
		Iterator<MyNode> nodesiterator;
		Iterator<EObject>  contentiterator= null;
		i=0;
		while (resiterator.hasNext())
		{
			EObject newnode = resiterator.next();
			newnodename=newnode.eGet(newnode.eClass().getEStructuralFeature(0)).toString();
			
			nodesiterator=nodes.iterator();
			while (nodesiterator.hasNext())
			{
				tmp=nodesiterator.next();
				tmpname=tmp.getName();
				if (tmpname.equals(newnodename))
				{
					source=tmp;
				}
			}
			contentiterator = newnode.eContents().iterator();
			while (contentiterator.hasNext())
			{				
				content=contentiterator.next();
				contentname=content.eGet(content.eClass().getEStructuralFeature(0)).toString();
				nodesiterator=nodes.iterator();
				while (nodesiterator.hasNext())
				{
					tmp=nodesiterator.next();
					tmpname=tmp.getName();
					if (tmpname.equals(contentname))
					{
						i++;
						destination=tmp;
						System.out.println(i+": "+newnodename+" -> "+tmpname);
						connect = new MyConnection(""+i, ""+i,source,destination);
						connections.add(connect);
					}
				}
			}	
		}
		//content
		resiterator= resource.eAllContents();
		while (resiterator.hasNext())
		{
			EObject newnode = resiterator.next();
			newnodename=newnode.eGet(newnode.eClass().getEStructuralFeature(0)).toString();
			
			nodesiterator=nodes.iterator();
			while (nodesiterator.hasNext())
			{
				tmp=nodesiterator.next();
				tmpname=tmp.getName();
				if (tmpname.equals(newnodename))
				{
					source=tmp;
				}
			}
			contentiterator = newnode.eCrossReferences().iterator();
			while (contentiterator.hasNext())
			{				
				content=contentiterator.next();
				contentname=content.eGet(content.eClass().getEStructuralFeature(0)).toString();
				nodesiterator=nodes.iterator();
				while (nodesiterator.hasNext())
				{
					tmp=nodesiterator.next();
					tmpname=tmp.getName();
					if (tmpname.equals(contentname))
					{
						i++;
						destination=tmp;
						System.out.println(i+": "+newnodename+" -> "+tmpname);
						connect = new MyConnection(""+i, ""+i,source,destination);
						connections.add(connect);
					}
				}
			}
			
		}
		*/
		
		//connections = new ArrayList<MyConnection>();

		for (MyConnection connection : connections) {
			connection.getSource().getConnectedTo()
					.add(connection.getDestination());
		}
	}

	public List<MyNode> getNodes() {
		
		return nodes;
	}
}
