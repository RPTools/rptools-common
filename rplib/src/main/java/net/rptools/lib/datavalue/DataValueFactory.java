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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.rptools.lib.result.Result;

/**
 * The DataValueFactory class is used to create the {@link DataValue}s of
 * different types. 
 * 
 * All DataValue objects returned by this class are guaranteed immutable.
 *
 */
public final class DataValueFactory {
	
	/**
	 * Creates a {@link DataValue} of type {@link DataType#LONG}.
	 * 
	 * @param val the long value to create a {@link DataValue} for.
	 * 
	 * @return the new {@link DataValue}.
	 */
	public static DataValue longValue(long val) {
		return new LongDataValue(val);
	}
	
	/**
	 * Creates a {@link DataValue} of type {@link DataType#DOUBLE}.
	 * 
	 * @param val the double value to create a {@link DataValue} for.
	 * 
	 * @return the new {@link DataValue}.
	 */
	public static DataValue doubleValue(double val) {
		return new DoubleDataValue(val);
	}
	
	/**
	 * Creates a {@link DataValue} of type {@link DataType#STRING}.
	 * 
	 * @param val the String value to create a {@link DataValue} for.
	 * 
	 * @return the new {@link DataValue}.
	 */
	public static DataValue stringValue(String val) {
		return new StringDataValue(val);
	}
	
	/**
	 * Creates a {@link DataValue} of type {@link DataType#LIST}.
	 * 
	 * @param vals the Collection to create a {@link DataValue} for.
	 * 
	 * @return the new {@link DataValue}.
	 */
	public static DataValue listValue(Collection<? extends DataValue> vals) {
		return new ListDataValue(vals);
	}

	/**
	 * Creates a {@link DataValue} of type {@link DataType#DICTIONARY}.
	 * 
	 * @param vals the Map value to create a {@link DataValue} for.
	 * 
	 * @return the new {@link DataValue}.
	 */
	public static DataValue dictionaryValue(Map<String, DataValue> vals) {
		return new DictionaryDataValue(vals);
	}
	
	/**
	 * Creates a {@link DataValue} of type {@link DataType#RESULT}.
	 * 
	 * @param res The {@link Result} object to create this from.
	 * 
	 * @return the new {@link DataValue}.
	 */
	public static DataValue resultValue(Result res) {
		return new ResultDataValue(res);
	}
	
	
	/**
	 * Creates a {@link DataValue} of type {@link DataType#RESULT}.
	 * 
	 * @param vals the Map value to create a {@link DataValue} for.
	 * 
	 * @return the new {@link DataValue}.
	 * 	
	 * @throws UnsupportedOperationException if the value can not be represented by a
	 *         {@link DataType#RESULT}.
	 * 
	 */
	public static DataValue resultValue(Map<String, DataValue> vals) {
		return new DictionaryDataValue(vals).asResultValue();
	}


    /**
     * Returns a boolean DataValue.
     *
     * @param val The boolean value to represent.
     *
     * @return the DataValue.
     */
    public static DataValue booleanValue(boolean val) {
        return BooleanDataValue.getBooleanDataValue(val);
    }


	/**
	 * Creates a {@link DataValue} based on the passed in DataValue with a
	 * {@link DataLabel} attached.
	 * 
	 * @param val The DataValue to label.
	 * @param l The label for the DataValue.
	 * 
	 * @return A new DataValue with the specified label attached.
	 */
	public static DataValue labeledValue(DataValue val, DataLabel l) {
		return new LabeledDataValue(val, l);
	}
	
	/**
	 * Creates a {@link DataValue} of type {@link DataType#LIST} containing
	 * {@link DataType#DOUBLE} values created from the Collection that is passed in.
	 * 
	 * @param vals The double values to create DataTypes for.
	 * 
	 * @return a {@link DataType#LIST} DataType containing the values.
	 */
	public static DataValue doubleListValue(Collection<Double> vals) {
		List<DataValue> lst = new ArrayList<>(vals.size());
		for (Double d : vals) {
			lst.add(doubleValue(d));
		}
		
		return listValue(lst);
	}
	
	/**
	 * Creates a {@link DataValue} of type {@link DataType#LIST} containing
	 * {@link DataType#STRING} values created from the Collection that is passed in.
	 * 
	 * @param vals The String values to create DataTypes for.
	 * 
	 * @return a {@link DataType#STRING} DataType containing the values.
	 */
	public static DataValue stringListValue(Collection<String> vals) {
		List<DataValue> lst = new ArrayList<>(vals.size());
		for (String s : vals) {
			lst.add(stringValue(s));
		}
		
		return listValue(lst);
	}

	/**
	 * Creates a {@link DataValue} of type {@link DataType#LIST} containing
	 * {@link DataType#LONG} values created from the Collection that is passed in.
	 *
	 * @param vals The long values to create DataTypes for.
	 *
	 * @return a {@link DataType#LIST} DataType containing the values.
	 */
	public static DataValue longListValue(Collection<Long> vals) {
		List<DataValue> lst = new ArrayList<>(vals.size());
		for (Long l : vals) {
			lst.add(longValue(l));
		}

		return listValue(lst);
	}

    /**
     * Creates a {@link DataValue} of type {@link DataType#LIST} containing
     * {@link DataType#LONG} values created from the Collection that is passed in.
     *
     * @param vals The integer values to create DataTypes for.
     *
     * @return a {@link DataType#LIST} DataType containing the values.
     */
    public static DataValue intListValue(Collection<Integer> vals) {
        List<DataValue> lst = new ArrayList<>(vals.size());
        for (Integer i : vals) {
            lst.add(longValue(i));
        }

        return listValue(lst);
    }


    /**
     * Creates a {@link DataValue} of type {@link DataType#LIST} containing
     * {@link DataType#LONG} values created from an array of integers.
     *
     * @param arr The array to create the list from.
     *
     * @return a {@link DataType#LIST} DataType containing the values.
     */
    public static DataValue listValueFromArray(int[] arr) {
        List<DataValue> lst = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            lst.add(longValue(arr[i]));
        }

        return listValue(lst);
    }

    /**
     * Creates a {@link DataValue} of type {@link DataType#LIST} containing
     * {@link DataType#LONG} values created from an array of longs.
     *
     * @param arr The array to create the list from.
     *
     * @return a {@link DataType#LIST} DataType containing the values.
     */
    public static DataValue listValueFromArray(long[] arr) {
        List<DataValue> lst = new ArrayList<>(arr.length);
        for (int i = 0; i < arr.length; i++) {
            lst.add(longValue(arr[i]));
        }

        return listValue(lst);
    }



    /**
     * Returns a NullDataValue instance.
     *
     * @return a NullDataValue.
     */
    public static DataValue nullDataValue() {
        return NullDataValue.getInstance();
    }
	
}
