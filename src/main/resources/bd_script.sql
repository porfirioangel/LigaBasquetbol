-- MySQL dump 10.13  Distrib 5.5.49, for debian-linux-gnu (x86_64)
--
-- Host: 127.0.0.1    Database: liga_basquet
-- ------------------------------------------------------
-- Server version	5.5.49-0ubuntu0.14.04.1

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
-- Current Database: `liga_basquet`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `liga_basquet` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `liga_basquet`;

--
-- Table structure for table `entrenador`
--

DROP TABLE IF EXISTS `entrenador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `entrenador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `ap_paterno` varchar(45) NOT NULL,
  `ap_materno` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `url_foto` varchar(255) NOT NULL,
  `equipo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `entrenador_telefono_uindex` (`telefono`),
  UNIQUE KEY `entrenador_url_foto_uindex` (`url_foto`),
  KEY `fk_jugador_equipo_idx` (`equipo_id`),
  CONSTRAINT `fk_jugador_equipo0` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrenador`
--

LOCK TABLES `entrenador` WRITE;
/*!40000 ALTER TABLE `entrenador` DISABLE KEYS */;
/*!40000 ALTER TABLE `entrenador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `equipo`
--

DROP TABLE IF EXISTS `equipo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `equipo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `url_logo` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  UNIQUE KEY `url_logo_UNIQUE` (`url_logo`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipo`
--

LOCK TABLES `equipo` WRITE;
/*!40000 ALTER TABLE `equipo` DISABLE KEYS */;
/*!40000 ALTER TABLE `equipo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jugador`
--

DROP TABLE IF EXISTS `jugador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jugador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `ap_paterno` varchar(45) NOT NULL,
  `ap_materno` varchar(45) NOT NULL,
  `telefono` varchar(45) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `url_foto` varchar(255) NOT NULL,
  `equipo_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `jugador_url_foto_uindex` (`url_foto`),
  UNIQUE KEY `jugador_telefono_uindex` (`telefono`),
  KEY `fk_jugador_equipo_idx` (`equipo_id`),
  CONSTRAINT `fk_jugador_equipo` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugador`
--

LOCK TABLES `jugador` WRITE;
/*!40000 ALTER TABLE `jugador` DISABLE KEYS */;
INSERT INTO `jugador` VALUES (98,'Porfirio Ángel','Díaz','Sánchez','4949428616','1996-07-20','jug_4949428616',NULL);
INSERT INTO `jugador` VALUES (99,'Francisco','Pérez','Lozano','9999999990','1996-08-21','jug_9999999990',NULL);
INSERT INTO `jugador` VALUES (100,'Martín','Cabrera','Mendoza','9999999991','1996-09-22','jug_9999999991',NULL);
INSERT INTO `jugador` VALUES (101,'Rodrigo','Pinedo','Márquez','9999999992','1996-10-23','jug_9999999992',NULL);
INSERT INTO `jugador` VALUES (102,'Esteban','De la Rosa','De la Cruz','9999999993','1996-11-24','jug_9999999993',NULL);
INSERT INTO `jugador` VALUES (103,'Mariana','Ortíz','Jasso','9999999994','1996-12-25','jug_9999999994',NULL);
INSERT INTO `jugador` VALUES (104,'Haydé','Delgado','Castro','9999999995','1998-11-15','jug_9999999995',NULL);
INSERT INTO `jugador` VALUES (105,'Julieta','Castro','Tejeda','9999999996','1978-10-16','jug_9999999996',NULL);
INSERT INTO `jugador` VALUES (106,'Leticia','Sánchez','Acevedo','9999999997','1969-10-23','jug_9999999997',NULL);
INSERT INTO `jugador` VALUES (107,'Jimena Sofía','Díaz','Sánchez','9999999999','2006-08-29','jug_9999999999',NULL);
INSERT INTO `jugador` VALUES (117,'Test','Test','Test','TestTestTe','2017-05-10','jug_TestTestTe',NULL);
/*!40000 ALTER TABLE `jugador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partido`
--

DROP TABLE IF EXISTS `partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `equipo_1_id` int(11) NOT NULL,
  `equipo_2_id` int(11) NOT NULL,
  `fecha` datetime NOT NULL,
  `torneo` varchar(45) NOT NULL,
  `jornada` varchar(45) NOT NULL,
  `puntos_e_1` int(11) DEFAULT NULL,
  `puntos_e_2` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `eq1_eq2_date_uq` (`equipo_2_id`,`equipo_1_id`,`fecha`),
  KEY `fk_partido_equipo1_idx` (`equipo_1_id`),
  KEY `fk_partido_equipo2_idx` (`equipo_2_id`),
  CONSTRAINT `fk_partido_equipo1` FOREIGN KEY (`equipo_1_id`) REFERENCES `equipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_partido_equipo2` FOREIGN KEY (`equipo_2_id`) REFERENCES `equipo` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partido`
--

LOCK TABLES `partido` WRITE;
/*!40000 ALTER TABLE `partido` DISABLE KEYS */;
/*!40000 ALTER TABLE `partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(40) NOT NULL,
  `tipo_usuario` varchar(15) NOT NULL,
  `clave_verificacion` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usuario_UNIQUE` (`email`),
  UNIQUE KEY `usuario_clave_verificacion_uindex` (`clave_verificacion`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-05-10 17:49:19
