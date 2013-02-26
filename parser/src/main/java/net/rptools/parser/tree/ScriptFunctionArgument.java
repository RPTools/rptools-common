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

/**
 * Represents a function argument from the script.
 *
 */
class ScriptFunctionArgument {

	/** The name of the function argument. */
	private final String name;
	
	/** The child that is the value for the argument. */
	private final ScriptTreeNode expression;
	
	/**
	 * Creates a new FunctionArgument.
	 * 
	 * @param paramName the name of the function argument.
	 * 
	 * @param expr The value for the argument.
	 */
	public ScriptFunctionArgument(String paramName, ScriptTreeNode expr) {
		assert expr != null : "Child node can not be null";

		name = paramName;
		expression = expr;
	}
	
	/**
	 * Creates a new FunctionArgument.
	 * 
	 * @param expr The expression that will be the argument value.
	 */
	public ScriptFunctionArgument(ScriptTreeNode expr) {
		name = null;
		expression = expr;
	}

	/**
	 * Returns the name of the argument.
	 * 
	 * @return the name of the argument.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the expression that us used as the function argument.
	 * 
	 * @return the expression. 
	 */
	public ScriptTreeNode getExpression() {
		return expression;
	}
	
	/**
	 * Checks to see if this is a named parameter.
	 * 
	 * @return true if this is a named parameter.
	 */
	public boolean isNamedParameter() {
		return name != null;
	}
	
}
