package com.chen.imkit;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.chen.imkit.cache.DataCacheManager;
import com.chen.imkit.cache.DemoCache;
import com.chen.imkit.cache.FriendDataCache;
import com.chen.imkit.cache.NimUserInfoCache;
import com.chen.imkit.cache.TeamDataCache;
import com.chen.imkit.contact.ContactEventListener;
import com.chen.imkit.contact.ContactProvider;
import com.chen.imkit.contact.LocationProvider;
import com.chen.imkit.session.SessionEventListener;
import com.chen.imkit.session.viewholder.MsgViewHolderBase;
import com.chen.imkit.session.viewholder.MsgViewHolderFactory;
import com.chen.imkit.userinfo.UserInfoHelper;
import com.chen.imkit.utils.LogUtil;
import com.chen.imkit.utils.ScreenUtil;
import com.chen.imkit.utils.SystemUtil;
import com.chen.imkit.utils.storage.StorageType;
import com.chen.imkit.utils.storage.StorageUtil;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * UIKit能力输出类。
 */
public final class NimUIKit {

    // context
    private static Context context;

    // 自己的用户帐号
    private static String account;

    // 用户信息提供者
    private static UserInfoProvider userInfoProvider;

    // 通讯录信息提供者
    private static ContactProvider contactProvider;

    // 地理位置信息提供者
    private static LocationProvider locationProvider;

    // 图片加载、缓存与管理组件
    private static ImageLoaderKit imageLoaderKit;

    // 会话窗口消息列表一些点击事件的响应处理函数
    private static SessionEventListener sessionListener;

    // 通讯录列表一些点击事件的响应处理函数
    private static ContactEventListener contactEventListener;

    /**
     * 初始化缓存信息，须传入context以及用户信息提供者
     */
    public static void initDataChe() {
//        NimUIKit.context = context.getApplicationContext();
//        NimUIKit.userInfoProvider = userInfoProvider;
//        NimUIKit.contactProvider = contactProvider;
//        NimUIKit.imageLoaderKit = new ImageLoaderKit(context, null);

        // init data cache
        LoginSyncDataStatusObserver.getInstance().registerLoginSyncDataStatus(true);  // 监听登录同步数据完成通知
        DataCacheManager.observeSDKDataChanged(true);
        if (!TextUtils.isEmpty(getAccount())) {
            DataCacheManager.buildDataCache(); // build data cache on auto login
        }

        // init tools
        StorageUtil.init(context, null);
        ScreenUtil.init(context);
//        StickerManager.getInstance().init();

        // init log
        String path = StorageUtil.getDirectoryByDirType(StorageType.TYPE_LOG);
        LogUtil.init(path, Log.DEBUG);
    }

    /**
     * 释放缓存，一般在注销时调用
     */
    public static void clearCache() {
        DataCacheManager.clearDataCache();
    }

//    /**
//     * 打开一个聊天窗口，开始聊天
//     *
//     * @param context       上下文
//     * @param id            聊天对象ID（用户帐号account或者群组ID）
//     * @param sessionType   会话类型
//     * @param customization 定制化信息。针对不同的聊天对象，可提供不同的定制化。
//     */
//    public static void startChatting(Context context, String id, SessionTypeEnum sessionType, SessionCustomization customization) {
//        if (sessionType == SessionTypeEnum.P2P) {
//            P2PMessageActivity.start(context, id, customization);
//        } else if (sessionType == SessionTypeEnum.Team) {
//            TeamMessageActivity.start(context, id, customization, null);
//        }
//    }

//    /**
//     * 打开一个聊天窗口（用于从聊天信息中创建群聊时，打开群聊）
//     *
//     * @param context       上下文
//     * @param id            聊天对象ID（用户帐号account或者群组ID）
//     * @param sessionType   会话类型
//     * @param customization 定制化信息。针对不同的聊天对象，可提供不同的定制化。
//     * @param backToClass   返回的指定页面
//     */
//    public static void startChatting(Context context, String id, SessionTypeEnum sessionType, SessionCustomization customization,
//                                     Class<? extends Activity> backToClass) {
//        if (sessionType == SessionTypeEnum.Team) {
//            TeamMessageActivity.start(context, id, customization, backToClass);
//        }
//    }

//    /**
//     * 打开联系人选择器
//     *
//     * @param context     上下文（Activity）
//     * @param option      联系人选择器可选配置项
//     * @param requestCode startActivityForResult使用的请求码
//     */
//    public static void startContactSelect(Context context, ContactSelectActivity.Option option, int requestCode) {
//        ContactSelectActivity.startActivityForResult(context, option, requestCode);
//    }

//    /**
//     * 打开讨论组或高级群资料页
//     *
//     * @param context 上下文
//     * @param teamId  群id
//     */
//    public static void startTeamInfo(Context context, String teamId) {
//        Team team = TeamDataCache.getInstance().getTeamById(teamId);
//        if (team == null) {
//            return;
//        }
//        if (team.getType() == TeamTypeEnum.Advanced) {
//            AdvancedTeamInfoActivity.start(context, teamId); // 启动固定群资料页
//        } else if (team.getType() == TeamTypeEnum.Normal) {
//            NormalTeamInfoActivity.start(context, teamId); // 启动讨论组资料页
//        }
//
//    }

    public static Context getContext() {
        return context;
    }

    public static String getAccount() {
        return account;
    }

    public static UserInfoProvider getUserInfoProvider() {
        return userInfoProvider;
    }

    public static ContactProvider getContactProvider() {
        return contactProvider;
    }

    public static LocationProvider getLocationProvider() {
        return locationProvider;
    }

    public static ImageLoaderKit getImageLoaderKit() {
        return imageLoaderKit;
    }

    public static void setLocationProvider(LocationProvider locationProvider) {
        NimUIKit.locationProvider = locationProvider;
    }
//
//    /**
//     * 根据消息附件类型注册对应的消息项展示ViewHolder
//     *
//     * @param attach     附件类型
//     * @param viewHolder 消息ViewHolder
//     */
//    public static void registerMsgItemViewHolder(Class<? extends MsgAttachment> attach, Class<? extends MsgViewHolderBase> viewHolder) {
//        MsgViewHolderFactory.register(attach, viewHolder);
//    }
//

    /**
     * 注册Tip类型消息项展示ViewHolder
     *
     * @param viewHolder Tip消息ViewHolder
     */
    public static void registerTipMsgViewHolder(Class<? extends MsgViewHolderBase> viewHolder) {
        MsgViewHolderFactory.registerTipMsgViewHolder(viewHolder);
    }

    /**
     * 设置当前登录用户的帐号
     *
     * @param account 帐号
     */
    public static void setAccount(String account) {
        NimUIKit.account = account;
    }

    /**
     * 获取聊天界面事件监听器
     *
     * @return
     */
    public static SessionEventListener getSessionListener() {
        return sessionListener;
    }

    /**
     * 设置聊天界面的事件监听器
     *
     * @param sessionListener
     */
    public static void setSessionListener(SessionEventListener sessionListener) {
        NimUIKit.sessionListener = sessionListener;
    }

    /**
     * 获取通讯录列表的事件监听器
     *
     * @return
     */
    public static ContactEventListener getContactEventListener() {
        return contactEventListener;
    }

    /**
     * 设置通讯录列表的事件监听器
     *
     * @param contactEventListener
     */
    public static void setContactEventListener(ContactEventListener contactEventListener) {
        NimUIKit.contactEventListener = contactEventListener;
    }

    /**
     * 当用户资料发生改动时，请调用此接口，通知更新UI
     *
     * @param accounts 有用户信息改动的帐号列表
     */
    public static void notifyUserInfoChanged(List<String> accounts) {
        UserInfoHelper.notifyChanged(accounts);
    }


    /**
     * 在Application onCreate时初始化
     */
    public static void initIMKit(Context context) {
        NimUIKit.context = context;
        NimUIKit.context = context.getApplicationContext();
        initUserInfoProvider();
        initContactProvider();
        NimUIKit.imageLoaderKit = new ImageLoaderKit(context, null);
        NIMClient.init(context, getLoginInfo(), getOptions());
        if (inMainProcess()) {
            NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
            initDataChe();
        }
    }


    public static void initUserInfoProvider() {
        userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                UserInfo user = NimUserInfoCache.getInstance().getUserInfo(account);
                if (user == null) {
                    NimUserInfoCache.getInstance().getUserInfoFromRemote(account, null);
                }

                return user;
            }

            @Override
            public int getDefaultIconResId() {
                return R.drawable.avatar_def;
            }

            @Override
            public Bitmap getTeamIcon(String teamId) {
                Drawable drawable = context.getResources().getDrawable(R.drawable.nim_avatar_group);
                if (drawable instanceof BitmapDrawable) {
                    return ((BitmapDrawable) drawable).getBitmap();
                }

                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                /**
                 * 注意：这里最好从缓存里拿，如果读取本地头像可能导致UI进程阻塞，导致通知栏提醒延时弹出。
                 */
                UserInfo user = getUserInfo(account);
                return (user != null) ? ImageLoaderKit.getNotificationBitmapFromCache(user) : null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId, SessionTypeEnum sessionType) {
                String nick = null;
                if (sessionType == SessionTypeEnum.P2P) {
                    nick = NimUserInfoCache.getInstance().getAlias(account);
                } else if (sessionType == SessionTypeEnum.Team) {
                    nick = TeamDataCache.getInstance().getTeamNick(sessionId, account);
                    if (TextUtils.isEmpty(nick)) {
                        nick = NimUserInfoCache.getInstance().getAlias(account);
                    }
                }
                // 返回null，交给sdk处理。如果对方有设置nick，sdk会显示nick
                if (TextUtils.isEmpty(nick)) {
                    return null;
                }

                return nick;
            }
        };
    }

    public static void initContactProvider() {
        contactProvider = new ContactProvider() {
            @Override
            public List<UserInfoProvider.UserInfo> getUserInfoOfMyFriends() {
                List<NimUserInfo> nimUsers = NimUserInfoCache.getInstance().getAllUsersOfMyFriend();
                List<UserInfoProvider.UserInfo> users = new ArrayList<>(nimUsers.size());
                if (!nimUsers.isEmpty()) {
                    users.addAll(nimUsers);
                }

                return users;
            }

            @Override
            public int getMyFriendsCount() {
                return FriendDataCache.getInstance().getMyFriendCounts();
            }

            @Override
            public String getUserDisplayName(String account) {
                return NimUserInfoCache.getInstance().getUserDisplayName(account);
            }
        };
    }


//    public static void initUIKit() {
//        // 初始化，需要传入用户信息提供者
//        NimUIKit.init(context, userInfoProvider, contactProvider);

    //TODO 根据需求配置
    // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
//        NimUIKit.setLocationProvider(new NimDemoLocationProvider());
    // 会话窗口的定制初始化。
//        SessionHelper.init();
    // 通讯录列表定制初始化
//        ContactHelper.init();
//    }
    private static LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();
//        Toast.makeText(getApplicationContext(),"account:"+account+";token = "+token,Toast.LENGTH_LONG).show();
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private static SDKOptions getOptions() {

        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给SDK完成，需要添加以下配置。
        StatusBarNotificationConfig config = UserPreferences.getStatusConfig();
        if (config == null) {
            config = new StatusBarNotificationConfig();
        }
        // 点击通知需要跳转到的界面
        //TODO 记得修改跳转相关配置
//        config.notificationEntrance = WelcomeActivity.class;
        config.notificationSmallIconId = R.drawable.ic_stat_notify_msg;

        // 通知铃声的uri字符串
//        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;
        DemoCache.setNotificationConfig(config);
        UserPreferences.setStatusConfig(config);

        // 配置保存图片，文件，log等数据的目录
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + context.getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置数据库加密秘钥
        options.databaseEncryptKey = "NETEASE";

        // 配置是否需要预下载附件缩略图
        options.preloadAttach = true;

        // 配置附件缩略图的尺寸大小，
//        options.thumbnailSize = MsgViewHolderThumbBase.getImageMaxEdge();

        // 用户信息提供者


        options.userInfoProvider = userInfoProvider;

        return options;
    }

    public static boolean inMainProcess() {
        String packageName = context.getPackageName();
        String processName = SystemUtil.getProcessName(context);
        return packageName.equals(processName);
    }

}
