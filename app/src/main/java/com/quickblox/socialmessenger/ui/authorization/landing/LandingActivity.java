package com.quickblox.socialmessenger.ui.authorization.landing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.quickblox.core.core.command.Command;
import com.quickblox.core.service.QBServiceConsts;
import com.quickblox.core.utils.Utils;
import com.quickblox.socialmessenger.R;
import com.quickblox.socialmessenger.ui.authorization.base.BaseAuthActivity;
import com.quickblox.socialmessenger.ui.authorization.login.LoginActivity;
import com.quickblox.socialmessenger.ui.authorization.signup.SignUpActivity;
import com.quickblox.users.model.QBUser;

public class LandingActivity extends BaseAuthActivity {

    public static void start(Context context) {
        Intent intent = new Intent(context, LandingActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        useDoubleBackPressed = true;

        initVersionName();
        addActions();
    }

    public void signUpOnClickListener(View view) {
        startSignUpActivity();
    }

    private void startSignUpActivity() {
        SignUpActivity.start(LandingActivity.this);
        finish();
    }

    public void loginOnClickListener(View view) {
        LoginActivity.start(LandingActivity.this);
        finish();
    }

    private void addActions() {
        addAction(QBServiceConsts.LOGIN_SUCCESS_ACTION, new SocialLoginSuccessAction());
        addAction(QBServiceConsts.LOGIN_FAIL_ACTION, new SocialLoginFailAction());
        updateBroadcastActionList();
    }

    private void initVersionName() {
        TextView versionView = _findViewById(R.id.version);
        versionView.setText(getString(R.string.lnd_version, Utils.getAppVersionName(this)));
    }

    private class SocialLoginSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            QBUser user = (QBUser) bundle.getSerializable(QBServiceConsts.EXTRA_USER);
            startMainActivity(LandingActivity.this, user, true);
        }
    }

    private class SocialLoginFailAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            Exception exception = (Exception) bundle.getSerializable(QBServiceConsts.EXTRA_ERROR);
            int errorCode = bundle.getInt(QBServiceConsts.EXTRA_ERROR_CODE);
            parseExceptionMessage(exception);
        }
    }
}