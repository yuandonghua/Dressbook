/*
 * Copyright by Gifted Youngs Workshop and the original author or authors.
 * 
 * This document only allow internal use , any of your behaviors using the file
 * not internal will pay legal responsibility.
 *
 * You may learn more information about Gifted Youngs Workshop from
 * 
 * giftedyoungs@163.com
 * 
 *
 * 此代码由天才少年工作小组开发完成, 仅限内部使用 
 * 外部使用该代码将负相应的法律责任
 * 更多信息请致信天才少年工作小组
 * 
 * giftedyoungs@163.com
 *
 */
package cn.dressbook.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cn.dressbook.ui.R;


/**
 * TODO
 * 
 * @author LiShen
 * @company Gifted Youngs Workshop
 * @date 2015-10-16 上午11:45:04
 * @since
 * @version
 */
@SuppressLint("InflateParams")
public class TopicAdapter extends BaseAdapter {

	/**
	 * TODO
	 */
	private Activity activity;
	/**
	 * TODO 选择了哪个标签
	 */
	private int topicChoice = -1;
	/**
	 * TODO 话题
	 */
	private String[] topics;

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 下午12:22:23
	 */
	public TopicAdapter(Activity activity, String[] topics) {
		this.activity = activity;
		this.topics = topics;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @return
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return topics.length;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public String getItem(int position) {
		return topics[position];
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @param position
	 * @return
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * TODO
	 * 
	 * @author LiShen
	 * @date 2015-10-16 上午11:45:05
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 * @see android.widget.Adapter#getView(int, View,
	 *      ViewGroup)
	 */
	@SuppressWarnings("deprecation")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			convertView = activity.getLayoutInflater().inflate(
					R.layout.item_gridview_topic, null);
			holder.tvItemTopic = (TextView) convertView
					.findViewById(R.id.tvItemTopic);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvItemTopic.setText(topics[position]);
		// 选择哪个标题，哪个标题变色
		if (position == topicChoice) {
			holder.tvItemTopic.setTextColor(activity.getResources().getColor(
					R.color.white));
			holder.tvItemTopic.setBackgroundDrawable(activity.getResources()
					.getDrawable(R.drawable.shape_red_radius_textview));
		} else {
			holder.tvItemTopic.setTextColor(activity.getResources().getColor(
					R.color.main_text_grey));
			holder.tvItemTopic.setBackgroundDrawable(activity.getResources()
					.getDrawable(R.drawable.shape_radius_grey_stroke_textview));
		}
		return convertView;
	}

	class ViewHolder {
		TextView tvItemTopic;
	}

	/**
	 * TODO 返回 topicChoice 的值
	 */
	public int getTopicChoice() {
		return topicChoice;
	}

	/**
	 * TODO 设置 topicChoice 的值
	 */
	public void setTopicChoice(int topicChoice) {
		this.topicChoice = topicChoice;
		notifyDataSetChanged();
	}

	/**
	 * TODO 返回 topics 的值
	 */
	public String[] getTopics() {
		return topics;
	}

	/**
	 * TODO 设置 topics 的值
	 */
	public void setTopics(String[] topics) {
		this.topics = topics;
		notifyDataSetChanged();
	}
}
