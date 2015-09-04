package com.quickblox.core.qb.commands;

import android.content.Context;
import android.content.Intent;

import com.quickblox.users.model.QBUser;
import com.quickblox.core.core.command.CompositeServiceCommand;
import com.quickblox.core.service.QBService;
import com.quickblox.core.service.QBServiceConsts;

import java.io.File;

public class QBSignUpCommand extends CompositeServiceCommand {

    public QBSignUpCommand(Context context, String successAction, String failAction) {
        super(context, successAction, failAction);
    }

    public static void start(Context context, QBUser user, File image) {
        Intent intent = new Intent(QBServiceConsts.SIGNUP_ACTION, null, context, QBService.class);
        intent.putExtra(QBServiceConsts.EXTRA_USER, user);
        intent.putExtra(QBServiceConsts.EXTRA_FILE, image);
        context.startService(intent);
    }
}