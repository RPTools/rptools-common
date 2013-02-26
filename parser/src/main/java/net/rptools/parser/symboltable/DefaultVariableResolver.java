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
package net.rptools.parser.symboltable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import net.rptools.lib.datavalue.DataValue;

/**
 * DefaultVariableResolver provides a simple variable resolver that allows
 * the script to set and lookup properties but does not persist or map
 * the variables to any other object.
 * 
 */
class DefaultVariableResolver implements VariableResolver {

	/** The variables. */
	private final Map<String, DataValue> variables = new LinkedHashMap<>();

	@Override
	public void setVariable(String name, DataValue value) {
		if (name == null) {
			throw new NullPointerException("Variable name can not be null.");
		}

		variables.put(name, value);
	}

	@Override
	public DataValue getVariable(String name) {
		if (name == null) {
			throw new NullPointerException("Variable name can not be null.");
		}

		return variables.get(name);
	}

	@Override
	public boolean containsVariable(String name) {
		if (name == null) {
			throw new NullPointerException("Variable name can not be null.");
		}

		return variables.containsKey(name);
	}

	@Override
	public Collection<String> getVariableName() {
		return Collections.unmodifiableCollection(variables.keySet());
	}



}
