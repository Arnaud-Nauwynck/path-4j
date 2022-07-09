package org.path4j.impl;

import org.path4j.NodeName;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StringNodeName extends NodeName {

	public final String name;
	
	@Override
	public String toText() {
		return name;
	}
}