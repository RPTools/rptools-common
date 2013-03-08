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
import net.rptools.lib.result.ResultBuilder;
import net.rptools.lib.result.RollExpression;
import net.rptools.lib.util.RandomUtil;
import net.rptools.parser.ScriptContext;

import java.util.Map;

/**
 * The StandardDice class implements the <i>X</i>d<i>Y</i> dice pattern in RPTool script as well as providing
 * several utility methods that can be used in other dice rollers.
 */
public class StandardDice implements Dice {

    /** The pattern used to match dice rolls. */
    private final DicePattern dicePattern;

    /**
     * Creates a new StandardDice object.
     */
    public StandardDice() {
        dicePattern = DicePattern.getCaseInsenstiveDicePattern("#{number:1}d#{sides}");
    }

    /**
     * Returns the rolls concatenated to a string with the specified delimiter.
     *
     * @param rolls The rolls to concatenate.
     * @param delim The delimiter to use when concatenating the rolls.
     *
     * @return the String with the concatenated rolls.
     */
    public String rollsToString(int[] rolls, String delim) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rolls.length; i++) {
            if (i > 0) {
                sb.append(delim);
            }

            sb.append(rolls[i]);
        }

        return sb.toString();
    }


    /**
     * Total all the rolls in the array.
     *
     * @param rolls The rolls to total.
     *
     * @return the total of the rolls.
     */
    public long totalRolls(int[] rolls) {
        long sum = 0;
        for (int i = 0; i < rolls.length; i++) {
            sum += rolls[i];
        }

        return sum;
    }

    @Override
    public String getName() {
        return "Standard Dice";
    }


    @Override
    public DataValue roll(ScriptContext context, RollExpression exp) {

        Map<String,DataValue> args = dicePattern.extractArgs(exp.getRollString());

        int numberRolls = (int) args.get("number").asLong();

        int sides = (int) args.get("sides").asLong();

        int[] rolls = RandomUtil.getInstance().rollDice(numberRolls, sides);
        long total = totalRolls(rolls);
        String details = rollsToString(rolls, ", ");
        DataValue rollList = DataValueFactory.listValueFromArray(rolls);



        ResultBuilder res = new ResultBuilder();
        if (exp.isVerbose() && exp.isSum() == false)  {
            res.setValue(rollList);
        } else {
            res.setLongValue(total);
        }
        res = res.setDetailedResult(DataValueFactory.stringValue(details));
        res = res.setIndividualValues(rollList.asList());
        return DataValueFactory.resultValue(res.toResult());

    }

    @Override
    public boolean matches(RollExpression exp) {
        return dicePattern.matches(exp.getRollString());
    }
}
