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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.rptools.lib.result.Result;
import net.rptools.lib.result.ResultBuilder;

/**
 * The DictionaryDataValue class represents script values that map names to 
 * other DataValues.
 * 
 */
final class DictionaryDataValue implements DataValue {
	
	/** The name to DataValue map. */
	private final Map<String, DataValue> values;
	
	/**
	 * Creates a DataValue with the name to DataValue mapping specified.
	 * 
	 * @param vals The {@link Map} containing the name/DataType relationships.
	 */
	DictionaryDataValue(Map<String, DataValue> vals) {
		Map<String, DataValue> newMap = new LinkedHashMap<String, DataValue>();
		newMap.putAll(vals);
		values = Collections.unmodifiableMap(newMap);
	}
	
	@Override
	public String asString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{ ");
		boolean first = true;
		for (String key : values.keySet()) {
			if (first == false) {
				sb.append(", ");
			} else {
				first = false;
			}
			sb.append(key);
			sb.append(":");
			sb.append(values.get(key).asString());
		}
		sb.append(" }");
		return sb.toString();
	}

	@Override
	public long asLong() {
		throw new NumberFormatException("Can not convert dictionary to a numeric value");
	}

	@Override
	public double asDouble() {
		throw new NumberFormatException("Can not convert dictionary to a numeric value");
	}

	@Override
	public List<DataValue> asList() {
        return Collections.<DataValue>singletonList(this);
	}

	@Override
	public DataValue add(DataValue val) {
		throw new UnsupportedOperationException("Mathematical operations not supported on dictionaries.");
	}

	@Override
	public DataValue subtract(DataValue val) {
		throw new UnsupportedOperationException("Mathematical operations not supported on dictionaries.");
	}


	@Override
	public DataValue multiply(DataValue val) {
		throw new UnsupportedOperationException("Mathematical operations not supported on dictionaries.");
	}

	@Override
	public DataValue divide(DataValue val) {
		throw new UnsupportedOperationException("Mathematical operations not supported on dictionaries.");
	}

	@Override
	public DataValue remainder(DataValue val) {
		throw new UnsupportedOperationException("Mathematical operations not supported on dictionaries.");
	}

	@Override
	public DataValue power(DataValue exp) {
		throw new UnsupportedOperationException("Mathematical operations not supported on dictionaries.");
	}	
	
	@Override
	public DataValue negate() {
		throw new UnsupportedOperationException("Mathematical operations not supported on dictionaries.");
	}



	@Override
	public DataType dataType() {
		return DataType.DICTIONARY;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DictionaryDataValue other = (DictionaryDataValue) obj;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
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
		return DataValueFactory.stringValue(asString());
	}

	@Override
	public DataValue asListValue() {
		return DataValueFactory.listValue(asList());
	}

	@Override
	public Map<String, DataValue> asDictionary() {
		return values;
	}

	@Override
	public DataValue asDictionaryValue() {
		return this;
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
