package ece493.imagemanipulation.NonlinearTransforms;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;
import android.util.Log;

import ece493.imagemanipulation.AppManager;
import ece493.imagemanipulation.GestureListeners.GestureInvokedListener;

/**
 * Created by ryan on 04/02/16.
 */
public abstract class RenderScriptContext extends AsyncTask<Bitmap, Void, Bitmap>{
    private AppManager manager;
    private Context context;

    protected Allocation inAllocation;
    protected Allocation outAllocation;
    protected RenderScript tRS;

    public RenderScriptContext(AppManager manager, Context context){
        this.manager = manager;
        this.context = context;
    }

    @Override
    protected Bitmap doInBackground(Bitmap... params){
        tRS = RenderScript.create(context);

        inAllocation = Allocation.createFromBitmap(tRS, manager.getSelectedBitMap(),
                Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);

        outAllocation = Allocation.createTyped(tRS, inAllocation.getType());

        invokeScript();
        outAllocation.copyTo(manager.getSelectedBitMap());
        tRS.destroy();
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap param){
        Log.d("DEBUG", "Script finished running");
        manager.setFilterTask(null);
    }

    protected abstract void invokeScript();

    protected int getBitmapWidth(){
        return manager.getSelectedBitMap().getWidth();
    }

    protected int getBitmapHeight(){
        return manager.getSelectedBitMap().getHeight();
    }

}
