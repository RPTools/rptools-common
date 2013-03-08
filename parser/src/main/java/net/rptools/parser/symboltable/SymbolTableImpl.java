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


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataType;
import net.rptools.lib.result.RollExpression;
import net.rptools.parser.ExpressionEvaluatorException;
import net.rptools.parser.ScriptContext;
import net.rptools.parser.dice.DiceRoller;

/**
 * SymbolTableImpl the SymbolTable and provides a default implementation that should be 
 * good enough for most purposes.
 *
 */
class SymbolTableImpl implements SymbolTable {

	/** Used to resolve variables. */
	private final VariableResolver variableResolver;
	
	/** Used to resolver properties. */
	private final PropertyResolver propertyResolver;
	
	/** Used to resolve prompt variables and groupings. */
	private final PromptResolver promptResolver;
		
	/** The roll results defined. */
	private final Map<String, List<DataValue>> rollResults = new LinkedHashMap<>();

	/** The roll expression defined. */
	private final Map<String, RollExpression> rollExpressions = new LinkedHashMap<>();
	
	/** The label defined. */
	private final Map<String, List<DataValue>> labels = new LinkedHashMap<>();
	
	
	/**
	 * Creates a new SymbolTableImpl.
	 * 
	 * @param variableResolver The variable resolver to use.
	 * @param propertyResolver The property resolver to use.
	 */
	SymbolTableImpl(VariableResolver variableResolver, PropertyResolver propertyResolver, 
				   PromptResolver promptResolver) {
		this.variableResolver = variableResolver;
		this.propertyResolver = propertyResolver;
		this.promptResolver = promptResolver;
	}


    /** Object used for dice rolling. */
	private final DiceRoller diceRoller = DiceRoller.getInstance();
	
	@Override
	public boolean containsVariable(String name) {
		return variableResolver.containsVariable(name);
	}
	
	@Override
	public DataValue getVariable(String name) {
		return variableResolver.getVariable(name);
	}
	
	@Override
	public void setVariable(String name, DataValue value) {
		variableResolver.setVariable(name, value);
	}
	
	@Override
	public Collection<String> getVariableNames() {
		return variableResolver.getVariableName();
	}


	@Override
	public boolean containsProperty(String name) {
		return propertyResolver.containsProperty(name);
	}
	

	@Override
	public DataValue getProperty(String name) {
		return propertyResolver.getProperty(name);
	}
	

	@Override
	public void setProperty(String name, DataValue value) {
		propertyResolver.setProperty(name, value);
	}
	

	@Override
	public Collection<String> getPropertyNames() {
		return variableResolver.getVariableName();
	}
	

	@Override
	public boolean propertyCanBeSetTo(String name, DataType dataType) {
		return propertyResolver.canBeSetTo(name, dataType);
	}
	

	@Override
	public boolean containsRollExpression(String name) {
		return rollExpressions.containsKey(name);
	}
	

	@Override
	public RollExpression getRollExpression(String name) {
		return rollExpressions.get(name);
	}


	@Override
	public Collection<RollExpression> getRollExpressions() {
		return Collections.unmodifiableCollection(rollExpressions.values());
	}

	
	

	@Override
	public void setRollExpression(String name, RollExpression rollExpr) {
		rollExpressions.put(name, rollExpr);
	}
	

	@Override
	public boolean containsRollResult(String name) {
		return rollResults.containsKey(name);
	}

	
	@Override
	public Collection<DataValue> getRollResult(String name) {
		return Collections.unmodifiableCollection(rollResults.get(name));
	}
	

	@Override
	public void addRollResult(String name, DataValue res) {
		if (containsRollResult(name) == false) {
			List<DataValue> list = new ArrayList<>();
			rollResults.put(name, list);
		}
		List<DataValue> list = rollResults.get(name);
		list.add(res);
	}
		

	@Override
	public DataValue resolveRoll(ScriptContext context, String name) throws ExpressionEvaluatorException {
		RollExpression rexpr = getRollExpression(name);
		DataValue res;
		res = diceRoller.roll(context, rexpr);
		addRollResult(name, res);
		
		return res;
	}
	
	

	@Override
	public boolean containsLabel(String name) {
		return labels.containsKey(name);
	}
	

	@Override
	public Collection<DataValue> getLabels(String name) {
		return labels.get(name);
	}
	

	@Override
	public void addLabel(String name, DataValue value) {
		if (containsLabel(name) == false) {
			List<DataValue> list = new ArrayList<>();
			labels.put(name, list);
		}		
		List<DataValue> list = labels.get(name);
		list.add(value);
	}
	

	@Override
	public Collection<String> getLabels() {
		return Collections.unmodifiableCollection(labels.keySet());
	}

	@Override
	public DataType propertyDataType(String name) {
		return propertyResolver.dataType(name);
	}

	@Override
	public boolean containsProperty(String id, String name) {
		return propertyResolver.containsProperty(id, name);
	}

	@Override
	public DataValue getProperty(String id, String name) {
		return propertyResolver.getProperty(id, name);
	}

	@Override
	public void setProperty(String id, String name, DataValue value) {
		propertyResolver.setProperty(id, name, value);
	}

	@Override
	public Collection<String> getPropertyNames(String id) {
		return propertyResolver.getPropertyNames(id);
	}

	@Override
	public boolean propertyCanBeSetTo(String id, String name, DataType dataType) {
		return propertyResolver.canBeSetTo(id, name, dataType);
	}

	@Override
	public DataType propertyDataType(String id, String name) {
		return propertyResolver.dataType(id, name);

	}

	@Override
	public DataValue promptForValue(String name) throws ExpressionEvaluatorException {
		return promptResolver.promptForValue(name);
	}

	@Override
	public DataValue promptForValue(String name, String description) throws ExpressionEvaluatorException {
		return promptResolver.promptForValue(name, description);
	}

}
