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
-- Table structure for table `administrador`
--

DROP TABLE IF EXISTS `administrador`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `administrador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `ap_paterno` varchar(45) NOT NULL,
  `ap_materno` varchar(45) NOT NULL,
  `usuario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `administrador_usuario_id_uindex` (`usuario_id`),
  CONSTRAINT `administrador_usuario_id_fk` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `administrador`
--

LOCK TABLES `administrador` WRITE;
/*!40000 ALTER TABLE `administrador` DISABLE KEYS */;
/*!40000 ALTER TABLE `administrador` ENABLE KEYS */;
UNLOCK TABLES;

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
  `usuario_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `entrenador_telefono_uindex` (`telefono`),
  UNIQUE KEY `entrenador_url_foto_uindex` (`url_foto`),
  UNIQUE KEY `entrenador_usuario_id_uindex` (`usuario_id`),
  CONSTRAINT `entrenador_usuario_id_fk` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=153 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `entrenador`
--

LOCK TABLES `entrenador` WRITE;
/*!40000 ALTER TABLE `entrenador` DISABLE KEYS */;
INSERT INTO `entrenador` VALUES (62,'Jaime','Delgado','Félix','1122112211','2017-05-25','ent_1122112211',63);
INSERT INTO `entrenador` VALUES (95,'Porfirio','Díaz','Sánchez','4949428616','2017-05-18','ent_4949428616',96);
INSERT INTO `entrenador` VALUES (123,'MiPapus','MiReys','PapsPapss','123Paps123','2017-05-12','ent_123Paps123',125);
INSERT INTO `entrenador` VALUES (146,'Claudio','Díaz','Pérez','3213216544','2017-05-25','ent_3213216544',148);
INSERT INTO `entrenador` VALUES (147,'Ernesto','Ernesto','Ramírez','4322344233','2017-05-10','ent_4322344233',149);
INSERT INTO `entrenador` VALUES (148,'Pablito','Software','Sodel','7897897899','2017-05-11','ent_7897897899',150);
INSERT INTO `entrenador` VALUES (149,'María','De León','Sigg','6546546544','2017-05-05','ent_6546546544',151);
INSERT INTO `entrenador` VALUES (150,'Antonio','García','Domínguez','765ToñoToñ','2017-05-24','ent_765ToñoToñ',152);
INSERT INTO `entrenador` VALUES (151,'Dolores','Lola','Lopez','LolaLolaLo','2017-05-01','ent_LolaLolaLo',153);
INSERT INTO `entrenador` VALUES (152,'Otra','Otre','Otri','OtroOtruOt','2017-05-27','ent_OtroOtruOt',154);
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
  `entrenador_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre_UNIQUE` (`nombre`),
  UNIQUE KEY `url_logo_UNIQUE` (`url_logo`),
  UNIQUE KEY `equipo_entrenador_id_uindex` (`entrenador_id`),
  CONSTRAINT `equipo_entrenador_id_fk` FOREIGN KEY (`entrenador_id`) REFERENCES `entrenador` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `equipo`
--

LOCK TABLES `equipo` WRITE;
/*!40000 ALTER TABLE `equipo` DISABLE KEYS */;
INSERT INTO `equipo` VALUES (84,'papuchos','equ_papuchos',62);
INSERT INTO `equipo` VALUES (85,'mamis','equ_mamis',95);
INSERT INTO `equipo` VALUES (86,'rocas','equ_rocas',123);
INSERT INTO `equipo` VALUES (87,'masmorra','equ_masmorra',146);
INSERT INTO `equipo` VALUES (88,'roomies','equ_roomies',147);
INSERT INTO `equipo` VALUES (89,'loquis','equ_loquis',148);
INSERT INTO `equipo` VALUES (90,'loopers','equ_loopers',149);
INSERT INTO `equipo` VALUES (91,'poopers','equ_poopers',150);
INSERT INTO `equipo` VALUES (92,'mendozas','equ_mendozas',151);
INSERT INTO `equipo` VALUES (93,'otroelultimo','equ_otroelultimo',152);
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
  CONSTRAINT `fk_jugador_equipo` FOREIGN KEY (`equipo_id`) REFERENCES `equipo` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB AUTO_INCREMENT=156 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `jugador`
--

LOCK TABLES `jugador` WRITE;
/*!40000 ALTER TABLE `jugador` DISABLE KEYS */;
INSERT INTO `jugador` VALUES (99,'Francisco','Pérez','Lozano','9999999990','1996-08-21','jug_9999999990',85);
INSERT INTO `jugador` VALUES (100,'Martín','Cabrera','Mendoza','9999999991','1996-09-22','jug_9999999991',90);
INSERT INTO `jugador` VALUES (102,'Esteban','De la Rosa','De la Cruz','9999999993','1996-11-24','jug_9999999993',84);
INSERT INTO `jugador` VALUES (104,'Haydé','Delgado','Castro','9999999995','1998-11-15','jug_9999999995',86);
INSERT INTO `jugador` VALUES (105,'Julieta','Castro','Tejeda','9999999996','1978-10-16','jug_9999999996',88);
INSERT INTO `jugador` VALUES (106,'Leticia','Sánchez','Acevedo','9999999997','1969-10-23','jug_9999999997',89);
INSERT INTO `jugador` VALUES (107,'Jimena Sofía','Díaz','Sánchez','9999999999','2006-08-29','jug_9999999999',87);
INSERT INTO `jugador` VALUES (153,'Esteban','Esteban','Esteban','EstebanEsteban','2017-05-10','jug_EstebanEsteban',91);
INSERT INTO `jugador` VALUES (154,'Mario','Mario','Mario','MarioMario','2017-05-20','jug_MarioMario',92);
INSERT INTO `jugador` VALUES (155,'Pepe','Pepe','Pepe','PepePepePe','2017-05-31','jug_PepePepePe',93);
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
  CONSTRAINT `fk_partido_equipo2` FOREIGN KEY (`equipo_2_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_partido_equipo1` FOREIGN KEY (`equipo_1_id`) REFERENCES `equipo` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `partido`
--

LOCK TABLES `partido` WRITE;
/*!40000 ALTER TABLE `partido` DISABLE KEYS */;
INSERT INTO `partido` VALUES (84,84,85,'2017-05-01 20:00:00','EquiposPar','Clasificación',1,2);
INSERT INTO `partido` VALUES (85,86,87,'2017-05-02 20:00:00','EquiposPar','Clasificación',2,1);
INSERT INTO `partido` VALUES (86,88,89,'2017-05-03 20:00:00','EquiposPar','Clasificación',1,1);
INSERT INTO `partido` VALUES (87,90,91,'2017-05-04 20:00:00','EquiposPar','Clasificación',2,3);
INSERT INTO `partido` VALUES (88,92,93,'2017-05-05 20:00:00','EquiposPar','Clasificación',2,3);
INSERT INTO `partido` VALUES (89,84,93,'2017-05-06 20:00:00','EquiposPar','Clasificación',2,3);
INSERT INTO `partido` VALUES (90,85,92,'2017-05-07 20:00:00','EquiposPar','Clasificación',2,4);
INSERT INTO `partido` VALUES (91,86,91,'2017-05-08 20:00:00','EquiposPar','Clasificación',4,3);
INSERT INTO `partido` VALUES (92,87,90,'2017-05-09 20:00:00','EquiposPar','Clasificación',5,4);
INSERT INTO `partido` VALUES (93,88,89,'2017-05-10 20:00:00','EquiposPar','Clasificación',3,4);
INSERT INTO `partido` VALUES (94,86,88,'2017-05-11 20:00:00','EquiposPar','Cuartos de final',4,5);
INSERT INTO `partido` VALUES (95,93,92,'2017-05-12 20:00:00','EquiposPar','Cuartos de final',5,3);
INSERT INTO `partido` VALUES (96,89,87,'2017-05-13 20:00:00','EquiposPar','Cuartos de final',4,5);
INSERT INTO `partido` VALUES (97,85,91,'2017-05-14 20:00:00','EquiposPar','Cuartos de final',5,4);
INSERT INTO `partido` VALUES (98,93,88,'2017-05-15 20:00:00','EquiposPar','Semifinales',2,3);
INSERT INTO `partido` VALUES (99,85,87,'2017-05-16 20:00:00','EquiposPar','Semifinales',3,2);
INSERT INTO `partido` VALUES (100,85,88,'2017-05-17 20:00:00','EquiposPar','Final',22,33);
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
) ENGINE=InnoDB AUTO_INCREMENT=155 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (63,'jaime@gmail.com','password','entrenador',NULL);
INSERT INTO `usuario` VALUES (96,'porfirioads@gmail.com','password','entrenador',NULL);
INSERT INTO `usuario` VALUES (125,'miguel@gmail.com','password','entrenador','kPWLMwrBiO');
INSERT INTO `usuario` VALUES (148,'claudio@gmail.com','password','entrenador','IjmHhzgoTW');
INSERT INTO `usuario` VALUES (149,'ernesto@gmail.com','password','entrenador','olzc8QnZ3w');
INSERT INTO `usuario` VALUES (150,'pablito@gmail.com','password','entrenador','c8e6ZZi2wd');
INSERT INTO `usuario` VALUES (151,'maria@mail.com','password','entrenador','FfNucpKtaK');
INSERT INTO `usuario` VALUES (152,'tono@gmail.com','password','entrenador','NvxeUR9r31');
INSERT INTO `usuario` VALUES (153,'lolalo@la.lo','password','entrenador','VrWioyEQTM');
INSERT INTO `usuario` VALUES (154,'otro@otro.otro','password','entrenador','DLizCYyzjJ');
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

-- Dump completed on 2017-05-26 10:49:13
