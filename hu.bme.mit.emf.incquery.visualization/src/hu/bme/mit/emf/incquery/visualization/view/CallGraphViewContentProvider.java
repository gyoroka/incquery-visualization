package hu.bme.mit.emf.incquery.visualization.view;

import hu.bme.mit.emf.incquery.visualization.model.MyConnection;
import hu.bme.mit.emf.incquery.visualization.model.MyNode;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.zest.core.viewers.IGraphEntityRelationshipContentProvider;

public class CallGraphViewContentProvider extends ArrayContentProvider implements IGraphEntityRelationshipContentProvider  {

	@Override
	public Object[] getElements(Object inputElement) {
		
		List<MyNode> nodes=new ArrayList<MyNode>();
		try{
			List<Object> objects=(List<Object>) inputElement;
			for (Object o:objects)
			{
				if (o instanceof PatternElement) nodes.add((MyNode)o);
			}
		}
		catch(Exception e)
		{e.printStackTrace();}
		return nodes.toArray();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object[] getRelationships(Object source, Object dest) {
		if (source instanceof MyNode) {
			MyNode node = (MyNode) source;
			List<MyConnection> conns=node.getConnectedTo();
			List<MyConnection> retcon=new ArrayList<MyConnection>();
			for (MyConnection c:conns)
			{
				List<MyConnection> conns2=c.getDestination().getConnectedTo();
				for (MyConnection c2:conns2)
				{
					if (c2.getDestination().equals(dest)) 
					{
						MyConnection conn=new MyConnection(c.getLabel(),c.getSource(),c2.getDestination());
						if (c2.isNegative()) conn.setNegative(true);
						retcon.add(conn);
					}
				}
			}
			return retcon.toArray();
		}
		
		throw new RuntimeException("Type not supported");
	}

}
