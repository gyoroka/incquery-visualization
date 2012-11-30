package hu.bme.mit.emf.incquery.visualization.callgraph;

import hu.bme.mit.emf.incquery.visualization.model.MyConnection;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef4.zest.core.viewers.IGraphEntityRelationshipContentProvider;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class CallGraphViewContentProvider extends ArrayContentProvider implements
        IGraphEntityRelationshipContentProvider {

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

        if (source instanceof PatternElement) {
            PatternElement node = (PatternElement) source;
            List<MyConnection> conns = node.getConnectedTo();
            List<MyConnection> retcon = new ArrayList<MyConnection>();
            for (MyConnection c : conns) {
                if (c.getDestination().equals(dest)) {
                    // retcon.add(c);
                    return new MyConnection[] { c };
                }
            }
            return retcon.toArray();
        }

        throw new RuntimeException("Type not supported");
    }

}
