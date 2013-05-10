package hu.bme.mit.emf.incquery.visualization.callgraph;

import hu.bme.mit.emf.incquery.visualization.model.AggregatedConnection;
import hu.bme.mit.emf.incquery.visualization.model.CustomConnection;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;
import org.eclipse.incquery.patternlanguage.patternLanguage.PatternCall;

public class CallGraphModel {
    private List<PatternElement> patterns;

    public CallGraphModel() {
        patterns = new ArrayList<PatternElement>();
    }

    public void addPattern(Pattern pattern) {
        getPattern(pattern);
    }

    public void addPatternCall(Pattern p, PatternCall pc, boolean b, int index) {
        PatternElement src = getPattern(p);
        PatternElement dst = getPattern(pc.getPatternRef());
        CustomConnection conn = new CustomConnection("Body: " + index, src, dst);
        conn.setNegative(b);
        boolean l = false;
        for (CustomConnection item : src.getConnectedTo()) {
            if ((item.getLabel().equals(conn.getLabel())) && (dst.getName().equals(item.getDestination().getName())))
                l = true;
        }
        if (!l)
            src.getConnectedTo().add(conn);
    }

    public void addAggregatedCall(Pattern p, PatternCall pc, boolean b, int index) {
        PatternElement src = getPattern(p);
        PatternElement dst = getPattern(pc.getPatternRef());
        AggregatedConnection conn = new AggregatedConnection("Body: " + index, src, dst);
        conn.setNegative(b);
        boolean l = false;
        for (CustomConnection item : src.getConnectedTo()) {
            if ((item.getLabel().equals(conn.getLabel())) && (dst.getName().equals(item.getDestination().getName())))
                l = true;
        }
        if (!l)
            src.getConnectedTo().add(conn);
    }

    private PatternElement findPattern(Pattern p) {
        for (PatternElement pe : patterns) {
            if (pe.getName().equals(p.getName()))
                return pe;
        }
        return null;
    }

    private PatternElement getPattern(Pattern p) {
        PatternElement pe = findPattern(p);
        if (pe != null)
            return pe;
        pe = new PatternElement(p.getName(), p, p);
        patterns.add(pe);
        return pe;
    }

    public List<PatternElement> getNodes() {
        List<PatternElement> list = new ArrayList<PatternElement>();
        list.addAll(patterns);
        return list;
    }

}
