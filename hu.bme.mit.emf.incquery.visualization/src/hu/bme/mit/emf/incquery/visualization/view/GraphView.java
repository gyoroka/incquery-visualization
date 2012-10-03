package hu.bme.mit.emf.incquery.visualization.view;

import hu.bme.mit.emf.incquery.visualization.callgraph.CallGraphLabelProvider;
import hu.bme.mit.emf.incquery.visualization.callgraph.CallGraphModelContentProvider;
import hu.bme.mit.emf.incquery.visualization.callgraph.CallGraphViewContentProvider;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphLabelProvider;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphModelContentProvider;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphViewContentProvider;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;

import javax.swing.JButton;

import org.eclipse.draw2d.FlowLayout;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternModel;
import org.eclipse.viatra2.patternlanguage.types.IEMFTypeProvider;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.BoxLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SugiyamaLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import com.google.inject.Inject;

public class GraphView extends ViewPart{

	private GraphViewer callGraphViewer;
	private GraphViewer contentGraphViewer;
	private PatternModel patternmodel;
//	private int callLayoutIndex=0;
//	private int contentLayoutIndex=0;
	@Inject 
	private IEMFTypeProvider iEMFTypeProvider;
	
	public void setContentModel(Pattern p)
	{
		ContentGraphModelContentProvider contentmodel = new ContentGraphModelContentProvider(p,iEMFTypeProvider);
		try {
			contentGraphViewer.setInput(contentmodel.getNodes());
		}
		catch(Exception e) { e.printStackTrace();}
	}
	
	
	public void setModel(PatternModel m)
	{
		patternmodel=m;
		CallGraphModelContentProvider callgraphmodel = new CallGraphModelContentProvider(patternmodel);
		try {
			callGraphViewer.setInput(callgraphmodel.getNodes());
		}
		catch(Exception e) { e.printStackTrace();}
	}
	
	@Override
	public void createPartControl(Composite parent) {
		
		SashForm form=new SashForm(parent,SWT.BORDER);
		callGraphViewer = new GraphViewer(form, SWT.NONE);
		//new Label(form,SWT.SEPARATOR|SWT.VERTICAL);
		contentGraphViewer = new GraphViewer(form, SWT.NONE);
	    
		callGraphViewer.setContentProvider(new CallGraphViewContentProvider());
		callGraphViewer.setLabelProvider(new CallGraphLabelProvider());
		callGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2(),true);
		callGraphViewer.applyLayout();
		callGraphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		callGraphViewer.addSelectionChangedListener( new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection=(StructuredSelection) event.getSelection();
				Object o=selection.getFirstElement();
				if (o instanceof PatternElement)
				{
					PatternElement pe=(PatternElement)o;
					setContentModel(pe.getPattern());
				}
				
			}
			
		});
	    
		contentGraphViewer.setContentProvider(new ContentGraphViewContentProvider());
		contentGraphViewer.setLabelProvider(new ContentGraphLabelProvider());
		contentGraphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		contentGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2(), true);
		contentGraphViewer.applyLayout();

		int[] weight={3,4};
		form.addControlListener(new ControlListener(){

			@Override
			public void controlMoved(ControlEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void controlResized(ControlEvent e) {
				// TODO Auto-generated method stub
				callGraphViewer.applyLayout();
				contentGraphViewer.applyLayout();
			}
			
		});
		form.setWeights(weight);
	}

	@Override
	public void setFocus() {
		callGraphViewer.getControl().setFocus();
		contentGraphViewer.getControl().setFocus();
	}




}
