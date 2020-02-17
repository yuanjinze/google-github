package com.example.tutorial;

import java.io.FileOutputStream;

import com.example.tutorial.AddressBookProtos.AddressBook;
import com.example.tutorial.AddressBookProtos.Person;

/**
 * @author jinze-yuan
 *
 */
class Writer {
  public static void main(String[] args) throws Exception {
    AddressBook.Builder addressBook = AddressBook.newBuilder();
    Person.Builder person = Person.newBuilder();
    person.setId(Integer.valueOf(34));
    person.setName("xiaoxiao");
    person.setEmail("yjzjk@125.com");
    
    Person.PhoneNumber.Builder phoneNumber = Person.PhoneNumber.newBuilder();
    phoneNumber.setNumber("10086");
    phoneNumber.setType(Person.PhoneType.MOBILE);
    person.addPhone(phoneNumber);
    
    // Add an address.
    addressBook.addPerson(person.build());
    
    // Write the new address book back to disk.
    FileOutputStream output = new FileOutputStream("output");    
    try {
      addressBook.build().writeTo(output);
    } finally {
      output.close();
    }
  }
}
