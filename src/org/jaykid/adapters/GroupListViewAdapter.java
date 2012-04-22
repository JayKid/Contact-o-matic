package org.jaykid.adapters;

import java.util.ArrayList;

import org.jaykid.classes.Group;
import org.jaykid.contactomatic.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupListViewAdapter extends ArrayAdapter<Group>
{
    private ArrayList<Group> groups;
    private LayoutInflater inflater;

    public GroupListViewAdapter(Context context, int textViewResourceId, ArrayList<Group> groups) 
    {
        super(context, textViewResourceId, groups);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groups = groups;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	ViewHolder viewHolder;
        Group group = groups.get(position);
        if (convertView == null)
        {
        	viewHolder = new ViewHolder();
    		convertView = inflater.inflate(R.layout.group_row, null);
    		viewHolder.name = (TextView) convertView.findViewById(R.id.groupName);
    		viewHolder.photo = (ImageView) convertView.findViewById(R.id.groupPhoto);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.name.setText(group.getName());
        viewHolder.photo.setImageBitmap(group.getPhoto());
        return convertView;

    }

	private class ViewHolder
	{
		public TextView name;
		public ImageView photo;
	}
}