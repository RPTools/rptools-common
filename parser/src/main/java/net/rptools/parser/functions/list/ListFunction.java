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
package net.rptools.parser.functions.list;

import java.util.Map;

import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.datavalue.DataValue;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.functions.FunctionDefinition;
import net.rptools.parser.functions.FunctionDefinitionBuilder;
import net.rptools.parser.functions.ScriptFunction;

/**
 * Implements the list script function.
 *
 * The list script function takes a variable number of arguments and
 * returns them as a single list.
 *
 */
public class ListFunction implements ScriptFunction {
	
	/** The singleton instance. */
	private static final ListFunction INSTANCE = new ListFunction();
	
	/** The function definition for the list script function. */
	private FunctionDefinition functionDefinition;
	
	/**
	 * Creates a new LsitFunction. 
	 */
	private ListFunction() {
		functionDefinition = new FunctionDefinitionBuilder().setName("list").addListVarargsParameter("values")
                                 .setReturnType(DataType.LIST).toFunctionDefinition();
	}

	/**
	 * Gets the singleton instance for the ListFunction.
	 * 
	 * @return the instance of ListFunction.
	 */
	public static ListFunction getListFunction() {
		return INSTANCE;
	}

	@Override
	public FunctionDefinition getDefinition() {
		return functionDefinition;
	}

	@Override
	public DataValue call(ScriptContext context, Map<String, DataValue> args) {
		DataValue values = args.get("values");
		return values.asListValue();
	}
	
}
