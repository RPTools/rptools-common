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
package net.rptools.parser.tree;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.parser.ScriptContext;

/**
 * Represents the script tree node that will retrieve property values.
 *
 */
class PropertyNode implements ScriptTreeNode {

	/** The name of the property. */
	private final String propertyName;
	
	/**
	 * Creates a new PropertyNode.
	 * 
	 * @param name The name of the property to get.
	 */
	public PropertyNode(String name) {
		assert name != null : "Property name can not be null";
		assert name.length() > 0 : "Property name can not be zero length.";

		propertyName = name;
	}

	@Override
	public DataValue evaluate(ScriptContext context) {
		// TODO: need to deal with snapshots?
		return context.getSymbolTable().getProperty(propertyName);
	}

}
