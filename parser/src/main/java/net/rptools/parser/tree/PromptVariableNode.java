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
import net.rptools.parser.ExpressionEvaluatorException;
import net.rptools.parser.ScriptContext;

/**
 * Represents the script tree node that will ask the symbol table to prompt for a 
 * value.
 */
class PromptVariableNode implements ScriptTreeNode {
	
	/** The name of the variable to prompt for. */
	private final String variableName;
	private final String prompt;
	
	public PromptVariableNode(String name, String message) {

		variableName = name;
		prompt = message;
	}

	@Override
	public DataValue evaluate(ScriptContext context) throws ExpressionEvaluatorException {
		// TODO: need to deal with snapshots? 
		
		DataValue val;
		if (prompt == null) {
			val = context.getSymbolTable().promptForValue(variableName);
		} else {
			val = context.getSymbolTable().promptForValue(variableName == null ? "Group" : variableName, prompt);
		}
		
		if (variableName != null) { 
			context.getSymbolTable().setVariable(variableName, val);
		}
		
		return val;
	}

}
