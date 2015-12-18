package com.example.fragmentdemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBopenhelper extends SQLiteOpenHelper {

	public DBopenhelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	public DBopenhelper(Context context, String name) {
		super(context, name, null, 0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		 db.execSQL("create table if not exists main_tb(_id integer primary key autoincrement,question text not null,answer text not null)");
		 db.execSQL("insert into main_tb (question,answer)values('your high school is ?','qibao')");
		 db.execSQL("insert into main_tb (question,answer)values('your college is ?','jiaotong')");
		 db.execSQL("insert into main_tb (question,answer)values('your primary school is ?','zhongxue')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
