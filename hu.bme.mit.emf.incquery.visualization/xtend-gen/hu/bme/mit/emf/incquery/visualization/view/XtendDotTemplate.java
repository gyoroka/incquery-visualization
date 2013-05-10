package hu.bme.mit.emf.incquery.visualization.view;

import com.google.common.base.Objects;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.IFigure;
import org.eclipse.gef4.zest.core.widgets.Graph;
import org.eclipse.gef4.zest.core.widgets.GraphConnection;
import org.eclipse.gef4.zest.core.widgets.GraphContainer;
import org.eclipse.gef4.zest.core.widgets.GraphNode;
import org.eclipse.gef4.zest.core.widgets.ZestStyles;
import org.eclipse.gef4.zest.layouts.LayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef4.zest.layouts.algorithms.TreeLayoutAlgorithm;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Conversions;
import org.eclipse.xtext.xbase.lib.Functions.Function0;

@SuppressWarnings("all")
public class XtendDotTemplate {
  private String[] compatibleFonts = new Function0<String[]>() {
    public String[] apply() {
      ArrayList<String> _newArrayList = CollectionLiterals.<String>newArrayList("AvantGarde-Book", 
        "AvantGarde-BookOblique", "AvantGarde-Demi", 
        "AvantGarde-DemiOblique", "Bookman-Demi", "Bookman-DemiItalic", 
        "Bookman-Light", "Bookman-LightItalic", "Courier", "Courier-Bold", 
        "Courier-BoldOblique", "Courier-Oblique", "Helvetica", 
        "Helvetica-Bold", "Helvetica-BoldOblique", "Helvetica-Narrow", 
        "Helvetica-Narrow-Bold", "Helvetica-Narrow-BoldOblique", 
        "Helvetica-Narrow-Oblique", "Helvetica-Oblique", 
        "NewCenturySchlbk-Bold", "NewCenturySchlbk-BoldItalic", 
        "NewCenturySchlbk-Italic", "NewCenturySchlbk-Roman", 
        "Palatino-Bold", "Palatino-BoldItalic", "Palatino-Italic", 
        "Palatino-Roman", "Symbol", "Times-Bold", "Times-BoldItalic", 
        "Times-Italic", "Times-Roman", "ZapfChancery-MediumItalic", 
        "ZapfDingbats");
      return ((String[])Conversions.unwrapArray(_newArrayList, String.class));
    }
  }.apply();
  
  public String generate(final Graph graph) {
    StringConcatenation _builder = new StringConcatenation();
    List _nodes = graph.getNodes();
    int _size = _nodes.size();
    final boolean small = (_size < 100);
    _builder.newLineIfNotEmpty();
    LayoutAlgorithm _xifexpression = null;
    LayoutAlgorithm _layoutAlgorithm = graph.getLayoutAlgorithm();
    boolean _notEquals = (!Objects.equal(_layoutAlgorithm, null));
    if (_notEquals) {
      LayoutAlgorithm _layoutAlgorithm_1 = graph.getLayoutAlgorithm();
      _xifexpression = _layoutAlgorithm_1;
    } else {
      TreeLayoutAlgorithm _treeLayoutAlgorithm = new TreeLayoutAlgorithm();
      _xifexpression = _treeLayoutAlgorithm;
    }
    final LayoutAlgorithm algo = _xifexpression;
    _builder.newLineIfNotEmpty();
    int _connectionStyle = graph.getConnectionStyle();
    final boolean digraph = (_connectionStyle == ZestStyles.CONNECTIONS_DIRECTED);
    _builder.newLineIfNotEmpty();
    String _xifexpression_1 = null;
    Class<? extends Object> _class = graph.getClass();
    String _simpleName = _class.getSimpleName();
    boolean _equals = Objects.equal(_simpleName, "Graph");
    if (_equals) {
      _xifexpression_1 = "ZestGraph";
    } else {
      Class<? extends Object> _class_1 = graph.getClass();
      String _simpleName_1 = _class_1.getSimpleName();
      _xifexpression_1 = _simpleName_1;
    }
    final String simpleClassName = _xifexpression_1;
    _builder.newLineIfNotEmpty();
    _builder.newLine();
    {
      if (digraph) {
        _builder.append("digraph");
      } else {
        _builder.append("graph");
      }
    }
    _builder.append(" ");
    _builder.append(simpleClassName, "");
    _builder.append("{");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Global settings */");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("graph[layout=");
    String _dotLayout = this.dotLayout(algo, small);
    _builder.append(_dotLayout, "	");
    _builder.append("]");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.append("node[shape=box] //more like the Zest default node look");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("rankdir=");
    String _rankDir = this.rankDir(algo);
    _builder.append(_rankDir, "	");
    _builder.newLineIfNotEmpty();
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Nodes */");
    _builder.newLine();
    {
      List _nodes_1 = graph.getNodes();
      for(final Object nodeObj : _nodes_1) {
        _builder.append("\t");
        final GraphNode node = ((GraphNode) nodeObj);
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _generate = this.generate(node);
        _builder.append(_generate, "	");
        _builder.append("\t");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("\t");
    _builder.newLine();
    _builder.append("\t");
    _builder.append("/* Edges */");
    _builder.newLine();
    {
      List _connections = graph.getConnections();
      for(final Object edgeObj : _connections) {
        _builder.append("\t");
        final GraphConnection edge = ((GraphConnection) edgeObj);
        _builder.newLineIfNotEmpty();
        _builder.append("\t");
        CharSequence _generate_1 = this.generate(edge, digraph);
        _builder.append(_generate_1, "	");
        _builder.newLineIfNotEmpty();
      }
    }
    _builder.append("}");
    _builder.newLine();
    return _builder.toString();
  }
  
  public CharSequence generate(final GraphNode node) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Class<? extends Object> _class = node.getClass();
      boolean _equals = Objects.equal(_class, GraphContainer.class);
      if (_equals) {
        {
          List _nodes = ((GraphContainer) node).getNodes();
          for(final Object nodeObj : _nodes) {
            CharSequence _generateSimple = this.generateSimple(node);
            _builder.append(_generateSimple, "");
            _builder.newLineIfNotEmpty();
          }
        }
      } else {
        CharSequence _generateSimple_1 = this.generateSimple(node);
        _builder.append(_generateSimple_1, "");
        _builder.newLineIfNotEmpty();
      }
    }
    return _builder;
  }
  
  public CharSequence generateSimple(final GraphNode node) {
    StringConcatenation _builder = new StringConcatenation();
    int _hashCode = node.hashCode();
    _builder.append(_hashCode, "");
    _builder.append("[");
    CharSequence _label = this.getLabel(node);
    _builder.append(_label, "");
    CharSequence _compatibleFont = this.getCompatibleFont(node);
    _builder.append(_compatibleFont, "");
    CharSequence _shape = this.getShape(node);
    _builder.append(_shape, "");
    _builder.append(" style=\"filled\" ");
    CharSequence _color = this.getColor(node);
    _builder.append(_color, "");
    _builder.append("];");
    return _builder;
  }
  
  public CharSequence getLabel(final GraphNode node) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("label=\"");
    String _text = node.getText();
    _builder.append(_text, "");
    _builder.append("\"");
    return _builder;
  }
  
  public CharSequence getCompatibleFont(final GraphNode node) {
    StringConcatenation _builder = new StringConcatenation();
    {
      Font _font = node.getFont();
      boolean _notEquals = (!Objects.equal(_font, null));
      if (_notEquals) {
        Font _font_1 = node.getFont();
        FontData[] _fontData = _font_1.getFontData();
        final FontData fontData = ((List<FontData>)Conversions.doWrapArray(_fontData)).get(0);
        _builder.append(" fontsize=");
        int _height = fontData.getHeight();
        _builder.append(_height, "");
        _builder.append(" fontname=\"");
        CharSequence _compatibleName = this.getCompatibleName(fontData);
        _builder.append(_compatibleName, "");
        _builder.append("\"");
      }
    }
    return _builder;
  }
  
  public CharSequence getShape(final GraphNode node) {
    StringConcatenation _builder = new StringConcatenation();
    {
      IFigure _figure = node.getFigure();
      if ((_figure instanceof Ellipse)) {
        _builder.append(" shape=ellipse");
      }
    }
    return _builder;
  }
  
  public CharSequence getColor(final GraphNode node) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("color=\"");
    Color _borderColor = node.getBorderColor();
    CharSequence _hexRGB = this.getHexRGB(_borderColor);
    _builder.append(_hexRGB, "");
    _builder.append("\" fontcolor=\"");
    Color _foregroundColor = node.getForegroundColor();
    CharSequence _hexRGB_1 = this.getHexRGB(_foregroundColor);
    _builder.append(_hexRGB_1, "");
    _builder.append("\" fillcolor=\"");
    Color _backgroundColor = node.getBackgroundColor();
    CharSequence _hexRGB_2 = this.getHexRGB(_backgroundColor);
    _builder.append(_hexRGB_2, "");
    _builder.append("\"");
    return _builder;
  }
  
  public CharSequence generate(final GraphConnection edge, final boolean digraph) {
    StringConcatenation _builder = new StringConcatenation();
    GraphNode _source = edge.getSource();
    int _hashCode = _source.hashCode();
    _builder.append(_hashCode, "");
    String _edgeString = this.edgeString(digraph);
    _builder.append(_edgeString, "");
    GraphNode _destination = edge.getDestination();
    int _hashCode_1 = _destination.hashCode();
    _builder.append(_hashCode_1, "");
    _builder.append("[style=");
    String _edgeStyle = this.edgeStyle(edge);
    _builder.append(_edgeStyle, "");
    _builder.append(" label=\"");
    String _text = edge.getText();
    _builder.append(_text, "");
    _builder.append("\"");
    CharSequence _direction = this.getDirection(edge);
    _builder.append(_direction, "");
    _builder.append(" ");
    CharSequence _color = this.getColor(edge);
    _builder.append(_color, "");
    _builder.append("];");
    _builder.newLineIfNotEmpty();
    return _builder;
  }
  
  public CharSequence getDirection(final GraphConnection edge) {
    StringConcatenation _builder = new StringConcatenation();
    {
      boolean _isDirected = edge.isDirected();
      if (_isDirected) {
        _builder.append(" dir=forward");
      }
    }
    return _builder;
  }
  
  public CharSequence getColor(final GraphConnection edge) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("color=\"");
    Color _lineColor = edge.getLineColor();
    CharSequence _hexRGB = this.getHexRGB(_lineColor);
    _builder.append(_hexRGB, "");
    _builder.append("\" fontcolor=\"");
    Color _highlightColor = edge.getHighlightColor();
    CharSequence _hexRGB_1 = this.getHexRGB(_highlightColor);
    _builder.append(_hexRGB_1, "");
    _builder.append("\"");
    return _builder;
  }
  
  public String dotLayout(final LayoutAlgorithm algorithm, final boolean small) {
    String _switchResult = null;
    Class<? extends Object> _class = algorithm.getClass();
    final Class<? extends Object> _switchValue = _class;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(_switchValue,RadialLayoutAlgorithm.class)) {
        _matched=true;
        _switchResult = "twopi";
      }
    }
    if (!_matched) {
      if (Objects.equal(_switchValue,GridLayoutAlgorithm.class)) {
        _matched=true;
        _switchResult = "osage";
      }
    }
    if (!_matched) {
      if (Objects.equal(_switchValue,SpringLayoutAlgorithm.class)) {
        _matched=true;
        String _xifexpression = null;
        if (small) {
          _xifexpression = "fdp";
        } else {
          _xifexpression = "sfdp";
        }
        _switchResult = _xifexpression;
      }
    }
    if (!_matched) {
      _switchResult = "dot";
    }
    return _switchResult;
  }
  
  public String rankDir(final LayoutAlgorithm algorithm) {
    String _switchResult = null;
    Class<? extends Object> _class = algorithm.getClass();
    final Class<? extends Object> _switchValue = _class;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(_switchValue,TreeLayoutAlgorithm.class)) {
        _matched=true;
        String _xifexpression = null;
        int _direction = ((TreeLayoutAlgorithm) algorithm).getDirection();
        boolean _equals = (_direction == TreeLayoutAlgorithm.LEFT_RIGHT);
        if (_equals) {
          _xifexpression = "LR";
        } else {
          _xifexpression = "TD";
        }
        _switchResult = _xifexpression;
      }
    }
    if (!_matched) {
      _switchResult = "LR";
    }
    return _switchResult;
  }
  
  public String edgeStyle(final GraphConnection connection) {
    String _switchResult = null;
    int _lineStyle = connection.getLineStyle();
    final int _switchValue = _lineStyle;
    boolean _matched = false;
    if (!_matched) {
      if (Objects.equal(_switchValue,SWT.LINE_DASH)) {
        _matched=true;
        _switchResult = "dashed";
      }
    }
    if (!_matched) {
      if (Objects.equal(_switchValue,SWT.LINE_DOT)) {
        _matched=true;
        _switchResult = "dotted";
      }
    }
    if (!_matched) {
      _switchResult = "solid";
    }
    return _switchResult;
  }
  
  public String edgeString(final boolean directed) {
    String _xifexpression = null;
    if (directed) {
      _xifexpression = "->";
    } else {
      _xifexpression = "--";
    }
    return _xifexpression;
  }
  
  public CharSequence getHexRGB(final Color c) {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("#");
    int _red = c.getRed();
    CharSequence _hex = this.getHex(_red);
    _builder.append(_hex, "");
    int _green = c.getGreen();
    CharSequence _hex_1 = this.getHex(_green);
    _builder.append(_hex_1, "");
    int _blue = c.getBlue();
    CharSequence _hex_2 = this.getHex(_blue);
    _builder.append(_hex_2, "");
    return _builder;
  }
  
  public CharSequence getHex(final int x) {
    StringConcatenation _builder = new StringConcatenation();
    String s = Integer.toHexString(x);
    {
      int _length = s.length();
      boolean _lessThan = (_length < 2);
      if (_lessThan) {
        _builder.append("0");
      }
    }
    _builder.append(s, "");
    return _builder;
  }
  
  public CharSequence getCompatibleName(final FontData f) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = f.getName();
    String s = _name.replaceAll(" ", "-");
    {
      boolean _contains = ((List<String>)Conversions.doWrapArray(this.compatibleFonts)).contains(s);
      if (_contains) {
        _builder.append(s, "");
      } else {
        _builder.append("Times-Roman");
      }
    }
    {
      int _style = f.getStyle();
      boolean _equals = (_style == SWT.BOLD);
      if (_equals) {
        _builder.append(" bold");
      }
    }
    {
      int _style_1 = f.getStyle();
      boolean _equals_1 = (_style_1 == SWT.ITALIC);
      if (_equals_1) {
        _builder.append(" italic");
      }
    }
    return _builder;
  }
}
