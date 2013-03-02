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

import java.util.Map;

/**
 * Represents the list.get Script function.
 */
public class DictGetFunction implements ScriptFunction {

    /** The singelton instance. */
    private static final DictGetFunction INSTANCE = new DictGetFunction();

    private final FunctionDefinition functionDefinition;
    /**
     * Returns an instance of DictGetFunction.
     *
     * @return the instance.
     */
    public static DictGetFunction getDictGetFunction() {
        return INSTANCE;
    }

    /**
     * Creates a new instance of DictGetFunction.
     */
    private DictGetFunction() {
        functionDefinition = new FunctionDefinitionBuilder().setName("dict.get").setReturnType(DataType.ANY)
                .addParameter("dict", DataType.DICTIONARY)
                .addParameter("key", DataType.STRING).toFunctionDefinition();
    }


    @Override
    public FunctionDefinition getDefinition() {
        return functionDefinition;
    }

    @Override
    public DataValue call(ScriptContext context, Map<String, DataValue> args) throws ScriptFunctionException {
        Map<String, DataValue> dict = args.get("dict").asDictionary();
        String key = args.get("key").asString();

        if (dict.containsKey(key)) {
            return dict.get(key);
        } else {
            return DataValueFactory.stringValue("");
        }
    }
}
