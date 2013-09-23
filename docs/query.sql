SELECT 
                     gsm.cell_id,                                                --基站号
                     gsm.call_type,                                      --呼叫类型
                     gsm.call_duration,                    --通话时长
                     gsm.other_party,    --对方号码
                     CEIL ( call_duration / 60) * 0.1 feee,
                     gsm.cfee                --基本通话费
           FROM fee_gsmdetail_tp gsm, fee_cell_stations stat
          WHERE stat.ci_dec = gsm.cell_id
            AND gsm.msisdn = '15877372051'
            AND gsm.bill_date = '201108' AND gsm.cfee  = CEIL ( call_duration / 60) * 0.1;