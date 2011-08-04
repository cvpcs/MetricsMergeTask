package org.cvpcs.java.metrics;

import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public abstract class MetricFileParser {
	
	private static final ArrayList<MetricFileParser> PARSERS;
	
	static {
		PARSERS = new ArrayList<MetricFileParser>();
		PARSERS.add(new AOPMetricFileParser());
		PARSERS.add(new CKJMMetricFileParser());
	}
	
	public static ArrayList<Metric> parseMetrics(File file) {
		ArrayList<Metric> metrics = new ArrayList<Metric>();
		
		try {			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(file);
			
			for (MetricFileParser parser : PARSERS) {
				if (parser.canParse(doc)) {
					metrics = parser.parseMetrics(doc);
					break;
				}
			}
		} catch(Exception e) { }
		
		return metrics;
	}
	
	public abstract boolean canParse(Document doc);
	
	protected abstract ArrayList<Metric> parseMetrics(Document doc);
}
