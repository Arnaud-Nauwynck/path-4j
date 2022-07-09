package org.path4j;

import java.util.Arrays;

import lombok.val;

/**
 * (immutable) value-object path, represented as NodeName[]
 *
 */
public final class NodeNamesPath {

	public static final NodeNamesPath ROOT = new NodeNamesPath(new NodeName[0]);

	private final NodeName[] pathElements;

	// private int hashCode;

	// ------------------------------------------------------------------------

	/** absolute pathes should be obtained from NodeNameEncoder.encodePath() */
	private NodeNamesPath(NodeName[] pathElements) {
		this.pathElements = pathElements;
	}

	public static NodeNamesPath of(NodeName... pathElements) {
		// private copy for ensuring immutability
		val len = pathElements.length;
		val copy = new NodeName[len];
		System.arraycopy(pathElements, 0, copy, 0, len);
		return new NodeNamesPath(copy);
	}

	public NodeNamesPath toChild(NodeName childName) {
		val len = size(); 
		val res = new NodeName[len + 1];
		System.arraycopy(pathElements, 0, res, 0, len);
		res[len] = childName;
		return new NodeNamesPath(res);
	}

	public boolean startsWith(NodeName name) {
		if (size() < 1) {
			return false;
		}
		return pathElements[0].equals(name);
	}

	/**
	 * @param start len to prune
	 * @return pruned path, example "a/b/c"  pruneStartPath(1) -> "b/c"
	 */
	public NodeNamesPath pruneStartPath(int start) {
		val len = size(); 
		val res = new NodeName[len - start];
		System.arraycopy(pathElements, start, res, 0, len-start);
		return new NodeNamesPath(res);
	}

	public NodeNamesPath subPath(int len) {
		val res = new NodeName[len];
		System.arraycopy(pathElements, 0, res, 0, len);
		return new NodeNamesPath(res);
	}

	public NodeNamesPath toParent() {
		val len = size();
		if (len == 0) {
			// error?
			return this;
		}
		val res = new NodeName[len - 1];
		System.arraycopy(pathElements, 0, res, 0, len - 1);
		return new NodeNamesPath(res);
	}

	public static NodeNamesPath commonPathOf(NodeNamesPath left, NodeNamesPath  right) {
		val leftPathElements = left.pathElements;
		val rightPathElements = right.pathElements;
		val maxLen = Math.min(leftPathElements.length, rightPathElements.length);
		int i = 0;
		for(; i < maxLen; i++) {
			if (! leftPathElements[i].equals(rightPathElements[i])) {
				break;
			}
		}
		return left.subPath(i);
	}

	// ------------------------------------------------------------------------

	public int size() {
		return size();
	}

	public NodeName get(int i) {
		return pathElements[i];
	}

	public NodeName last() {
		return pathElements[size()-1];
	}

	public NodeName lastOrEmpty() {
		if (size() == 0) return NodeName.EMPTY;
		return pathElements[size()-1];
	}

	public String toPathSlash() {
		val len = size();
		StringBuilder sb = new StringBuilder(50 + 20 * len); // may iterate to compute exact len?
		for(int i = 0; i < len; i++) {
			pathElements[i].appendTo(sb);
			if (i + 1 < len) {
				sb.append('/');
			}
		}
		return sb.toString();
	}

	public String[] toTexts() {
		val len = size();
		val res = new String[len];
		for(int i = 0; i < len; i++) {
			res[i] = pathElements[i].toText();
		}
		return res;
	}

	// ------------------------------------------------------------------------

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(pathElements);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeNamesPath other = (NodeNamesPath) obj;
		return Arrays.equals(pathElements, other.pathElements);
	}

	@Override
	public String toString() {
		return toPathSlash();
	}

}
