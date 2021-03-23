package fr.isen.Nicolas_LEBON.ble

import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import fr.isen.Nicolas_LEBON.R
import fr.isen.Nicolas_LEBON.databinding.CellBleDeviceBinding

class BleScanAdapter(private val listBLE: MutableList<BluetoothDevice>,
                     private val clickListener: (BluetoothDevice) -> Unit) : RecyclerView.Adapter<BleScanAdapter.DeviceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BleScanAdapter.DeviceViewHolder {
        val binding = CellBleDeviceBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DeviceViewHolder(binding.root)
    }

    class DeviceViewHolder(binding: ConstraintLayout) : RecyclerView.ViewHolder(binding)
    {
        val titleDevice: TextView = binding.findViewById(R.id.titleDevice)
        val layout = binding.findViewById<View>(R.id.cellDeviceLayout)
    }


    override fun onBindViewHolder(holder: DeviceViewHolder, position: Int) {
        if(listBLE[position].name.toString() == null) {
            holder.titleDevice.text = listBLE[position].toString()
        }
        else
        {
            holder.titleDevice.text = listBLE[position].name.toString()
        }
        holder.layout.setOnClickListener{
            clickListener(listBLE[position])
        }
    }

    override fun getItemCount(): Int {
        return listBLE.size
    }
}



