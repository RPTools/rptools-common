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
package net.rptools.parser.functions.javascript;

import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.permissions.PermissionLevel;

public class ExportedFunction {
    private final String functionName;
    private final DataType returnType;
    private final Object paramList;
    private final String jsFunctionName;
    private final PermissionLevel permissionLevel;

    public ExportedFunction(String name, DataType rType, Object params, String jsFuncName,
                            PermissionLevel perms) {
        // TODO check for nulls
        functionName = name;
        returnType = rType;
        paramList = params;
        jsFunctionName = jsFuncName;
        permissionLevel = perms;
    }


    public String getFunctionName() {
        return functionName;
    }

    public DataType getReturnType() {
        return returnType;
    }

    public Object getParamList() {
        return paramList;
    }

    public String getJsFunctionName() {
        return jsFunctionName;
    }

    public PermissionLevel getPermissionLevel() {
        return permissionLevel;
    }
}
