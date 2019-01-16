package me.jfenn.timedatepickers.interfaces;

import androidx.annotation.ColorInt;

public interface Themable {

	/**
	 * Sets the background color of the currently
	 * selected item in the picker.
	 * 
	 * @param color			The background color to be
	 *						used for the currently selected
	 *						item in the picker.
	 */
    void setSelectionColor(@ColorInt int color);

	/**
	 * Obtains the background color of the currently
	 * selected item in the picker.
	 *
	 * @return The background color used for the currently
	 *			selected item.
	 */
    @ColorInt
    int getSelectionColor();

	/**
	 * Set the text color of the currently selected
	 * item in the picker.
	 *
	 * @param color			The text color to be used for the
	 * 						currently selected item in the
	 * 						picker.
	 */
    void setSelectionTextColor(@ColorInt int color);

	/**
	 * Obtains the text color of the currently selected
	 * item in the picker.
	 * 
	 * @return The text color used for the currently selected
	 * 			item.
	 */
    @ColorInt
    int getSelectionTextColor();

	/**
	 * Set the "primary" text color to be used for unselected
	 * items.
	 * 
	 * @param color			The "primary" text color to be
	 * 						used for unselected items.
	 */
    void setPrimaryTextColor(@ColorInt int color);

	/**
	 * Obtains the "primary" text color used for unselected
	 * items in the picker.
	 * 
	 * @return The "primary" text color used for unselected
	 * 			items.
	 */
    @ColorInt
    int getPrimaryTextColor();

	/**
	 * Sets the "secondary" text color to be used for
	 * unselected items.
	 * 
	 * @param color			The "secondary" text color to be
	 * 						used for unselected items..
	 */
    void setSecondaryTextColor(@ColorInt int color);

	/**
	 * Obtains the "secondary" text color used for unselected
	 * items in the picker.
	 *
	 * @return The "secondary" text color used for unselected
	 * 			items.
	 */
    @ColorInt
    int getSecondaryTextColor();

	/**
	 * Sets the background color of the picker.
	 *
	 * @param color			The background color to use for
	 * 						the picker.
	 */
    void setBackgroundColor(@ColorInt int color);

	/**
	 * Obtains the background color used for the picker.
	 * 
	 * @return The picker's background color.
	 */
    @ColorInt
    int getBackgroundColor();

	/**
	 * Sets the "primary" background color used for unselected
	 * items in the picker.
	 * 
	 * @param color			The "primary" background color used
	 * 						for unselected items in the picker.
	 */
    void setPrimaryBackgroundColor(@ColorInt int color);

	/**
	 * Obtains the "primary" background color of unselected
	 * picker items.
	 * 
	 * @return The "primary" background color of unselected
	 * 			items.
	 */
    @ColorInt
    int getPrimaryBackgroundColor();

	/**
	 * Sets the "secondary" background color used for unselected
	 * items in the picker.
	 * 
	 * @param color			The "secondary" background color used
	 * 						for unselected items in the picker.
	 */
    void setSecondaryBackgroundColor(@ColorInt int color);

	/**
	 * Obtains the "secondary" background color of unselected
	 * picker items.
	 * 
	 * @return The "secondary" background color of unselected
	 * 			items.
	 */
    @ColorInt
    int getSecondaryBackgroundColor();
}
