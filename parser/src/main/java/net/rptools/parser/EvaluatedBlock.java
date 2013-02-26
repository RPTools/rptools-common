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
package net.rptools.parser;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import net.rptools.lib.datavalue.DataValue;

/**
 * The EvaluatedBlock class holds the results of a block of text that has been
 * evaluated with {@link TextBlockEvaluator#evaluate(String)}.
 */
public class EvaluatedBlock {
	/** The text block output as a freemarker template,*/
	private final String templatedText;
	
	/** The expressions that were found in the text block. */
	private final Map<String, String> expressions;
	
	/** The results of evaluating the expressions found in the text block. */
	private final Map<String, DataValue> results;
	
	/** The options specified in the expressions in the text block. */
	private final Map<String, BlockExpressionOptions> options;


	/**
	 * Creates a new EvaluatedBlock object.
	 * 
	 * @param templText The text freemarker template of the text block.
	 * @param inputExpr The expression that were found in the text block block.
	 * @param exprOutputs The results of the evaluated expressions in the text block.
	 * @param exprOpts The options found for the expressions in the text block.
	 */
	EvaluatedBlock(String templText, Map<String, String> inputExpr, Map<String, DataValue> exprOutputs, Map<String, BlockExpressionOptions> exprOpts) {
		assert templText != null : "EvaluatedBlock template text can not be null";
		assert inputExpr != null : "EvaluatedBlock expressions can not be null";
		assert exprOutputs != null : "EvaluatedBlock expression outputs can not be null.";
		assert exprOpts != null : "EvaluatedBlock expression options can not be null.";
		
		templatedText = templText;
		
		Map<String, String> inputs = new LinkedHashMap<>();
		inputs.putAll(inputExpr);
		expressions = Collections.unmodifiableMap(inputs);
		
		Map<String, DataValue> output = new LinkedHashMap<>();
		output.putAll(exprOutputs);
		results = Collections.unmodifiableMap(exprOutputs);
		
		Map<String, BlockExpressionOptions> opts = new LinkedHashMap<>();
		opts.putAll(exprOpts);
		options = Collections.unmodifiableMap(exprOpts);
	}


	/**
	 * Gets the freemarker template version of the processed text block.
	 * 
	 * @return the template version of the text block.
	 */
	public String getTemplatedText() {
		return templatedText;
	}
	
	/**
	 * Gets the labels that were generated for each of the expressions that
	 * were in the text block.
	 * 
	 * @return the labels for the expressions in the text block.
	 */
	public Collection<String> getExpressionLabels() {
		return expressions.keySet();
	}

	/**
	 * Checks to see if the specified label exsits for the expressions in the
	 * text block.
	 * 
	 * @param label The label to check.
	 * @return true if the labeled expression exists.
	 */
	public boolean hasExpressionLabel(String label) {
		return expressions.containsKey(label);
	}

	/**
	 * Returns the expression that is assigned to the specified label.
	 * 
	 * @param label The label of the expression to get.
	 * @return the expression for the label.
	 */
	public String getExpression(String label) {
		return expressions.get(label);
	}
	
	/**
	 * Returns the result of evaluating the expression assigned to the specified label.
	 * 
	 * @param label The label of the expression to get the result of.
	 * @return the result of the evaluated expression.
	 */
	public DataValue getExpressionResult(String label) {
		return results.get(label);
	}

	/**
	 * Returns the option for the expression assigned to the specified label.
	 * 
	 * @param label The label of the expression to get the option for.
	 * @return the option for the expression.
	 */
	public BlockExpressionOptions getExpressionOption(String label) {
		return options.get(label);
	}
	
}
