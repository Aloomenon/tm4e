package org.eclipse.tm4e.ui;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.tm4e.core.grammar.IGrammar;
import org.eclipse.tm4e.registry.TMEclipseRegistryPlugin;
import org.junit.Assert;
import org.junit.Test;

public class RegistryTest {

	@Test
	public void testGrammarRegistered() {
		IContentType contentType = Platform.getContentTypeManager().getContentType("org.eclipse.tm4e.ui.tests.testContentType");
		IGrammar grammar = TMEclipseRegistryPlugin.getGrammarRegistryManager().getGrammarFor(new IContentType[] { contentType });
		Assert.assertNotNull(grammar);
	}
}
