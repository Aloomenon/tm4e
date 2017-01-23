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
package org.eclipse.tm4e.core.grammar;

import org.eclipse.tm4e.core.internal.grammar.Grammar;
import org.eclipse.tm4e.core.internal.oniguruma.OnigString;
import org.eclipse.tm4e.core.internal.types.IRawGrammar;

public class GrammarHelper {

	public static IGrammar createGrammar(IRawGrammar rawGrammar, IGrammarRepository repository) {
		return new Grammar(rawGrammar, repository);
	}

	public static OnigString createOnigString(String str) {
		return new OnigString(str);
	}

}
