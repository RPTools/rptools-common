/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 * 
 */
package net.rptools.parser;

import net.rptools.lib.permissions.PlayerPermissions;
import net.rptools.parser.symboltable.PropertyResolver;
import net.rptools.parser.symboltable.SymbolTableBuilder;
import net.rptools.parser.symboltable.VariableResolver;

/**
 * ScriptContextBuilder is used to build a ScriptContext for script execution.
 *
 */
public class ScriptContextBuilder {
	
	/** The player permission the script will be evaluated with. */
	private PlayerPermissions playerPermissions = PlayerPermissions.getUnspecifiedPlayerPermissions();
	
	/** The variable resolver to be used when evaluating the script. */
	private VariableResolver variableResolver;
	
	/** The property resolver to be used when evaluating the script. */
	private PropertyResolver propertyResolver;
	
	/** User data made available to script functions via an API. */
	private Object userData;
	
	/** Should the script be evaluated in debug mode. */
	private boolean debug = false;
	
	
	/**
	 * Sets the player permissions for the script context.
	 * 
	 * @param perms The player permissions to evaluate the script with.
	 * 
	 * @return {@code this} so that methods can be chained.
	 */
	public ScriptContextBuilder setPermissions(PlayerPermissions perms) {
		playerPermissions = perms;
		return this;
	}
	
	/**
	 * Sets the variable resolver for the script execution.
	 * 
	 * @param resolver The variable resolver to be used.
	 * 
	 * @return {@code this} so methods can be chained.
	 * 
	 * @throws NullPointerException if resolver is null.
	 */
	public ScriptContextBuilder setVariableResolver(VariableResolver resolver) {
		if (resolver == null) {
			throw new NullPointerException("Variable resolver can not be null.");
		}
		variableResolver = resolver;
		return this;
	}
	
	/**
	 * Sets the property resolver for script execution.
	 * 
	 * @param resolver The property resolver to use.
	 * 
	 * @return {@code this} so modules can be chained.
	 * 
	 * @throws NullPointerException if resolver is null.
	 */
	public ScriptContextBuilder setPropertyResolver(PropertyResolver resolver) {
		if (resolver == null) {
			throw new NullPointerException("Property resolver can not be null.");
		}
		
		propertyResolver = resolver;
		return this;
	}
	
	
	/**
	 * Sets the user data available to script functions via API.
	 * 
	 * @param ud The user data to set.
	 * 
	 * @return {@code this} so methods can be chained.
	 * 
	 * @throws NullPointerException if ud is null.
	 */
	public ScriptContextBuilder setUserData(Object ud) {
		if (ud == null) {
			throw new NullPointerException("User Defined value can not be null.");
		}
		
		userData = ud;
		return this;
	}
	
	/**
	 * Sets the debug flag for script evaluation.
	 * 
	 * @param dbg The value to set debug flag to.
	 * 
	 * @return {@code this} so that methods can be chained.
	 * 
	 */
	public ScriptContextBuilder setDebug(boolean dbg) {
		debug = dbg;
		return this;
	}
	
	
	/**
	 * Returns a ScriptContext built from this object.
	 * 
	 * @return the ScriptContext.
	 */
	public ScriptContext toScriptContext() {
		SymbolTableBuilder symTableBuilder = new SymbolTableBuilder();
		if (propertyResolver != null) {
			symTableBuilder.setPropertyResolver(propertyResolver);
		}
		
		if (variableResolver != null) {
			symTableBuilder.setVariableResolver(variableResolver);
		}
		
		return new ScriptContext(playerPermissions, symTableBuilder.toSymbolTable(), userData, debug);
	}
	
	
}
