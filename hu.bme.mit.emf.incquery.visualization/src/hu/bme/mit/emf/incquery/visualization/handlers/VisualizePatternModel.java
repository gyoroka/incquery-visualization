package hu.bme.mit.emf.incquery.visualization.handlers;


import hu.bme.mit.emf.incquery.visualization.view.GraphView;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.viatra2.patternlanguage.eMFPatternLanguage.PatternModel;
import org.eclipse.xtext.ui.resource.IResourceSetProvider;

import com.google.inject.Inject;

public class VisualizePatternModel extends AbstractHandler {

	@Inject
	private IResourceSetProvider resourceSetProvider;

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		// Selection should contain only a single eiq file - enforced by
		// plugin.xml conditions
		IFile file = (IFile) ((StructuredSelection) selection)
				.getFirstElement();
		ResourceSet resourceSet = resourceSetProvider.get(file.getProject());
		Resource resource = resourceSet.getResource(
				URI.createPlatformPluginURI(file.getFullPath().toOSString(),
						false), true);
		EObject eObject = resource.getContents().get(0);
		if (eObject instanceof PatternModel) {
			PatternModel model = (PatternModel) eObject;
			createVisualization(model);
		}
		return null;
	}

	private void createVisualization(PatternModel model) {
		// TODO fill body
		

		
		String ID_VIEW="hu.bme.mit.emf.incquery.visualization.view.GraphView";
		
		
		IWorkbenchPage page = PlatformUI.getWorkbench()
				.getActiveWorkbenchWindow().getActivePage();
		IViewPart form = page.findView(ID_VIEW);
		if (form == null) {
			try {
				form = page.showView(ID_VIEW);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		} else
		page.bringToTop(form);

		GraphView view = (GraphView) form;
		view.setModel(model);
		
	}
}