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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.parser.ExpressionEvaluatorException;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.functions.FunctionDispatcher;
import net.rptools.parser.functions.ArgumentList;

/**
 * Script node that represents a function call.
 * 
 */
class FunctionCallNode implements ScriptTreeNode {

	/** The argument list for the function call. */
	private final FunctionArgumentList argumentList;
	
	/** The name of the function to call. */
	private final String name;
	
	
	/**
	 * Creates a new FunctionCallNode.
	 * 
	 * @param funcName the name of the function.
	 * @param argList The argument list for the function.
	 */
	public FunctionCallNode(String funcName, FunctionArgumentList argList) {
		assert funcName != null : "Function name can not be null";
		assert argList != null : "Argument list node can not be null";

		name = funcName;
		argumentList = argList;
	}
	
	
	@Override
	public DataValue evaluate(ScriptContext context) throws ExpressionEvaluatorException {
		final List<DataValue> posArgs  = new ArrayList<>();
		final Map<String, DataValue> namedArgs = new HashMap<>();

		for (ScriptFunctionArgument arg : argumentList.getPositionalArguments()) {
			posArgs.add(arg.getExpression().evaluate(context));
		}

		for (String argName : argumentList.getArgumentNames()) {
			ScriptFunctionArgument arg = argumentList.getArgument(argName);
			namedArgs.put(argName, arg.getExpression().evaluate(context));
		}

		ArgumentList alist = new ArgumentList(posArgs, namedArgs);
		
		return FunctionDispatcher.getFunctionDispatcher().call(name, alist, context);	
	}


	/**
	 * Returns the argument list for the function call.
	 * 
	 * @return the argument list.
	 */
	public FunctionArgumentList getArgumentList() {
		return argumentList;
	}


	/**
	 * Returns the name of the function to call.
	 * 
	 * @return the name of the function.
	 */
	public String getFunctionName() {
		return name;
	}

}
