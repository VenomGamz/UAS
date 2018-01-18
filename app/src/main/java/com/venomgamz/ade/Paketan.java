package com.venomgamz.ade;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Paketan extends AppCompatActivity {

    final Context c=this;
    DatabaseReference databaseReference;
    List<DTPaketan> paketans;
    public static String idpaket;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paketan);
        databaseReference= FirebaseDatabase.getInstance().getReference("Paketan");
        listView=(ListView)findViewById(R.id.listpaketan);
        paketans=new ArrayList<DTPaketan>();



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final DTPaketan paketan=paketans.get(i);
                LayoutInflater layoutInflater=LayoutInflater.from(c);
                final View mView =layoutInflater.inflate(R.layout.detilpaketan,null);
                AlertDialog.Builder alertDialogBuilderUserInput=new AlertDialog.Builder(c);
                alertDialogBuilderUserInput.setView(mView);

                final EditText nama=(EditText) mView.findViewById(R.id.edtuppaketan);
                nama.setText(paketan.getNama());
                final EditText harga=(EditText) mView.findViewById(R.id.edtuphargpk);
                harga.setText(paketan.getHarga());
                final EditText stok=(EditText) mView.findViewById(R.id.edtupstok);
                stok.setText(paketan.getStok());
                Button delete=(Button)mView.findViewById(R.id.btndelete);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.child(paketan.getId()).removeValue();
                        Intent intent=new Intent(getApplicationContext(),Paketan.class);
                        startActivity(intent);
                    }
                });
                alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String t1=nama.getText().toString();
                        String t2=harga.getText().toString();
                        String t3=stok.getText().toString();

                        if(!TextUtils.isEmpty(t1) && !TextUtils.isEmpty(t2) && !TextUtils.isEmpty(t3)) {
                            DatabaseReference Ref= FirebaseDatabase.getInstance().getReference("Paketan").child(paketan.getId());

                            DTPaketan paketan1=new DTPaketan(paketan.getId(),t1,t2,t3);
                            Ref.setValue(paketan1);

                            Toast.makeText(Paketan.this, "Data Terupdate", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                        .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alertDialog=alertDialogBuilderUserInput.create();
                alertDialog.setTitle("Update Data Paketan");
                alertDialog.show();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                paketans.clear();
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    DTPaketan  paketan=postSnapshot.getValue(DTPaketan.class);
                    paketans.add(paketan);
                }
                ListPaket adaptersimpanan=new ListPaket(Paketan.this,paketans,databaseReference);
                listView.setAdapter(adaptersimpanan);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_tambah) {
            LayoutInflater layoutInflater=LayoutInflater.from(c);
            View mView =layoutInflater.inflate(R.layout.inputdata,null);
            AlertDialog.Builder alertDialogBuilderUserInput=new AlertDialog.Builder(c);
            alertDialogBuilderUserInput.setView(mView);

            final EditText dnama=(EditText)mView.findViewById(R.id.edtnmpaketan);
            final EditText dharga=(EditText)mView.findViewById(R.id.edthrgpaketan);
            final EditText dstok=(EditText)mView.findViewById(R.id.edtstock);
            alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("Simpan", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String t1=dnama.getText().toString();
                    String t2=dharga.getText().toString();
                    String t3=dstok.getText().toString();

                    if(!TextUtils.isEmpty(t1) && !TextUtils.isEmpty(t2) && !TextUtils.isEmpty(t3)) {
                        String id = databaseReference.push().getKey();
                        DTPaketan paketan = new DTPaketan(id, t1, t2, t3);
                        databaseReference.child(id).setValue(paketan);
                        Toast.makeText(Paketan.this, "Data Tersimpan", Toast.LENGTH_SHORT).show();
                    }
                }

            }).setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alertDialog=alertDialogBuilderUserInput.create();
            alertDialog.setTitle("Input Data");
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

}
