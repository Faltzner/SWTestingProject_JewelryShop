-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 21, 2018 at 08:36 PM
-- Server version: 10.1.35-MariaDB
-- PHP Version: 7.2.9

USE "gemdb";
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gemdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `buyorder`
--

CREATE TABLE `buyorder` (
  `buyorderID` char(5) NOT NULL,
  `productID` char(5) DEFAULT NULL,
  `Quantity` int(11) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `assignedDate` date DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `assignednum` int(11) NOT NULL,
  `status` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `buyorder`
--

INSERT INTO `buyorder` (`buyorderID`, `productID`, `Quantity`, `price`, `assignedDate`, `dueDate`, `assignednum`, `status`) VALUES
('00001', 'e0001', 3, 2000, '2018-12-16', '2018-12-25', 1, 1),
('00002', 'n0002', 1, 5000, '2018-12-16', '2018-12-25', 1, 1),
('00003', 'n0003', 2, 3000, '2018-12-16', '2018-12-21', 2, 0),
('00004', 'n0003', 1, 5000, '2018-12-10', '2018-12-20', 1, 1),
('00005', 'r0000', 1, 300, '2018-12-21', '2018-12-25', 1, 1),
('00006', 'b0000', 2, 1000, '2018-12-21', '2018-12-31', 2, 2),
('00007', 'e0001', 4, 1000, '2018-12-22', '2019-01-31', 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `employeeID` char(5) NOT NULL,
  `name` text,
  `phone` char(10) DEFAULT NULL,
  `password` char(10) DEFAULT NULL,
  `NumOfWork` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`employeeID`, `name`, `phone`, `password`, `NumOfWork`) VALUES
('00001', 'Mr.Arm', '0845978562', '78562s0001', 1),
('00002', 'Mr.First', '0879658421', '58421s0002', 0),
('00003', 'Mr.Fluke', '0987458687', '58687s0003', 1),
('00004', 'Mr.Ton', '0897548631', '48631s0004', 1);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE `product` (
  `productID` char(5) NOT NULL,
  `name` text,
  `description` text,
  `type` text,
  `rank` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productID`, `name`, `description`, `type`, `rank`) VALUES
('b0000', 'Steel Bracelet', 'Beautiful Bracelet made from Steel.', 'bracelet', 0),
('e0001', 'Golden Earrings', 'Beautiful Earrings made from Golden.', 'earrings', 1),
('n0001', 'Ruby Necklace', 'Beautiful Necklace made from Ruby.', 'necklace', 1),
('n0002', 'Amber Necklace', 'Beautiful Necklace made from Amber.', 'necklace', 1),
('n0003', 'Steel Necklace', 'Beautiful Necklace made from Steel.', 'necklace', 0),
('n0004', 'Golden Necklace', 'Beautiful Necklace made from Golden.', 'necklace', 1),
('r0000', 'Golden Ring', 'Beautiful Ring made from Golden.', 'ring', 0);

-- --------------------------------------------------------

--
-- Table structure for table `receipt`
--

CREATE TABLE `receipt` (
  `receiptID` char(5) NOT NULL,
  `totalprice` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `buyorderID` char(5) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `receipt`
--

INSERT INTO `receipt` (`receiptID`, `totalprice`, `date`, `buyorderID`) VALUES
('00001', 5000, '2018-12-16', '00004'),
('00002', 5000, '2018-12-16', '00002');

-- --------------------------------------------------------

--
-- Table structure for table `skill`
--

CREATE TABLE `skill` (
  `employeeID` char(5) NOT NULL,
  `necklace` int(11) DEFAULT NULL,
  `earrings` int(11) DEFAULT NULL,
  `ring` int(11) DEFAULT NULL,
  `bracelet` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `skill`
--

INSERT INTO `skill` (`employeeID`, `necklace`, `earrings`, `ring`, `bracelet`) VALUES
('00001', 1, 1, 1, 1),
('00002', 0, 0, 1, 1),
('00003', 1, 1, 0, 0),
('00004', 0, 1, 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `workorder`
--

CREATE TABLE `workorder` (
  `workID` char(5) NOT NULL,
  `employeeID` char(5) DEFAULT NULL,
  `productID` char(5) DEFAULT NULL,
  `buyorderID` char(5) DEFAULT NULL,
  `assignedDate` date DEFAULT NULL,
  `dueDate` date DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `workorder`
--

INSERT INTO `workorder` (`workID`, `employeeID`, `productID`, `buyorderID`, `assignedDate`, `dueDate`, `status`) VALUES
('w0001', '00001', 'n0002', '00002', '2018-04-18', '2018-04-25', 'done'),
('w0002', '00002', 'e0001', '00001', '2018-07-07', '2018-07-22', 'done'),
('w0003', '00003', 'n0003', '00003', '2018-08-04', '2018-08-10', 'working'),
('w0004', '00002', 'r0000', '00005', '2018-12-21', '2018-12-24', 'done'),
('w0005', '00004', 'n0003', '00003', '2018-12-21', '2018-12-22', 'working'),
('w0006', '00001', 'b0000', '00006', '2018-12-21', '2018-12-30', 'done'),
('w0007', '00002', 'b0000', '00006', '2018-12-22', '2018-12-31', 'done'),
('w0008', '00001', 'e0001', '00007', '2018-12-22', '2019-01-20', 'working');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `buyorder`
--
ALTER TABLE `buyorder`
  ADD PRIMARY KEY (`buyorderID`),
  ADD KEY `productID` (`productID`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`employeeID`);

--
-- Indexes for table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`productID`);

--
-- Indexes for table `receipt`
--
ALTER TABLE `receipt`
  ADD PRIMARY KEY (`receiptID`),
  ADD KEY `buyorderID` (`buyorderID`);

--
-- Indexes for table `skill`
--
ALTER TABLE `skill`
  ADD PRIMARY KEY (`employeeID`),
  ADD KEY `employeeID` (`employeeID`);

--
-- Indexes for table `workorder`
--
ALTER TABLE `workorder`
  ADD PRIMARY KEY (`workID`),
  ADD KEY `employeeID` (`employeeID`),
  ADD KEY `productID` (`productID`),
  ADD KEY `buyorderID` (`buyorderID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `buyorder`
--
ALTER TABLE `buyorder`
  ADD CONSTRAINT `buyorder_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`) ON UPDATE CASCADE;

--
-- Constraints for table `receipt`
--
ALTER TABLE `receipt`
  ADD CONSTRAINT `receipt_ibfk_1` FOREIGN KEY (`buyorderID`) REFERENCES `buyorder` (`buyorderID`) ON UPDATE CASCADE;

--
-- Constraints for table `skill`
--
ALTER TABLE `skill`
  ADD CONSTRAINT `skill_ibfk_1` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`) ON UPDATE CASCADE;

--
-- Constraints for table `workorder`
--
ALTER TABLE `workorder`
  ADD CONSTRAINT `workorder_ibfk_1` FOREIGN KEY (`employeeID`) REFERENCES `employee` (`employeeID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `workorder_ibfk_2` FOREIGN KEY (`buyorderID`) REFERENCES `buyorder` (`buyorderID`) ON UPDATE CASCADE,
  ADD CONSTRAINT `workorder_ibfk_3` FOREIGN KEY (`productID`) REFERENCES `product` (`productID`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;