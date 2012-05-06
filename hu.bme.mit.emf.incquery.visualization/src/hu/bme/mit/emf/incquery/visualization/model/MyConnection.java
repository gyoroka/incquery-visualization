package hu.bme.mit.emf.incquery.visualization.model;

public class MyConnection {
	private String id; 
	private String label; 
	final MyNode source;
	final MyNode destination;
	private boolean negative;
	
	public MyConnection(String id, String label, MyNode source, MyNode destination) {
		this.id = id;
		this.label = label;
		this.source = source;
		this.destination = destination;
		negative=false;
	}

	public String getLabel() {
		//System.out.println(label);
		return label;
	}
	
	public MyNode getSource() {
		return source;
	}
	public MyNode getDestination() {
		return destination;
	}
	public void setNegative(boolean n)
	{
		negative=n;
	}
	public boolean isNegative()
	{
		return negative;
	}
	
}
