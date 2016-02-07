package ece493.imagemanipulation.NonlinearTransforms;

import android.app.Activity;
import android.renderscript.ScriptC;

import ece493.imagemanipulation.AppManager;
import ece493.imagemanipulation.R;

/**
 * Created by ryan on 06/02/16.
 */
public class Swirl extends RenderScriptContext {

    public Swirl(AppManager manager, Activity context) {
        super(manager, context);
    }

    @Override
    protected ScriptC invokeScript() {

        return null;
    }
}
