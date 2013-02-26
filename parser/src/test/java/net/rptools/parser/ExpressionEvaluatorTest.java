package net.rptools.parser;

import static org.junit.Assert.*;
import net.rptools.lib.datavalue.DataValue;
import net.rptools.lib.datavalue.DataValueFactory;
import net.rptools.lib.datavalue.DataType;
import org.junit.Test;

public class ExpressionEvaluatorTest {

	@Test public void variableAssignment() throws  ExpressionEvaluatorException {
		ScriptEvaluator ep = ScriptEvaluator.getInstance("$a = 12");
		assertTrue(ep.hasNext());
		
		DataValue dv = ep.evaluateNext();
		
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size(), 1);
		assertEquals(12, dv.asList().get(0).asLong());
		assertEquals(12, ep.getSymbolTable().getVariable("a").asLong());
	}
	
	@Test public void variableRead() throws ExpressionEvaluatorException {
		ScriptEvaluator ep = ScriptEvaluator.getInstance("$a");
		ep.getSymbolTable().setVariable("a", DataValueFactory.longValue(22));
		assertTrue(ep.hasNext());
		
		DataValue dv = ep.evaluateNext();
		
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(22, dv.asList().get(0).asLong());	
		
		
	}

	@Test public void propertyAssignment() throws  ExpressionEvaluatorException {
		ScriptEvaluator ep = ScriptEvaluator.getInstance("@a = 12");
		assertTrue(ep.hasNext());
		
		DataValue dv = ep.evaluateNext();
		
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size(), 1);
		assertEquals(12, dv.asList().get(0).asLong());
		assertEquals(12, ep.getSymbolTable().getProperty("a").asLong());
	}
	
	@Test public void propertyRead() throws ExpressionEvaluatorException {
		ScriptEvaluator ep = ScriptEvaluator.getInstance("@a");
		ep.getSymbolTable().setProperty("a", DataValueFactory.longValue(22));
		assertTrue(ep.hasNext());
		
		DataValue dv = ep.evaluateNext();
		
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(22, dv.asList().get(0).asLong());	
		
		
	}
	
	@Test public void variableAndPropertyAssignment() throws  ExpressionEvaluatorException {
		ScriptContext context = new ScriptContextBuilder().toScriptContext();
		
		ScriptEvaluator ep = ScriptEvaluator.getInstance(context, "$a = 12");
		assertTrue(ep.hasNext());
		
		DataValue dv = ep.evaluateNext();
		
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size(), 1);
		assertEquals(12, dv.asList().get(0).asLong());
	
	    ep = ScriptEvaluator.getInstance(context, "@a = 42");
		assertTrue(ep.hasNext());
		
		dv = ep.evaluateNext();
		
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size(), 1);
		assertEquals(42, dv.asList().get(0).asLong());

		assertEquals(12, ep.getSymbolTable().getVariable("a").asLong());
		assertEquals(42, ep.getSymbolTable().getProperty("a").asLong());

	}
	
	@Test public void variableAndPropertyRead() throws ExpressionEvaluatorException {
		ScriptContext context = new ScriptContextBuilder().toScriptContext();
		
		ScriptEvaluator ep = ScriptEvaluator.getInstance(context, "$a");
		ep.getSymbolTable().setVariable("a", DataValueFactory.longValue(22));
		ep.getSymbolTable().setProperty("a", DataValueFactory.longValue(52));
		assertTrue(ep.hasNext());
		
		DataValue dv = ep.evaluateNext();
		
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(22, dv.asList().get(0).asLong());	
		
		ep = ScriptEvaluator.getInstance(context, "@a");
		assertTrue(ep.hasNext());
		
		dv = ep.evaluateNext();
		
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(52, dv.asList().get(0).asLong());	
	}
	
	@Test public void stringLiteral() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("'string1'").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.STRING, dv.asList().get(0).dataType());
		assertEquals("string1", dv.asList().get(0).asString());					

		dv = ScriptEvaluator.getInstance("\"string1\"").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.STRING, dv.asList().get(0).dataType());
		assertEquals("string1", dv.asList().get(0).asString());					
	}
	
	@Test public void longValue() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("22").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.LONG, dv.asList().get(0).dataType());
		assertEquals(22, dv.asList().get(0).asLong());					
	}

	@Test public void doubleValue() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("22.5").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.DOUBLE, dv.asList().get(0).dataType());
		assertEquals(22.5, dv.asList().get(0).asDouble(), 0);					
	}
	
	@Test public void doubleValueLeading0() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("0.7").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.DOUBLE, dv.asList().get(0).dataType());
		assertEquals(0.7, dv.asList().get(0).asDouble(), 0);					
	}

	@Test public void doubleValueNoLeading0() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance(".7").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.DOUBLE, dv.asList().get(0).dataType());
		assertEquals(0.7, dv.asList().get(0).asDouble(), 0);					
	}
	

	@Test public void listValue() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("list(1, 3, 5)").evaluateNext();

		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(3, dv.asList().size());
		assertEquals(DataType.LONG, dv.asList().get(0).dataType());
		assertEquals(1, dv.asList().get(0).asLong());
		assertEquals(DataType.LONG, dv.asList().get(1).dataType());
		assertEquals(3, dv.asList().get(1).asLong());
		assertEquals(DataType.LONG, dv.asList().get(2).dataType());
		assertEquals(5, dv.asList().get(2).asLong());
	}

	@Test public void emptyList() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("list()").evaluateNext();

		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(0, dv.asList().size());
	}

	@Test public void listValueAlias() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("list.create(1, 3, 5)").evaluateNext();

		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(3, dv.asList().size());
		assertEquals(DataType.LONG, dv.asList().get(0).dataType());
		assertEquals(1, dv.asList().get(0).asLong());
		assertEquals(DataType.LONG, dv.asList().get(1).dataType());
		assertEquals(3, dv.asList().get(1).asLong());
		assertEquals(DataType.LONG, dv.asList().get(2).dataType());
		assertEquals(5, dv.asList().get(2).asLong());
	}

	@Test public void emptyListAlias() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("list()").evaluateNext();

		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(0, dv.asList().size());
	}

	
	@Test public void longAddition() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("6 + 7").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.LONG, dv.asList().get(0).dataType());
		assertEquals(13, dv.asList().get(0).asLong());	
	}
	
	@Test public void longSubtraction() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("6 - 7").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.LONG, dv.asList().get(0).dataType());
		assertEquals(-1, dv.asList().get(0).asLong());	
	}

	@Test public void longMultiplication() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("6 * 7").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.LONG, dv.asList().get(0).dataType());
		assertEquals(42, dv.asList().get(0).asLong());			
	}

	@Test public void longDivision() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("12 / 5").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.LONG, dv.asList().get(0).dataType());
		assertEquals(2, dv.asList().get(0).asLong());			
	}
	
	@Test public void longPower() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("12 ^ 2").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.LONG, dv.asList().get(0).dataType());
		assertEquals(144, dv.asList().get(0).asLong());			
	}
 
	@Test public void longRemainder() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("22 % 7").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.LONG, dv.asList().get(0).dataType());
		assertEquals(1, dv.asList().get(0).asLong());			
	}
	
	@Test public void longNegate() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("-45").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.LONG, dv.asList().get(0).dataType());
		assertEquals(-45, dv.asList().get(0).asLong());			
	}

	@Test public void doubleAddition() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("6.1 + 7.3").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.DOUBLE, dv.asList().get(0).dataType());
		assertEquals(13.4, dv.asList().get(0).asDouble(), 0.0001);	
	}
	
	@Test public void doubleSubtraction() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("6.2 - 7.5").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.DOUBLE, dv.asList().get(0).dataType());
		assertEquals(-1.3, dv.asList().get(0).asDouble(), 0.0001);	
	}

	@Test public void doubleMultiplication() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("6.1 * 7").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(42.7, dv.asList().get(0).asDouble(), 0.0001);			
	}

	@Test public void doubleDivision() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("12.0 / 5").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.DOUBLE, dv.asList().get(0).dataType());
		assertEquals(2.4, dv.asList().get(0).asDouble(), 0.0001);			
	}
	
	@Test public void doublePower() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("49 ^ 0.5").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.DOUBLE, dv.asList().get(0).dataType());
		assertEquals(7, dv.asList().get(0).asDouble(), 0.0001);			
	}

	@Test public void doubleRemainder() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("22.5 % 7").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.DOUBLE, dv.asList().get(0).dataType());
		assertEquals(1.5, dv.asList().get(0).asDouble(), 0.0001);			
	}
	
	@Test public void doubleNegate() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("-45.7").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.DOUBLE, dv.asList().get(0).dataType());
		assertEquals(-45.7, dv.asList().get(0).asDouble(), 0.0001);			
	}

	@Test public void stringAdd() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("'string1' + 'string2'").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.STRING, dv.asList().get(0).dataType());
		assertEquals("string1string2", dv.asList().get(0).asString());					

		dv = ScriptEvaluator.getInstance("\"string1\" + \"string2\"").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.STRING, dv.asList().get(0).dataType());
		assertEquals("string1string2", dv.asList().get(0).asString());					
	}
	
	@Test public void stringSubtract() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("'string1string2' - 'ing'").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.STRING, dv.asList().get(0).dataType());
		assertEquals("str1str2", dv.asList().get(0).asString());					
	}
	
	@Test public void stringMultiplyLong() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("'string1' * 2").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.STRING, dv.asList().get(0).dataType());
		assertEquals("string1string1", dv.asList().get(0).asString());					
	
		dv = ScriptEvaluator.getInstance("2 * 'string1'").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.STRING, dv.asList().get(0).dataType());
		assertEquals("string1string1", dv.asList().get(0).asString());					
	}

	@Test public void stringMultiplyDouble() throws ExpressionEvaluatorException {
		DataValue dv = ScriptEvaluator.getInstance("'string1' * 4.3").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.STRING, dv.asList().get(0).dataType());
		assertEquals("string1string1string1string1", dv.asList().get(0).asString());					
	
		dv = ScriptEvaluator.getInstance("4.3 * 'string1'").evaluateNext();
		assertEquals(dv.dataType(), DataType.LIST);
		assertEquals(1, dv.asList().size());
		assertEquals(DataType.STRING, dv.asList().get(0).dataType());
		assertEquals("string1string1string1string1", dv.asList().get(0).asString());					
	}
	
	
	@Test public void RemoveMe() throws ExpressionEvaluatorException {
		try {
			DataValue dv = ScriptEvaluator.getInstance("list.shuffle(list(3,1,8,9,2,7))").evaluateNext();
			System.out.println(dv);
            dv = ScriptEvaluator.getInstance("listSum(1,2,3)").evaluateNext();
            System.out.println(dv);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	

}