package ru.fragmentcastle.helpdial;

import ru.fragmentcastle.R;
import ru.fragmentcastle.R.id;
import ru.fragmentcastle.R.layout;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class dialog02 extends DialogFragment implements OnClickListener { 
	

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		      Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.dialog2, null);
		v.findViewById(R.id.button1).setOnClickListener(this);
		return v;
		
}
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
  setStyle(DialogFragment.STYLE_NO_TITLE,0);
    }
	@Override
	public void onClick(View arg0) {
		dismiss();		
	}
}