//var HOST="http://rednet.pm.comsenz-service.com/";
//var HOST="http://bbs.rednet.cn/";
var HOST="http://10.0.6.58/";
var	is2GOr3GLoadImgs=true;//2/3G网络是否加载图片

$(document).ready(function(){
			   $("div.box03").click(function(){onThreadOptionsClick();});
			   $(".reply_botton_j a").click(function(){loadMore();});
			   $(".thread_details .P_share div a:eq(0)").click(function(){praise();});
			   $(".thread_details .P_share div a:eq(1)").click(function(){report();});
			   $(".thread_details .P_share div a:eq(2)").click(function(){share();});
			   $(".thread_details .P_share div a:eq(3)").click(function(){report();});
//			   test();

			});



/**
 *如果客户端webview on loading不能执行改方法,由android客户端调用(“javascript:onRefresh(JSON)”)
 *@param JSON 帖子详情数据
 *@param is2GOr3GLoadImgs 2/3G网络是否加载图片
 */
function onRefresh(JSON){
	if(JSON===undefined){
		return;
	}

	var forum = "discuz模板";
	 var subject = JSON.Variables.thread.subject;
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
//   	$(".header_title").html("<span class='title_type'> ["+type+"] </span>"+subject);
   	$(".forumName").html(JSON.Variables.thread.forumnames);
	$(".content_box .P_share div span:eq(0)").html(JSON.Variables.thread.recommend_add);
	$("#viewsCount").html(format(JSON.Variables.thread.views));
    $("#commentCount").html(JSON.Variables.thread.replies);
    var hasZan = JSON.Variables.thread.recommend;
    if(hasZan == 1){
        $(".p_zanBtn").attr("src","images/zanSmall_icon1.png");
    }else{
        $(".p_zanBtn").attr("src","images/praise.png");
    }
//	$("div.box01 img:first").addClass("without23G").attr({"url":JSON.Variables.postlist[0].avatar,"defaultRes":"images/noavatar_small.png"});
	$("div.box01 label:first").html(JSON.Variables.postlist[0].author);
	$("div.box01 label.p_date").html(JSON.Variables.postlist[0].dateline);
	$("div.box01").click(function(){userInfo(JSON.Variables.thread.authorid)});
	$("div.box01").click(function(){userInfo(JSON.Variables.thread.authorid)});

	if(!textIsNull(JSON.Variables.postlist)){
		$("div.thread_details").attr("id","rednet_anchor_id_"+JSON.Variables.postlist[0].pid);
		$("div#thread_content").html(JSON.Variables.postlist[0].message);
		$("div#thread_content img").each(function(){
			$(this).click(function(){threadThumbsClick($(this).attr("src"));})
					   .addClass("without23G")
				       .attr({"url":$(this).attr("src"),"defaultRes":"images/picture.png"})
				       .css({"width":"auto","height":"auto"});
		});

		if(!textIsNull(JSON.Variables.postlist[0].attachments)){ //帖子图片附件
           var str = "http://wsq.demo.comsenz-service.com/";
           var aid = "";
         var strlist = JSON.Variables.postlist[0].attachments;
           $(".thread_details .attachlist").removeAttr("hidden");
           for(var key in strlist){

               	 if(strlist[key].ext != "mp3"){
                              var imgUrl = strlist[key].attachmenturl;
//                               imgUrl = imgUrl + strlist[key].attachment;
//                               aid = strlist[key].aid;
                               $(".thread_details .attachlist ul")
                               					.append("<li><img aid='"+aid+"' style='max-height:80%;' url='"+imgUrl+"'/></li>");

//                               console.log("img="+imgUrl);
                 }

           }
             for(var key in strlist){
                 	if(strlist[key].ext == "mp3"){
                               	    var audioUrl = str + strlist[key].url;
                               	    audioUrl = audioUrl + strlist[key].attachment;
                               	    var audioTime = strlist[key].description;
                               	    var timeIndex = audioTime.indexOf("|");
                               	    audioTime = audioTime.substring(timeIndex+1);
                                                    var audioStr = "<li>"+
                                                                    "<audio controls='controls' class='audio' src='"+audioUrl+"'></audio>"+
                                                                    "<div class='box'>"+
                                                                    "<span id='audioImg' class='audioPause'></span></div>"+
                                                                    "<span class='audioTxt'>"+audioTime+"s'</span>"+
                                                                    "</div></li>";
                                                	$(".thread_details .attachlist ul").append(audioStr);

                               	}

             }
		/*语音贴操作*/
		var audioDom = $('.audio');
		var boxDom = $('.box');
		var audioObj = $('.audioPause');
		boxDom.each(function(index, item){

			setInterval(function(){
				if(audioDom[index].ended){
					audioObj.eq(index).removeClass('audioPlay');
				}
			},500);

			$(this).click(index, function(){

				if(audioDom[index].paused){
					audioDom[index].play();
					for(var i = 0; i < audioDom.length; i++){
						if(i != index){
							audioDom[i].pause();
							audioDom[i].currentTime = 0;
							audioObj.eq(i).removeClass('audioPlay');
						}
					}
					$(this).find(".audioPause").addClass('audioPlay');
				}else{
					audioDom[index].pause();
					$(this).find(".audioPause").removeClass('audioPlay');
                      //初始化音频文件
                      audioDom[index].currentTime = 0;
                  }
              })

})

				$(".thread_details .attachlist img").each(function(){
                				$(this).click(function(){threadThumbsClick($(this).attr("src"));})
                					   .addClass("without23G")
                					   .attr({"src":"images/picture.png","defaultRes":"images/picture.png"});
              	});
		}
	}
	onLoadReply(JSON,false);

}

/**
 *加载用户评论
 *isAppend true json数据从0开始 false从1开始因为0是用户发表的帖子内容
 */
function onLoadReply(JSON,isAppend){
	if(JSON===undefined){
		return;
	}
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
			li.attr({"pid":postlist[i].pid,"authorid":postlistitem.authorid,"id":"rednet_anchor_id_"+postlistitem.pid});//设置#锚点
			li.find("div.ageImg img")
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
//			if(position > 1){
//			var lou="";
////			 for(var i1 = 0;i1 < cache_custominfo_postno.length;i1++){
////			 alert(cache_custominfo_postno[i]);
////			 }
//
//			     if(position == 2){
//			     lou = "沙发";
//			     }else if(position == 3){
//			      lou = "板凳";
//			     }else if(position == 4){
//			     lou = "地板";
//			     }else{
//			     lou = position +"楼";
//			     }
//            	li.find("div.messContent p:eq(0) span").html(lou);
//            }
//			li.find("div.messContent p:eq(0) span").html("沙发");
			li.find("div.messContent p:eq(1)").html(postlistitem.message);
			li.find("div.messContent p:eq(1) img").each(function(){
				$(this).attr({"url":$(this).attr("src"),"defaultRes":"images/picture.png"})
					   .addClass("without23G")
					   .click(function(){threadThumbsClick($(this).attr("src"));});
			});
			li.find("div.P_share div.naTime_02 label.time").html(postlistitem.dateline);
			li.find("div.P_share a.huifu").click(function(){discussUser($(this).parents("li").attr("pid"));});
			li.find("div.P_share a.jubao").click(function(){reportComment($(this).parents("li").attr("pid"));});

            li.removeAttr("hidden");
            ul.removeAttr("hidden");
            ul.append(li);
            $(".thread_reply .attachlist").removeAttr("hidden");
			 var commendStr = "http://wsq.demo.comsenz-service.com/";
            var attach = postlistitem.attachments;
            if(!textIsNull(attach)){
                 li.find(".attachlist").removeAttr("hidden");
                for(var key in attach){
                     var commendImgUrl = commendStr + attach[key].url;
                      commendImgUrl = commendImgUrl + attach[key].attachment;
                      var aid = attach[key].aid;
                       li.find(".attachlist ul").append("<li><img aid='"+aid+"' style='max-height:80%;' url='"+commendImgUrl+"'/></li>");
//                 li.find(".attachlist ul").append("<li><img aid='"+aid+"' style='max-height:80%;' url='"+commendImgUrl)+"'/></li>");// attachurl
                    }
                    li.find(".attachlist img").each(function(){
                    					$(this).attr({"src":"images/picture.png","defaultRes":"images/picture.png"})
                    						   .addClass("without23G")
                    						   .click(function(){($(this).attr("src"));});
                    				});
            }



//			//附件
//			if(!textIsNull(postlistitem.attchlist)){
//				li.find(".attachlist").removeAttr("hidden");
//				var attachlist=postlistitem.attchlist;
//				for(var j=0;j<attachlist.length;j++){
//					li.find(".attachlist ul").append("<li><img aid='"+attachlist[j].aid+"' url='"+(HOST+attachlist[j].thumburl)+"'/></li>");// attachurl
//				}
//				li.find(".attachlist img").each(function(){
//					$(this).attr({"src":"images/picture.png","defaultRes":"images/picture.png"})
//						   .addClass("without23G")
//						   .click(function(){($(this).attr("src"));});
//				});
//			}


		}
    }
//	if(JSON.Variables.thread.replies==0){
//		$(".p_vote").attr("hidden");
//	}else{
//		$(".p_vote").removeAttr("hidden");
//	}
//    if(JSON.Variables.thread.replies==0&&JSON.Variables.postlist.length<2){
////        $(".p_vote").attr({style:"display:none"});
//        enableLoadMore(true);
//    }else if((JSON.Variables.postlist.length-1)==0){
//        enableLoadMore(true);
//    }else{
//		enableLoadMore(false);
//	}
	checkImageLoadable();
	//alert(document.body.offsetHeight+","+window.innerHeight);
}

/**已加载玩所有评论*/
function onLoadOver(){
//	enableLoadMore(false);
}

/**2/3G网络是否加载图片, json数据中包含的img标签再判断2/3g加载图前先做默认图地址处理*/
function setImgLoadable(loadable){
    if(is2GOr3GLoadImgs!=loadable){
       is2GOr3GLoadImgs=loadable;
	    checkImageLoadable();
    }
}

/*@hidden*/function onThreadOptionsClick(){
	Rednet.onThreadOptionsClick();
}

/**
 *回复用户评论或帖子成功 客户端回调webview.loadUrl("javascript:onDiscussSuccess(JSON,pid);")
 *@param JSON 页面信息包含用户所有评论 如果为空则页面不会自动跳到用户回复的地方
 *@param pid 回复成功后返回的pid 用字符串形式传入
 */
function onDiscussSuccess(JSON,pid){
	if (!textIsNull(JSON)){
        onRefresh(JSON);
		if(!textIsNull(pid)){
			window.location.href="#rednet_anchor_id_"+pid;
		}
	}
}

/*支持帖子成功后更新支持者数量 */
function onPraiseSuccess(){
	var el=$(".content_box .P_share div span:eq(0)");
	el.html(parseInt(el.html())+1);
}

/*@hidden*/function checkImageLoadable(){
	$(".without23G").each(function(){
		$(this).attr("src",is2GOr3GLoadImgs?$(this).attr("url"):$(this).parent().hide());
		});
}

//回调android客户端中 @JavascriptInterface onLoadMore方法
/*@hidden*/function loadMore(){
	Rednet.onLoadMore();
}

/*@hidden*/function enableLoadMore(enable){
	if(enable){
		$("p.reply_botton_j").removeAttr("hidden");
	}else{
		$("p.reply_botton_j").attr("hidden","hidden");
	}
}

/*@hidden*/function threadThumbsClick(url){
	if(is2GOr3GLoadImgs){
		Rednet.onThreadThumbsClicked(url);
	}
}

/*@hidden*/function userInfo(authorid){
	Rednet.onUserInfo(authorid);
}

//回复用户评论
/*@hidden*/function discussUser(pid){
	Rednet.onDiscussUser(pid);
}

//举报用户评论
/*@hidden*/function reportComment(pid){
	Rednet.onReportComment(pid);
}

//支持帖子 call @JavascriptInterface onPraise() method
/*@hidden*/function praise(){
	Rednet.onPraise();
}

//帖子分享 call @JavascriptInterface onShare() method
/*@hidden*/function share(){
	Rednet.onShare();
}

//帖子举报 call @JavascriptInterface onReport() method
/*@hidden*/function report(){
	Rednet.onReport();
}

/*@hidden*/
function format(views){
	if(views>9999){
		return Number(views/10000).toFixed(1)+"w";
	}else{
		return views;
	}
}

/*@hidden*/function textIsNull(v){
	return v===undefined||v.length==0;
}

//测试js 页面样式
var test=function(){
	onRefresh(TEST_THREAD_JSON);
}

var TEST_THREAD_JSON={
                         "Version": "5",
                         "Charset": "UTF-8",
                         "Variables": {
                             "cookiepre": "jVSp_2132_",
                             "auth": null,
                             "saltkey": "VaA57XA4",
                             "member_uid": "0",
                             "member_username": "",
                             "member_avatar": "http://www.jinbifun.com/uc_server/data/avatar/000/00/00/00_avatar_small.jpg",
                             "groupid": "7",
                             "formhash": "a2401daa",
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
                                 "uploadhash": "34d8060a607785b9f5a8d1519d91dabe"
                             },
                             "activity_setting": {
                                 "activityfield": {
                                     "mobile": "手机",
                                     "qq": "QQ",
                                     "gender": "性别"
                                 },
                                 "activitytype": [
                                     "出外郊游",
                                     "公益活动",
                                     "线上活动",
                                     "自驾出行"
                                 ]
                             },
                             "thread": {
                                 "tid": "4247267",
                                 "fid": "72933",
                                 "posttableid": "0",
                                 "typeid": "65535",
                                 "sortid": "0",
                                 "readperm": "0",
                                 "price": "0",
                                 "author": "基建",
                                 "authorid": "331728",
                                 "subject": "投资碎言",
                                 "dateline": "1482238328",
                                 "lastpost": "2017-10-29 18:38",
                                 "lastposter": "苦豆花",
                                 "views": "278384",
                                 "replies": "684",
                                 "displayorder": "0",
                                 "highlight": "0",
                                 "digest": "0",
                                 "rate": "0",
                                 "special": "0",
                                 "attachment": "2",
                                 "moderated": "1",
                                 "closed": "0",
                                 "stickreply": "0",
                                 "recommends": "0",
                                 "recommend_add": "0",
                                 "recommend_sub": "0",
                                 "heats": "119",
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
                                 "maxposition": "811",
                                 "bgcolor": "",
                                 "comments": "0",
                                 "hidden": "0",
                                 "threadtable": "forum_thread",
                                 "threadtableid": "0",
                                 "posttable": "forum_post",
                                 "allreplies": "684",
                                 "is_archived": "",
                                 "archiveid": "0",
                                 "subjectenc": "%E6%8A%95%E8%B5%84%E7%A2%8E%E8%A8%80",
                                 "short_subject": "投资碎言",
                                 "recommendlevel": "0",
                                 "heatlevel": "2",
                                 "relay": "0",
                                 "forumnames": "财富梦想",
                                 "ordertype": "0",
                                 "favorited": "0",
                                 "recommend": "0"
                             },
                             "fid": "72933",
                             "postlist": [
                                 {
                                     "pid": "7997581",
                                     "tid": "4247267",
                                     "first": "1",
                                     "author": "基建",
                                     "authorid": "331728",
                                     "dateline": "2016-12-20 20:52",
                                     "message": "先立个贴，记录下各种琐碎感言。 慢慢更新。 <img src=\"http://www.jinbifun.com/static/image/smiley/default/lol.gif\" />",
                                     "anonymous": "0",
                                     "attachment": "0",
                                     "status": "4",
                                     "replycredit": "0",
                                     "position": "170",
                                     "username": "基建",
                                     "adminid": "0",
                                     "groupid": "26",
                                     "memberstatus": "0",
                                     "number": "1",
                                     "dbdateline": "1482238328",
                                     "attachments": [],
                                     "attachlist": [],
                                     "imagelist": [],
                                     "groupiconid": "梅里雪山级会员",
                                     "avatar": "http://www.jinbifun.com/uc_server/data/avatar/000/33/17/28_avatar_small.jpg"
                                 },
                                 {
                                     "pid": "7997618",
                                     "tid": "4247267",
                                     "first": "0",
                                     "author": "基建",
                                     "authorid": "331728",
                                     "dateline": "2016-12-20 21:38",
                                     "message": "这个版块能审核出来，不容易啊。。。<img src=\"http://www.jinbifun.com/static/image/smiley/default/shocked.gif\" /><img src=\"http://www.jinbifun.com/static/image/smiley/default/shocked.gif\" /><br />\r\n<br />\r\n<br />\r\n谈下现在的深市和创业板，为什么说现在深市和创业板弱爆呢？ <br />\r\n形态上看看嘛，这种形态要变长期的多头慢慢熬吧， 12月12日那根破位的大阴就已经决定了深市又该按熊市策略操盘了。。。。<br />\r\n<div class=\"img\"><img src=\"http://www.jinbifun.com/data/attachment/forum/201612/20/211530s79arttuazawsq96.jpg\" attach=\"7997618\" /></div><br />\n<br />\n<br />\n就像地震一样，接下来还有余震，下跌动能还没完，任何短期内的上涨都按反弹处理。<br />\n<br />\n<br />\n",
                                     "anonymous": "0",
                                     "attachment": "0",
                                     "status": "4",
                                     "replycredit": "0",
                                     "position": "171",
                                     "username": "基建",
                                     "adminid": "0",
                                     "groupid": "26",
                                     "memberstatus": "0",
                                     "number": "2",
                                     "dbdateline": "1482241124",
                                     "attachments": [],
                                     "attachlist": [],
                                     "imagelist": [],
                                     "groupiconid": "梅里雪山级会员",
                                     "avatar": "http://www.jinbifun.com/uc_server/data/avatar/000/33/17/28_avatar_small.jpg"
                                 },
                                 {
                                     "pid": "7997714",
                                     "tid": "4247267",
                                     "first": "0",
                                     "author": "基建",
                                     "authorid": "331728",
                                     "dateline": "2016-12-20 23:42",
                                     "message": "人的观念总是滞后，中国已经高速发展了30多年，还有许多人依然用落后和贫穷去审视它。 <br />\r\n同样市场也是如此，多头已经变空头后的一段时间，人们还在抱着多头的希望去看待它。",
                                     "anonymous": "0",
                                     "attachment": "0",
                                     "status": "4",
                                     "replycredit": "0",
                                     "position": "172",
                                     "username": "基建",
                                     "adminid": "0",
                                     "groupid": "26",
                                     "memberstatus": "0",
                                     "number": "3",
                                     "dbdateline": "1482248562",
                                     "attachments": [],
                                     "attachlist": [],
                                     "imagelist": [],
                                     "groupiconid": "梅里雪山级会员",
                                     "avatar": "http://www.jinbifun.com/uc_server/data/avatar/000/33/17/28_avatar_small.jpg"
                                 },
                                 {
                                     "pid": "7997954",
                                     "tid": "4247267",
                                     "first": "0",
                                     "author": "基建",
                                     "authorid": "331728",
                                     "dateline": "2016-12-21 08:24",
                                     "message": "亏钱，晓不得咋个亏的，问题大了。<br />\r\n赚钱，晓不得咋个赚的，问题也大。<br />\n<br />\n<br />\n只能说明你没有适合你自己的交易系统，买卖还处于随机状态中。<br />\n<br />\n<br />\n即便你有了交易系统，<br />\r\n不按规则交易，亏钱了，问题大了。<br />\r\n不按规则交易，赚钱了，问题更大。<br />\r\n按规则交易，亏钱了，好！<br />\r\n按规则交易，赚钱了，好上加好！<br />\n<br />\n<br />\n这里的投资老手应该都清楚这些道理。。。",
                                     "anonymous": "0",
                                     "attachment": "0",
                                     "status": "4",
                                     "replycredit": "0",
                                     "position": "173",
                                     "username": "基建",
                                     "adminid": "0",
                                     "groupid": "26",
                                     "memberstatus": "0",
                                     "number": "4",
                                     "dbdateline": "1482279857",
                                     "attachments": [],
                                     "attachlist": [],
                                     "imagelist": [],
                                     "groupiconid": "梅里雪山级会员",
                                     "avatar": "http://www.jinbifun.com/uc_server/data/avatar/000/33/17/28_avatar_small.jpg"
                                 },
                                 {
                                     "pid": "7998480",
                                     "tid": "4247267",
                                     "first": "0",
                                     "author": "苦豆花",
                                     "authorid": "152166",
                                     "dateline": "2016-12-21 15:04",
                                     "message": "<div class=\"reply_wrap\"><font color=\"#999999\">基建 发表于 2016-12-20 21:38</font> <a href=\"http://www.jinbifun.com/forum.php?mod=redirect&amp;goto=findpost&amp;pid=7997618&amp;ptid=4247267\"><div class=\"img\"><img src=\"http://www.jinbifun.com/static/image/common/back.gif\" /></div></a><br />\r\n这个版块能审核出来，不容易啊。。。</div><br />\n哈哈，等我请领导们尽快放权啊。",
                                     "anonymous": "0",
                                     "attachment": "0",
                                     "status": "4",
                                     "replycredit": "0",
                                     "position": "168",
                                     "username": "苦豆花",
                                     "adminid": "3",
                                     "groupid": "3",
                                     "memberstatus": "0",
                                     "number": "5",
                                     "dbdateline": "1482303882",
                                     "attachments": [],
                                     "attachlist": [],
                                     "imagelist": [],
                                     "groupiconid": "版主",
                                     "avatar": "http://www.jinbifun.com/uc_server/data/avatar/000/15/21/66_avatar_small.jpg"
                                 },
                                 {
                                     "pid": "7998481",
                                     "tid": "4247267",
                                     "first": "0",
                                     "author": "苦豆花",
                                     "authorid": "152166",
                                     "dateline": "2016-12-21 15:05",
                                     "message": "<div class=\"reply_wrap\"><font color=\"#999999\">基建 发表于 2016-12-20 23:42</font> <a href=\"http://www.jinbifun.com/forum.php?mod=redirect&amp;goto=findpost&amp;pid=7997714&amp;ptid=4247267\"><div class=\"img\"><img src=\"http://www.jinbifun.com/static/image/common/back.gif\" /></div></a><br />\r\n人的观念总是滞后，中国已经高速发展了30多年，还有许多人依然用落后和贫穷去审视它。 <br />\r\n同样市场也是如此， ...</div><br />\n哈哈，这叫惯性啊！",
                                     "anonymous": "0",
                                     "attachment": "0",
                                     "status": "4",
                                     "replycredit": "0",
                                     "position": "169",
                                     "username": "苦豆花",
                                     "adminid": "3",
                                     "groupid": "3",
                                     "memberstatus": "0",
                                     "number": "6",
                                     "dbdateline": "1482303925",
                                     "attachments": [],
                                     "attachlist": [],
                                     "imagelist": [],
                                     "groupiconid": "版主",
                                     "avatar": "http://www.jinbifun.com/uc_server/data/avatar/000/15/21/66_avatar_small.jpg"
                                 },
                                 {
                                     "pid": "7998485",
                                     "tid": "4247267",
                                     "first": "0",
                                     "author": "苦豆花",
                                     "authorid": "152166",
                                     "dateline": "2016-12-21 15:06",
                                     "message": "<div class=\"reply_wrap\"><font color=\"#999999\">基建 发表于 2016-12-21 08:24</font> <a href=\"http://www.jinbifun.com/forum.php?mod=redirect&amp;goto=findpost&amp;pid=7997954&amp;ptid=4247267\"><div class=\"img\"><img src=\"http://www.jinbifun.com/static/image/common/back.gif\" /></div></a><br />\r\n亏钱，晓不得咋个亏的，问题大了。<br />\r\n赚钱，晓不得咋个赚的，问题也大。</div><br />\n哈哈，知道是一回事，执行是另一回事。",
                                     "anonymous": "0",
                                     "attachment": "0",
                                     "status": "4",
                                     "replycredit": "0",
                                     "position": "167",
                                     "username": "苦豆花",
                                     "adminid": "3",
                                     "groupid": "3",
                                     "memberstatus": "0",
                                     "number": "7",
                                     "dbdateline": "1482303987",
                                     "attachments": [],
                                     "attachlist": [],
                                     "imagelist": [],
                                     "groupiconid": "版主",
                                     "avatar": "http://www.jinbifun.com/uc_server/data/avatar/000/15/21/66_avatar_small.jpg"
                                 },
                                 {
                                     "pid": "7998894",
                                     "tid": "4247267",
                                     "first": "0",
                                     "author": "基建",
                                     "authorid": "331728",
                                     "dateline": "2016-12-21 19:05",
                                     "message": "<div class=\"reply_wrap\"><font color=\"#999999\">苦豆花 发表于 2016-12-21 15:06</font> <a href=\"http://www.jinbifun.com/forum.php?mod=redirect&amp;goto=findpost&amp;pid=7998485&amp;ptid=4247267\"><div class=\"img\"><img src=\"http://www.jinbifun.com/static/image/common/back.gif\" /></div></a><br />\r\n哈哈，知道是一回事，执行是另一回事。</div><br />\n对啊，最后还是心态的问题。 <img src=\"http://www.jinbifun.com/static/image/smiley/default/lol.gif\" />",
                                     "anonymous": "0",
                                     "attachment": "0",
                                     "status": "4",
                                     "replycredit": "0",
                                     "position": "165",
                                     "username": "基建",
                                     "adminid": "0",
                                     "groupid": "26",
                                     "memberstatus": "0",
                                     "number": "8",
                                     "dbdateline": "1482318353",
                                     "attachments": [],
                                     "attachlist": [],
                                     "imagelist": [],
                                     "groupiconid": "梅里雪山级会员",
                                     "avatar": "http://www.jinbifun.com/uc_server/data/avatar/000/33/17/28_avatar_small.jpg"
                                 },
                                 {
                                     "pid": "7998917",
                                     "tid": "4247267",
                                     "first": "0",
                                     "author": "基建",
                                     "authorid": "331728",
                                     "dateline": "2016-12-21 19:28",
                                     "message": "市场一般会有相关性，<br />\n<br />\n<br />\n比如，美元和黄金，<br />\r\n债券和股市，<br />\r\n欧股，日股与美股，<br />\r\n只看一个市场，是不能获得预判信息的。<br />\r\n多个市场都有了解，相互确认。<br />\r\n<br />\r\n<br />\r\n其中谈谈重要的A股，今年A股和铜的走势相关性较高，基本面的原因主要是中国需要大量的原材料，中国经济的走势对铜的影响大。<br />\n<br />\n<br />\n铜在10月21日开始飙升，A股在10月10日开始了一波行情。 铜价对中国经济的变化滞后了一点，但还算同步。<br />\r\n铜在11月28日结束了这个上升波段，A股在11月29日结束波段，还真巧了，<img src=\"http://www.jinbifun.com/static/image/smiley/default/lol.gif\" /><br />\n<br />\n<br />\n昨天12月20日，铜价小级别见底，并反抽，今天12月21日A股就跟着反抽。",
                                     "anonymous": "0",
                                     "attachment": "0",
                                     "status": "4",
                                     "replycredit": "0",
                                     "position": "164",
                                     "username": "基建",
                                     "adminid": "0",
                                     "groupid": "26",
                                     "memberstatus": "0",
                                     "number": "9",
                                     "dbdateline": "1482319713",
                                     "attachments": [],
                                     "attachlist": [],
                                     "imagelist": [],
                                     "groupiconid": "梅里雪山级会员",
                                     "avatar": "http://www.jinbifun.com/uc_server/data/avatar/000/33/17/28_avatar_small.jpg"
                                 },
                                 {
                                     "pid": "7999172",
                                     "tid": "4247267",
                                     "first": "0",
                                     "author": "基建",
                                     "authorid": "331728",
                                     "dateline": "2016-12-22 00:42",
                                     "message": "很多同事和朋友问过我关于学习K线的看法，对于K线我的看法有几点：<br />\n<br />\n<br />\n第一，K线随便了解下收盘，开盘，上影下影就足够了。 新手一开始就学习K线组合，K线形状等于误入歧途。 你随便去找几个锤子线看看，有多少概率是可以抄底的？ 书上和网上展示给你的都是马后炮和完美的答案。 <br />\n<br />\n<br />\n第二，没有K线，我照样可以看盘。我看的是结构和形态，K线仅仅是辅助看一看价格的细微变化，体验这个结构点上多空的力量如何，并不是关键的因素。<br />\r\n做个比喻，本质与表象，本质要靠表象表现出来。表象可以是很多种。市场的本质是众人合力形成的结构和走势，K线仅仅是体现这种结构的表象之一。 市场的结构还可以用简单的线段，用美式线，甚至用数字表现出来。<br />\r\n你应该去抓住本质去，不要被几根K线就迷住。<br />\n<br />\n<br />\n第三，并不是说K线不好，而是说初学者不应沉迷于研究K线形态和组合去，这样是本末倒置。 成熟的投资者一定是把握结构和走势，然后利用K线的价格变化来做入场或出场的辅助判断。<br />\n<br />\n<br />\n第四，让你少走点弯路，什么精通K线之类的书，直接丢了吧，我都怀疑书的作者自己操盘吗？还是写书赚稿费啊？ 哈哈。",
                                     "anonymous": "0",
                                     "attachment": "0",
                                     "status": "4",
                                     "replycredit": "0",
                                     "position": "5",
                                     "username": "基建",
                                     "adminid": "0",
                                     "groupid": "26",
                                     "memberstatus": "0",
                                     "number": "10",
                                     "dbdateline": "1482338555",
                                     "attachments": [],
                                     "attachlist": [],
                                     "imagelist": [],
                                     "groupiconid": "梅里雪山级会员",
                                     "avatar": "http://www.jinbifun.com/uc_server/data/avatar/000/33/17/28_avatar_small.jpg"
                                 }
                             ],
                             "allowpostcomment": [],
                             "comments": [],
                             "commentcount": [],
                             "ppp": "10",
                             "setting_rewriterule": {
                                 "portal_topic": "topic-{name}.html",
                                 "portal_article": "article-{id}-{page}.html",
                                 "forum_forumdisplay": "forum-{fid}-{page}.html",
                                 "forum_viewthread": "thread-{tid}-{page}-{prevpage}.html",
                                 "group_group": "group-{fid}-{page}.html",
                                 "home_space": "space-{user}-{value}.html",
                                 "home_blog": "blog-{uid}-{blogid}.html",
                                 "forum_archiver": "{action}-{value}.html"
                             },
                             "setting_rewritestatus": [
                                 "portal_topic",
                                 "portal_article",
                                 "forum_forumdisplay",
                                 "forum_viewthread",
                                 "group_group",
                                 "home_space",
                                 "plugin"
                             ],
                             "forum_threadpay": "",
                             "cache_custominfo_postno": [
                                 "<sup>#</sup>",
                                 ""
                             ],
                             "forum": {
                                 "password": "0",
                                 "threadtypes": {
                                     "required": "1",
                                     "listable": "1",
                                     "prefix": "1",
                                     "types": {
                                         "73381": "产经",
                                         "73382": "股票",
                                         "73383": "基金",
                                         "73384": "电子商务",
                                         "73385": "理财",
                                         "73386": "外汇",
                                         "73387": "期货",
                                         "73388": "保险",
                                         "73389": "信托",
                                         "73390": "藏品"
                                     },
                                     "icons": {
                                         "73381": "",
                                         "73382": "",
                                         "73383": "",
                                         "73384": "",
                                         "73385": "",
                                         "73386": "",
                                         "73387": "",
                                         "73388": "",
                                         "73389": "",
                                         "73390": ""
                                     },
                                     "moderators": {
                                         "73381": null,
                                         "73382": null,
                                         "73383": null,
                                         "73384": null,
                                         "73385": null,
                                         "73386": null,
                                         "73387": null,
                                         "73388": null,
                                         "73389": null,
                                         "73390": null
                                     }
                                 }
                             }
                         }
                     };