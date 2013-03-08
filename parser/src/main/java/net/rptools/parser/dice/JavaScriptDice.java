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
package net.rptools.parser.dice;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.lib.result.RollExpression;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.functions.ScriptFunctionException;
import net.rptools.parser.functions.javascript.JavaScriptFunction;

import java.util.HashMap;
import java.util.Map;


/**
 * The JavaScriptDice class implements the {@link Dice} interface on behalf of a JavaScript function called from
 * the RPtools script engine.
 *
 */
public class JavaScriptDice implements Dice {


    /** The pattern used to match dice rolls. */
    private final DicePattern dicePattern;

    /** The {@link JavaScriptFunction} to call to perform the dice roll. */
    private final JavaScriptFunction jsFunction;

    /** The name of the dice roll. */
    private final String diceName;

    /**
     * Creates a new JavaScriptDice instance.
     *
     * @param dicePat The dice pattern to match.
     * @param name The name of the dice roll.
     * @param function The {@link JavaScriptFunction} called to perform the roll.
     */
    public JavaScriptDice(DicePattern dicePat, String name, JavaScriptFunction function) {
        dicePattern = dicePat;
        jsFunction = function;
        diceName = name;
    }

    /**
     * Returns the JavaScript function that rolling this dice will call.
     *
     * @return the JavaScript function that will be called.
     */
    public JavaScriptFunction getJavaScriptFunction() {
        return jsFunction;
    }


    @Override
    public String getName() {
        return diceName;
    }

    @Override
    public DataValue roll(ScriptContext context, RollExpression exp) throws ScriptFunctionException {
        Map<String,DataValue> args = new HashMap<>();
        args.putAll(dicePattern.extractArgs(exp.getRollString()));

        args.put("__verbose", DataValueFactory.booleanValue(exp.isVerbose()));
        args.put("__summed", DataValueFactory.booleanValue(exp.isSum()));

        return jsFunction.call(context, args);
    }

    @Override
    public boolean matches(RollExpression exp) {
        return dicePattern.matches(exp.getRollString());
    }
}
