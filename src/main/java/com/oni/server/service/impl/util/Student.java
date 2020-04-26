package com.oni.server.service.impl.util;

import java.lang.reflect.Field;

public class Student {

	private int id;
	private String firstName;
	private String lastName;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Student() {
		super();
	}

	public Student(int id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + "]";

	}
	
	
	public String printStringWithDelimeter(String DELIMITER) {
		return this.getId() + DELIMITER + this.getFirstName() + DELIMITER + this.getLastName();
	}

	public String printKeyValuePairWithDelimeter(String DELIMITER) {
		  StringBuilder result = new StringBuilder();
		  Field[] fields = this.getClass().getDeclaredFields();
		  for ( Field field : fields  ) {
		    try {
		      result.append( field.getName() );
		      result.append(":");
		      result.append( field.get(this) );
		    } catch ( IllegalAccessException ex ) {
		      System.out.println(ex);
		    }
		    result.append(DELIMITER);
		  }
		  //Remove last character 
		  result.replace(result.length()-1, result.length(), "");

		  return result.toString();
		}

	public String printOnlyValueWithDelimeter(String DELIMITER) {
		  StringBuilder result = new StringBuilder();
		  Field[] fields = this.getClass().getDeclaredFields();
		  for ( Field field : fields  ) {
		    try {
		     // result.append( field.getName() );
		    //  result.append(":");
		      result.append( field.get(this) );
		    } catch ( IllegalAccessException ex ) {
		      System.out.println(ex);
		    }
		    result.append(DELIMITER);
		  }
		  //Remove last character 
		  result.replace(result.length()-1, result.length(), "");

		  return result.toString();
		}

	
	public static void main(String[] args) {

		Student s = new Student(1, "Aarti", "Malviya");
		System.out.println(s.toString());
		System.out.println(s.printStringWithDelimeter("$"));
		System.out.println(s.printKeyValuePairWithDelimeter("$"));
		System.out.println(s.printOnlyValueWithDelimeter("$"));
		

	}
}