package hu.bme.mit.emf.incquery.visualization.view;

import hu.bme.mit.emf.incquery.visualization.model.NodeModelContentProvider;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.viatra2.patternlanguage.core.patternLanguage.PatternModel;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

public class OldView {

	 Shell shell;
	 GraphViewer viewer = null;
	
	private PatternModel model;
	public OldView()
	{
		
	}
	public void setModel(PatternModel pm)
	{
		model=pm;
	}
	public void open()
	{
		
		Menu menuBar, fileMenu, viewMenu, layoutMenu;
		MenuItem fileMenuHeader, viewMenuHeader, layoutMenuHeader;
		MenuItem fileExitItem;
		MenuItem normalItem, hierarchyItem, callGraphItem;
		MenuItem springItem, treeItem, radialItem, gridItem;
		Display display = Display.getCurrent();
		shell = new Shell(display);
		shell.setSize(600, 600);
		shell.setText("Query Visualization");
		shell.setLayout(new FillLayout(SWT.None));
		
		menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&File");
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);
		
		fileExitItem = new MenuItem(fileMenu, SWT.PUSH);
		fileExitItem.setText("E&xit");

		viewMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		viewMenuHeader.setText("&View");
		viewMenu = new Menu(shell, SWT.DROP_DOWN);
		viewMenuHeader.setMenu(viewMenu);

		normalItem = new MenuItem(viewMenu, SWT.PUSH);
		normalItem.setText("&Normal");		
		hierarchyItem = new MenuItem(viewMenu, SWT.PUSH);
		hierarchyItem.setText("Hierarchy");		
		callGraphItem = new MenuItem(viewMenu, SWT.PUSH);
		callGraphItem.setText("&Call graph");
		
		layoutMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		layoutMenuHeader.setText("&Layout");
		layoutMenu = new Menu(shell, SWT.DROP_DOWN);
		layoutMenuHeader.setMenu(layoutMenu);
		
		springItem = new MenuItem(layoutMenu, SWT.PUSH);
		springItem.setText("&Spring");
		treeItem = new MenuItem(layoutMenu, SWT.PUSH);
		treeItem.setText("&Tree");
		radialItem = new MenuItem(layoutMenu, SWT.PUSH);
		radialItem.setText("&Radial");
		gridItem = new MenuItem(layoutMenu, SWT.PUSH);
		gridItem.setText("&Grid");
		
		fileExitItem.addSelectionListener(new fileExitItemListener());
		normalItem.addSelectionListener(new viewItemListener());
		hierarchyItem.addSelectionListener(new viewItemListener());
		callGraphItem.addSelectionListener(new viewItemListener());
		
		springItem.addSelectionListener(new springItemListener());
		treeItem.addSelectionListener(new treeItemListener());
		radialItem.addSelectionListener(new radialItemListener());
		gridItem.addSelectionListener(new gridItemListener());
		
		viewer = new GraphViewer(shell,SWT.BORDER);
		viewer.setContentProvider(new ZestNodeContentProvider());
		viewer.setLabelProvider(new ZestLabelProvider());
		viewer.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING));
		NodeModelContentProvider mymodel = new NodeModelContentProvider(model);
		viewer.setInput(mymodel.getNodes());
		
		//shell.pack();
		shell.open();
		
		while (!shell.isDisposed()) {
			while (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		
		
	}
	
	
	class fileExitItemListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			shell.close();
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			shell.close();
		}
	}

	class viewItemListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			System.out.println("TODO!");
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			System.out.println("TODO!");
		}
	}
	
	
	class springItemListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			viewer.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
		}
		public void widgetDefaultSelected(SelectionEvent event) {
			viewer.setLayoutAlgorithm(new SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
		}
	}
	
	class treeItemListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			viewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
		}
		public void widgetDefaultSelected(SelectionEvent event) {
			viewer.setLayoutAlgorithm(new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
		}
	}
	
	class radialItemListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			viewer.setLayoutAlgorithm(new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
		}
		public void widgetDefaultSelected(SelectionEvent event) {
			viewer.setLayoutAlgorithm(new RadialLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
		}
	}
	
	class gridItemListener implements SelectionListener {
		public void widgetSelected(SelectionEvent event) {
			viewer.setLayoutAlgorithm(new GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
		}
		public void widgetDefaultSelected(SelectionEvent event) {
			viewer.setLayoutAlgorithm(new GridLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),true);
		}
	}
}
