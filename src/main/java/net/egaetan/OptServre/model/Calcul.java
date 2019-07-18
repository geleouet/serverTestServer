package net.egaetan.OptServre.model;

import java.util.List;

public class Calcul {

	String name;
	List<OperationType> operations;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<OperationType> getOperations() {
		return operations;
	}

	public void setOperations(List<OperationType> operations) {
		this.operations = operations;
	}

}
