package com.quickblox.socialmessenger.ui.authorization.base;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.facebook.Session;
import com.facebook.SessionState;
import com.quickblox.auth.model.QBProvider;
import com.quickblox.core.db.managers.ChatDatabaseManager;
import com.quickblox.core.models.AppSession;
import com.quickblox.core.models.LoginType;
import com.quickblox.core.qb.commands.QBLoginRestWithSocialCommand;
import com.quickblox.core.utils.DialogUtils;
import com.quickblox.socialmessenger.R;
import com.quickblox.socialmessenger.ui.authorization.landing.UserAgreementDialog;
import com.quickblox.socialmessenger.ui.base.BaseActivity;
import com.quickblox.socialmessenger.ui.main.MainActivity;
import com.quickblox.socialmessenger.utils.FacebookHelper;
import com.quickblox.socialmessenger.utils.ValidationUtils;
import com.quickblox.users.model.QBUser;

public class BaseAuthActivity extends BaseActivity {

    protected static final String STARTED_LOGIN_TYPE = "started_login_type";

    protected EditText usernameEditText;
    protected EditText emailEditText;
    protected EditText passwordEditText;
    protected FacebookHelper facebookHelper;
    protected LoginType startedLoginType = LoginType.EMAIL;
    protected ValidationUtils validationUtils;
    protected Resources resources;

    protected UserAgreementDialog userAgreementDialog;

    protected DialogInterface.OnClickListener positiveUserAgreementOnClickListener;
    protected DialogInterface.OnClickListener negativeUserAgreementOnClickListener;

    public static void start(Context context) {
        Intent intent = new Intent(context, BaseAuthActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        resources = getResources();

        if (savedInstanceState != null && savedInstanceState.containsKey(STARTED_LOGIN_TYPE)) {
            startedLoginType = (LoginType) savedInstanceState.getSerializable(STARTED_LOGIN_TYPE);
        }

        facebookHelper = new FacebookHelper(this, savedInstanceState, new FacebookSessionStatusCallback());
    }

    @Override
    public void onStart() {
        super.onStart();
        facebookHelper.onActivityStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        facebookHelper.onActivityStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(STARTED_LOGIN_TYPE, startedLoginType);
        facebookHelper.onSaveInstanceState(outState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookHelper.onActivityResult(requestCode, resultCode, data);
    }

    public void connectFacebookOnClickListener(View view) {
        startedLoginType = LoginType.FACEBOOK;
        loginWithFacebook();
    }

    private void loginWithFacebook() {
        FacebookHelper.logout(); // clearing old data
        facebookHelper.loginWithFacebook();
    }


    protected void startMainActivity(Context context, QBUser user, boolean saveRememberMe) {
        ChatDatabaseManager.clearAllCache(context);
        AppSession.getSession().updateUser(user);
        AppSession.saveRememberMe(saveRememberMe);
        MainActivity.start(context);
        finish();
    }

    protected void parseExceptionMessage(Exception exception) {
        String errorMessage = exception.getMessage();

        hideProgress();

        // TODO: temp decision
        if (exception.getMessage().equals(resources.getString(R.string.error_bad_timestamp))) {
            errorMessage = resources.getString(R.string.error_bad_timestamp_from_app);
        } else if (exception.getMessage().equals(resources.getString(
                R.string.error_email_already_taken)) && startedLoginType.equals(LoginType.FACEBOOK)) {
            errorMessage = resources.getString(R.string.error_email_already_taken_from_app);
            DialogUtils.showLong(BaseAuthActivity.this, errorMessage);
            return;
        } else if (exception.getMessage().equals(resources.getString(R.string.error_email_already_taken))){
            errorMessage = resources.getString(R.string.error_email_already_taken_from_app_without_facebook);
        }

        validationUtils.setError(errorMessage);
    }

    private class FacebookSessionStatusCallback implements Session.StatusCallback {

        @Override
        public void call(Session session, SessionState state, Exception exception) {
            if (session.isOpened()) {
                showProgress();
                QBLoginRestWithSocialCommand.start(BaseAuthActivity.this, QBProvider.FACEBOOK,
                        session.getAccessToken(), null);
            }
        }
    }
}