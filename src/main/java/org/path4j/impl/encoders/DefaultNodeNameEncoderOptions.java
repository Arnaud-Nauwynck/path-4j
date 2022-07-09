package org.path4j.impl.encoders;

import static org.path4j.impl.encoders.NodeNameEncoderConstants.VM_ArrayHeaderSize;
import static org.path4j.impl.encoders.NodeNameEncoderConstants.VM_CharSize;
import static org.path4j.impl.encoders.NodeNameEncoderConstants.VM_IntSize;
import static org.path4j.impl.encoders.NodeNameEncoderConstants.VM_ObjectHeaderSize;
import static org.path4j.impl.encoders.NodeNameEncoderConstants.VM_RefSize;

import org.path4j.NodeName;
import org.path4j.encoder.NodeNameEncoderOption;
import org.path4j.impl.StringNodeName;

import lombok.AllArgsConstructor;

public class DefaultNodeNameEncoderOptions {

	public static final StringNodeNameEncoderOption CHAR_ARRAY_OPTION = new StringNodeNameEncoderOption();
	public static final StringNodeNameEncoderOption STRING_OPTION = new StringNodeNameEncoderOption();

	public static int defaultStringEncoderSize(String name) {
		return VM_ObjectHeaderSize // for StringNodeName object
				+ VM_RefSize // for StringNodeName.name
				+ VM_ObjectHeaderSize // for java.lang.String object
				+ VM_RefSize // for java.lang.String.value ref
				+ VM_IntSize // for java.lan.String.hash
				+ VM_ArrayHeaderSize // for char[] array object
				+ name.length() * VM_CharSize; // for chars element (maybe ascii compressed by jvm?)
	}
	
	public static int defaultCharArrayEncoderSize(char[] name) {
		return VM_ObjectHeaderSize // for CharArrayNodeName object
				+ VM_RefSize // for StringNodeName.name
				+ VM_ArrayHeaderSize // for char[] array object
				+ name.length * VM_CharSize; // for chars element (maybe ascii compressed by jvm?)
	}
	
	// ------------------------------------------------------------------------
	
	public static class StringNodeNameEncoderOption extends NodeNameEncoderOption {
		@Override
		public NodeName tryEncode(String name, int maxEstimatedSize, EncodeSizeResult sizeResult) {
			sizeResult.estimatedSize = defaultStringEncoderSize(name);
			return new StringNodeName(name);
		}
	}

	// ------------------------------------------------------------------------
	
	@AllArgsConstructor
	public static class CharArrayNodeName extends NodeName {

		private final char[] name;
		
		@Override
		public String toText() {
			return new String(name);
		}
	}
	
	public static class CharArrayNodeNameEncoderOption extends NodeNameEncoderOption {
		@Override
		public NodeName tryEncode(String name, int maxEstimatedSize, EncodeSizeResult sizeResult) {
			char[] charArray = name.toCharArray();
			sizeResult.estimatedSize = defaultCharArrayEncoderSize(charArray);
			return new CharArrayNodeName(charArray);
		}
	}

}
