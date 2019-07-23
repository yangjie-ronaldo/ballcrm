CREATE DATABASE  IF NOT EXISTS `ballcrmtest` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ballcrmtest`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: 127.0.0.1    Database: ballcrm
-- ------------------------------------------------------
-- Server version	5.7.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `code_def`
--

DROP TABLE IF EXISTS `code_def`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `code_def` (
  `code` varchar(5) NOT NULL COMMENT '代码编号',
  `category` varchar(45) NOT NULL COMMENT '代码类别',
  `definition` varchar(45) NOT NULL COMMENT '代码含义',
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='代码表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code_def`
--

LOCK TABLES `code_def` WRITE;
/*!40000 ALTER TABLE `code_def` DISABLE KEYS */;
INSERT INTO `code_def` VALUES ('CHL01','CHANNELTYPE','walk in'),('CHL02','CHANNELTYPE','定点'),('CHL03','CHANNELTYPE','地推'),('CHL04','CHANNELTYPE','活动'),('CHL05','CHANNELTYPE','口碑'),('CHL06','CHANNELTYPE','TMK'),('PST01','PLANSTATUS','待处理'),('PST02','PLANSTATUS','已处理'),('PST03','PLANSTATUS','待经理审核'),('PST04','PLANSTATUS','作废'),('PST05','PLANSTATUS','申请客户放弃'),('SEX01','SEX','男孩'),('SEX02','SEX','女孩'),('SIG01','SIGNSTATUS','已签到'),('SIG02','SIGNSTATUS','等待上课'),('SIG03','SIGNSTATUS','旷课'),('SIG04','SIGNSTATUS','改期'),('SST01','STUSTATUS','未承诺上门'),('SST02','STUSTATUS','承诺上门'),('SST03','STUSTATUS','承诺未上门'),('SST04','STUSTATUS','上门未交费'),('SST05','STUSTATUS','体验课放弃'),('SST06','STUSTATUS','营销课准备待开班'),('SST07','STUSTATUS','营销课正常上课中'),('SST08','STUSTATUS','营销课中途暂停'),('SST09','STUSTATUS','营销结课未转化'),('SST10','STUSTATUS','营销课放弃'),('SST11','STUSTATUS','正课准备待开班'),('SST12','STUSTATUS','正课正常上课中'),('SST13','STUSTATUS','正课中途暂停'),('SST14','STUSTATUS','正课结课未续费'),('SST15','STUSTATUS','正课放弃'),('STP01','STUTYPE','体验课资源'),('STP02','STUTYPE','营销课资源'),('STP03','STUTYPE','正式课资源'),('STP04','STUTYPE','潜力资源');
/*!40000 ALTER TABLE `code_def` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact_plan`
--

DROP TABLE IF EXISTS `contact_plan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contact_plan` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '联系计划编号',
  `plan_note` varchar(45) DEFAULT NULL COMMENT '联系计划内容',
  `finish_note` varchar(150) DEFAULT NULL COMMENT '完成计划备注',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `plan_date` date DEFAULT NULL COMMENT '联系时间',
  `finish_date` date DEFAULT NULL COMMENT '完成计划时间',
  `sid` int(11) DEFAULT NULL COMMENT '相关客户编号',
  `eid` int(11) DEFAULT NULL COMMENT '创建员工编号',
  `status` varchar(5) DEFAULT NULL COMMENT '联系计划状态',
  `verify_note` varchar(45) DEFAULT NULL COMMENT '审核意见',
  `verify_eid` int(11) DEFAULT NULL COMMENT '审核主管编号',
  PRIMARY KEY (`pkid`),
  KEY `sid` (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='联系计划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_plan`
--
--
-- Table structure for table `course_buy_record`
--

DROP TABLE IF EXISTS `course_buy_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_buy_record` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT,
  `sid` int(11) NOT NULL COMMENT '学员编号',
  `eid` int(11) DEFAULT NULL COMMENT '关单人',
  `course_type_id` int(11) DEFAULT NULL COMMENT '课程编号',
  `fee` int(11) DEFAULT NULL COMMENT '费用',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='学员买课流水记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_buy_record`
--
--
-- Table structure for table `course_phase`
--

DROP TABLE IF EXISTS `course_phase`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_phase` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '阶段编号',
  `name` varchar(45) NOT NULL COMMENT '阶段名称',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='课程阶段';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_phase`
--

LOCK TABLES `course_phase` WRITE;
/*!40000 ALTER TABLE `course_phase` DISABLE KEYS */;
INSERT INTO `course_phase` VALUES (1,'免费'),(2,'体验'),(3,'营销'),(4,'正式');
/*!40000 ALTER TABLE `course_phase` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `course_schedule`
--

DROP TABLE IF EXISTS `course_schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_schedule` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '流水号',
  `sid` int(11) NOT NULL COMMENT '学员编号',
  `booking_date` date NOT NULL COMMENT '上课日期',
  `course_type_id` int(11) DEFAULT NULL COMMENT '课程类型编号',
  `teach_eid` int(11) DEFAULT NULL COMMENT '主教编号',
  `notify_status` varchar(5) DEFAULT NULL COMMENT '通知状态',
  `notify_note` varchar(45) DEFAULT NULL COMMENT '通知备注',
  `sign_status` varchar(5) DEFAULT NULL COMMENT '学员签到状态',
  `trace_status` varchar(5) DEFAULT NULL COMMENT '反馈状态',
  `trace_note` varchar(45) DEFAULT NULL COMMENT '反馈备注',
  `trace_eid` int(11) DEFAULT NULL COMMENT '课程反馈员工',
  `close_eid` int(11) DEFAULT NULL COMMENT '此课程关单员工',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`pkid`),
  KEY `sid` (`sid`),
  KEY `close_eid` (`close_eid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学员上课记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_schedule`
--
--
-- Table structure for table `course_type`
--

DROP TABLE IF EXISTS `course_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `course_type` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '课程类型编号',
  `type_name` varchar(45) DEFAULT NULL COMMENT '类型名',
  `desc` varchar(45) DEFAULT NULL COMMENT '描述',
  `num` int(11) DEFAULT NULL COMMENT '课程次数',
  `fee` int(11) DEFAULT NULL COMMENT '参考费用',
  `valid_day` int(11) DEFAULT NULL COMMENT '参考有效天数',
  `phase_id` int(11) DEFAULT NULL COMMENT '阶段编号',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='课程类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_type`
--

LOCK TABLES `course_type` WRITE;
/*!40000 ALTER TABLE `course_type` DISABLE KEYS */;
INSERT INTO `course_type` VALUES (1,'DEMO课','DEMO体验课',1,0,30,2),(2,'198小课包','198小课包',3,198,60,3),(3,'年卡正课','VIP年卡正课',300,500,365,4),(4,'D12课','ishow课',12,568,60,3);
/*!40000 ALTER TABLE `course_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp_info`
--

DROP TABLE IF EXISTS `emp_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emp_info` (
  `eid` int(11) NOT NULL AUTO_INCREMENT COMMENT '员工编号',
  `name` varchar(45) DEFAULT NULL COMMENT '员工姓名',
  `loginid` varchar(45) DEFAULT NULL COMMENT '登录ID',
  `pass` varchar(45) DEFAULT NULL COMMENT '密码',
  `nid` int(11) DEFAULT NULL COMMENT '所属门店',
  PRIMARY KEY (`eid`),
  UNIQUE KEY `loginid` (`loginid`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='员工信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_info`
--

LOCK TABLES `emp_info` WRITE;
/*!40000 ALTER TABLE `emp_info` DISABLE KEYS */;
INSERT INTO `emp_info` VALUES (1,'boss','boss','123',null),(2,'cc2','cc2','123',1);
/*!40000 ALTER TABLE `emp_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emp_role_rel`
--

DROP TABLE IF EXISTS `emp_role_rel`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `emp_role_rel` (
  `eid` int(11) NOT NULL COMMENT '员工编号',
  `role` varchar(15) NOT NULL COMMENT '角色',
  PRIMARY KEY (`eid`,`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='员工角色关系';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_role_rel`
--

LOCK TABLES `emp_role_rel` WRITE;
/*!40000 ALTER TABLE `emp_role_rel` DISABLE KEYS */;
INSERT INTO `emp_role_rel` VALUES (1,'boss'),(1,'ccm');
/*!40000 ALTER TABLE `emp_role_rel` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login_token`
--

DROP TABLE IF EXISTS `login_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login_token` (
  `eid` int(11) NOT NULL COMMENT '员工登陆id',
  `token` varchar(64) NOT NULL COMMENT 'token',
  `expired` datetime NOT NULL COMMENT '过期时间',
  `status` int(11) NOT NULL COMMENT 'token状态1有效0失效',
  PRIMARY KEY (`eid`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='token验证表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login_token`
--

LOCK TABLES `login_token` WRITE;
/*!40000 ALTER TABLE `login_token` DISABLE KEYS */;
INSERT INTO `login_token` VALUES (0,'fucktoken','2020-09-01 00:00:00',1);
/*!40000 ALTER TABLE `login_token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `node_info`
--

DROP TABLE IF EXISTS `node_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `node_info` (
  `nid` int(11) NOT NULL AUTO_INCREMENT COMMENT '门店编号',
  `name` varchar(45) DEFAULT NULL COMMENT '门店名',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`nid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='门店信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `node_info`
--

LOCK TABLES `node_info` WRITE;
/*!40000 ALTER TABLE `node_info` DISABLE KEYS */;
INSERT INTO `node_info` VALUES (1,'1号店','2019-06-04 10:34:00'),(2,'2号店','2019-08-12 00:00:00');
/*!40000 ALTER TABLE `node_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role` varchar(15) NOT NULL COMMENT '角色',
  `name` varchar(45) NOT NULL COMMENT '角色名称',
  `desc` varchar(45) DEFAULT NULL COMMENT '角色描述',
  PRIMARY KEY (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色列表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('boss','总裁','总负责人'),('cc','销售精英','负责日常学员营销'),('ccm','销售主管','主管门店日常事项'),('teacher','老师','老师');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stu`
--

DROP TABLE IF EXISTS `stu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stu` (
  `sid` int(11) NOT NULL AUTO_INCREMENT COMMENT '学员编号',
  `create_date` datetime NOT NULL COMMENT '创建时间',
  `name` varchar(45) NOT NULL COMMENT '姓名',
  `niki_name` varchar(45) DEFAULT NULL COMMENT '小名',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `sex` varchar(5) DEFAULT NULL COMMENT '性别',
  `tel` varchar(15) DEFAULT NULL COMMENT '电话',
  `status` varchar(15) DEFAULT NULL COMMENT '最新状态',
  `verify_status` varchar(45) DEFAULT NULL COMMENT '审核状态',
  `channel` varchar(15) DEFAULT NULL COMMENT '渠道',
  `channel_note` varchar(45) DEFAULT NULL COMMENT '渠道备注',
  `type` varchar(5) DEFAULT NULL COMMENT '学员类型',
  `wechat` varchar(5) DEFAULT NULL COMMENT '微信',
  `pre_cc` int(11) DEFAULT NULL COMMENT '上一任cc',
  `cc` int(11) DEFAULT NULL COMMENT '所属cc编号',
  `node` int(11) DEFAULT NULL COMMENT '所属门店编号',
  `teacher_id` int(11) DEFAULT NULL COMMENT '关联老师编号',
  `popularize_id` int(11) DEFAULT NULL COMMENT '推广员工号',
  `update_date` datetime DEFAULT NULL COMMENT '最后买课日期',
  PRIMARY KEY (`sid`),
  KEY `cc` (`cc`),
  KEY `node` (`node`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='学员信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stu`
--
--
-- Table structure for table `stu_course`
--

DROP TABLE IF EXISTS `stu_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stu_course` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '买课流水号',
  `sid` int(11) NOT NULL COMMENT '学员编号',
  `eid` int(11) DEFAULT NULL COMMENT '关单员工ID',
  `course_type_id` int(11) NOT NULL COMMENT '课程类型',
  `num` int(11) DEFAULT NULL COMMENT '课时',
  `fee` int(11) DEFAULT NULL COMMENT '费用',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `end_date` datetime DEFAULT NULL COMMENT '截止时间',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='学员买课情况表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stu_course`
--
--
-- Table structure for table `stu_family`
--

DROP TABLE IF EXISTS `stu_family`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stu_family` (
  `sid` int(11) NOT NULL COMMENT '学员编号',
  `pay_ability` int(11) DEFAULT NULL COMMENT '支付能力',
  `pay_will_mother` int(11) DEFAULT NULL COMMENT '妈妈支付意愿',
  `pay_will_father` int(11) DEFAULT NULL COMMENT '爸爸支付意愿',
  `class_weekday` int(11) DEFAULT NULL COMMENT '选班周几',
  `class_time` time DEFAULT NULL COMMENT '选班时间',
  `demo_target` varchar(80) DEFAULT NULL COMMENT '体验课上门目的',
  `education_idea` varchar(80) DEFAULT NULL COMMENT '教育理念',
  `pickup` int(11) DEFAULT NULL COMMENT '是否接送',
  `deal` int(11) DEFAULT NULL COMMENT '是否成交预判',
  `deal_reason` varchar(80) DEFAULT NULL COMMENT '预判原因',
  `update_date` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='学员家庭情况';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stu_family`
--
--
-- Table structure for table `stu_status`
--

DROP TABLE IF EXISTS `stu_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stu_status` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '状态流水',
  `sid` int(11) NOT NULL COMMENT '学员编号',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `status` varchar(5) DEFAULT NULL COMMENT '状态',
  `note` varchar(45) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`pkid`),
  KEY `sid` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='学员状态变更流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stu_status`
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-07-23  9:17:25
