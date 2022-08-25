package com.gicode.simpleCalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gicode.simpleCalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    Button[] numericButtons;
    Button btnClear, btnClearDisplay, btnNegative, btnDelete,
            btnDivision, btnMultiplication, btnSubmission, btnSum, btnEqual; //btnDot;

    TextView tvDisplay, tvPhrase;

    boolean isOperatorPressed = false;
    boolean isEqualPressed = false;

    double num1 = 0;
    double num2 = 0;
    char op = ' ';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        findViews();

        numericButtons = new Button[11];
        for (int i = 0; i < 11; i++) {
            int btnID = getResources().getIdentifier("btn_" + i, "id", getPackageName());
            numericButtons[i] = findViewById(btnID);

            int finalI = i;
            numericButtons[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (isOperatorPressed) {
                        tvDisplay.setText("");
                        isOperatorPressed = false;
                    }

                    if (isEqualPressed) {
                        tvPhrase.setText("");
                        tvDisplay.setText("");
                        isEqualPressed = false;
                    }

                    String tvDisplayText = tvDisplay.getText().toString().replace(",", "");

                    if (tvDisplayText.equals("0")) {
                        tvDisplayText = "";
                    }

                    // case for button '000'
                    if (finalI < 10) {
                        tvDisplayText += finalI;
                    } else {
                        if (tvDisplayText.equals("")) tvDisplayText += "0";
                        else tvDisplayText += "000";
                    }

                    tvDisplay.setText(addThousandSeparator(tvDisplayText));
                }
            });

        }

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOperatorPressed = false;
                num1 = 0;
                num2 = 0;
                op = ' ';
                tvPhrase.setText("");
                tvDisplay.setText("0");
            }
        });

        btnClearDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvDisplay.setText("0");
            }
        });

        btnNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tvDisplayText = tvDisplay.getText().toString().replace(",", "");
                if (tvDisplayText.contains("-")) {
                    tvDisplayText = tvDisplayText.substring(1);
                } else {
                    tvDisplayText = "-" + tvDisplayText;
                }
                tvDisplay.setText(addThousandSeparator(tvDisplayText));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOperatorPressed = false;
                String tvDisplayText = tvDisplay.getText().toString().replace(",", "");
                if (tvDisplayText.length() > 0) {
                    tvDisplayText = tvDisplayText.substring(0, tvDisplayText.length() - 1);
                    if (tvDisplayText.length() == 0) {
                        tvDisplayText = "0";
                    }
                    tvDisplay.setText(addThousandSeparator(tvDisplayText));
                }
            }
        });

        btnDivision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOperatorPressed = true;
                String tvDisplayText = tvDisplay.getText().toString().replace(",", "");

                if (isEqualPressed) {
                    tvPhrase.setText("");
                    isEqualPressed = false;
                }

                if (op != '÷') {
                    num1 = Double.parseDouble(tvDisplayText);
                    String temp = addThousandSeparator(tvDisplayText) + " ÷ ";
                    tvPhrase.setText(temp);
                    op = '÷';
                } else {
                    num2 = Double.parseDouble(tvDisplayText);
                    if (num1 == 0 || num2 == 0)
                        return;
                    num1 = calculate(num1, num2, op);

                    String strNum1 = "";
                    if (isInteger(num1)) {
                        strNum1 = String.valueOf(num1).substring(0, String.valueOf(num1).indexOf("."));
                    } else {
                        strNum1 = String.valueOf(num1);
                    }

                    String temp = addThousandSeparator(strNum1) + " ÷ ";
                    tvPhrase.setText(temp);

                    tvDisplay.setText(addThousandSeparator(strNum1));
                }
            }
        });

        btnMultiplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOperatorPressed = true;
                String tvDisplayText = tvDisplay.getText().toString().replace(",", "");

                if (isEqualPressed) {
                    tvPhrase.setText("");
                    isEqualPressed = false;
                }

                if (op != 'x') {
                    num1 = Double.parseDouble(tvDisplayText);
                    String temp = addThousandSeparator(tvDisplayText) + " x ";
                    tvPhrase.setText(temp);
                    op = 'x';
                } else {
                    num2 = Double.parseDouble(tvDisplayText);
                    num1 = calculate(num1, num2, op);

                    String strNum1 = "";
                    if (isInteger(num1)) {
                        strNum1 = String.valueOf(num1).substring(0, String.valueOf(num1).indexOf("."));
                    } else {
                        strNum1 = String.valueOf(num1);
                    }

                    String temp = addThousandSeparator(strNum1) + " x ";
                    tvPhrase.setText(temp);

                    tvDisplay.setText(addThousandSeparator(strNum1));
                }
            }
        });

        btnSubmission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOperatorPressed = true;
                String tvDisplayText = tvDisplay.getText().toString().replace(",", "");

                if (isEqualPressed) {
                    tvPhrase.setText("");
                    isEqualPressed = false;
                }

                if (op != '-') {
                    num1 = Double.parseDouble(tvDisplayText);
                    String temp = addThousandSeparator(tvDisplayText) + " - ";
                    tvPhrase.setText(temp);
                    op = '-';
                } else {
                    num2 = Double.parseDouble(tvDisplayText);
                    num1 = calculate(num1, num2, op);

                    String strNum1 = "";
                    if (isInteger(num1)) {
                        strNum1 = String.valueOf(num1).substring(0, String.valueOf(num1).indexOf("."));
                    } else {
                        strNum1 = String.valueOf(num1);
                    }

                    String temp = addThousandSeparator(strNum1) + " - ";
                    tvPhrase.setText(temp);

                    tvDisplay.setText(addThousandSeparator(strNum1));
                }
            }
        });

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOperatorPressed = true;
                String tvDisplayText = tvDisplay.getText().toString().replace(",", "");

                if (isEqualPressed) {
                    tvPhrase.setText("");
                    isEqualPressed = false;
                }

                if (op != '+') {
                    num1 = Double.parseDouble(tvDisplayText);
                    String temp = addThousandSeparator(tvDisplayText) + " + ";
                    tvPhrase.setText(temp);
                    op = '+';
                } else {
                    num2 = Double.parseDouble(tvDisplayText);
                    num1 = calculate(num1, num2, op);

                    String strNum1 = "";
                    if (isInteger(num1)) {
                        strNum1 = String.valueOf(num1).substring(0, String.valueOf(num1).indexOf("."));
                    } else {
                        strNum1 = String.valueOf(num1);
                    }

                    String temp = addThousandSeparator(strNum1) + " + ";
                    tvPhrase.setText(temp);

                    tvDisplay.setText(addThousandSeparator(strNum1));
                }
            }
        });

        btnEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tvDisplayText = tvDisplay.getText().toString().replace(",", "");
                if (op != ' ') {
                    isEqualPressed = true;
                    isOperatorPressed = false;
                    num2 = Double.parseDouble(tvDisplayText);
                    double res = calculate(num1, num2, op);

                    String strNum1 = "";
                    if (isInteger(num1)) {
                        strNum1 = String.valueOf(num1).substring(0, String.valueOf(num1).indexOf("."));
                    } else {
                        strNum1 = String.valueOf(num1);
                    }

                    String strNum2 = "";
                    if (isInteger(num2)) {
                        strNum2 = String.valueOf(num2).substring(0, String.valueOf(num2).indexOf("."));
                    } else {
                        strNum2 = String.valueOf(num2);
                    }

                    String strRes = "";
                    if (isInteger(res)) {
                        strRes = String.valueOf(res).substring(0, String.valueOf(res).indexOf("."));
                    } else {
                        strRes = String.valueOf(res);
                    }

                    tvDisplay.setText(addThousandSeparator(strRes));
                    String temp = addThousandSeparator(strNum1) + " " + op + " " + addThousandSeparator(strNum2) + " = ";
                    tvPhrase.setText(temp);
                    op = ' ';
                    num1 = 0;
                    num2 = 0;
                }
            }
        });

//        btnDot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String tvDisplayText = tvDisplay.getText().toString().replace(",", "");
//                if (tvDisplayText.isEmpty() || tvDisplayText.contains(".")) {
//                    return;
//                }
//                tvDisplayText += '.';
//                tvDisplay.setText(addThousandSeparator(tvDisplayText));
//            }
//        });

    }

    private boolean isInteger(double num) {
        return Math.ceil(num) == Math.floor(num);
    }

    private double calculate(double num1, double num2, char op) {
        switch (op) {
            case '+':
                return num1 + num2;
            case '-':
                return num1 - num2;
            case 'x':
                return num1 * num2;
            case '÷':
                return num1 / num2;
            default:
                return 0;
        }
    }

    /**
     * Add , every thousand.
     */
    private String addThousandSeparator(String amount) {
        String integer, decimal;
        if (amount.indexOf('.') >= 0) {
            integer = amount.substring(0, amount.indexOf('.'));
            decimal = amount.substring(amount.indexOf('.'));
        } else {
            integer = amount;
            decimal = "";
        }

        if (integer.length() > 3) {
            StringBuilder tmp = new StringBuilder(integer);
            int i = integer.length() - 3;
            while (i > 0) {
                tmp.insert(i, ",");
                i = i - 3;
            }
            integer = tmp.toString();
        }
        return integer + decimal;
    }

    private void findViews() {
        btnClear = binding.btnClear;
        btnClearDisplay = binding.btnClearDisplay;
        btnNegative = binding.btnNegative;
        btnDelete = binding.btnDelete;
        btnDivision = binding.btnDivision;
        btnMultiplication = binding.btnMultiplication;
        btnSubmission = binding.btnSubmission;
        btnSum = binding.btnSum;
        btnEqual = binding.btnEqual;
        // btnDot = findViewById(R.id.btn_dot);
        tvDisplay = binding.tvDisplay;
        tvPhrase = binding.tvPhrase;
    }

}// end of MainActivity class
