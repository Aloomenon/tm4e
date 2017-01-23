/**
 *  Copyright (c) 2015-2017 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 * This code is an translation of code copyrighted by Microsoft Corporation, and initially licensed under MIT.
 *
 * Contributors:
 *  - Microsoft Corporation: Initial code, written in TypeScript, licensed under MIT license
 *  - Angelo Zerr <angelo.zerr@gmail.com> - translation and adaptation to Java
 */
package org.eclipse.tm4e.core.model;

import org.eclipse.tm4e.core.grammar.StackElement;

public class TMState {

	private TMState _parentEmbedderState;
	private StackElement ruleStack;

	public TMState(TMState parentEmbedderState, StackElement ruleStatck) {
		this._parentEmbedderState = parentEmbedderState;
		this.ruleStack = ruleStatck;
	}

	public void setRuleStack(StackElement ruleStack) {
		this.ruleStack = ruleStack;
	}

	public StackElement getRuleStack() {
		return ruleStack;
	}

	public TMState clone() {
		TMState parentEmbedderStateClone = this._parentEmbedderState != null ? this._parentEmbedderState.clone() : null;
		return new TMState(parentEmbedderStateClone, this.ruleStack);
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || !(other instanceof TMState)) {
			return false;
		}
		TMState otherState = (TMState) other;

		// Equals on `_parentEmbedderState`
		if (!safeEquals(this._parentEmbedderState, otherState._parentEmbedderState)) {
			return false;
		}

		// Equals on `_ruleStack`
		if (this.ruleStack == null && otherState.ruleStack == null) {
			return true;
		}
		if (this.ruleStack == null || otherState.ruleStack == null) {
			return false;
		}
		return this.ruleStack.equals(otherState.ruleStack);
	}

	public static boolean safeEquals(TMState a, TMState b) {
		if (a == null && b == null) {
			return true;
		}
		if (a == null || b == null) {
			return false;
		}
		return a.equals(b);
	}

}
