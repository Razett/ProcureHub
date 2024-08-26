-- ProcureHUB 데이터베이스 구조 내보내기
CREATE DATABASE IF NOT EXISTS `ProcureHUB` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `ProcureHUB`;

-- 트리거 ProcureHUB.insert_into_export 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER insert_into_export
    AFTER INSERT ON prcr
    FOR EACH ROW
BEGIN
    DECLARE exportStatus INT DEFAULT 0;  -- 기본 상태값 설정
    DECLARE exportDueDate DATETIME;

    -- 납기일 계산 (prcr 테이블의 reqdate 값에서 2일 더함)
    SET exportDueDate = DATE_ADD(NEW.reqdate, INTERVAL 2 DAY);

    -- export 테이블에 데이터 삽입
    INSERT INTO export (moddate, regdate, duedate, quantity, status, emp_empno, prcr_prcrno)
    VALUES (NOW(), NOW(), exportDueDate, NEW.quantity, exportStatus, NEW.emp_empno, NEW.prcrno);
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 ProcureHUB.update_into_export 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `update_into_export` AFTER UPDATE ON `prcr` FOR EACH ROW BEGIN
    DECLARE exportDueDate DATETIME;

    -- 납기일 계산 (prcr 테이블의 reqdate 값에서 2일 더함)
    SET exportDueDate = DATE_ADD(NEW.reqdate, INTERVAL 2 DAY);

    -- export 테이블의 데이터를 업데이트
    UPDATE export
    SET
        moddate = NOW(),
        quantity = NEW.quantity
    WHERE prcr_prcrno = NEW.prcrno;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

