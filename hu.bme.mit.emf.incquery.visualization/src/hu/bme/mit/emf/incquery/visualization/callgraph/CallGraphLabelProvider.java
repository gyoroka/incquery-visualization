package hu.bme.mit.emf.incquery.visualization.callgraph;

import hu.bme.mit.emf.incquery.visualization.model.AggregatedConnection;
import hu.bme.mit.emf.incquery.visualization.model.MyConnection;
import hu.bme.mit.emf.incquery.visualization.model.MyNode;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;
import hu.bme.mit.emf.incquery.visualization.view.Settings;
import hu.bme.mit.emf.incquery.visualization.view.Settings.Colors;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

public class CallGraphLabelProvider extends LabelProvider implements
IConnectionStyleProvider, IEntityStyleProvider
{

	@Override
	public String getText(Object element) {
		if (element instanceof PatternElement) {
			PatternElement pe = (PatternElement) element;
			return pe.getName();
		}
		if (element instanceof MyNode) {
			MyNode myNode = (MyNode) element;
			return myNode.getName();
		}
		// Not called with the IGraphEntityContentProvider
		if (element instanceof MyConnection) {
			MyConnection myConnection = (MyConnection) element;
			return myConnection.getLabel();
		}
		
		if (element instanceof EntityConnectionData) {
			EntityConnectionData test = (EntityConnectionData) element;
			return "";
		}
		
		throw new RuntimeException("Wrong type: "
				+ element.getClass().toString());
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
		if (rel instanceof AggregatedConnection) return ZestStyles.CONNECTIONS_DOT;
		return 0;
	}

	@Override
	public Color getColor(Object rel) {
		// TODO Auto-generated method stub
		if (rel instanceof AggregatedConnection) 
		{
//			if (((AggregatedConnection) rel).isNegative())
//				return Settings.Colors.aggregatedNeg;
//				else
				return Settings.Colors.aggregated;
			
		}
		if (rel instanceof MyConnection)
		{
			MyConnection conn=(MyConnection)rel;
			if (conn.isNegative()) return Settings.Colors.findNeg;
		}
		return Settings.Colors.defaultRel;
	}

	@Override
	public Color getHighlightColor(Object rel) {
		// TODO Auto-generated method stub
		return Settings.Colors.defaultRelHighlight;
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
