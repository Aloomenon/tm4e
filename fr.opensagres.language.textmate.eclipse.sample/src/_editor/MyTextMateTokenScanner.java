package _editor;

import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

import _editor.editors.ColorManager;
import _editor.editors.IXMLColorConstants;

public class MyTextMateTokenScanner extends TextMateTokenScanner {

	private ColorManager manager;

	public MyTextMateTokenScanner(ColorManager colorManager) {
		this.manager = colorManager;
	}

	@Override
	protected IToken createToken(fr.opensagres.language.textmate.grammar.IToken tt) {
		//System.err.println(tt.getScopes());
		if (tt.getScopes().contains("storage.type.function.js") || tt.getScopes().contains("storage.type.js")) {
			return new Token(new TextAttribute(manager.getColor(IXMLColorConstants.KEY_WORD)));
		} else if (tt.getScopes().contains("comment.block.documentation")) {
			return new Token(new TextAttribute(manager.getColor(IXMLColorConstants.STRING)));
		}
		return new Token(new TextAttribute(manager.getColor(IXMLColorConstants.DEFAULT)));
	}

}
