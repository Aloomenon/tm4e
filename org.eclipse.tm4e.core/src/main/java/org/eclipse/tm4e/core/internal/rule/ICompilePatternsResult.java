package org.eclipse.tm4e.core.internal.rule;

import java.util.Collection;

public class ICompilePatternsResult {

	public ICompilePatternsResult(Collection<Integer> patterns, boolean hasMissingPatterns) {
		this.hasMissingPatterns = hasMissingPatterns;
		this.patterns = patterns.toArray(new Integer[0]);
	}

	public Integer[] patterns;

	public boolean hasMissingPatterns;
}
