package com.icarus.ligabasquetbol.fileio;

import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.UI;

import java.io.*;
import java.util.stream.Collectors;

public class FileRW {
    public static String leerArchivoDelTema(String filename) {
        try {
            UI ui = UI.getCurrent();
            InputStream is = ui.getSession().getService()
                    .getThemeResourceAsStream(ui, ui.getTheme(), filename);
            return new BufferedReader(new InputStreamReader(is))
                    .lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
