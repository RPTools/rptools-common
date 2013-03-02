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
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.functions.FunctionDefinition;
import net.rptools.parser.functions.FunctionDefinitionBuilder;
import net.rptools.parser.functions.ScriptFunction;
import net.rptools.parser.functions.ScriptFunctionException;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents the Script dict.set() function.
 */
public class DictSetFunction implements ScriptFunction {

    /** The singleton instance. */
    private static final DictSetFunction INSTANCE = new DictSetFunction();

    private final FunctionDefinition functionDefinition;
    /**
     * Returns an instance of DictSetFunction.
     *
     * @return the instance.
     */
    public static DictSetFunction getDictSetFunction() {
        return INSTANCE;
    }

    /**
     * Creates a new instance of DictSetFunction.
     */
    private DictSetFunction() {
        functionDefinition = new FunctionDefinitionBuilder().setName("dict.set").setReturnType(DataType.DICTIONARY)
                .addParameter("__dict", DataType.DICTIONARY)
                .addDictionaryVarargsParameter("__values").toFunctionDefinition();
    }


    @Override
    public FunctionDefinition getDefinition() {
        return functionDefinition;
    }

    @Override
    public DataValue call(ScriptContext context, Map<String, DataValue> args) throws ScriptFunctionException {
        Map<String, DataValue> vals = new HashMap<>();
        vals.putAll(args.get("__dict").asDictionary());
        // Set new values
        vals.putAll(args.get("__values").asDictionary());

        return DataValueFactory.dictionaryValue(vals);
    }
}
