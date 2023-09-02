package fr.calc.classes;

import static org.junit.Assert.*;
import org.junit.Test;

public class CalcTest {

	@Test
	public void testGo() {
		Calc calc = new Calc("3x2");
	    assertEquals("6.0",calc.go() );
	}
}
