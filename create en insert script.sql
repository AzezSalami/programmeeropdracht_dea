CREATE DATABASE  IF NOT EXISTS `spotitube_db`
USE `spotitube_db`;


DROP TABLE IF EXISTS `playlist`;

CREATE TABLE `playlist` (
  `playlistId` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `owner` tinyint NOT NULL,
  `token` varchar(45) NOT NULL,
  PRIMARY KEY (`playlistId`)
);


INSERT INTO `playlist` VALUES (1,'Death metal',1,'1234567'),(2,'Pop',0,'1234567'),(3,'aaafff',1,'1234567'),(4,'zzzzzz',1,'1234567'),(5,'aa',1,'1234567'),(6,'ss',1,'1234567');


DROP TABLE IF EXISTS `track`;

CREATE TABLE `track` (
  `trackId` int NOT NULL,
  `title` varchar(45) NOT NULL,
  `performer` varchar(45) NOT NULL,
  `duration` int NOT NULL,
  `album` varchar(45) DEFAULT NULL,
  `playcount` varchar(45) DEFAULT NULL,
  `publicationDate` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `offlineAvailable` tinyint DEFAULT NULL,
  PRIMARY KEY (`trackId`)
) ;


INSERT INTO `track` VALUES (1,'The cost','The Frames',350,'The cost',NULL,NULL,NULL,0),(2,'Song for someone','The Frames',423,NULL,'37','19-03-2006','Title song from the Album The Cost',1),(3,'Ocean and a rock','Lisa Hannigan',337,'Sea sew',NULL,NULL,NULL,0),(4,'So Long, Marianne','Leonard Cohen',546,'Songs of Leonard Cohen',NULL,NULL,NULL,0),(5,'One','Metallica',423,NULL,'37','18-03-2001','Long version',1);



DROP TABLE IF EXISTS `tracks_in_playlist`;

CREATE TABLE `tracks_in_playlist` (
  `playlistId` int NOT NULL,
  `trackId` varchar(45) NOT NULL,
  PRIMARY KEY (`playlistId`,`trackId`),
  CONSTRAINT `playlist_tracks_in_playlist` FOREIGN KEY (`playlistId`) REFERENCES `playlist` (`playlistId`),
  CONSTRAINT `track_tracks_in_playlist` FOREIGN KEY (`playlistId`) REFERENCES `track` (`trackId`)
);


INSERT INTO `tracks_in_playlist` VALUES (1,'1'),(1,'2');


DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `login_name` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `token` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  PRIMARY KEY (`login_name`)
);

INSERT INTO `users` VALUES ('azez salami','password','1234567','azezsalami');


