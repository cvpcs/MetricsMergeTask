package org.cvpcs.java.metrics;

public abstract class Metric {
	private String m_Value;
	
	public Metric() {
		m_Value = "0";
	}
	
	public abstract String getName();
	
	public String getValue() { return m_Value; }
	public void setValue(String value) { m_Value = value; }
	
	@Override
	public String toString() {
		return getName() + "=" + getValue() + ";";
	}
}
