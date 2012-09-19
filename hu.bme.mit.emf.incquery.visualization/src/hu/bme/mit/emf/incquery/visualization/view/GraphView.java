package hu.bme.mit.emf.incquery.visualization.view;

import hu.bme.mit.emf.incquery.visualization.model.CallGraphModelContentProvider;
import hu.bme.mit.emf.incquery.visualization.model.ContentGraphModelContentProvider;
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
import org.eclipse.ui.part.ViewPart;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternModel;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

public class GraphView extends ViewPart{

	private GraphViewer callGraphViewer;
	private GraphViewer contentGraphViewer;
	private PatternModel patternmodel;
	private int callLayoutIndex=0;
	private int contentLayoutIndex=0;
	
	public void setContentModel(Pattern p)
	{
		//NodeModelContentProvider nodemodel = new NodeModelContentProvider(p);
		ContentGraphModelContentProvider contentmodel = new ContentGraphModelContentProvider(p);
		  
		try {
			contentGraphViewer.setInput(contentmodel.getNodes());
		}
		catch(Exception e) { e.printStackTrace();}
	}
	
	
	public void setModel(PatternModel m)
	{
		patternmodel=m;
		CallGraphModelContentProvider callgraphmodel = new CallGraphModelContentProvider(patternmodel);
		//NodeModelContentProvider nodemodel = new NodeModelContentProvider(patternmodel);
		  
		try {
			callGraphViewer.setInput(callgraphmodel.getNodes());
			//contentGraphViewer.setInput(nodemodel.getNodes());
		}
		catch(Exception e) { e.printStackTrace();}
	}
	
	@Override
	public void createPartControl(Composite parent) {
		
		SashForm form=new SashForm(parent,SWT.NONE);
		Composite callMenu=new Composite(form,SWT.PUSH);
		callGraphViewer = new GraphViewer(form, SWT.NONE);
		contentGraphViewer = new GraphViewer(form, SWT.NONE);
		Composite contentMenu=new Composite(form,SWT.PUSH);
		
		
	    
		callGraphViewer.setContentProvider(new CallGraphViewContentProvider());
		callGraphViewer.setLabelProvider(new CallGraphLabelProvider());
		callGraphViewer.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
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
		contentGraphViewer.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
		contentGraphViewer.applyLayout();

		int[] weight={1,4,4,1};
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
		
		//Button callChangeLayoutButton=new Button(callMenu);
		callMenu.setLayout(new FillLayout(SWT.VERTICAL));
		contentMenu.setLayout(new FillLayout(SWT.VERTICAL));
		Button callChangeLayoutButton=new Button(callMenu,SWT.PUSH);
		Button contentChangeLayoutButton=new Button(contentMenu,SWT.PUSH);
		callChangeLayoutButton.setText("Change Layout");
		contentChangeLayoutButton.setText("Change Layout");
		callChangeLayoutButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				changeCallLayout();
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}});
		contentChangeLayoutButton.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				changeContentLayout();
				
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		
		
		form.setWeights(weight);
	}

	@Override
	public void setFocus() {
		callGraphViewer.getControl().setFocus();
		contentGraphViewer.getControl().setFocus();
	}
	public void changeCallLayout()
	{
		switch (callLayoutIndex)
		{
		case 0: 
			callGraphViewer.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
			break;
		case 1: 
			callGraphViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
			break;
		case 2: 
			callGraphViewer.setLayoutAlgorithm(new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
			break;
		case 3: 
			callGraphViewer.setLayoutAlgorithm(new GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
			break;
		}
		callLayoutIndex++;
		if (callLayoutIndex>3) callLayoutIndex=0;
		callGraphViewer.applyLayout();
	}
	public void changeContentLayout()
	{
		switch (contentLayoutIndex)
		{
		case 0: 
			contentGraphViewer.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
			break;
		case 1: 
			contentGraphViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
			break;
		case 2: 
			contentGraphViewer.setLayoutAlgorithm(new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
			break;
		case 3: 
			contentGraphViewer.setLayoutAlgorithm(new GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
			break;
		}
		contentLayoutIndex++;
		if (contentLayoutIndex>3) contentLayoutIndex=0;
		contentGraphViewer.applyLayout();
	}

}
