package com.proquest.interview.phonebook;

import org.junit.Test;
import static org.junit.Assert.assertTrue;
public class PhoneBookImplTest {
	
	public PhoneBook initPhoneBook() {
		return new PhoneBookImpl();
	}
	
	@Test
	public void shouldAddFindPerson() throws Exception {
		PhoneBook phoneBook = initPhoneBook();
		
		
		assertTrue(phoneBook.findPerson("Cynthia", "Smith") == null);
		Person newPerson = new Person();
		newPerson.setFirstName("Cynthia");
		newPerson.setLastName("Smith");
		newPerson.setPhoneNumber("(824) 128-8758");
		newPerson.setAddress("875 Main St, Ann Arbor, MI");
		
		phoneBook.addPerson(newPerson);
		
		assertTrue(phoneBook.findPerson("Cynthia", "Smith") != null);
	}
}
