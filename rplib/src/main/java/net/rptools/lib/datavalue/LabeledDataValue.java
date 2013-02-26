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

import java.util.List;
import java.util.Map;

import net.rptools.lib.result.Result;

/**
 * The class that represents script values that have had a label attached.
 * This class simply decorates the original DataValue with the label.
 * 
 * Note: Performing any mathematical operation on the DataValue will return
 *       a DataValue that is not labeled as its a new value and not the
 *       value that the script originally asked to be labeled. This is
 *       also true for any asX coercion methods (with the possible exception
 *       of when you try to coerce the type to something it already is).
 *
 */
final class LabeledDataValue implements DataValue {

	
	/** The label attached to the value. */
	private final DataLabel label;
	
	/** The value to decorate with the label. */
	private final DataValue value;
	
	
	/**
	 * Creates a new LabeledDataValue.
	 * 
	 * @param val The value to label.
	 * @param l The label to apply.
	 */
	LabeledDataValue(DataValue val, DataLabel l) {
		value = val;
		label = l;
	}
	
	@Override
	public String asString() {
		return value.asString();
	}

	@Override
	public long asLong() {
		return value.asLong();
	}

	@Override
	public double asDouble() {
		return value.asDouble();
	}

	@Override
	public List<DataValue> asList() {
		return value.asList();
	}

	@Override
	public DataValue add(DataValue val) {
		return value.add(val); // Note we don't wrap this in a LabeledDataType
	}

	@Override
	public DataValue subtract(DataValue val) {
		return value.subtract(val); // Note we don't wrap this in a LabeledDataType
	}

	@Override
	public DataValue multiply(DataValue val) {
		return value.multiply(val); // Note we don't wrap this in a LabeledDataType

	}

	@Override
	public DataValue divide(DataValue val) {
		return value.divide(val); // Note we don't wrap this in a LabeledDataType

	}

	@Override
	public DataValue remainder(DataValue val) {
		return value.remainder(val); // Note we don't wrap this in a LabeledDataType

	}

	@Override
	public DataValue power(DataValue exp) {
		return value.power(exp); // Note we don't wrap this in a LabeledDataType
	}

	@Override
	public boolean hasLabel() {
		return true;
	}

	@Override
	public DataLabel getLabel() {
		return label;
	}

	@Override
	public DataType dataType() {
		return value.dataType();
	}

	@Override
	public DataValue negate() {
		return value.negate(); // Note we don't wrap this in a LabeledDataType
	}

	@Override
	public String toString() {
		return dataType().toString() + ": " + asString() + "{Label: " + label.getName() + "," + label.getIndex() + "}";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
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
		
		LabeledDataValue other = (LabeledDataValue) obj;
		if (label == null) {
			if (other.label != null) {
				return false;
			}
		} else if (!label.equals(other.label)) {
			return false;
		}
		
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
	public DataValue asLongValue() {
		return DataValueFactory.longValue(asLong());
	}

	@Override
	public DataValue asDoubleValue() {
		return DataValueFactory.doubleValue(asLong());
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
		return value.asDictionary();
	}

	@Override
	public DataValue asDictionaryValue() {
		return DataValueFactory.dictionaryValue(asDictionary());
	}

	@Override
	public Result asResult() {
		return value.asResult();
	}

	@Override
	public DataValue asResultValue() {
		return DataValueFactory.resultValue(asResult());
	}

}
