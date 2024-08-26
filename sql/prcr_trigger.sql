-- 트리거 ProcureHUB.insert_into_prcr 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `insert_into_prcr` AFTER INSERT ON `prdc_plan` FOR EACH ROW BEGIN
    DECLARE materialId BIGINT;
    DECLARE empId BIGINT;
    DECLARE prcrQuantity BIGINT;
    DECLARE prcrReqdate DATETIME;
    DECLARE prcrStatus INT;
    DECLARE prcrModdate DATETIME;
    DECLARE prcrRegdate DATETIME;
    DECLARE mtrlQuantity BIGINT; -- 추가된 변수 선언
    DECLARE done INT DEFAULT 0;

    -- Cursor 선언
    DECLARE cur CURSOR FOR
        SELECT material.mtrlno, prdc_mtrl.quantity
        FROM prdc_mtrl
                 LEFT JOIN material ON prdc_mtrl.material_mtrlno = material.mtrlno
        WHERE prdc_mtrl.prdc_prdcno = NEW.prdc_prdcno;

    -- Cursor가 더 이상 데이터를 찾지 못했을 때 처리
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO materialId, mtrlQuantity;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- 필요한 다른 필드들 설정
        SET prcrQuantity = NEW.quantity * mtrlQuantity;
        SET prcrReqdate = DATE_SUB(NEW.startdate, INTERVAL 2 DAY);  -- 납기일은 시작일에서 2일 뺀 날짜로 설정
        SET prcrStatus = 1;  -- 기본 상태값 설정
        SET prcrModdate = NOW();  -- 현재 시간으로 설정
        SET prcrRegdate = NOW();  -- 현재 시간으로 설정
        SET empId = 201723058;  -- 예시로 사원 번호를 설정

        -- prcr 테이블에 데이터 삽입
        INSERT INTO prcr (moddate, regdate, quantity, reqdate, status, emp_empno, material_mtrlno, prdc_plan_prdc_plan_no)
        VALUES (prcrModdate, prcrRegdate, prcrQuantity, prcrReqdate, prcrStatus, empId, materialId, NEW.prdc_plan_no);
    END LOOP;

    CLOSE cur;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- 트리거 ProcureHUB.update_into_prcr 구조 내보내기
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `update_into_prcr` AFTER UPDATE ON `prdc_plan` FOR EACH ROW BEGIN
    DECLARE materialId BIGINT;
    DECLARE empId BIGINT;
    DECLARE prcrQuantity BIGINT;
    DECLARE prcrReqdate DATETIME;
    DECLARE prcrStatus INT;
    DECLARE prcrModdate DATETIME;
    DECLARE prcrRegdate DATETIME;
    DECLARE mtrlQuantity BIGINT; -- 추가된 변수 선언
    DECLARE done INT DEFAULT 0;

    -- Cursor 선언
    DECLARE cur CURSOR FOR
        SELECT material.mtrlno, prdc_mtrl.quantity
        FROM prdc_mtrl
                 LEFT JOIN material ON prdc_mtrl.material_mtrlno = material.mtrlno
        WHERE prdc_mtrl.prdc_prdcno = NEW.prdc_prdcno;

    -- Cursor가 더 이상 데이터를 찾지 못했을 때 처리
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;

    OPEN cur;

    read_loop: LOOP
        FETCH cur INTO materialId, mtrlQuantity;
        IF done THEN
            LEAVE read_loop;
        END IF;

        -- 필요한 다른 필드들 설정
        SET prcrQuantity = NEW.quantity * mtrlQuantity;  -- 새 생산계획 수량과 자재의 필요 수량을 곱함
        SET prcrReqdate = DATE_SUB(NEW.startdate, INTERVAL 2 DAY);  -- 납기일은 시작일에서 2일 뺀 날짜로 설정
        SET prcrStatus = 1;  -- 기본 상태값 설정
        SET prcrModdate = NOW();  -- 현재 시간으로 설정
        SET prcrRegdate = NOW();  -- 현재 시간으로 설정
        SET empId = 201723058;  -- 예시로 사원 번호를 설정

        -- prcr 테이블의 기존 데이터 업데이트
        UPDATE prcr
        SET
            moddate = prcrModdate,
            quantity = prcrQuantity,
            reqdate = prcrReqdate,
            status = prcrStatus,
            emp_empno = empId,
            material_mtrlno = materialId
        WHERE prdc_plan_prdc_plan_no = NEW.prdc_plan_no AND material_mtrlno = materialId;
    END LOOP;

    CLOSE cur;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;