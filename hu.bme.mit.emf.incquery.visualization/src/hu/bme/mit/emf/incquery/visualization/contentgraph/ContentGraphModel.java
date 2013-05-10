package hu.bme.mit.emf.incquery.visualization.contentgraph;

import hu.bme.mit.emf.incquery.visualization.model.AggregatedElement;
import hu.bme.mit.emf.incquery.visualization.model.CheckElement;
import hu.bme.mit.emf.incquery.visualization.model.CustomConnection;
import hu.bme.mit.emf.incquery.visualization.model.CustomNode;
import hu.bme.mit.emf.incquery.visualization.model.PathConnection;
import hu.bme.mit.emf.incquery.visualization.model.PatternElement;
import hu.bme.mit.emf.incquery.visualization.model.VariableElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.incquery.patternlanguage.emf.eMFPatternLanguage.EClassifierConstraint;
import org.eclipse.incquery.patternlanguage.emf.types.IEMFTypeProvider;
import org.eclipse.incquery.patternlanguage.helper.CorePatternLanguageHelper;
import org.eclipse.incquery.patternlanguage.patternLanguage.AggregatedValue;
import org.eclipse.incquery.patternlanguage.patternLanguage.BoolValue;
import org.eclipse.incquery.patternlanguage.patternLanguage.CheckConstraint;
import org.eclipse.incquery.patternlanguage.patternLanguage.CompareConstraint;
import org.eclipse.incquery.patternlanguage.patternLanguage.ComputationValue;
import org.eclipse.incquery.patternlanguage.patternLanguage.DoubleValue;
import org.eclipse.incquery.patternlanguage.patternLanguage.IntValue;
import org.eclipse.incquery.patternlanguage.patternLanguage.LiteralValueReference;
import org.eclipse.incquery.patternlanguage.patternLanguage.PathExpressionConstraint;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;
import org.eclipse.incquery.patternlanguage.patternLanguage.PatternCall;
import org.eclipse.incquery.patternlanguage.patternLanguage.StringValue;
import org.eclipse.incquery.patternlanguage.patternLanguage.ValueReference;
import org.eclipse.incquery.patternlanguage.patternLanguage.Variable;
import org.eclipse.incquery.patternlanguage.patternLanguage.VariableReference;
import org.eclipse.incquery.patternlanguage.patternLanguage.VariableValue;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

public class ContentGraphModel {
    private Pattern parentPattern;
    private List<VariableElement> variables;
    private List<PatternElement> patterns;
    private List<AggregatedElement> aggregateds;
    private List<CheckElement> checks;
    private List<CustomNode> ints, strings, bools, doubles;
    private IEMFTypeProvider iEMFTypeProvider;

    public List<CustomNode> getNodes() {
        List<CustomNode> tmp = new ArrayList<CustomNode>();
        tmp.addAll(variables);
        tmp.addAll(ints);
        tmp.addAll(strings);
        tmp.addAll(bools);
        tmp.addAll(doubles);
        tmp.addAll(patterns);
        tmp.addAll(aggregateds);
        tmp.addAll(checks);
        return tmp;
    }

    public ContentGraphModel(IEMFTypeProvider iEMFTypeProvider0, Pattern p) {
        variables = new ArrayList<VariableElement>();
        ints = new ArrayList<CustomNode>();
        strings = new ArrayList<CustomNode>();
        bools = new ArrayList<CustomNode>();
        doubles = new ArrayList<CustomNode>();
        patterns = new ArrayList<PatternElement>();
        aggregateds = new ArrayList<AggregatedElement>();
        checks = new ArrayList<CheckElement>();
        iEMFTypeProvider = iEMFTypeProvider0;
        parentPattern = p;
    }

    public void addParameter(Variable v) {
        VariableElement ve = new VariableElement(v.getName(), v, parentPattern, true);
        EClassifier eclass = iEMFTypeProvider.getClassifierForVariable(v);
        if (eclass != null)
            ve.setClassifierName(eclass.getName());
        variables.add(ve);
    }

    public void addVariable(Variable v) {
        VariableElement ve = getVariable(v);
        EClassifier eclass = iEMFTypeProvider.getClassifierForVariable(v);
        if ((ve.getClassifierName() == null) && (eclass != null)) {
            ve.setClassifierName(eclass.getName());
        }
    }

    public void addPathExpression(PathExpressionConstraint pec, String tail) {
        CustomNode snode = getVariableReference(pec.getHead().getSrc());
        CustomNode dnode = getValueNode(pec.getHead().getDst());
        PathConnection conn = new PathConnection(tail, snode, dnode, pec, parentPattern);
        snode.getConnectedTo().add(conn);
    }

    public void addClassifier(EClassifierConstraint ecc, String name) {
        VariableElement ve = findVariable(name);
        if ((ve != null) && (!ve.isParameter()) && (!(ve.getOrigin() instanceof VariableReference)))
            ve.setOrigin(ecc.getVar());
    }

    public void addCompare(CompareConstraint cc) {
        CustomNode left = getValueNode(cc.getLeftOperand());
        CustomNode right = getValueNode(cc.getRightOperand());
        CustomConnection conn = new CustomConnection(cc.getFeature().getLiteral(), left, right, cc, parentPattern);
        left.getConnectedTo().add(conn);
    }

    private PatternElement addComputationValue(ComputationValue cv) {
        AggregatedValue av = (AggregatedValue) cv;
        AggregatedElement node = addAggregatedComposition(av.getCall());
        return node;
    }

    public AggregatedElement addAggregatedComposition(PatternCall pc) {
        Pattern p = pc.getPatternRef();
        List<ValueReference> srcParams = pc.getParameters();
        List<Variable> dstParams = p.getParameters();
        AggregatedElement ae = addAggregatedValue(p);
        for (int index = 0; index < srcParams.size(); index++) {
            ValueReference vr = srcParams.get(index);
            CustomNode src = getValueNode(vr);
            String varName = dstParams.get(index).getName();
            boolean l = false;
            for (String parString : ae.getParameters()) {
                if (parString.equals(varName))
                    l = true;
            }
            if (!l)
                ae.getParameters().add(varName);
            CustomConnection conn = new CustomConnection(varName, src, ae, pc, parentPattern);
            src.getConnectedTo().add(conn);
        }
        return ae;
    }

    private AggregatedElement addAggregatedValue(Pattern p) {
        AggregatedElement node = new AggregatedElement(p.getName(), p, p);
        aggregateds.add(node);
        return node;
    }

    public PatternElement addPatternComposition(PatternCall pc, boolean negative) {
        Pattern p = pc.getPatternRef();
        List<ValueReference> srcParams = pc.getParameters();
        List<Variable> dstParams = p.getParameters();
        PatternElement pe = addPatternValue(p, negative);
        for (int index = 0; index < srcParams.size(); index++) {
            ValueReference vr = srcParams.get(index);
            CustomNode src = getValueNode(vr);
            String varName = dstParams.get(index).getName();
            boolean l = false;
            for (String parString : pe.getParameters()) {
                if (parString.equals(varName))
                    l = true;
            }
            if (!l)
                pe.getParameters().add(varName);
            CustomConnection conn = new CustomConnection(varName, src, pe, pc, parentPattern);
            src.getConnectedTo().add(conn);

        }
        return pe;
    }

    public void addCheck(CheckConstraint c) {
        ICompositeNode eObjectNode = NodeModelUtils.getNode(c);
        String s = eObjectNode.getText(); // this is the output
        CheckElement ce = new CheckElement(s, c, parentPattern);
        Set<Variable> sv = CorePatternLanguageHelper.getReferencedPatternVariablesOfXExpression(c.getExpression());
        for (Variable variable : sv) {
            VariableElement ve = getVariable(variable);
            CustomConnection conn = new CustomConnection("", ce, ve, c, parentPattern);
            ce.getConnectedTo().add(conn);
        }
        checks.add(ce);
    }
    
    
    public Object findElementByName(String name)
    {
    	for (VariableElement element : variables) {
			if (element.getName().equals(name)) return element;
		}
    	for (CustomNode element : ints) {
			if (element.getName().equals(name)) return element;
		}
    	for (CustomNode element : strings) {
			if (element.getName().equals(name)) return element;
		}
    	for (CustomNode element : bools) {
			if (element.getName().equals(name)) return element;
		}
    	for (CustomNode element : doubles) {
			if (element.getName().equals(name)) return element;
		}
    	for (PatternElement element : patterns) {
			if (element.getName().equals(name)) return element;
		}
    	for (AggregatedElement element : aggregateds) {
			if (element.getName().equals(name)) return element;
		}
    	for (CheckElement element : checks) {
			if (element.getName().equals(name)) return element;
		}
    	return null;
    }

    private PatternElement addPatternValue(Pattern p, boolean negative) {
        PatternElement node = new PatternElement(p.getName(), p, p, negative);
        patterns.add(node);
        return node;
    }

    // find=null if not found
    public VariableElement findVariable(Variable v) {
        String s = v.getName();
        return findVariable(s);
    }

    public VariableElement findVariable(String s) {
        for (VariableElement item : variables) {
            if (item.getName().equals(s))
                return item;
        }
        return null;
    }

    public CustomNode findInt(int i) {
        String s = Integer.toString(i);
        return findInt(s);
    }

    public CustomNode findInt(String s) {
        for (CustomNode item : ints) {
            if (item.getName().equals(s))
                return item;
        }
        return null;
    }

    public CustomNode findString(String s) {
        for (CustomNode item : strings) {
            if (item.getName().equals(s))
                return item;
        }
        return null;
    }

    public CustomNode findBool(boolean l) {
        String s = Boolean.toString(l);
        return findBool(s);
    }

    public CustomNode findBool(String s) {
        for (CustomNode item : bools) {
            if (item.getName().equals(s))
                return item;
        }
        return null;
    }

    public CustomNode findDouble(double d) {
        String s = Double.toString(d);
        return findDouble(s);
    }

    public CustomNode findDouble(String s) {
        for (CustomNode item : doubles) {
            if (item.getName().equals(s))
                return item;
        }
        return null;
    }

    public PatternElement findPatternElement(Pattern p) {
        String s = p.getName();
        for (PatternElement item : patterns) {
            if (item.getName().equals(s))
                return item;
        }
        return null;
    }

    // get=creates if not found
    private CustomNode getValueNode(ValueReference vr) {
        CustomNode node = null;
        LiteralValueReference lvr = null;
        if (vr instanceof VariableValue)
            node = getVariableValue((VariableValue) vr);
        if (vr instanceof LiteralValueReference) {
            lvr = (LiteralValueReference) vr;
            if (lvr instanceof IntValue)
                node = getIntValue((IntValue) lvr);
            if (lvr instanceof StringValue)
                node = getStringValue((StringValue) lvr);
            if (lvr instanceof BoolValue)
                node = getBoolValue((BoolValue) lvr);
            if (lvr instanceof DoubleValue)
                node = getDoubleValue((DoubleValue) lvr);
        }
        if (vr instanceof ComputationValue)
            node = addComputationValue((ComputationValue) vr);
        return node;
    }

    private VariableElement getVariable(Variable v) {
        VariableElement node = findVariable(v);
        if (node != null)
            return node;
        node = new VariableElement(v.getName(), v, parentPattern);
        variables.add(node);
        return node;
    }

    private VariableElement getVariableReference(VariableReference vr) {
        VariableElement node = findVariable(vr.getVariable());
        if (node != null) {
            if (!node.isParameter()) {
                EObject o = node.getOrigin();
                if (!(o instanceof VariableReference))
                    node.setOrigin(vr);
            }
            return node;
        }
        node = new VariableElement(vr.getVariable().getName(), vr, parentPattern);
        variables.add(node);
        return node;
    }

    private VariableElement getVariableValue(VariableValue vv) {
        VariableElement node = getVariableReference(vv.getValue());
        return node;
    }

    private CustomNode getIntValue(IntValue iv) {
        int i = iv.getValue();
        CustomNode node = findInt(i);
        if (node != null)
            return node;
        node = new CustomNode(Integer.toString(i), iv, parentPattern);
        ints.add(node);
        return node;
    }

    private CustomNode getStringValue(StringValue s) {
        CustomNode node = findString(s.getValue());
        if (node != null)
            return node;
        node = new CustomNode(s.getValue(), s, parentPattern);
        strings.add(node);
        return node;
    }

    private CustomNode getBoolValue(BoolValue bv) {
        boolean b = bv.isValue();
        CustomNode node = findBool(b);
        if (node != null)
            return node;
        node = new CustomNode(Boolean.toString(b), bv, parentPattern);
        bools.add(node);
        return node;
    }

    private CustomNode getDoubleValue(DoubleValue dv) {
        double d = dv.getValue();
        CustomNode node = findDouble(d);
        if (node != null)
            return node;
        node = new CustomNode(Double.toString(d), dv, parentPattern);
        doubles.add(node);
        return node;
    }

}
