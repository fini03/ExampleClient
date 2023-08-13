package client.game.color;

public enum EColor {
	/*
	 * TAKEN FROM <2> Source:
	 * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	 * This source was taken as inspiration.
	 */

	/*
	 * TAKEN FROM START <2>
	 */

	// Color reset
	RESET("\u001b[0m"),

	// Colors
	BLACK("\u001b[30m"),
	RED("\u001b[31m"),
	GREEN("\u001b[32m"),
	YELLOW("\u001b[33m"),
	BLUE("\u001b[34m"),
	MAGENTA("\u001b[35m"),
	CYAN("\u001b[36m"),
	WHITE("\u001b[37m"),
	
	// Backgrounds
	BLACK_BACKGROUND("\u001b[40m"),
	RED_BACKGROUND("\u001b[41m"),
	GREEN_BACKGROUND("\u001b[42m"),
	YELLOW_BACKGROUND("\u001b[43m"),
	BLUE_BACKGROUND("\u001b[44m"),
	MAGENTA_BACKGROUND("\u001b[45m"),
	CYAN_BACKGROUND("\u001b[46m"),
	WHITE_BACKGROUND("\u001b[47m");

	private final String code;

	EColor(final String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	/*
	 * TAKEN FROM END <2>
	 */
}
