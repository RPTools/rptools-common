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
package net.rptools.lib.result;

import java.util.List;

import net.rptools.lib.datavalue.DataValue;

/**
 * The Result class represents values that may have additional information attached
 * to them such as a detailed explanation of the value and/or a list of individual
 * values that make up that the result.
 *
 */
public class Result {

	/** The actual result this represents. */
	private final DataValue value;
	/** Any detailed information about the result. */
	private final DataValue detailedResult;
	/** Any individual values that make up the result, e.g. dice rolls. */
	private final List<DataValue> values;
	/** The {@link RollExpression} if any that is attached to this result. */
	private final RollExpression rollExpression;
	
	/**
	 * Creates a new Result object.
	 * 
	 * @param val The value to be represented.
	 * @param details Any detailed information that helps explain the result.
	 * @param vals Any individual values that go with the result.
	 */
	Result(DataValue val, DataValue details, List<DataValue> vals, RollExpression rexpr) {
		value = val;
		detailedResult = details;
		values = vals;
		rollExpression = rexpr;
	}
	
	/**
	 * Returns the value represented by this object.
	 * 
	 * @return the value represented result.
	 */
	public DataValue getValue() {
		return value;
	}
	
	/**
	 * Returns the detailed information associated with the value.
	 * 
	 * @return the detailed information.
	 */
	public DataValue getDetailedResult() {
		return detailedResult;
	}
	
	/**
	 * Returns the individual values associated with the result.
	 * 
	 * @return the individual values.
	 */
	public List<DataValue> getValues() {
		return values;
	}
	
	/**
	 * Checks to see if there is a {@link RollExpression} is attached to
	 * this Result.
	 *  
	 * @return true if there is a a {@link RollExpression} attached to
	 *         this Result.
	 */
	public boolean hasRollExpression() {
		return rollExpression != null;
	}
	
	/**
	 * Returns the {@link RollExpression} attached to this Result.
	 * 
	 * @return the {@link RollExpression} attached to this Result.
	 */
	public RollExpression getRollExpression() {
		return rollExpression;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((detailedResult == null) ? 0 : detailedResult.hashCode());
		result = prime * result
				+ ((this.value == null) ? 0 : this.value.hashCode());
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
		Result other = (Result) obj;
		if (detailedResult == null) {
			if (other.detailedResult != null)
				return false;
		} else if (!detailedResult.equals(other.detailedResult))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}

	
	
}
