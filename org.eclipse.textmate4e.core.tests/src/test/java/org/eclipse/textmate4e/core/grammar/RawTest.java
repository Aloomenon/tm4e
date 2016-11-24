package org.eclipse.textmate4e.core.grammar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.textmate4e.core.registry.IGrammarLocator;
import org.eclipse.textmate4e.core.registry.Registry;
import org.junit.runner.Describable;
import org.junit.runner.Description;

import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestResult;

public class RawTest implements Test, Describable {

	private String desc;
	private List<String> grammars;
	private String grammarPath;
	private String grammarScopeName;
	private List<String> grammarInjections;
	private List<RawTestLine> lines;
	private File testLocation;

	public String getDesc() {
		return desc;
	}

	public List<String> getGrammars() {
		return grammars;
	}

	public String getGrammarPath() {
		return grammarPath;
	}

	public String getGrammarScopeName() {
		return grammarScopeName;
	}

	public List<String> getGrammarInjections() {
		return grammarInjections;
	}

	public List<RawTestLine> getLines() {
		return lines;
	}

	@Override
	public void run(TestResult result) {
		try {
			result.startTest(this);
			executeTest(this, testLocation);
		} catch (Throwable e) {
			result.addError(this, e);
		} finally {
			result.endTest(this);
		}
	}

	@Override
	public int countTestCases() {
		return 1;
	}

	private static void executeTest(RawTest test, File testLocation) throws Exception {
		IGrammarLocator locator = new IGrammarLocator() {

			@Override
			public String getFilePath(String scopeName) {
				return null;
			}

			/*
			 * getInjections: (scopeName:string) => { if (scopeName ===
			 * test.grammarScopeName) { return test.grammarInjections; } return
			 * void 0; }
			 */

		};
		Registry registry = new Registry(locator);
		IGrammar grammar = getGrammar(test, registry, testLocation.getParentFile());

		if (test.getGrammarScopeName() != null) {
			grammar = registry.grammarForScopeName(test.getGrammarScopeName());
		}

		if (grammar == null) {
			throw new Exception("I HAVE NO GRAMMAR FOR TEST");
		}

		StackElement prevState = null;
		for (RawTestLine testLine : test.getLines()) {
			prevState = assertLineTokenization(grammar, testLine, prevState);
		}
	}

	private static IGrammar getGrammar(RawTest test, Registry registry, File testLocation) throws Exception {
		for (String grammarPath : test.getGrammars()) {
			IGrammar tmpGrammar = registry.loadGrammarFromPathSync(new File(testLocation, grammarPath));
			if (grammarPath.equals(test.getGrammarPath())) {
				return tmpGrammar;
			}
		}
		return null;
	}

	private static StackElement assertLineTokenization(IGrammar grammar, RawTestLine testCase, StackElement prevState) {
		ITokenizeLineResult actual = grammar.tokenizeLine(testCase.getLine(), prevState);

		List<RawToken> actualTokens = getActualTokens(actual.getTokens(), testCase);

		// let actualTokens:IRawToken[] = actual.tokens.map((token) => {
		// return {
		// value: testCase.line.substring(token.startIndex, token.endIndex),
		// scopes: token.scopes
		// };
		// });
		//
		// // TODO@Alex: fix tests instead of working around
		// if (testCase.line.length > 0) {
		// // Remove empty tokens...
		// testCase.tokens = testCase.tokens.filter((token) => {
		// return (token.value.length > 0);
		// });
		// }
		//
		// assert.deepEqual(actualTokens, testCase.tokens, 'Tokenizing line ' +
		// testCase.line);

		deepEqual(actualTokens, testCase.getTokens(), "Tokenizing line " + testCase.getLine());

		return actual.getRuleStack();
	}

	private static void deepEqual(List<RawToken> actualTokens, List<RawToken> expextedTokens, String message) {
		Assert.assertEquals(message, expextedTokens, actualTokens);

	}

	private static List<RawToken> getActualTokens(IToken[] tokens, RawTestLine testCase) {
		List<RawToken> actualTokens = new ArrayList<>();
		for (int i = 0; i < tokens.length; i++) {
			IToken token = tokens[i];
			String value = testCase.getLine().substring(token.getStartIndex(), token.getEndIndex());
			actualTokens.add(new RawToken(value, token.getScopes()));
		}
		return actualTokens;
	}

	public void setTestLocation(File testLocation) {
		this.testLocation = testLocation;
	}

	@Override
	public Description getDescription() {
		return Description.createSuiteDescription(desc);
	}
}
