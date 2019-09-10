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
			   //test();

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
//	var authorid =JSON.Variables.thread.authorid;
    if(!textIsNull(JSON.Variables.list)){
   for(var i = 0;i < JSON.Variables.list.length;i++){
//      if( JSON.Variables.list[i].authorid != authorid){
           var datetime = JSON.Variables.list[i].dateline;
           var message =JSON.Variables.list[i].message;
           var avatar =JSON.Variables.list[i].avatar;
           var author =JSON.Variables.list[i].author;
           var attach =JSON.Variables.list[i].imagelists;
           var authorId = JSON.Variables.list[i].authorid;
           var commendStr = "http://bbs.wsq.comsenz-service.com/newwsq/";
           var append_img_box = '';
           if(!textIsNull(attach)){
                append_img_box = append_img_box + "<div class='postImgBox'>";
               for(var key in attach){
               var commendImgUrl = commendStr + attach[key].url;
                  commendImgUrl = commendImgUrl + attach[key].attachment;
                    if(!textIsNull(attach[key].url) && !textIsNull(attach[key].attachment)){
                    append_img_box = append_img_box + "<a class='postImg'><img class='without23G' src='"+commendImgUrl+"'></a>";
                    }

                    }
                    append_img_box = append_img_box + "</div>";
             }
             var append_box = "<div class='itPostItem'><div class='userAvatar'><a><img  authid='"+authorId+"' src='"+avatar+"'/></a><a class='nicking'>"+author+"</a><span class='time pull-right'>"+datetime+"</span></div><div class='itContent'>"+message;

              $(".userAvatar img").attr(is2GOr3GLoadImgs?"src":"url",is2GOr3GLoadImgs?avatar:"images/noavatar_small.png");
             append_box = append_box + append_img_box;
             append_box = append_box + "</div></div><div class='margin10px'></div>";
             $(".interactionBox").append(append_box);

//       }
     }
   }
    //头像的点击事件

                $(".userAvatar img").each(function(index, item){
                    $(this).click(function(){
//                         alert($(this).attr('authid'));
                         userInfo($(this).attr('authid'));
                    });
                })

              /*  $(".userAvatar img")

                   .on('click', function() {
                  alert($(this).attr('authorid'));
                  })*/
//	onLoadReply(JSON,false);
//图片的点击事件

 $(".postImg img").attr({"url":$(this).attr("src"),"defaultRes":"images/picture.png"})
   .addClass("without23G")
   .on('click', function() {
//       alert($(this).attr('src'));
       threadThumbsClick($(this).attr('src'));
  })
  checkImageLoadable();
}

/**
 *加载用户评论
 *isAppend true json数据从0开始 false从1开始因为0是用户发表的帖子内容
 */
function onLoadReply(JSON,isAppend){
	if(JSON===undefined){
		return;
	}
    var postlist=JSON.Variables.list;
//    var authorid =JSON.Variables.thread.authorid;
      if(!textIsNull(JSON.Variables.list)){
      for(var i = 0;i < JSON.Variables.list.length;i++){
   //      if( JSON.Variables.list[i].authorid != authorid){
              var datetime = JSON.Variables.list[i].dateline;
              var message =JSON.Variables.list[i].message;
              var avatar =JSON.Variables.list[i].avatar;
              var author =JSON.Variables.list[i].author;
              var attach =JSON.Variables.list[i].imagelists;
              var authorId = JSON.Variables.list[i].authorid;
              var commendStr = "http://bbs.wsq.comsenz-service.com/newwsq/";
              var append_img_box = '';
              if(!textIsNull(attach)){
                   append_img_box = append_img_box + "<div class='postImgBox'>";
                  for(var key in attach){
                  var commendImgUrl = commendStr + attach[key].url;
                      commendImgUrl = commendImgUrl + attach[key].attachment;
                       if(!textIsNull(attach[key].url) && !textIsNull(attach[key].attachment)){
                          append_img_box = append_img_box + "<a class='postImg'><img class='without23G' src='"+commendImgUrl+"'></a>";
                         }


                       }
                       append_img_box = append_img_box + "</div>";
                }
                var append_box = "<div class='itPostItem'><div class='userAvatar'><a><img  authid='"+authorId+"' src='"+avatar+"'/></a><a class='nicking'>"+author+"</a><span class='time pull-right'>"+datetime+"</span></div><div class='itContent'>"+message;

                 $(".userAvatar img").attr(is2GOr3GLoadImgs?"src":"url",is2GOr3GLoadImgs?avatar:"images/noavatar_small.png");
                append_box = append_box + append_img_box;
                append_box = append_box + "</div></div><div class='margin10px'></div>";
                $(".interactionBox").append(append_box);

   //       }
        }
      }

 //头像的点击事件

                $(".userAvatar img").each(function(index, item){
                    $(this).click(function(){
//                         alert($(this).attr('authid'));
                         userInfo($(this).attr('authid'));
                    });
                })
//图片的点击事件
   $(".postImg img").attr({"url":$(this).attr("src"),"defaultRes":"images/picture.png"})
     .addClass("without23G")
     .on('click', function() {
  //       alert($(this).attr('src'));
         threadThumbsClick($(this).attr('src'));
    })
    checkImageLoadable();
}

/**已加载玩所有评论*/
function onLoadOver(){
	enableLoadMore(false);
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

//var TEST_THREAD_JSON={
//    "Version": "4",
//    "Charset": "GBK",
//    "Variables": {
//        "cookiepre": "FKPl_27d4_",
//        "auth": null,
//        "saltkey": "B5nWl9zG",
//        "member_uid": "0",
//        "member_username": "",
//        "member_avatar": "http://u.rednet.cn/images/noavatar_middle.gif",
//        "groupid": "7",
//        "formhash": "5b15434a",
//        "ismoderator": "",
//        "readaccess": "20",
//        "notice": {
//            "newpush": "0",
//            "newpm": "0",
//            "newprompt": "0",
//            "newmypost": "0"
//        },
//        "allowperm": {
//            "allowpost": "0",
//            "allowreply": "0",
//            "uploadhash": "c6ba01ec77370fc7a8549ec92f6e1505"
//        },
//        "thread": {
//            "tid": "46233630",
//            "fid": "69",
//            "posttableid": "0",
//            "typeid": "0",
//            "sortid": "0",
//            "readperm": "0",
//            "price": "0",
//            "author": "卡拉没有K",
//            "authorid": "5275406",
//            "subject": "我们在路上——美丽乡村发现之旅湘潭行",
//            "dateline": "1478313279",
//            "lastpost": "2016-11-12 09:04",
//            "lastposter": "吧主麻国",
//            "views": "216895",
//            "replies": "79",
//            "displayorder": "1",
//            "highlight": "42",
//            "digest": "0",
//            "rate": "0",
//            "special": "0",
//            "attachment": "2",
//            "moderated": "1",
//            "closed": "0",
//            "stickreply": "0",
//            "recommends": "5",
//            "recommend_add": "3",
//            "recommend_sub": "0",
//            "heats": "58",
//            "status": "32",
//            "isgroup": "0",
//            "favtimes": "0",
//            "sharetimes": "0",
//            "stamp": "4",
//            "icon": "-1",
//            "pushedaid": "0",
//            "cover": "0",
//            "replycredit": "0",
//            "relatebytag": "0",
//            "maxposition": "84",
//            "bgcolor": "",
//            "comments": "0",
//            "hidden": "0",
//            "audit": "0",
//            "send": "0",
//            "threadtable": "forum_thread",
//            "threadtableid": "0",
//            "posttable": "forum_post",
//            "addviews": "18",
//            "allreplies": "79",
//            "is_archived": "",
//            "archiveid": "0",
//            "subjectenc": "%CE%D2%C3%C7%D4%DA%C2%B7%C9%CF%A1%AA%A1%AA%C3%C0%C0%F6%CF%E7%B4%E5%B7%A2%CF%D6%D6%AE%C2%C3%CF%E6%CC%B6%D0%D0",
//            "short_subject": "我们在路上——美丽乡村发现之旅湘潭行",
//            "recommendlevel": "0",
//            "heatlevel": "1",
//            "relay": "0",
//            "ordertype": "0",
//            "recommend": "0"
//        },
//        "fid": "69",
//        "postlist": [
//            {
//                "pid": "92394035",
//                "tid": "46233630",
//                "first": "1",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 10:34",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/103817k47j844l7jzbu4nb.jpg\" attach=\"92394035\" /></div><br />\r\n<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/103211tggg997kipx2ppl7.jpg\" attach=\"92394035\" /></div><br />\r\n秋高气爽，我们相约红网，开启美丽乡村发现之旅湘潭行。网友们在红网门前集合准备出发。<br />\r\n<br />\r\n<br />\r\n————————————————————————————————————————<br />\r\n<font color=\"#ff0000\"><br />\r\n<a href=\"http://bbs.rednet.cn/plugin.php?id=muquan_whn&amp;topic=mlxcfxzl16830\">美丽乡村发现之旅专题</a></font><br />\r\n<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/112251b2zkz9vnkvrq9fro.jpg\" attach=\"92394035\" /></div><br />\n<br />\n<br />\n<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201607/18/160937p77vi08mze7gc7hp.jpg\" /></div><br />\n<br />\n<br />\n<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201607/18/145703nes0tqu0l8mgblje.jpg\" /></div><br />\r\n<br />\r\n<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "1",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "1",
//                "dbdateline": "1478313279",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8927758",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8927758&size=360x480&type=fixnone&nocache=yes&key=316607ff53812612"
//                    },
//                    {
//                        "aid": "8927794",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8927794&size=360x480&type=fixnone&nocache=yes&key=0eca52983279b932"
//                    },
//                    {
//                        "aid": "8928107",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928107&size=360x480&type=fixnone&nocache=yes&key=f7ab4c508f4f12ec"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92394071",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 10:37",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/103624tkfiecf2y5kzp9o3.jpg\" attach=\"92394071\" /></div>车上的美女网友略显羞涩<img src=\"http://bbs.rednet.cn/static/image/smiley/default/titter.gif\" /><br />\r\n<br />\r\n<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "2",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "2",
//                "dbdateline": "1478313471",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8927780",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8927780&size=360x480&type=fixnone&nocache=yes&key=6a6cc2a8dad0c9b4"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92394157",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 10:43",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/104139gz80k4tz4iq3h88h.jpg\" attach=\"92394157\" /></div><br />\r\n阳光正好，乡村如画<br />\r\n<br />\r\n<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "3",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "3",
//                "dbdateline": "1478313819",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8927832",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8927832&size=360x480&type=fixnone&nocache=yes&key=78005619a8e4cd67"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92394170",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 10:44",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/104415b951ljepju7295m5.jpg\" attach=\"92394170\" /></div><br />\r\n大家猜猜，这个玩意是用什么做的？<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "4",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "4",
//                "dbdateline": "1478313888",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8927856",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8927856&size=360x480&type=fixnone&nocache=yes&key=eafa443b98183b46"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92394174",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 10:45",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/104513t26tow96zewo7mmz.jpg\" attach=\"92394174\" /></div><br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "5",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "5",
//                "dbdateline": "1478313916",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8927862",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8927862&size=360x480&type=fixnone&nocache=yes&key=8c6f6d91af6fce24"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92394621",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 11:21",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/111810ndzwweedds60j6ej.jpg\" attach=\"92394621\" /></div><br />\r\n<br />\r\n<br />\r\n活动旗帜在风中飘摇，与蓝天相映，自成一景<br />\r\n<br />\r\n<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "7",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "6",
//                "dbdateline": "1478316062",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8928029",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928029&size=360x480&type=fixnone&nocache=yes&key=20bffe1a10690f7c"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92394636",
//                "tid": "46233630",
//                "first": "0",
//                "author": "52p9",
//                "authorid": "50502",
//                "dateline": "2016-11-5 11:21",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/112131t6bgub8eeu8pedpg.jpg\" attach=\"92394636\" /></div><br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "8",
//                "username": "52p9",
//                "adminid": "0",
//                "groupid": "12",
//                "memberstatus": "0",
//                "number": "7",
//                "dbdateline": "1478316096",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8928060",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928060&size=360x480&type=fixnone&nocache=yes&key=7a0ae2bf8eedffb4"
//                    }
//                ],
//                "groupiconid": "3",
//                "avatar": "http://u.rednet.cn/data/avatar/000/05/05/02_avatar_small.jpg"
//            },
//            {
//                "pid": "92394765",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 11:24",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/112328fwyctfpw0qgbiwq5.jpg\" attach=\"92394765\" /></div><br />\n<br />\n<br />\n好山好景好天气，别光顾着玩，合照留个影先<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "9",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "8",
//                "dbdateline": "1478316274",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8928125",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928125&size=360x480&type=fixnone&nocache=yes&key=0cf0cae684f4180f"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92394780",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 11:25",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/112501vsfruwsshorsrjlt.jpg\" attach=\"92394780\" /></div><br />\r\n<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/112501hyyo4yffoyncyhhl.jpg\" attach=\"92394780\" /></div><br />\r\n<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/112502a6nhnnj3zndxzyxf.jpg\" attach=\"92394780\" /></div><br />\r\n<br />\r\n<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "10",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "9",
//                "dbdateline": "1478316312",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8928151",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928151&size=360x480&type=fixnone&nocache=yes&key=d5cae2d05fae1b72"
//                    },
//                    {
//                        "aid": "8928152",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928152&size=360x480&type=fixnone&nocache=yes&key=aba91b7de0b0ebf1"
//                    },
//                    {
//                        "aid": "8928153",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928153&size=360x480&type=fixnone&nocache=yes&key=bfa48d9e62d01a1c"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92394816",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 11:26",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/112549dgz06ljfc00gcllo.jpg\" attach=\"92394816\" /></div><br />\n<br />\n<br />\n来一张震撼全景图，如此风景你羡慕吗？<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "11",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "10",
//                "dbdateline": "1478316402",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8928165",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928165&size=360x480&type=fixnone&nocache=yes&key=e913cdaed95cc2f1"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92394828",
//                "tid": "46233630",
//                "first": "0",
//                "author": "毋忘我",
//                "authorid": "259000",
//                "dateline": "2016-11-5 11:27",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/112719jmsyqrs0ssr9afjf.jpg\" attach=\"92394828\" /></div><div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/112726hby89keqswrwe8ac.jpg\" attach=\"92394828\" /></div><br />\r\n<br />\r\n<br />\r\n天气真好啊<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "12",
//                "username": "毋忘我",
//                "adminid": "0",
//                "groupid": "14",
//                "memberstatus": "0",
//                "number": "11",
//                "dbdateline": "1478316460",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8928174",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928174&size=360x480&type=fixnone&nocache=yes&key=e79918a5851ac68c"
//                    },
//                    {
//                        "aid": "8928175",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928175&size=360x480&type=fixnone&nocache=yes&key=51134bb33cc4766c"
//                    }
//                ],
//                "groupiconid": "5",
//                "avatar": "http://u.rednet.cn/data/avatar/000/25/90/00_avatar_small.jpg"
//            },
//            {
//                "pid": "92394851",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 11:29",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/112834j4xawz5jd5o27572.jpg\" attach=\"92394851\" /></div><br />\r\n这可是老朋友了，姿势略帅，你认出来了吗？<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "13",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "12",
//                "dbdateline": "1478316596",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8928179",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928179&size=360x480&type=fixnone&nocache=yes&key=0dcf5e56791418b0"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92394996",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 11:41",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/114127h2eyrh7nnekeyfwf.jpg\" attach=\"92394996\" /></div><br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "14",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "13",
//                "dbdateline": "1478317291",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8928279",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928279&size=360x480&type=fixnone&nocache=yes&key=9b4bd723f21f5c00"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92395001",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 11:41",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/114152epty2599capcyy5y.jpg\" attach=\"92395001\" /></div><br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "15",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "14",
//                "dbdateline": "1478317315",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8928280",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928280&size=360x480&type=fixnone&nocache=yes&key=292c844dc575e737"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92395031",
//                "tid": "46233630",
//                "first": "0",
//                "author": "卡拉没有K",
//                "authorid": "5275406",
//                "dateline": "2016-11-5 11:45",
//                "message": "<div class=\"img\"><img src=\"http://f3.rednet.cn/data/attachment/forum/201611/05/114445g7sybj98sdswbbjd.jpg\" attach=\"92395031\" /></div><br />\r\n网友接受媒体的采访<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "16",
//                "username": "卡拉没有K",
//                "adminid": "0",
//                "groupid": "11",
//                "memberstatus": "0",
//                "number": "15",
//                "dbdateline": "1478317504",
//                "attachments": [],
//                "imagelist": [],
//                "attchlist": [
//                    {
//                        "aid": "8928312",
//                        "thumburl": "api/mobile/?version=4&module=cutimage&aid=8928312&size=360x480&type=fixnone&nocache=yes&key=15aaeb12bff8867b"
//                    }
//                ],
//                "groupiconid": "2",
//                "avatar": "http://u.rednet.cn/data/avatar/005/27/54/06_avatar_small.jpg"
//            },
//            {
//                "pid": "92395078",
//                "tid": "46233630",
//                "first": "0",
//                "author": "天上人间2013",
//                "authorid": "4224719",
//                "dateline": "2016-11-5 11:53",
//                "message": "<img src=\"http://bbs.rednet.cn/static/image/smiley/default/biggrin.gif\" /><img src=\"http://bbs.rednet.cn/static/image/smiley/default/biggrin.gif\" /><img src=\"http://bbs.rednet.cn/static/image/smiley/default/biggrin.gif\" /><br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "1024",
//                "replycredit": "0",
//                "position": "17",
//                "username": "天上人间2013",
//                "adminid": "0",
//                "groupid": "26",
//                "memberstatus": "0",
//                "number": "16",
//                "dbdateline": "1478317994",
//                "groupiconid": "13",
//                "avatar": "http://u.rednet.cn/data/avatar/004/22/47/19_avatar_small.jpg"
//            },
//            {
//                "pid": "92395355",
//                "tid": "46233630",
//                "first": "0",
//                "author": "肖福祥",
//                "authorid": "295576",
//                "dateline": "2016-11-5 12:49",
//                "message": "真想回来看看！<br />\n<br />\n<br />\n好！<br />\n<br />\n<br />\n支持！<br />\n<br />\n<br />\n大顶！<br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "18",
//                "username": "肖福祥",
//                "adminid": "0",
//                "groupid": "23",
//                "memberstatus": "0",
//                "number": "17",
//                "dbdateline": "1478321370",
//                "groupiconid": "10",
//                "avatar": "http://u.rednet.cn/data/avatar/000/29/55/76_avatar_small.jpg"
//            },
//            {
//                "pid": "92395532",
//                "tid": "46233630",
//                "first": "0",
//                "author": "网络说话",
//                "authorid": "1344326",
//                "dateline": "2016-11-5 13:22",
//                "message": "支持！顶。发现开发宣传。",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "19",
//                "username": "网络说话",
//                "adminid": "0",
//                "groupid": "13",
//                "memberstatus": "0",
//                "number": "18",
//                "dbdateline": "1478323371",
//                "groupiconid": "4",
//                "avatar": "http://u.rednet.cn/data/avatar/001/34/43/26_avatar_small.jpg"
//            },
//            {
//                "pid": "92395539",
//                "tid": "46233630",
//                "first": "0",
//                "author": "yinzhijun0737",
//                "authorid": "4917491",
//                "dateline": "2016-11-5 13:24",
//                "message": "<br />\r\n<font color=\"#ff0000\"><strong>我们在路上！<img src=\"http://bbs.rednet.cn/static/image/smiley/default/victory.gif\" /></strong></font><br />\r\n",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "20",
//                "username": "yinzhijun0737",
//                "adminid": "0",
//                "groupid": "31",
//                "memberstatus": "0",
//                "number": "19",
//                "dbdateline": "1478323477",
//                "groupiconid": "18",
//                "avatar": "http://u.rednet.cn/data/avatar/004/91/74/91_avatar_small.jpg"
//            },
//            {
//                "pid": "92395932",
//                "tid": "46233630",
//                "first": "0",
//                "author": "邵阳老酒",
//                "authorid": "338040",
//                "dateline": "2016-11-5 14:31",
//                "message": "但愿这样的活动多搞。<a href=\"cn.rednet.bbs://cn.rednet.bbs/thread/common?tid=46233631\">goo</a>",
//                "anonymous": "0",
//                "attachment": "0",
//                "status": "4",
//                "replycredit": "0",
//                "position": "21",
//                "username": "邵阳老酒",
//                "adminid": "0",
//                "groupid": "30",
//                "memberstatus": "0",
//                "number": "20",
//                "dbdateline": "1478327464",
//                "groupiconid": "17",
//                "avatar": "http://u.rednet.cn/data/avatar/000/33/80/40_avatar_small.jpg"
//            }
//        ],
//        "allowpostcomment": [
//            "2"
//        ],
//        "comments": [],
//        "commentcount": [],
//        "ppp": "20",
//        "setting_rewriterule": {
//            "portal_topic": "topic-{name}.html",
//            "portal_article": "article-{id}-{page}.html",
//            "forum_forumdisplay": "forum-{fid}-{page}.html",
//            "forum_viewthread": "thread-{tid}-{page}-{prevpage}.html",
//            "group_group": "group-{fid}-{page}.html",
//            "home_space": "space-{user}-{value}.html",
//            "home_blog": "blog-{uid}-{blogid}.html",
//            "forum_archiver": "{action}-{value}.html",
//            "plugin": "{pluginid}-{module}.html"
//        },
//        "setting_rewritestatus": [
//            "forum_forumdisplay",
//            "forum_viewthread",
//            "home_blog"
//        ],
//        "forum_threadpay": "",
//        "cache_custominfo_postno": [
//            "楼",
//            ""
//        ],
//        "forum": {
//            "password": "0"
//        }
//    }
//};