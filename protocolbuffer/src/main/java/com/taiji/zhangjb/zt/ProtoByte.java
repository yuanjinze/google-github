package com.taiji.zhangjb.zt;

import java.util.List;
import org.junit.Test;

import com.google.protobuf.InvalidProtocolBufferException;
import com.taiji.zhangjb.zt.AddressBookProtos;
import com.taiji.zhangjb.zt.AddressBookProtos.Person;
import com.taiji.zhangjb.zt.AddressBookProtos.Person.PhoneNumber;

public class ProtoByte {
	public static void main(String[] args) throws Exception {
		ProtoByte pb = new ProtoByte();
		pb.testProtocolBuffer();
	}

	public void testProtocolBuffer() {
		// step1:(client)将对象通过protocolBuffer变成byte[]数组
		AddressBookProtos.Person.Builder builder = AddressBookProtos.Person.newBuilder();
		builder.setEmail("904545149@qq.com");
		builder.setId(1);
		builder.setName("zhangjb");
		builder.addPhone(AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("18612596995")
				.setType(AddressBookProtos.Person.PhoneType.MOBILE));
		builder.addPhone(AddressBookProtos.Person.PhoneNumber.newBuilder().setNumber("28889626")
				.setType(AddressBookProtos.Person.PhoneType.HOME));

		Person person = builder.build();
		System.out.println("----------writer:" + person.toString());
		byte[] buf = person.toByteArray();

		// step2:(server)模拟解析socket传过来的byte[]数组
		try {
			Person ps = AddressBookProtos.Person.parseFrom(buf);
			System.out.println("----------reader:" + ps.toString());
			System.out.println(ps.getName() + " , " + ps.getEmail());
			List<PhoneNumber> lstPhones = ps.getPhoneList();
			for (PhoneNumber phoneNumber : lstPhones) {
				System.out.println(phoneNumber);
			}

		} catch (InvalidProtocolBufferException e) {
			e.printStackTrace();
		}
		System.out.println(buf);
	}
}
