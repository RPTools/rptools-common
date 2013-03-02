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
package net.rptools.parser.functions.dictionary;

import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.datavalue.DataValue;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.functions.FunctionDefinition;
import net.rptools.parser.functions.FunctionDefinitionBuilder;
import net.rptools.parser.functions.ScriptFunction;
import net.rptools.parser.functions.ScriptFunctionException;

import java.util.Map;

/**
 * Represents the Script function that converts arguments to a Dictionary value.
 *
 */
public class DictFunction implements ScriptFunction {

    /** The singelton instance. */
    private static final DictFunction INSTANCE = new DictFunction();

    private final FunctionDefinition functionDefinition;
    /**
     * Returns an instance of DictFunction.
     *
     * @return the instance.
     */
    public static DictFunction getDictFunction() {
        return INSTANCE;
    }

    /**
     * Creates a new instance of DictFunction.
     */
    private DictFunction() {
        functionDefinition = new FunctionDefinitionBuilder().setName("dict").setReturnType(DataType.DICTIONARY)
                .addDictionaryVarargsParameter("__dict").toFunctionDefinition();
    }


    @Override
    public FunctionDefinition getDefinition() {
        return functionDefinition;
    }

    @Override
    public DataValue call(ScriptContext context, Map<String, DataValue> args) throws ScriptFunctionException {
        // The hard work has already been done for us.
        return args.get("__dict");
    }
}
