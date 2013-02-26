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
package net.rptools.parser.functions;

import java.util.Map;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.parser.ScriptContext;


/**
 * Interface that that is required for all objects that implement a script
 * function.
 *
 */
public interface ScriptFunction {
	
	/**
	 * Gets the definition of the script function.
	 * 
	 * @return the script function definition.
	 */
	public FunctionDefinition getDefinition();
	
	/**
	 * Performs the call of the script function and returns the result.
	 *
     * @param context The script context that the function is being called with.
	 * @param args The arguments in the scripts function call.
	 * 
	 * @return the function result.
	 */
	public DataValue call(ScriptContext context, Map<String, DataValue> args) throws ScriptFunctionException;
}
