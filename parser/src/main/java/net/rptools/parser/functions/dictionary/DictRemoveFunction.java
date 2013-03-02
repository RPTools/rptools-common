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
 * Represents the Script function dict.remove.
 */
public class DictRemoveFunction implements ScriptFunction {

    /** The singleton instance. */
    private static final DictRemoveFunction INSTANCE = new DictRemoveFunction();

    private final FunctionDefinition functionDefinition;
    /**
     * Returns an instance of DictRemoveFunction.
     *
     * @return the instance.
     */
    public static DictRemoveFunction getDictRemoveFunction() {
        return INSTANCE;
    }

    /**
     * Creates a new instance of DictRemoveFunction.
     */
    private DictRemoveFunction() {
        functionDefinition = new FunctionDefinitionBuilder().setName("dict.remove").setReturnType(DataType.DICTIONARY)
                .addParameter("__dict", DataType.DICTIONARY)
                .addListVarargsParameter("__keys").toFunctionDefinition();
    }


    @Override
    public FunctionDefinition getDefinition() {
        return functionDefinition;
    }

    @Override
    public DataValue call(ScriptContext context, Map<String, DataValue> args) throws ScriptFunctionException {
        Map<String, DataValue> dict = new HashMap<>();

        dict.putAll(args.get("__dict").asDictionary());

        DataValue keys = args.get("__keys");


        // If the only keys argument is a list use the values in the list
        if (keys.asList().size() == 1 && keys.asList().get(0).dataType() == DataType.LIST) {
            keys = keys.asList().get(0);
        }

        for (DataValue dv : keys.asList()) {
            dict.remove(dv.asString());
        }

        return DataValueFactory.dictionaryValue(dict);
    }
}
