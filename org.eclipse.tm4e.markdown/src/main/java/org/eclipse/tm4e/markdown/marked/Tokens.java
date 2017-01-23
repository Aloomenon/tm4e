/**
 *  Copyright (c) 2015-2017 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 * This code is an translation of code copyrighted by https://github.com/chjj/marked, and initially licensed under MIT.
 *
 * Contributors:
 *  - https://github.com/chjj/marked: Initial code, written in JavaScript, licensed under MIT license
 *  - Angelo Zerr <angelo.zerr@gmail.com> - translation and adaptation to Java
 */
package org.eclipse.tm4e.markdown.marked;

import java.util.ArrayList;
import java.util.Collections;

public class Tokens extends ArrayList<Token> {

	public Object links;

	public Tokens reverse() {
		Collections.reverse(this);
		return this;
	}

	public Token pop() {
		if (super.isEmpty()) {
			return null;
		}
		return super.remove(super.size() - 1);
	}

}
