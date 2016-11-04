package fr.opensagres.language.textmate.core.registry;

import java.util.HashMap;
import java.util.Map;

import fr.opensagres.language.textmate.core.grammar.GrammarHelper;
import fr.opensagres.language.textmate.core.grammar.IGrammar;
import fr.opensagres.language.textmate.core.grammar.IGrammarRepository;
import fr.opensagres.language.textmate.core.internal.types.IRawGrammar;

public class SyncRegistry implements IGrammarRepository {

	private final Map<String, IGrammar> _grammars;
	private final Map<String, IRawGrammar> _rawGrammars;

	public SyncRegistry() {
		this._grammars = new HashMap<String, IGrammar>();
		this._rawGrammars = new HashMap<String, IRawGrammar>();
	}

	/**
	 * Add `grammar` to registry and return a list of referenced scope names
	 */
	public String[] addGrammar(IRawGrammar grammar) {
		this._rawGrammars.put(grammar.getScopeName(), grammar);
		return GrammarHelper.extractIncludedScopes(grammar);
	}

	@Override
	public IRawGrammar lookup(String scopeName) {
		return this._rawGrammars.get(scopeName);
	}

	/**
	 * Lookup a grammar.
	 */
	public IGrammar grammarForScopeName(String scopeName) {
		if (!this._grammars.containsKey(scopeName)) {
			IRawGrammar rawGrammar = lookup(scopeName);
			if (rawGrammar == null) {
				return null;
			}
			this._grammars.put(scopeName, GrammarHelper.createGrammar(rawGrammar, this));
		}
		return this._grammars.get(scopeName);
	}

}
