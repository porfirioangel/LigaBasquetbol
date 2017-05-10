package com.icarus.ligabasquetbol.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Se encarga de subir un archivo de imagen a un servidor de php.
 */
public class ImageUploader {
    private final String url = "http://localhost/upload_image.php";

    public String uploadImage(File imagen) {
        String uploadImage = getStringImage(imagen);
        HashMap<String, String> data = new HashMap<>();
        data.put("filename", imagen.getName());
        data.put("image", uploadImage);
        String response = sendPostRequest(url, createHttpParameters(data));
        System.out.println("uploadImage >>> " + response);
        return response;
    }

    private String getStringImage(File imagen) {
        byte[] fileBytes = readBytes(imagen.getName());
        return Base64.getEncoder().encodeToString(fileBytes);
    }

    private static byte[] readBytes(String filename) {
        File file = new File(filename);
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte fileBytes[] = new byte[(int) file.length()];
            inputStream.read(fileBytes);
            return fileBytes;
        } catch (FileNotFoundException e) {
            System.out.println("File not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the File " + ioe);
        }
        return null;
    }

    public String sendPostRequest(String url, String params) {
        String response = null;
        try {
            URL postUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) postUrl
                    .openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(params);
            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
                response = br.readLine();
            } else {
                response = "Error Registering";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public String createHttpParameters(Map<String, String> parameterMap) {
        try {
            StringBuilder result = new StringBuilder();
            boolean first = true;
            for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
                if (first)
                    first = false;
                else
                    result.append("&");
                result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                result.append("=");
                result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
            }
            return result.toString();
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

}
