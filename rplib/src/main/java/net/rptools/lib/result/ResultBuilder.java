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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.lib.datavalue.DataType;

/**
 * The ResultBuilder class is used to build a immutable {@link Result} object.
 * 
 * When creating the {@link Result} with the {@link #toString()} method if the
 * detailed value or the individual values are not set then they will be set
 * to the value of the {@link Result}. The value must always be specified to
 * create a valid {@link Result} object.
 *
 */
public class ResultBuilder {

	/** The actual result this represents. */
	private DataValue value;
	/** Any detailed information about the result. */
	private DataValue detailedResult;
	/** Any individual values that make up the result, e.g. dice rolls. */
	private List<DataValue> values = new ArrayList<>();
	/** The {@link RollExpression} if any that is attached to this result. */
	private RollExpression rollExpression;

	
	/**
	 * Sets the value for the {@link Result} that this will create.
	 * 
	 * @param val The value of the {@link Result}.
	 * @return {@code this} so that methods can be chained.
	 */
	public ResultBuilder setValue(DataValue val) {
		value = val;
		return this;
	}

	/**
	 * Sets the long value for the {@link Result} that this will create.
	 * 
	 * @param val The value of the {@link Result}.
	 * @return {@code this} so that methods can be chained.
	 */
	public ResultBuilder setLongValue(long val) {
		value = DataValueFactory.longValue(val);
		return this;
	}
	
	
	/**
	 * Sets the double value for the {@link Result} that this will create.
	 * 
	 * @param val The value of the {@link Result}.
	 * @return {@code this} so that methods can be chained.
	 */
	public ResultBuilder setDoubleValue(double val) {
		value = DataValueFactory.doubleValue(val);
		return this;
	}
	
	/**
	 * Sets the list value for the {@link Result} that this will create.
	 * 
	 * @param vals The value of the {@link Result}.
	 * @return {@code this} so that methods can be chained.
	 */
	public ResultBuilder setListValue(Collection<DataValue> vals) {
		value = DataValueFactory.listValue(vals);
		return this;
	}
	
	/**
	 * Sets the String value for the {@link Result} that this will create.
	 * 
	 * @param val The value of the {@link Result}.
	 * @return {@code this} so that methods can be chained.
	 */
	public ResultBuilder setStringValue(String val) {
		value = DataValueFactory.stringValue(val);
		return this;
	}
	
	/**
	 * Sets the {@link DataType#DICTIONARY} value for the {@link Result} that 
	 * this will create.
	 * 
	 * @param vals The value of the {@link Result}.
	 * @return {@code this} so that methods can be chained.
	 */
	public ResultBuilder setDictionayrValue(Map<String, DataValue> vals) {
		value = DataValueFactory.dictionaryValue(vals);
		return this;
	}
	
	/**
	 * Sets the detailed result for the {@link Result} that this will create.
	 * 
	 * @param val The detailed result of the {@link Result}.
	 * @return {@code this} so that methods can be chained.
	 */
	public ResultBuilder setDetailedResult(DataValue val) {
		detailedResult = val;
		return this;
	}
	
	/**
	 * Sets the individual values that make up the {@link Result} that this will
	 * create.
	 * 
	 * @param vals The individual values that make up the {@link Result}.
	 * 
	 * @return {@code this} so that methods can be chained.
	 */
	public ResultBuilder setIndividualValues(Collection<DataValue> vals) {
		values.clear();
		values.addAll(vals);
		return this;
	}
	
	
	/**
	 * Sets the {@link RollExpression} that is associated with the 
	 * {@link Result} this will create.
	 * 
	 * @param rexpr The {@link RollExpression} associated with the
	 *        {@link Result}.
	 * @return {@code this} so that methods can be chained.
	 */
	public ResultBuilder setRollExpression(RollExpression rexpr) {
		rollExpression = rexpr;
		return this;
	}
	
	/**
	 * Builds the {@link Result} object using the parameters set in
	 * this ResultBuilder.
	 * 
	 * @return the {@link Result}
	 * @throws NullPointerException if the value has not been set.
	 */
	public Result toResult() {
		if (value == null) {
			throw new NullPointerException("Value for Result can not be null.");
		}
		
		if (detailedResult == null) {
			detailedResult = value;
		}
		
		if (values.size() == 0) {
			values.add(value);
		}
		
		return new Result(detailedResult, detailedResult, values, rollExpression);
	}
	
	
}
