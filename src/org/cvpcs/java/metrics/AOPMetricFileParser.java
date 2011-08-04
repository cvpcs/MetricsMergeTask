package org.cvpcs.java.metrics;

import java.util.ArrayList;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class AOPMetricFileParser extends MetricFileParser {

	@Override
	public boolean canParse(Document doc) {
		return (doc.getDocumentElement().getTagName().equals("project"));
	}
	
	@Override
	protected ArrayList<Metric> parseMetrics(Document doc) {
		ArrayList<Metric> metrics = new ArrayList<Metric>();
		
		try {
			NodeList nodes = XPathAPI.selectNodeList(doc, "//package");
			
			for (int i = 0; i < nodes.getLength(); i++) {
				for (Metric metric : parsePackage((Element)nodes.item(i))) {
					metrics.add(metric);
				}
			}
		} catch (Exception e) { }
		
		return metrics;
	}

	private ArrayList<Metric> parsePackage(Element packageNode) {
		ArrayList<Metric> metrics = new ArrayList<Metric>();
		
		try {
			String packageName = packageNode.getAttribute("name");
			
			NodeList nodes = XPathAPI.selectNodeList(packageNode, "./type[@kind='class']");
			
			for (int i = 0; i < nodes.getLength(); i++) {
				for (Metric metric : parseClass((Element)nodes.item(i))) {
					metrics.add(metric);
				}
			}
			
			nodes = XPathAPI.selectNodeList(packageNode, "./metric");
			
			for (int i = 0; i < nodes.getLength(); i++) {
				Element elem = (Element)nodes.item(i);
				String name = elem.getAttribute("name");
				String value = elem.getAttribute("value");
				PackageMetric metric = null;
				
				if (name.equals("NOT")) {
				} else if (name.equals("A")) {
				} else if (name.equals("RMartin Ce")) {
				} else if (name.equals("RMartin Ca")) {
				} else if (name.equals("RMartin I")) {
				} else if (name.equals("RMartin D")) {
				} else if (name.equals("Ce")) {
				} else if (name.equals("Ca")) {
				} else if (name.equals("I")) {
				} else if (name.equals("Dn")) {	
				}
				
				if (metric != null) {
					metric.setPackageName(packageName);
					metric.setValue(value);
					metrics.add(metric);
				}
			}
		} catch (Exception e) { }
		
		return metrics;
	}
	
	private ArrayList<Metric> parseClass(Element classNode) {
		ArrayList<Metric> metrics = new ArrayList<Metric>();
		
		try {
			String className = classNode.getAttribute("name");
			
			NodeList nodes = XPathAPI.selectNodeList(classNode, "./metric");
			
			for (int i = 0; i < nodes.getLength(); i++) {
				Element elem = (Element)nodes.item(i);
				String name = elem.getAttribute("name");
				String value = elem.getAttribute("value");
				ClassMetric metric = null;
				
				if (name.equals("LOCC")) {
					metric = new LinesOfCodeMetric();
				} else if (name.equals("WOM")) {
				} else if (name.equals("DIT")) {
					metric = new DepthOfInheritanceTreeMetric();
				} else if (name.equals("NOC")) {
					metric = new NumberOfChildrenMetric();
				} else if (name.equals("CFA")) {
				} else if (name.equals("CMC")) {
				} else if (name.equals("CBM")) {
					metric = new CouplingBetweenObjectsMetric();
				} else if (name.equals("CDA")) {
				} else if (name.equals("CAE")) {
				} else if (name.equals("RFM")) {
					metric = new ResponseForAClassMetric();
				} else if (name.equals("LCO")) {
					metric = new LackOfCohesionInMethodsMetric();
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
