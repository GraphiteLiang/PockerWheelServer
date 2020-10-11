package com.example.demo.solution;

import java.io.Serializable;

public abstract class BasicData{
	public Type dataType;
	public String address;
	public abstract String toJson();
	public String getAddress() {
		return address;
	}

}
