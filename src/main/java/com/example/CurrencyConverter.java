package com.example;

import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverter extends JFrame {

    // UI components
    private JComboBox<String> fromCurrency;
    private JComboBox<String> toCurrency;
    private JTextField amountField;
    private JButton convertButton;
    private JLabel resultLabel;

    // List of currency codes (you can add more as needed)
    private static final String[] CURRENCIES = {
        "USD", "EUR", "GBP", "INR", "JPY", "AUD", "CAD", "CHF", "CNY", "SEK"
    };

    // Your APILayer API key
    private static final String API_KEY = "VWZq1zOymvIkGM28pnItND1GqaTmCKhp";

    public CurrencyConverter() {
        setTitle("Real-Time Currency Converter");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        // Set up layout constraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Amount Label and Field
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Amount:"), gbc);

        amountField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(amountField, gbc);

        // From Currency Label and ComboBox
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("From:"), gbc);

        fromCurrency = new JComboBox<>(CURRENCIES);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(fromCurrency, gbc);

        // To Currency Label and ComboBox
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("To:"), gbc);

        toCurrency = new JComboBox<>(CURRENCIES);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(toCurrency, gbc);

        // Convert Button
        convertButton = new JButton("Convert");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        add(convertButton, gbc);

        // Result Label
        resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        add(resultLabel, gbc);

        // Button action listener
        convertButton.addActionListener((ActionEvent e) -> performConversion());
    }

    private void performConversion() {
        String amountText = amountField.getText().trim();
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String from = (String) fromCurrency.getSelectedItem();
        String to = (String) toCurrency.getSelectedItem();

        // Use SwingWorker to perform the network call off the UI thread
        new SwingWorker<Double, Void>() {
            @Override
            protected Double doInBackground() throws Exception {
                // We call the API with amount=1 so that "result" equals the conversion rate.
                return getConversionRate(from, to);
            }

            @Override
            protected void done() {
                try {
                    double rate = get();
                    double convertedAmount = amount * rate;
                    resultLabel.setText(String.format("Result: %.2f %s", convertedAmount, to));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CurrencyConverter.this,
                            "Error retrieving conversion rate.", "Conversion Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }.execute();
    }

    /**
     * Fetches the conversion rate using the APILayer Exchange Rates Data API.
     * The API is called with an amount of 1 so that the result is the conversion rate.
     *
     * @param from Source currency code.
     * @param to   Target currency code.
     * @return The conversion rate.
     * @throws Exception if an error occurs during the API request.
     */
    private double getConversionRate(String from, String to) throws Exception {
        // Build the API URL; note the amount=1 to fetch the rate.
        String urlStr = "https://api.apilayer.com/exchangerates_data/convert?from="
                        + from + "&to=" + to + "&amount=1";
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        // Set the required API key header
        connection.setRequestProperty("apikey", API_KEY);

        // Get response code and decide which stream to use
        int responseCode = connection.getResponseCode();
        System.out.println("HTTP Response Code: " + responseCode);

        BufferedReader reader;
        if (responseCode == HttpURLConnection.HTTP_OK) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
        }

        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        reader.close();
        String response = responseBuilder.toString();
        System.out.println("API Response: " + response);

        // Parse the JSON response using org.json
        JSONObject json = new JSONObject(response);

        // If the API indicates success == false, throw an error with details
        if (!json.optBoolean("success", false)) {
            if (json.has("error")) {
                JSONObject errorObj = json.getJSONObject("error");
                String errorType = errorObj.optString("type", "Unknown error type");
                String errorInfo = errorObj.optString("info", "No additional info");
                throw new Exception("API returned an error: " + errorType + " - " + errorInfo);
            } else {
                throw new Exception("API returned success=false: " + response);
            }
        }

        // The "info" object contains "rate"
        JSONObject infoObj = json.getJSONObject("info");
        double rate = infoObj.getDouble("rate");
        return rate;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CurrencyConverter converter = new CurrencyConverter();
            converter.setVisible(true);
        });
    }
}
