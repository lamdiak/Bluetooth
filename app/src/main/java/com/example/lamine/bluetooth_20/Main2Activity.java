package com.example.lamine.bluetooth_20;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ebanx.swipebtn.OnStateChangeListener;
import com.ebanx.swipebtn.SwipeButton;



import java.util.ArrayList;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import io.ghyeok.stickyswitch.widget.StickySwitch;

import static android.Manifest.permission.SEND_SMS;

public class Main2Activity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemClickListener {

    // Déclaration d'une variable d'incrémantation
    private final static int REQUEST_CODE_ENABLE_BLUETOOTH = 0;
    private static final int REQUEST_SMS = 0;
    BluetoothAdapter blueAdapter;
    private static final String active = "ACTIVÉ";
    private static final String active1 = "DÉSACTIVÉ";
    private Set<BluetoothDevice> devices ;
    TextView t1,t2;
    private static final String Tag = "Coucou";
    Switch s1;
    String name,adresse;
    Timer timer;

    //ReceiverBluetooth receiverBluetooth;
    private ProgressDialog mProgressDlg;

    ListView myList,List2;
    ArrayList<String> StringArrayList= new ArrayList<String>();
    ArrayAdapter<String> mArrayAdapter;
    ArrayAdapter<String> mArrayAdapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //DATABASE
      /*  timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                // code ici !!!!!!!!!!!
                showToast("Timer écouler");
                finish();
            }
        },9000);*/

        s1 = (Switch)findViewById(R.id.switch2);
        s1.setEnabled(false);
        s1.setOnCheckedChangeListener(this);
        SwipeButton swipeButton = (SwipeButton)findViewById(R.id.swipe_btn);
        swipeButton.setOnStateChangeListener(new OnStateChangeListener() {
            // Activation/ Desactivation du Bluetooth
            @Override
            public void onStateChange(boolean active) {
                //Toast.makeText(Main2Activity.this,"Bluetooth "+active,Toast.LENGTH_SHORT).show();
                if (active==true){
                    // Pop up pour activer le bluetooth
                    if (!blueAdapter.isEnabled()) {
                        Intent activeBluetooth = new Intent(blueAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(activeBluetooth, REQUEST_CODE_ENABLE_BLUETOOTH);
                        s1.setEnabled(true);
                    }
                }else {
                    blueAdapter.disable();
                    Toast.makeText(getApplicationContext(),"" +
                            "Bluetooth désactivé",Toast.LENGTH_LONG).show();
                    s1.setEnabled(false);
                }
            }
        });

        myList = (ListView) findViewById(R.id.List1);
        List2= (ListView) findViewById(R.id.List2);
        t1 = (TextView) findViewById(R.id.text1);
        t2 = (TextView) findViewById(R.id.text2);


        mProgressDlg = new ProgressDialog(this);
        mProgressDlg.setMessage("Recherche d'appareil...");
        mProgressDlg.setCancelable(false);
        mProgressDlg.setButton(DialogInterface.BUTTON_NEGATIVE, "Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //Vérification de la présence du Bluetooth sur le terminal
        blueAdapter = BluetoothAdapter.getDefaultAdapter();
        if (blueAdapter == null) {
            // Le terminal ne possède pas de Bluetooth
            Toast.makeText(Main2Activity.this, "Ce périphérique ne possède pas" +
                    "de bluetooth", Toast.LENGTH_LONG).show();
        }

        /*******************************ENVOI DE MESSAGE************************************************/
      /*  if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            int hasSMSPermission = checkSelfPermission(SEND_SMS);
            if (hasSMSPermission != PackageManager.PERMISSION_GRANTED) {
                if (!shouldShowRequestPermissionRationale(SEND_SMS)) {
                    showMessageOKCancel("You need to allow access to Send SMS",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[] {SEND_SMS},
                                                REQUEST_SMS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[] {SEND_SMS},
                        REQUEST_SMS);
                return;
            }
            // Envoi de message
            // les actions a réalisées
            SmsManager smsManager = SmsManager.getDefault();
            String destinataire = "0660875958";
            String message = "Alert!!!!!!! Danger !!!!!!!!";
            smsManager.sendTextMessage(destinataire, null, message, null, null);*/
           // finish();

        /*****************************************************************************/



       // final TextView textView = (TextView)findViewById(R.id.active_desac);
        //final StickySwitch stickySwitch = (StickySwitch)findViewById(R.id.stick_switch);

      /*  if(stickySwitch.getRightText()==active){
            textView.setText("ACTIVÉ");
            }
        }else if (stickySwitch.getLeftText()==active1){
            textView.setText("DÉSACTIVÉ");
        }
        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String s) {
                Toast.makeText(Main2Activity.this,"Bluetooth "+s,Toast.LENGTH_SHORT).show();
            }
        });*/

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_STARTED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(myReceiver, filter);
        mArrayAdapter2 = new ArrayAdapter<>(this, R.layout.row,R.id.text1,StringArrayList);
        List2.setAdapter(mArrayAdapter2);
        List2.setOnItemClickListener(this);
    }


    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Visibilité du Bluetooth par les autres appareils durant 300 secondes
        /*    Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
            startActivity(discoverableIntent);*/
    public void ListPaired() {
        /* La méthode getBondedDevices()
         *retourne la liste des périphériques connus :
         */
        devices = blueAdapter.getBondedDevices();
        if (devices == null || devices.size() == 0 || !blueAdapter.isEnabled()) {
            //showToast("Aucun périphériques associés trouvé");
            t1.setText("Aucun périphériques associés  trouvé !!!!!");
            t1.setTextColor(Color.BLACK);
        }
            // On affiche cette liste dans le cas contraire
        else{
            String[] liste = new String[devices.size()];
            int index = 0;

            //  ArrayList<BluetoothDevice> list = new ArrayList<>();
            // list.addAll(devices);

            if (devices.size() > 0) {
                for (BluetoothDevice blueDevice : devices) {
                    liste[index] = blueDevice.getName();
                    index++;
                }
                mArrayAdapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,liste);
                myList.setAdapter(mArrayAdapter);
            }

        }
    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {


        @Override
        public void onReceive(Context context, Intent intent) {

            final String action = intent.getAction();
            // showToast("OnReceive : ACTION FOUND.");

            if (BluetoothAdapter.ACTION_DISCOVERY_STARTED.equals(action)) {
              //  StringArrayList = new ArrayList<String>();
                mProgressDlg.show();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                mProgressDlg.dismiss();


            }

           else if (action.equals(BluetoothDevice.ACTION_FOUND))
            {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                showToast("Appareil trouvé :    " + device.getName());
                Log.i(Tag, "Ma_liste_de_Bluetooth :  " + device.getName() + " \n " + device.getAddress());
                StringArrayList.add(device.getName() +"  " + device.getAddress());
                mArrayAdapter2.notifyDataSetChanged();
                 name = device.getName();
                 adresse = device.getAddress();


            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode!=REQUEST_CODE_ENABLE_BLUETOOTH) {
            return;
        }
        if (resultCode==RESULT_OK)

            Toast.makeText(getApplicationContext(),"" +
                    "Bluetooth activé",Toast.LENGTH_LONG).show();
        else{
            Toast.makeText(getApplicationContext(),"" +
                    "Bluetooth non activé",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //mHeadingReference.addValueEventListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy()

    {
        super.onDestroy();
        blueAdapter.disable();
        unregisterReceiver(myReceiver);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (!blueAdapter.isEnabled()){
            s1.setEnabled(false);
        }
        else {

            s1.setEnabled(true);
            if(s1.isChecked()){
                StringArrayList.clear();
                mArrayAdapter2.notifyDataSetChanged();
                s1.setText("Annuler");
                s1.setEnabled(true);
                ListPaired();
                blueAdapter.startDiscovery(); // Permet de lancer la recherche des périphériques à proximités


            }else{
                blueAdapter.cancelDiscovery();
                s1.setText("Analyser");
            }
        }

    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, BaseActivity.class);
        intent.putExtra("sanae", name);
        startActivity(intent);
        Toast.makeText(this, "vous avez cliqué "+name+"  "+adresse, Toast.LENGTH_SHORT).show();
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(Main2Activity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

}
