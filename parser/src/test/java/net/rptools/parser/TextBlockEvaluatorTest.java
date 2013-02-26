package net.rptools.parser;

import static org.junit.Assert.assertEquals;

import java.util.Iterator;

import net.rptools.lib.datavalue.DataValueFactory;

import org.junit.Test;

public class TextBlockEvaluatorTest {
	
	
	@Test public void testSingleExpression() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a 1 [2 - 1] here";
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 1);
		
		String lbl = val.getExpressionLabels().iterator().next();
		assertEquals(DataValueFactory.longValue(1).asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
		
		assertEquals(block, txt);
	}

	@Test public void testMultipleExpression() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a 3 [4 - 1] here and 7 [1 + 1 + 5] here";
		
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 2);


		Iterator<String> keyIter = val.getExpressionLabels().iterator();
		String lbl = keyIter.next();
		assertEquals(DataValueFactory.longValue(3).asListValue(), val.getExpressionResult(lbl));

		lbl = keyIter.next();
		assertEquals(DataValueFactory.longValue(7).asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
		
		assertEquals(block, txt);
	}
	
	
	@Test(expected=ExpressionEvaluatorException.class)
	public void incompleteExpression() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		@SuppressWarnings("unused")
		EvaluatedBlock val = eval.evaluate("this is a test that should thrown an exception, testing [1], [2],[3 ");
	}

	@Test public void testSingleExpressionSingleQuote() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a 1] ['1]'] here";
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 1);
		
		String lbl = val.getExpressionLabels().iterator().next();
		assertEquals(DataValueFactory.stringValue("1]").asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
		
		assertEquals(block, txt);
	}
	
	
	@Test public void testMultipleExpressionSingleQuote() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a 3] [4 - 1 + ']'] here and 7]] [1 + 1 + 5 + ']]'] here";
		
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 2);


		Iterator<String> keyIter = val.getExpressionLabels().iterator();
		String lbl = keyIter.next();
		assertEquals(DataValueFactory.stringValue("3]").asListValue(), val.getExpressionResult(lbl));

		lbl = keyIter.next();
		assertEquals(DataValueFactory.stringValue("7]]").asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
		
		assertEquals(block, txt);
	}

	@Test public void testSingleExpressionDoubleQuote() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a 1] [\"1]\"] here";
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 1);
		
		String lbl = val.getExpressionLabels().iterator().next();
		assertEquals(DataValueFactory.stringValue("1]").asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
		
		assertEquals(block, txt);
	}
	
	
	@Test public void testMultipleExpressionDoubleQuote() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a 3] [4 - 1 + \"]\"] here and 7]] [1 + 1 + 5 + \"]]\"] here";
		
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 2);


		Iterator<String> keyIter = val.getExpressionLabels().iterator();
		String lbl = keyIter.next();
		assertEquals(DataValueFactory.stringValue("3]").asListValue(), val.getExpressionResult(lbl));

		lbl = keyIter.next();
		assertEquals(DataValueFactory.stringValue("7]]").asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
		
		assertEquals(block, txt);
	}

	@Test public void testEmbededDoubleQuote() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a \"1] ['\"1]'] here";
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 1);
		
		String lbl = val.getExpressionLabels().iterator().next();
		assertEquals(DataValueFactory.stringValue("\"1]").asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
		
		assertEquals(block, txt);
	}
	
	@Test public void testEmbededSingleQuote() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a '1] [\"'1]\"] here";
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 1);
		
		String lbl = val.getExpressionLabels().iterator().next();
		assertEquals(DataValueFactory.stringValue("'1]").asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
		
		assertEquals(block, txt);
	}
	@Test public void testEmbededDoubleQuotes() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a \"1\"] ['\"1\"]'] here";
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 1);
		
		String lbl = val.getExpressionLabels().iterator().next();
		assertEquals(DataValueFactory.stringValue("\"1\"]").asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
		
		assertEquals(block, txt);
	}
	
	@Test public void testEmbededSingleQuotes() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a '1] [\"'1]'\"] here";
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 1);
		
		String lbl = val.getExpressionLabels().iterator().next();
		assertEquals(DataValueFactory.stringValue("'1]'").asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
		
		assertEquals(block, txt);
	}

	@Test public void testEscapeOpen() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a 1 [2 - 1] here and this should be ignored \\[";
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 1);
		
		String lbl = val.getExpressionLabels().iterator().next();
		assertEquals(DataValueFactory.longValue(1).asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
		
		assertEquals(block.replaceAll("\\\\\\[", "\\["), txt);
	}
	
	@Test public void testEscapeClose() throws ExpressionEvaluatorException {
		TextBlockEvaluator eval = new TextBlockEvaluator();
		String block = "this is a test, and should be a 1 [2 - 1] here and this should be ignored \\]";
		EvaluatedBlock val = eval.evaluate(block);
		
		assertEquals(val.getExpressionLabels().size(), 1);
		
		String lbl = val.getExpressionLabels().iterator().next();
		assertEquals(DataValueFactory.longValue(1).asListValue(), val.getExpressionResult(lbl));
		
		String txt = val.getTemplatedText();
		
		for (String s : val.getExpressionLabels()) {
			txt = txt.replace("${" + s + "}",  "[" + val.getExpression(s) + "]");
		}
				
		assertEquals(block.replaceAll("\\\\]", "\\]"), txt);
	}

}
