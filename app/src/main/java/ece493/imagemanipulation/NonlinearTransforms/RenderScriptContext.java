package ece493.imagemanipulation.NonlinearTransforms;

import android.app.Activity;
import android.content.res.Resources;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;
import android.util.Log;

import ece493.imagemanipulation.AppManager;
import ece493.imagemanipulation.GestureListeners.GestureInvokedListener;

/**
 * Created by ryan on 04/02/16.
 */
public abstract class RenderScriptContext implements GestureInvokedListener{
    private AppManager manager;
    private Activity context;

    protected Allocation inAllocation;
    protected Allocation outAllocation;
    protected RenderScript tRS;

    public RenderScriptContext(AppManager manager, Activity context){
        this.manager = manager;
        this.context = context;
    }


    public void invokeTransform(){
        tRS = RenderScript.create(context);

        inAllocation = Allocation.createFromBitmap(tRS, manager.getSelectedBitMap(),
                Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);

        outAllocation = Allocation.createTyped(tRS, inAllocation.getType());
        invokeScript();
        outAllocation.copyTo(manager.getSelectedBitMap());
        Log.d("DEBUG", "Script finished running");
        manager.updateObservers();
    }

    protected abstract void invokeScript();

    protected Resources getResources(){
        return context.getResources();
    }

    protected int getBitmapWidth(){
        return manager.getSelectedBitMap().getWidth();
    }

    protected int getBitmapHeight(){
        return manager.getSelectedBitMap().getHeight();
    }

    @Override
    public void onGestureInvoked() {
        invokeScript();
    }
}
