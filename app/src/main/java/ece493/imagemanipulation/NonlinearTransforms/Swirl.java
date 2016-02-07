package ece493.imagemanipulation.NonlinearTransforms;

import android.app.Activity;
import android.content.Context;
import android.renderscript.ScriptC;

import ece493.imagemanipulation.AppManager;
import ece493.imagemanipulation.NonlinearTransoforms.ScriptC_swirl;

/**
 * Created by ryan on 06/02/16.
 */
public class Swirl extends RenderScriptContext {

    public Swirl(AppManager manager, Context context) {
        super(manager, context);
    }

    @Override
    protected void invokeScript() {
        ScriptC_swirl swirlScript = new ScriptC_swirl(tRS);
        swirlScript.set_height(getBitmapHeight());
        swirlScript.set_width(getBitmapWidth());
        swirlScript.set_input(inAllocation);
        swirlScript.set_output(outAllocation);
        swirlScript.invoke_Swirl(0.01f);
    }
}
