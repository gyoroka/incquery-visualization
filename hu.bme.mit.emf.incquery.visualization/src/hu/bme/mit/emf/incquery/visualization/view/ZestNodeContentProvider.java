package hu.bme.mit.emf.incquery.visualization.view;

import hu.bme.mit.emf.incquery.visualization.model.MyConnection;
import hu.bme.mit.emf.incquery.visualization.model.MyNode;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

public class ZestNodeContentProvider extends ArrayContentProvider  implements IGraphEntityContentProvider {

	@Override
	public Object[] getConnectedTo(Object entity) {
		
		if (entity instanceof MyNode) {
			MyNode node = (MyNode) entity;
			List<MyConnection> conns=node.getConnectedTo();
			List<MyNode> nodes=new ArrayList<MyNode>();
			for (MyConnection c:conns)
			{
				nodes.add(c.getDestination());
			}
			return nodes.toArray();
		}
		
		throw new RuntimeException("Type not supported");
	}
}
