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
package org.eclipse.tm4e.ui.internal.themes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.tm4e.ui.themes.IThemeAssociation;

/**
 * Theme association registry.
 *
 */
public class ThemeAssociationRegistry extends BaseThemeAssociationRegistry {

	private final Map<String, BaseThemeAssociationRegistry> scopes;

	public ThemeAssociationRegistry() {
		scopes = new HashMap<>();
	}

	@Override
	public void register(IThemeAssociation association) {
		String scopeName = association.getScopeName();
		if (scopeName == null) {
			super.register(association);
		} else {
			BaseThemeAssociationRegistry registry = scopes.get(scopeName);
			if (registry == null) {
				registry = new BaseThemeAssociationRegistry();
				scopes.put(scopeName, registry);
			}
			registry.register(association);
		}
	}

	@Override
	public void unregister(IThemeAssociation association) {
		String scopeName = association.getScopeName();
		if (scopeName == null) {
			super.unregister(association);
		} else {
			BaseThemeAssociationRegistry registry = scopes.get(scopeName);
			if (registry != null) {
				registry.unregister(association);
			}
		}
	}

	public IThemeAssociation getThemeAssociationFor(String scopeName, String eclipseThemeId) {
		IThemeAssociation association = null;
		BaseThemeAssociationRegistry registry = scopes.get(scopeName);
		if (registry != null) {
			association = registry.getThemeAssociationFor(eclipseThemeId);
			if (association == null) {
				association = registry.getDefaultAssociation();
			}
		}
		if (association == null) {
			association = super.getThemeAssociationFor(eclipseThemeId);
		}
		return association != null ? association : getDefaultAssociation();
	}

	public IThemeAssociation[] getThemeAssociationsForScope(String scopeName) {
		BaseThemeAssociationRegistry registry = scopes.get(scopeName);
		if (registry != null) {
			return registry.getThemeAssociations().toArray(new IThemeAssociation[0]);
		}
		return getThemeAssociations(true);
	}
	
	@Override
	public List<IThemeAssociation> getThemeAssociations() {
		List<IThemeAssociation> allAssociations = new ArrayList<>(super.getThemeAssociations());
		for (BaseThemeAssociationRegistry registry : scopes.values()) {
			allAssociations.addAll(registry.getThemeAssociations());
		}
		return allAssociations;
	}

}
