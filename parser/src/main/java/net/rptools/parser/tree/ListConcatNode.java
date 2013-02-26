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
import java.util.List;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.parser.ExpressionEvaluatorException;
import net.rptools.parser.ScriptContext;

/**
 * Script node that represents list concatenation operator.
 *
 */
class ListConcatNode implements ScriptTreeNode {

	/** The left list to concatenate. */
	private final ScriptTreeNode left;
	
	/** The right list to concatenate. */
	private final ScriptTreeNode right;
	
	/**
	 * Creates a ListConcatNode.
	 * 
	 * @param left The left list to concatenate.
	 * 
	 * @param right The right list to concatenate.
	 */
	public ListConcatNode(ScriptTreeNode left, ScriptTreeNode right) {
		assert left != null : "Left child node can not be null";
		assert right != null : "Right child node can not be null";

		this.left = left;
		this.right = right;
	}
	
	
	@Override
	public DataValue evaluate(ScriptContext context) throws ExpressionEvaluatorException {
		
		final DataValue l = left.evaluate(context);
		final DataValue r = right.evaluate(context);
		
		final List<DataValue> lst = new ArrayList<DataValue>();
		lst.addAll(l.asList());
		lst.addAll(r.asList());
		
		return DataValueFactory.listValue(lst);
	}
	
	

}
