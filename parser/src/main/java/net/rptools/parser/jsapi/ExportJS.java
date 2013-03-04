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
import net.rptools.parser.functions.FunctionDefinitionBuilder;

import java.util.*;

/**
 * Class used for JavaScript API call to export JavaScript functions.
 *
 */
public class ExportJS {

    /** The temporary list of exported functions */
    private static final List<ExportedFunction> exportedFunctions = new ArrayList<>();

    private ExportJS() {
        // Stop instantiation.
    }


    /**
     * Clear the currently exported functions. This should be run before calling a new JavaScript
     * script so that you can record which functions were defined in this script.
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

}
