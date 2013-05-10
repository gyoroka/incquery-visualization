package hu.bme.mit.emf.incquery.visualization.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;

public class CustomConnection {
    private EObject origin;
    private Pattern pattern;
    private String label;
    private CustomNode source;
    private CustomNode destination;
    private boolean negative;

    public CustomConnection(String label, CustomNode source, CustomNode destination) {
        this.label = label;
        this.source = source;
        this.destination = destination;
        negative = false;
    }

    public CustomConnection(String label, CustomNode source, CustomNode destination, EObject o, Pattern p) {
        this.label = label;
        this.source = source;
        this.destination = destination;
        origin = o;
        pattern = p;
        negative = false;
    }

    public String getLabel() {
        return label;
    }

    public CustomNode getSource() {
        return source;
    }

    public CustomNode getDestination() {
        return destination;
    }

    public EObject getOrigin() {
        return origin;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setNegative(boolean n) {
        negative = n;
    }

    public boolean isNegative() {
        return negative;
    }

}
