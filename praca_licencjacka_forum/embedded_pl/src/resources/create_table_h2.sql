
-- -----------------------------------------------------
-- Table `users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL UNIQUE,
  `password` VARCHAR(45) NOT NULL,
  `firstname` VARCHAR(45) NULL,
  `lastname` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`user_id`))



-- -----------------------------------------------------
-- Table `topic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `topic` (
  `topic_id` INT NOT NULL AUTO_INCREMENT,
  `author_id` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `last_post` INT NULL,
  `topic_content` TEXT NULL,
  PRIMARY KEY (`topic_id`),
  CONSTRAINT `fk_topic_users1`
    FOREIGN KEY (`author_id`)
    REFERENCES `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)



-- -----------------------------------------------------
-- Table `posts`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `posts` (
  `post_id` INT NOT NULL AUTO_INCREMENT,
  `author_id` INT NOT NULL,
  `topic_id` INT NOT NULL,
  `post_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `text` TEXT NULL,
  PRIMARY KEY (`post_id`),
  CONSTRAINT `fk_posts_users`
    FOREIGN KEY (`author_id`)
    REFERENCES `users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_posts_topic1`
    FOREIGN KEY (`topic_id`)
    REFERENCES `topic` (`topic_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)



-- -----------------------------------------------------
-- Data for table `users`
-- -----------------------------------------------------

INSERT INTO `users` (`user_id`, `login`, `password`, `firstname`, `lastname`, `email`) VALUES (1, 'awojcik', 'awojcik', 'Aleksander', 'Wojcik', 'aleksander.w@1992.wp.pl');
INSERT INTO `users` (`user_id`, `login`, `password`, `firstname`, `lastname`, `email`) VALUES (2, 'stas', 'stas', 'Stanislaw', 'Nowak', 'stas@wp.pl');
INSERT INTO `users` (`user_id`, `login`, `password`, `firstname`, `lastname`, `email`) VALUES (3, 'janek', 'janek', 'Jan', 'Kowalski', 'jan@wp.pl');



-- -----------------------------------------------------
-- Data for table `topic`
-- -----------------------------------------------------

INSERT INTO `topic` (`topic_id`, `author_id`, `title`, `last_post`, `topic_content`) VALUES (1, 1, 'Start', 1, 'Uruchomienie serwera');
INSERT INTO `topic` (`topic_id`, `author_id`, `title`, `last_post`, `topic_content`) VALUES (2, 1, 'Problemy', 2, 'Ewentualne problemy');



-- -----------------------------------------------------
-- Data for table `posts`
-- -----------------------------------------------------

INSERT INTO `posts` (`post_id`, `author_id`, `topic_id`, `post_time`, `text`) VALUES (1, 1, 1, default, 'Uruchomiono serwer. Prosze zapoznac sie z jego dzialaniem.');
INSERT INTO `posts` (`post_id`, `author_id`, `topic_id`, `post_time`, `text`) VALUES (2, 1, 2, default, 'Ewentualne usterki zgloszamy tu.');
