package net.rptools.lib.datavalue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.junit.Test;

public class ListDataValueTest {
	
	
	Random random = new Random(1);
	
	
	private boolean listEqual(List<DataValue> lst1, List<DataValue> lst2) {
		boolean eq = false;
		
		if (lst1.size() == lst2.size()) {
			final Iterator<DataValue> iter1 = lst1.iterator();
			final Iterator<DataValue> iter2 = lst2.iterator();
			
			eq = true;
			while (iter1.hasNext()) {
				if (iter1.next().equals(iter2.next()) == false) {
					eq = false;
					break;
				}
			}
		}
		
		return eq;
	}
	
	private List<DataValue> generateList() {
		final List<DataValue> lst = new ArrayList<>(20);
		for (int i = 0; i < 20; i++) {
			final String s1 = Double.toString(random.nextDouble());
			lst.add(new StringDataValue(s1));
		}
		return lst;
	}
	
	@Test public void create() {
		for (int i = 0; i < 20; i++) {
			final List<DataValue> lst = generateList();
			final DataValue dvl = new ListDataValue(lst);
			
			assertEquals(DataType.LIST, dvl.dataType());
			assertTrue(listEqual(lst, dvl.asList()));
		}
			
	}
	
	
	
	@Test(expected=NumberFormatException.class)
	public void asLong() {
		final DataValue dv = new ListDataValue(generateList());
		dv.asLong();
	}
	

	
	@Test(expected=NumberFormatException.class)
	public void asDouble() {
		final DataValue dv = new ListDataValue(generateList());
		dv.asDouble();
	}
	
	@Test public void asString() {
		final List<DataValue> lst1 = generateList();
		final DataValue dv = new ListDataValue(lst1);
		final String[] vals = dv.asString().split(",");
		assertEquals(lst1.size(), vals.length);
	}

	@Test public void asList() {
		for (int i = 0; i < 20; i++) {
			final List<DataValue> lst = generateList();
			final DataValue dvl = new ListDataValue(lst);
			
			assertEquals(DataType.LIST, dvl.dataType());
			assertTrue(listEqual(lst, dvl.asList()));
		}
	}


	@Test public void addLong() {
		for (int i = 0; i < 20; i++) {
			List<DataValue> lst1 = generateList(); 
			final long l2 = random.nextLong();
			
			DataValue dv = new ListDataValue(lst1);
			final DataValue dv2 = new LongDataValue(l2);
			dv = dv.add(dv2);
			
			lst1.add(dv2);
			assertEquals(DataType.LIST, dv.dataType());
			assertTrue(listEqual(lst1, dv.asList()));
			
		}
	}
	
	@Test(expected=NumberFormatException.class)
	public void subtractLong() {
		final DataValue dv = new ListDataValue(generateList());
		dv.subtract(new LongDataValue(1));
	}
	
	@Test(expected=NumberFormatException.class)
	public void multiplyLong() {
		final DataValue dv = new ListDataValue(generateList());
		dv.multiply(new LongDataValue(1));
	}
	
	
	@Test(expected=NumberFormatException.class)
	public void divideLong() {
		final DataValue dv = new ListDataValue(generateList());
		dv.divide(new LongDataValue(1));
	}
	
	@Test(expected=NumberFormatException.class)
	public void remainderLong() {
		final DataValue dv = new ListDataValue(generateList());
		dv.remainder(new LongDataValue(1));
	}
	
	
	@Test(expected=NumberFormatException.class)
	public void powerLong() {
		final DataValue dv = new ListDataValue(generateList());
		dv.power(new LongDataValue(1));
	}


	@Test public void addDouble() {
		for (int i = 0; i < 20; i++) {
			List<DataValue> lst1 = generateList(); 
			final double d2 = random.nextDouble();
			
			DataValue dv = new ListDataValue(lst1);
			final DataValue dv2 = new DoubleDataValue(d2);
			dv = dv.add(dv2);
			
			lst1.add(dv2);
			assertEquals(DataType.LIST, dv.dataType());
			assertTrue(listEqual(lst1, dv.asList()));
			
		}	}
	
	
	@Test(expected=NumberFormatException.class)
	public void subtractDouble() {
		final DataValue dv = new ListDataValue(generateList());
		dv.subtract(new DoubleDataValue(1.0));
	}
	
	@Test(expected=NumberFormatException.class)
	public void multiplyDouble() {
		final DataValue dv = new ListDataValue(generateList());
		dv.multiply(new DoubleDataValue(1.0));
	}
	
	
	@Test(expected=NumberFormatException.class)
	public void divideDobule() {
		final DataValue dv = new ListDataValue(generateList());
		dv.subtract(new DoubleDataValue(1.0));
	}
	
	@Test(expected=NumberFormatException.class)
	public void remainderDouble() {
		final DataValue dv = new ListDataValue(generateList());
		dv.subtract(new DoubleDataValue(1.0));
	}
	
	
	@Test(expected=NumberFormatException.class)
	public void powerDouble() {
		final DataValue dv = new ListDataValue(generateList());
		dv.subtract(new DoubleDataValue(1.0));
	}
	

	@Test public void addString() {
		for (int i = 0; i < 20; i++) {
			final List<DataValue> lst1 = generateList();
			final String s2 = Long.toString(random.nextLong());
			
			DataValue dv = new ListDataValue(lst1);
			dv = dv.add(new StringDataValue(s2));
			
			assertEquals(DataType.LIST, dv.dataType());
			lst1.add(new StringDataValue(s2));
			assertTrue(listEqual(lst1, dv.asList()));
		}
	}
	
	
	@Test(expected=NumberFormatException.class)
	public void subtractString() {
		final DataValue dv = new ListDataValue(generateList());
		dv.subtract(new StringDataValue("1"));
	}
	
	@Test(expected=NumberFormatException.class)
	public void multiplyString() {
		final DataValue dv = new ListDataValue(generateList());
		dv.multiply(new StringDataValue("1"));
	}
	
	
	@Test(expected=NumberFormatException.class)
	public void divideString() {
		final DataValue dv = new ListDataValue(generateList());
		dv.divide(new StringDataValue("1"));
	}
	
	@Test(expected=NumberFormatException.class)
	public void remainderString() {
		final DataValue dv = new ListDataValue(generateList());
		dv.remainder(new StringDataValue("1"));
	}
	
	@Test(expected=NumberFormatException.class)
	public void powerString() {
		final DataValue dv = new ListDataValue(generateList());
		dv.power(new StringDataValue("1"));
	}


	@Test public void addList() {
		for (int i = 0; i < 20; i++) {
			final List<DataValue> lst1 = generateList();
			final List<DataValue> lst2 = generateList();
			final List<DataValue> lst3 = new ArrayList<>(lst1.size() + lst2.size());
			lst3.addAll(lst1);
			lst3.addAll(lst2);
			
			
			DataValue dv = new ListDataValue(lst1);
			DataValue dv2 = new ListDataValue(lst2);
			
			DataValue dv3 = dv.add(dv2);
			
			assertEquals(DataType.LIST, dv3.dataType());
			assertTrue(listEqual(lst3, dv3.asList()));
		}
	}
	
	@Test public void addEmptyList() {
		for (int i = 0; i < 20; i++) {
			final List<DataValue> lst1 = generateList();
			final List<DataValue> lst2 = new ArrayList<DataValue>();
			
			
			DataValue dv = new ListDataValue(lst1);
			DataValue dv2 = new ListDataValue(lst2);
			
			DataValue dv3 = dv.add(dv2);
			
			assertEquals(DataType.LIST, dv3.dataType());
			assertTrue(listEqual(dv.asList(), dv3.asList()));
		}
	}
	
	
	@Test
	public void subtractList() {
		for (int i = 0; i < 20; i++) {
			final List<DataValue> lst1 = generateList();
			final List<DataValue> lst2 = new ArrayList<>(lst1.size());
			
			lst2.addAll(lst1);
			for (int x = 0; x < i && x < lst1.size(); x++) {
				lst2.remove(0);
			}
			final List<DataValue> lst3 = new ArrayList<>(lst1.size() + lst2.size());
			lst3.addAll(lst1);
			lst3.removeAll(lst2);
			
			
			DataValue dv = new ListDataValue(lst1);
			DataValue dv2 = new ListDataValue(lst2);
			
			DataValue dv3 = dv.subtract(dv2);
			
			assertEquals(DataType.LIST, dv3.dataType());
			assertTrue(listEqual(lst3, dv3.asList()));
		}
	}
	
	@Test public void subtractEmptyList() {
		for (int i = 0; i < 20; i++) {
			final List<DataValue> lst1 = generateList();
			final List<DataValue> lst2 = new ArrayList<DataValue>();
			
			
			DataValue dv = new ListDataValue(lst1);
			DataValue dv2 = new ListDataValue(lst2);
			
			DataValue dv3 = dv.subtract	(dv2);
			
			assertEquals(DataType.LIST, dv3.dataType());
			assertTrue(listEqual(dv.asList(), dv3.asList()));
		}
	}

	
	@Test(expected=NumberFormatException.class)
	public void multiplyList() {
		final DataValue dv = new ListDataValue(generateList());
		dv.multiply(dv);
	}
	
	
	@Test(expected=NumberFormatException.class)
	public void divideList() {
		final DataValue dv = new ListDataValue(generateList());
		dv.divide(dv);
	}
	
	@Test(expected=NumberFormatException.class)
	public void remainderList() {
		final DataValue dv = new ListDataValue(generateList());
		dv.remainder(dv);
	}
	
	@Test(expected=NumberFormatException.class)
	public void powerList() {
		final DataValue dv = new ListDataValue(generateList());
		dv.power(dv);
	}


	@Test(expected=NumberFormatException.class)
	public void negate() {
		final DataValue dv = new ListDataValue(generateList());
		dv.negate();			
	}
	

	@Test public void label() {
		for (int i = 0; i < 20; i++) {
			final DataValue dv = new ListDataValue(generateList());

			assertFalse(dv.hasLabel());
			assertEquals(DataLabel.NO_LABEL, dv.getLabel());
		}
	}
	
	@Test public void hashCodeTest() {
		for (int i = 0; i < 20; i++) {
			final List<DataValue> lst = generateList();
			final DataValue dv1 = new ListDataValue(lst);
			final DataValue dv2 = new ListDataValue(lst);
			
			assertEquals(dv1.hashCode(), dv2.hashCode());
		}
	}
	
	@Test public void equalsTest() {
		for (int i = 0; i < 20; i++) {
			final List<DataValue> lst = generateList();
			final DataValue dv1 = new ListDataValue(lst);
			final DataValue dv2 = new ListDataValue(lst);
			
			assertTrue(dv1.equals(dv2));
			assertTrue(dv2.equals(dv1));
		}
	}
}
