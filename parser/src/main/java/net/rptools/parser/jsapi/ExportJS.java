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
package net.rptools.parser.jsapi;

import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.permissions.PermissionLevel;
import net.rptools.lib.permissions.PlayerPermissions;
import net.rptools.parser.dice.DicePattern;
import net.rptools.parser.dice.JavaScriptDice;
import net.rptools.parser.functions.FunctionDefinitionBuilder;
import net.rptools.parser.functions.javascript.JavaScriptFunction;

import java.util.*;

/**
 * Class used for JavaScript API call to export JavaScript functions.
 *
 */
public class ExportJS {

    /** The temporary list of exported functions */
    private static final List<ExportedFunction> exportedFunctions = new ArrayList<>();

    /** The temporary list of exported dice rolls. */
    private static final List<JavaScriptDice> exportedDice = new ArrayList<>();

    private ExportJS() {
        // Stop instantiation.
    }


    /**
     * Clear the currently exported functions. This should be run before calling a new JavaScript
     * script so that you can record which functions were defined in the script.
     */
    public static void clearExportedFunctions() {
        exportedFunctions.clear();
    }

    /**
     * Returns the exported functions since the last {@code clearExportedFunctions()} call.
     *
     * @return the exported functions.
     */
    public static Collection<ExportedFunction> getExportedFunctions() {
        return Collections.unmodifiableCollection(exportedFunctions);
    }

    /**
     * Callback to export a JavaScript function.
     *
     * @param name The name of the scripting language function.
     * @param params The parameter list for the function.
     * @param returnType The return type of the function.
     * @param jsFunctionName The name of the JavaScript function.
     * @param perm The default permissions required to run the function.
     */
    public static void exportFunction(String name, Object params, String returnType,
                                      String jsFunctionName, String perm) {


        FunctionDefinitionBuilder fdBuilder = new FunctionDefinitionBuilder();

        fdBuilder.setReturnType(DataType.valueOf(returnType.toUpperCase()));
        fdBuilder.setName(name);
        fdBuilder.setDefaultPermission(PermissionLevel.valueOf(perm.toUpperCase()));

        ExportedFunction ef = new ExportedFunction(name, DataType.valueOf(returnType.toUpperCase()),
                                                   params, jsFunctionName, PermissionLevel.valueOf(perm.toUpperCase()));

        exportedFunctions.add(ef);
    }

    /**
     * Clear the list of currently exported dice. This should be run before calling a new JavaScript script
     * so that cou can record which dice were defined in the script.
     */
    public static void clearExportedDice() {
        exportedDice.clear();
    }


    /**
     * Returns a list of the exported dice.
     *
     * @return the exported dice.
     */
    public static Collection<JavaScriptDice> getExportedDice() {
        return Collections.unmodifiableCollection(exportedDice);
    }


    /**
     * Call back to export JavaScript dice.
     *
     * @param name The name of the dice.
     * @param diceString The dice pattern string.
     * @param jsFunctionName The name of the JavaScript function to call.
     * @param caseInsensitive Is the the dice pattern to be exported case insensitive.
     *
     */
    public static void exportDice(String name, String diceString, String jsFunctionName, boolean caseInsensitive) {
        FunctionDefinitionBuilder fdBuilder = new FunctionDefinitionBuilder();
        fdBuilder.setReturnType(DataType.RESULT);
        fdBuilder.setName("Dice Roll(" + name + ")");
        fdBuilder.setDefaultPermission(PlayerPermissions.getUnspecifiedPlayerPermissions().getPermissionLevel());


        DicePattern dicePattern;
        if (caseInsensitive) {
            dicePattern = DicePattern.getCaseInsenstiveDicePattern(diceString);
        } else {
            dicePattern = DicePattern.getDicePattern(diceString);
        }

        // Add all of the arguments.
        for (String argName : dicePattern.getArgNames()) {
            fdBuilder.addParameter(argName, DataType.DOUBLE);

        }

        JavaScriptFunction jsf = new JavaScriptFunction(jsFunctionName, fdBuilder.toFunctionDefinition());

        JavaScriptDice jsd = new JavaScriptDice(dicePattern, name, jsf);


        exportedDice.add(jsd);

    }
}
