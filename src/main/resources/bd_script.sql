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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipo`
--

LOCK TABLES `equipo` WRITE;
/*!40000 ALTER TABLE `equipo` DISABLE KEYS */;
INSERT INTO `equipo` VALUES (1,'ElEquipo1','logo1.png');
INSERT INTO `equipo` VALUES (2,'ElEquipo2','logo2.png');
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugador`
--

LOCK TABLES `jugador` WRITE;
/*!40000 ALTER TABLE `jugador` DISABLE KEYS */;
INSERT INTO `jugador` VALUES (1,'Martin','Pérez','Pinedo','1233211231','2017-05-19','martin.png',2);
INSERT INTO `jugador` VALUES (2,'Pedro','Morales','López','3213213121','2017-05-17','pedro.png',1);
INSERT INTO `jugador` VALUES (6,'Haydé','Delgado','Castro','1233215434','2017-05-23','hayde.png',NULL);
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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'porfirioads@gmail.com','e516f979536994a14d9b0500bca3a1287b9ea9fe','administrador',NULL);
INSERT INTO `usuario` VALUES (2,'haydedc@gmail.com','e516f979536994a14d9b0500bca3a1287b9ea9fe','entrenador','abcd2345');
INSERT INTO `usuario` VALUES (3,'claudio@gmail.com','e516f979536994a14d9b0500bca3a1287b9ea9fe','entrenador','abcd3456');
INSERT INTO `usuario` VALUES (4,'hola@gmail.com','e516f979536994a14d9b0500bca3a1287b9ea9fe','administrador','12345qwert');
INSERT INTO `usuario` VALUES (5,'hola@gmail.com.','5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8','administrador','fGmi13lD8B');
INSERT INTO `usuario` VALUES (6,'hola@gmail.com..','5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8','administrador','kFCWwDDa4T');
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Current Database: `golf`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `golf` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `golf`;

--
-- Table structure for table `apuesta`
--

DROP TABLE IF EXISTS `apuesta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apuesta` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `apuesta_nombre_uindex` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apuesta`
--

LOCK TABLES `apuesta` WRITE;
/*!40000 ALTER TABLE `apuesta` DISABLE KEYS */;
INSERT INTO `apuesta` VALUES (2,'Coneja');
INSERT INTO `apuesta` VALUES (4,'Foursome');
INSERT INTO `apuesta` VALUES (3,'Polla');
INSERT INTO `apuesta` VALUES (1,'Rayas');
/*!40000 ALTER TABLE `apuesta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `apuesta_partido`
--

DROP TABLE IF EXISTS `apuesta_partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `apuesta_partido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `partido_id` int(11) NOT NULL,
  `apuesta_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `apuesta_partido_partido_id_apuesta_id_uindex` (`partido_id`,`apuesta_id`),
  KEY `apuesta_partido_apuesta_id_fk` (`apuesta_id`),
  CONSTRAINT `apuesta_partido_apuesta_id_fk` FOREIGN KEY (`apuesta_id`) REFERENCES `apuesta` (`id`),
  CONSTRAINT `apuesta_partido_partido_id_fk` FOREIGN KEY (`partido_id`) REFERENCES `partido` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `apuesta_partido`
--

LOCK TABLES `apuesta_partido` WRITE;
/*!40000 ALTER TABLE `apuesta_partido` DISABLE KEYS */;
INSERT INTO `apuesta_partido` VALUES (1,1,1);
INSERT INTO `apuesta_partido` VALUES (2,1,2);
INSERT INTO `apuesta_partido` VALUES (3,2,3);
INSERT INTO `apuesta_partido` VALUES (4,3,4);
INSERT INTO `apuesta_partido` VALUES (5,4,1);
/*!40000 ALTER TABLE `apuesta_partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `campo`
--

DROP TABLE IF EXISTS `campo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `campo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100) NOT NULL,
  `ciudad` char(100) NOT NULL,
  `par_hoyo_1` int(11) NOT NULL,
  `par_hoyo_2` int(11) NOT NULL,
  `par_hoyo_3` int(11) NOT NULL,
  `par_hoyo_4` int(11) NOT NULL,
  `par_hoyo_5` int(11) NOT NULL,
  `par_hoyo_6` int(11) NOT NULL,
  `par_hoyo_7` int(11) NOT NULL,
  `par_hoyo_8` int(11) NOT NULL,
  `par_hoyo_9` int(11) NOT NULL,
  `par_hoyo_10` int(11) NOT NULL,
  `par_hoyo_11` int(11) NOT NULL,
  `par_hoyo_12` int(11) NOT NULL,
  `par_hoyo_13` int(11) NOT NULL,
  `par_hoyo_14` int(11) NOT NULL,
  `par_hoyo_15` int(11) NOT NULL,
  `par_hoyo_16` int(11) NOT NULL,
  `par_hoyo_17` int(11) NOT NULL,
  `par_hoyo_18` int(11) NOT NULL,
  `ventaja_hoyo_1` int(11) NOT NULL,
  `ventaja_hoyo_2` int(11) NOT NULL,
  `ventaja_hoyo_3` int(11) NOT NULL,
  `ventaja_hoyo_4` int(11) NOT NULL,
  `ventaja_hoyo_5` int(11) NOT NULL,
  `ventaja_hoyo_6` int(11) NOT NULL,
  `ventaja_hoyo_7` int(11) NOT NULL,
  `ventaja_hoyo_8` int(11) NOT NULL,
  `ventaja_hoyo_9` int(11) NOT NULL,
  `ventaja_hoyo_10` int(11) NOT NULL,
  `ventaja_hoyo_11` int(11) NOT NULL,
  `ventaja_hoyo_12` int(11) NOT NULL,
  `ventaja_hoyo_13` int(11) NOT NULL,
  `ventaja_hoyo_14` int(11) NOT NULL,
  `ventaja_hoyo_15` int(11) NOT NULL,
  `ventaja_hoyo_16` int(11) NOT NULL,
  `ventaja_hoyo_17` int(11) NOT NULL,
  `ventaja_hoyo_18` int(11) NOT NULL,
  `owner_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `campo_usuario_id_fk` (`owner_id`),
  CONSTRAINT `campo_usuario_id_fk` FOREIGN KEY (`owner_id`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `campo`
--

LOCK TABLES `campo` WRITE;
/*!40000 ALTER TABLE `campo` DISABLE KEYS */;
INSERT INTO `campo` VALUES (1,'CampitoDeEjemplo','Aguascalientes',4,5,6,6,3,7,5,6,5,4,3,5,8,8,6,5,4,4,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,2);
INSERT INTO `campo` VALUES (2,'CampitoDeEjemplo','Aguascalientes',4,5,6,6,3,7,5,6,5,4,3,5,8,8,6,5,4,4,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,1);
INSERT INTO `campo` VALUES (3,'CampitoDeEjemplo','zacatecas',4,5,6,6,3,7,5,6,5,4,3,5,8,8,6,5,4,4,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,1);
INSERT INTO `campo` VALUES (4,'CampitoDeEjemplo','zacatecas',4,5,6,6,3,7,5,6,5,4,3,5,8,8,6,5,4,4,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,1);
INSERT INTO `campo` VALUES (5,'CampitoDeEjemplo','zacatecas',4,5,6,6,3,7,5,6,5,4,3,5,8,8,6,5,4,4,18,17,16,15,14,13,12,11,10,9,8,7,6,5,4,3,2,1,1);
/*!40000 ALTER TABLE `campo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clave_consulta_partido`
--

DROP TABLE IF EXISTS `clave_consulta_partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clave_consulta_partido` (
  `clave` char(8) NOT NULL,
  PRIMARY KEY (`clave`),
  UNIQUE KEY `clave_partido_clave_uindex` (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clave_consulta_partido`
--

LOCK TABLES `clave_consulta_partido` WRITE;
/*!40000 ALTER TABLE `clave_consulta_partido` DISABLE KEYS */;
INSERT INTO `clave_consulta_partido` VALUES ('abcdefgh');
INSERT INTO `clave_consulta_partido` VALUES ('ahF6ireP');
INSERT INTO `clave_consulta_partido` VALUES ('CgNDaOsX');
INSERT INTO `clave_consulta_partido` VALUES ('D3gQb4aY');
INSERT INTO `clave_consulta_partido` VALUES ('kuYOjH4C');
INSERT INTO `clave_consulta_partido` VALUES ('WE6DmYZE');
/*!40000 ALTER TABLE `clave_consulta_partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `clave_edicion_partido`
--

DROP TABLE IF EXISTS `clave_edicion_partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clave_edicion_partido` (
  `clave` char(8) NOT NULL,
  PRIMARY KEY (`clave`),
  UNIQUE KEY `clave_edicion_partido_clave_uindex` (`clave`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clave_edicion_partido`
--

LOCK TABLES `clave_edicion_partido` WRITE;
/*!40000 ALTER TABLE `clave_edicion_partido` DISABLE KEYS */;
INSERT INTO `clave_edicion_partido` VALUES ('4mUdEYSq');
INSERT INTO `clave_edicion_partido` VALUES ('abcdefgh');
INSERT INTO `clave_edicion_partido` VALUES ('acpW07KV');
INSERT INTO `clave_edicion_partido` VALUES ('mN8Qm8OO');
INSERT INTO `clave_edicion_partido` VALUES ('tPTFEIa7');
INSERT INTO `clave_edicion_partido` VALUES ('WvUCWXRX');
/*!40000 ALTER TABLE `clave_edicion_partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jugador`
--

DROP TABLE IF EXISTS `jugador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jugador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(20) NOT NULL,
  `handicap` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugador`
--

LOCK TABLES `jugador` WRITE;
/*!40000 ALTER TABLE `jugador` DISABLE KEYS */;
INSERT INTO `jugador` VALUES (1,'Porfirio',10);
INSERT INTO `jugador` VALUES (2,'Margarito',5);
INSERT INTO `jugador` VALUES (3,'Abraham',8);
INSERT INTO `jugador` VALUES (4,'Santiago',2);
INSERT INTO `jugador` VALUES (5,'Adrián',1);
INSERT INTO `jugador` VALUES (6,'Haydé',10);
/*!40000 ALTER TABLE `jugador` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `jugador_partido`
--

DROP TABLE IF EXISTS `jugador_partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `jugador_partido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `jugador_id` int(11) NOT NULL,
  `partido_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `jugador_partido_jugador_id_uindex` (`jugador_id`),
  KEY `jugador_partido_partido_id_fk` (`partido_id`),
  CONSTRAINT `jugador_partido_jugador_id_fk` FOREIGN KEY (`jugador_id`) REFERENCES `jugador` (`id`),
  CONSTRAINT `jugador_partido_partido_id_fk` FOREIGN KEY (`partido_id`) REFERENCES `partido` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugador_partido`
--

LOCK TABLES `jugador_partido` WRITE;
/*!40000 ALTER TABLE `jugador_partido` DISABLE KEYS */;
INSERT INTO `jugador_partido` VALUES (1,1,1);
INSERT INTO `jugador_partido` VALUES (2,2,2);
INSERT INTO `jugador_partido` VALUES (3,3,3);
INSERT INTO `jugador_partido` VALUES (4,4,1);
INSERT INTO `jugador_partido` VALUES (5,5,2);
/*!40000 ALTER TABLE `jugador_partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `partido`
--

DROP TABLE IF EXISTS `partido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `partido` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inicio` datetime DEFAULT NULL,
  `fin` datetime DEFAULT NULL,
  `campo_id` int(11) DEFAULT NULL,
  `clave_consulta` char(8) NOT NULL,
  `clave_edicion` char(8) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `partido_clave_consulta_uindex` (`clave_consulta`),
  UNIQUE KEY `partido_clave_edicion_uindex` (`clave_edicion`),
  KEY `partido_campo_id_fk` (`campo_id`),
  CONSTRAINT `partido_campo_id_fk` FOREIGN KEY (`campo_id`) REFERENCES `campo` (`id`),
  CONSTRAINT `partido_clave_consulta_partido_clave_fk` FOREIGN KEY (`clave_consulta`) REFERENCES `clave_consulta_partido` (`clave`),
  CONSTRAINT `partido_clave_edicion_partido_clave_fk` FOREIGN KEY (`clave_edicion`) REFERENCES `clave_edicion_partido` (`clave`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partido`
--

LOCK TABLES `partido` WRITE;
/*!40000 ALTER TABLE `partido` DISABLE KEYS */;
INSERT INTO `partido` VALUES (1,NULL,NULL,NULL,'abcdefgh','abcdefgh');
INSERT INTO `partido` VALUES (2,'2017-04-10 00:00:00',NULL,2,'ahF6ireP','4mUdEYSq');
INSERT INTO `partido` VALUES (3,'2017-03-11 00:00:00',NULL,1,'D3gQb4aY','tPTFEIa7');
INSERT INTO `partido` VALUES (4,'2017-02-12 00:00:00',NULL,2,'WE6DmYZE','WvUCWXRX');
INSERT INTO `partido` VALUES (5,'2017-01-13 00:00:00',NULL,1,'CgNDaOsX','acpW07KV');
INSERT INTO `partido` VALUES (6,'2017-12-14 00:00:00',NULL,2,'kuYOjH4C','mN8Qm8OO');
/*!40000 ALTER TABLE `partido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `puntuaciones`
--

DROP TABLE IF EXISTS `puntuaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `puntuaciones` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hoyo` int(11) NOT NULL,
  `golpes` int(11) NOT NULL,
  `unidades` int(11) NOT NULL DEFAULT '0',
  `jugador_id` int(11) NOT NULL,
  `partido_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `puntuaciones_id_uindex` (`id`),
  UNIQUE KEY `puntuaciones_jugador_id_partido_id_hoyo_uindex` (`jugador_id`,`partido_id`,`hoyo`),
  KEY `puntuaciones_partido_id_fk` (`partido_id`),
  CONSTRAINT `puntuaciones_jugador_id_fk` FOREIGN KEY (`jugador_id`) REFERENCES `jugador` (`id`),
  CONSTRAINT `puntuaciones_partido_id_fk` FOREIGN KEY (`partido_id`) REFERENCES `partido` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `puntuaciones`
--

LOCK TABLES `puntuaciones` WRITE;
/*!40000 ALTER TABLE `puntuaciones` DISABLE KEYS */;
INSERT INTO `puntuaciones` VALUES (1,1,4,1,1,1);
INSERT INTO `puntuaciones` VALUES (2,2,5,1,1,1);
INSERT INTO `puntuaciones` VALUES (3,3,6,0,1,1);
INSERT INTO `puntuaciones` VALUES (4,4,6,0,1,1);
INSERT INTO `puntuaciones` VALUES (5,5,5,2,1,1);
INSERT INTO `puntuaciones` VALUES (6,1,4,1,2,1);
INSERT INTO `puntuaciones` VALUES (7,2,5,1,2,1);
INSERT INTO `puntuaciones` VALUES (8,3,4,0,2,1);
INSERT INTO `puntuaciones` VALUES (9,4,5,0,2,1);
INSERT INTO `puntuaciones` VALUES (10,5,3,2,2,1);
INSERT INTO `puntuaciones` VALUES (11,1,5,1,2,2);
INSERT INTO `puntuaciones` VALUES (12,2,6,1,2,2);
INSERT INTO `puntuaciones` VALUES (13,3,7,0,2,2);
INSERT INTO `puntuaciones` VALUES (14,4,6,0,2,2);
INSERT INTO `puntuaciones` VALUES (15,5,5,2,2,2);
/*!40000 ALTER TABLE `puntuaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `usuario_email_uindex` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'porfirioadsssss@gmail.com','c890742d209c5b0f0fe90495a3c3359f6b70b311');
INSERT INTO `usuario` VALUES (2,'otrossss@gmail.com','38b0f54891db47818b099f0556a427c7f97a9236');
INSERT INTO `usuario` VALUES (4,'jimenasds@gmail.com','e516f979536994a14d9b0500bca3a1287b9ea9fe');
INSERT INTO `usuario` VALUES (6,'otrox@gmail.com','38b0f54891db47818b099f0556a427c7f97a9236');
INSERT INTO `usuario` VALUES (7,'porfiriss@gmail.com','38b0f54891db47818b099f0556a427c7f97a9236');
INSERT INTO `usuario` VALUES (8,'porfirss@gmail.com','38b0f54891db47818b099f0556a427c7f97a9236');
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

-- Dump completed on 2017-05-07 11:32:23
