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
package net.rptools.parser.functions;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.rptools.lib.permissions.PermissionLevel;
import net.rptools.parser.functions.javascript.JavaScriptFunction;
import net.rptools.parser.functions.javascript.JavaScripEvaluator;

/**
 * Manages the list of function and the permissions required to call the
 * functions defined for the scripting engine.
 *
 */
public class FunctionManager {


    private static final FunctionManager INSTANCE = new FunctionManager();

	/** The list of built in functions. */
	private final Map<String, ScriptFunction> builtinFunctions = new TreeMap<>();
	
	/** The list of permissions required to run the function. */
	private final Map<ScriptFunction, PermissionLevel> builtinFunctionPermissions = new HashMap<>();
	
	/** The list of built in functions. */
	private final Map<String, ScriptFunction> userFunctions = new TreeMap<>();
	
	/** The list of permissions required to run the function. */
	private final Map<ScriptFunction, PermissionLevel> userFunctionPermissions = new HashMap<>();


    /**
     * Returns an instance of FunctionManager.
     *
     * @return the instance of FunctionManager.
     */
    public static FunctionManager getInstance() {
        return INSTANCE;
    }

    /**
     * Creates a new FunctionManager instance.
     */
	private FunctionManager() {
        for (ScriptFunction bif : BuiltInFunctionList.getInstance().getBuiltInFunctions()) {
            builtinFunctions.put(bif.getDefinition().name(), bif);
            builtinFunctionPermissions.put(bif, bif.getDefinition().defaultRequiredPermissionLevel());
        }
    }
	

	
	/**
	 * Return the built in script function with the specified name.
	 * 
	 * @param functionName The name of the function.
	 * 
	 * @return the script function.
	 * 
	 * @throws NullPointerException if functionName is null.
	 */
	public ScriptFunction getBuiltinFunction(String functionName) {
		if (functionName == null) {
			throw new NullPointerException("Function name is null");
		}
		return builtinFunctions.get(functionName);
	}
	
	/**
	 * Return the user defined script function with the specified name.
	 * 
	 * @param functionName The name of the function.
	 * 
	 * @return the script function.
	 * 
	 * @throws NullPointerException if functionName is null.
	 */
	public ScriptFunction getUserFunction(String functionName) {
		if (functionName == null) {
			throw new NullPointerException("Function name is null");
		}
		return userFunctions.get(functionName);
	}
	
	
	/**
	 * Checks to see if the built in function is defined.
	 * 
	 * @param name The name of the function.
	 * 
	 * @return true of the built in function is defined.
	 * 
	 * @throws NullPointerException if name is null.
	 */
	public boolean containsBuiltinFunction(String name) {
		return builtinFunctions.containsKey(name);
	}
	
	/**
	 * Checks to see if the user defined function is defined.
	 * 
	 * @param name The name of the function.
	 * 
	 * @return true of the user defined function is defined.
	 * 
	 * @throws NullPointerException if name is null.
	 */	
	public boolean containsUserFunction(String name) {
		return userFunctions.containsKey(name);
	}
	
	/**
	 * Checks to see if the function is defined as a built in function.
	 * 
	 * @param function The function to check.
	 * 
	 * @return true if the function is defined as a built in function.
	 */
	public boolean isBuiltinFunction(ScriptFunction function) {
		return builtinFunctionPermissions.containsKey(function);
	}
	
	/**
	 * Returns the function permissions required to call the function.
	 * 
	 * @param function The function to get the permissions for.
	 * 
	 * @return the permissions required.
	 * 
	 * @throws NullPointerException if function is null.
	 */
	public PermissionLevel getFunctionPermission(ScriptFunction function) {
		if (function == null) {
			throw new NullPointerException("Function is null.");
		}
	
		if (userFunctionPermissions.containsKey(function)) {
			return userFunctionPermissions.get(function);
		} else {
			return builtinFunctionPermissions.get(function);
		}
	}
	
	/**
	 * Defines the user defined function.
	 * 
	 * @param function the function to define.
	 * 
	 * @throws NullPointerException if function is null.
	 * 
	 * @throws IllegalArgumentException if the function exists as built in function
	 *         or a function with the same name exists as a user defined function.
	 */
	public void definFunction(ScriptFunction function) {
		if (function == null) {
			throw new NullPointerException("Function is null.");
		}
		
		if (builtinFunctionPermissions.containsKey(function) || 
				userFunctions.containsKey(function.getDefinition().name())) {
			throw new IllegalArgumentException("Function has already been defined.");
		}
		
		userFunctions.put(function.getDefinition().name(), function);
		userFunctionPermissions.put(function, function.getDefinition().defaultRequiredPermissionLevel());
	}
	
	/**
	 * Removes the function from the list of user defined functions.
	 * 
	 * @param function the function to remove.
	 * 
	 * @throws NullPointerException if the function is null.
	 */
	public void undefineFunction(ScriptFunction function) {
		if (function == null) {
			throw new NullPointerException("Function is null.");
		}
	
		if (userFunctionPermissions.containsKey(function)) {
			userFunctionPermissions.remove(function);
			userFunctions.remove(function.getDefinition().name());
		}
	}
	
	/**
	 * Removes all the user defined functions.
	 */
	public void undefineAll() {
		userFunctionPermissions.clear();
		userFunctions.clear();
	}
	
	/**
	 * Returns the built in functions that are defined.
	 * 
	 * @return the built in functions.
	 */
	public Collection<ScriptFunction> getBuiltinFunctions() {
		return Collections.unmodifiableCollection(builtinFunctions.values());
	}
	
	/**
	 * Returns the user defined functions.
	 * 
	 * @return the user defined functions.
	 */
	public Collection<ScriptFunction> getUserDefinedFunctions() {
		return Collections.unmodifiableCollection(userFunctions.values());
	}


}
