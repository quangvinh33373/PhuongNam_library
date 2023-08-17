package com.example.qltv;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qltv.fragmentManager.doanhThu;
import com.example.qltv.fragmentManager.doiMatKhau;
import com.example.qltv.fragmentManager.loaiSach;
import com.example.qltv.fragmentManager.phieuMuon;
import com.example.qltv.fragmentManager.sach;
import com.example.qltv.fragmentManager.thanhVien;
import com.example.qltv.fragmentManager.themNguoiDung;
import com.example.qltv.fragmentManager.themThuThu;
import com.example.qltv.fragmentManager.thongKe;
import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout mdrawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        String fullName = intent.getStringExtra("fullName");

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mdrawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mdrawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        mdrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.navigationView);
        View headView = navigationView.getHeaderView(0);
        TextView txtHeaderUserName =  headView.findViewById(R.id.txtHeaderUserName);
        TextView txtHeaderEmail = headView.findViewById(R.id.txtHeaderEmail);
        txtHeaderUserName.setText(fullName);
        txtHeaderEmail.setText(userName + "@fpt.edu.vn");
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString("userName", "");
        if (!user.equals("Admin")){
            Menu nav_Menu = navigationView.getMenu();
            nav_Menu.findItem(R.id.themNguoiDung).setVisible(false);
            nav_Menu.findItem(R.id.themThuThu).setVisible(false);
        }

        replaceFragment(new phieuMuon());
        navigationView.getMenu().findItem(R.id.phieuMuon).setChecked(true);
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.phieuMuon:
                replaceFragment(new phieuMuon());
                break;
            case R.id.loaiSach:
                replaceFragment(new loaiSach());
                break;
            case R.id.sach:
                replaceFragment(new sach());
                break;
            case R.id.thanhVien:
                replaceFragment(new thanhVien());
                break;
            case R.id.top10:
                replaceFragment(new thongKe());
                break;
            case R.id.doanhThu:
                replaceFragment(new doanhThu());
                break;
            case R.id.doiMatKhau:
                replaceFragment(new doiMatKhau());
                break;
            case R.id.themNguoiDung:
                replaceFragment(new themNguoiDung());
                break;
            case R.id.themThuThu:
                replaceFragment(new themThuThu());
                break;
            case R.id.dangXuat:{
                Toast.makeText(this, "DangXuat", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity2.this, DangNhap.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
                break;
            case R.id.thoat:
                System.exit(0);
        }
        toolbar.setTitle(item.getTitle());
        mdrawerLayout.closeDrawer(navigationView);
        return true;
    }

    public void replaceFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentFrame,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (mdrawerLayout.isDrawerOpen(GravityCompat.START)){
            mdrawerLayout.closeDrawer(GravityCompat.START);
        }   else {
            super.onBackPressed();
        }
    }

}