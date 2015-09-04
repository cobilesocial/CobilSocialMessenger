package com.quickblox.core.db;

import android.content.UriMatcher;
import android.net.Uri;

import com.quickblox.core.db.tables.FriendTable;
import com.quickblox.core.db.tables.FriendsRelationTable;
import com.quickblox.core.db.tables.MessageTable;
import com.quickblox.core.db.tables.DialogTable;
import com.quickblox.core.db.tables.NotSendMessageTable;
import com.quickblox.core.db.tables.UserTable;

public class ContentDescriptor {

    public static final String AUTHORITY = "com.quickblox.socialmessenger";
    public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);
    public static final UriMatcher URI_MATCHER = buildUriMatcher();

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);

        matcher.addURI(AUTHORITY, UserTable.PATH, UserTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, FriendTable.PATH, FriendTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, UserTable.USER_FRIEND_PATH, UserTable.USER_FRIEND_PATH_TOKEN);
        matcher.addURI(AUTHORITY, FriendsRelationTable.PATH, FriendsRelationTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, DialogTable.PATH, DialogTable.PATH_TOKEN);
        matcher.addURI(AUTHORITY, MessageTable.PATH, MessageTable.PATH_TOKEN);

        // Added to store not sent messages
        matcher.addURI(AUTHORITY, NotSendMessageTable.PATH, NotSendMessageTable.PATH_TOKEN);
        // TODO Sergey Fedunets other tables can be added

        return matcher;
    }
}