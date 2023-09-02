package fr.calc.classes;

import static org.junit.Assert.*;

import org.junit.Test;

public class ProcessTest {

	@Test
	public void testGo() {
		Process process = new Process("(3+2)x2");
		assertEquals("10.0", process.go());
	}

}
