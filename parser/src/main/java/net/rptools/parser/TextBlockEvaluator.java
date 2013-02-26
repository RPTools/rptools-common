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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import net.rptools.lib.datavalue.DataValue;

/**
 * The TextBlockEvaluator class scans through a block of text for expressions that will be
 * passed the a {@link ScriptEvaluator}. 
 * 
 * Expressions in the text block are contained within square brackets '[' and ']'. If you 
 * wish to include a '[' or ']' without introducing an expression you can use '\[' or '\]'.
 * Expressions may start with a flag detailing how the results should be formatted.
 * The following flag options are valid
 * <ul>
 * <li>H or h - Hidden, this expression should be evaluated but the output not displayed</li>
 * <li>T or t - Tooltip, the simple result should be displayed and the detailed result displayed
 *              in the tooltip. </li>
 * <li>R or r - Result only, the result should be displayed without any special formatting. </li>
 * <li>E or e - Expanded, the detailed result of the expression should be displayed. </li>
 * <li>U or u - Unformatted, the unformatted detailed result should be displayed. </li>
 * </ul>
 * 
 * Flags must be specified in the format [<i>X</i>: ...] where <i>X</i> is the flag, only one
 * flag is supported, and there can be no spaces between the leading '[' and the ':' character.
 * 
 * Evaluating the text block returns the results of any expressions that were evaluated, any
 * options that were specified for these expressions and the block of text converted to a 
 * freemarker template and the labels for the expressions which match the freemarker tags.
 *
 */
public class TextBlockEvaluator {
	
	/**
	 * Represents the block of text after it has been parsed and all expressions have
	 * been evaluated.
	 */
	private static class EvaluatedBlockBuilder {
		/** The original block of text. */
		private final String originalBlock;
		
		/** The block of text converted into a freemarker template, */
		private String templatedBlock;
		
		/** Mapping between expression labels and expressions found in the text block. */
		private final Map<String, String> expressions = new LinkedHashMap<>();
		
		/** Mapping between expression labels and evaluated results in the text block. */
		private final Map<String, DataValue> results = new LinkedHashMap<>();
		
		/** Mapping between the expression labels and expression options in the text block. */
		private final Map<String, BlockExpressionOptions> options = new LinkedHashMap<>();
		
		/**
		 * Creates a new EvaluatedBlockBuilder object.
		 * 
		 * @param input The text input to parse and evaluate.
		 */
		private EvaluatedBlockBuilder(String input) {
			assert input != null : "Text input is null.";
			
			originalBlock = input;
		}
		
		/**
		 * Returns the labels that were generated for the expressions within the text block.
		 * 
		 * @return the labels for expressions.
		 */
		private Collection<String> expressionLabels() {
			return expressions.keySet();
		}
		
		/**
		 * Returns the expressions that were found in the text block.
		 * 
		 * @return the expressions that were found in the text block.
		 */
		private Collection<String> expressions() {
			return expressions.values();
		}
		
		/**
		 * Returns the expression option at the start of an expression in the text block.
		 * 
		 * @param str The string starting at the character after the expression marker.
		 * 
		 * @return The option found or {@link BlockExpressionOptions#NONE}. if no options
		 *  	   were found.
		 * 
		 * @throws ExpressionEvaluatorException if an unknown option was specified. 
		 */
		private BlockExpressionOptions getOption(String str) throws ExpressionEvaluatorException {
			assert str != null : "Option string is null";
			
			BlockExpressionOptions opt  = BlockExpressionOptions.NONE;
			if (str.charAt(1) == ':') {
				char optChar = str.charAt(0);
				if (optChar == 'H' || optChar == 'h') {
					opt = BlockExpressionOptions.HIDDEN;
				} else if (optChar == 'T' || optChar == 't') {
					opt = BlockExpressionOptions.TOOLTIP;
				} else if (optChar == 'R' || optChar == 'r') {
					opt = BlockExpressionOptions.RESULT;
				} else if (optChar == 'E' || optChar == 'e') {
					opt = BlockExpressionOptions.EXPANDED;
				} else if (optChar == 'U' || optChar == 'u') {
					opt = BlockExpressionOptions.UNFORMATTED;
				} else {
					throw new ExpressionEvaluatorException("Unknown expession option '" + optChar + "'");
				}
			}
			
			return opt;
		}
		
		
		/**
		 * Parses the text block extracting all the expressions and their options.
		 * 
		 * @throws ExpressionEvaluatorException if there is an incomplete expression
		 *         found during parsing.
		 */
		private void parseInputBlock() throws ExpressionEvaluatorException {
			boolean inEscape = false;
			boolean inExprBlock = false;
			char inQuote = ' ';
			int startExpr = 0;
			int expressionNumber = 0;
			String currentExpressionLabel = "";
			
			StringBuilder outBlock = new StringBuilder();
			
			for (int i = 0, len = originalBlock.length(); i < len; i++) {
				char c = originalBlock.charAt(i);

				if (inEscape) {
					if (c == '[' || c == ']') {
						outBlock.append(c);
					} else {
						outBlock.append('\\');
						outBlock.append(c);
					}
					inEscape = false;
					continue;
				}
			
				if (!inExprBlock) {
					if (c == '[') {
						currentExpressionLabel = "rptools_expression_" + expressionNumber;
						outBlock.append("${");
						outBlock.append(currentExpressionLabel);
						outBlock.append("}");
						expressionNumber++;
						BlockExpressionOptions opt = getOption(originalBlock.substring(i+1));
						if (opt != BlockExpressionOptions.NONE) {
							i += 2;
						}
						inExprBlock = true;
						startExpr = i + 1;
					} else if (c == '\\') {
						inEscape = true;
					} else {
						outBlock.append(c);
					}
				} else {
					if (inQuote == ' ') {
						if (c == ']') {
							inExprBlock = false;
							String exp = originalBlock.substring(startExpr, i);
							expressions.put(currentExpressionLabel, exp);
						} else if (c == '"' || c == '\'') {
							inQuote = c;
						}
					} else {
						if (inQuote == c) {
							inQuote = ' ';
						}
					}
				}
				
			}
			
			// If we get to the end of the text and we are still in an expression block then
			// we raise an exception as we have an incomplete expression.
			
			if (inExprBlock) {
				throw new ExpressionEvaluatorException("Incomplete Expression in input block.");
			}
			
			//outBlock.append(originalBlock.substring(lastIndex, originalBlock.length()));
			templatedBlock = outBlock.toString();
		}
		
		/**
		 * Associates a new evaluated value with an expression label.
		 * 
		 * @param label The expression label to associate value with.
		 * @param val The evaluated value.
		 */
		private void putValue(String label, DataValue val) {
			assert label != null : "Expression label is null null.";
			assert val != null : "Evaluated value is  null.";
			
			results.put(label, val);
		}
		
		/**
		 * Creates an {@link EvaluatedBlock} from this EvaluatedBlockBuilder.
		 * 
		 * @return an {@link EvaluatedBlock}.
		 */
		private EvaluatedBlock toEvaluatedBlock() {
			return new EvaluatedBlock(templatedBlock, expressions, results, options);
		}
		
		
	}
	
	/**
	 * Evaluates a block of text extracting and evaluating the expressions using
	 * the default symbol table.
	 * 
	 * @param inputBlock The text to evaluate.
	 * @return the {@link EvaluatedBlock} that holds the results of evaluation.
	 * 
	 * @throws ExpressionEvaluatorException if an exception occurs during
	 *         evaluation of the text.
	 * @throws NullPointerException if inputBlock is null.
	 */
	public EvaluatedBlock evaluate(String inputBlock) throws ExpressionEvaluatorException {
		return evaluate(new ScriptContextBuilder().toScriptContext(), inputBlock);
	}
	
	/**
	 * Evaluates a block of text extracting and evaluating the expressions using the 
	 * specified symbol table.
	 * 
	 * @param context The context to run the script with.
	 * @param inputBlock The text to evaluate. 
	 * 
	 * @return the {@link EvaluatedBlock} that holds the results of evaluation.
	 * 
	 * @throws ExpressionEvaluatorException if an exception occurs during
	 *         evaluation of the text.
	 *         
	 * @throws NullPointerException if any of the arguments are null.
	 */
	public EvaluatedBlock evaluate(ScriptContext context, String inputBlock) throws ExpressionEvaluatorException {
		if (inputBlock == null) {
			throw new NullPointerException("TextBlockEvaluator input text can not be null."); 
		}
		
		EvaluatedBlockBuilder parsedBlock = new EvaluatedBlockBuilder(inputBlock);
		parsedBlock.parseInputBlock();
		
		
		assert parsedBlock.expressionLabels().size() == parsedBlock.expressions().size() : "Parse block label mismatch";
		
	
		ScriptEvaluator exprEvaluator = ScriptEvaluator.getInstance(context, parsedBlock.expressions());
		
		Iterator<String> labelIter = parsedBlock.expressionLabels().iterator();
		
		while (exprEvaluator.hasNext()) {
			DataValue val = exprEvaluator.evaluateNext();
			String label = labelIter.next();
			parsedBlock.putValue(label, val);
		}
		
		return parsedBlock.toEvaluatedBlock();
	}
	
}
