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
import java.util.Collection;
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
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.core.logger.ILogger;
import org.eclipse.tm4e.core.registry.IGrammarLocator;
import org.eclipse.tm4e.registry.EclipseSystemLogger;
import org.eclipse.tm4e.registry.IGrammarRegistryManager;
import org.eclipse.tm4e.registry.TMEclipseRegistryPlugin;

/**
 * 
 * TextMate Grammar registry manager implementation.
 *
 */
public class GrammarRegistryManager implements IGrammarRegistryManager, IRegistryChangeListener {

	private static final String EXTENSION_GRAMMARS = "grammars";

	private static final GrammarRegistryManager INSTANCE = new GrammarRegistryManager();

	private static final ILogger GRAMMAR_LOGGER = new EclipseSystemLogger(
			"org.eclipse.tm4e.registry/debug/log/Grammar");

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
	public IGrammar getGrammarFor(IContentType[] contentTypes) {
		loadGrammarsIfNeeded();
		if (contentTypes == null) {
			return null;
		}
		// Find grammar by content type
		for (IContentType contentType : contentTypes) {
			String scopeName = getScopeName(contentType.getId());
			if (scopeName != null) {
				IGrammar grammar = registry.getGrammar(scopeName);
				if (grammar != null) {
					return grammar;
				}
			}
		}
		return null;
	}

	@Override
	public IGrammar getGrammarFor(String contentTypeId) {
		loadGrammarsIfNeeded();
		// Find grammar by content type
		String scopeName = getScopeName(contentTypeId);
		if (scopeName != null) {
			IGrammar grammar = registry.getGrammar(scopeName);
			if (grammar != null) {
				return grammar;
			}
		}
		return null;
	}

	private String getScopeName(String contentTypeId) {
		return scopeNameBindings.get(contentTypeId);
	}

	@Override
	public void registryChanged(final IRegistryChangeEvent event) {
		IExtensionDelta[] deltas = event.getExtensionDeltas(TMEclipseRegistryPlugin.PLUGIN_ID, EXTENSION_GRAMMARS);
		if (deltas != null) {
			for (IExtensionDelta delta : deltas)
				handleGrammarDelta(delta);
		}
	}

	private void handleGrammarDelta(IExtensionDelta delta) {

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
		IConfigurationElement[] cf = Platform.getExtensionRegistry()
				.getConfigurationElementsFor(TMEclipseRegistryPlugin.PLUGIN_ID, EXTENSION_GRAMMARS);
		GrammarRegistry registry = new GrammarRegistry(new IGrammarLocator() {

			@Override
			public Collection<String> getInjections(String scopeName) {
				return GrammarRegistryManager.this.registry.getInjections(scopeName);
			}

			@Override
			public String getFilePath(String scopeName) {
				GrammarDefinition info = GrammarRegistryManager.this.registry.getDefinition(scopeName);
				return info != null ? info.getPath() : null;
			}

			@Override
			public InputStream getInputStream(String scopeName) throws IOException {
				GrammarDefinition info = GrammarRegistryManager.this.registry.getDefinition(scopeName);
				return info != null ? info.getInputStream() : null;
			}

		}, GRAMMAR_LOGGER);

		Map<String, String> scopeNameBindings = new HashMap<>();
		loadGrammars(cf, registry, scopeNameBindings);
		addRegistryListener();
		this.scopeNameBindings = scopeNameBindings;
		this.registry = registry;
	}

	private void addRegistryListener() {
		if (registryListenerIntialized)
			return;

		IExtensionRegistry registry = Platform.getExtensionRegistry();
		registry.addRegistryChangeListener(this, TMEclipseRegistryPlugin.PLUGIN_ID);
		registryListenerIntialized = true;
	}

	/**
	 * Load TextMate grammars declared from the extension point.
	 */
	private void loadGrammars(IConfigurationElement[] cf, GrammarRegistry cache,
			Map<String, String> scopeNameBindings) {
		for (IConfigurationElement ce : cf) {
			String name = ce.getName();
			if (XMLConstants.GRAMMAR_ELT.equals(name)) {
				cache.register(new GrammarDefinition(ce));
			} else if (XMLConstants.INJECTION_ELT.equals(name)) {
				String scopeName = ce.getAttribute(XMLConstants.SCOPE_NAME_ATTR);
				String injectTo = ce.getAttribute(XMLConstants.INJECT_TO_ATTR);
				cache.registerInjection(scopeName, injectTo);
			} else if (XMLConstants.SCOPE_NAME_CONTENT_TYPE_BINDING_ELT.equals(name)) {
				String contentTypeId = ce.getAttribute(XMLConstants.CONTENT_TYPE_ID_ATTR);
				String scopeName = ce.getAttribute(XMLConstants.SCOPE_NAME_ATTR);
				scopeNameBindings.put(contentTypeId, scopeName);
			}
		}
	}

}
