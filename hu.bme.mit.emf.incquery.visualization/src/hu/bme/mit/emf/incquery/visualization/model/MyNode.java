package hu.bme.mit.emf.incquery.visualization.model;

import java.util.ArrayList;
import java.util.List;

public class MyNode {
	private final String id;
	private final String name;
	private List<MyConnection> connections;

	public MyNode(String id, String name) {
		this.id = id;
		this.name = name;
		this.connections = new ArrayList<MyConnection>();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<MyConnection> getConnectedTo() {
		return connections;
	}

}