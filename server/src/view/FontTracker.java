package view;

import java.awt.Color;
import java.awt.Font;

public class FontTracker {

	Font textFont;
	Color foregroundColor;
	Color backgroundColor;

	public FontTracker(String fontName, String size,
			String foregroundColorValue, String backgroundColorValue) {
		textFont = new Font(fontName, Font.PLAIN, Integer.valueOf(size)
				.intValue());
		foregroundColor = new Color(Integer.valueOf(foregroundColorValue)
				.intValue());
		backgroundColor = new Color(Integer.valueOf(backgroundColorValue)
				.intValue());
	}

	public FontTracker(Font tf, Color fgc, Color bgc) {
		textFont = tf;
		foregroundColor = fgc;
		backgroundColor = bgc;
	}

	public void setFontTracker(FontTracker newFontTracker) {
		textFont = newFontTracker.textFont;
		foregroundColor = newFontTracker.foregroundColor;
		backgroundColor = newFontTracker.backgroundColor;
	}

	public Font getTextFont() {
		return textFont;
	}

	public void setTextFont(Font textFont) {
		this.textFont = textFont;
	}

	public Color getForegroundColor() {
		return foregroundColor;
	}

	public void setForegroundColor(Color foregroundColor) {
		this.foregroundColor = foregroundColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

}
