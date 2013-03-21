package hu.bme.mit.emf.incquery.visualization.contentgraph;
import hu.bme.mit.emf.incquery.visualization.model.AggregatedElement;
import hu.bme.mit.emf.incquery.visualization.model.CheckElement;
import hu.bme.mit.emf.incquery.visualization.model.MyNode;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;
import hu.bme.mit.emf.incquery.visualization.model.VariableElement;

import org.eclipse.gef4.zest.layouts.LayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.interfaces.EntityLayout;
import org.eclipse.gef4.zest.layouts.interfaces.LayoutContext;
import org.eclipse.swt.widgets.Item;

public class ContentGraphLayoutAlgorithm implements LayoutAlgorithm {
	private LayoutContext context;
	@Override
	public void setLayoutContext(LayoutContext context) {
		this.context = context;
		
	}

	@Override
	public void applyLayout(boolean clean) {
		EntityLayout[] entitiesToLayout = context.getEntities();
//		int totalSteps = entitiesToLayout.length;
		int parameterLocation=0;
		int variableLocation=0;
		int patternLocation=0;
		int aggregatedLocation=0;
		int checkLocation=0;
		
		int maxH=0,maxW=0;
		
		for (EntityLayout entity : entitiesToLayout) {
			if (entity.getSize().height>maxH) maxH=(int) entity.getSize().height;
			if (entity.getSize().width>maxW) maxW=(int) entity.getSize().width;
		}

		for (int currentStep = 0; currentStep < entitiesToLayout.length; currentStep++) {
			EntityLayout layoutEntity = entitiesToLayout[currentStep];
			Item[] item=layoutEntity.getItems();
			if (item[0]!=null)
			{
				Object o=item[0].getData();
				boolean l=false;
				if (o instanceof VariableElement) {
					VariableElement v=(VariableElement) o;
					if (v.isParameter())
					{
						
						layoutEntity.setLocation(parameterLocation+layoutEntity.getSize().width,50);
						parameterLocation+=2*layoutEntity.getSize().width;
					}
					else
					{
						if (v.isTemporary())
						{
							layoutEntity.setLocation(variableLocation+layoutEntity.getSize().width,150);
							variableLocation+=2*layoutEntity.getSize().width;
						}
						else
						{
							layoutEntity.setLocation(variableLocation+layoutEntity.getSize().width,100);
							variableLocation+=2*layoutEntity.getSize().width;
						}
						
					}
					
					l=true;
				}
				if (o instanceof PatternElement) {
					
					layoutEntity.setLocation(patternLocation+layoutEntity.getSize().width,200);
					patternLocation+=2*layoutEntity.getSize().width;
					l=true;
				}
				if (o instanceof AggregatedElement) {
					
					layoutEntity.setLocation(aggregatedLocation+layoutEntity.getSize().width,250);
					aggregatedLocation+=2*layoutEntity.getSize().width;
					l=true;
				}
				if (o instanceof CheckElement) {
					
					layoutEntity.setLocation(checkLocation+layoutEntity.getSize().width,300);
					checkLocation+=2*layoutEntity.getSize().width;
					l=true;
				}
				if (!l)
				{
					if (o instanceof MyNode) {
						System.out.println(((MyNode)o).getName());
					}
				}
			}
			
		}
	}

}
