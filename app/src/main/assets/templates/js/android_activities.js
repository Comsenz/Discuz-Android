var HOST="http://wsq.demo.comsenz-service.com/";
//var HOST="http://10.0.6.58/";
var	isLoadReplyOver=false;//是否所有评论已加载完毕
var	is2GOr3GLoadImgs=true;//2/3G网络是否加载图片

$(document).ready(function(){
	$("div.box03").bind("click",function(){onThreadOptionsClick();});
	$(".activity_content .P_share div a:eq(0)").bind("click",function(){praise();});
//	$(".activity_content .P_share div a:eq(1)").bind("click",function(){report();});
	$(".activity_content .P_share div a:eq(1)").click(function(){report();});
	$(".activity_content .P_share div a:eq(2)").bind("click",function(){share();});
	$(".reply_botton_j a").bind("click",function(){loadMore();});
	$(".sendoperate .senbtto button").bind("click",function(){submit();});
	//test();
});

/*@hidden*/function setThread(JSON){
     var forum = "discuz模板";
     var subject = JSON.Variables.thread.subject;
     var typeid = JSON.Variables.thread.typeid;
   var typeid = JSON.Variables.thread.typeid;
                if(!textIsNull(typeid)){
                  if(!textIsNull(JSON.Variables.forum.threadtypes)){
                   var types = JSON.Variables.forum.threadtypes.types;
                   var type = types[typeid];
                                   if(!textIsNull(type)){
                                                     $(".header_title").html("<span class='title_type'> ["+type+"] </span>"+subject);
                                                     }else{
                                                      $(".header_title").html(subject);
                                                     }
                  }else{
                  $(".header_title").html(subject);
                  }

                }else{
                  $(".header_title").html(subject);
                }
//     $(".header_title").html("<span class='title_type'> ["+type+"] </span>"+subject+"    <span>"+JSON.Variables.special_activity.class+"</span>");
     $(".forumName").html(JSON.Variables.thread.forumnames);
	/*$(".p_header .header_text").html(JSON.Variables.thread.subject+"    <span>"+JSON.Variables.special_activity.class+"</span>");*/
//	$(".p_header .P_figure .box01 img").attr(is2GOr3GLoadImgs?JSON.Variables.thread.avatar:null);
	$(".p_header .P_figure .box01 img").attr(is2GOr3GLoadImgs?"src":"url",is2GOr3GLoadImgs?JSON.Variables.thread.avatar:"images/noavatar_small.png");
	$(".p_header .P_figure .box01 label:eq(0)").html(JSON.Variables.thread.author);
	$(".p_header .P_figure .box01 .p_date").html(JSON.Variables.postlist[0].dateline);
	$(".p_header .P_figure .box01").click(function(){userInfo(JSON.Variables.thread.authorid)});
	$(".p_header .P_figure .box01").click(function(){userInfo(JSON.Variables.thread.authorid)});
	var hasZan = JSON.Variables.thread.recommend;
        if(hasZan == 1){
            $(".p_zanBtn").attr("src","images/zanSmall_icon1.png");
        }else{
            $(".p_zanBtn").attr("src","images/praise.png");
        }
//	$(".activity_time_tip").html("报名进行中，还剩"+eval(JSON.Variables.special_activity.number-JSON.Variables.special_activity.allapplynum)+"个名额，"
//	+JSON.Variables.special_activity.expiration+"截止");

    var expiration =JSON.Variables.special_activity.expiration;
    if(JSON.Variables.special_activity.number-JSON.Variables.special_activity.allapplynum >0){
    $(".p_vote-text").html("报名进行中，还剩"+eval(JSON.Variables.special_activity.number-JSON.Variables.special_activity.allapplynum)+"个名额，"
                               	);
    }else{
    $(".p_vote-text").html("报名进行中,");
    }
    if(expiration != 0){
    $(".p_vote-text").append(expiration+"截止");
    }else{
     $(".p_vote-text").append("长期有效");
    }
	var sex = JSON.Variables.special_activity.gender;
	var hadPeopleNum = JSON.Variables.special_activity.allapplynum;
	var uid = JSON.Variables.member_uid;
	var authorid = JSON.Variables.thread.authorid;
	var hasPeopleNum = JSON.Variables.special_activity.number-JSON.Variables.special_activity.allapplynum;
    if(uid==authorid){
     $(".manageractivity").show();
    }else{
     $(".manageractivity").hide();
    }
    $(".manageractivity").bind("click",function(){share();});
	$(".activity_content .timeplace .clock span").html(JSON.Variables.special_activity.starttimefrom);
	var starttimeto = JSON.Variables.special_activity.starttimeto;
	if(starttimeto == 0){
	$(".activity_content .timeplace .over span").attr("hidden")
	}else{
	$(".activity_content .timeplace .over span").html(JSON.Variables.special_activity.starttimeto);
	}

	$(".activity_content .timeplace .place span").html(JSON.Variables.special_activity.place);
	$(".activity_content .timeplace .sex span").html(sex==1?"男":(sex==2?"女":"不限"));
	$(".activity_content .timeplace .cost span").html(JSON.Variables.special_activity.cost+" 元");
	$(".activity_content .timeplace .hadPeople span i").html(hadPeopleNum);
//	$(".activity_content .timeplace .hasPeople span").html(hasPeopleNum);
	$(".activity_content .P_share div span:eq(0)").html(JSON.Variables.thread.recommend_add);
	$(".activity_content .P_share div span:eq(1)").html(JSON.Variables.thread.replies);
	$(".p_header .header_bottom .view").html(JSON.Variables.thread.views);
	$(".p_header .header_bottom .reply").html(JSON.Variables.thread.replies);
	$(".activity_content .P_share div a:eq(1)").click(function(){discussUser(null);});
	if(!textIsNull(JSON.Variables.postlist)){
		$("div.thread_details").attr("id","rednet_anchor_id_"+JSON.Variables.postlist[0].pid);
		$(".contentPictext img").css({"width":"0px","height":"0px"});
		$(".contentPictext").html(!textIsNull(JSON.Variables.postlist)?JSON.Variables.postlist[0].message:"");
//		$(".contentPictext img").attr("src",is2GOr3GLoadImgs?$(this).attr("src"):$(this).parents().hide())
		$(".contentPictext img")
		.attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
		.css({"width":"auto","height":"auto"})
		.click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null);
        if(!textIsNull(JSON.Variables.special_activity)){
			$(".thread_details .attachlist img").css({"width":"0px","height":"0px"});
			$(".thread_details .attachlist").removeAttr("hidden");
			var special_activity=JSON.Variables.special_activity;
			if(!textIsNull(special_activity.attachurl)){
            					$(".thread_details .attachlist ul").append("<li><img aid='"+special_activity.aid+"' style='max-height:80%;' src='"+(HOST+special_activity.attachurl)+"'/></li>");
            				}
		}
		$(".thread_details .attachlist img").click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
//        			.attr("src",is2GOr3GLoadImgs?$(this).attr("src"):$(this).parent().hide())
                    .attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
        			.css({"width":"auto","height":"auto"});
	}
	if(JSON.Variables.thread.replies == 0){
	    onLoadOver();
	}else{
        onLoadReply(JSON,false);
	    enableLoadMore(false);
	}
}

/**
 *加载用户评论
 *isAppend true json数据从0开始 false从1开始因为0是用户发表的帖子内容
 */
function onLoadReply(JSON,isAppend){
	var postlist=JSON.Variables.postlist;
	var ul=$("ul.thread_reply");
	if(!textIsNull(postlist)){
		for(var i=isAppend?0:1;i<postlist.length;i++){
			var postlistitem=postlist[i];
			var anchor=document.getElementById("rednet_anchor_id_"+postlistitem.pid);
			if(anchor!=null){
				continue;//重复数据不展示
			}
			var li=ul.find("li:first").clone();
			li.attr("pid",postlistitem.pid).attr("authorid",postlistitem.authorid).attr("id","rednet_anchor_id_"+postlistitem.pid);//设置#锚点
			li.find("div.ageImg img").attr(is2GOr3GLoadImgs?"src":"url",is2GOr3GLoadImgs?postlistitem.avatar:"images/noavatar_small.png").click(function(){userInfo($(this).parents("li").attr("authorid"));});
			li.find("div.messContent p:eq(0) a").html(postlistitem.author).click(function(){userInfo($(this).parents("li").attr("authorid"));});
			var position =	postlistitem.position;
            var cache_custominfo_postno = JSON.Variables.cache_custominfo_postno;
			var louCeng = "";
            var lou =cache_custominfo_postno[position];
            if(!textIsNull(lou)){
             louCeng = lou;
            }else{
             louCeng = position+"楼";
            }
            li.find("div.messContent p:eq(0) span").html(louCeng);
//			li.find("div.messContent p:eq(0) span").html("沙发");
			li.find("div.messContent p:eq(1) img").css({"width":"0px","height":"0px"});
			li.find("div.messContent p:eq(1)").html(postlistitem.message);
			li.find("div.messContent p:eq(1) img")
//			     .attr("src",is2GOr3GLoadImgs?$(this).attr("src"):$(this).parent().hide())
                .attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
				.click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
				.css({"width":"auto","height":"auto"});
			li.find("div.P_share div.naTime_02 label.time").html(postlistitem.dateline);
			li.find("div.P_share div .a").click(function(){discussUser($(this).parents("li").attr("pid"));});
            li.find("div.P_share div .b").click(function(){reportComment($(this).parents("li").attr("pid"));});

             var commendStr = "http://wsq.demo.comsenz-service.com/";
                        var attach = postlistitem.attachments;
                        if(!textIsNull(attach)){
                        li.find(".attachlist img").css({"width":"0px","height":"0px"});
                        li.find(".attachlist").removeAttr("hidden");
                            for(var key in attach){
                                 var commendImgUrl = commendStr + attach[key].url;
                                  commendImgUrl = commendImgUrl + attach[key].attachment;
                                  var aid = attach[key].aid;
//                                   $(".attachlist ul").append("<li><img aid='"+aid+"' style='max-height:80%;' url='"+commendImgUrl+"'/></li>");
                                   li.find(".attachlist ul").append("<li><img aid='"+aid+"' src='"+commendImgUrl+"'/></li>");
                                }
                               li.find(".attachlist img")
//                                            .attr("src",is2GOr3GLoadImgs?$(this).attr("src"):$(this).parent().hide())
                                            .attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
                               				.click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
                               				.css({"width":"auto","height":"auto"});
                        }
			//附件
			if(!textIsNull(postlistitem.attchlist)){
				li.find(".attachlist img").css({"width":"0px","height":"0px"});
				li.find(".attachlist").removeAttr("hidden");
				var attachlist=postlistitem.attchlist;
				for(var j=0;j<attachlist.length;j++){
					if(!textIsNull(attachlist[j].attachurl)){
						li.find(".attachlist ul").append("<li><img aid='"+attachlist[j].aid+"' src='"+attachlist[j].attachurl+"'/></li>");
					}
				}
				li.find(".attachlist img")
//				.attr("src",is2GOr3GLoadImgs?$(this).attr("src"):$(this).hide())
                .attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
				.click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
				.css({"width":"auto","height":"auto"});
			}
			li.removeAttr("hidden");
			ul.append(li);
		}
	}
//	if(JSON.Variables.thread.replies==0){
//		$(".p_vote").attr("hidden");
//	}else{
//		$(".p_vote").removeAttr("hidden");
//	}

//	if(!isLoadReplyOver){
//        alert(ul.find("li").size());
//        alert(JSON.Variables.thread.replies);
		if(ul.find("li").size() >= JSON.Variables.thread.replies ||ul.find("li").size() == 1){
			 onLoadOver();
		}else{
			enableLoadMore(false);
		}
//	}
}

/**
 *刷新
 *@param joined 当前登录用户是否已参加该活动
 *@param is2GOr3GLoadImgs 2G3G网络是否加载图片(帖子、回复内容图及头像)
 */
function onRefresh(JSON,joined,is2GOr3GLoadImgs){
	this.is2GOr3GLoadImgs=is2GOr3GLoadImgs;
	this.isLoadReplyOver=false;
    setThread(JSON);
	onUpdatePartyState(joined);
    onLoadReply(JSON,false);
}

/**支持(帖子)成功更新支持者数量*/
function onPraiseSuccess(){
	var el=$(".activity_content .P_share div span:eq(0)");
	el.html(parseInt(el.html())+1);
}

/**
 *回复用户评论或帖子成功 客户端回调webview.loadUrl("javascript:onDiscussSuccess(JSON,AttachsJSON,joined,is2GOr3GLoadImgs,pid);")
 *@param JSON 页面信息包含用户所有评论 如果为空则页面不会自动跳到用户回复的地方
 *@param AttachsJSON	
 *@param joined	
 *@param is2GOr3GLoadImgs
 *@param pid 回复成功后返回的pid 用字符串形式传入
 */
function onDiscussSuccess(JSON,joined,is2GOr3GLoadImgs,pid){
	if (!textIsNull(JSON)){
        onRefresh(JSON,joined,is2GOr3GLoadImgs);
		if(!textIsNull(pid)){
			window.location.href="#rednet_anchor_id_"+pid;
		}
	}
}

/**更新(当前登录用户)活动参加状态按钮*/
function onUpdatePartyState(joined){
	$(".sendoperate .senbtto button").attr("joined",joined?"true":"false").html(joined?"取消报名":"我要参加");
}

/**显示隐藏"我要参加"按钮*/
function setSubmitButtonVisible(visible){
	if(visible){
		$(".sendoperate").removeAttr("hidden");
	}else{
		$(".sendoperate").attr("hidden","hidden");
	}
}

function onLoadOver(){
	isLoadReplyOver=true;
	enableLoadMore(true);
}

//回复某评论
/*@hidden*/function discussUser(pid){
	Rednet.onDiscussUser(pid);
}

//点击用户头像或者用户名进入到其个人空间
/*@hidden*/function userInfo(authorid){
	Rednet.onUserInfo(authorid);
}

//查看大图
/*@hidden*/function threadThumbsClick(url){
	Rednet.onThreadThumbsClicked(url);
}

//帖子其他操作事件
/*@hidden*/function onThreadOptionsClick(){
	Rednet.onThreadOptionsClick();
}

/*@hidden*/function enableLoadMore(enable){
	if(enable){
		$("p.reply_botton_j").removeAttr("hidden");
	}else{
		$("p.reply_botton_j").attr("hidden","hidden");
	}
}

/*@hidden*/function enableAllComment(){
//    $(".p_vote").attr({style:"display:none"});
}


//帖子分享
/*@hidden*/function share(){
	Rednet.onShare();
}

//帖子举报 回调android客户端中 @JavascriptInterface onReport()方法
/*@hidden*/function report(){
	Rednet.onReport();
}

//举报用户评论
/*@hidden*/function reportComment(pid){
	Rednet.onReportComment(pid);
}

//支持(帖子)
/*@hidden*/function praise(){
	Rednet.onPraise();
}

/*@hidden*/function loadMore(){
	Rednet.onLoadMore();
}

/*@hidden*/function submit(){
	Rednet.onSubmit();
}

/*@hidden*/
function format(views){
	if(views>9999){
		return Number(views/10000).toFixed(1)+"w";
	}else{
		return views;
	}
}

/*@hidden*/function textIsNull(strings){
	return strings===undefined||strings.length==0;
}

function test(){
	onRefresh(TEST_JSON2,true,true);
}

var TEST_JSON2={
    "Charset": "UTF-8",
    "Version": "4",
    "Variables": {
        "fid": "42",
        "member_uid": "55",
        "forum": {
            "password": "0"
        },
        "formhash": "83d51cac",
        "saltkey": "UIEeGi6z",
        "setting_rewritestatus": "",
        "readaccess": "200",
        "member_username": "fuwei111",
        "ppp": "10",
        "member_avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
        "special_activity": {
            "uid": "55",
            "userfield": "",
            "aid": "0",
            "starttimefrom": "2015-7-23 11:34",
            "attachurl": "",
            "button": "join",
            "starttimeto": "2015-8-23 11:34",
            "gender": "0",
            "closed": "0",
            "tid": "158",
            "credit": "0",
            "place": "北戴河",
            "applynumber": "0",
            "basefield": [],
            "status": "join",
            "expiration": "0",
            "class": "出外郊游",
            "number": "6",
            "is_ex": "0",
            "joinfield": [],
            "cost": "0",
            "extfield": "",
            "applied": "0",
            "allapplynum": "0",
            "isverified": "0",
            "thumb": "",
            "ufield": {
                "userfield": null,
                "extfield": []
            }
        },
        "allowperm": {
            "uploadhash": "6cf911c08819d4377925e2c30a99accb",
            "allowupload": {
                "jpg": "-1",
                "zip": "-1",
                "jpeg": "-1",
                "png": "-1",
                "pdf": "-1",
                "mp3": "-1",
                "rar": "-1",
                "txt": "-1",
                "gif": "-1"
            },
            "attachremain": {
                "count": "-1",
                "size": "-1"
            },
            "allowreply": "1",
            "allowpost": "1"
        },
        "setting_rewriterule": null,
        "forum_threadpay": "",
        "cache_custominfo_postno": [
            "<sup>#</sup>",
            "楼主",
            "沙发",
            "板凳",
            "地板"
        ],
        "commentcount": [],
        "allowpostcomment": [
            "1",
            "2"
        ],
        "ismoderator": "1",
        "postlist": [
            {
                "position": "1",
                "status": "8",
                "pid": "305",
                "number": "1",
                "dateline": "2015-7-23 11:34:54",
                "attachment": "0",
                "adminid": "1",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "message": "你自己在家自己",
                "anonymous": "0",
                "author": "fuwei111",
                "memberstatus": "0",
                "username": "fuwei111",
                "authorid": "55",
                "dbdateline": "1437622494",
                "groupiconid": "admin",
                "replycredit": "0",
                "tid": "158",
                "groupid": "1",
                "first": "1",
                "attachlist": []
            },
            {
                "position": "2",
                "status": "1288",
                "pid": "743",
                "number": "2",
                "dateline": "刚刚",
                "attachment": "0",
                "adminid": "1",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "message": "111",
                "anonymous": "0",
                "author": "fuwei111",
                "memberstatus": "0",
                "username": "fuwei111",
                "authorid": "55",
                "dbdateline": "1441784258",
                "groupiconid": "admin",
                "replycredit": "0",
                "tid": "158",
                "groupid": "1",
                "first": "0",
                "attachlist": []
            }
        ],
        "thread": {
            "readperm": "0",
            "relay": "0",
            "recommendlevel": "0",
            "threadtableid": "0",
            "dateline": "2015-7-23 11:34",
            "threadtable": "forum_thread",
            "highlight": "0",
            "bgcolor": "",
            "allreplies": "0",
            "cover": "0",
            "moderated": "0",
            "authorid": "55",
            "displayorder": "0",
            "tid": "158",
            "favtimes": "0",
            "posttable": "forum_post",
            "heatlevel": "0",
            "starttime": "1437622494",
            "recommends": "0",
            "status": "1024",
            "lastpost": "1437622494",
            "ordertype": "0",
            "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
            "is_archived": "",
            "price": "0",
            "replies": "120",
            "remaintime": "",
            "replycredit": "0",
            "fid": "42",
            "relatebytag": "0",
            "subject": "北戴河睡觉",
            "digest": "0",
            "recommend": "0",
            "short_subject": "北戴河睡觉",
            "author": "fuwei111",
            "rate": "0",
            "typeid": "0",
            "subjectenc": "%E5%8C%97%E6%88%B4%E6%B2%B3%E7%9D%A1%E8%A7%89",
            "closed": "0",
            "sharetimes": "0",
            "archiveid": "0",
            "icon": "-1",
            "stamp": "-1",
            "recommend_sub": "0",
            "recommend_add": "12",
            "isgroup": "0",
            "lastposter": "fuwei111",
            "posttableid": "0",
            "heats": "0",
            "attachment": "0",
            "pushedaid": "0",
            "maxposition": "0",
            "stickreply": "0",
            "hidden": "0",
            "views": "3000000000000",
            "sortid": "0",
            "special": "4",
            "comments": "0"
        },
        "groupid": "1",
        "notice": {
            "newmypost": "6",
            "newpm": "0",
            "newprompt": "0",
            "newpush": "0"
        },
        "cookiepre": "31Iy_2132_",
        "comments": [],
        "auth": "bb9dAjmrWO3qTDKoVXLYBMrL1XILiHjSb8yYzqt7znrgMZfG6wQ5rYGk4612ximwbCvVZU5xHagSKaB6iH1YWQ"
    }
};

var TEST_JSON={
    "Version": "4",
    "Charset": "UTF-8",
    "Variables": {
        "cookiepre": "31Iy_2132_",
        "auth": null,
        "saltkey": "AM6H5EY7",
        "member_uid": "0",
        "member_username": "",
        "member_avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=0&size=small",
        "groupid": "7",
        "formhash": "46c8edb9",
        "ismoderator": "0",
        "readaccess": "1",
        "notice": {
            "newpush": "0",
            "newpm": "0",
            "newprompt": "0",
            "newmypost": "0"
        },
        "allowperm": {
            "allowpost": "0",
            "allowreply": "0",
            "uploadhash": "1f2f0af15dc975adf709295d0a563158"
        },
        "thread": {
            "tid": "163",
            "fid": "42",
            "posttableid": "0",
            "typeid": "0",
            "sortid": "0",
            "readperm": "0",
            "price": "0",
            "author": "fuwei111",
            "authorid": "55",
            "subject": "还是宿舍",
            "dateline": "2015-7-24 11:06",
            "lastpost": "1441698058",
            "lastposter": "fuwei111",
            "views": "17400000000000",
            "replies": "25",
            "displayorder": "0",
            "highlight": "0",
            "digest": "0",
            "rate": "0",
            "special": "4",
            "attachment": "0",
            "moderated": "0",
            "closed": "0",
            "stickreply": "0",
            "recommends": "2",
            "recommend_add": "2",
            "recommend_sub": "0",
            "heats": "4",
            "status": "1024",
            "isgroup": "0",
            "favtimes": "2",
            "sharetimes": "0",
            "stamp": "-1",
            "icon": "-1",
            "pushedaid": "0",
            "cover": "0",
            "replycredit": "0",
            "relatebytag": "0",
            "maxposition": "26",
            "bgcolor": "",
            "comments": "0",
            "hidden": "0",
            "threadtable": "forum_thread",
            "threadtableid": "0",
            "posttable": "forum_post",
            "allreplies": "25",
            "is_archived": "",
            "archiveid": "0",
            "subjectenc": "%E8%BF%98%E6%98%AF%E5%AE%BF%E8%88%8D",
            "short_subject": "还是宿舍",
            "starttime": "1437707169",
            "remaintime": "",
            "recommendlevel": "0",
            "heatlevel": "0",
            "relay": "0",
            "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
            "ordertype": "0",
            "recommend": "0"
        },
        "fid": "42",
        "postlist": [
            {
                "pid": "310",
                "tid": "163",
                "first": "1",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-7-24 11:06:09",
                "message": "百战百胜你睡吧",
                "anonymous": "0",
                "attachment": "0",
                "status": "8",
                "replycredit": "0",
                "position": "1",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "1",
                "dbdateline": "1437707169",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            },
            {
                "pid": "311",
                "tid": "163",
                "first": "0",
                "author": "admin",
                "authorid": "1",
                "dateline": "2015-7-24 11:41:59",
                "message": " wwww",
                "anonymous": "0",
                "attachment": "0",
                "status": "1032",
                "replycredit": "0",
                "position": "2",
                "username": "admin",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "2",
                "dbdateline": "1437709319",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=1&size=small",
                "groupiconid": "admin"
            },
            {
                "pid": "500",
                "tid": "163",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-8-28 17:38:38",
                "message": "1",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "3",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "3",
                "dbdateline": "1440754718",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            },
            {
                "pid": "501",
                "tid": "163",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-8-28 17:38:41",
                "message": "2",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "4",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "4",
                "dbdateline": "1440754721",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            },
            {
                "pid": "502",
                "tid": "163",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-8-28 17:38:43",
                "message": "3",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "5",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "5",
                "dbdateline": "1440754723",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            },
            {
                "pid": "503",
                "tid": "163",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-8-28 17:38:46",
                "message": "4",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "6",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "6",
                "dbdateline": "1440754726",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            },
            {
                "pid": "504",
                "tid": "163",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-8-28 17:38:49",
                "message": "5",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "7",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "7",
                "dbdateline": "1440754729",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            },
            {
                "pid": "505",
                "tid": "163",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-8-28 17:38:51",
                "message": "6",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "8",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "8",
                "dbdateline": "1440754731",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            },
            {
                "pid": "506",
                "tid": "163",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-8-28 17:38:54",
                "message": "7",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "9",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "9",
                "dbdateline": "1440754734",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            },
            {
                "pid": "507",
                "tid": "163",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-8-28 17:38:56",
                "message": "8",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "10",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "10",
                "dbdateline": "1440754736",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            }
        ],
        "allowpostcomment": [
            "1",
            "2"
        ],
        "comments": [],
        "commentcount": [],
        "ppp": "10",
        "setting_rewriterule": null,
        "setting_rewritestatus": "",
        "forum_threadpay": "",
        "cache_custominfo_postno": [
            "<sup>#</sup>",
            "楼主",
            "沙发",
            "板凳",
            "地板"
        ],
        "special_activity": {
            "tid": "163",
            "uid": "55",
            "aid": "0",
            "cost": "0",
            "starttimefrom": "2015-7-24 11:05",
            "starttimeto": "2015-8-24 11:05",
            "place": "beijw",
            "class": "自驾出行",
            "gender": "0",
            "number": "6",
            "applynumber": "0",
            "expiration": "0",
            "ufield": {
                "userfield": null,
                "extfield": []
            },
            "credit": "0",
            "thumb": "",
            "attachurl": "",
            "allapplynum": "0",
            "joinfield": [],
            "userfield": "",
            "extfield": "",
            "basefield": [],
            "closed": "0",
            "status": "join",
            "button": "join",
            "is_ex": "0",
            "isverified": "0",
            "applied": "0"
        },
        "forum": {
            "password": "0"
        }
    }
};