package com.proquest.interview.phonebook;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.proquest.interview.util.DatabaseUtil;

public class PhoneBookImpl implements PhoneBook {
	public static List<Person> people;
	
	@Override
	public void addPerson(Person newPerson) throws Exception {
		//TODO: write this method
		Connection cn = null;
		PreparedStatement ps = null;
		try {
			cn = DatabaseUtil.getConnection();
			ps =cn.prepareStatement("INSERT INTO PHONEBOOK "
					+ "(FIRSTNAME,LASTNAME, PHONENUMBER, ADDRESS) "
					+ " VALUES(?,?,?,?)");
			int i =0;
			ps.setString(++i, newPerson.getFirstName());
			ps.setString(++i, newPerson.getLastName());
			ps.setString(++i, newPerson.getPhoneNumber());
			ps.setString(++i, newPerson.getAddress());
			ps.execute();
			cn.commit();
			people.add(newPerson);
		}
		catch(Exception e) {
			throw e;
		}
		finally {
			if(ps!=null)
				try{ps.close();}catch(Exception c) {};
			if(cn!=null)
				try{cn.close();}catch(Exception c) {};
		}
		
	}
	
	@Override
	public Person findPerson(String firstName, String lastName) {
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Person result = null;
		try {
			people = new ArrayList<Person>();
			cn = DatabaseUtil.getConnection();
			ps =cn.prepareStatement("SELECT FIRSTNAME,LASTNAME, PHONENUMBER, ADDRESS"
					+ " FROM PHONEBOOK WHERE "
					+ " FIRSTNAME = ? AND"
					+ " LASTNAME = ? ");
			int i =0;
			ps.setString(++i, firstName);
			ps.setString(++i, lastName);
			rs =ps.executeQuery();
			while(rs.next()) {
				result = new Person();
				result.setFirstName(rs.getString("FIRSTNAME"));
				result.setLastName(rs.getString("LASTNAME"));
				result.setPhoneNumber(rs.getString("PHONENUMBER"));
				result.setAddress(rs.getString("ADDRESS"));
				break;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(rs != null)
				try{rs.close();}catch(Exception c) {};
			if(ps!=null)
				try{ps.close();}catch(Exception c) {};
			if(cn!=null)
				try{cn.close();}catch(Exception c) {};
		}
		return result;
	}
	
	public PhoneBookImpl() {
		DatabaseUtil.initDB();  //You should not remove this line, it creates the in-memory database
		Connection cn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			people = new ArrayList<Person>();
			cn = DatabaseUtil.getConnection();
			ps =cn.prepareStatement("SELECT FIRSTNAME,LASTNAME, PHONENUMBER, ADDRESS"
					+ " FROM PHONEBOOK ");
			rs =ps.executeQuery();
			while(rs.next()) {
				Person p = new Person();
				p.setFirstName(rs.getString("FIRSTNAME"));
				p.setLastName(rs.getString("LASTNAME"));
				p.setPhoneNumber(rs.getString("PHONENUMBER"));
				p.setAddress(rs.getString("ADDRESS"));
				people.add(p);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(rs != null)
				try{rs.close();}catch(Exception c) {};
			if(ps!=null)
				try{ps.close();}catch(Exception c) {};
			if(cn!=null)
				try{cn.close();}catch(Exception c) {};
		}
	}
	
	public static void main(String[] args) {
		/* TODO: create person objects and put them in the PhoneBook and database
		 * John Smith, (248) 123-4567, 1234 Sand Hill Dr, Royal Oak, MI
		 * Cynthia Smith, (824) 128-8758, 875 Main St, Ann Arbor, MI
		*/ 
		try {
			PhoneBook phoneBook = new PhoneBookImpl();
			
			Person p1 = new Person();
			p1.setFirstName("John");
			p1.setLastName("Smith");
			p1.setPhoneNumber("(248) 123-4567");
			p1.setAddress("1234 Sand Hill Dr, Royal Oak, MI");
			
			phoneBook.addPerson(p1);
			
			Person p2 = new Person();
			p2.setFirstName("Cynthia");
			p2.setLastName("Smith");
			p2.setPhoneNumber("(824) 128-8758");
			p2.setAddress("875 Main St, Ann Arbor, MI");
			
			phoneBook.addPerson(p2);
			
			// TODO: print the phone book out to System.out
			try {
				for(Person p : people) {
					System.out.println(p.toString());
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO: find Cynthia Smith and print out just her entry
			
			Person findResult = phoneBook.findPerson("Cynthia", "Smith");
			if(findResult != null)
				System.out.println(findResult.toString());
			// TODO: insert the new person objects into the database
			
			Person newPerson = new Person();
			newPerson.setFirstName("Wai Kin");
			newPerson.setLastName("Ng");
			newPerson.setPhoneNumber("(248) -1111");
			newPerson.setAddress("44 Sand Hill Dr, Hello");
			phoneBook.addPerson(newPerson);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
