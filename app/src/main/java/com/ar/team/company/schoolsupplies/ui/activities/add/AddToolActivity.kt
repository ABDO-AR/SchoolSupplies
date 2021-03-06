package com.ar.team.company.schoolsupplies.ui.activities.add

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.ar.team.company.schoolsupplies.R
import com.ar.team.company.schoolsupplies.control.interfaces.LoadingDialog
import com.ar.team.company.schoolsupplies.control.managers.DatabaseManager
import com.ar.team.company.schoolsupplies.databinding.ActivityAddToolBinding
import com.ar.team.company.schoolsupplies.model.intentions.AddToolIntentions
import com.ar.team.company.schoolsupplies.model.models.Tool
import com.ar.team.company.schoolsupplies.model.states.AddToolViewStates
import com.ar.team.company.schoolsupplies.ui.activities.home.HomeActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream


@AndroidEntryPoint
class AddToolActivity : AppCompatActivity() {
    private lateinit var  loading: LoadingDialog;
    // Fields:
    private val binding: ActivityAddToolBinding by lazy { ActivityAddToolBinding.inflate(layoutInflater) }
    private val model: AddViewModel by viewModels()

    // Spinner:
    private var spinnerText: String = "General Tool"

    // SpinnerAdapter:
    private lateinit var adapter: ArrayAdapter<CharSequence>

    // ImagePicker:
    private lateinit var imagePicker: ActivityResultLauncher<String>
    private var encodedImage: String? = null

    // Companion:
    companion object {
        // Tags:
        const val TAG: String = "AddToolActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Super:
        super.onCreate(savedInstanceState)
        setContentView(binding.root).also { supportActionBar?.hide() }
        // Initializing:
        imagePicker = registerForActivityResult(ActivityResultContracts.GetContent(), ::onPick)
        adapter = ArrayAdapter.createFromResource(this, R.array.type_tool, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item)
        // Developing:
        binding.backButton.setOnClickListener { onBackPressed() }
        binding.toolUploadTextView.setOnClickListener { imagePicker.launch("image/*") }
        binding.toolTypeSpinner.adapter = adapter
        loading = LoadingDialog(this)
        binding.saveButton.setOnClickListener {
            // Submitting:
            submit(binding.toolNameEditText.text.toString(), binding.detailsEditText.text.toString(), spinnerText)
        }
        binding.toolTypeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            // Method(OnItemSelected):
            override fun onItemSelected(arg0: AdapterView<*>, arg1: View?, arg2: Int, arg3: Long) {
                // Updating:
                spinnerText = binding.toolTypeSpinner.selectedItem.toString()
            }

            // Method(OnNothingSelected):
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // Debugging:
                Log.d(TAG, "OnNothingSelected: C")
            }
        }
    }

    // Method(Submit):
    private fun submit(name: String, details: String, type: String) {
        // Checking:
        when {

            // Checking:
            TextUtils.isEmpty(name) -> Snackbar.make(binding.root, getString(R.string.tool_cannot_empty_msg), Snackbar.LENGTH_SHORT).show()
            TextUtils.isEmpty(details) -> Snackbar.make(binding.root, getString(R.string.you_have_detail_tool_msg), Snackbar.LENGTH_SHORT).show()
            TextUtils.isEmpty(type) -> Snackbar.make(binding.root, getString(R.string.specify_type_msg), Snackbar.LENGTH_SHORT).show()
            encodedImage == null -> Snackbar.make(binding.root, getString(R.string.tool_image_msg), Snackbar.LENGTH_SHORT).show()
            // Coroutines:
            else ->
            {
                loading.startLoading()
                lifecycleScope.launchWhenCreated {
                var key =DatabaseManager.toolsDBReference.push().key.toString()
                // Submitting:
                model.toolChannel.send(AddToolIntentions.Upload(Tool(key,key, name, details, type, encodedImage!!, FirebaseAuth.getInstance().currentUser!!.uid, "","","")))
                // Enabling(Progress):
                progressToggle(true)
                // Collecting:
                model.state.collect {
                    // Checking:
                    when (it) {
                        // Singing:
                        is AddToolViewStates.Success -> progressToggle(false).also {
                            val handler = Handler()
                            handler.postDelayed(object :Runnable{
                                override fun run() {
                                    loading.isDismiss()

                                    startActivity(
                                        Intent(
                                            this@AddToolActivity,
                                            HomeActivity::class.java
                                        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                    )

                                }

                            },500)
                        }
                        is AddToolViewStates.Failure -> progressToggle(false).also { Log.d(TAG, "submit: ${getString(R.string.something_wrong_in_upload_msg)}") }
                    }
                }
            }}
        }
    }

    // Method(ProgressToggle):
    private fun progressToggle(visible: Boolean) {
        // Toggling:
        binding.saveButton.visibility = if (visible) View.GONE else View.VISIBLE
        binding.uploadProgress.visibility = if (visible) View.VISIBLE else View.GONE
    }

    // Method(OnPick):
    private fun onPick(uri: Uri?) {
        // Checking:
        if (uri !== null) encodedImage = encode(uri)
    }

    // Method(Encode)
    private fun encode(uri: Uri): String {
        // Initializing:
        @Suppress("DEPRECATION") val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
        val stream = ByteArrayOutputStream()
        // Compressing:
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        // Initializing:
        val bytes: ByteArray = stream.toByteArray()
        // Returning:
        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }
}