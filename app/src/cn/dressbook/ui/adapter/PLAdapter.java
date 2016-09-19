package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import cn.dressbook.ui.R;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.PL;

/**
 * @description: 品类适配器
 * @author:袁东华
 * @time:2015-10-13上午11:10:24
 */
public class PLAdapter extends Adapter<PLAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private Context mContext;
	private Handler mHandler;
	private String state;
	private ArrayList<PL> list = new ArrayList<PL>();

	public PLAdapter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return list != null ? list.size() : 0;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		// TODO Auto-generated method stub
		setContent(viewHolder, position);
	}

	public ArrayList<PL> getData() {
		return list;
	}

	/**
	 * @description:给条目设置内容
	 * @parameters
	 */
	private void setContent(final ViewHolder viewHolder, final int position) {
		// TODO Auto-generated method stub
		if (list != null && list.size() > position) {
			final PL pl = list.get(position);
			if (pl != null) {
				viewHolder.tv.setText(pl.getName());
				if (pl.getIsSelected() == 0) {
					viewHolder.tv
							.setBackgroundResource(R.drawable.textview_bg_5);
				} else {
					viewHolder.tv
							.setBackgroundResource(R.drawable.textview_bg_6);
				}
//				viewHolder.tv.setOnClickListener(new OnClickListener() {
//
//					@Override
//					public void onClick(View v) {
//						// TODO Auto-generated method stub
//						if (pl.getIsSelected() == 0) {
//							pl.setIsSelected(1);
//							viewHolder.tv
//									.setBackgroundResource(R.drawable.textview_bg_6);
//						} else {
//							pl.setIsSelected(0);
//							viewHolder.tv
//									.setBackgroundResource(R.drawable.textview_bg_5);
//						}
//
//					}
//				});
			}
		} else {
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.pl_item, parent, false);
		// set the view's size, margins, paddings and layout parameters
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
	}


	public void setData(ArrayList<PL> mList) {
		this.list = mList;
	}

	/**
	 * @Description:设置条目点击监听,供外部调用
	 */
	public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
		this.mOnItemClickListener = mOnItemClickListener;

	}

	/**
	 * @Description:设置长按点击监听,供外部调用
	 */
	public void setOnItemLongClickListener(
			OnItemLongClickListener mOnItemLongClickListener) {
		this.mOnItemLongClickListener = mOnItemLongClickListener;

	}

	public class ViewHolder extends RecyclerView.ViewHolder implements
			OnClickListener, OnLongClickListener {
		private OnItemClickListener mOnItemClickListener;
		private OnItemLongClickListener mOnItemLongClickListener;
		private TextView tv;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			tv = (TextView) v.findViewById(R.id.tv);
			v.setOnClickListener(this);
			v.setOnLongClickListener(this);

		}

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			if (mOnItemLongClickListener != null) {
				mOnItemLongClickListener.onItemLongClick(v, getPosition());

			}
			return true;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (mOnItemClickListener != null) {
				mOnItemClickListener.onItemClick(v, getPosition());

			}
		}

	}
}
