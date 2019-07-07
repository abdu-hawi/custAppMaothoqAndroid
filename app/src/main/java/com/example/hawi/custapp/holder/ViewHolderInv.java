package com.example.hawi.custapp.holder;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.hawi.custapp.AppControllerProf;
import com.example.hawi.custapp.InvoiceItems;

/**
 * Created by Hawi on 14/12/17.
 */

public class ViewHolderInv {

    ImageLoader imageLoader = AppControllerProf.getmInstance().getmImageLoader();
    //public int  isStr , isFav , ordID , useID , inv_stat , inTrash , isRead, invNum;

    public NetworkImageView imgInv;
    public TextView comName;
    public TextView date,braName,tot_amt,product;
    public ImageView isFav,isStr,inTrash,isPrint;


//    public void setDataInView(InvoiceItems invIt){
//
//        imgInv.setImageUrl(invIt.getImgUrl(),imageLoader);
//        date.setText(invIt.getDate());
//        comName.setText(invIt.getComName());
//        braName.setText(invIt.getBraName());
//        tot_amt.setText(String.valueOf(invIt.getTot_amt()));
//
//    }
}
