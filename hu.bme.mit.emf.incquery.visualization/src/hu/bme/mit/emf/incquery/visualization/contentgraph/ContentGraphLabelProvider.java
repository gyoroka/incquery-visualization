package hu.bme.mit.emf.incquery.visualization.contentgraph;

import hu.bme.mit.emf.incquery.visualization.model.AggregatedElement;
import hu.bme.mit.emf.incquery.visualization.model.MyConnection;
import hu.bme.mit.emf.incquery.visualization.model.MyNode;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;
import hu.bme.mit.emf.incquery.visualization.model.VariableElement;
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
import org.eclipse.zest.core.viewers.IGraphContentProvider;

public class ContentGraphLabelProvider extends LabelProvider implements
IConnectionStyleProvider, IEntityStyleProvider {
	
	@Override
	public String getText(Object element) {
		if (element instanceof AggregatedElement)
		{
			PatternElement pe=(PatternElement)element;
			if (pe.getParameters().size()==0) return pe.getName();
			String s="";
			for (String ss:pe.getParameters())
			{
				s+=","+ss;
			}
			s=s.substring(1);
			s=pe.getName()+"("+s+")";
			//return pe.getName()+"("+s+")";
			return "count "+s;
			//return s;
		}
		if (element instanceof PatternElement)
		{
			PatternElement pe=(PatternElement)element;
			if (pe.getParameters().size()==0) return pe.getName();
			String s="";
			for (String ss:pe.getParameters())
			{
				s+=","+ss;
			}
			s=s.substring(1);
			s=pe.getName()+"("+s+")";
			//return pe.getName()+"("+s+")";
			//if (pe.getCount()) return "count "+s;
			return s;
		}
		if (element instanceof VariableElement)
		{
			VariableElement ve= (VariableElement)element;
			String s=ve.getName();
			//if (ve.getName().startsWith("_")) s=ve.getName().substring(1);
			if (ve.getClassifierName()!=null) 
				//return ve.getClassifierName()+"("+s+")";
				return s+" : "+ve.getClassifierName();
			else return s;
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
		//return new Color(Display.getDefault(),255, 0, 0);
		return null;
	}

	@Override
	public Color getBorderColor(Object entity) {
		// TODO Auto-generated method stub
//		if (entity instanceof VariableElement)
//		{
//			VariableElement ve= (VariableElement)entity;
//			if (ve.getName().startsWith("_")) return new Color(Display.getDefault(),100, 100, 20);
//		}
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
//		if (entity instanceof VariableElement)
//		{
//			VariableElement ve= (VariableElement)entity;
//			if (ve.getName().startsWith("_")) return 2;
//		}
		return 0;
	}

	@Override
	public Color getBackgroundColour(Object entity) {
		if (entity instanceof AggregatedElement)
		{
			//if (((AggregatedElement) entity).isNegative()) return Settings.Colors.aggregatedNeg;
			//else 
				return Settings.Colors.aggregated;
		}
		if (entity instanceof PatternElement)
		{
			if (((PatternElement) entity).isNegative()) return Settings.Colors.findNeg;
			else return Settings.Colors.find;
		}
		if (entity instanceof VariableElement)
		{
			VariableElement ve=(VariableElement)entity;
			if (ve.isParameter()) return Settings.Colors.nodeBackground;
			if (ve.getName().startsWith("_")) return Settings.Colors.tempNodeBackground;
		}
		return null;
	}

	@Override
	public Color getForegroundColour(Object entity) {
		if (entity instanceof AggregatedElement)
		{
			//if (((AggregatedElement) entity).isNegative()) return Settings.Colors.aggregatedNegForeground;
			//else 
				return Settings.Colors.aggregatedForeground;
		}
		if (entity instanceof PatternElement)
		{
			if (((PatternElement) entity).isNegative()) return Settings.Colors.findNegForeground;
			else return Settings.Colors.findForeground;
		}
		if (entity instanceof VariableElement)
		{
			VariableElement ve=(VariableElement)entity;
			if (ve.isParameter()) return Settings.Colors.nodeForeground;
			if (ve.getName().startsWith("_")) return Settings.Colors.tempNodeForeground;
		}
		return null;
	}

	@Override
	public boolean fisheyeNode(Object entity) {
		// TODO Auto-generated method stub
		//if (entity instanceof VariableElement) return true;
		return false;
	}

	@Override
	public int getConnectionStyle(Object rel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Color getColor(Object rel) {
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
		//return null;
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
