package org.cvpcs.java.metrics;

public abstract class ClassMetric extends Metric {
	private String m_ClassName;
	
	public ClassMetric() {
		m_ClassName = "";
	}
	
	public String getClassName() { return m_ClassName; }
	public void setClassName(String name) { m_ClassName = name; }

	@Override
	public String toString() {
		return getClassName() + "@" + getName() + "=" + getValue() + ";";
	}
}
