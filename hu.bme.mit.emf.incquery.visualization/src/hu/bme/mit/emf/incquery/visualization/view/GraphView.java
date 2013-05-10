package hu.bme.mit.emf.incquery.visualization.view;

import hu.bme.mit.emf.incquery.visualization.callgraph.CallGraphDoubleClickListener;
import hu.bme.mit.emf.incquery.visualization.callgraph.CallGraphLabelProvider;
import hu.bme.mit.emf.incquery.visualization.callgraph.CallGraphModelContentProvider;
import hu.bme.mit.emf.incquery.visualization.callgraph.CallGraphViewContentProvider;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphDoubleClickListener;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphLabelProvider;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphModelContentProvider;
import hu.bme.mit.emf.incquery.visualization.contentgraph.ContentGraphViewContentProvider;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JOptionPane;

import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef4.zest.core.viewers.GraphViewer;
import org.eclipse.gef4.zest.core.widgets.Graph;
import org.eclipse.gef4.zest.core.widgets.ZestStyles;
import org.eclipse.gef4.zest.layouts.LayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.incquery.patternlanguage.emf.eMFPatternLanguage.PatternModel;
import org.eclipse.incquery.patternlanguage.emf.types.IEMFTypeProvider;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
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
	private CustomColorTheme colorTheme;
	private Dimension callGraphDimension;
	private Dimension contentGraphSugiyamaDimension,contentGraphTreeDimension;

	@Inject
	private IEMFTypeProvider iEMFTypeProvider;
	@Inject
	private ILocationInFileProvider locationProvider;

	public void setContentModel(Pattern p, int index) {
		int bodies = p.getBodies().size();
		String items[] = new String[bodies];
		for (int i = 0; i < bodies; i++) {
			items[i] = "Body: " + (i + 1);
		}
		combo.setItems(items);
		if (bodies > 1)
			combo.setVisible(true);
		else
			combo.setVisible(false);
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

		colorTheme=new CustomColorTheme(Display.getDefault());
		callGraphDimension=new Dimension(35,5);
		contentGraphSugiyamaDimension=new Dimension(75,25);
		contentGraphTreeDimension=new Dimension(200,100);
		
		SashForm form = new SashForm(parent, SWT.BORDER);
		callGraphViewer = new GraphViewer(form, SWT.NONE);
		Composite composite = new Composite(form, SWT.None);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		composite.setLayout(gridLayout);
		combo = new Combo(composite, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		combo.setVisible(false);
		combo.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				int index = combo.getSelectionIndex();
				setContentModel(patternElement.getPattern(), index);
				combo.setText(combo.getItem(index));
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});
		
		contentGraphViewer = new GraphViewer(composite, SWT.NONE);
		contentGraphViewer.getGraphControl().setLayoutData(
				new GridData(GridData.FILL_BOTH));
		
		callGraphViewer.setContentProvider(new CallGraphViewContentProvider());
		callGraphViewer.setLabelProvider(new CallGraphLabelProvider(colorTheme));
		callGraphViewer
				.setLayoutAlgorithm(new CustomSugiyamaLayoutAlgorithm(SWT.VERTICAL,callGraphDimension), true);
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
		contentGraphViewer.setLabelProvider(new ContentGraphLabelProvider(colorTheme));
		contentGraphViewer.setLayoutAlgorithm(new CustomSugiyamaLayoutAlgorithm(CustomSugiyamaLayoutAlgorithm.VERTICAL,contentGraphSugiyamaDimension),
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
		IMenuManager menu=getViewSite().getActionBars().getMenuManager();
		menu.add(new LayoutAction("Spring Layout",new SpringLayoutAlgorithm()));
		menu.add(new LayoutAction("Tree Layout",new TreeLayoutAlgorithm(TreeLayoutAlgorithm.TOP_DOWN,contentGraphTreeDimension)));
		menu.add(new LayoutAction("Radial Layout",new RadialLayoutAlgorithm()));
		menu.add(new LayoutAction("Grid Layout",new GridLayoutAlgorithm()));
		menu.add(new Separator());
		menu.add(new LayoutAction("Custom Vertical Sugiyama Layout",new CustomSugiyamaLayoutAlgorithm(CustomSugiyamaLayoutAlgorithm.VERTICAL,contentGraphSugiyamaDimension)));
		menu.add(new LayoutAction("Custom Horizontal Sugiyama Layout",new CustomSugiyamaLayoutAlgorithm(CustomSugiyamaLayoutAlgorithm.HORIZONTAL,contentGraphSugiyamaDimension)));
		menu.add(new Separator());
		menu.add(new ExportAction("Export Call Graph",callGraphViewer.getGraphControl()));
		menu.add(new ExportAction("Export Content Graph",contentGraphViewer.getGraphControl()));
	}

	@Override
	public void setFocus() {
		callGraphViewer.getControl().setFocus();
		contentGraphViewer.getControl().setFocus();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		colorTheme.dispose();
	}

	public GraphViewer getCallGraphViewer() {
		return callGraphViewer;
	}

	public GraphViewer getContentGraphViewer() {
		return contentGraphViewer;
	}
	
	public void exportGraph(Graph graph) {
		try {

			Shell shell = new Shell(Display.getCurrent());
			FileDialog fd = new FileDialog(shell, SWT.SAVE);
			String[] s = { "*.dot","*.jpeg","*.png","*.bmp" };
			fd.setFileName("*.dot");
			fd.setFilterExtensions(s);
			String fileName = fd.open();
			if (fileName!=null)
			{
				File file = new File(fileName);
				boolean l=true;
				// if file doesnt exists, then create it
				if (file.exists()) {
					int choice=JOptionPane.showConfirmDialog(null, "Overwrite "+ fileName +"?", "File already exists.", JOptionPane.OK_CANCEL_OPTION);
					if (choice!=JOptionPane.YES_OPTION) l=false;
				}
				if (l)
				{
					if (fd.getFilterIndex()==0)
					{
						file.createNewFile();
						FileWriter fw = new FileWriter(file.getAbsoluteFile());
						BufferedWriter bw = new BufferedWriter(fw);
						//
						String raw = new XtendDotTemplate().generate(graph);
						Scanner scanner = new Scanner(raw);
						StringBuilder builder = new StringBuilder();
						while (scanner.hasNextLine()) {
							String line = scanner.nextLine();
							if (!line.trim().equals("")) { //$NON-NLS-1$
								builder.append(line + "\n"); //$NON-NLS-1$
							}
						}
						String content = builder.toString();
						bw.write(content);
						scanner.close();
						bw.close();
					}
					else
					{
						Point size = new Point(graph.getContents().getSize().width, graph.getContents().getSize().height);
						Image image = new Image(null, size.x, size.y);
//						BufferedImage image = new BufferedImage(size.x, size.y,BufferedImage.TYPE_INT_ARGB);
						GC gc = new GC(image);
						SWTGraphics swtGraphics = new SWTGraphics(gc);
						graph.getContents().paint(swtGraphics);
						gc.copyArea(image, 0, 0);
						gc.dispose();
						ImageLoader loader = new ImageLoader();
						loader.data = new ImageData[] { image.getImageData() };
						switch (fd.getFilterIndex()) {
						case 1:
							loader.save(fileName, SWT.IMAGE_JPEG);
//							ImageIO.write(image,"jpeg", file);
							break;
						case 2:
							loader.save(fileName, SWT.IMAGE_PNG);
							break;

						default:
							loader.save(fileName, SWT.IMAGE_BMP);
							break;
						}
					}
				}
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}
	
	private class LayoutAction extends Action implements IWorkbenchAction {
		LayoutAlgorithm algorithm;
		public LayoutAction(String name,LayoutAlgorithm algo) {
			super();
			setText(name);
			algorithm=algo;
		}

		public void run() {
			contentGraphViewer.setLayoutAlgorithm(algorithm);
			contentGraphViewer.applyLayout();
		}

		public void dispose() {
		}
	}
	
	private class ExportAction extends Action implements IWorkbenchAction {
		Graph graph;
		public ExportAction(String name,Graph g) {
			super();
			setText(name);
			graph=g;
		}

		public void run() {
			exportGraph(graph);
		}

		public void dispose() {}
	}
	
}
