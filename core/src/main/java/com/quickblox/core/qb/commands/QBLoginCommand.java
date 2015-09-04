package com.quickblox.core.qb.commands;

import android.content.Context;
import android.content.Intent;

import com.quickblox.users.model.QBUser;
import com.quickblox.core.core.command.CompositeServiceCommand;
import com.quickblox.core.service.QBService;
import com.quickblox.core.service.QBServiceConsts;

public class QBLoginCommand extends CompositeServiceCommand {

    public QBLoginCommand(Context context, String successAction, String failAction) {
        super(context, successAction, failAction);
    }

    public static void start(Context context, QBUser user) {
        Intent intent = new Intent(QBServiceConsts.LOGIN_ACTION, null, context, QBService.class);
        intent.putExtra(QBServiceConsts.EXTRA_USER, user);
        context.startService(intent);
    }
}