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
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.rptools.lib.result.Result;
import net.rptools.lib.result.ResultBuilder;

/**
 * The ListDataValue represents scripting values that are a list 
 * data type. The list contains DataValues and their types need
 * not be Homogeneous.
 *
 */
final class ListDataValue implements DataValue {

	/** The list of DataValues */
	private final List<DataValue> value;

	/**
	 * Creates a new ListDataType containing the specified values.
	 * 
	 * @param vals The values to add to the list.
	 */
	ListDataValue(Collection<? extends DataValue> vals) {
		List<DataValue> lst = new ArrayList<>(vals.size());
		lst.addAll(vals);
		value = Collections.unmodifiableList(lst);
	}

	@Override
	public String asString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		boolean first = true;
		for (DataValue dv : value) {
			if (first == false) {
				sb.append(", ");
			} else {
				first = false;
			}
			sb.append(dv.asString());
		}
		sb.append(" ]");
		return sb.toString();
	}

	@Override
	public long asLong() {
		throw new NumberFormatException("Can not convert list to a numeric value");
	}

	@Override
	public double asDouble() {
		throw new NumberFormatException("Can not convert list to a numeric value");
	}

	@Override
	public List<DataValue> asList() {
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
		throw new UnsupportedOperationException("Can not convert list to a numeric value");
	}

	@Override
	public DataType dataType() {
		return DataType.LIST;
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
	public boolean equals(Object o) {
		boolean eq = false;
		if (o != null) {
			if (getClass() == o.getClass()) {

				final DataValue dv = (DataValue) o;
				final List<DataValue> lst = dv.asList();
				if (value.size() == lst.size()) {
					final Iterator<DataValue> viter = value.iterator();
					final Iterator<DataValue> liter = lst.iterator();
					eq = true;

					while (viter.hasNext()) {
						final DataValue dv1 = viter.next();
						final DataValue dv2 = liter.next();

						if (dv1.equals(dv2) == false) {
							eq = false;
							break;
						}
					}
				}
			}
		}
		return eq;
	}

	@Override
	public int hashCode() {
		int hash = 31;
		for (DataValue dv : value) {
			hash = hash + 31 * dv.hashCode();
		}
		return hash;
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
		return this;
	}

	@Override
	public Map<String, DataValue> asDictionary() {
		throw new UnsupportedOperationException("Can not a list value to a dictionary.");
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
        return value.size() != 0;
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
