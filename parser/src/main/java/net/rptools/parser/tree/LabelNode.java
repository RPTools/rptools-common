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

import net.rptools.lib.datavalue.DataLabel;
import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.parser.ExpressionEvaluatorException;
import net.rptools.parser.ScriptContext;

/**
 * Script Node that represents the label operation.
 *
 */
class LabelNode implements ScriptTreeNode {

	/** The name of the label. */
	private final String label;
	
	/** The child to evaluate. */
	private final ScriptTreeNode child;
	
	/**
	 * Creates a new LabelNode.
	 * 
	 * @param label The name of the label.
	 * @param child The child to label.
	 */
	public LabelNode(String label, ScriptTreeNode child) {
		assert label != null : "Label name can not be null";
		assert label.length() > 0 : "Label name can not be empty";
		assert child != null : "Child node can not be null";

		this.label = label;
		this.child = child;
	}
	
	
	@Override
	public DataValue evaluate(ScriptContext context) throws ExpressionEvaluatorException {
		final DataValue val = child.evaluate(context);
		
		context.getSymbolTable().addLabel(label, val);

		return DataValueFactory.labeledValue(val, new DataLabel(label, 0));
	}
	
	

}
