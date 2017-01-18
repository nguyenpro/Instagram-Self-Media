package com.npc.instafeed.ui.start;

import com.npc.instafeed.ui.dialog.LoginDialog;

/**
 * Created by USER on 16/01/2017.
 */

public interface LoginView extends LoginDialog.LoginResultListener {
    void onTokenSaved();
}
