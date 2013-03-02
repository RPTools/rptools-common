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
 * The ResultDataValue represents the a {@link Result} script value.
 *
 * Any operations performed on the ResultDataValue will return a new
 * DataValue of the same type as the {@link Result} value, not a new
 * ResultDataValue.
 * 
 */
final class ResultDataValue implements DataValue {

	/** The {@link Result} that this DataValue represents. */
	private final Result result;
	
	/** The result to be used in calculations. */
	private final DataValue value;
	
	/**
	 * Creates a new ResultDataValue object. 
	 * 
	 * @param res The {@link Result} that this DataValue will 
	 *            represent.w
	 */
	ResultDataValue(Result res) {
		result = res;
		
		if (res.hasRollExpression()) {
			if (res.getRollExpression().isSum()) {
				value = res.getValue();
			} else {
				value = DataValueFactory.listValue(res.getValues());				
			}
		} else {
			value = res.getValue();
		}
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
		return value.add(val); 
	}

	@Override
	public DataValue subtract(DataValue val) {
		return value.subtract(val);
	}

	@Override
	public DataValue multiply(DataValue val) {
		return value.multiply(val);

	}

	@Override
	public DataValue divide(DataValue val) {
		return value.divide(val);

	}

	@Override
	public DataValue remainder(DataValue val) {
		return value.remainder(val);

	}

	@Override
	public DataValue power(DataValue exp) {
		return value.power(exp);
	}

	@Override
	public boolean hasLabel() {
		return value.hasLabel();
	}

	@Override
	public DataLabel getLabel() {
		return value.getLabel();
	}

	@Override
	public DataType dataType() {
		return DataType.RESULT;
	}

	@Override
	public DataValue negate() {
		return value.negate();
	}

	@Override
	public String toString() {
		return "Data Value " + result.toString();
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
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
		ResultDataValue other = (ResultDataValue) obj;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		return true;
	}

	@Override
	public Map<String, DataValue> asDictionary() {
		return result.getValue().asDictionary();
	}

	@Override
	public DataValue asDictionaryValue() {
		return DataValueFactory.dictionaryValue(asDictionary());
	}

	@Override
	public Result asResult() {
		return result;
	}

	@Override
	public DataValue asResultValue() {
		return this;
	}

}
