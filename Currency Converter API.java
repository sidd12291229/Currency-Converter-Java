import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Map;

public class Main extends JFrame {
    private JTextField amountTextField;
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;
    private JLabel resultLabel;

    private Map<String, Double> conversionRates;
    private DecimalFormat decimalFormat;

    public Main() {
        setTitle("Currency Converter");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        decimalFormat = new DecimalFormat("#0.00");

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        int sizeX = 6;
        int sizeY = 6;
        constraints.insets = new Insets(sizeX, sizeY, sizeX, sizeY);

        String[] currencies = {
                "AED", "AFN", "ALL", "AMD", "ANG", "AOA", "ARS", "AUD", "AWG", "AZN",
                "BAM", "BBD", "BDT", "BGN", "BHD", "BIF", "BMD", "BND", "BOB", "BRL",
                "BSD", "BTC", "BTN", "BWP", "BYN", "BZD", "CAD", "CDF", "CHF", "CLF",
                "CLP", "CNH", "CNY", "COP", "CRC", "CUC", "CUP", "CVE", "CZK", "DJF",
                "DKK", "DOP", "DZD", "EGP", "ERN", "ETB", "EUR", "FJD", "FKP", "GBP",
                "GEL", "GGP", "GHS", "GIP", "GMD", "GNF", "GTQ", "GYD", "HKD", "HNL",
                "HRK", "HTG", "HUF", "IDR", "ILS", "IMP", "INR", "IQD", "IRR", "ISK",
                "JEP", "JMD", "JOD", "JPY", "KES", "KGS", "KHR", "KMF", "KPW", "KRW",
                "KWD", "KYD", "KZT", "LAK", "LBP", "LKR", "LRD", "LSL", "LYD", "MAD",
                "MDL", "MGA", "MKD", "MMK", "MNT", "MOP", "MRO", "MRU", "MUR", "MVR",
                "MWK", "MXN", "MYR", "MZN", "NAD", "NGN", "NIO", "NOK", "NPR", "NZD",
                "OMR", "PAB", "PEN", "PGK", "PHP", "PKR", "PLN", "PYG", "QAR", "RON",
                "RSD", "RUB", "RWF", "SAR", "SBD", "SCR", "SDG", "SEK", "SGD", "SHP",
                "SLL", "SOS", "SRD", "SSP", "STD", "STN", "SVC", "SYP", "SZL", "THB",
                "TJS", "TMT", "TND", "TOP", "TRY", "TTD", "TWD", "TZS", "UAH", "UGX",
                "USD", "UYU", "UZS", "VEF", "VES", "VND", "VUV", "WST", "XAF", "XAG",
                "XAU", "XCD", "XDR", "XOF", "XPD", "XPF", "XPT", "YER", "ZAR", "ZMW",
                "ZWL"
        };


        JLabel amountLabel = new JLabel("Amount:");
        amountTextField = new JTextField(10);
        JLabel fromCurrencyLabel = new JLabel("From:");
        fromCurrencyComboBox = new JComboBox<>(currencies);
        JTextField fromTextField = new JTextField(10);
        JButton fromSearch = createSearchButton();
        JLabel toCurrencyLabel = new JLabel("To:");
        toCurrencyComboBox = new JComboBox<>(currencies);
        JButton toSearch = createSearchButton();
        JTextField toTextField = new JTextField(10);
        JButton convertButton = createConvertButton();
        resultLabel = new JLabel();

        addComponent(amountLabel, 0, 0, 1, 1);
        addComponent(amountTextField, 1, 0, 1, 1);
        addComponent(fromCurrencyLabel, 0, 1, 1, 1);
        addComponent(fromCurrencyComboBox, 1, 1, 1, 1);
        addComponent(fromTextField, 2, 1, 1, 1);
        addComponent(fromSearch, 3, 1, 1, 1);
        addComponent(toCurrencyLabel, 0, 2, 1, 1);
        addComponent(toCurrencyComboBox, 1, 2, 1, 1);
        addComponent(toTextField, 2, 2, 1, 1);
        addComponent(toSearch, 3, 2, 1 , 1);
        addComponent(convertButton, 0, 3, 4, 1);
        addComponent(resultLabel, 0, 4, 4, 1);

        convertButton.addActionListener(e -> {
            double amount = Double.parseDouble(amountTextField.getText());
            String fromCurrency = fromCurrencyComboBox.getSelectedItem().toString();
            String toCurrency = toCurrencyComboBox.getSelectedItem().toString();
            double convertedAmount = convertCurrency(fromCurrency, toCurrency, amount);
            resultLabel.setText(toCurrency + ": " + decimalFormat.format(convertedAmount));
        });

        ActionListener searchActionListener = e -> {
            fromCurrencyComboBox.setSelectedItem(fromTextField.getText());
            toCurrencyComboBox.setSelectedItem(toTextField.getText());
        };
        fromSearch.addActionListener(searchActionListener);
        toSearch.addActionListener(searchActionListener);
    }

    private void addComponent(Component component, int x, int y, int width, int height) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 5, 5, 5);
        add(component, constraints);
    }

    private JButton createSearchButton() {
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.BOLD, 12));
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(new Color(51, 153, 255)); // Blue background color
        return searchButton;
    }

    private JButton createConvertButton() {
        JButton convertButton = new JButton("Convert");
        convertButton.setFont(new Font("Arial", Font.BOLD, 14));
        convertButton.setForeground(Color.WHITE);
        convertButton.setBackground(new Color(0, 102, 0)); // Green background color
        return convertButton;
    }

    private double convertCurrency(String fromCurrency, String toCurrency, double amount) {
        try {
            String apiUrl = String.format("https://api.exchangerate-api.com/v4/latest/%s", fromCurrency);

            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Parse JSON response
                String jsonResponse = response.toString();
                double exchangeRate = Double.parseDouble(jsonResponse
                        .substring(jsonResponse.indexOf(toCurrency) + 5, jsonResponse.indexOf(",", jsonResponse.indexOf(toCurrency))))
                        * amount;
                return exchangeRate;
            } else {
                System.out.println("Error: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0.0;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main converter = new Main();
            converter.setVisible(true);
        });
    }
}
