package hu.bme.mit.emf.incquery.visualization.view;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

import hu.bme.mit.emf.incquery.visualization.model.MyNode;

public class ZestNodeContentProvider extends ArrayContentProvider  implements IGraphEntityContentProvider {

	@Override
	public Object[] getConnectedTo(Object entity) {
		if (entity instanceof MyNode) {
			MyNode node = (MyNode) entity;
			return node.getConnectedTo().toArray();
		}
		throw new RuntimeException("Type not supported");
	}
}
