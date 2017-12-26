package com.handen.sonofsun;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ControlFragment extends Fragment {

    private EditText ipEditText;

    private DatagramSocket socket = null;
    private ImageButton scanImageButton;
    private TextView statusTextView;
    private Button onButton, offButton;
    private SeekBar dimmerSeekBar;
    private EditText delayEditText;
    private CheckBox autoCheckBox;


    public ControlFragment() {
        // Required empty public constructor
    }

    public static ControlFragment newInstance() {
        ControlFragment fragment = new ControlFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        try {
            //            socket = new DatagramSocket(0x8888);
            socket = new DatagramSocket();
            //            socket.setSoTimeout(1000);
        }
        catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control, container, false);

        delayEditText = view.findViewById(R.id.edit_text_delay);
        if(MainActivity.delay > 0)
            delayEditText.setText(Integer.toString(MainActivity.delay));
        delayEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                MainActivity.delay = Integer.parseInt(s.toString());
                SharedPreferences.getInstance(getContext()).save();
            }
        });

        ipEditText = view.findViewById(R.id.ip_edit_view);
        scanImageButton = view.findViewById(R.id.scan_image_button);
        scanImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendCommand(1, 0, 0, 0);
            }
        });
        statusTextView = view.findViewById(R.id.status_text_view);
        view.findViewById(R.id.button_on).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String delay = delayEditText.getText().toString();
                int d = 0;
                if (delay.length() > 0) d = Integer.parseInt(delay);
                if (d != 0) {
                    sendCommand(0, 1000, 0, d);
                }
                else {
                    sendCommand(0, 0, 0, 0);
                }

            }
        });

        ((SeekBar) view.findViewById(R.id.seek_bar_dimmer)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                System.out.println(progress);
                sendCommand(0, 1000 - progress * 10, 1000 - progress * 10, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        view.findViewById(R.id.button_off).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String delay = delayEditText.getText().toString();
                int d = 0;
                if (delay.length() > 0) d = Integer.parseInt(delay);
                if (d != 0) {
                    sendCommand(0, 0, 1000, d);
                }
                else {
                    sendCommand(0, 1000, 1000, 0);
                }
            }
        });

        autoCheckBox = view.findViewById(R.id.check_box_auto);
        if (MainActivity.isAuto) autoCheckBox.setChecked(true);
        autoCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MainActivity.isAuto = b;
                SharedPreferences.getInstance(getContext()).save();
            }
        });

        System.err.println("ControlFragment is ready");

        return view;
    }

    private void sendCommand(final int scan, final int begin_bright, final int end_bright, final int time) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    InetAddress address = null;
                    final String hostName = getIpAddress();
                    address = InetAddress.getByName(hostName);
                    byte a[] = address.getAddress();
                    a[3] = (byte) 255;
                    address = InetAddress.getByAddress(a);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ipEditText.setText("My IP: " + hostName);
                            statusTextView.setText("");
                        }
                    });

                    DatagramPacket sendPacket, receivePacket;
                    byte[] sendBuf = new byte[7];
                    sendBuf[0] = (byte) scan;
                    for (int i = 1; i < sendBuf.length; i++) {
                        sendBuf[i] = 0;
                    }
                    sendBuf[1] = (byte) (begin_bright & 0xFF);
                    sendBuf[2] = (byte) ((begin_bright & 0xFF00) >> 8);
                    sendBuf[3] = (byte) (end_bright & 0xFF);
                    sendBuf[4] = (byte) ((end_bright & 0xFF00) >> 8);
                    sendBuf[5] = (byte) (time & 0xFF);
                    sendBuf[6] = (byte) ((time & 0xFF00) >> 8);

                    //                    sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, 0x8888);
                    sendPacket = new DatagramPacket(sendBuf, sendBuf.length, address, 0x8888);
                    socket.send(sendPacket);
                    Thread.sleep(50);
                    socket.send(sendPacket);
                    Thread.sleep(50);
                    socket.send(sendPacket);
/*

                    byte[] receiveBuf = new byte[7];
                    for (int i = 0; i < receiveBuf.length; i++) {
                        receiveBuf[i] = 0;
                    }

                    socket.send(sendPacket);
                    Thread.sleep(50);
                    socket.send(sendPacket);
                    Thread.sleep(50);
                    socket.send(sendPacket);
                    for (int i = 0; i < 10; i++) {
                        receivePacket = new DatagramPacket(receiveBuf, receiveBuf.length);
                        socket.receive(receivePacket);
                        String hostName2 = receivePacket.getAddress().toString();
                        if (!hostName2.equals("/" + hostName)) {
                            int pwm = (receiveBuf[1] & 0xFF) + ((int)receiveBuf[2] << 8);
                            final String s = hostName2 + ", PWM: " + Integer.toString(pwm);
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    statusTextView.setText(s);
                                }
                            });
                            break;
                        }Thread.sleep(100);
                    }
                    */
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                //     String myLANIP = addr.getHostAddress();
            }
        });
        thread.start();
    }


    public static String getIpAddress() {
        try {
            for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface networkInterface = (NetworkInterface) en.nextElement();
                for (Enumeration enumIpAddr = networkInterface.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        String ipAddress = inetAddress.getHostAddress();
                        Log.e("IP address", "" + ipAddress);
                        return ipAddress;
                    }
                }
            }
        }
        catch (SocketException ex) {
            Log.e("Socket exception", ex.toString());
        }
        return null;
    }
}
