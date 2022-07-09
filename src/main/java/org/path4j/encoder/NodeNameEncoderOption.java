package org.path4j.encoder;

import org.path4j.NodeName;

public abstract class NodeNameEncoderOption {

	public static class EncodeSizeResult {
		public int estimatedSize;
	}
	
	public abstract NodeName tryEncode(String name, int maxEstimatedSize, EncodeSizeResult sizeResult);

}
