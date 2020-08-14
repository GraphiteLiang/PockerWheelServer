package com.example.demo.solution;

public abstract class BasicData {
	public Type dataType;
	public String address;
	public abstract String toJson();
	public String getAddress() {
		return address;
	}

}
