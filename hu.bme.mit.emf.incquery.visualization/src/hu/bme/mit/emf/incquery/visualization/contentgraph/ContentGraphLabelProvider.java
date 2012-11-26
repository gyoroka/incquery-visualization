package hu.bme.mit.emf.incquery.visualization.contentgraph;

import hu.bme.mit.emf.incquery.visualization.model.AggregatedElement;
import hu.bme.mit.emf.incquery.visualization.model.CheckElement;
import hu.bme.mit.emf.incquery.visualization.model.MyConnection;
import hu.bme.mit.emf.incquery.visualization.model.MyNode;
import hu.bme.mit.emf.incquery.visualization.model.PathConnection;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;
import hu.bme.mit.emf.incquery.visualization.model.VariableElement;
import hu.bme.mit.emf.incquery.visualization.view.Settings;

import java.util.Scanner;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.EllipseAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef4.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.gef4.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.gef4.zest.core.viewers.IFigureProvider;
import org.eclipse.gef4.zest.core.viewers.ISelfStyleProvider;
import org.eclipse.gef4.zest.core.widgets.GraphConnection;
import org.eclipse.gef4.zest.core.widgets.GraphNode;
import org.eclipse.gef4.zest.core.widgets.IStyleableFigure;
import org.eclipse.gef4.zest.core.widgets.ZestStyles;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;

public class ContentGraphLabelProvider extends LabelProvider implements
		IConnectionStyleProvider, IEntityStyleProvider, IFigureProvider,
		ISelfStyleProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof AggregatedElement) {
			PatternElement pe = (PatternElement) element;
			if (pe.getParameters().size() == 0)
				return pe.getName();
			String s = "";
			for (String ss : pe.getParameters()) {
				s += "," + ss;
			}
			s = s.substring(1);
			s = pe.getName() + "(" + s + ")";
			return "count " + s;
		}
		if (element instanceof PatternElement) {
			PatternElement pe = (PatternElement) element;
			if (pe.getParameters().size() == 0)
				return pe.getName();
			String s = "";
			for (String ss : pe.getParameters()) {
				s += "," + ss;
			}
			s = s.substring(1);
			s = pe.getName() + "(" + s + ")";
			if (pe.isNegative())
				s = "neg " + s;
			return s;
		}
		if (element instanceof VariableElement) {
			VariableElement ve = (VariableElement) element;
			String s = ve.getName();
			if (ve.getClassifierName() != null)
				return s + " : " + ve.getClassifierName();
			else
				return s;
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
		if (entity instanceof AggregatedElement) {
			return Settings.Colors.aggregated;
		}
		if (entity instanceof PatternElement) {
			if (((PatternElement) entity).isNegative())
				return Settings.Colors.findNeg;
			else
				return Settings.Colors.find;
		}
		if (entity instanceof VariableElement) {
			VariableElement ve = (VariableElement) entity;
			if (ve.isParameter())
				return Settings.Colors.nodeBackground;
			if (ve.getName().startsWith("_"))
				return Settings.Colors.tempNodeBackground;
		}
		return null;
	}

	@Override
	public Color getForegroundColour(Object entity) {
		if (entity instanceof AggregatedElement) {
			return Settings.Colors.aggregatedForeground;
		}
		if (entity instanceof PatternElement) {
			if (((PatternElement) entity).isNegative())
				return Settings.Colors.findNegForeground;
			else
				return Settings.Colors.findForeground;
		}
		if (entity instanceof VariableElement) {
			VariableElement ve = (VariableElement) entity;
			if (ve.isParameter())
				return Settings.Colors.nodeForeground;
			if (ve.getName().startsWith("_"))
				return Settings.Colors.tempNodeForeground;
		}
		return null;
	}

	@Override
	public boolean fisheyeNode(Object entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getConnectionStyle(Object rel) {
		if (rel instanceof PathConnection)
			return ZestStyles.CONNECTIONS_DIRECTED;
		return 0;
	}

	@Override
	public Color getColor(Object rel) {
		if (rel instanceof MyConnection) {
			MyConnection conn = (MyConnection) rel;
			if (conn.isNegative())
				return Settings.Colors.findNeg;
		}
		return Settings.Colors.defaultRel;
	}

	@Override
	public Color getHighlightColor(Object rel) {
		return Settings.Colors.defaultRelHighlight;
		// return null;
	}

	@Override
	public int getLineWidth(Object rel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IFigure getTooltip(Object rel) {
		if (rel instanceof CheckElement) {
			IFigure figure = new RectangleFigure();
			Label l = new Label(((CheckElement) rel).getHooverText());
			StackLayout layout = new StackLayout();
			figure.setLayoutManager(layout);
			FontData[] fontData = Display.getDefault().getSystemFont()
					.getFontData();
			Font font = new Font(Display.getDefault().getSystemFont()
					.getDevice(), fontData);
			l.setFont(font);

			Dimension d = l.getPreferredSize();
			figure.setSize(d);
			figure.add(l);
			return figure;
		}
		return null;
	}

	@Override
	public ConnectionRouter getRouter(Object rel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IFigure getFigure(Object element) {
		if (element instanceof AggregatedElement) {
			AggregatedElement ae = (AggregatedElement) element;
			MyNodeFigure f = new MyNodeFigure(getText(ae));
			return f;
		}
		if (element instanceof PatternElement)
			return null;
		if (element instanceof VariableElement) {
			VariableElement v = (VariableElement) element;
			String s = "";
			if (v.isTemporary())
				s += v.getName().substring(1);
			else
				s += v.getName();
			if (v.getClassifierName() != null)
				s += " : " + v.getClassifierName();
			MyNodeFigure f = new MyNodeFigure(s);
			if (v.isParameter()) {
				f.setBackgroundColor(Settings.Colors.paramNodeBackground);
				f.setForegroundColor(Settings.Colors.paramNodeForeground);
				return f;
			}
			if (v.isTemporary()) {
				f.setBackgroundColor(Settings.Colors.tempNodeBackground);
				f.setForegroundColor(Settings.Colors.tempNodeForeground);
				return f;
			}
			return f;
		}
		return null;
	}

	@Override
	public void selfStyleConnection(Object element, GraphConnection connection) {
		IFigure nodeFigure;
		Connection connectionFigure = connection.getConnectionFigure();
		nodeFigure = connection.getSource().getFigure();
		if (nodeFigure instanceof MyNodeFigure) {
			connectionFigure.setSourceAnchor(new EllipseAnchor(nodeFigure));
		}
		nodeFigure = connection.getDestination().getFigure();
		if (nodeFigure instanceof MyNodeFigure) {
			connectionFigure.setTargetAnchor(new EllipseAnchor(nodeFigure));
		}

	}

	@Override
	public void selfStyleNode(Object element, GraphNode node) {
		if (element instanceof CheckElement) {
			Dimension d = node.getSize();
			int h = d.height;
			int w = d.width;
			if (h > 30)
				h = 30;
			if (w > 100)
				w = 100;
			String s = ((CheckElement) element).getHooverText();
			Scanner scanner = new Scanner(s);
			if (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				node.setText(line);
				scanner.close();
			}
			node.setSize(w, h);

		}
	}

	class MyNodeFigure extends Ellipse implements IStyleableFigure {
		// private Color borderColor;
		public MyNodeFigure(String s) {
			super();
			Label l = new Label(s);
			StackLayout layout = new StackLayout();
			setLayoutManager(layout);
			l.setFont(Display.getDefault().getSystemFont());
			Dimension d = l.getPreferredSize();
			d.height *= 2;
			d.width *= 1.25;
			setSize(d);
			add(l);
			setForegroundColor(Settings.Colors.nodeForeground);
			setBackgroundColor(Settings.Colors.nodeBackground);
		}

		@Override
		public void setBorderColor(Color borderColor) {
			// this.borderColor=borderColor;
		}

		@Override
		public void setBorderWidth(int borderWidth) {
			setLineWidth(borderWidth);
		}
	}

}
