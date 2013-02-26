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
import java.util.Collections;
import java.util.List;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.parser.ExpressionEvaluatorException;
import net.rptools.parser.ScriptContext;

/**
 * Script tree node that represents the script to be run.
 *
 */
class ScriptNode implements ScriptTreeNode {
	
	/** The statements that make up the script. */
	private final List<ScriptTreeNode> statements = new ArrayList<>();

	/**
	 * Adds a statement to the script.
	 * 
	 * @param node the node for the statement to add.
	 */
	public void addStatement(ScriptTreeNode node) {
		assert node != null : "Child null can not be null.";
		statements.add(node);
	}
	
	@Override
	public DataValue evaluate(ScriptContext context) throws ExpressionEvaluatorException {
		
		DataValue returnVal = DataValueFactory.listValue(Collections.<DataValue>emptyList());
		for (ScriptTreeNode node : statements) {
			DataValue val = node.evaluate(context);
			returnVal = returnVal.add(val);			
		}
		
		return returnVal;
	}

}