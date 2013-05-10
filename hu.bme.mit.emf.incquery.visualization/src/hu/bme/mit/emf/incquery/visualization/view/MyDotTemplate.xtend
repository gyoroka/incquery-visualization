package hu.bme.mit.emf.incquery.visualization.view

import org.eclipse.gef4.zest.core.widgets.Graph
import org.eclipse.gef4.zest.core.widgets.GraphConnection
import org.eclipse.gef4.zest.core.widgets.GraphContainer
import org.eclipse.gef4.zest.core.widgets.GraphNode
import org.eclipse.gef4.zest.core.widgets.ZestStyles
import org.eclipse.gef4.zest.layouts.LayoutAlgorithm
import org.eclipse.gef4.zest.layouts.algorithms.GridLayoutAlgorithm
import org.eclipse.gef4.zest.layouts.algorithms.RadialLayoutAlgorithm
import org.eclipse.gef4.zest.layouts.algorithms.SpringLayoutAlgorithm
import org.eclipse.gef4.zest.layouts.algorithms.TreeLayoutAlgorithm
import org.eclipse.swt.SWT
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.graphics.FontData
import org.eclipse.draw2d.Ellipse

class MyDotTemplate {
	//«»
	var String[] compatibleFonts = newArrayList( "AvantGarde-Book",
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
			"ZapfDingbats" );
	def public String generate(Graph graph)
	{
		var boolean small = graph.nodes.size < 100;
		var LayoutAlgorithm algo=graph.layoutAlgorithm;
		if (algo==null) algo=new TreeLayoutAlgorithm()
		var boolean digraph = graph.connectionStyle==ZestStyles::CONNECTIONS_DIRECTED;
		var String simpleClassName = graph.getClass().simpleName;
		var FontData fontData;
		/* The exact name 'Graph' is not valid for rendering with Graphviz: */
		if (simpleClassName.equals("Graph")) simpleClassName="Zest"+ simpleClassName;

//template:		
'''«IF digraph»digraph«ELSE»graph«ENDIF» «simpleClassName»{
	/* Global settings */
	graph[layout=«getLayout(algo,small)»]
	node[shape=box] //more like the Zest default node look
	rankdir=«IF ((graph.layoutAlgorithm != null) && (graph.layoutAlgorithm.getClass() == typeof(TreeLayoutAlgorithm)) && ((graph.getLayoutAlgorithm() as TreeLayoutAlgorithm).direction == TreeLayoutAlgorithm::LEFT_RIGHT))»LR«ELSE»TD«ENDIF»
	
	/* Nodes */
	«FOR node : graph.nodes as Iterable<GraphNode> SEPARATOR '\n'»« 
	IF (!(node instanceof GraphContainer))»«node.hashCode»[label="«node.text»"«IF node.font!=null »fontsize=«{fontData=node.font.fontData.get(0);fontData.getHeight}» fontname="«fontData.compatibleName»"«ENDIF
		» style=filled«IF node.figure instanceof Ellipse» shape=ellipse«ENDIF» color="«node.borderColor.hexRGB»" fontcolor="«node.foregroundColor.hexRGB»" fillcolor="«node.backgroundColor.hexRGB»"
	];«ELSE»«
	FOR n : (node as GraphContainer).nodes as Iterable<GraphNode> SEPARATOR '\n'» 
    «node.hashCode»[label="«n.text»"];
    «ENDFOR»
	«ENDIF»
	«ENDFOR»
	
	/* Edges */
	«FOR edge : graph.connections as Iterable<GraphConnection> SEPARATOR '\n'»
	«edge.source.hashCode» «
	IF (digraph) »-> «ELSE»--«ENDIF»«edge.destination.hashCode»[style=«edge.edgeLineStyle» label="«edge.text»"«IF edge.directed» dir=forward«ENDIF» color="«edge.lineColor.hexRGB»" fontcolor="«edge.highlightColor.hexRGB»"];
	«ENDFOR»
}'''
//
	}
	
	def private String getLayout(LayoutAlgorithm algo,boolean small)
	{
		switch algo.getClass() {
			case typeof(RadialLayoutAlgorithm) : "twopi"
			case typeof(GridLayoutAlgorithm) : "osage"
			case typeof(SpringLayoutAlgorithm) : if (small) "fdp" else "sfdp"
			default: "dot"
		}
	}
	
	def private String getEdgeLineStyle(GraphConnection edge)
	{
		
		switch edge.getLineStyle() {
			case SWT::LINE_DASH : "dashed"
			case SWT::LINE_DOT : "dotted"
			default : "solid"
		}
	}
	
	def private String getHexRGB(Color c) {
		"#"+ c.red.hex + c.green.hex + c.blue.hex
	}
	
	def private String getHex(int x) {
		var String s = Integer::toHexString(x);
		if (s.length() < 2) return "0" + s;
		return s;
	}
	
	def private String getCompatibleName(FontData f)
	{
		var String fontName="Times-Roman";
		var String s=f.name;
		s.replaceAll(" ","-");
		if (compatibleFonts.contains(s)) fontName=s;
		if (f.style==SWT::BOLD) fontName=fontName+" bold";
		if (f.style==SWT::ITALIC) fontName=fontName+" italic";
		return fontName
	}
	
}