package org.cvpcs.java.metrics;

public abstract class PackageMetric extends Metric {
	private String m_PackageName;
	
	public PackageMetric() {
		m_PackageName = "";
	}
	
	public String getPackageName() { return m_PackageName; }
	public void setPackageName(String name) { m_PackageName = name; }

	@Override
	public String toString() {
		return getPackageName() + "[" + getName() + "=" + getValue() + "];";
	}
}
