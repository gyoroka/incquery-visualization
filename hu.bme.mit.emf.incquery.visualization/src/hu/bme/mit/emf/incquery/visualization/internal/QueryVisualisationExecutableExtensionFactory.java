package hu.bme.mit.emf.incquery.visualization.internal;

import org.eclipse.incquery.patternlanguage.emf.ui.EMFPatternLanguageExecutableExtensionFactory;
import org.osgi.framework.Bundle;

public class QueryVisualisationExecutableExtensionFactory extends
		EMFPatternLanguageExecutableExtensionFactory {

	@Override
	protected Bundle getBundle() {
		return QueryVisualizationPlugin.getInstance().getBundle();
	}
}
