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
package net.rptools.lib.datavalue;


import java.util.ArrayList;
import java.util.List;

/**
 * DataValueOperations is a helper class that implements the mathematical operations on DataValues.
 */
class DataValueOperations {

    /**
     * Returns the result of adding two DataValues.
     *
     * @param v1 The first DataValue to add.
     * @param v2 The second DataValue to add.
     *
     * @return the result.
     *
     * throws UnsupportedOperationException if the values can not be added.
     */
    public static DataValue add(DataValue v1, DataValue v2) {

        // Get the real value.
        DataValue dv1 = v1;
        DataValue dv2 = v2;

        while (dv1.dataType() == DataType.RESULT) {
            dv1 = dv1.asResult().getValue();
        }

        while (dv2.dataType() == DataType.RESULT) {
            dv2 = dv2.asResult().getValue();
        }

        // Adding two lists or to a list.
        if (dv1.dataType() == DataType.LIST || dv2.dataType() == DataType.LIST) {
            List<DataValue> lst = new ArrayList<>();
            lst.addAll(dv1.asList());
            lst.addAll(dv2.asList());

            return DataValueFactory.listValue(lst);
        }

        // Adding a null to something
        if (dv1.dataType() == DataType.NULL || dv2.dataType() == DataType.NULL) {
            throw new UnsupportedOperationException("Can not perform addition on a null.");
        }

        // Adding to a dictionary
        if (dv1.dataType() == DataType.DICTIONARY || dv2.dataType() == DataType.DICTIONARY) {
            throw new UnsupportedOperationException("Can not perform addition with a dictionary.");
        }

        // Adding boolean
        if (dv1.dataType() == DataType.BOOLEAN || dv2.dataType() == DataType.BOOLEAN) {
            throw new UnsupportedOperationException("Can not perform addition with a boolean.");
        }

        // Adding to a string.
        if (dv1.dataType() == DataType.STRING || dv2.dataType() == DataType.STRING) {
            return DataValueFactory.stringValue(dv1.asString() + dv2.asString());
        }



        // Only numeric types left.
        if (dv1.dataType() == DataType.DOUBLE || dv2.dataType() == DataType.DOUBLE) {
            return DataValueFactory.doubleValue(dv1.asDouble() + dv2.asDouble());
        } else {
            return DataValueFactory.longValue(dv1.asLong() + dv2.asLong());
        }


    }

    /**
     * Returns the result of subtracting one DataValue from another.
     *
     * @param v1 The DataValues to subtract from.
     * @param v2 The DataValue to subtract.
     *
     * @return the result.
     *
     * throws UnsupportedOperationException if the values can not be subtracted.
     */
    public static DataValue subtract(DataValue v1, DataValue v2) {

        // Get the real value.
        DataValue dv1 = v1;
        DataValue dv2 = v2;

        while (dv1.dataType() == DataType.RESULT) {
            dv1 = dv1.asResult().getValue();
        }

        while (dv2.dataType() == DataType.RESULT) {
            dv2 = dv2.asResult().getValue();
        }

        // Subtracting two lists or to a list.
        if (dv1.dataType() == DataType.LIST && dv2.dataType() == DataType.LIST) {
            List<DataValue> lst = new ArrayList<>();
            lst.addAll(dv1.asList());
            lst.removeAll(dv2.asList());

            return DataValueFactory.listValue(lst);
        } else if (dv1.dataType() == DataType.LIST || dv2.dataType() == DataType.LIST) {
            throw new UnsupportedOperationException("Can only subtract a list from another list.");
        }

        // Subtracting a null from something
        if (dv1.dataType() == DataType.NULL || dv2.dataType() == DataType.NULL) {
            throw new UnsupportedOperationException("Can not perform subtraction on a null.");
        }

        // Subtracting from a dictionary
        if (dv1.dataType() == DataType.DICTIONARY || dv2.dataType() == DataType.DICTIONARY) {
            throw new UnsupportedOperationException("Can not perform subtraction with a dictionary.");
        }

        // Subtracting boolean
        if (dv1.dataType() == DataType.BOOLEAN || dv2.dataType() == DataType.BOOLEAN) {
            throw new UnsupportedOperationException("Can not perform subtraction with a boolean.");
        }

        // Subtracting from a string.
        if (dv1.dataType() == DataType.STRING && dv2.dataType() == DataType.STRING) {
            return DataValueFactory.stringValue(dv1.asString().replace(dv2.asString(), ""));
        } else if (dv1.dataType() == DataType.STRING || dv2.dataType() == DataType.STRING) {
            throw new UnsupportedOperationException("Can not subtract a number from a string.");
        }



        // Only numeric types left.
        if (dv1.dataType() == DataType.DOUBLE || dv2.dataType() == DataType.DOUBLE) {
            return DataValueFactory.doubleValue(dv1.asDouble() - dv2.asDouble());
        } else {
            return DataValueFactory.longValue(dv1.asLong() - dv2.asLong());
        }

    }



    /**
     * Returns the result of multiplying two DataValues.
     *
     * @param v1 The first DataValue to multiply.
     * @param v2 The second DataValue to multiply.
     *
     * @return the result.
     *
     * throws UnsupportedOperationException if the values can not be multiplied.
     */
    public static DataValue multiply(DataValue v1, DataValue v2) {

        // Get the real value.
        DataValue dv1 = v1;
        DataValue dv2 = v2;

        while (dv1.dataType() == DataType.RESULT) {
            dv1 = dv1.asResult().getValue();
        }

        while (dv2.dataType() == DataType.RESULT) {
            dv2 = dv2.asResult().getValue();
        }

        // Multiply two lists or to a list.
        if (dv1.dataType() == DataType.LIST || dv2.dataType() == DataType.LIST) {
            throw new UnsupportedOperationException("Can not multiply a list.");
        }

        // Multiply by a null
        if (dv1.dataType() == DataType.NULL || dv2.dataType() == DataType.NULL) {
            throw new UnsupportedOperationException("Can not perform multiplication by a null.");
        }

        // Adding to a dictionary
        if (dv1.dataType() == DataType.DICTIONARY || dv2.dataType() == DataType.DICTIONARY) {
            throw new UnsupportedOperationException("Can not multiply dictionary.");
        }

        // Adding boolean
        if (dv1.dataType() == DataType.BOOLEAN || dv2.dataType() == DataType.BOOLEAN) {
            throw new UnsupportedOperationException("Can not multiply a boolean.");
        }

        // multiply a string.
        if (dv1.dataType() == DataType.STRING) {
            if (dv2.dataType() == DataType.DOUBLE || dv2.dataType() == DataType.LONG) {
                StringBuilder sb = new StringBuilder();
                for (int i = (int)dv2.asLong(); i > 0; i--) {
                    sb.append(dv1.asString());
                }
                return DataValueFactory.stringValue(sb.toString());
            } else {
                throw new UnsupportedOperationException("Can not multiply a string by a non numeric value.");
            }
        }

        if (dv2.dataType() == DataType.STRING) {
            if (dv1.dataType() == DataType.DOUBLE || dv1.dataType() == DataType.LONG) {
                StringBuilder sb = new StringBuilder();
                for (int i = (int)dv1.asLong(); i > 0; i--) {
                    sb.append(dv2.asString());
                }
                return DataValueFactory.stringValue(sb.toString());
            } else {
                throw new UnsupportedOperationException("Can not multiply a string by a non numeric value.");
            }
        }


        // Only numeric types left.
        if (dv1.dataType() == DataType.DOUBLE || dv2.dataType() == DataType.DOUBLE) {
            return DataValueFactory.doubleValue(dv1.asDouble() * dv2.asDouble());
        } else {
            return DataValueFactory.longValue(dv1.asLong() * dv2.asLong());
        }


    }


    /**
     * REturns the result of dividing one DataValue by another.
     *
     * @param v1 The DataValues to divide.
     * @param v2 The DataValue to divide by.
     *
     * @return the result.
     *
     * throws UnsupportedOperationException if the values can not be divided.
     */
    public static DataValue divide(DataValue v1, DataValue v2) {

        // Get the real value.
        DataValue dv1 = v1;
        DataValue dv2 = v2;

        while (dv1.dataType() == DataType.RESULT) {
            dv1 = dv1.asResult().getValue();
        }

        while (dv2.dataType() == DataType.RESULT) {
            dv2 = dv2.asResult().getValue();
        }

        // division two lists or to a list.
        if (dv1.dataType() == DataType.LIST || dv2.dataType() == DataType.LIST) {
            throw new UnsupportedOperationException("Can not perform division with a list.");
        }

        // division of or by a null
        if (dv1.dataType() == DataType.NULL || dv2.dataType() == DataType.NULL) {
            throw new UnsupportedOperationException("Can not perform sdivision with a null.");
        }

        // Division of or by a dictionary
        if (dv1.dataType() == DataType.DICTIONARY || dv2.dataType() == DataType.DICTIONARY) {
            throw new UnsupportedOperationException("Can not perform division with a dictionary.");
        }

        // Division with a boolean
        if (dv1.dataType() == DataType.BOOLEAN || dv2.dataType() == DataType.BOOLEAN) {
            throw new UnsupportedOperationException("Can not perform division with a boolean.");
        }

        // Division with a string.
        if (dv1.dataType() == DataType.STRING || dv2.dataType() == DataType.STRING) {
            throw new UnsupportedOperationException("Can not perform division with a String.");
        }



        // Only numeric types left.
        if (dv1.dataType() == DataType.DOUBLE || dv2.dataType() == DataType.DOUBLE) {
            return DataValueFactory.doubleValue(dv1.asDouble() / dv2.asDouble());
        } else {
            return DataValueFactory.longValue(dv1.asLong() / dv2.asLong());
        }

    }

    /**
     * Returns the remainder when dividing one DataValue by another.
     *
     * @param v1 The DataValues to divide.
     * @param v2 The DataValue to divide by.
     *
     * @return the result.
     *
     * throws UnsupportedOperationException if the values can not be divided.
     */
    public static DataValue remainder(DataValue v1, DataValue v2) {

        // Get the real value.
        DataValue dv1 = v1;
        DataValue dv2 = v2;

        while (dv1.dataType() == DataType.RESULT) {
            dv1 = dv1.asResult().getValue();
        }

        while (dv2.dataType() == DataType.RESULT) {
            dv2 = dv2.asResult().getValue();
        }

        // division two lists or to a list.
        if (dv1.dataType() == DataType.LIST || dv2.dataType() == DataType.LIST) {
            throw new UnsupportedOperationException("Can not perform division with a list.");
        }

        // division of or by a null
        if (dv1.dataType() == DataType.NULL || dv2.dataType() == DataType.NULL) {
            throw new UnsupportedOperationException("Can not perform sdivision with a null.");
        }

        // Division of or by a dictionary
        if (dv1.dataType() == DataType.DICTIONARY || dv2.dataType() == DataType.DICTIONARY) {
            throw new UnsupportedOperationException("Can not perform division with a dictionary.");
        }

        // Division with a boolean
        if (dv1.dataType() == DataType.BOOLEAN || dv2.dataType() == DataType.BOOLEAN) {
            throw new UnsupportedOperationException("Can not perform division with a boolean.");
        }

        // Division with a string.
        if (dv1.dataType() == DataType.STRING || dv2.dataType() == DataType.STRING) {
            throw new UnsupportedOperationException("Can not perform division with a String.");
        }



        // Only numeric types left.
        if (dv1.dataType() == DataType.DOUBLE || dv2.dataType() == DataType.DOUBLE) {
            return DataValueFactory.doubleValue(dv1.asDouble() % dv2.asDouble());
        } else {
            return DataValueFactory.longValue(dv1.asLong() % dv2.asLong());
        }

    }


    /**
     * Returns the result of raising to the power of one DataValue by another.
     *
     * @param v1 The DataValues to raise.
     * @param v2 The DataValue to raise by.
     *
     * @return the result.
     *
     * throws UnsupportedOperationException if the values can not be divided.
     */
    public static DataValue power(DataValue v1, DataValue v2) {

        // Get the real value.
        DataValue dv1 = v1;
        DataValue dv2 = v2;

        while (dv1.dataType() == DataType.RESULT) {
            dv1 = dv1.asResult().getValue();
        }

        while (dv2.dataType() == DataType.RESULT) {
            dv2 = dv2.asResult().getValue();
        }

        // division two lists or to a list.
        if (dv1.dataType() == DataType.LIST || dv2.dataType() == DataType.LIST) {
            throw new UnsupportedOperationException("Can not perform division with a list.");
        }

        // division of or by a null
        if (dv1.dataType() == DataType.NULL || dv2.dataType() == DataType.NULL) {
            throw new UnsupportedOperationException("Can not perform sdivision with a null.");
        }

        // Division of or by a dictionary
        if (dv1.dataType() == DataType.DICTIONARY || dv2.dataType() == DataType.DICTIONARY) {
            throw new UnsupportedOperationException("Can not perform division with a dictionary.");
        }

        // Division with a boolean
        if (dv1.dataType() == DataType.BOOLEAN || dv2.dataType() == DataType.BOOLEAN) {
            throw new UnsupportedOperationException("Can not perform division with a boolean.");
        }

        // Division with a string.
        if (dv1.dataType() == DataType.STRING || dv2.dataType() == DataType.STRING) {
            throw new UnsupportedOperationException("Can not perform division with a String.");
        }



        // Only numeric types left.
        if (dv1.dataType() == DataType.DOUBLE || dv2.dataType() == DataType.DOUBLE) {
            return DataValueFactory.doubleValue(Math.pow(dv1.asDouble(), dv2.asDouble()));
        } else {
            return DataValueFactory.longValue((long)Math.pow(dv1.asLong(), dv2.asLong()));
        }

    }


}
