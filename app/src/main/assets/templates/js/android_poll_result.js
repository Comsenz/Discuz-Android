var HOST="http://wsq.demo.comsenz-service.com/";
//var HOST="http://bbs.rednet.cn/";
var isLoadReplyOver=false;//是否所有评论已加载完毕
var	is2GOr3GLoadImgs=true;//2/3G网络是否加载图片
var isMultiSelected=true;
$(document).ready(function(){
	$("div.box03").bind("click",function(){threadOptionsClick();});
	$(".thread_details .P_share div a:eq(0)").bind("click",function(){praise();});
	$(".thread_details .P_share div a:eq(2)").bind("click",function(){share();});
	$(".thread_details .P_share div a:eq(1)").click(function(){report();});
	$(".reply_botton_j a").click(function(){loadMore();});
	//test();
});

/*@hidden*/var setThread=function(JSON){
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

       $(".forumName").html(JSON.Variables.thread.forumnames);
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
	$(".P_figure .box01").click(function(){userInfo(JSON.Variables.thread.authorid)});
	 var timestamp = Date.parse(new Date());
	 isMultiSelected=(parseInt(JSON.Variables.special_poll.multiple)==1);
    	 var expirations =JSON.Variables.special_poll.expirations;
    //	 expiration = expiration.replace(/-/g,'/')
    //        var date = new Date(expiration)
            var poll ="";
            if(isMultiSelected ==true){
            poll = "多选投票(最多可选"+JSON.Variables.special_poll.maxchoices+"):";
            }else{
           poll = "单选投票:";
            }
	$(".vote_tips").html(poll+"已有"+JSON.Variables.special_poll.voterscount+"人参加"
		+"<a href='javascript:void(0)' onclick='voterDetails()' id='voters' style='color:#3c96d6'>  查看参与投票的人</a>");
		$(".vote_time").html("投票截止时间:"+new Date(parseInt(expirations) * 1000).toLocaleString().replace(/:\d{1,2}$/,' '));
	$(".thread_details .P_share div span:eq(0)").html(JSON.Variables.thread.recommend_add);
//	$(".thread_details .P_share div span:eq(1)").html(JSON.Variables.thread.replies);
	$(".p_header .header_bottom .view").html(JSON.Variables.thread.views);
    	$(".p_header .header_bottom .reply").html(JSON.Variables.thread.replies);
	$(".thread_details .P_share div a:eq(1)").click(function(){discussUser(null);});
	if (!textIsNull(JSON.Variables.postlist)){
		$("div.thread_details").attr("id","rednet_anchor_id_"+JSON.Variables.postlist[0].pid);
		$("p#thread_content img").css({"width":"0px","height":"0px"});
		$("p#thread_content").html(JSON.Variables.postlist[0].message);
		$("p#thread_content img").click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
		.attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
		.css({"width":"auto","height":"auto"});
	}
//    if(!textIsNull(JSON.Variables.postlist) && JSON.Variables.postlist.length >1){
//        for(var i=1;i<JSON.Variables.postlist.length;i++){
//        var postlistitem = JSON.Variables.postlist[i];
//        	if(!textIsNull(postlistitem)){
//            		$(".thread_details .attachlist img").css({"width":"0px","height":"0px"});
//            		$(".thread_details .attachlist").removeAttr("hidden");
//            //		var attachlist=JSON.Variables.postlist[0].attchlist;
//            		 var str = "http://bbs.wsq.comsenz-service.com/newwsq/";
//                     var aid = "";
//                     var strlist = postlistitem.attachments;
//                               for(var key in strlist){
//                                  var imgUrl = str + strlist[key].url;
//                                   imgUrl = imgUrl + strlist[key].attachment;
//                                   aid = strlist[key].aid;
//                                 $(".thread_details .attachlist ul").append("<li><img aid='"+aid+"' style='max-height:80%;'  src='"+imgUrl+"'/></li>");
//                               }
//
//            		$(".thread_details .attachlist img").click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
//            		.attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
//            		.css({"width":"auto","height":"auto"});
//            	}
//         }
//    }

    //投票结果
	var pollResultList=$("ul.poll_result");
	for(option in JSON.Variables.special_poll.polloptions){
		var pollOption=JSON.Variables.special_poll.polloptions[option];
		var imgPid=pollOption.imginfo.pid;
		var item=pollResultList.find("li:first").clone();
		if(imgPid!==undefined&&imgPid.length>0){
			item.find(".ui-poll-img").removeAttr("hidden");
			item.find(".ui-poll-img img").attr("src","http://wsq.demo.comsenz-service.com/"+pollOption.imginfo.small);
			$(".ui-poll-img img").click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
                		.attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
                		.css({"width":"auto","height":"auto"});
		}
		item.find("label").html(option+"."+pollOption.polloption);
		item.find(".ui-poll-progressbar").css("width",parseInt(pollOption.width)*2)//
			.progressbar({max:100,value:100});
		item.find("span").html(pollOption.percent+"%")
			.css("left",parseInt(pollOption.width)*2)
			.css("top",-17)
			.css("color","#"+pollOption.color);
		item.find(".ui-progressbar-value").css("background-color","#"+pollOption.color);
		item.removeAttr("hidden");
		pollResultList.append(item);
	}
	if(JSON.Variables.thread.replies == 0){
        	   onLoadOver();
        	}else{
                onLoadReply(JSON,false);
        	   onLoadReply(JSON,false)
        	}

}

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

			var li=ul.find("li.bor1:first").clone();
			li.attr("pid",postlistitem.pid).attr("authorid",postlistitem.authorid).attr("id","rednet_anchor_id_"+postlistitem.pid);//设置#锚点
			li.find("div.ageImg img").attr(is2GOr3GLoadImgs?"src":"url",is2GOr3GLoadImgs?postlistitem.avatar:"images/noavatar_small.png")
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
			li.find("div.messContent p:eq(1) img").css({"width":"0px","height":"0px"});
			li.find("div.messContent p:eq(1)").html(postlistitem.message);
			li.find("div.messContent p:eq(1) img").attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
				.click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
				.css({"width":"auto","height":"auto"});
			li.find("div.P_share div.naTime_02 label.time").html(postlistitem.dateline);
		    li.find("div.P_share div .a").click(function(){discussUser($(this).parents("li").attr("pid"));});
        	li.find("div.P_share div .b").click(function(){reportComment($(this).parents("li").attr("pid"));});


             var attach = postlistitem.attachments;
           if(!textIsNull(attach)){
            li.find(".attachlist img").css({"width":"0px","height":"0px"});
            li.find(".attachlist").removeAttr("hidden");
            var commendStr = "http://wsq.demo.comsenz-service.com/";
            for(var key in attach){
              var commendImgUrl = commendStr + attach[key].url;
              commendImgUrl = commendImgUrl + attach[key].attachment;
              var aid = attach[key].aid;
              li.find(".attachlist ul").append("<li><img aid='"+aid+"' style='max-height:80%;' src='"+commendImgUrl+"'/></li>");
                  }
              li.find(".attachlist img").attr(is2GOr3GLoadImgs?"src":"hidden",is2GOr3GLoadImgs?$(this).attr("src"):"hidden")
                     .click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
                     .css({"width":"auto","height":"auto"});
               }

			//附件
			/*if(!textIsNull(postlistitem.attchlist)){
				li.find(".attachlist img").css({"width":"0px","height":"0px"});
				li.find(".attachlist").removeAttr("hidden");
				var attachlist=postlistitem.attchlist;
				for(var j=0;j<attachlist.length;j++){
					if(!textIsNull(attachlist[j].attachurl)){
						li.find(".attachlist ul").append("<li><img aid='"+attachlist[j].aid+"' src='"+attachlist[j].attachurl+"'/></li>");
					}
				}
				li.find(".attachlist img").attr("src",is2GOr3GLoadImgs?$(this).attr("src"):"images/picture.png")
				.click(is2GOr3GLoadImgs?function(){threadThumbsClick($(this).attr("src"));}:null)
				.css({"width":"auto","height":"auto"});
			}*/
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
 *@param is2GOr3GLoadImgs 2/3G网络是否加载图片
 */
function onRefresh(JSON,is2GOr3GLoadImgs){
	this.is2GOr3GLoadImgs=is2GOr3GLoadImgs;
	this.isLoadReplyOver=false;
    setThread(JSON);
    onLoadReply(JSON,false);
}

/*@hidden*/function loadMore(){
	Rednet.onLoadMore();
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

/*@hidden*/function enableAllComment(){
    $(".bg").attr({style:"display:none"});
}

/*@hidden*/function threadOptionsClick(){
	Rednet.onThreadOptionsClick();
}

/*@hidden*/function threadThumbsClick(url){
	Rednet.onThreadThumbsClicked(url);
}

/*@hidden*/function praise(){
	Rednet.onPraise();
}

/*@hidden*/function userInfo(authorid){
	Rednet.onUserInfo(authorid);
}

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

//产看参与投票的人
/*@hidden*/function voterDetails(){
	Rednet.onVisitVoters();
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
	onRefresh(TEST_JSON_TXT,true);
}

var TEST_JSON_TXT={
    "Version": "4",
    "Charset": "UTF-8",
    "Variables": {
        "cookiepre": "31Iy_2132_",
        "auth": "0430Xs3qnMekTvY7NpYavLV7N2IK1Y9lZObldo/+LhIsz2VWvVHmcQIg7LTg7xI/cnLWLB70s30m+naL8h2BOg",
        "saltkey": "pB5rBR5O",
        "member_uid": "55",
        "member_username": "fuwei111",
        "member_avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
        "groupid": "1",
        "formhash": "c4b9e36f",
        "ismoderator": "1",
        "readaccess": "200",
        "notice": {
            "newpush": "0",
            "newpm": "0",
            "newprompt": "0",
            "newmypost": "1"
        },
        "allowperm": {
            "allowpost": "1",
            "allowreply": "1",
            "allowupload": {
                "jpg": "-1",
                "jpeg": "-1",
                "gif": "-1",
                "png": "-1",
                "mp3": "-1",
                "txt": "-1",
                "zip": "-1",
                "rar": "-1",
                "pdf": "-1"
            },
            "attachremain": {
                "size": "-1",
                "count": "-1"
            },
            "uploadhash": "6cf911c08819d4377925e2c30a99accb"
        },
        "thread": {
            "tid": "162",
            "fid": "41",
            "posttableid": "0",
            "typeid": "0",
            "sortid": "0",
            "readperm": "0",
            "price": "0",
            "author": "fuwei111",
            "authorid": "55",
            "subject": "不在家这",
            "dateline": "2015-7-23 16:30",
            "lastpost": "1438245012",
            "lastposter": "fuwei111",
            "views": "127",
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
            "status": "1024",
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
            "subjectenc": "%E4%B8%8D%E5%9C%A8%E5%AE%B6%E8%BF%99",
            "short_subject": "不在家这",
            "starttime": "1437640203",
            "remaintime": [
                "33058",
                "3",
                "48",
                "14"
            ],
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
                "pid": "309",
                "tid": "162",
                "first": "1",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "2015-7-23 16:30:03",
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
                "dbdateline": "1437640203",
                "message": "Ti5 我想所有人都在期盼着东方神秘的力量 奶起来！！！",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin",
				"attachlist":[
				{"attachurl":"http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small","aid":"125"}
				]
            },
            {
                "pid": "253",
                "tid": "118",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "7&nbsp;分钟前",
                "message": "HAHAH !ZOUQI LA !",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "2",
                "username": "fuwei111",
                "adminid": "0",
                "groupid": "11",
                "memberstatus": "0",
                "number": "2",
                "dbdateline": "1435994286",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "2","attachlist":[
				{"attachurl":"http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small","aid":"125"}
				]
            },
            {
                "pid": "253",
                "tid": "118",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "7&nbsp;分钟前",
                "message": "HAHAH !ZOUQI LA !",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "2",
                "username": "fuwei111",
                "adminid": "0",
                "groupid": "11",
                "memberstatus": "0",
                "number": "2",
                "dbdateline": "1435994286",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "2"
            }
        ],
        "allowpostcomment": [
            "1",
            "2"
        ],
        "comments": [],
        "commentcount": [],
        "ppp": "5",
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
                    "polloptionid": "55",
                    "polloption": "患得患失姐姐",
                    "votes": "1",
                    "width": "33%",
                    "percent": "33.33",
                    "color": "E92725",
                    "imginfo": []
                },
                "2": {
                    "polloptionid": "56",
                    "polloption": "或者主持词",
                    "votes": "2",
                    "width": "67%",
                    "percent": "66.67",
                    "color": "F27B21",
                    "imginfo": []
                }
            },
            "expirations": "4294967295",
            "multiple": "1",
            "maxchoices": "2",
            "voterscount": "2",
            "visiblepoll": "0",
            "allowvote": "",
            "remaintime": [
                "33058",
                "3",
                "48",
                "14"
            ],
            "allowvotepolled": "",
            "allowvotethread": "1",
            "allwvoteusergroup": "1",
            "overt": "1",
            "isrunnig": "running"
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
        "auth": "0430Xs3qnMekTvY7NpYavLV7N2IK1Y9lZObldo/+LhIsz2VWvVHmcQIg7LTg7xI/cnLWLB70s30m+naL8h2BOg",
        "saltkey": "pB5rBR5O",
        "member_uid": "55",
        "member_username": "fuwei111",
        "member_avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
        "groupid": "1",
        "formhash": "c4b9e36f",
        "ismoderator": "1",
        "readaccess": "200",
        "notice": {
            "newpush": "0",
            "newpm": "0",
            "newprompt": "0",
            "newmypost": "1"
        },
        "allowperm": {
            "allowpost": "1",
            "allowreply": "1",
            "allowupload": {
                "jpg": "-1",
                "jpeg": "-1",
                "gif": "-1",
                "png": "-1",
                "mp3": "-1",
                "txt": "-1",
                "zip": "-1",
                "rar": "-1",
                "pdf": "-1"
            },
            "attachremain": {
                "size": "-1",
                "count": "-1"
            },
            "uploadhash": "6cf911c08819d4377925e2c30a99accb"
        },
        "thread": {
            "tid": "173",
            "fid": "41",
            "posttableid": "0",
            "typeid": "0",
            "sortid": "0",
            "readperm": "0",
            "price": "0",
            "author": "fuwei111",
            "authorid": "55",
            "subject": "1111111",
            "dateline": "2015-7-30 16:21",
            "lastpost": "1438742479",
            "lastposter": "fuwei111",
            "views": "499999",
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
            "recommends": "0",
            "recommend_add": "0",
            "recommend_sub": "0",
            "heats": "0",
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
            "subjectenc": "1111111",
            "short_subject": "1111111",
            "starttime": "1438244465",
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
                "pid": "328",
                "tid": "173",
                "first": "1",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "6&nbsp;天前",
                "message": "111111111111<br />\r\n",
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
                "dbdateline": "1438244465",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "admin"
            },
            {
                "pid": "253",
                "tid": "118",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "7&nbsp;分钟前",
                "message": "HAHAH !ZOUQI LA !",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "2",
                "username": "fuwei111",
                "adminid": "0",
                "groupid": "11",
                "memberstatus": "0",
                "number": "2",
                "dbdateline": "1435994286",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "2"
            },
            {
                "pid": "253",
                "tid": "118",
                "first": "0",
                "author": "fuwei111",
                "authorid": "55",
                "dateline": "7&nbsp;分钟前",
                "message": "HAHAH !ZOUQI LA !",
                "anonymous": "0",
                "attachment": "0",
                "status": "1024",
                "replycredit": "0",
                "position": "2",
                "username": "fuwei111",
                "adminid": "0",
                "groupid": "11",
                "memberstatus": "0",
                "number": "2",
                "dbdateline": "1435994286",
                "avatar": "http://iwechat.pm.comsenz-service.com/uc_server/avatar.php?uid=55&size=small",
                "groupiconid": "2"
            }
        ],
        "allowpostcomment": [
            "1",
            "2"
        ],
        "comments": [],
        "commentcount": [],
        "ppp": "5",
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
                    "polloptionid": "57",
                    "polloption": "1111111",
                    "votes": "1",
                    "width": "100%",
                    "percent": "100.00",
                    "color": "E92725",
                    "imginfo": {
                        "aid": "9",
                        "poid": "57",
                        "tid": "173",
                        "pid": "328",
                        "uid": "55",
                        "filename": "鲨鱼.jpg",
                        "filesize": "136386",
                        "attachment": "201507/30/162047oyvubv1vfj7vmtd7.jpg",
                        "remote": "0",
                        "width": "794",
                        "thumb": "1",
                        "dateline": "1438244447",
                        "small": "data/attachment/forum/201507/30/162047oyvubv1vfj7vmtd7.jpg.thumb.jpg",
                        "big": "data/attachment/forum/201507/30/162047oyvubv1vfj7vmtd7.jpg"
                    }
                },
                "2": {
                    "polloptionid": "58",
                    "polloption": "22222222",
                    "votes": "0",
                    "width": "8px",
                    "percent": "0.00",
                    "color": "F27B21",
                    "imginfo": {
                        "aid": "10",
                        "poid": "58",
                        "tid": "173",
                        "pid": "328",
                        "uid": "55",
                        "filename": "2012-11-04 124224.jpg",
                        "filesize": "1170882",
                        "attachment": "201507/30/162059agu5sn70nvc4cczq.jpg",
                        "remote": "0",
                        "width": "1536",
                        "thumb": "1",
                        "dateline": "1438244459",
                        "small": "data/attachment/forum/201507/30/162059agu5sn70nvc4cczq.jpg.thumb.jpg",
                        "big": "data/attachment/forum/201507/30/162059agu5sn70nvc4cczq.jpg"
                    }
                }
            },
            "expirations": "1438828887",
            "multiple": "0",
            "maxchoices": "1",
            "voterscount": "1",
            "visiblepoll": "0",
            "allowvote": "",
            "remaintime": "",
            "allowvotepolled": "",
            "allowvotethread": "1",
            "allwvoteusergroup": "1",
            "overt": "0",
            "isrunnig": "running"
        },
        "forum": {
            "password": "0"
        }
    }
};


