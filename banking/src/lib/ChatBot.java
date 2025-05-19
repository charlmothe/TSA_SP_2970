package lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChatBot {

    public static String Chatbot(String message) {
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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"; // API key goes here
        String model = "gpt-4o-mini"; // current model of chatgpt api

        try {
            // Create the HTTP POST request
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + apiKey);
            con.setRequestProperty("Content-Type", "application/json");

            // Build the request body
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Get the response
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // returns the extracted contents of the response.
            return extractContentFromResponse(response.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // This method extracts the response expected from chatgpt and returns it.
    public static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content")+11; // Marker for where the content starts.
        int endMarker = response.indexOf("\"", startMarker); // Marker for where the content ends.
        String newline = response.substring(startMarker, endMarker); // this response contains new line literals that need to be removed
        String output = "";
        for(int i = 0; i < newline.length(); i++) {
            if(!(newline.substring(i, i+1).equals("\\n"))) {
            output += newline.substring(i, i + 1);}
        }
        return output;
    }
}