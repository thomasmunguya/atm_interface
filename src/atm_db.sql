-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema atm
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema atm
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `atm` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `atm` ;

-- -----------------------------------------------------
-- Table `atm`.`branch`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`branch` (
  `id` INT(11) NOT NULL,
  `name` VARCHAR(45) NULL DEFAULT NULL,
  `location` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `atm`.`account_holder`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`account_holder` (
  `nrc_number` VARCHAR(9) NOT NULL,
  `first_name` VARCHAR(20) NULL DEFAULT NULL,
  `middle_name` VARCHAR(20) NULL DEFAULT NULL,
  `last_name` VARCHAR(20) NULL DEFAULT NULL,
  `nationality` VARCHAR(45) NULL DEFAULT NULL,
  `dob` DATE NULL DEFAULT NULL,
  `occupation` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`nrc_number`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `atm`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`account` (
  `number` INT(11) NOT NULL,
  `balance` INT(11) NULL DEFAULT NULL,
  `pin` VARCHAR(4) NULL DEFAULT NULL,
  `branch_id` INT(11) NULL DEFAULT NULL,
  `holder_nrc` VARCHAR(9) NOT NULL,
  PRIMARY KEY (`number`, `holder_nrc`),
  INDEX `branch_id_idx` (`branch_id` ASC) VISIBLE,
  INDEX `holder_nrc_idx` (`holder_nrc` ASC) VISIBLE,
  CONSTRAINT `branch_id`
    FOREIGN KEY (`branch_id`)
    REFERENCES `atm`.`branch` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `holder_nrc`
    FOREIGN KEY (`holder_nrc`)
    REFERENCES `atm`.`account_holder` (`nrc_number`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `atm`.`address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`address` (
  `nrc_number_3` VARCHAR(9) NOT NULL,
  `street_address` VARCHAR(45) NULL DEFAULT NULL,
  `city` VARCHAR(45) NULL DEFAULT NULL,
  `country` VARCHAR(45) NULL DEFAULT NULL,
  `postal_or_zip_code` VARCHAR(10) NULL DEFAULT NULL,
  PRIMARY KEY (`nrc_number_3`),
  CONSTRAINT `nrc_number_3`
    FOREIGN KEY (`nrc_number_3`)
    REFERENCES `atm`.`account_holder` (`nrc_number`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `atm`.`email_address`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`email_address` (
  `nrc_number` VARCHAR(9) NOT NULL,
  `email_address` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`nrc_number`),
  CONSTRAINT `nrc_number`
    FOREIGN KEY (`nrc_number`)
    REFERENCES `atm`.`account_holder` (`nrc_number`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `atm`.`mobile_number`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`mobile_number` (
  `nrc_number` VARCHAR(9) NOT NULL,
  `mobile_number` VARCHAR(45) NULL DEFAULT NULL,
  INDEX `nrc_number_idx` (`nrc_number` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `atm`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`transaction` (
  `id` VARCHAR(14) NOT NULL,
  `date` DATE NULL DEFAULT NULL,
  `type` VARCHAR(45) NULL DEFAULT NULL,
  `login_time` VARCHAR(45) NULL DEFAULT NULL,
  `logout_time` VARCHAR(45) NULL DEFAULT NULL,
  `amount` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
