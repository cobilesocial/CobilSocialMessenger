package com.quickblox.socialmessenger.ui.chats.dialogs;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.quickblox.chat.model.QBDialog;
import com.quickblox.chat.model.QBDialogType;
import com.quickblox.core.core.command.Command;
import com.quickblox.core.db.managers.ChatDatabaseManager;
import com.quickblox.core.db.managers.UsersDatabaseManager;
import com.quickblox.core.db.tables.UserTable;
import com.quickblox.core.models.ParcelableQBDialog;
import com.quickblox.core.models.User;
import com.quickblox.core.qb.commands.QBDeleteDialogCommand;
import com.quickblox.core.service.QBServiceConsts;
import com.quickblox.core.utils.ChatUtils;
import com.quickblox.core.utils.ConstsCore;
import com.quickblox.core.utils.DialogUtils;
import com.quickblox.socialmessenger.R;
import com.quickblox.socialmessenger.ui.base.BaseFragment;
import com.quickblox.socialmessenger.ui.chats.GroupDialogActivity;
import com.quickblox.socialmessenger.ui.chats.PrivateDialogActivity;
import com.quickblox.socialmessenger.ui.dialogs.ConfirmDialog;

import java.util.ArrayList;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class DialogsFragment extends BaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int DIALOGS_LOADER_ID = 0;

    private ListView dialogsListView;
    private DialogsAdapter dialogsAdapter;
    private TextView emptyListTextView;
    private ContentObserver userTableContentObserver;
    private Cursor dialogsCursor;
    private int selectedPositionList;
    private boolean isRooms;
    private String roomTitle;

    public static DialogsFragment newInstance() {
        return new DialogsFragment();
    }
    public static DialogsFragment newInstance(Boolean isRooms) {
        Bundle args = new Bundle();
        args.putBoolean(QBServiceConsts.EXTRA_IS_ROOMS, isRooms);
        DialogsFragment fragment = new DialogsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isRooms = getArguments() != null && getArguments()
                .getBoolean(QBServiceConsts.EXTRA_IS_ROOMS, false);
        title = isRooms ? getString(R.string.nvd_title_web_room) : getString(R.string.nvd_title_chats);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialogs_list, container, false);

        if(savedInstanceState != null){
            selectedPositionList = savedInstanceState.getInt(ConstsCore.LAST_CLICKED_DIALOG);
        }

        initUI(view);
        initListeners();
        Crouton.cancelAllCroutons();

        addActions();
        initCursorLoaders();

        registerForContextMenu(dialogsListView);
        registerContentObservers();

        return view;
    }

    private void registerContentObservers() {
        userTableContentObserver = new ContentObserver(new Handler()) {

            @Override
            public void onChange(boolean selfChange) {
                initCursorLoaders();
            }
        };

        baseActivity.getContentResolver().registerContentObserver(UserTable.CONTENT_URI, true,
                userTableContentObserver);
    }

    private void unregisterContentObservers() {
        baseActivity.getContentResolver().unregisterContentObserver(userTableContentObserver);
    }

    private void initCursorLoaders() {
        if (isAdded()) {
            getLoaderManager().initLoader(DIALOGS_LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if(isRooms) {
            return ChatDatabaseManager.getAllDialogsCursorLoaderByType(baseActivity, 2);
        } else {
            return ChatDatabaseManager.getAllDialogsCursorLoaderByType(baseActivity, 3);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor dialogsCursor) {
        this.dialogsCursor = dialogsCursor;
        initChatsDialogs(dialogsCursor);
        checkVisibilityEmptyLabel();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private void initUI(View view) {
        setHasOptionsMenu(true);
        dialogsListView = (ListView) view.findViewById(R.id.chats_listview);
        emptyListTextView = (TextView) view.findViewById(R.id.empty_list_textview);
    }

    private void initListeners() {
        dialogsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                Crouton.clearCroutonsForActivity(getActivity());

                // Check cursor
                Cursor selectedChatCursor = null;
                if (dialogsAdapter.getCursor().getCount() > ConstsCore.ZERO_INT_VALUE) {
                    selectedChatCursor = (Cursor) dialogsAdapter.getItem(position);
                }

                QBDialog dialog = ChatDatabaseManager.getDialogFromCursor(selectedChatCursor);
                if (dialog.getType() == QBDialogType.PRIVATE) {
                    startPrivateChatActivity(dialog);
                } else {
                    startGroupChatActivity(dialog);

                }

                selectedPositionList = parent.getFirstVisiblePosition();
            }
        });

        dialogsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                Crouton.clearCroutonsForActivity(getActivity());

                ConfirmDialog confirmDialog = ConfirmDialog.newInstance(R.string.dlg_delete_dialog_title, R.string.dlg_delete_dialog_message);
                confirmDialog.setPositiveButton(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface confirmDialogInterface, int which) {
                        // Check cursor
                        Cursor selectedChatCursor = null;
                        if (dialogsAdapter.getCursor().getCount() > ConstsCore.ZERO_INT_VALUE) {
                            selectedChatCursor = (Cursor) dialogsAdapter.getItem(position);
                        }

                        QBDialog dialog = ChatDatabaseManager.getDialogFromCursor(selectedChatCursor);

                        QBDeleteDialogCommand.start(getActivity(), dialog.getDialogId(), dialog.getType());

                    }
                });
                confirmDialog.show(getFragmentManager(), null);

//                RestAdapter restAdapter = new RestAdapter.Builder()
//                                                         .setLogLevel(RestAdapter.LogLevel.FULL)
//                                                         .setEndpoint(Consts.QB_DOMAIN)
//                                                         .setClient(new OkClient(new OkHttpClient()))
//                                                         .build();
//                DialogsInterface apiInterface = restAdapter.create(DialogInterface.class)




                return true;
            }
        });

//        dialogsListView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//                selectedPositionList = dialogsListView.getFirstVisiblePosition();
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//            }
//        });
    }

    private void checkVisibilityEmptyLabel() {
        emptyListTextView.setVisibility(dialogsAdapter.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onResume() {
        if (dialogsAdapter != null) {
            checkVisibilityEmptyLabel();
        }

        restoreLastUsedListPosition();

        super.onResume();
    }

    private void restoreLastUsedListPosition() {
        dialogsListView.setSelection(selectedPositionList);

        // Erase last used list position to prevent int using next time
        selectedPositionList = ConstsCore.ZERO_INT_VALUE;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        unregisterContentObservers();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(isRooms) {
            inflater.inflate(R.menu.dialogs_list_menu, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startNewDialogPage();
                break;
        }
        return true;
    }

    private void initChatsDialogs(Cursor newCursor) {
        if (dialogsAdapter == null) {
            dialogsAdapter = new DialogsAdapter(baseActivity, dialogsCursor, isRooms);

            dialogsAdapter.registerDataSetObserver(new DataSetObserver() {
                @Override
                public void onChanged() {
                    super.onChanged();
                    checkVisibilityEmptyLabel();
                }
            });
            dialogsListView.setAdapter(dialogsAdapter);
        } else {
            dialogsAdapter.swapCursor(newCursor);
//            if(selectedPositionList > ConstsCore.ZERO_INT_VALUE){
//                dialogsListView.setSelection(selectedPositionList);
//            }
        }

    }

    private void startPrivateChatActivity(QBDialog dialog) {
        int occupantId = ChatUtils.getOccupantIdFromList(dialog.getOccupants());
        User occupant = dialogsAdapter.getOccupantById(occupantId);
        if (!TextUtils.isEmpty(dialog.getDialogId())) {
            PrivateDialogActivity.start(baseActivity, occupant, dialog);
        }
    }

    private void startGroupChatActivity(QBDialog dialog) {
        GroupDialogActivity.start(baseActivity, dialog);
    }

    private void startNewDialogPage() {
        boolean isFriends = UsersDatabaseManager.getAllFriends(baseActivity)
                .getCount() > ConstsCore.ZERO_INT_VALUE;
        if (isFriends) {

            if(isRooms) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Room Name");
                // Set up the input
                final EditText input = new EditText(getActivity());

                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);

                // Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        roomTitle = input.getText().toString();
                        NewDialogActivity.start(baseActivity, roomTitle);

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            } else {
                NewDialogActivity.start(baseActivity);
            }
        } else {
            DialogUtils.showLong(baseActivity, getResources().getString(
                    R.string.ndl_no_friends_for_new_chat));
        }
    }

    private void addActions() {
        baseActivity.addAction(QBServiceConsts.LOAD_CHATS_DIALOGS_SUCCESS_ACTION,
                new LoadChatsDialogsSuccessAction());
        baseActivity.addAction(QBServiceConsts.LOAD_CHATS_DIALOGS_FAIL_ACTION, failAction);
        baseActivity.updateBroadcastActionList();
    }

    private class LoadChatsDialogsSuccessAction implements Command {

        @Override
        public void execute(Bundle bundle) {
            ArrayList<ParcelableQBDialog> parcelableDialogsList = bundle.getParcelableArrayList(
                    QBServiceConsts.EXTRA_CHATS_DIALOGS);
            if (parcelableDialogsList == null) {
                emptyListTextView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ConstsCore.LAST_CLICKED_DIALOG, selectedPositionList);
    }
}