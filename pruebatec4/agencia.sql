-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 11-01-2024 a las 04:04:34
-- Versión del servidor: 10.4.28-MariaDB
-- Versión de PHP: 8.1.17

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `agencia`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente`
--

CREATE TABLE `cliente` (
  `id` bigint(20) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `dni` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `num_tarjeta` varchar(255) NOT NULL,
  `telefono` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente`
--

INSERT INTO `cliente` (`id`, `apellido`, `dni`, `email`, `nombre`, `num_tarjeta`, `telefono`) VALUES
(1, 'Lopez', '12346986A', 'juan@example.com', 'Juan', '156454444423456', '677154523'),
(2, 'Pérez', '12345699A', 'juan@example.com', 'Juan', '1234554564456789', '123455456789'),
(52, 'Gomez', '78901234B', 'maria@example.com', 'Maria', '378945612345678', '611223344'),
(53, 'Rodriguez', '56789012C', 'carlos@example.com', 'Carlos', '9876543210123456', '688987654');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cliente_seq`
--

CREATE TABLE `cliente_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `cliente_seq`
--

INSERT INTO `cliente_seq` (`next_val`) VALUES
(151);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitacion`
--

CREATE TABLE `habitacion` (
  `id` bigint(20) NOT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha_alta` date NOT NULL,
  `fecha_baja` date DEFAULT NULL,
  `precio` double NOT NULL,
  `tipo` varchar(255) NOT NULL,
  `usuario_alta` varchar(255) NOT NULL,
  `usuario_baja` varchar(255) DEFAULT NULL,
  `id_hotel` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `habitacion`
--

INSERT INTO `habitacion` (`id`, `codigo`, `descripcion`, `fecha_alta`, `fecha_baja`, `precio`, `tipo`, `usuario_alta`, `usuario_baja`, `id_hotel`) VALUES
(1, 'AR-0002-001', 'habitacion con baño privado y vistas al mar', '2024-01-11', NULL, 630, 'doble', 'admin', NULL, 1),
(2, 'AR-0003-0001', 'Habitación doble con vista al mar', '2024-01-11', NULL, 150, 'doble', 'admin', NULL, 3),
(3, 'RC-0001-0002', 'Suite triple con balcón', '2024-01-11', NULL, 220, 'triple', 'admin', NULL, 4),
(4, 'RC-0002-0003', 'Habitación individual estándar', '2024-01-11', NULL, 120, 'individual', 'admin', NULL, 5),
(6, 'AR-0003-0002', 'Habitación doble con vista al mar', '2024-01-11', NULL, 150, 'doble', 'admin', NULL, 3),
(7, 'AR-0003-0003', 'Habitación doble con vista al mar', '2024-01-11', NULL, 150, 'doble', 'admin', NULL, 3),
(8, 'AR-0003-0004', 'Habitación doble con vista al mar', '2024-01-11', NULL, 250, 'triple', 'admin', NULL, 3),
(10, 'RC-0002-0004', 'Habitación individual estándar', '2024-01-11', NULL, 120, 'doble', 'admin', NULL, 5),
(11, 'GH-0003-0001', 'Habitación doble estándar', '2024-01-11', NULL, 150, 'doble', 'admin', NULL, 6),
(12, 'GH-0004-0002', 'Suite triple con vista panorámica', '2024-01-11', NULL, 220, 'triple', 'admin', NULL, 7),
(13, 'HL-0005-0003', 'Habitación individual con balcón', '2024-01-11', NULL, 120, 'individual', 'admin', NULL, 8),
(14, 'HL-0006-0004', 'Habitación múltiple con cocina completa', '2024-01-11', NULL, 180, 'múltiple', 'admin', NULL, 10),
(15, 'MT-0007-0005', 'Habitación doble con acceso a la piscina', '2024-01-11', NULL, 200, 'doble', 'admin', NULL, 11),
(16, 'SH-0008-0006', 'Habitación individual con jacuzzi', '2024-01-11', NULL, 130, 'individual', 'admin', NULL, 12),
(17, 'SH-0009-0007', 'Habitación doble con vista a las cataratas', '2024-01-11', NULL, 160, 'doble', 'admin', NULL, 13),
(18, 'IR-0010-0008', 'Suite presidencial triple', '2024-01-11', NULL, 250, 'triple', 'admin', NULL, 14);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `habitacion_seq`
--

CREATE TABLE `habitacion_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `habitacion_seq`
--

INSERT INTO `habitacion_seq` (`next_val`) VALUES
(101);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel`
--

CREATE TABLE `hotel` (
  `id` bigint(20) NOT NULL,
  `ciudad` varchar(255) NOT NULL,
  `codigo` varchar(255) NOT NULL,
  `fecha_alta` date NOT NULL,
  `fecha_baja` date DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `usuario_alta` varchar(255) NOT NULL,
  `usuario_baja` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hotel`
--

INSERT INTO `hotel` (`id`, `ciudad`, `codigo`, `fecha_alta`, `fecha_baja`, `nombre`, `usuario_alta`, `usuario_baja`) VALUES
(1, 'Miami', 'AR-0002', '2024-01-11', NULL, 'Atlantis Resort', 'admin', NULL),
(3, 'Miami', 'AR-0003', '2024-01-11', NULL, 'Atlantis Resort 2', 'admin', NULL),
(4, 'Buenos Aires', 'RC-0001', '2024-01-11', NULL, 'Ritz-Carlton', 'admin', NULL),
(5, 'Medellín', 'RC-0002', '2024-01-11', NULL, 'Ritz-Carlton 2', 'admin', NULL),
(6, 'Madrid', 'GH-0003', '2024-01-11', NULL, 'Grand Hyatt', 'admin', NULL),
(7, 'Buenos Aires', 'GH-0004', '2024-01-11', NULL, 'Grand Hyatt 2', 'admin', NULL),
(8, 'Barcelona', 'HL-0005', '2024-01-11', NULL, 'Hilton', 'admin', NULL),
(10, 'Barcelona', 'HL-0006', '2024-01-11', NULL, 'Hilton 2', 'admin', NULL),
(11, 'Barcelona', 'MT-0007', '2024-01-11', NULL, 'Marriott', 'admin', NULL),
(12, 'Madrid', 'SH-0008', '2024-01-11', '2024-01-11', 'Sheraton', 'admin', 'admin'),
(13, 'Iguazú', 'SH-0009', '2024-01-11', NULL, 'Sheraton 2', 'admin', NULL),
(14, 'Cartagena', 'IR-0010', '2024-01-11', NULL, 'Intercontinental', 'admin', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `hotel_seq`
--

CREATE TABLE `hotel_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `hotel_seq`
--

INSERT INTO `hotel_seq` (`next_val`) VALUES
(101);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva_hotel`
--

CREATE TABLE `reserva_hotel` (
  `id` bigint(20) NOT NULL,
  `fecha_alta` date NOT NULL,
  `fecha_baja` date DEFAULT NULL,
  `fecha_desde` date NOT NULL,
  `fecha_hasta` date NOT NULL,
  `noches` int(11) NOT NULL,
  `num_personas` int(11) NOT NULL,
  `precio` double DEFAULT NULL,
  `usuario_alta` varchar(255) NOT NULL,
  `usuario_baja` varchar(255) DEFAULT NULL,
  `id_cliente` bigint(20) NOT NULL,
  `id_habitacion` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva_hotel`
--

INSERT INTO `reserva_hotel` (`id`, `fecha_alta`, `fecha_baja`, `fecha_desde`, `fecha_hasta`, `noches`, `num_personas`, `precio`, `usuario_alta`, `usuario_baja`, `id_cliente`, `id_habitacion`) VALUES
(1, '2024-01-11', NULL, '2024-09-01', '2024-09-03', 2, 2, 1260, 'admin', NULL, 1, 1),
(2, '2024-01-11', NULL, '2024-09-16', '2024-09-17', 1, 3, 150, 'admin', NULL, 1, 11),
(3, '2024-01-11', NULL, '2024-09-05', '2024-09-07', 2, 3, 440, 'admin', NULL, 52, 12),
(4, '2024-01-11', NULL, '2024-09-10', '2024-09-12', 2, 1, 240, 'admin', NULL, 53, 13);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva_hotel_seq`
--

CREATE TABLE `reserva_hotel_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva_hotel_seq`
--

INSERT INTO `reserva_hotel_seq` (`next_val`) VALUES
(101);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva_vuelo`
--

CREATE TABLE `reserva_vuelo` (
  `id` bigint(20) NOT NULL,
  `fecha_alta` date NOT NULL,
  `fecha_baja` date DEFAULT NULL,
  `num_personas` int(11) NOT NULL,
  `precio` double DEFAULT NULL,
  `tipo_asiento` varchar(255) NOT NULL,
  `usuario_alta` varchar(255) NOT NULL,
  `usuario_baja` varchar(255) DEFAULT NULL,
  `id_cliente` bigint(20) NOT NULL,
  `id_vuelo` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva_vuelo`
--

INSERT INTO `reserva_vuelo` (`id`, `fecha_alta`, `fecha_baja`, `num_personas`, `precio`, `tipo_asiento`, `usuario_alta`, `usuario_baja`, `id_cliente`, `id_vuelo`) VALUES
(1, '2024-01-11', NULL, 5, 250, 'economico', 'anonymousUser', NULL, 2, 1),
(2, '2024-01-11', NULL, 5, 250, 'economico', 'anonymousUser', NULL, 2, 1),
(52, '2024-01-11', NULL, 5, 250, 'economico', 'anonymousUser', NULL, 2, 1),
(53, '2024-01-11', NULL, 5, 250, 'economico', 'anonymousUser', NULL, 2, 1),
(54, '2024-01-11', NULL, 5, 250, 'economico', 'anonymousUser', NULL, 2, 1),
(55, '2024-01-11', NULL, 5, 250, 'economico', 'anonymousUser', NULL, 2, 1),
(56, '2024-01-11', NULL, 5, 250, 'economico', 'anonymousUser', NULL, 2, 1),
(57, '2024-01-11', NULL, 5, 250, 'economico', 'anonymousUser', NULL, 2, 1),
(58, '2024-01-11', NULL, 5, 250, 'economico', 'anonymousUser', NULL, 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva_vuelo_seq`
--

CREATE TABLE `reserva_vuelo_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva_vuelo_seq`
--

INSERT INTO `reserva_vuelo_seq` (`next_val`) VALUES
(151);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vuelo`
--

CREATE TABLE `vuelo` (
  `id` bigint(20) NOT NULL,
  `asientos_economicos` int(11) DEFAULT NULL,
  `asientos_economicos_disponibles` int(11) DEFAULT NULL,
  `asientos_premium` int(11) DEFAULT NULL,
  `asientos_premium_disponibles` int(11) DEFAULT NULL,
  `destino` varchar(255) NOT NULL,
  `fecha` date NOT NULL,
  `fecha_alta` date NOT NULL,
  `fecha_baja` date DEFAULT NULL,
  `num_vuelo` varchar(255) NOT NULL,
  `origen` varchar(255) NOT NULL,
  `precio_asiento_economico` double DEFAULT NULL,
  `precio_asiento_premium` double DEFAULT NULL,
  `usuario_alta` varchar(255) NOT NULL,
  `usuario_baja` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vuelo`
--

INSERT INTO `vuelo` (`id`, `asientos_economicos`, `asientos_economicos_disponibles`, `asientos_premium`, `asientos_premium_disponibles`, `destino`, `fecha`, `fecha_alta`, `fecha_baja`, `num_vuelo`, `origen`, `precio_asiento_economico`, `precio_asiento_premium`, `usuario_alta`, `usuario_baja`) VALUES
(1, 50, 5, 50, 50, 'Miami', '2024-02-10', '2024-01-11', NULL, 'BAMI-1235', 'Barcelona', 50, 200, 'admin', NULL),
(3, 50, 50, 50, 50, 'Madrid', '2024-02-10', '2024-01-11', NULL, 'MIMA1420', 'Miami', 50, 200, 'admin', NULL),
(4, 50, 50, 50, 50, 'Madrid', '2024-02-10', '2024-01-11', NULL, 'MIMA-1420', 'Miami', 50, 200, 'admin', NULL),
(5, 50, 50, 50, 50, 'Buenos Aires', '2024-02-10', '2024-01-11', NULL, 'BABU-5536', 'Barcelona', 50, 200, 'admin', NULL),
(6, 50, 50, 50, 50, 'Barcelona', '2024-02-10', '2024-01-11', NULL, 'BUBA-3369', 'Buenos Aires', 50, 200, 'admin', NULL),
(7, 50, 50, 50, 50, 'Barcelona', '2024-02-10', '2024-01-11', NULL, 'IGBA-3369', 'Iguazú', 50, 200, 'admin', NULL),
(8, 50, 50, 50, 50, 'Cartagena', '2024-02-10', '2024-01-11', NULL, 'BOCA-4213', 'Bogotá', 50, 200, 'admin', NULL),
(9, 50, 50, 50, 50, 'Medellín', '2024-02-10', '2024-01-11', NULL, 'CAME-0321', 'Cartagena', 50, 200, 'admin', NULL),
(10, 50, 50, 50, 50, 'Iguazú', '2024-02-10', '2024-01-11', NULL, 'BOIG-6567', 'Bogotá', 50, 200, 'admin', NULL),
(11, 50, 50, 50, 50, 'Buenos Aires', '2024-02-10', '2024-01-11', NULL, 'BOBA-6567', 'Bogotá', 50, 200, 'admin', NULL),
(12, 50, 50, 50, 50, 'Madrid', '2024-02-10', '2024-01-11', NULL, 'BOMA-4442', 'Bogotá', 50, 200, 'admin', NULL),
(13, 50, 50, 50, 50, 'Miami', '2024-02-10', '2024-01-11', NULL, 'MEMI-9986', 'Medellín', 50, 200, 'admin', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `vuelo_seq`
--

CREATE TABLE `vuelo_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `vuelo_seq`
--

INSERT INTO `vuelo_seq` (`next_val`) VALUES
(101);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cliente`
--
ALTER TABLE `cliente`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `habitacion`
--
ALTER TABLE `habitacion`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_tdu5j0k44rnq6qj9ohfs5ft1w` (`codigo`),
  ADD KEY `FKnati1hwmlnaiemvjcwuql8web` (`id_hotel`);

--
-- Indices de la tabla `hotel`
--
ALTER TABLE `hotel`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_7shn083e1hv1x1cevkstme4h6` (`codigo`);

--
-- Indices de la tabla `reserva_hotel`
--
ALTER TABLE `reserva_hotel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8xh6mhb8x0gg39egdf44bprrk` (`id_cliente`),
  ADD KEY `FK6m0b71qwt5snhaqjcvomhdv6n` (`id_habitacion`);

--
-- Indices de la tabla `reserva_vuelo`
--
ALTER TABLE `reserva_vuelo`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKt0kirl2xmt8c3dt8whieiomov` (`id_cliente`),
  ADD KEY `FKpauoxdcvff5e2ahpfnqhw4ia4` (`id_vuelo`);

--
-- Indices de la tabla `vuelo`
--
ALTER TABLE `vuelo`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UnicoVueloDestinoFecha` (`num_vuelo`,`origen`,`destino`,`fecha`);

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `habitacion`
--
ALTER TABLE `habitacion`
  ADD CONSTRAINT `FKnati1hwmlnaiemvjcwuql8web` FOREIGN KEY (`id_hotel`) REFERENCES `hotel` (`id`);

--
-- Filtros para la tabla `reserva_hotel`
--
ALTER TABLE `reserva_hotel`
  ADD CONSTRAINT `FK6m0b71qwt5snhaqjcvomhdv6n` FOREIGN KEY (`id_habitacion`) REFERENCES `habitacion` (`id`),
  ADD CONSTRAINT `FK8xh6mhb8x0gg39egdf44bprrk` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`);

--
-- Filtros para la tabla `reserva_vuelo`
--
ALTER TABLE `reserva_vuelo`
  ADD CONSTRAINT `FKpauoxdcvff5e2ahpfnqhw4ia4` FOREIGN KEY (`id_vuelo`) REFERENCES `vuelo` (`id`),
  ADD CONSTRAINT `FKt0kirl2xmt8c3dt8whieiomov` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
