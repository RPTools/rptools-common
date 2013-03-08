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
import net.rptools.lib.result.RollExpression;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.functions.ScriptFunctionException;


/**
 * Interface for classes that perform the dice roll mechanic for the RPTool script.
 */
public interface Dice {

    /**
     * Returns the name of the dice. The name of the dice should be human readable.
     *
     * @return the name of the dice.
     */
    public String getName();

    /**
     * Returns the result of the roll. You should call {@link #matches(net.rptools.lib.result.RollExpression)} to
     * ensure that the {@link RollExpression} matches this Dice before calling this method or the results are
     * undefined and probably very bad.
     *
     *
     * @param context The {@link net.rptools.parser.ScriptContext} to perform the dice roll with.
     * @param exp The {@link net.rptools.lib.result.RollExpression} to get the results for.
     *
     * @return the result of performing the {@link RollExpression}.
     *
     * @throws net.rptools.parser.ExpressionEvaluatorException if there is an error performing the roll.
     */
    public DataValue roll(ScriptContext context, RollExpression exp) throws ScriptFunctionException;


    /**
     * Checks to see if the {@link RollExpression} matches this Dice.
     *
     * @param exp The {@link RollExpression} to check.
     *
     * @return true if this Dice handles the {@link RollExpression}.
     */
    public boolean matches(RollExpression exp);

}
