package ece493.imagemanipulation.Utilities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ryan on 11/01/16.
 */
public class DialogHelper {

    Activity context;
    ProgressDialog progressDialog;

    public DialogHelper(Activity context){
        this.context = context;
        progressDialog = getFilterDialog(context);
    }

    public void showProgressDialog(){
        if (!context.isFinishing()){
            progressDialog = DialogHelper.getFilterDialog(context);
            progressDialog.show();
        }
    }

    public void hideFilterDialog(){
        try{
            progressDialog.dismiss();
        } catch (Exception e){
            //We don't care about this
        }
    }

    private static ProgressDialog getFilterDialog(Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Applying Filter");
        return  progressDialog;
    }
}
