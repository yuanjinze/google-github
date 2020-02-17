package com.example.tutorial;

import java.io.FileInputStream;

import com.example.tutorial.AddressBookProtos.AddressBook;
import com.example.tutorial.AddressBookProtos.Person;

/**
 * @author jinze-yuan
 *
 */
class Reader {
	// Iterates though all people in the AddressBook and prints info about them.
	static void Print(AddressBook addressBook) {
		for (Person person : addressBook.getPersonList()) {
			System.out.println("  Person ID: " + person.getId());
			System.out.println("  Name: " + person.getName());
			if (person.hasEmail()) {
				System.out.println("  E-mail address: " + person.getEmail());
			}

			for (Person.PhoneNumber phoneNumber : person.getPhoneList()) {
				switch (phoneNumber.getType()) {
				case MOBILE:
					System.out.print("  Mobile phone #: ");
					break;
				case HOME:
					System.out.print("  Home phone #: ");
					break;
				case WORK:
					System.out.print("  Work phone #: ");
					break;
				}
				System.out.println(phoneNumber.getNumber());
			}
		}
	}

	// Main function: Reads the entire address book from a file and prints all
	// the information inside
	public static void main(String[] args) throws Exception {
		// Read the existing address book.
		AddressBook addressBook = AddressBook.parseFrom(new FileInputStream("output"));
		Print(addressBook);
	}
}
