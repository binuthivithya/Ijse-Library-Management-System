-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: library_management
-- ------------------------------------------------------
-- Server version	8.0.38

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `book_categories`
--

DROP TABLE IF EXISTS `book_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book_categories` (
  `category_id` varchar(45) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book_categories`
--

LOCK TABLES `book_categories` WRITE;
/*!40000 ALTER TABLE `book_categories` DISABLE KEYS */;
INSERT INTO `book_categories` VALUES ('C-001','JAVA','coding'),('C-002','fiction','fiction stories'),('C-003','Kids','Childrens story'),('C-004','Educational','Related to educational topics'),('C-005','Translations','Translated to sinhala');
/*!40000 ALTER TABLE `book_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books` (
  `id` varchar(45) NOT NULL,
  `title` varchar(100) NOT NULL,
  `author` varchar(100) NOT NULL,
  `isbn` varchar(45) DEFAULT NULL,
  `qoh` varchar(45) NOT NULL,
  `book_categories_category_id` varchar(45) NOT NULL,
  `unit_price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_books_book_categories1_idx` (`book_categories_category_id`),
  CONSTRAINT `fk_books_book_categories1` FOREIGN KEY (`book_categories_category_id`) REFERENCES `book_categories` (`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES ('B-002','Beast Quest- Tagus The Horse Man','ADAM BLADE','978-96-500847-8-0','1','C-003',170),('B-003','Christy','Ganga Niroshani','955-65-228060-1-2','1','C-005',430),('B-004','Hannibal','Thomas Harris','009-29-770197-8-0','1','C-002',950),('B-005','English For Everybody','Cathrine Marshall','978-81-945814-0-6','1','C-004',500),('B-006','Java Fundamentals','Austen','890-78-987654-1-2','3','C-001',900);
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `borrowing`
--

DROP TABLE IF EXISTS `borrowing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrowing` (
  `id` varchar(45) NOT NULL,
  `borrow_date` date NOT NULL,
  `due_date` date NOT NULL,
  `members_id` varchar(45) NOT NULL,
  `books_id` varchar(45) NOT NULL,
  `is_returned` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`,`members_id`,`books_id`),
  KEY `fk_borrowing_members1_idx` (`members_id`),
  KEY `fk_borrowing_books1_idx` (`books_id`),
  CONSTRAINT `fk_borrowing_books1` FOREIGN KEY (`books_id`) REFERENCES `books` (`id`),
  CONSTRAINT `fk_borrowing_members1` FOREIGN KEY (`members_id`) REFERENCES `members` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrowing`
--

LOCK TABLES `borrowing` WRITE;
/*!40000 ALTER TABLE `borrowing` DISABLE KEYS */;
INSERT INTO `borrowing` VALUES ('BR-001','2024-07-31','2024-08-14','M-001 ','B-006 ',0),('BR-002','2024-07-31','2024-08-14','M-001 ','B-002 ',1),('BR-003','2024-07-31','2024-08-14','M-002 ','B-003 ',1),('BR-004','2024-07-31','2024-08-14','M-003 ','B-004 ',0),('BR-005','2024-07-31','2024-08-14','M-003 ','B-006 ',1);
/*!40000 ALTER TABLE `borrowing` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `members`
--

DROP TABLE IF EXISTS `members`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `members` (
  `id` varchar(45) NOT NULL,
  `name` varchar(100) NOT NULL,
  `address` varchar(150) DEFAULT NULL,
  `phone` varchar(12) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `membership_date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `members`
--

LOCK TABLES `members` WRITE;
/*!40000 ALTER TABLE `members` DISABLE KEYS */;
INSERT INTO `members` VALUES ('M-001','farhan hameeth','angoda','','f@gamail.com','2024-07-29'),('M-002','Biyanka Manori','Panadura','0712667534','biy@gmail.com','2024-07-31'),('M-003','Aruna Shantha','Bandaragama','0771800679','k@gmail.com','2024-07-31');
/*!40000 ALTER TABLE `members` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `return`
--

DROP TABLE IF EXISTS `return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `return` (
  `id` varchar(45) NOT NULL,
  `return_date` date NOT NULL,
  `fine` decimal(10,0) NOT NULL,
  `borrowing_id` varchar(45) NOT NULL,
  `borrowing_members_id` varchar(45) NOT NULL,
  `borrowing_books_id` varchar(45) NOT NULL,
  `is_paid` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`,`borrowing_id`,`borrowing_members_id`,`borrowing_books_id`),
  KEY `fk_return_borrowing1_idx` (`borrowing_id`,`borrowing_members_id`,`borrowing_books_id`),
  CONSTRAINT `fk_return_borrowing1` FOREIGN KEY (`borrowing_id`, `borrowing_members_id`, `borrowing_books_id`) REFERENCES `borrowing` (`id`, `members_id`, `books_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `return`
--

LOCK TABLES `return` WRITE;
/*!40000 ALTER TABLE `return` DISABLE KEYS */;
INSERT INTO `return` VALUES ('R-001','2024-07-31',0,'BR-002','M-001 ','B-002 ',0),('R-002','2024-07-31',0,'BR-005','M-003 ','B-006 ',0),('R-003','2024-07-31',0,'BR-003','M-002 ','B-003 ',1);
/*!40000 ALTER TABLE `return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `email` varchar(45) NOT NULL,
  `first_name` varchar(45) NOT NULL,
  `last_name` varchar(45) NOT NULL,
  `password` varchar(1000) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('b@gmail.com','Binu','Thivithya','$2a$10$zDLvMNWH0m1UhoBdMA4cQe5cDihdXaK3yQ3K1fwP8IpdswMplbzKS'),('bb@gmail.com','biyanka','hh','$2a$10$j6xmIVQWDvlkQ6rdwM31u.v6c6jO65cBqjiL2cI4RA8oJ0RIcxvHu'),('bi@gmail.com','biynaka','manori','$2a$10$RqVH3a9X19BlGbYy0mhJ6.wZ/9UytGbMhR5o0tPK518uIyFnzV/Nm'),('k@gmail.com','kal','ed','$2a$10$jYsJroa9TQevJ2XPS1VBzex41DJqZ5h2HhsFu.fxm5t4um6JjqxYu');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-07-31 17:58:05
