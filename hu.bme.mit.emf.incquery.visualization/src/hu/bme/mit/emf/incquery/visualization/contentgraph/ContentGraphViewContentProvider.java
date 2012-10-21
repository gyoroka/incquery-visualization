package hu.bme.mit.emf.incquery.visualization.contentgraph;

import hu.bme.mit.emf.incquery.visualization.model.MyConnection;
import hu.bme.mit.emf.incquery.visualization.model.MyNode;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.IGraphEntityRelationshipContentProvider;

public class ContentGraphViewContentProvider extends ArrayContentProvider
		implements IGraphEntityRelationshipContentProvider {

	@Override
	public Object[] getRelationships(Object source, Object dest) {
		if (source instanceof MyNode) {
			MyNode node = (MyNode) source;
			List<MyConnection> conns = node.getConnectedTo();
			List<MyConnection> retcon = new ArrayList<MyConnection>();
			for (MyConnection c : conns) {
				if (c.getDestination().equals(dest)) {
					retcon.add(c);
				}
			}
			return retcon.toArray();
		}
		return null;
	}

}
