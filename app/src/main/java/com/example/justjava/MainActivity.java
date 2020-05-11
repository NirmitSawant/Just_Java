package com.example.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    int quantity=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private String createOrderSummary(int no,String addWhippedCream,String addChocolate,String name,boolean whippedcream,boolean
                                      chocolate){
        String message = getString(R.string.name)+" "+ name;
        message += "\n"+getString(R.string.add_whipped_cream) +" "+ addWhippedCream;
        message += "\n" +getString(R.string.add_chocolate)+" "+ addChocolate;
        message += "\n"+getString(R.string.quantity) +": " + no;
        message += "\n"+getString(R.string.total) +" "+ calculatePrice(whippedcream,chocolate);
        message += "\n"+getString(R.string.thank_you);
        return message;
    }

    private String calculatePrice(boolean whippedcream,boolean chocolate){
        int baseprice=5;
        
        if(whippedcream){
            baseprice += 1;
        }
        
        if(chocolate){
            baseprice += 2;
        }
        
        int price = quantity * baseprice;
        
        Format format = NumberFormat.getCurrencyInstance(new Locale("en","in"));
        return format.format(new BigDecimal(price));
    }

    public void submitOrder(View view) {
        CheckBox whippedcreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean whippedcream = whippedcreamCheckBox.isChecked();
        
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean chocolate = chocolateCheckBox.isChecked();
        
        EditText namefield = (EditText) findViewById(R.id.name_field);
        String name = namefield.getText().toString();
        
        String hasWhippedCream;
        String hasChocolate;
        
        if(whippedcream){
            hasWhippedCream =getString(R.string.yes);
        }
        else{
            hasWhippedCream = getString(R.string.no);
        }
        if(chocolate){
            hasChocolate=getString(R.string.yes);
        }
        else{
            hasChocolate=getString(R.string.yes);
        }
        String message = createOrderSummary(quantity,hasWhippedCream,hasChocolate,name,whippedcream,chocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.just_java)+name);
        intent.putExtra(Intent.EXTRA_TEXT,message);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

        displayMessage(message);
    }

    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    public void increment(View view) {
        if(quantity<100) {
            quantity = quantity + 1;
        }else{
            Toast.makeText(this,getString(R.string.no_more),Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }


    public void decrement(View view) {
        if(quantity>1) {
            quantity = quantity - 1;
        }
        else{
            Toast.makeText(this, getString(R.string.no_less), Toast.LENGTH_SHORT).show();
        }
        display(quantity);
    }
}
