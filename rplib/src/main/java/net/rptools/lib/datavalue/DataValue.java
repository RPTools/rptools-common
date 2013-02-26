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
 * Represents the values returned by script operators or functions.
 * This interface is used to represent all of the different types that 
 * scripting operators can return as well as methods that can be used
 * to perform these operations. All scripting functions will also return
 * a DataValue. 
 * 
 * DataValue objects created using {@link DataValueFactory} are guaranteed 
 * be immutable, any new class that you create that implements this
 * interface should also be immutable to avoid unpredictable behavior. 
 * 
 */
public interface DataValue {
	
	/**
	 * Returns the {@link DataType} for this DataValue.
	 * 
	 * @return the {@link DataType} of the value.
	 */
	public DataType dataType();

	/**
	 * Returns the value of this DataValue as a {@link String}. 
	 * 
	 * Note this is different than the {@code toString()} method which 
	 * returns the String representation of the DataValue not the String 
	 * representation of the value that it holds.
	 * 
	 * @return the String representation of the value held by this DataValue.
	 */
	public String asString();

	/**
	 * Returns the value of this DataValue as a long. 
	 * 
	 * @return the long representation of this value.
	 * 
	 * @throws NumberFormatException if the value can not be converted to a number.
	 * 
	 */
	public long asLong();

	/**
	 * Returns the value of this DataValue as a double. 
	 * 
	 * @return the double representation of this value.
	 * 
	 * @throws NumberFormatException if the value can not be converted to a number.
	 * 
	 */
	public double asDouble();

	/**
	 * Returns the value of this DataValue as a {@link List}. If the DataValue is not
	 * of type {@link DataType#LIST} then a @{link {@link List} created containing
	 * the single value if it is a scalar type.
	 * 
	 * It should be assumed that the List returned is unmodifiable.
	 * 
	 * @return a {@link List} representation of the value.
	 * 
	 * @throws UnsupportedOperationException if the value can not be represented by a {@link List}.
	 */
	public List<DataValue> asList();
	
	/**
	 * Returns a {@link Map} representation of the DataValue. 
	 * It should be assumed that the Map returned is unmodifiable.
	 * 
	 * @return the {@link Map} representation of the DataValue.
	 * 
	 * @throws UnsupportedOperationException if the value can not be represented by a {@link Map}.
	 * 
	 */
	public Map<String, DataValue> asDictionary();
	

	/**
	 * Returns a {@link Result} representation of the DataValue.
	 * 
	 * @return the {@link Result} representation of the DataValue.
	 * 
	 * @throws UnsupportedOperationException if value can not be converted to a {@link Result}.
	 */
	public Result asResult();

	
	/**
	 * Returns the result of a second DataValue added to this DataValue.
	 * 
	 * @param val the DataValue to add to this DataValue.
	 * 
	 * @return a new DataValue with the result.
	 * 
	 * @throws NumberFormatException if either value can not be converted to a number.
	 * @throws UnsupportedOperationException if addition is not supported on either value.
	 */
	public DataValue add(DataValue val);

	/**
	 * Returns the result of a second DataValue subtracted from this DataValue.
	 * 
	 * @param val the DataValue to subtract from this DataValue.
	 * 
	 * @return a new DataValue with the result.
	 * 
	 * @throws NumberFormatException if either value can not be converted to a number.
	 * @throws UnsupportedOperationException if subtraction is not supported on either value.
	 * 
	 */	
	public DataValue subtract(DataValue val);

	/**
	 * Returns the result of this DataValue multiplied by a second DataValue.
	 * 
	 * @param val the DataValue to multiply this DataValue by.
	 * 
	 * @return a new DataValue with the result.
	 * 
	 * @throws NumberFormatException if either value can not be converted to a number.
	 * @throws UnsupportedOperationException if multiplication is not supported on either value.
	 * 
	 */	
	public DataValue multiply(DataValue val);

	/**
	 * Returns the result of this DataValue divided by a second DataValue.
	 * 
	 * @param val the DataValue to divide this DataValue by.
	 * 
	 * @return a new DataValue with the result.
	 * 
	 * @throws NumberFormatException if either value can not be converted to a number.
	 * @throws UnsupportedOperationException if division is not supported on either value.
	 * 
	 */	
	public DataValue divide(DataValue val);

	/**
	 * Returns the remainder when this DataValue is divided by a second DataValue.
	 * 
	 * @param val the DataValue to divide this DataValue by.
	 * 
	 * @return a new DataValue with the result.
	 * 
	 * @throws NumberFormatException if either value can not be converted to a number.
	 * @throws UnsupportedOperationException if division is not supported on either value.
	 * 
	 */	
	public DataValue remainder(DataValue val);

	/**
	 * Returns the result of raising this DataValue raised to the power of a second DataValue.
	 * 
	 * @param exp the DataValue to raise this DataValue by the power of.
	 * 
	 * @return a new DataValue with the result.
	 * 
	 * @throws NumberFormatException if either value can not be converted to a number.
	 * @throws UnsupportedOperationException if exponentiation is not supported on either value.
	 * 
	 */	
	public DataValue power(DataValue exp);

	/**
	 * Returns the negated value of this DataValue.
	 * 
	 * @return a new DataValue with the result.
	 * 
	 * @throws NumberFormatException if either value can not be converted to a number.
	 * @throws UnsupportedOperationException if negation is not supported on either value.
	 * 
	 */	
	public DataValue negate();
	
	/**
	 * Returns {@code true} if this DataValue has a label attached to it.
	 * 
	 * @return {@code true} if there is a label attached to this DataValue.
	 */
	public boolean hasLabel();
	
	/** 
	 * The {@link DataLabel} attached to this DataValue.
	 * 
	 * If this DataValue has no label ({@link #hasLabel()} returns {@code false}) then
	 * {@link DataLabel#NO_LABEL} is returned.
	 * 
	 * @return the {@link DataLabel} attached to this DataValue.
	 */
	public DataLabel getLabel();

	/**
	 * Convert this and return it as a {@link DataType#LONG} DataValue.
	 * 
	 * @return the {@link DataType#LONG} DataValue.
	 * 
	 * @throws NumberFormatException if either value can not be converted to a number.
	 * 
	 * @see #asLong().
	 * 
	 */
	public DataValue asLongValue();

	/**
	 * Convert this and return it as a {@link DataType#DOUBLE} DataValue.
	 * 
	 * @return the {@link DataType#DOUBLE} DataValue.
	 * 
	 * @throws NumberFormatException if either value can not be converted to a number.
	 * 
	 * @see #asDouble().
	 */
	public DataValue asDoubleValue();
	
	/**
	 * Convert this and return it as a {@link DataType#STRING} DataValue.
	 * 
	 * @return the {@link DataType#STRING} DataValue.
	 * 
	 * 
	 * @see #asList().
	 * 
	 */
	public DataValue asStringValue();
	
	/**
	 * Convert this and return it as a {@link DataType#LIST} DataValue.
	 * 
	 * @return the {@link DataType#LIST} DataValue.
	 * 	
	 * @throws UnsupportedOperationException if the value can not be represented
	 *  	   by a {@link DataType#LIST}.
	 *  
	 * @throws UnsupportedOperationException if the value can not be represented by a 
	 *         {@link DataType#LIST}.
	 *  
	 *  @see #asList().
	 *  
	 */	
	public DataValue asListValue();
	
	/**
	 * Convert this and return it as a {@link DataType#DICTIONARY} DataValue.
	 * 
	 * @return the {@link DataType#DICTIONARY} DataValue.
	 * 	
	 * @throws UnsupportedOperationException if the value can not be represented
	 *  	   by a {@link DataType#DICTIONARY}.
	 *  
	 * @throws UnsupportedOperationException if the value can not be represented by a
	 *         {@link DataType#DICTIONARY}.
	 *  
	 * @see #asDictionary().
	 */		
	public DataValue asDictionaryValue();

	/**
	 * Convert this and return it as a {@link DataType#RESULT} DataValue.
	 * 
	 * @return the {@link DataType#RESULT} DataValue.
	 * 
	 * @see #asResult().
	 * 	
	 * @throws UnsupportedOperationException if the value can not be represented by a
	 *         {@link DataType#RESULT}.
	 *         
	 */	
	public DataValue asResultValue();
	
}
