package net.rptools.lib.datavalue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class DoubleDataValueTest {
	
	Random random = new Random(1);
	
	@Test public void create() {
		for (int i = 0; i < 20; i++) {
			final double l1 = random.nextDouble();
			final DataValue dv = new DoubleDataValue(l1);
			assertEquals(l1, dv.asDouble(), 0.0);
			assertEquals(DataType.DOUBLE, dv.dataType());
		}
			
	}
	
	
	@Test public void asLong() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final DataValue dv = new DoubleDataValue(d1);
			assertEquals((long)d1, dv.asLong());
		}
	}

	@Test public void asDouble() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final DataValue dv = new DoubleDataValue(d1);
			assertEquals((double)d1, dv.asDouble(), 0.0);
		}
	}
	
	@Test public void asString() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final DataValue dv = new DoubleDataValue(d1);
			assertEquals(Double.toString(d1), dv.asString());
		}
	}

	@Test public void asList() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final DataValue dv = new DoubleDataValue(d1);
			assertEquals(1, dv.asList().size());
			assertEquals(dv, dv.asList().get(0));
		}
	}


	@Test public void addLong() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final long l2 = random.nextLong();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.add(new LongDataValue(l2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(d1 + (double)l2, dv.asDouble(), 0.0);
			
		}
	}
	
	
	@Test public void subtractLong() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final long l2 = random.nextLong();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.subtract(new LongDataValue(l2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(d1 - (double)l2, dv.asDouble(), 0.0);
			
		}
	}
	
	@Test public void multiplyLong() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final long l2 = random.nextLong();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.multiply(new LongDataValue(l2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(d1 * (double)l2, dv.asDouble(), 0.0);
			
		}
	}
	
	
	@Test public void divideLong() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final long l2 = random.nextLong();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.divide(new LongDataValue(l2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(d1 / (double)l2, dv.asDouble(), 0.0);
			
		}
	}
	
	@Test public void remainderLong() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final long l2 = random.nextLong();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.remainder(new LongDataValue(l2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(d1 % (double)l2, dv.asDouble(), 0.0);
		}
	}
	
	
	@Test public void powerLong() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final long l2 = random.nextLong();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.power(new LongDataValue(l2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
		}
	}


	@Test public void addDouble() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final double d2 = random.nextDouble();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.add(new DoubleDataValue(d2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(d1 + d2, dv.asDouble(), 0.0);
		}
	}
	
	
	@Test public void subtractDouble() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final double d2 = random.nextDouble();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.subtract(new DoubleDataValue(d2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(d1 - d2, dv.asDouble(), 0.0);
			
		}
	}
	
	@Test public void multiplyDouble() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final double d2 = random.nextDouble();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.multiply(new DoubleDataValue(d2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(d1 * d2, dv.asDouble(), 0.0);
			
		}
	}
	
	
	@Test public void divideDobule() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final double d2 = random.nextDouble();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.divide(new DoubleDataValue(d2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(d1 / d2, dv.asDouble(), 0.0);
			
		}
	}
	
	@Test public void remainderDouble() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final double d2 = random.nextDouble();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.remainder(new DoubleDataValue(d2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(d1 % d2, dv.asDouble(), 0.0);
			
		}
	}
	
	
	@Test public void powerDouble() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final double d2 = random.nextDouble();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.power(new DoubleDataValue(d2));
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(Math.pow(d1, d2), dv.asDouble(), 0.0);		
		}
	}
	

	@Test public void addString() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final String s2 = Long.toString(random.nextLong());
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.add(new StringDataValue(s2));
			
			assertEquals(DataType.STRING, dv.dataType());
			assertEquals(Double.toString(d1) + s2, dv.asString());
		}
	}
	
	
	@Test(expected=NumberFormatException.class)
	public void subtractString() {
		final double l1 = random.nextDouble();
		final String s2 = "test";
			
		DataValue dv = new DoubleDataValue(l1);
		dv = dv.subtract(new StringDataValue(s2));
	}
	
	@Test
	public void multiplyString() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextInt(10) + 0.5;
			final String s2 = Long.toString(random.nextLong());
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.multiply(new StringDataValue(s2));
			
			final StringBuilder sb = new StringBuilder();
			for (int x = 0; x < (long)d1; x++) {
				sb.append(s2);
			}
			assertEquals(DataType.STRING, dv.dataType());
			assertEquals(sb.toString(), dv.asString());
			
		}
	}
	
	
	@Test(expected=NumberFormatException.class)
	public void divideString() {
		final double d1 = random.nextDouble();
		final String s2 = "1";
			
		DataValue dv = new DoubleDataValue(d1);
		dv = dv.divide(new StringDataValue(s2));
	}
	
	@Test(expected=NumberFormatException.class)
	public void remainderString() {
		final double d1 = random.nextDouble();
		final String s2 = "1";
			
		DataValue dv = new DoubleDataValue(d1);
		dv = dv.remainder(new StringDataValue(s2));
	}
	
	@Test(expected=NumberFormatException.class)
	public void powerString() {
		final double d1 = random.nextDouble();
		final String s2 = "1";
			
		DataValue dv = new DoubleDataValue(d1);
		dv = dv.power(new StringDataValue(s2));
	}


	@Test public void addList() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final List<DataValue> lst2 = new ArrayList<>();
			
			for (int x = 1; x < 20; x++) {
				final double  d2 = random.nextDouble();
				lst2.add(new DoubleDataValue(d2));
			}
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.add(new ListDataValue(lst2));
			
			assertEquals(DataType.LIST, dv.dataType());
			
			final List<DataValue> newList = new ArrayList<>();
			newList.add(new DoubleDataValue(d1));
			newList.addAll(lst2);
			
			assertEquals(newList.size(), dv.asList().size());
			
			final Iterator<DataValue> iter1 = newList.iterator();
			final Iterator<DataValue> iter2 = dv.asList().iterator();
			
			while(iter1.hasNext()) {
				final DataValue dv1 = iter1.next();
				final DataValue dv2 = iter2.next();
				
				assertEquals(dv1, dv2);
			}
		}
	}
	
	
	@Test(expected=NumberFormatException.class)
	public void subtractList() {
		final double d1 = random.nextDouble();
		final List<DataValue> lst2 = new ArrayList<>();
		
		for (int x = 1; x < 20; x++) {
			final double  d2 = random.nextDouble();
			lst2.add(new DoubleDataValue(d2));
		}
			
		DataValue dv = new DoubleDataValue(d1);
		dv = dv.subtract(new ListDataValue(lst2));
	}
	
	@Test(expected=NumberFormatException.class)
	public void multiplyList() {
		final double d1 = random.nextDouble();
		final List<DataValue> lst2 = new ArrayList<>();
		
		for (int x = 1; x < 20; x++) {
			final double d2 = random.nextDouble();
			lst2.add(new DoubleDataValue(d2));
		}
			
		DataValue dv = new DoubleDataValue(d1);
		dv = dv.multiply(new ListDataValue(lst2));
	}
	
	
	@Test(expected=NumberFormatException.class)
	public void divideList() {
		final double d1 = random.nextDouble();
		final List<DataValue> lst2 = new ArrayList<>();
		
		for (int x = 1; x < 20; x++) {
			final double d2 = random.nextDouble();
			lst2.add(new DoubleDataValue(d2));
		}
			
		DataValue dv = new DoubleDataValue(d1);
		dv = dv.divide(new ListDataValue(lst2));
	}
	
	@Test(expected=NumberFormatException.class)
	public void remainderList() {
		final double d1 = random.nextDouble();
		final List<DataValue> lst2 = new ArrayList<>();
		
		for (int x = 1; x < 20; x++) {
			final double d2 = random.nextDouble();
			lst2.add(new DoubleDataValue(d2));
		}
			
		DataValue dv = new DoubleDataValue(d1);
		dv = dv.remainder(new ListDataValue(lst2));
	}
	
	@Test(expected=NumberFormatException.class)
	public void powerList() {
		final double d1 = random.nextDouble();
		final List<DataValue> lst2 = new ArrayList<>();
		
		for (int x = 1; x < 20; x++) {
			final double d2 = random.nextDouble();
			lst2.add(new DoubleDataValue(d2));
		}
			
		DataValue dv = new DoubleDataValue(d1);
		dv = dv.power(new ListDataValue(lst2));
	}


	
	@Test public void negate() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			
			DataValue dv = new DoubleDataValue(d1);
			dv = dv.negate();
			
			assertEquals(DataType.DOUBLE, dv.dataType());
			assertEquals(-d1, dv.asDouble(), 0.0);	
		}
	}
	

	@Test public void label() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			
			DataValue dv = new DoubleDataValue(d1);
			
			assertFalse(dv.hasLabel());
			assertEquals(DataLabel.NO_LABEL, dv.getLabel());
		}
	}
	
	@Test public void hashCodeTest() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final DataValue dv1 = new DoubleDataValue(d1);
			final DataValue dv2 = dv1.negate().negate();
			
			assertEquals(dv1.hashCode(), dv2.hashCode());
		}
	}
	
	@Test public void equalsTest() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final DataValue dv1 = new DoubleDataValue(d1);
			final DataValue dv2 = dv1.negate().negate();

			assertTrue(dv1.equals(dv2));
			assertTrue(dv2.equals(dv1));
		}
	}
	
	
	
	
	
}
