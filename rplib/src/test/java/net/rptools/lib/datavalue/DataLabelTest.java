package net.rptools.lib.datavalue;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DataLabelTest {

	private static final String[] LABELS = {
		"testing",
		"test123",
		"blah"
	};
	
	@Test public void testDataLabel() {
		for (int i = 0; i < 50; i++) {
			for (int x = 0; x < LABELS.length; x++) {
				DataLabel dl = new DataLabel(LABELS[x], i);
				assertEquals(LABELS[x], dl.getName());
				assertEquals(i, dl.getIndex());
			}
		}
	}
}
