CREATE OR REPLACE PACKAGE BODY FEE.pack_fee_sendback
AS
   PROCEDURE pr_all_feesenback (
      p_i_msisdn      IN       VARCHAR2,                             --�ֻ���
      p_i_bill_date   IN       VARCHAR2,                           --�˵�����
      p_i_isdouble    IN       VARCHAR2,                       --�Ƿ�˫���˷�
      p_i_product     IN       VARCHAR2,                           --��Ʒ����
      p_i_product_v   IN       VARCHAR2,                            --V������
      p_o_result      OUT      VARCHAR2
   )
/******************************************************************************************************************/
--
--  ���ڱ�����վ�˷ѵ����̴���
--
/**********************************************************************************************************************/
   IS
      --ϵͳ��־����
      v_step         VARCHAR2 (10)   := '0';
      v_success      VARCHAR2 (10)   := 'SUCCESS';
      v_failed       VARCHAR2 (10)   := 'FAILED';
      v_result       VARCHAR2 (1000);
      v_user         VARCHAR2 (100);
      v_start_time   VARCHAR2 (100);
      v_end_time     VARCHAR2 (100);
      v_sql          VARCHAR2 (1000);
      v_log_level    VARCHAR2 (100);
      v_proc_name    VARCHAR2 (100);
      --����Ϊ�û��Զ������
      v_msisdn       VARCHAR2 (11);
      v_bill_date    VARCHAR2 (25);
      v_amt          NUMBER (5, 2);
      v_amt_tmp      NUMBER (5, 2);
      v_calls_cnt    INTEGER;
      v_limit_cnt    INTEGER;
       v_scale   INTEGER;
      v_rec_cnt       INTEGER;
      v_isdouble     VARCHAR2 (10);
      v_product      VARCHAR2 (20);                      --ӪҵԱ��ǰ̨ѡ��Ĳ�Ʒ
      v_product_v    VARCHAR2 (20);                   --ӪҵԱ��ǰ̨ѡ���V����ʶ
      v_standard     NUMBER (5, 2);
      v_status       VARCHAR2 (100);
      v_msg         VARCHAR2 (100);
      v_temp        NUMBER;
      v_tuifee      NUMBER(10,2);
      v_scnt        NUMBER;
      --��ѯ������վͨ����¼
      CURSOR cur
      IS
         SELECT gsm.rowid gsm_rowid,
                     gsm.cell_id,                                                --��վ��
                     gsm.call_type,                                      --��������
                     gsm.call_duration,                    --ͨ��ʱ��
                     gsm.other_party,    --�Է�����
                     gsm.cfee                --����ͨ����
           FROM fee_gsmdetail_tp gsm, fee_cell_stations stat
          WHERE stat.ci_dec = gsm.cell_id
            AND gsm.msisdn = v_msisdn
            AND gsm.bill_date = v_bill_date AND gsm.cfee > 0 ORDER BY START_DATE ASC;
   BEGIN
      v_bill_date := p_i_bill_date;                                --�˵�����
      v_msisdn := p_i_msisdn;                                      --�ֻ�����
      v_product := p_i_product;                                  --��Ʒ����ID
      v_product_v := p_i_product_v;                                   --V��ID
      v_isdouble := p_i_isdouble;                          --�Ƿ�˫���˷ѱ�ʶ
      p_o_result := v_success;
      v_start_time := TO_CHAR (SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS');
      v_user := 'FEE';
      v_log_level := 3;
      v_proc_name := 'PR_FEESENBACK';
      v_step := '1';
      v_amt := 0.00;
      v_tuifee := 0.00;
      v_scnt := 1;
     --alter session set nls_date_format='yyyy-mm-dd';
                               
      --ͳ���ܵ�ͨ����¼��
      SELECT COUNT (1)
        INTO v_calls_cnt
        FROM fee_gsmdetail_tp gsm
       WHERE msisdn = v_msisdn AND gsm.bill_date = v_bill_date;

      IF v_isdouble = 'true'                                           --˫���˷�
      THEN
         v_scale :=2;
      ELSE
          v_scale :=1;
      END IF;
      
      v_step := '2';

      --ͳ�Ʊ���ͨ����¼��
        --���û�в�ѯ���굥�������λ�վ���˳�
      SELECT COUNT (1)
        INTO v_limit_cnt
        FROM fee_gsmdetail_tp gsm, fee_cell_stations stat
        WHERE stat.ci_dec = gsm.cell_id
         AND gsm.msisdn = v_msisdn
         AND gsm.bill_date = v_bill_date  AND gsm.cfee > 0;

              IF v_limit_cnt <=0 THEN
                   p_o_result := '{"success":true,"status":-1,"msg":"�ޱ���ͨ����¼","atm":"'||v_amt||'"}';
                    DELETE FROM  fee_gsmdetail_tp  WHERE msisdn = v_msisdn AND bill_date = v_bill_date ;
                    COMMIT;    
                    dbms_output.put_line('�����μ�¼:'||v_rec_cnt);
                    RETURN;
             END IF;
  
            
      v_step := '3';
    
      FOR v_cur IN cur
      
      LOOP
         v_amt_tmp := 0;
         IF v_cur.call_type = '����'
         THEN
            --��������Ϊ����ʱ��������ʲô��Ʒ�������з��ö���
            v_amt := v_amt + v_cur.cfee;
             v_tuifee := v_cur.cfee;
         ELSE
            /**
            * ����Ϊ���д���
            * ���д������ַ�V�������������Ʒ����������
            * V���жϱ�׼��3λ���ϣ�6λ���£���"5��6"��ͷ
            */
            IF     LENGTH (v_cur.other_party) >= 3
               AND LENGTH (v_cur.other_party) <= 6
               AND SUBSTR (v_cur.other_party, 1, 1) = '6'
               AND SUBSTR (v_cur.other_party, 2, 1) <> '0'       --�������V�����й�������V������
            THEN
               IF v_product_v IN ('100001', '100013')
               THEN
                  /**
                  * ��V����ʶΪ100001��100013ʱ������ӪҵԱ��ǰ̨ѡ��Ĳ�Ʒ��Ӧ�ı�׼�˷�
                  */
                  --��ѯӪҵԱѡ��Ĳ�Ʒ��ʶ��Ӧ���ʷѱ�׼
                  SELECT product_standard
                    INTO v_standard
                    FROM fee_product
                   WHERE product_id = v_product;

                  --���ղ�Ʒ��Ӧ���ʼ���õ�Ӧ��������
                  v_amt_tmp := CEIL (v_cur.call_duration / 60) * v_standard;
                  --ʵ���˷ѽ��
                  v_amt := v_amt + (v_cur.cfee - v_amt_tmp);
                  v_tuifee := (v_cur.cfee - v_amt_tmp);
               ELSIF v_cur.other_party IN
                       ('521',
                        '522',
                        '523',
                        '524',
                        '525',
                        '526',
                        '527',
                        '528',
                        '529'
                       )                                         --��ͥV������
               THEN
                  /**
                  * ��ͥV���̶�Ϊ3λ��521~529
                  */
                  v_amt := v_amt + v_cur.cfee;
                   v_tuifee :=  v_cur.cfee;
               ELSE
                  /**
                  * ��V����ʶ��Ϊ100001��100013ʱ������ӪҵԱ��ǰ̨ѡ���V����ʶ��Ӧ�ı�׼�˷�
                  */--��ѯV����ʶ��Ӧ�ķ��ʱ�׼
                    
              SELECT count(0) INTO v_temp   FROM fee_product_v  WHERE v_id = v_product_v;
                if v_temp <> 0 then 
                   SELECT v_fee_standard  INTO v_standard    FROM fee_product_v  WHERE v_id = v_product_v;
                end if; 

                --����V����ʶ��Ӧ���ʼ���õ�Ӧ��������
                  v_amt_tmp := CEIL (v_cur.call_duration / 60) * v_standard;
                  --����ʵ���˷ѽ��
                  v_amt := v_amt + (v_cur.cfee - v_amt_tmp);
                   v_tuifee := (v_cur.cfee - v_amt_tmp);
               END IF;
            ELSE
               /**
               * 
               *������Ʒ����,��V������
               * --��ѯ�ò�Ʒ��ʶ��Ӧ�ķ��ʱ�׼
               */
            BEGIN
               SELECT prd.product_standard
                 INTO v_standard
                 FROM fee_product prd
                WHERE prd.product_id = v_product;
             
      
            EXCEPTION
                    WHEN no_data_found THEN
                    DBMS_OUTPUT.PUT_LINE (' exception no_data_found--'||v_product );
            END;
               --���ղ�Ʒ��Ӧ���ʼ���õ�Ӧ��������
               v_amt_tmp := CEIL (v_cur.call_duration / 60) * v_standard;
               --�õ��˷ѽ��
               v_amt := v_amt + (v_cur.cfee - v_amt_tmp);
                v_tuifee := (v_cur.cfee - v_amt_tmp);
            END IF;
         END IF;
         
            --������
            IF v_tuifee <> 0    THEN           
              
              UPDATE fee_gsmdetail_tp  tp SET TUI_FEE = (v_tuifee*v_scale) WHERE ROWID = v_cur.gsm_rowid;
             v_tuifee := 0;
                             
           END IF;
                      v_scnt := v_scnt+1;       
      END LOOP;
        
      v_step := '4';

      /**
      * true-˫���˷ѣ�false-��
      */
      IF v_isdouble = 'true'                                           --˫���˷�
      THEN
         v_amt := v_amt * 2;
          v_tuifee := v_tuifee * 2;
      END IF;

      v_step := '5';

      IF v_amt > 0 THEN
                 
           SELECT COUNT (1)
        INTO v_limit_cnt
        FROM fee_gsmdetail_tp gsm
       WHERE msisdn = v_msisdn AND gsm.bill_date = v_bill_date AND gsm.tui_fee <> 0;
       
         --status 0: �����ʼ״̬     -1: �޼�¼�����˷�        1: �Ѿ��ɹ��˷�  999:����
        INSERT INTO fee_sendback_his
                  (msisdn, bill_date, opt_user, amt, calls_cnt,
                   limit_cnt, is_double, is_treble,
                   oper_date,
                   oper_time, status,cmt
                  )
           VALUES (v_msisdn, v_bill_date, 'PROC', v_amt, v_calls_cnt,
                   v_limit_cnt, p_i_isdouble, '0',
                   TO_CHAR (SYSTIMESTAMP, 'YYYY-MM-DD'),
                   TO_CHAR (SYSTIMESTAMP, 'HH24:MI:SS'), '0',''
                  );

      COMMIT;
      
      ELSE
            DELETE FROM  fee_gsmdetail_tp  WHERE msisdn = v_msisdn AND bill_date = v_bill_date ;
         COMMIT;
      END IF;
      
      v_step := '6';
      pack_log.pr_log_write (v_proc_name,
                             v_log_level,
                             v_start_time,
                             v_end_time,
                             'SUCCESS',
                             v_step,
                             NULL,
                             NULL,
                             '�˷Ѻϼ�:'||v_amt || '--' || v_calls_cnt || '-->'
                             || '���μ�¼:'||v_limit_cnt
                            );
                            
          p_o_result := '{"success":true,"status":0}';
   EXCEPTION
      WHEN OTHERS
      THEN
         p_o_result := '{"success":false,"status":9999}';
         v_end_time := TO_CHAR (SYSTIMESTAMP, 'YYYY-MM-DD HH24:MI:SS');
         pack_log.pr_log_write (v_proc_name,
                                v_log_level,
                                v_start_time,
                                v_end_time,
                                'FAILED',
                                v_step,
                                NULL,
                                NULL,
                                'ERROR OCCUR:' || SQLERRM
                               );
         raise_application_error (-20001, 'PR_FEESENBACK');
   END pr_all_feesenback;

   FUNCTION seq_no_genertator
      RETURN VARCHAR
   IS
      seqvalue   VARCHAR (1000);
/*****************************************************************************
   NAME:       FIN.SEQ_NO_GENERTATOR
   PURPOSE:

   REVISIONS:
   Ver        Date        Author           Description
   ---------  ----------  ---------------  ------------------------------------
   1.0        2008-8-14          1. Created this function.

   NOTES:

   Automatically available Auto Replace Keywords:
      Object Name:     SEQ_NO_GENERTATOR
      Sysdate:         2008-8-14
      Date and Time:   2008-8-14, ?? 04:27:25, and 2008-8-14 ?? 04:27:25
      Username:         (set in TOAD Options, Procedure Editor)
      Table Name:       (set in the "New PL/SQL Object" dialog)

******************************************************************************/
   BEGIN
      SELECT    TO_CHAR (SYSDATE, 'yyyyMMdd')
             || LPAD (resource_id_seq.NEXTVAL, 9, '0')
        INTO seqvalue
        FROM DUAL;

      RETURN seqvalue;
   END seq_no_genertator;
END pack_fee_sendback;
/
