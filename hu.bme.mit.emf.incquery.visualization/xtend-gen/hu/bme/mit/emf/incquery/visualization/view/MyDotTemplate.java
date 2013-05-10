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
public class MyDotTemplate {
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
    String _xblockexpression = null;
    {
      List _nodes = graph.getNodes();
      int _size = _nodes.size();
      boolean small = (_size < 100);
      LayoutAlgorithm algo = graph.getLayoutAlgorithm();
      boolean _equals = Objects.equal(algo, null);
      if (_equals) {
        TreeLayoutAlgorithm _treeLayoutAlgorithm = new TreeLayoutAlgorithm();
        algo = _treeLayoutAlgorithm;
      }
      int _connectionStyle = graph.getConnectionStyle();
      boolean digraph = (_connectionStyle == ZestStyles.CONNECTIONS_DIRECTED);
      Class<? extends Object> _class = graph.getClass();
      String simpleClassName = _class.getSimpleName();
      FontData fontData = null;
      boolean _equals_1 = simpleClassName.equals("Graph");
      if (_equals_1) {
        String _plus = ("Zest" + simpleClassName);
        simpleClassName = _plus;
      }
      StringConcatenation _builder = new StringConcatenation();
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
      _builder.append("/* Global settings */");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("graph[layout=");
      String _layout = this.getLayout(algo, small);
      _builder.append(_layout, "	");
      _builder.append("]");
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.append("node[shape=box] //more like the Zest default node look");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("rankdir=");
      {
        boolean _and = false;
        boolean _and_1 = false;
        LayoutAlgorithm _layoutAlgorithm = graph.getLayoutAlgorithm();
        boolean _notEquals = (!Objects.equal(_layoutAlgorithm, null));
        if (!_notEquals) {
          _and_1 = false;
        } else {
          LayoutAlgorithm _layoutAlgorithm_1 = graph.getLayoutAlgorithm();
          Class<? extends Object> _class_1 = _layoutAlgorithm_1.getClass();
          boolean _equals_2 = Objects.equal(_class_1, TreeLayoutAlgorithm.class);
          _and_1 = (_notEquals && _equals_2);
        }
        if (!_and_1) {
          _and = false;
        } else {
          LayoutAlgorithm _layoutAlgorithm_2 = graph.getLayoutAlgorithm();
          int _direction = ((TreeLayoutAlgorithm) _layoutAlgorithm_2).getDirection();
          boolean _equals_3 = (_direction == TreeLayoutAlgorithm.LEFT_RIGHT);
          _and = (_and_1 && _equals_3);
        }
        if (_and) {
          _builder.append("LR");
        } else {
          _builder.append("TD");
        }
      }
      _builder.newLineIfNotEmpty();
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Nodes */");
      _builder.newLine();
      _builder.append("\t");
      {
        List _nodes_1 = graph.getNodes();
        boolean _hasElements = false;
        for(final GraphNode node : ((Iterable<GraphNode>) _nodes_1)) {
          if (!_hasElements) {
            _hasElements = true;
          } else {
            _builder.appendImmediate("\n", "	");
          }
          {
            boolean _not = (!(node instanceof GraphContainer));
            if (_not) {
              int _hashCode = node.hashCode();
              _builder.append(_hashCode, "	");
              _builder.append("[label=\"");
              String _text = node.getText();
              _builder.append(_text, "	");
              _builder.append("\"");
              {
                Font _font = node.getFont();
                boolean _notEquals_1 = (!Objects.equal(_font, null));
                if (_notEquals_1) {
                  _builder.append("fontsize=");
                  int _xblockexpression_1 = (int) 0;
                  {
                    Font _font_1 = node.getFont();
                    FontData[] _fontData = _font_1.getFontData();
                    FontData _get = ((List<FontData>)Conversions.doWrapArray(_fontData)).get(0);
                    fontData = _get;
                    int _height = fontData.getHeight();
                    _xblockexpression_1 = (_height);
                  }
                  _builder.append(_xblockexpression_1, "	");
                  _builder.append(" fontname=\"");
                  String _compatibleName = this.getCompatibleName(fontData);
                  _builder.append(_compatibleName, "	");
                  _builder.append("\"");
                }
              }
              _builder.append(" style=filled");
              {
                IFigure _figure = node.getFigure();
                if ((_figure instanceof Ellipse)) {
                  _builder.append(" shape=ellipse");
                }
              }
              _builder.append(" color=\"");
              Color _borderColor = node.getBorderColor();
              String _hexRGB = this.getHexRGB(_borderColor);
              _builder.append(_hexRGB, "	");
              _builder.append("\" fontcolor=\"");
              Color _foregroundColor = node.getForegroundColor();
              String _hexRGB_1 = this.getHexRGB(_foregroundColor);
              _builder.append(_hexRGB_1, "	");
              _builder.append("\" fillcolor=\"");
              Color _backgroundColor = node.getBackgroundColor();
              String _hexRGB_2 = this.getHexRGB(_backgroundColor);
              _builder.append(_hexRGB_2, "	");
              _builder.append("\"");
              _builder.newLineIfNotEmpty();
              _builder.append("\t");
              _builder.append("];");
            } else {
              {
                List _nodes_2 = ((GraphContainer) node).getNodes();
                boolean _hasElements_1 = false;
                for(final GraphNode n : ((Iterable<GraphNode>) _nodes_2)) {
                  if (!_hasElements_1) {
                    _hasElements_1 = true;
                  } else {
                    _builder.appendImmediate("\n", "");
                  }
                  _builder.append(" ");
                  _builder.newLineIfNotEmpty();
                  int _hashCode_1 = node.hashCode();
                  _builder.append(_hashCode_1, "");
                  _builder.append("[label=\"");
                  String _text_1 = n.getText();
                  _builder.append(_text_1, "");
                  _builder.append("\"];");
                  _builder.newLineIfNotEmpty();
                }
              }
            }
          }
        }
      }
      _builder.append("\t");
      _builder.newLine();
      _builder.append("\t");
      _builder.append("/* Edges */");
      _builder.newLine();
      {
        List _connections = graph.getConnections();
        boolean _hasElements_2 = false;
        for(final GraphConnection edge : ((Iterable<GraphConnection>) _connections)) {
          if (!_hasElements_2) {
            _hasElements_2 = true;
          } else {
            _builder.appendImmediate("\n", "	");
          }
          _builder.append("\t");
          GraphNode _source = edge.getSource();
          int _hashCode_2 = _source.hashCode();
          _builder.append(_hashCode_2, "	");
          _builder.append(" ");
          {
            if (digraph) {
              _builder.append("-> ");
            } else {
              _builder.append("--");
            }
          }
          GraphNode _destination = edge.getDestination();
          int _hashCode_3 = _destination.hashCode();
          _builder.append(_hashCode_3, "	");
          _builder.append("[style=");
          String _edgeLineStyle = this.getEdgeLineStyle(edge);
          _builder.append(_edgeLineStyle, "	");
          _builder.append(" label=\"");
          String _text_2 = edge.getText();
          _builder.append(_text_2, "	");
          _builder.append("\"");
          {
            boolean _isDirected = edge.isDirected();
            if (_isDirected) {
              _builder.append(" dir=forward");
            }
          }
          _builder.append(" color=\"");
          Color _lineColor = edge.getLineColor();
          String _hexRGB_3 = this.getHexRGB(_lineColor);
          _builder.append(_hexRGB_3, "	");
          _builder.append("\" fontcolor=\"");
          Color _highlightColor = edge.getHighlightColor();
          String _hexRGB_4 = this.getHexRGB(_highlightColor);
          _builder.append(_hexRGB_4, "	");
          _builder.append("\"];");
          _builder.newLineIfNotEmpty();
        }
      }
      _builder.append("}");
      _xblockexpression = (_builder.toString());
    }
    return _xblockexpression;
  }
  
  private String getLayout(final LayoutAlgorithm algo, final boolean small) {
    String _switchResult = null;
    Class<? extends Object> _class = algo.getClass();
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
  
  private String getEdgeLineStyle(final GraphConnection edge) {
    String _switchResult = null;
    int _lineStyle = edge.getLineStyle();
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
  
  private String getHexRGB(final Color c) {
    int _red = c.getRed();
    String _hex = this.getHex(_red);
    String _plus = ("#" + _hex);
    int _green = c.getGreen();
    String _hex_1 = this.getHex(_green);
    String _plus_1 = (_plus + _hex_1);
    int _blue = c.getBlue();
    String _hex_2 = this.getHex(_blue);
    String _plus_2 = (_plus_1 + _hex_2);
    return _plus_2;
  }
  
  private String getHex(final int x) {
    String s = Integer.toHexString(x);
    int _length = s.length();
    boolean _lessThan = (_length < 2);
    if (_lessThan) {
      return ("0" + s);
    }
    return s;
  }
  
  private String getCompatibleName(final FontData f) {
    String fontName = "Times-Roman";
    String s = f.getName();
    s.replaceAll(" ", "-");
    boolean _contains = ((List<String>)Conversions.doWrapArray(this.compatibleFonts)).contains(s);
    if (_contains) {
      fontName = s;
    }
    int _style = f.getStyle();
    boolean _equals = (_style == SWT.BOLD);
    if (_equals) {
      String _plus = (fontName + " bold");
      fontName = _plus;
    }
    int _style_1 = f.getStyle();
    boolean _equals_1 = (_style_1 == SWT.ITALIC);
    if (_equals_1) {
      String _plus_1 = (fontName + " italic");
      fontName = _plus_1;
    }
    return fontName;
  }
}
