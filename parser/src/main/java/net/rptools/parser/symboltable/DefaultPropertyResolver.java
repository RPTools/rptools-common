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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataType;

/**
 * DefaultPropertyResolver provides a simple property resolver that allows
 * the script to set and lookup properties but does not persist or map
 * the properties to any other object.
 * 
 */
class DefaultPropertyResolver implements PropertyResolver {

	/** The id that will be used as the default id. */
	public final static String NO_ID = "";
	
	/** The storage for the properties. */
	private final Map<String, Map<String, DataValue>> allPropeties = new LinkedHashMap<>();

	@Override
	public void setProperty(String name, DataValue value) {
		setProperty(NO_ID, name, value);
	}

	@Override
	public DataValue getProperty(String name) {
		return getProperty(NO_ID, name);
	}

	@Override
	public boolean containsProperty(String name) {
		return containsProperty(NO_ID, name);
	}

	@Override
	public boolean canBeSetTo(String name, DataType type) {
		return canBeSetTo(NO_ID, name, type);
	}

	@Override
	public DataType dataType(String name) {
		return dataType(NO_ID, name);
	}

	@Override
	public Collection<String> getPropertyNames() {
		return getPropertyNames(NO_ID);
	}

	@Override
	public void setProperty(String id, String name, DataValue value) {
		if (name == null) {
			throw new NullPointerException("Property name can not be null.");
		}
		
		if (id == null) {
			throw new NullPointerException("Id of property holder can not be null.");
		}
		
		if (value == null) {
			throw new NullPointerException("Value stored in property can not be null.");
		}
		
		if (canBeSetTo(id, name, value.dataType())) {
			Map<String, DataValue> properties = getOrCreatePropertyMap(id);
			properties.put(name, value);
		} else {
			throw new IllegalArgumentException("Property " + name + " can not be set to a " + value.dataType().toString());
		}		
	}

	@Override
	public DataValue getProperty(String id, String name) {
		if (id == null) {
			throw new NullPointerException("Id of property holder can not be null.");
		}
		
		if (name == null) {
			throw new NullPointerException("Property name can not be null.");
		}
	
		// In the default property resolver we never want to throw an exception
		// because of an id not existing.
		Map<String, DataValue> properties = getOrCreatePropertyMap(id);
		
		return properties.get(name);	
	}

	@Override
	public boolean containsProperty(String id, String name) {
		if (null == id) {
			throw new NullPointerException("Id of poperty holder can not be null.");
		}
		
		if (name == null) {
			throw new NullPointerException("Variable name can not be null.");
		}
		
		// In the default property resolver we never want to throw an exception
		// because of an id not existing.
		Map<String, DataValue> properties = getOrCreatePropertyMap(id);
		
		return properties.containsKey(name);
	}

	@Override
	public boolean canBeSetTo(String id, String name, DataType type) {
		return true; // No restrictions on default property resolver
	}

	@Override
	public DataType dataType(String id, String name) {
		if (id == null) {
			throw new NullPointerException("Id of poperty holder can not be null.");
		}
		
		if (name == null) {
			throw new NullPointerException("Property name can not be null.");
		}
		
		// For the default property resolver there are no restrictions on data types.
		return DataType.ANY;
	}

	@Override
	public Collection<String> getPropertyNames(String id) {
		// In the default property resolver we never want to throw an exception
		// because of an id not existing.
		Map<String, DataValue> properties = getOrCreatePropertyMap(id);
		
		return Collections.unmodifiableCollection(properties.keySet());
	}

	/**
	 * Returns the property map for the specified id. If there is no 
	 * property map for the specified then one will be created and 
	 * returned.
	 * 
	 * @param id The id of the property map.
	 * 
	 * @return the property map for an id.
	 */
	private Map<String, DataValue> getOrCreatePropertyMap(String id) {
		Map<String, DataValue> properties = allPropeties.get(id);
		if (properties == null) {
			properties = new LinkedHashMap<>();
			allPropeties.put(id, properties);
		}
		
		return properties;
	}

	@Override
	public boolean hasDefaultId() {
		return true; // The default id always exists for default property resolver
	}

	@Override
	public boolean hasId(String id) {
		return true; // The id always exists for default property resolver.
	}
}
