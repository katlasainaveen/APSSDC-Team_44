package com.example.recipeapp2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.ActivityCompat;

public class Alert_message extends AppCompatDialogFragment {

    int tag;
    String title, message;

    public Alert_message(int tag, String title, String Message) {
        this.title = title;
        this.message = Message;
        this.tag = tag;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (tag == 1) {
            builder.setTitle(title).setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
        }
        else if (tag == 2) {
            builder.setTitle(title).setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent welcome = new Intent(getActivity(), MainActivity.class);
                    welcome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    ActivityCompat.finishAffinity(getActivity());
                    startActivity(welcome);
                }
            });
        }

        return builder.create();

    }
}
