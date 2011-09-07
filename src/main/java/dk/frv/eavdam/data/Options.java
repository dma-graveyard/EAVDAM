package dk.frv.eavdam.data;

public class Options {
    
    public static int LARGE_ICONS = 1;
    public static int SMALL_ICONS = 2;
    
    private int iconsSize = LARGE_ICONS;  // default
    
    public int getIconsSize() {
        return iconsSize;
    }
    
    public void setIconsSize(int iconsSize) {
        if (iconsSize == LARGE_ICONS || iconsSize == SMALL_ICONS) {
            this.iconsSize = iconsSize;
        }
    }

}
