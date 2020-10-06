/*
 * Copyright (C) 2020 Massimiliano Fiori massimiliano.fiori@me.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package it.baywaylabs.timepickerparticledialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;


import java.util.Calendar;

/**
 * A dialog that prompts the user for the time of day using a {@link TimePicker}.
 */
public class TimePickerParticleDialog extends AlertDialog implements OnClickListener, TimePicker.OnTimeChangedListener {

    public static final String TIME_SEPARATOR = " : ";

    /**
     * The callback interface used to indicate the user is done filling in
     * the time (they clicked on the 'Set' button).
     */
    public interface OnTimeSetListener {

        /**
         * @param view The view associated with this listener.
         * @param hourOfDay The hour that was set.
         * @param minute The minute that was set.
         */
        void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds);
    }

    private static final String HOUR = "hour";
    private static final String MINUTE = "minute";
    private static final String SECONDS = "seconds";
    private static final String IS_24_HOUR = "is24hour";

    private final TimePicker mTimePicker;
    private final OnTimeSetListener mCallback;
    private final Calendar mCalendar;
    private final java.text.DateFormat mDateFormat;

    int mInitialHourOfDay;
    int mInitialMinute;
    int mInitialSeconds;
    boolean mIs24HourView;

    boolean mShowHourOfDay;
    boolean mShowMinute;
    boolean mShowSeconds;

    /**
     * @param context Parent.
     * @param callBack How parent is notified.
     * @param hourOfDay The initial hour.
     * @param minute The initial minute.
     * @param is24HourView Whether this is a 24 hour view, or AM/PM.
     */
    public TimePickerParticleDialog(Context context,
                                    OnTimeSetListener callBack,
                                    int hourOfDay, int minute, int seconds, boolean is24HourView,
                                    boolean showHourOfDay, boolean showMinute, boolean showSeconds) {

        this(context, 0,
                callBack, hourOfDay, minute, seconds, is24HourView,
                showHourOfDay, showMinute, showSeconds);
    }

    /**
     * @param context Parent.
     * @param theme the theme to apply to this dialog
     * @param callBack How parent is notified.
     * @param hourOfDay The initial hour.
     * @param minute The initial minute.
     * @param is24HourView Whether this is a 24 hour view, or AM/PM.
     */
    public TimePickerParticleDialog(Context context,
                                    int theme,
                                    OnTimeSetListener callBack,
                                    int hourOfDay, int minute, int seconds, boolean is24HourView,
                                    boolean showHourOfDay, boolean showMinute, boolean showSeconds) {
        super(context, theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCallback = callBack;
        mInitialHourOfDay = hourOfDay;
        mInitialMinute = minute;
        mInitialSeconds = seconds;
        mIs24HourView = is24HourView;

        mShowHourOfDay = showHourOfDay;
        mShowMinute = showMinute;
        mShowSeconds = showSeconds;

        mDateFormat = DateFormat.getTimeFormat(context);
        mCalendar = Calendar.getInstance();
        updateTitle(mInitialHourOfDay, mInitialMinute, mInitialSeconds);

        setButton(context.getText(R.string.confirm), this);
        setButton2(context.getText(R.string.cancel), (OnClickListener) null);
        //setIcon(android.R.drawable.ic_dialog_time);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_time_picker, null);
        setView(view);
        mTimePicker = (TimePicker) view.findViewById(R.id.timePicker);

        // initialize view
        if (mShowHourOfDay)
        {
            mTimePicker.setHourVisible();
        }
        else {
            mTimePicker.setHourNotVisible();
        }
        if (mShowMinute)
        {
            mTimePicker.setMinuteVisible();
        }
        else {
            mTimePicker.setMinuteNotVisible();
        }
        if (showSeconds)
        {
            mTimePicker.setSecondsVisible();
        }
        else {
            mTimePicker.setSecondsNotVisible();
        }

        // initialize state
        mTimePicker.setCurrentHour(mInitialHourOfDay);
        mTimePicker.setCurrentMinute(mInitialMinute);
        mTimePicker.setCurrentSecond(mInitialSeconds);
        mTimePicker.setIs24HourView(mIs24HourView);
        mTimePicker.setOnTimeChangedListener(this);
    }

    public void onClick(DialogInterface dialog, int which) {
        if (mCallback != null) {
            mTimePicker.clearFocus();
            mCallback.onTimeSet(mTimePicker, mTimePicker.getCurrentHour(),
                    mTimePicker.getCurrentMinute(), mTimePicker.getCurrentSeconds());
        }
    }

    public void onTimeChanged(TimePicker view, int hourOfDay, int minute, int seconds) {
        updateTitle(hourOfDay, minute, seconds);
    }

    public void updateTime(int hourOfDay, int minutOfHour, int seconds) {
        mTimePicker.setCurrentHour(hourOfDay);
        mTimePicker.setCurrentMinute(minutOfHour);
        mTimePicker.setCurrentSecond(seconds);
    }

    private void updateTitle(int hour, int minute, int seconds) {
        String sHour = String.format("%02d", hour);
        String sMin = String.format("%02d", minute);
        String sSec = String.format("%02d", seconds);
        String title = "";
        if (mShowHourOfDay)
        {
            title += sHour + " hh " + TIME_SEPARATOR;
        }
        if (mShowMinute)
        {
            title += sMin + " mm " + TIME_SEPARATOR;
        }
        if (mShowSeconds)
        {
            title += sSec + " ss";
        }

        if (title.endsWith(TIME_SEPARATOR))
        {
            title.substring(0, title.length() - TIME_SEPARATOR.length());
        }
        setTitle(title);
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(HOUR, mTimePicker.getCurrentHour());
        state.putInt(MINUTE, mTimePicker.getCurrentMinute());
        state.putInt(SECONDS, mTimePicker.getCurrentSeconds());
        state.putBoolean(IS_24_HOUR, mTimePicker.is24HourView());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int hour = savedInstanceState.getInt(HOUR);
        int minute = savedInstanceState.getInt(MINUTE);
        int seconds = savedInstanceState.getInt(SECONDS);
        mTimePicker.setCurrentHour(hour);
        mTimePicker.setCurrentMinute(minute);
        mTimePicker.setCurrentSecond(seconds);
        mTimePicker.setIs24HourView(savedInstanceState.getBoolean(IS_24_HOUR));
        mTimePicker.setOnTimeChangedListener(this);
        updateTitle(hour, minute, seconds);
    }


}