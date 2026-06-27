package com.example.estatemap;




import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LeaseAgreementActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_agreement);

        // Access signature fields
        EditText landlordSignature = findViewById(R.id.landlord_signature);
        EditText tenantSignature = findViewById(R.id.tenant_signature);

        // Example: Validate if signatures are provided (this could be hooked to a "submit" button)
        findViewById(R.id.btn_submit).setOnClickListener(v -> {
            String landlord = landlordSignature.getText().toString().trim();
            String tenant = tenantSignature.getText().toString().trim();

            if (landlord.isEmpty() || tenant.isEmpty()) {
                Toast.makeText(this, "Please ensure both signatures are provided!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Lease Agreement Signed!", Toast.LENGTH_SHORT).show();
                // You can proceed to save or submit the data here
            }
        });
    }
}
