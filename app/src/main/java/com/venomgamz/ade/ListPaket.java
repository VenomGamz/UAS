package com.venomgamz.ade;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

/**
 * Created by VENOM on 1/9/2018.
 */

public class ListPaket extends ArrayAdapter<DTPaketan> {
    private Activity context;
    private List<DTPaketan> paketans;
    DatabaseReference databaseReference;
    TextView nama,nomor;

    public ListPaket(@NonNull Activity context, List<DTPaketan> anggotas, DatabaseReference databaseReference) {
        super(context, R.layout.listpaketan,anggotas);
        this.context=context;
        this.paketans=anggotas;

    }

    public View getView(int pos, View view, ViewGroup parent){
        LayoutInflater inflater=context.getLayoutInflater();
        View listViewItem=inflater.inflate(R.layout.listpaketan,null,true);

        TextView txtname=(TextView)listViewItem.findViewById(R.id.txtpk1);
        TextView txtharga=(TextView)listViewItem.findViewById(R.id.txtpk2);
        TextView txtstok=(TextView)listViewItem.findViewById(R.id.txtpk3);

        final DTPaketan paketan=paketans.get(pos);
        txtname.setText(paketan.getNama());
        txtharga.setText(paketan.getHarga());
        txtstok.setText(paketan.getStok());
        return listViewItem;
    }
}
