package com.srinith.gpa_sriramulas1_calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText[] gradeInputs = new EditText[5];
    private TextView gpaResult;
    private ConstraintLayout mainLayout;
    private Button computeGpaButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        gradeInputs[0] = findViewById(R.id.course1Grade);
        gradeInputs[1] = findViewById(R.id.course2Grade);
        gradeInputs[2] = findViewById(R.id.course3Grade);
        gradeInputs[3] = findViewById(R.id.course4Grade);
        gradeInputs[4] = findViewById(R.id.course5Grade);
        gpaResult = findViewById(R.id.gpaResult);
        computeGpaButton = findViewById(R.id.computeGpaButton);
        mainLayout = findViewById(R.id.mainLayout);

        computeGpaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("Compute GPA".equals(computeGpaButton.getText().toString())) {
                    computeGPA();
                } else {
                    clearForm();
                }
            }
        });


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                computeGpaButton.setText("Compute GPA");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };

        for (EditText editText : gradeInputs) {
            editText.addTextChangedListener(textWatcher);
        }
    }

    private void computeGPA() {
        double sum = 0;
        boolean isValid = true;
        for (EditText gradeInput : gradeInputs) {
            String input = gradeInput.getText().toString();
            gradeInput.setBackgroundColor(Color.WHITE);
            if (input.isEmpty()) {
                gradeInput.setError("Cannot be empty");
                gradeInput.setBackgroundColor(Color.RED);
                isValid = false;
                continue;
            }
            try {
                double grade = Double.parseDouble(input);
                if (grade < 0 || grade > 100) {
                    gradeInput.setError("Enter a valid grade");
                    gradeInput.setBackgroundColor(Color.RED);
                    isValid = false;
                } else {
                    sum += grade;
                }
            } catch (NumberFormatException e) {
                gradeInput.setError("Invalid input");
                gradeInput.setBackgroundColor(Color.RED);
                isValid = false;
            }
        }

        if (isValid) {
            double gpa = sum / gradeInputs.length;
            gpaResult.setText(String.format("GPA: %.2f", gpa));
            updateBackgroundColor(gpa);
            computeGpaButton.setText("Clear Form");
        }
    }

    private void updateBackgroundColor(double gpa) {
        if (gpa < 60) {
            mainLayout.setBackgroundColor(Color.RED);
        } else if (gpa <= 79) {
            mainLayout.setBackgroundColor(Color.YELLOW);
        } else {
            mainLayout.setBackgroundColor(Color.GREEN);
        }
    }


    private void clearForm() {
        for (EditText editText : gradeInputs) {
            editText.setText("");
            editText.setBackgroundResource(android.R.drawable.edit_text);
        }

        gpaResult.setText("");
        mainLayout.setBackgroundColor(Color.WHITE);
        computeGpaButton.setText("Compute GPA");
    }
}
