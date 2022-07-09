package org.path4j;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.path4j.impl.StringNodeName;

public abstract class NodeName implements Comparable<NodeName> {

    public static final NodeName EMPTY = new StringNodeName("");

    // --------------------------------------------------------------------------------------------
    
	public abstract String toText();

	public void appendTo(StringBuilder sb) {
		String text = toText();
		sb.append(text);
	}

	public void appendTo(PrintStream out) {
		String text = toText();
		out.append(text);
	}

	public void writeUTF(DataOutputStream out) throws IOException {
		String text = toText();
		out.writeUTF(text);
	}

	// ------------------------------------------------------------------------

	@Override
	public int compareTo(NodeName other) {
		String text = toText();
		String otherText = other.toText();
		return text.compareTo(otherText);
	}

	@Override
	public String toString() {
		return toText();
	}

	@Override
	public int hashCode() {
		return toText().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeName other = (NodeName) obj;
		return toText().equals(other.toText());
	}

}
