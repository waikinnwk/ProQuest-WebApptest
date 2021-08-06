package com.proquest.interview.phonebook;

public interface PhoneBook {
	public Person findPerson(String firstName, String lastName) throws Exception ;
	public void addPerson(Person newPerson) throws Exception ;
}
