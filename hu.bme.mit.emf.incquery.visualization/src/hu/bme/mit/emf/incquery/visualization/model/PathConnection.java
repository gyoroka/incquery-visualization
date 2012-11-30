package hu.bme.mit.emf.incquery.visualization.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;

public class PathConnection extends MyConnection {

    public PathConnection(String label, MyNode source, MyNode destination, EObject o, Pattern p) {
        super(label, source, destination, o, p);
        // TODO Auto-generated constructor stub
    }

}
