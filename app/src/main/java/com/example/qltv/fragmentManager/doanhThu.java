package com.example.qltv.fragmentManager;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qltv.Dao.PhieuMuonDao;
import com.example.qltv.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class doanhThu extends Fragment {

    EditText edtDateStart, edtDateEnd;
    Button btnThongKe;
    TextView txtDateStart, txtDateEnd, tvDoanhThu;
    boolean ipDateStart, isIpDateEnd;
    String dateStart, dateEnd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doanh_thu, container, false);

        edtDateStart = view.findViewById(R.id.edtDateStart);
        edtDateEnd = view.findViewById(R.id.edtDateEnd);
        btnThongKe = view.findViewById(R.id.btnThongKe);
        txtDateStart = view.findViewById(R.id.txtDateStart);
        txtDateEnd = view.findViewById(R.id.txtDateEnd);
        tvDoanhThu = view.findViewById(R.id.tvDoanhThu);

        edtDateStart.setFocusable(false);
        edtDateEnd.setFocusable(false);

        ipDateStart = false;
        isIpDateEnd = false;


        
        edtDateStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(calendar.DAY_OF_MONTH);
                int thang = calendar.get(calendar.MONTH);
                int nam = calendar.get(calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        String date = "";
                        calendar.set(mYear, mMonth, mDay);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        date = simpleDateFormat.format(calendar.getTime());
                        edtDateStart.setText(date);
                        dateStart = edtDateStart.getText().toString();
                        ipDateStart = true;
                    }
                }, nam, thang, ngay);
                datePickerDialog.show();
            }
        });
        
        edtDateEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int ngay = calendar.get(calendar.DAY_OF_MONTH);
                int thang = calendar.get(calendar.MONTH);
                int nam = calendar.get(calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int mYear, int mMonth, int mDay) {
                        calendar.set(mYear, mMonth, mDay);
                        String date = "";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        date = simpleDateFormat.format(calendar.getTime());
                        edtDateEnd.setText(date);
                        dateEnd = date;
                        isIpDateEnd = true;
                    }
                }, nam, thang, ngay);
                datePickerDialog.show();
            }
        }); 
        
        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkDoanhThu = true;
                if (!ipDateStart){
                    checkDoanhThu = false;
                    Toast.makeText(getContext(), "Chọn ngày bắt đầu!", Toast.LENGTH_SHORT).show();
                }
                if (!isIpDateEnd){
                    checkDoanhThu = false;
                    Toast.makeText(getContext(), "Chọn ngày kết thúc!", Toast.LENGTH_SHORT).show();
                }
                if (checkDoanhThu){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date dDateS = null;
                    try {
                        dDateS = simpleDateFormat.parse(dateStart);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date dDateE = null;
                    try {
                        dDateE = simpleDateFormat.parse(dateEnd);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    if (dDateS.before(dDateE)){
                        txtDateStart.setText(dateStart);
                        txtDateEnd.setText(dateEnd);
                        PhieuMuonDao phieuMuonDao = new PhieuMuonDao(getContext());
                        double doanhThu = phieuMuonDao.getDoanhThu(dateStart, dateEnd);
                        String fDoanhThu = String.format("%,.2f", doanhThu);
                        tvDoanhThu.setText(fDoanhThu);
                    }
                    else {
                        Toast.makeText(getContext(), "Ngày bắt đầu phải nhỏ hơn hoặc bằng ngày kết thúc", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return view;
    }
}