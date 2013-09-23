SELECT 
                     gsm.cell_id,                                                --��վ��
                     gsm.call_type,                                      --��������
                     gsm.call_duration,                    --ͨ��ʱ��
                     gsm.other_party,    --�Է�����
                     CEIL ( call_duration / 60) * 0.1 feee,
                     gsm.cfee                --����ͨ����
           FROM fee_gsmdetail_tp gsm, fee_cell_stations stat
          WHERE stat.ci_dec = gsm.cell_id
            AND gsm.msisdn = '15877372051'
            AND gsm.bill_date = '201108' AND gsm.cfee  = CEIL ( call_duration / 60) * 0.1;