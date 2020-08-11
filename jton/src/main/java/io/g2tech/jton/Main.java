package io.g2tech.jton;

import java.util.Date;

public class Main {

	public static void main(String[] args) {
		JtonPrimitive p = new JtonPrimitive(new Date(), true);
		
		JtonArray arr = new JtonArray();
		arr.add(p);
		arr.add((Boolean)null);
		
		System.out.println(arr.get(1).getClass());
		
		System.out.println(arr);
	}
}
