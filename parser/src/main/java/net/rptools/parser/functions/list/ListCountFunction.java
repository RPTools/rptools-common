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
package net.rptools.parser.functions.list;

import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.functions.FunctionDefinition;
import net.rptools.parser.functions.FunctionDefinitionBuilder;
import net.rptools.parser.functions.ScriptFunction;
import net.rptools.parser.functions.ScriptFunctionException;

import java.util.*;

/**
 * Implements the count RPTools script function.
 */
public class ListCountFunction implements ScriptFunction {

    /** The singleton instance. */
    private static final ListCountFunction INSTANCE = new ListCountFunction();

    /** The function definition for the list script function. */
    private FunctionDefinition functionDefinition;


    private static final String GREATER_THAN = "greaterThan";
    private static final String LESS_THAN = "lessThan";
    private static final String EQUAL_TO = "equals";
    private static final String AT_LEAST = "atLeast";
    private static final String AT_MOST = "atMost";
    private static final String NOT = "not";

    /**
     * Creates a new ListCountFunction.
     */
    private ListCountFunction() {
        functionDefinition = new FunctionDefinitionBuilder().setName("count")
                .setReturnType(DataType.LONG)
                .addParameter("list", DataType.LIST)
                .addParameter(GREATER_THAN, DataType.DOUBLE, DataValueFactory.nullDataValue())
                .addParameter(LESS_THAN, DataType.DOUBLE, DataValueFactory.nullDataValue())
                .addParameter(EQUAL_TO, DataType.DOUBLE, DataValueFactory.nullDataValue())
                .addParameter(AT_LEAST, DataType.DOUBLE, DataValueFactory.nullDataValue())
                .addParameter(AT_MOST, DataType.DOUBLE, DataValueFactory.nullDataValue())
                .addParameter(NOT, DataType.DOUBLE, DataValueFactory.nullDataValue())
                .toFunctionDefinition();
    }

    /**
     * Gets the singleton instance for the ListFunction.
     *
     * @return the instance of ListFunction.
     */
    public static ListCountFunction listCountFunction() {
        return INSTANCE;
    }


    /**
     * Checks to see if all of the values in the list are numeric or not.
     * @param vals
     * @return
     */
    private boolean checkForNonNumeric(DataValue vals) {
        for (DataValue dv : vals.asList()) {

            if (dv.dataType() != DataType.LONG && dv.dataType() != DataType.DOUBLE) {
                return true;
            }
        }

        return false;
    }


    @Override
    public FunctionDefinition getDefinition() {
        return functionDefinition;
    }

    @Override
    public DataValue call(ScriptContext context, Map<String, DataValue> args) throws ScriptFunctionException {

        boolean containsNonNumeric;

        DataValue values = args.get("list");
        if (values.asList().size() == 0) {
            return DataValueFactory.longValue(0);
        }

        containsNonNumeric = checkForNonNumeric(values);

        List<DataValue> matching = new ArrayList<>();
        matching.addAll(values.asList());

        // Perform equal to checks
        DataValue equal = args.get(EQUAL_TO);
        if (equal.dataType() != DataType.NULL) {
            List<DataValue> lst = new ArrayList<>();
            for (DataValue dv : matching) {
                if (dv.equals(equal)) {
                    lst.add(dv);
                }
            }

            matching = lst;
        }


        // Perform not checks
        DataValue not = args.get(NOT);
        if (not.dataType() != DataType.NULL) {
            List<DataValue> lst = new ArrayList<>();
            for (DataValue dv : matching) {
                if (dv.equals(not) == false) {
                    lst.add(dv);
                }
            }

            matching = lst;
        }

        // Perform greater than checks.
        DataValue greater = args.get(GREATER_THAN);
        if (greater.dataType() != DataType.NULL) {
            if (containsNonNumeric) {
                throw new ScriptFunctionException("Can not do comparison count if list contains non numeric values.");
            }
            List<DataValue> lst = new ArrayList<>();
            for (DataValue dv : matching) {
                if (dv.asDouble() > greater.asDouble()) {
                    lst.add(dv);
                }
            }

            matching = lst;
        }

        // Perform less than checks
        DataValue less = args.get(LESS_THAN);
        if (less.dataType() != DataType.NULL) {
            if (containsNonNumeric) {
                throw new ScriptFunctionException("Can not do comparison count if list contains non numeric values.");
            }
            List<DataValue> lst = new ArrayList<>();
            for (DataValue dv : matching) {
                if (dv.asDouble() < less.asDouble()) {
                    lst.add(dv);
                }
            }

            matching = lst;
        }

        // Perform at least checks
        DataValue atLeast = args.get(AT_LEAST);
        if (atLeast.dataType() != DataType.NULL) {
            if (containsNonNumeric) {
                throw new ScriptFunctionException("Can not do comparison count if list contains non numeric values.");
            }
            List<DataValue> lst = new ArrayList<>();
            for (DataValue dv : matching) {
                if (dv.asDouble() >= atLeast.asDouble()) {
                    lst.add(dv);
                }
            }

            matching = lst;
        }

        // Perform at most checks
        DataValue atMost = args.get(AT_MOST);
        if (atMost.dataType() != DataType.NULL) {
            if (containsNonNumeric) {
                throw new ScriptFunctionException("Can not do comparison count if list contains non numeric values.");
            }
            List<DataValue> lst = new ArrayList<>();
            for (DataValue dv : matching) {
                if (dv.asDouble() <= atMost.asDouble()) {
                    lst.add(dv);
                }
            }

            matching = lst;
        }


        return DataValueFactory.longValue(matching.size());
    }

}
