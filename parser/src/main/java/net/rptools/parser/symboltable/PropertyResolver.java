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
import net.rptools.lib.datavalue.DataType;

/**
 * Interface describing an object that can be used by the script engine to lookup and set
 * properties. 
 * 
 * The script engine imposes no meaning on the concept of properties and it is up to 
 * the module calling the script engine to define the semantics. This is also true for
 * the id for a property set which can be used to map properties to different data stores
 * or name spaces.
 *
 */
public interface PropertyResolver {	
	
	/**
	 * Sets the value of the specified property for the default id.
	 * 
	 * @param name The name of the property to set.
	 * @param value The value to set.
	 * 
	 * @throws NullPointerException if any of the parameters are null.
	 * @throws IllegalArgumentException if there is no default id or the data
	 * 		   type is not valid for the property.
	 */
	public void setProperty(String name, DataValue value);

	/**
	 * Returns the value of the specified property for the default id.
	 * 
	 * @param name The name of the property to get.
	 * 
	 * @return the value of the property.
	 * 
	 * @throws NullPointerException if any of the parameters are null.
	 * @throws IllegalArgumentException if there is no default id or the data
	 * 		   type is not valid for the property.
	 */
	public DataValue getProperty(String name);

	/**
	 * Checks to see if the specified property is valid for the default id.
	 * 
	 * @param name The name of the property.
	 * 
	 * @return true if the property name is valid.
	 * 
	 * @throws NullPointerException if any of the parameters are null.
	 * @throws IllegalArgumentException if there is no default id or the data
	 * 		   type is not valid for the property.
	 */
	public boolean containsProperty(String name);
	
	
	/**
	 * Checks to see if a property can be set to a certain data type or that data
	 * type can be converted to the property types expected data value for the default
	 * id.
	 * 
	 * @param name The name of the property. 
	 * @param type The data type that you want to set the property to.
	 * 
	 * @return true if the property can be set to this data type.
	 * 
	 * @throws NullPointerException if any of the parameters are null.
	 * @throws IllegalArgumentException if there is no default id.
	 */
	public boolean canBeSetTo(String name, DataType type);
	
	/**
	 * Returns the data type that the property expects. This may differ to the value
	 * of the current value in the property if the property accepts the data type 
	 * {@link DataType#ANY}.
	 * 
	 * @param name The name of the property. 
	 * 
	 * @return the data type of the property.
	 */
	public DataType dataType(String name);
	
	/**
	 * Gets the property names for the default id.
	 * 
	 * @return the property names defined for the default id.
	 */
	public Collection<String> getPropertyNames();
	
	/**
	 * Sets the value of the specified property for the specified.id.
	 * 
	 * @param id The id for the property set.
	 * @param name The name of the property to set.
	 * @param value The value to set.
	 * 
	 * @throws NullPointerException if any of the parameters are null..
	 * @throws IllegalArgumentException if the id does not exist.
	 */
	public void setProperty(String id, String name, DataValue value);
	
	/**
	 * Returns the value of the specified property for the specified id.
	 * 
	 * @param id The id for the property set.
	 * @param name The name of the property to get.
	 * 
	 * @return the value of the property.
	 * 
	 * @throws NullPointerException if any of the parameters are null.
	 * @throws IllegalArgumentException if the id does not exist.
	 */
	public DataValue getProperty(String id, String name);
	
	/**
	 * Checks to see if the specified property is valid for the specified id.
	 * 
	 * @param id The id for the property set.
	 * @param name The name of the property.
	 * 
	 * @return true if the property name is valid.
	 * 
	 * @throws NullPointerException if any of the parameters are null.
	 * @throws IllegalArgumentException if the id does not exist.
	 */
	public boolean containsProperty(String id, String name);
	
	/**
	 * Checks to see if a property can be set to a certain data type or that data
	 * type can be converted to the property types expected data value.
	 * 
	 * @param id The id for the property set.
	 * @param name The name of the property. 
	 * @param type The data type that you want to set the property to.
	 * 
	 * @return true if the property can be set to this data type.
	 * 
	 * @throws NullPointerException if any of the parameters are null.
	 * @throws IllegalArgumentException if the id does not exist.
	 */
	public boolean canBeSetTo(String id, String name, DataType type);
	
	/**
	 * Returns the data type that the property expects. This may differ to the value
	 * of the current value in the property if the property accepts the data type 
	 * {@link DataType#ANY}.
	 * 
	 * @param id The id for the property set.
	 * @param name The name of the property. 
	 * 
	 * @return the data type of the property.
	 * 	 
	 * @throws NullPointerException if any of the parameters are null.
	 * @throws IllegalArgumentException if the id does not exist.
	 */
	public DataType dataType(String id, String name);
	
	/**
	 * Gets the property names.
	 * 
	 * @param id The id for the property set.
	 * 
	 * @throws NullPointerException if the id is null.
	 * @throws IllegalArgumentException if the id does not exist.
	 */
	public Collection<String> getPropertyNames(String id);
	
	/**
	 * Checks to see if there is a default id defined. 
	 * 
	 * @return true if there is a default id defined.
	 */
	public boolean hasDefaultId();
	
	
	/**
	 * Checks to see if the specified id exists.
	 * 
	 * @return true if the id exists.
	 * 
	 * @throws NullPointerException if the id is null.
	 */
	public boolean hasId(String id);
}
