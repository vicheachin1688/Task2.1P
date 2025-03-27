package com.asd.task21p;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private Spinner sourceUnitSpinner, destinationUnitSpinner;
    private EditText valueInput;
    private TextView convertedValueText;
    private Button convertButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI elements
        sourceUnitSpinner = findViewById(R.id.sourceUnitSpinner);
        destinationUnitSpinner = findViewById(R.id.destinationUnitSpinner);
        valueInput = findViewById(R.id.valueInput);
        convertedValueText = findViewById(R.id.convertedValueText);
        convertButton = findViewById(R.id.convertButton);
        // Set up adapters for spinners
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getUnits());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sourceUnitSpinner.setAdapter(adapter);
        destinationUnitSpinner.setAdapter(adapter);


        // Handle conversion logic when the button is clicked
        convertButton.setOnClickListener(v -> {
            String sourceUnit = sourceUnitSpinner.getSelectedItem().toString();
            String destinationUnit = destinationUnitSpinner.getSelectedItem().toString();
            String inputValue = valueInput.getText().toString();

            if (!inputValue.isEmpty()) {
                double value = Double.parseDouble(inputValue);
                double convertedValue = convertUnits(sourceUnit, destinationUnit, value);
                convertedValueText.setText("Converted Value: " + convertedValue);
            } else {
                convertedValueText.setText("Please enter a value");
            }
        });
    }

    // Get the list of available units for conversion
    private String[] getUnits() {
        return new String[] {
                "inch", "Foot", "Yard", "Mile",
                "Pound", "Ounce", "Ton",
                "Celsius", "Fahrenheit", "Kelvin"
        };
    }

    // Convert units based on the selected source and destination units
    private double convertUnits(String sourceUnit, String destinationUnit, double value) {
        // Length Conversion (to cm for simplicity)
        if (sourceUnit.equals("inch")) value *= 2.54;
        if (sourceUnit.equals("Foot")) value *= 30.48;
        if (sourceUnit.equals("Yard")) value *= 91.44;
        if (sourceUnit.equals("Mile")) value *= 160934;

        // Weight Conversions (to kg for simplicity)
        if (sourceUnit.equals("Pound")) value *= 0.453592;
        if (sourceUnit.equals("Ounce")) value *= 0.0283495;
        if (sourceUnit.equals("Ton")) value *= 907.185;

        // Temperature Conversions
        if (sourceUnit.equals("Celsius") && destinationUnit.equals("Fahrenheit")) return (value * 1.8) + 32;
        if (sourceUnit.equals("Fahrenheit") && destinationUnit.equals("Celsius")) return (value - 32) / 1.8;
        if (sourceUnit.equals("Celsius") && destinationUnit.equals("Kelvin")) return value + 273.15;
        if (sourceUnit.equals("Kelvin") && destinationUnit.equals("Celsius")) return value - 273.15;

        // If source and destination units are the same
        return value;
    }
}
