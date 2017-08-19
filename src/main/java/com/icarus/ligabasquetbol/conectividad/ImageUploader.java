package com.icarus.ligabasquetbol.conectividad;

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
    private final String url = "http://localhost/liga_basquet/upload_image.php";

    /**
     * Envía la imagen pasada como parámetro al servidor por medio de una
     * petición http.
     *
     * @param imagen Es la imagen que se subirá.
     * @return La respuesta del servidor ante la subida de la imagen.
     */
    public String uploadImage(File imagen) {
        System.out.println(">>> File: " + imagen.getAbsolutePath());
        String uploadImage = getStringImage(imagen);
        HashMap<String, String> data = new HashMap<>();
        data.put("filename", imagen.getName());
        data.put("image", uploadImage);
        String response = sendPostRequest(url, createHttpParameters(data));
        System.out.println("uploadImage >>> " + response);
        return response;
    }

    /**
     * Codifica la imagen como String para enviarla al servidor.
     *
     * @param imagen Es la imagen que se codificará.
     * @return String con el resultado de la codificación.
     */
    private String getStringImage(File imagen) {
        byte[] fileBytes = readBytes(imagen.getName());
        return Base64.getEncoder().encodeToString(fileBytes);
    }

    /**
     * Lee y devuelve los bytes de un archivo en un arreglo.
     *
     * @param filename Es el archivo a leer.
     * @return Arreglo con los bytes del archivo.
     */
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

    /**
     * Envía una petición post a un servidor.
     *
     * @param url    Es la url a donde se enviará la petición.
     * @param params Son los parámetros de la petición.
     * @return String con la respuesta del servidor.
     */
    private String sendPostRequest(String url, String params) {
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

    /**
     * Crea los parámetros de la petición a partir de un mapa.
     *
     * @param parameterMap Es el mapa de donde se obtienen los parámetros.
     * @return String con los parámetros formateados.
     */
    private String createHttpParameters(Map<String, String> parameterMap) {
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
