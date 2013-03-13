package hu.bme.mit.emf.incquery.visualization.view;

import hu.bme.mit.emf.incquery.visualization.callgraph.CallGraphDoubleClickListener;
import hu.bme.mit.emf.incquery.visualization.callgraph.CallGraphLabelProvider;
import hu.bme.mit.emf.incquery.visualization.callgraph.CallGraphModelContentProvider;
import hu.bme.mit.emf.incquery.visualization.callgraph.CallGraphViewContentProvider;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphDoubleClickListener;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphLabelProvider;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphLayoutAlgorithm;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphModelContentProvider;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphViewContentProvider;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;

import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.Pattern;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternModel;
import org.eclipse.viatra2.patternlanguage.types.IEMFTypeProvider;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShiftAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import com.google.inject.Inject;

public class GraphView extends ViewPart{

	private GraphViewer callGraphViewer;
	private GraphViewer contentGraphViewer;
	private PatternModel patternmodel;
	private Combo combo;
//	private String comboText="";
	private PatternElement patternElement;
	
	@Inject 
	private IEMFTypeProvider iEMFTypeProvider;
	@Inject
	private ILocationInFileProvider locationProvider;
	
	public void setContentModel(Pattern p,int index)
	{
		int bodies=p.getBodies().size();
		String items[]=new String[bodies];
//		ArrayList<String> items=new ArrayList<String>();
		for (int i = 0; i < bodies; i++) {
//			items.add("Body: "+(i+1));
			items[i]="Body: "+(i+1);
		}
		combo.setItems(items);
//		if (bodies>1) {comboText=p.getName()+" contains "+bodies+" bodies."; combo.setVisible(true);}
//		else comboText=p.getName()+" contains one body.";
		if (bodies>1) combo.setVisible(true);
		else combo.setVisible(false);
//		combo.setText(comboText);
		ContentGraphModelContentProvider contentmodel = new ContentGraphModelContentProvider(p,iEMFTypeProvider,index);
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
//		SashForm form2=new SashForm(form,SWT.VERTICAL);
//		int w0[]={2,8};
		
//		RowLayout rowLayout=new RowLayout(SWT.VERTICAL);
//		form2.setLayout(rowLayout);
//		form2.setLayoutData(new RowData());
//		Group group=new Group(form, SWT.NONE);
		Composite composite=new Composite(form, SWT.None);
		GridLayout gridLayout=new GridLayout();
		gridLayout.numColumns=1;
		composite.setLayout(gridLayout);
//		FormLayout formLayout=new FormLayout();
//		contentGraphViewer = new GraphViewer(form, SWT.NONE);
		combo=new Combo(composite, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.setVisible(false);
		combo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				int index=combo.getSelectionIndex();
				setContentModel(patternElement.getPattern(),index);
				combo.setText(combo.getItem(index));
//				System.out.println(index);
				
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
//		contentGraphViewer = new GraphViewer(composite, SWT.NONE);
//		group.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		contentGraphViewer = new GraphViewer(composite, SWT.NONE);
		contentGraphViewer.getGraphControl().setLayoutData(new GridData(GridData.FILL_BOTH));
//		form2.setWeights(w0);
//		composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
//		combo.pack();
		callGraphViewer.setContentProvider(new CallGraphViewContentProvider());
		callGraphViewer.setLabelProvider(new CallGraphLabelProvider());
		
		SugiyamaLayoutAlgorithm2 sugiyamaAlgorithm = new SugiyamaLayoutAlgorithm2(0);
		HorizontalShiftAlgorithm horizontalAlgorithm = new HorizontalShiftAlgorithm();
		callGraphViewer.setLayoutAlgorithm(new CompositeLayoutAlgorithm(new LayoutAlgorithm[]{ sugiyamaAlgorithm,horizontalAlgorithm}),true);
//		callGraphViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm2(3,new Dimension(200,50)),true);
		callGraphViewer.applyLayout();
		callGraphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		callGraphViewer.addSelectionChangedListener( new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent event) {
				StructuredSelection selection=(StructuredSelection) event.getSelection();
				Object o=selection.getFirstElement();
				if (o instanceof PatternElement)
				{
//					PatternElement pe=(PatternElement)o;
					patternElement=(PatternElement)o;
					setContentModel(patternElement.getPattern(),0);
				}
				
			}
			
		});
		callGraphViewer.addDoubleClickListener(new CallGraphDoubleClickListener(locationProvider));
	    
		contentGraphViewer.setContentProvider(new ContentGraphViewContentProvider());
		contentGraphViewer.setLabelProvider(new ContentGraphLabelProvider());
		contentGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2(), true);
		contentGraphViewer.applyLayout();
		contentGraphViewer.addDoubleClickListener(new ContentGraphDoubleClickListener(locationProvider));

		int[] weight={3,4};
		form.addControlListener(new ControlListener(){

			@Override
			public void controlMoved(ControlEvent e) {
				callGraphViewer.applyLayout();
				contentGraphViewer.applyLayout();
				
			}

			@Override
			public void controlResized(ControlEvent e) {
				callGraphViewer.applyLayout();
				contentGraphViewer.applyLayout();
			}
			
		});
		form.setWeights(weight);
		

		  
		getViewSite().getActionBars().getMenuManager().add(new SpringAction());
		getViewSite().getActionBars().getMenuManager().add(new TreeAction());
		getViewSite().getActionBars().getMenuManager().add(new RadialAction());
		getViewSite().getActionBars().getMenuManager().add(new GridAction());
		getViewSite().getActionBars().getMenuManager().add(new Separator()); 
		getViewSite().getActionBars().getMenuManager().add(new CustomHorizontalSugiyamaAction());
		getViewSite().getActionBars().getMenuManager().add(new CustomVerticalSugiyamaAction());
		getViewSite().getActionBars().getMenuManager().add(new Separator()); 
//		getViewSite().getActionBars().getMenuManager().add(new HorizontalSugiyamaAction());
//		getViewSite().getActionBars().getMenuManager().add(new VerticalSugiyamaAction());
//		getViewSite().getActionBars().getMenuManager().add(new Separator()); 
		getViewSite().getActionBars().getMenuManager().add(new CustomContentAction());
		getViewSite().getActionBars().getMenuManager().add(new Separator()); 
		getViewSite().getActionBars().getMenuManager().add(new GraphSaveImageAction(callGraphViewer,"Save CallGraph Image")); 
		getViewSite().getActionBars().getMenuManager().add(new GraphSaveImageAction(contentGraphViewer,"Save ContentGraph Image"));
	}

	@Override
	public void setFocus() {
		callGraphViewer.getControl().setFocus();
		contentGraphViewer.getControl().setFocus();
	}
	
	public GraphViewer getCallGraphViewer()
	{
		return callGraphViewer;
	}

	public GraphViewer getContentGraphViewer()
	{
		return contentGraphViewer;
	}


	public class SpringAction extends Action implements IWorkbenchAction{  
		public SpringAction(){ 
			super();
			setText("Spring Layout");
		}  
		public void run() {  
			contentGraphViewer.setLayoutAlgorithm(new SpringLayoutAlgorithm());
			contentGraphViewer.applyLayout();
//			callGraphViewer.setLayoutAlgorithm(new SpringLayoutAlgorithm());
//			callGraphViewer.applyLayout();
		}  
		public void dispose() {}  
		}  
	public class TreeAction extends Action implements IWorkbenchAction{  
		public TreeAction(){ 
			super();
			setText("Tree Layout");
		}  
		public void run() {  
			contentGraphViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm());
			contentGraphViewer.applyLayout();
//			callGraphViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm());
//			callGraphViewer.applyLayout();
		}  
		public void dispose() {}  
		} 
	public class RadialAction extends Action implements IWorkbenchAction{  
		public RadialAction(){ 
			super();
			setText("Radial Layout");
		}  
		public void run() {  
			contentGraphViewer.setLayoutAlgorithm(new RadialLayoutAlgorithm());
			contentGraphViewer.applyLayout();
//			callGraphViewer.setLayoutAlgorithm(new RadialLayoutAlgorithm());
//			callGraphViewer.applyLayout();
		}  
		public void dispose() {}  
		} 
	public class GridAction extends Action implements IWorkbenchAction{  
		public GridAction(){ 
			super();
			setText("Grid Layout");
		}  
		public void run() {  
			contentGraphViewer.setLayoutAlgorithm(new GridLayoutAlgorithm());
			contentGraphViewer.applyLayout();
//			callGraphViewer.setLayoutAlgorithm(new GridLayoutAlgorithm());
//			callGraphViewer.applyLayout();
		}  
		public void dispose() {}  
		} 
	public class CustomHorizontalSugiyamaAction extends Action implements IWorkbenchAction{  
		public CustomHorizontalSugiyamaAction(){ 
			super();
			setText("Custom Horizontal Sugiyama Layout");
		}  
		public void run() {  
			contentGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2(1));
			contentGraphViewer.applyLayout();
//			callGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2(1));
//			callGraphViewer.applyLayout();
		}  
		public void dispose() {}  
		} 
	public class CustomVerticalSugiyamaAction extends Action implements IWorkbenchAction{  
		public CustomVerticalSugiyamaAction(){ 
			super();
			setText("Custom Vertical Sugiyama Layout");
		}  
		public void run() {  
			contentGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2());
			contentGraphViewer.applyLayout();
//			callGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2());
//			callGraphViewer.applyLayout();
		}  
		public void dispose() {}  
		} 
	public class HorizontalSugiyamaAction extends Action implements IWorkbenchAction{  
		public HorizontalSugiyamaAction(){ 
			super();
			setText("Horizontal Sugiyama Layout");
		}  
		public void run() {  
			contentGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2(1));
			contentGraphViewer.applyLayout();
//			callGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm(2));
//			callGraphViewer.applyLayout();

		}  
		public void dispose() {}  
		} 
	public class VerticalSugiyamaAction extends Action implements IWorkbenchAction{  
		public VerticalSugiyamaAction(){ 
			super();
			setText("Vertical Sugiyama Layout");
		}  
		public void run() {  
			contentGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2());
			contentGraphViewer.applyLayout();
//			callGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm(1));
//			callGraphViewer.applyLayout();
		}  
		public void dispose() {}  
		} 
	public class CustomContentAction extends Action implements IWorkbenchAction{  
		public CustomContentAction(){ 
			super();
			setText("Custom Content Layout");
		}  
		public void run() {  
			contentGraphViewer.setLayoutAlgorithm(new ContentGraphLayoutAlgorithm());
			contentGraphViewer.applyLayout();
		}  
		public void dispose() {}  
		} 
	public class GraphSaveImageAction extends Action implements IWorkbenchAction{ 
		GraphViewer g;
		public GraphSaveImageAction(GraphViewer g0,String s){ 
			super();
			setText(s);
			g=g0;
		}  
		public void run() {  
			
			Point size = new Point(g.getGraphControl().getContents().getSize().width, g.getGraphControl().getContents().getSize().height);
			final Image image = new Image(null, size.x, size.y);
			GC gc = new GC(image);
			SWTGraphics swtGraphics = new SWTGraphics(gc);
			g.getGraphControl().getContents().paint(swtGraphics);
			gc.copyArea(image, 0, 0);
			gc.dispose();
			ImageLoader loader = new ImageLoader();
		    loader.data = new ImageData[] {image.getImageData()};
			Shell shell=new Shell(Display.getCurrent());
			FileDialog fd=new FileDialog(shell,SWT.SAVE);
			String[] s={"*.png"};
			fd.setFileName("*.png");
			fd.setFilterExtensions(s);
			try{
			String name=fd.open();
			loader.save(name, SWT.IMAGE_PNG);
			}
			catch(Exception e)
			{e.printStackTrace();}
			
		}  
		public void dispose() {}  
		} 
}
