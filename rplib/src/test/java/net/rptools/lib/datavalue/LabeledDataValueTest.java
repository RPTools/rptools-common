package net.rptools.lib.datavalue;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;

public class LabeledDataValueTest {

	@Test public void labeledLong() {
		final DataValue dv = new LabeledDataValue(new LongDataValue(1), new DataLabel("Test", 0));
		assertEquals(new DataLabel("Test", 0), dv.getLabel());	
		
		assertEquals(DataLabel.NO_LABEL, dv.add(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new DoubleDataValue(1.1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new StringDataValue("1")).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new ListDataValue(Collections.<DataValue>emptyList())).getLabel());

		assertEquals(DataLabel.NO_LABEL, dv.subtract(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.subtract(new DoubleDataValue(1.1)).getLabel());

		assertEquals(DataLabel.NO_LABEL, dv.divide(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.divide(new DoubleDataValue(1.1)).getLabel());

		assertEquals(DataLabel.NO_LABEL, dv.remainder(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.remainder(new DoubleDataValue(1.1)).getLabel());

		assertEquals(DataLabel.NO_LABEL, dv.power(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.power(new DoubleDataValue(1.1)).getLabel());
		
	}
	
	@Test public void labeledDouble() {
		DataValue dv = new LabeledDataValue(new DoubleDataValue(1.1), new DataLabel("Test1", 2));
		assertEquals(new DataLabel("Test1", 2), dv.getLabel());
		
		assertEquals(DataLabel.NO_LABEL, dv.add(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new DoubleDataValue(1.1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new StringDataValue("1")).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new ListDataValue(Collections.<DataValue>emptyList())).getLabel());

		assertEquals(DataLabel.NO_LABEL, dv.subtract(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.subtract(new DoubleDataValue(1.1)).getLabel());

		assertEquals(DataLabel.NO_LABEL, dv.divide(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.divide(new DoubleDataValue(1.1)).getLabel());

		assertEquals(DataLabel.NO_LABEL, dv.remainder(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.remainder(new DoubleDataValue(1.1)).getLabel());

		assertEquals(DataLabel.NO_LABEL, dv.power(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.power(new DoubleDataValue(1.1)).getLabel());
	}

	@Test public void labeledString() {
		DataValue dv = new LabeledDataValue(new StringDataValue("blah"), new DataLabel("Test1", 4));
		assertEquals(new DataLabel("Test1", 4), dv.getLabel());
		
		assertEquals(DataLabel.NO_LABEL, dv.add(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new DoubleDataValue(1.1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new StringDataValue("1")).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new ListDataValue(Collections.<DataValue>emptyList())).getLabel());
	}

	@Test public void labeledList() {
		DataValue dv = new LabeledDataValue(new ListDataValue(Collections.<DataValue>emptyList()), new DataLabel("Test", 2));
		assertEquals(new DataLabel("Test", 2), dv.getLabel());

		assertEquals(DataLabel.NO_LABEL, dv.add(new LongDataValue(1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new DoubleDataValue(1.1)).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new StringDataValue("1")).getLabel());
		assertEquals(DataLabel.NO_LABEL, dv.add(new ListDataValue(Collections.<DataValue>emptyList())).getLabel());

		assertEquals(DataLabel.NO_LABEL, dv.subtract(new ListDataValue(Collections.<DataValue>emptyList())).getLabel());
	}

}
