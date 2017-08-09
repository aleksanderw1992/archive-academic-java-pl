SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

CREATE SCHEMA IF NOT EXISTS `awojforum` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `awojforum` ;

-- -----------------------------------------------------
-- Table `awojforum`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `awojforum`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL UNIQUE,
  `password` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `awojforum`.`topic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `awojforum`.`topic` (
  `topic_id` INT NOT NULL AUTO_INCREMENT,
  `author_id` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `last_post` INT NULL,
  `topic_content` TEXT NULL,
  PRIMARY KEY (`topic_id`),
  INDEX `fk_topic_users1_idx` (`author_id` ASC),
  CONSTRAINT `fk_topic_users1`
    FOREIGN KEY (`author_id`)
    REFERENCES `awojforum`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `awojforum`.`posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `awojforum`.`posts` (
  `post_id` INT NOT NULL AUTO_INCREMENT,
  `author_id` INT NOT NULL,
  `topic_id` INT NOT NULL,
  `post_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `text` TEXT NULL,
  PRIMARY KEY (`post_id`),
  INDEX `fk_posts_users_idx` (`author_id` ASC),
  INDEX `fk_posts_topic1_idx` (`topic_id` ASC),
  CONSTRAINT `fk_posts_users`
    FOREIGN KEY (`author_id`)
    REFERENCES `awojforum`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_posts_topic1`
    FOREIGN KEY (`topic_id`)
    REFERENCES `awojforum`.`topic` (`topic_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Data for table `awojforum`.`users`
-- -----------------------------------------------------
START TRANSACTION;
USE `awojforum`;
INSERT INTO `awojforum`.`users` (`user_id`, `login`, `password`, `firstname`, `lastname`, `email`) VALUES (1, 'awojcik', 'awojcik', 'Aleksander', 'Wojcik', 'aleksander.w@1992.wp.pl');
INSERT INTO `awojforum`.`users` (`user_id`, `login`, `password`, `firstname`, `lastname`, `email`) VALUES (2, 'stas', 'stas', 'Stanislaw', 'Nowak', 'stas@wp.pl');
INSERT INTO `awojforum`.`users` (`user_id`, `login`, `password`, `firstname`, `lastname`, `email`) VALUES (3, 'janek', 'janek', 'Jan', 'Kowalski', 'jan@wp.pl');

COMMIT;


-- -----------------------------------------------------
-- Data for table `awojforum`.`topic`
-- -----------------------------------------------------
START TRANSACTION;
USE `awojforum`;
INSERT INTO `awojforum`.`topic` (`topic_id`, `author_id`, `title`, `last_post`, `topic_content`) VALUES (1, 1, 'Start', 1, 'Uruchomienie serwera');
INSERT INTO `awojforum`.`topic` (`topic_id`, `author_id`, `title`, `last_post`, `topic_content`) VALUES (2, 1, 'Problemy', 2, 'Ewentualne problemy');

COMMIT;


-- -----------------------------------------------------
-- Data for table `awojforum`.`posts`
-- -----------------------------------------------------
START TRANSACTION;
USE `awojforum`;
INSERT INTO `awojforum`.`posts` (`post_id`, `author_id`, `topic_id`, `post_time`, `text`) VALUES (1, 1, 1, default, 'Uruchomiono serwer. Prosze zapoznac sie z jego dzialaniem.');
INSERT INTO `awojforum`.`posts` (`post_id`, `author_id`, `topic_id`, `post_time`, `text`) VALUES (2, 1, 2, default, 'Ewentualne usterki zgloszamy tu.');

COMMIT;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
