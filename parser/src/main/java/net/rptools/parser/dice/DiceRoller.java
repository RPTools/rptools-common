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

import java.util.*;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.lib.result.ResultBuilder;
import net.rptools.lib.result.RollExpression;
import net.rptools.lib.util.RandomUtil;
import net.rptools.parser.ExpressionEvaluatorException;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.functions.ScriptFunctionException;

/**
 * The DiceRoller class manages dice patterns and the rolling of dice matching those patterns.
 */
public class DiceRoller {

    /** The singleton instance. */
    private final static DiceRoller INSTANCE = new DiceRoller();

    /** The defined built in dice. */
    private final Set<Dice> builtInDice = new HashSet<>();

    /** The user defined dice. */
    private final Set<Dice> userDefinedDice = new HashSet<>();


    /**
     * Returns an instance of DiceRoller.
     *
     * @return the instance of DiceRoller.
     */
    public static DiceRoller getInstance() {
        return INSTANCE;
    }


    /**
     * Creates a new DiceRoller.
     */
    private DiceRoller() {
        builtInDice.add(new StandardDice());
    }

    /**
     * Returns the result of performing the dice roll matching the pattern in the {@link RollExpression}.
     *
     * @param context {@link ScriptContext} the script context to perform the roll with.
     * @param rollExpression The {@link RollExpression} to match.
     *
     * @return the result of performing the dice roll.
     *
     * @throws ExpressionEvaluatorException if the dice pattern is not matched or an error occurs in the dice roll
     * function.
     */
	public DataValue roll(ScriptContext context, RollExpression rollExpression) throws ExpressionEvaluatorException {
        // Check user defined dice first.
        for (Dice dice : userDefinedDice) {
            if (dice.matches(rollExpression)) {
                try {
                    return dice.roll(context, rollExpression);
                } catch (ScriptFunctionException e) {
                    throw new ExpressionEvaluatorException(e.getMessage(), e);
                }
            }
        }

        // If not a user defined dice then check the built in dice.
        for (Dice dice : builtInDice) {
            if (dice.matches(rollExpression)) {
                try {
                    return dice.roll(context, rollExpression);
                } catch (ScriptFunctionException e) {
                    throw new ExpressionEvaluatorException(e.getMessage(), e);
                }
            }
        }

        throw new ExpressionEvaluatorException("Unknown dice pattern " + rollExpression.getRollString());
	}

    /**
     * Adds user defined dice.
     *
     * @param dice The dice to add.
     */
    public void addUserDefinedDice(Dice dice) {
        userDefinedDice.add(dice);
    }
	

}
