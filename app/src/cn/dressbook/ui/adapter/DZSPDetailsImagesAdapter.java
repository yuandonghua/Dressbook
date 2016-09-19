package cn.dressbook.ui.adapter;

import java.util.ArrayList;

import org.xutils.x;
import org.xutils.common.util.LogUtil;
import org.xutils.image.ImageOptions;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import cn.dressbook.app.ManagerUtils;
import cn.dressbook.ui.R;
import cn.dressbook.ui.listener.OnItemClickListener;
import cn.dressbook.ui.listener.OnItemLongClickListener;
import cn.dressbook.ui.model.DZSPDetailsImgs;
import cn.dressbook.ui.model.LSDZFL;

/**
 * @description: 量身定制分类
 * @author:袁东华
 * @time:2015-9-28上午10:22:02
 */
public class DZSPDetailsImagesAdapter extends
		Adapter<DZSPDetailsImagesAdapter.ViewHolder> {
	private OnItemClickListener mOnItemClickListener;
	private OnItemLongClickListener mOnItemLongClickListener;
	private ArrayList<DZSPDetailsImgs> mList;
	private Activity mContext;
	private ImageOptions mImageOptions = ManagerUtils.getInstance()
			.getImageOptions(ImageView.ScaleType.CENTER_CROP);
	private Handler mHandler;

	public DZSPDetailsImagesAdapter(Activity mContext, Handler handler) {
		super();
		this.mContext = mContext;
		mHandler = handler;

	}

	public void setData(ArrayList<DZSPDetailsImgs> mList) {
		this.mList = mList;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0 : mList.size();
	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, int position) {
		// TODO Auto-generated method stub
		setContent(viewHolder, position);

	}

	/**
	 * @description:给条目设置内容
	 * @parameters
	 */
	private void setContent(final ViewHolder viewHolder, final int position) {
		// TODO Auto-generated method
		// stubmLSDZChildAdapter.setBitmapUtils(mBitmapUtils);
		if (mList != null && mList.get(position) != null) {
			x.image().bind(viewHolder.imageview1, mList.get(position).getUrl(),
					mImageOptions);

		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v = LayoutInflater.from(parent.getContext()).inflate(
				R.layout.dzspdetailsimages_item, parent, false);
		ViewHolder vh = new ViewHolder(v, mOnItemClickListener,
				mOnItemLongClickListener);
		return vh;
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
		private ImageView imageview1;

		public ViewHolder(View v, OnItemClickListener itemClick,
				OnItemLongClickListener itemLongClick) {
			super(v);
			// TODO Auto-generated constructor stub
			mOnItemClickListener = itemClick;
			mOnItemLongClickListener = itemLongClick;
			imageview1 = (ImageView) v.findViewById(R.id.imageView1);
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
