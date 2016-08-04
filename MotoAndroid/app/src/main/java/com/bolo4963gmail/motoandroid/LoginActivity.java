package com.bolo4963gmail.motoandroid;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bolo4963gmail.motoandroid.javaClass.ThisDatabaseHelper;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText address = (EditText) findViewById(R.id.server_address);
        final EditText projectName = (EditText) findViewById(R.id.project_name);
        Button connectButton = (Button) findViewById(R.id.login_connect_button);

        connectButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(address.getText().toString())) {
                    Toast.makeText(LoginActivity.this, "请输入服务器地址", Toast.LENGTH_SHORT).show();
                    return;
                }

                ThisDatabaseHelper dbHelper =
                        new ThisDatabaseHelper(LoginActivity.this, "motoAndroid.db", null, 1,
                                               "AddressName");//获得APP自带数据库
                SQLiteDatabase db = dbHelper.getWritableDatabase();

                db.beginTransaction();//开启事务
                try {
                    db.execSQL("insert into AddressName (address) values(?)",
                               new String[]{address.getText().toString()});

                    Cursor cursor = db.rawQuery("select max(id) from AddressName", null);
                    Integer thisId = cursor.getInt(cursor.getColumnIndex("id")) + 1;

                    ThisDatabaseHelper UserHelper;
                    if (TextUtils.isEmpty(projectName.getText().toString())) {
                        Toast.makeText(LoginActivity.this, "请稍后在设置中输入项目名称", Toast.LENGTH_SHORT)
                                .show();

                        UserHelper = new ThisDatabaseHelper(LoginActivity.this,
                                                            "Database" + thisId.toString() + ".db",
                                                            null, 1, "temp");
                    } else {
                        UserHelper = new ThisDatabaseHelper(LoginActivity.this,
                                                            "Database" + thisId.toString() + ".db",
                                                            null, 1,
                                                            projectName.getText().toString());
                    }
                    UserHelper.getWritableDatabase();//新建此服务器的数据库
                    db.setTransactionSuccessful();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();//结束事务
                }

                LoginWebViewActivity.actionStart(LoginActivity.this, address.getText().toString(),
                                                 projectName.getText().toString(), false);
                finish();
            }
        });

        ImageView loginBack = (ImageView) findViewById(R.id.login_back);
        loginBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}