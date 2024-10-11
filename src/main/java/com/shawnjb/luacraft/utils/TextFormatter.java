package com.shawnjb.luacraft.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextFormatter {

	/**
	 * Converts ampersand (&) codes to Minecraft section symbol (§) codes.
	 * 
	 * @param inputString the input string with & codes.
	 * @return the formatted string with § codes.
	 */
	public static String toSections(String inputString) {
		return inputString == null ? null : inputString.replace("&", "§");
	}

	/**
	 * Converts hex color codes in the format &#RRGGBB to a Kyori Adventure
	 * Component with those colors applied.
	 * 
	 * @param inputString the input string with hex color codes.
	 * @return a Kyori Adventure Component with the colors applied.
	 */
	public static Component toHexColors(String inputString) {
		String regex = "&#([A-Fa-f0-9]{6})([^&#]*)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(inputString);

		TextComponent.Builder finalComponent = Component.text();

		int lastIndex = 0;
		while (matcher.find()) {
			String hexCode = matcher.group(1);
			String textPart = matcher.group(2);

			if (matcher.start() > lastIndex) {
				finalComponent.append(Component.text(inputString.substring(lastIndex, matcher.start())));
			}

			TextColor color = TextColor.fromHexString("#" + hexCode);
			finalComponent.append(Component.text(textPart).color(color));

			lastIndex = matcher.end();
		}

		if (lastIndex < inputString.length()) {
			finalComponent.append(Component.text(inputString.substring(lastIndex)));
		}

		return finalComponent.build();
	}

	/**
	 * Translates Minecraft-style color codes using the ampersand (&) symbol into
	 * a Kyori Adventure Component.
	 * 
	 * @param message the input message with color codes.
	 * @return a Kyori Adventure Component with the appropriate colors.
	 */
	public static Component translateColorCodes(String message) {
		return Component.text(message.replaceAll("&([0-9a-fk-or])", "§$1"));
	}

	/**
	 * Combines both hex color translation and ampersand (&) color code
	 * translation into a single Kyori Adventure Component.
	 * 
	 * @param inputString the input string containing both hex and ampersand color
	 *                    codes.
	 * @return a Kyori Adventure Component with the applied colors.
	 */
	public static Component toComponent(String inputString) {
		Component hexComponent = toHexColors(inputString);
		String plainText = hexComponent.toString();
		return translateColorCodes(plainText);
	}
}
