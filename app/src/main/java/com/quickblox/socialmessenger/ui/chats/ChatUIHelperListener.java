package com.quickblox.socialmessenger.ui.chats;

public interface ChatUIHelperListener {

    public void onScrollMessagesToBottom();

    public void onScreenResetPossibilityPerformLogout(boolean canPerformLogout);
}