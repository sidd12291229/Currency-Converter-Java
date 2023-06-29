import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class CurrencyConverter extends JFrame {
    private JTextField amountTextField;
    private JComboBox<String> fromCurrencyComboBox;
    private JComboBox<String> toCurrencyComboBox;
    private JLabel resultLabel;

    private Map<String, Double> conversionRates;
    private DecimalFormat decimalFormat;

    public CurrencyConverter() {
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
                "USD", "EUR", "INR", "JPY", "GBP", "CAD", "AUD", "CHF", "HKD", "NZD",
                "SEK", "KRW", "CNY", "RUB", "SGD", "BRL", "PLN", "TRY", "ZAR", "AED",
                "MXN", "THB"
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
            double convertedAmount = convertCurrency(amount, fromCurrency, toCurrency);
            resultLabel.setText(toCurrency + ": " + decimalFormat.format(convertedAmount));
        });

        ActionListener searchActionListener = e -> {
            fromCurrencyComboBox.setSelectedItem(fromTextField.getText());
            toCurrencyComboBox.setSelectedItem(toTextField.getText());
        };

        fromSearch.addActionListener(searchActionListener);
        toSearch.addActionListener(searchActionListener);

        initializeConversionRates();
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

    private void initializeConversionRates() {
        conversionRates = new HashMap<>();
        conversionRates.put("USD", 1.0);
        conversionRates.put("EUR", 0.85);
        conversionRates.put("INR", 74.50);
        conversionRates.put("JPY", 110.50);
        conversionRates.put("GBP", 0.72);
        conversionRates.put("CAD", 1.24);
        conversionRates.put("AUD", 1.34);
        conversionRates.put("CHF", 0.91);
        conversionRates.put("HKD", 7.77);
        conversionRates.put("NZD", 1.46);
        conversionRates.put("SEK", 8.91);
        conversionRates.put("KRW", 1178.50);
        conversionRates.put("CNY", 6.46);
        conversionRates.put("RUB", 73.25);
        conversionRates.put("SGD", 1.37);
        conversionRates.put("BRL", 5.24);
        conversionRates.put("PLN", 3.90);
        conversionRates.put("TRY", 8.64);
        conversionRates.put("ZAR", 13.99);
        conversionRates.put("AED", 3.67);
        conversionRates.put("MXN", 20.12);
        conversionRates.put("THB", 31.51);
    }

    private double convertCurrency(double amount, String fromCurrency, String toCurrency) {
        double fromRate = conversionRates.get(fromCurrency);
        double toRate = conversionRates.get(toCurrency);

        return (amount / fromRate) * toRate;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CurrencyConverter().setVisible(true));
    }
}

