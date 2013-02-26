package net.rptools.lib;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MD5KeyTest {
	
	private static final String[] values = {
		"This is a test string",
		"Yet another test string",
		"And yet another test string",
		"How many test strings do you want?!?!"
	};
	
	private static final String[] knownMD5HexValues = {
		"c639efc1e98762233743a75e7798dd9c",
		"f21dc2a9a191889018ab60121294ae1f",
		"8c40a99f5bd7d2ce7ad8d5ea8d62d58f",
		"ae80da675eff54499264941d3a3aac13"
	};
	
	
	@Test public void byteMD5Key() {
		for (int i = 0; i < values.length; i++) {
			MD5Key md5key = new MD5Key(values[i].getBytes());
			assertEquals(md5key.toString(), knownMD5HexValues[i]);
		}
	}
	
	@Test public void streamMD5Key() throws IOException {
		for (int i = 0; i < values.length; i++) {
			MD5Key md5key = new MD5Key(new 
					ByteArrayInputStream(values[i].getBytes())
			);
			assertEquals(md5key.toString(), knownMD5HexValues[i]);
		}		
	}
	
	@Test public void testEquals() throws IOException {
		for (int i = 0; i < values.length; i++) {
			MD5Key md5key1 = new MD5Key(new 
					ByteArrayInputStream(values[i].getBytes())
			);
			MD5Key md5key2 = new MD5Key(values[i].getBytes());
			MD5Key md5key3 = MD5Key.fromString(md5key1.toString());
			assertEquals(md5key1, md5key2);
			assertEquals(md5key1, md5key3);
			assertEquals(md5key2, md5key3);
		}
	}

	@Test public void testHash() throws IOException {
		for (int i = 0; i < values.length; i++) {
			MD5Key md5key1 = new MD5Key(new 
					ByteArrayInputStream(values[i].getBytes())
			);
			MD5Key md5key2 = new MD5Key(values[i].getBytes());
			MD5Key md5key3 = MD5Key.fromString(md5key1.toString());
			assertEquals(md5key1.hashCode(), md5key2.hashCode());
			assertEquals(md5key1.hashCode(), md5key3.hashCode());
			assertEquals(md5key2.hashCode(), md5key3.hashCode());
		}
	}

}
