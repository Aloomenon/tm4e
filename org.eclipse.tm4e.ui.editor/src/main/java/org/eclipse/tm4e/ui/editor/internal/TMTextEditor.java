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
package org.eclipse.tm4e.ui.editor.internal;

import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.tm4e.ui.text.TMPresentationReconciler;
import org.eclipse.ui.editors.text.TextEditor;

/**
 * Basic TextMate TextEditor to consume the {@link TMPresentationReconciler}.
 *
 */
public class TMTextEditor extends TextEditor {

	public TMTextEditor() {
		setSourceViewerConfiguration(new SourceViewerConfiguration() {
			@Override
			public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
				return new TMPresentationReconciler();
			}
		});
	}
}
