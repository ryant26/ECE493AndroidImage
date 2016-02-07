package ece493.imagemanipulation.NonlinearTransforms;

import android.app.Activity;
import android.content.res.Resources;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;

import ece493.imagemanipulation.AppManager;
import ece493.imagemanipulation.GestureListeners.GestureInvokedListener;

/**
 * Created by ryan on 04/02/16.
 */
public abstract class RenderScriptContext implements GestureInvokedListener{
    private AppManager manager;
    private Activity context;

    protected Allocation tInAllocation;
    protected Allocation tOutAllocation;
    protected RenderScript tRS;

    public RenderScriptContext(AppManager manager, Activity context){
        this.manager = manager;
        this.context = context;
    }


    public void invokeTransform(){
        tRS = RenderScript.create(context);
        tInAllocation = Allocation.createCubemapFromBitmap(tRS, manager.getSelectedBitMap(),
                Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT);

        tOutAllocation = Allocation.createTyped(tRS, tInAllocation.getType());
        invokeScript();
        tOutAllocation.copyTo(manager.getSelectedBitMap());
        manager.updateObservers();
    }

    protected abstract ScriptC invokeScript();

    protected Resources getResources(){
        return context.getResources();
    }

    @Override
    public void onGestureInvoked() {
        invokeScript();
    }
}
