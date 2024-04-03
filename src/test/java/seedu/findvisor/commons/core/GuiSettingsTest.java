package seedu.findvisor.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class GuiSettingsTest {
    @Test
    public void equalsMethod() {
        GuiSettings guiSettings = new GuiSettings(100, 100, 20, 20, true, 0.5);

        // same object -> returns true
        assertTrue(guiSettings.equals(guiSettings));

        // same values -> returns true
        assertTrue(guiSettings.equals(new GuiSettings(100, 100, 20, 20, true, 0.5)));

        // different types -> returns false
        assertFalse(guiSettings.equals(10));

        // null -> returns false
        assertFalse(guiSettings.equals(null));

        // different values -> returns false
        assertFalse(guiSettings.equals(new GuiSettings(100, 100, 20, 20, false, 0.5)));
        assertFalse(guiSettings.equals(new GuiSettings(5, 6, 7, 8, true, 0.1)));
    }

    @Test
    public void toStringMethod() {
        GuiSettings guiSettings = new GuiSettings();
        String expected = GuiSettings.class.getCanonicalName() + "{windowWidth=" + guiSettings.getWindowWidth()
                + ", windowHeight=" + guiSettings.getWindowHeight() + ", windowCoordinates="
                + guiSettings.getWindowCoordinates() + ", isMaximized=" + guiSettings.getIsMaximized()
                + ", dividerPosition=" + guiSettings.getDividerPosition() + "}";
        assertEquals(expected, guiSettings.toString());
    }
}
