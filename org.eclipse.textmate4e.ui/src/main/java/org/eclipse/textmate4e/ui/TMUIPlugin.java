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
package org.eclipse.textmate4e.ui;

import org.eclipse.textmate4e.ui.internal.model.TMModelManager;
import org.eclipse.textmate4e.ui.internal.themes.ThemeManager;
import org.eclipse.textmate4e.ui.model.ITMModelManager;
import org.eclipse.textmate4e.ui.themes.IThemeManager;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class TMUIPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.textmate4e.ui"; //$NON-NLS-1$

	// The shared instance
	private static TMUIPlugin plugin;

	/**
	 * The constructor
	 */
	public TMUIPlugin() {
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		ThemeManager.getInstance().initialize();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		ThemeManager.getInstance().destroy();
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static TMUIPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns the TextMate model manager.
	 * 
	 * @return the TextMate model manager.
	 */
	public static ITMModelManager getTMModelManager() {
		return TMModelManager.getInstance();
	}

	/**
	 * Returns the TextMate themes manager.
	 * 
	 * @return the TextMate themes manager.
	 */
	public static IThemeManager getThemeManager() {
		return ThemeManager.getInstance();
	}

}
