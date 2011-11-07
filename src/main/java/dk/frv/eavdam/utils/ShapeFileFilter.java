package dk.frv.eavdam.utils;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class ShapeFileFilter extends FileFilter {
 
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        if (f != null && f.getName().endsWith(".shp")) {
			return true;
		}
        return false;
    }
 
    public String getDescription() {
        return "Shape files";
    }

}