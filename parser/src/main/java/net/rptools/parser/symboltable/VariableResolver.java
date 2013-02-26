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
package net.rptools.parser.symboltable;

import java.util.Collection;

import net.rptools.lib.datavalue.DataValue;

/**
 * Interface describing an object that can be used by the script engine to lookup and set
 * variables. 
 *
 */
public interface VariableResolver {
	/**
	 * Sets a variable.
	 * 
	 * @param name The name of the variable to set.
	 * @param value The value to set the variable to.
	 * 
	 * @throws NullPointerException if any of the parameters are null.
	 */
	public void setVariable(String name, DataValue value);
	
	/**
	 * Returns the value of a variable.
	 * 
	 * @param name The name of the variable to get the value of.
	 * 
	 * @return the value of the variable.
	 * 
	 * @throws NullPointerException if name is null.
	 */
	public DataValue getVariable(String name);

	/**
	 * Checks to see if the variable has been set.
	 * 
	 * @param name The name of the variable.
	 * 
	 * @return true if the variable name has been set.
	 * 
	 * @throws NullPointerException if the name is null.
	 */
	public boolean containsVariable(String name);
		

	/**
	 * Returns the defined variable names.
	 * 
	 * @return the names of the defined variables.
	 */
	public Collection<String> getVariableName();
}
