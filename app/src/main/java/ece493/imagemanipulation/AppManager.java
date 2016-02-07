package ece493.imagemanipulation;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import ece493.imagemanipulation.Utilities.FilterTask;
import ece493.imagemanipulation.Utilities.Observer;

/**
 * Created by ryan on 07/01/16.
 */
public class AppManager extends Application{
    private Bitmap selectedBitMap;
    private AsyncTask<Bitmap, Void, Bitmap> filterTask;
    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer){
        if (!observers.contains(observer)){
            observers.add(observer);
        }
    }

    public Bitmap getSelectedBitMap() {
        return selectedBitMap;
    }

    public void setFilterTask(AsyncTask<Bitmap, Void, Bitmap> filterTask) {
        cancelFilterTask();
        this.filterTask = filterTask;
        if (filterTask != null) this.filterTask.execute(selectedBitMap);
        updateObservers();
    }

    public void setSelectedBitMap(Bitmap selectedBitMap) {
        this.selectedBitMap = selectedBitMap;
        updateObservers();
    }

    public boolean filterTaskRunning(){
        try{
            return filterTask.getStatus() != AsyncTask.Status.FINISHED;
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
