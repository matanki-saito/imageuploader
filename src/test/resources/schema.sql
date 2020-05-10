SET NAMES utf8 ;

DROP TABLE IF EXISTS `image`;

SET character_set_client = utf8mb4 ;

CREATE TABLE `image` (
  `image_id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `key` VARCHAR(255) NOT NULL,
  `thumbnail_key` VARCHAR(45) NULL,
  `enable` TINYINT(1) NOT NULL DEFAULT 1,
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`image_id`),
  UNIQUE INDEX `key_UNIQUE` (`key` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
