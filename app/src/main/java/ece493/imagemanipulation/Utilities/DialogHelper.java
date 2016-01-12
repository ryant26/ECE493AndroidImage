package ece493.imagemanipulation.Utilities;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ryan on 11/01/16.
 */
public class DialogHelper {

    public static ProgressDialog getFilterDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Applying Filter");
        return  progressDialog;
    }
}
