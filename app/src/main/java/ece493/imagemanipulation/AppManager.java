package ece493.imagemanipulation;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ece493.imagemanipulation.Utilities.Observer;

/**
 * Created by ryan on 07/01/16.
 */
public class AppManager extends Application{
    private Bitmap selectedBitMap;
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        if (!observers.contains(observer)){
            observers.add(observer);
        }
    }

    public Bitmap getSelectedBitMap() {
        return selectedBitMap;
    }

    public void setSelectedBitMap(Bitmap selectedBitMap) {
        this.selectedBitMap = selectedBitMap;
        updateObservers();
    }

    private void updateObservers(){
        for(Observer ob : observers){
            ob.update();
        }
    }

}
