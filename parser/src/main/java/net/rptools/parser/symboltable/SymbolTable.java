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
import net.rptools.lib.result.RollExpression;
import net.rptools.parser.ExpressionEvaluatorException;

/**
 * Interface that describes objects that can perform all the lookups and 
 * tracking of the different symbols.
 */
public interface SymbolTable {

	/**
	 * Checks to see if the symbol table has the variable defined.
	 * 
	 * @param name The name of the variable.
	 * 
	 * @return true if the symbol table has the variable.
	 * 
	 * @throws NullPointerException if the name is null.
	 */
	public boolean containsVariable(String name);

	/**
	 * Returns the value for the variable.
	 * 
	 * @param name The name of the variable.
	 * 
 	 * @return The value of the variable.
 	 * 
 	 * @throws NullPointerException if the name is null.
	 */
	public DataValue getVariable(String name);

	/**
	 * Sets the value of a variable.
	 * 
	 * @param name The name of the variable.
	 * @param value The value to set.
	 * 
	 * @throws NullPointerException if either of the arguments are null.
	 */
	public void setVariable(String name, DataValue value);

	/**
	 * Gets the variable names that are defined.
	 * 
	 * @return the variable names.
	 */
	public Collection<String> getVariableNames();

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
	 * Sets the value of the specified property for the specified.id.
	 * 
	 * @param name The name of the property to set.
	 * @param value The value to set.
	 * 
	 * @throws NullPointerException if any of the parameters are null..
	 * @throws IllegalArgumentException if the id does not exist.
	 */
	public void setProperty(String name, DataValue value);
	
	/**
	 * Gets the property names for the default id.
	 * 
	 * @return the property names defined for the default id.
	 */
	public Collection<String> getPropertyNames();

	/**
	 * Checks to see if a property can be set to a certain data type or that data
	 * type can be converted to the property types expected data value for the default
	 * id.
	 * 
	 * @param name The name of the property. 
	 * @param dataType The data type that you want to set the property to.
	 * 
	 * @return true if the property can be set to this data type.
	 * 
	 * @throws NullPointerException if any of the parameters are null.
	 * @throws IllegalArgumentException if there is no default id.
	 */
	public boolean propertyCanBeSetTo(String name, DataType dataType);
	
	/**
	 * Returns the data type that the property expects for the default id. 
	 * This may differ to the value of the current value in the property if the 
	 * property accepts the data type {@link DataType#ANY}.
	 * 
	 * @param name The name of the property. 
	 * 
	 * @return the data type of the property.
	 * 	 
	 * @throws NullPointerException if any of the parameters are null.
	 * @throws IllegalArgumentException if the default id does not exist.
	 */
	public DataType propertyDataType(String name);
	
	
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
	 * Gets the property names.
	 * 
	 * @param id The id for the property set.
	 * 
	 * @return the property names defined for the default id.
	 */
	public Collection<String> getPropertyNames(String id);

	/**
	 * Checks to see if a property can be set to a certain data type or that data
	 * type can be converted to the property types expected data value.
	 * 
	 * @param id The id for the property set.
	 * @param name The name of the property. 
	 * @param dataType The data type that you want to set the property to.
	 * 
	 * @return true if the property can be set to this data type.
	 * 
	 * @throws NullPointerException if any of the parameters are null.
	 * @throws IllegalArgumentException if there is no default id.
	 */
	public boolean propertyCanBeSetTo(String id, String name, DataType dataType);
	
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
	public DataType propertyDataType(String id, String name);
	
	
	
	/**
	 * Checks to see if the symbol table contains a roll expression
	 * 
	 * @param name The name of the roll expression.
	 * 
	 * @return true if the symbol table contains the roll expression.
	 * 
	 * @throws NullPointerException if the name is null.
	 */
	public boolean containsRollExpression(String name);

	/**
	 * Returns the specified roll expression from the symbol table.
	 * 
	 * @param name the name of the roll expression.
	 * 
	 * @return the roll expression.
	 * 
	 * @throws NullPointerException if name is null.
	 */
	public RollExpression getRollExpression(String name);

	/**
	 * Returns all of the roll expressions in the symbol table.
	 * 
	 * @return all of the roll expressions in the symbol table.
	 */
	public Collection<RollExpression> getRollExpressions();

	/**
	 * Sets the roll expression for the specified name.
	 * 
	 * @param name The name of the roll expression.
	 * @param rollExpr The roll expression.
	 * 
	 * @throws NullPointerException if any of the parameters are null.
	 */
	public void setRollExpression(String name, RollExpression rollExpr);

	/**
	 * Checks to see if the roll result exists in the symbol table.
	 * 
	 * @param name The name of the roll.
	 * 
	 * @return true if the roll result exists.
	 * 
	 * @throws NullPointerException if name is null.
	 */
	public boolean containsRollResult(String name);

	/**
	 * Gets all of the roll results for the specified name.
	 * There may be multiple values for a single roll because of grouping.
	 * 
	 * @param name The name of the roll.
	 * 
	 * @return The roll results.
	 * 
	 * @throws NullPointerException if the name is null;
	 */
	public Collection<DataValue> getRollResult(String name);

	/**
	 * Adds a roll result to the symbol table. You can add multiple roll results
	 * under the same name and the symbol table will keep track of all of them to support
	 * grouping expressions. 
	 * 
	 * @param name The name of the roll.
	 * 
	 * @param res The result of the roll
	 * 
	 * @throws NullPointerException if any of the arguments are null.
	 */
	public void addRollResult(String name, DataValue res);

	/**
	 * Resolves the named roll expression. Resolving a roll will automatically
	 * add the roll result to the symbol table.
	 * 
	 * @param name The name of the roll expression to resolve.
	 * 
	 * @return The result of the roll.
	 * 
	 * @throws NullPointerException if name is null.
	 * @throws IllegalArgumentException if the roll expression does not exist.
	 */
	public DataValue resolveRoll(String name);

	/**
	 * Checks to see if the the specified label exists in the symbol table.
	 * 
	 * @param name The name of the label.
	 * 
	 * @return true if the label exists.
	 * 
	 * @throws NullPointerException if the name is null.
	 */
	public boolean containsLabel(String name);

	/**
	 * Returns the data values for a label. There may be more than one
	 * data value to support when the label is set in a group expression. 
	 * 
	 * @param name The name of the label.
	 * 
	 * @return The values stored against the label.
	 * 
	 * @throws NullPointerException if name is null.
	 * @throws IllegalArgumentException if the label does not exist.
	 */
	public Collection<DataValue> getLabels(String name);

	/**
	 * Adds a value to the label.
	 * 
	 * @param name The name of the label.
	 * @param value The value for the label.
	 * 
	 * @throws NullPointerException if either of the arguments are null.
	 */
	public void addLabel(String name, DataValue value);

	/**
	 * Returns all the labels defined in the symbol table.
	 * 
	 * @return the labels in the symbol table.
	 */
	public Collection<String> getLabels();

	/**
	 * Returns the data value after prompting.
	 * 
	 * @param name The name of the prompt.
	 * 
	 * @return the data value.
	 * 
	 * @throws NullPointerException if the name is null
	 * @throws ExpressionEvaluatorException if the prompt was canceled.
	 */
	public DataValue promptForValue(String name) throws ExpressionEvaluatorException;
	
	/**
	 * Returns the data value after prompting.
	 * 
	 * @param name The name of the prompt.
	 * @param Description the descriptive text for the prompt.
	 * 
	 * @return the data value.
	 * 
	 * @throws NullPointerException if the name is null
	 * @throws ExpressionEvaluatorException if the prompt was canceled.
	 */
	public DataValue promptForValue(String name, String Description) throws ExpressionEvaluatorException;

}
