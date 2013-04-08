package gitmad.app.WhereUAt;

/*
 * The application needs to have the permission to write to external storage
 * if the output file is written to the external storage, and also the
 * permission to record audio. These permissions must be set in the
 * application's AndroidManifest.xml file, with something like:
 *
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
 * <uses-permission android:name="android.permission.RECORD_AUDIO" />
 *
 * Based heavily on example from Android's help docs (https://developer.android.com/guide/topics/media/audio-capture.html)
 * 
 */

import gitmad.app.WhereUAt.db.MemoDatabaseHandler;
import gitmad.app.WhereUAt.model.Memo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class RecordMemoFragment extends Fragment {
    private static final String LOG_TAG = RecordMemoFragment.class
            .getSimpleName();
    private static final Object THREE_GPP_FILE_EXTENSION = "3gp";

    private String mBasePath = null;

    private Button mRecordButton = null;
    private MediaRecorder mRecorder = null;
    private boolean mStartRecording = true;

    private Button mPlayButton = null;
    private MediaPlayer mPlayer = null;
    private boolean mStartPlaying = true;

    private EditText mLocationEditView;

    private MemoDatabaseHandler mDbHandler;
    private MemoAdapter mListAdapter;
    private Memo mCurrentMemo;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_record_memo, container, false);

        // grab the external storage directory, so we can hold recorded files
        mBasePath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mLocationEditView = (EditText) v.findViewById(R.id.location_edit);
        //because android insists and we don't want it
        mLocationEditView.clearFocus();

        v.findViewById(R.id.record_button).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        onRecord(mStartRecording);
                        if (mStartRecording) {
                            btn.setText(R.string.button_stop_recording);
                        } else {
                            btn.setText(R.string.button_start_recording);
                        }
                        mStartRecording = !mStartRecording;
                    }

                });
        v.findViewById(R.id.play_button).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        onPlay(mStartPlaying);
                        if (mStartPlaying) {
                            btn.setText(R.string.button_stop_playing);
                        } else {
                            btn.setText(R.string.button_start_playing);
                        }
                        mStartPlaying = !mStartPlaying;
                    }

                });
        mListView = (ListView) v.findViewById(R.id.memo_list);
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                    int position, long id) {
                mCurrentMemo = mListAdapter.select(position);
            }
        });

        mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> listView, View view,
                    int position, long id) {
                mCurrentMemo = mListAdapter.select(position);
                doConfirmDelete();
                return true;
            }
        });
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDbHandler = new MemoDatabaseHandler(this.getActivity());
        List<Memo> memos = mDbHandler.getAllMemos();
        mListAdapter = new MemoAdapter(this.getActivity(), memos);
        mListView.setAdapter(mListAdapter);

    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
            saveRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mCurrentMemo.getFilePath());
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        //clear the current memo and set up a new memo
        clearCurrentMemo();
        mCurrentMemo = new Memo();
        mCurrentMemo.setFilePath(generateFilePath());
        //then prepare and start the media recorder
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mCurrentMemo.getFilePath());
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    /**
     * Save the recording, after confirming with the user and asking for a name
     * 
     */
    private void saveRecording() {
        final String location = mLocationEditView.getEditableText().toString();
        final EditText nameText = new EditText(this.getActivity());
        AlertDialog.Builder dlg = new AlertDialog.Builder(this.getActivity());
        dlg.setCancelable(true)
           .setView(nameText)
           .setMessage(getString(R.string.save_recording, location))
           .setTitle(R.string.save_recording_title)
           .setNegativeButton(android.R.string.no,
                    new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                int which) {
                            doDeleteMemo();
                            dialog.cancel();
                        }
                    })
           .setPositiveButton(android.R.string.yes,
                    new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                int which) {
                            doSaveMemo(nameText.getEditableText()
                                    .toString(), location);
                        }
                    })
           .show();
    }

    /**
     * Called to pop up a delete confirmation dialog, and delete a recording
     * if the user presses yes.
     * 
     */
    private void doConfirmDelete() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this.getActivity());
        dlg.setCancelable(true)
           .setMessage(
                    getString(R.string.delete_recording,
                            mCurrentMemo.getName()))
           .setTitle(R.string.delete_recording_title)
           .setNegativeButton(android.R.string.no,
                    new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                int which) {
                            dialog.cancel();
                        }
                    })
           .setPositiveButton(android.R.string.yes,
                    new Dialog.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog,
                                int which) {
                            doDeleteMemo();
                        }
                    })
           .show();
    }

    /**
     * Called to delete the recording from the file system, the database, and the list
     * 
     */
    protected void doDeleteMemo() {
        new File(mCurrentMemo.getFilePath()).delete();
        mListAdapter.removeMemo(mCurrentMemo);
        if (mCurrentMemo.getId() > 0) {
            mDbHandler.deleteMemo(mCurrentMemo);
        }

        clearCurrentMemo();
    }

    /**
     * Called to save the current memo to the database and list, based
     * on the file name generated in startRecording()
     * 
     * @param name
     * @param location
     */
    protected void doSaveMemo(String name, String location) {
        mCurrentMemo.setName(name);
        mCurrentMemo.setLocation(location);

        mDbHandler.addMemo(mCurrentMemo);
        mListAdapter.addMemo(mCurrentMemo);
        clearCurrentMemo();
    }

    /**
     * Clear the currently selected memo
     */
    private void clearCurrentMemo() {
        mCurrentMemo = null;
        mListAdapter.clearSelection();
    }

    /**
     * Generate a random file name in the base file path, for saving files.
     * 
     * @return
     */
    private String generateFilePath() {
        return String.format("%s/%s.%s", mBasePath, UUID.randomUUID()
                .toString().replaceAll("-", ""), THREE_GPP_FILE_EXTENSION);
    }
}