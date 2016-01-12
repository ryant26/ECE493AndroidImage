package ece493.imagemanipulation.Utilities;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ryan on 11/01/16.
 */
public class DialogHelper {

    private ProgressDialog progressDialog;
    private Context context;

    public DialogHelper(Context context){
        this.context=context;
    }

    public void showProgressDialog(){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Applying Filter");
        progressDialog.show();
    }

    public void hideProgressDialog(){
        progressDialog.dismiss();
    }
}
