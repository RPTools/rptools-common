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
 * Negates the child script tree node.
 *
 */
class NegateNode implements ScriptTreeNode {

	/** The child node to negate. */
	private final ScriptTreeNode child;
	
	/**
	 * Creates a new NegaeNode.
	 * 
	 * @param child The child to negate.
	 */
	public NegateNode(ScriptTreeNode child) {
		assert child != null : "Child node can not be null";

		this.child = child;
	}
	
	
	@Override
	public DataValue evaluate(ScriptContext context) throws ExpressionEvaluatorException {
		final DataValue val = child.evaluate(context);
		return val.negate();
	}
	
	

}
