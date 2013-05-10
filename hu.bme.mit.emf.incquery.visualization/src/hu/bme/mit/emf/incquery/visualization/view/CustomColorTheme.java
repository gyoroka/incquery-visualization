package hu.bme.mit.emf.incquery.visualization.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.incquery.tooling.ui.retevis.theme.ColorTheme;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public class CustomColorTheme extends ColorTheme {

	public static final int NODE_DEFAULT=0;
	public static final int VARIABLE=NODE_DEFAULT;
	public static final int RELATION_DEFAULT=3;
	public static final int RELATION_NEGATIVE=2;
	public static final int PARAMETER=1;
	public static final int TEMPORARY=4;
	public static final int FIND=5;
	public static final int FIND_NEGATIVE=RELATION_NEGATIVE;
	public static final int AGGREGATED=7;
	
	
	
	private List<Color> colors;
	private List<Color> textColors;

	public CustomColorTheme(Display display) {
		super(display);
		colors=new ArrayList<Color>();
		textColors=new ArrayList<Color>();
		
		for (int i = 0; i < 9; i++) {
			colors.add(new Color(display,0,0,0));
			textColors.add(new Color(display,0,0,0));
		}
		
		colors.set(NODE_DEFAULT,super.getNodeColor(NODE_DEFAULT));
		textColors.set(NODE_DEFAULT,super.getTextColor(NODE_DEFAULT));
		colors.set(PARAMETER,super.getNodeColor(PARAMETER));
		textColors.set(PARAMETER,super.getTextColor(PARAMETER));
		colors.set(RELATION_NEGATIVE,super.getNodeColor(RELATION_NEGATIVE));
		textColors.set(RELATION_NEGATIVE,super.getTextColor(RELATION_NEGATIVE));
		
		colors.set(FIND, new Color(display, 55, 112, 231));
		textColors.set(FIND,new Color(display, 255, 255, 255));
		colors.set(AGGREGATED, new Color(display, 50, 200, 100));
		textColors.set(AGGREGATED,new Color(display, 250, 200, 250));
		colors.set(RELATION_DEFAULT, new Color(display, 50, 50, 100));
		textColors.set(RELATION_DEFAULT,new Color(display, 100, 100, 150));
		colors.set(TEMPORARY, new Color(display, 175, 175, 175));
		textColors.set(TEMPORARY,new Color(display, 255, 255, 255));		
	}
	
	@Override
	public Color getNodeColor(int id) {
//		Preconditions.checkElementIndex(id, colors.size());
		return colors.get(id);
	}
	
	@Override
	public Color getTextColor(int id) {
//		Preconditions.checkElementIndex(id, textColors.size());
		return textColors.get(id);
	}
	
	public Color getColor(int id) {
		return getNodeColor(id);
	}
	
	public void setColors(int id,Color c) {
		colors.set(id, c);
	}
	
	public void setTextColors(int id,Color c) {
		textColors.set(id, c);
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		super.dispose();
		colors.clear();
		textColors.clear();
	}

}
