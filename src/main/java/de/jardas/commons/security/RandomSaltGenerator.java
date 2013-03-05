package de.jardas.commons.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.springframework.security.crypto.codec.Hex;

public class RandomSaltGenerator implements SaltGenerator {
	private final Random random;
	private final MessageDigest messageDigest;

	public RandomSaltGenerator(final String algorithm) {
		this(System.currentTimeMillis(), algorithm);
	}

	public RandomSaltGenerator(final long seed, final String algorithm) {
		random = new Random(seed);

		try {
			messageDigest = MessageDigest.getInstance(algorithm);
		} catch (final NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Invalid message digest algorithm '" + algorithm + "': " + e, e);
		}
	}

	@Override
	public String generateSalt() {
		final byte[] bytes = new byte[4];
		random.nextBytes(bytes);

		final byte[] digest = messageDigest.digest(bytes);
		return new String(Hex.encode(digest));
	}
}
