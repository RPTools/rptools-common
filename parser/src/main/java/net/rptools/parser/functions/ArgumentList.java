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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rptools.lib.datavalue.DataValue;

/**
 * Represents the list of arguments passed to a scripting function.
 *
 */
public final class ArgumentList {
	/** The list of positional arguments to be passed. */
	private final List<DataValue> positionalArguments;
	/** The list of named arguments to be passed. */
	private final Map<String, DataValue> namedArguments;
	
	/**
	 * Creates a new ArgumentList object.
	 * 
	 * @param positional The positional arguments in the argument list.
	 * @param named The named arguments in the argument list.
	 */
	public ArgumentList(List<DataValue> positional, Map<String, DataValue> named) {
		if (positional != null) {
			List<DataValue> pargs = new ArrayList<>(positional.size());
			pargs.addAll(positional);
			positionalArguments = Collections.unmodifiableList(pargs);
		} else {
			positionalArguments = Collections.emptyList();
		}
		

		if (named != null) {
			Map<String, DataValue> nargs = new HashMap<>();
			nargs.putAll(named);
			namedArguments = Collections.unmodifiableMap(nargs);
		} else {
			namedArguments = Collections.emptyMap();
		}
	}
	
	/**
	 * Gets the position arguments in the argument list.
	 * 
	 * @return the positional arguments.
	 */
	public List<DataValue> getPositionalArguments() {
		return positionalArguments;
	}
	
	/**
	 * Gets the named arguments in the argument list.
	 * 
	 * @return the named arguments.
	 */
	public Map<String, DataValue> getNamedArguments() {
		return namedArguments;
	}
	
	
}
