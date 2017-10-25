package com.extensions.logmonitor.testSome;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月24日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class UnicodeTest {

	public static void main(String[] args) {
		System.out.println((int) '0');
		System.out.println((int) '9');
		System.out.println((int) 'a');
		System.out.println((int) 'z');
		System.out.println((int) 'A');
		System.out.println((int) 'Z');
		System.out.println((int) '.');
		System.out.println((int) '*');
		System.out.println((int) '_');
		System.out.println((int) '$');
		for (int i = 91; i < 97; i++) {
			System.out.print((char) i + "\t");
		}
	}

}
