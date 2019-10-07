package com.example.justjava;
/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 1;
    /**
     * This method is called when + button is clicked.
     **/
    public void increment(View view) {
        if (quantity == 15) {
            Toast.makeText(this,"You cannot have more than 15 coffees", Toast.LENGTH_SHORT).show();
            return; }
        quantity = quantity + 1;
        displayQuantity(quantity);
        displayPrice(quantity * 5);
    }

    /**
     * This method is called when - button is clicked.
     **/
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this,"You cannot have less than 1 coffees", Toast.LENGTH_SHORT).show();
            return; }
        quantity = quantity - 1;
        displayQuantity(quantity);
        displayPrice(quantity * 5);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();
        Log.v("MailActivity","Name : "+ name);
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhipedCream = whippedCreamCheckBox.isChecked();
        Log.v("MainActivity","Has Whipped Cream " + hasWhipedCream);
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        Log.v("MainActivity","Has Chocolate " + hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.CoffeeOrderFor,name));
        intent.putExtra(Intent.EXTRA_TEXT,createOrderSummary(name,calculatePrice(hasWhipedCream,hasChocolate),hasWhipedCream,hasChocolate));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

        displayMessage(createOrderSummary(name,calculatePrice(hasWhipedCream,hasChocolate),hasWhipedCream,hasChocolate));



    }
    /**
     * Calculates the price of the order based on the current quantity.
     * @return the price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream){
            basePrice += 1;}
        if (addChocolate){
            basePrice += 2; }
        return quantity * basePrice;
    }

    public String createOrderSummary(String name , int price , Boolean addWhippedCream , Boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage += "\n"+ getString(R.string.order_summary_quantity,quantity);
        priceMessage += "\n"+ getString(R.string.pricePerCup,5);
        if (addWhippedCream == true){
            priceMessage += "\n"+getString(R.string.addWhippedCream);}
        if (addChocolate == true){priceMessage += "\n"+getString(R.string.addChocolate);}
        priceMessage += "\n"+ getString(R.string.order_summary_price,price);
        priceMessage += "\n"+getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     **/
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     **/
    private void displayPrice(float number) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summary_text_view);
        priceTextView.setText("$ " + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
