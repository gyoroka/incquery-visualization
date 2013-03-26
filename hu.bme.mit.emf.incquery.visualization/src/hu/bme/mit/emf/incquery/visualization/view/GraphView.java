package hu.bme.mit.emf.incquery.visualization.view;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

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
import org.eclipse.gef4.zest.core.viewers.GraphViewer;
import org.eclipse.gef4.zest.core.widgets.Graph;
import org.eclipse.gef4.zest.core.widgets.GraphNode;
import org.eclipse.gef4.zest.core.widgets.ZestStyles;
import org.eclipse.gef4.zest.dot.DotGraph;
import org.eclipse.gef4.zest.internal.dot.DotExport;
import org.eclipse.gef4.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.incquery.patternlanguage.emf.eMFPatternLanguage.PatternModel;
import org.eclipse.incquery.patternlanguage.emf.types.IEMFTypeProvider;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;
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
import org.eclipse.xtext.resource.ILocationInFileProvider;

import com.google.inject.Inject;

public class GraphView extends ViewPart {

	private GraphViewer callGraphViewer;
	private GraphViewer contentGraphViewer;
	private PatternModel patternmodel;
	private Combo combo;
	private PatternElement patternElement;
	private DotGraph contentDotGraph;

	@Inject
	private IEMFTypeProvider iEMFTypeProvider;
	@Inject
	private ILocationInFileProvider locationProvider;

	public void setContentModel(Pattern p, int index) {
		int bodies = p.getBodies().size();
		String items[] = new String[bodies];
		// ArrayList<String> items=new ArrayList<String>();
		for (int i = 0; i < bodies; i++) {
			// items.add("Body: "+(i+1));
			items[i] = "Body: " + (i + 1);
		}
		combo.setItems(items);
		// if (bodies>1) {comboText=p.getName()+" contains "+bodies+" bodies.";
		// combo.setVisible(true);}
		// else comboText=p.getName()+" contains one body.";
		if (bodies > 1)
			combo.setVisible(true);
		else
			combo.setVisible(false);
		// combo.setText(comboText);
		ContentGraphModelContentProvider contentmodel = new ContentGraphModelContentProvider(
				p, iEMFTypeProvider, index);
		try {
			contentGraphViewer.setInput(contentmodel.getNodes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setModel(PatternModel m) {
		patternmodel = m;
		CallGraphModelContentProvider callgraphmodel = new CallGraphModelContentProvider(
				patternmodel);
		try {
			callGraphViewer.setInput(callgraphmodel.getNodes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createPartControl(Composite parent) {

		SashForm form = new SashForm(parent, SWT.BORDER);
		callGraphViewer = new GraphViewer(form, SWT.NONE);
		// SashForm form2=new SashForm(form,SWT.VERTICAL);
		// int w0[]={2,8};

		// RowLayout rowLayout=new RowLayout(SWT.VERTICAL);
		// form2.setLayout(rowLayout);
		// form2.setLayoutData(new RowData());
		// Group group=new Group(form, SWT.NONE);
		Composite composite = new Composite(form, SWT.None);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);
		// FormLayout formLayout=new FormLayout();
		// contentGraphViewer = new GraphViewer(form, SWT.NONE);
		combo = new Combo(composite, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.setVisible(false);
		combo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = combo.getSelectionIndex();
				setContentModel(patternElement.getPattern(), index);
				combo.setText(combo.getItem(index));
				// System.out.println(index);

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		// contentGraphViewer = new GraphViewer(composite, SWT.NONE);
		// group.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		contentGraphViewer = new GraphViewer(composite, SWT.NONE);
		contentGraphViewer.getGraphControl().setLayoutData(
				new GridData(GridData.FILL_BOTH));
		// form2.setWeights(w0);
		// composite.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		// combo.pack();
		callGraphViewer.setContentProvider(new CallGraphViewContentProvider());
		callGraphViewer.setLabelProvider(new CallGraphLabelProvider());
		callGraphViewer
				.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2(), true);
		callGraphViewer.applyLayout();
		callGraphViewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);
		callGraphViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {

					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						StructuredSelection selection = (StructuredSelection) event
								.getSelection();
						Object o = selection.getFirstElement();
						if (o instanceof PatternElement) {
							// PatternElement pe=(PatternElement)o;
							patternElement = (PatternElement) o;
							setContentModel(patternElement.getPattern(), 0);
						}

					}

				});
		callGraphViewer
				.addDoubleClickListener(new CallGraphDoubleClickListener(
						locationProvider));

		contentGraphViewer
				.setContentProvider(new ContentGraphViewContentProvider());
		contentGraphViewer.setLabelProvider(new ContentGraphLabelProvider());
		contentGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2(),
				true);
		contentGraphViewer.applyLayout();
		contentGraphViewer
				.addDoubleClickListener(new ContentGraphDoubleClickListener(
						locationProvider));

		int[] weight = { 3, 4 };
		form.addControlListener(new ControlListener() {

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
		getViewSite().getActionBars().getMenuManager()
				.add(new HorizontalSugiyamaAction());
		getViewSite().getActionBars().getMenuManager()
				.add(new VerticalSugiyamaAction());
		getViewSite().getActionBars().getMenuManager().add(new Separator());
		getViewSite()
				.getActionBars()
				.getMenuManager()
				.add(new GraphSaveImageAction(callGraphViewer,
						"Save CallGraph Image"));
		getViewSite()
				.getActionBars()
				.getMenuManager()
				.add(new GraphSaveImageAction(contentGraphViewer,
						"Save ContentGraph Image"));
		getViewSite().getActionBars().getMenuManager().add(new Separator());
		getViewSite().getActionBars().getMenuManager()
		.add(new CallDotExport());
		getViewSite().getActionBars().getMenuManager()
				.add(new ContentDotExport());
	}

	@Override
	public void setFocus() {
		callGraphViewer.getControl().setFocus();
		contentGraphViewer.getControl().setFocus();
	}

	public GraphViewer getCallGraphViewer() {
		return callGraphViewer;
	}

	public GraphViewer getContentGraphViewer() {
		return contentGraphViewer;
	}

	public void exportDotGraph(Graph graph) {
		String raw = new DotTemplate2().generate(graph);
		Scanner scanner = new Scanner(raw);
		StringBuilder builder = new StringBuilder();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if (!line.trim().equals("")) { //$NON-NLS-1$
				builder.append(line + "\n"); //$NON-NLS-1$
			}
		}
		String content = builder.toString();
//		System.out.println(content);
		scanner.close();
		try {

			Shell shell = new Shell(Display.getCurrent());
			FileDialog fd = new FileDialog(shell, SWT.SAVE);
			String[] s = { "*.gv" };
			fd.setFileName("*.gv");
			fd.setFilterExtensions(s);
			String fileName = fd.open();
			File file = new File(fileName);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public class SpringAction extends Action implements IWorkbenchAction {
		public SpringAction() {
			super();
			setText("Spring Layout");
		}

		public void run() {
			contentGraphViewer.setLayoutAlgorithm(new SpringLayoutAlgorithm());
			contentGraphViewer.applyLayout();
		}

		public void dispose() {
		}
	}

	public class TreeAction extends Action implements IWorkbenchAction {
		public TreeAction() {
			super();
			setText("Tree Layout");
		}

		public void run() {
			contentGraphViewer.setLayoutAlgorithm(new TreeLayoutAlgorithm());
			contentGraphViewer.applyLayout();
		}

		public void dispose() {
		}
	}

	public class RadialAction extends Action implements IWorkbenchAction {
		public RadialAction() {
			super();
			setText("Radial Layout");
		}

		public void run() {
			contentGraphViewer.setLayoutAlgorithm(new RadialLayoutAlgorithm());
			contentGraphViewer.applyLayout();
		}

		public void dispose() {
		}
	}

	public class GridAction extends Action implements IWorkbenchAction {
		public GridAction() {
			super();
			setText("Grid Layout");
		}

		public void run() {
			contentGraphViewer.setLayoutAlgorithm(new GridLayoutAlgorithm());
			contentGraphViewer.applyLayout();
		}

		public void dispose() {
		}
	}

	public class HorizontalSugiyamaAction extends Action implements
			IWorkbenchAction {
		public HorizontalSugiyamaAction() {
			super();
			setText("Custom Horizontal Sugiyama Layout");
		}

		public void run() {
			contentGraphViewer.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2(
					1));
			contentGraphViewer.applyLayout();
		}

		public void dispose() {
		}
	}

	public class VerticalSugiyamaAction extends Action implements
			IWorkbenchAction {
		public VerticalSugiyamaAction() {
			super();
			setText("Custom Vertical Sugiyama Layout");
		}

		public void run() {
			contentGraphViewer
					.setLayoutAlgorithm(new SugiyamaLayoutAlgorithm2());
			contentGraphViewer.applyLayout();
		}

		public void dispose() {
		}
	}

	public class GraphSaveImageAction extends Action implements
			IWorkbenchAction {
		GraphViewer g;

		public GraphSaveImageAction(GraphViewer g0, String s) {
			super();
			setText(s);
			g = g0;
		}

		public void run() {

			Point size = new Point(
					g.getGraphControl().getContents().getSize().width, g
							.getGraphControl().getContents().getSize().height);
			final Image image = new Image(null, size.x, size.y);
			GC gc = new GC(image);
			SWTGraphics swtGraphics = new SWTGraphics(gc);
			g.getGraphControl().getContents().paint(swtGraphics);
			gc.copyArea(image, 0, 0);
			gc.dispose();
			ImageLoader loader = new ImageLoader();
			loader.data = new ImageData[] { image.getImageData() };
			Shell shell = new Shell(Display.getCurrent());
			FileDialog fd = new FileDialog(shell, SWT.SAVE);
			String[] s = { "*.png" };
			fd.setFileName("*.png");
			fd.setFilterExtensions(s);
			try {
				String name = fd.open();
				loader.save(name, SWT.IMAGE_PNG);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public void dispose() {
		}
	}

	public class CallDotExport extends Action implements IWorkbenchAction {
		public CallDotExport() {
			super();
			setText("Export call graph to DOT format");
		}

		public void run() {
			exportDotGraph(callGraphViewer.getGraphControl());
			// System.out.println(new
			// DotExport(contentGraphViewer.getGraphControl()).toDotString());
		}

		public void dispose() {
		}
	}

	public class ContentDotExport extends Action implements IWorkbenchAction {
		public ContentDotExport() {
			super();
			setText("Export content graph to DOT format");
		}

		public void run() {
//			GraphNode node=(GraphNode) contentGraphViewer.getGraphControl().getNodes().get(0);
//			System.out.println(node.getFigure().getFont().getFontData()[0].getName());
			exportDotGraph(contentGraphViewer.getGraphControl());
			// System.out.println(new
			// DotExport(contentGraphViewer.getGraphControl()).toDotString());
		}

		public void dispose() {
		}
	}
}
