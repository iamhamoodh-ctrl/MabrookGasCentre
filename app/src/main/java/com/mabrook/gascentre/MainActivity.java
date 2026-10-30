package com.mabrook.gascentre;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.*;

public class MainActivity extends Activity {
  final String[] sizes={"12.5 kg","5.4 kg","3 kg","2 kg"};
  final String[] states={"Gas available","Gas stock finished","Gas arriving soon","Orders closed"};
  final String[] en={"Gas is now available.","Gas stock is currently unavailable. We will inform you when new stock arrives.","New gas stock is on the way and is expected to arrive in a few hours.","Gas orders are now closed. We will inform you when ordering reopens."};
  final String[] ta={"எரிவாயு தற்போது இருப்பில் உள்ளது.","தற்போது எரிவாயு இருப்பில் இல்லை. புதிய இருப்பு வந்ததும் அறிவிக்கப்படும்.","புதிய எரிவாயுத் தொகை வந்து கொண்டிருக்கிறது. இன்னும் சில மணிநேரங்களில் வந்தடையும் என எதிர்பார்க்கப்படுகிறது.","எரிவாயு முன்பதிவுகள் தற்போது நிறுத்தப்பட்டுள்ளன. மீண்டும் தொடங்கும்போது அறிவிப்போம்."};
  final String[] si={"ගෑස් තොග දැන් ලබා ගත හැකිය.","මේ වන විට ගෑස් තොග නොමැත. නව තොග ලැබුණු වහාම දැනුම් දෙන්නෙමු.","නව ගෑස් තොගය පැමිණෙමින් පවතී. පැය කිහිපයකින් ලැබෙනු ඇතැයි අපේක්ෂා කෙරේ.","ගෑස් ඇණවුම් භාර ගැනීම දැනට අවසන් කර ඇත. නැවත ආරම්භ වූ විට දැනුම් දෙන්නෙමු."};
  CheckBox[] checks=new CheckBox[4]; Spinner status; TextView preview;
  int green=Color.rgb(15,113,78);
  @Override public void onCreate(Bundle b){super.onCreate(b);
    ScrollView sc=new ScrollView(this);sc.setFillViewport(true);sc.setBackgroundColor(Color.rgb(246,249,248));
    LinearLayout root=new LinearLayout(this);root.setOrientation(1);root.setPadding(24,32,24,28);sc.addView(root);
    TextView title=text("MABROOK GAS CENTRE",25,true,green);root.addView(title);
    root.addView(text("Ethungahakotuwa  •  076 727 2505",14,false,Color.DKGRAY));
    section(root,"Select gas sizes");
    for(int i=0;i<4;i++){checks[i]=new CheckBox(this);checks[i].setText(sizes[i]);checks[i].setTextSize(17);checks[i].setPadding(8,8,8,8);checks[i].setOnCheckedChangeListener((v,c)->refresh());root.addView(checks[i]);}
    section(root,"Choose announcement");
    status=new Spinner(this);ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,states);status.setAdapter(adapter);root.addView(status);
    section(root,"Message preview (English • Tamil • Sinhala)");
    preview=text("",15,false,Color.rgb(32,48,43));preview.setTextIsSelectable(true);preview.setPadding(16,16,16,16);
    GradientDrawable bg=new GradientDrawable();bg.setColor(Color.WHITE);bg.setCornerRadius(18);preview.setBackground(bg);root.addView(preview);
    status.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener(){public void onNothingSelected(android.widget.AdapterView<?> p){} public void onItemSelected(android.widget.AdapterView<?> p,View v,int pos,long id){refresh();}});
    Button proceed=new Button(this);proceed.setText("PROCEED TO WHATSAPP");proceed.setTextColor(Color.WHITE);proceed.setBackgroundTintList(android.content.res.ColorStateList.valueOf(green));LinearLayout.LayoutParams bp=new LinearLayout.LayoutParams(-1,-2);bp.topMargin=22;root.addView(proceed,bp);
    proceed.setOnClickListener(v->{Intent send=new Intent(Intent.ACTION_SEND);send.setType("text/plain");send.putExtra(Intent.EXTRA_TEXT,message());send.setPackage("com.whatsapp");try{startActivity(send);}catch(Exception e){send.setPackage(null);startActivity(Intent.createChooser(send,"Share gas announcement"));}});
    root.addView(text("Select a WhatsApp chat or group and tap Send. Messages are never sent automatically.",12,false,Color.GRAY));
    setContentView(sc);refresh();
  }
  TextView text(String s,int size,boolean bold,int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);if(bold)t.setTypeface(null,Typeface.BOLD);return t;}
  void section(LinearLayout r,String s){TextView t=text(s,18,true,green);t.setPadding(0,25,0,10);r.addView(t);}
  String message(){int i=status==null?0:status.getSelectedItemPosition();if(i<0)i=0;StringBuilder sel=new StringBuilder();for(int j=0;j<4;j++)if(checks[j].isChecked()){if(sel.length()>0)sel.append(", ");sel.append(sizes[j]);}
    String sizesText=sel.length()>0?"\nGas sizes: "+sel:"";
    return "🔥 MABROOK GAS CENTRE 🔥\n📍 Ethungahakotuwa\n\n"+states[i].toUpperCase()+"\n"+en[i]+sizesText+"\n\n"+ta[i]+"\n\n"+si[i]+"\n\n📞 076 727 2505\nMabrook Gas Centre — Your Trusted Gas Supplier.";
  }
  void refresh(){if(preview!=null)preview.setText(message());}
}
