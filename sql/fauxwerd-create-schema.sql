SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `fauxwerd` ;
CREATE SCHEMA IF NOT EXISTS `fauxwerd` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci ;
USE `fauxwerd` ;

-- -----------------------------------------------------
-- Table `fauxwerd`.`user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `fauxwerd`.`user` (
  `user_id` INT NOT NULL ,
  `email` VARCHAR(256) NULL ,
  PRIMARY KEY (`user_id`) ,
  UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) ,
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fauxwerd`.`content`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `fauxwerd`.`content` (
  `content_id` INT NOT NULL ,
  `url` VARCHAR(2000) NOT NULL ,
  PRIMARY KEY (`content_id`) ,
  UNIQUE INDEX `content_id_UNIQUE` (`content_id` ASC) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `fauxwerd`.`user_content`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `fauxwerd`.`user_content` (
  `user_id` INT NOT NULL ,
  `content_id` INT NOT NULL ,
  INDEX `user_id` (`user_id` ASC) ,
  INDEX `content_id` (`content_id` ASC) ,
  CONSTRAINT `user_id`
    FOREIGN KEY (`user_id` )
    REFERENCES `fauxwerd`.`user` (`user_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `content_id`
    FOREIGN KEY (`content_id` )
    REFERENCES `fauxwerd`.`content` (`content_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
