package com.example.teacher.atndapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.teacher.atndapp.R;
import com.example.teacher.atndapp.dto.EventDto;
import com.example.teacher.atndapp.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by teacher on 2016/10/08.
 */
public class EventAdapter extends BaseAdapter {

    private Context _context;
    private List<EventDto> _eventLst;
    private LayoutInflater _inflater;

    public EventAdapter(Context context){
        _context = context;
        _eventLst = new ArrayList<>();
        _inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void add(List<EventDto> lst){
        for(EventDto dto : lst){
            _eventLst.add(dto);
        }
        notifyDataSetChanged();
    }

    public void add(EventDto dto){
        _eventLst.add(dto);
        notifyDataSetChanged();
    }

    public void clear(){
        _eventLst.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return _eventLst.size();
    }

    @Override
    public Object getItem(int position) {
        return _eventLst.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            // 初回のインスタンス生成時のみ
            convertView = _inflater.inflate(R.layout.event_item,null);
            viewHolder = new ViewHolder();
            //　インスタンスを取得
            viewHolder.txtTitle =
                    (TextView)convertView.findViewById(R.id.txt_title);
            viewHolder.txtStartedAt =
                    (TextView)convertView.findViewById(R.id.txt_started_at);
            convertView.setTag(viewHolder);
        }else{
            // 初回以外のすでにインスタンスが生成されている場合の処理
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 値設定
        EventDto dto = _eventLst.get(position);
        viewHolder.txtTitle.setText(dto.getTitle());
        viewHolder.txtStartedAt.setText(
                DateUtil.dateToString(dto.getStartAt())
        );

        return convertView;
    }

    private class ViewHolder{
        TextView txtTitle;
        TextView txtStartedAt;
    }
}
