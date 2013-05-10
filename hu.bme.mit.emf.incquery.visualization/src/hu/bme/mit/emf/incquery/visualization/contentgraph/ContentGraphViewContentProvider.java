package hu.bme.mit.emf.incquery.visualization.contentgraph;

import hu.bme.mit.emf.incquery.visualization.model.CustomConnection;
import hu.bme.mit.emf.incquery.visualization.model.CustomNode;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef4.zest.core.viewers.IGraphEntityRelationshipContentProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;

public class ContentGraphViewContentProvider extends ArrayContentProvider implements
        IGraphEntityRelationshipContentProvider {

    @Override
    public Object[] getRelationships(Object source, Object dest) {
        if (source instanceof CustomNode) {
            CustomNode node = (CustomNode) source;
            List<CustomConnection> conns = node.getConnectedTo();
            List<CustomConnection> retcon = new ArrayList<CustomConnection>();
            for (CustomConnection c : conns) {
                if (c.getDestination().equals(dest)) {
                    retcon.add(c);
                }
            }
            return retcon.toArray();
        }
        return null;
    }

}
