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

import net.rptools.parser.ExpressionEvaluatorException;
import net.rptools.parser.ScriptContext;

import net.rptools.lib.datavalue.DataValue;

/**
 * Interface that represents all the different nodes in the tree representing the script
 * elements that will be evaluated.
 */
public interface ScriptTreeNode {
	
	/**
	 * Returns the result of evaluating the nodes contents. 
	 * 
	 * @param context The script context to evaluate the node with.
     *
	 * @return the result of evaluating the node.
     *
	 * @throws ExpressionEvaluatorException
	 */
	public DataValue evaluate(ScriptContext context) throws ExpressionEvaluatorException;
}
