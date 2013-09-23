declare
CURSOR my_cur IS
select * from LOG_PROC_CALL;
begin
FOR per_rec IN my_cur    --可以使用per_rec取每一列的值
loop
 DBMS_OUTPUT.PUT_LINE(per_rec.proc_name|| '----' ||per_rec.log_level);

end loop;
end;


      SELECT  count(1)  into v_rec_cnt             
           FROM fee_gsmdetail_tp gsm, fee_cell_stations stat
          WHERE stat.ci_dec = gsm.cell_id
            AND gsm.msisdn = v_msisdn
            AND gsm.bill_date = v_bill_date;