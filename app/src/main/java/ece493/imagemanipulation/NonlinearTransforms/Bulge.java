package ece493.imagemanipulation.NonlinearTransforms;

import android.content.Context;

import ece493.imagemanipulation.AppManager;

/**
 * Created by ryan on 07/02/16.
 */
public class Bulge extends RenderScriptContext {

    public Bulge(AppManager manager, Context context) {
        super(manager, context);
    }

    @Override
    protected void invokeScript() {
        tScript.invoke_bulge();
    }
}
