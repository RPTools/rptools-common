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
import java.util.Map;

import net.rptools.lib.result.Result;
import net.rptools.lib.result.ResultBuilder;

/**
 * The LongDataValue class represents integer script values.
 *
 */
final class LongDataValue implements DataValue {
	
	/** The numeric value of this LongDataValue. */
	private final long value;
	
	/**
	 * Creates a new LongDataValue with the specified numeric value.
	 * 
	 * @param val The value to create a LongDataValue for.
	 */
	LongDataValue(long val) {
		value = val;
	}
	
	@Override
	public String asString() {
		return Long.toString(value);
	}

	@Override
	public long asLong() {
		return value;
	}

	@Override
	public double asDouble() {
		return (double) value;
	}

	@Override
	public List<DataValue> asList() {
		List<DataValue> lst = new ArrayList<>(1);
		lst.add(this);
		return lst;
	}

	@Override
	public DataValue add(DataValue val) {
        return DataValueOperations.add(this, val);
	}

	@Override()
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
		return DataValueFactory.longValue(-value);
	}



	@Override
	public DataType dataType() {
		return DataType.LONG;
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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		
		if (obj == null) {
			return false;
		}

        if (obj instanceof DataValue)  {
            DataValue dv = (DataValue) obj;
            if (dv.dataType() == DataType.LONG) {
                if (value == dv.asLong()) {
                    return  true;
                } else {
                    return  false;
                }
            } else if (dv.dataType() == DataType.DOUBLE) {
                if (asDouble() == dv.asDouble()) {
                    return true;
                } else {
                    return false;
                }
            }
        }

		return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (value ^ (value >>> 32));
		return result;
	}
	
	@Override
	public String toString() {
		return dataType().toString() + ": " + asString();
	}
	
	@Override
	public DataValue asLongValue() {
		return this;
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
	public Map<String, DataValue> asDictionary() {
		throw new UnsupportedOperationException("Can not convert a numeric value to a dictionary.");
	}

	@Override
	public DataValue asDictionaryValue() {
		return DataValueFactory.dictionaryValue(asDictionary());
	}

	@Override
	public Result asResult() {
		return new ResultBuilder().setValue(this).toResult();
	}

    @Override
    public boolean asBoolean() {
        return value != 0;
    }

    @Override
	public DataValue asResultValue() {
		return DataValueFactory.resultValue(asResult());
	}

    @Override
    public DataValue asBooleanValue() {
        return DataValueFactory.booleanValue(asBoolean());
    }

}
