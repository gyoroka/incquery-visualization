package hu.bme.mit.emf.incquery.visualization.callgraph;

import hu.bme.mit.emf.incquery.visualization.model.AggregatedConnection;
import hu.bme.mit.emf.incquery.visualization.model.CustomConnection;
import hu.bme.mit.emf.incquery.visualization.model.CustomNode;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;
import hu.bme.mit.emf.incquery.visualization.view.CustomColorTheme;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef4.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.gef4.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.gef4.zest.core.widgets.ZestStyles;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;

public class CallGraphLabelProvider extends LabelProvider implements IConnectionStyleProvider, IEntityStyleProvider {

	CustomColorTheme ct;
	
	public CallGraphLabelProvider(CustomColorTheme colorTheme)
	{
		ct=colorTheme;
	}
	
    @Override
    public String getText(Object element) {
        if (element instanceof PatternElement) {
            PatternElement pe = (PatternElement) element;
            return pe.getName();
        }
        if (element instanceof CustomNode) {
            CustomNode myNode = (CustomNode) element;
            return myNode.getName();
        }
        // Not called with the IGraphEntityContentProvider
        if (element instanceof CustomConnection) {
            // MyConnection myConnection = (MyConnection) element;
            // return myConnection.getLabel();
            return "";
        }

        // if (element instanceof EntityConnectionData) {
        // EntityConnectionData test = (EntityConnectionData) element;
        // return "";
        // }

        throw new RuntimeException("Wrong type: " + element.getClass().toString());
    }

    @Override
    public Color getNodeHighlightColor(Object entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Color getBorderColor(Object entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Color getBorderHighlightColor(Object entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int getBorderWidth(Object entity) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Color getBackgroundColour(Object entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Color getForegroundColour(Object entity) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean fisheyeNode(Object entity) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getConnectionStyle(Object rel) {
        // TODO Auto-generated method stub
        if (rel instanceof AggregatedConnection)
            return ZestStyles.CONNECTIONS_DOT;
        return 0;
    }

    @Override
    public Color getColor(Object rel) {
        // TODO Auto-generated method stub
        if (rel instanceof AggregatedConnection) {
            return ct.getColor(CustomColorTheme.AGGREGATED);
        }
        if (rel instanceof CustomConnection) {
            CustomConnection conn = (CustomConnection) rel;
            if (conn.isNegative())
                return ct.getColor(CustomColorTheme.FIND_NEGATIVE);
        }
        return ct.getColor(CustomColorTheme.RELATION_DEFAULT);
    }

    @Override
    public Color getHighlightColor(Object rel) {
        return ct.getTextColor(CustomColorTheme.RELATION_DEFAULT);
    }

    @Override
    public int getLineWidth(Object rel) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public IFigure getTooltip(Object rel) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ConnectionRouter getRouter(Object rel) {
        // TODO Auto-generated method stub
        return null;
    }

}
