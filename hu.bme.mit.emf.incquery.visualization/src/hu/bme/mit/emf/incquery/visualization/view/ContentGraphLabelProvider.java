package hu.bme.mit.emf.incquery.visualization.view;

import hu.bme.mit.emf.incquery.visualization.model.MyConnection;
import hu.bme.mit.emf.incquery.visualization.model.MyNode;
import hu.bme.mit.emf.incquery.visualization.model.VariableElement;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;

public class ContentGraphLabelProvider extends LabelProvider implements
IConnectionStyleProvider, IEntityStyleProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof VariableElement)
		{
			VariableElement ve= (VariableElement)element;
			if (ve.getClassifierName()!=null) return ve.getClassifierName()+"("+ve.getName()+")";
			else return ve.getName();
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
		if (entity instanceof VariableElement)
		{
			VariableElement ve=(VariableElement)entity;
			if (ve.isParameter()) return new Color(Display.getDefault(),100,150,250);
		}
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
		return 0;
	}

	@Override
	public Color getColor(Object rel) {
		Color color=new Color(Display.getDefault(),50,50,100);
		if (rel instanceof MyConnection)
		{
			MyConnection conn=(MyConnection)rel;
			if (conn.isNegative()) color = new Color(Display.getDefault(),255, 0, 0);
		}
		return color;
	}

	@Override
	public Color getHighlightColor(Object rel) {
		// TODO Auto-generated method stub
		return null;
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
