package org.cvpcs.java.metrics;

import java.util.ArrayList;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class CKJMMetricFileParser extends MetricFileParser {

	@Override
	public boolean canParse(Document doc) {
		return (doc.getDocumentElement().getTagName().equals("ckjm"));
	}
	
	@Override
	protected ArrayList<Metric> parseMetrics(Document doc) {
		ArrayList<Metric> metrics = new ArrayList<Metric>();
		
		try {
			NodeList nodes = XPathAPI.selectNodeList(doc, "//class");
			
			for (int i = 0; i < nodes.getLength(); i++) {
				for (Metric metric : parseClass((Element)nodes.item(i))) {
					metrics.add(metric);
				}
			}
		} catch (Exception e) { }
		
		return metrics;
	}
	
	private ArrayList<Metric> parseClass(Element classNode) {
		ArrayList<Metric> metrics = new ArrayList<Metric>();
		
		try {
			Element nameNode = (Element)XPathAPI.selectSingleNode(classNode, "./name");
			String className = nameNode.getTextContent();
			
			NodeList nodes = XPathAPI.selectNodeList(classNode, "./*");
			
			for (int i = 0; i < nodes.getLength(); i++) {
				Element elem = (Element)nodes.item(i);
				String name = elem.getTagName();
				String value = elem.getTextContent();
				ClassMetric metric = null;
				
				if (name.equals("wmc")) {
					metric = new WeightedMethodsPerClassMetric();
				} else if (name.equals("dit")) {
					metric = new DepthOfInheritanceTreeMetric();
				} else if (name.equals("noc")) {
					metric = new NumberOfChildrenMetric();
				} else if (name.equals("cbo")) {
					metric = new CouplingBetweenObjectsMetric();
				} else if (name.equals("rfc")) {
					metric = new ResponseForAClassMetric();
				} else if (name.equals("lcom")) {
					metric = new LackOfCohesionInMethodsMetric();
				} else if (name.equals("ca")) {
					metric = new AfferentCouplingsMetric();
				} else if (name.equals("npm")) {
					metric = new NumberOfPublicMethodsMetric();
				}
				
				if (metric != null) {
					metric.setClassName(className);
					metric.setValue(value);
					metrics.add(metric);
				}
			}
		} catch (Exception e) { }
		
		return metrics;
	}
}
