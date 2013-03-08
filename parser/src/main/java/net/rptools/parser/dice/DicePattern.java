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

import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The DicePattern class represents the pattern that is matched to the input string for dice rolls.
 *
 * The format of the dice pattern is made up from arguments between #{} and a string to match.
 *
 * For example to match a roll string of the format 1d6 the following dice pattern that would match.
 * #{number}d#{sides}. When this pattern is matched the {@code number} and {@code sides} arguments will
 * be extracted. The string part can contain most regular expressions but can not contain any capture
 * groups. A default value can be provided to dice pattern arguments with the ':' character in which
 * case that argument becomes optional. For example #{number:1}d#{sides} will match <i>2d6</i> and
 * <i>d6</i> if the optional argument is not provided then it will have the default value. All arguments
 * are numeric only.
 */
public class DicePattern {

    /** The regexp pattern generated. */
    private final Pattern pattern;

    /** The argument names for this dice pattern and their default values. */
    private final Map<String, DataValue> arguments = new LinkedHashMap<>();

    /**
     * Creates a new DicePattern.
     *
     * @param dicePattern The pattern to match.
     * @param caseInsensitive Should the pattern be case insensitive.
     */
    private DicePattern(String dicePattern, boolean caseInsensitive) {

        Pattern pat = Pattern.compile("#\\{([A-z][A-z0-9]+)(?::(\\d+))?\\}");
        Matcher matcher = pat.matcher(dicePattern);


        int startIndex = 0;

        while (matcher.find(startIndex)) {
            DataValue defaultArg = DataValueFactory.nullDataValue();
            if (matcher.group(2) != null) {
                defaultArg = DataValueFactory.longValue(Long.parseLong(matcher.group(2)));
            }
            arguments.put(matcher.group(1), defaultArg);

            startIndex = matcher.end();
        }

        String regex = dicePattern;

        // Now build the regex
        for (Map.Entry<String, DataValue> ent : arguments.entrySet()) {
            if (ent.getValue().dataType() == DataType.NULL) {
                regex = regex.replaceFirst("#\\{" + ent.getKey() + "(?::\\d+)?\\}", "(?<" + ent.getKey() + ">[0-9]+)");
            } else {
                regex = regex.replaceFirst("#\\{" + ent.getKey() + "(?::\\d+)?\\}", "(?<" + ent.getKey() + ">[0-9]+)?");
            }
        }



        if (caseInsensitive) {
            pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        } else {
            pattern = Pattern.compile(regex);
        }
    }

    /**
     * Returns an instance of DicePattern that will match the specified string pattern.
     *
     * @param dicePattern the string representation of the dice pattern.
     *
     * @return a DicePattern instance.
     */
    public static DicePattern getDicePattern(String dicePattern) {
        return new DicePattern(dicePattern, false);
    }

    /**
     * Returns an instance of DicePattern that will match the specified string pattern.
     *
     * @param dicePattern the string representation of the dice pattern.
     *
     * @return a DicePattern instance.
     */
    public static DicePattern getCaseInsenstiveDicePattern(String dicePattern) {
        return new DicePattern(dicePattern, true);
    }


    /**
     * Checks to see if this pattern matches the specified dice roll string.
     *
     * @param rollString The dice roll string to check.
     *
     * @return true if the pattern matches the dice roll string.
     */
    public boolean matches(String rollString) {
        return pattern.matcher(rollString).matches();
    }

    /**
     * Extracts the arguments from the dice roll string matching this pattern.
     * You must call {@link #matches(String)} first to ensure that the pattern matches the roll string, otherwise
     * the results are undefined.
     *
     * @param rollString The roll string to extract the arguments from.
     *
     * @return the arguments specified in the roll string.
     */
    public Map<String, DataValue> extractArgs(String rollString) {
        Matcher matcher = pattern.matcher(rollString);
        Map<String, DataValue> rollArgs = new HashMap<>();
        if (matcher.matches()) {

            for (Map.Entry<String, DataValue> entry : arguments.entrySet()) {
                String argStr = matcher.group(entry.getKey());
                DataValue argVal;
                if (argStr == null) {  // not specified so use the default.
                    argVal = entry.getValue();
                } else {
                    argVal = DataValueFactory.longValue(Long.parseLong(argStr));
                }

                rollArgs.put(entry.getKey(), argVal);
            }

        }
        return rollArgs;
    }


    /**
     * Returns the names of the arguments for this DicePattern.
     *
     * @return the arguments for the DicePattern.
     */
    public Collection<String> getArgNames() {
        return Collections.unmodifiableCollection(arguments.keySet());
    }
}
