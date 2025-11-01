package es.usj.jjhernandez.mastertsa

import android.Manifest
import android.R
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import es.usj.jjhernandez.mastertsa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val view by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var devices: Set<BluetoothDevice>

    private val visibilityRequest =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { }

    private val permissions =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(view.root)
        supportActionBar?.hide()
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        view.btnList.setOnClickListener { list() }
        view.btnOn.setOnClickListener { on() }
        view.btnOff.setOnClickListener { off() }
        view.btnVisible.setOnClickListener { visible() }
    }

    private fun list() {
        devices = bluetoothAdapter?.bondedDevices ?: setOf()
        val list = ArrayList<String>()
        for (bt in devices)
            list.add(bt.name)
        Toast.makeText(applicationContext, "Showing Paired Devices",
            Toast.LENGTH_SHORT).show()
        val adapter = ArrayAdapter(
            this,
            R.layout.simple_list_item_1, list
        )
        view.lv.adapter = adapter
    }
    private fun on() {
        if (!isBluetoothPermissionGranted()) {
            permissions.launch(arrayOf(Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN, Manifest.permission.BLUETOOTH_CONNECT))
        }
    }

    private fun off() {
        bluetoothAdapter?.disable()
        Toast.makeText(applicationContext, "Bluetooth disabled",
            Toast.LENGTH_LONG).show()
    }

    private fun visible() {
        val visibleIntent =
            Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        visibilityRequest.launch(visibleIntent)
    }

    private fun isBluetoothPermissionGranted(): Boolean {
        var granted = false
        val bluetoothGranted =
            checkSelfPermission(Manifest.permission.BLUETOOTH)
        val bluetoothAdminGranted =
            checkSelfPermission(Manifest.permission.BLUETOOTH_ADMIN)
        val bluetoothConnectGranted =
            checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT)
        if (bluetoothGranted == PackageManager.PERMISSION_GRANTED &&
            bluetoothAdminGranted == PackageManager.PERMISSION_GRANTED &&
            bluetoothConnectGranted == PackageManager.PERMISSION_GRANTED) {
            granted = true
        }
        return granted
    }
}