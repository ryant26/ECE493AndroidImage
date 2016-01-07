package ece493.imagemanipulation;

import android.app.Application;
import android.graphics.Bitmap;

/**
 * Created by ryan on 07/01/16.
 */
public class AppManager extends Application{
    private Bitmap selectedBitMap;

    public Bitmap getSelectedBitMap() {
        return selectedBitMap;
    }

    public void setSelectedBitMap(Bitmap selectedBitMap) {
        this.selectedBitMap = selectedBitMap;
    }

}
