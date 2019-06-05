CREATE DATABASE  IF NOT EXISTS `ballcrm` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ballcrm`;
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
INSERT INTO `code_def` VALUES ('PST01','PLANSTATUS','待联系'),('PST02','PLANSTATUS','已处理'),('SEX01','SEX','帅哥'),('SEX02','SEX','美女'),('SST01','STUSTATUS','考虑中'),('SST02','STUSTATUS','已预约'),('STP01','STUTYPE','潜力客户'),('STP02','STUTYPE','小课包客户'),('STP03','STUTYPE','年卡客户');
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
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `plan_date` datetime DEFAULT NULL COMMENT '联系时间',
  `sid` int(11) DEFAULT NULL COMMENT '相关客户编号',
  `eid` int(11) DEFAULT NULL COMMENT '创建员工编号',
  `status` varchar(5) DEFAULT NULL COMMENT '联系计划状态',
  `verify_status` varchar(45) DEFAULT NULL COMMENT '审核状态',
  `verify_note` varchar(45) DEFAULT NULL COMMENT '审核意见',
  `verify_eid` int(11) DEFAULT NULL COMMENT '审核主管编号',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='联系计划';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact_plan`
--

LOCK TABLES `contact_plan` WRITE;
/*!40000 ALTER TABLE `contact_plan` DISABLE KEYS */;
INSERT INTO `contact_plan` VALUES (1,'这个客户优质，要联系！','2019-06-05 18:54:38','2019-09-11 11:00:00',1,1,'PST02',NULL,NULL,NULL),(2,'这个客户优质，要联系！','2019-06-05 19:00:40','2019-09-11 11:00:00',1,1,'PST01',NULL,NULL,NULL),(3,'联系周云龙！','2019-06-05 19:38:50','2019-09-11 11:00:00',1,1,'PST01',NULL,NULL,NULL),(4,'联系周云龙！','2019-06-05 19:52:58','2019-09-11 11:00:00',1,1,'PST01',NULL,NULL,NULL);
/*!40000 ALTER TABLE `contact_plan` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='课程类型';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `course_type`
--

LOCK TABLES `course_type` WRITE;
/*!40000 ALTER TABLE `course_type` DISABLE KEYS */;
INSERT INTO `course_type` VALUES (1,'DEMO课','DEMO体验课',1),(2,'198小课包','198小课包',3);
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
  `role` varchar(45) DEFAULT NULL COMMENT '角色',
  `nid` int(11) DEFAULT NULL COMMENT '所属门店',
  PRIMARY KEY (`eid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='员工信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emp_info`
--

LOCK TABLES `emp_info` WRITE;
/*!40000 ALTER TABLE `emp_info` DISABLE KEYS */;
INSERT INTO `emp_info` VALUES (1,'小李','cc1','123','cc',1),(2,'小张','cc2','123','cc',1);
/*!40000 ALTER TABLE `emp_info` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='门店信息';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `node_info`
--

LOCK TABLES `node_info` WRITE;
/*!40000 ALTER TABLE `node_info` DISABLE KEYS */;
INSERT INTO `node_info` VALUES (1,'1号店','2019-06-04 10:34:00');
/*!40000 ALTER TABLE `node_info` ENABLE KEYS */;
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
  `cc` int(11) DEFAULT NULL COMMENT '所属cc编号',
  `node` int(11) DEFAULT NULL COMMENT '所属门店编号',
  PRIMARY KEY (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8 COMMENT='学员信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stu`
--

LOCK TABLES `stu` WRITE;
/*!40000 ALTER TABLE `stu` DISABLE KEYS */;
INSERT INTO `stu` VALUES (1,'2019-06-04 10:35:44','杨杰1','杨杨','1988-09-18','SEX01','18583281859','SST01',NULL,'自助','自己拉的','STP01',NULL,1,1),(2,'2019-06-04 15:05:27','杨杰2',NULL,NULL,'SEX02',NULL,NULL,NULL,NULL,NULL,'STP01',NULL,NULL,NULL),(3,'2019-06-04 15:05:27','杨杰3',NULL,NULL,'SEX01',NULL,NULL,NULL,NULL,NULL,'STP01',NULL,NULL,NULL),(4,'2019-06-04 15:05:27','杨杰4',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'STP01',NULL,NULL,NULL),(5,'2019-06-04 15:05:27','杨杰5',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'STP01',NULL,NULL,NULL),(6,'2019-06-04 15:05:27','杨杰6',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'STP01',NULL,NULL,NULL),(7,'2019-06-04 15:05:27','龙龙1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(8,'2019-06-04 15:05:27','龙龙2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(9,'2019-06-04 15:17:45','qqq123',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(10,'2019-06-04 15:19:00','bbbb123?status=考虑中',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(11,'2019-06-04 15:19:28','周周1',NULL,NULL,NULL,NULL,'SST01',NULL,NULL,NULL,'STP02',NULL,NULL,NULL),(12,'2019-06-04 16:24:24','柯基1',NULL,'1988-09-17',NULL,'18583281859',NULL,NULL,NULL,NULL,NULL,NULL,1,1),(13,'2019-06-04 16:34:19','柯基2',NULL,'1988-09-17',NULL,'18583281859',NULL,NULL,NULL,NULL,NULL,NULL,1,1),(14,'2019-06-04 16:34:41','柯基3',NULL,'1988-09-17',NULL,'18583281859',NULL,NULL,NULL,NULL,NULL,NULL,1,1),(15,'2019-06-04 16:38:21','柯基4',NULL,'1988-09-17',NULL,'18583281859',NULL,NULL,NULL,NULL,NULL,NULL,1,1),(16,'2019-06-04 16:44:44','柯基5',NULL,'1988-09-18',NULL,'18583281859',NULL,NULL,NULL,NULL,NULL,NULL,1,1),(17,'2019-06-05 13:12:09','柯基6',NULL,'1988-09-18','SEX01','18583281859',NULL,NULL,NULL,NULL,NULL,NULL,1,1),(18,'2019-06-05 16:07:53','阿铁1',NULL,'1988-09-18','SEX01','18583281859',NULL,NULL,NULL,NULL,NULL,NULL,1,1),(19,'2019-06-05 17:04:13','魂牵梦萦',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(20,'2019-06-05 17:04:13','bb',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(21,'2019-06-05 16:54:51','晶晶1',NULL,'1988-09-17','SEX02','18583281859',NULL,NULL,NULL,NULL,NULL,NULL,1,1),(22,'2019-06-05 19:48:30','qqfdsa1',NULL,'1988-09-17','SEX01','18583281859','SST01',NULL,NULL,NULL,'STP01',NULL,1,1);
/*!40000 ALTER TABLE `stu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stu_course`
--

DROP TABLE IF EXISTS `stu_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stu_course` (
  `pkid` int(11) NOT NULL AUTO_INCREMENT COMMENT '买课流水号',
  `sid` int(11) NOT NULL COMMENT '学员编号',
  `course_type_id` int(11) NOT NULL COMMENT '课程类型',
  `num` int(11) DEFAULT NULL COMMENT '课时',
  `fee` int(11) DEFAULT NULL COMMENT '费用',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `end_date` datetime DEFAULT NULL COMMENT '截止时间',
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='学员买课情况表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stu_course`
--

LOCK TABLES `stu_course` WRITE;
/*!40000 ALTER TABLE `stu_course` DISABLE KEYS */;
INSERT INTO `stu_course` VALUES (1,21,1,1,0,'2019-06-05 16:54:51',NULL),(2,22,1,1,0,'2019-06-05 19:48:30',NULL);
/*!40000 ALTER TABLE `stu_course` ENABLE KEYS */;
UNLOCK TABLES;

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
  PRIMARY KEY (`pkid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='学员状态变更流水表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stu_status`
--

LOCK TABLES `stu_status` WRITE;
/*!40000 ALTER TABLE `stu_status` DISABLE KEYS */;
INSERT INTO `stu_status` VALUES (2,21,'2019-06-05 16:54:51','STP01','潜力客户来咯！努力与他建立良好关系吧！'),(3,22,'2019-06-05 19:48:30','STP01','潜力客户来咯！努力与他建立良好关系吧！');
/*!40000 ALTER TABLE `stu_status` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-06-05 20:15:43
