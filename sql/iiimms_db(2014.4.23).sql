-- phpMyAdmin SQL Dump
-- version 2.10.2
-- http://www.phpmyadmin.net
-- 
-- 主机: localhost
-- 生成日期: 2014 年 04 月 23 日 13:43
-- 服务器版本: 5.0.45
-- PHP 版本: 5.2.3

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- 数据库: `iiimms_db`
-- 

-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_crawlerconfig`
-- 

DROP TABLE IF EXISTS `tbl_crawlerconfig`;
CREATE TABLE IF NOT EXISTS `tbl_crawlerconfig` (
  `configId` int(11) NOT NULL auto_increment,
  `configName` varchar(30) NOT NULL,
  `proxyHost` varchar(30) default NULL,
  `refreshHour` int(11) default NULL,
  `refreshMin` int(11) default NULL,
  `refreshSec` int(11) default NULL,
  `configExp` text,
  PRIMARY KEY  (`configId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- 
-- 导出表中的数据 `tbl_crawlerconfig`
-- 


-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_extraparame`
-- 

DROP TABLE IF EXISTS `tbl_extraparame`;
CREATE TABLE IF NOT EXISTS `tbl_extraparame` (
  `extraParameId` int(11) NOT NULL auto_increment,
  `siteId` int(11) NOT NULL,
  `loginUrl` varchar(50) NOT NULL,
  `loginName` varchar(50) NOT NULL,
  `loginPwd` varchar(50) NOT NULL,
  PRIMARY KEY  (`extraParameId`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=2 ;

-- 
-- 导出表中的数据 `tbl_extraparame`
-- 

INSERT INTO `tbl_extraparame` (`extraParameId`, `siteId`, `loginUrl`, `loginName`, `loginPwd`) VALUES 
(1, 1, 'https://passport.baidu.com/v2/?login&tpl=mn&u=http', 'superhy199148', 'qdhy199148');

-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_fetchpagerobj`
-- 

DROP TABLE IF EXISTS `tbl_fetchpagerobj`;
CREATE TABLE IF NOT EXISTS `tbl_fetchpagerobj` (
  `fetchPagerObjId` int(11) NOT NULL auto_increment,
  `fetchPagerObjName` varchar(50) NOT NULL,
  `generalable` int(11) NOT NULL default '0',
  `used` int(11) NOT NULL default '0',
  `objExp` text,
  PRIMARY KEY  (`fetchPagerObjId`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- 导出表中的数据 `tbl_fetchpagerobj`
-- 

INSERT INTO `tbl_fetchpagerobj` (`fetchPagerObjId`, `fetchPagerObjName`, `generalable`, `used`, `objExp`) VALUES 
(1, 'GetBaidutiebaPagerImpl', 0, 1, '百度贴吧帖子内部分页分析实现类'),
(2, 'GetTianyaluntanPagerImpl', 0, 1, '天涯论坛分页实现类'),
(3, 'GetMaoputietiePagerImpl', 0, 1, '猫扑贴贴分页实现类');

-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_fetchparame`
-- 

DROP TABLE IF EXISTS `tbl_fetchparame`;
CREATE TABLE IF NOT EXISTS `tbl_fetchparame` (
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- 导出表中的数据 `tbl_fetchparame`
-- 

INSERT INTO `tbl_fetchparame` (`fetchParameId`, `siteId`, `categoryId`, `baNameQuery`, `titleQuery`, `readNumQuery`, `commentNumQuery`, `forwardNumQuery`, `pagerQuery`, `fetchPagerObjId`, `postDivQuery`, `postContentQuery`, `postAuthorQuery`, `postTimeQuery`, `replyDivQuery`, `replyContentQuery`, `replyAuthorQuery`, `replyTimeQuery`) VALUES 
(1, 1, 1, 'a#tab_home', 'h1.core_title_txt', 'li.l_reply_num>span[style^=margin]', 'li.l_reply_num>span[style^=margin]', '', 'li[class*=l_pager]', 1, 'div[class*=l_post]', 'div.p_content', 'a[class*=p_author_name]', 'ul.p_tail', 'li[class*=lzl_single_post]', 'span.lzl_content_main', 'a.at.j_user_card', 'span.lzl_time'),
(2, 2, 1, 'p.crumbs a[href*=list]', 'span.s_title>span', 'div.atl-info>span:nth-child(3)', 'div.atl-info>span:nth-child(4)', '', 'div.atl-pages', 2, 'div#bd', 'div[class^=bbs-content]', 'a[uname]', 'div.atl-info>span:nth-last-child(1)', 'div[js_username]', 'div.bbs-content', 'a[uname]', 'div.atl-info>span:nth-last-child(1)'),
(3, 3, 1, 'div.mbx>div.inn', 'h1[id*=title]', 'div.num>span:nth-child(1)', 'div.num>span:nth-child(2)', '', 'div.page', 3, 'div.tzbdP', 'div#js-sub-body', 'div.name>a.fcB', 'span.date', 'div.box2.js-reply', 'div.h_nr.js-reply-body', 'a.h_yh', 'div.h_lz');

-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_grabparame`
-- 

DROP TABLE IF EXISTS `tbl_grabparame`;
CREATE TABLE IF NOT EXISTS `tbl_grabparame` (
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- 
-- 导出表中的数据 `tbl_grabparame`
-- 

INSERT INTO `tbl_grabparame` (`grabParameId`, `siteId`, `categoryId`, `themeListDivQuery`, `themeDivQuery`, `themeNameQuery`, `themeUrlQuery`, `postListPagerQuery`, `postListNextQuery`, `postListSpDivQuery`, `postDivQuery`, `postNameQuery`, `postUrlQuery`, `postClickNumQuery`, `postReplyNumQuery`, `postForwardNumQuery`, `postTimeQuery`) VALUES 
(1, 1, 2, 'div#ba_list', 'div[class^=ba_info]', 'p.ba_name', 'a[class^=ba_href]', 'div#frs_list_pager', 'a.next', 'div#content_leftList', 'li[class*=thread]', 'a[class*=tit]', 'a[class*=tit]', 'div.threadlist_rep_num', 'div.threadlist_rep_num', '', 'span[class^=threadlist_reply_date]'),
(2, 2, 1, 'ul.block-ranking', 'li', 'a[href*=list]', 'a[href*=list]', 'div.links', 'a[rel=nofollow]', 'table[class^=tab-bbs-list]', 'tr:has(td[class^=td])', 'a[href*=post]', 'a[href*=post]', 'td:nth-child(3)', 'td:nth-child(4)', '', 'td[title^=20]'),
(3, 3, 1, 'ul[style$=block]', 'li[class^=active]', 'a[href]', 'a[href]', 'div.page', 'a.end', 'table.tiezi_table', 'tr[data-sid]', 'a[title]', 'a[title]', 'td:nth-child(3)', 'td:nth-child(3)', '', 'td.time'),
(4, 4, 1, 'ul[class^=top-15]', 'li[class^=li]', 'a', 'a', 'div.numb_post2', 'div>a:nth-child(3)', 'tbody', 'tr:has(td)', 'td.common>a[href*=viewthread]', 'td.common>a[href*=viewthread]', 'span.green', 'span.cRed', '', 'td[class$=kmhf]>em'),
(5, 5, 1, 'div#sub_nav', 'dd', 'a', 'a', 'div.pages', 'a.next', 'form[name=moderate]', 'tbody[class^=author_thread]', 'span[id^=thread]>a', 'span[id^=thread]>a', 'td.nums>strong', 'td.nums>em', '', 'td.lastpost a'),
(6, 6, 1, 'div.bbs_nav>div.list', 'a[href*=list]', 'font', 'a', 'div.board-page', 'a:last-child', 'div#board-list-bd', 'div[class^=board-list-one]', 'span.article-title>a', 'span.article-title>a', 'div.board-list-count-inner', 'div.board-list-count-inner', '', 'div.board-list-reply>span.date');

-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_grabuserparame`
-- 

DROP TABLE IF EXISTS `tbl_grabuserparame`;
CREATE TABLE IF NOT EXISTS `tbl_grabuserparame` (
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- 
-- 导出表中的数据 `tbl_grabuserparame`
-- 

INSERT INTO `tbl_grabuserparame` (`grabUserParameId`, `siteId`, `categoryId`, `themePageNumLimit`, `postStartTimeLimit`, `postEndTimeLimit`, `postTimeRangeLimit`, `timeDeterminer`, `postClickNumLimit`, `postReplyNumLimit`, `postForwardNumLimit`) VALUES 
(1, 1, 2, 3, '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', 0, 0, 0, 0),
(2, 2, 1, 3, '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', 0, 0, 0, 0),
(3, 3, 1, 3, '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', 0, 0, 0, 0),
(4, 4, 1, 3, '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', 0, 0, 0, 0),
(5, 5, 1, 3, '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', 0, 0, 0, 0),
(6, 6, 1, 3, '2014-03-01 00:00:00', '2014-04-23 00:00:00', '00-00-03', 0, 0, 0, 0);

-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_post`
-- 

DROP TABLE IF EXISTS `tbl_post`;
CREATE TABLE IF NOT EXISTS `tbl_post` (
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=25 ;

-- 
-- 导出表中的数据 `tbl_post`
-- 

INSERT INTO `tbl_post` (`postId`, `siteId`, `themeId`, `postName`, `postUrl`, `postUrlMD5`, `clickNum`, `replyNum`, `forwardNum`, `lastReplyTime`, `fetchable`, `refreshTime`) VALUES 
(1, 3, 16, '春季养生知识', 'http://tt.mop.com/read_14768663_1_0.html', '6AAAF76B2E82FB116032D53E8606FA11', 2, 0, 0, '2014-03-27 11:08:40', 0, '2014-04-02 10:13:13'),
(2, 3, 30, '我在苏宁易购的购物经历', 'http://tt.mop.com/read_14541228_1_0.html', '93C8BD5FC4257BF9B241B65ED1BEF723', 442, 4, 0, '2014-03-31 16:33:16', 0, '2014-04-02 10:13:09'),
(3, 3, 24, '【圣火令】想起小时候的游戏，放大镜烤蚂蚁', 'http://tt.mop.com/read_14750555_1_0.html', 'EDB6AB40E2B218EABF6733404327F504', 238, 0, 0, '2014-03-20 21:26:32', 0, '2014-04-02 10:13:13'),
(4, 3, 31, '惊艳的美，心醉的美, 美的流连忘返！！', 'http://tt.mop.com/read_3930945_1_0.html', 'E097DE29D3EEB91079F4EEDF142D9650', 6999893, 4079, 0, '2014-04-02 09:26:20', 0, '2014-04-02 10:13:06'),
(5, 3, 31, '(转 WEIBO美图', 'http://tt.mop.com/read_14781944_1_0.html', '4BECEC1B6DC1B2B27D8C22C28D6A0B2C', 27, 1, 0, '2014-04-01 19:23:17', 0, '2014-04-02 10:13:04'),
(6, 3, 23, '贾宝林足以撼动市场的的领导者，唯一的煤运贾宝林', 'http://tt.mop.com/read_14764093_1_0.html', '2D73C81C86B9922E17AA3B2472B62C90', 25, 0, 0, '2014-03-25 21:13:36', 0, '2014-04-02 10:13:06'),
(7, 3, 27, '特写Dal★shabet（美妍）- B.B.B _LN', 'http://tt.mop.com/read_14774993_1_0.html', '8E25705A380349E40DE87CBD134F81E2', 71, 0, 0, '2014-03-29 10:49:59', 0, '2014-04-02 10:13:04'),
(8, 3, 28, '广东雷州老虎遭屠杀', 'http://tt.mop.com/read_14769268_1_0.html', 'BB2527FE22321FCA8BA97FE448D5096D', 158, 2, 0, '2014-03-29 22:13:08', 0, '2014-04-02 10:13:13'),
(9, 3, 1, '小女子清妆，来这里聊聊心事吧，你的，我的，我们', 'http://tt.mop.com/read_13483428_1_0.html', '2FD68F2C18082F41BDDD26F60EB6ECF1', 104902, 36364, 0, '2014-04-02 09:29:14', 0, '2014-04-02 10:13:07'),
(10, 3, 17, '3月31日A股收评：下一个变盘点隐现', 'http://tt.mop.com/read_14781144_1_0.html', '8A8253FCFD851DA7D9C8BB8789377182', 259, 2, 0, '2014-04-01 17:10:41', 0, '2014-04-02 10:13:11'),
(11, 3, 9, '高校毕业论文“查重软件”，都来检测', 'http://tt.mop.com/read_14775615_1_0.html', '20C6629C16DB1EDE8BFD6F8C0019173C', 31, 1, 0, '2014-03-29 16:33:35', 0, '2014-04-02 10:13:10'),
(12, 3, 11, '【Mop搜图令】时尚界的那些事~！天气这么热了，说', 'http://tt.mop.com/read_14771821_1_0.html', '14DDEFC44B0E46ABFA2FE36ABE2274A1', 1405, 3, 0, '2014-04-01 22:01:50', 0, '2014-04-02 10:13:13'),
(13, 3, 25, '【Mop体育】拜仁新星：瓜帅真是了不起', 'http://tt.mop.com/read_14598276_1_0.html', '9B00B5D0E1E12BE30CEB05A3309F1A6F', 17, 7, 0, '2014-03-30 02:11:47', 0, '2014-04-02 10:13:12'),
(14, 3, 29, '男神的标准:你能满足多少条', 'http://tt.mop.com/read_14433995_1_0.html', '9A9C8FC6C62A3DCB4F46193F34E47D0D', 715, 14, 0, '2014-03-31 16:44:43', 0, '2014-04-02 10:13:08'),
(15, 3, 9, '四六级没过的进，本人已过专八，给大家介绍点学习', 'http://tt.mop.com/read_10114481_1_0.html', 'A3B8B6BEAB24392167BF76A3F6661C05', 165002, 26581, 0, '2014-03-29 23:52:52', 0, '2014-04-02 10:13:10'),
(16, 3, 8, 'NEW LOOK与时尚共舞 让流行永恒', 'http://tt.mop.com/read_14772333_1_0.html', '82BE58B861D6556FAE53FF243BC9A36D', 25, 0, 0, '2014-03-28 11:37:25', 0, '2014-04-02 10:13:12'),
(17, 3, 1, '新年快乐！凌霜儿…', 'http://tt.mop.com/read_14625154_1_0.html', '05FF0661D2640CCB4ACC57AFF17EB00E', 126, 16, 0, '2014-04-01 15:56:20', 0, '2014-04-02 10:13:08'),
(18, 3, 31, '河南驻马店市西平县的西平高中一男生从四楼跳下', 'http://tt.mop.com/read_14784354_1_0.html', '14F962C82AF9DF05AAE67BE300860721', 73, 2, 0, '2014-04-02 01:49:06', 0, '2014-04-02 10:13:06'),
(19, 3, 29, '每日签到水帖', 'http://tt.mop.com/read_14502605_1_0.html', 'A6DDAA1A6090E8542E228A031A99493B', 38, 7, 0, '2014-03-31 13:17:06', 0, '2014-04-02 10:13:06'),
(20, 3, 19, '初音未来应援精美手办', 'http://tt.mop.com/read_11774513_1_0.html', 'F5D852B1FA48E152DD94EB5A85553837', 227, 14, 0, '2014-03-23 10:35:21', 0, '2014-04-02 10:13:12'),
(21, 3, 13, '关爱生命，安全为天', 'http://tt.mop.com/read_14685990_1_0.html', '5B65585500216DAA28A37E83F121C843', 11, 6, 0, '2014-04-01 11:09:54', 0, '2014-04-02 10:13:12'),
(22, 3, 20, '小女22岁，16岁出家门混社会，找一个男友！', 'http://tt.mop.com/read_14327703_1_0.html', 'F09AB3EF8FA31A329ADD39E3D569F323', 10726, 46, 0, '2014-03-31 22:04:37', 0, '2014-04-02 10:13:06'),
(23, 3, 31, '【Mop搜图令】松鼠冰激凌，甜蜜萌翻了。太逼真了', 'http://tt.mop.com/read_14784029_1_0.html', 'BD48109C123451057FED3394F02DD081', 28, 1, 0, '2014-04-01 21:48:05', 0, '2014-04-02 10:13:04'),
(24, 3, 22, '世界菜贩子 第二十章 李杰的初吻', 'http://tt.mop.com/read_14713621_1_0.html', '9B8371EC7976C165A26632C4DB054444', 11, 2, 0, '2014-03-07 21:34:01', 0, '2014-04-02 10:13:10');

-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_site`
-- 

DROP TABLE IF EXISTS `tbl_site`;
CREATE TABLE IF NOT EXISTS `tbl_site` (
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- 
-- 导出表中的数据 `tbl_site`
-- 

INSERT INTO `tbl_site` (`siteId`, `categoryId`, `toolId`, `siteName`, `nickName`, `seedUrl`, `enCode`, `siteHotNum`, `siteGrabable`, `refreshTime`, `siteExp`) VALUES 
(1, 2, 3, '百度贴吧', 'baidutieba', 'http://tieba.baidu.com/f/index/forumpark?cn=&ci=0&pcn=%C9%E7%BB%E1&pci=0&ct=1&st=popular', 'utf-8', 0, 1, '2016-03-08 01:27:16', ''),
(2, 1, 1, '天涯论坛', 'tianyaluntan', 'http://bbs.tianya.cn/', 'utf-8', 0, 1, '2015-11-06 09:23:12', ''),
(3, 1, 1, '猫扑论坛', 'maoputietie', 'http://tt.mop.com/', 'utf-8', 0, 1, '2014-03-15 22:21:20', ''),
(4, 1, 1, '凤凰论坛', 'fenghuangluntan', 'http://bbs.ifeng.com/', 'utf-8', 0, 1, '2014-03-16 15:01:13', ''),
(5, 1, 1, '新浪论坛', 'sinaluntan', 'http://bbs.sina.com.cn/', 'gb2312', 0, 1, '2014-03-16 16:19:57', ''),
(6, 1, 1, '网易论坛', 'wy163luntan', 'http://bbs.163.com/', 'gb2312', 0, 0, '2014-03-26 21:36:34', '');

-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_sitecategory`
-- 

DROP TABLE IF EXISTS `tbl_sitecategory`;
CREATE TABLE IF NOT EXISTS `tbl_sitecategory` (
  `categoryId` int(11) NOT NULL auto_increment,
  `categoryName` varchar(30) NOT NULL,
  `reqLogin` int(11) NOT NULL default '0',
  `categoryExp` text,
  PRIMARY KEY  (`categoryId`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- 导出表中的数据 `tbl_sitecategory`
-- 

INSERT INTO `tbl_sitecategory` (`categoryId`, `categoryName`, `reqLogin`, `categoryExp`) VALUES 
(1, '论坛大类', 0, '包括论坛，社区，BBS'),
(2, '登陆贴吧类', 1, '需要登录的贴吧'),
(3, '博客大类', 0, '包含各种博客');

-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_sitelog`
-- 

DROP TABLE IF EXISTS `tbl_sitelog`;
CREATE TABLE IF NOT EXISTS `tbl_sitelog` (
  `siteLogId` varchar(30) NOT NULL,
  `taskLogId` varchar(30) NOT NULL,
  `siteId` int(11) NOT NULL,
  `siteStatus` int(11) NOT NULL COMMENT '站点分任务执行状态，初始是0',
  `siteThemeNum` int(11) NOT NULL,
  `siteNewPostNum` int(11) NOT NULL,
  `siteUpdatePostNum` int(11) NOT NULL,
  `siteFixPostNum` int(11) NOT NULL,
  `grabCostTime` varchar(50) NOT NULL,
  `fetchNum` int(11) NOT NULL,
  `fetchSuccNum` int(11) NOT NULL,
  `fetchCostTime` varchar(50) NOT NULL,
  `siteLogExp` text NOT NULL,
  PRIMARY KEY  (`siteLogId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- 导出表中的数据 `tbl_sitelog`
-- 


-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_tasklog`
-- 

DROP TABLE IF EXISTS `tbl_tasklog`;
CREATE TABLE IF NOT EXISTS `tbl_tasklog` (
  `taskLogId` varchar(30) NOT NULL COMMENT '以任务的时间戳为主键如：20140402123455',
  `taskStatus` int(11) NOT NULL COMMENT '任务执行到的阶段，初始是0',
  `startTime` datetime NOT NULL,
  `endTime` datetime NOT NULL,
  `costTime` varchar(50) NOT NULL,
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

-- 
-- 导出表中的数据 `tbl_tasklog`
-- 


-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_theme`
-- 

DROP TABLE IF EXISTS `tbl_theme`;
CREATE TABLE IF NOT EXISTS `tbl_theme` (
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=32 ;

-- 
-- 导出表中的数据 `tbl_theme`
-- 

INSERT INTO `tbl_theme` (`themeId`, `siteId`, `themeName`, `themeUrl`, `themeUrlMD5`, `themeGrabable`, `themeHotNum`, `refreshTime`, `themeExp`) VALUES 
(1, 3, '冷笑话', 'http://tt.mop.com/topic/list_209_63_0_0.html', '96E41AEC5264E47C2A6EC4AB72FB0E2A', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的冷笑话主题'),
(2, 3, '电台直播', 'http://tt.mop.com/topic/list_462_438_0_0.html', '9854F085D777B80B7C20521C8E10137B', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的电台直播主题'),
(3, 3, '私房话', 'http://tt.mop.com/topic/list_213_427_0_0.html', 'D0D5617C35F3FD55276D0C96298D3885', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的私房话主题'),
(4, 3, '征婚交友', 'http://tt.mop.com/topic/list_463_304_0_0.html', '30B651DCCA34B960CBD9BD43BA6C4CB9', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的征婚交友主题'),
(5, 3, '联盟乐园', 'http://tt.mop.com/topic/list_291_294_0_0.html', '990B3F5CFE4D2ECC3DB6B294C3EC64D8', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的联盟乐园主题'),
(6, 3, '家有萌宠', 'http://tt.mop.com/topic/list_213_17_0_0.html', '15E619C5A4B42D4177010AEEC30C3343', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的家有萌宠主题'),
(7, 3, '健康养生', 'http://tt.mop.com/topic/list_213_217_0_0.html', '6FDB1FC7318545FD9C1C02E926A206EE', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的健康养生主题'),
(8, 3, '潮流服饰', 'http://tt.mop.com/topic/list_213_214_0_0.html', '7267594E86DE985CE02D3E36F95E0818', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的潮流服饰主题'),
(9, 3, '青芜校园', 'http://tt.mop.com/topic/list_171_172_0_0.html', 'B0AE3A92936BECB24E2EBAC3B766FDB0', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的青芜校园主题'),
(10, 3, '猫扑摄影', 'http://tt.mop.com/topic/list_394_13_0_0.html', '3E4E631C564E25BA4C6594BFD245CC4C', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的猫扑摄影主题'),
(11, 3, '搞笑图片', 'http://tt.mop.com/topic/list_1_12_0_0.html', '5F68BE0B09FD81E87C6ED6547E1018EC', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的搞笑图片主题'),
(12, 3, '军事图片', 'http://tt.mop.com/topic/list_1_14_0_0.html', '96814850DC9F7713767DDCC758E028F8', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的军事图片主题'),
(13, 3, '闲聊茶馆', 'http://tt.mop.com/topic/list_209_210_0_0.html', '7C83D22A4124857D99243C6622720FCF', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的闲聊茶馆主题'),
(14, 3, '爆料台', 'http://tt.mop.com/topic/list_209_429_0_0.html', '97670056CCBA73BA4E0DC3C701153B9D', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的爆料台主题'),
(15, 3, '鬼话连篇', 'http://tt.mop.com/topic/list_101_45_0_0.html', '88E208F2DBF94CEC7C5ECB6041F46508', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的鬼话连篇主题'),
(16, 3, '猫隐村', 'http://tt.mop.com/topic/list_237_238_0_0.html', '974150ED181CBA05DA85C709AC6E55E2', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的猫隐村主题'),
(17, 3, '股票基金', 'http://tt.mop.com/topic/list_136_138_0_0.html', '9BA3DE511868088A7DF748C0C49C78DC', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的股票基金主题'),
(18, 3, '真我秀', 'http://tt.mop.com/topic/list_1_72_0_0.html', '2653E0E16226EF4BC749FDA5D3599024', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的真我秀主题'),
(19, 3, '动漫风云', 'http://tt.mop.com/topic/list_360_44_0_0.html', '2252D2E743F17743830ABB657F315BC3', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的动漫风云主题'),
(20, 3, '单身公寓', 'http://tt.mop.com/topic/list_76_78_0_0.html', 'E2EF0629E509947CD7F04E129029B00B', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的单身公寓主题'),
(21, 3, '车迷交流', 'http://tt.mop.com/topic/list_167_57_0_0.html', 'F2370FCFFE94979D89EA4E6B67163519', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的车迷交流主题'),
(22, 3, '文学小说', 'http://tt.mop.com/topic/list_209_50_0_0.html', '4EC81D85FDD538D89B8F274FA519C252', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的文学小说主题'),
(23, 3, '感情生活', 'http://tt.mop.com/topic/list_76_53_0_0.html', 'EACE8CFA0B19886FE5BFB8B2241E69A8', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的感情生活主题'),
(24, 3, '社会趣闻', 'http://tt.mop.com/topic/list_70_73_0_0.html', '24FD35AEB78A87AEE36F8D07A6776A89', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的社会趣闻主题'),
(25, 3, '足球乐园', 'http://tt.mop.com/topic/list_131_132_0_0.html', 'ED43668499AC8D0526A67B0D073ABFE5', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的足球乐园主题'),
(26, 3, '大话房产', 'http://tt.mop.com/topic/list_216_342_0_0.html', '689B14F8FE8404E59E577CC3499A0AFF', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的大话房产主题'),
(27, 3, '明星八卦', 'http://tt.mop.com/topic/list_94_48_0_0.html', '2E755F8C82268C353969A5D274257791', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的明星八卦主题'),
(28, 3, '视频综合', 'http://tt.mop.com/topic/list_3_239_0_0.html', 'C087E8EBE2135F5CE199D8AA4810E382', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的视频综合主题'),
(29, 3, '灌水专区', 'http://tt.mop.com/topic/list_209_43_0_0.html', '0901E3736F871EC6DBF614B25F49D22E', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的灌水专区主题'),
(30, 3, '社会广角', 'http://tt.mop.com/topic/list_70_19_0_0.html', '34B489C164015FA7751CA6E1A276B469', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的社会广角主题'),
(31, 3, '五花八门', 'http://tt.mop.com/topic/list_1_8_0_0.html', 'F00E0399B0D730E7A115D38459F0EE1D', 1, 0, '2014-04-02 09:30:02', '猫扑论坛中的五花八门主题');

-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_themelog`
-- 

DROP TABLE IF EXISTS `tbl_themelog`;
CREATE TABLE IF NOT EXISTS `tbl_themelog` (
  `themeLogId` varchar(30) NOT NULL,
  `taskLogId` varchar(30) NOT NULL,
  `themeId` int(11) NOT NULL,
  `siteId` int(11) NOT NULL COMMENT '所属站点编号',
  `themeNewPostNum` int(11) NOT NULL,
  `themeUpdatePostNum` int(11) NOT NULL,
  `themeFetchedPostNum` int(11) NOT NULL COMMENT '成功解析的节点数目',
  `themeLogExp` text NOT NULL,
  PRIMARY KEY  (`themeLogId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- 导出表中的数据 `tbl_themelog`
-- 


-- --------------------------------------------------------

-- 
-- 表的结构 `tbl_tool`
-- 

DROP TABLE IF EXISTS `tbl_tool`;
CREATE TABLE IF NOT EXISTS `tbl_tool` (
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
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

-- 
-- 导出表中的数据 `tbl_tool`
-- 

INSERT INTO `tbl_tool` (`toolId`, `toolName`, `grabThemeObj`, `grabPostObj`, `fetchContentObj`, `checkNetObj`, `checkDatabaseObj`, `checkGrabParameObj`, `checkFetchParameObj`, `toolExp`) VALUES 
(1, '基础BBS工具包', 'BasicBBSGrabThemeUrlsImpl', 'BasicBBSGrabPostUrlsImpl', 'BasicBBSFetchContentImpl', 'BasicCheckNetConnection', 'GeneralCheckDbConnection', 'BasicCheckBBSGrabParameAble', 'BasicCheckBBSFetchParameAble', '基础论坛类工具包，适用于绝大多数论坛大类站点'),
(2, 'httpclient链接社区工具包', 'HcBBSGrabThemeUrlsImpl', 'HcBBSGrabPostUrlsImpl', 'HcBBSFetchContentImpl', 'test', 'test', 'test', 'test', '使用httpclient建立连接的基础工具包'),
(3, '简单登录工具包', 'BsLoginBaGrabThemeUrlsImpl', 'BsLoginBaGrabPostUrlsImpl', 'BsLoginBaFetchContentImpl', 'test', 'test', 'test', 'test', '使用java URLconnection的简单登录工具包'),
(4, '基础Blog工具包', 'test', 'test', 'test', 'test', 'test', 'test', 'test', '适用于大部分博客网站的爬取解析和检查');
