package com.zdb.android.view.pinyin;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.zdb.android.R;

public class SortGroupMemberAdapter extends BaseAdapter implements
		SectionIndexer {
	private List<GroupMemberBean> list = null;
	private Context mContext;

	public SortGroupMemberAdapter(Context mContext, List<GroupMemberBean> list) {
		this.mContext = mContext;
		this.list = list;
	}

	public void updateListView(List<GroupMemberBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View v, ViewGroup arg2) {
		ViewHolder vh = null;
		final GroupMemberBean mContent = list.get(position);
		if (v == null) {
			vh = new ViewHolder();
			v = LayoutInflater.from(mContext).inflate(
					R.layout.activity_group_member_item, null);
			vh.tvTitle = (TextView) v.findViewById(R.id.title);
			vh.tvLetter = (TextView) v.findViewById(R.id.catalog);
			v.setTag(vh);
		} else
			vh = (ViewHolder) v.getTag();
		int section = getSectionForPosition(position);
		if (position == getPositionForSection(section)) {
			vh.tvLetter.setVisibility(View.VISIBLE);
			vh.tvLetter.setText(mContent.getSortLetters());
		} else {
			vh.tvLetter.setVisibility(View.GONE);
		}

		vh.tvTitle.setText(this.list.get(position).getName());
		return v;

	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
	}

	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}