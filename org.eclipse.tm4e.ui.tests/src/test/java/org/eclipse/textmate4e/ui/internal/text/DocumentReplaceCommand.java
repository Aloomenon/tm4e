package org.eclipse.textmate4e.ui.internal.text;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;

public class DocumentReplaceCommand extends Command {

	private final int pos;
	private final int length;
	private final String text;
	private final Document document;

	public DocumentReplaceCommand(int pos, int length, String text, Document document) {
		super(getName(pos, length, text));
		this.pos = pos;
		this.length = length;
		this.text = text;
		this.document = document;
	}

	public static String getName(int pos, int length, String text) {
		return "document.replace(" + pos + ", " + length + ", \"" + toText(text) + "\");";
	}

	@Override
	protected void doExecute() {
		try {
			document.replace(pos, length, text);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Integer getLineTo() {
		try {
			return document.getLineOfOffset(pos + length);
		} catch (BadLocationException e) {
			return null;
		}
	}

}