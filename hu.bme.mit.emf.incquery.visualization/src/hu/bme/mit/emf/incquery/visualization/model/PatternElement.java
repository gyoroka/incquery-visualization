package hu.bme.mit.emf.incquery.visualization.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;

public class PatternElement extends CustomNode {

    private List<String> parameters;
    private boolean negative;

    public PatternElement(String name, EObject o, Pattern p) {
        super(name, o, p);
        parameters = new ArrayList<String>();
    }

    public PatternElement(String name, EObject o, Pattern p, boolean b) {
        super(name, o, p);
        parameters = new ArrayList<String>();
        negative = b;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setNegative(boolean b) {
        negative = b;
    }

    public boolean isNegative() {
        return negative;
    }
}
