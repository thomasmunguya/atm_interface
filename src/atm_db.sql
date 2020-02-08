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
  `account_number` INT(11) NOT NULL,
  `balance` INT(11) NULL DEFAULT NULL,
  `pin` VARCHAR(4) NULL DEFAULT NULL,
  `holder_nrc_number` VARCHAR(9) NOT NULL,
  PRIMARY KEY (`account_number`, `holder_nrc_number`),
  INDEX `holder_nrc_idx` (`holder_nrc_number` ASC) VISIBLE,
  CONSTRAINT `holder_nrc`
    FOREIGN KEY (`holder_nrc_number`)
    REFERENCES `atm`.`account_holder` (`nrc_number`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `atm`.`contact_information`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`contact_information` (
  `phone_number` INT(10) NULL DEFAULT NULL,
  `email` VARCHAR(45) NULL DEFAULT NULL,
  `nrc_number` VARCHAR(9) NOT NULL,
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
-- Table `atm`.`transaction`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `atm`.`transaction` (
  `transaction_id` VARCHAR(14) NOT NULL,
  `transaction_date` DATE NULL DEFAULT NULL,
  `transaction_type` VARCHAR(45) NULL DEFAULT NULL,
  `login_time` TIME NULL DEFAULT NULL,
  `logout_time` TIME NULL DEFAULT NULL,
  `withdraw_amount` INT(11) NULL DEFAULT NULL,
  `deposit_amount` INT(11) NULL DEFAULT NULL,
  `account_number` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  INDEX `account_number_idx` (`account_number` ASC) VISIBLE,
  CONSTRAINT `account_number`
    FOREIGN KEY (`account_number`)
    REFERENCES `atm`.`account` (`account_number`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
