package hu.bme.mit.emf.incquery.visualization.callgraph;

import hu.bme.mit.emf.incquery.visualization.model.PatternElement;

import java.util.List;

import org.eclipse.incquery.patternlanguage.emf.eMFPatternLanguage.PatternModel;
import org.eclipse.incquery.patternlanguage.patternLanguage.AggregatedValue;
import org.eclipse.incquery.patternlanguage.patternLanguage.CompareConstraint;
import org.eclipse.incquery.patternlanguage.patternLanguage.ComputationValue;
import org.eclipse.incquery.patternlanguage.patternLanguage.Constraint;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;
import org.eclipse.incquery.patternlanguage.patternLanguage.PatternBody;
import org.eclipse.incquery.patternlanguage.patternLanguage.PatternCompositionConstraint;
import org.eclipse.incquery.patternlanguage.patternLanguage.ValueReference;

public class CallGraphModelContentProvider {
    private int bodycount;
    private CallGraphModel cgm;

    public CallGraphModelContentProvider(PatternModel model) {
        cgm = new CallGraphModel();
        for (Pattern p : model.getPatterns()) {
            add(p);
        }
    }

    private void add(Pattern p) {
        bodycount = 0;
        cgm.addPattern(p);
        for (PatternBody pb : p.getBodies()) {
            bodycount++;
            add(p, pb, bodycount);
        }
    }

    private void add(Pattern p, PatternBody pb, int index) {
        for (Constraint c : pb.getConstraints()) {
            add(p, c, index);
        }
    }

    private void add(Pattern p, PatternCompositionConstraint pcc, int index) {
        cgm.addPatternCall(p, pcc.getCall(), pcc.isNegative(), index);
    }

    private void add(Pattern p, ValueReference vr, int index) {
        if (vr instanceof ComputationValue) {
            AggregatedValue ag = (AggregatedValue) vr;
            cgm.addAggregatedCall(p, ag.getCall(), false, index);
        }
    }

    private void add(Pattern p, CompareConstraint cc, int index) {
        add(p, cc.getLeftOperand(), index);
        add(p, cc.getRightOperand(), index);
    }

    private void add(Pattern p, Constraint c, int index) {
        if (c instanceof PatternCompositionConstraint) {
            add(p, (PatternCompositionConstraint) c, index);
        }
        if (c instanceof CompareConstraint) {
            add(p, (CompareConstraint) c, index);
        }
    }

    public List<PatternElement> getNodes() {
        return cgm.getNodes();
    }
}
