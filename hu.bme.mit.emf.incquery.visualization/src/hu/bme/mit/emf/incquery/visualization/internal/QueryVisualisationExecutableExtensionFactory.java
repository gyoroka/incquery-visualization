package hu.bme.mit.emf.incquery.visualization.internal;

import org.eclipse.xtext.ui.XtextExecutableExtensionFactory;
import org.osgi.framework.Bundle;

public class QueryVisualisationExecutableExtensionFactory extends
		XtextExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return QueryVisualizationPlugin.getInstance().getBundle();
	}
}
