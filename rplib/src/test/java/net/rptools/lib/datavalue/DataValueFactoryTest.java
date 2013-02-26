package net.rptools.lib.datavalue;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

public class DataValueFactoryTest {

	@Test public void longDataValue() {
		DataValue dv = DataValueFactory.longValue(1);
		assertEquals(DataType.LONG, dv.dataType());
		assertEquals(1, dv.asLong());
	}

	@Test public void doubleDataValue() {
		DataValue dv = DataValueFactory.doubleValue(1.2);
		assertEquals(DataType.DOUBLE, dv.dataType());
		assertEquals(1.2, dv.asDouble(), 0.0);
	}

	@Test public void stringDataValue() {
		DataValue dv = DataValueFactory.stringValue("test");
		assertEquals(DataType.STRING, dv.dataType());
		assertEquals("test", dv.asString());
	}

	@Test public void listDataValue() {
		DataValue[] arr = {
		        DataValueFactory.longValue(2),
		        DataValueFactory.longValue(5)
		};
		
		DataValue dv = DataValueFactory.listValue(Arrays.asList(arr));
		assertEquals(DataType.LIST, dv.dataType());
		assertEquals(2, dv.asList().size());
		
		assertEquals(2, dv.asList().get(0).asLong());
		assertEquals(5, dv.asList().get(1).asLong());
		
	}


}
