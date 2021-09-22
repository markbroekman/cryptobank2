-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: cryptobank
-- ------------------------------------------------------
-- Server version	8.0.23

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

Use cryptobank;

-- customer
-- Dumping data for table `admin`
--

LOCK TABLES admin WRITE;
INSERT INTO admin VALUES ('admin','5b49dfb8f336487260af55dc7998a2923e5ab79ae3dfb6899cb31f947d52749d','9d264ec3');
UNLOCK TABLES;

--
-- Dumping data for table `asset`
--

LOCK TABLES asset WRITE;
INSERT INTO asset VALUES ('ADA','Cardano','ADA'),('BCH','Bitcoin Cash','BCH'),('BNB','Binance Coin','BNB'),('BTC','Bitcoin','BTC'),('BUSD','Binance USD','BUSD'),('CAKE','PancakeSwap','pannekoek, goed idee voor lunch'),('DOGE','Dogecoin','DOGE'),('DOT','Polkadot','polka klinkt wel lekker pools en gezellig'),('ETH','Ethereum','ETH'),('HEX','HEX','HEX'),('LINK','Chainlink','LINK'),('LTC','Litecoin','LTC'),('LUNA','Terra','LUNA'),('MATIC','Polygon','MATIC'),('SOL','Solana','SOL is een prima zomerbiertje'),('UNI','Uniswap','UNI'),('USDC','USDC','USDC'),('USDT','Tether USD','USDT'),('WBTC','Wrapped BTC','een wrap is altijd lekker'),('XRP','XRP','XRP');
UNLOCK TABLES;

--
-- Dumping data for table `bankaccount`
--

LOCK TABLES bankaccount WRITE;
INSERT INTO bankaccount VALUES ('NL24COKI3309054260',5000000); -- LET OP: dit is het account van de bank
INSERT INTO bankaccount VALUES ('NL69COKI5000000003',1000000),('NL13COKI8974196092',1000),('NL87COKI9583557878',1000),('NL49COKI7517641892',1000);
UNLOCK TABLES;

--
-- Dumping data for table `crypto_currency_rate`
--

LOCK TABLES crypto_currency_rate WRITE;
INSERT INTO crypto_currency_rate VALUES ('ADA','2021-08-25 11:57:00',2.65),('BCH','2021-08-25 11:57:12',639.50),('BNB','2021-08-25 11:57:01',478.97),('BTC','2021-08-25 11:56:53',47638.40),('BUSD','2021-08-25 11:57:11',1.00),('CAKE','2021-08-25 11:57:15',24.44),('DOGE','2021-08-25 11:57:05',0.29),('DOT','2021-08-25 11:57:08',25.28),('ETH','2021-08-25 11:56:58',3130.00),('HEX','2021-08-25 11:57:04',0.18),('LINK','2021-08-25 11:57:14',25.92),('LTC','2021-08-25 11:57:13',173.34),('LUNA','2021-08-25 11:57:07',29.35),('MATIC','2021-08-25 11:57:16',1.46),('SOL','2021-08-25 11:57:10',67.12),('UNI','2021-08-25 11:57:10',26.20),('USDC','2021-08-25 11:57:09',1.00),('USDT','2021-08-25 11:57:02',1.00),('WBTC','2021-08-25 11:57:17',47700.17),('XRP','2021-08-25 11:57:03',1.14);
UNLOCK TABLES;

--
-- Dumping data for table `customer`
--

LOCK TABLES customer WRITE;
INSERT INTO customer VALUES ('sarah','4a31a7bf19a029ac766d1e9486216c579495022c7b12e41509a92ba7e245bc72','b1b63d09','sarah','van Norgaerde','1992-08-22',123456789,'cryptostreet','1234KB',12,'bis','NL69COKI5000000003', 'Vlaardingen', 'sarahjayne@gmail.com');
UNLOCK TABLES;

--
-- Dumping data for table `ownedasset`
--

LOCK TABLES ownedasset WRITE;
INSERT INTO cryptobank.ownedasset (IBAN, abbreviation, aantalEenheden) VALUES ('NL69COKI5000000003', 'ADA', 35);
INSERT INTO cryptobank.ownedasset (IBAN, abbreviation, aantalEenheden) VALUES ('NL69COKI5000000003', 'BCH', 6);
INSERT INTO cryptobank.ownedasset (IBAN, abbreviation, aantalEenheden) VALUES ('NL69COKI5000000003', 'BTC', 2);
INSERT INTO cryptobank.ownedasset (IBAN, abbreviation, aantalEenheden) VALUES ('NL69COKI5000000003', 'DOGE', 200);
INSERT INTO cryptobank.ownedasset (IBAN, abbreviation, aantalEenheden) VALUES ('NL69COKI5000000003', 'ETH', 5);
INSERT INTO cryptobank.ownedasset (IBAN, abbreviation, aantalEenheden) VALUES ('NL13COKI8974196092', 'ETH', 1);
INSERT INTO cryptobank.ownedasset(IBAN, abbreviation, aantalEenheden) VALUES ('NL24COKI3309054260', 'ADA', 4000); -- LET OP: dit is het portfolio van de bank
INSERT INTO cryptobank.ownedasset(IBAN, abbreviation, aantalEenheden) VALUES ('NL24COKI3309054260', 'BCH', 4000);
INSERT INTO cryptobank.ownedasset(IBAN, abbreviation, aantalEenheden) VALUES ('NL24COKI3309054260', 'BTC', 4000);
INSERT INTO cryptobank.ownedasset(IBAN, abbreviation, aantalEenheden) VALUES ('NL24COKI3309054260', 'CAKE', 4000);
INSERT INTO cryptobank.ownedasset(IBAN, abbreviation, aantalEenheden) VALUES ('NL24COKI3309054260', 'DOGE', 4000);
INSERT INTO cryptobank.ownedasset(IBAN, abbreviation, aantalEenheden) VALUES ('NL24COKI3309054260', 'ETH', 4000);
INSERT INTO cryptobank.ownedasset(IBAN, abbreviation, aantalEenheden) VALUES ('NL24COKI3309054260', 'HEX', 4000);

UNLOCK TABLES;

--
-- Dumping data for table `transaction`
--

LOCK TABLES transaction WRITE;
INSERT INTO cryptobank.transaction (transactionId, abbreviation, assetAmount, assetPrice, ibanBuyer, ibanSeller, transactioncost, datetimeprocessed) VALUES (1, 'BTC', 2, 46123.25, 'NL69COKI5000000003', 'NL24COKI3309054260', 5.25, '2021-09-01 10:47:08');
INSERT INTO cryptobank.transaction (transactionId, abbreviation, assetAmount, assetPrice, ibanBuyer, ibanSeller, transactioncost, datetimeprocessed) VALUES (2, 'CAKE', 25, 19.75, 'NL69COKI5000000003', 'NL24COKI3309054260', 8.3, '2021-08-31 09:15:36');
INSERT INTO cryptobank.transaction (transactionId, abbreviation, assetAmount, assetPrice, ibanBuyer, ibanSeller, transactioncost, datetimeprocessed) VALUES (3, 'DOGE', 12500, 0.20, 'NL69COKI5000000003', 'NL24COKI3309054260', 9.95, '2021-09-02 16:47:51');
UNLOCK TABLES;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
INSERT INTO cryptobank.`order` (orderId, abbreviation, assetAmount, desiredPrice, iban, datetimecreated, orderType) VALUES (1, 'ADA', 1234, 2.65, 'NL69COKI5000000003', '2021-09-02 17:07:08', 1);
INSERT INTO cryptobank.`order` (orderId, abbreviation, assetAmount, desiredPrice, iban, datetimecreated, orderType) VALUES (2, 'CAKE', 10, 24.44, 'NL69COKI5000000003', '2021-09-02 17:05:16', 1);
INSERT INTO cryptobank.`order` (orderId, abbreviation, assetAmount, desiredPrice, iban, datetimecreated, orderType) VALUES (3, 'ETH', 1.6, 3130, 'NL69COKI5000000003', '2021-09-08 19:05:22', 1);
INSERT INTO cryptobank.`order` (orderId, abbreviation, assetAmount, desiredPrice, iban, datetimecreated, orderType) VALUES (4, 'ETH', 16, 3130, 'NL13COKI8974196092', '2021-09-09 21:11:12', 1);
UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed
