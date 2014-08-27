/*
MySQL Data Transfer
Source Host: localhost
Source Database: iiimms_db
Target Host: localhost
Target Database: iiimms_db
Date: 2014/5/19 22:58:21
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for tbl_crawlerconfig
-- ----------------------------
CREATE TABLE `tbl_crawlerconfig` (
  `configId` int(11) NOT NULL auto_increment,
  `configName` varchar(30) NOT NULL,
  `proxyHost` varchar(30) default NULL,
  `refreshHour` int(11) default NULL,
  `refreshMin` int(11) default NULL,
  `refreshSec` int(11) default NULL,
  `configExp` text,
  PRIMARY KEY  (`configId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_extraparame
-- ----------------------------
CREATE TABLE `tbl_extraparame` (
  `extraParameId` int(11) NOT NULL auto_increment,
  `siteId` int(11) NOT NULL,
  `loginUrl` varchar(50) NOT NULL,
  `loginName` varchar(50) NOT NULL,
  `loginPwd` varchar(50) NOT NULL,
  PRIMARY KEY  (`extraParameId`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_fetchpagerobj
-- ----------------------------
CREATE TABLE `tbl_fetchpagerobj` (
  `fetchPagerObjId` int(11) NOT NULL auto_increment,
  `fetchPagerObjName` varchar(50) NOT NULL,
  `generalable` int(11) NOT NULL default '0',
  `used` int(11) NOT NULL default '0',
  `objExp` text,
  PRIMARY KEY  (`fetchPagerObjId`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_fetchparame
-- ----------------------------
CREATE TABLE `tbl_fetchparame` (
  `fetchParameId` int(11) NOT NULL auto_increment,
  `siteId` int(11) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `baNameQuery` varchar(50) default NULL,
  `titleQuery` varchar(50) default NULL,
  `readNumQuery` varchar(50) default NULL,
  `commentNumQuery` varchar(50) default NULL,
  `forwardNumQuery` varchar(50) default NULL,
  `pagerQuery` varchar(50) default NULL,
  `fetchPagerObjId` int(11) NOT NULL,
  `postDivQuery` varchar(50) default NULL,
  `postContentQuery` varchar(50) default NULL,
  `postAuthorQuery` varchar(50) default NULL,
  `postTimeQuery` varchar(50) default NULL,
  `replyDivQuery` varchar(50) default NULL,
  `replyContentQuery` varchar(50) default NULL,
  `replyAuthorQuery` varchar(50) default NULL,
  `replyTimeQuery` varchar(50) default NULL,
  PRIMARY KEY  (`fetchParameId`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_grabparame
-- ----------------------------
CREATE TABLE `tbl_grabparame` (
  `grabParameId` int(11) NOT NULL auto_increment,
  `siteId` int(11) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `themeListDivQuery` varchar(50) default NULL,
  `themeDivQuery` varchar(50) default NULL,
  `themeNameQuery` varchar(50) default NULL,
  `themeUrlQuery` varchar(50) default NULL,
  `postListPagerQuery` varchar(50) default NULL,
  `postListNextQuery` varchar(50) default NULL,
  `postListSpDivQuery` varchar(50) default NULL,
  `postDivQuery` varchar(50) default NULL,
  `postNameQuery` varchar(50) default NULL,
  `postUrlQuery` varchar(50) default NULL,
  `postClickNumQuery` varchar(50) default NULL,
  `postReplyNumQuery` varchar(50) default NULL,
  `postForwardNumQuery` varchar(50) default NULL,
  `postTimeQuery` varchar(50) default NULL,
  PRIMARY KEY  (`grabParameId`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_grabuserparame
-- ----------------------------
CREATE TABLE `tbl_grabuserparame` (
  `grabUserParameId` int(11) NOT NULL auto_increment,
  `siteId` int(11) NOT NULL,
  `categoryId` int(11) NOT NULL,
  `themePageNumLimit` int(11) NOT NULL default '5',
  `postStartTimeLimit` datetime default NULL COMMENT '最近回复起始时间限制',
  `postEndTimeLimit` datetime default NULL COMMENT '最近回复截止时间',
  `postTimeRangeLimit` varchar(50) NOT NULL default '00-01-00',
  `timeDeterminer` int(11) NOT NULL default '1',
  `postClickNumLimit` int(11) NOT NULL default '0',
  `postReplyNumLimit` int(11) NOT NULL default '0',
  `postForwardNumLimit` int(11) NOT NULL default '0',
  PRIMARY KEY  (`grabUserParameId`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_post
-- ----------------------------
CREATE TABLE `tbl_post` (
  `postId` int(11) NOT NULL auto_increment,
  `siteId` int(11) NOT NULL,
  `themeId` int(11) NOT NULL,
  `postName` varchar(110) NOT NULL,
  `postUrl` varchar(150) NOT NULL,
  `postUrlMD5` varchar(50) NOT NULL,
  `clickNum` int(11) NOT NULL default '0',
  `replyNum` int(11) NOT NULL default '0',
  `forwardNum` int(11) NOT NULL default '0',
  `lastReplyTime` datetime NOT NULL,
  `fetchable` int(11) NOT NULL,
  `refreshTime` datetime NOT NULL,
  PRIMARY KEY  (`postId`)
) ENGINE=MyISAM AUTO_INCREMENT=262 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_site
-- ----------------------------
CREATE TABLE `tbl_site` (
  `siteId` int(11) NOT NULL auto_increment,
  `categoryId` int(11) NOT NULL,
  `toolId` int(11) NOT NULL,
  `siteName` varchar(100) NOT NULL,
  `nickName` varchar(50) default NULL,
  `seedUrl` varchar(150) NOT NULL,
  `enCode` varchar(20) NOT NULL default 'UTF-8',
  `siteHotNum` int(11) NOT NULL,
  `siteGrabable` int(11) NOT NULL default '1',
  `refreshTime` datetime NOT NULL,
  `siteExp` text,
  PRIMARY KEY  (`siteId`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_sitecategory
-- ----------------------------
CREATE TABLE `tbl_sitecategory` (
  `categoryId` int(11) NOT NULL auto_increment,
  `categoryName` varchar(30) NOT NULL,
  `reqLogin` int(11) NOT NULL default '0',
  `categoryExp` text,
  PRIMARY KEY  (`categoryId`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_sitelog
-- ----------------------------
CREATE TABLE `tbl_sitelog` (
  `siteLogId` varchar(30) NOT NULL,
  `taskLogId` varchar(30) NOT NULL,
  `siteId` int(11) NOT NULL,
  `siteStatus` int(11) NOT NULL COMMENT '站点分任务执行状态，初始是0',
  `siteThemeNum` int(11) NOT NULL,
  `siteNewPostNum` int(11) NOT NULL,
  `siteUpdatePostNum` int(11) NOT NULL,
  `siteFixPostNum` int(11) NOT NULL,
  `grabCostTime` varchar(50) NOT NULL,
  `grabCostTimeNum` int(11) NOT NULL,
  `siteFetchNum` int(11) NOT NULL,
  `siteFetchSuccNum` int(11) NOT NULL,
  `fetchCostTime` varchar(50) NOT NULL,
  `fetchCostTimeNum` int(11) NOT NULL,
  `siteLogExp` text NOT NULL,
  PRIMARY KEY  (`siteLogId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_tasklog
-- ----------------------------
CREATE TABLE `tbl_tasklog` (
  `taskLogId` varchar(30) NOT NULL COMMENT '以任务的时间戳为主键如：20140402123455',
  `taskStatus` int(11) NOT NULL COMMENT '任务执行到的阶段，初始是0',
  `startTime` datetime NOT NULL,
  `endTime` datetime default NULL,
  `costTime` varchar(50) NOT NULL,
  `costTimeNum` int(11) NOT NULL,
  `grabSiteNum` int(11) NOT NULL,
  `grabSiteSuccNum` int(11) NOT NULL,
  `totalThemeNum` int(11) NOT NULL,
  `totalGrabNewPostNum` int(11) NOT NULL,
  `totalGrabUpdatePostNum` int(11) NOT NULL,
  `totalFetchPostNum` int(11) NOT NULL,
  `totalFetchSuccPostNum` int(11) NOT NULL,
  `taskPauseTimes` int(11) NOT NULL default '0',
  `taskInfoExp` text NOT NULL,
  `taskLogExp` text NOT NULL,
  PRIMARY KEY  (`taskLogId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_theme
-- ----------------------------
CREATE TABLE `tbl_theme` (
  `themeId` int(11) NOT NULL auto_increment,
  `siteId` int(11) NOT NULL,
  `themeName` varchar(30) NOT NULL,
  `themeUrl` varchar(100) NOT NULL,
  `themeUrlMD5` varchar(50) NOT NULL,
  `themeGrabable` int(11) NOT NULL default '1',
  `themeHotNum` int(11) NOT NULL,
  `refreshTime` datetime NOT NULL,
  `themeExp` text,
  PRIMARY KEY  (`themeId`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_themelog
-- ----------------------------
CREATE TABLE `tbl_themelog` (
  `themeLogId` varchar(30) NOT NULL,
  `taskLogId` varchar(30) NOT NULL,
  `themeId` int(11) NOT NULL,
  `siteId` int(11) NOT NULL COMMENT '所属站点编号',
  `themeNewPostNum` int(11) NOT NULL,
  `themeUpdatePostNum` int(11) NOT NULL,
  `themeFetchNum` int(11) NOT NULL,
  `themeFetchSuccNum` int(11) NOT NULL COMMENT '成功解析的节点数目',
  `themeLogExp` text NOT NULL,
  PRIMARY KEY  (`themeLogId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tbl_tool
-- ----------------------------
CREATE TABLE `tbl_tool` (
  `toolId` int(11) NOT NULL auto_increment,
  `toolName` varchar(50) NOT NULL,
  `grabThemeObj` varchar(30) NOT NULL,
  `grabPostObj` varchar(30) NOT NULL,
  `fetchContentObj` varchar(30) NOT NULL,
  `checkNetObj` varchar(30) NOT NULL,
  `checkDatabaseObj` varchar(30) NOT NULL,
  `checkGrabParameObj` varchar(30) NOT NULL,
  `checkFetchParameObj` varchar(30) NOT NULL,
  `toolExp` text,
  PRIMARY KEY  (`toolId`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
INSERT INTO `tbl_extraparame` VALUES ('1', '1', 'https://passport.baidu.com/v2/?login&tpl=mn&u=http', 'superhy199148', 'qdhy199148');
INSERT INTO `tbl_fetchpagerobj` VALUES ('1', 'GetBaidutiebaPagerImpl', '0', '1', '百度贴吧帖子内部分页分析实现类');
INSERT INTO `tbl_fetchpagerobj` VALUES ('2', 'GetTianyaluntanPagerImpl', '0', '1', '天涯论坛分页实现类');
INSERT INTO `tbl_fetchpagerobj` VALUES ('3', 'GetMaoputietiePagerImpl', '0', '1', '猫扑贴贴分页实现类');
INSERT INTO `tbl_fetchpagerobj` VALUES ('31', 'GetDoubanxiaozuPagerImpl', '0', '1', '豆瓣小组分页实现类');
INSERT INTO `tbl_fetchparame` VALUES ('1', '1', '1', 'a#tab_home', 'h1.core_title_txt', 'li.l_reply_num>span[style^=margin]', 'li.l_reply_num>span[style^=margin]', '', 'li[class*=l_pager]', '1', 'div[class*=l_post]', 'div.p_content', 'a[class*=p_author_name]', 'ul.p_tail', 'li[class*=lzl_single_post]', 'span.lzl_content_main', 'a.at.j_user_card', 'span.lzl_time');
INSERT INTO `tbl_fetchparame` VALUES ('2', '2', '1', 'p.crumbs a[href*=list]', 'span.s_title>span', 'div.atl-info>span:nth-child(3)', 'div.atl-info>span:nth-child(4)', '', 'div.atl-pages', '2', 'div#bd', 'div[class^=bbs-content]', 'a[uname]', 'div.atl-info>span:nth-child(2)', 'div[js_username]', 'div.bbs-content', 'a[uname]', 'div.atl-info>span:nth-last-child(1)');
INSERT INTO `tbl_fetchparame` VALUES ('3', '3', '1', 'div.mbx>div.inn', 'h1[id*=title]', 'div.num>span:nth-child(1)', 'div.num>span:nth-child(2)', '', 'div.page', '3', 'div.tzbdP', 'div#js-sub-body', 'div.name>a.fcB', 'span.date', 'div.box2.js-reply', 'div.h_nr.js-reply-body', 'a.h_yh', 'div.h_lz');
INSERT INTO `tbl_fetchparame` VALUES ('31', '31', '4', 'div.group-item', 'div#content>h1', '', '', '', 'div.paginator', '31', 'div.article', 'div.topic-content', 'span.from', 'span.color-green', 'li[class$=comment-item]', 'div[class^=reply-doc]>p', 'h4>a', 'h4>span.pubtime');
INSERT INTO `tbl_grabparame` VALUES ('1', '1', '2', 'div#ba_list', 'div[class^=ba_info]', 'p.ba_name', 'a[class^=ba_href]', 'div#frs_list_pager', 'a.next', 'div#content_leftList', 'li[class*=thread]', 'a[class*=tit]', 'a[class*=tit]', 'div.threadlist_rep_num', 'div.threadlist_rep_num', '', 'span[class^=threadlist_reply_date]');
INSERT INTO `tbl_grabparame` VALUES ('2', '2', '1', 'ul.block-ranking', 'li', 'a[href*=list]', 'a[href*=list]', 'div.links', 'a[rel=nofollow]', 'table[class^=tab-bbs-list]', 'tr:has(td[class^=td])', 'a[href*=post]', 'a[href*=post]', 'td:nth-child(3)', 'td:nth-child(4)', '', 'td[title^=20]');
INSERT INTO `tbl_grabparame` VALUES ('3', '3', '1', 'ul[style$=block]', 'li[class^=active]', 'a[href]', 'a[href]', 'div.page', 'a.end', 'table.tiezi_table', 'tr[data-sid]', 'a[title]', 'a[title]', 'td:nth-child(3)', 'td:nth-child(3)', '', 'td.time');
INSERT INTO `tbl_grabparame` VALUES ('4', '4', '1', 'ul[class^=top-15]', 'li[class^=li]', 'a', 'a', 'div.numb_post2', 'div>a:nth-child(3)', 'tbody', 'tr:has(td)', 'td.common>a[href*=viewthread]', 'td.common>a[href*=viewthread]', 'span.green', 'span.cRed', '', 'td[class$=kmhf]>em');
INSERT INTO `tbl_grabparame` VALUES ('5', '5', '1', 'div#sub_nav', 'dd', 'a', 'a', 'div.pages', 'a.next', 'form[name=moderate]', 'tbody[class^=author_thread]', 'span[id^=thread]>a', 'span[id^=thread]>a', 'td.nums>strong', 'td.nums>em', '', 'td.lastpost a');
INSERT INTO `tbl_grabparame` VALUES ('6', '6', '1', 'div.bbs_nav>div.list', 'a[href*=list]', 'font', 'a', 'div.board-page', 'a:last-child', 'div#board-list-bd', 'div[class^=board-list-one]', 'span.article-title>a', 'span.article-title>a', 'div.board-list-count-inner', 'div.board-list-count-inner', '', 'div.board-list-reply>span.date');
INSERT INTO `tbl_grabparame` VALUES ('31', '31', '4', 'div.nav-items', 'li>a[href*=explore/]', 'a[href*=explore/]', 'a[href*=explore/]', 'div.paginator', 'span.next>a', 'div.article', 'div[class^=channel]', 'h3>a', 'h3>a', '', '', '', 'span.pubtime');
INSERT INTO `tbl_grabuserparame` VALUES ('1', '1', '2', '3', '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', '0', '0', '0', '0');
INSERT INTO `tbl_grabuserparame` VALUES ('2', '2', '1', '3', '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', '0', '0', '0', '0');
INSERT INTO `tbl_grabuserparame` VALUES ('3', '3', '1', '1', '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', '0', '0', '0', '0');
INSERT INTO `tbl_grabuserparame` VALUES ('4', '4', '1', '3', '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', '0', '0', '0', '0');
INSERT INTO `tbl_grabuserparame` VALUES ('5', '5', '1', '3', '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', '0', '0', '0', '0');
INSERT INTO `tbl_grabuserparame` VALUES ('6', '6', '1', '3', '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', '0', '0', '0', '0');
INSERT INTO `tbl_grabuserparame` VALUES ('31', '31', '4', '1', '2014-05-18 12:36:18', '2014-05-19 22:35:00', '00-00-03', '0', '0', '0', '0');
INSERT INTO `tbl_post` VALUES ('1', '31', '5', '刚刚在ins上发现了一个超级美的bra牌子。。。。。', 'http://www.douban.com/group/topic/51934604/', 'D83B3CCF7A30E9ECA0D98F2ECC7EE62E', '0', '0', '0', '2014-05-19 17:15:00', '0', '2014-05-19 22:38:30');
INSERT INTO `tbl_post` VALUES ('2', '31', '1', '发现神奇护肤圣品 毛孔变小变干净 皮肤变嫩 而且还是天然的哦', 'http://www.douban.com/group/topic/38563110/', '1F5E14834A4DF0EFF1CE3D32E4B4EA4D', '0', '0', '0', '2014-05-19 18:24:00', '0', '2014-05-19 22:38:32');
INSERT INTO `tbl_post` VALUES ('3', '31', '6', '良心贴！厦门人爆料的厦门旅游内幕！你所知的旅游攻略全是假的！', 'http://www.douban.com/group/topic/47991910/', '23F535AEF51BD1BF1EAC6D794055E24B', '0', '0', '0', '2014-05-19 22:32:00', '0', '2014-05-19 22:38:31');
INSERT INTO `tbl_post` VALUES ('4', '31', '1', '已更新美白~【【闺蜜白到没朋友！！！】】露珠也在努力追随中！！！', 'http://www.douban.com/group/topic/49637128/', '7FDEE63B10409A4FEF5614A8C5D19368', '0', '0', '0', '2014-05-19 07:48:00', '0', '2014-05-19 22:38:33');
INSERT INTO `tbl_post` VALUES ('5', '31', '6', '1000元穷游丽江7天（想和我结伴的举个手）', 'http://www.douban.com/group/topic/49726582/', 'AEA30057F05007609ADB33CC8E34135A', '0', '0', '0', '2014-05-19 14:52:00', '0', '2014-05-19 22:38:30');
INSERT INTO `tbl_post` VALUES ('6', '31', '6', '如果你打算来西安旅游,看了这个贴让你的西安之旅不被坑！！！还有各种美食攻略，持续更新中...', 'http://www.douban.com/group/topic/51001856/', '0DF72A06D42108F535EA40CD8F3265AD', '0', '0', '0', '2014-05-19 22:06:00', '0', '2014-05-19 22:38:31');
INSERT INTO `tbl_post` VALUES ('7', '31', '4', '有兴趣一起聊书吗~', 'http://www.douban.com/group/topic/51706901/', '334AFB7AE70B141BE70B4BE1B2569654', '0', '0', '0', '2014-05-19 03:33:00', '0', '2014-05-19 22:38:32');
INSERT INTO `tbl_post` VALUES ('8', '31', '3', '史上最打动人心的十大小清新电影推荐', 'http://www.douban.com/group/topic/51886176/', '167BF25897EAEE58EEA4B1663C0B2075', '0', '0', '0', '2014-05-19 14:48:00', '0', '2014-05-19 22:38:29');
INSERT INTO `tbl_post` VALUES ('9', '31', '1', '我只做了两天腹肌撕裂者，你们看这是已经出现效果了吗', 'http://www.douban.com/group/topic/51908212/', '58A96B5028638FF0A20D2E5BF83D9895', '0', '0', '0', '2014-05-19 05:49:00', '0', '2014-05-19 22:38:33');
INSERT INTO `tbl_post` VALUES ('10', '31', '1', '后悔脑子被门夹了没早点用国货彩妆，被老外黑了那么多钱，附上各种粉饼的使用感受本人混合皮T区油', 'http://www.douban.com/group/topic/35235477/', '89CE42BA3E98915A973CF47557854A24', '0', '0', '0', '2014-05-19 18:00:00', '0', '2014-05-19 22:38:32');
INSERT INTO `tbl_post` VALUES ('11', '31', '3', '我心中的最佳50部国产电影', 'http://www.douban.com/group/topic/51231688/', '156A66378654DF8E507C3B6F6B798F5F', '0', '0', '0', '2014-05-19 04:24:00', '0', '2014-05-19 22:38:29');
INSERT INTO `tbl_post` VALUES ('12', '31', '3', '来收集一些温暖的适合写在明信片上的歌词。', 'http://www.douban.com/group/topic/27966085/', '1A794D9C06FA9EE821BD3032B453A55D', '0', '0', '0', '2014-05-19 05:17:00', '0', '2014-05-19 22:38:32');
INSERT INTO `tbl_post` VALUES ('13', '31', '6', '【更新图】❤20+安卓APP推荐！！另眼缘fo', 'http://www.douban.com/group/topic/49374146/', '9128CF7E381E1ACA8FD8B80E3B19B2D9', '0', '0', '0', '2014-05-19 18:20:00', '0', '2014-05-19 22:38:31');
INSERT INTO `tbl_post` VALUES ('14', '31', '1', '脸色不太好的妹纸，我来告诉你很实用的美白保养方法', 'http://www.douban.com/group/topic/50333286/', '2042F90234F1ADAF8F0EFAD2E80A646C', '0', '0', '0', '2014-05-19 21:19:00', '0', '2014-05-19 22:38:32');
INSERT INTO `tbl_post` VALUES ('15', '31', '1', '【三年美白历程】~从原来的很黑很黑到现在的白的出水', 'http://www.douban.com/group/topic/50302507/', '249FAE3DA4FCF54FC8B396D27BFB65D2', '0', '0', '0', '2014-05-19 07:20:00', '0', '2014-05-19 22:38:33');
INSERT INTO `tbl_post` VALUES ('16', '31', '2', '精心挑选的PPT教程，别再说你不会做PPT了！！！', 'http://www.douban.com/group/topic/33508685/', '20BDBD0A915DBE14BC94409A5C83C7A4', '0', '0', '0', '2014-05-19 21:23:00', '0', '2014-05-19 22:38:30');
INSERT INTO `tbl_post` VALUES ('17', '31', '1', '【更完~】半夜睡不着，分享卤煮的彩妆护肤与黑名单！说一说开架与专柜到底该怎么选!', 'http://www.douban.com/group/topic/48220285/', 'A910CC445834892643EE9C72BDC36423', '0', '0', '0', '2014-05-19 22:22:00', '0', '2014-05-19 22:38:33');
INSERT INTO `tbl_post` VALUES ('18', '31', '5', '关于纠正腿型，X腿，O型腿的帖子', 'http://www.douban.com/group/topic/49324824/', '1EB11CC47FD76FC56214643BDC4FB772', '0', '0', '0', '2014-05-19 21:37:00', '0', '2014-05-19 22:38:31');
INSERT INTO `tbl_post` VALUES ('19', '31', '5', '【汇报分享】大坑！欧美范儿多年淘原单，收藏夹+已买到', 'http://www.douban.com/group/topic/34014641/', '0C9610DBCFC7D5096F0CFF685FB5CDD2', '0', '0', '0', '2014-05-19 16:34:00', '0', '2014-05-19 22:38:30');
INSERT INTO `tbl_post` VALUES ('20', '31', '2', '(转帖)美国人教你这样用Google，你真的会变特工', 'http://www.douban.com/group/topic/11475068/', 'BD535C735F5794FD863D8B1460EEBEB2', '0', '0', '0', '2014-05-19 11:34:00', '0', '2014-05-19 22:38:30');
INSERT INTO `tbl_site` VALUES ('1', '2', '3', '百度贴吧', 'baidutieba', 'http://tieba.baidu.com/f/index/forumpark?cn=&ci=0&pcn=%C9%E7%BB%E1&pci=0&ct=1&st=popular', 'utf-8', '1', '0', '2014-04-27 22:36:18', 'asdfa');
INSERT INTO `tbl_site` VALUES ('2', '1', '1', '天涯论坛', 'tianyaluntan', 'http://bbs.tianya.cn/', 'utf-8', '0', '0', '2015-11-06 09:23:12', '');
INSERT INTO `tbl_site` VALUES ('3', '1', '1', '猫扑论坛', 'maoputietie', 'http://tt.mop.com/', 'utf-8', '0', '0', '2014-03-15 22:21:20', '');
INSERT INTO `tbl_site` VALUES ('4', '1', '1', '凤凰论坛', 'fenghuangluntan', 'http://bbs.ifeng.com/', 'utf-8', '0', '0', '2014-03-16 15:01:13', '');
INSERT INTO `tbl_site` VALUES ('5', '1', '1', '新浪论坛', 'sinaluntan', 'http://bbs.sina.com.cn/', 'gb2312', '0', '0', '2014-03-16 16:19:57', '');
INSERT INTO `tbl_site` VALUES ('6', '1', '1', '网易论坛', 'wy163luntan', 'http://bbs.163.com/', 'gb2312', '0', '0', '2014-03-26 21:36:34', '');
INSERT INTO `tbl_site` VALUES ('31', '4', '5', '豆瓣小组', 'doubangroup', 'http://www.douban.com/group/explore', 'utf-8', '0', '1', '2014-05-13 18:21:45', '豆瓣小组新兴媒体');
INSERT INTO `tbl_sitecategory` VALUES ('1', '论坛大类', '0', '包括论坛，社区，BBS');
INSERT INTO `tbl_sitecategory` VALUES ('2', '登陆贴吧类', '1', '需要登录的贴吧');
INSERT INTO `tbl_sitecategory` VALUES ('3', '博客大类', '0', '包含各种博客');
INSERT INTO `tbl_sitecategory` VALUES ('4', '新兴媒体类', '0', '新兴出现的媒体');
INSERT INTO `tbl_sitelog` VALUES ('2014050409492631', '20140504094926', '31', '0', '0', '283', '0', '0', '00:00:00', '0', '20', '20', '00:00:00', '0', '站点分任务日志被创建');
INSERT INTO `tbl_tasklog` VALUES ('20140504094926', '0', '2014-05-04 09:49:26', '2014-05-04 09:49:26', '00:00:00', '0', '2', '0', '0', '0', '0', '0', '0', '0', '{}', '{}');
INSERT INTO `tbl_theme` VALUES ('1', '31', '时尚', 'http://www.douban.com/group/explore/fashion', 'C76B845ED69CD1F18671967560E1B30E', '0', '0', '2014-05-19 22:35:06', '豆瓣小组中的时尚主题');
INSERT INTO `tbl_theme` VALUES ('2', '31', '科技', 'http://www.douban.com/group/explore/tech', '7F5477176408CE33AE4947DCB1B56A19', '0', '0', '2014-05-19 22:35:06', '豆瓣小组中的科技主题');
INSERT INTO `tbl_theme` VALUES ('3', '31', '娱乐', 'http://www.douban.com/group/explore/ent', '7049A267829104E5E919CF6CB6B82963', '0', '0', '2014-05-19 22:35:06', '豆瓣小组中的娱乐主题');
INSERT INTO `tbl_theme` VALUES ('4', '31', '文化', 'http://www.douban.com/group/explore/culture', '7E6ECE9B62962C1CCE717E7444DC2841', '0', '0', '2014-05-19 22:35:06', '豆瓣小组中的文化主题');
INSERT INTO `tbl_theme` VALUES ('5', '31', '生活', 'http://www.douban.com/group/explore/life', '5F06D0B2F269880143DF06601E392AD3', '0', '0', '2014-05-19 22:35:06', '豆瓣小组中的生活主题');
INSERT INTO `tbl_theme` VALUES ('6', '31', '行摄', 'http://www.douban.com/group/explore/travel', '3A24F9BC0CBBD56823F8B4EC707ECFA5', '0', '0', '2014-05-19 22:35:06', '豆瓣小组中的行摄主题');
INSERT INTO `tbl_themelog` VALUES ('20140504094926314', '20140504094926', '4', '31', '58', '0', '1', '1', '该主题所有节点已加载成功，主题反馈日志创建');
INSERT INTO `tbl_themelog` VALUES ('20140504094926311', '20140504094926', '1', '31', '58', '0', '7', '7', '该主题所有节点已加载成功，主题反馈日志创建');
INSERT INTO `tbl_themelog` VALUES ('20140504094926313', '20140504094926', '3', '31', '46', '0', '3', '3', '该主题所有节点已加载成功，主题反馈日志创建');
INSERT INTO `tbl_themelog` VALUES ('20140504094926312', '20140504094926', '2', '31', '22', '0', '2', '2', '该主题所有节点已加载成功，主题反馈日志创建');
INSERT INTO `tbl_themelog` VALUES ('20140504094926316', '20140504094926', '6', '31', '44', '0', '4', '4', '该主题所有节点已加载成功，主题反馈日志创建');
INSERT INTO `tbl_themelog` VALUES ('20140504094926315', '20140504094926', '5', '31', '55', '0', '3', '3', '该主题所有节点已加载成功，主题反馈日志创建');
INSERT INTO `tbl_tool` VALUES ('1', '基础BBS工具包', 'BasicBBSGrabThemeUrlsImpl', 'BasicBBSGrabPostUrlsImpl', 'BasicBBSFetchContentImpl', 'BasicCheckNetConnection', 'GeneralCheckDbConnection', 'BasicCheckGrabParameAble', 'BasicCheckFetchParameAble', '基础论坛类工具包，适用于绝大多数论坛大类站点');
INSERT INTO `tbl_tool` VALUES ('2', 'httpclient链接社区工具包', 'HcBBSGrabThemeUrlsImpl', 'HcBBSGrabPostUrlsImpl', 'HcBBSFetchContentImpl', 'test', 'test', 'test', 'test', '使用httpclient建立连接的基础工具包');
INSERT INTO `tbl_tool` VALUES ('3', '简单登录工具包', 'BsLoginBaGrabThemeUrlsImpl', 'BsLoginBaGrabPostUrlsImpl', 'BsLoginBaFetchContentImpl', 'test', 'test', 'test', 'test', '使用java URLconnection的简单登录工具包');
INSERT INTO `tbl_tool` VALUES ('4', '基础Blog工具包', 'BasicBlogGrabThemeUrlsImpl', 'BasicBlogGrabPostUrlsImpl', 'test', 'BasicCheckNetConnection', 'GeneralCheckDbConnection', 'BasicCheckGrabParameAble', 'BasicCheckFetchParameAble', '适用于大部分博客网站的爬取解析和检查');
INSERT INTO `tbl_tool` VALUES ('5', '新兴媒体工具包', 'NewMediaGrabThemeUrlsImpl', 'NewMediaGrabPostUrlsImpl', 'NewMediaFetchContentImpl', 'HcCheckNetConnection', 'GeneralCheckDbConnection', 'HcCheckGrabParameAble', 'HcCheckFetchParameAble', '适用于豆瓣小组等新兴媒体');
