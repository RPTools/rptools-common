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

/**
 * Represents a function exported from JavaScript code and made available to RPTools script.
 */
public class ExportedFunction {
    /** The exported function name. */
    private final String functionName;

    /** The return type of the function. */
    private final DataType returnType;

    /** The parameter list for the function. */
    private final Object paramList;

    /** The name of the JavaScript function to call. */
    private final String jsFunctionName;

    /** The default player permission level required to execute the function. */
    private final PermissionLevel defaultPermissionLevel;

    /**
     * Creates a new ExportedFunction.
     *
     * @param name The exported function name.
     * @param rType The type returned by the function.
     * @param params The parameter list for the function.
     * @param jsFuncName The name of the JavaScript function to call.
     * @param perms The default permission required to call the function.
     *
     * @throws  NullPointerException if any of the arguments are null.
     */
    public ExportedFunction(String name, DataType rType, Object params, String jsFuncName,
                            PermissionLevel perms) {
        if (name == null) {
            throw new NullPointerException("Name of exported function is null");
        }

        if (rType == null) {
            throw new NullPointerException("Return type of exported function is null");
        }

        if (params == null) {
            throw new NullPointerException("Parameter List of exported function is null");
        }

        if (jsFuncName == null) {
            throw new NullPointerException("JavaScript name of exported function is null");
        }

        if (perms == null) {
            throw new NullPointerException("Default permission level for function is null");
        }

        functionName = name;
        returnType = rType;
        paramList = params;
        jsFunctionName = jsFuncName;
        defaultPermissionLevel = perms;
    }


    /**
     * Returns the exported name of the function.
     *
     * @return the exported name.
     */
    public String getFunctionName() {
        return functionName;
    }

    /**
     * Returns the return type of the function.
     *
     * @return the return type.
     */
    public DataType getReturnType() {
        return returnType;
    }

    /**
     * Returns the parameter list of the function.
     *
     * @return the parameter list.
     */
    public Object getParamList() {
        return paramList;
    }

    /**
     * Returns the JavaScript function name to call.
     *
     * @return the JavaSCript function name.
     */
    public String getJsFunctionName() {
        return jsFunctionName;
    }

    /**
     * Gets the default permission level required to call the function.
     *
     * @return the default permission level.
     */
    public PermissionLevel getDefaultPermissionLevel() {
        return defaultPermissionLevel;
    }
}
