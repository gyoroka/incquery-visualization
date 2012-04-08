package hu.bme.mit.emf.incquery.visualization.internal;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;


public class QueryVisualizationPlugin extends AbstractUIPlugin implements
		BundleActivator {
	private static QueryVisualizationPlugin INSTANCE;

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
	}
	
	
	@Override
	public void stop(BundleContext context) throws Exception {
		INSTANCE = null;
		super.stop(context);
	}
	
	public static QueryVisualizationPlugin getInstance() {
		return INSTANCE;
	}
}
