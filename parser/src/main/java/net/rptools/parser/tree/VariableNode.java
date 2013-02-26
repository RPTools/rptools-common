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

import net.rptools.lib.datavalue.DataValue;
import net.rptools.parser.ScriptContext;

/**
 * Script tree node that represents variable lookup.
 */
class VariableNode implements ScriptTreeNode {
	
	/** The name of the variable. */
	private final String variableName;
	
	/**
	 * Creates a new VariableNode. 
	 * 
	 * @param name The name of the variable.
	 */
	public VariableNode(String name) {
		assert name != null : "Variable name can not be null.";
		assert name.length() > 0 : "Variable name can not be zero length.";

		variableName = name;
	}

	@Override
	public DataValue evaluate(ScriptContext context) {
		// TODO: need to deal with snapshots?
		return context.getSymbolTable().getVariable(variableName);
	}

}
