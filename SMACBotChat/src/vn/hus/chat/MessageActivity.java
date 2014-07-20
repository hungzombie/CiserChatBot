package vn.hus.chat;

import java.util.ArrayList;

import vn.hus.readdata.ReadData;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 
 * @author Hungzombie
 * 
 */
public class MessageActivity extends Activity {
	/** Called when the activity is first created. */

	ArrayList<Message> messages;
	ChatAdapter adapter;
	EditText text;
	static String sender = "CiserBot";
	static String newMessage;
	private ListView listView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		text = (EditText) this.findViewById(R.id.text);

		this.setTitle(sender);
		messages = new ArrayList<Message>();

		listView = (ListView) findViewById(R.id.list);

		adapter = new ChatAdapter(this, messages);
		listView.setAdapter(adapter);

		registerForContextMenu(listView);

	}

	public void sendMessage(View v) {
		newMessage = text.getText().toString().trim();
		if (newMessage.length() > 0) {
			text.setText("");
			addNewMessage(new Message(newMessage, true));
			new SendMessage().execute();
		}
	}

	private class SendMessage extends AsyncTask<Void, String, String> {

		@Override
		protected String doInBackground(Void... params) {
			try {
				Thread.sleep(1000); // simulate a network call
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.publishProgress(String.format("%s đang viết...", sender));
			try {
				Thread.sleep(1000);// simulate a network call
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return ReadData.getAnswer(newMessage);

		}

		@Override
		public void onProgressUpdate(String... v) {

			if (messages.get(messages.size() - 1).isStatusMessage) {
				messages.get(messages.size() - 1).setMessage(v[0]);
				adapter.notifyDataSetChanged();
				listView.setSelection(messages.size() - 1);
			} else {
				addNewMessage(new Message(true, v[0]));
			}
		}

		@Override
		protected void onPostExecute(String text) {
			if (messages.get(messages.size() - 1).isStatusMessage) {
				messages.remove(messages.size() - 1);
			}

			addNewMessage(new Message(text, false));
		}

	}

	void addNewMessage(Message m) {
		messages.add(m);
		adapter.notifyDataSetChanged();
		listView.setSelection(messages.size() - 1);
	}

}