package be.ac.umons.sgl.lazer.g06;

import static org.junit.Assert.*;

import org.junit.Test;

public class TimerTest {
	
	@Test
	public void secondsShortTest() {
		assertEquals("seconds alone", "1s", Time.prettyTime(1, false, false));
	}
	@Test
	public void secondsLongTest() {
		assertEquals("seconds alone", "1seconds", Time.prettyTime(1, false, true));
	}
	@Test
	public void secondsAllTest() {
		assertEquals("seconds alone", "0j 0h 0m 1s", Time.prettyTime(1, true, false));
	}
	@Test
	public void minutesShortTest() {
		assertEquals("seconds alone", "2m", Time.prettyTime(2*60, false, false));
	}
	@Test
	public void minutesLongTest() {
		assertEquals("seconds alone", "2minutes", Time.prettyTime(2*60, false, true));
	}
	@Test
	public void minutesAllTest() {
		assertEquals("seconds alone", "0j 0h 2m 0s", Time.prettyTime(2*60, true, false));
	}
	@Test
	public void hoursShortTest() {
		assertEquals("seconds alone", "1h", Time.prettyTime(60*60, false, false));
	}
	@Test
	public void hoursLongTest() {
		assertEquals("seconds alone", "1heures", Time.prettyTime(60*60, false, true));
	}
	@Test
	public void hoursAllTest() {
		assertEquals("seconds alone", "0j 1h 0m 0s", Time.prettyTime(60*60, true, false));
	}
	@Test
	public void daysShortTest() {
		assertEquals("seconds alone", "1j", Time.prettyTime(60*60*24, false, false));
	}
	@Test
	public void daysLongTest() {
		assertEquals("seconds alone", "1jours", Time.prettyTime(60*60*24, false, true));
	}
	@Test
	public void daysAllTest() {
		assertEquals("seconds alone", "1j 0h 0m 0s", Time.prettyTime(60*60*24, true, false));
	}
	@Test
	public void AllInAllTest() {
		assertEquals("seconds alone", "1j 23h 59m 59s", Time.prettyTime(60*60*24+60*60*23+59*60+59, true, false));
	}
}
