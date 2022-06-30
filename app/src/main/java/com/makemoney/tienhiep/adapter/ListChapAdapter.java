package com.makemoney.tienhiep.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.makemoney.tienhiep.model.Chapter;
import com.makemoney.tienhiep.R;

import java.util.ArrayList;

public class ListChapAdapter extends BaseAdapter {
    final private ArrayList<Chapter> listChapter;

    public ListChapAdapter(ArrayList<Chapter> listChapter) {
        this.listChapter = listChapter;
    }

    @Override
    public int getCount() {
        return listChapter.size();
    }

    @Override
    public Object getItem(int position) {
        return listChapter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listChapter.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View viewChapter;
        if (convertView == null) {
            viewChapter = View.inflate(parent.getContext(), R.layout.fragment_listchapter, null);
        } else viewChapter = convertView;

        Chapter chapter = (Chapter) listChapter.get(position);
        ((TextView) viewChapter.findViewById(R.id.title_chapter)).setText(chapter.getTitle());
        return viewChapter;
    }
}
