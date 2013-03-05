package de.jardas.commons.security;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomPasswordGenerator implements PasswordGenerator {
	private static final char[] ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
			.toCharArray();
	private static final int ALPHABET_LENGTH = ALPHABET.length;
	private final int length;
	private final Random random = new Random();

	public RandomPasswordGenerator(final int length) {
		this.length = length;
	}

	@Override
	public String createRandomPassword() {
		final char[] password = new char[length];

		for (int i = 0; i < length; i++) {
			password[i] = ALPHABET[random.nextInt(ALPHABET_LENGTH)];
		}

		return new String(password);
	}
}
