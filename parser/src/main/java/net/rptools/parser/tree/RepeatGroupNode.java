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

import java.util.ArrayList;
import java.util.List;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.lib.datavalue.DataType;
import net.rptools.parser.ExpressionEvaluatorException;
import net.rptools.parser.ScriptContext;

/**
 * Represents the repeat groups in the scripting language.
 *
 */
class RepeatGroupNode implements ScriptTreeNode {
	
	/** The child to repeat. */
	private final ScriptTreeNode child;

	/** Should the repeated group return the sum. */
	private final boolean sum;
	
	/** The number of times to repeat. */
	private final int times;
	
	/** The property name to get the number of times to repeat from. */
	private final String property;
	
	/** The variable name to get the number of times to repeat from. */
	private final String variable;
	
	/** The prompt message to use if prompting for the number of times to repeat. */
	private final String promptMsg;
	
	/** Should the user be prompted for the number of times to repeat. */
	private final boolean prompt;
	
	/** The name to use for the prompt. */
	private static String PROMPT_NAME = "Repeat Group Prompt";
	
	
	/**
	 * Creates a new RepeatGroupNode.
	 * 
	 * @param times The number of times to repeat.
 	 * @param child The child to repeat.
	 * @param sum Is it a repeat sum group.
	 */
	RepeatGroupNode(int times, ScriptTreeNode child, boolean sum) {
		this(times, child, sum, null, null, false, null);
	}
	
	/**
	 * Creates a new RepeatGroupNode. Several of the options are mutually exclusive so the static
	 * methods that get a repeat node should be used instead of using new.
	 * 
	 * @param times The number of times to repeat.
	 * @param child The child to repeat.
	 * @param sum Is the repeat group a sum group.
	 * @param variable The variable to get the number times to repeat from.
	 * @param property The property to get the number of times to repeat from.
	 * @param prompt Should the number of times to repeat be prompted for.
	 * @param promptMsg
	 */
	RepeatGroupNode(int times, ScriptTreeNode child, boolean sum, String variable, String property, boolean prompt, String promptMsg) {
		this.times = times;
		this.child = child;
		this.sum = sum;
		this.variable = variable;
		this.property = property;
		this.promptMsg = promptMsg;
		this.prompt = prompt;
	}
	
	/**
	 * Returns a RepeatGroupNode.
	 * 
	 * @param times The number of times to repeat.
	 * @param child The child node to repeat.
	 * 
	 * @return the RepeatGroupNode.
	 */
	public static RepeatGroupNode getRepeatNode(int times, ScriptTreeNode child) {
		return new RepeatGroupNode(times, child, false);
	}
	
	/**
	 * Returns a RepeatGroupNode that will return a sum of the repeated result.
	 * 
	 * @param times The number of times to repeat.
	 * @param child The child to repeat.
	 * 
	 * @return the RepeatGroupNode.
	 */
	public static RepeatGroupNode getRepeatSumNode(int times, ScriptTreeNode child) {
		return new RepeatGroupNode(times, child, true);
	}

	/**
	 * Returns a RepeatGroupNode that uses a variable to determine the number of times to repeat.
	 * 
	 * @param variable The name of the variable.
	 * @param child The child to repeat.
	 * 
	 * @return the RepeatGroupNode.
	 */
	public static RepeatGroupNode getVariableRepeatNode(String variable, ScriptTreeNode child) {
		return new RepeatGroupNode(0, child, false, variable, null, false, null);
	}
	
	/**
	 * Returns a RepeatGroupNode that uses a variable to determine the number of times to repeat
	 * and returns a sum of the results.
	 * 
	 * @param variable The name of the variable.
	 * @param child The child to repeat.
	 * 
	 * @return the RepeatGroupNode.
	 */
	public static RepeatGroupNode getVariableRepeatSumNode(String variable, ScriptTreeNode child) {
		return new RepeatGroupNode(0, child, true, variable, null, false, null);
	}
	
	/**
	 * Returns a RepeatGroupNode that uses property to determine the number of times to repeat.
	 * 
	 * @param property The name of the property.
	 * @param child The child to repeat.
	 * 
	 * @return the RepeatGroupNode.
	 */
	public static RepeatGroupNode getPropertyRepeatNode(String property, ScriptTreeNode child) {
		return new RepeatGroupNode(0, child, false, null, property, false, null);
	}
	
	/**
	 * Returns a RepeatGroupNode that uses a property to determine the number of times to repeat
	 * and returns a sum of the results.
	 * 
	 * @param property The name of the property.
	 * @param child The child to repeat.
	 * 
	 * @return the RepeatGroupNode.
	 */
	public static RepeatGroupNode getPropertyRepeatSumNode(String property, ScriptTreeNode child) {
		return new RepeatGroupNode(0, child, true, null, property, false, null);
	}

	/**
	 * Returns a RepeatGroupNode that prompts the user for the number of times to repeat.
	 * 
	 * @param prompt The message displayed to the user.
	 * @param child The child to repeat.
	 * 
	 * @return the RepeatGroupNode.
	 */
	public static RepeatGroupNode getPromptRepeatNode(String prompt, ScriptTreeNode child) {
		return new RepeatGroupNode(0, child, false, null, null, true, prompt);
	}
	
	/**
	 * Returns a RepeatGroupNode that prompts the user for the number of times to repeat and
	 * returns the sum of the result.
	 * 
	 * @param prompt The message displayed to the user.
	 * 
	 * @param child The child to repeat.
	 * 
	 * @return the RepeatGroupNode.
	 */
	public static RepeatGroupNode getPromptRepeatSumNode(String prompt, ScriptTreeNode child) {
		return new RepeatGroupNode(0, child, true, null, null, true, prompt);
	}


	@Override
	public DataValue evaluate(ScriptContext context) throws ExpressionEvaluatorException {
		final List<DataValue> values = new ArrayList<>();
		
		long noTimes;
		
		if (prompt) {
			if (promptMsg == null) {
				noTimes = context.getSymbolTable().promptForValue(PROMPT_NAME).asLong();
			} else {
				noTimes = context.getSymbolTable().promptForValue(PROMPT_NAME, promptMsg).asLong();
			}
		} else if (variable != null) {
			DataValue dv = context.getSymbolTable().getVariable(variable);
			if (dv == null) {
				throw new NullPointerException("Variable " + variable + " does not exist.");
			}
			noTimes = dv.asLong();
		} else if (property != null) {
			DataValue dv = context.getSymbolTable().getProperty(property);
			if (dv == null) {
				throw new NullPointerException("Property " + property + " does not exist.");
			}
			noTimes = dv.asLong();
		} else {
			noTimes = times;
		}
		
		DataValue total = DataValueFactory.longValue(0);
		for (int i = 0; i < noTimes; i++) {
			DataValue val = child.evaluate(context);
			values.add(val);
			
			if (sum) {
				if (val.dataType() == DataType.LIST) {
					for (DataValue v : val.asList()) {
						total = total.add(v);
					}
				} else {
					total = total.add(val);
				}
			}
		}
		
		DataValue retval;
		if (sum) {
			retval = total;
		} else {
			retval = DataValueFactory.listValue(values);
		}
		
		return retval;
	}

	/**
	 * Gets the prompt message used for prompting for the number of times to repeat.
	 * 
	 * @return the prompt message.
	 */
	public String getPrompt() {
		return promptMsg;
	}

}
