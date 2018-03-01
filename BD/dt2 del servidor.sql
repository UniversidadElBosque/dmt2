-- phpMyAdmin SQL Dump
-- version 4.7.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 21, 2017 at 12:14 AM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dt2`
--

-- --------------------------------------------------------

--
-- Table structure for table `actividadfisica`
--

CREATE TABLE `actividadfisica` (
  `paciente` bigint(11) NOT NULL,
  `fecha` date NOT NULL,
  `ejercicio30min` int(11) NOT NULL,
  `escogeejercicio` int(11) NOT NULL,
  `ejerciciosconotros` int(11) NOT NULL,
  `limitacionesaejercicios` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `actividadfisica`
--

INSERT INTO `actividadfisica` (`paciente`, `fecha`, `ejercicio30min`, `escogeejercicio`, `ejerciciosconotros`, `limitacionesaejercicios`) VALUES
(1001, '2017-09-19', 3, 1, 1, 4),
(52362520, '2017-09-30', 4, 1, 3, 1),
(2002, '2017-10-06', 3, 1, 4, 5),
(41414866, '2017-10-10', 3, 3, 3, 3),
(99083065, '2017-10-13', 4, 2, 2, 4),
(41414866, '2017-10-13', 3, 3, 3, 3),
(1030548087, '2017-10-15', 3, 3, 3, 3),
(2006, '2017-10-16', 0, 2, 4, 1),
(19403577, '2017-10-17', 3, 3, 3, 3),
(88222234, '2017-10-19', 3, 3, 3, 3),
(40367108, '2017-10-20', 3, 3, 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `animo`
--

CREATE TABLE `animo` (
  `paciente` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `animo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `animo`
--

INSERT INTO `animo` (`paciente`, `fecha`, `animo`) VALUES
(1001, '2017-09-18', 0),
(1001, '2017-09-14', 4),
(1001, '2017-09-15', 3),
(1001, '2017-09-16', 5),
(1001, '2017-09-17', 2),
(1001, '2017-09-18', 3),
(1001, '2017-09-21', 3),
(1001, '2017-09-22', 4),
(1001, '2017-09-23', 4),
(52362520, '2017-09-23', 0),
(123, '2017-09-29', 0),
(222222, '2017-10-01', 0),
(2002, '2017-10-05', 0),
(1001, '2017-10-05', 3),
(52362520, '2017-10-05', 3),
(2002, '2017-10-06', 4),
(19403577, '2017-10-06', 0),
(41414866, '2017-10-10', 0),
(2002, '2017-10-10', 4),
(99083065, '2017-10-12', 0),
(1001, '2017-10-12', 4),
(1030548087, '2017-10-13', 0),
(41414866, '2017-10-13', 3),
(2006, '2017-10-14', 0),
(99083065, '2017-10-14', 2),
(41414866, '2017-10-14', 2),
(41414866, '2017-10-15', 2),
(41414866, '2017-10-16', 2),
(1001, '2017-10-17', 3),
(88222234, '2017-10-17', 0),
(41414866, '2017-10-17', 2),
(19403577, '2017-10-17', 3),
(40367108, '2017-10-19', 0),
(41414866, '2017-10-19', 4),
(40367108, '2017-10-20', 4);

-- --------------------------------------------------------

--
-- Table structure for table `apoyosocial`
--

CREATE TABLE `apoyosocial` (
  `paciente` bigint(11) NOT NULL,
  `fecha` date NOT NULL,
  `familiaapoya` int(11) NOT NULL,
  `amigosapoyan` int(11) NOT NULL,
  `satisfechoapoyado` int(11) NOT NULL,
  `puedehablar` int(11) NOT NULL,
  `reunoamigos` int(11) NOT NULL,
  `sepreocupanpormi` int(11) NOT NULL,
  `hablaproblemas` int(11) NOT NULL,
  `atiendoconsejos` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `apoyosocial`
--

INSERT INTO `apoyosocial` (`paciente`, `fecha`, `familiaapoya`, `amigosapoyan`, `satisfechoapoyado`, `puedehablar`, `reunoamigos`, `sepreocupanpormi`, `hablaproblemas`, `atiendoconsejos`) VALUES
(1001, '2017-09-19', 3, 4, 2, 0, 4, 3, 3, 1),
(52362520, '2017-09-23', 4, 1, 3, 5, 4, 5, 3, 4),
(52362520, '2017-09-29', 4, 1, 4, 2, 4, 4, 1, 4),
(222222, '2017-10-01', 4, 5, 1, 5, 1, 3, 3, 2),
(2002, '2017-10-05', 1, 0, 5, 5, 0, 5, 5, 0),
(19403577, '2017-10-06', 4, 2, 4, 4, 2, 3, 4, 2),
(41414866, '2017-10-10', 3, 3, 3, 3, 3, 3, 3, 3),
(99083065, '2017-10-12', 3, 3, 3, 3, 3, 3, 3, 3),
(1030548087, '2017-10-13', 3, 3, 3, 3, 3, 3, 3, 3),
(2006, '2017-10-14', 4, 0, 5, 0, 2, 3, 3, 5),
(88222234, '2017-10-17', 4, 3, 4, 4, 3, 4, 4, 4),
(40367108, '2017-10-19', 5, 3, 5, 5, 5, 5, 5, 5);

-- --------------------------------------------------------

--
-- Table structure for table `autocuidado`
--

CREATE TABLE `autocuidado` (
  `paciente` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `tiempoenmicuidado` int(11) NOT NULL,
  `buscoinfo` int(11) NOT NULL,
  `conozcomedicamentos` int(11) NOT NULL,
  `tengoprecaucion` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `autocuidado`
--

INSERT INTO `autocuidado` (`paciente`, `fecha`, `tiempoenmicuidado`, `buscoinfo`, `conozcomedicamentos`, `tengoprecaucion`) VALUES
(1001, '2017-09-19', 3, 1, 1, 4),
(52362520, '2017-09-30', 4, 1, 3, 1),
(2002, '2017-10-06', 3, 1, 4, 5),
(41414866, '2017-10-10', 2, 2, 2, 2),
(99083065, '2017-10-13', 4, 2, 2, 4),
(41414866, '2017-10-13', 3, 3, 3, 3),
(1030548087, '2017-10-15', 3, 3, 3, 3),
(2006, '2017-10-16', 0, 2, 4, 1),
(19403577, '2017-10-17', 3, 3, 3, 3),
(88222234, '2017-10-19', 3, 3, 3, 3),
(40367108, '2017-10-20', 3, 3, 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `autoeficacia`
--

CREATE TABLE `autoeficacia` (
  `paciente` bigint(11) NOT NULL,
  `fecha` date NOT NULL,
  `metasrealistas` int(11) NOT NULL,
  `metasplan` int(11) NOT NULL,
  `conocemotivaciones` int(11) NOT NULL,
  `aceptarecomendaciones` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `autoeficacia`
--

INSERT INTO `autoeficacia` (`paciente`, `fecha`, `metasrealistas`, `metasplan`, `conocemotivaciones`, `aceptarecomendaciones`) VALUES
(1001, '2017-09-19', 1, 3, 4, 5),
(52362520, '2017-09-30', 4, 4, 1, 4),
(2002, '2017-10-06', 3, 5, 5, 1),
(41414866, '2017-10-10', 3, 3, 3, 3),
(99083065, '2017-10-13', 2, 3, 3, 5),
(41414866, '2017-10-13', 3, 3, 3, 3),
(1030548087, '2017-10-15', 3, 3, 3, 3),
(2006, '2017-10-16', 3, 5, 1, 4),
(19403577, '2017-10-17', 3, 3, 3, 3),
(88222234, '2017-10-19', 3, 3, 3, 3),
(40367108, '2017-10-20', 3, 3, 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `avances`
--

CREATE TABLE `avances` (
  `identificacion` bigint(11) NOT NULL,
  `fecha` date NOT NULL,
  `completado` int(11) NOT NULL,
  `entradas` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `avances`
--

INSERT INTO `avances` (`identificacion`, `fecha`, `completado`, `entradas`) VALUES
(1001, '2017-09-18', 7, 56),
(1000, '2017-09-01', 2, 68),
(3002078021, '2017-09-22', 0, 0),
(3005733045, '2017-09-22', 2, 10),
(3006491142, '2017-09-22', 2, 11),
(3014696578, '2017-09-22', 2, 84),
(3103196004, '2017-09-22', 2, 9),
(3105644680, '2017-09-22', 2, 9),
(3115619771, '2017-09-22', 0, 0),
(3125762992, '2017-09-22', 0, 0),
(3153254672, '2017-09-22', 2, 7),
(3164293458, '2017-09-22', 0, 0),
(3197047211, '2017-09-22', 0, 0),
(3197668358, '2017-09-22', 2, 17),
(3214680957, '2017-09-22', 2, 1),
(52362520, '2017-09-23', 7, 4),
(123, '2017-09-29', 0, 3),
(222222, '2017-10-01', 4, 3),
(2002, '2017-10-05', 7, 22),
(19403577, '2017-10-06', 7, 5),
(41414866, '2017-10-10', 7, 19),
(99083065, '2017-10-12', 7, 5),
(1030548087, '2017-10-13', 7, 6),
(1006, '2017-10-14', 0, 6),
(2006, '2017-10-14', 7, 2),
(88222234, '2017-10-17', 7, 3),
(40367108, '2017-10-19', 7, 18);

-- --------------------------------------------------------

--
-- Table structure for table `ciudad`
--

CREATE TABLE `ciudad` (
  `id` bigint(11) NOT NULL,
  `nombre` text NOT NULL,
  `pais` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `ciudad`
--

INSERT INTO `ciudad` (`id`, `nombre`, `pais`) VALUES
(1, 'Bogotá', 1),
(2, 'Cali', 1),
(3, 'Medellín', 1),
(4, 'Barranquillla', 1),
(5, 'Bucaramanga', 1),
(6, 'Cartagena', 1);

-- --------------------------------------------------------

--
-- Table structure for table `comorbilidad`
--

CREATE TABLE `comorbilidad` (
  `paciente` bigint(11) NOT NULL,
  `fecha` date NOT NULL,
  `dislipidemia` tinyint(1) NOT NULL,
  `hipertension` tinyint(1) NOT NULL,
  `enfermedadpulmonar` tinyint(1) NOT NULL,
  `hipotiroidismo` tinyint(1) NOT NULL,
  `retinopatia` tinyint(1) NOT NULL,
  `nefropatia` tinyint(1) NOT NULL,
  `neuropatia` tinyint(1) NOT NULL,
  `piediabetico` tinyint(1) NOT NULL,
  `enfermedadcardiovascular` tinyint(1) NOT NULL,
  `cordicaloclusiva` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comorbilidad`
--

INSERT INTO `comorbilidad` (`paciente`, `fecha`, `dislipidemia`, `hipertension`, `enfermedadpulmonar`, `hipotiroidismo`, `retinopatia`, `nefropatia`, `neuropatia`, `piediabetico`, `enfermedadcardiovascular`, `cordicaloclusiva`) VALUES
(123, '2017-09-29', 1, 0, 1, 1, 0, 0, 1, 0, 0, 0),
(1001, '2017-09-18', 1, 0, 1, 0, 0, 1, 0, 1, 0, 0),
(2002, '2017-10-05', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1),
(2006, '2017-10-14', 1, 0, 1, 0, 0, 0, 0, 1, 0, 0),
(222222, '2017-10-01', 1, 0, 0, 1, 1, 0, 1, 0, 1, 1),
(19403577, '2017-10-06', 0, 1, 0, 1, 0, 0, 0, 0, 0, 0),
(40367108, '2017-10-19', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(41414866, '2017-10-10', 0, 1, 0, 0, 1, 0, 0, 0, 1, 1),
(52362520, '2017-09-23', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0),
(88222234, '2017-10-17', 0, 0, 0, 0, 1, 0, 0, 0, 0, 0),
(99083065, '2017-10-12', 1, 1, 1, 0, 1, 0, 0, 0, 0, 0),
(1030548087, '2017-10-13', 1, 1, 0, 0, 0, 1, 0, 0, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `comunidad`
--

CREATE TABLE `comunidad` (
  `fecha` date NOT NULL,
  `tema` text NOT NULL,
  `autor` bigint(11) NOT NULL,
  `participante` bigint(11) NOT NULL,
  `mensaje` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `comunidad`
--

INSERT INTO `comunidad` (`fecha`, `tema`, `autor`, `participante`, `mensaje`) VALUES
('2017-09-23', 'Experiencias con MiDT2 con psicoeducación', 1000, 0, ''),
('2017-09-23', 'Experiencias con MiDT2 con psicoeducación', 0, 1000, 'Por la velocidad y poco tiempo que quedaba para entregar la App, no se ha podido probar completamente, no nos extraña que aparezcan muchos bugs'),
('2017-09-29', 'Experiencias con MiDT2 con psicoeducación', 0, 1030548086, 'Creo que las opciones de Metas y habitos saludables seran de gran ayuda para mi tratamiento.'),
('2017-09-30', 'Experiencias con MiDT2 con psicoeducación', 0, 52362520, ''),
('2017-10-14', 'la familia y la diabetes', 1006, 0, ''),
('2017-10-14', 'la familia y la diabetes', 0, 1006, 'este foro es para compartir experiencias '),
('2017-10-17', 'la familia y la diabetes', 0, 88222234, 'La familia es apoyo total en la diabetes '),
('2017-10-20', 'Experiencias con MiDT2 con psicoeducación', 0, 40367108, 'me atendio Diego\n');

-- --------------------------------------------------------

--
-- Table structure for table `diagnosticoinicial`
--

CREATE TABLE `diagnosticoinicial` (
  `paciente` bigint(11) NOT NULL,
  `fecha` date NOT NULL,
  `tension1` int(11) NOT NULL,
  `tension2` int(11) NOT NULL,
  `trigliceridos` int(11) NOT NULL,
  `hba1c` float(2,1) NOT NULL,
  `peso` int(11) NOT NULL,
  `talla` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `diagnosticoinicial`
--

INSERT INTO `diagnosticoinicial` (`paciente`, `fecha`, `tension1`, `tension2`, `trigliceridos`, `hba1c`, `peso`, `talla`) VALUES
(1001, '2017-09-18', 180, 80, 210, 6.7, 90, 175),
(2002, '2017-10-05', 180, 90, 174, 7.0, 99, 168),
(2006, '2017-10-14', 173, 87, 190, 6.5, 98, 170),
(222222, '2017-10-01', 17, 5, 25, 5.7, 80, 200),
(19403577, '2017-10-06', 160, 20, 111, 5.6, 80, 80),
(40367108, '2017-10-19', 125, 62, 148, 7.5, 60, 157),
(41414866, '2017-10-10', 150, 45, 153, 5.8, 56, 152),
(52362520, '2017-09-23', 140, 90, 160, 5.9, 60, 165),
(88222234, '2017-10-17', 110, 70, 140, 7.5, 61, 168),
(99083065, '2017-10-12', 34, 6, 23, 5.7, 65, 170),
(1030548087, '2017-10-13', 150, 80, 147, 5.4, 65, 168);

-- --------------------------------------------------------

--
-- Table structure for table `estadoanimo`
--

CREATE TABLE `estadoanimo` (
  `paciente` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `puedecuidarse` int(11) NOT NULL,
  `optimistafuturo` int(11) NOT NULL,
  `seenoja` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `estadoanimo`
--

INSERT INTO `estadoanimo` (`paciente`, `fecha`, `puedecuidarse`, `optimistafuturo`, `seenoja`) VALUES
(1001, '2017-09-19', 1, 5, 3),
(52362520, '2017-09-30', 1, 4, 1),
(2002, '2017-10-06', 5, 3, 4),
(41414866, '2017-10-10', 3, 3, 3),
(99083065, '2017-10-13', 3, 3, 1),
(41414866, '2017-10-13', 3, 3, 3),
(1030548087, '2017-10-15', 3, 3, 3),
(2006, '2017-10-16', 4, 1, 3),
(19403577, '2017-10-17', 3, 3, 3),
(88222234, '2017-10-19', 3, 3, 3),
(40367108, '2017-10-20', 3, 3, 3);

-- --------------------------------------------------------

--
-- Table structure for table `estilovida`
--

CREATE TABLE `estilovida` (
  `paciente` bigint(11) NOT NULL,
  `fecha` date NOT NULL,
  `comercada3o4horas` int(11) NOT NULL,
  `resistetentacion` int(11) NOT NULL,
  `eligebien` int(11) NOT NULL,
  `frutas3verduras2` int(11) NOT NULL,
  `comesano` int(11) NOT NULL,
  `nofritos` int(11) NOT NULL,
  `dietaespecializada` int(11) NOT NULL,
  `comesal` int(11) NOT NULL,
  `comefibra` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `estilovida`
--

INSERT INTO `estilovida` (`paciente`, `fecha`, `comercada3o4horas`, `resistetentacion`, `eligebien`, `frutas3verduras2`, `comesano`, `nofritos`, `dietaespecializada`, `comesal`, `comefibra`) VALUES
(1001, '2017-09-19', 5, 1, 1, 4, 2, 4, 2, 3, 4),
(52362520, '2017-09-23', 2, 1, 4, 0, 1, 3, 4, 5, 1),
(52362520, '2017-09-29', 3, 3, 3, 3, 3, 3, 3, 3, 3),
(222222, '2017-10-01', 3, 4, 1, 3, 4, 0, 0, 5, 2),
(2002, '2017-10-05', 4, 0, 0, 5, 4, 5, 4, 3, 5),
(19403577, '2017-10-06', 4, 1, 2, 2, 3, 2, 2, 2, 4),
(41414866, '2017-10-10', 3, 3, 3, 3, 3, 3, 3, 3, 3),
(99083065, '2017-10-12', 3, 3, 3, 3, 3, 3, 3, 3, 3),
(1030548087, '2017-10-13', 3, 3, 3, 3, 3, 3, 3, 3, 3),
(2006, '2017-10-14', 3, 1, 5, 5, 3, 5, 3, 1, 3),
(88222234, '2017-10-17', 4, 4, 4, 3, 4, 2, 4, 2, 4),
(40367108, '2017-10-19', 5, 5, 5, 5, 3, 5, 4, 5, 3);

-- --------------------------------------------------------

--
-- Table structure for table `fyc`
--

CREATE TABLE `fyc` (
  `paciente` bigint(11) NOT NULL,
  `fecha` date NOT NULL,
  `fuma` tinyint(1) NOT NULL,
  `cuantosfuma` int(11) NOT NULL,
  `consecuenciasfumar` tinyint(4) NOT NULL,
  `alcohol` tinyint(1) NOT NULL,
  `cantidadalcohol` int(11) NOT NULL,
  `consecuenciasalcohol` tinyint(4) NOT NULL,
  `dt2todavida` tinyint(1) NOT NULL,
  `controlcondym` tinyint(1) NOT NULL,
  `organos` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fyc`
--

INSERT INTO `fyc` (`paciente`, `fecha`, `fuma`, `cuantosfuma`, `consecuenciasfumar`, `alcohol`, `cantidadalcohol`, `consecuenciasalcohol`, `dt2todavida`, `controlcondym`, `organos`) VALUES
(1001, '2017-09-19', 1, 60, 1, 1, 6, 1, 1, 1, 'Pies'),
(52362520, '2017-09-23', 0, 0, 1, 0, 0, 1, 1, 1, 'corazón, ojos (visión)'),
(52362520, '2017-09-29', 0, 0, 0, 0, 0, 1, 1, 1, 'corazón, pies, ojos'),
(222222, '2017-10-01', 0, 0, 0, 0, 0, 0, 1, 1, ''),
(2002, '2017-10-05', 1, 208, 1, 1, 800, 1, 1, 1, 'pulmones'),
(19403577, '2017-10-06', 0, 0, 1, 0, 0, 1, 1, 1, 'pancreas, ojos'),
(41414866, '2017-10-10', 0, 0, 1, 0, 0, 1, 1, 1, 'pies'),
(99083065, '2017-10-12', 1, 84, 1, 0, 0, 0, 1, 1, ''),
(1030548087, '2017-10-13', 0, 0, 1, 0, 0, 1, 1, 1, 'pies'),
(2006, '2017-10-14', 1, 60, 1, 1, 500, 1, 1, 1, ''),
(88222234, '2017-10-17', 1, 300, 1, 1, 100, 1, 1, 1, 'Ojos, organos blandos '),
(40367108, '2017-10-19', 0, 0, 1, 0, 0, 1, 1, 1, 'pulmones, riñones, vista');

-- --------------------------------------------------------

--
-- Table structure for table `glucosa`
--

CREATE TABLE `glucosa` (
  `id` bigint(20) NOT NULL,
  `fechahora` datetime NOT NULL,
  `glucosa` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `glucosa`
--

INSERT INTO `glucosa` (`id`, `fechahora`, `glucosa`) VALUES
(1001, '2017-09-14 00:00:00', 0),
(52362520, '2017-09-23 10:29:36', 0),
(123, '2017-09-29 13:02:21', 0),
(222222, '2017-10-01 15:19:35', 0),
(2002, '2017-10-05 12:28:50', 0),
(19403577, '2017-10-06 12:18:42', 0),
(41414866, '2017-10-10 14:52:19', 0),
(99083065, '2017-10-12 17:42:24', 0),
(1030548087, '2017-10-13 14:11:27', 0),
(2006, '2017-10-14 14:19:15', 0),
(2006, '2017-10-14 14:53:46', 86),
(1001, '2017-10-17 13:09:08', 180),
(1001, '2017-10-17 13:09:41', 140),
(88222234, '2017-10-17 16:14:27', 0),
(88222234, '2017-10-17 17:11:13', 145),
(40367108, '2017-10-19 09:36:54', 0),
(88222234, '2017-10-19 15:37:59', 135),
(40367108, '2017-10-20 16:12:10', 128);

-- --------------------------------------------------------

--
-- Table structure for table `habitos`
--

CREATE TABLE `habitos` (
  `idfila` int(11) NOT NULL,
  `titulo` text NOT NULL,
  `descripcion` text NOT NULL,
  `contenido` longtext NOT NULL,
  `imagen` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `habitos`
--

INSERT INTO `habitos` (`idfila`, `titulo`, `descripcion`, `contenido`, `imagen`) VALUES
(1, 'La dieta mediterranea', 'La Dieta Mediterránea es una valiosa herencia cultural que representa mucho más que una simple pauta nutricional, rica y saludable. Es un estilo de vida equilibrado que recoge recetas, formas de cocinar, celebraciones, costumbres, productos típicos y actividades humanas diversas.', '1. UTILIZAR EL ACEITE DE OLIVA COMO PRINCIPAL GRASA DE ADICIÓN\nEs el aceite más utilizado en la cocina mediterránea. Es un alimento rico en vitamina E, beta-carotenos y ácidos grasos monoinsaturados que le confieren propiedades cardioprotectoras. Este alimento representa un tesoro dentro de la dieta mediterránea, y ha perdurado a través de siglos entre las costumbres gastronómicas regionales, otorgando a los platos un sabor y aroma únicos.\n\n2. CONSUMIR ALIMENTOS DE ORIGEN VEGETAL EN ABUNDANCIA: FRUTAS, VERDURAS, LEGUMBRES, CHAMPIÑONES Y FRUTOS SECOS\nLas verduras, hortalizas y frutas son la principal fuente de vitaminas, minerales y fibra de nuestra dieta y nos aportan al mismo tiempo, una gran cantidad de agua. Es fundamental consumir 5 raciones de fruta y verdura a diario. Gracias a su contenido elevado en antioxidantes y fibra pueden contribuir a prevenir, entre otras, algunas enfermedades cardiovasculares y algunos tipos de cáncer.\n\n3. EL PAN Y LOS ALIMENTOS PROCEDENTES DE CEREALES (PASTA, ARROZ Y ESPECIALMENTE SUS PRODUCTOS INTEGRALES) DEBERÍAN FORMAR PARTE DE LA ALIMENTACIÓN DIARIA\nEl consumo diario de pasta, arroz y cereales es indispensable por su composición rica en carbohidratos. Nos aportan una parte importante de energía necesaria para nuestras actividades diarias.\n\n4. LOS ALIMENTOS POCO PROCESADOS, FRESCOS Y DE TEMPORADA SON LOS MÁS ADECUADOS\nLos alimentos poco procesados, frescos y de temporada son los más adecuados\nEs importante aprovechar los productos de temporada ya que, sobre todo en el caso de las frutas y verduras, nos permite consumirlas en su mejor momento, tanto a nivel de aportación de nutrientes como por su aroma y sabor.\n\n5. CONSUMIR DIARIAMENTE PRODUCTOS LÁCTEOS, PRINCIPALMENTE YOGURT Y QUESOS\nNutricionalmente se debe que destacar que los productos lácteos como excelentes fuentes de proteínas de alto valor biológico, minerales (calcio, fósforo, etc) y vitaminas. El consumo de leches fermentadas (yogur, etc.) se asocia a una serie de beneficios para la salud porque estos productos contienen microorganismos vivos capaces de mejorar el equilibrio de la microflora intestinal.\n\n6. LA CARNE ROJA SE TENDRÍA QUE CONSUMIR CON MODERACIÓN Y SI PUEDE SER COMO PARTE DE GUISOS Y OTRAS RECETAS. Y LAS CARNES PROCESADAS EN CANTIDADES PEQUEÑAS Y COMO INGREDIENTES DE BOCADILLOS Y PLATOS.\nEl consumo excesivo de grasas animales no es bueno para la salud. Por lo tanto, se recomienda el consumo en cantidades pequeñas, preferentemente carnes magras, y formando parte de platos a base de verduras y cereales.\n\n7. CONSUMIR PESCADO EN ABUNDANCIA Y HUEVOS CON MODERACIÓN.\nSe recomienda el consumo de pescado azul como mínimo una o dos veces a la semana ya que sus grasas – aunque de origen animal- tienen propiedades muy parecidas a las grasas de origen vegetal a las que se les atribuyen propiedades protectoras frente enfermedades cardiovasculares.\nLos huevos contienen proteínas de muy buena calidad, grasas y muchas vitaminas y minerales que los convierten en un alimento muy rico. El consumo de tres o cuatro huevos a la semana es una buena alternativa a la carne y el pescado.\n\n8. LA FRUTA FRESCA TENDRÍA QUE SER EL POSTRE HABITUAL. LOS DULCES Y PASTELES DEBERÍAN CONSUMIRSE OCASIONALMENTE.\nLas frutas son alimentos muy nutritivos que aportan color y sabor a nuestra alimentación diaria y son también una buena alternativa a media mañana y como merienda.\n\n9. EL AGUA ES LA BEBIDA POR EXCELENCIA EN EL MEDITERRÁNEO. EL VINO DEBE TOMARSE CON MODERACIÓN Y DURANTE LAS COMIDAS.\nEl agua es fundamental en nuestra dieta. El vino es un alimento tradicional en la dieta mediterránea que puede tener efectos beneficiosos para la salud consumiéndolo con moderación y en el contexto de una dieta equilibrada.\n\n10. REALIZAR ACTIVIDAD FÍSICA TODOS LOS DÍAS, YA QUE ES TAN IMPORTANTE COMO COMER ADECUADAMENTE\nMantenerse físicamente activo y realizar cada día un ejercicio físico adaptado a nuestras capacidades es muy importante para conservar una buena salud.', '1505775123515'),
(2, 'Estrés y Diabetes', 'Todos tenemos un nivel de presión que es conveniente y útil para funcionar en la vida, pero cuando éste aumenta por sobre el nivel óptimo, hablamos de estrés, que es la respuesta a un nivel de presión inapropiado. ', 'El estrés está relacionado con el aumento de la glicemia, por lo tanto es importante que las personas que tienen diabetes puedan aprender a controlarlo, para evitar descompensaciones peligrosas para su enfermedad.\n\nHay algunas personas que cuando están angustiadas, se alimentan en forma descontrolada y en ciertos momentos pueden llegar a consumir golosinas y otros alimentos azucarados para saciar su ansiedad, con lo que podrían llegar a aumentar su glicemia en forma peligrosa. Otras personas pierden el apetito con el estrés y dejan de comer, lo que puede producir una descompensación en su enfermedad, aumentando el riesgo de tener una hipoglicemia. A causa del estrés, otras personas podrían también aumentar la ingesta alcohólica, el tabaquismo y el uso indiscriminado de tranquilizantes, perjudiciales para la salud de todas las personas, y especialmente peligrosos en la diabetes.\n\nPara algunas personas, el solo hecho de saber que tienen diabetes representa un\nimportante factor de estrés, por el temor a la enfermedad y su inseguridad de tener la capacidad para manejarla en forma adecuada.\n\nEl manejo del estrés no solamente depende de la persona que lo padece, sino también de la interacción y de la buena comunicación que exista entre ella y los miembros de su grupo familiar, amigos, compañeros de trabajo, el equipo de salud que lo atiende y los integrantes de las asociaciones de diabéticos con los que comparte. Por esta razón, es fundamental que la persona con diabetes cuente con el apoyo y comprensión de todos ellos, especialmente de su grupo familiar.\n\nPermitir a la persona que exprese sus sentimientos, y el refuerzo constante de las personas que lo aprecian, ayudará a la persona con diabetes a mejorar su autoestima y la confianza en su capacidad para manejar su enfermedad.\nAlgunos familiares asumen una actitud de sobreprotección con la persona que tiene\ndiabetes. Esto puede producirle una sensación de incapacidad y de dependencia\nque puede aumentar el estrés y llegar a producir depresión. La familia debe apoyar y estimular a las personas que tienen diabetes para que lleven una vida normal de trabajo y/o estudio y disfruten de la recreación con sus seres queridos.\n\nLa persona con diabetes debe tomar conciencia de la importancia de lograr un equilibrio entre trabajo, reposo y recreación, con el fin de evitar el agotamiento producido por el estrés, caracterizado por la ansiedad, el insomnio y la irritabilidad, que podrían conducirlo al agotamiento o a la depresión.\n\nEL ESTRÉS PROVOCA\n1. Angustia\n2. Disminución de la concentración\n3. Tensión muscular\n4. Irritabilidad\n5. Cansancio constante\n6. Apatía\n7. Olvido\n8. Sentimientos de frustración\n9. Mareos\n10. Pérdida o aumento del apetito\n11. Llantos incontrolables', '1506117828303'),
(3, 'Trotar o caminar al menos 30 minutos', 'La actividad fisica, si bien sirve para todas las personas, es indispensable para pacientes con DM2', 'Empieza caminando por al menos 15 minutos y luego mejor trota los mismos 15 minutos. 15 minutos en la noche y 15 minutos en la mañana.', 'null');

-- --------------------------------------------------------

--
-- Table structure for table `hba1c`
--

CREATE TABLE `hba1c` (
  `paciente` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `hba1c` float(2,1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `hba1c`
--

INSERT INTO `hba1c` (`paciente`, `fecha`, `hba1c`) VALUES
(1001, '2017-04-15', 6.7),
(1001, '2017-07-17', 7.5),
(1001, '2017-09-18', 6.0),
(52362520, '2017-09-23', 5.9),
(222222, '2017-10-01', 5.7),
(2002, '2017-10-05', 7.0),
(19403577, '2017-10-06', 5.6),
(41414866, '2017-10-10', 5.8),
(99083065, '2017-10-12', 5.7),
(1030548087, '2017-10-13', 5.4),
(2006, '2017-10-14', 6.5),
(88222234, '2017-10-17', 7.5),
(40367108, '2017-10-19', 7.5);

-- --------------------------------------------------------

--
-- Table structure for table `informacion`
--

CREATE TABLE `informacion` (
  `idfila` int(11) NOT NULL,
  `titulo` text NOT NULL,
  `descripcion` text NOT NULL,
  `contenido` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `informacion`
--

INSERT INTO `informacion` (`idfila`, `titulo`, `descripcion`, `contenido`) VALUES
(1, '¿Qué es la diabetes tipo 2?', 'Es una enfermedad crónica, caracterizada  por una concentración anormalmente alta de glucosa o azúcar en la sangre. ', 'En la diabetes tipo 2, el organismo puede producir insulina, pero esta es insuficiente o no puede ser utilizada, porque hay problemas en las entradas especiales que la célula tiene para permitir el ingreso de la insulina.\r\nComo el azúcar no puede entrar a la célula, a pesar de la presencia de insulina, aumenta el nivel del azúcar en la sangre y se produce hiperglicemia. En general, las personas con diabetes tipo 2 no necesitan inyectarse insulina para vivir.\r\n\r\n¿A quiénes afecta con mayor frecuencia?\r\nGeneralmente a adultos mayores de 40 años con sobrepeso.\r\n\r\n¿Cuáles son sus causas?\r\nPredisposición hereditaria más obesidad, la que provoca resistencia de las células a la acción de la insulina.\r\n\r\n¿Cuáles son sus síntomas?\r\nMucha sed, apetito excesivo y gran cantidad de orina. En muchas ocasiones, estos síntomas pueden pasar desapercibidos para la persona.\r\n\r\n¿Cuál es el tratamiento de este tipo de diabetes?\r\nEn la mayoría de los casos se trata con una alimentación ordenada, ejercicios y tabletas hipoglicemiantes (que bajan la glicemia). En una proporción menor es necesario utilizar insulina.\r\n\r\nFuente: Manual para educadores en diabetes mellitus - Organización Panamericana de la Salud - Organizacion Mundial de la Salud - Gobierno de Chile.'),
(2, 'Hemoglobina Glicosilada', 'Se considera que el mejor indicador para evaluar el control metabólico de la diabetes es la hemoglobina glicosilada, ya que informa sobre el grado de control en un período aproximado de 3 meses previos al examen.', 'Es un examen complementario a la glicemia que permite diferenciar entre una elevación transitoria de la glicemia (por estrés por ejemplo) o la que corresponde a una diabetes descompensada por una elevción persistente de la glicemia.\r\n\r\nLa hemoglobina glicosilada es un examen independiente de la alimentación y el ejercicio del día, por lo que se puede realizar a cualquier hora sin necesidad de estar en ayunas. Este examen no forma parte del autocontrol, por lo que el paciente debería solicitar al médico que se lo haga cada tres o seis meses, dependiendo de las condiciones del paciente.\r\n\r\nFuente: Manual para educadores en diabetes mellitus - Organización Panamericana de la Salud - Organizacion Mundial de la Salud - Gobierno de Chile.'),
(3, 'La alimentación en la diabetes', 'Cuando una persona tiene diabetes, sus necesidades nutricionales continúan siendo las mismas que antes de tener la enfermedad. Estas necesidades dependen de su edad, sexo, estado fisiológico (embarazo y lactancia), actividad física y, en forma muy importante, de su estado nutricional.', 'El sobrepeso y la obesidad aumentan la resistencia a la insulina y, por lo tanto, el riesgo de hiperglicemia en las personas con diabetes. Por esto es particularmente importante para ellas mantener el peso corporal dentro del rango adecuado para la estatura. Esto implica mantener una ingesta energé tica que guarde relación con el gasto energético, el que a su vez depende en gran medida de la actividad física que realiza la persona.\r\n\r\nUna alimentación variada proporciona todos los nutrientes que el organismo necesita:\r\nproteínas para el crecimiento, mantenimiento y reparación de los órganos y tejidos del cuerpo; hidratos de carbono (H de C) como fuente de energía; grasas o lípidos como reserva energética y para ciertas funciones esenciales del organismo; minerales y vitaminas, en especial los con funciones antioxidantes, que aseguran el perfecto funcionamiento del organismo y la defensa frente a las enfermedades. Los alimentos también aportan sustancias denominadas no nutrientes, como la fibra dietética, los flavonoides, fitoestrógenos y otros, que contribuyen a prevenir las enfermedades crónicas no transmisibles relacionadas con la alimentación, como las cardiovasculares y el cáncer. \r\n\r\nPara que los nutrientes puedan ser utilizados por el organismo, los alimentos deben ser transformados en pequeñas partículas, que liberan la glucosa de los hidratos de carbono, los aminoácidos de las proteínas y los ácidos grasos de las grasas y aceites.\r\nLa glucosa es la fuente de energía más rápidamente disponible para la actividad cerebral y muscular. La insulina permite que la glucosa entre a la célula y se transforme en la energía que se necesita para realizar actividades de trabajo, deporte, recreativas y otras.\r\n\r\nFuente: Manual para educadores en diabetes mellitus - Organización Panamericana de la Salud - Organizacion Mundial de la Salud - Gobierno de Chile.\r\n\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `medicamento`
--

CREATE TABLE `medicamento` (
  `idfila` int(11) NOT NULL,
  `paciente` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `nombre` text NOT NULL,
  `tipo` text NOT NULL,
  `dosificacion` int(11) NOT NULL,
  `posologia` int(11) NOT NULL,
  `recordar` tinyint(4) NOT NULL,
  `observaciones` mediumtext NOT NULL,
  `ultimatoma` time NOT NULL,
  `estado` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `medicamento`
--

INSERT INTO `medicamento` (`idfila`, `paciente`, `fecha`, `nombre`, `tipo`, `dosificacion`, `posologia`, `recordar`, `observaciones`, `ultimatoma`, `estado`) VALUES
(1, 1001, '2017-09-19', 'Metformina', 'Pastilla', 2, 12, 1, 'de 500mg', '08:00:00', 1),
(2, 1001, '2017-09-19', 'Acarbosa', 'Pastilla', 3, 8, 0, 'de 7mg', '09:20:00', 1),
(8, 1030548087, '2017-10-13', 'Linagliptina', 'Pastilla', 1, 24, 1, 'en ayunas', '06:00:00', 1),
(9, 2006, '2017-10-14', 'aspirina ', 'Pastilla', 25, 10, 0, 'es una', '11:01:00', 1),
(10, 88222234, '2017-10-17', 'Glargina', 'Inyección', 38, 24, 0, 'Antes de dormir', '17:05:00', 1),
(11, 88222234, '2017-10-19', 'Glulisina', 'Inyección', 32, 8, 0, 'Aplicar antes de cada comida 10-12-10', '15:39:00', 1);

-- --------------------------------------------------------

--
-- Table structure for table `mensajes`
--

CREATE TABLE `mensajes` (
  `id` bigint(11) NOT NULL,
  `destinatario` bigint(11) NOT NULL,
  `mensaje` text NOT NULL,
  `fecha` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `mensajes`
--

INSERT INTO `mensajes` (`id`, `destinatario`, `mensaje`, `fecha`) VALUES
(1000, 1001, 'Buenos dias sr. Gonzales', '2017-09-19'),
(1000, 1000, 'Buenos dias sr. Gonzales', '2017-09-19'),
(1001, 1000, 'Hola doctor', '2017-09-19'),
(1001, 1001, 'Hola doctor', '2017-09-19'),
(3103196004, 52362520, 'Hola Marina,  bienvenida!  estaré observando tu evolución para identificar si vas acercándote a alcanzar diariamente tus metas', '2017-09-23'),
(3103196004, 3103196004, 'Hola Marina,  bienvenida!  estaré observando tu evolución para identificar si vas acercándote a alcanzar diariamente tus metas', '2017-09-23'),
(52362520, 3103196004, 'Hola doc,  estoy lista\"', '2017-09-29'),
(52362520, 52362520, 'Hola doc,  estoy lista\"', '2017-09-29'),
(3014696578, 3014696578, 'Hola Diana, bienvenida, esperamos apoyar tu tratamiento a traves de la app', '2017-09-29'),
(3006491142, 1030548087, 'Hola, estamos acá para apoyar su tratamiento ', '2017-10-13'),
(3006491142, 1030548087, 'hola Diego. bienvenido, estamos acá para apoyar su tratamiento', '2017-10-13'),
(3006491142, 3006491142, 'hola Diego. bienvenido, estamos acá para apoyar su tratamiento', '2017-10-13'),
(1030548087, 3006491142, 'Gracias. Espero que así sea', '2017-10-13'),
(1030548087, 1030548087, 'Gracias. Espero que así sea', '2017-10-13'),
(1006, 2006, 'hola paciente ', '2017-10-14'),
(1006, 1006, 'hola paciente ', '2017-10-14'),
(2006, 1006, 'hola doctor ', '2017-10-14'),
(2006, 2006, 'hola doctor ', '2017-10-14'),
(3014696578, 88222234, 'Hola Mauricio. Como te ha parecido la app?', '2017-10-17'),
(3014696578, 3014696578, 'Hola Mauricio. Como te ha parecido la app?', '2017-10-17'),
(88222234, 3014696578, 'Hasta ahora muy bien con ella', '2017-10-19'),
(88222234, 88222234, 'Hasta ahora muy bien con ella', '2017-10-19'),
(3014696578, 40367108, 'Señora Judith cómo le ha parecido la aplicación?', '2017-10-19'),
(3014696578, 3014696578, 'Señora Judith cómo le ha parecido la aplicación?', '2017-10-19'),
(40367108, 3014696578, 'digamos q si\n', '2017-10-20'),
(40367108, 40367108, 'digamos q si\n', '2017-10-20');

-- --------------------------------------------------------

--
-- Table structure for table `metas`
--

CREATE TABLE `metas` (
  `id` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `meta1` tinyint(4) NOT NULL,
  `meta2` tinyint(4) NOT NULL,
  `meta3` tinyint(4) NOT NULL,
  `meta4` tinyint(4) NOT NULL,
  `meta5` tinyint(4) NOT NULL,
  `meta6` tinyint(4) NOT NULL,
  `meta7` tinyint(4) NOT NULL,
  `meta8` tinyint(4) NOT NULL,
  `meta9` tinyint(4) NOT NULL,
  `meta10` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `metas`
--

INSERT INTO `metas` (`id`, `fecha`, `meta1`, `meta2`, `meta3`, `meta4`, `meta5`, `meta6`, `meta7`, `meta8`, `meta9`, `meta10`) VALUES
(1001, '2017-09-19', 0, 1, 0, 1, 1, 0, 0, 0, 0, 0),
(52362520, '2017-09-30', 0, 0, 1, 1, 0, 1, 0, 0, 0, 0),
(123, '2017-09-29', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(222222, '2017-10-01', 1, 1, 1, 1, 0, 1, 0, 0, 0, 0),
(2002, '2017-10-05', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(19403577, '2017-10-06', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(41414866, '2017-10-19', 0, 1, 0, 0, 0, 1, 0, 0, 0, 0),
(99083065, '2017-10-12', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(1030548087, '2017-10-13', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0),
(2006, '2017-10-14', 1, 1, 0, 0, 1, 0, 0, 0, 0, 0),
(88222234, '2017-10-19', 1, 1, 0, 1, 1, 0, 0, 0, 0, 0),
(40367108, '2017-10-20', 0, 1, 0, 0, 0, 0, 0, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `notificaciones`
--

CREATE TABLE `notificaciones` (
  `id` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `mensaje` tinyint(11) NOT NULL,
  `recurso` int(11) NOT NULL,
  `recordatorio` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `notificaciones`
--

INSERT INTO `notificaciones` (`id`, `fecha`, `mensaje`, `recurso`, `recordatorio`) VALUES
(2000, '2017-09-18', 0, 2, 1),
(3000, '2017-09-18', 0, 2, 1),
(1001, '2017-09-18', 0, 2, 0),
(300207802, '2017-09-22', 0, 2, 1),
(3005733045, '2017-09-22', 0, 2, 0),
(3006491142, '2017-09-22', 0, 2, 0),
(3014696578, '2017-09-22', 0, 0, 0),
(3103196004, '2017-09-22', 0, 2, 0),
(3105644680, '2017-09-22', 0, 2, 0),
(3115619771, '2017-09-22', 0, 2, 1),
(3125762992, '2017-09-22', 0, 2, 1),
(3153254672, '2017-09-22', 0, 2, 0),
(3164293458, '2017-09-22', 0, 2, 1),
(3197047211, '2017-09-22', 0, 2, 1),
(3197668358, '2017-09-22', 0, 2, 0),
(3214680957, '2017-09-22', 0, 2, 1),
(52362520, '2017-09-23', 0, 2, 0),
(123, '2017-09-29', 0, 2, 1),
(52635214, '2017-09-29', 0, 2, 0),
(222222, '2017-10-01', 0, 2, 0),
(2002, '2017-10-05', 0, 2, 0),
(19403577, '2017-10-06', 0, 0, 0),
(41414866, '2017-10-10', 0, 0, 0),
(99083065, '2017-10-12', 0, 0, 0),
(93986123, '2017-10-13', 0, 2, 0),
(1030548087, '2017-10-13', 0, 2, 0),
(1006, '2017-10-14', 1, 0, 0),
(2006, '2017-10-14', 0, 0, 0),
(39710568, '2017-10-14', 0, 0, 0),
(88222234, '2017-10-17', 0, 0, 0),
(40367108, '2017-10-19', 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `observaciones`
--

CREATE TABLE `observaciones` (
  `id` bigint(20) NOT NULL,
  `paciente` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `observacion` longtext NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `observaciones`
--

INSERT INTO `observaciones` (`id`, `paciente`, `fecha`, `observacion`) VALUES
(1006, 2006, '2017-10-14', 'este paciente edta empezando ');

-- --------------------------------------------------------

--
-- Table structure for table `paciente`
--

CREATE TABLE `paciente` (
  `id` bigint(11) NOT NULL,
  `fechanacimiento` date NOT NULL,
  `ocupacion` text NOT NULL,
  `eps` text NOT NULL,
  `estrato` tinyint(11) NOT NULL,
  `estadocivil` text NOT NULL,
  `hijos` tinyint(11) NOT NULL,
  `estatura` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `paciente`
--

INSERT INTO `paciente` (`id`, `fechanacimiento`, `ocupacion`, `eps`, `estrato`, `estadocivil`, `hijos`, `estatura`) VALUES
(123, '2017-09-29', '', '', 0, '', 0, 0),
(1001, '1974-07-17', '', '', 0, '', 0, 0),
(2002, '1986-09-05', '', '', 0, '', 0, 0),
(2006, '1986-05-14', '', '', 0, '', 0, 0),
(222222, '1993-09-02', '', '', 0, '', 0, 0),
(19403577, '1957-03-20', '', '', 0, '', 0, 0),
(40367108, '1953-04-27', '', '', 0, '', 0, 0),
(41414866, '1924-06-08', '', '', 0, '', 0, 0),
(52362520, '1976-00-26', '', '', 0, '', 0, 0),
(88222234, '1976-10-11', '', '', 0, '', 0, 0),
(99083065, '1977-09-12', '', '', 0, '', 0, 0),
(1030548087, '1981-09-13', '', '', 0, '', 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `pais`
--

CREATE TABLE `pais` (
  `id` int(11) NOT NULL,
  `nombre` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pais`
--

INSERT INTO `pais` (`id`, `nombre`) VALUES
(1, 'Colombia'),
(2, 'Venezuela'),
(3, 'Ecuador'),
(4, 'Panamá');

-- --------------------------------------------------------

--
-- Table structure for table `percepcionriesgo`
--

CREATE TABLE `percepcionriesgo` (
  `paciente` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `infarto` int(11) NOT NULL,
  `presionalta` int(11) NOT NULL,
  `caida` int(11) NOT NULL,
  `enfermedadcorazon` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `percepcionriesgo`
--

INSERT INTO `percepcionriesgo` (`paciente`, `fecha`, `infarto`, `presionalta`, `caida`, `enfermedadcorazon`) VALUES
(1001, '2017-09-19', 1, 1, 1, 1),
(52362520, '2017-09-30', 0, 1, 1, 1),
(2002, '2017-10-06', 1, 0, 0, 1),
(41414866, '2017-10-10', 1, 1, 1, 1),
(99083065, '2017-10-13', 0, 1, 1, 0),
(41414866, '2017-10-13', 1, 1, 0, 0),
(1030548087, '2017-10-15', 1, 1, 1, 1),
(2006, '2017-10-16', 0, 0, 1, 1),
(19403577, '2017-10-17', 0, 0, 0, 0),
(88222234, '2017-10-19', 0, 0, 1, 0),
(40367108, '2017-10-20', 1, 0, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `pesoimc`
--

CREATE TABLE `pesoimc` (
  `paciente` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `peso` int(11) NOT NULL,
  `imc` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pesoimc`
--

INSERT INTO `pesoimc` (`paciente`, `fecha`, `peso`, `imc`) VALUES
(1001, '2017-06-01', 90, 29),
(1001, '2017-06-18', 102, 31),
(1001, '2017-07-05', 85, 25),
(52362520, '2017-09-23', 60, 22),
(222222, '2017-10-01', 80, 20),
(2002, '2017-10-05', 99, 35),
(19403577, '2017-10-06', 80, 125),
(41414866, '2017-10-10', 56, 24),
(99083065, '2017-10-12', 65, 22),
(1030548087, '2017-10-13', 65, 23),
(2006, '2017-10-14', 98, 34),
(88222234, '2017-10-17', 61, 22),
(40367108, '2017-10-19', 60, 24);

-- --------------------------------------------------------

--
-- Table structure for table `recursos`
--

CREATE TABLE `recursos` (
  `fecha` date NOT NULL,
  `titulo` text NOT NULL,
  `mensaje` text NOT NULL,
  `recurso` text NOT NULL,
  `video` text NOT NULL,
  `responsable` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `recursos`
--

INSERT INTO `recursos` (`fecha`, `titulo`, `mensaje`, `recurso`, `video`, `responsable`) VALUES
('2017-09-19', 'Horario de las comidas', 'El plan de alimentación de las personas con diabetes consulta en general cuatro comidas, entre las que los hidratos de carbono se distribuyen en cantidades semejantes.\nCuando la persona usa insulina, se recomienda aumentar el número de comidas a 6, intercalando dos colaciones, una a media mañana y otra antes de dormir.', '1505830093075', 'https://youtu.be/RTNBm8m8qvo', 1000),
('2017-10-14', 'Los ejercios en la diabetes tipo 2 - 1 de 15', 'Caminata con una buena intensidad\n\nLo ideal es hacerlo al aire libre, pero no a la hora de más calor y al sol. Hay que tener criterio: si no es posible hacerlo a la intemperie una buena caminadora los sustituye igualmente. Este es un ejercicio aeróbico.\n\n', '1508009777761', 'https://youtu.be/G0WGr1XUhbA', 1006);

-- --------------------------------------------------------

--
-- Table structure for table `tension`
--

CREATE TABLE `tension` (
  `paciente` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `tension1` int(11) NOT NULL,
  `tension2` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tension`
--

INSERT INTO `tension` (`paciente`, `fecha`, `tension1`, `tension2`) VALUES
(1001, '2017-09-18', 180, 80),
(52362520, '2017-09-23', 140, 90),
(222222, '2017-10-01', 17, 5),
(2002, '2017-10-05', 180, 90),
(19403577, '2017-10-06', 160, 20),
(41414866, '2017-10-10', 150, 45),
(99083065, '2017-10-12', 34, 6),
(1030548087, '2017-10-13', 150, 80),
(2006, '2017-10-14', 173, 87),
(88222234, '2017-10-17', 110, 70),
(40367108, '2017-10-19', 125, 62);

-- --------------------------------------------------------

--
-- Table structure for table `tiemposdeuso`
--

CREATE TABLE `tiemposdeuso` (
  `usuario` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `registropaciente` bigint(20) NOT NULL,
  `ingresopaciente1` bigint(20) NOT NULL,
  `ingresopaciente2` bigint(20) NOT NULL,
  `creacionrecurso` bigint(20) NOT NULL,
  `uso` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tiemposdeuso`
--

INSERT INTO `tiemposdeuso` (`usuario`, `fecha`, `registropaciente`, `ingresopaciente1`, `ingresopaciente2`, `creacionrecurso`, `uso`) VALUES
(1001, '2017-09-21', 0, 0, 17, 0, 0),
(3197668358, '2017-09-22', 230, 0, 0, 0, 0),
(3002078021, '2017-09-22', 0, 0, 0, 0, 0),
(3005733045, '2017-09-22', 0, 0, 0, 0, 0),
(3006491142, '2017-09-22', 0, 0, 0, 0, 0),
(3014696578, '2017-09-22', 0, 0, 0, 0, 0),
(3103196004, '2017-09-22', 0, 0, 0, 0, 0),
(3105644680, '2017-09-22', 0, 0, 0, 0, 0),
(3115619771, '2017-09-22', 0, 0, 0, 0, 0),
(3153254672, '2017-09-22', 0, 0, 0, 0, 0),
(3197047211, '2017-09-22', 0, 0, 0, 0, 0),
(3197668358, '2017-09-22', 0, 0, 0, 0, 0),
(3214680957, '2017-09-22', 0, 0, 0, 0, 0),
(3002078021, '2017-09-22', 0, 0, 0, 0, 0),
(3005733045, '2017-09-22', 0, 0, 0, 0, 0),
(3006491142, '2017-09-22', 0, 0, 0, 0, 0),
(3014696578, '2017-09-22', 0, 0, 0, 0, 0),
(3103196004, '2017-09-22', 0, 0, 0, 0, 0),
(3105644680, '2017-09-22', 0, 0, 0, 0, 0),
(3115619771, '2017-09-22', 0, 0, 0, 0, 0),
(3153254672, '2017-09-22', 0, 0, 0, 0, 0),
(3197047211, '2017-09-22', 0, 0, 0, 0, 0),
(3197668358, '2017-09-22', 0, 0, 0, 0, 0),
(3214680957, '2017-09-22', 0, 0, 0, 0, 0),
(1000, '2017-09-22', 0, 0, 0, 0, 0),
(3103196004, '2017-09-23', 151, 0, 0, 0, 0),
(52362520, '2017-09-23', 0, 305, 0, 0, 0),
(3214680957, '2017-09-28', 122, 0, 0, 0, 0),
(3014696578, '2017-09-29', 168, 0, 0, 0, 0),
(3197668358, '2017-09-29', 73, 0, 0, 0, 0),
(3014696578, '2017-09-29', 60, 0, 0, 0, 0),
(3014696578, '2017-09-29', 80, 0, 0, 0, 0),
(52362520, '2017-09-29', 0, 544069, 0, 0, 0),
(3014696578, '2017-09-29', 334, 0, 0, 0, 0),
(3014696578, '2017-09-29', 81, 0, 0, 0, 0),
(3014696578, '2017-09-29', 0, 0, 0, 99, 0),
(52362520, '2017-09-30', 0, 0, 99, 0, 0),
(3197668358, '2017-10-01', 89, 0, 0, 0, 0),
(222222, '2017-10-01', 0, 185, 0, 0, 0),
(3006491142, '2017-10-03', 83, 0, 0, 0, 0),
(3006491142, '2017-10-03', 355, 0, 0, 0, 0),
(3153254672, '2017-10-05', 129, 0, 0, 0, 0),
(3153254672, '2017-10-05', 412, 0, 0, 0, 0),
(3153254672, '2017-10-05', 101, 0, 0, 0, 0),
(3014696578, '2017-10-05', 82, 0, 0, 0, 0),
(1000, '2017-10-05', 76, 0, 0, 0, 0),
(2002, '2017-10-05', 0, 96, 0, 0, 0),
(3014696578, '2017-10-05', 0, 0, 0, 84, 0),
(3014696578, '2017-10-06', 54, 0, 0, 0, 0),
(3014696578, '2017-10-06', 0, 0, 0, 63, 0),
(3014696578, '2017-10-06', 54, 0, 0, 0, 0),
(3014696578, '2017-10-06', 68, 0, 0, 0, 0),
(2002, '2017-10-06', 0, 0, 18, 0, 0),
(3014696578, '2017-10-06', 90, 0, 0, 0, 0),
(3005733045, '2017-10-06', 145, 0, 0, 0, 0),
(19403577, '2017-10-06', 0, 474, 0, 0, 0),
(3014696578, '2017-10-10', 63, 0, 0, 0, 0),
(3014696578, '2017-10-10', 61, 0, 0, 0, 0),
(41414866, '2017-10-10', 0, 126, 0, 0, 0),
(3197668358, '2017-10-12', 71, 0, 0, 0, 0),
(99083065, '2017-10-12', 0, 228, 0, 0, 0),
(99083065, '2017-10-13', 0, 0, 324, 0, 0),
(41414866, '2017-10-13', 0, 0, 16, 0, 0),
(3006491142, '2017-10-13', 61, 0, 0, 0, 0),
(1030548087, '2017-10-13', 0, 90, 0, 0, 0),
(1030548087, '2017-10-15', 0, 0, 55, 0, 0),
(1006, '2017-10-14', 224, 0, 0, 0, 0),
(1006, '2017-10-14', 0, 0, 0, 587, 0),
(2006, '2017-10-14', 0, 382, 0, 0, 0),
(2006, '2017-10-16', 0, 0, 22, 0, 0),
(3014696578, '2017-10-17', 638, 0, 0, 0, 0),
(88222234, '2017-10-17', 0, 322, 0, 0, 0),
(19403577, '2017-10-17', 0, 0, 11, 0, 0),
(3014696578, '2017-10-19', 286, 0, 0, 0, 0),
(40367108, '2017-10-19', 0, 735, 0, 0, 0),
(88222234, '2017-10-19', 0, 0, 98, 0, 0),
(40367108, '2017-10-20', 0, 0, 60, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tratantes`
--

CREATE TABLE `tratantes` (
  `paciente` bigint(11) NOT NULL,
  `idprofesional` bigint(11) NOT NULL,
  `fechainicio` date NOT NULL,
  `idfamiliar1` bigint(11) NOT NULL,
  `parentesco1` text NOT NULL,
  `idfamiliar2` bigint(11) NOT NULL,
  `parentesco2` text NOT NULL,
  `estado` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `tratantes`
--

INSERT INTO `tratantes` (`paciente`, `idprofesional`, `fechainicio`, `idfamiliar1`, `parentesco1`, `idfamiliar2`, `parentesco2`, `estado`) VALUES
(1001, 1000, '2017-09-18', 0, '', 0, '', 1),
(52362520, 3103196004, '2017-09-23', 0, '', 0, '', 1),
(123, 3197668358, '2017-09-29', 0, '', 0, '', 1),
(222222, 3197668358, '2017-10-01', 0, '', 0, '', 1),
(2002, 1000, '2017-10-05', 0, '', 0, '', 1),
(19403577, 3005733045, '2017-10-06', 0, '', 0, '', 1),
(41414866, 3014696578, '2017-10-10', 0, '', 0, '', 1),
(99083065, 3197668358, '2017-10-12', 0, '', 0, '', 1),
(99083065, 0, '2017-10-13', 93986123, '', 0, '', 1),
(1030548087, 3006491142, '2017-10-13', 0, '', 0, '', 1),
(2006, 1006, '2017-10-14', 0, '', 0, '', 1),
(41414866, 0, '2017-10-14', 39710568, '', 0, '', 1),
(88222234, 3014696578, '2017-10-17', 0, '', 0, '', 1),
(40367108, 3014696578, '2017-10-19', 0, '', 0, '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `trigliceridos`
--

CREATE TABLE `trigliceridos` (
  `id` bigint(20) NOT NULL,
  `fecha` date NOT NULL,
  `trigliceridos` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `trigliceridos`
--

INSERT INTO `trigliceridos` (`id`, `fecha`, `trigliceridos`) VALUES
(1001, '2017-09-18', 210),
(52362520, '2017-09-23', 160),
(222222, '2017-10-01', 25),
(2002, '2017-10-05', 174),
(19403577, '2017-10-06', 111),
(41414866, '2017-10-10', 153),
(99083065, '2017-10-12', 23),
(1030548087, '2017-10-13', 147),
(2006, '2017-10-14', 190),
(88222234, '2017-10-17', 140),
(40367108, '2017-10-19', 148);

-- --------------------------------------------------------

--
-- Table structure for table `usuario`
--

CREATE TABLE `usuario` (
  `correo` text NOT NULL,
  `clave` text NOT NULL,
  `identificacion` bigint(15) NOT NULL,
  `rol` text NOT NULL,
  `nombres` text NOT NULL,
  `apellidos` text NOT NULL,
  `estado` tinyint(1) NOT NULL,
  `sexo` text NOT NULL,
  `telefono` text NOT NULL,
  `direccion` text NOT NULL,
  `fecharegistro` date NOT NULL,
  `fechaingreso` date NOT NULL,
  `ciudad` int(11) NOT NULL,
  `aceptaterminos` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `usuario`
--

INSERT INTO `usuario` (`correo`, `clave`, `identificacion`, `rol`, `nombres`, `apellidos`, `estado`, `sexo`, `telefono`, `direccion`, `fecharegistro`, `fechaingreso`, `ciudad`, `aceptaterminos`) VALUES
('paciente', 'bosque', 123, 'paciente', 'Paciente nuevo', '', 0, '', '0', '', '2017-09-29', '2017-09-29', 1, 0),
('p', '123', 1000, 'profesional', 'Juan David', 'Velásquez Bedoya', 1, 'masculino', '3007863567', 'Cra 17 No.14-87', '2017-09-18', '2017-09-18', 1, 1),
('o', '123', 1001, 'paciente', 'Sebastian', 'Gonzales', 1, 'Masculino', '315465789', 'Cll. 126 No. 9-12', '2017-09-18', '2017-09-19', 1, 1),
('j@g.com', '123', 1006, 'profesional', 'Jairo', 'González ', 1, 'N/A', '1236497', 'cra 3', '2017-10-14', '2017-10-14', 1, 1),
('carlosregifos@gmail.com', '123', 2002, 'paciente', 'Carlos ', 'Rengifo', 1, 'Masculino', '36985274', 'calle 8 no 24-34', '2017-10-05', '2017-10-05', 1, 1),
('luisa@gmail.com', '123', 2006, 'paciente', 'luisa marcela', 'lozano torres', 1, 'Masculino', '896464', 'calle 4', '2017-10-14', '2017-10-14', 1, 1),
('paciente0@gmail.com', '123456', 222222, 'paciente', 'juan c', 'diaz', 1, 'Masculino', '222555333', 'cra123 n 76', '2017-10-01', '2017-10-01', 1, 1),
('widabaes@yahoo.com', 'bosque', 19403577, 'paciente', 'william', 'barbosa', 1, 'Masculino', '7556814', 'Cll 22', '2017-10-06', '2017-10-06', 1, 1),
('familiar-hotmail.com', '123', 39710568, 'familiar', 'Familiar nuevo', '', 0, '', '0', '', '2017-10-14', '2017-10-14', 1, 0),
('rosajudithbeltran@gmail.com', 'laila', 40367108, 'paciente', 'Rosa Judith', 'Beltrán Aguilar', 1, 'Masculino', '3208256944', 'Tranv 68c 30-11 sur', '2017-10-19', '2017-10-19', 1, 1),
('isabel-hotmail.com', 'kogan', 41414866, 'paciente', 'Isabel', 'Álvarez', 1, 'Femenino', '8027369', 'CL 6C 71B 28', '2017-10-10', '2017-10-10', 1, 1),
('marard.6@hotmail.com', 'Sabogal35', 52362520, 'paciente', 'Marina', 'Ardila', 1, 'Femenino', '3124074058', 'calle 153 #9-08', '2017-09-23', '2017-09-23', 1, 1),
('maualejoalvarez76@gmail.com', 'Diabete99', 88222234, 'paciente', 'Mauricio ', 'Alvarez', 1, 'Masculino', '3177713640', 'Cra 12H 31C 17Sur', '2017-10-17', '2017-10-17', 1, 1),
('familiar2@gmail.com', '123', 93986123, 'familiar', 'Familiar nuevo', '', 0, '', '0', '', '2017-10-13', '2017-10-13', 1, 0),
('sistemasJJD', 'bosque2017', 94476708, 'administrador', 'Desarrollo Sistemas', 'Universiad El Bosque', 1, 'N/A', '', '', '0000-00-00', '0000-00-00', 0, 0),
('paciente2@gmail.com', '12345', 99083065, 'paciente', 'Jose', 'Rodriguez', 1, 'Masculino', '2274569', 'calle 99 n 12-5', '2017-10-12', '2017-10-12', 1, 1),
('diegolga@hotmail.com', 'kogan', 1030548087, 'paciente', 'Diego Alfonso', 'Alvarez Alvarez', 1, 'Masculino', '8027369', 'CL 6C 71B 28', '2017-10-13', '2017-10-13', 1, 1),
('lopezcarlosa@unbosque.edu.co', '8021', 3002078021, 'profesional', 'Carlos Andrés', 'Lopez', 1, 'masculino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 1),
('ambarbosas@unbosque.edu.co', '3045', 3005733045, 'profesional', 'Adriana', 'Barbosa Sierra', 1, 'femenino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 1),
('orozcoangela@unbosque.edu.co', '1142', 3006491142, 'profesional', 'Angela María', 'Orozco', 1, 'femenino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 1),
('daalvarez@unbosque.edu.co', '6578', 3014696578, 'profesional', 'Diego Alfonso', 'Álvarez', 1, 'masculino', '1234', 'nn', '2017-09-22', '2017-09-22', 1, 1),
('asabogala@unbosque.edu.co', '6004', 3103196004, 'profesional', 'Angie Tatiana', 'Sabogal', 1, 'femenino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 1),
('blopezf@unbosque.edu.co', '4680', 3105644680, 'profesional', 'Bryan', 'Lopez', 1, 'masculino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 1),
('delgadocarlos@unbosque.edu.co', '9771', 3115619771, 'profesional', 'Carlos Ignacio', 'Delgado', 1, 'masculino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 0),
('svalenciav@unbosque.edu.co', '2992', 3125762992, 'profesional', 'Sebastian', 'Valencia', 1, 'masculino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 0),
('aramirezgo@unbosque.edu.co', '4672', 3153254672, 'profesional', 'Andrea', 'Ramirez', 1, 'femenino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 1),
('apastran@unbosque.edu.co', '3458', 3164293458, 'profesional', 'Ana María', 'Pastran', 1, 'femenino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 0),
('latrujillo@unbosque.edu.co', '7211', 3197047211, 'profesional', 'Luz Adriana', 'Trujillo', 1, 'femenino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 0),
('jcdiazg@unbosque.edu.co', '8358', 3197668358, 'profesional', 'Juan Camilo', 'Diaz Guzman', 1, 'masculino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 1),
('lacastror@unbosque.edu.co', '0957', 3214680957, 'profesional', 'Angie', 'Castro', 1, 'femenino', '12345', 'nn', '2017-09-22', '2017-09-22', 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `ciudad`
--
ALTER TABLE `ciudad`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `comorbilidad`
--
ALTER TABLE `comorbilidad`
  ADD PRIMARY KEY (`paciente`);

--
-- Indexes for table `diagnosticoinicial`
--
ALTER TABLE `diagnosticoinicial`
  ADD PRIMARY KEY (`paciente`);

--
-- Indexes for table `habitos`
--
ALTER TABLE `habitos`
  ADD PRIMARY KEY (`idfila`);

--
-- Indexes for table `informacion`
--
ALTER TABLE `informacion`
  ADD PRIMARY KEY (`idfila`);

--
-- Indexes for table `medicamento`
--
ALTER TABLE `medicamento`
  ADD PRIMARY KEY (`idfila`);

--
-- Indexes for table `paciente`
--
ALTER TABLE `paciente`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pais`
--
ALTER TABLE `pais`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`identificacion`),
  ADD UNIQUE KEY `identificacion` (`identificacion`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `ciudad`
--
ALTER TABLE `ciudad`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `habitos`
--
ALTER TABLE `habitos`
  MODIFY `idfila` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `informacion`
--
ALTER TABLE `informacion`
  MODIFY `idfila` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `medicamento`
--
ALTER TABLE `medicamento`
  MODIFY `idfila` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
--
-- AUTO_INCREMENT for table `pais`
--
ALTER TABLE `pais`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Metadata
--
USE `phpmyadmin`;

--
-- Metadata for table actividadfisica
--

--
-- Metadata for table animo
--

--
-- Metadata for table apoyosocial
--

--
-- Metadata for table autocuidado
--

--
-- Metadata for table autoeficacia
--

--
-- Metadata for table avances
--

--
-- Metadata for table ciudad
--

--
-- Metadata for table comorbilidad
--

--
-- Metadata for table comunidad
--

--
-- Metadata for table diagnosticoinicial
--

--
-- Metadata for table estadoanimo
--

--
-- Metadata for table estilovida
--

--
-- Metadata for table fyc
--

--
-- Metadata for table glucosa
--

--
-- Metadata for table habitos
--

--
-- Metadata for table hba1c
--

--
-- Metadata for table informacion
--

--
-- Metadata for table medicamento
--

--
-- Metadata for table mensajes
--

--
-- Metadata for table metas
--

--
-- Metadata for table notificaciones
--

--
-- Metadata for table observaciones
--

--
-- Metadata for table paciente
--

--
-- Metadata for table pais
--

--
-- Metadata for table percepcionriesgo
--

--
-- Metadata for table pesoimc
--

--
-- Metadata for table recursos
--

--
-- Metadata for table tension
--

--
-- Metadata for table tiemposdeuso
--

--
-- Dumping data for table `pma__table_uiprefs`
--

INSERT INTO `pma__table_uiprefs` (`username`, `db_name`, `table_name`, `prefs`, `last_update`) VALUES
('root', 'dt2', 'tiemposdeuso', '{\"sorted_col\":\"`usuario` ASC\"}', '2017-10-10 20:20:56');

--
-- Metadata for table tratantes
--

--
-- Metadata for table trigliceridos
--

--
-- Metadata for table usuario
--

--
-- Dumping data for table `pma__table_uiprefs`
--

INSERT INTO `pma__table_uiprefs` (`username`, `db_name`, `table_name`, `prefs`, `last_update`) VALUES
('root', 'dt2', 'usuario', '{\"sorted_col\":\"`fecharegistro`  ASC\"}', '2017-10-10 18:30:46');

--
-- Metadata for database dt2
--

--
-- Dumping data for table `pma__bookmark`
--

INSERT INTO `pma__bookmark` (`dbase`, `user`, `label`, `query`) VALUES
('dt2', 'root', 'borrado1', 'SELECT * FROM `tratantes`, `tension`, `pesoimc`, `paciente`, `glucemia`,`diagnosticoinicial`,`comorbilidad`,`avances`,`animo` WHERE 1'),
('dt2', 'root', 'update de avance', 'update avances set completado = 1 WHERE identificacion = 1002');

--
-- Dumping data for table `pma__relation`
--

INSERT INTO `pma__relation` (`master_db`, `master_table`, `master_field`, `foreign_db`, `foreign_table`, `foreign_field`) VALUES
('dt2', 'Comunidad', 'participante', 'dt2', 'usuario', 'identificacion'),
('dt2', 'actividadfisica', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'animo', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'apoyosocial', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'autoeficacia', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'avances', 'identificacion', 'dt2', 'usuario', 'identificacion'),
('dt2', 'ciudad', 'pais', 'dt2', 'pais', 'id'),
('dt2', 'comorbilidad', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'comunidad', 'participante', 'dt2', 'usuario', 'identificacion'),
('dt2', 'diagnosticoinicial', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'estilovida', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'fyc', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'hba1c', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'mensajes', 'destinatario', 'dt2', 'usuario', 'identificacion'),
('dt2', 'mensajes', 'id', 'dt2', 'usuario', 'identificacion'),
('dt2', 'paciente', 'id', 'dt2', 'usuario', 'identificacion'),
('dt2', 'pesoimc', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'recursos', 'responsable', 'dt2', 'usuario', 'identificacion'),
('dt2', 'tension', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'tratantes', 'paciente', 'dt2', 'paciente', 'id'),
('dt2', 'trigliceridos', 'id', 'dt2', 'paciente', 'id'),
('dt2', 'usuario', 'ciudad', 'dt2', 'ciudad', 'id');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
