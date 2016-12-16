package org.eclipse.tm4e.core.internal.rule;

public class CaptureRule extends Rule {

	public final Integer retokenizeCapturedWithRuleId;

	public CaptureRule(int id, String name, String contentName, Integer retokenizeCapturedWithRuleId) {
		super(id, name, contentName);
		this.retokenizeCapturedWithRuleId = retokenizeCapturedWithRuleId;
	}

}
