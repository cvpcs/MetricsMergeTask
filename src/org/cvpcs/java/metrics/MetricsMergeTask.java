package org.cvpcs.java.metrics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.Path;

public class MetricsMergeTask extends Task {
	private File m_Output = null;
	private ArrayList<Path> m_Paths = new ArrayList<Path>();
	private ArrayList<File> m_Files = new ArrayList<File>();
	
	public void setOutput(File path) {
		m_Output = path;
	}
	
	public void addPath(Path path) {
		m_Paths.add(path);
	}
	
	public void execute()
			throws BuildException {
		// validate the configuration
		validate();
		
		ArrayList<Metric> metrics = new ArrayList<Metric>();
		
		for(File file : m_Files) {
			for(Metric metric : MetricFileParser.parseMetrics(file)) {
				metrics.add(metric);
			}
		}
		
		try {
			BufferedWriter writer = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(m_Output)));
			
			for(Metric metric : metrics) {
				writer.write(metric.toString() + "\n");
			}
			
			writer.flush();
			writer.close();
		} catch (Exception e) { }
	}
	
	private void validate()
			throws BuildException {
		if (m_Output == null) throw new BuildException("output file not set");
		if (m_Paths.size() == 0) throw new BuildException("paths not set");
		
		for (Path path : m_Paths) {
			for (String filepath : path.list()) {
				File file = new File(filepath);
				
				if (!file.exists()) {
					throw new BuildException("specified file does not exist: [" + file.getAbsolutePath() + "]");
				} else {
					m_Files.add(file);
				}
			}
		}
	}
}
