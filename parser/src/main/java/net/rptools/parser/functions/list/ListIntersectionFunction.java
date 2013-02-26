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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.functions.FunctionDefinition;
import net.rptools.parser.functions.FunctionDefinitionBuilder;
import net.rptools.parser.functions.ScriptFunction;

/**
 * Implements the list.union script function.
 *
 * This function takes multiple lists and does a union on all of them
 *
 */
public class ListIntersectionFunction implements ScriptFunction {
	
	/** The singleton instance. */
	private static final ListIntersectionFunction INSTANCE = new ListIntersectionFunction();
	
	/** The function definition for the list script function. */
	private FunctionDefinition functionDefinition;
	
	/**
	 * Creates a new LsitFunction. 
	 */
	private ListIntersectionFunction() {
		functionDefinition = new FunctionDefinitionBuilder().setName("list.intersection")
                .setReturnType(DataType.LIST).addListVarargsParameter("values").toFunctionDefinition();
	}

	/**
	 * Gets the singleton instance for the ListFunction.
	 * 
	 * @return the instance of ListFunction.
	 */
	public static ListIntersectionFunction getListFunction() {
		return INSTANCE;
	}

	@Override
	public FunctionDefinition getDefinition() {
		return functionDefinition;
	}

	@Override
	public DataValue call(ScriptContext context, Map<String, DataValue> args) {
		DataValue values = args.get("values");
		if (values.asList().size() == 0) {
			return DataValueFactory.listValue(new ArrayList<DataValue>());
		}
		
		Set<DataValue> result = new LinkedHashSet<>();
		
		// First add all of the first list
		result.addAll(values.asList().get(0).asList());
		
		for (DataValue list : values.asList()) {
			// Only retain the values in the other lists, we process first list again but it
			// wont make any difference result wise and barely measurable performance wise.
			result.retainAll(list.asList());
		}
		return DataValueFactory.listValue(result);

	}
	
}
