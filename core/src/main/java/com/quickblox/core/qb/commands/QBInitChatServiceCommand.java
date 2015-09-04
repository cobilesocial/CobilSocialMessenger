package com.quickblox.core.qb.commands;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.core.command.ServiceCommand;
import com.quickblox.core.qb.helpers.QBChatRestHelper;
import com.quickblox.core.service.QBService;
import com.quickblox.core.service.QBServiceConsts;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;

public class QBInitChatServiceCommand extends ServiceCommand {

    private QBChatRestHelper chatRestHelper;

    public QBInitChatServiceCommand(Context context, QBChatRestHelper chatRestHelper, String successAction,
            String failAction) {
        super(context, successAction, failAction);
        this.chatRestHelper = chatRestHelper;
    }

    public static void start(Context context) {
        Intent intent = new Intent(QBServiceConsts.INIT_CHAT_SERVICE_ACTION, null, context, QBService.class);
        context.startService(intent);
    }

    @Override
    public Bundle perform(Bundle extras) throws QBResponseException {
        try {
            chatRestHelper.initChatService();
        } catch (XMPPException | SmackException e) {
            throw new QBResponseException(e.getLocalizedMessage());
        }
        return extras;
    }
}