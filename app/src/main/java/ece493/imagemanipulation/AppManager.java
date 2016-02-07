package ece493.imagemanipulation;

import android.app.Application;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import ece493.imagemanipulation.Utilities.FilterTask;
import ece493.imagemanipulation.Utilities.Observer;

/**
 * Created by ryan on 07/01/16.
 */
public class AppManager extends Application{
    private Bitmap selectedBitMap;
    private FilterTask filterTask;
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        if (!observers.contains(observer)){
            observers.add(observer);
        }
    }

    public Bitmap getSelectedBitMap() {
        return selectedBitMap;
    }

    public void setFilterTask(FilterTask filterTask) {
        cancelFilterTask();
        this.filterTask = filterTask;
        this.filterTask.execute(selectedBitMap);
        updateObservers();
    }

    public void setSelectedBitMap(Bitmap selectedBitMap) {
        this.selectedBitMap = selectedBitMap;
        updateObservers();
    }

    public boolean filterTaskRunning(){
        try{
            return !filterTask.isFinishedFiltering();
        } catch (NullPointerException e){
            return false;
        }
    }

    public void cancelFilterTask(){
        if (filterTaskRunning()){
            filterTask.cancel(true);
            filterTask = null;
        }
    }

    public void updateObservers(){
        for(Observer ob : observers){
            ob.update();
        }
    }



}
