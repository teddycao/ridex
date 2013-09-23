CREATE OR REPLACE PROCEDURE "PR_FEESENBACK" (P_I_MSISDN IN VARCHAR2,P_I_BILL_DATE IN VARCHAR2,P_I_isdouble IN VARCHAR2,P_I_is_homecard IN VARCHAR2, P_O_RESULT OUT VARCHAR)
/******************************************************************************************************************/
--
--
--
/**********************************************************************************************************************/
   AS
      --系统日志变量
      V_STEP        VARCHAR2 (10) := '0';
      V_SUCCESS   VARCHAR2 (10) := 'SUCCESS';
      V_FAILED    VARCHAR2 (10) := 'FAILED';
      V_RESULT    VARCHAR2(1000);
      V_USER    VARCHAR2(100);
      V_START_TIME  VARCHAR2(100);
      V_END_TIME  VARCHAR2(100);
      V_SQL      VARCHAR2(1000);
      V_LOG_LEVEL      VARCHAR2(100);
      V_PROC_NAME      VARCHAR2(100);
      
      --一下为用户自定义变量
      V_MSISDN  VARCHAR2(11);
      V_BILL_DATE  VARCHAR2(25);
      V_AMT   NUMBER(5,2);
      V_CALLS_CNT integer;
      V_LIMIT_CNT integer;

   BEGIN

      V_BILL_DATE := P_I_BILL_DATE;
      V_MSISDN  := P_I_MSISDN;
      
      P_O_RESULT := V_SUCCESS;
      V_START_TIME :=TO_CHAR (SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS');
      V_USER :='FEE';
      V_LOG_LEVEL := 1;
      V_PROC_NAME := 'PR_FEESENBACK';
      
      
      V_STEP := '1';
      

       select sum(gsm.lfee) AMT  into  V_AMT from FEE_GSMDETAIL_TP gsm, FEE_CELL_STATIONS stat
       		where stat.CI_DEC = gsm.CELL_ID and gsm.MSISDN = V_MSISDN and gsm.BILL_DATE = V_BILL_DATE;
      
       select count(1) into V_CALLS_CNT  from FEE_GSMDETAIL_TP gsm  where  MSISDN = V_MSISDN and gsm.BILL_DATE = V_BILL_DATE;
       
       --统计边漫通话记录数
       SELECT count(gsm.lfee) into V_LIMIT_CNT FROM fee_gsmdetail_tp gsm, fee_cell_stations stat WHERE 
            stat.ci_dec = gsm.cell_id AND gsm.msisdn = V_MSISDN AND gsm.bill_date =  V_BILL_DATE and gsm.lfee > 0;
                  
       INSERT INTO FEE_SENDBACK_HIS ( MSISDN, BILL_DATE, OPT_USER, AMT, CALLS_CNT, LIMIT_CNT, IS_DOUBLE, IS_TREBLE, OPER_DATE, OPER_TIME, CMT ) 
		 								 VALUES (V_MSISDN, V_BILL_DATE, 'PROC', V_AMT,V_CALLS_CNT, V_LIMIT_CNT, P_I_isdouble, '0', TO_CHAR (SYSTIMESTAMP, 'YYYY-MM-DD'), TO_CHAR (SYSTIMESTAMP, 'HH24:MI:SS'), '');
    
                                       
       COMMIT;                                 
     PACK_LOG.PR_LOG_WRITE (V_PROC_NAME,V_LOG_LEVEL,V_START_TIME,V_END_TIME,'SUCCESS',
                                 V_STEP,NULL,NULL,V_AMT||'--'||V_CALLS_CNT||'-->'||V_LIMIT_CNT
                                );
                                
                                
      V_STEP :='2';



   EXCEPTION
      WHEN OTHERS
      THEN
         P_O_RESULT := V_FAILED;
         V_END_TIME :=TO_CHAR (SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS');
         PACK_LOG.PR_LOG_WRITE (V_PROC_NAME,V_LOG_LEVEL,V_START_TIME,V_END_TIME,'FAILED',
                                 V_STEP,NULL,NULL,
                                 'ERROR OCCUR:' || SQLERRM
                                );
         RAISE_APPLICATION_ERROR (-20001, 'PR_FEESENBACK');
   END PR_FEESENBACK;
/
