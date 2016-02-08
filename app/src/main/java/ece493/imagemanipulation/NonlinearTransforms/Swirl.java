package ece493.imagemanipulation.NonlinearTransforms;

import android.content.Context;

import ece493.imagemanipulation.AppManager;

/**
 * Created by ryan on 06/02/16.
 */
public class Swirl extends RenderScriptContext {

    public Swirl(AppManager manager, Context context) {
        super(manager, context);
    }

    @Override
    protected void invokeScript() {
        tScript.invoke_Swirl(0.005f);
    }
}
