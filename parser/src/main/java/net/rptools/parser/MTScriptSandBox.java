/*
new w * Licensed under the Apache License, Version 2.0 (the "License");
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

import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.result.RollExpression;

import net.rptools.parser.symboltable.SymbolTable;
import net.rptools.parser.tree.ScriptTreeNode;
import net.rptools.parser.tree.MTScriptTreeParser;

import org.antlr.runtime.ANTLRFileStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.CommonTreeNodeStream;

/**
 * 
 *
 */
// TODO Reomve?
public class MTScriptSandBox {
	
	
	public static void mainOld(String[] args) throws Exception {
		
		long startTime = System.currentTimeMillis();
		MTScriptLexer lexer = new MTScriptLexer(new ANTLRFileStream("/tmp/tst1.mts"));
		
		
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		
		ScriptContext scriptContext = new ScriptContextBuilder().toScriptContext();
		
		SymbolTable symbolTable =  scriptContext.getSymbolTable();	
	 
		MTScriptParser parser = new MTScriptParser(tokenStream);
		parser.setSymbolTable(symbolTable);
		
		CommonTree tree = (CommonTree) (parser.mtscript().getTree());
		System.out.println(tree.toStringTree());
		System.out.println("--- START ROLLS ---");
		for (RollExpression rxpr : symbolTable.getRollExpressions()) {
			System.out.print(rxpr.getRollString());
			if (rxpr.isVerbose()) { 
				System.out.print(" (verbose)");
			}
			System.out.println("");
		}
		System.out.println("--- END ROLLS ---");
		
		CommonTreeNodeStream nodeStream = new CommonTreeNodeStream(tree);
		MTScriptTreeParser walker = new MTScriptTreeParser(nodeStream); 
		
		walker.setSymbolTable(symbolTable);
		
		ScriptTreeNode scriptNode = walker.evaluator();
		
		DataValue results = scriptNode.evaluate(scriptContext);
	
		System.out.println("Result = " + results.asString());
		
		System.out.println("--- START LABELS ---");
		for (String s : symbolTable.getLabels()) {
			int i = 0;
			for (DataValue l : symbolTable.getLabels(s)) {
				System.out.println(s + "(" + i + ") = " + l.asString());		
				i++;
			}
		}
		System.out.println("--- END LABELS ---");
		
		System.out.println("Time (ms) = " + (System.currentTimeMillis() - startTime));
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(ScriptEvaluator.getInstance("$a = 1 + 1").evaluateNext().toString());
	}

}