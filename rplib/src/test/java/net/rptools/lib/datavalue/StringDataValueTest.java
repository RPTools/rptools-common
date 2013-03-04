package net.rptools.lib.datavalue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class StringDataValueTest {
	
	Random random = new Random(1);
	
	@Test public void create() {
		for (int i = 0; i < 20; i++) {
			final String s1 = Double.toString(random.nextDouble());
			final DataValue dv = new StringDataValue(s1);
			assertEquals(s1, dv.asString());
			assertEquals(DataType.STRING, dv.dataType());
		}
			
	}
	
	
	@Test public void asLongOk() {
		for (int i = 0; i < 20; i++) {
			final long l1 = random.nextLong();
			final String s1 = Long.toString(l1);
			
			final DataValue dv = new StringDataValue(s1);
			assertEquals(l1, dv.asLong());
		}
	}
	
	@Test(expected=NumberFormatException.class)
	public void asLongException() {
		final DataValue dv = new StringDataValue("test");
		dv.asLong();
	}
	
	
	@Test public void asDoublOKe() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final String s1 = Double.toString(d1);
			final DataValue dv = new StringDataValue(s1);
			assertEquals(d1, dv.asDouble(), 0.0);
		}
	}
	
	@Test(expected=NumberFormatException.class)
	public void asDoubleException() {
		final DataValue dv = new StringDataValue("test");
		dv.asDouble();
	}
	
	@Test public void asString() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final String s1 = Double.toString(d1);
			final DataValue dv = new StringDataValue(s1);
			assertEquals(Double.toString(d1), dv.asString());
		}
	}

	@Test public void asList() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final String s1 = Double.toString(d1);
			final DataValue dv = new StringDataValue(s1);
			assertEquals(1, dv.asList().size());
			assertEquals(dv, dv.asList().get(0));
		}
	}


	@Test public void addLong() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final String s1 = Double.toString(d1);
			final long l2 = random.nextLong();
			
			DataValue dv = new StringDataValue(s1);
			dv = dv.add(new LongDataValue(l2));
			
			assertEquals(DataType.STRING, dv.dataType());
			assertEquals(s1 + Long.toString(l2), dv.asString());
			
		}
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void subtractLong() {
		final DataValue dv = new StringDataValue("11");
		dv.subtract(new LongDataValue(1));
	}
	
	@Test public void multiplyLong() {
		for (int i = 0; i < 20; i++) {
			final long l1 = random.nextInt(10);
			final String s2 = Long.toString(random.nextLong());
			
			DataValue dv = new StringDataValue(s2);
			dv = dv.multiply(new LongDataValue(l1));
			
			final StringBuilder sb = new StringBuilder();
			for (int x = 0; x < l1; x++) {
				sb.append(s2);
			}
			assertEquals(DataType.STRING, dv.dataType());
			assertEquals(sb.toString(), dv.asString());
			
		}
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void divideLong() {
		final DataValue dv = new StringDataValue("11");
		dv.divide(new LongDataValue(1));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void remainderLong() {
		final DataValue dv = new StringDataValue("11");
		dv.remainder(new LongDataValue(1));
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void powerLong() {
		final DataValue dv = new StringDataValue("11");
		dv.power(new LongDataValue(1));
	}


	@Test public void addDouble() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final String s1 = Double.toString(d1);
			final double d2 = random.nextDouble();
			
			DataValue dv = new StringDataValue(s1);
			dv = dv.add(new DoubleDataValue(d2));
			
			assertEquals(DataType.STRING, dv.dataType());
			assertEquals(s1 + Double.toString(d2), dv.asString());
			
		}
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void subtractDouble() {
		final DataValue dv = new StringDataValue("11.0");
		dv.subtract(new DoubleDataValue(1.0));
	}
	
	@Test public void multiplyDouble() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextInt(10) + 0.5;
			final String s2 = Long.toString(random.nextLong());
			
			DataValue dv = new StringDataValue(s2);
			dv = dv.multiply(new DoubleDataValue(d1));
			
			final StringBuilder sb = new StringBuilder();
			for (int x = 0; x < (int)d1; x++) {
				sb.append(s2);
			}
			assertEquals(DataType.STRING, dv.dataType());
			assertEquals(sb.toString(), dv.asString());
			
		}

	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void divideDobule() {
		final DataValue dv = new StringDataValue("11.0");
		dv.subtract(new DoubleDataValue(1.0));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void remainderDouble() {
		final DataValue dv = new StringDataValue("11.0");
		dv.subtract(new DoubleDataValue(1.0));
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void powerDouble() {
		final DataValue dv = new StringDataValue("11.0");
		dv.subtract(new DoubleDataValue(1.0));
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
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void subtractString() {
		final double l1 = random.nextDouble();
		final String s2 = "test";
			
		DataValue dv = new DoubleDataValue(l1);
		dv = dv.subtract(new StringDataValue(s2));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void multiplyString() {
		final double d1 = random.nextDouble();
		final String s1 = Double.toString(d1);
			
		DataValue dv = new StringDataValue(s1);
		dv = dv.multiply(dv);
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void divideString() {
		final double d1 = random.nextDouble();
		final String s1 = Double.toString(d1);
			
		DataValue dv = new StringDataValue(s1);
		dv = dv.divide(dv);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void remainderString() {
		final double d1 = random.nextDouble();
		final String s1 = Double.toString(d1);
			
		DataValue dv = new StringDataValue(s1);
		dv = dv.remainder(dv);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void powerString() {
		final double d1 = random.nextDouble();
		final String s1 = Double.toString(d1);
			
		DataValue dv = new StringDataValue(s1);
		dv = dv.power(dv);
	}


	@Test public void addList() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final String s1 = Double.toString(d1);
			final List<DataValue> lst2 = new ArrayList<>();
			
			for (int x = 1; x < 20; x++) {
				final double  d2 = random.nextDouble();
				lst2.add(new DoubleDataValue(d2));
			}
			
			DataValue dv = new StringDataValue(s1);
			DataValue dv2 = new ListDataValue(lst2);
			
			dv = dv.add(dv2);
			
			assertEquals(DataType.LIST, dv.dataType());
        }
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void subtractList() {
		final double d1 = random.nextDouble();
		final String s1 = Double.toString(d1);
		final List<DataValue> lst2 = new ArrayList<>();
		
		for (int x = 1; x < 20; x++) {
			final double  d2 = random.nextDouble();
			lst2.add(new DoubleDataValue(d2));
		}
			
		DataValue dv = new StringDataValue(s1);
		dv = dv.subtract(new ListDataValue(lst2));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void multiplyList() {
		final double d1 = random.nextDouble();
		final String s1 = Double.toString(d1);

		final List<DataValue> lst2 = new ArrayList<>();
		
		for (int x = 1; x < 20; x++) {
			final double d2 = random.nextDouble();
			lst2.add(new DoubleDataValue(d2));
		}
			
		DataValue dv = new StringDataValue(s1);
		dv = dv.multiply(new ListDataValue(lst2));
	}
	
	
	@Test(expected=UnsupportedOperationException.class)
	public void divideList() {
		final double d1 = random.nextDouble();
		final String s1 = Double.toString(d1);

		final List<DataValue> lst2 = new ArrayList<>();
		
		for (int x = 1; x < 20; x++) {
			final double d2 = random.nextDouble();
			lst2.add(new DoubleDataValue(d2));
		}
			
		DataValue dv = new StringDataValue(s1);
		dv = dv.divide(new ListDataValue(lst2));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void remainderList() {
		final double d1 = random.nextDouble();
		final String s1 = Double.toString(d1);

		final List<DataValue> lst2 = new ArrayList<>();
		
		for (int x = 1; x < 20; x++) {
			final double d2 = random.nextDouble();
			lst2.add(new DoubleDataValue(d2));
		}
			
		DataValue dv = new StringDataValue(s1);
		dv = dv.remainder(new ListDataValue(lst2));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void powerList() {
		final double d1 = random.nextDouble();
		final String s1 = Double.toString(d1);

		final List<DataValue> lst2 = new ArrayList<>();
		
		for (int x = 1; x < 20; x++) {
			final double d2 = random.nextDouble();
			lst2.add(new DoubleDataValue(d2));
		}
			
		DataValue dv = new StringDataValue(s1);
		dv = dv.power(new ListDataValue(lst2));
	}


	@Test(expected=UnsupportedOperationException.class)
	public void negate() {
		final double d1 = random.nextDouble();
		final String s1 = Double.toString(d1);

			
		DataValue dv = new StringDataValue(s1);
		dv = dv.negate();			
	}
	

	@Test public void label() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final String s1 = Double.toString(d1);

			DataValue dv = new StringDataValue(s1);
			
			assertFalse(dv.hasLabel());
			assertEquals(DataLabel.NO_LABEL, dv.getLabel());
		}
	}
	
	@Test public void hashCodeTest() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final String s1 = Double.toString(d1);

			final DataValue dv1 = new StringDataValue(s1);
			final DataValue dv2 = new StringDataValue(s1);
			
			assertEquals(dv1.hashCode(), dv2.hashCode());
		}
	}
	
	@Test public void equalsTest() {
		for (int i = 0; i < 20; i++) {
			final double d1 = random.nextDouble();
			final String s1 = Double.toString(d1);

			final DataValue dv1 = new StringDataValue(s1);
			final DataValue dv2 = new StringDataValue(s1);

			assertTrue(dv1.equals(dv2));
			assertTrue(dv2.equals(dv1));
		}
	}
}
