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
 * The DataLabel class represents the labels that can be attached to {@link DataValue}s by
 * the script engine.
 * 
 * All labels have an index that can be incremented to support the same label being set
 * more than once as happens in repeat groups.
 * 
 */
public final class DataLabel {

	/** The value that represents no label being attached to the {@link DataValue}. */
	public static final DataLabel NO_LABEL = new DataLabel("NO LABEL", 0);

	/** The name of the label. */
	private final String name;
	
	/** The index for the label, used to support a list of the same label. */
	private final int index;
	
	/**
	 * Creates a new DataLabel.
	 * 
	 * @param name The name of the label.
	 * @param index The index value for the label.
	 */
	public DataLabel(String name, int index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * Returns the name of the label.
	 * 
	 * @return the name of the label.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the index of this label.
	 * 
	 * @return the index of this label.
	 */
	public int getIndex() {
		return index;
	}
	
	@Override 
	public String toString() {
		return name + "(" + index + ")";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
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
		DataLabel other = (DataLabel) obj;
		if (index != other.index)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
