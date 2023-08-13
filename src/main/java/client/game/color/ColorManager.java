package client.game.color;

public class ColorManager {
	/*
	 * TAKEN FROM <3> Source:
	 * https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println
	 * This source was taken as inspiration.
	 */

	/*
	 * TAKEN FROM START <3>
	 */
	public static String blackFont(final String input) {
		return EColor.BLACK.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String redFont(final String input) {
		return EColor.RED.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String blueFont(final String input) {
		return EColor.BLUE.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String greenFont(final String input) {
		return EColor.GREEN.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String magentaFont(final String input) {
		return EColor.MAGENTA.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String yellowFont(final String input) {
		return EColor.YELLOW.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String cyanFont(final String input) {
		return EColor.CYAN.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String whiteFont(final String input) {
		return EColor.WHITE.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String blackSquare(final String input) {
		return EColor.BLACK.getCode() + EColor.BLACK_BACKGROUND.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String redSquare(final String input) {
		return EColor.RED.getCode() + EColor.RED_BACKGROUND.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String blueSquare(final String input) {
		return EColor.BLUE.getCode() + EColor.BLUE_BACKGROUND.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String greenSquare(final String input) {
		return EColor.GREEN.getCode() + EColor.GREEN_BACKGROUND.getCode() + input + EColor.RESET.getCode();
	}

	public static String magentaSquare(final String input) {
		return EColor.MAGENTA.getCode() + EColor.MAGENTA_BACKGROUND.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String yellowSquare(final String input) {
		return EColor.YELLOW.getCode() + EColor.YELLOW_BACKGROUND.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String cyanSquare(final String input) {
		return EColor.CYAN.getCode() + EColor.CYAN_BACKGROUND.getCode() + input + EColor.RESET.getCode();
	}
	
	public static String whiteSquare(final String input) {
		return EColor.WHITE.getCode() + EColor.WHITE_BACKGROUND.getCode() + input + EColor.RESET.getCode();
	}
	
	/*
	 * TAKEN FROM END <3>
	 */
}
