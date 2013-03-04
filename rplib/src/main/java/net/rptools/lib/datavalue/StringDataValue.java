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
 * The StringDataValue represents String script values.
 *
 */
final class StringDataValue implements DataValue {
	
	/** The String value this represents. */
	private final String value;
	
	/**
	 * Creates a new StringDataValue.
	 * 
	 * @param val The String that this StringDatatValue will represent.
	 */
	StringDataValue(String val) {
		value = val;
	}
	
	@Override
	public String asString() {
		return value;
	}

	@Override
	public long asLong() {
		return Long.parseLong(value);
	}

	@Override
	public double asDouble() {
		return Double.parseDouble(value);
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
		throw new UnsupportedOperationException("Can not convert string to a numeric value");
	}



	@Override
	public DataType dataType() {
		return DataType.STRING;
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
		
		if (getClass() != obj.getClass()) {
			return false;
		}
		
		StringDataValue other = (StringDataValue) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}
	
	@Override
	public String toString() {
		return dataType().toString() + ": " + asString();
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
		return this;
	}

	@Override
	public DataValue asListValue() {
		return DataValueFactory.listValue(asList());
	}

	@Override
	public Map<String, DataValue> asDictionary() {
		throw new UnsupportedOperationException("Can not convert a string value to a dictionary.");
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
        return value.isEmpty() == false;
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
