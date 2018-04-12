package com.singhster.gaurav.eatit.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.singhster.gaurav.eatit.Interface.ItemOnClickListner;
import com.singhster.gaurav.eatit.R;

/**
 * Created by gaurav on 3/11/2018.
 */

public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView texMenuName;
    public ImageView imageView;
    private ItemOnClickListner itemClickLisner;

    public MenuViewHolder(View itemView) {
        super(itemView);
        texMenuName= (TextView) itemView.findViewById(R.id.menu_name);
        imageView= (ImageView) itemView.findViewById(R.id.menu_image);
        itemView.setOnClickListener(this);
    }
    public  void  setItemClickListner(ItemOnClickListner itemClickListner){
        this.itemClickLisner=itemClickListner;
    }

    @Override
    public void onClick(View v) {
        itemClickLisner.onClick(v,getAdapterPosition(),false);

    }
}
