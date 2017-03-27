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
package org.eclipse.tm4e.registry.internal;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.IConfigurationElement;

public class GrammarDefinition {

	private static final String PLATFORM_PLUGIN = "platform:/plugin/"; //$NON-NLS-1$
	
	private String path;
	private String scopeName;
	private String pluginId;

	public GrammarDefinition(IConfigurationElement element) {
		this.path = element.getAttribute("path");
		this.scopeName = element.getAttribute("scopeName");
		this.pluginId = element.getNamespaceIdentifier();
	}

	public String getScopeName() {
		return scopeName;
	}

	public String getPath() {
		return path;
	}

	public InputStream getInputStream() throws IOException {
		if (path != null && path.length() > 0) {
			URL url = new URL(
					new StringBuilder(PLATFORM_PLUGIN).append(pluginId).append("/").append(path).toString());
			return url.openStream();
		}
		return null;
	}

}
