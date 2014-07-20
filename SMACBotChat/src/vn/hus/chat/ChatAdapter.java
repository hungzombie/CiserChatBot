package vn.hus.chat;

import java.util.ArrayList;

import vn.hus.chat.R;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 
 * @author Hungzombie
 * 
 */
public class ChatAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Message> mMessages;

	public ChatAdapter(Context context, ArrayList<Message> messages) {
		super();
		this.mContext = context;
		this.mMessages = messages;
	}

	@Override
	public int getCount() {
		return mMessages.size();
	}

	@Override
	public Object getItem(int position) {
		return mMessages.get(position);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Message message = (Message) this.getItem(position);

		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.sms_row, parent, false);
			holder.message = (TextView) convertView
					.findViewById(R.id.message_text);
			convertView.setTag(holder);

			/****************************
			 * Lấy kích thước màn hình
			 */
			DisplayMetrics displayMetrics = mContext.getResources()
					.getDisplayMetrics();
			int px = displayMetrics.widthPixels;

			/********
			 * Dat lai kich thuoc cho khung chat
			 */

			holder.message.setMaxWidth(2*px / 3);

		} else
			holder = (ViewHolder) convertView.getTag();

		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.imageView);
		holder.message.setText(message.getMessage());

		if (!message.isMine) {
			imageView.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.nao));
		} else {
			imageView.setImageDrawable(mContext.getResources().getDrawable(
					R.drawable.icon));
		}

		LayoutParams lp = (LayoutParams) convertView
				.findViewById(R.id.chatItem).getLayoutParams();
		// check if it is a status message then remove background, and change
		// text color.

		if (!message.isMine()) {
			holder.message
					.setBackgroundResource(R.drawable.speech_bubble_green);
			lp.gravity = Gravity.RIGHT;

			LinearLayout layout = (LinearLayout) convertView
					.findViewById(R.id.chatItem);
			ImageView tv = (ImageView) layout.findViewById(R.id.imageView);
			layout.removeView(tv);
			layout.addView(tv);

		} else {
			holder.message
					.setBackgroundResource(R.drawable.speech_bubble_orange);
			lp.gravity = Gravity.LEFT;

			LinearLayout layout = (LinearLayout) convertView
					.findViewById(R.id.chatItem);
			TextView tv = (TextView) layout.findViewById(R.id.message_text);
			layout.removeView(tv);
			layout.addView(tv);
		}
		holder.message.setLayoutParams(lp);
		

		return convertView;
	}
	
	

	private static class ViewHolder {
		TextView message;
	}

	@Override
	public long getItemId(int position) {
		// Unimplemented, because we aren't using Sqlite.
		return position;
	}
	
}
