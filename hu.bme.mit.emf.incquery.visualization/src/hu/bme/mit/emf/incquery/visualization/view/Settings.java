package hu.bme.mit.emf.incquery.visualization.view;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class Settings {
	public static class Colors {
		
		public static Color nodeBackground=new Color(Display.getDefault(),100,150,250);
		public static Color tempNodeBackground=new Color(Display.getDefault(),175,175,175);
		public static Color nodeForeground=new Color(Display.getDefault(),250,250,200);
		public static Color tempNodeForeground=new Color(Display.getDefault(),255,255,255);
		public static Color find=new Color(Display.getDefault(),50,50,100);
		public static Color findNeg=new Color(Display.getDefault(),255, 0, 0);
		public static Color defaultRelHighlight=new Color(Display.getDefault(),250, 250, 50);
		public static Color defaultRel=new Color(Display.getDefault(),50,50,100);
		public static Color aggregated=new Color(Display.getDefault(),50,200,100);
		public static Color aggregatedNeg=new Color(Display.getDefault(),255,200,100);
	}
}
