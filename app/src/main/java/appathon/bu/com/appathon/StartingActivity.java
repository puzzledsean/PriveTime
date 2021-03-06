package appathon.bu.com.appathon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by FreddieV4 on 1/31/2015.
 */
public class StartingActivity extends Activity {

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private boolean enabled = true;
    private Button enter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting);
        checkPlayServices();  //check for valid install of Google Play Services

        // Enable Local Datastore.
        Parse.enableLocalDatastore(StartingActivity.this);

        Parse.initialize(StartingActivity.this, "y3D6qePfthe7VxIIHdwynl7csbYGoDDOPFXteYHa", "l7QD57x45ILOGQidOtZq7DiD9tRmY3dVe115krz7");

        // Find EditText views
        final EditText name = (EditText) findViewById(R.id.nameText);
        final EditText phoneNumber = (EditText) findViewById(R.id.phoneNumberText);

        enter = (Button) findViewById(R.id.enterButton);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get user input from EditText and add values to strings
                String nameString = name.getText().toString();
                String phoneString = phoneNumber.getText().toString();

                // Put name and phone number into Parse data table
                ParseObject signUpObject = new ParseObject("SignUpObject");
                signUpObject.put("Name", nameString);
                signUpObject.put("Phone-Number", phoneString);
                signUpObject.saveInBackground();

                // Move to next Activity to test it
                Intent i = new Intent(StartingActivity.this, ContactsActivity.class);
                startActivity(i);
            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {

                finish();
            }
            return false;
        }
        return true;
    }
}
