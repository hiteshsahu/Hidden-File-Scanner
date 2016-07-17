package com.example.nomedia;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<CustomFile> hiddenFile;

	public CustomListAdapter(Activity activity, List<CustomFile> movieItems) {
		this.activity = activity;
		this.hiddenFile = movieItems;
	}

	@Override
	public int getCount() {
		return hiddenFile.size();
	}

	@Override
	public Object getItem(int location) {
		return hiddenFile.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);

		TextView name = (TextView) convertView.findViewById(R.id.title);
		TextView path = (TextView) convertView.findViewById(R.id.path);
		TextView directory = (TextView) convertView.findViewById(R.id.dir);
		TextView isImageFile = (TextView) convertView
				.findViewById(R.id.file_type);

		// getting movie data for the row
		CustomFile m = hiddenFile.get(position);

		// title
		name.setText("Name : " + m.getName());

		if (m.isImageFile()) {
			isImageFile.setTextColor(Color.GREEN);

		} else {
			isImageFile.setTextColor(Color.RED);
		}

		isImageFile.setText("Is ImageFile ? " + m.isImageFile());

		// rating
		path.setText("Path : " + m.getFullPath());

		// genre
		directory.setText("Directory : " + m.getDirectory());

		return convertView;
	}

}