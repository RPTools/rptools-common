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

import net.rptools.lib.result.Result;
import net.rptools.lib.result.ResultBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The BooleanDataValue represents boolean values in the scripting language.
 */
public class BooleanDataValue implements DataValue {

    /** The actual value. */
    boolean value;


    /** Singleton instance for a true BooleanDataValue. */
    private static BooleanDataValue trueDataValue = new BooleanDataValue(true);


    /** Singleton instance for a false BooleanDataValue. */
    private static BooleanDataValue falseDataValue = new BooleanDataValue(false);


    /**
     * Returns the BooleanDataValue for the boolean value.
     *
     * @param val The value to represent.
     *
     * @return the BooleanDataValue.
     */
    public static BooleanDataValue getBooleanDataValue(boolean val) {
        if (val) {
            return trueDataValue;
        } else {
            return falseDataValue;
        }
    }


    /**
     * Creates a new BooleanDataValue.
     *
     * @param val The value this BooleanDataValue represents.
     */
    private BooleanDataValue(boolean val) {
        value = val;
    }


    @Override
    public DataType dataType() {
        return DataType.BOOLEAN;
    }

    @Override
    public String asString() {
        return Boolean.toString(value);
    }

    @Override
    public long asLong() {
        throw new NumberFormatException("Can not convert boolean to a number.");
    }

    @Override
    public double asDouble() {
        throw new NumberFormatException("Can not convert boolean to a number.");
    }

    @Override
    public List<DataValue> asList() {
        return Collections.<DataValue>singletonList(this);
    }

    @Override
    public Map<String, DataValue> asDictionary() {
        throw new UnsupportedOperationException("Can not convert boolean to a dictionary.");
    }

    @Override
    public Result asResult() {
        return new ResultBuilder().setBooleanValue(value).toResult();
    }

    @Override
    public boolean asBoolean() {
        return value;
    }

    @Override
    public DataValue add(DataValue val) {
        return DataValueOperations.add(this, val);
    }

    @Override
    public DataValue subtract(DataValue val) {
        return DataValueOperations.subtract(this, val);
    }

    @Override
    public DataValue multiply(DataValue val) {
        return DataValueOperations.multiply(this, val);
    }

    @Override
    public DataValue divide(DataValue val) {
        return DataValueOperations.divide(this, val);
    }

    @Override
    public DataValue remainder(DataValue val) {
        return DataValueOperations.remainder(this, val);
    }

    @Override
    public DataValue power(DataValue exp) {
        return DataValueOperations.power(this, exp);
    }

    @Override
    public DataValue negate() {
        throw new UnsupportedOperationException("Can not add negate values.");
    }

    @Override
    public boolean hasLabel() {
        return false;
    }

    @Override
    public DataLabel getLabel() {
        return DataLabel.NO_LABEL;
    }

    @Override
    public DataValue asLongValue() {
        return DataValueFactory.longValue(asLong());
    }

    @Override
    public DataValue asDoubleValue() {
        return DataValueFactory.doubleValue(asDouble());
    }

    @Override
    public DataValue asStringValue() {
        return DataValueFactory.stringValue(asString());
    }

    @Override
    public DataValue asListValue() {
        return DataValueFactory.listValue(asList());
    }

    @Override
    public DataValue asDictionaryValue() {
        return DataValueFactory.dictionaryValue(asDictionary());
    }

    @Override
    public DataValue asResultValue() {
        return DataValueFactory.resultValue(asResult());
    }

    @Override
    public DataValue asBooleanValue() {
        return this;
    }
}
