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
package net.rptools.parser.functions;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataType;

/**
 * Represents parameters for script functions.
 *
 */
public class FunctionParameter {
	/** The name of the parameter. */
	private final String name;
	
	/** The Data type of the parameter. */
	private final DataType dataType;
	
	/** The default value (if any) for the parameter. */
	private final DataValue defaultValue;
	
	/** Does this parameter consume all the unknown arguments. */
	private final boolean consumeRemaining;

	
	/**
	 * Creates a FunctionParameter object.
	 * 
	 * @param paramName The name of the function parameter. 
	 * @param dtype The data type of the function parameter.
	 * @param defValue The default value for the function parameter.
	 */
	private FunctionParameter(String paramName, DataType dtype, DataValue defValue) {
		assert paramName != null : "Parameter name can not be null";
		assert dtype != null : "Dataype can not be null";
		assert defValue != null : "Default value can not be null";
		
		name = paramName;
		dataType = dtype;
		defaultValue = defValue;
		consumeRemaining = false;
	}

	/**
	 * Creates a FunctionParameter object.
	 * @param paramName The name of the function parameter.
	 * @param dtype The data type of the function parameter.
	 * @param consume should the parameter consume the extra arguments.
	 */
	private FunctionParameter(String paramName, DataType dtype, boolean consume) {
		assert paramName != null : "Parameter name can not be null";
		assert dtype != null : "Dataype can not be null";

		name = paramName;
		dataType = dtype;
		defaultValue = null;
		consumeRemaining = consume;
	}
	
	/**
	 * Creates a FunctionParameter object that has a default value.
	 * 
	 * @param name The name of the function parameter.
	 * @param dataType The data type for the function parameter.
	 * @param defaultValue The default value for the function parameter.
	 * 
	 * @return The FunctionParamter object.
	 * 
	 * @throws NullPointerException if any of the arguments are null.
	 */
	public static FunctionParameter createWithDefault(String name, DataType dataType, DataValue defaultValue) {
		if (name == null) {
			throw new NullPointerException("Name of function parameter can not be null.");
		}
		
		if (dataType == null) {
			throw new NullPointerException("Data type of function parameter can not be null.");
		}
		
		if (defaultValue == null) {
			throw new NullPointerException("Defined default value for function paramter can not be null.");
		}
		
		return new FunctionParameter(name, dataType, defaultValue);
	}
	
	/**
	 * Creates a FunctionParameter object that has no default value.
	 * 
	 * @param name The name of the function parameter.
	 * @param dataType The data type for the function parameter.
	 * 
	 * @return the FunctionParamter object.
	 * 
	 * @throws NullPointerException if any of the arguments are null.
	 */
	public static FunctionParameter create(String name, DataType dataType) {
		if (name == null) {
			throw new NullPointerException("Name of function parameter can not be null.");
		}
		
		if (dataType == null) {
			throw new NullPointerException("Data type of function parameter can not be null.");
		}
		
		return new FunctionParameter(name, dataType, false);
	}
	
	/**
	 * Creates a FunctionParameter that will consume extra positional arguments as a list.
	 * 
	 * @param name Then name of the function parameter.
	 * 
	 * @return the FunctionParameter object.
	 * 
	 * @throws NullPointerException if the name is null.
	 */
	public static FunctionParameter createConsumeRemainingAsList(String name) {
		if (name == null) {
			throw new NullPointerException("Name of function parameter can not be null.");
		}
		
		return new FunctionParameter(name, DataType.LIST, true);
	}
	
	/**
	 * Creates a FunctionParameter that will consume extra positional arguments as a dictionary.
	 * 
	 * @param name Then name of the function parameter.
	 * 
	 * @return the FunctionParameter object.
	 * 
	 * @throws NullPointerException if the name is null.
	 */
	public static FunctionParameter createConsumeRemainingAsDictionary(String name) {
		if (name == null) {
			throw new NullPointerException("Name of function parameter can not be null.");
		}
		
		return new FunctionParameter(name, DataType.DICTIONARY, true);
	}
		
	/**
	 * Returns the name of the function parameter.
	 * 
	 * @return the name of the function parameter.
	 */
	public String name() {
		return name;
	}
	
	/**
	 * Returns the data type of the function parameter.
	 * 
	 * @return the data type of the function parameter.
	 */
	public DataType dataType() {
		return dataType;
	}
	
	/**
	 * Checks the function parameter to see if it has a default value.
	 * 
	 * @return true if the function parameter has a default value.
	 */
	public boolean hasDefaultValue() {
		return defaultValue != null;
	}
	
	/**
	 * Returns the default value for the function parameter.
	 * 
	 * @return the default value for the function parameter.
	 */
	public DataValue getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Checks to see if the parameter will consume the remaining arguments
	 * after all parameters have been matched. 
	 * 
	 * @return true if the parameter will consume remaining arguments.
	 */
	public boolean consumesRemaining() {
		return consumeRemaining;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (consumeRemaining ? 1231 : 1237);
		result = prime * result
				+ ((dataType == null) ? 0 : dataType.hashCode());
		result = prime * result
				+ ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		FunctionParameter other = (FunctionParameter) obj;
		if (consumeRemaining != other.consumeRemaining)
			return false;
		if (dataType != other.dataType)
			return false;
		if (defaultValue == null) {
			if (other.defaultValue != null)
				return false;
		} else if (!defaultValue.equals(other.defaultValue))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}
