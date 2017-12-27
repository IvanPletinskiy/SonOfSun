package com.handen.sonofsun;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.ParseException;

import static com.handen.sonofsun.MainActivity.dateFormat;

public class SettingsFragment extends Fragment {

    private EditText maxBright;
    private TextView sunriseBegin, sunriseEnd, sunsetBegin, sunsetEnd;
    private EditText illumination;
    private CheckBox weekend;

    public SettingsFragment() {

    }

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        maxBright = view.findViewById(R.id.max_bright_edit_text);
        if (MainActivity.maxPower > 0) maxBright.setText(Integer.toString(MainActivity.maxPower));
        maxBright.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.maxPower = Integer.parseInt(s.toString());
                SharedPreferences.getInstance(getContext()).save();
            }
        });

        illumination = view.findViewById(R.id.illumination_edit_text);
        if (MainActivity.illuminationTime > 0) {
            illumination.setText(Integer.toString(MainActivity.illuminationTime));
        }
        illumination.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.illuminationTime = Integer.parseInt(s.toString());
                SharedPreferences.getInstance(getContext()).save();
            }
        });

        weekend = view.findViewById(R.id.weekend_check_box);
        weekend.setChecked(MainActivity.isWeekend);
        weekend.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                MainActivity.isWeekend = isChecked;
                SharedPreferences.getInstance(getContext()).save();
            }
        });


        sunriseBegin = view.findViewById(R.id.sunrise_begin);
        sunriseBegin.setText(dateFormat.format(MainActivity.sunriseBegin));
        sunriseBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        try {
                            MainActivity.sunriseBegin = dateFormat.parse(Integer.toString(selectedHour) + "." + Integer.toString(selectedMinute));
                            sunriseBegin.setText(dateFormat.format(MainActivity.sunriseBegin));
                            SharedPreferences.getInstance(getContext()).save();
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, MainActivity.sunriseBegin.getHours(), MainActivity.sunriseEnd.getMinutes(), true);
                timePickerDialog.show();
            }
        });

        sunriseEnd = view.findViewById(R.id.sunrise_end);
        sunriseEnd.setText(dateFormat.format(MainActivity.sunriseEnd));
        sunriseEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        try {
                            MainActivity.sunriseEnd = dateFormat.parse(Integer.toString(selectedHour) + "." + Integer.toString(selectedMinute));
                            sunriseEnd.setText(dateFormat.format(MainActivity.sunriseEnd));
                            SharedPreferences.getInstance(getContext()).save();
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, MainActivity.sunriseEnd.getHours(), MainActivity.sunriseEnd.getMinutes(), true);
                timePickerDialog.show();
            }
        });

        sunsetBegin = view.findViewById(R.id.sunset_begin);
        sunsetBegin.setText(dateFormat.format(MainActivity.sunsetBegin));
        sunsetBegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        try {
                            MainActivity.sunsetBegin = dateFormat.parse(Integer.toString(selectedHour) + "." + Integer.toString(selectedMinute));
                            sunsetBegin.setText(dateFormat.format(MainActivity.sunsetBegin));
                            SharedPreferences.getInstance(getContext()).save();
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                }, MainActivity.sunsetBegin.getHours(), MainActivity.sunriseEnd.getMinutes(), true);
                timePickerDialog.show();
            }
        });

        sunsetEnd = view.findViewById(R.id.sunset_end);
        sunsetEnd.setText(dateFormat.format(MainActivity.sunsetEnd));
        sunsetEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        try {
                            MainActivity.sunsetEnd = dateFormat.parse(Integer.toString(selectedHour) + "." + Integer.toString(selectedMinute));
                            sunsetEnd.setText(dateFormat.format(MainActivity.sunsetEnd));
                            SharedPreferences.getInstance(getContext()).save();
                        }
                        catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }, MainActivity.sunsetEnd.getHours(), MainActivity.sunriseEnd.getMinutes(), true);
                timePickerDialog.show();
            }
        });


        return view;
    }
}
