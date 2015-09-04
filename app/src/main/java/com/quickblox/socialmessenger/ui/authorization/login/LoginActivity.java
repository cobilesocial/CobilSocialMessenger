package com.quickblox.socialmessenger.ui.authorization.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.quickblox.core.core.command.Command;
import com.quickblox.core.qb.commands.QBLoginCommand;
import com.quickblox.core.service.QBServiceConsts;
import com.quickblox.core.utils.PrefsHelper;
import com.quickblox.socialmessenger.R;
import com.quickblox.socialmessenger.ui.authorization.base.BaseAuthActivity;
import com.quickblox.socialmessenger.ui.authorization.landing.LandingActivity;
import com.quickblox.socialmessenger.ui.forgotpassword.ForgotPasswordActivity;
import com.quickblox.socialmessenger.utils.AnalyticsUtils;
import com.quickblox.socialmessenger.utils.ValidationUtils;
import com.quickblox.users.model.QBUser;

public class LoginActivity extends BaseAuthActivity {

    private CheckBox rememberMeCheckBox;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initUI();

        boolean isRememberMe = PrefsHelper.getPrefsHelper().getPref(PrefsHelper.PREF_REMEMBER_ME, true);
        rememberMeCheckBox.setChecked(isRememberMe);

        validationUtils = new ValidationUtils(LoginActivity.this,
                new EditText[]{emailEditText, passwordEditText}, new String[]{resources.getString(
                R.string.dlg_not_username_field_entered), resources.getString(
                R.string.dlg_not_password_field_entered)});

        addActions();
    }

    @Override
    public void onBackPressed() {
        LandingActivity.start(this);
        finish();
    }

    public void loginOnClickListener(View view) {
//        String userName = usernameEditText.getText().toString();
        String userEmail = emailEditText.getText().toString();
        String userPassword = passwordEditText.getText().toString();

        if (validationUtils.isValidUserDate(userEmail, userPassword)) {
            PrefsHelper.getPrefsHelper().savePref(PrefsHelper.PREF_IMPORT_INITIALIZED, true);
            login(userEmail, null, userPassword);
        }
    }

    public void forgotPasswordOnClickListener(View view) {
        startChangePasswordActivity();
    }

    private void startChangePasswordActivity() {
        ForgotPasswordActivity.start(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initUI() {
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        usernameEditText = _findViewById(R.id.login_edittext);
        emailEditText = _findViewById(R.id.email_textview);
        passwordEditText = _findViewById(R.id.password_edittext);
        rememberMeCheckBox = _findViewById(R.id.remember_me_checkbox);
    }

    private void addActions() {
        addAction(QBServiceConsts.LOGIN_SUCCESS_ACTION, new LoginSuccessAction());
        addAction(QBServiceConsts.LOGIN_FAIL_ACTION, new LoginFailAction());
        updateBroadcastActionList();
    }

    private void login(String userName, String userEmail, String userPassword) {
        QBUser user = new QBUser(userName, userPassword);
        showProgress();
        QBLoginCommand.start(this, user);
    }

    private class LoginSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            QBUser user = (QBUser) bundle.getSerializable(QBServiceConsts.EXTRA_USER);
            boolean saveRememberMe = false;
            if (rememberMeCheckBox.isChecked()) {
                saveRememberMe = true;
            }
            startMainActivity(LoginActivity.this, user, saveRememberMe);

            AnalyticsUtils.pushAnalyticsData(LoginActivity.this, user, "User Sign In");
        }
    }

    private class LoginFailAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            Exception exception = (Exception) bundle.getSerializable(QBServiceConsts.EXTRA_ERROR);
            int errorCode = bundle.getInt(QBServiceConsts.EXTRA_ERROR_CODE);
            parseExceptionMessage(exception);
        }
    }
}