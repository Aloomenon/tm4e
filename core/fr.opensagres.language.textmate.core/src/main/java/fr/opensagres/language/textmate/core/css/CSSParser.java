/*******************************************************************************
 * Copyright (c) 2005, 2016 Angelo Zerr and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 *******************************************************************************/
package fr.opensagres.language.textmate.core.css;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.List;

import org.w3c.css.sac.CSSException;
import org.w3c.css.sac.InputSource;
import org.w3c.css.sac.Parser;
import org.w3c.css.sac.Selector;
import org.w3c.css.sac.SelectorList;

import fr.opensagres.language.textmate.core.internal.css.CSSConditionFactory;
import fr.opensagres.language.textmate.core.internal.css.CSSDocumentHandler;
import fr.opensagres.language.textmate.core.internal.css.CSSSelectorFactory;
import fr.opensagres.language.textmate.core.internal.css.ExtendedSelector;

/**
 * CSS Parser to parse style for TextMate syntax coloration.
 *
 */
public class CSSParser {

	private final CSSDocumentHandler handler;

	public CSSParser(InputStream source) throws Exception {
		this(toSource(source));
	}

	private static InputSource toSource(InputStream source) {
		InputSource in = new InputSource();
		in.setByteStream(source);
		return in;
	}

	public CSSParser(InputSource source) throws Exception {
		this(source, SACParserFactory.newInstance().makeParser());
	}

	public CSSParser(String source) throws Exception {
		this(new InputSource(new StringReader(source)));
	}

	public CSSParser(InputSource source, Parser parser) throws CSSException, IOException {
		this.handler = new CSSDocumentHandler();
		parser.setDocumentHandler(handler);
		parser.setConditionFactory(CSSConditionFactory.INSTANCE);
		parser.setSelectorFactory(CSSSelectorFactory.INSTANCE);
		parser.parseStyleSheet(source);
	}

	public CSSStyle getBestStyle(List<String> names) {
		int bestSpecificity = 0;
		CSSStyle bestStyle = null;
		for (CSSStyle style : handler.getList()) {
			SelectorList list = style.getSelectorList();
			for (int i = 0; i < list.getLength(); i++) {
				Selector selector = list.item(i);
				if (selector instanceof ExtendedSelector) {
					ExtendedSelector s = ((ExtendedSelector) selector);
					if (s.match(names)) {
						int specificity = s.getSpecificity();
						if (bestStyle == null || (specificity > bestSpecificity)) {
							bestStyle = style;
							bestSpecificity = specificity;
						}
					}

				} else {
					System.err.println("argh");
				}
			}

		}

		return bestStyle;
	}

	public List<CSSStyle> getStyles() {
		return handler.getList();
	}

}
