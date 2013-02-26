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
 * Script tree node to perform assignment to a variable.
 */
class AssignVariableNode implements ScriptTreeNode {

	/** The node for the child expression. */
	private final ScriptTreeNode child;
	
	/** The variable name to set. */
	private final String variableName;
	
	
	/**
	 * Creates a new AddignVariableNode. 
	 * 
	 * @param variableName The name of the variable to set.
	 * @param child the child node of the expression.
	 */
	public AssignVariableNode(String variableName, ScriptTreeNode child) {
		assert variableName != null : "Variable name can not be null";
		assert child != null : "Child node can not be null";

		this.child = child;
		this.variableName = variableName;
	}
	
	@Override
	public DataValue evaluate(ScriptContext context) throws ExpressionEvaluatorException {
		DataValue val = child.evaluate(context);

		// TODO: snapshots
		context.getSymbolTable().setVariable(variableName, val);
		
		return val;
	}

	
}
