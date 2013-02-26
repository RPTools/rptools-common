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
import java.util.Collections;
import java.util.List;
import java.util.Map;

import net.rptools.lib.result.Result;
import net.rptools.lib.result.ResultBuilder;

/**
 * The Script DataValue used to represent floating point numbers.
 * 
 */
final class DoubleDataValue implements DataValue {

	/** The number that this DataValue represents. */
	private final double value;
	
	/**
	 * Creates a new DataValue representing the specified floating point
	 * number.
	 * 
	 * @param val The number to represent.
	 */
	DoubleDataValue(double val) {
		value = val;
	}
	
	@Override
	public String asString() {
		return Double.toString(value);
	}

	@Override
	public long asLong() {
		return (long) value;
	}

	@Override
	public double asDouble() {
		return value;
	}

	@Override
	public List<DataValue> asList() {
		return Collections.<DataValue>singletonList(this);
	}

	@Override
	public DataValue add(DataValue val) {
		DataValue newDataVal = null;
		switch (val.dataType()) {
			case LONG:
				newDataVal = DataValueFactory.doubleValue(asDouble() + val.asLong());
				break;
			case DOUBLE:
				newDataVal = DataValueFactory.doubleValue(asDouble() + val.asDouble());
				break;
			case STRING:
				newDataVal = DataValueFactory.stringValue(asString() + val.asString());
				break;
			case LIST:
				List<? extends DataValue> l1 = asList();
				List<? extends DataValue> l2 = val.asList();
				List<DataValue> lst = new ArrayList<DataValue>(l1.size() + l2.size());
				lst.addAll(l1);
				lst.addAll(l2);
				newDataVal = DataValueFactory.listValue(lst);
				break;
			case DICTIONARY:
				throw new NumberFormatException("Can not convert dictionary to a numeric value");
		}
		return newDataVal;
	}

	@Override
	public DataValue subtract(DataValue val) {
		DataValue newDataVal = null;
		switch (val.dataType()) {
			case LONG:
				newDataVal = DataValueFactory.doubleValue(asDouble() - val.asDouble());
				break;
			case DOUBLE:
				newDataVal = DataValueFactory.doubleValue(asDouble() - val.asDouble());
				break;
			case STRING:
				throw new NumberFormatException("Can not convert string to a numeric value");
			case LIST:
				throw new NumberFormatException("Can not convert list to a numeric value");
			case DICTIONARY:
				throw new NumberFormatException("Can not convert dictionary to a numeric value");
		}
		return newDataVal;
	}


	@Override
	public DataValue multiply(DataValue val) {
		DataValue newDataVal = null;
		switch (val.dataType()) {
			case LONG:
				newDataVal = DataValueFactory.doubleValue(asDouble() * val.asDouble());
				break;
			case DOUBLE:
				newDataVal = DataValueFactory.doubleValue(asDouble() * val.asDouble());
				break;
			case STRING:
				String s = val.asString();
				StringBuilder sb = new StringBuilder(s.length());
				for (long i = 0, times = (long) asLong(); i < times; i++) {
					sb.append(s);
				}
				newDataVal = DataValueFactory.stringValue(sb.toString());
				break;
			case LIST:
				throw new NumberFormatException("Can not convert list to a numeric value");
			case DICTIONARY:
				throw new NumberFormatException("Can not convert dictionary to a numeric value");
		}
		return newDataVal;	
	}

	@Override
	public DataValue divide(DataValue val) {
		DataValue newDataVal = null;
		switch (val.dataType()) {
			case LONG:
				newDataVal = DataValueFactory.doubleValue(asDouble() / val.asDouble());
				break;
			case DOUBLE:
				newDataVal = DataValueFactory.doubleValue(asDouble() / val.asDouble());
				break;
			case STRING:
				throw new NumberFormatException("Can not convert string to a numeric value");
			case LIST:
				throw new NumberFormatException("Can not convert list to a numeric value");
			case DICTIONARY:
				throw new NumberFormatException("Can not convert dictionary to a numeric value");
		}
		return newDataVal;	
	}

	@Override
	public DataValue remainder(DataValue val) {
		DataValue newDataVal = null;
		switch (val.dataType()) {
			case LONG:
				newDataVal = DataValueFactory.doubleValue(asDouble() % val.asDouble());
				break;
			case DOUBLE:
				newDataVal = DataValueFactory.doubleValue(asDouble() % val.asDouble());
				break;
			case STRING:
				throw new NumberFormatException("Can not convert string to a numeric value");
			case LIST:
				throw new NumberFormatException("Can not convert list to a numeric value");
			case DICTIONARY:
				throw new NumberFormatException("Can not convert dictionary to a numeric value");
		}
		return newDataVal;	
	}

	@Override
	public DataValue power(DataValue exp) {
		DataValue newDataVal = null;
		switch (exp.dataType()) {
			case LONG:
				newDataVal = DataValueFactory.doubleValue(Math.pow(asDouble(), exp.asDouble()));
				break;
			case DOUBLE:
				newDataVal = DataValueFactory.doubleValue(Math.pow(asDouble(), exp.asDouble()));
				break;
			case STRING:
				throw new NumberFormatException("Can not convert string to a numeric value");
			case LIST:
				throw new NumberFormatException("Can not convert list to a numeric value");
			case DICTIONARY:
				throw new NumberFormatException("Can not convert dictionary to a numeric value");
		}
		return newDataVal;	
	}	
	
	@Override
	public DataValue negate() {
		return DataValueFactory.doubleValue(-value);
	}



	@Override
	public DataType dataType() {
		return DataType.DOUBLE;
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
		
		DoubleDataValue other = (DoubleDataValue) obj;
		if (Double.doubleToLongBits(value) != Double.doubleToLongBits(other.value)) {
			return false;
		}
		
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(value);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		return this;
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
	public DataValue asResultValue() {
		return DataValueFactory.resultValue(asResult());
	}
}
