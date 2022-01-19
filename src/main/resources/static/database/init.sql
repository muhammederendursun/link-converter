USE `link_conversion`;
CREATE TABLE IF NOT EXISTS `conversions` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `request` varchar(255) DEFAULT NULL,
  `response` varchar(255) DEFAULT NULL,
  `conversionType` varchar(25) DEFAULT NULL,
  PRIMARY KEY (id)
);