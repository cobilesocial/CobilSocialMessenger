package com.quickblox.socialmessenger.ui.main;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;

import com.quickblox.chat.model.QBDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.core.core.command.Command;
import com.quickblox.core.db.managers.ChatDatabaseManager;
import com.quickblox.core.db.managers.UsersDatabaseManager;
import com.quickblox.core.models.AppSession;
import com.quickblox.core.models.User;
import com.quickblox.core.qb.commands.QBInitVideoChatCommand;
import com.quickblox.core.qb.commands.QBLoadDialogsCommand;
import com.quickblox.core.qb.commands.QBLoadFriendListCommand;
import com.quickblox.core.service.ConnectivityListener;
import com.quickblox.core.service.QBServiceConsts;
import com.quickblox.core.utils.ConstsCore;
import com.quickblox.core.utils.PrefsHelper;
import com.quickblox.socialmessenger.R;
import com.quickblox.socialmessenger.core.gcm.GSMHelper;
import com.quickblox.socialmessenger.ui.base.BaseLogeableActivity;
import com.quickblox.socialmessenger.ui.chats.GroupDialogActivity;
import com.quickblox.socialmessenger.ui.chats.PrivateDialogActivity;
import com.quickblox.socialmessenger.ui.chats.dialogs.DialogsFragment;
import com.quickblox.socialmessenger.ui.friends.FriendsListFragment;
import com.quickblox.socialmessenger.ui.importfriends.ImportFriends;
import com.quickblox.socialmessenger.ui.invitefriends.InviteFriendsFragment;
import com.quickblox.socialmessenger.ui.mediacall.CallActivity;
import com.quickblox.socialmessenger.ui.settings.SettingsFragment;
import com.quickblox.socialmessenger.utils.FacebookHelper;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends BaseLogeableActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    public static final int ID_CHATS_LIST_FRAGMENT = 0;
    public static final int ID_ROOMS_LIST_FRAGMENT = 1;
    public static final int ID_CONTACTS_LIST_FRAGMENT = 2;
//    public static final int ID_INVITE_FRIENDS_FRAGMENT = 2;
    public static final int ID_SETTINGS_FRAGMENT = 3;
//    public static final int ID_FEEDBACK_FRAGMENT = 4;

    private static final String TAG = MainActivity.class.getSimpleName();

    private NavigationDrawerFragment navigationDrawerFragment;
    private FacebookHelper facebookHelper;
    private ImportFriends importFriends;
    private GSMHelper gsmHelper;
    private boolean isNeedToOpenDialog;
    private Set<ConnectivityListener> connectivityListeners;

    public static void start(Context context) {

        PrefsHelper.getPrefsHelper().savePref(PrefsHelper.PREF_PUSH_NOTIFICATIONS_ON_LOGOUT, false);

        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (navigationDrawerFragment != null) {
            prepareMenu(menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (currentFragment instanceof InviteFriendsFragment) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
        } else if (facebookHelper != null) {
            facebookHelper.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void prepareMenu(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            menu.getItem(i).setVisible(!navigationDrawerFragment.isDrawerOpen());
            menu.getItem(i).collapseActionView();
        }
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        Fragment fragment = null;
        switch (position) {
            case ID_CHATS_LIST_FRAGMENT:
                fragment = DialogsFragment.newInstance();
                break;
            case ID_ROOMS_LIST_FRAGMENT:
                fragment = DialogsFragment.newInstance(true);
                break;
            case ID_CONTACTS_LIST_FRAGMENT:
                fragment = FriendsListFragment.newInstance();
                break;
//            case ID_INVITE_FRIENDS_FRAGMENT:
//                fragment = InviteFriendsFragment.newInstance();
//                break;
            case ID_SETTINGS_FRAGMENT:
                fragment = SettingsFragment.newInstance();
                break;
//            case ID_FEEDBACK_FRAGMENT:
//                fragment = FeedbackFragment.newInstance();
//                break;
        }

        if (fragment instanceof ConnectivityListener){
            connectivityListeners.add((ConnectivityListener) fragment);
        }

        setCurrentFragment(fragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        useDoubleBackPressed = true;
        connectivityListeners = new HashSet<>();
        gsmHelper = new GSMHelper(this);

        initNavigationDrawer();

        initBroadcastActionList();
        checkGCMRegistration();
        loadFriendsList();

        initVideoChat();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectivityListeners.clear();
    }

    public void removeConnectivityListener(ConnectivityListener listener){
        connectivityListeners.remove(listener);
    }

    private void initVideoChat() {
        QBInitVideoChatCommand.start(this, CallActivity.class);
    }

    private boolean isImportInitialized() {
        PrefsHelper prefsHelper = PrefsHelper.getPrefsHelper();
        return prefsHelper.getPref(PrefsHelper.PREF_IMPORT_INITIALIZED, false);
    }

    private void initBroadcastActionList() {
        addAction(QBServiceConsts.LOAD_CHATS_DIALOGS_SUCCESS_ACTION, new LoadDialogsSuccessAction());
        addAction(QBServiceConsts.LOAD_FRIENDS_SUCCESS_ACTION, new LoadFriendsSuccessAction());
        addAction(QBServiceConsts.LOAD_FRIENDS_FAIL_ACTION, failAction);
        addAction(QBServiceConsts.LOAD_CHATS_DIALOGS_FAIL_ACTION, failAction);
        addAction(QBServiceConsts.IMPORT_FRIENDS_SUCCESS_ACTION, new ImportFriendsSuccessAction());
        addAction(QBServiceConsts.IMPORT_FRIENDS_FAIL_ACTION, new ImportFriendsFailAction());
        addAction(QBServiceConsts.LOGIN_AND_JOIN_CHATS_SUCCESS_ACTION, new LoginAndJoinChatsSuccessAction());
    }

    private void initNavigationDrawer() {
        navigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(
                R.id.navigation_drawer);
        navigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(
                R.id.drawer_layout));
    }

    private void checkGCMRegistration() {
        if (gsmHelper.checkPlayServices()) {
            if (!gsmHelper.isDeviceRegisteredWithUser(AppSession.getSession().getUser())) {
                gsmHelper.registerInBackground();
            }
        } else {
            Log.i(TAG, "No valid Google Play Services APK found.");
        }
    }

    private void loadFriendsList() {
        QBLoadFriendListCommand.start(this);
    }

    private void loadChatsDialogs() {
        QBLoadDialogsCommand.start(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gsmHelper.checkPlayServices();
        showActionBarProgress();
        checkVisibilityProgressBars();
    }

    @Override
    protected void onFailAction(String action) {
        hideActionBarProgress();
        hideProgress();
        if (QBServiceConsts.LOAD_FRIENDS_FAIL_ACTION.equals(action)) {
            loadChatsDialogs();
        }
    }

    private void checkVisibilityProgressBars() {
        isNeedToOpenDialog = PrefsHelper.getPrefsHelper().getPref(
                PrefsHelper.PREF_PUSH_MESSAGE_NEED_TO_OPEN_DIALOG, false);
        if (isJoinedToDialogs()) {
            hideActionBarProgress();
        }
        if (isNeedToOpenDialog && !isJoinedToDialogs()) {
            hideActionBarProgress();
            showProgress();
        }
    }

    private boolean isJoinedToDialogs() {
        PrefsHelper prefsHelper = PrefsHelper.getPrefsHelper();
        return prefsHelper.getPref(PrefsHelper.PREF_JOINED_TO_ALL_DIALOGS, false);
    }

    private void startDialog() {
        PrefsHelper.getPrefsHelper().savePref(PrefsHelper.PREF_PUSH_MESSAGE_NEED_TO_OPEN_DIALOG, false);
        String dialogId = PrefsHelper.getPrefsHelper().getPref(PrefsHelper.PREF_PUSH_MESSAGE_DIALOG_ID, null);
        long userId = PrefsHelper.getPrefsHelper().getPref(PrefsHelper.PREF_PUSH_MESSAGE_USER_ID,
                ConstsCore.NOT_INITIALIZED_VALUE);
        QBDialog dialog = ChatDatabaseManager.getDialogByDialogId(this, dialogId);
        if (dialog.getType() == QBDialogType.PRIVATE) {
            startPrivateChatActivity(dialog, userId);
        } else {
            startGroupChatActivity(dialog);
        }
    }

    private void startPrivateChatActivity(QBDialog dialog, long userId) {
        User occupantUser = UsersDatabaseManager.getUserById(this, userId);
        if (occupantUser != null && userId != ConstsCore.ZERO_INT_VALUE) {
            PrivateDialogActivity.start(this, occupantUser, dialog);
        }
    }

    private void importFriendsFinished() {
        PrefsHelper.getPrefsHelper().savePref(PrefsHelper.PREF_IMPORT_INITIALIZED, true);
        hideProgress();
    }

    private void startGroupChatActivity(QBDialog dialog) {
        GroupDialogActivity.start(this, dialog);
    }

    private class LoadDialogsSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            hideActionBarProgress();
            hideProgress();

            if (isNeedToOpenDialog) {
                startDialog();
            }
        }
    }

    private class LoginAndJoinChatsSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            loadChatsDialogs();
        }
    }

    private class LoadFriendsSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) throws Exception {
            loadChatsDialogs();
        }
    }

    private class ImportFriendsSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            importFriendsFinished();
        }
    }

    private class ImportFriendsFailAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            importFriendsFinished();
        }
    }

    @Override
    public void onConnectionChange(boolean isConnected) {
        super.onConnectionChange(isConnected);
        for (ConnectivityListener listener : connectivityListeners){
            listener.onConnectionChange(isConnected);
        }

        if(currentFragment instanceof FriendsListFragment) {
            invalidateOptionsMenu();
        }
    }
}