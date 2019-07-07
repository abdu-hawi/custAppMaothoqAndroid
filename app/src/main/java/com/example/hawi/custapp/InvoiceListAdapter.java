package com.example.hawi.custapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.hawi.custapp.DBFolder.DBManager;
import com.example.hawi.custapp.custassest.InTrash;
import com.example.hawi.custapp.custassest.IsFavor;
import com.example.hawi.custapp.custassest.IsStr;
import com.example.hawi.custapp.custassest.PrintPDF;
import com.example.hawi.custapp.holder.ViewHolderInv;

import java.util.List;

/**
 * Created by Hawi on 11/12/17.
 */

public class InvoiceListAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<InvoiceItems> invoiceItems;
    ImageLoader imageLoader = AppControllerProf.getmInstance().getmImageLoader();
    Context cmt ;

    public InvoiceListAdapter(Activity activity, List<InvoiceItems> invoiceItems) {
        this.activity = activity;
        this.invoiceItems = invoiceItems;
    }

    @Override
    public int getCount() {
        return invoiceItems.size();
    }

    @Override
    public Object getItem(int position) {
        return invoiceItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        if(inflater == null){
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        final ViewHolderInv viHold ;
        final InvoiceItems it;
        if(view == null){
            view = inflater.inflate(R.layout.tst_list_row,null);
            viHold = new ViewHolderInv();
            viHold.imgInv = view.findViewById(R.id.tst_img);
            viHold.comName = view.findViewById(R.id.com_name);
            viHold.braName = view.findViewById(R.id.branch);
            viHold.date = view.findViewById(R.id.inv_date);
            viHold.product = view.findViewById(R.id.item);
            viHold.tot_amt = view.findViewById(R.id.price);
            viHold.isFav = view.findViewById(R.id.img_fav);
            viHold.isStr = view.findViewById(R.id.img_str);
            viHold.inTrash = view.findViewById(R.id.img_del);
            viHold.isPrint = view.findViewById(R.id.img_download);


            view.setTag(viHold);
        }else{
            viHold = (ViewHolderInv) view.getTag();
        }
        if(imageLoader == null){
            imageLoader = AppControllerProf.getmInstance().getmImageLoader();
        }
//////////////////////////////////////////////////////
        it = invoiceItems.get(position);
        viHold.comName.setText(it.getComName());
        viHold.imgInv.setImageUrl(Constant.URL_INV_IMG+it.getImgUrl(),imageLoader);
        viHold.braName.setText(it.getBraName());
        viHold.date.setText(it.getDate());
        viHold.tot_amt.setText(String.valueOf(it.getTot_amt()));
        viHold.product.setText(it.getProduct());

        // instance of DBManager
        final DBManager db = new DBManager(activity.getApplicationContext());
        // Favorite
        if (it.getIsFav() == 1) {
            //imgFav.setBackgroundResource(R.drawable.ic_favorite);
            viHold.isFav.setImageResource(R.drawable.ic_favorite);
        } else {
            viHold.isFav.setBackgroundResource(R.drawable.ic_favorite_border);
        }
        viHold.isFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (it.getIsFav() == 0) {
                    viHold.isFav.setBackgroundResource(0);
                    viHold.isFav.setImageResource(R.drawable.ic_favorite);
                    it.setIsFav(1);
                    new IsFavor(it.getOrdID(), 1);
                    db.updateFav(1,it.getOrdID());
                } else {
                    viHold.isFav.setImageDrawable(null);
                    viHold.isFav.setBackgroundResource(R.drawable.ic_favorite_border);
                    it.setIsFav(0);
                    new IsFavor(it.getOrdID(), 0);
                    db.updateFav(0,it.getOrdID());
                }
            }
        });

        // is Stars
        if (it.getIsStr() == 1) {
            viHold.isStr.setImageResource(R.drawable.ic_star);
        } else {
            viHold.isStr.setImageResource(R.drawable.ic_star_border);
        }

        viHold.isStr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (it.getIsStr() == 1) {
                    viHold.isStr.setImageDrawable(null);
                    viHold.isStr.setImageResource(R.drawable.ic_star_border);
                    it.setIsStr(0);
                    new IsStr(it.getOrdID(), 0);
                    db.updateStr(0,it.getOrdID());
                } else {
                    viHold.isStr.setImageDrawable(null);
                    viHold.isStr.setBackgroundResource(R.drawable.ic_star);
                    it.setIsStr(1);
                    new IsStr(it.getOrdID(), 1);
                    db.updateStr(1,it.getOrdID());
                }
            }
        });

        // inTrash
        viHold.inTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder buil = new AlertDialog.Builder(activity);
                buil.setMessage("Are you sure you want delete")
                        .setIcon(R.drawable.ic_delete)
                        .setTitle("Delete Configration")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.updateTrash(it.getOrdID());
                                invoiceItems.remove(position);
                                new InTrash(it.getOrdID(),1);
                                InvoiceListAdapter.this.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("No" , new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });

        viHold.isPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("pdf","click");
                new PrintPDF(it.getOrdID());
                Log.d("pdf","re click");
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("pp", "" + position + " /in: " + it.getOrdID() + " /inv " + it.getInvNum()+"/ isStr "+it.getIsStr());
            }
        });

        return view;

    }


}
