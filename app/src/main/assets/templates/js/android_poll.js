var HOST="http://wsq.demo.comsenz-service.com/";
//var HOST="http://bbs.rednet.cn/";
var isMultiSelected=true;
var maxChoices=2;
var isLoadReplyOver=false;//是否所有评论已加载完毕
var	is2GOr3GLoadImgs=true;//2/3G网络是否加载图片

$(document).ready(function(){
	$("div.box03").bind("click",function(){threadOptionsClick();});
	$(".P_submit").bind("click",function(){sendPoll();});
	$(".thread_details .P_share div a:eq(0)").bind("click",function(){praise();});
	$(".thread_details .P_share div a:eq(2)").bind("click",function(){share();});
	$(".thread_details .P_share div a:eq(1)").click(function(){report();});
	$(".reply_botton_j a").click(function(){loadMore();});
	//test();
});

/*@hidden*/var setThread=function(JSON){

	isMultiSelected=(parseInt(JSON.Variables.special_poll.multiple)==1);
	maxChoices=parseInt(JSON.Variables.special_poll.maxchoices);
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
     var forum = "discuz模板";
//           $(".header_title").html("<span class='title_type'> ["+type+"] </span>"+subject);
           $(".forumName").html(JSON.Variables.thread.forumnames);
/*
	$(".header_text").html(JSON.Variables.thread.subject+(isMultiSelected?"    <span>多选</span>":""));*/
	var hasZan = JSON.Variables.thread.recommend;
            if(hasZan == 1){
                $(".p_zanBtn").attr("src","images/zanSmall_icon1.png");
            }else{
                $(".p_zanBtn").attr("src","images/praise.png");
            }

	$(".P_figure .box01 img").attr(is2GOr3GLoadImgs?"src":"url",is2GOr3GLoadImgs?JSON.Variables.thread.avatar:"images/noavatar_small.png");
	$(".P_figure .box01 label:eq(0)").html(JSON.Variables.thread.author);
	$(".P_figure .box01 label:eq(1)").html(JSON.Variables.postlist[0].dateline);
	$(".P_figure .box01").click(function(){userInfo(JSON.Variables.thread.authorid)});
	 var timestamp = Date.parse(new Date());
	 var expirations =JSON.Variables.special_poll.expirations;
//	 expiration = expiration.replace(/-/g,'/')
//        var date = new Date(expiration)
        var poll ="";
        if(timestamp >=expirations){
        poll = "投票已结束,";
        }else{
       poll = "投票进行中,";
        }
//        +(isMultiSelected?"    <span>多选</span>":"")
	$(".vote_tips").html(isMultiSelected?"多选投票(最多可选"+JSON.Variables.special_poll.maxchoices+"):":"单选投票:");
	$(".vote_tips").append("已有"+JSON.Variables.special_poll.voterscount+"人参加")
     var   d=new   Date(expirations);
	$(".vote_time").html("投票截止时间:"+new Date(parseInt(expirations) * 1000).toLocaleString().replace(/:\d{1,2}$/,' '));
	$(".thread_details .P_share div span:eq(0)").html(JSON.Variables.thread.recommend_add);
//	$(".thread_details .P_share div span:eq(1)").html(JSON.Variables.thread.replies);
//alert(JSON.Variables.thread.views);
	$(".p_header .header_bottom .view").html(JSON.Variables.thread.views);
	$(".p_header .header_bottom .reply").html(JSON.Variables.thread.replies);
	$(".thread_details .P_share div a:eq(1)").click(function(){discussUser(null);});
	if (!textIsNull(JSON.Variables.postlist)){
		$("div.thread_details").attr("id","rednet_anchor_id_"+JSON.Variables.postlist[0].pid);
		$("p#thread_content img").css({"width":"0px","height":"0px"});
		$("p#thread_content").html(JSON.Variables.postlist[0].message);
//		$("p#thread_content img").click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
//		.attr("src",is2GOr3GLoadImgs?$(this).attr("src"):"images/picture.png")
//		.css({"width":"auto","height":"auto"});
	    $("p#thread_content img").click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
		.attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
		.css({"width":"auto","height":"auto"});
	}
	if(!textIsNull(JSON.Variables.postlist[0].attachments)){
		$(".thread_details .attachlist img").css({"width":"0px","height":"0px"});
		$(".thread_details .attachlist").removeAttr("hidden");
//		var attachlist=JSON.Variables.postlist[0].attchlist;
		 var str = "http://wsq.demo.comsenz-service.com/";
         var aid = "";
         var strlist = JSON.Variables.postlist[0].attachments;
                   for(var key in strlist){
                      var imgUrl = str + strlist[key].url;
                       imgUrl = imgUrl + strlist[key].attachment;
                       aid = strlist[key].aid;
                     $(".thread_details .attachlist ul").append("<li><img aid='"+aid+"' style='max-height:80%;'  src='"+imgUrl+"'/></li>");
                   }

		$(".thread_details .attachlist img").click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
		.attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
		.css({"width":"auto","height":"auto"});
	}

	var pollList=$("ul.poll_list");
	for(option in JSON.Variables.special_poll.polloptions){
		var pollOption=JSON.Variables.special_poll.polloptions[option];
		var imgPid=pollOption.imginfo.pid;
//		alert(imgPid);
		var item=isMultiSelected?pollList.find("li:eq(0)").clone():pollList.find("li:eq(1)").clone();
		if(imgPid!==undefined&&imgPid.length>0){
			item.find(".ui-type-poll-img-txt").removeAttr("hidden");
//			item.find(".ui-type-poll-img-txt p img").attr("src",HOST+pollOption.imginfo.small);
			item.find(".ui-type-poll-img-txt p img").attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?HOST+pollOption.imginfo.small:"hidden");
//			item.find(".ui-type-poll-img-txt p img")..attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?HOST+pollOption.imginfo.small:"hidden");
			item.find(".ui-type-poll-img-txt input").attr("id","poll_item"+option).attr("value",pollOption.polloptionid);
			item.find(".ui-type-poll-img-txt label").attr("for","poll_item"+option).html(option+"."+pollOption.polloption);
		}else{
			item.find(".ui-type-poll-txt-only").removeAttr("hidden");
			item.find(".ui-type-poll-txt-only input").attr("id","poll_item"+option).attr("value",JSON.Variables.special_poll.polloptions[option].polloptionid);
			item.find(".ui-type-poll-txt-only label").attr("for","poll_item"+option).html(option+"."+JSON.Variables.special_poll.polloptions[option].polloption);
		}
		item.removeAttr("hidden");
		pollList.append(item);
	}
	pollList.buttonset();

	if(isMultiSelected){
		pollList.find(":checkbox").bind("change",function(){
			var size=$("ul.poll_list li:not(:hidden) div:not(:hidden) :checked").size();
			if(size==maxChoices){
				var uncheckeds=$(".poll_list li:not(:hidden) div:not(:hidden) input:not(:checked)");
				for(var i=0,size=uncheckeds.size();i<size;i++){
					uncheckeds.eq(i).attr("disabled","disabled");
				}
			}else{
				var uncheckeds=$(".poll_list li:not(:hidden) div:not(:hidden) input[disabled='disabled']");
				for(var i=0,size=uncheckeds.size();i<size;i++){
					uncheckeds.eq(i).removeAttr("disabled");
				}
			}
		});
	}
	if(JSON.Variables.thread.replies == 0){
    	     onLoadOver();
    	}else{
            onLoadReply(JSON,false)
    	    enableLoadMore(false);
    	}
//	onLoadReply(JSON,false)
};

/**
 *加载用户评论
 *@param JSON 评论JSON数据
 *@param isAppend 是否为分页评论
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
			li.attr("pid",postlist[i].pid).attr("authorid",postlistitem.authorid).attr("id","rednet_anchor_id_"+postlistitem.pid);//设置#锚点
			li.find("div.ageImg img")
			    .attr(is2GOr3GLoadImgs?"src":"url",is2GOr3GLoadImgs?postlistitem.avatar:"images/noavatar_small.png")
				.click(function(){userInfo($(this).parents("li").attr("authorid"));});

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
			    .attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
				.click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:"null")
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
                    li.find(".attachlist ul").append("<li><img aid='"+aid+"' style='max-height:80%;' src='"+commendImgUrl+"'/></li>");
                                }
                         li.find(".attachlist img")
                                        .attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
                         				.click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
                         				.css({"width":"auto","height":"auto"});
                        }
			//附件
//			if(!textIsNull(attach)){
//				li.find(".attachlist img").css({"width":"0px","height":"0px"});
//				li.find(".attachlist").removeAttr("hidden");
//				var attachlist=postlistitem.attachlist;
//				for(var j=0;j<attachlist.length;j++){
//					if(!textIsNull(attachlist[j].attachurl)){
//						li.find(".attachlist ul").append("<li><img aid='"+attachlist[j].aid+"' src='"+attachlist[j].attachurl+"'/></li>");
//					}
//				}
//				li.find(".attachlist img").attr("src",is2GOr3GLoadImgs?$(this).attr("src"):"images/picture.png")
//				.click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
//				.css({"width":"auto","height":"auto"});
//			}
			li.removeAttr("hidden");
			ul.append(li);
		}
	}
	if(ul.find("li").size() >= JSON.Variables.thread.replies ||ul.find("li").size() == 1){
    			 onLoadOver();
    		}else{
    			enableLoadMore(false);
    		}
//	if(JSON.Variables.thread.replies==0){
//		$(".p_vote").attr("hidden");
//	}else{
//		$(".p_vote").removeAttr("hidden");
//	}
// if(JSON.Variables.thread.replies==0&&JSON.Variables.postlist.length<2){
////        $(".p_vote").attr({style:"display:none"});
//        enableLoadMore(true);
//    }else if((JSON.Variables.postlist.length-1)==0 ||JSON.Variables.thread.replies==ul.find("li").size() ){
//        enableLoadMore(true);
//    }else{
//		enableLoadMore(false);
//	}
	/*if(!isLoadReplyOver){
		if(ul.find("li").size()>1){
			enableLoadMore(true);
		}else{
			if($("p.reply_botton_j").attr("hidden")!==undefined){
				enableLoadMore(false);
			}
		}
	}*/
}

/**
 *如果客户端webview on loading不能执行改方法,由android客户端调用(“javascript:onRefresh(JSON,AttachsJSON,is2GOr3GLoadImgs)”)
 *@param JSON 帖子详情数据
 *@param AttachsJSON 帖子附件数据(可以传空)
 *@param is2GOr3GLoadImgs 2/3G网络是否加载图片
 */
function onRefresh(JSON,is2GOr3GLoadImgs){
	this.is2GOr3GLoadImgs=is2GOr3GLoadImgs;
	this.isLoadReplyOver=false;
    setThread(JSON);
    onLoadReply(JSON,false);
}

/**已加载玩所有评论*/
function onLoadOver(){
	isLoadReplyOver=true;
	enableLoadMore(true);
}

/*@hidden*/function enableLoadMore(enable){
	if(enable){
		$("p.reply_botton_j").removeAttr("hidden");
	}else{
		$("p.reply_botton_j").attr("hidden","hidden");
	}
}

///*@hidden*/function enableAllComment(){
//    $(".p_vote").attr({style:"display:none"});
//}

/*@hidden*/function loadMore(){
	Rednet.onLoadMore();
}

/*@hidden*/function userInfo(authorid){
	Rednet.onUserInfo(authorid);
}

/*@hidden*/function praise(){
	Rednet.onPraise();
}

/**帖子点赞成功刷新赞的个数*/
function onPraiseSuccess(){
	var el=$(".content_box .P_share div span:eq(0)");
	el.html(parseInt(el.html())+1);
}

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

/*@hidden*/function discussUser(pid){
	Rednet.onDiscussUser(pid);
}

/**
 *回复用户评论或帖子成功 客户端回调webview.loadUrl("javascript:onDiscussSuccess(JSON,pid);")
 *@param JSON 页面信息包含用户所有评论 如果为空则页面不会自动跳到用户回复的地方
 *@param pid 回复成功后返回的pid 用字符串形式传入
 */
function onDiscussSuccess(JSON,is2GOr3GLoadImgs,pid){
	if (!textIsNull(JSON)){
        onRefresh(JSON,is2GOr3GLoadImgs);
		if(!textIsNull(pid)){
			window.location.href="#rednet_anchor_id_"+pid;
		}
	}
}

/*@hidden*/function sendPoll(){
	var checks=$(".poll_list :checked");
	if(checks!==undefined){
		var values=new Array(checks.length);
		if(isMultiSelected){
			for(var i=0;i<checks.length;i++){
				values[i]=checks.eq(i).attr("value");
			}
		}else{
			values[0]=checks.attr("value");
		}
		Rednet.onSendPoll(values);
	}
}

//查看大图
/*@hidden*/function threadThumbsClick(url){
	Rednet.onThreadThumbsClicked(url);
}

//帖子其他操作事件
/*@hidden*/function threadOptionsClick(){
	Rednet.onThreadOptionsClick();
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
function formatDate(now)
{
var year=now.getYear();
var month=now.getMonth()+1;
var date=now.getDate();
var hour=now.getHours();
var minute=now.getMinutes();
var second=now.getSeconds();
return year+"-"+month+"-"+date+" "+hour+":"+minute+":"+second;
}

function test(){
	onRefresh(TEST_JSON,true);
}
function getNowFormatDate() {
    var date = new Date();
    var seperator1 = "-";
    var seperator2 = ":";
    var month = date.getMonth() + 1;
    var strDate = date.getDate();
    if (month >= 1 && month <= 9) {
        month = "0" + month;
    }
    if (strDate >= 0 && strDate <= 9) {
        strDate = "0" + strDate;
    }
    var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + date.getHours() + seperator2 + date.getMinutes()
            + seperator2 + date.getSeconds();
    return currentdate;
}
var TEST_JSON={
    "Version": "4",
    "Charset": "UTF-8",
    "Variables": {
        "cookiepre": "31Iy_2132_",
        "auth": null,
        "saltkey": "a71EEGZ9",
        "member_uid": "0",
        "member_username": "",
        "member_avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=0&size=small",
        "groupid": "7",
        "formhash": "83a2b85c",
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
            "tid": "46",
            "fid": "41",
            "posttableid": "0",
            "typeid": "2",
            "sortid": "0",
            "readperm": "0",
            "price": "0",
            "author": "国文",
            "authorid": "31",
            "subject": "我想投票",
            "dateline": "2015-5-18 18:43",
            "lastpost": "1438851727",
            "lastposter": "",
            "views": "3524",
            "replies": "1",
            "displayorder": "0",
            "highlight": "0",
            "digest": "0",
            "rate": "0",
            "special": "1",
            "attachment": "0",
            "moderated": "0",
            "closed": "0",
            "stickreply": "0",
            "recommends": "1",
            "recommend_add": "1",
            "recommend_sub": "0",
            "heats": "1",
            "status": "0",
            "isgroup": "0",
            "favtimes": "0",
            "sharetimes": "0",
            "stamp": "-1",
            "icon": "-1",
            "pushedaid": "0",
            "cover": "0",
            "replycredit": "0",
            "relatebytag": "0",
            "maxposition": "0",
            "bgcolor": "",
            "comments": "0",
            "hidden": "0",
            "threadtable": "forum_thread",
            "threadtableid": "0",
            "posttable": "forum_post",
            "allreplies": "0",
            "is_archived": "",
            "archiveid": "0",
            "subjectenc": "%E6%88%91%E6%83%B3%E6%8A%95%E7%A5%A8",
            "short_subject": "我想投票",
            "starttime": "1431945819",
            "remaintime": "",
            "recommendlevel": "0",
            "heatlevel": "0",
            "relay": "0",
            "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=31&size=small",
            "ordertype": "0",
            "recommend": "0"
        },
        "fid": "41",
        "postlist": [
            {
                "pid": "91",
                "tid": "46",
                "first": "1",
                "author": "国文",
                "authorid": "31",
                "dateline": "2015-5-18 18:43:39",
                "message": "&amp;nbsp;",
                "anonymous": "0",
                "attachment": "0",
                "status": "0",
                "replycredit": "0",
                "position": "1",
                "username": "国文",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "1",
                "dbdateline": "1431945819",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=31&size=small",
                "groupiconid": "admin",
				"attachlist": [
                    {
                        "attachurl": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                        "aid": "123"
                    },
					{
                        "attachurl": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                        "aid": "123"
                    }
                ]
            },
		 {
                "pid": "91",
                "tid": "46",
                "first": "1",
                "author": "国文",
                "authorid": "31",
                "dateline": "2015-5-18 18:43:39",
                "message": "motherfuckers",
                "anonymous": "0",
                "attachment": "0",
                "status": "0",
                "replycredit": "0",
                "position": "1",
                "username": "国文",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "1",
                "dbdateline": "1431945819",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=31&size=small",
                "groupiconid": "admin",
				"attachlist": [
                    {
                        "attachurl": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                        "aid": "123"
                    },
					{
                        "attachurl": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                        "aid": "123"
                    }
                ]
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
        "special_poll": {
            "polloptions": {
                "1": {
                    "polloptionid": "11",
                    "polloption": "我好想",
                    "votes": "1",
                    "width": "100%",
                    "percent": "100.00",
                    "color": "E92725",
                    "imginfo": []
                },
                "2": {
                    "polloptionid": "12",
                    "polloption": "我不想",
                    "votes": "0",
                    "width": "8px",
                    "percent": "0.00",
                    "color": "F27B21",
                    "imginfo": []
                },
                "3": {
                    "polloptionid": "13",
                    "polloption": "随便",
                    "votes": "0",
                    "width": "8px",
                    "percent": "0.00",
                    "color": "F2A61F",
                    "imginfo": []
                }
            },
            "expirations": "1432032219",
            "multiple": "1",
            "maxchoices": "3",
            "voterscount": "1",
            "visiblepoll": "0",
            "allowvote": "",
            "remaintime": "",
            "allowvotepolled": "1",
            "allowvotethread": "",
            "allwvoteusergroup": "0",
            "overt": "0"
        },
        "forum": {
            "password": "0"
        }
    }
};

var TEST_JSON_IMG={
    "Version": "4",
    "Charset": "UTF-8",
    "Variables": {
        "cookiepre": "31Iy_2132_",
        "auth": null,
        "saltkey": "tjA0BygX",
        "member_uid": "0",
        "member_username": "",
        "member_avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=0&size=small",
        "groupid": "7",
        "formhash": "a879808d",
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
            "tid": "120",
            "fid": "41",
            "posttableid": "0",
            "typeid": "0",
            "sortid": "0",
            "readperm": "0",
            "price": "0",
            "author": "fuwei111",
            "authorid": "55",
            "subject": "再来一次！",
            "dateline": "2015-7-4 15:49",
            "lastpost": "1438851895",
            "lastposter": "fuwei111",
            "views": "10",
            "replies": "0",
            "displayorder": "0",
            "highlight": "0",
            "digest": "0",
            "rate": "0",
            "special": "1",
            "attachment": "0",
            "moderated": "0",
            "closed": "0",
            "stickreply": "0",
            "recommends": "1",
            "recommend_add": "1",
            "recommend_sub": "0",
            "heats": "1",
            "status": "32",
            "isgroup": "0",
            "favtimes": "0",
            "sharetimes": "0",
            "stamp": "-1",
            "icon": "-1",
            "pushedaid": "0",
            "cover": "0",
            "replycredit": "0",
            "relatebytag": "0",
            "maxposition": "0",
            "bgcolor": "",
            "comments": "0",
            "hidden": "0",
            "threadtable": "forum_thread",
            "threadtableid": "0",
            "posttable": "forum_post",
            "allreplies": "0",
            "is_archived": "",
            "archiveid": "0",
            "subjectenc": "%E5%86%8D%E6%9D%A5%E4%B8%80%E6%AC%A1%EF%BC%81",
            "short_subject": "再来一次！",
            "starttime": "1435996144",
            "remaintime": "",
            "recommendlevel": "0",
            "heatlevel": "0",
            "relay": "0",
            "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
            "ordertype": "0",
            "recommend": "0"
        },
        "fid": "41",
        "postlist": [
            {
                "pid": "255",
                "tid": "120",
                "first": "1",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-7-4 15:49:04",
                "message": "11111111111111<br />\r\n",
                "anonymous": "0",
                "attachment": "0",
                "status": "0",
                "replycredit": "0",
                "position": "1",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "1",
                "dbdateline": "1435996144",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            },
				 {
                "pid": "255",
                "tid": "120",
                "first": "1",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-7-4 15:49:04",
                "message": "11111111111111<br />\r\n",
                "anonymous": "0",
                "attachment": "0",
                "status": "0",
                "replycredit": "0",
                "position": "1",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "1",
                "dbdateline": "1435996144",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            }, {
                "pid": "255",
                "tid": "120",
                "first": "1",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-7-4 15:49:04",
                "message": "11111111111111<br />\r\n",
                "anonymous": "0",
                "attachment": "0",
                "status": "0",
                "replycredit": "0",
                "position": "1",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "1",
                "dbdateline": "1435996144",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            }, {
                "pid": "255",
                "tid": "120",
                "first": "1",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-7-4 15:49:04",
                "message": "11111111111111<br />\r\n",
                "anonymous": "0",
                "attachment": "0",
                "status": "0",
                "replycredit": "0",
                "position": "1",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "1",
                "dbdateline": "1435996144",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            }, {
                "pid": "255",
                "tid": "120",
                "first": "1",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-7-4 15:49:04",
                "message": "11111111111111<br />\r\n",
                "anonymous": "0",
                "attachment": "0",
                "status": "0",
                "replycredit": "0",
                "position": "1",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "1",
                "dbdateline": "1435996144",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            }, {
                "pid": "255",
                "tid": "120",
                "first": "1",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-7-4 15:49:04",
                "message": "11111111111111<br />\r\n",
                "anonymous": "0",
                "attachment": "0",
                "status": "0",
                "replycredit": "0",
                "position": "1",
                "username": "fuwei111",
                "adminid": "1",
                "groupid": "1",
                "memberstatus": "0",
                "number": "1",
                "dbdateline": "1435996144",
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
        "special_poll": {
            "polloptions": {
                "1": {
                    "polloptionid": "46",
                    "polloption": "aaaaaa",
                    "votes": "0",
                    "width": "8px",
                    "percent": "0.00",
                    "color": "E92725",
                    "imginfo": {
                        "aid": "4",
                        "poid": "46",
                        "tid": "120",
                        "pid": "255",
                        "uid": "55",
                        "filename": "鲨鱼.jpg",
                        "filesize": "136386",
                        "attachment": "201507/04/154844u20r2ogh4rfuuud2.jpg",
                        "remote": "0",
                        "width": "794",
                        "thumb": "1",
                        "dateline": "1435996124",
                        "small": "data/attachment/forum/201507/04/154844u20r2ogh4rfuuud2.jpg.thumb.jpg",
                        "big": "data/attachment/forum/201507/04/154844u20r2ogh4rfuuud2.jpg"
                    }
                },
                "2": {
                    "polloptionid": "47",
                    "polloption": "bbbbbbb",
                    "votes": "0",
                    "width": "8px",
                    "percent": "0.00",
                    "color": "F27B21",
                    "imginfo": {
                        "aid": "5",
                        "poid": "47",
                        "tid": "120",
                        "pid": "255",
                        "uid": "55",
                        "filename": "鲨鱼.jpg",
                        "filesize": "136386",
                        "attachment": "201507/04/154848qc77077277zdg21f.jpg",
                        "remote": "0",
                        "width": "794",
                        "thumb": "1",
                        "dateline": "1435996128",
                        "small": "data/attachment/forum/201507/04/154848qc77077277zdg21f.jpg.thumb.jpg",
                        "big": "data/attachment/forum/201507/04/154848qc77077277zdg21f.jpg"
                    }
                },
                "3": {
                    "polloptionid": "48",
                    "polloption": "ccccccc",
                    "votes": "0",
                    "width": "8px",
                    "percent": "0.00",
                    "color": "F2A61F",
                    "imginfo": {
                        "aid": "6",
                        "poid": "48",
                        "tid": "120",
                        "pid": "255",
                        "uid": "55",
                        "filename": "鲨鱼.jpg",
                        "filesize": "136386",
                        "attachment": "201507/04/154852c1uws23cws5uwzwc.jpg",
                        "remote": "0",
                        "width": "794",
                        "thumb": "1",
                        "dateline": "1435996132",
                        "small": "data/attachment/forum/201507/04/154852c1uws23cws5uwzwc.jpg.thumb.jpg",
                        "big": "data/attachment/forum/201507/04/154852c1uws23cws5uwzwc.jpg"
                    }
                }
            },
            "expirations": "1436168944",
            "multiple": "0",
            "maxchoices": "1",
            "voterscount": "0",
            "visiblepoll": "0",
            "allowvote": "",
            "remaintime": "",
            "allowvotepolled": "1",
            "allowvotethread": "",
            "allwvoteusergroup": "0",
            "overt": "1"
        },
        "forum": {
            "password": "0"
        }
    }
};