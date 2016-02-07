package ece493.imagemanipulation.NonlinearTransforms;

import android.app.Activity;
import android.renderscript.ScriptC;

import ece493.imagemanipulation.AppManager;
import ece493.imagemanipulation.NonlinearTransoforms.ScriptC_swirl;

/**
 * Created by ryan on 06/02/16.
 */
public class Swirl extends RenderScriptContext {

    public Swirl(AppManager manager, Activity context) {
        super(manager, context);
    }

    @Override
    protected void invokeScript() {
        ScriptC_swirl swirlScript = new ScriptC_swirl(tRS);
        swirlScript.set_height(getBitmapHeight());
        swirlScript.set_width(getBitmapWidth());
        swirlScript.bind_input(inAllocation);
        swirlScript.bind_output(outAllocation);
        swirlScript.invoke_Swirl(0.1f);
    }
}
