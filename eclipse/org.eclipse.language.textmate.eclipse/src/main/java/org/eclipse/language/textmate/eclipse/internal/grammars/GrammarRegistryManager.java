package org.eclipse.language.textmate.eclipse.internal.grammars;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionDelta;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IRegistryChangeEvent;
import org.eclipse.core.runtime.IRegistryChangeListener;
import org.eclipse.core.runtime.Platform;
import org.eclipse.language.textmate.core.grammar.IGrammar;
import org.eclipse.language.textmate.eclipse.TMPlugin;
import org.eclipse.language.textmate.eclipse.grammars.IGrammarProvider;
import org.eclipse.language.textmate.eclipse.grammars.IGrammarRegistryManager;

public class GrammarRegistryManager implements IGrammarRegistryManager, IRegistryChangeListener {

	private static final String EXTENSION_GRAMMARS = "grammars";

	private static final GrammarRegistryManager INSTANCE = new GrammarRegistryManager();

	public static GrammarRegistryManager getInstance() {
		return INSTANCE;
	}

	private boolean registryListenerIntialized;

	private List<IGrammarProvider> providers;

	private GrammarRegistry registry;
	private Map<String, String> scopeNameBindings;

	public GrammarRegistryManager() {
		this.registryListenerIntialized = false;
		this.scopeNameBindings = new HashMap<>();
	}

	@Override
	public IGrammar getGrammarFor(IFile file) throws CoreException {
		loadGrammarsIfNeeded();
		if (providers != null) {
			IGrammar grammar = null;
			for (IGrammarProvider provider : providers) {
				// grammar = provider.getGrammarFor(resource);
				// if (grammar != null) {
				// return grammar;
				// }
			}
		}
		// Find grammar by content type
		String scopeName = getScopeName(file);
		return registry.grammarForScopeName(scopeName);
	}

	private String getScopeName(IFile file) throws CoreException {
		String contentType = file.getContentDescription().getContentType().getId();
		return scopeNameBindings.get(contentType);
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
