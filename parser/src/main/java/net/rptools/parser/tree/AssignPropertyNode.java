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
 * Script tree node to perform assignment to a property.
 */
class AssignPropertyNode implements ScriptTreeNode {

	/** The node of the expression. */
	private final ScriptTreeNode child;
	
	/** The name of the property. */
	private final String propertyName;
	
	
	/**
	 * Creates a new AsiggnPropertyNode.
	 * 
	 * @param propertyName The name of the property to assign.
	 * @param child The node of the expression.
	 */
	public AssignPropertyNode(String propertyName, ScriptTreeNode child) {
		assert propertyName != null : "Property name can not be null";
		assert child != null : "Child node can not be null";

		this.child = child;
		this.propertyName = propertyName;
	}
	
	@Override
	public DataValue evaluate(ScriptContext context) throws ExpressionEvaluatorException {
		final DataValue val = child.evaluate(context);

		// TODO: snapshots 
		context.getSymbolTable().setProperty(propertyName, val);
		return val;
	}

	
}
