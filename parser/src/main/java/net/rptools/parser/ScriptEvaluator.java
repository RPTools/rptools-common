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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataType;
import net.rptools.parser.symboltable.SymbolTable;
import net.rptools.parser.tree.ScriptTreeNode;
import net.rptools.parser.tree.MTScriptTreeParser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 * ScriptEvaluator evaluates the scripts passed in and returns the results.
 *
 */
public class ScriptEvaluator  {

	/** The context that this script will run with. */
	private final ScriptContext scriptContext;

	
	/** The List of input text that needs to be evaluated. */
	private final List<String> inputText = new ArrayList<>();
	
	/** The index of the input text to process. */
	private int index;

	/**
	 * Gets a ScriptEvaluator object to evaluate a script using
	 * the supplied symbol table for lookups and setting values.
	 * 
	 * @param context The context that the script runs with.
	 * @param text the script commands to evaluate.
	 * 
	 * @throws NullPointerException if either the symTable or text parameters
	 *         provided are null.
	 */
	private ScriptEvaluator(ScriptContext context, Collection<String> text) {
		if (context == null) {
			throw new NullPointerException("Script context can not be null");
		}

		if (text == null) {
			throw new NullPointerException("List of text to parse can not be null.");
		}

		scriptContext = context;
		inputText.addAll(text);
	}

	/**
	 * Gets a ScriptEvaluator object to evaluate a script using the default
	 * context. 
	 * 
	 * @param text The text of the script to evaluate.
	 * @return the ScriptEvaluator used to evaluate the script.
	 * 
	 * @throws NullPointerException if text parameter is null.
	 */
	public static ScriptEvaluator getInstance(String text) {
		return getInstance(new ScriptContextBuilder().toScriptContext(), text);
	}

	/**
	 * Gets a ScriptEvaluator object to evaluate a script.
	 * 
	 * @param context The context to run the script with.
	 * @param text The text of the script to evaluate.
	 * @return the ScriptEvaluator used to evaluate the script.
	 * 
	 * @throws NullPointerException if either the context or text parameters
	 *         provided are null.
	 */
	public static ScriptEvaluator getInstance(ScriptContext context, String text) {
		return new ScriptEvaluator(context, Collections.singleton(text));		
	}
	
	/**
	 * Gets a ScriptEvaluator object to evaluate several scripts using the specified
	 * symbol table for lookups and setting values.
	 * 
	 * @param text The text of the scripts to evaluate.
	 * @return the ScriptEvaluator used to evaluate the scripts.
	 * 
	 * @throws NullPointerException if either the context or text parameters
	 *         provided are null.
	 */
	public static ScriptEvaluator getInstance(ScriptContext context, Collection<String> text) {
		if (context == null) {
			throw new NullPointerException("Script Context can not be null");
		}

		if (text == null) {
			throw new NullPointerException("List of text to parse can not be null.");
		}

		if (text.size() == 0) {
			throw new IllegalArgumentException("No text to parse.");
		}

		return new ScriptEvaluator(context, text);

	}

	/**
	 * Gets a ScriptEvaluator object to evaluate several scripts using the default
	 * symbol table for lookups and setting values.
	 * 
	 * @param text The text of the scripts to evaluate.
     *
	 * @return the ScriptEvaluator used to evaluate the scripts.
	 * 
	 * @throws NullPointerException if text parameter is null.
	 */
	public static ScriptEvaluator create(Collection<String> text) {
		return getInstance(new ScriptContextBuilder().toScriptContext(), text);
	}

	/**
	 * Returns the symbol table that this ScriptEvaluator is using.
	 * 
	 * @return the symbol table being used.
	 */
	public SymbolTable getSymbolTable() {
		return scriptContext.getSymbolTable();
	}

	/**
	 * Checks if there is another script to be evaluated.
	 * 
	 * @return true if there is another script to be evaluated.
	 */
	public boolean hasNext() {
		return index < inputText.size();
	}

	/**
	 * Evaluates the next script and returns a {@link DataValue} with the results.
	 * Since a script may have multiple statements the {@link DataValue} returned is
	 * always of type {@link DataType#LIST} which contains the result of each statement.
	 * 
	 * @return the results of evaluating the script.
	 * 
	 * @throws ExpressionEvaluatorException if errors occur while evaluating the script.
	 * @throws IndexOutOfBoundsException if there are no more scripts to evaluate.
	 */
	public DataValue evaluateNext() throws ExpressionEvaluatorException {
		
		if (index >= inputText.size()) {
			throw new IndexOutOfBoundsException("Evaluate Index = " + index + " input lines size = " + inputText.size()) ;
		}
		
		MTScriptLexer lexer = new MTScriptLexer(new ANTLRStringStream(
				inputText.get(index)));
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		MTScriptParser parser = new MTScriptParser(tokenStream);
		parser.setSymbolTable(scriptContext.getSymbolTable());

		DataValue results;
		try {
			CommonTree tree = (CommonTree) (parser.mtscript().getTree());


			CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(tree);
			MTScriptTreeParser walker = new MTScriptTreeParser(nodeStream);

			walker.setSymbolTable(scriptContext.getSymbolTable());

			ScriptTreeNode scriptNode = walker.evaluator();

			results = scriptNode.evaluate(scriptContext);
            index++;
		} catch (RecognitionException e) {
			// TODO: log?
			throw new ParserException(e.getLocalizedMessage(), e);
		}
		return results;
	}
}
