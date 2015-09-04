package com.quickblox.core.qb.commands.push;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.quickblox.core.exception.QBResponseException;
import com.quickblox.messages.QBMessages;
import com.quickblox.messages.model.QBEvent;
import com.quickblox.core.core.command.ServiceCommand;
import com.quickblox.core.core.gcm.NotificationHelper;
import com.quickblox.core.qb.helpers.QBFriendListHelper;
import com.quickblox.core.service.QBService;
import com.quickblox.core.service.QBServiceConsts;
import com.quickblox.core.utils.ConstsCore;

import java.util.ArrayList;

public class QBSendPushCommand extends ServiceCommand {

    private QBFriendListHelper friendListHelper;

    public QBSendPushCommand(Context context, String successAction, String failAction) {
        super(context, successAction, failAction);
    }

    public static void start(Context context, String message, ArrayList<Integer> friendIdsList) {
        Intent intent = new Intent(QBServiceConsts.SEND_PUSH_ACTION, null, context, QBService.class);
        intent.putExtra(QBServiceConsts.EXTRA_FRIENDS, friendIdsList);
        intent.putExtra(ConstsCore.PUSH_MESSAGE, message);
        context.startService(intent);
    }

    public static void start(Context context, String message, Integer friendId) {
        Intent intent = new Intent(QBServiceConsts.SEND_PUSH_ACTION, null, context, QBService.class);
        ArrayList<Integer> friendIdsList = new ArrayList<Integer>();
        friendIdsList.add(friendId);
        intent.putExtra(QBServiceConsts.EXTRA_FRIENDS, friendIdsList);
        intent.putExtra(ConstsCore.PUSH_MESSAGE, message);
        context.startService(intent);
    }

    @Override
    protected Bundle perform(Bundle extras) throws QBResponseException {
        ArrayList<Integer> usersIdsList = (ArrayList<Integer>) extras.getSerializable(
                QBServiceConsts.EXTRA_FRIENDS);
        String message = extras.getString(ConstsCore.PUSH_MESSAGE);
        QBEvent pushEvent = NotificationHelper.createPushEvent(usersIdsList, message, null);

        try {
            QBMessages.createEvent(pushEvent);
        } catch (QBResponseException e) {
            /* ignore message = "No one can receive the message" */
        }

        return null;
    }
}
