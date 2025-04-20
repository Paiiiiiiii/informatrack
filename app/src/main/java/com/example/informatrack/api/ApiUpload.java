package com.example.informatrack.api;

import com.example.informatrack.model.ApiResponUpload;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Field;

public interface ApiUpload {

    @Multipart
    @POST("upload_pdf.php")
    Call<ApiResponUpload> uploadPdf(
            @Part MultipartBody.Part pdfFile,
            @Part("keterangan") RequestBody keterangan
    );

    @FormUrlEncoded
    @POST("process_pdf.php")
    Call<ApiResponUpload> processPdf(
            @Field("filename") String filename
    );
}