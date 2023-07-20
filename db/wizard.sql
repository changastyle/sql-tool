-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 25-05-2023 a las 07:33:36
-- Versión del servidor: 10.4.22-MariaDB
-- Versión de PHP: 8.1.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `wizard`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `componentes`
--

CREATE TABLE `componentes` (
  `id` int(11) NOT NULL,
  `clases` varchar(255) DEFAULT NULL,
  `cod` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `cantidadComponentesPorPagina` int(11) NOT NULL,
  `disposicion` varchar(255) DEFAULT NULL,
  `texto` varchar(255) DEFAULT NULL,
  `texto2` varchar(255) DEFAULT NULL,
  `urlProv` varchar(255) DEFAULT NULL,
  `fkComponentePadre` int(11) DEFAULT NULL,
  `fkFoto` int(11) DEFAULT NULL,
  `fkSitio` int(11) DEFAULT NULL,
  `fkTexto1` int(11) DEFAULT NULL,
  `fkTexto2` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `componentes`
--

INSERT INTO `componentes` (`id`, `clases`, `cod`, `nombre`, `cantidadComponentesPorPagina`, `disposicion`, `texto`, `texto2`, `urlProv`, `fkComponentePadre`, `fkFoto`, `fkSitio`, `fkTexto1`, `fkTexto2`) VALUES
(4, 'col-12', 2234, 'carru', 0, NULL, '', NULL, 'carrusel.html', NULL, 44, 1, 1, 2),
(5, NULL, 0, 'medio-carru-izq', 0, NULL, NULL, '', 'medio-carru-izq.html', 4, NULL, 1, 4, 4),
(6, 'col-12', 2235, 'first-text', 0, NULL, '<p>We work hand-in-hand with trusted local peacebuilder in conflict zones</p><p>across central Africa to help their communities organize to prevent violent&nbsp;</p><p>conflicts, recover from its impacts and build sustainable peace2.</p>', '', '', NULL, NULL, 1, 4, 4),
(7, 'col-12', 4000, 'col-estampas', 0, NULL, '', '', '', NULL, NULL, 1, 4, 4),
(8, '', 4001, 'estampa-1', 0, NULL, '<p>CRISIS MAPPING &amp;</p><p>CONFLICT&nbsp;</p><p>ANALYSIS</p>', '', '', 7, 23, 1, 4, 4),
(9, '', 4002, 'estampa-2', 0, NULL, '', '', '', 7, 22, 1, 4, 4),
(10, '', 4003, 'estampa-3', 0, NULL, '<p>COMMUNITY -</p><p>BASSED EARLY</p><p>WARNING AND</p><p>RESPONSE</p>', '', '', 7, 24, 1, 4, 4),
(11, '', 4004, 'estampa-4', 0, NULL, '', '', '', 7, 53, 1, 4, 4),
(12, '', 4005, 'estampa-5', 0, NULL, 'MEDIA-BASED<br>COMMUNITY<br>SENSITIZATION', '', '', 7, 25, 1, 4, 4),
(13, '', 4006, 'estampa-6', 0, NULL, '', '', '', 7, 22, 1, 4, 4),
(14, NULL, 1001, 'logo', 0, NULL, '', '', '', NULL, 51, 1, 4, 4),
(15, '', 5000, 'tarjetas', 0, NULL, '', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonum. Lorem Ipsum has been the industry\'s standard dummy.', '', NULL, 51, 1, 4, 4),
(16, 'col-5', 5001, 'tarjeta1', 0, NULL, 'LOREM IMSUN IS SIMPLY', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonum. Lorem Ipsum has been the industry\'s standard dummy.', '', 15, 57, 1, 4, 4),
(17, 'col-5', 5002, 'tarjeta2', 0, NULL, 'LOREM IMSUN IS SIMPLY', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonum. Lorem Ipsum has been the industry\'s standard dummy.', '', 15, 57, 1, 4, 4),
(18, 'col-5', 5003, 'tarjeta3', 0, NULL, 'LOREM IMSUN IS SIMPLY', '', '', 15, 57, 1, 4, 4),
(19, '', 6000, 'timeline', 2, '7-5|3-3-6|6-6|8-4|', '', '', '', NULL, 57, 1, 4, 4),
(20, '', 6001, 'timeline1', 0, NULL, 'Lorem ipsum dolor sit amet, consectetuer adipiscing.', '', '', 19, 57, 1, 4, 4),
(21, '', 6002, 'timeline2', 0, NULL, 'Lorem ipsum dolor sit amet, consectetuer adipiscing.', '', '', 19, 58, 1, 4, 4),
(22, '', 6003, 'timeline3', 0, NULL, 'Lorem ipsum dolor sit amet, consectetuer adipiscing.', '', '', 19, 62, 1, 4, 4),
(23, '', 6004, 'timeline4', 0, NULL, 'Lorem ipsum dolor sit amet, consectetuer adipiscing.', '', '', 19, 59, 1, 4, 4),
(24, '', 6005, 'timeline5', 0, NULL, 'Lorem ipsum dolor sit amet, consectetuer adipiscing.', '', '', 19, 58, 1, 4, 4),
(25, '', 6006, 'timeline6', 0, NULL, 'Lorem ipsum dolor sit amet, consectetuer adipiscing.', '', '', 19, 60, 1, 4, 4),
(26, '', 6007, 'timeline7', 0, NULL, 'Lorem ipsum dolor sit amet, consectetuer adipiscing.', '', '', 19, 61, 1, 4, 4),
(27, '', 6008, 'timeline8', 0, NULL, 'Lorem ipsum dolor sit amet, consectetuer adipiscing.', '', '', 19, 61, 1, 4, 4),
(28, '', 6009, 'timeline9', 0, NULL, 'Lorem ipsum dolor sit amet, consectetuer adipiscing.', '', '', 19, 61, 1, 4, 4),
(29, 'col-5', 5004, 'tarjeta3', 0, NULL, 'LOREM IMSUN IS SIMPLY', '', '', 15, 57, 1, NULL, NULL),
(34, 'col-5', 5005, 'tarjeta3', 0, NULL, 'LOREM IMSUN IS TOO SIMPLY', '', '', 15, 63, 1, NULL, NULL),
(35, 'col-5', 5006, 'tarjeta3', 0, NULL, 'LOREM IMSUN IS SIMPLY', '', '', 15, 57, 1, NULL, NULL),
(36, 'col-5', 5007, 'tarjeta3', 0, NULL, 'LOREM IMSUN IS SIMPLY', '', '', 15, 57, 1, NULL, NULL),
(37, 'col-5', 5008, 'tarjeta3', 0, NULL, 'LOREM IMSUN IS SIMPLY', '', '', 15, 57, 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `configuraciones`
--

CREATE TABLE `configuraciones` (
  `id` int(11) NOT NULL,
  `carpetaWeb` varchar(255) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `ip` varchar(255) DEFAULT NULL,
  `linux` bit(1) NOT NULL,
  `nextRefresh` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `nombreProyecto` varchar(255) DEFAULT NULL,
  `protocolo` varchar(255) DEFAULT NULL,
  `puerto` varchar(255) DEFAULT NULL,
  `subCarpetaImagenes` varchar(255) DEFAULT NULL,
  `dentroDeCarpetaHTMLEnVarWww` bit(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `configuraciones`
--

INSERT INTO `configuraciones` (`id`, `carpetaWeb`, `enabled`, `ip`, `linux`, `nextRefresh`, `nombre`, `nombreProyecto`, `protocolo`, `puerto`, `subCarpetaImagenes`, `dentroDeCarpetaHTMLEnVarWww`) VALUES
(1, '1681632003540', b'1', 'localhost', b'0', 3599, 'desarollo', 'wizard', 'http://', NULL, '0', b'0'),
(2, '1681632003540', b'0', 'eu.viewdevs.com.ar', b'1', 3597, 'produccion', 'wizard', 'http://', '9000', '0', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `detalles_textos`
--

CREATE TABLE `detalles_textos` (
  `id` int(11) NOT NULL,
  `fkTextoPadre` int(11) DEFAULT NULL,
  `fkIdioma` int(11) DEFAULT NULL,
  `valor` text DEFAULT NULL,
  `fechaUltMod` datetime DEFAULT NULL,
  `esHtml` bit(1) NOT NULL,
  `activo` bit(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `detalles_textos`
--

INSERT INTO `detalles_textos` (`id`, `fkTextoPadre`, `fkIdioma`, `valor`, `fechaUltMod`, `esHtml`, `activo`) VALUES
(1, 1, 1, 'WE ARE GREENTUITION', '2023-05-23 09:48:39', b'0', b'0'),
(2, 1, 2, 'SOMOS GREENTUITION', '2023-05-23 09:48:39', b'0', b'0'),
(3, 2, 2, 'Lorom ipsum dolor sit amet, consectetur adipiscing elit. Sed neque ', '2023-05-23 09:48:39', b'0', b'0'),
(4, 2, 1, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed neque', '2023-05-23 09:48:39', b'0', b'0'),
(5, 1, 3, 'SIAMO GREENTUITION', '2023-05-23 09:48:39', b'0', b'0'),
(6, 5, 1, 'DAME PLATA!', '2023-05-23 09:48:39', b'0', b'0'),
(7, 5, 2, 'GARPA', '2023-05-23 09:48:39', b'0', b'0'),
(8, 6, 1, 'OUR WORKer!', '2023-05-23 09:48:39', b'0', b'1'),
(9, 6, 2, 'NUESTRO TRABAJO!', '2023-05-23 09:48:39', b'0', b'1'),
(10, 7, 2, 'ASDASDASDDDDDDDDDDASDASDASDASDASASDASDASD\r\nASD\r\nAS\r\nDA\r\nSD\r\nAS\r\nDAASDASD', '2023-05-23 09:48:39', b'1', b'1'),
(11, 7, 1, 'VIEJITA', '2023-05-23 09:48:39', b'1', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `fotos`
--

CREATE TABLE `fotos` (
  `id` int(11) NOT NULL,
  `urlProv` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `fotos`
--

INSERT INTO `fotos` (`id`, `urlProv`) VALUES
(1, 'nicoo3061.jpg'),
(2, 'nico29483.jpg'),
(3, 'nico2584.png'),
(4, 'img_20220622_1747040333.jpg'),
(5, 'img_20220622_1747045687.jpg'),
(6, 'screenshot_2022-08-30-05-16-55-130_com.whatsapp3988.jpg'),
(7, 'img_20220409_1749116854.jpg'),
(8, 'img_20220409_1749116221.jpg'),
(9, 'nico23517.jpg'),
(10, 'img_20220301_1613534090.jpg'),
(11, 'img_20220301_1613536319.jpg'),
(12, 'img_20220301_1613531895.jpg'),
(13, 'img_20220301_1613539195.jpg'),
(14, 'img_20220301_1613539231.jpg'),
(15, 'img_20220301_1613534172.jpg'),
(16, 'img_20220301_1613536658.jpg'),
(17, 'git flow3406.jpg'),
(18, 'git flow4987.jpg'),
(19, 'img_20220622_1747044584.jpg'),
(20, 'nico9811.jpg'),
(21, 'rotada-rotada-rotada-pexels-zhang-kaiyv-10835156500.jpg'),
(22, 'pcs2815.jpg'),
(23, 'conflict-analysis0972.jpg'),
(24, 'ewn-20226883.jpg'),
(25, 'conflict-analysis (1)7768.jpg'),
(26, 'nico4793.jpg'),
(27, 'nicoo3504.jpg'),
(28, NULL),
(29, NULL),
(30, NULL),
(31, NULL),
(32, NULL),
(33, 'img-galeria-cinco1837.jpg'),
(34, 'img-galeria-cinco4998.jpg'),
(35, 'img-galeria-cinco8376.jpg'),
(36, 'img-galeria-cinco9602.jpg'),
(37, 'img-galeria-cuatro1474.jpg'),
(38, 'img-galeria-tres0813.jpg'),
(39, 'logo6360.jpg'),
(40, 'img-galeria-uno6456.jpg'),
(41, 'img-galeria-dos7386.jpg'),
(42, 'img-galeria-dos1394.jpg'),
(43, 'img-carrusel23605.jpg'),
(44, 'rotada-rotada-rotada-banner0761.jpg'),
(45, 'rotada-rotada-rotada-logo4001.jpg'),
(46, 'logo5987.jpg'),
(47, 'logo8034.jpg'),
(48, 'logo8279.jpg'),
(49, 'logo0430.png'),
(50, 'logo2016.jpg'),
(51, 'logo9428.png'),
(52, 'conflict-analysis4871.jpg'),
(53, 'returnee8800.jpg'),
(54, 'rotada-rotada-rotada-istockphoto-1368965646-1024x10241476.jpg'),
(55, 'img-carrusel6705.jpg'),
(56, 'img-carrusel1590.jpg'),
(57, 'img-carrusel8969.jpg'),
(58, 'rotada-rotada-rotada-img-galeria-cinco6400.jpg'),
(59, 'rotada-rotada-rotada-img-galeria-cuatro7559.jpg'),
(60, 'img-galeria-tres8981.jpg'),
(61, 'rotada-rotada-rotada-istockphoto-1368965646-1024x10243043.jpg'),
(62, 'rotada-rotada-rotada-img-galeria-dos6985.jpg'),
(63, 'rotada-rotada-rotada-2023-05-08 04_33_49-window6871.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `idiomas`
--

CREATE TABLE `idiomas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `activo` bit(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `idiomas`
--

INSERT INTO `idiomas` (`id`, `nombre`, `activo`) VALUES
(1, 'EN', b'1'),
(2, 'ES', b'1'),
(3, 'IT', b'1'),
(4, 'FR', b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `propiedades`
--

CREATE TABLE `propiedades` (
  `id` int(11) NOT NULL,
  `fkComponente` int(11) DEFAULT NULL,
  `fkPropiedadDisponible` int(11) DEFAULT NULL,
  `valor` varchar(255) DEFAULT NULL,
  `unidad` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `propiedades`
--

INSERT INTO `propiedades` (`id`, `fkComponente`, `fkPropiedadDisponible`, `valor`, `unidad`) VALUES
(10, 4, 2, '750', 'px'),
(11, 4, 6, '#000000', 'color');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `propiedadesdisponibles`
--

CREATE TABLE `propiedadesdisponibles` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `ignoredInStyle` bit(1) NOT NULL,
  `hayQuePonerUnidad` bit(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `propiedadesdisponibles`
--

INSERT INTO `propiedadesdisponibles` (`id`, `nombre`, `ignoredInStyle`, `hayQuePonerUnidad`) VALUES
(1, 'width', b'1', b'1'),
(2, 'height', b'0', b'1'),
(4, 'colsH', b'1', b'0'),
(5, 'background-color', b'0', b'0'),
(6, 'color', b'0', b'0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sitios`
--

CREATE TABLE `sitios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `colorCua` varchar(255) DEFAULT NULL,
  `colorPri` varchar(255) DEFAULT NULL,
  `colorSec` varchar(255) DEFAULT NULL,
  `colorTer` varchar(255) DEFAULT NULL,
  `fkFavicon` int(11) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `sitios`
--

INSERT INTO `sitios` (`id`, `nombre`, `url`, `colorCua`, `colorPri`, `colorSec`, `colorTer`, `fkFavicon`) VALUES
(1, 'greentuition', '/greentuition', '#feffff', '#1abc9c', '#df5bd3', '#333333', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `textos`
--

CREATE TABLE `textos` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `fkComponente` int(11) DEFAULT NULL,
  `orden` int(11) NOT NULL,
  `activo` bit(1) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `textos`
--

INSERT INTO `textos` (`id`, `nombre`, `fkComponente`, `orden`, `activo`) VALUES
(1, 'H1-CARRUSEL', 4, 1, b'1'),
(2, 'H2-CARRUSEL', 4, 2, b'1'),
(5, 'BUTTON-CARRUSEL', 4, 2, b'1'),
(6, 'H-TITULO-PIE-CARRUSEL', 4, 2, b'1'),
(7, 'TEXTO-PIE-CARRUSEL', 4, 2, b'1');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `componentes`
--
ALTER TABLE `componentes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1poqxoc1bb1dh2pnmi7tj8mkh` (`fkTexto1`),
  ADD KEY `FKjhkoswvumkaj0lcn8fhj99f99` (`fkTexto2`),
  ADD KEY `FKa9lo472bqr7hli45kvurlu1yj` (`fkComponentePadre`),
  ADD KEY `FK7g9e5aiuspqpfw7sp97fr2ujb` (`fkFoto`),
  ADD KEY `FKayuqsaohaq5v63beamwir285k` (`fkSitio`);

--
-- Indices de la tabla `configuraciones`
--
ALTER TABLE `configuraciones`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `detalles_textos`
--
ALTER TABLE `detalles_textos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKtpqb0n6bv6smlmpl86fog6bji` (`fkIdioma`),
  ADD KEY `FKdbp20rbnj3ha7gc0btx17uitj` (`fkTextoPadre`);

--
-- Indices de la tabla `fotos`
--
ALTER TABLE `fotos`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `idiomas`
--
ALTER TABLE `idiomas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `propiedades`
--
ALTER TABLE `propiedades`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK65hi2g418mvjkynjj0d77e43b` (`fkComponente`),
  ADD KEY `FKljaw9ov2kudpcugjpxgr8pybt` (`fkPropiedadDisponible`);

--
-- Indices de la tabla `propiedadesdisponibles`
--
ALTER TABLE `propiedadesdisponibles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `sitios`
--
ALTER TABLE `sitios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKbqd97fmc66q4vb521d7jki5wh` (`fkFavicon`);

--
-- Indices de la tabla `textos`
--
ALTER TABLE `textos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK3tms0j8o8gpl2jboiqfmmyw3x` (`fkComponente`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `componentes`
--
ALTER TABLE `componentes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT de la tabla `configuraciones`
--
ALTER TABLE `configuraciones`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `detalles_textos`
--
ALTER TABLE `detalles_textos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `fotos`
--
ALTER TABLE `fotos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=64;

--
-- AUTO_INCREMENT de la tabla `idiomas`
--
ALTER TABLE `idiomas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `propiedades`
--
ALTER TABLE `propiedades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `propiedadesdisponibles`
--
ALTER TABLE `propiedadesdisponibles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT de la tabla `sitios`
--
ALTER TABLE `sitios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `textos`
--
ALTER TABLE `textos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
