package ViewModel;

import static org.junit.Assert.*;

import org.junit.Test;

public class LogInVMTest {
	
	LogInVM tester = new LogInVM();
	
	

	@Test
	public void testGetEmailadress() {
		tester.setEmailadress("vm@mail.com");
		assertEquals("Get email at login", "vm@mail.com",  tester.getEmailadress());
	}

	@Test
	public void testGetPassword() {
		tester.setPassword("mona");
		assertEquals("Get email at login", "mona",  tester.getPassword());
	}

}
