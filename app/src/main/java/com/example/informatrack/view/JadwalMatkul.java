package com.example.informatrack.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.database.Cursor;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.informatrack.R;
import com.example.informatrack.model.ApiResponUpload;
import com.example.informatrack.viewmodel.JadwalViewModel;

import java.io.InputStream;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class JadwalMatkul extends AppCompatActivity {

    private static final int PICK_PDF_REQUEST = 1;
    private Uri pdfUri;
    private TextView tvSelectedFile;
    private Button btnUpload, btnSelectPdf;
    private JadwalViewModel jadwalViewModel;
    private String selectedFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal_matkul);

        initComponents();

        actionComponents();

    }

    private void initComponents (){
        btnSelectPdf = findViewById(R.id.btnSelectPdf);
        tvSelectedFile = findViewById(R.id.tvSelectedFile);
        btnUpload = findViewById(R.id.btnUpload);
        jadwalViewModel = new ViewModelProvider(this).get(JadwalViewModel.class);
    }

    private void actionComponents (){
        btnSelectPdf.setOnClickListener(v -> openFileChooser());
        btnUpload.setOnClickListener(v -> {
            if (pdfUri != null) {
                uploadPdf();
            } else {
                Toast.makeText(this, "Pilih file PDF dulu!", Toast.LENGTH_SHORT).show();
            }
        });

        jadwalViewModel.getUploadResponse().observe(this, response -> {
            if (response != null) {
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();

                if ("success".equals(response.getStatus())) {
                    // Setelah upload sukses, jalankan proses PDF
                    jadwalViewModel.processPdf(getFileName(pdfUri));
                }
            }
        });

        jadwalViewModel.getProcessResponse().observe(this, response -> {
            if (response != null) {
                Toast.makeText(this, response.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");
        startActivityForResult(Intent.createChooser(intent, "Pilih PDF"), PICK_PDF_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_PDF_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            pdfUri = data.getData();
            selectedFileName = getFileName(pdfUri);
            tvSelectedFile.setText(selectedFileName);
            btnUpload.setEnabled(true);
        }
    }

    private String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme() != null && uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    private void uploadPdf() {
        try {
            InputStream inputStream = getContentResolver().openInputStream(pdfUri);
            byte[] pdfBytes = new byte[inputStream.available()];
            int bytesRead = inputStream.read(pdfBytes);
            inputStream.close();

            if (bytesRead <= 0) {
                Toast.makeText(this, "File kosong, pilih file lain", Toast.LENGTH_SHORT).show();
                return;
            }

            RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse("application/pdf"), pdfBytes);
            MultipartBody.Part body = MultipartBody.Part.createFormData("pdf_file", selectedFileName, requestBody);
            RequestBody keterangan = RequestBody.create(MultipartBody.FORM, "File Jadwal");

            jadwalViewModel.uploadJadwal(body, keterangan);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error membaca file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}