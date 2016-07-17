package com.example.nomedia;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MainActivity extends Activity {

	private static final String FILE_TYPE_NO_MEDIA = ".nomedia";
	private CustomListAdapter adapter;
	private ListView listView;
	private TextView scanTime;
	private long startTime, endTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		listView = (ListView) findViewById(R.id.hidden_file_list);
		scanTime = (TextView) findViewById(R.id.time_taken);

		findViewById(R.id.scan_sd_card).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						new FileLoaderTask(MainActivity.this).execute();

					}
				});

	}

	private class FileLoaderTask extends
			AsyncTask<Void, Void, ArrayList<CustomFile>> {

		private Activity appContext;
		private ProgressDialog pDialog;

		public FileLoaderTask(Activity appContext) {
			this.appContext = appContext;
		}

		@Override
		protected void onPreExecute() {

			pDialog = new ProgressDialog(appContext);
			pDialog.setMessage("Scanning Files...");
			pDialog.show();
			scanTime.setText("00");

		}

		@Override
		protected void onPostExecute(ArrayList<CustomFile> result) {

			endTime = System.nanoTime();

			scanTime.setText("Time Taken "
					+ TimeUnit.SECONDS.convert((endTime - startTime),
							TimeUnit.MICROSECONDS) + "  Micro Sec");
			if (pDialog != null) {
				pDialog.dismiss();
				pDialog = null;
			}
			adapter = new CustomListAdapter(appContext, result);
			listView.setAdapter(adapter);
		}

		@Override
		protected ArrayList<CustomFile> doInBackground(Void... params) {
			startTime = System.nanoTime();
			return filterFiles(appContext);
		}
	}

	/**
	 * This function return list of hidden media files
	 * 
	 * @param context
	 * @return list of hidden media files
	 */
	private ArrayList<CustomFile> filterFiles(Context context) {

		ArrayList<CustomFile> listOfHiddenFiles = new ArrayList<CustomFile>();
		String hiddenFilePath;

		// Scan all no Media files
		String nonMediaCondition = MediaStore.Files.FileColumns.MEDIA_TYPE
				+ "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_NONE;

		// Files with name contain .nomedia
		String where = nonMediaCondition + " AND "
				+ MediaStore.Files.FileColumns.TITLE + " LIKE ?";

		String[] params = new String[] { "%" + FILE_TYPE_NO_MEDIA + "%" };

		// make query for non media files with file title contain ".nomedia" as
		// text on External Media URI
		Cursor cursor = context.getContentResolver().query(
				MediaStore.Files.getContentUri("external"),
				new String[] { MediaStore.Files.FileColumns.DATA }, where,
				params, null);

		// No Hidden file found
		if (cursor.getCount() == 0) {

			listOfHiddenFiles.add(new CustomFile("No Hidden File Found",
					"Nothing to show Here", "Nothing to show Here", false));

			// show Nothing Found
			return listOfHiddenFiles;
		}

		// Add Hidden file name, path and directory in file object
		while (cursor.moveToNext()) {
			hiddenFilePath = cursor.getString(cursor
					.getColumnIndex(MediaStore.Files.FileColumns.DATA));
			if (hiddenFilePath != null) {

				listOfHiddenFiles
						.add(new CustomFile(FileUtils
								.getFileName(hiddenFilePath), hiddenFilePath,
								FileUtils.getFileParent(hiddenFilePath),
								isDirHaveImages(FileUtils
										.getFileParent(hiddenFilePath))));
			}
		}

		cursor.close();

		return listOfHiddenFiles;

	}

	/**
	 * 
	 * @param dir
	 *            to serch in
	 * @param fileType
	 *            //pass fileType as a music , video, etc.
	 * @return ArrayList of files of comes under fileType cataegory
	 */
	public boolean isDirHaveImages(String hiddenDirectoryPath) {

		File listFile[] = new File(hiddenDirectoryPath).listFiles();

		boolean dirHaveImages = false;

		if (listFile != null && listFile.length > 0) {
			for (int i = 0; i < listFile.length; i++) {

				if (listFile[i].getName().endsWith(".png")
						|| listFile[i].getName().endsWith(".jpg")
						|| listFile[i].getName().endsWith(".jpeg")
						|| listFile[i].getName().endsWith(".gif")) {

					// Break even if folder have a single image file
					dirHaveImages = true;
					break;

				}
			}
		}
		return dirHaveImages;

	}
}
