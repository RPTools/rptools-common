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

/**
 * Enumeration that represents the types of DataValues used in scripts.
 * 
 */
public enum DataType {

	/** Integer script values. */
	LONG 		{ public DataValue coerce(DataValue val) {return val.asLongValue();} },
	/** Floating point script values. */
	DOUBLE 		{ public DataValue coerce(DataValue val) {return val.asDoubleValue();} },
	/** String script values. */
	STRING		{ public DataValue coerce(DataValue val) {return val.asStringValue();} },
	/** List script values. */
	LIST		{ public DataValue coerce(DataValue val) {return val.asListValue();} },
	/** Dictionary script values. */
	DICTIONARY	{ public DataValue coerce(DataValue val) {return val.asDictionaryValue();} },
	/** Result script values. */
	RESULT 		{ public DataValue coerce(DataValue val) {return val.asResultValue();} },
    /** Boolean script values. */
    BOOLEAN     { public DataValue coerce(DataValue val) {return val.asBooleanValue();} },
	/**
	 * Any data pseudo data type, this is not a type supported by the script but an
	 * indication that any data type is valid. There is no object that can be instantiated
	 * with this type. Attempting to coerce any DataValue to this type will return the 
	 * DataValue you attempted to coerce as by definition its already the type wanted.
	 */
	ANY 		{ public DataValue coerce(DataValue val) {return val; } },
    NULL        { public DataValue coerce(DataValue val) {return DataValueFactory.nullDataValue();} }
    ;


	/** Capitalized printable name for the type. */
	private final String capatalized;
	
	/**
	 * Creates the enumerated value.
	 */
	private DataType() {
		// For debuging more than anything else so no need for i18n
		capatalized = name().substring(0,1).toUpperCase() + name().substring(1).toLowerCase();
	}
	
	@Override
	public String toString() {
		return capatalized;
	}
	
	/** 
	 * Returns a DataValue of this type representing the specified data value if conversion
	 * is possible.
	 * 
	 * @param val The DataValue to convert to this type.
	 * 
	 * @return A DataValue of this type that represents the specified value.
	 * 
	 * @throws NumberFormatException if trying to ({@link #LONG} or {@link #DOUBLE}) and
	 *                               the value can not be converted to a number.
	 * @throws UnsupportedOperationException if value can not be converted to this type.
	 */
	public abstract DataValue coerce(DataValue val);
}
