//package org.eclipse.gef4.zest.internal.dot
package hu.bme.mit.emf.incquery.visualization.view

import org.eclipse.gef4.zest.core.widgets.Graph
import org.eclipse.gef4.zest.layouts.algorithms.TreeLayoutAlgorithm
import org.eclipse.gef4.zest.core.widgets.ZestStyles
import org.eclipse.gef4.zest.layouts.LayoutAlgorithm
import org.eclipse.gef4.zest.layouts.algorithms.RadialLayoutAlgorithm
import org.eclipse.gef4.zest.layouts.algorithms.GridLayoutAlgorithm
import org.eclipse.gef4.zest.layouts.algorithms.SpringLayoutAlgorithm
import org.eclipse.gef4.zest.core.widgets.GraphNode
import org.eclipse.gef4.zest.core.widgets.GraphContainer
import org.eclipse.gef4.zest.core.widgets.GraphConnection
import org.eclipse.swt.SWT
import org.eclipse.draw2d.Ellipse
import org.eclipse.swt.graphics.FontData
import org.eclipse.swt.graphics.Color

class XtendDotTemplate {
	String[] compatibleFonts = newArrayList( "AvantGarde-Book",
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
	def String generate(Graph graph) '''
	  «val small = graph.nodes.size < 100»
	  «val algo = if (graph.layoutAlgorithm != null) graph.layoutAlgorithm else new TreeLayoutAlgorithm»
	  «val digraph = graph.connectionStyle == ZestStyles::CONNECTIONS_DIRECTED»
	  «val simpleClassName = if (graph.getClass().simpleName == "Graph") "ZestGraph" else graph.getClass().simpleName»
	  
	  «IF digraph»digraph«ELSE»graph«ENDIF» «simpleClassName»{
	  	
	  	/* Global settings */
	  	graph[layout=«algo.dotLayout(small)»]
	  	node[shape=box] //more like the Zest default node look
	  	rankdir=«algo.rankDir»
	  	
	  	/* Nodes */
	  	«FOR nodeObj : graph.nodes»
	  		«val node = nodeObj as GraphNode»
	  		«node.generate»	
	  	«ENDFOR»
	  	
	  	/* Edges */
	  	«FOR edgeObj : graph.connections»
	  		«val edge = edgeObj as GraphConnection»
	  		«edge.generate(digraph)»
	  	«ENDFOR»
	  }
	'''
	
	def generate(GraphNode node) '''
		«IF node.getClass() == typeof(GraphContainer)»
			«FOR nodeObj : (node as GraphContainer).nodes»
				«node.generateSimple»
			«ENDFOR»
	  	«ELSE»
	  		«node.generateSimple»
	  	«ENDIF»
	'''
	
	def generateSimple(GraphNode node) 
	'''«node.hashCode»[«node.label»«node.compatibleFont»«node.shape» style="filled" «node.color»];'''
	
	def getLabel(GraphNode node) 
	'''label="«node.text»"'''
	
	def getCompatibleFont(GraphNode node) 
	'''«IF node.font!=null »«val fontData=node.font.fontData.get(0)» fontsize=«fontData.getHeight» fontname="«fontData.compatibleName»"«ENDIF»'''
	
	def getShape(GraphNode node) 
	'''«IF node.figure instanceof Ellipse» shape=ellipse«ENDIF»'''
	
	def getColor(GraphNode node) 
	'''color="«node.borderColor.hexRGB»" fontcolor="«node.foregroundColor.hexRGB»" fillcolor="«node.backgroundColor.hexRGB»"'''
	
	def generate(GraphConnection edge, boolean digraph) '''
«edge.source.hashCode»«edgeString(digraph)»«edge.destination.hashCode»[style=«edge.edgeStyle» label="«edge.text»"«edge.direction» «edge.color»];
	'''
	
	def getDirection(GraphConnection edge) 
	'''«IF edge.directed» dir=forward«ENDIF»'''
	
	def getColor(GraphConnection edge) 
	'''color="«edge.lineColor.hexRGB»" fontcolor="«edge.highlightColor.hexRGB»"'''
	
	def dotLayout(LayoutAlgorithm algorithm, boolean small) {
		switch(algorithm.getClass()){
			case typeof(RadialLayoutAlgorithm): "twopi"
			case typeof(GridLayoutAlgorithm) : "osage"
			case typeof(SpringLayoutAlgorithm) : if (small) "fdp" else "sfdp"
			default : "dot"
		}
	}
	
	def rankDir(LayoutAlgorithm algorithm) {
		switch(algorithm.getClass()){
			case typeof(TreeLayoutAlgorithm):
				if ((algorithm as TreeLayoutAlgorithm).direction == TreeLayoutAlgorithm::LEFT_RIGHT) "LR" else "TD"
			default:	
				"LR"
		}
	}
	
	def edgeStyle(GraphConnection connection) {
	    switch (connection.lineStyle) {
	    	case SWT::LINE_DASH: "dashed"
	    	case SWT::LINE_DOT: "dotted"
	    	default: "solid"
	    }
	}
	
	def edgeString(boolean directed){
		if (directed)
			"->"
		else
			"--"
	}
	
	def getHexRGB(Color c) 
	'''#«c.red.hex»«c.green.hex»«c.blue.hex»'''
	
	def getHex(int x) 
	'''«var String s = Integer::toHexString(x)»«IF s.length() < 2»0«ENDIF»«s»'''
	
	def getCompatibleName(FontData f) 
	'''«var String s=f.name.replaceAll(" ","-")»«IF (compatibleFonts.contains(s))»«s»«ELSE»Times-Roman«ENDIF»«IF (f.style==SWT::BOLD)» bold«ENDIF»«IF (f.style==SWT::ITALIC)» italic«ENDIF»'''
}