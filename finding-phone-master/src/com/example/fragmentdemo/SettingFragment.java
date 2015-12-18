package com.example.fragmentdemo;

import java.io.ObjectInputStream.GetField;
import java.sql.SQLData;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListPopupWindow;
import android.widget.TextView;

public class SettingFragment extends Fragment  implements OnTouchListener,
OnItemClickListener {
    private EditText etTest;
    private EditText answer_et;
    private Button btn;
    
    private ListPopupWindow lpw;
    private String [] list;
    private int choosed;
    
    private SQLiteDatabase db;
    private DBopenhelper helper;
    private Cursor c;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			View settingLayout = inflater.inflate(R.layout.setting_layout,
			container, false);
			btn=(Button) settingLayout.findViewById(R.id.button1);
		
			etTest = (EditText) settingLayout.findViewById(R.id.et_test);
	        etTest.setOnTouchListener(this);
	        
	        answer_et=(EditText) settingLayout.findViewById(R.id.editText1);

	        choosed=1;

	        helper =new DBopenhelper(getActivity(),"main_tb", null, 1);
	        db= helper.getWritableDatabase();
	        c=db.query("main_tb",null,null,null,null,null,null);
	        list = new String[c.getCount()]; 
	         
	        Log.i("c.getcount", c.getCount()+"<<<<<<<<<");
	        
	        int count=1;
	        int nameIndex = c.getColumnIndex("question");  
	        
	        c.moveToFirst();
	        list[0]=c.getString(1);
	        while(c.moveToNext()) {
	        	list[count]= c.getString(1);  
	            count++;
	        }
	        
	        
	        lpw = new ListPopupWindow(getActivity().getApplicationContext());
	        lpw.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(),
	                android.R.layout.simple_list_item_1, list));
	        lpw.setAnchorView(etTest);
	        lpw.setModal(true);
	        lpw.setOnItemClickListener(this);
	        
	        
//	        c.close();//关闭结果集  
//	        db.close();//关闭数据库对象  
	btn.setOnClickListener(new OnClickListener() {
//			http://blog.csdn.net/qingdujun/article/details/42651565	
				@Override
				public void onClick(View v) {
//					 ContentValues cv = new ContentValues();    
//				        cv.put("answer" , answer.getText().toString());    
//				        String[] args = {String.valueOf(choosed)};    
//					db.update("main_tb",cv,"photoId=?" ,args);
					
//					String sql = "update main_tb set 'answer'=answer_et.getText().toString() where '_id'=choosed";//修改的SQL语句	
//					db.execSQL(sql);//执行修改
					
					String strFilter = "_id=" + choosed;
					ContentValues args = new ContentValues();
					args.put("answer", answer_et.getText().toString());
					db.update("main_tb", args, strFilter, null);
					
//					db.execSQL("UPDATE db SET answer=answer_et.getText().toString() WHERE _id=choosed");
				}
			});
	        return settingLayout;
	}

	  @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position,
	            long id) {
		  	choosed=position+1;//这里的加一很重要，我用了navicat查看数据库，才解决这个问题
		  
	        String item = list[position];
	        etTest.setText(item);
	        lpw.dismiss();
	        
	        c.moveToFirst();
	        c.move(position);
	        String an=c.getString(2);//2所对应的那一列写的就是答案
	        answer_et.setText(an);
	    }

	  @Override
	    public boolean onTouch(View v, MotionEvent event) {
	        final int DRAWABLE_RIGHT = 2;

	        if (event.getAction() == MotionEvent.ACTION_UP) {
	            if (event.getX() >= (v.getWidth() - ((EditText) v)
	                    .getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
	                lpw.show();
	                return true;
	            }
	        }
	        return false;
	    }

}
