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
package net.rptools.parser.functions.javascript;

import java.util.Map;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.functions.FunctionDefinition;
import net.rptools.parser.functions.ScriptFunction;
import net.rptools.parser.functions.ScriptFunctionException;

/**
 * The JavaScriptFunction implements the ScriptFunction interface on behalf of JavaScript functions that are exported
 * to the RPTools script.
 */
public class JavaScriptFunction implements ScriptFunction {


    /** The name of the JavaScript Function. */
    private final String jsFunctionName;

    /** The function definition. */
    private final FunctionDefinition functionDefinition;


    /**
     * Creates a new JavaScriptFunction object.
     *
     * @param jsFuncName The name of the JavaScrpit function.
     * @param def The function definition.
     *
     * @throws NullPointerException if either argument is null.
     */
    public JavaScriptFunction(String jsFuncName, FunctionDefinition def) {
        if (jsFuncName == null) {
            throw new NullPointerException("Java Script function name can not be null.");
        }

        if (def == null) {
            throw new NullPointerException("Function definition can not be null.");
        }
        jsFunctionName = jsFuncName;
        functionDefinition = def;
    }
	
	@Override
	public FunctionDefinition getDefinition() {
        return functionDefinition;
	}

	@Override
	public DataValue call(ScriptContext context, Map<String, DataValue> args) throws ScriptFunctionException {
        return JavaScripEvaluator.getInstance().call(context, this, args, functionDefinition.getReturnType());
	}

    /**
     * Returns the name of the JavaScript function to call.
     *
     * @return the name of the JavaScript function.
     */
    String jsFunctionName() {
        return jsFunctionName;
    }

}
