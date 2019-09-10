package cn.tencent.DiscuzMob.net;

/**
 * Created by cg on 2017/4/10.
 */

public class AppNetConfig {

    public static final String BASE_ADDRESS = "https://guanjia.comsenz-service.com/"; //【注意】更改BASE_ADDRESS的时候，要搜索全局替换， 因为JS中也有。
//    public static final String BASE_ADDRESS = "https://bbs.comsenz-service.com/"; //正式服务器
    public  String  ChangeUrl=BASE_ADDRESS;

    public String getChangeUrl() {
        return ChangeUrl;
    }

    public void setChangeUrl(String changeUrl) {
        ChangeUrl = changeUrl;
    }

    public static final String IMGURL = BASE_ADDRESS;
    public static final String IMGURL1 = BASE_ADDRESS;
    public static final String BASEURL = BASE_ADDRESS + "api/mobile/";
    public static final String LIVEBASEURL = "http://bbs.wsq.comsenz-service.com/newwsq/forum.php";
    public static final String LOGOURL = IMGURL1 + "static/image/common/logo.png";
    public static final String IMAGES = BASEURL + "?module=iwechat&data=json&version=5&mobiletype=Android";//轮播图
    public static final String ALLFORUM = BASEURL + "?module=forumindex&version=5&debug=1&mobiletype=Android";//全部版块
    public static final String HOTFORUM = BASEURL + "?module=hotforum&version=5&mobiletype=Android";//热门版块
    public static final String HOTPOST = BASEURL + "?module=hotthread&version=4&mobiletype=IOS&ios=1";//热帖
    public static final String QUESTION = BASEURL + "?module=secure&version=5&mobiletype=Android&type=";//获取验证码/问题
    public static final String LOGIN = BASEURL + "?module=login&version=5&&loginfield=auto&mobiletype=Android";//登录
    public static final String LOGIN1 = BASEURL + "?module=login&loginsubmit=yes&version=5&mapifrom=wx&charset=utf-8";//登录
    public static final String USERINFO1 = BASEURL + "?module=profile&version=4&loginsubmit=yes&mobiletype=Android";//用户信息
    public static final String USERINFO = BASEURL + "?module=profile&version=5&loginsubmit=yes&mobiletype=Android";//用户信息
    public static final String MYFRIEND = BASEURL + "?module=friend&checkavatar=1&version=4&uid=";//我的好友
    public static final String MYFAV = BASEURL + "?module=myfavforum&version=1&android=1&debug=1";//我收藏的版块
    public static final String MYFAVTHREAD = BASEURL + "?module=myfavthread&version=1&android=1&debug=1";//我收藏的帖子
    public static final String SYSTEM_MESSAGE = BASEURL + "?module=mypm&filter=announcepm&version=5&mobiletype=Android&page=";//系统消息
    public static final String PRIVATE_LETTER = BASEURL + "?module=mypm&touid=2&page=1&version=4&mobiletype=IOS&ios=1&debug=1";//我的消息————>私信
    public static final String MESSAGE_REPLIES = BASEURL + "?module=mynoticelist&mapifrom=android";//我的消息--->回复
    public static final String THEME = BASEURL + "?module=mythread&version=5&mobiletype=Android&type=thread";//我的主题
//    public static final String MYREPLISE = BASEURL + "?module=mythread&version=4&android=1&type=reply";//w我的回复
    public static final String MYREPLISE = BASEURL + "?module=mythread&version=4&android=1&type=reply";//w我的回复
    public static final String COLLECTION_BANKUAI = BASEURL + "?module=favorite&version=5&type=forum";//点击收藏板块
    public static final String HISTHME = BASEURL + "?module=userthread&version=5&android=1&debug=1&mobiletype=Android";//他的回复
    public static final String CONVERSATION = BASEURL + "?module=mypm&subop=view&version=5&mobiletype=Android&checkavatar=1&Android=1&smiley=no&convimg=1";//会话详情
    public static final String UNCOLLECTION = BASEURL + "?module=favorite&version=5&op=delete&type=forum&mobiletype=Android&id=";//点击取消收藏
    public static final String REGISTER = BASEURL + "?version=5&regsubmit=yes&module=register&";//普通注册
    public static final String GETREGISTER = BASEURL + "?version=5&module=check";//普通注册
    public static final String REGISTERBANGDING = BASEURL + "?version=5&module=weiqqregister";
    public static final String COMMENT = BASEURL + "?module=sendreply&version=5&replysubmit=yes&mobiletype=Android&mapifrom=android";//帖子详情跟帖
    public static final String SEARCH = BASEURL + "?module=search&version=5&android=1";//搜索
    public static final String JURISDICTION = BASEURL + "?module=newthread&submodule=checkpost&debug=1&version=1";//检查权限
    public static final String ACTIVITYTHRADF = BASEURL + "?module=newthread&version=5&android=1&topicsubmit=yes&fid=";//活动帖
    public static final String VOTETHREAD = BASEURL + "?module=newthread&version=5&android=1&topicsubmit=yes&fid=";//投票贴
    public static final String THREAD = BASEURL + "?module=newthread&version=5&android=1&debug=1&topicsubmit=yes&fid=";//普通帖
    public static final String QUOTE = BASEURL + "?module=sendreply&version=3";//回复引用
    public static final String PRAISETHREAD = BASEURL + "?module=recommend&version=4";//帖子点赞
    public static final String COLLECTTHEAD = BASEURL + "?module=favorite&version=5&type=thread&id=";//收藏帖子http://10.0.6.58/api/mobile/version=4&android=1&debug=1&module=favthread&id=46&favoritesubmit=true
    public static final String UNCOLLECTTHEAD = BASEURL + "?module=favorite&version=5&op=delete&mobiletype=Android&id=";//取消收藏帖子
    public static final String JOINACTIVITY = BASEURL + "?module=activityapplies&version=5&activitysubmit=true";//参加活动
    public static final String DELETELETTER = BASEURL + "?module=delpm&version=5&deletesubmit=1";//删除我的私信
    public static final String DELETEMESSAGE = BASEURL + "?version=5&module=delpmview&deletesubmit=1";//删除聊天记录
    public static final String LIVETHREAD = BASEURL + "?module=livethread&version=5&mobiletype=Android";//直播列表
    public static final String LIVETOADY = BASEURL + "?module=recommon_livethread&version=5&mobiletype=Android";//直播推荐
    public static final String LIVEDETIAL = BASEURL + "?version=5&ppp=10&module=viewthread&ordertype=1&submodule=checkpost&mobiletype=Android";//直播
    public static final String INTERACTION = BASEURL + "?module=threadreply&version=5&mobiletype=Android&ppp=10";
    public static final String RECOMMEND = BASEURL + "?module=recommon_thread&version=5&mobiletype=Android&android=1";//推荐
    public static final String ESSENCE = BASEURL + "?module=forumguide&view=digest&version=5&type=digest&mobiletype=Android&page=";//精华帖
    public static final String NEW = BASEURL + "?module=forumguide&view=newthread&version=5&type=digest&mobiletype=Android&page=";//最新帖
    public static final String MYTHRED = BASEURL + "?module=mynotelist&view=mypost&type=post&version=5&mobiletype=Android";//我的帖子
    public static final String MYCOMMON = BASEURL + "?module=mynotelist&view=mypost&type=pcomment&version=3&mobiletype=Android";//点评
    public static final String NEWS = BASEURL + "?module=notes&version=5&mobiletype=Android";//是否有新的消息
    public static final String MYACTVITY = BASEURL + "?module=mynotelist&view=mypost&type=activity&version=3&mobiletype=Android";//我的活动
    public static final String MYREWARD = BASEURL + "?module=mynotelist&view=mypost&type=reward&version=3&mobiletype=Android";//悬赏
    public static final String GOODS = BASEURL + "?module=mynotelist&view=mypost&type=goods&version=3&mobiletype=Android";//商品
    public static final String MENTIONME = BASEURL + "?module=mynotelist&view=mypost&type=at&version=3&mobiletype=Android";//提到我的
    public static final String MYCALL = BASEURL + "?module=mynotelist&view=interactive&type=post&version=3&mobiletype=Android";//打招呼
    public static final String myFriends = BASEURL + "?module=mynotelist&view=interactive&type=friend&version=3&mobiletype=Android";//好友
    public static final String LEAVINGMESSAGE = BASEURL + "?module=mynotelist&view=interactive&type=wall&version=3&mobiletype=Android";//留言
    public static final String MYCOMMENT = BASEURL + "?module=mynotelist&view=interactive&type=comment&version=3&mobiletype=Android";//评论
    public static final String QUITE = BASEURL + "?module=mynotelist&view=interactive&type=click&version=3&mobiletype=Android";//挺你
    public static final String SHARE = BASEURL + "?module=mynotelist&view=interactive&type=sharenotice&version=3&mobiletype=Android";//分享
    public static final String SYSTEM = BASEURL + "?module=mynotelist&view=system&version=3&mobiletype=Android";//系统提醒
    public static final String ADMINISTRATION = BASEURL + "?module=mynotelist&view=manage&version=3&mobiletype=Android";//管理工作
    public static final String JOINMANAGER = BASEURL + "?module=forummisc&action=activityapplylist&version=5&mobiletype=Android";//报名管理
    public static final String activity_management = BASEURL + "?module=forummisc&action=activityapplylist&applylistsubmit=yes&version=5&tid=";
}