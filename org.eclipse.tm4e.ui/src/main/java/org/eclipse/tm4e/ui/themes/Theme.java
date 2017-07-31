/**
 *  Copyright (c) 2015-2017 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package org.eclipse.tm4e.ui.themes;

import java.io.InputStream;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.tm4e.registry.TMResource;
import org.eclipse.tm4e.registry.XMLConstants;
import org.eclipse.tm4e.ui.themes.css.CSSTokenProvider;

/**
 * {@link ITheme} implementation.
 *
 */
public class Theme extends TMResource implements ITheme {

	private static final String DARK_ATTR = "dark";
	private static final String DEFAULT_ATTR = "default";

	private ITokenProvider tokenProvider;

	private String id;
	private String name;
	private boolean dark;
	private boolean isDefault;

	/**
	 * Constructor for user preferences (loaded from Json with Gson).
	 */
	public Theme() {
		super();
	}

	/**
	 * Constructor for extension point.
	 * 
	 * @param element
	 */
	public Theme(String id, String path, String name, boolean dark, boolean isDefault) {
		super(path);
		this.id = id;
		this.name = name;
		this.dark = dark;
		this.isDefault = isDefault;
	}

	public Theme(IConfigurationElement ce) {
		super(ce);
		id = ce.getAttribute(XMLConstants.ID_ATTR);
		name = ce.getAttribute(XMLConstants.NAME_ATTR);
		dark = "true".equals(ce.getAttribute(DARK_ATTR));
		isDefault = "true".equals(ce.getAttribute(DEFAULT_ATTR));
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public IToken getToken(String type) {
		return getTokenProvider().getToken(type);
	}

	private ITokenProvider getTokenProvider() {
		if (tokenProvider == null) {
			try {
				InputStream in = super.getInputStream();
				if (in == null) {
					return null;
				}
				tokenProvider = new CSSTokenProvider(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tokenProvider;
	}

	@Override
	public String toCSSStyleSheet() {
		return super.getResourceContent();
	}

	@Override
	public boolean isDark() {
		return dark;
	}

	@Override
	public boolean isDefault() {
		return isDefault;
	}
}
