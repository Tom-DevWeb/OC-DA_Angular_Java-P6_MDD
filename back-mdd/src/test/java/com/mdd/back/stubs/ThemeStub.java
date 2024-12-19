package com.mdd.back.stubs;

import com.mdd.back.entities.Theme;

public class ThemeStub {

    public static Theme getDefaultTheme() {
        Theme theme = new Theme();
        theme.setId(1L);
        theme.setTitle("Default Theme");
        theme.setDescription("This is the default theme description.");
        return theme;
    }
}
