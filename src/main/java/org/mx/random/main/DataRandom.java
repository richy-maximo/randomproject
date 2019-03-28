/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mx.random.main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
/**
 *
 * @author Ricardo.Alvarez
 */
public class DataRandom {
    /**
	 * Creates a random instance of the class provided, if a suitable {@link RandomGenerator} is registered.
	 * A suitable generator can be registered in several ways:
	 * <li>automatically when placed in same package as the SupportedType</li>
	 * <li>Manually by registering either class or instance directly to {@link com.namics.commons.random.DataRandom#addRandomGenerator}</li>
	 * <li>Manually by registering package for scanning  to {@link com.namics.commons.random.DataRandom#addRandomGenerators(String)}</li>
	 *
	 * @param type class to create a random object for.
	 * @param info optional additional information for the object to create, e.g. a field name.
	 * @return Random instance of the class.
	 * @throws java.lang.IllegalArgumentException if no suitable {@link RandomGenerator} is registered.
	 */
	public static <T> T random(Type type, Object... info) throws IllegalArgumentException {
		if (type instanceof ParameterizedType) {
			ParameterizedType parameterizedType = (ParameterizedType) type;
			Type[] genericTypes = parameterizedType.getActualTypeArguments();
			Object[] information = new Object[0];
			information = ArrayUtils.addAll(information, genericTypes);
			information = ArrayUtils.addAll(information, info);
			return (T) random((Class) parameterizedType.getRawType(), information);
		} else if (type instanceof Class) {
			return (T) random((Class) type, info);
		}
		throw new IllegalArgumentException(type.toString() + " not supported, only ParameterizedType and Class are supported.");
	}
        

	private static <T> T createInstance(Class<T> clazz) {
		try {
			Constructor<T> constructor = clazz.getDeclaredConstructor();
			constructor.setAccessible(true);
			return constructor.newInstance();
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException(
					"Could not create random instance for " + clazz + ", maybe there is no default constructor available, see exception cause for details.", e);
		} catch (NoSuchMethodException e) {
                    System.out.println("No constructor without arguments");
			return createWithNoDefaultConstructor(clazz);
		}
	}

	@SuppressWarnings("unchecked")
	private static <T> T createWithNoDefaultConstructor(Class<T> clazz) {
		try {
			Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
			for (Constructor<T> constructor : constructors) {
				if (constructor != null) {
					Parameter[] parameters = constructor.getParameters();
					Object[] args = new Object[parameters.length];
					for (int i = 0; i < parameters.length; i++) {
						args[i] = random(parameters[i].getType(), parameters[i].getName());
					}
					constructor.setAccessible(true);
					return constructor.newInstance(args);
				}
			}
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException(
					"Could not create random instance for " + clazz + ", maybe there is no default constructor available, see exception cause for details.", e);
		}
		throw new IllegalArgumentException(
				"Could not create random instance for " + clazz + ", maybe there is no default constructor available, see exception cause for details.");
	}

	/**
	 * Pickes a random value of the array / vararg provided.
	 *
	 * @param items array / vararg to pick an items from
	 * @param <I>   Item class
	 * @return Randomly picked items
	 */
	public static <I> I random(I[] items) {
		return random(Arrays.asList(items));
	}

	/**
	 * Pickes a random value of the collection provided.
	 *
	 * @param items collection to pick an items from
	 * @param <I>   Item class
	 * @return Randomly picked items
	 */
	public static <I> I random(Collection<I> items) {
		if (items == null || items.size() == 0) {
			return null;
		}
		int size = items.size();
		int random = randomInteger(0, size);
		int current = 0;
		for (I i : items) {
			if (current == random) {
				return i;
			}
			current++;
		}
		return items.iterator().next();
	}

	public static boolean randomBoolean() {
		return random(Boolean.class);
	}

	public static Integer randomInteger() {
		return random(Integer.class);
	}

	public static Long randomLong() {
		return random(Long.class);
	}

	public static float randomFloat() {
		return random(Float.class);
	}

	public static BigDecimal randomBigDecimal() {
		return random(BigDecimal.class);
	}

	public static String randomString() {
		return random(String.class);
	}

	public static Date date() {
		return random(Date.class);
	}

	public static LocalDateTime dateTime() {
		return random(LocalDateTime.class);
	}

	public static String alphabetic(int count) {
		return RandomStringUtils.randomAlphabetic(count);
	}

	public static String alphanumeric(int count) {
		return RandomStringUtils.randomAlphanumeric(count);
	}


	public static int randomInteger(int min, int max) {
		return min + RandomUtils.nextInt(max - min + 1);
	}

	public static long randomLong(int min, int max) {
		return min + RandomUtils.nextInt(max - min + 1);
	}

	public static float randomFloat0to1() {
		return RandomUtils.nextFloat();
	}

	public static float randomFloat(int min, int max) {
		return RandomUtils.nextFloat() + RandomUtils.nextInt(max - min);
	}

	public static float randomFloat(float min, float max) {
		if (Float.isInfinite(max - min)) {
			throw new IllegalArgumentException("range of float is infinty");
		}
		return min + RandomUtils.nextFloat() * (max - min);
	}

	public static double randomDouble(int min, int max) {
		return RandomUtils.nextDouble() + RandomUtils.nextInt(max - min);
	}

	public static double randomDouble(double min, double max) {
		if (Double.isInfinite(max - min)) {
			throw new IllegalArgumentException("range of double is infinty");
		}
		return min + RandomUtils.nextDouble() * (max - min);
	}


	public static String lang() {
		return languageCode();
	}

	public static String languageCode() {
		return locale().getLanguage();
	}

	public static Locale locale() {
		return random(Locale.class);
	}

	public static String countryCode() {
		return random(Locale.getISOCountries());
	}

	/**
	 * Generate a Hex encoded block formatted type 4 (pseudo randomly generated) UUID.
	 *
	 * @see {@link UUID#randomUUID}
	 * @return uuid string representation.
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * Generate a short UUID - 22 digit base64 representation of a UUID.
	 * Details: http://www.shortguid.com
	 *
	 * @return 22 digit base64 representation of a UUID
	 */
	public static String shortGuid() {
		UUID uuid = UUID.randomUUID();
		byte[] array = toByteArray(uuid);
		return Base64.getEncoder().withoutPadding().encodeToString(array);
	}

	/**
	 * Convert UUID to 16 byte array.
	 *
	 * @param uuid uuid to get byte representation
	 * @return 16 byte array, null for null
	 */
	private static byte[] toByteArray(UUID uuid) {
		if (uuid == null) {
			return null;
		}
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());
		return bb.array();
	}

	/**
	 * Creates a HashMap, key and value class must be provided, since Generics are unknown at runtime.
	 *
	 * @param keyClass   class of key
	 * @param valueClass class of value
	 * @return Map populated with random values.
	 */

	public static Map map(Class<? extends Comparable> keyClass, Class<?> valueClass) {
		Map map = new HashMap();
		for (int i = 0; i < 10; i++) {
			Object key = random(keyClass);
			Object value = random(valueClass);
			if (key != null && value != null) {
				map.put(key, value);
			}
		}
		return map;
	}
        
        
}
