package com.tvx;

import org.junit.Assert;
import org.junit.Test;

public class EncryptorTest 
{
	@Test
	public void EncryptTestMethod () {
		String sData = "{\"username\" : \"pravin\",\"password\" : \"travelex123\",\"code\" : \"0120\"}";
		String enData = Encryptor.encrypt(sData);
		String deData = Encryptor.decrypt(enData);
		Object obj = Encryptor.convertStringToJsonObject(deData);
		String name = Encryptor.getValueFromJsonNode(obj, "username");
		Assert.assertTrue("pravin".equals(name));
	}
}
