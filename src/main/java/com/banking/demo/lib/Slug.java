package com.banking.demo.lib;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.Locale;
import java.util.regex.Pattern;

public class Slug {
	private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
	private static final Pattern  SEPARATORS = Pattern.compile("[\\s]");

	public static String makeSlug(String input) {
		
		String nowhitespace = SEPARATORS.matcher(input).replaceAll("-");
		
		String normalized = Normalizer.normalize(nowhitespace, Form.NFD);
		
		String slug = NONLATIN.matcher(normalized).replaceAll("");
		
		return slug.toLowerCase(Locale.ENGLISH).replaceAll("-{2,}","-").replaceAll("^-|-$","");
	}
}
