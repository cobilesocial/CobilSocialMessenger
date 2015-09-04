package com.quickblox.core.service;

public class QBServiceConsts {


    //main actions
    public static final String COMMAND_ACTION = "action";
    public static final String ADD_FRIEND_ACTION = "add_friend_action";
    public static final String ACCEPT_FRIEND_ACTION = "accept_friend_action";
    public static final String REMOVE_FRIEND_ACTION = "remove_friend_action";
    public static final String REJECT_FRIEND_ACTION = "reject_friend_action";
    public static final String IMPORT_FRIENDS_ACTION = "import_friends_action";
    public static final String CHANGE_PASSWORD_ACTION = "change_password_action";
    public static final String GET_FILE_ACTION = "get_file_action";
    public static final String LOGIN_ACTION = "login_action";
    public static final String LOGIN_REST_ACTION = "login_rest_command";
    public static final String LOGIN_REST_SOCIAL_ACTION = "login_rest__social_command";
    public static final String LOGIN_CHAT_ACTION = "login_chat_action";
    public static final String LOGOUT_ACTION = "logout_action";
    public static final String LOGOUT_REST_ACTION = "logout_rest_action";
    public static final String LOGOUT_CHAT_ACTION = "logout_chat_action";
    public static final String LOGOUT_AND_DESTROY_CHAT_ACTION = "logout_and_destroy_chat_action";
    public static final String RESET_PASSWORD_ACTION = "reset_password_action";
    public static final String SIGNUP_ACTION = "signup_action";
    public static final String SOCIAL_LOGIN_ACTION = "social_login_action";
    public static final String UPDATE_USER_ACTION = "update_user_action";
    public static final String LOAD_FRIENDS_ACTION = "friends_load_action";
    public static final String LOAD_USERS_ACTION = "users_search_action";
    public static final String FIND_USERS_ACTION = "find_search_action";
    public static final String LOAD_USER_ACTION = "user_search_action";
    public static final String SEND_MESSAGE_ACTION = "send_message_action";
    public static final String SEND_GROUP_MESSAGE_ACTION = "send_group_message_action";
    public static final String LOAD_ATTACH_FILE_ACTION = "load_attach_file_action";
    public static final String INIT_FRIEND_LIST_ACTION = "init friend list action";
    public static final String INIT_CHATS_ACTION = "init_chats_action";
    public static final String INIT_CHAT_SERVICE_ACTION = "init_chat_service_action";
    public static final String INIT_VIDEO_CHAT_ACTION = "init_video_chat_action";
    public static final String SIGNUP_REST_ACTION = "signup_rest_action";
    public static final String CREATE_GROUP_CHAT_ACTION = "create_group_chat_action";
    public static final String CREATE_PRIVATE_CHAT_ACTION = "create_private_chat_action";
    public static final String LOAD_CHATS_DIALOGS_ACTION = "chats_dialogs_load_action";
    public static final String UPDATE_CHAT_DIALOG_ACTION = "update_chat_dialog_action";
    public static final String JOIN_GROUP_CHAT_ACTION = "join_group_chat_action";
    public static final String LOAD_DIALOG_MESSAGES_ACTION = "load_dialog_messages_action";
    public static final String LOAD_GROUP_DIALOG_ACTION = "load_group_dialog_action";
    public static final String LEAVE_GROUP_DIALOG_ACTION = "leave_group_group_action";
    public static final String ADD_FRIENDS_TO_GROUP_ACTION = "add_friends_to_group_action";
    public static final String UPDATE_STATUS_MESSAGE_ACTION = "update_status_message_action";
    public static final String SEND_PUSH_ACTION = "send_push_action";
    public static final String GET_USER_BY_ID_ACTION = "get_user_by_id_action";
    public static final String RE_LOGIN_IN_CHAT_ACTION = "relogin_in_chat";
    public static final String DELETE_DIALOG_ACTION = "delete_dialog_action";
    public static final String UPDATE_GROUP_DIALOG_ACTION = "update_group_dialog_name_action";

    //success and fail actions
    public static final String ADD_FRIEND_SUCCESS_ACTION = "add_friend_success_action";
    public static final String ADD_FRIEND_FAIL_ACTION = "add_friend_fail_action";
    public static final String ACCEPT_FRIEND_SUCCESS_ACTION = "accept_friend_success_action";
    public static final String ACCEPT_FRIEND_FAIL_ACTION = "accept_friend_fail_action";
    public static final String REMOVE_FRIEND_SUCCESS_ACTION = "remove_friend_success_action";
    public static final String REMOVE_FRIEND_FAIL_ACTION = "remove_friend_fail_action";
    public static final String REJECT_FRIEND_SUCCESS_ACTION = "reject_friend_success_action";
    public static final String REJECT_FRIEND_FAIL_ACTION = "reject_friend_fail_action";
    public static final String IMPORT_FRIENDS_SUCCESS_ACTION = "import_friends_success_action";
    public static final String IMPORT_FRIENDS_FAIL_ACTION = "import_friends_fail_action";
    public static final String CHANGE_PASSWORD_SUCCESS_ACTION = "change_password_success_action";
    public static final String CHANGE_PASSWORD_FAIL_ACTION = "change_password_fail_action";
    public static final String GET_FILE_SUCCESS_ACTION = "get_file_success_action";
    public static final String GET_FILE_FAIL_ACTION = "get_file_fail_action";
    public static final String LOGIN_SUCCESS_ACTION = "login_success_action";
    public static final String LOGIN_FAIL_ACTION = "login_fail_action";
    public static final String LOGOUT_SUCCESS_ACTION = "logout_success_action";
    public static final String LOGOUT_FAIL_ACTION = "logout_fail_action";
    public static final String RESET_PASSWORD_SUCCESS_ACTION = "reset_password_success_action";
    public static final String RESET_PASSWORD_FAIL_ACTION = "reset_password_fail_action";
    public static final String SIGNUP_SUCCESS_ACTION = "signup_success_action";
    public static final String SIGNUP_FAIL_ACTION = "signup_fail_action";
    public static final String UPDATE_USER_SUCCESS_ACTION = "update_user_success_action";
    public static final String UPDATE_USER_FAIL_ACTION = "update_user_fail_action";
    public static final String LOAD_FRIENDS_SUCCESS_ACTION = "friends_load_success_action";
    public static final String LOAD_FRIENDS_FAIL_ACTION = "friends_load_fail_action";
    public static final String LOAD_USERS_SUCCESS_ACTION = "users_search_success_action";
    public static final String LOAD_USERS_FAIL_ACTION = "users_search_fail_action";
    public static final String FIND_USERS_SUCCESS_ACTION = "find_users_success_action";
    public static final String FIND_USERS_FAIL_ACTION = "find_users_fail_action";
    public static final String LOAD_USER_SUCCESS_ACTION = "user_search_success_action";
    public static final String LOAD_USER_FAIL_ACTION = "user_search_fail_action";
    public static final String SEND_MESSAGE_SUCCESS_ACTION = "send_message_success_action";
    public static final String SEND_MESSAGE_FAIL_ACTION = "send_message_fail_action";
    public static final String LOAD_ATTACH_FILE_SUCCESS_ACTION = "load_attach_file_success_action";
    public static final String LOAD_ATTACH_FILE_FAIL_ACTION = "load_attach_file_fail_action";
    public static final String INIT_FRIEND_LIST_SUCCESS_ACTION = "init_friend_list_success_action";
    public static final String INIT_FRIEND_LIST_FAIL_ACTION = "init_friend_list_fail_action";
    public static final String LOGIN_REST_SUCCESS_ACTION = "login_rest_success_action";
    public static final String LOGIN_REST_FAIL_ACTION = "login_rest_fail_action";
    public static final String LOGIN_CHAT_SUCCESS_ACTION = "login_chat_success_action";
    public static final String LOGIN_CHAT_FAIL_ACTION = "login_chat_fail_action";
    public static final String LOGOUT_CHAT_SUCCESS_ACTION = "logout_chat_success_action";
    public static final String LOGOUT_CHAT_FAIL_ACTION = "logout_chat_fail_action";
    public static final String INIT_CHATS_SUCCESS_ACTION = "init_chats_success_action";
    public static final String INIT_CHATS_FAIL_ACTION = "init_chats_fail_action";
    public static final String INIT_CHAT_SERVICE_SUCCESS_ACTION = "init_chat_service_success_action";
    public static final String INIT_CHAT_SERVICE_FAIL_ACTION = "init_chat_service_fail_action";
    public static final String INIT_VIDEO_CHAT_SUCCESS_ACTION = "init_video_chat_success_action";
    public static final String INIT_VIDEO_CHAT_FAIL_ACTION = "init_video_chat_fail_action";
    public static final String LOGOUT_AND_DESTROY_CHAT_SUCCESS_ACTION = "logout_and_destroy_chat_success_action";
    public static final String LOGOUT_AND_DESTROY_CHAT_FAIL_ACTION = "logout_and_destroy_chat_fail_action";
    public static final String LOGOUT_REST_SUCCESS_ACTION = "logout_rest_success_action";
    public static final String LOGOUT_REST_FAIL_ACTION = "logout_rest_success_action";
    public static final String SIGNUP_REST_SUCCESS_ACTION = "signup_rest_success_action";
    public static final String SIGNUP_REST_FAIL_ACTION = "signup_rest_fail_action";
    public static final String CREATE_GROUP_CHAT_SUCCESS_ACTION = "create_group_chat_success_action";
    public static final String CREATE_GROUP_CHAT_FAIL_ACTION = "create_group_chat_fail_action";
    public static final String CREATE_PRIVATE_CHAT_SUCCESS_ACTION = "create_private_chat_success_action";
    public static final String CREATE_PRIVATE_CHAT_FAIL_ACTION = "create_private_chat_fail_action";
    public static final String LOAD_CHATS_DIALOGS_SUCCESS_ACTION = "chats_dialogs_success_action";
    public static final String LOAD_CHATS_DIALOGS_FAIL_ACTION = "chats_dialogs_fail_action";
    public static final String JOIN_GROUP_CHAT_SUCCESS_ACTION = "join_group_chat_success_action";
    public static final String JOIN_GROUP_CHAT_FAIL_ACTION = "join_group_chat_fail_action";
    public static final String UPDATE_CHAT_DIALOG_SUCCESS_ACTION = "update_chat_dialog_success_action";
    public static final String UPDATE_CHAT_DIALOG_FAIL_ACTION = "update_chat_dialog_fail_action";
    public static final String LOAD_DIALOG_MESSAGES_SUCCESS_ACTION = "load_dialog_messages_load_success_action";
    public static final String LOAD_DIALOG_MESSAGES_FAIL_ACTION = "load_dialog_messages_load_fail_action";
    public static final String UPDATE_STATUS_MESSAGE_SUCCESS_ACTION = "update_status_message_success_action";
    public static final String UPDATE_STATUS_MESSAGE_FAIL_ACTION = "update_status_message_fail_action";
    public static final String SEND_PUSH_MESSAGES_SUCCESS_ACTION = "send_push_message_success_action";
    public static final String SEND_PUSH_MESSAGES_FAIL_ACTION = "send_push_message_fail_action";
    public static final String LOAD_GROUP_DIALOG_SUCCESS_ACTION = "load_group_dialog_success_action";
    public static final String LOAD_GROUP_DIALOG_FAIL_ACTION = "load_group_dialog_fail_action";
    public static final String LEAVE_GROUP_DIALOG_SUCCESS_ACTION = "leave_group_dialog_success_action";
    public static final String LEAVE_GROUP_DIALOG_FAIL_ACTION = "leave_group_dialog_fail_action";
    public static final String ADD_FRIENDS_TO_GROUP_SUCCESS_ACTION = "add_friends_to_group_success_action";
    public static final String ADD_FRIENDS_TO_GROUP_FAIL_ACTION = "add_friends_to_group_fail_action";
    public static final String LOGIN_AND_JOIN_CHATS_SUCCESS_ACTION = "login_and_join_chats_sucess_action";
    public static final String LOGIN_AND_JOIN_CHATS_FAIL_ACTION = "login_and_join_chats_fail_action";
    public static final String USER_CHANGED_ACTION = "friend_status_changed_action";
    public static final String RE_LOGIN_IN_CHAT_SUCCESS_ACTION = "relogin_in_chat_success_action";
    public static final String RE_LOGIN_IN_CHAT_FAIL_ACTION = "relogin_in_chat_fail_action";
    public static final String RE_LOGIN_COMPLETE = "relogin_complete";
    public static final String UPDATE_GROUP_DIALOG_SUCCESS_ACTION = "update_group_dialog_success_action";
    public static final String UPDATE_GROUP_DIALOG_FAIL_ACTION = "update_group_dialog_fail_action";
    public static final String DELETE_DIALOG_SUCCESS_ACTION = "delete_dialog_success_action";
    public static final String DELETE_DIALOG_FAIL_ACTION = "delete_dialog_fail_action";

    public static final String EXTRA_ATTACH_FILE = "attach_file";
    public static final String EXTRA_CHAT_MESSAGE = "chat_message";
    public static final String EXTRA_ROOM_JID = "room_jid_id";
    public static final String EXTRA_IS_PRIVATE_MESSAGE = "is_private_message";
    public static final String EXTRA_IS_ROOMS = "is_rooms";
    public static final String EXTRA_IS_FOR_PRIVATE = "is_for_private";
    public static final String EXTRA_USER = "user";
    public static final String EXTRA_USERS = "users";
    public static final String EXTRA_ERROR = "error";
    public static final String EXTRA_ERROR_CODE = "error_code";
    public static final String EXTRA_FRIEND = "friend";
    public static final String EXTRA_FRIEND_ID = "friend_id";
    public static final String EXTRA_FRIENDS = "friends";
    public static final String EXTRA_FRIENDS_FACEBOOK = "friends_facebook";
    public static final String EXTRA_FRIENDS_CONTACTS = "friends_contacts";
    public static final String EXTRA_FILE = "file";
    public static final String EXTRA_QBFILE = "qb_file";
    public static final String EXTRA_SOCIAL_PROVIDER = "social_provider";
    public static final String EXTRA_ACCESS_TOKEN = "access_token";
    public static final String EXTRA_ACCESS_TOKEN_SECRET = "access_token_secret";
    public static final String EXTRA_EMAIL = "email";
    public static final String EXTRA_FILE_ID = "file_id";
    public static final String EXTRA_CONSTRAINT = "constraint";
    public static final String EXTRA_TOTAL_ENTRIES = "total_entries";
    public static final String EXTRA_STATUS = "status";
    public static final String EXTRA_CHATS_DIALOGS = "chats_dialogs";
    public static final String EXTRA_ROOM_NAME = "room_name";
    public static final String EXTRA_ROOM_JID_LIST = "room_jid_list";
    public static final String EXTRA_LAST_CHAT_MESSAGE = "last_chat_message";
    public static final String EXTRA_LAST_CHAT_MESSAGE_ID = "last_chat_message_id";
    public static final String EXTRA_OPPONENT_ID = "opponent_id";
    public static final String EXTRA_COUNT_UNREAD_CHATS_DIALOGS = "count_unread_chats_dialogs";
    public static final String EXTRA_DIALOG = "dialog";
    public static final String EXTRA_DIALOG_ID = "dialog_id";
    public static final String EXTRA_DIALOG_TYPE = "dialog_type";
    public static final String EXTRA_IS_OWN_MESSAGE = "is_own_message";
    public static final String EXTRA_GROUP_DIALOG = "group_dialog";
    public static final String EXTRA_GROUP_CHAT_ID = "group_chat_id";
    public static final String EXTRA_OPPONENT = "opponent_friend";
    public static final String EXTRA_DIALOG_MESSAGES = "dialog_messages";
    public static final String EXTRA_DIALOG_COUNT_UNREAD_MESSAGE = "dialog_count_unread_message";
    public static final String EXTRA_GROUP_NAME = "group_name";
    public static final String EXTRA_DATE_LAST_UPDATE_HISTORY = "last_update_history";
    public static final String EXTRA_MESSAGE_ID = "message_id";
    public static final String EXTRA_STATUS_MESSAGE = "status_message";
    public static final String EXTRA_USER_ID = "user_id";
    public static final String EXTRA_DATE_SENT = "date_sent";
    public static final String EXTRA_EMOJIS = "emojis";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_FILE_PATH = "file_path";
    public static final String EXTRA_FRIEND_ALERT_MESSAGE = "alert_message";
    public static final String EXTRA_IS_TYPING = "is_typing";
    public static final String EXTRA_CALL_ACTIVITY = "call_activity";
    public static final String EXTRA_PAGE = "load_elements";
    public static final String EXTRA_SKIP_ITEMS = "skip_items";

    public static final String UPDATE_DIALOG = "update_dialog";
    public static final String UPDATE_DIALOG_DETAILS = "update_dialog_details";
    public static final String TYPING_MESSAGE = "typing_message";
    public static final String GOT_CHAT_MESSAGE = "got_chat_message";
    public static final String GOT_CONTACT_REQUEST = "got_contact_request";
    public static final String LOGIN_AND_JOIN_CHAT_ACTION = "login_and_join_chats";
    public static final String DESTROY_CHAT = "destroy_chat_after_logout";
    public static final String FORCE_RELOGIN = "force_relogin";
    public static final String REFRESH_SESSION = "refresh_session";
    public static final String FRIEND_ALERT_SHOW = "friend_alert";
    public static final String AUTH_ACTION_TYPE = "authorize_type";
    public static final int AUTH_TYPE_REGISTRATION = 1;
    public static final int AUTH_TYPE_LOGIN = 2;

    /**
     * Loading strategy means what algorithm are we use.
     * By default we are loading in {@link com.quickblox.core.qb.commands.QBLoadDialogMessagesCommand}
     * all messages which are less then some date, but in some case we can need to load greater then values so we can
     * by setting this param true value
     *
     * LTE strategy - true
     * GTE strategy - false
     */
    public static final String EXTRA_LOAD_STRATEGY = "extra_load_strategy";
}