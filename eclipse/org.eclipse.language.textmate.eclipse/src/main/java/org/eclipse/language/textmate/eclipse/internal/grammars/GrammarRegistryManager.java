/**
 *  Copyright (c) 2015-2016 Angelo ZERR.
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors:
 *  Angelo Zerr <angelo.zerr@gmail.com> - initial API and implementation
 */
package org.eclipse.language.textmate.eclipse.internal.grammars;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.language.textmate.core.grammar.IGrammar;
import org.eclipse.language.textmate.eclipse.TMPlugin;
import org.eclipse.language.textmate.eclipse.grammars.IGrammarRegistryManager;

/**
 * 
 * TextMate Grammar registry manager implementation.
 *
 */
public class GrammarRegistryManager implements IGrammarRegistryManager, IRegistryChangeListener {

	private static final String EXTENSION_GRAMMARS = "grammars";

	private static final GrammarRegistryManager INSTANCE = new GrammarRegistryManager();

	public static GrammarRegistryManager getInstance() {
		return INSTANCE;
	}

	private boolean registryListenerIntialized;

	private GrammarRegistry registry;
	private Map<String, String> scopeNameBindings;

	public GrammarRegistryManager() {
		this.registryListenerIntialized = false;
		this.scopeNameBindings = new HashMap<>();
	}

	@Override
	public IGrammar getGrammarFor(IContentType contentType) {
		loadGrammarsIfNeeded();
		// Find grammar by content type
		String scopeName = getScopeName(contentType);
		return registry.grammarForScopeName(scopeName);
	}

	private String getScopeName(IContentType contentType) {
		return scopeNameBindings.get(contentType.getId());
	}

	@Override
	public void registryChanged(final IRegistryChangeEvent event) {
		IExtensionDelta[] deltas = event.getExtensionDeltas(TMPlugin.PLUGIN_ID, EXTENSION_GRAMMARS);
		if (deltas != null) {
			for (IExtensionDelta delta : deltas)
				handleGrammarDelta(delta);
		}
	}

	private void handleGrammarDelta(IExtensionDelta delta) {
		// TODO Auto-generated method stub

	}

	public IGrammar registerGrammar(InputStream in) {
		return null;
	}

	public IGrammar registerGrammar(IPath grammarPath) {
		return null;
	}

	public void initialize() {

	}

	public void destroy() {
		Platform.getExtensionRegistry().removeRegistryChangeListener(this);
	}

	/**
	 * Load the grammar.
	 */
	private void loadGrammarsIfNeeded() {
		if (registry != null) {
			return;
		}
		loadGrammars();
	}

	private synchronized void loadGrammars() {
		if (registry != null) {
			return;
		}
		IConfigurationElement[] cf = Platform.getExtensionRegistry().getConfigurationElementsFor(TMPlugin.PLUGIN_ID,
				EXTENSION_GRAMMARS);
		GrammarRegistry registry = new GrammarRegistry();
		loadGrammars(cf, registry);
		addRegistryListener();
		this.registry = registry;
	}

	private void addRegistryListener() {
		if (registryListenerIntialized)
			return;

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.addRegistryChangeListener(this, TMPlugin.PLUGIN_ID);
		registryListenerIntialized = true;
	}

	/**
	 * Load TextMate grammars declared from th extension point.
	 */
	private void loadGrammars(IConfigurationElement[] cf, GrammarRegistry cache) {
		for (IConfigurationElement ce : cf) {
			String name = ce.getName();
			if ("grammar".equals(name)) {
				cache.register(new GrammarInfo(ce));
			} else if ("scopeNameContentTypeBinding".equals(name)) {
				String contentTypeId = ce.getAttribute("contentTypeId");
				String scopeName = ce.getAttribute("scopeName");
				scopeNameBindings.put(contentTypeId, scopeName);
			}
		}
	}
}
