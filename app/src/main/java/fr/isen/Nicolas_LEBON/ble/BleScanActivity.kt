package fr.isen.Nicolas_LEBON.ble

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import fr.isen.Nicolas_LEBON.R
import fr.isen.Nicolas_LEBON.databinding.ActivityBleBinding

class BleScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBleBinding
    private var isScanning = false
    private var bluetoothAdapter: BluetoothAdapter? = null

    /* ajouté
   private val SCAN_PERIOD: Long = 10000
   private val bluetoothLeScanner: BluetoothLeScanner? =
       bluetoothAdapter?.bluetoothLeScanner //ajouté
   private val handler = Handler()
   private val leDeviceListAdapter = LeDeviceListAdapter()

    */

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialisation du Bluetooth adapter.
        bluetoothAdapter = getSystemService(BluetoothManager::class.java)?.adapter

        startBLEIfPossible()
        isDeviceHasBLESupport()

        binding.bleScanPlayPauseAction.setOnClickListener {
            togglePlayPauseAction()
            isDeviceHasBLESupport()
        }
        binding.bleScanTitle.setOnClickListener() {
            togglePlayPauseAction()
        }

    }

    private fun startBLEIfPossible() {

        when {
            !isDeviceHasBLESupport() || bluetoothAdapter == null -> {
                Toast.makeText(this, "Cet appareil n'est pas compatible, sorry", Toast.LENGTH_SHORT)
                        .show()
            }
            !(bluetoothAdapter?.isEnabled ?: false) -> {
                //je dois activer le bluethooth
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            }
            ActivityCompat.checkSelfPermission
            (
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED -> {
                ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                        REQUEST_PERMISSION_LOCATION
                )
            }
            else -> {
                //youpi, on peut faire du BLE des alpes

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_ENABLE_BT && resultCode == RESULT_OK) {
            startBLEIfPossible()
        }
    }

    private fun isDeviceHasBLESupport(): Boolean {
        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "Cet appareil n'est pas compatible, sorry", Toast.LENGTH_SHORT)
                    .show()
            //finish()
        }
        return packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)
    }

    private fun togglePlayPauseAction() {
        isScanning = !isScanning

        if (isScanning) {
            binding.bleScanTitle.text = getString(R.string.ble_scan_pause_title)
            binding.bleScanPlayPauseAction.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            binding.loadingProgress.isVisible = true
            binding.divider.isVisible = false
        } else {
            binding.bleScanTitle.text = getString(R.string.ble_scan_pause_title)
            binding.bleScanPlayPauseAction.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            binding.loadingProgress.isVisible = false
            binding.divider.isVisible = true
        }
    }

    companion object {
        const val REQUEST_ENABLE_BT = 22
        const val REQUEST_PERMISSION_LOCATION = 22
    }

    // Stops scanning after 10 seconds.

    /*ajouté
    private fun scanLeDevice() {
        bluetoothLeScanner?.let { scanner ->
            if (!isScanning) { // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    isScanning = false
                    scanner.stopScan(leScanCallback)
                }, SCAN_PERIOD)
                isScanning = true
                scanner.startScan(leScanCallback)
            } else {
                isScanning = false
                scanner.stopScan(leScanCallback)
            }
        }
    }

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            leDeviceListAdapter.addDevice(result.device)
            leDeviceListAdapter.notifyDataSetChanged()
        }
    }
     */
}