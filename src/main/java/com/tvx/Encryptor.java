package com.tvx;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * The Class Encryptor.
 */
public class Encryptor {

	/** The Constant key. */
	private static final String key = "aEsEn(ryPt!0nKeY";

	/** The Constant initVector. */
	private static final String initVector = "En(ryPt!0nIntVec";

	/**
	 * Encrypt.
	 *
	 * @param value the value
	 * @return the string - encrypted value
	 */
	public static String encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			return Base64.encodeBase64String(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * Decrypt.
	 *
	 * @param encrypted the encrypted
	 * @return the string - decrypted value
	 */
	public static String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
			return "";
		}
	}

	/**
	 * Read file.
	 *
	 * @param filePath the file path
	 * @return the string
	 */
	public static String readFile(String filePath) {
		File file = new File(filePath); 

		BufferedReader br = null;
		String st = "";
		try {
			br = new BufferedReader(new FileReader(file));
			while ((st = br.readLine()) != null) {
				return st;
			}
		} catch (IOException ex) {
			System.out.println("Error in readFile() : " + ex.getMessage());
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				System.out.println("Error in readFile() : " + ex.getMessage());
			}
		}
		return st;
	}

	/**
	 * Write file.
	 *
	 * @param filePath the file path
	 * @param data the data
	 */
	public static boolean writeFile(String filePath, String data) {
		boolean flag = false;
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File(filePath));
			os.write(data.getBytes(), 0, data.length());
			flag = true;
		} catch (IOException ex) {
			System.out.println("Error in writeFile() : " + ex.getMessage());
			flag = false;
		} finally {
			try {
				os.close();
			} catch (IOException ex) {
				System.out.println("Error in writeFile() : " + ex.getMessage());
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * Convert string to json object.
	 *
	 * @param jsonString the json string
	 * @return the JSON object
	 */
	public static JSONObject convertStringToJsonObject(String jsonString) {
		try {
			return (JSONObject) new JSONParser().parse(jsonString);
		} catch (ParseException ex) {
			System.out.println("Error in convertStringToJsonObject() : " + ex.getMessage());
			return null;
		}
	}

	/**
	 * Gets the value from json node. For simple JSON object.
	 *
	 * @param Object the object
	 * @param nodeName the node name
	 * @return the value from json node
	 */
	public static String getValueFromJsonNode(Object jsonObject, String nodeName) {
		try {
			JSONObject obj = (JSONObject) jsonObject;
			return obj.get(nodeName).toString();
		} catch (Exception ex) {
			System.out.println("Error in getValueFromJsonNode() : " + ex.getMessage());
			return null;
		}
	}

	/**
	 * Gets the value from json node. For simple JSON object.
	 *
	 * @param jsonObject the json object
	 * @param nodeName the node name
	 * @return the value from json node
	 */
	public static String getValueFromJsonNode(JSONObject jsonObject, String nodeName) {
		try {
			return jsonObject.get(nodeName).toString();
		} catch (Exception ex) {
			System.out.println("Error in getValueFromJsonNode() : " + ex.getMessage());
			return null;
		}
	}
}
