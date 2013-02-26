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
package net.rptools.parser.tree;

import java.util.ArrayList; 
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a function argument list built from the script input.
 *
 */
class FunctionArgumentList {

	/** The positional arguments in the function call. */
	private final List<ScriptFunctionArgument> positionalArguments = new ArrayList<>();
	
	/** The named arguments in the function call. */
	private final Map<String, ScriptFunctionArgument> namedArguments = new LinkedHashMap<>();
	
	
	/**
	 * Adds an argument to the argument list.
	 * 
	 * @param arg The argument to add.
	 */
	public void addArgument(ScriptFunctionArgument arg) {
		assert arg != null : "Argument can not be null";

		if (arg.isNamedParameter()) {
			namedArguments.put(arg.getName(), arg);
		} else {
			positionalArguments.add(arg);
		}
	}
	
	/**
	 * Returns the number of arguments in the list.
	 * 
	 * @return the number of arguments in the list.
	 */
	public int getArgumentCount() {
		return namedArguments.size() + positionalArguments.size();
	}
	
	/**
	 * Returns the positional arguments in the argument list.
	 * Positional arguments are those arguments that have not been named in
	 * the function call so the script execution engine must try and match
	 * them based on the position that they appear.
	 * 
	 * @return the positional arguments in the function call.
	 */
	public Collection<ScriptFunctionArgument> getPositionalArguments() {
		return Collections.unmodifiableCollection(positionalArguments);
	}
	
	/**
	 * Returns the names of the named arguments in the argument list.
	 * 
	 * @return the names of the named arguments.
	 */
	public Collection<String> getArgumentNames() {
		return Collections.unmodifiableCollection(namedArguments.keySet());
	}
	
	/**
	 * Returns the value of the named argument.
	 * 
	 * @param name The name of the argument to get the value of.
	 * 
	 * @return the value of the argument.
	 */
	public ScriptFunctionArgument getArgument(String name) {
		assert name != null : "The name of the agument to return the value of can not be null";
		return namedArguments.get(name);
	}
}
