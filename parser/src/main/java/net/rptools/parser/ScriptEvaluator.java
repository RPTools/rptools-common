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

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataType;
import net.rptools.parser.dice.DiceRoller;
import net.rptools.parser.dice.JavaScriptDice;
import net.rptools.parser.functions.FunctionManager;
import net.rptools.parser.functions.ScriptFunction;
import net.rptools.parser.functions.javascript.JavaScripEvaluator;
import net.rptools.parser.functions.javascript.JavaScriptExports;
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


    /** Has the ScriptEvaluator been initialised. */
    private static boolean initialised = false;

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
        if (!initialised) {
            initJSApi();
            initialised = true;
        }

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

    /**
     * Adds JavaScript code to the script engine and executes it. This code can provide either an API
     * for other JavaScript code or export functions or dice to RPTools script. Each of these scripts
     * will be run in their own scope.
     *
     * If you want to run more than one script in the same scope then you need to use the
     * {@link #addJavaScripts(java.util.Map)} method.
     *
     * @param name The name of the script or file.
     * @param jsBody The JavaScript code.
     */
    public static void addJavaScript(String name, String jsBody) {
        addJavaScripts(Collections.singletonMap(name, jsBody));
    }

    /**
     * Adds JavaScript code to the script engine and executes it. This code can provide either an API for other
     * JavaScript code or export functions or dice to RPTools script. All of these scripts will be run in the same
     * scope.
     *
     * @param scripts The name and scripts to run.
     */
    public static void addJavaScripts(Map<String, String> scripts) {
        JavaScriptExports exports = JavaScripEvaluator.getInstance().addJavaScripts(scripts);
        for (ScriptFunction sf : exports.getExportedFunctions()) {
            FunctionManager.getInstance().definFunction(sf);
        }

        for (JavaScriptDice jsd : exports.getExportedDice()) {
            DiceRoller.getInstance().addUserDefinedDice(jsd);
        }
    }


    // TODO: Temp remove
    /**
     * Initialise the JavaScript part of the script API.
     */
    private void initJSApi() {
        try {
            JavaScripEvaluator.getInstance().test();
            URL url = FunctionManager.class.getResource("/net/rptools/parser/javascript/api/API.js");
            Path p = Paths.get(url.toURI());
            byte[] bytes = Files.readAllBytes(p);
            addJavaScript("JS API", new String(bytes));
            url = FunctionManager.class.getResource("/net/rptools/parser/javascript/api/API2.js");
            p = Paths.get(url.toURI());
            byte[] bytes1 = Files.readAllBytes(p);
            addJavaScript("JS API 2", new String(bytes1));
        } catch (Exception e) {
            e.printStackTrace();
        } catch (ExceptionInInitializerError eie) {
            eie.printStackTrace();
            eie.getCause().printStackTrace();
        }
    }
}
