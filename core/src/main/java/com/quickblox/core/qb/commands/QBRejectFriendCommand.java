package com.quickblox.core.qb.commands;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.core.command.ServiceCommand;
import com.quickblox.core.qb.helpers.QBFriendListHelper;
import com.quickblox.core.service.QBService;
import com.quickblox.core.service.QBServiceConsts;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

public class QBRejectFriendCommand extends ServiceCommand {

    private QBFriendListHelper friendListHelper;

    public QBRejectFriendCommand(Context context, QBFriendListHelper friendListHelper, String successAction,
            String failAction) {
        super(context, successAction, failAction);
        this.friendListHelper = friendListHelper;
    }

    public static void start(Context context, int friendId) {
        Intent intent = new Intent(QBServiceConsts.REJECT_FRIEND_ACTION, null, context, QBService.class);
        intent.putExtra(QBServiceConsts.EXTRA_FRIEND_ID, friendId);
        context.startService(intent);
    }

    @Override
    protected Bundle perform(Bundle extras) throws QBResponseException {
        int friendId = extras.getInt(QBServiceConsts.EXTRA_FRIEND_ID);

        try {
            friendListHelper.rejectFriend(friendId);
        } catch (SmackException | XMPPException e){
            throw new QBResponseException(e.getLocalizedMessage());
        }
        Bundle result = new Bundle();
        result.putSerializable(QBServiceConsts.EXTRA_FRIEND_ID, friendId);

        return result;
    }
}