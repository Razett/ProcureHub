BEGIN
    DECLARE exportStatus INT DEFAULT 0;  -- 기본 상태값 설정
    DECLARE exportDueDate DATETIME;

    -- 납기일 계산 (prcr 테이블의 reqdate 값에서 2일 더함)
    SET exportDueDate = DATE_ADD(NEW.reqdate, INTERVAL 2 DAY);

    -- export 테이블에 데이터 삽입
INSERT INTO export (moddate, regdate, duedate, quantity, status, emp_empno, prcr_prcrno)
VALUES (NOW(), NOW(), exportDueDate, NEW.quantity, exportStatus, NEW.emp_empno, NEW.prcrno);
END

---------------------------

BEGIN
    DECLARE exportDueDate DATETIME;

    -- 납기일 계산 (prcr 테이블의 reqdate 값에서 2일 더함)
    SET exportDueDate = DATE_ADD(NEW.reqdate, INTERVAL 2 DAY);

    -- export 테이블의 데이터를 업데이트
UPDATE export
SET
    moddate = NOW(),
    quantity = NEW.quantity
WHERE prcr_prcrno = NEW.prcrno;
END