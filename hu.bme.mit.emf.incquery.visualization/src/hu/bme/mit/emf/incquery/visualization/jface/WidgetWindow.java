package hu.bme.mit.emf.incquery.visualization.jface;

import hu.bme.mit.emf.incquery.visualization.model.NodeModelContentProvider;
import hu.bme.mit.emf.incquery.visualization.view.ZestLabelProvider;
import hu.bme.mit.emf.incquery.visualization.view.ZestNodeContentProvider;

import org.eclipse.draw2d.Button;
import org.eclipse.jface.window.ApplicationWindow;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternModel;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

public class WidgetWindow extends ApplicationWindow {
	private GraphViewer viewer;
	private PatternModel patternmodel;
	public WidgetWindow()
	  {
	    super(null);
	  }

	  protected Control createContents(Composite parent)
	  {
	    getShell().setText("Query Visualization");
	    
	    parent.setSize(800,800);
	    
	    //FillLayout fl= new FillLayout(SWT.VERTICAL);	    
	    //parent.setLayout(fl);
	    
	    GraphViewer viewer = new GraphViewer(parent, SWT.BORDER);
	    Text hello = new Text(parent,SWT.LEFT);
	    hello.setText("hello");
		viewer.setContentProvider(new ZestNodeContentProvider());
		viewer.setLabelProvider(new ZestLabelProvider());
		viewer.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
		viewer.applyLayout();
		  NodeModelContentProvider model = new NodeModelContentProvider(patternmodel);
		  
		  try {
		  viewer.setInput(model.getNodes());
		  }
		  catch(Exception e) { e.printStackTrace();}
		
		parent.pack();
	    return parent;
	  }
	  public void setModel(PatternModel m)
	  {
		  patternmodel=m;
	  }
}