package org.eclipse.textmate4e.core.internal.grammars;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.textmate4e.core.grammar.IGrammar;
import org.eclipse.textmate4e.core.registry.Registry;

public class GrammarRegistry extends Registry {

	private Map<String, GrammarInfo> infos;

	public GrammarRegistry() {
		this.infos = new HashMap<>();
	}

	public void register(GrammarInfo info) {
		infos.put(info.getScopeName(), info);
	}

	@Override
	public IGrammar grammarForScopeName(String scopeName) {
		IGrammar grammar = super.grammarForScopeName(scopeName);
		if (grammar != null) {
			return grammar;
		}
		GrammarInfo info = infos.get(scopeName);
		if (info != null) {
			try {
				return super.loadGrammarFromPathSync(info.getPath(), info.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

}
