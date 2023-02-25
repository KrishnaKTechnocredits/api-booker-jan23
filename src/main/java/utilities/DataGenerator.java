package utilities;

import net.datafaker.Faker;

public class DataGenerator {

	private static Faker faker = new Faker();
	
	public static String getEmailId() {
		return faker.name().firstName() + "." + faker.name().lastName() + "@gmail.com";
	}
	
	public static String getFullName() {
		return faker.name().fullName();
	}
	
	public static String getPhoneNumber() {
		return faker.number().digits(10);
	}

}
